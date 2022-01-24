package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BalsamFirTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public BalsamFirTreeFeature(Codec<BaseTreeFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        int trunkHeight = config.trunkPlacer.getHeight(rand);


        boolean flag = true;
        if (pos.getY() >= 1 && pos.getY() + trunkHeight + 1 <= reader.getHeight()) {
            for(int j = pos.getY(); j <= pos.getY() + 1 + trunkHeight; ++j) {
                int k = 1;
                if (j == pos.getY()) {
                    k = 0;
                }

                if (j >= pos.getY() + 1 + trunkHeight - 2) {
                    k = 2;
                }

                BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

                for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l) {
                    for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1) {
                        if (j >= 0 && j < reader.getHeight()) {
                            if (!WorldgenUtils.isAirOrLeaves(reader, blockpos$mutableblockpos.setPos(l, j, i1))) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.down()) && pos.getY() < reader.getHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.down());
                Direction dir = WorldgenUtils.randomHorizontalDirection(rand);
                int j = 0;
                boolean dead = true;
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.up(i).offset(dir,j), rand, config, Direction.Axis.Y);
                    if (i > i*0.3 && (trunkHeight - i) % 2 == 0) balsamBranch(reader,pos.up(i).offset(dir,j),rand,config,dead,i>trunkHeight-1);
                    if (i > trunkHeight/2f && (trunkHeight - i) % 2 == 0 && rand.nextFloat() < 0.3) {
                        j = 1;
                        if (WorldgenUtils.isAirOrLeaves(reader, pos.up(i).offset(dir,j))) {
                            WorldgenUtils.placeLogAt(reader, pos.up(i).offset(dir,j), rand, config, Direction.Axis.Y);
                            WorldgenUtils.placeLeafAt(reader, pos.up(i).offset(dir,j).offset(dir,j), rand, config);
                            WorldgenUtils.placeLeafAt(reader, pos.up(i).offset(dir,j).offset(dir.rotateY(),j), rand, config);
                            WorldgenUtils.placeLeafAt(reader, pos.up(i).offset(dir,j).offset(dir.rotateYCCW(),j), rand, config);
                        }
                    }
                    if (rand.nextFloat() > 0.2) dead = false;

                }
                WorldgenUtils.placeLeafAt(reader, pos.up(trunkHeight+1).offset(dir,j), rand, config);


                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private void balsamBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, boolean dead, boolean top) {
        List<BlockPos> leaves = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            if (rand.nextFloat() < 0.4 && !top) {
                if (WorldgenUtils.isAirOrLeaves(reader, pos.offset(dir))) {
                    WorldgenUtils.placeLogAt(reader,pos.offset(dir),rand,config,dir.getAxis());
                    if (!dead) {
                        leaves.add(pos.offset(dir).offset(dir));
                        leaves.add(pos.offset(dir).offset(dir.rotateY()));
                        leaves.add(pos.offset(dir).offset(dir.rotateYCCW()));
                    }
                }
            } else {
                if (!dead) {
                    leaves.add(pos.offset(dir));
                }
            }
        }
        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.BALSAM_FIR_SAPLING.get());
    }
}
