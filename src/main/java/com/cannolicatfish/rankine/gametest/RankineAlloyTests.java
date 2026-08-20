package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.PackagedArmorItem;
import com.cannolicatfish.rankine.items.PackagedToolItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
}
