package com.cannolicatfish.rankine.blocks.charcoalpit;

import com.cannolicatfish.rankine.blocks.RankineEightLayerBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.*;

import static com.cannolicatfish.rankine.init.RankineBlocks.CHARCOAL_PIT_TILE;

public class CharcoalPitTile extends TileEntity implements ITickableTileEntity {
    int MAX_HEIGHT = Config.MACHINES.CHARCOAL_PIT_HEIGHT.get();
    double RADIUS = Config.MACHINES.CHARCOAL_PIT_RADIUS.get()+0.5;
    int totalTime;
    int proccessTime = 0;

    public CharcoalPitTile() {
        super(CHARCOAL_PIT_TILE);
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
        if (!world.isAreaLoaded(pos, 1) || !this.getBlockState().get(CharcoalPitBlock.LIT)) return;
        if (proccessTime == 0) {
            totalTime = MathHelper.nextInt(world.rand,(int) Math.round(0.8*Config.MACHINES.CHARCOAL_PIT_SPEED.get()),(int) Math.round(1.2*Config.MACHINES.CHARCOAL_PIT_SPEED.get()));
        }

        if (proccessTime >= totalTime) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            Set<BlockPos> connected = new HashSet<>();

            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (Direction dir : Direction.values()) {
                        if (pos.getY() - cp.getY() <= MAX_HEIGHT && Math.pow(pos.getX() - cp.getX(), 2) + Math.pow(pos.getZ() - cp.getZ(), 2) <= RADIUS * RADIUS) {
                            BlockState target = world.getBlockState(cp.offset(dir));
                            if (target.isIn(BlockTags.LOGS_THAT_BURN) || target.matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) {
                                toCheck.add(cp.offset(dir));
                                connected.add(cp.offset(dir));
                            }
                        }
                    }

                }
            }


            for (BlockPos blockpos : connected) {
                List<BlockPos> Sides = Arrays.asList(blockpos.down(), blockpos.north(), blockpos.east(), blockpos.west(), blockpos.south(), blockpos.up());
                boolean valid = true;
                for (BlockPos Side : Sides) {
                    if (!world.getBlockState(Side).isOpaqueCube(world, Side) && !world.getBlockState(Side).matchesBlock(RankineBlocks.CHARCOAL_BLOCK.get())) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    Block target = world.getBlockState(blockpos).getBlock();
                    if (VanillaIntegration.fuelValueMap.containsKey(target.asItem())) {
                        int value = VanillaIntegration.fuelValueMap.get(target.asItem());

                        world.setBlockState(blockpos, RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, MathHelper.clamp(value/100f -2f + world.rand.nextFloat() < value%100 ? 1 : 0,1,8)));
                    } else if (target.isIn(BlockTags.LOGS_THAT_BURN)) {
                        world.setBlockState(blockpos, RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, 2));
                    } else if (target.matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) {
                        world.setBlockState(blockpos, RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, 2));

                    }
                }
            }
            proccessTime = 0;
        } else {
            proccessTime += 1;
        }
    }



}
