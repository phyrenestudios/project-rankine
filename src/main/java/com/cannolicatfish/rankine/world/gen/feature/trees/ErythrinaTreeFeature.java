package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ErythrinaTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public ErythrinaTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                            if (!isAirOrLeaves(reader, blockpos$mutableblockpos.set(l, j, i1))) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }

            //build tree
            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.below()) && pos.getY() < reader.getMaxBuildHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.below());
                int branchPoint = (int) Math.round(trunkHeight * 0.3);
                for(int i = 0; i <= branchPoint; ++i) {
                    if (isAirOrLeaves(reader, pos.above(i))) {
                        this.placeLogAt(reader, pos.above(i), rand, config, Direction.Axis.Y);
                    }
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 4; ++branch) {
                    erythrinaBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint,dir,0);
                    dir = dir + rand.nextInt(2)+2;
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

    private void magnoliaLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-1,0,-1),pos.offset(1,0,1))) {
            leaves.add(b.immutable());
        }
        leaves.add(pos.below(1));
        leaves.add(pos.below(1).north());
        leaves.add(pos.below(1).east());
        leaves.add(pos.below(1).west());
        leaves.add(pos.below(1).south());
        leaves.add(pos.above(2));
        leaves.add(pos.above(1));
        leaves.add(pos.above(1).south());
        leaves.add(pos.above(1).east());
        leaves.add(pos.above(1).west());
        leaves.add(pos.above(1).north());

        for (BlockPos b : leaves) {
            if (isAirOrLeaves(reader, b)) {
                this.placeLeafAt(reader, b, rand, config);
            }
        }
    }

    private static boolean areAllNeighborsEmpty(IWorldReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }

        return true;
    }

    private void erythrinaBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int branchHeight, int dir, int tier) {
        int topHeight = rand.nextInt(branchHeight)+1;
        int split = rand.nextInt(2)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            if (isAirOrLeaves(reader, b.above(i))) {
                placeLogAt(reader,b.above(i),rand,config, Direction.Axis.Y);
            }
            if (isAirOrLeaves(reader, b.above(i+1))) {
                placeLogAt(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            }
            if (i == split && branchHeight-i-1 > 0) {
                erythrinaBranch(reader, b.above(i+1), rand, config, branchHeight-i-1, dir-2, tier+1);
            }

            dir = (rand.nextBoolean() ? 0 : 1) + dir;
        }
        magnoliaLeaves(reader, b.above(topHeight+1), rand, config);

    }


    private void placeLogAt(IWorldWriter reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, Direction.Axis axis) {
        this.setLogState(reader, pos, config.trunkProvider.getState(rand, pos).setValue(BlockStateProperties.AXIS, axis));
    }

    private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        if (isAirOrLeaves(world, pos)) {
            this.setLogState(world, pos, config.leavesProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
        }
    }

    protected final void setLogState(IWorldWriter reader, BlockPos pos, BlockState state) {
        reader.setBlock(pos, state, 18);
    }

    public static boolean isAir(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.IBlockReader)) {
            return reader.isStateAtPosition(pos, BlockState::isAir);
        }
        else {
            return reader.isStateAtPosition(pos, state -> state.isAir((net.minecraft.world.IBlockReader) reader, pos));
        }
    }

    public static boolean isAirOrLeaves(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (reader instanceof IWorldReader) {
            return reader.isStateAtPosition(pos, state -> state.canBeReplacedByLeaves((IWorldReader) reader, pos));
        }
        return reader.isStateAtPosition(pos, (state) -> {
            return state.isAir() || state.is(BlockTags.LEAVES);
        });
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.ERYTHRINA_SAPLING.get());
    }
}
