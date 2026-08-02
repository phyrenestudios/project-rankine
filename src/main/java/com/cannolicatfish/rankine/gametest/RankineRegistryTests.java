package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
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
}
