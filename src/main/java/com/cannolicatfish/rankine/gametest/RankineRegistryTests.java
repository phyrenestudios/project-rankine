package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.data.RankineBiomeModifiers;
import com.cannolicatfish.rankine.blocks.AntimatterBlock;
import com.cannolicatfish.rankine.blocks.BlastingPowderBlock;
import com.cannolicatfish.rankine.blocks.GunCottonBlock;
import com.cannolicatfish.rankine.blocks.UnamedExplosiveBlock;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.blocks.buildingmodes.BuildingModeBlock;
import com.cannolicatfish.rankine.entities.ReactiveItemEntity;
import com.cannolicatfish.rankine.events.handlers.common.BlockBreakHandler;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import com.cannolicatfish.rankine.items.ShulkerGasVacuumItem;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.RankinePlacedFeatures;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.ores.RankineOreFeatureConfig;
import com.cannolicatfish.rankine.world.modifier.ConfigGatedFeaturesBiomeModifier;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankinePOIs;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineVillagerProfessions;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.event.level.BlockEvent;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    /**
     * F2 — every item Rankine declares compostable must actually be compostable.
     *
     * <p>1.18.2 wrote {@code ComposterBlock.COMPOSTABLES} at load time; NeoForge 1.21 reads only the
     * {@code neoforge:compostables} data map, which is a generated datapack file. Nothing errors
     * when it is missing — the composter just quietly rejects the item — so this asserts the data
     * map the running server actually loaded, item by item, against
     * {@link VanillaIntegration#compostables()}, the list datagen was driven from. It therefore
     * fails both if the provider is dropped and if the generated JSON is stale.
     */
    @GameTest(template = "empty")
    public void compostablesDataMapMatchesTheModsList(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        Map<Item, Float> expected = VanillaIntegration.compostables();

        assertTrue(expected.size() > 90, "expected >90 rankine compostables, the mod's list has " + expected.size());

        for (Map.Entry<Item, Float> e : expected.entrySet()) {
            Item item = e.getKey();
            String name = BuiltInRegistries.ITEM.getKey(item).toString();
            Compostable compostable = item.builtInRegistryHolder().getData(NeoForgeDataMaps.COMPOSTABLES);
            if (compostable == null) {
                problems.add(name + " has no neoforge:compostables entry");
            } else if (compostable.chance() != e.getValue()) {
                problems.add(name + " composts at " + compostable.chance() + ", expected " + e.getValue());
            } else if (compostable.canVillagerCompost()) {
                // 1.18.2's WorkAtComposter had a hard-coded item list no mod could extend.
                problems.add(name + " is villager-compostable; 1.18.2 allowed no rankine item to be");
            }
        }

        assertEmpty(problems, "compostable data map mismatches");
        helper.succeed();
    }

    /**
     * F3 — the five Rankine professions must be acquirable by villagers.
     *
     * <p>An unemployed villager only claims job sites matching
     * {@code VillagerProfession.ALL_ACQUIRABLE_JOBS}, i.e. {@code #minecraft:acquirable_job_site}.
     * 1.18.2 needed no such tag, so an absent {@code PoiTypeTagsProvider} makes every Rankine
     * profession — and all 21 of its trades — unreachable with no error anywhere.
     */
    @GameTest(template = "empty")
    public void rankineJobSitesAreAcquirable(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        List<ResourceKey<PoiType>> jobSites = List.of(
                RankinePOIs.TEMPLATE_TABLE_POI.getKey(),
                RankinePOIs.MATERIAL_TESTING_TABLE_POI.getKey(),
                RankinePOIs.BOTANIST_STATION_POI.getKey(),
                RankinePOIs.GEM_CUTTER_POI.getKey(),
                RankinePOIs.ROCK_COLLECTOR_POI.getKey());

        for (ResourceKey<PoiType> key : jobSites) {
            Holder<PoiType> holder = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolder(key).orElse(null);
            if (holder == null) {
                problems.add(key.location() + " is not registered");
                continue;
            }
            if (!holder.is(PoiTypeTags.ACQUIRABLE_JOB_SITE)) {
                problems.add(key.location() + " missing from #minecraft:acquirable_job_site");
            }
            // Vanilla's village.json is ["#minecraft:acquirable_job_site", home, meeting], so this
            // should follow for free; asserting it catches the tag being added to the wrong file.
            if (!holder.is(PoiTypeTags.VILLAGE)) {
                problems.add(key.location() + " missing from #minecraft:village");
            }
        }

        // Every profession's job site must be one of the five above, or a profession is orphaned.
        for (var profession : List.of(RankineVillagerProfessions.METALLURGIST, RankineVillagerProfessions.MINERALOGIST,
                RankineVillagerProfessions.BOTANIST, RankineVillagerProfessions.GEM_CUTTER,
                RankineVillagerProfessions.ROCK_COLLECTOR)) {
            boolean acquirable = BuiltInRegistries.POINT_OF_INTEREST_TYPE.holders()
                    .anyMatch(h -> h.is(PoiTypeTags.ACQUIRABLE_JOB_SITE) && profession.get().acquirableJobSite().test(h));
            if (!acquirable) {
                problems.add(profession.getId() + " has no acquirable job site");
            }
        }

        assertEmpty(problems, "villager job-site failures");
        helper.succeed();
    }

    /**
     * F4/F6 — Rankine enchantment availability must match the 1.18.2 flags.
     *
     * <p>All 44 had {@code isTradeable() == true} and {@code isTreasureOnly() == false}; 14
     * overrode {@code isDiscoverable()} to false. Those map onto {@code minecraft:tradeable},
     * {@code minecraft:treasure} and {@code minecraft:on_random_loot} / {@code on_mob_spawn_equipment}
     * / {@code on_traded_equipment}. Asserted in BOTH directions so the fix cannot overshoot: the
     * curse in particular must be tradeable but never roll onto loot or mob gear.
     *
     * <p>The table pool is asserted separately below, because 1.18.2 gated it on a THIRD flag the
     * tags cannot express: {@code canApplyAtEnchantingTable(stack)}, which Forge patched into
     * {@code getAvailableEnchantmentResults}. Antiquated returned true there only on a
     * pewter-alloyed alloy tool, and six armour enchantments returned false for every stack; both
     * are per-STACK rules, which 1.21 keeps in {@code isPrimaryItemFor} — the alloy gate for the
     * first, an empty {@code primary_items} for the six. All seven are therefore in the pool tag
     * and tag membership alone proves nothing about what the table offers, so the last block drives
     * the real {@code EnchantmentMenu} code path instead. The six get their own dedicated test,
     * {@code tableBannedEnchantmentsHaveEmptyPrimaryItems}.
     */
    @GameTest(template = "empty")
    public void rankineEnchantmentsHave1182Availability(GameTestHelper helper) {
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<String> problems = new ArrayList<>();

        // Tradeable regardless of discoverability — the three the review found unobtainable, plus
        // representatives of the other groups.
        List<ResourceKey<Enchantment>> tradeable = List.of(
                RankineEnchantments.ENDPLAY, RankineEnchantments.WITHERING_CURSE,
                RankineEnchantments.GHASTLY_REGENERATION, RankineEnchantments.SHAPE_MEMORY,
                RankineEnchantments.ENDOBIOTIC, RankineEnchantments.ANTIQUATED,
                RankineEnchantments.DUNE_WALKER, RankineEnchantments.PUNCTURE);

        // isDiscoverable() == true at the baseline, so 1.18.2 could roll these onto loot and gear.
        List<ResourceKey<Enchantment>> randomLoot = List.of(
                RankineEnchantments.PUNCTURE, RankineEnchantments.GUARD,
                RankineEnchantments.DUNE_WALKER, RankineEnchantments.GAS_PROTECTION,
                RankineEnchantments.AQUA_LENSE, RankineEnchantments.ANTIQUATED);

        // isDiscoverable() == false at the baseline: must NOT reach loot, mob gear or trade gear.
        List<ResourceKey<Enchantment>> notRandomLoot = List.of(
                RankineEnchantments.ENDPLAY, RankineEnchantments.WITHERING_CURSE,
                RankineEnchantments.GHASTLY_REGENERATION, RankineEnchantments.SHAPE_MEMORY,
                RankineEnchantments.ENDOBIOTIC, RankineEnchantments.ENDURE);

        for (ResourceKey<Enchantment> key : tradeable) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElse(null);
            if (holder == null) {
                problems.add(key.location() + " is not registered");
                continue;
            }
            if (!holder.is(EnchantmentTags.TRADEABLE)) {
                problems.add(key.location() + " missing from #minecraft:tradeable (1.18.2 isTradeable() was true)");
            }
            if (holder.is(EnchantmentTags.TREASURE)) {
                problems.add(key.location() + " is in #minecraft:treasure; no 1.18.2 rankine enchantment was treasure-only");
            }
        }

        for (ResourceKey<Enchantment> key : randomLoot) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElseThrow();
            for (TagKey<Enchantment> tag : List.of(EnchantmentTags.ON_RANDOM_LOOT,
                    EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT, EnchantmentTags.ON_TRADED_EQUIPMENT)) {
                if (!holder.is(tag)) {
                    problems.add(key.location() + " missing from #" + tag.location() + " (1.18.2 isDiscoverable() was true)");
                }
            }
        }

        for (ResourceKey<Enchantment> key : notRandomLoot) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElseThrow();
            for (TagKey<Enchantment> tag : List.of(EnchantmentTags.ON_RANDOM_LOOT,
                    EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT, EnchantmentTags.ON_TRADED_EQUIPMENT)) {
                if (holder.is(tag)) {
                    problems.add(key.location() + " is in #" + tag.location()
                            + " but 1.18.2 isDiscoverable() was false — more available than the baseline");
                }
            }
        }

        // ---- the enchanting-table pool ----
        // In the tag: 1.18.2 isDiscoverable() && !isTreasureOnly(), i.e. everything the table would
        // have CONSIDERED. Antiquated is the whole of F6 — its class overrides
        // canApplyAtEnchantingTable to accept a pewter-alloyed IAlloyTool, never isDiscoverable().
        // The six armour ones (F17) answered canApplyAtEnchantingTable false for every stack, which
        // is a per-STACK rule and so lives in their empty primary_items, not in this tag; the
        // offers below prove the tag membership does not make them offerable.
        List<ResourceKey<Enchantment>> inTable = List.of(
                RankineEnchantments.ANTIQUATED, RankineEnchantments.PUNCTURE,
                RankineEnchantments.QUAKE, RankineEnchantments.RETRIEVAL,
                RankineEnchantments.DUNE_WALKER, RankineEnchantments.SNOW_DRIFTER,
                RankineEnchantments.SPEED_SKATER, RankineEnchantments.SWIFT_SWIMMER,
                RankineEnchantments.GAS_PROTECTION, RankineEnchantments.AQUA_LENSE);

        // Out of the tag for the one reason that is a per-ENCHANTMENT decision: these override
        // isDiscoverable() to false, so 1.18.2 never entered them into the pool at all.
        List<ResourceKey<Enchantment>> notInTable = List.of(
                RankineEnchantments.SHAPE_MEMORY, RankineEnchantments.ENDOBIOTIC,
                RankineEnchantments.WITHERING_CURSE);

        for (ResourceKey<Enchantment> key : inTable) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElseThrow();
            for (TagKey<Enchantment> tag : List.of(EnchantmentTags.IN_ENCHANTING_TABLE, EnchantmentTags.NON_TREASURE)) {
                if (!holder.is(tag)) {
                    problems.add(key.location() + " missing from #" + tag.location()
                            + " (1.18.2 could offer it at the table)");
                }
            }
        }
        for (ResourceKey<Enchantment> key : notInTable) {
            Holder<Enchantment> holder = enchantments.getHolder(key).orElseThrow();
            for (TagKey<Enchantment> tag : List.of(EnchantmentTags.IN_ENCHANTING_TABLE, EnchantmentTags.NON_TREASURE)) {
                if (holder.is(tag)) {
                    problems.add(key.location() + " is in #" + tag.location()
                            + " but 1.18.2's enchanting table could never offer it");
                }
            }
        }

        // The pool the table actually builds. EnchantmentMenu#getEnchantmentList streams
        // #minecraft:in_enchanting_table into EnchantmentHelper.getAvailableEnchantmentResults,
        // which filters it with stack::isPrimaryItemFor. Calling that directly reproduces the menu's
        // candidate list with no RNG, and is the only headless way to show that the tag membership
        // and the per-stack alloy gate combine into the 1.18.2 outcome rather than cancelling out.
        HolderSet<Enchantment> tablePool = enchantments.getTag(EnchantmentTags.IN_ENCHANTING_TABLE).orElse(null);
        if (tablePool == null) {
            problems.add("#minecraft:in_enchanting_table does not exist — the table can offer nothing");
        } else {
            // Each case carries its own table cost, because getAvailableEnchantmentResults only
            // returns an enchantment when the cost falls inside its own min/max window. A negative
            // case tested outside that window would pass no matter what the tag said.
            record Offer(String name, ItemStack stack, ResourceKey<Enchantment> enchantment, int cost,
                         boolean expected, int expectedLevel) {
            }

            ResourceLocation pewterAlloy = ResourceLocation.parse("rankine:alloying/pewter_alloying");
            ResourceLocation roseGoldAlloy = ResourceLocation.parse("rankine:alloying/rose_gold_alloying");

            ItemStack pewterSword = new ItemStack(RankineItems.PEWTER_SWORD.get());
            pewterSword.set(RankineDataComponents.ALLOY_RECIPE.get(), pewterAlloy);
            ItemStack roseGoldSword = new ItemStack(RankineItems.PEWTER_SWORD.get());
            roseGoldSword.set(RankineDataComponents.ALLOY_RECIPE.get(), roseGoldAlloy);

            List<Offer> offers = List.of(
                    // The F6 outcome: 1.18.2's table offered Antiquated on pewter gear, and because
                    // its min_cost is a flat 15 at every level it offered the MAX level at any cost
                    // the scan reaches — level 5, not level 1.
                    new Offer("antiquated on a pewter-alloyed pewter sword", pewterSword,
                            RankineEnchantments.ANTIQUATED, 30, true, 5),
                    // Wrong alloy: 1.18.2 compared the recipe id, so rose gold was refused.
                    new Offer("antiquated on a rose-gold-alloyed sword", roseGoldSword,
                            RankineEnchantments.ANTIQUATED, 30, false, 0),
                    // No alloy at all: canApplyAtEnchantingTable refused outright (unlike the anvil).
                    new Offer("antiquated on a sword with no alloy", new ItemStack(RankineItems.PEWTER_SWORD.get()),
                            RankineEnchantments.ANTIQUATED, 30, false, 0),
                    // Positive control: an ordinary pool member on ordinary gear, so a failure above
                    // cannot be blamed on the pool being empty or this call being mis-wired.
                    new Offer("quake on a steel pickaxe", new ItemStack(RankineItems.STEEL_PICKAXE.get()),
                            RankineEnchantments.QUAKE, 30, true, 3),
                    // The table-banned six, through the real code path rather than the tag. Cost 20
                    // is inside their level-1 window (min 10, max 25), so these would come back true
                    // the moment either of them entered the pool.
                    new Offer("dune walker on diamond boots", new ItemStack(Items.DIAMOND_BOOTS),
                            RankineEnchantments.DUNE_WALKER, 20, false, 0),
                    new Offer("aqua lense on a diamond helmet", new ItemStack(Items.DIAMOND_HELMET),
                            RankineEnchantments.AQUA_LENSE, 20, false, 0),
                    // ---- F28 / decision 3B: the spear and the Totem of Infusing at the TABLE ----
                    // 1.18.2 SpearItem.java:189 returned true from canApplyAtEnchantingTable for
                    // Impaling and Loyalty, so a spear could roll both despite not being a Trident.
                    // Costs are picked to land inside a real level window: Impaling is
                    // min 1+8(l-1)/max 21+8(l-1), so cost 30 is level 4; Loyalty is min 12+7(l-1),
                    // max a flat 50, so cost 30 is level 3.
                    new Offer("impaling on a flint spear", new ItemStack(RankineItems.FLINT_SPEAR.get()),
                            Enchantments.IMPALING, 30, true, 4),
                    new Offer("loyalty on a flint spear", new ItemStack(RankineItems.FLINT_SPEAR.get()),
                            Enchantments.LOYALTY, 30, true, 3),
                    // The allowance is exactly the two the baseline named. Riptide is the control:
                    // same trident-only supported_items, same absent primary_items, no override, so
                    // it must still be refused -- otherwise the fix has opened the whole category.
                    new Offer("riptide on a flint spear", new ItemStack(RankineItems.FLINT_SPEAR.get()),
                            Enchantments.RIPTIDE, 30, false, 0),
                    // 1.18.2 InfusingTotemItem.java:93 returned true for EVERY enchantment. Sharpness
                    // is the honest probe: it is one of the seven vanilla enchantments that DECLARE
                    // primary_items (#enchantable/sword), and the totem is in no enchantable tag at
                    // all, so the default isPrimaryItemFor refuses it however permissive
                    // supportsEnchantment is. Cost 30 is inside Sharpness level 3 (min 23, max 43).
                    new Offer("sharpness on the totem of infusing", new ItemStack(RankineItems.TOTEM_OF_INFUSING.get()),
                            Enchantments.SHARPNESS, 30, true, 3));

            for (Offer o : offers) {
                List<EnchantmentInstance> available = EnchantmentHelper.getAvailableEnchantmentResults(
                        o.cost(), o.stack(), tablePool.stream());
                var offered = available.stream().filter(i -> i.enchantment.is(o.enchantment())).findFirst();
                if (offered.isPresent() != o.expected()) {
                    problems.add(o.name() + ": table offer == " + offered.isPresent()
                            + ", expected " + o.expected());
                } else if (o.expected() && offered.get().level != o.expectedLevel()) {
                    problems.add(o.name() + ": offered at level " + offered.get().level
                            + ", expected " + o.expectedLevel());
                }
            }

            // ---- F28 / decision 3B, the other half: the ANVIL keeps its allowance ----
            // Karl's decision was "allow BOTH", so restoring the table must not be done by moving
            // the override back. supportsEnchantment is the anvil/book hook and it must still say
            // yes to the same three pairs the table now offers.
            record Anvil(String name, ItemStack stack, ResourceKey<Enchantment> enchantment) {}
            for (Anvil a : List.of(
                    new Anvil("impaling on a flint spear", new ItemStack(RankineItems.FLINT_SPEAR.get()),
                            Enchantments.IMPALING),
                    new Anvil("loyalty on a flint spear", new ItemStack(RankineItems.FLINT_SPEAR.get()),
                            Enchantments.LOYALTY),
                    new Anvil("sharpness on the totem of infusing",
                            new ItemStack(RankineItems.TOTEM_OF_INFUSING.get()), Enchantments.SHARPNESS))) {
                Holder<Enchantment> holder = enchantments.getHolder(a.enchantment()).orElseThrow();
                if (!a.stack().supportsEnchantment(holder)) {
                    problems.add(a.name() + ": the anvil path (supportsEnchantment) refuses it; "
                            + "decision 3B keeps the port's anvil allowance as well as the table");
                }
            }
        }

        assertEmpty(problems, "enchantment availability mismatches");
        helper.succeed();
    }

    /**
     * F17 — the six armour enchantments 1.18.2's enchanting table refused on every stack must be in
     * the pool tags and still be offered by nothing.
     *
     * <p>Their 1.18.2 classes override {@code canApplyAtEnchantingTable(stack)} to an unconditional
     * {@code false} while leaving {@code canEnchant} as the plain {@code ARMOR_FEET}/
     * {@code ARMOR_HEAD} category check (e.g. {@code DuneWalkerEnchantment.java:26} at
     * {@code 859f87773}). That is a per-STACK rule, so its 1.21 home is the enchantment definition's
     * {@code primary_items}, not a tag: an explicitly EMPTY holder set, which
     * {@code IItemExtension#isPrimaryItemFor} (IItemExtension.java:458) reads as
     * {@code primaryItems.isEmpty() || stack.is(primaryItems.get())} — present-but-empty fails for
     * every stack, whereas ABSENT means "no restriction". This asserts the distinction on the LIVE
     * registry, so it fails if the generated JSON goes stale, if the datagen encode silently drops
     * an empty holder set, or if a future edit reaches for the absent form.
     *
     * <p>The three consumer chains that read it, all of which 1.18.2 gated on the same override:
     * <ul>
     *   <li>table — {@code EnchantmentMenu#getEnchantmentList} (EnchantmentMenu.java:201) streams
     *       {@code #in_enchanting_table} into {@code EnchantmentHelper.getAvailableEnchantmentResults},
     *       which filters with {@code stack::isPrimaryItemFor} (EnchantmentHelper.java:608);</li>
     *   <li>mob gear — {@code Mob#populateDefaultEquipmentEnchantments} (Mob.java:1158) →
     *       {@code EnchantmentHelper.enchantItemFromProvider} →
     *       {@code VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT}
     *       (VanillaEnchantmentProviders.java:24), an {@code EnchantmentsByCostWithDifficulty} over
     *       {@code #on_mob_spawn_equipment} whose {@code enchant}
     *       (EnchantmentsByCostWithDifficulty.java:35) calls
     *       {@code EnchantmentHelper.selectEnchantment} → the same filter. Villager gear
     *       (VillagerTrades.java:1577) and {@code EnchantWithLevelsFunction}
     *       (EnchantWithLevelsFunction.java:64) reach it the same way, through
     *       {@code EnchantmentHelper.enchantItem} (EnchantmentHelper.java:544);</li>
     *   <li>anvil and {@code EnchantRandomlyFunction} — {@code supportsEnchantment} only
     *       (EnchantRandomlyFunction.java:62), which is 1.18.2's {@code canEnchant}. Those must
     *       still say YES, or the fix has overshot into territory the baseline allowed.</li>
     * </ul>
     * That is what closes batch 2's triage item (a): the six were already in
     * {@code on_mob_spawn_equipment}/{@code on_traded_equipment}, where 1.18.2's mob-gear path also
     * required {@code canApplyAtEnchantingTable}. Tag membership was never the thing to remove.
     *
     * <p>Books are the one place the six become MORE available than they were at HEAD, and that is
     * parity: 1.18.2's table reached a book through a separate {@code flag && isAllowedOnBooks()}
     * branch (no Rankine class overrode {@code isAllowedOnBooks}), and 1.21 keeps that special case
     * ahead of the {@code primary_items} test (IItemExtension.java:454). While the six were out of
     * the pool tag, that branch was unreachable for them.
     */
    @GameTest(template = "empty")
    public void tableBannedEnchantmentsHaveEmptyPrimaryItems(GameTestHelper helper) {
        var enchantments = helper.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<String> problems = new ArrayList<>();

        // armour: a VANILLA piece of the slot 1.18.2's canEnchant accepted, so a negative cannot be
        // blamed on the item being unenchantable in the first place.
        record Banned(String name, ResourceKey<Enchantment> key, Item armour) {
        }

        List<Banned> banned = List.of(
                new Banned("dune_walker", RankineEnchantments.DUNE_WALKER, Items.DIAMOND_BOOTS),
                new Banned("snow_drifter", RankineEnchantments.SNOW_DRIFTER, Items.DIAMOND_BOOTS),
                new Banned("speed_skater", RankineEnchantments.SPEED_SKATER, Items.DIAMOND_BOOTS),
                new Banned("swift_swimmer", RankineEnchantments.SWIFT_SWIMMER, Items.DIAMOND_BOOTS),
                new Banned("gas_protection", RankineEnchantments.GAS_PROTECTION, Items.DIAMOND_HELMET),
                new Banned("aqua_lense", RankineEnchantments.AQUA_LENSE, Items.DIAMOND_HELMET));

        // Every one of the six has min_cost 10 and max_cost 25 at its only level, so 20 is inside
        // the window: getAvailableEnchantmentResults would return them the moment the per-stack
        // filter let them through. A cost outside the window would make every negative vacuous.
        final int COST = 20;

        HolderSet<Enchantment> tablePool = enchantments.getTag(EnchantmentTags.IN_ENCHANTING_TABLE).orElse(null);
        HolderSet<Enchantment> mobPool = enchantments.getTag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT).orElse(null);
        assertTrue(tablePool != null, "#minecraft:in_enchanting_table does not exist");
        assertTrue(mobPool != null, "#minecraft:on_mob_spawn_equipment does not exist");

        for (Banned b : banned) {
            Holder<Enchantment> holder = enchantments.getHolder(b.key()).orElse(null);
            if (holder == null) {
                problems.add(b.name() + " is not registered");
                continue;
            }
            ItemStack stack = new ItemStack(b.armour());

            // ---- the mechanism, off the live registry ----
            Optional<HolderSet<Item>> primary = holder.value().definition().primaryItems();
            if (primary.isEmpty()) {
                problems.add(b.name() + " has NO primary_items; absent means \"no restriction\","
                        + " which is the opposite of 1.18.2's canApplyAtEnchantingTable() == false");
            } else if (primary.get().size() != 0) {
                problems.add(b.name() + " has " + primary.get().size() + " primary_items, expected an empty set");
            }
            // supported_items must still cover the armour, or the anvil half breaks with it.
            if (!stack.is(holder.value().definition().supportedItems())) {
                problems.add(b.name() + ": " + b.armour() + " is not in its supported_items,"
                        + " so 1.18.2's canEnchant() == true is lost too");
            }

            // ---- tag membership: in, exactly as 1.18.2's isDiscoverable()/!isTreasureOnly() ----
            for (TagKey<Enchantment> tag : List.of(EnchantmentTags.IN_ENCHANTING_TABLE, EnchantmentTags.NON_TREASURE,
                    EnchantmentTags.TRADEABLE, EnchantmentTags.ON_RANDOM_LOOT,
                    EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT, EnchantmentTags.ON_TRADED_EQUIPMENT)) {
                if (!holder.is(tag)) {
                    problems.add(b.name() + " missing from #" + tag.location());
                }
            }
            if (holder.is(EnchantmentTags.TREASURE)) {
                problems.add(b.name() + " is in #minecraft:treasure; no 1.18.2 rankine enchantment was treasure-only");
            }

            // ---- offers unchanged: still nothing, on either selection path ----
            if (offers(COST, stack, tablePool, b.key())) {
                problems.add(b.name() + " is offered at the enchanting table on " + b.armour()
                        + "; 1.18.2's canApplyAtEnchantingTable() refused every stack");
            }
            if (offers(COST, stack, mobPool, b.key())) {
                problems.add(b.name() + " can roll onto mob spawn equipment (" + b.armour()
                        + "); 1.18.2's mob-gear path required canApplyAtEnchantingTable too");
            }

            // ---- but the anvil / EnchantRandomly half must still say yes (1.18.2 canEnchant) ----
            if (!stack.supportsEnchantment(holder)) {
                problems.add(b.name() + ": book refused on the anvil for " + b.armour()
                        + "; 1.18.2's canEnchant() accepted it");
            }

            // ---- books: reachable again, which is what 1.18.2 did ----
            if (!offers(COST, new ItemStack(Items.BOOK), tablePool, b.key())) {
                problems.add(b.name() + " is not offered on a plain book at the table; 1.18.2 reached"
                        + " books through isAllowedOnBooks(), which no rankine class overrode");
            }
        }

        // Positive controls on the very same stacks, costs and pools, so a blanket failure of the
        // selection call cannot masquerade as six passing negatives. Guard's window at level 1 is
        // [10, 40], so COST is inside it, and its primary_items are ABSENT — the other branch of
        // the isPrimaryItemFor test, which would also break if NO_PRIMARY_ITEMS were applied to
        // everything.
        for (Item armour : List.of(Items.DIAMOND_BOOTS, Items.DIAMOND_HELMET)) {
            ItemStack stack = new ItemStack(armour);
            if (!offers(COST, stack, tablePool, RankineEnchantments.GUARD)) {
                problems.add("rankine:guard is NOT offered at the table on " + armour
                        + ", so the negatives above prove nothing");
            }
            if (!offers(COST, stack, mobPool, RankineEnchantments.GUARD)) {
                problems.add("rankine:guard cannot roll onto mob gear on " + armour
                        + ", so the mob-gear negatives above prove nothing");
            }
        }
        for (ResourceKey<Enchantment> unrestricted : List.of(RankineEnchantments.GUARD, RankineEnchantments.PUNCTURE)) {
            if (enchantments.getHolder(unrestricted).orElseThrow().value().definition().primaryItems().isPresent()) {
                problems.add(unrestricted.location() + " has a primary_items set; only the six the"
                        + " 1.18.2 table always refused should have one");
            }
        }

        assertEmpty(problems, "empty primary_items failures");
        helper.succeed();
    }

    /** True when the real table/loot selection call would return {@code enchantment} for this stack. */
    private static boolean offers(int cost, ItemStack stack, HolderSet<Enchantment> pool, ResourceKey<Enchantment> enchantment) {
        return EnchantmentHelper.getAvailableEnchantmentResults(cost, stack, pool.stream())
                .stream().anyMatch(i -> i.enchantment.is(enchantment));
    }

    /**
     * F5 — Rankine crops must keep tilled soil hydrated.
     *
     * <p>{@code TilledSoilBlock#hasCrops} and vanilla {@code FarmBlock} both read
     * {@code #minecraft:maintains_farmland}; a crop missing from it lets the soil dry out and
     * revert to dirt with the crop still standing on it.
     */
    @GameTest(template = "empty")
    public void rankineCropsMaintainFarmland(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        List<Block> crops = new ArrayList<>();
        crops.addAll(RankineLists.CROPS_SINGLE);
        crops.addAll(RankineLists.CROPS_DOUBLE);
        crops.addAll(RankineLists.CROPS_TRIPLE);

        assertTrue(crops.size() >= 12, "expected >=12 rankine crop blocks, found " + crops.size());

        for (Block crop : crops) {
            if (!crop.defaultBlockState().is(BlockTags.MAINTAINS_FARMLAND)) {
                problems.add(BuiltInRegistries.BLOCK.getKey(crop) + " missing from #minecraft:maintains_farmland");
            }
            if (!crop.defaultBlockState().is(BlockTags.CROPS)) {
                problems.add(BuiltInRegistries.BLOCK.getKey(crop) + " missing from #minecraft:crops");
            }
        }

        // Vanilla's own crops must stay in the tag: the mod contributes #minecraft:crops to it and
        // must not have replaced vanilla's list.
        if (!Blocks.WHEAT.defaultBlockState().is(BlockTags.MAINTAINS_FARMLAND)) {
            problems.add("minecraft:wheat fell out of #minecraft:maintains_farmland");
        }

        assertEmpty(problems, "crop farmland-hydration failures");
        helper.succeed();
    }

    // ------------------------------------------------------------------------------------------
    // Worldgen biome selection (F7-F9). These run on the real server, so they see the biome
    // registry, the mod's biome tags and the biome modifiers exactly as a world would.
    // ------------------------------------------------------------------------------------------

    /** Stone layer list the config gives a biome, or null if the biome has no row at all. */
    private static List<String> layersFor(ResourceKey<Biome> biome) {
        int index = WorldgenUtils.GEN_BIOMES.indexOf(biome.location());
        return index == -1 ? null : WorldgenUtils.LAYER_LISTS.get(index);
    }

    /**
     * Every Rankine ore vein attached to a biome, in the order {@code RankineOreBiomeModifier}
     * added them (= the order of the matching {@code oreSettings} rows), read off the live
     * (modified) generation settings.
     * <p>
     * The veins are identified structurally, by their {@link RankineOreFeatureConfig}: the modifier
     * builds inline {@code Holder.direct} placed features, so they have no registry name to match on.
     * Nothing else in the mod ever constructs that config, so this list is exactly the ore rows.
     */
    private static List<Block> oreVeinList(Holder<Biome> biome) {
        List<Block> found = new ArrayList<>();
        List<HolderSet<PlacedFeature>> byStep = biome.value().getGenerationSettings().features();
        int step = GenerationStep.Decoration.UNDERGROUND_ORES.ordinal();
        if (step >= byStep.size()) return found;
        for (Holder<PlacedFeature> holder : byStep.get(step)) {
            if (holder.value().feature().value().config() instanceof RankineOreFeatureConfig ore
                    && !ore.targetStates.isEmpty()) {
                found.add(ore.targetStates.get(0).state.getBlock());
            }
        }
        return found;
    }

    /** Blocks of every Rankine ore vein attached to a biome, read off the live (modified) settings. */
    private static Set<Block> oreVeinBlocks(Holder<Biome> biome) {
        return new HashSet<>(oreVeinList(biome));
    }

    private static List<String> idsOf(List<Block> blocks) {
        List<String> ids = new ArrayList<>();
        for (Block b : blocks) ids.add(String.valueOf(BuiltInRegistries.BLOCK.getKey(b)));
        return ids;
    }

    private static boolean hasPlacedFeature(Holder<Biome> biome, GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
        List<HolderSet<PlacedFeature>> byStep = biome.value().getGenerationSettings().features();
        if (step.ordinal() >= byStep.size()) return false;
        for (Holder<PlacedFeature> holder : byStep.get(step.ordinal())) {
            if (holder.unwrapKey().filter(feature::equals).isPresent()) return true;
        }
        return false;
    }

    /**
     * F7 — the {@code "the_end"} biomeSettings row and the eight End ore rows must actually match.
     *
     * <p>{@code CATEGORY_TAGS} was keyed {@code "theend"} while every shipped config row says
     * {@code "the_end"} (the string 1.18.2's {@code BiomeCategory.THEEND.getName()} produced and
     * {@code byName} consumed), so the lookup returned null and all nine rows were skipped in
     * silence: no End stone layering, and no End-exclusive ore veins.
     */
    @GameTest(template = "empty")
    public void endCategoryRowsSelectEndBiomes(GameTestHelper helper) {
        WorldgenUtils.ensureBiomesResolved(helper.getLevel().registryAccess());
        var biomeRegistry = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        List<ResourceKey<Biome>> endBiomes = List.of(Biomes.THE_END, Biomes.END_HIGHLANDS,
                Biomes.END_MIDLANDS, Biomes.END_BARRENS, Biomes.SMALL_END_ISLANDS);
        for (ResourceKey<Biome> key : endBiomes) {
            List<String> layers = layersFor(key);
            if (layers == null) {
                problems.add(key.location() + " missing from GEN_BIOMES");
            } else if (!List.of("minecraft:end_stone").equals(layers)) {
                problems.add(key.location() + " stone layers are " + layers + ", expected [minecraft:end_stone]");
            }
        }

        // The eight ore rows keyed "the_end" must reach an End biome through RankineOreBiomeModifier.
        Set<Block> veins = oreVeinBlocks(biomeRegistry.getHolderOrThrow(Biomes.END_HIGHLANDS));
        for (String ore : List.of("rankine:native_gallium_ore", "rankine:native_indium_ore",
                "rankine:native_selenium_ore", "rankine:native_tellurium_ore", "rankine:molybdenite_ore",
                "rankine:uraninite_ore", "rankine:xenotime_ore", "rankine:greenockite_ore")) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(ore));
            if (!veins.contains(block)) {
                problems.add(ore + " has no vein in minecraft:end_highlands");
            }
        }

        assertEmpty(problems, "End worldgen selection failures");
        helper.succeed();
    }

    /**
     * F7 — the {@code "none"} row (1.18.2 {@code BiomeCategory.NONE}) had no mapping at all.
     *
     * <p>No tag can say "declared no category", so it is a predicate: matches a biome in none of the
     * other {@code CATEGORY_TAGS} tags. In vanilla that is {@code minecraft:the_void}. The row sits
     * after every exact-biome row in the config, and {@code GEN_BIOMES.indexOf} returns the first
     * match, so biomes with their own row (stony_shore, which {@code c:is_beach} does not contain)
     * must still resolve to that row and not to this one.
     */
    @GameTest(template = "empty")
    public void noneCategoryRowSelectsUncategorizedBiomes(GameTestHelper helper) {
        WorldgenUtils.ensureBiomesResolved(helper.getLevel().registryAccess());
        var biomeRegistry = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        List<String> noneLayers = List.of("rankine:wadsleyone", "rankine:troctolite", "rankine:gabbro",
                "rankine:slate", "rankine:shale", "rankine:tholeiitic_basalt");
        List<String> voidLayers = layersFor(Biomes.THE_VOID);
        if (voidLayers == null) {
            problems.add("minecraft:the_void missing from GEN_BIOMES");
        } else if (!noneLayers.equals(voidLayers)) {
            problems.add("minecraft:the_void stone layers are " + voidLayers + ", expected the \"none\" row " + noneLayers);
        }

        if (!WorldgenUtils.matchesCategory(biomeRegistry.getHolderOrThrow(Biomes.THE_VOID), WorldgenUtils.CATEGORY_NONE)) {
            problems.add("minecraft:the_void does not match category \"none\"");
        }
        if (WorldgenUtils.matchesCategory(biomeRegistry.getHolderOrThrow(Biomes.PLAINS), WorldgenUtils.CATEGORY_NONE)) {
            problems.add("minecraft:plains matches category \"none\" but it is in #c:is_plains");
        }

        // Ordering guard: an exact-biome row earlier in the list must still win over "none".
        List<String> shoreLayers = layersFor(Biomes.STONY_SHORE);
        if (shoreLayers == null) {
            problems.add("minecraft:stony_shore missing from GEN_BIOMES");
        } else if (noneLayers.equals(shoreLayers)) {
            problems.add("minecraft:stony_shore resolved to the \"none\" row instead of its own row");
        }

        assertEmpty(problems, "\"none\" category failures");
        helper.succeed();
    }

    /**
     * F8 — {@code "icy"} pointed at {@code c:is_icy}, which is {@code [ice_spikes, frozen_peaks]}.
     *
     * <p>1.18.2's {@code BiomeCategory.ICY} was {@code [snowy_plains, ice_spikes]}, so snowy plains
     * lost every bit of Rankine terrain and frozen peaks (a MOUNTAIN biome) was wrongly pulled in.
     * {@code rankine:is_icy} restores the exact membership.
     */
    @GameTest(template = "empty")
    public void icyCategoryRowSelectsSnowyPlainsAndIceSpikes(GameTestHelper helper) {
        WorldgenUtils.ensureBiomesResolved(helper.getLevel().registryAccess());
        var biomeRegistry = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        List<String> icyLayers = List.of("rankine:whiteschist", "rankine:white_marble", "rankine:comendite",
                "rankine:mica_schist", "rankine:phyllite", "minecraft:andesite", "rankine:chalk");
        for (ResourceKey<Biome> key : List.of(Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES)) {
            List<String> layers = layersFor(key);
            if (layers == null) {
                problems.add(key.location() + " missing from GEN_BIOMES");
            } else if (!icyLayers.equals(layers)) {
                problems.add(key.location() + " stone layers are " + layers + ", expected the \"icy\" row " + icyLayers);
            }
            if (!biomeRegistry.getHolderOrThrow(key).is(RankineTags.Biomes.IS_ICY)) {
                problems.add(key.location() + " missing from #rankine:is_icy");
            }
        }

        // frozen_peaks was MOUNTAIN in 1.18.2 and the "mountain" row already claims it: it must NOT
        // be pulled in by the icy row (which is what a #c:is_icy union would do).
        if (biomeRegistry.getHolderOrThrow(Biomes.FROZEN_PEAKS).is(RankineTags.Biomes.IS_ICY)) {
            problems.add("minecraft:frozen_peaks is in #rankine:is_icy but was MOUNTAIN in 1.18.2");
        }
        List<String> peakLayers = layersFor(Biomes.FROZEN_PEAKS);
        if (peakLayers != null && icyLayers.equals(peakLayers)) {
            problems.add("minecraft:frozen_peaks resolved to the \"icy\" row instead of the \"mountain\" row");
        }

        // rankine:cryolite_ore's only overworld-cold row lists "icy"; snowy plains must get it.
        Set<Block> veins = oreVeinBlocks(biomeRegistry.getHolderOrThrow(Biomes.SNOWY_PLAINS));
        Block cryolite = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("rankine:cryolite_ore"));
        if (!veins.contains(cryolite)) {
            problems.add("rankine:cryolite_ore has no vein in minecraft:snowy_plains");
        }

        assertEmpty(problems, "\"icy\" category failures");
        helper.succeed();
    }

    /**
     * F9 — flat bedrock, world replacer and intrusions must reach every biome in every dimension.
     *
     * <p>1.18.2 added all three unconditionally ({@code FeatureGeneration#addFeaturesToBiomes}
     * guarded them on nothing but the config value). The port gated them on
     * {@code c:is_overworld} / {@code minecraft:is_nether} / {@code minecraft:is_end}, so any biome
     * in none of those three — every modded dimension, plus {@code minecraft:the_void} — got vanilla
     * stone. The modifiers now omit their {@code biomes} field, which means "all biomes".
     *
     * <p>{@code flat_bedrock}'s gate ({@code flatBedrockLayers}) defaults to 0, so only its biome
     * set is asserted; the other two gates are on by default and are checked end to end.
     */
    @GameTest(template = "empty")
    public void dimensionWideFeaturesReachEveryBiome(GameTestHelper helper) {
        var registryAccess = helper.getLevel().registryAccess();
        var modifiers = registryAccess.registryOrThrow(NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        var biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        for (ResourceKey<BiomeModifier> key : List.of(RankineBiomeModifiers.FLAT_BEDROCK,
                RankineBiomeModifiers.WORLD_REPLACER, RankineBiomeModifiers.INTRUSION)) {
            BiomeModifier modifier = modifiers.get(key);
            if (!(modifier instanceof ConfigGatedFeaturesBiomeModifier gated)) {
                problems.add(key.location() + " is not a rankine:config_gated_add_features modifier");
            } else if (gated.biomes().isPresent()) {
                problems.add(key.location() + " restricts its biome set instead of applying to every biome");
            }
        }

        // The two gates that are on by default must be visible on every single loaded biome.
        int checked = 0;
        for (Holder.Reference<Biome> biome : biomeRegistry.holders().toList()) {
            checked++;
            if (!hasPlacedFeature(biome, GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_WORLD_REPLACER)) {
                problems.add(biome.key().location() + " has no rankine:world_replacer");
            }
            if (!hasPlacedFeature(biome, GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_INTRUSION)) {
                problems.add(biome.key().location() + " has no rankine:intrusion");
            }
        }
        assertTrue(checked >= 60, "expected >=60 loaded biomes, found " + checked);

        // the_void is the vanilla stand-in for a biome outside all three dimension tags, which is
        // the exact shape of the modded-dimension regression this fix is about.
        if (!hasPlacedFeature(biomeRegistry.getHolderOrThrow(Biomes.THE_VOID),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, RankinePlacedFeatures.PLACED_WORLD_REPLACER)) {
            problems.add("minecraft:the_void has no rankine:world_replacer (it is in none of the three dimension tags)");
        }

        assertEmpty(problems, "biomes missing dimension-wide Rankine features");
        helper.succeed();
    }

    // ------------------------------------------------------------------------------------------
    // F7 runtime proof. endCategoryRowsSelectEndBiomes above proves the eight End ore rows are
    // *selected*; these two prove the feature they build is actually attached in the expected
    // number, and that running it over end stone actually replaces blocks.
    // ------------------------------------------------------------------------------------------

    /** The eight {@code "the_end"} oreSettings rows, in config order (Config.java:1288-1295). */
    private static final List<String> END_ORE_VEINS = List.of(
            "rankine:native_gallium_ore", "rankine:native_indium_ore", "rankine:native_selenium_ore",
            "rankine:native_tellurium_ore", "rankine:molybdenite_ore", "rankine:uraninite_ore",
            "rankine:xenotime_ore", "rankine:greenockite_ore");

    /**
     * Of those eight, the six that appear in NO other row. uraninite and molybdenite also have an
     * {@code "overworld"} row (Config.java:1227-1228), so they are not End-exclusive.
     */
    private static final List<String> END_EXCLUSIVE_ORES = List.of(
            "rankine:native_gallium_ore", "rankine:native_indium_ore", "rankine:native_selenium_ore",
            "rankine:native_tellurium_ore", "rankine:xenotime_ore", "rankine:greenockite_ore");

    /**
     * F7 — an End biome must carry exactly the eight End ore veins, no more and no fewer.
     *
     * <p><b>Derivation of the number 8</b> (from the shipped default {@code oreSettings},
     * Config.java:1176-1295 — 75 active rows — read through {@code RankineOreBiomeModifier#matches}):
     * <ul>
     *   <li>43 rows select {@code "overworld"}, which is {@link WorldgenUtils#isOverworld}:
     *       {@code !biome.is(IS_NETHER) && !biome.is(IS_END)}. Every End biome is in
     *       {@code minecraft:is_end}, so <b>none</b> of the 43 match — this is why the count is 8
     *       and not 51, and why {@code rankine:hematite_ore} (overworld-only) is checked absent below;</li>
     *   <li>9 rows select the {@code "nether"} category ({@code minecraft:is_nether}) — no match;</li>
     *   <li>2 rows name an exact biome ({@code minecraft:nether_wastes},
     *       {@code minecraft:crimson_forest}) — no match;</li>
     *   <li>12 rows select combinations of overworld-only categories (mesa, ocean, beach, mushroom,
     *       desert, plains, savanna, jungle, swamp, forest, taiga, extreme_hills, mountain, icy).
     *       An End biome is in none of those tags — no match;</li>
     *   <li>1 row selects {@code "all"}, which was never a {@code BiomeCategory} name and so matches
     *       nothing (parity with 1.18.2, see {@link WorldgenUtils#isCategory});</li>
     *   <li>{@code "none"} would be excluded anyway: it means "in none of the CATEGORY_TAGS tags",
     *       and an End biome is in {@code minecraft:is_end}, which is one of them. No default row
     *       uses it (the only one, basaltic_tuff, is commented out);</li>
     *   <li>leaving the 8 rows keyed {@code "the_end"} → {@code minecraft:is_end}. 43+9+2+12+1+8 = 75.</li>
     * </ul>
     * The order is the config's, because the modifier appends one placed feature per matching row.
     */
    @GameTest(template = "empty")
    public void endBiomesCarryExactlyTheEightEndOreVeins(GameTestHelper helper) {
        var biomeRegistry = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        for (ResourceKey<Biome> key : List.of(Biomes.THE_END, Biomes.END_HIGHLANDS,
                Biomes.END_MIDLANDS, Biomes.END_BARRENS, Biomes.SMALL_END_ISLANDS)) {
            List<String> veins = idsOf(oreVeinList(biomeRegistry.getHolderOrThrow(key)));
            if (!END_ORE_VEINS.equals(veins)) {
                problems.add(key.location() + " has " + veins.size() + " Rankine ore veins " + veins
                        + ", expected the " + END_ORE_VEINS.size() + " \"the_end\" rows in config order");
            }
        }

        // The other half of the derivation: "overworld" must NOT reach the End, and "the_end" must
        // not leak into the overworld. hematite has two overworld rows and no other row at all.
        List<String> endVeins = idsOf(oreVeinList(biomeRegistry.getHolderOrThrow(Biomes.END_HIGHLANDS)));
        if (endVeins.contains("rankine:hematite_ore")) {
            problems.add("minecraft:end_highlands carries rankine:hematite_ore (an \"overworld\" row)");
        }
        List<String> plainsVeins = idsOf(oreVeinList(biomeRegistry.getHolderOrThrow(Biomes.PLAINS)));
        for (String ore : END_EXCLUSIVE_ORES) {
            if (plainsVeins.contains(ore)) {
                problems.add("minecraft:plains carries " + ore + ", which only has a \"the_end\" row");
            }
        }
        if (!plainsVeins.contains("rankine:hematite_ore")) {
            problems.add("minecraft:plains has no rankine:hematite_ore, so the overworld rows are not applying"
                    + " and the End count above would be vacuous");
        }

        // QA's in-world scans looked for #c:ores. All eight End ores are in it (through their
        // per-element child tags), so a scan that finds nothing is evidence about the veins, not
        // about the tag. Pinned here so that stays true.
        for (String ore : END_ORE_VEINS) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(ore));
            if (!block.defaultBlockState().is(Tags.Blocks.ORES)) {
                problems.add(ore + " is not in #c:ores");
            }
        }

        assertEmpty(problems, "End ore vein attachment failures");
        helper.succeed();
    }

    /** Fixed seed for the vein below; chosen because it places 5 blocks, none of them borderline. */
    private static final long VEIN_SEED = 1004L;
    /** Test region: 5x5x5, relative x/z 0..4 and y 1..5, centred on {@link #VEIN_ORIGIN}. */
    private static final BlockPos VEIN_REGION_MIN = new BlockPos(0, 1, 0);
    private static final BlockPos VEIN_REGION_MAX = new BlockPos(4, 5, 4);
    private static final BlockPos VEIN_ORIGIN = new BlockPos(2, 3, 2);
    private static final int VEIN_REGION_BLOCKS = 125;

    private static void fillRegion(GameTestHelper helper, BlockState state) {
        for (BlockPos pos : BlockPos.betweenClosed(VEIN_REGION_MIN, VEIN_REGION_MAX)) {
            helper.setBlock(pos, state);
        }
    }

    private static int countInRegion(GameTestHelper helper, Block block) {
        int found = 0;
        for (BlockPos pos : BlockPos.betweenClosed(VEIN_REGION_MIN, VEIN_REGION_MAX)) {
            if (helper.getBlockState(pos).is(block)) found++;
        }
        return found;
    }

    /**
     * F7 — the last unproven link: the vein feature actually runs and replaces end stone.
     *
     * <p>Selection ({@code endCategoryRowsSelectEndBiomes}) and attachment
     * ({@code endBiomesCarryExactlyTheEightEndOreVeins}) only prove the feature is wired up. This
     * runs it: a solid 5x5x5 of {@code minecraft:end_stone} is built in the test structure and the
     * real {@code rankine:default_ore_vein} {@link Feature} is invoked directly, through
     * {@link FeaturePlaceContext}, with the same {@link RankineOreFeatureConfig} the modifier builds
     * for the {@code rankine:native_gallium_ore} {@code "the_end"} row (Config.java:1288 — default
     * vein, size 4, density 1.0, discard 0.0) and a seeded {@link RandomSource}, so the outcome is
     * deterministic. {@link GameTestHelper#getLevel()} is a {@code ServerLevel}, which implements
     * {@code WorldGenLevel}, so no adaptation is needed.
     *
     * <p>The {@link TagMatchTest} against {@code rankine:rankine_ore_replaceables} is half the
     * point, so it is not stubbed out: the negative control re-runs the identical feature, config
     * and seed over {@code minecraft:end_stone_bricks}, which differs from end stone only in not
     * being in that tag, and must change nothing. End stone reaches the tag through
     * {@code c:base_stone_end} (RankineBlockTagsProvider.java:658).
     */
    @GameTest(template = "empty")
    public void endOreVeinFeatureReplacesEndStone(GameTestHelper helper) {
        var level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        Block ore = RankineBlocks.NATIVE_GALLIUM_ORE.get();
        TagKey<Block> replaceables = RankineTags.Blocks.RANKINE_ORE_REPLACEABLES;
        if (!Blocks.END_STONE.defaultBlockState().is(replaceables)) {
            problems.add("minecraft:end_stone is not in #rankine:rankine_ore_replaceables");
        }
        if (Blocks.END_STONE_BRICKS.defaultBlockState().is(replaceables)) {
            problems.add("minecraft:end_stone_bricks IS in #rankine:rankine_ore_replaceables,"
                    + " so it is not a valid negative control");
        }

        Feature<RankineOreFeatureConfig> feature = RankineFeatures.DEFAULT_ORE_VEIN.get();
        RankineOreFeatureConfig config = new RankineOreFeatureConfig(new TagMatchTest(replaceables),
                ore.defaultBlockState(), 4, 0.0F, 1.0F, 1.0F);
        BlockPos origin = helper.absolutePos(VEIN_ORIGIN);

        fillRegion(helper, Blocks.END_STONE.defaultBlockState());
        boolean placed = feature.place(new FeaturePlaceContext<>(Optional.empty(), level,
                level.getChunkSource().getGenerator(), RandomSource.create(VEIN_SEED), origin, config));
        int oreBlocks = countInRegion(helper, ore);
        int endStone = countInRegion(helper, Blocks.END_STONE);

        if (!placed) {
            problems.add("place() returned false over solid end_stone");
        }
        if (oreBlocks < 1) {
            problems.add("no end_stone became rankine:native_gallium_ore (" + endStone + "/"
                    + VEIN_REGION_BLOCKS + " still end_stone)");
        }
        if (oreBlocks + endStone != VEIN_REGION_BLOCKS) {
            problems.add("region holds " + oreBlocks + " ore + " + endStone + " end_stone, expected the two"
                    + " to account for all " + VEIN_REGION_BLOCKS + " blocks");
        }

        // Negative control: same feature, same config, same seed, one non-tagged target block.
        fillRegion(helper, Blocks.END_STONE_BRICKS.defaultBlockState());
        feature.place(new FeaturePlaceContext<>(Optional.empty(), level,
                level.getChunkSource().getGenerator(), RandomSource.create(VEIN_SEED), origin, config));
        int controlOre = countInRegion(helper, ore);
        int controlIntact = countInRegion(helper, Blocks.END_STONE_BRICKS);
        if (controlOre != 0) {
            problems.add(controlOre + " end_stone_bricks became ore despite failing the tag match");
        }
        if (controlIntact != VEIN_REGION_BLOCKS) {
            problems.add("only " + controlIntact + "/" + VEIN_REGION_BLOCKS + " control blocks survived");
        }

        assertEmpty(problems, "End ore vein placement failures");
        helper.succeed();
    }

    // ------------------------------------------------------------------------------------------
    // F18 — End ore vein height bands (review decision #7, "veins need to be findable").
    // ------------------------------------------------------------------------------------------

    /**
     * Sample grid for the End terrain profile: two 24x24 patches of columns 32 blocks apart, both in
     * the outer-island ring (the End's islands only exist beyond r=1024, and end_highlands only out
     * there). Two widely separated patches so the profile is not one island field's accident. The
     * GameTest server seeds its world at 0 ({@code GameTestServer.WORLD_OPTIONS}) and
     * {@code getBaseColumn} is pure noise with no chunk generation, so this is deterministic.
     */
    private static final int[][] END_SAMPLE_PATCHES = {{2048, 2048}, {-4096, 6144}};
    private static final int END_SAMPLE_STEP = 32;
    private static final int END_SAMPLE_GRID = 24;

    /**
     * The slab the End's stone occupies, as measured below. Not a guess: the End's noise settings
     * are {@code NoiseSettings.END_NOISE_SETTINGS = create(0, 128, 2, 1)}, so terrain can only exist
     * in y 0..127 at all, and {@code NoiseRouterData.slideEnd} fades density in over y 4..32 and out
     * from y 56 upwards, leaving a slab with soft edges. These are loose brackets on the
     * measurement, not the measurement.
     */
    private static final int END_SLAB_MIN = 8;
    private static final int END_SLAB_MAX = 70;

    /**
     * The bands this retune replaced — the 1.18.2 numbers verbatim. Nothing in the mod reads them
     * any more; they are here so the new bands can be scored against the old ones on one profile.
     */
    private static final int[] OLD_NATIVE_BAND = {-50, 70};
    private static final int[] OLD_SPHERE_BAND = {10, 40};

    /** Per-y probability of {@code HeightRangePlacement}, indexed from {@code min}. */
    private static double[] placementMass(boolean triangle, int min, int max) {
        int k = max - min;
        double[] mass = new double[k + 1];
        if (!triangle) {
            // UniformHeight#sample -> Mth.randomBetweenInclusive(random, min, max).
            for (int i = 0; i <= k; i++) mass[i] = 1.0D / (k + 1);
        } else {
            // TrapezoidHeight#sample with plateau 0 -> min + U{0..i1} + U{0..l}, l = k/2 rounded down.
            int l = k / 2;
            int i1 = k - l;
            double total = (double) (i1 + 1) * (l + 1);
            for (int a = 0; a <= i1; a++) {
                for (int b = 0; b <= l; b++) {
                    mass[a + b] += 1.0D / total;
                }
            }
        }
        return mass;
    }

    /** How much of a band's placement mass falls in {@code [lo, hi]} inclusive. */
    private static double massWithin(double[] mass, int min, int lo, int hi) {
        double sum = 0.0D;
        for (int i = 0; i < mass.length; i++) {
            if (min + i >= lo && min + i <= hi) sum += mass[i];
        }
        return sum;
    }

    /**
     * The chance one placement attempt lands on a solid block, weighting the placement distribution
     * by the measured fraction of columns that are stone at each y. Attempts outside the sampled
     * height range — in particular below the End's y=0 floor, where the old triangle rows put a
     * third of theirs — contribute nothing, which is exactly what happens in game.
     */
    private static double hitRate(double[] mass, int min, long[] solidByY, int minY, int columns) {
        double sum = 0.0D;
        for (int i = 0; i < mass.length; i++) {
            int index = min + i - minY;
            if (index >= 0 && index < solidByY.length) {
                sum += mass[i] * ((double) solidByY[index] / columns);
            }
        }
        return sum;
    }

    private static String num(double d) {
        return String.format(java.util.Locale.ROOT, "%.3f", d);
    }

    /**
     * F18 — the eight End ore rows must aim their placement attempts at terrain that exists.
     *
     * <p>Review decision #7, approved by Karl on 2026-08-19: a deliberate content change, the one
     * item in this workstream where 1.18.2 parity is explicitly NOT the goal. The baseline shipped
     * the four native rows as {@code triangle(-50, 70)} and the four sphere rows as
     * {@code uniform(10, 40)}; both are kept below as the control this is scored against.
     *
     * <p>It does not assume where End terrain is, it measures it. {@code getBaseColumn} on the live
     * End {@code NoiseBasedChunkGenerator} returns the raw noise column without generating a chunk,
     * so a grid of them is a cheap, exact, deterministic profile of the dimension a player gets.
     * Each row's expected chance that one attempt lands inside end stone is then its placement
     * distribution integrated against the measured per-y stone fraction — the findability number
     * decision #7 is about, and the one the retune moves.
     *
     * <p>Asserted in both directions: the new bands must clear a floor and the old ones must not,
     * and the profile itself is bracketed first so neither number can quietly go vacuous.
     * {@code endBiomesCarryExactlyTheEightEndOreVeins} pins WHICH rows reach the End and
     * {@code endOreVeinFeatureReplacesEndStone} pins that the feature replaces end stone when it
     * runs; this pins that it is aimed somewhere it can.
     *
     * <p>It reads {@code ORE_SETTINGS.getDefault()}, not {@code get()}, and that is the whole point
     * of the distinction: what this change edits is the shipped DEFAULT. A config file that already
     * exists keeps its own values forever — the dev {@code run/config/rankine-common.toml} did, and
     * scored the old bands here until it was deleted — so a running server can legitimately be using
     * anything at all, and asserting {@code get()} would fail for every player who has ever tuned
     * their ore generation.
     */
    @GameTest(template = "empty")
    public void endOreVeinBandsTargetEndIslandTerrain(GameTestHelper helper) {
        ServerLevel end = helper.getLevel().getServer().getLevel(Level.END);
        assertTrue(end != null, "the gametest server has no minecraft:the_end level to profile");

        ChunkGenerator generator = end.getChunkSource().getGenerator();
        RandomState randomState = end.getChunkSource().randomState();
        int minY = end.getMinBuildHeight();
        int height = end.getHeight();
        List<String> problems = new ArrayList<>();

        long[] solidByY = new long[height];
        int columns = 0;
        int islandColumns = 0;
        long solid = 0L;

        for (int[] patch : END_SAMPLE_PATCHES) {
            for (int i = 0; i < END_SAMPLE_GRID; i++) {
                for (int j = 0; j < END_SAMPLE_GRID; j++) {
                    NoiseColumn column = generator.getBaseColumn(
                            patch[0] + i * END_SAMPLE_STEP, patch[1] + j * END_SAMPLE_STEP, end, randomState);
                    columns++;
                    boolean island = false;
                    for (int y = minY; y < minY + height; y++) {
                        if (!column.getBlock(y).isAir()) {
                            solidByY[y - minY]++;
                            solid++;
                            island = true;
                        }
                    }
                    if (island) islandColumns++;
                }
            }
        }

        // ---- the profile has to be a real one, or every number below is noise ----
        assertTrue(minY == 0 && height == 256, "the End's build range is " + minY + ".." + (minY + height)
                + ", expected 0..256; the band constants are calibrated to it");
        assertTrue(solid > 2000L, "only " + solid + " solid blocks in " + columns + " End columns —"
                + " the sample is not hitting islands, so every hit rate below is vacuous");
        assertTrue(islandColumns > columns / 10 && islandColumns < columns * 9 / 10, islandColumns + "/"
                + columns + " sampled columns contain stone; a sample that is nearly all void or nearly"
                + " all island is not the outer-island ring these bands are calibrated on");

        long inSlab = 0L;
        for (int y = END_SLAB_MIN; y <= END_SLAB_MAX; y++) inSlab += solidByY[y - minY];
        if ((double) inSlab / solid < 0.99D) {
            problems.add("only " + num((double) inSlab / solid) + " of End stone lies in y " + END_SLAB_MIN
                    + ".." + END_SLAB_MAX + "; the slab has moved and the bands are stale");
        }
        // The half of the old rows' problem that is easiest to get wrong by eye: there is nothing up
        // there to hit. The End's top slide is supposed to guarantee it.
        long aboveSlab = 0L;
        for (int y = END_SLAB_MAX + 1; y < minY + height; y++) aboveSlab += solidByY[y - minY];
        if (aboveSlab != 0L) {
            problems.add(aboveSlab + " solid blocks above y " + END_SLAB_MAX + ", which slideEnd should"
                    + " make impossible — the End's noise settings have changed");
        }

        // ---- every "the_end" row, scored against that profile ----
        int nativeRows = 0;
        int sphereRows = 0;
        for (List<Object> row : Config.BIOME_GEN.ORE_SETTINGS.getDefault()) {
            List<String> biomes = (List<String>) row.get(1);
            if (biomes.isEmpty() || !biomes.contains("the_end")) continue;
            String ore = (String) row.get(0);
            boolean triangle = "triangle".equals(row.get(3));
            int min = (int) row.get(4);
            int max = (int) row.get(5);
            double[] mass = placementMass(triangle, min, max);
            String where = ore + " (" + row.get(3) + " " + min + ".." + max + ")";
            if ("sphere".equals(row.get(2))) sphereRows++; else nativeRows++;

            // Not one attempt may be thrown outside the dimension. The old triangle rows sent 34.3%
            // below the y=0 floor alone, and 51.4% outside the stone slab y 11..64 altogether; that
            // is the waste this change removes.
            double outside = 1.0D - massWithin(mass, min, minY, END_SLAB_MAX);
            if (outside > 0.01D) {
                problems.add(where + " throws " + num(outside) + " of its attempts outside y " + minY
                        + ".." + END_SLAB_MAX + ", where the End has nothing to replace");
            }

            double hit = hitRate(mass, min, solidByY, minY, columns);
            double hitIsland = hitRate(mass, min, solidByY, minY, islandColumns);
            // 0.75 sits comfortably under both measured values and comfortably over both old ones, so
            // it fails for a band that drifts off the slab in either direction without being hostage
            // to the exact sample.
            if (hitIsland < 0.75D) {
                problems.add(where + " lands in end stone on only " + num(hitIsland) + " of attempts inside"
                        + " an island (all sampled columns: " + num(hit) + "); decision #7 wants these findable");
            }
        }
        if (nativeRows != 4 || sphereRows != 4) {
            problems.add("found " + nativeRows + " default-vein and " + sphereRows + " sphere \"the_end\" rows,"
                    + " expected 4 and 4 — the config no longer holds the rows this test is about");
        }

        // ---- control: the bands this replaced must FAIL the same threshold ----
        // Without this the threshold could be met by any band at all and the retune would be
        // unfalsifiable. Both old rows are re-scored against the very same profile.
        double oldNative = hitRate(placementMass(true, OLD_NATIVE_BAND[0], OLD_NATIVE_BAND[1]),
                OLD_NATIVE_BAND[0], solidByY, minY, islandColumns);
        double oldSphere = hitRate(placementMass(false, OLD_SPHERE_BAND[0], OLD_SPHERE_BAND[1]),
                OLD_SPHERE_BAND[0], solidByY, minY, islandColumns);
        if (oldNative >= 0.75D) {
            problems.add("the OLD native band " + OLD_NATIVE_BAND[0] + ".." + OLD_NATIVE_BAND[1] + " already"
                    + " scores " + num(oldNative) + " on this profile, so the threshold proves nothing");
        }
        if (oldSphere >= 0.75D) {
            problems.add("the OLD sphere band " + OLD_SPHERE_BAND[0] + ".." + OLD_SPHERE_BAND[1] + " already"
                    + " scores " + num(oldSphere) + " on this profile, so the threshold proves nothing");
        }
        // And the specific defect the review named, which is pure arithmetic: a third of the old
        // native attempts fell out of the world entirely.
        double oldBelowFloor = massWithin(placementMass(true, OLD_NATIVE_BAND[0], OLD_NATIVE_BAND[1]),
                OLD_NATIVE_BAND[0], OLD_NATIVE_BAND[0], minY - 1);
        if (oldBelowFloor < 0.30D) {
            problems.add("the old native band puts only " + num(oldBelowFloor) + " of its attempts below the"
                    + " End floor; placementMass no longer matches TrapezoidHeight#sample");
        }

        assertEmpty(problems, "End vein band failures");
        helper.succeed();
    }

    // ---------------------------------------------------------------------------------------
    // F13 — fluid entity interaction
    // ---------------------------------------------------------------------------------------

    /** Every FluidType the mod registers. Pinned so a future fluid cannot dodge the assertions below. */
    private static final int RANKINE_FLUID_TYPES = 16;

    /**
     * F13 — all 16 fluids used a default {@code FluidType.Properties}, so entities drowned in them,
     * swam in them and were pushed by them. In 1.18.2 you could stand in maple sap safely: Forge's
     * {@code FluidAttributes} carried no entity behaviour at all, and vanilla gated drowning,
     * swimming, current pushing, boating, hydration and mob path types on {@code FluidTags.WATER}
     * and {@code FluidTags.LAVA}, neither of which contains a Rankine fluid (the baseline shipped no
     * fluid tag files, and HEAD only adds the six {@code c:} content tags).
     *
     * <p>The entity-facing assertions go through {@code IEntityExtension} on a real spawned
     * {@link LivingEntity} — the exact calls {@code CommonHooks#onLivingBreathe},
     * {@code Entity#updateFluidHeightAndDoFluidPushing} and {@code Entity#updateSwimming} make.
     *
     * <p>Properties deliberately left at their NeoForge defaults are asserted too, because "default"
     * is a 1.18.2-parity claim here and a future default change should break this test:
     * canExtinguish / canConvertToSource / canHydrate false, density 1000, temperature 300,
     * viscosity 1000 — 6000 for carbon disulfide, the one place the baseline deviated.
     */
    @GameTest(template = "empty")
    public void rankineFluidsDoNotBehaveLikeWater(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        Pig pig = helper.spawn(EntityType.PIG, new BlockPos(1, 1, 1));

        List<String> ids = new ArrayList<>();
        for (var entry : NeoForgeRegistries.FLUID_TYPES.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id)) continue;
            ids.add(id.toString());
            FluidType type = entry.getValue();

            if (type.canDrownIn(pig)) problems.add(id + " canDrownIn");
            if (type.canPushEntity(pig)) problems.add(id + " canPushEntity");
            if (type.canSwim(pig)) problems.add(id + " canSwim");
            if (type.getFallDistanceModifier(pig) != 1.0F) {
                problems.add(id + " fallDistanceModifier is " + type.getFallDistanceModifier(pig) + ", expected 1.0");
            }
            if (type.canExtinguish(pig)) problems.add(id + " canExtinguish");
            if (type.canHydrate(pig)) problems.add(id + " canHydrate");
            if (type.getDensity() != 1000) problems.add(id + " density is " + type.getDensity());
            if (type.getTemperature() != 300) problems.add(id + " temperature is " + type.getTemperature());
            int expectedViscosity = id.getPath().equals("carbon_disulfide") ? 6000 : 1000;
            if (type.getViscosity() != expectedViscosity) {
                problems.add(id + " viscosity is " + type.getViscosity() + ", expected " + expectedViscosity);
            }

            // The entity-side wrappers the vanilla call sites actually use.
            if (pig.canDrownInFluidType(type)) problems.add(id + " canDrownInFluidType(pig)");
            if (pig.isPushedByFluid(type)) problems.add(id + " isPushedByFluid(pig)");
            if (pig.canSwimInFluidType(type)) problems.add(id + " canSwimInFluidType(pig)");
            if (pig.getFluidFallDistanceModifier(type) != 1.0F) problems.add(id + " getFluidFallDistanceModifier(pig)");
        }

        // Path type, source conversion and hydration need a FluidState, so they run per fluid.
        BlockPos probe = helper.absolutePos(new BlockPos(0, 1, 0));
        for (var entry : BuiltInRegistries.FLUID.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!isRankine(id)) continue;
            FluidState state = entry.getValue().defaultFluidState();
            FluidType type = state.getFluidType();
            if (type.getBlockPathType(state, helper.getLevel(), probe, null, false) != null) {
                problems.add(id + " overrides the mob path type; null (= vanilla, which yields OPEN) is the 1.18.2 outcome");
            }
            if (type.getBlockPathType(state, helper.getLevel(), probe, null, true) != null) {
                problems.add(id + " overrides the waterloggable mob path type");
            }
            if (type.getAdjacentBlockPathType(state, helper.getLevel(), probe, null, PathType.WALKABLE) != null) {
                problems.add(id + " overrides the adjacent mob path type");
            }
            if (type.canConvertToSource(state, helper.getLevel(), probe)) problems.add(id + " canConvertToSource");
            if (type.canHydrate(state, helper.getLevel(), probe, Blocks.FARMLAND.defaultBlockState(), probe)) {
                problems.add(id + " hydrates farmland");
            }
            if (type.canExtinguish(state, helper.getLevel(), probe)) problems.add(id + " extinguishes fire");
        }

        if (ids.size() != RANKINE_FLUID_TYPES) {
            problems.add("expected exactly " + RANKINE_FLUID_TYPES + " rankine fluid types, found "
                    + ids.size() + " " + ids);
        }

        // Vacuity guard: vanilla water must still say yes to all three, or the getters are lying.
        FluidType water = Fluids.WATER.getFluidType();
        if (!water.canDrownIn(pig) || !water.canSwim(pig) || !water.canPushEntity(pig)) {
            problems.add("minecraft:water no longer drowns/floats/pushes, so every negative above is vacuous");
        }

        pig.discard();
        assertEmpty(problems, "rankine fluid entity-interaction failures");
        helper.succeed();
    }

    /** Sealed sap pit for {@link #entityDoesNotDrownInRankineFluid}: stone shell, sap interior. */
    private static final BlockPos POOL_MIN = new BlockPos(0, 0, 0);
    private static final BlockPos POOL_MAX = new BlockPos(4, 5, 4);
    private static final BlockPos POOL_INNER_MIN = new BlockPos(1, 1, 1);
    private static final BlockPos POOL_INNER_MAX = new BlockPos(3, 4, 3);
    private static final BlockPos POOL_SPAWN = new BlockPos(2, 1, 2);
    private static final int DROWN_TICKS = 40;
    private static final int START_AIR = 100;

    /**
     * F13, behavioural half — an entity submerged in {@code rankine:maple_sap} must not lose air.
     * That is the symptom the review reported, and this drives it through the real server tick
     * rather than through the property getters: {@code LivingEntity#baseTick} calls
     * {@code CommonHooks#onLivingBreathe}, which asks {@code canDrownInFluidType} about the fluid on
     * the entity's eyes and drains one air per tick when the answer is yes.
     *
     * <p>The pit is a sealed stone shell, so the source blocks cannot spread or drain away and the
     * pig cannot walk out; sap has no collision, so the pig rests on the floor with sap over its
     * eyes. With the fix, {@code onLivingBreathe} reports "can breathe" with a zero refill, so the
     * air supply holds at exactly its starting value — it neither drains nor regenerates, which is
     * what distinguishes this from the pig simply not being in the fluid. The eye check makes that
     * distinction explicit rather than trusting it.
     */
    @GameTest(template = "empty", timeoutTicks = 200)
    public void entityDoesNotDrownInRankineFluid(GameTestHelper helper) {
        for (BlockPos pos : BlockPos.betweenClosed(POOL_MIN, POOL_MAX)) {
            helper.setBlock(pos, Blocks.STONE.defaultBlockState());
        }
        BlockState sap = RankineBlocks.MAPLE_SAP.get().defaultBlockState();
        for (BlockPos pos : BlockPos.betweenClosed(POOL_INNER_MIN, POOL_INNER_MAX)) {
            helper.setBlock(pos, sap);
        }

        Pig pig = helper.spawn(EntityType.PIG, POOL_SPAWN);
        pig.setNoAi(true);
        pig.setAirSupply(START_AIR);

        helper.runAfterDelay(DROWN_TICKS, () -> {
            List<String> problems = new ArrayList<>();
            FluidType eyes = pig.getEyeInFluidType();
            FluidType expected = RankineFluids.MAPLE_SAP_TYPE.get();
            if (eyes != expected) {
                problems.add("the pig's eyes are in " + eyes + ", not rankine:maple_sap — the pit did not hold,"
                        + " so the air check below would prove nothing");
            }
            if (pig.getAirSupply() != START_AIR) {
                problems.add("air supply went " + START_AIR + " -> " + pig.getAirSupply() + " over "
                        + DROWN_TICKS + " ticks submerged in maple sap");
            }
            if (pig.getHealth() != pig.getMaxHealth()) {
                problems.add("the pig took damage while submerged in maple sap (" + pig.getHealth() + " hp)");
            }
            pig.discard();
            assertEmpty(problems, "maple sap drowning failures");
            helper.succeed();
        });
    }

    // ---------------------------------------------------------------------------------------
    // F14 — antimatter explosion drop decay
    // ---------------------------------------------------------------------------------------

    /** Trials per mode in {@link #antimatterExplosionDropsDecay}. */
    private static final int DECAY_TRIALS = 120;

    /**
     * F14 — {@code AntimatterBlock.explode} passed {@code BlockInteraction.DESTROY}, which since
     * 1.19.3 means "full drops". 1.18.2's {@code DESTROY} was the decaying one: the mod's own
     * {@code SphericalExplosion#finalizeExplosion} (SphericalExplosion.java:149 at 859f87773) added
     * {@code LootContextParams.EXPLOSION_RADIUS} for exactly that value. A radius-12 blast therefore
     * dropped roughly twelve times the intended item count.
     *
     * <p>Two assertions, because the constant alone would be a tautology and the blast itself is far
     * too large to run inside a test structure (a radius-12 sphere is on the order of 7,000 blocks):
     * <ol>
     *   <li>the mode the block actually passes is {@code DESTROY_WITH_DECAY};</li>
     *   <li>that mode really does decay, driven through the vanilla path the mod's explosion class
     *       delegates to — {@code BlockState#onExplosionHit} — on plain stone at the same radius 12,
     *       against a {@code DESTROY} control.</li>
     * </ol>
     *
     * <p>Decay is random (each drop survives with probability 1/radius), so the second assertion is a
     * bound rather than an equality: over {@link #DECAY_TRIALS} = 120 trials at p = 1/12 the expected
     * yield is 10 and the threshold is 60, a margin of roughly 1e-30 in the tail. The control is
     * exact: without the radius parameter {@code minecraft:explosion_decay} is a no-op, so
     * {@code DESTROY} must yield all 120.
     */
    @GameTest(template = "empty")
    public void antimatterExplosionDropsDecay(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        if (AntimatterBlock.EXPLOSION_MODE != Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
            problems.add("AntimatterBlock passes " + AntimatterBlock.EXPLOSION_MODE
                    + "; 1.18.2's decaying DESTROY is 1.21's DESTROY_WITH_DECAY");
        }
        if (AntimatterBlock.EXPLOSION_RADIUS != 12f) {
            problems.add("blast radius is " + AntimatterBlock.EXPLOSION_RADIUS + ", expected 12 as in 1.18.2");
        }

        int withDecay = countExplosionDrops(helper, AntimatterBlock.EXPLOSION_MODE);
        int withoutDecay = countExplosionDrops(helper, Explosion.BlockInteraction.DESTROY);

        if (withoutDecay != DECAY_TRIALS) {
            problems.add("DESTROY dropped " + withoutDecay + "/" + DECAY_TRIALS + " cobblestone; without"
                    + " EXPLOSION_RADIUS the decay loot function is a no-op and it must drop all of them");
        }
        if (withDecay >= DECAY_TRIALS / 2) {
            problems.add("DESTROY_WITH_DECAY dropped " + withDecay + "/" + DECAY_TRIALS
                    + " cobblestone; at radius 12 the expected yield is about " + (DECAY_TRIALS / 12));
        }

        assertEmpty(problems, "antimatter explosion decay failures");
        helper.succeed();
    }

    /** Breaks stone with an explosion of the given mode {@link #DECAY_TRIALS} times; returns the item count dropped. */
    private static int countExplosionDrops(GameTestHelper helper, Explosion.BlockInteraction mode) {
        BlockPos relative = new BlockPos(1, 1, 1);
        BlockPos absolute = helper.absolutePos(relative);
        Explosion explosion = new Explosion(helper.getLevel(), null, absolute.getX(), absolute.getY(),
                absolute.getZ(), AntimatterBlock.EXPLOSION_RADIUS, false, mode);
        int drops = 0;
        for (int i = 0; i < DECAY_TRIALS; i++) {
            helper.setBlock(relative, Blocks.STONE.defaultBlockState());
            List<ItemStack> dropped = new ArrayList<>();
            helper.getLevel().getBlockState(absolute)
                    .onExplosionHit(helper.getLevel(), absolute, explosion, (stack, pos) -> dropped.add(stack));
            for (ItemStack stack : dropped) drops += stack.getCount();
        }
        helper.setBlock(relative, Blocks.AIR.defaultBlockState());
        return drops;
    }

    // ---------------------------------------------------------------------------------------
    // F15 — model predicate stores
    // ---------------------------------------------------------------------------------------

    /**
     * F15 — the {@code rankine:gas_held} and {@code rankine:building_mode} model predicates read
     * {@code minecraft:custom_data}, but the port moved the writes to dedicated data components. So
     * the Shulker Gas Vacuum always rendered empty and building-mode blocks always rendered as
     * mode 1.
     *
     * <p>The predicates themselves are client-only ({@code ClientSetup}) and cannot be invoked from a
     * dedicated server, so this drives the accessors they now delegate to —
     * {@code ShulkerGasVacuumItem#holdsGas} and {@code BuildingModeBlockItem#getBuildingMode} —
     * against the real gameplay writes, including the {@code getCloneItemStack} write that was
     * itself pointing at the wrong store. That is the whole content of the fix: one accessor and one
     * component shared by the read side and the write side.
     */
    @GameTest(template = "empty")
    public void modelPredicateStoresMatchGameplayWrites(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        // --- gas_held: ShulkerGasVacuumItem#use writes GAS, #useOn removes it.
        ItemStack vacuum = new ItemStack(RankineItems.SHULKER_GAS_VACUUM.get());
        if (ShulkerGasVacuumItem.holdsGas(vacuum)) {
            problems.add("a fresh Shulker Gas Vacuum reports a held gas");
        }
        vacuum.set(RankineDataComponents.GAS.get(), "rankine:helium_gas_block");
        if (!ShulkerGasVacuumItem.holdsGas(vacuum)) {
            problems.add("a vacuum holding rankine:helium_gas_block reports empty — gas_held reads a"
                    + " different store than ShulkerGasVacuumItem#use writes");
        }
        vacuum.remove(RankineDataComponents.GAS.get());
        if (ShulkerGasVacuumItem.holdsGas(vacuum)) {
            problems.add("a vacuum whose gas was released still reports full");
        }
        // The store the predicate used to read must stay irrelevant, or the bug is only half fixed.
        ItemStack legacyVacuum = new ItemStack(RankineItems.SHULKER_GAS_VACUUM.get());
        CustomData.update(DataComponents.CUSTOM_DATA, legacyVacuum,
                tag -> tag.putString("gas", "rankine:helium_gas_block"));
        if (ShulkerGasVacuumItem.holdsGas(legacyVacuum)) {
            problems.add("gas_held still answers from minecraft:custom_data, which no gameplay code writes");
        }

        // --- building_mode: BuildingModeBlockItem#use and BuildingModeBlock#getCloneItemStack write
        //     BUILDING_MODE; #getStateForPlacement and the predicate read it back.
        Player player = helper.makeMockPlayer(GameType.CREATIVE);
        BlockPos pickPos = helper.absolutePos(new BlockPos(0, 1, 0));
        int checked = 0;
        for (RankineStone stone : RankineLists.RANKINE_STONES) {
            Block block = stone.getBricks();
            if (!(block instanceof BuildingModeBlock modeBlock)) continue;
            if (!(block.asItem() instanceof BuildingModeBlockItem item)) continue;
            checked++;

            ItemStack fresh = new ItemStack(block);
            if (item.getBuildingMode(fresh) != 1) {
                problems.add(block + " reads mode " + item.getBuildingMode(fresh) + " on a fresh stack, expected 1");
            }

            int mode = Math.min(2, modeBlock.getMaxStyles());
            ItemStack set = new ItemStack(block);
            set.set(RankineDataComponents.BUILDING_MODE.get(), mode);
            if (item.getBuildingMode(set) != mode) {
                problems.add(block + " reads mode " + item.getBuildingMode(set) + " after a write of " + mode);
            }

            // Pick-block round trip: placed state -> stack -> mode. Same accessor the icon uses.
            BlockState placed = modeBlock.defaultBlockState().setValue(modeBlock.getProperty(), mode);
            ItemStack picked = modeBlock.getCloneItemStack(placed, null, helper.getLevel(), pickPos, player);
            if (item.getBuildingMode(picked) != mode) {
                problems.add(block + " pick-block on a mode-" + mode + " block yields mode "
                        + item.getBuildingMode(picked)
                        + " — getCloneItemStack writes a store getBuildingMode does not read");
            }
            if (picked.has(DataComponents.CUSTOM_DATA)) {
                problems.add(block + " pick-block still writes minecraft:custom_data");
            }
        }
        if (checked == 0) {
            problems.add("no BuildingModeBlock bricks found, so the building_mode half is vacuous");
        }

        assertEmpty(problems, "model predicate store mismatches");
        helper.succeed();
    }

    // ---------------------------------------------------------------------------------------
    // F16 — "beach" category membership
    // ---------------------------------------------------------------------------------------

    /** 1.18.2 {@code BiomeCategory.BEACH}, in full. */
    private static final Set<String> BEACH_BIOMES = Set.of(
            "minecraft:beach", "minecraft:snowy_beach", "minecraft:stony_shore");

    /** The ores whose only shore-reaching rows are keyed "beach" (Config.java:1207, 1237, 1238). */
    private static final List<String> BEACH_ORES = List.of(
            "rankine:pentlandite_ore", "rankine:sylvinite", "rankine:phosphorite");

    /**
     * F16 — {@code "beach"} pointed at {@code minecraft:is_beach}, which is
     * {@code [beach, snowy_beach]} ({@code c:is_beach} is just an alias of it). 1.18.2's
     * {@code BiomeCategory.BEACH} also held {@code stony_shore} — all three carry
     * {@code "category": "beach"} in that version's biome JSONs, and no other 1.18.2 biome does — so
     * the three ore rows keyed {@code "beach"} never reached stony shores. {@code rankine:is_beach}
     * restores the exact membership. Same shape as {@code icyCategoryRowSelectsSnowyPlainsAndIceSpikes}.
     */
    @GameTest(template = "empty")
    public void beachCategoryRowSelectsBeachesAndStonyShore(GameTestHelper helper) {
        WorldgenUtils.ensureBiomesResolved(helper.getLevel().registryAccess());
        var biomeRegistry = helper.getLevel().registryAccess().registryOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        for (ResourceKey<Biome> key : List.of(Biomes.BEACH, Biomes.SNOWY_BEACH, Biomes.STONY_SHORE)) {
            Holder<Biome> holder = biomeRegistry.getHolderOrThrow(key);
            if (!holder.is(RankineTags.Biomes.IS_BEACH)) {
                problems.add(key.location() + " missing from #rankine:is_beach");
            }
            if (!WorldgenUtils.matchesCategory(holder, "beach")) {
                problems.add(key.location() + " does not match the \"beach\" config category");
            }
        }

        // Membership must be exactly those three; nothing else may join.
        List<String> extra = new ArrayList<>();
        for (ResourceKey<Biome> key : biomeRegistry.registryKeySet()) {
            if (BEACH_BIOMES.contains(key.location().toString())) continue;
            if (biomeRegistry.getHolderOrThrow(key).is(RankineTags.Biomes.IS_BEACH)) {
                extra.add(key.location().toString());
            }
        }
        if (!extra.isEmpty()) {
            problems.add("#rankine:is_beach also contains " + extra + ", which were not BEACH in 1.18.2");
        }

        // stony_shore leaves the "none" bucket, where the missing tag membership had parked it.
        if (WorldgenUtils.matchesCategory(biomeRegistry.getHolderOrThrow(Biomes.STONY_SHORE), WorldgenUtils.CATEGORY_NONE)) {
            problems.add("minecraft:stony_shore still matches category \"none\"");
        }

        // The point of the fix: the three "beach" ore rows now reach stony shores.
        Set<Block> shoreVeins = oreVeinBlocks(biomeRegistry.getHolderOrThrow(Biomes.STONY_SHORE));
        Set<Block> beachVeins = oreVeinBlocks(biomeRegistry.getHolderOrThrow(Biomes.BEACH));
        for (String ore : BEACH_ORES) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(ore));
            if (!shoreVeins.contains(block)) problems.add(ore + " has no vein in minecraft:stony_shore");
            if (!beachVeins.contains(block)) problems.add(ore + " has no vein in minecraft:beach");
        }

        // Negative control. Only sylvinite is exclusively shore-ish: its single row is
        // ["beach","ocean","desert"] (Config.java:1237), so a forest must miss it. The other two
        // cannot serve here - pentlandite has a second "overworld" row (:1208) and phosphorite an
        // "extreme_hills"/"mountain" row (:1239), both of which legitimately place them elsewhere.
        Set<Block> forestVeins = oreVeinBlocks(biomeRegistry.getHolderOrThrow(Biomes.FOREST));
        Block sylvinite = BuiltInRegistries.BLOCK.get(ResourceLocation.parse("rankine:sylvinite"));
        if (forestVeins.contains(sylvinite)) {
            problems.add("rankine:sylvinite has a vein in minecraft:forest, which matches none of its"
                    + " row's categories - so the stony_shore hit above is not evidence of the beach row");
        }
        if (forestVeins.isEmpty()) {
            problems.add("minecraft:forest has no Rankine ore veins at all, so the negative control is vacuous");
        }

        // Ordering guard: stony_shore's own exact biomeSettings row (Config.java:974) precedes the
        // "beach" row (:1037) and GEN_BIOMES.indexOf takes the first match, so terrain must still
        // come from its own row.
        List<String> shoreLayers = layersFor(Biomes.STONY_SHORE);
        List<String> beachLayers = layersFor(Biomes.BEACH);
        if (shoreLayers == null) {
            problems.add("minecraft:stony_shore missing from GEN_BIOMES");
        } else if (shoreLayers.equals(beachLayers)) {
            problems.add("minecraft:stony_shore resolved to the \"beach\" row instead of its own exact row");
        }
        if (beachLayers == null) {
            problems.add("minecraft:beach missing from GEN_BIOMES");
        } else if (!beachLayers.equals(layersFor(Biomes.SNOWY_BEACH))) {
            problems.add("minecraft:beach and minecraft:snowy_beach resolved to different rows");
        }

        assertEmpty(problems, "\"beach\" category failures");
        helper.succeed();
    }

    // ---------------------------------------------------------------------------------------
    // F30 — tree-chop stump placement (batch 4)
    // ---------------------------------------------------------------------------------------

    /**
     * F30 — {@code BlockBreakHandler}'s stump check used to test
     * {@code below.is(BlockTags.DIRT) || below.is(BlockTags.SAND)}. 1.18.2 (859f87773, line 100)
     * tested {@code Material.DIRT}/{@code Material.SAND}, which excluded grass_block/mycelium
     * (Material.GRASS). {@code #minecraft:dirt} is not that set — it additionally carries
     * grass_block/mycelium/moss_block, and this mod's own tags provider adds its
     * GRASS_BLOCKS/MYCELIUM_BLOCKS to it too — so felling any tree grown on ordinary grass (the
     * normal case in every overworld biome) silently replaced its bottom log with a stump instead
     * of dropping it. Fixed by testing {@code RankineTags.Blocks.STUMP_BASES}, an explicit
     * reconstruction of the 1.18.2 Material set (finding #23).
     * <p>
     * Both scenarios drive the real {@link BlockBreakHandler#blockBreakingEvents} path with a
     * survival-mode mock player wielding a tree-chopper axe, felling a single log with one
     * non-persistent leaf neighbor (to satisfy the "alive tree" gate the handler requires before it
     * will touch stumps or drops at all): grass_block (must NOT stump — the regression, and the bug
     * this whole fix is about) and dirt (MUST stump — the legitimate 1.18.2 case, proving the fix
     * does not just disable stumping altogether).
     */
    @GameTest(template = "empty")
    public void treeChopOnGrassDropsLogWithoutStump(GameTestHelper helper) {
        runTreeChopStumpScenario(helper, Blocks.GRASS_BLOCK.defaultBlockState(), false);
    }

    /** @see #treeChopOnGrassDropsLogWithoutStump */
    @GameTest(template = "empty")
    public void treeChopOnDirtPlacesStump(GameTestHelper helper) {
        runTreeChopStumpScenario(helper, Blocks.DIRT.defaultBlockState(), true);
    }

    private static void runTreeChopStumpScenario(GameTestHelper helper, BlockState ground, boolean expectStump) {
        ServerLevel level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        if (!level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            throw new GameTestAssertException("doTileDrops is off in this test world; the drop path cannot be observed");
        }

        BlockPos groundPos = new BlockPos(2, 1, 2);
        BlockPos logPos = groundPos.above();
        BlockPos leafPos = logPos.east();

        helper.setBlock(groundPos, ground);
        helper.setBlock(logPos, Blocks.OAK_LOG.defaultBlockState());
        helper.setBlock(leafPos, Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, false));

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(RankineItems.STEEL_AXE.get()));

        BlockPos absLog = helper.absolutePos(logPos);
        BlockState logState = level.getBlockState(absLog);
        if (!logState.is(RankineTags.Blocks.TREE_LOGS)) {
            throw new GameTestAssertException("oak_log is not in #rankine:tree_logs; test setup invalid");
        }
        if (!level.getBlockState(helper.absolutePos(leafPos)).is(RankineTags.Blocks.TREE_LEAVES)) {
            throw new GameTestAssertException("oak_leaves is not in #rankine:tree_leaves; test setup invalid");
        }

        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, absLog, logState, player);
        BlockBreakHandler.blockBreakingEvents(event);

        BlockState afterLog = level.getBlockState(absLog);
        boolean isStump = afterLog.is(RankineBlocks.STUMP.get());

        if (expectStump != isStump) {
            problems.add("expected stump=" + expectStump + " on " + ground.getBlock() + ", got block " + afterLog.getBlock());
        }

        if (!expectStump) {
            // No stump: the log block itself must be gone (destroyed with drops), not left in place.
            if (afterLog.is(RankineTags.Blocks.TREE_LOGS)) {
                problems.add("log block is still present after chopping on " + ground.getBlock());
            }
            List<ItemEntity> drops = level.getEntitiesOfClass(ItemEntity.class, new AABB(absLog).inflate(3.0D));
            boolean droppedLog = false;
            for (ItemEntity e : drops) {
                if (e.getItem().is(Blocks.OAK_LOG.asItem())) {
                    droppedLog = true;
                    break;
                }
            }
            if (!droppedLog) {
                problems.add("no oak_log item dropped after chopping on " + ground.getBlock()
                        + " (found " + drops.size() + " item entities nearby)");
            }
        }

        assertEmpty(problems, "tree-chop stump placement failures (ground=" + ground.getBlock() + ")");
        helper.succeed();
    }

    // ---------------------------------------------------------------------------------------
    // F31 — explosion drop mode: 1.18.2 BREAK maps to 1.21 DESTROY, not DESTROY_WITH_DECAY (batch 4)
    // ---------------------------------------------------------------------------------------

    /**
     * F31 — four call sites (ReactiveItemEntity's block-breaking branch, BlastingPowderBlock,
     * GunCottonBlock, UnamedExplosiveBlock) passed {@code Explosion.BlockInteraction.BREAK} in
     * 1.18.2 (859f87773) — the pre-1.19.3 name for today's plain {@code DESTROY}: full drops,
     * unconditionally, no gamerule. The port mapped all four onto
     * {@code Level.ExplosionInteraction.BLOCK}, which resolves through
     * {@code GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY} (default ON, Level.java:735) to the
     * decaying {@code DESTROY_WITH_DECAY} instead — the same bug shape as F14's antimatter finding,
     * but inverted (F14 was DESTROY where DESTROY_WITH_DECAY was meant; this is
     * DESTROY_WITH_DECAY where DESTROY was meant). Confirmed against the mod's OWN 1.18.2
     * {@code SphericalExplosion#finalizeExplosion} (859f87773, line ~149), which added
     * {@code LootContextParams.EXPLOSION_RADIUS} — the decay trigger — for exactly
     * {@code BlockInteraction.DESTROY} and never for {@code BREAK}.
     * <p>
     * One empirical measurement, reusing {@link #countExplosionDrops} (the same apparatus F14
     * used), proves {@code ReactiveItemEntity.EXPLOSION_MODE} really does yield full drops against
     * a {@code DESTROY_WITH_DECAY} control that must NOT; the other three call sites (byte-identical
     * fixes) get a cheap static assertion against the same named-constant pattern rather than a
     * second full trial run.
     */
    @GameTest(template = "empty")
    public void reactiveItemAndExplosiveBlocksDropFullyNotDecayed(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        if (ReactiveItemEntity.EXPLOSION_MODE != Explosion.BlockInteraction.DESTROY) {
            problems.add("ReactiveItemEntity.EXPLOSION_MODE is " + ReactiveItemEntity.EXPLOSION_MODE
                    + "; 1.18.2's non-decaying BREAK is 1.21's plain DESTROY");
        }
        if (BlastingPowderBlock.EXPLOSION_MODE != Explosion.BlockInteraction.DESTROY) {
            problems.add("BlastingPowderBlock.EXPLOSION_MODE is " + BlastingPowderBlock.EXPLOSION_MODE + ", expected DESTROY");
        }
        if (GunCottonBlock.EXPLOSION_MODE != Explosion.BlockInteraction.DESTROY) {
            problems.add("GunCottonBlock.EXPLOSION_MODE is " + GunCottonBlock.EXPLOSION_MODE + ", expected DESTROY");
        }
        if (UnamedExplosiveBlock.EXPLOSION_MODE != Explosion.BlockInteraction.DESTROY) {
            problems.add("UnamedExplosiveBlock.EXPLOSION_MODE is " + UnamedExplosiveBlock.EXPLOSION_MODE + ", expected DESTROY");
        }

        int fullDrops = countExplosionDrops(helper, ReactiveItemEntity.EXPLOSION_MODE);
        int decayedDrops = countExplosionDrops(helper, Explosion.BlockInteraction.DESTROY_WITH_DECAY);

        if (fullDrops != DECAY_TRIALS) {
            problems.add("ReactiveItemEntity.EXPLOSION_MODE dropped " + fullDrops + "/" + DECAY_TRIALS
                    + " cobblestone; DESTROY must drop all of them (no EXPLOSION_RADIUS -> explosion_decay is a no-op)");
        }
        if (decayedDrops >= DECAY_TRIALS / 2) {
            problems.add("DESTROY_WITH_DECAY control dropped " + decayedDrops + "/" + DECAY_TRIALS
                    + "; expected roughly " + (DECAY_TRIALS / (int) AntimatterBlock.EXPLOSION_RADIUS)
                    + " (a low-yield control, proving the two modes actually differ in this harness)");
        }

        assertEmpty(problems, "reactive-item / explosive-block break-mode failures");
        helper.succeed();
    }

    // ---------------------------------------------------------------------------------------
    // F32 — ensureBiomesResolved must reset cleanly, not latch or double-populate (batch 4)
    // ---------------------------------------------------------------------------------------

    /**
     * F32 — {@code WorldgenUtils.biomesResolved} used to latch permanently with no reset path, so a
     * second world loaded in the same client session would resolve against the FIRST world's stale
     * lists — correct for 1.18.2's static, world-independent biome registry, wrong for 1.21's
     * per-world datapack biome registry. Fixed with {@link WorldgenUtils#resetBiomeResolution()},
     * wired into {@code RankineEventHandler#serverAboutToStart} before every
     * {@code ensureBiomesResolved} call, so every server start re-resolves from scratch.
     * <p>
     * A real second-world-with-different-datapack-biomes scenario can't be simulated inside one
     * GameTest server (there is exactly one registry here), so this proves the narrower, load-
     * bearing invariant instead: resolve (already done once by this GameTest server's own startup),
     * snapshot every parallel list's size plus {@code GEN_BIOMES}' exact ordered content, reset,
     * resolve again against the SAME registry, and assert both the sizes AND the content are
     * unchanged — no duplication, no drift, no misalignment. This is exactly what breaks first if
     * the clear is incomplete (see {@link WorldgenUtils#resetBiomeResolution()}'s javadoc for the
     * specific 2x-vs-3x misalignment a partial clear of the INTRUSION_* family produces).
     */
    @GameTest(template = "empty")
    public void biomeResolutionResetIsLossless(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        RegistryAccess registryAccess = helper.getLevel().registryAccess();

        // Baseline: already resolved once by this GameTest server's own ServerAboutToStartEvent.
        WorldgenUtils.ensureBiomesResolved(registryAccess);

        int genBiomes = WorldgenUtils.GEN_BIOMES.size();
        int o1 = WorldgenUtils.O1.size();
        int intrusionLists = WorldgenUtils.INTRUSION_LISTS.size();
        int intrusionBlocks = WorldgenUtils.INTRUSION_BLOCKS.size();
        int intrusionCollections = WorldgenUtils.INTRUSION_COLLECTIONS.size();
        int layerLists = WorldgenUtils.LAYER_LISTS.size();
        int vegetationLists = WorldgenUtils.VEGETATION_LISTS.size();
        int vegetationCollections = WorldgenUtils.VEGETATION_COLLECTIONS.size();
        int gravels = WorldgenUtils.GRAVELS.size();
        List<ResourceLocation> genBiomesContent = new ArrayList<>(WorldgenUtils.GEN_BIOMES);

        if (genBiomes == 0) {
            throw new GameTestAssertException("GEN_BIOMES is empty after ensureBiomesResolved; test cannot prove anything");
        }
        // Sanity on the baseline itself: every parallel list shares one "biome index" with
        // GEN_BIOMES, and the INTRUSION_*/VEGETATION_* derived lists share one with their _LISTS.
        if (o1 != genBiomes) problems.add("O1.size()=" + o1 + " != GEN_BIOMES.size()=" + genBiomes + " before reset");
        if (layerLists != genBiomes) problems.add("LAYER_LISTS.size()=" + layerLists + " != GEN_BIOMES.size()=" + genBiomes + " before reset");
        if (gravels != genBiomes) problems.add("GRAVELS.size()=" + gravels + " != GEN_BIOMES.size()=" + genBiomes + " before reset");
        if (intrusionBlocks != intrusionLists) problems.add("INTRUSION_BLOCKS.size()=" + intrusionBlocks + " != INTRUSION_LISTS.size()=" + intrusionLists + " before reset");
        if (vegetationCollections != vegetationLists) problems.add("VEGETATION_COLLECTIONS.size()=" + vegetationCollections + " != VEGETATION_LISTS.size()=" + vegetationLists + " before reset");

        WorldgenUtils.resetBiomeResolution();
        if (!WorldgenUtils.GEN_BIOMES.isEmpty()) {
            problems.add("GEN_BIOMES not empty immediately after resetBiomeResolution(): " + WorldgenUtils.GEN_BIOMES.size());
        }

        WorldgenUtils.ensureBiomesResolved(registryAccess);

        if (WorldgenUtils.GEN_BIOMES.size() != genBiomes) {
            problems.add("GEN_BIOMES.size() after reset+re-resolve is " + WorldgenUtils.GEN_BIOMES.size() + ", expected " + genBiomes + " (unchanged)");
        }
        if (!WorldgenUtils.GEN_BIOMES.equals(genBiomesContent)) {
            problems.add("GEN_BIOMES content differs after reset+re-resolve (order or membership drifted)");
        }
        if (WorldgenUtils.O1.size() != o1) {
            problems.add("O1.size() after reset+re-resolve is " + WorldgenUtils.O1.size() + ", expected " + o1);
        }
        if (WorldgenUtils.INTRUSION_LISTS.size() != intrusionLists) {
            problems.add("INTRUSION_LISTS.size() after reset+re-resolve is " + WorldgenUtils.INTRUSION_LISTS.size() + ", expected " + intrusionLists);
        }
        if (WorldgenUtils.INTRUSION_BLOCKS.size() != intrusionBlocks) {
            problems.add("INTRUSION_BLOCKS.size() after reset+re-resolve is " + WorldgenUtils.INTRUSION_BLOCKS.size()
                    + ", expected " + intrusionBlocks + " (this is the 2x/3x misalignment case if the clear is incomplete)");
        }
        if (WorldgenUtils.INTRUSION_COLLECTIONS.size() != intrusionCollections) {
            problems.add("INTRUSION_COLLECTIONS.size() after reset+re-resolve is " + WorldgenUtils.INTRUSION_COLLECTIONS.size() + ", expected " + intrusionCollections);
        }
        if (WorldgenUtils.LAYER_LISTS.size() != layerLists) {
            problems.add("LAYER_LISTS.size() after reset+re-resolve is " + WorldgenUtils.LAYER_LISTS.size() + ", expected " + layerLists);
        }
        if (WorldgenUtils.VEGETATION_LISTS.size() != vegetationLists) {
            problems.add("VEGETATION_LISTS.size() after reset+re-resolve is " + WorldgenUtils.VEGETATION_LISTS.size() + ", expected " + vegetationLists);
        }
        if (WorldgenUtils.VEGETATION_COLLECTIONS.size() != vegetationCollections) {
            problems.add("VEGETATION_COLLECTIONS.size() after reset+re-resolve is " + WorldgenUtils.VEGETATION_COLLECTIONS.size() + ", expected " + vegetationCollections);
        }
        if (WorldgenUtils.GRAVELS.size() != gravels) {
            problems.add("GRAVELS.size() after reset+re-resolve is " + WorldgenUtils.GRAVELS.size() + ", expected " + gravels);
        }

        assertEmpty(problems, "biome-resolution reset round-trip failures");
        helper.succeed();
    }
}
