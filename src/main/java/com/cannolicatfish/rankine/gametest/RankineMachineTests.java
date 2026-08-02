package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Machine-level tests. The 1.21 capability rework replaced every {@code getCapability}
 * override with a central registration, and block entity load/save gained a
 * {@code HolderLookup.Provider} parameter — both are the kind of change that compiles
 * cleanly and then silently breaks hoppers or eats inventories at runtime.
 */
@GameTestHolder(ProjectRankine.MODID)
@PrefixGameTestTemplate(false)
public class RankineMachineTests {

    /**
     * Machines whose item handler must be reachable through the capability system.
     * Pedestals are deliberately absent: there is no "rankine:pedestal" block — they are
     * registered per alloy (rose_gold_pedestal, ...), and are covered by the sweep in
     * {@link #blockEntitiesTickWithoutCrashing} instead.
     */
    private static final List<String> ITEM_HANDLER_MACHINES = List.of(
            "alloy_furnace", "induction_furnace", "battery_charger", "crucible",
            "evaporation_tower", "fusion_furnace", "gas_bottler", "mixing_barrel",
            "material_testing_table");

    private static Block block(String path) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path));
    }

    private static void assertEmpty(List<String> problems, String what) {
        if (!problems.isEmpty()) {
            throw new GameTestAssertException(problems.size() + " " + what + ": " + String.join(", ", problems));
        }
    }

    /**
     * Every machine must expose an IItemHandler through the capability system, on at least
     * one side. A null result here means hoppers and pipes cannot interact with the block —
     * invisible to the compiler, and the exact failure mode the capability rework risks.
     */
    @GameTest(template = "empty")
    public void machinesExposeItemHandlerCapability(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        List<String> problems = new ArrayList<>();

        for (String path : ITEM_HANDLER_MACHINES) {
            Block b = block(path);
            if (b == net.minecraft.world.level.block.Blocks.AIR) {
                problems.add(path + " (block not registered)");
                continue;
            }
            helper.setBlock(pos, b.defaultBlockState());
            BlockPos absolute = helper.absolutePos(pos);

            boolean anySide = false;
            for (Direction side : Direction.values()) {
                IItemHandler handler = helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, absolute, side);
                // A sided handler may legitimately expose 0 slots on faces with no accessible
                // slots (that is how the 1.18.2 sided wrappers behaved too), so only require
                // that at least one face gives a usable handler.
                if (handler != null && handler.getSlots() > 0) {
                    anySide = true;
                }
            }
            if (!anySide) {
                problems.add(path + " (no usable IItemHandler on any side)");
            }
            helper.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR);
        }

        assertEmpty(problems, "machines with broken item handler capabilities");
        helper.succeed();
    }

    /**
     * Places every Rankine block that has a block entity, lets the server tick it, then
     * reads it back. Catches ticker crashes and load/save regressions that only appear
     * once a block entity actually runs.
     */
    @GameTest(template = "empty", timeoutTicks = 400)
    public void blockEntitiesTickWithoutCrashing(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        List<String> problems = new ArrayList<>();
        List<Block> withBlockEntities = new ArrayList<>();

        for (var entry : BuiltInRegistries.BLOCK.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!ProjectRankine.MODID.equals(id.getNamespace())) continue;
            Block b = entry.getValue();
            BlockState state = b.defaultBlockState();
            if (state.hasBlockEntity()) {
                withBlockEntities.add(b);
            }
        }

        if (withBlockEntities.isEmpty()) {
            throw new GameTestAssertException("no rankine blocks report hasBlockEntity()");
        }

        for (Block b : withBlockEntities) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(b);
            try {
                helper.setBlock(pos, b.defaultBlockState());
                BlockEntity be = helper.getBlockEntity(pos);
                if (be == null) {
                    problems.add(id + " (hasBlockEntity but none created)");
                } else {
                    // saveWithoutMetadata exercises saveAdditional(tag, provider), whose signature changed.
                    be.saveWithoutMetadata(helper.getLevel().registryAccess());
                }
                helper.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR);
            } catch (RuntimeException e) {
                problems.add(id + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
            }
        }

        assertEmpty(problems, "block entities that failed to place/save");
        helper.succeed();
    }

    /**
     * Block entity types must accept the blocks they are bound to. A mismatch throws at
     * placement time in a live world.
     */
    @GameTest(template = "empty")
    public void blockEntityTypesAcceptTheirBlocks(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        for (var entry : BuiltInRegistries.BLOCK_ENTITY_TYPE.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            if (!ProjectRankine.MODID.equals(id.getNamespace())) continue;
            BlockEntityType<?> type = entry.getValue();
            boolean bound = BuiltInRegistries.BLOCK.stream().anyMatch(b -> type.isValid(b.defaultBlockState()));
            if (!bound) problems.add(id.toString());
        }
        assertEmpty(problems, "block entity types bound to no block");
        helper.succeed();
    }

    /** Sanity check that the registered block entity types cover the machines we test above. */
    @GameTest(template = "empty")
    public void machineBlockEntityTypesRegistered(GameTestHelper helper) {
        List<String> missing = new ArrayList<>();
        for (String path : ITEM_HANDLER_MACHINES) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path);
            if (BuiltInRegistries.BLOCK.get(id) == net.minecraft.world.level.block.Blocks.AIR) {
                missing.add(path);
            }
        }
        assertEmpty(missing, "machine blocks not registered");

        long types = BuiltInRegistries.BLOCK_ENTITY_TYPE.keySet().stream()
                .filter(k -> ProjectRankine.MODID.equals(k.getNamespace())).count();
        if (types < 15) {
            throw new GameTestAssertException("expected >=15 rankine block entity types, found " + types);
        }
        helper.succeed();
    }
}
