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

public class PinyonPineTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public PinyonPineTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                int branchPoint = rand.nextInt(3)+2;
                for(int i = 0; i <= branchPoint; ++i) {
                    WorldgenUtils.checkLog(reader, pos.up(i), rand, config, Direction.Axis.Y);
                }

                int dir = rand.nextInt(8);
                for (int branch = 1; branch <= 3; ++branch) {
                    juniperBranch(reader,pos.up(branchPoint),rand,config, trunkHeight-branchPoint-1,dir);
                    dir = rand.nextInt(8);
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

    private void pinyonPineLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-2,0,-2),pos.add(2,0,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.up(b.getY()-pos.getY()),b,2.1D)) leaves.add(b.toImmutable());
        }
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-2,1,-2),pos.add(2,1,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.up(b.getY()-pos.getY()),b,2.5D)) leaves.add(b.toImmutable());
        }
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-2,2,-2),pos.add(2,2,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.up(b.getY()-pos.getY()),b,2.1D)) leaves.add(b.toImmutable());
        }
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-1,3,-1),pos.add(1,3,1))) {
            leaves.add(b.toImmutable());
        }
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-1,4,-1),pos.add(1,4,1))) {
            if (WorldgenUtils.inRadiusCenter(pos.up(b.getY()-pos.getY()),b,1.1D)) leaves.add(b.toImmutable());
        }
        leaves.add(pos.up(5));
        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }
    }

    private void juniperBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int branchHeight, int dir) {
        int topHeight = rand.nextInt(branchHeight)+1;
        BlockPos b = pos;
        for (int i = 0; i<topHeight; ++i) {
            b = WorldgenUtils.eightBlockDirection(b,dir,1);
            WorldgenUtils.checkLog(reader,b.up(i),rand,config, Direction.Axis.Y);
            WorldgenUtils.checkLog(reader,b.up(i+1),rand,config, Direction.Axis.Y);
            dir = ((rand.nextBoolean() ? 0 : 1) + dir) % 8;
        }
        pinyonPineLeaves(reader, b.up(topHeight), rand, config);
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.PINYON_PINE_SAPLING.get());
    }
}
