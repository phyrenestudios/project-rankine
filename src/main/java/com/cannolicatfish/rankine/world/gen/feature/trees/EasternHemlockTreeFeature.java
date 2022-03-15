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

import java.util.Random;

public class EasternHemlockTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public EasternHemlockTreeFeature(Codec<BaseTreeFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        int trunkHeight = config.trunkPlacer.getTreeHeight(rand);
        boolean flag = true;
        if (pos.getY() >= 1 && pos.getY() + trunkHeight + 1 <= reader.getMaxBuildHeight()) {
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
                        if (j >= 0 && j < reader.getMaxBuildHeight()) {
                            if (!WorldgenUtils.isAirOrLeaves(reader, blockpos$mutableblockpos.set(l, j, i1))) {
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
            } else if (isValidGround(reader, pos.below()) && pos.getY() < reader.getMaxBuildHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.below());
                int crownHeight = (int) Math.round(trunkHeight*0.6);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
                    if (i < trunkHeight-crownHeight) {
                        WorldgenUtils.checkLog(reader, pos.above(i).west(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.above(i).east(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.above(i).north(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.above(i).south(), rand, config, Direction.Axis.Y);
                    }

                }
                hemlockBranch(reader,pos.above(trunkHeight),rand,config,crownHeight+2);
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

    private void hemlockBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int crownHeight) {
        WorldgenUtils.placeLeafAt(reader,pos.above(2),rand,config);
        for (int i = 0; i < crownHeight; ++i) {
            if (i > 1) {
                if (i%2==0) {
                    WorldgenUtils.placeLogAt(reader,pos.below(i).north(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.below(i).north(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.below(i).south(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.below(i).south(), rand, config);
                    if (i > crownHeight * 0.35) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).north(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).north(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).south(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).south(2), rand, config);
                    }
                    if (i > crownHeight * 0.70) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).north(3),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).north(3), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).north(2).east(),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).north(2).east(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).north(2).west(),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).north(2).west(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).south(3),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).south(3), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).south(2).east(),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).south(2).east(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).south(2).west(),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).south(2).west(), rand, config);
                    }
                } else {
                    WorldgenUtils.placeLogAt(reader,pos.below(i).east(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.below(i).east(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.below(i).west(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.below(i).west(), rand, config);
                    if (i > crownHeight * 0.35) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).east(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).east(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).west(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).west(2), rand, config);
                    }
                    if (i > crownHeight * 0.70) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).east(3),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).east(3), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).east(2).north(),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).east(2).north(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).east(2).south(),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).east(2).south(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).west(3),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).west(3), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).west(2).north(),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).west(2).north(), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).west(2).south(),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).west(2).south(), rand, config);
                    }
                }
            }
            hemlockLeaves(reader, pos, rand, config);
            hemlockLeaves(reader, pos.below(1), rand, config);
        }

    }

    private void hemlockLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN)) continue;
            WorldgenUtils.placeLeafAt(reader,pos.relative(dir),rand,config);
        }
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.EASTERN_HEMLOCK_SAPLING.get());
    }
}
