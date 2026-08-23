package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import com.cannolicatfish.rankine.init.RankineArmorMaterials;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.PackagedArmorItem;
import com.cannolicatfish.rankine.items.PackagedToolItem;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Alloy-correctness regression tests (review P1 "Alloy system").
 *
 * <p>All three cover port bugs that compile cleanly and are invisible to the datagen cache: a
 * recipe constraint lost on the JSON encode side, a recipe id that is only resolved lazily, and a
 * block/item data hand-off routed through a component nothing writes.
 */
@GameTestHolder(ProjectRankine.MODID)
@PrefixGameTestTemplate(false)
public class RankineAlloyTests {

    private static final String STEEL_ALLOYING = "rankine:alloying/steel_alloying";
    private static final String CAST_IRON_ALLOYING = "rankine:alloying/cast_iron_alloying";

    private static void assertEmpty(List<String> problems, String what) {
        if (!problems.isEmpty()) {
            int shown = Math.min(problems.size(), 20);
            throw new GameTestAssertException(problems.size() + " " + what + ": "
                    + String.join(", ", problems.subList(0, shown))
                    + (problems.size() > shown ? " (+" + (problems.size() - shown) + " more)" : ""));
        }
    }

    private static ItemStack ingot(String id, String composition, String alloyRecipe) {
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(id)));
        stack.set(RankineDataComponents.ALLOY_COMPOSITION.get(), composition);
        stack.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse(alloyRecipe));
        return stack;
    }

    /** An alloy rod carrying the given source alloy recipe, or none at all when {@code alloyRecipe} is null. */
    private static ItemStack rod(String alloyRecipe) {
        ItemStack stack = new ItemStack(RankineItems.ALLOY_ROD.get());
        if (alloyRecipe != null) {
            stack.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse(alloyRecipe));
        }
        return stack;
    }

    /**
     * The 40 tier-3 tool recipes must demand a STEEL alloy rod, not just any alloy rod.
     *
     * <p>1.18.2 wrote a per-key {@code alloyRecipe} constraint into each of these recipe JSONs and
     * {@code AlloyCraftingRecipe.checkMatch} enforced it by comparing the alloy recipe stamped on
     * the ingredient's example stack against the stack in the slot. The 1.21 port kept the enforcing
     * code but lost the constraint on the way out to disk: the datagen builder hands the recipe
     * object to {@code RecipeOutput}, which re-encodes it through {@code Ingredient.CODEC}, and that
     * codec writes an item id and drops every data component. The generated file then said "any
     * rankine:alloy_rod", which is exactly what the recipe accepted.
     *
     * <p>Both directions are asserted deliberately: accepting the steel rod alone would still pass
     * with the constraint missing, so the wrong-alloy and no-alloy rejections are what make this
     * test non-vacuous.
     */
    @GameTest(template = "empty")
    public void tierThreeToolRecipesRequireASteelRod(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        record Shape(String recipeId, String ingotId, String alloyRecipe, int[] ingotSlots, int rodSlot) {}
        // Slot indices into a 3x3 CraftingInput, matching the 1.18.2 patterns for each tool.
        List<Shape> shapes = List.of(
                // pickaxe: ### / _s_ / _s_  (two rod slots; the second is filled with the same rod)
                new Shape("rankine:cobalt_superalloy_pickaxe", "rankine:cobalt_superalloy_ingot",
                        "rankine:alloying/cobalt_superalloy_alloying", new int[]{0, 1, 2}, 4),
                // sword: # / # / s
                new Shape("rankine:nickel_superalloy_sword", "rankine:nickel_superalloy_ingot",
                        "rankine:alloying/nickel_superalloy_alloying", new int[]{0, 3}, 6),
                // knife: _# / s_
                new Shape("rankine:ender_amalgam_knife", "rankine:ender_amalgam_ingot",
                        "rankine:alloying/ender_amalgam_alloying", new int[]{1}, 3),
                // axe: ## / #s / _s
                new Shape("rankine:stainless_steel_axe", "rankine:stainless_steel_ingot",
                        "rankine:alloying/stainless_steel_alloying", new int[]{0, 1, 3}, 4),
                // shovel: # / s / s
                new Shape("rankine:tungsten_heavy_alloy_shovel", "rankine:tungsten_heavy_alloy_ingot",
                        "rankine:alloying/tungsten_heavy_alloy_alloying", new int[]{0}, 3));

        record Rod(String name, String alloyRecipe, boolean expectMatch) {}
        List<Rod> rods = List.of(
                new Rod("steel", STEEL_ALLOYING, true),
                new Rod("castiron", CAST_IRON_ALLOYING, false),
                new Rod("norecipe", null, false));

        for (Shape shape : shapes) {
            var holder = level.getRecipeManager().byKey(ResourceLocation.parse(shape.recipeId())).orElse(null);
            if (holder == null) {
                problems.add(shape.recipeId() + ": recipe not found");
                continue;
            }
            if (!(holder.value() instanceof AlloyCraftingRecipe recipe)) {
                problems.add(shape.recipeId() + ": not an AlloyCraftingRecipe");
                continue;
            }
            for (Rod variant : rods) {
                List<ItemStack> grid = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    grid.add(ItemStack.EMPTY);
                }
                for (int slot : shape.ingotSlots()) {
                    grid.set(slot, ingot(shape.ingotId(), "70Co-20Cr-10Ni", shape.alloyRecipe()));
                }
                // Fill every rod slot the pattern uses: the rod slot itself and, for the 3-tall
                // shapes, the slot directly under it.
                grid.set(shape.rodSlot(), rod(variant.alloyRecipe()));
                if (shape.rodSlot() + 3 < 9 && needsSecondRod(shape.recipeId())) {
                    grid.set(shape.rodSlot() + 3, rod(variant.alloyRecipe()));
                }

                boolean matched = recipe.matches(CraftingInput.of(3, 3, grid), level);
                if (matched != variant.expectMatch()) {
                    // Kept terse: assertEmpty's message is written to a lectern book, which rejects
                    // anything over 1024 characters.
                    problems.add(shape.recipeId().substring("rankine:".length()) + "/" + variant.name()
                            + "=" + matched);
                }
            }
        }

        assertEmpty(problems, "tier-3 recipes with the wrong alloy-rod constraint");
        helper.succeed();
    }

    /** Pickaxe, sword and shovel are three rows tall and use two rod slots; knife and axe use one column of rods. */
    private static boolean needsSecondRod(String recipeId) {
        return recipeId.endsWith("_pickaxe") || recipeId.endsWith("_sword") || recipeId.endsWith("_shovel")
                || recipeId.endsWith("_axe");
    }

    /**
     * Packaged loot gear must mint a complete item on a world where nothing has been smelted yet.
     *
     * <p>Recipes lost {@code getId()} in 1.21; {@code AlloyingRecipe} regained it as a field that is
     * only filled in lazily, from {@code matches()}/{@code generateResult()}. Nothing warms it on a
     * fresh world, so {@code PackagedToolItem}/{@code PackagedArmorItem} passed a null id into
     * {@code createAlloyNBT}, which then REMOVES the alloy-recipe component — leaving loot-chest
     * gear with no source recipe, a blank name and stats missing every recipe bonus.
     *
     * <p>The cold path is what runs here: this test never smelts, and the ids now come from the
     * {@code RecipeHolder}s the RecipeManager has held since datapack load.
     *
     * <p>Also pins the draw itself. Both items pick from the list of alloying recipes that resolve
     * their elements, and 2 of the 49 shipped recipes (crucible steel, damascus steel) declare
     * none — so a draw bounded by the unfiltered list overruns the filtered one about 4% of the
     * time. 128 iterations make hitting that a near-certainty (~1 in 45,000 to miss), and asserting
     * that every minted id is one of the element-resolving recipes catches the other direction.
     */
    @GameTest(template = "empty", timeoutTicks = 600)
    public void packagedGearResolvesItsAlloyRecipe(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        List<RecipeHolder<AlloyingRecipe>> all = level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
        if (all.isEmpty()) {
            throw new GameTestAssertException("no alloying recipes loaded — nothing to package");
        }
        Set<ResourceLocation> drawable = new HashSet<>();
        for (RecipeHolder<AlloyingRecipe> holder : all) {
            if (!holder.value().getElementList(level).isEmpty()) {
                drawable.add(holder.id());
            }
        }
        if (drawable.isEmpty()) {
            throw new GameTestAssertException("no alloying recipe resolves its elements — nothing is drawable");
        }

        PackagedToolItem toolItem = (PackagedToolItem) RankineItems.PACKAGED_TOOL.get();
        PackagedArmorItem armorItem = (PackagedArmorItem) RankineItems.PACKAGED_ARMOR.get();

        ItemStack toolPackage = new ItemStack(toolItem);
        toolPackage.set(RankineDataComponents.FORCE_TOOL.get(), "PICKAXE");
        ItemStack armorPackage = new ItemStack(armorItem);
        armorPackage.set(RankineDataComponents.FORCE_ARMOR.get(), "CHESTPLATE");

        int named = 0;
        int draws = 128;
        for (int i = 0; i < draws; i++) {
            for (boolean tool : new boolean[]{true, false}) {
                String what = (tool ? "packaged tool" : "packaged armor") + " draw " + i;
                ItemStack minted = tool
                        ? toolItem.genRandomTool(toolPackage, level)
                        : armorItem.genRandomTool(armorPackage, level);

                ResourceLocation recipeId = IAlloyItem.getAlloyRecipe(minted);
                if (recipeId == null) {
                    problems.add(what + ": no alloy recipe on the minted " + BuiltInRegistries.ITEM.getKey(minted.getItem()));
                    continue;
                }
                var holder = level.getRecipeManager().byKey(recipeId).orElse(null);
                if (holder == null || !(holder.value() instanceof AlloyingRecipe alloy)) {
                    problems.add(what + ": alloy recipe " + recipeId + " is not a loaded AlloyingRecipe");
                    continue;
                }
                if (!drawable.contains(recipeId)) {
                    problems.add(what + ": drew " + recipeId + ", which resolves no elements and must "
                            + "never be picked");
                }
                if (IAlloyItem.getAlloyComposition(minted).isEmpty()) {
                    problems.add(what + ": empty composition");
                }
                var stats = minted.get(RankineDataComponents.ALLOY_STATS.get());
                if (stats == null || stats.durability() <= 0) {
                    problems.add(what + ": no usable alloy stats (" + stats + ")");
                }
                // getLocalName() is itself derived from the recipe id, so a resolved id is the only
                // way a name override can ever be stamped. Only 3 of the 47 drawable alloys declare
                // one; the other 44 must come out with no override at all, so this is asserted for
                // every draw rather than only the named ones.
                String expectedName = alloy.getLocalName();
                String actualName = IAlloyItem.getNameOverride(minted);
                if (!expectedName.isEmpty()) {
                    named++;
                }
                if (!expectedName.equals(actualName)) {
                    problems.add(what + ": name override '" + actualName + "', expected '" + expectedName + "'");
                }
            }
        }

        if (named == 0) {
            problems.add("not one of " + (draws * 2) + " draws produced a named alloy — either every alloying "
                    + "recipe stopped declaring a local name, or ids are still unresolved");
        }

        assertEmpty(problems, "packaged-gear minting failures");
        helper.succeed();
    }

    /**
     * Placing an alloy block and breaking it again must return the same alloy, not the item's default.
     *
     * <p>1.18.2 copied the whole stack tag onto the block entity on place and back onto the drop on
     * break. The port routed both halves through {@code DataComponents.CUSTOM_DATA}, which alloy
     * items never write — so the drop came back bare and {@code AlloyBlockItem.inventoryTick}
     * re-initialised it to the block's hard-coded default composition, silently converting any
     * custom alloy into the stock one.
     *
     * <p>Drives the real hooks: {@code BlockItem#place} through {@code useOn} for the placement and
     * {@code AlloyBlock#playerWillDestroy} for the break, then reads the popped ItemEntity.
     */
    @GameTest(template = "empty")
    public void alloyBlockSurvivesPlaceAndBreak(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        if (!level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            throw new GameTestAssertException("doTileDrops is off in this test world; the break path cannot drop");
        }

        // Deliberately NOT the item's default "75Au-22Cu-3Ni": a wipe-and-reinitialise failure
        // produces the default, so a distinct composition is what separates "preserved" from "reset".
        final String composition = "60Au-40Cu";
        final String alloyRecipe = "rankine:alloying/rose_gold_alloying";
        final String nameOverride = "item.rankine.rose_gold_alloying";
        final int color = 0x123456;

        ItemStack placing = new ItemStack(RankineItems.ROSE_GOLD_BLOCK.get());
        placing.set(RankineDataComponents.ALLOY_COMPOSITION.get(), composition);
        placing.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse(alloyRecipe));
        placing.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
        placing.set(RankineDataComponents.COLOR.get(), color);

        BlockPos floor = new BlockPos(1, 1, 1);
        BlockPos target = floor.above();
        helper.setBlock(floor, Blocks.STONE);
        helper.setBlock(target, Blocks.AIR);

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, placing);

        BlockPos absFloor = helper.absolutePos(floor);
        BlockHitResult hit = new BlockHitResult(
                Vec3.atCenterOf(absFloor).add(0.0D, 0.5D, 0.0D), Direction.UP, absFloor, false);
        placing.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hit));

        BlockPos absTarget = helper.absolutePos(target);
        BlockState placed = level.getBlockState(absTarget);
        if (!placed.is(com.cannolicatfish.rankine.init.RankineBlocks.ROSE_GOLD_BLOCK.get())) {
            throw new GameTestAssertException("rose gold block did not place; found " + placed);
        }

        // The placement half: vanilla's applyComponentsFromItemStack must have parked the alloy data
        // on the block entity, otherwise the break half has nothing to give back.
        BlockEntity be = level.getBlockEntity(absTarget);
        if (be == null) {
            throw new GameTestAssertException("placed alloy block has no block entity");
        }
        var held = be.collectComponents();
        if (!composition.equals(held.get(RankineDataComponents.ALLOY_COMPOSITION.get()))) {
            problems.add("block entity holds composition '" + held.get(RankineDataComponents.ALLOY_COMPOSITION.get())
                    + "', expected '" + composition + "'");
        }

        // The break half.
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.NETHERITE_PICKAXE));
        placed.getBlock().playerWillDestroy(level, absTarget, placed, player);

        List<ItemEntity> drops = level.getEntitiesOfClass(ItemEntity.class,
                new AABB(absTarget).inflate(2.0D));
        ItemStack dropped = ItemStack.EMPTY;
        for (ItemEntity entity : drops) {
            if (entity.getItem().is(RankineItems.ROSE_GOLD_BLOCK.get())) {
                dropped = entity.getItem();
                break;
            }
        }
        if (dropped.isEmpty()) {
            throw new GameTestAssertException("breaking the alloy block dropped no rose gold block (found "
                    + drops.size() + " item entities)");
        }

        if (!composition.equals(IAlloyItem.getAlloyComposition(dropped))) {
            problems.add("drop composition '" + IAlloyItem.getAlloyComposition(dropped)
                    + "', expected '" + composition + "'");
        }
        ResourceLocation droppedRecipe = IAlloyItem.getAlloyRecipe(dropped);
        if (droppedRecipe == null || !alloyRecipe.equals(droppedRecipe.toString())) {
            problems.add("drop alloy recipe " + droppedRecipe + ", expected " + alloyRecipe);
        }
        if (!nameOverride.equals(IAlloyItem.getNameOverride(dropped))) {
            problems.add("drop name override '" + IAlloyItem.getNameOverride(dropped)
                    + "', expected '" + nameOverride + "'");
        }
        if (IAlloyItem.getColorNBT(dropped) != color) {
            problems.add("drop color " + IAlloyItem.getColorNBT(dropped) + ", expected " + color);
        }

        assertEmpty(problems, "alloy block round-trip failures");
        helper.succeed();
    }

    // ----------------------------------------------------------------------------------------
    // Worn alloy armor (review #20/#21/#27)
    // ----------------------------------------------------------------------------------------

    private static String tex(ArmorMaterial.Layer layer, boolean inner) {
        return layer.texture(inner).toString();
    }

    /**
     * The ALLOY material must declare the two render passes 1.18.2's dyeable branch drew, and the
     * other five materials must keep declaring exactly one.
     *
     * <p>1.18.2 {@code HumanoidArmorLayer#renderArmorPiece} rendered a {@code DyeableArmorItem}
     * twice: the base texture tinted with {@code getColor(stack)}, then an untinted {@code overlay}
     * pass. 1.21 turns that pair into the material's layer list, and the port shipped a single
     * layer, so the second pass had no way to happen and — because the tint is per-layer — the
     * first pass had nothing marked dyeable to tint. This asserts the layer list itself, since the
     * render call that consumes it is client-only.
     *
     * <p>The non-alloy half is not decoration: {@code Layer} resolves its texture eagerly from the
     * suffix, and brigandine/rubber/diving/conduit_diving ship no {@code _overlay} asset, so an
     * overlay layer leaking onto them would draw the missing-texture checkerboard on the player.
     */
    @GameTest(template = "empty")
    public void alloyArmorMaterialDeclaresTintedBaseAndOverlayLayers(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        ArmorMaterial alloy = RankineArmorMaterials.ALLOY.get();
        if (alloy.layers().size() != 2) {
            throw new GameTestAssertException("alloy armor material declares " + alloy.layers().size()
                    + " layer(s), expected 2 (tinted base + untinted overlay)");
        }

        ArmorMaterial.Layer base = alloy.layers().get(0);
        ArmorMaterial.Layer overlay = alloy.layers().get(1);
        if (!base.dyeable()) {
            problems.add("alloy base layer is not dyeable, so HumanoidArmorLayer would tint it 0xFFFFFFFF");
        }
        if (overlay.dyeable()) {
            problems.add("alloy overlay layer is dyeable; 1.18.2 drew the overlay pass untinted at (1,1,1)");
        }
        if (!"rankine:textures/models/armor/alloy_layer_1.png".equals(tex(base, false))) {
            problems.add("alloy base outer texture " + tex(base, false));
        }
        if (!"rankine:textures/models/armor/alloy_layer_2.png".equals(tex(base, true))) {
            problems.add("alloy base inner texture " + tex(base, true));
        }
        if (!"rankine:textures/models/armor/alloy_layer_1_overlay.png".equals(tex(overlay, false))) {
            problems.add("alloy overlay outer texture " + tex(overlay, false));
        }
        if (!"rankine:textures/models/armor/alloy_layer_2_overlay.png".equals(tex(overlay, true))) {
            problems.add("alloy overlay inner texture " + tex(overlay, true));
        }

        Map<String, ArmorMaterial> singleLayerMaterials = Map.of(
                "brigandine", RankineArmorMaterials.BRIGANDINE.get(),
                "rubber", RankineArmorMaterials.RUBBER.get(),
                "diving", RankineArmorMaterials.DIVING.get(),
                "conduit_diving", RankineArmorMaterials.CONDUIT_DIVING.get());
        for (Map.Entry<String, ArmorMaterial> entry : singleLayerMaterials.entrySet()) {
            List<ArmorMaterial.Layer> layers = entry.getValue().layers();
            if (layers.size() != 1) {
                problems.add(entry.getKey() + " declares " + layers.size() + " layers, expected 1 (it ships no _overlay texture)");
                continue;
            }
            if (layers.get(0).dyeable()) {
                problems.add(entry.getKey() + " layer is dyeable, expected untinted");
            }
            String expected = "rankine:textures/models/armor/" + entry.getKey() + "_layer_1.png";
            if (!expected.equals(tex(layers.get(0), false))) {
                problems.add(entry.getKey() + " outer texture " + tex(layers.get(0), false) + ", expected " + expected);
            }
        }

        // The layer list only matters for pieces that actually use this material.
        for (Item item : List.of(RankineItems.ALLOY_HELMET.get(), RankineItems.ALLOY_CHESTPLATE.get(),
                RankineItems.ALLOY_LEGGINGS.get(), RankineItems.ALLOY_BOOTS.get())) {
            if (!(item instanceof ArmorItem armorItem)) {
                problems.add(BuiltInRegistries.ITEM.getKey(item) + " is not an ArmorItem");
            } else if (armorItem.getMaterial().value() != alloy) {
                problems.add(BuiltInRegistries.ITEM.getKey(item) + " does not use the alloy armor material");
            }
        }

        assertEmpty(problems, "alloy armor layer problems");
        helper.succeed();
    }

    /**
     * Worn armor and the inventory icon must read one colour through one accessor.
     *
     * <p>The port tinted the icon from {@code RankineDataComponents.COLOR} but left the worn layer
     * on NeoForge's default fallback, {@code DataComponents.DYED_COLOR} behind an
     * {@code ItemTags.DYEABLE} check — a component and a tag the mod never uses — so the worn piece
     * rendered 0xFFFFFFFF while its own icon was bronze. Both now call
     * {@code AlloyColorHelper#getTintARGB}: {@code AlloyItemColor#getColor} for the icon,
     * {@code AlloyArmorClientExtensions#getDefaultDyeColor} for the worn layer (both client-only
     * classes, hence the assertion on the shared accessor).
     *
     * <p>The alpha half is not cosmetic: 1.21 multiplies the quad by the returned ARGB, and every
     * Rankine colour is a plain 0xRRGGBB whose alpha byte is 0, which renders nothing at all.
     */
    @GameTest(template = "empty")
    public void alloyArmorTintReadsTheSameColourAsTheIcon(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        final int color = 0x2E86AB;

        for (Item item : List.of(RankineItems.ALLOY_HELMET.get(), RankineItems.ALLOY_CHESTPLATE.get(),
                RankineItems.ALLOY_LEGGINGS.get(), RankineItems.ALLOY_BOOTS.get())) {
            String name = String.valueOf(BuiltInRegistries.ITEM.getKey(item));

            ItemStack stack = new ItemStack(item);
            stack.set(RankineDataComponents.COLOR.get(), color);

            int tint = AlloyColorHelper.getTintARGB(stack);
            if (tint != (0xFF000000 | color)) {
                problems.add(name + " worn tint " + Integer.toHexString(tint)
                        + ", expected " + Integer.toHexString(0xFF000000 | color));
            }
            if (!(item instanceof AlloyArmorItem armor)) {
                problems.add(name + " is not an AlloyArmorItem");
            } else if (armor.getColor(stack) != color) {
                problems.add(name + " AlloyArmorItem#getColor " + armor.getColor(stack) + ", expected " + color);
            }

            // No colour yet (a stack that has not had an inventory tick): untinted white, never
            // transparent black.
            ItemStack bare = new ItemStack(item);
            int bareTint = AlloyColorHelper.getTintARGB(bare);
            if (bareTint != 0xFFFFFFFF) {
                problems.add(name + " colourless tint " + Integer.toHexString(bareTint) + ", expected ffffffff");
            }
        }

        assertEmpty(problems, "alloy armor tint problems");
        helper.succeed();
    }

    // ----------------------------------------------------------------------------------------
    // Alloy block client sync (decision 8A; batch-3 Engineer D triage (a)/(b))
    // ----------------------------------------------------------------------------------------

    /**
     * A placed alloy block must hand its alloy to the client, or nothing client-side can see it.
     *
     * <p>{@link net.minecraft.world.level.block.entity.BlockEntity} defaults to an EMPTY update tag
     * and a NULL update packet, and 1.18.2's AlloyBlockTile overrode neither. So the client copy of
     * an alloy block carried no alloy at all: {@code AlloyBlockColor} had nothing to tint with, and
     * pick-block — {@code Minecraft#pickBlock}, which calls
     * {@code BlockState#getCloneItemStack} against the CLIENT level — returned a bare block.
     *
     * <p>Covers both wires with the real serialisation: the chunk-load tag
     * ({@code getUpdateTag} → {@code loadWithComponents}, what {@code LevelChunk#replaceWithPacketData}
     * does) and the placement packet ({@code getUpdatePacket}, what
     * {@code ChunkHolder#broadcastBlockEntityIfNeeded} sends), then the pick-block call itself.
     */
    @GameTest(template = "empty")
    public void alloyBlockSyncsItsAlloyToTheClient(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        final String composition = "60Au-40Cu";
        final String alloyRecipe = "rankine:alloying/rose_gold_alloying";
        final String nameOverride = "item.rankine.rose_gold_alloying";
        final int color = 0x2E86AB;

        ItemStack placing = new ItemStack(RankineItems.ALLOY_BLOCK.get());
        placing.set(RankineDataComponents.ALLOY_COMPOSITION.get(), composition);
        placing.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse(alloyRecipe));
        placing.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
        placing.set(RankineDataComponents.COLOR.get(), color);

        BlockPos floor = new BlockPos(1, 1, 1);
        BlockPos target = floor.above();
        helper.setBlock(floor, Blocks.STONE);
        helper.setBlock(target, Blocks.AIR);

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, placing);

        BlockPos absFloor = helper.absolutePos(floor);
        BlockHitResult hit = new BlockHitResult(
                Vec3.atCenterOf(absFloor).add(0.0D, 0.5D, 0.0D), Direction.UP, absFloor, false);
        placing.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hit));

        BlockPos absTarget = helper.absolutePos(target);
        BlockState placed = level.getBlockState(absTarget);
        if (!placed.is(RankineBlocks.ALLOY_BLOCK.get())) {
            throw new GameTestAssertException("alloy block did not place; found " + placed);
        }
        if (!(level.getBlockEntity(absTarget) instanceof AlloyBlockTile tile)) {
            throw new GameTestAssertException("placed alloy block has no AlloyBlockTile");
        }

        // (1) The colour the block color handler asks for.
        if (tile.getAlloyColor() != color) {
            problems.add("tile colour " + tile.getAlloyColor() + ", expected " + color);
        }

        // (2) Chunk-load wire: save -> fresh tile -> load must reproduce the whole alloy.
        CompoundTag updateTag = tile.getUpdateTag(level.registryAccess());
        if (updateTag.isEmpty()) {
            throw new GameTestAssertException("getUpdateTag is empty; the client would receive no alloy data");
        }
        AlloyBlockTile fresh = new AlloyBlockTile(absTarget, placed);
        fresh.loadWithComponents(updateTag, level.registryAccess());
        problems.addAll(alloyMismatches("update tag", fresh, composition, alloyRecipe, nameOverride, color));

        // (3) Placement wire: the same data, through the packet ChunkHolder actually sends.
        Packet<ClientGamePacketListener> packet = tile.getUpdatePacket();
        if (packet == null) {
            throw new GameTestAssertException("getUpdatePacket is null; a freshly placed alloy block syncs nothing");
        }
        if (!(packet instanceof ClientboundBlockEntityDataPacket dataPacket)) {
            throw new GameTestAssertException("getUpdatePacket returned " + packet.getClass().getName());
        }
        AlloyBlockTile fromPacket = new AlloyBlockTile(absTarget, placed);
        fromPacket.loadWithComponents(dataPacket.getTag(), level.registryAccess());
        problems.addAll(alloyMismatches("update packet", fromPacket, composition, alloyRecipe, nameOverride, color));

        // (4) Pick-block. Minecraft#pickBlock runs exactly this against the client level.
        ItemStack picked = placed.getCloneItemStack(hit, level, absTarget, player);
        if (!picked.is(RankineItems.ALLOY_BLOCK.get())) {
            problems.add("pick-block returned " + picked);
        } else {
            if (!composition.equals(IAlloyItem.getAlloyComposition(picked))) {
                problems.add("pick-block composition '" + IAlloyItem.getAlloyComposition(picked) + "'");
            }
            ResourceLocation pickedRecipe = IAlloyItem.getAlloyRecipe(picked);
            if (pickedRecipe == null || !alloyRecipe.equals(pickedRecipe.toString())) {
                problems.add("pick-block alloy recipe " + pickedRecipe);
            }
            if (!nameOverride.equals(IAlloyItem.getNameOverride(picked))) {
                problems.add("pick-block name override '" + IAlloyItem.getNameOverride(picked) + "'");
            }
            if (IAlloyItem.getColorNBT(picked) != color) {
                problems.add("pick-block colour " + IAlloyItem.getColorNBT(picked) + ", expected " + color);
            }
        }

        assertEmpty(problems, "alloy block client-sync failures");
        helper.succeed();
    }


    // ----------------------------------------------------------------------------------------
    // F20b — a crafted alloy block must carry the alloy's colour, not just its composition
    // ----------------------------------------------------------------------------------------

    /**
     * Crafting nine alloy ingots into a {@code rankine:alloy_block} must produce a block that is
     * tinted with the alloy's colour, and melting it back down must not lose it again.
     *
     * <p>F19/F20 made the colour RENDER — the tint slot, the block-entity sync and pick-block all
     * read {@code RankineDataComponents.COLOR} now — but left the survival path with nothing to
     * render: an alloy ingot leaves the alloy furnace carrying its alloying recipe's colour
     * ({@code AlloyingRecipe#assemble}), while {@code AlloyCraftingRecipe#assemble} inherited only
     * the composition and the recipe id. The block therefore came out with the alloy's identity and
     * none of its colour, and the return trip lost it a second time.
     *
     * <p>It could not be fixed in the recipe JSON: alloy_block is the ONE alloy_crafting recipe fed
     * by the generic {@code rankine:alloy_ingot}, so it can be any alloy and no single {@code color}
     * field is right — which is exactly why it ships the white sentinel. The 102 recipes that DO
     * name a colour are each fed by one specific named ingot.
     *
     * <p>The scope of the inheritance is asserted in both directions, because it is a judgement
     * call rather than a mechanical fix: the generic items take the ingredient colour, and a NAMED
     * result — {@code rankine:steel_block}, whose recipe is also {@code inherit} + sentinel — must
     * still come out with no COLOR component at all, since its texture is already steel-coloured
     * and a component there would only split stack identity.
     */
    @GameTest(template = "empty")
    public void craftedAlloyBlockKeepsTheAlloyColour(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        final String composition = "60Au-40Cu";
        final String alloyRecipe = "rankine:alloying/rose_gold_alloying";
        final int color = 0x2E86AB;

        ItemStack sourceIngot = ingot("rankine:alloy_ingot", composition, alloyRecipe);
        IAlloyItem.addColorNBT(sourceIngot, color);

        // 9 ingots -> one alloy block, through the real recipe.
        ItemStack block = craft(level, problems, "rankine:alloy_block", 3, 3, sourceIngot);
        if (block != null) {
            if (!block.is(RankineItems.ALLOY_BLOCK.get())) {
                problems.add("alloy_block recipe produced " + block);
            }
            problems.addAll(craftedAlloyMismatches("crafted alloy block", block, composition, alloyRecipe, color));

            // ...and back out again: block -> 9 ingots must not drop it either.
            ItemStack backToIngots = craft(level, problems, "rankine:alloy_ingot_from_alloy_block", 1, 1, block);
            if (backToIngots != null) {
                problems.addAll(craftedAlloyMismatches("alloy ingots from the block", backToIngots,
                        composition, alloyRecipe, color));
            }
        }

        // Negative control: a NAMED result, from a recipe that is also inherit + white sentinel.
        // steel_block must stay componentless, or the scoping has leaked across 380 other recipes.
        ItemStack steelIngot = ingot("rankine:steel_ingot", "99Fe-1C", STEEL_ALLOYING);
        IAlloyItem.addColorNBT(steelIngot, color);
        ItemStack steelBlock = craft(level, problems, "rankine:steel_block", 3, 3, steelIngot);
        if (steelBlock != null && steelBlock.has(RankineDataComponents.COLOR.get())) {
            problems.add("steel_block inherited a colour component (" + IAlloyItem.getColorNBT(steelBlock)
                    + "); only the generic alloy items should");
        }

        assertEmpty(problems, "crafted alloy colour failures");
        helper.succeed();
    }

    /** Fills a width x height grid with copies of one stack and runs the named recipe over it. */
    private static ItemStack craft(net.minecraft.server.level.ServerLevel level, List<String> problems,
                                   String recipeId, int width, int height, ItemStack each) {
        var holder = level.getRecipeManager().byKey(ResourceLocation.parse(recipeId)).orElse(null);
        if (holder == null || !(holder.value() instanceof AlloyCraftingRecipe recipe)) {
            problems.add(recipeId + ": not a loaded AlloyCraftingRecipe");
            return null;
        }
        List<ItemStack> grid = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            grid.add(each.copy());
        }
        CraftingInput input = CraftingInput.of(width, height, grid);
        if (!recipe.matches(input, level)) {
            problems.add(recipeId + ": the grid did not match the recipe");
            return null;
        }
        ItemStack result = recipe.assemble(input, level.registryAccess());
        if (result.isEmpty()) {
            problems.add(recipeId + ": assembled an empty stack");
            return null;
        }
        return result;
    }

    private static List<String> craftedAlloyMismatches(String what, ItemStack stack, String composition,
                                                       String alloyRecipe, int color) {
        List<String> problems = new ArrayList<>();
        if (!composition.equals(IAlloyItem.getAlloyComposition(stack))) {
            problems.add(what + " composition '" + IAlloyItem.getAlloyComposition(stack) + "'");
        }
        ResourceLocation recipe = IAlloyItem.getAlloyRecipe(stack);
        if (recipe == null || !alloyRecipe.equals(recipe.toString())) {
            problems.add(what + " alloy recipe " + recipe);
        }
        if (IAlloyItem.getColorNBT(stack) != color) {
            problems.add(what + " colour " + IAlloyItem.getColorNBT(stack) + ", expected " + color);
        }
        // The accessor every renderer actually reads, alpha included.
        int tint = AlloyColorHelper.getTintARGB(stack);
        if (tint != (0xFF000000 | color)) {
            problems.add(what + " tint " + Integer.toHexString(tint) + ", expected "
                    + Integer.toHexString(0xFF000000 | color));
        }
        return problems;
    }

    private static List<String> alloyMismatches(String what, AlloyBlockTile tile, String composition,
                                                String alloyRecipe, String nameOverride, int color) {
        List<String> problems = new ArrayList<>();
        var held = tile.collectComponents();
        if (!composition.equals(held.get(RankineDataComponents.ALLOY_COMPOSITION.get()))) {
            problems.add(what + " composition '" + held.get(RankineDataComponents.ALLOY_COMPOSITION.get())
                    + "', expected '" + composition + "'");
        }
        ResourceLocation recipe = held.get(RankineDataComponents.ALLOY_RECIPE.get());
        if (recipe == null || !alloyRecipe.equals(recipe.toString())) {
            problems.add(what + " alloy recipe " + recipe + ", expected " + alloyRecipe);
        }
        if (!nameOverride.equals(held.get(RankineDataComponents.NAME_OVERRIDE.get()))) {
            problems.add(what + " name override '" + held.get(RankineDataComponents.NAME_OVERRIDE.get())
                    + "', expected '" + nameOverride + "'");
        }
        if (tile.getAlloyColor() != color) {
            problems.add(what + " colour " + tile.getAlloyColor() + ", expected " + color);
        }
        return problems;
    }
}
