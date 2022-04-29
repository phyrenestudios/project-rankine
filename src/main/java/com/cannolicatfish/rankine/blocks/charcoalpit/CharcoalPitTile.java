package com.cannolicatfish.rankine.blocks.charcoalpit;

import com.cannolicatfish.rankine.blocks.RankineEightLayerBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CharcoalPitTile extends TileEntity implements ITickableTileEntity {
    int MAX_HEIGHT = Config.MACHINES.CHARCOAL_PIT_HEIGHT.get();
    double RADIUS = Config.MACHINES.CHARCOAL_PIT_RADIUS.get()+0.5;
    int totalTime;
    int proccessTime = 0;

    public CharcoalPitTile() {
        super(RankineTileEntities.CHARCOAL_PIT.get());
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
        if (!world.isAreaLoaded(pos, 1) || !world.isRemote() || !this.getBlockState().get(CharcoalPitBlock.LIT) || world.getBlockState(pos.down()).matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) return;
        if (proccessTime == 0) {
            totalTime = MathHelper.nextInt(world.rand,(int) Math.round(0.8*Config.MACHINES.CHARCOAL_PIT_SPEED.get()),(int) Math.round(1.2*Config.MACHINES.CHARCOAL_PIT_SPEED.get()));
        }

        if (proccessTime >= totalTime) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            Set<BlockPos> validLogs = new HashSet<>();
            Set<BlockPos> invalidLogs = new HashSet<>();
            Set<BlockPos> toChar = new HashSet<>();

            toCheck.add(pos);
            validLogs.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (Direction dir : Direction.values()) {
                        if (cp.getY() - pos.getY() <= MAX_HEIGHT && cp.getY() - pos.getY() >= 0 && Math.pow(pos.getX() - cp.getX(), 2) + Math.pow(pos.getZ() - cp.getZ(), 2) <= RADIUS * RADIUS + 0.5) {
                            BlockState target = world.getBlockState(cp.offset(dir));
                            if (target.isIn(BlockTags.LOGS_THAT_BURN) || target.matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) {
                                boolean valid = true;
                                for (Direction dir2 : Direction.values()) {
                                    if (dir.getOpposite() == dir2) continue;
                                    if (!world.getBlockState(cp.offset(dir).offset(dir2)).isOpaqueCube(world, cp.offset(dir).offset(dir2))) {
                                        valid = false;
                                        invalidLogs.add(cp.offset(dir));
                                        break;
                                    }
                                }
                                if (valid) {
                                    toCheck.add(cp.offset(dir));
                                    validLogs.add(cp.offset(dir));
                                }
                            }
                        }
                    }

                }
            }


            int k = 0;
            while (world.getBlockState(pos.up(k)).matchesBlock(RankineBlocks.CHARCOAL_PIT.get()) && k <= MAX_HEIGHT) {
                k++;
            }

            for (int i = 0; i<=k; ++i) {
                for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-RADIUS,i,-RADIUS),pos.add(RADIUS,i,RADIUS))) {
                    if (validLogs.contains(b.toImmutable())) {
                        int layerCount = logLayerCount(world,world.getBlockState(b).getBlock());
                        if (layerCount == 0) continue;
                        int j = 1;
                        while (WorldgenUtils.isGasOrAir(world,b.down(j)) && j <= i) {
                            j++;
                        }
                        if (world.getBlockState(b.down(j)).matchesBlock(RankineBlocks.CHARCOAL_BLOCK.get())) {
                            int downState = world.getBlockState(b.down(j)).get(RankineEightLayerBlock.LAYERS);
                            if (downState + layerCount > 8) {
                                world.setBlockState(b.down(j), RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, 8),3);
                                world.setBlockState(b.down(j-1), RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, downState+layerCount-8),3);
                                if (j>1) {
                                    world.removeBlock(b,false);
                                }
                            } else {
                                world.setBlockState(b.down(j), RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, downState+layerCount),3);
                                world.removeBlock(b,false);
                            }
                        } else {
                            world.setBlockState(b, RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, layerCount),3);
                        }
                    } else if (invalidLogs.contains(b.toImmutable())) {
                        if (world.getRandom().nextFloat() < 0.2) {
                            world.setBlockState(b, RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get().getDefaultState(),3);
                        } else {
                            world.setBlockState(b, RankineBlocks.CHARRED_LOG.get().getDefaultState(),3);
                        }
                    }
                }
            }
        } else {
            proccessTime += 1;
        }
    }

    public static int logLayerCount(World worldIn, Block target) {
        if (VanillaIntegration.fuelValueMap.containsKey(target.asItem())) {
            int value = VanillaIntegration.fuelValueMap.get(target.asItem());
            return (int) Math.floor(MathHelper.clamp(value/100D -2.0D + (worldIn.rand.nextFloat() < (value%100)/100D ? 1 : 0),1,8));
        } else if (target.isIn(BlockTags.LOGS_THAT_BURN)) {
            return 2;
        } else if (target.matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) {
            return 2;
        }
        return 0;
    }

}
