package com.cannolicatfish.rankine.blocks.groundtap;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GroundTapTile extends TileEntity implements ITickableTileEntity {
    private int proccessTime;

    public GroundTapTile() {
        super(RankineTileEntities.GROUND_TAP.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.proccessTime = nbt.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("ProcessTime", this.proccessTime);
        return compound;
    }

    public void tick() {
        World levelIn = this.getWorld();
        BlockState stateIn = levelIn.getBlockState(pos);

        if (!stateIn.get(GroundTapBlock.WATERLOGGED)) {
            this.proccessTime++;
            if (this.proccessTime > Config.MACHINES.GROUND_TAP_SPEED.get()) {
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();

                toCheck.add(pos);
                while (!toCheck.isEmpty()) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        for (Direction dir : Direction.values()) {
                            BlockState target = levelIn.getBlockState(cp.offset(dir).toImmutable());
                            if (target.matchesBlock(RankineBlocks.METAL_PIPE.get())) {
                                toCheck.add(cp.offset(dir).toImmutable());
                            } else if (target.matchesBlock(RankineBlocks.FLOOD_GATE.get()) && target.get(BlockStateProperties.WATERLOGGED)) {
                                levelIn.setBlockState(pos, stateIn.with(BlockStateProperties.WATERLOGGED, true));
                                levelIn.setBlockState(cp.offset(dir).toImmutable(), RankineBlocks.FLOOD_GATE.get().getDefaultState().with(BlockStateProperties.WATERLOGGED, false));
                                this.proccessTime = 0;
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            this.proccessTime = 0;
        }
    }


}
