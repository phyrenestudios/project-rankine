package com.cannolicatfish.rankine.world.gen.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BalsamFirTreeFeature extends Feature<TreeConfiguration> {

    public BalsamFirTreeFeature(Codec<TreeConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = reader.getRandom();
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
                Direction dir = WorldgenUtils.randomHorizontalDirection(rand);
                int j = 0;
                boolean dead = true;
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i).relative(dir,j), rand, config, Direction.Axis.Y);
                    if (i > i*0.3 && (trunkHeight - i) % 2 == 0) balsamBranch(reader,pos.above(i).relative(dir,j),rand,config,dead,i>trunkHeight-1);
                    if (i > trunkHeight/2f && (trunkHeight - i) % 2 == 0 && rand.nextFloat() < 0.3) {
                        j = 1;
                        if (WorldgenUtils.isAirOrLeaves(reader, pos.above(i).relative(dir,j))) {
                            WorldgenUtils.placeLogAt(reader, pos.above(i).relative(dir,j), rand, config, Direction.Axis.Y);
                            WorldgenUtils.placeLeafAt(reader, pos.above(i).relative(dir,j).relative(dir,j), rand, config);
                            WorldgenUtils.placeLeafAt(reader, pos.above(i).relative(dir,j).relative(dir.getClockWise(),j), rand, config);
                            WorldgenUtils.placeLeafAt(reader, pos.above(i).relative(dir,j).relative(dir.getCounterClockWise(),j), rand, config);
                        }
                    }
                    if (rand.nextFloat() > 0.2) dead = false;

                }
                WorldgenUtils.placeLeafAt(reader, pos.above(trunkHeight+1).relative(dir,j), rand, config);


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

    private void balsamBranch(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, boolean dead, boolean top) {
        List<BlockPos> leaves = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().equals(Direction.Axis.Y)) continue;
            if (rand.nextFloat() < 0.4 && !top) {
                if (WorldgenUtils.isAirOrLeaves(reader, pos.relative(dir))) {
                    WorldgenUtils.placeLogAt(reader,pos.relative(dir),rand,config,dir.getAxis());
                    if (!dead) {
                        leaves.add(pos.relative(dir).relative(dir));
                        leaves.add(pos.relative(dir).relative(dir.getClockWise()));
                        leaves.add(pos.relative(dir).relative(dir.getCounterClockWise()));
                    }
                }
            } else {
                if (!dead) {
                    leaves.add(pos.relative(dir));
                }
            }
        }
        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.BALSAM_FIR.getSapling());
    }
}
