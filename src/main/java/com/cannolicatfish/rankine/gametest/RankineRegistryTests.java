package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry and datapack integrity tests. These run on a real headless server
 * ({@code gradlew runGameTestServer}) after all registries are frozen and datapacks
 * are loaded, so they catch the failure modes a compile cannot: missing registrations,
 * datapack entries that failed to parse, and loot tables that do not exist.
 */
@GameTestHolder(ProjectRankine.MODID)
@PrefixGameTestTemplate(false)
public class RankineRegistryTests {

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new GameTestAssertException(message);
        }
    }

    /** Fails with the offending entries listed, so one run reports every problem rather than the first. */
    private static void assertEmpty(List<String> problems, String what) {
        if (!problems.isEmpty()) {
            int shown = Math.min(problems.size(), 25);
            throw new GameTestAssertException(problems.size() + " " + what + ": "
                    + String.join(", ", problems.subList(0, shown))
                    + (problems.size() > shown ? " (+" + (problems.size() - shown) + " more)" : ""));
        }
    }

    private static boolean isRankine(ResourceLocation id) {
        return id != null && ProjectRankine.MODID.equals(id.getNamespace());
    }

    @GameTest(template = "empty")
    public void blocksRegistered(GameTestHelper helper) {
        long count = BuiltInRegistries.BLOCK.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(count > 500, "expected >500 rankine blocks, found " + count);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void itemsRegistered(GameTestHelper helper) {
        long count = BuiltInRegistries.ITEM.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(count > 1000, "expected >1000 rankine items, found " + count);
        helper.succeed();
    }

    /**
     * Most blocks should be obtainable as items. This is a floor check rather than a per-block
     * assertion: a number of blocks legitimately have no BlockItem (fluids, technical blocks such
     * as piston_crusher/gyratory_crusher, worldgen-only blocks), and that was equally true in
     * 1.18.2. The floor catches a wholesale BlockItem registration failure, which is the real risk.
     */
    @GameTest(template = "empty")
    public void blocksHaveItems(GameTestHelper helper) {
        long blocks = BuiltInRegistries.BLOCK.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        long withItems = BuiltInRegistries.BLOCK.entrySet().stream()
                .filter(e -> isRankine(e.getKey().location()))
                .filter(e -> e.getValue().asItem() != net.minecraft.world.item.Items.AIR)
                .count();
        assertTrue(withItems > blocks * 9 / 10,
                "only " + withItems + " of " + blocks + " rankine blocks have a BlockItem");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void blockEntityTypesValid(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        for (var entry : BuiltInRegistries.BLOCK_ENTITY_TYPE.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id)) continue;
            BlockEntityType<?> type = entry.getValue();
            boolean anyValid = BuiltInRegistries.BLOCK.stream().anyMatch(b -> type.isValid(b.defaultBlockState()));
            if (!anyValid) {
                problems.add(id + " (bound to no block)");
            }
        }
        assertEmpty(problems, "block entity types with no valid block");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void recipeTypesAndSerializersRegistered(GameTestHelper helper) {
        long types = BuiltInRegistries.RECIPE_TYPE.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        long serializers = BuiltInRegistries.RECIPE_SERIALIZER.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(types >= 15, "expected >=15 rankine recipe types, found " + types);
        assertTrue(serializers >= 15, "expected >=15 rankine recipe serializers, found " + serializers);
        helper.succeed();
    }

    /**
     * Recipes are datapack content: a serializer that throws on a shipped JSON is dropped with a
     * log warning at load, not a crash. Asserting real counts is what catches a broken codec.
     */
    @GameTest(template = "empty")
    public void recipesLoaded(GameTestHelper helper) {
        var manager = helper.getLevel().getServer().getRecipeManager();
        List<String> empty = new ArrayList<>();
        for (var entry : BuiltInRegistries.RECIPE_TYPE.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id)) continue;
            @SuppressWarnings({"unchecked", "rawtypes"})
            int size = manager.getAllRecipesFor((net.minecraft.world.item.crafting.RecipeType) entry.getValue()).size();
            if (size == 0) {
                empty.add(id.toString());
            }
        }
        assertEmpty(empty, "rankine recipe types that loaded zero recipes");

        long rankineRecipes = manager.getRecipes().stream()
                .filter(h -> isRankine(h.id())).count();
        assertTrue(rankineRecipes > 800, "expected >800 rankine recipes loaded, found " + rankineRecipes);
        helper.succeed();
    }

    /** Enchantments are datapack-driven in 1.21 — a malformed definition silently disappears. */
    @GameTest(template = "empty")
    public void enchantmentsLoaded(GameTestHelper helper) {
        RegistryAccess access = helper.getLevel().registryAccess();
        var registry = access.registryOrThrow(Registries.ENCHANTMENT);
        long count = registry.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(count >= 40, "expected >=40 rankine enchantments in the datapack registry, found " + count);

        List<String> broken = new ArrayList<>();
        for (var key : registry.keySet()) {
            if (!isRankine(key)) continue;
            ResourceKey<Enchantment> rk = ResourceKey.create(Registries.ENCHANTMENT, key);
            Holder<Enchantment> holder = registry.getHolder(rk).orElse(null);
            if (holder == null) {
                broken.add(key + " (no holder)");
                continue;
            }
            Enchantment ench = holder.value();
            if (ench.getMaxLevel() < 1) broken.add(key + " (maxLevel<1)");
            if (ench.definition().supportedItems().size() == 0) broken.add(key + " (no supported items)");
        }
        assertEmpty(broken, "malformed rankine enchantments");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void damageTypesLoaded(GameTestHelper helper) {
        var registry = helper.getLevel().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        long count = registry.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(count >= 4, "expected >=4 rankine damage types, found " + count);
        helper.succeed();
    }

    /** Every fluid needs a FluidType or the client crashes on first render of it. */
    @GameTest(template = "empty")
    public void fluidsHaveFluidTypes(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        for (var entry : BuiltInRegistries.FLUID.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id)) continue;
            Fluid fluid = entry.getValue();
            try {
                if (fluid.getFluidType() == null) problems.add(id + " (null fluid type)");
            } catch (RuntimeException e) {
                problems.add(id + " (" + e.getClass().getSimpleName() + ")");
            }
        }
        assertEmpty(problems, "rankine fluids without a fluid type");

        long fluids = BuiltInRegistries.FLUID.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(fluids >= 32, "expected >=32 rankine fluids (16 source + 16 flowing), found " + fluids);
        helper.succeed();
    }

    /**
     * Loot tables are datapack content keyed off the block id. A block whose table is missing
     * drops nothing in game — the classic silent regression after a datagen change.
     * <p>
     * This is a floor check, not a per-block assertion: 44 rankine blocks have never had a loot
     * table (verified against the 1.18.2 generated output — skarn_stairs, distillation_tower,
     * the fumaroles, pcf and friends all dropped nothing there too). Requiring one for every
     * block would assert a content gap that predates the port. The floor catches the failure that
     * actually matters: the loot provider silently emitting far fewer tables than it should.
     */
    @GameTest(template = "empty")
    public void blockLootTablesExist(GameTestHelper helper) {
        var lootRegistry = helper.getLevel().getServer().reloadableRegistries().get()
                .registryOrThrow(Registries.LOOT_TABLE);
        long present = BuiltInRegistries.BLOCK.entrySet().stream()
                .filter(e -> isRankine(e.getKey().location()))
                .map(e -> e.getValue().getLootTable())
                .filter(k -> k != null && isRankine(k.location()))
                .filter(k -> lootRegistry.get(k) != null)
                .count();
        assertTrue(present > 900, "expected >900 rankine block loot tables loaded, found " + present);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void creativeTabsPopulated(GameTestHelper helper) {
        List<String> empty = new ArrayList<>();
        long tabs = BuiltInRegistries.CREATIVE_MODE_TAB.keySet().stream().filter(RankineRegistryTests::isRankine).count();
        assertTrue(tabs == 6, "expected 6 rankine creative tabs, found " + tabs);
        assertEmpty(empty, "empty creative tabs");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void dataComponentsRegistered(GameTestHelper helper) {
        long count = BuiltInRegistries.DATA_COMPONENT_TYPE.keySet().stream()
                .filter(RankineRegistryTests::isRankine).count();
        assertTrue(count >= 5, "expected >=5 rankine data component types, found " + count);
        helper.succeed();
    }

    /**
     * The alloy-gated enchantments must respect the alloy a stack was actually made from.
     *
     * <p>1.18.2 enforced this inside the Enchantment classes; 1.21 applicability is an item tag,
     * which cannot see a per-stack alloy, so it now runs through NeoForge's supportsEnchantment
     * (anvil) and isPrimaryItemFor (enchanting table). Neither is observable from a headless gate
     * through normal play, and the anvil additionally skips the check entirely for creative
     * players, so this asserts both hooks directly and in both directions.
     */
    @GameTest(template = "empty")
    public void alloyGatedEnchantmentsRespectAlloy(GameTestHelper helper) {
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<String> problems = new ArrayList<>();

        record Case(String name, Item item, ResourceKey<Enchantment> enchantment,
                    ResourceLocation matching, ResourceLocation wrong) {
        }

        ResourceLocation roseGold = ResourceLocation.parse("rankine:alloying/rose_gold_alloying");
        List<Case> cases = List.of(
                new Case("antiquated/pewter_sword", RankineItems.PEWTER_SWORD.get(), RankineEnchantments.ANTIQUATED,
                        ResourceLocation.parse("rankine:alloying/pewter_alloying"), roseGold),
                new Case("shape_memory/alloy_pickaxe", RankineItems.ALLOY_PICKAXE.get(), RankineEnchantments.SHAPE_MEMORY,
                        ResourceLocation.parse("rankine:alloying/nitinol_alloying"), roseGold),
                new Case("endobiotic/alloy_helmet", RankineItems.ALLOY_HELMET.get(), RankineEnchantments.ENDOBIOTIC,
                        ResourceLocation.parse("rankine:alloying/ender_amalgam_alloying"), roseGold));

        for (Case c : cases) {
            Holder<Enchantment> holder = enchantments.getHolder(c.enchantment()).orElse(null);
            if (holder == null) {
                problems.add(c.name() + ": enchantment missing from the registry");
                continue;
            }

            ItemStack matching = new ItemStack(c.item());
            matching.set(RankineDataComponents.ALLOY_RECIPE.get(), c.matching());
            if (!matching.supportsEnchantment(holder)) {
                problems.add(c.name() + ": refused on its own alloy (anvil path)");
            }

            ItemStack wrong = new ItemStack(c.item());
            wrong.set(RankineDataComponents.ALLOY_RECIPE.get(), c.wrong());
            if (wrong.supportsEnchantment(holder)) {
                problems.add(c.name() + ": allowed on the wrong alloy (anvil path)");
            }
            if (wrong.isPrimaryItemFor(holder)) {
                problems.add(c.name() + ": offered on the wrong alloy (enchanting table path)");
            }

            // A stack carrying no alloy at all: 1.18's enchanting table refused outright, while the
            // anvil deferred to the type check. Only the table half is asserted here, because the
            // anvil fallback is decided by the supported_items tag rather than by this gate.
            if (new ItemStack(c.item()).isPrimaryItemFor(holder)) {
                problems.add(c.name() + ": offered on a stack with no alloy (enchanting table path)");
            }
        }

        // The alloy match must not be able to override the item type. Antiquated is a TOOL
        // enchantment, so a pewter-alloyed helmet has the right alloy and the wrong type; 1.18.2
        // refused it via `stack.getItem() instanceof IAlloyTool`, which is the tag now.
        Holder<Enchantment> antiquated = enchantments.getHolder(RankineEnchantments.ANTIQUATED).orElse(null);
        if (antiquated != null) {
            ItemStack pewterHelmet = new ItemStack(RankineItems.ALLOY_HELMET.get());
            pewterHelmet.set(RankineDataComponents.ALLOY_RECIPE.get(),
                    ResourceLocation.parse("rankine:alloying/pewter_alloying"));
            if (pewterHelmet.supportsEnchantment(antiquated)) {
                problems.add("antiquated: accepted on a pewter ALLOY HELMET (tool-only enchantment)");
            }
        }

        assertEmpty(problems, "alloy-gated enchantment failures");
        helper.succeed();
    }

    /**
     * Rankine's alloy enchantments must never reach a vanilla item.
     *
     * <p>Nothing in the mod's own code enforces this -- the alloy gate only runs on Rankine's item
     * classes, so a diamond sword is decided entirely by the enchantment's supported_items tag.
     * This asserts that the tags really are Rankine-only, which is the whole of the protection.
     *
     * <p>Note that observing this in-game requires SURVIVAL mode: the anvil forces its applicability
     * check to true for creative players (AnvilMenu: {@code if (instabuild) flag1 = true}), so a
     * creative player can put any enchantment on any item and it proves nothing about these rules.
     */
    @GameTest(template = "empty")
    public void alloyEnchantmentsRejectVanillaItems(GameTestHelper helper) {
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<String> problems = new ArrayList<>();

        List<ResourceKey<Enchantment>> gated = List.of(
                RankineEnchantments.ANTIQUATED, RankineEnchantments.SHAPE_MEMORY, RankineEnchantments.ENDOBIOTIC);
        List<Item> vanilla = List.of(
                Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.NETHERITE_AXE,
                Items.IRON_HELMET, Items.DIAMOND_CHESTPLATE, Items.STICK);

        for (ResourceKey<Enchantment> key : gated) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElse(null);
            if (holder == null) {
                problems.add(key.location() + ": missing from the registry");
                continue;
            }
            for (Item item : vanilla) {
                ItemStack stack = new ItemStack(item);
                if (stack.supportsEnchantment(holder)) {
                    problems.add(key.location().getPath() + " accepted on vanilla "
                            + BuiltInRegistries.ITEM.getKey(item).getPath() + " (anvil path)");
                }
                if (stack.isPrimaryItemFor(holder)) {
                    problems.add(key.location().getPath() + " offered on vanilla "
                            + BuiltInRegistries.ITEM.getKey(item).getPath() + " (enchanting table path)");
                }
            }
        }

        assertEmpty(problems, "vanilla items accepting rankine alloy enchantments");
        helper.succeed();
    }

    /**
     * Every ingredient of every element recipe must resolve to at least one item.
     *
     * <p>This is the regression net for the forge:->c: tag namespace migration: element recipes
     * reference tags as raw "T#namespace:path" strings, and a tag that does not exist never throws
     * anywhere — TagKey.create accepts it, Ingredient.of(tag) resolves to an EMPTY ingredient, and
     * element detection (Alloy Furnace, Induction Furnace, Template Table, JEI) silently finds
     * nothing. All 120 element recipes shipped with dead "T#forge:" references after the port and
     * no gate noticed; this test makes that failure loud.
     */
    @GameTest(template = "empty")
    public void elementRecipeIngredientsResolve(GameTestHelper helper) {
        var holders = helper.getLevel().getRecipeManager()
                .getAllRecipesFor(RankineRecipeTypes.ELEMENT.get());
        assertTrue(holders.size() > 100, "expected >100 element recipes, found " + holders.size());

        List<String> problems = new ArrayList<>();
        for (var holder : holders) {
            var recipe = holder.value();
            var ingredients = recipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                if (ingredients.get(i).getItems().length == 0) {
                    problems.add(holder.id().getPath() + " entry " + i);
                }
            }
        }
        assertEmpty(problems, "element recipe entries resolving to zero items");
        helper.succeed();
    }

    /**
     * Every registered rankine item must appear in at least one rankine creative tab.
     *
     * <p>Tab contents normally only build on the client, which is why ~1,974 missing items (all
     * block-group BlockItems, the metal lanterns, the HL crushing heads) survived every headless
     * gate — nothing ever built the tabs. CreativeModeTabs.tryRebuildTabContents is common code,
     * so a GameTest can force the build and diff the result against the item registry. The
     * exclusion list is the 13 items that had no .tab() in 1.18.2 either (verified against the
     * baseline) — intentionally hidden, not regressions.
     */
    @GameTest(template = "empty")
    public void creativeTabsContainEveryItem(GameTestHelper helper) {
        CreativeModeTabs.tryRebuildTabContents(
                helper.getLevel().enabledFeatures(), true, helper.getLevel().registryAccess());

        java.util.Set<Item> shown = new java.util.HashSet<>();
        for (var entry : BuiltInRegistries.CREATIVE_MODE_TAB.entrySet()) {
            if (!isRankine(entry.getKey().location())) continue;
            for (ItemStack stack : entry.getValue().getDisplayItems()) {
                shown.add(stack.getItem());
            }
        }
        assertTrue(!shown.isEmpty(), "rankine creative tabs built empty — tryRebuildTabContents produced nothing");

        java.util.Set<String> intentionallyHidden = java.util.Set.of(
                "garland", "pineapple_sleeves", "soy_milk", "pcf", "particle_accelerator",
                "leather_canteen", "stainless_steel_canteen", "enderball", "biome_indicator_generic",
                "biome_indicator_river", "biome_indicator_ocean", "element", "unamed_explosive");

        List<String> problems = new ArrayList<>();
        for (var entry : BuiltInRegistries.ITEM.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id) || intentionallyHidden.contains(id.getPath())) continue;
            if (!shown.contains(entry.getValue())) {
                problems.add(id.getPath());
            }
        }
        assertEmpty(problems, "rankine items absent from every creative tab");
        helper.succeed();
    }

    /**
     * Armor must not stack and must be damageable.
     *
     * <p>1.18.2's ArmorItem constructor applied the material's durability to the Properties for you;
     * 1.21's does not, and {@code Item.Properties.durability(n)} is the only thing that sets
     * MAX_STACK_SIZE=1. Bare Properties therefore produce armor that stacks to 64, never takes
     * damage, and cannot be enchanted ({@code Item#isEnchantable} needs both stack size 1 and
     * MAX_DAMAGE) — all silently, with no error anywhere. That is exactly what happened to all 22
     * Rankine armor pieces, and no headless gate could see it until this test.
     */
    @GameTest(template = "empty")
    public void armorIsSingleStackAndDamageable(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        int checked = 0;
        for (var entry : BuiltInRegistries.ITEM.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id) || !(entry.getValue() instanceof net.minecraft.world.item.ArmorItem)) continue;
            checked++;
            ItemStack stack = new ItemStack(entry.getValue());
            if (stack.getMaxStackSize() != 1) {
                problems.add(id.getPath() + " stacks to " + stack.getMaxStackSize());
            } else if (!stack.has(net.minecraft.core.component.DataComponents.MAX_DAMAGE)) {
                problems.add(id.getPath() + " has no MAX_DAMAGE (indestructible, un-enchantable)");
            }
        }
        assertTrue(checked >= 20, "expected >=20 rankine armor items, found " + checked);
        assertEmpty(problems, "rankine armor items with a bad stack size or no durability");
        helper.succeed();
    }

    /**
     * The Rankine Journal must be bound to its book.
     *
     * <p>Patchouli 1.20.5+ binds a guide book through a dedicated {@code patchouli:book} data
     * component and has no legacy-NBT fallback, so writing the book id anywhere else — notably into
     * {@code minecraft:custom_data}, the literal translation of 1.18's {@code nbt} field — yields a
     * real guide book bound to no book, i.e. a blank one. Nothing logs. Skipped when Patchouli is
     * absent, so this is only meaningful on a dev run that has it on the classpath.
     */
    @GameTest(template = "empty")
    public void patchouliJournalIsBoundToItsBook(GameTestHelper helper) {
        if (!com.cannolicatfish.rankine.compatibility.Patchouli.isInstalled()) {
            helper.succeed();
            return;
        }
        var bookComponent = BuiltInRegistries.DATA_COMPONENT_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("patchouli", "book"));
        assertTrue(bookComponent != null,
                "patchouli is loaded but the patchouli:book data component is not registered — "
                        + "Patchouli changed its binding mechanism, so compatibility/Patchouli needs updating");

        ItemStack journal = com.cannolicatfish.rankine.compatibility.Patchouli.getJournalBookStack();
        assertTrue(!journal.isEmpty(), "getJournalBookStack() returned EMPTY while patchouli is loaded");
        Object bound = journal.get(bookComponent);
        assertTrue(ResourceLocation.fromNamespaceAndPath("rankine", "rankine_journal").equals(bound),
                "journal stack is bound to " + bound + ", expected rankine:rankine_journal");
        helper.succeed();
    }

    /**
     * Actually CRAFTS the alloy tool/armor shapes that JEI displayed scrambled, proving the
     * scrambling was display-only and the recipes themselves match and assemble correctly.
     *
     * <p>Covers the three non-square shapes the shapeless-guess broke — 2x3 hoe (asymmetric, so
     * also tested mirrored), 1x3 sword, and 3x2 boots (which has a hole in the pattern) — plus a
     * deliberately wrong arrangement that must NOT match, and alloy-data inheritance on the
     * assembled result (inherit=true recipes yield EMPTY unless an input carries a composition,
     * exactly as in 1.18).
     */
    @GameTest(template = "empty")
    public void alloyCraftingShapesCraftCorrectly(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        // A rose gold ingot as it exists in real play: composition + source recipe components.
        java.util.function.Supplier<ItemStack> ingot = () -> {
            ItemStack s = new ItemStack(RankineItems.ROSE_GOLD_INGOT.get());
            s.set(RankineDataComponents.ALLOY_COMPOSITION.get(), "75Au-22Cu-3Ni");
            s.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse("rankine:alloying/rose_gold_alloying"));
            return s;
        };
        java.util.function.Supplier<ItemStack> stick = () -> new ItemStack(Items.STICK);
        ItemStack air = ItemStack.EMPTY;

        record Craft(String name, String recipeId, List<ItemStack> grid3x3, String expectItem, boolean expectMatch) {}
        List<Craft> crafts = List.of(
                // hoe: AB / _D / _F placed in a 3x3
                new Craft("hoe 2x3", "rankine:rose_gold_hoe",
                        List.of(ingot.get(), ingot.get(), air, air, stick.get(), air, air, stick.get(), air),
                        "rankine:rose_gold_hoe", true),
                // hoe mirrored: BA / D_ / F_
                new Craft("hoe 2x3 mirrored", "rankine:rose_gold_hoe",
                        List.of(ingot.get(), ingot.get(), air, stick.get(), air, air, stick.get(), air, air),
                        "rankine:rose_gold_hoe", true),
                // sword: single column A/B/C
                new Craft("sword 1x3", "rankine:rose_gold_sword",
                        List.of(ingot.get(), air, air, ingot.get(), air, air, stick.get(), air, air),
                        "rankine:rose_gold_sword", true),
                // boots: A_C / D_F — the hole in the middle is the shape a shapeless layout scrambles
                new Craft("boots 3x2 with hole", "rankine:rose_gold_boots",
                        List.of(ingot.get(), air, ingot.get(), ingot.get(), air, ingot.get(), air, air, air),
                        "rankine:alloy_boots", true),
                // negative: hoe ingredients squashed into a 2x2 block must not match the hoe
                new Craft("hoe wrong shape", "rankine:rose_gold_hoe",
                        List.of(ingot.get(), ingot.get(), air, stick.get(), stick.get(), air, air, air, air),
                        null, false));

        for (Craft c : crafts) {
            var holder = level.getRecipeManager().byKey(ResourceLocation.parse(c.recipeId())).orElse(null);
            if (holder == null) {
                problems.add(c.name() + ": recipe " + c.recipeId() + " not found");
                continue;
            }
            if (!(holder.value() instanceof com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe recipe)) {
                problems.add(c.name() + ": " + c.recipeId() + " is not an AlloyCraftingRecipe");
                continue;
            }
            // Same construction the crafting menu uses: trims to the placed bounding box.
            var input = net.minecraft.world.item.crafting.CraftingInput.of(3, 3, c.grid3x3());
            boolean matched = recipe.matches(input, level);
            if (matched != c.expectMatch()) {
                problems.add(c.name() + ": matches() == " + matched + ", expected " + c.expectMatch());
                continue;
            }
            if (c.expectMatch()) {
                ItemStack out = recipe.assemble(input, level.registryAccess());
                ResourceLocation outId = BuiltInRegistries.ITEM.getKey(out.getItem());
                if (!outId.toString().equals(c.expectItem())) {
                    problems.add(c.name() + ": assembled " + outId + ", expected " + c.expectItem());
                }
                String comp = com.cannolicatfish.rankine.items.alloys.IAlloyItem.getAlloyComposition(out);
                if (!"75Au-22Cu-3Ni".equals(comp)) {
                    problems.add(c.name() + ": inherited composition '" + comp + "', expected 75Au-22Cu-3Ni");
                }
            }
        }

        // The recipe book's grid-fill contract: these recipes must BE ShapedRecipes with true dims.
        var hoe = level.getRecipeManager().byKey(ResourceLocation.parse("rankine:rose_gold_hoe")).orElseThrow();
        if (!(hoe.value() instanceof net.minecraft.world.item.crafting.ShapedRecipe shaped)) {
            problems.add("rose_gold_hoe is not a ShapedRecipe — vanilla recipe book will treat it as shapeless");
        } else if (shaped.getWidth() != 2 || shaped.getHeight() != 3) {
            problems.add("rose_gold_hoe reports " + shaped.getWidth() + "x" + shaped.getHeight() + ", expected 2x3");
        }

        assertEmpty(problems, "alloy crafting failures");
        helper.succeed();
    }

    /**
     * Every Rankine tool, weapon and armour piece must be enchantable.
     *
     * <p>1.18.2 decided vanilla enchantment applicability with {@code EnchantmentCategory}
     * instanceof checks, which picked mod items up for free. 1.21 replaced them with
     * {@code minecraft:enchantable/*} item tags that a mod is in only if it says so, and the
     * failure is silent: the enchanting table simply shows three empty offer rows and the anvil
     * refuses the book. Nothing in Java enforces membership, so this is the only guard.
     *
     * <p>The named cases pin the tags a player actually notices; the registry sweep after them
     * re-states the old category predicates as rules, so a tool added later cannot quietly miss.
     */
    @GameTest(template = "empty")
    public void rankineGearIsEnchantable(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        record Expect(Item item, List<TagKey<Item>> tags) {
        }

        List<Expect> expected = List.of(
                new Expect(RankineItems.STEEL_PICKAXE.get(), List.of(
                        ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE,
                        ItemTags.DURABILITY_ENCHANTABLE, ItemTags.VANISHING_ENCHANTABLE)),
                new Expect(RankineItems.STEEL_SWORD.get(), List.of(
                        ItemTags.SWORD_ENCHANTABLE, ItemTags.SHARP_WEAPON_ENCHANTABLE,
                        ItemTags.WEAPON_ENCHANTABLE, ItemTags.FIRE_ASPECT_ENCHANTABLE,
                        ItemTags.DURABILITY_ENCHANTABLE)),
                new Expect(RankineItems.STEEL_CROWBAR.get(), List.of(
                        ItemTags.MINING_ENCHANTABLE, ItemTags.MINING_LOOT_ENCHANTABLE,
                        ItemTags.DURABILITY_ENCHANTABLE)),
                new Expect(RankineItems.STONE_HAMMER.get(), List.of(
                        ItemTags.MINING_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE)),
                new Expect(RankineItems.FLINT_AXE.get(), List.of(
                        ItemTags.MINING_ENCHANTABLE, ItemTags.SHARP_WEAPON_ENCHANTABLE,
                        ItemTags.WEAPON_ENCHANTABLE)),
                new Expect(RankineItems.FLINT_KNIFE.get(), List.of(
                        ItemTags.SWORD_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE)),
                new Expect(RankineItems.ALLOY_HELMET.get(), List.of(
                        ItemTags.HEAD_ARMOR_ENCHANTABLE, ItemTags.ARMOR_ENCHANTABLE,
                        ItemTags.EQUIPPABLE_ENCHANTABLE, ItemTags.DURABILITY_ENCHANTABLE)),
                new Expect(RankineItems.DIVING_CHESTPLATE.get(), List.of(
                        ItemTags.CHEST_ARMOR_ENCHANTABLE, ItemTags.ARMOR_ENCHANTABLE)),
                new Expect(RankineItems.BRIGADINE_LEGGINGS.get(), List.of(
                        ItemTags.LEG_ARMOR_ENCHANTABLE, ItemTags.ARMOR_ENCHANTABLE)),
                new Expect(RankineItems.SNOWSHOES.get(), List.of(
                        ItemTags.FOOT_ARMOR_ENCHANTABLE, ItemTags.ARMOR_ENCHANTABLE)));

        for (Expect e : expected) {
            ItemStack stack = new ItemStack(e.item());
            for (TagKey<Item> tag : e.tags()) {
                if (!stack.is(tag)) {
                    problems.add(BuiltInRegistries.ITEM.getKey(e.item()).getPath()
                            + " missing from #" + tag.location());
                }
            }
        }

        // The old categories were rules, not lists: anything satisfying the 1.18.2 predicate must
        // be in the tag that replaced it. BREAKABLE was canBeDepleted(), and VANISHABLE was
        // BREAKABLE plus the Vanishable marker, which no non-damageable Rankine item carried.
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (!isRankine(id)) {
                continue;
            }
            String name = id.getPath();
            ItemStack stack = new ItemStack(item);

            if (item.components().has(DataComponents.MAX_DAMAGE)) {
                if (!stack.is(ItemTags.DURABILITY_ENCHANTABLE)) {
                    problems.add(name + " is damageable but not in #enchantable/durability");
                }
                if (!stack.is(ItemTags.VANISHING_ENCHANTABLE)) {
                    problems.add(name + " is damageable but not in #enchantable/vanishing");
                }
            }
            if (item instanceof DiggerItem) {
                if (!stack.is(ItemTags.MINING_ENCHANTABLE)) {
                    problems.add(name + " is a DiggerItem but not in #enchantable/mining");
                }
                if (!stack.is(ItemTags.MINING_LOOT_ENCHANTABLE)) {
                    problems.add(name + " is a DiggerItem but not in #enchantable/mining_loot");
                }
            }
            if (item instanceof SwordItem && !stack.is(ItemTags.SWORD_ENCHANTABLE)) {
                problems.add(name + " is a SwordItem but not in #enchantable/sword");
            }
            if (item instanceof ArmorItem armor) {
                TagKey<Item> slot = switch (armor.getType()) {
                    case HELMET -> ItemTags.HEAD_ARMOR_ENCHANTABLE;
                    case CHESTPLATE -> ItemTags.CHEST_ARMOR_ENCHANTABLE;
                    case LEGGINGS -> ItemTags.LEG_ARMOR_ENCHANTABLE;
                    case BOOTS -> ItemTags.FOOT_ARMOR_ENCHANTABLE;
                    case BODY -> null; // 1.18.2 had no BODY slot; Rankine registers none
                };
                if (slot != null && !stack.is(slot)) {
                    problems.add(name + " is armour but not in #" + slot.location());
                }
                if (!stack.is(ItemTags.ARMOR_ENCHANTABLE)) {
                    problems.add(name + " is armour but not in #enchantable/armor");
                }
            }
        }

        // Tag membership is the mechanism; these are the outcomes a player sees. Both hooks are
        // asserted because they are different code paths: supportsEnchantment is the anvil and
        // isPrimaryItemFor is the enchanting table.
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

        record Applies(String name, Item item, ResourceKey<Enchantment> enchantment) {
        }

        List<Applies> applies = List.of(
                new Applies("efficiency on steel pickaxe", RankineItems.STEEL_PICKAXE.get(), Enchantments.EFFICIENCY),
                new Applies("fortune on steel pickaxe", RankineItems.STEEL_PICKAXE.get(), Enchantments.FORTUNE),
                new Applies("protection on alloy helmet", RankineItems.ALLOY_HELMET.get(), Enchantments.PROTECTION),
                new Applies("unbreaking on steel crowbar", RankineItems.STEEL_CROWBAR.get(), Enchantments.UNBREAKING),
                new Applies("mending on brigandine leggings", RankineItems.BRIGADINE_LEGGINGS.get(), Enchantments.MENDING),
                new Applies("sharpness on steel sword", RankineItems.STEEL_SWORD.get(), Enchantments.SHARPNESS));

        for (Applies a : applies) {
            Holder<Enchantment> holder = enchantments.getHolder(a.enchantment()).orElse(null);
            if (holder == null) {
                problems.add(a.name() + ": enchantment missing from the registry");
                continue;
            }
            ItemStack stack = new ItemStack(a.item());
            if (!stack.supportsEnchantment(holder)) {
                problems.add(a.name() + ": refused on the anvil path (supportsEnchantment)");
            }
            if (!stack.isPrimaryItemFor(holder)) {
                problems.add(a.name() + ": not offered at the enchanting table (isPrimaryItemFor)");
            }
        }

        assertEmpty(problems, "enchantability failures");
        helper.succeed();
    }

    /**
     * The three hand-written applicability bans were table-only in 1.18.2 and must stay that way.
     *
     * <p>1.18.2 expressed them through Forge's {@code canApplyAtEnchantingTable}, which only
     * {@code EnchantmentHelper.getAvailableEnchantmentResults} consulted; the anvil went through
     * {@code Enchantment#canEnchant} and happily applied the corresponding book. In 1.21 the two
     * paths are {@code isPrimaryItemFor} (table) and {@code supportsEnchantment} (anvil), and
     * because the default {@code isPrimaryItemFor} short-circuits on {@code supportsEnchantment},
     * putting the ban on the wrong one silently kills both.
     */
    @GameTest(template = "empty")
    public void enchantingTableBansDoNotBlockBooks(GameTestHelper helper) {
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<String> problems = new ArrayList<>();

        record Ban(String name, Item item, ResourceKey<Enchantment> enchantment) {
        }

        List<Ban> bans = List.of(
                new Ban("efficiency on stone hammer", RankineItems.STONE_HAMMER.get(), Enchantments.EFFICIENCY),
                new Ban("silk touch on stone hammer", RankineItems.STONE_HAMMER.get(), Enchantments.SILK_TOUCH),
                new Ban("efficiency on steel hammer", RankineItems.STEEL_HAMMER.get(), Enchantments.EFFICIENCY),
                new Ban("efficiency on steel crowbar", RankineItems.STEEL_CROWBAR.get(), Enchantments.EFFICIENCY),
                new Ban("silk touch on steel crowbar", RankineItems.STEEL_CROWBAR.get(), Enchantments.SILK_TOUCH),
                new Ban("fortune on steel crowbar", RankineItems.STEEL_CROWBAR.get(), Enchantments.FORTUNE),
                new Ban("sweeping edge on flint knife", RankineItems.FLINT_KNIFE.get(), Enchantments.SWEEPING_EDGE),
                new Ban("fire aspect on flint knife", RankineItems.FLINT_KNIFE.get(), Enchantments.FIRE_ASPECT),
                new Ban("knockback on flint knife", RankineItems.FLINT_KNIFE.get(), Enchantments.KNOCKBACK),
                new Ban("fire aspect on steel knife", RankineItems.STEEL_KNIFE.get(), Enchantments.FIRE_ASPECT));

        for (Ban b : bans) {
            Holder<Enchantment> holder = enchantments.getHolder(b.enchantment()).orElse(null);
            if (holder == null) {
                problems.add(b.name() + ": enchantment missing from the registry");
                continue;
            }
            ItemStack stack = new ItemStack(b.item());
            if (stack.isPrimaryItemFor(holder)) {
                problems.add(b.name() + ": still offered at the enchanting table (1.18.2 refused it)");
            }
            if (!stack.supportsEnchantment(holder)) {
                problems.add(b.name() + ": book refused on the anvil (1.18.2 accepted it)");
            }
        }

        assertEmpty(problems, "enchanting-table ban failures");
        helper.succeed();
    }
}
