package com.cannolicatfish.rankine.blocks.groundtap;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.GROUND_TAP_TILE;

public class GroundTapTile extends BlockEntity {
    private int proccessTime;

    public GroundTapTile(BlockPos posIn, BlockState stateIn) {
        super(GROUND_TAP_TILE, posIn, stateIn);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("ProcessTime", this.proccessTime);
        return compound;
    }

    public void tick() {

        if (!this.getBlockState().getValue(GroundTapBlock.WATERLOGGED)) {
            this.proccessTime++;
            if (this.proccessTime % Config.MACHINES.GROUND_TAP_SPEED.get() == 0) {
                Level worldIn = this.getLevel();
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();
                boolean attached = false;

                toCheck.add(worldPosition.below());
                while (!toCheck.isEmpty() && !attached) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        for (Direction dir : Direction.values()) {
                            BlockState target = worldIn.getBlockState(cp.relative(dir).immutable());
                            if (target.is(RankineBlocks.METAL_PIPE.get())) {
                                toCheck.add(cp.relative(dir).immutable());
                            } else if (target.is(RankineBlocks.FLOOD_GATE.get()) && cp.getY() <= WorldgenUtils.waterTableHeight(worldIn, worldPosition)) {
                                attached = true;
                            }
                        }
                    }
                }

                if (attached) {
                    worldIn.setBlock(worldPosition, this.getBlockState().setValue(GroundTapBlock.WATERLOGGED, true), 2);
                    proccessTime = 0;
                }

            }
        } else {
            proccessTime = 0;
        }
    }


}
