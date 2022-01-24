package com.cannolicatfish.rankine.world.gen.feature.trees;

import com.cannolicatfish.rankine.blocks.LocustSpineBlock;
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

public class HoneyLocustTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public HoneyLocustTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                int branchPoint = (int) Math.round(trunkHeight * 0.6);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.up(i), rand, config, Direction.Axis.Y);
                    for (Direction dir : Direction.values()) {
                        if (dir.getAxis().equals(Direction.Axis.Y)) continue;
                        if (rand.nextFloat() < 0.8) {
                            if (WorldgenUtils.isAir(reader, pos.up(i).offset(dir))) {
                                reader.setBlockState(pos.up(i).offset(dir), RankineBlocks.LOCUST_SPINE.get().getDefaultState().with(LocustSpineBlock.FACING,dir),3);
                            }
                        }
                    }
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

    private void willowLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-2,-1,-2),pos.add(2,3,2))) {
            if (WorldgenUtils.inRadiusCenter(pos,b,2.5)) {
                leaves.add(b.toImmutable());
            }
        }

        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
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
            for (Direction d : Direction.values()) {
                if (d.getAxis().equals(Direction.Axis.Y)) continue;
                if (rand.nextFloat() < 0.8) {
                    if (WorldgenUtils.isAir(reader, b.up(i).offset(d))) {
                        reader.setBlockState(b.up(i).offset(d), RankineBlocks.LOCUST_SPINE.get().getDefaultState().with(LocustSpineBlock.FACING,d),3);
                    }
                }
            }
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
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.HONEY_LOCUST_SAPLING.get());
    }
}
