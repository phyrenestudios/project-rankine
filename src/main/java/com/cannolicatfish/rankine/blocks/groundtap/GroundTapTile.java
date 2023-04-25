package com.cannolicatfish.rankine.blocks.groundtap;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GroundTapTile extends BlockEntity {
    private int proccessTime;

    public GroundTapTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.GROUND_TAP.get(), posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("ProcessTime", this.proccessTime);
    }

    public static void tick(Level levelIn, BlockPos posIn, BlockState stateIn, GroundTapTile tile) {

        if (!stateIn.getValue(GroundTapBlock.WATERLOGGED)) {
            tile.proccessTime++;
            if (tile.proccessTime > Config.MACHINES.GROUND_TAP_SPEED.get()) {
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();

                toCheck.add(posIn);
                while (!toCheck.isEmpty()) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        for (Direction dir : Direction.values()) {
                            BlockState target = levelIn.getBlockState(cp.relative(dir).immutable());
                            if (target.is(RankineBlocks.METAL_PIPE.get())) {
                                toCheck.add(cp.relative(dir).immutable());
                            } else if (target.is(RankineBlocks.FLOOD_GATE.get()) && target.getValue(BlockStateProperties.WATERLOGGED)) {
                                levelIn.setBlockAndUpdate(posIn, stateIn.setValue(BlockStateProperties.WATERLOGGED, true));
                                levelIn.setBlockAndUpdate(cp.relative(dir).immutable(), RankineBlocks.FLOOD_GATE.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
                                tile.proccessTime = 0;
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            tile.proccessTime = 0;
        }
    }


}
