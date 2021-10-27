package com.cannolicatfish.rankine.blocks.groundtap;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.cannolicatfish.rankine.init.RankineBlocks.GROUND_TAP_TILE;

public class GroundTapTile extends TileEntity implements ITickableTileEntity {
    private int proccessTime;

    public GroundTapTile() {
        super(GROUND_TAP_TILE);
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

        if (!this.getBlockState().get(GroundTapBlock.WATERLOGGED)) {
            this.proccessTime++;
            if (this.proccessTime % Config.MACHINES.GROUND_TAP_SPEED.get() == 0) {
                World worldIn = this.getWorld();
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();
                boolean attached = false;

                toCheck.add(pos.down());
                while (!toCheck.isEmpty() && !attached) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        for (Direction dir : Direction.values()) {
                            BlockState target = worldIn.getBlockState(cp.offset(dir).toImmutable());
                            if (target.matchesBlock(RankineBlocks.METAL_PIPE.get())) {
                                toCheck.add(cp.offset(dir).toImmutable());
                            } else if (target.matchesBlock(RankineBlocks.FLOOD_GATE.get()) && cp.getY() <= WorldgenUtils.waterTableHeight(worldIn, pos)) {
                                attached = true;
                            }
                        }
                    }
                }

                if (attached) {
                    worldIn.setBlockState(pos, this.getBlockState().with(GroundTapBlock.WATERLOGGED, true), 2);
                    proccessTime = 0;
                }

            }
        } else {
            proccessTime = 0;
        }
    }


}
