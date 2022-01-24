package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeepingWillowTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public WeepingWillowTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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

            //build tree
            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.down()) && pos.getY() < reader.getHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.down());
                int branchPoint = (int) Math.round(trunkHeight * 0.5);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.up(i), rand, config, Direction.Axis.Y);
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 5; ++branch) {
                    willowBranch(reader,pos.up(branchPoint),rand,config, trunkHeight-branchPoint,dir,0);
                    dir = dir+2;
                }
                willowLeaves(reader, pos.up(trunkHeight+1), rand, config);
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

    private static boolean areAllNeighborsEmpty(IWorldReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!worldIn.isAirBlock(pos.offset(direction))) {
                return false;
            }
        }
        return true;
    }

    private void willowLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-3,-1,-3),pos.add(3,2,3))) {
            if (WorldgenUtils.inRadiusCenter(pos,b,2.5)) {
                leaves.add(b.toImmutable());
            }
        }

        for (BlockPos b : leaves) {
            if (rand.nextFloat() < 0.7 &&  WorldgenUtils.isAir(reader,b.down()) && areAllNeighborsEmpty(reader,b.down())) {
                WorldgenUtils.placeLeafAt(reader, b.down(), rand, config);
                if (rand.nextFloat() < 0.7 &&  WorldgenUtils.isAir(reader,b.down(2)) && areAllNeighborsEmpty(reader,b.down(2))) {
                    WorldgenUtils.placeLeafAt(reader, b.down(2), rand, config);
                    placeBranchlet(b.down(2),reader,rand);
                }
            }
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private static void placeBranchlet(BlockPos pos, IWorld world, Random rand) {
        BlockPos.Mutable blockpos$mutable = pos.toMutable().move(Direction.DOWN);
        if (world.isAirBlock(blockpos$mutable)) {
            int i = MathHelper.nextInt(rand, 2, 5);
            if (rand.nextInt(7) == 0) {
                i *= 2;
            }
            WillowBranchletFeature.func_236427_a_(world, rand, blockpos$mutable, i, 23, 25);
        }
    }

    private void willowBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int branchHeight, int dir, int tier) {
        int topHeight = rand.nextInt(branchHeight)+1;
        int split = rand.nextInt(2)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.up(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.up(i+1),rand,config, Direction.Axis.Y);
            if (i == split && branchHeight-i-1 > 0) {
                willowBranch(reader, b.up(i+1), rand, config, branchHeight-i-1, dir-2, tier+1);
            }

            dir = (rand.nextBoolean() ? 0 : 1) + dir;
        }
        willowLeaves(reader, b.up(topHeight+1), rand, config);

    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.WEEPING_WILLOW_SAPLING.get());
    }
}
