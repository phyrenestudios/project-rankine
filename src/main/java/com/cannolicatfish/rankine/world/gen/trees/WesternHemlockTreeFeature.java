package com.cannolicatfish.rankine.world.gen.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.Random;

public class WesternHemlockTreeFeature extends Feature<TreeConfiguration> {

    public WesternHemlockTreeFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        TreeConfiguration config = p_159749_.config();
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

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

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
                int crownHeight = (int) Math.round(trunkHeight*0.5);
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

    private void hemlockBranch(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config, int crownHeight) {
        WorldgenUtils.placeLeafAt(reader,pos.above(2),rand,config);
        for (int i = 0; i < crownHeight; ++i) {
            if (i > 1) {
                if (i%2==0) {
                    WorldgenUtils.placeLogAt(reader,pos.below(i).north(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.below(i).north(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.below(i).south(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.below(i).south(), rand, config);
                    if (i > crownHeight * 0.50) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).north(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).north(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).south(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.below(i).south(2), rand, config);
                    }
                } else {
                    WorldgenUtils.placeLogAt(reader,pos.below(i).east(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.below(i).east(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.below(i).west(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.below(i).west(), rand, config);
                    if (i > crownHeight * 0.50) {
                        WorldgenUtils.placeLogAt(reader,pos.below(i).east(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).east(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.below(i).west(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.below(i).west(2), rand, config);
                    }
                }
            }
            hemlockLeaves(reader, pos, rand, config);
            hemlockLeaves(reader, pos.below(1), rand, config);
        }

    }

    private void hemlockLeaves(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config) {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN)) continue;
            WorldgenUtils.placeLeafAt(reader,pos.relative(dir),rand,config);
        }
    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.WESTERN_HEMLOCK.getSapling());
    }
}
