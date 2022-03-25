package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.blocks.LocustSpineBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HoneyLocustTreeFeature extends Feature<TreeConfiguration> {

    public HoneyLocustTreeFeature(Codec<TreeConfiguration> config) {
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

            //build tree
            if (!flag) {
                return false;
            } else if (isValidGround(reader, pos.below()) && pos.getY() < reader.getMaxBuildHeight() - trunkHeight - 1) {
                setDirtAt(reader, pos.below());
                int branchPoint = (int) Math.round(trunkHeight * 0.6);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.above(i), rand, config, Direction.Axis.Y);
                    for (Direction dir : Direction.values()) {
                        if (dir.getAxis().equals(Direction.Axis.Y)) continue;
                        if (rand.nextFloat() < 0.8) {
                            if (WorldgenUtils.isAir(reader, pos.above(i).relative(dir))) {
                                reader.setBlock(pos.above(i).relative(dir), RankineBlocks.LOCUST_SPINE.get().defaultBlockState().setValue(LocustSpineBlock.FACING,dir),3);
                            }
                        }
                    }
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch < 5; ++branch) {
                    willowBranch(reader,pos.above(branchPoint),rand,config, trunkHeight-branchPoint,dir,0);
                    dir = dir+2;
                }
                willowLeaves(reader, pos.above(trunkHeight+1), rand, config);
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

    private void willowLeaves(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-2,-1,-2),pos.offset(2,3,2))) {
            if (WorldgenUtils.inRadiusCenter(pos,b,2.5)) {
                leaves.add(b.immutable());
            }
        }

        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private void willowBranch(WorldGenLevel reader, BlockPos pos, Random rand, TreeConfiguration config, int branchHeight, int dir, int tier) {
        int topHeight = rand.nextInt(branchHeight)+1;
        int split = rand.nextInt(2)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.above(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.above(i+1),rand,config, Direction.Axis.Y);
            for (Direction d : Direction.values()) {
                if (d.getAxis().equals(Direction.Axis.Y)) continue;
                if (rand.nextFloat() < 0.8) {
                    if (WorldgenUtils.isAir(reader, b.above(i).relative(d))) {
                        reader.setBlock(b.above(i).relative(d), RankineBlocks.LOCUST_SPINE.get().defaultBlockState().setValue(LocustSpineBlock.FACING,d),3);
                    }
                }
            }
            if (i == split && branchHeight-i-1 > 0) {
                willowBranch(reader, b.above(i+1), rand, config, branchHeight-i-1, dir-2, tier+1);
            }

            dir = (rand.nextBoolean() ? 0 : 1) + dir;
        }
        willowLeaves(reader, b.above(topHeight+1), rand, config);

    }

    public static void setDirtAt(LevelAccessor reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
        }
    }

    public static boolean isValidGround(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.HONEY_LOCUST_SAPLING.get());
    }
}
