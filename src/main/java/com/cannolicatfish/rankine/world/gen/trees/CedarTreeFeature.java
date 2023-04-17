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

public class CedarTreeFeature extends Feature<TreeConfiguration> {

    public CedarTreeFeature(Codec<TreeConfiguration> config) {
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
                int dir = rand.nextInt(8);
                int lean = rand.nextInt(trunkHeight-7)+2;
                BlockPos base = pos;
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, base.above(i), rand, config, Direction.Axis.Y);
                    if (i == lean) {
                        base = WorldgenUtils.eightBlockDirection(base,dir,1);
                        WorldgenUtils.checkLog(reader, base.above(i), rand, config, Direction.Axis.Y);
                    }
                    if (i == trunkHeight-4) {
                        WorldgenUtils.checkLog(reader, base.above(i).north(), rand, config, Direction.Axis.Z);
                        WorldgenUtils.checkLog(reader, base.above(i+1).north(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, base.above(i).south(), rand, config, Direction.Axis.Z);
                        WorldgenUtils.checkLog(reader, base.above(i+1).south(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, base.above(i).east(), rand, config, Direction.Axis.X);
                        WorldgenUtils.checkLog(reader, base.above(i+1).east(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, base.above(i).west(), rand, config, Direction.Axis.X);
                        WorldgenUtils.checkLog(reader, base.above(i+1).west(), rand, config, Direction.Axis.Y);
                    }
                }

                BlockPos leafPos = base.above(trunkHeight);
                List<BlockPos> leaves = new ArrayList<>();
                for (BlockPos b : BlockPos.betweenClosed(leafPos.offset(-2,-4,-2),leafPos.offset(2,-4,2))) {
                    if (WorldgenUtils.inRadiusCenter(leafPos.above(b.getY()-leafPos.getY()),b,2.1D)) leaves.add(b.immutable());
                }
                for (BlockPos b : BlockPos.betweenClosed(leafPos.offset(-2,-3,-2),leafPos.offset(2,-1,2))) {
                    if (WorldgenUtils.inRadiusCenter(leafPos.above(b.getY()-leafPos.getY()),b,2.5D)) leaves.add(b.immutable());
                }
                for (BlockPos b : BlockPos.betweenClosed(leafPos.offset(-2,0,-2),leafPos.offset(2,1,2))) {
                    if (WorldgenUtils.inRadiusCenter(leafPos.above(b.getY()-leafPos.getY()),b,2.1D)) leaves.add(b.immutable());
                }
                for (BlockPos b : BlockPos.betweenClosed(leafPos.offset(-2,2,-2),leafPos.offset(2,3,2))) {
                    if (WorldgenUtils.inRadiusCenter(leafPos.above(b.getY()-leafPos.getY()),b,1.1D)) leaves.add(b.immutable());
                }
                leaves.add(leafPos.above(4));
                for (BlockPos b : leaves) {
                    WorldgenUtils.placeLeafAt(reader, b, rand, config);
                }
                for (BlockPos b : BlockPos.betweenClosed(leafPos.offset(-2,-1,-2),leafPos.offset(2,3,2))) {
                    if (reader.getBlockState(b).is(config.foliageProvider.getState(rand,b).getBlock()) && reader.getBlockState(b.above()).isAir()) {
                        float r = rand.nextFloat();
                        if (r < 0.2) {
                            reader.setBlock(b,Blocks.AIR.defaultBlockState(),19);
                        } else if (r < 0.4) {
                            WorldgenUtils.placeLeafAt(reader, b.above(), rand, config);
                        }
                    }
                }

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

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, RankineBlocks.CEDAR.getSapling());
    }
}
