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

public class WesternHemlockTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public WesternHemlockTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                int crownHeight = (int) Math.round(trunkHeight*0.5);
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, pos.up(i), rand, config, Direction.Axis.Y);
                    if (i < trunkHeight-crownHeight) {
                        WorldgenUtils.checkLog(reader, pos.up(i).west(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.up(i).east(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.up(i).north(), rand, config, Direction.Axis.Y);
                        WorldgenUtils.checkLog(reader, pos.up(i).south(), rand, config, Direction.Axis.Y);
                    }

                }
                hemlockBranch(reader,pos.up(trunkHeight),rand,config,crownHeight+2);
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
        WorldgenUtils.placeLeafAt(reader,pos.up(2),rand,config);
        for (int i = 0; i < crownHeight; ++i) {
            if (i > 1) {
                if (i%2==0) {
                    WorldgenUtils.placeLogAt(reader,pos.down(i).north(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.down(i).north(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.down(i).south(),rand,config,Direction.Axis.Z);
                    hemlockLeaves(reader, pos.down(i).south(), rand, config);
                    if (i > crownHeight * 0.50) {
                        WorldgenUtils.placeLogAt(reader,pos.down(i).north(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.down(i).north(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.down(i).south(2),rand,config,Direction.Axis.Z);
                        hemlockLeaves(reader, pos.down(i).south(2), rand, config);
                    }
                } else {
                    WorldgenUtils.placeLogAt(reader,pos.down(i).east(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.down(i).east(), rand, config);
                    WorldgenUtils.placeLogAt(reader,pos.down(i).west(),rand,config,Direction.Axis.X);
                    hemlockLeaves(reader, pos.down(i).west(), rand, config);
                    if (i > crownHeight * 0.50) {
                        WorldgenUtils.placeLogAt(reader,pos.down(i).east(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.down(i).east(2), rand, config);
                        WorldgenUtils.placeLogAt(reader,pos.down(i).west(2),rand,config,Direction.Axis.X);
                        hemlockLeaves(reader, pos.down(i).west(2), rand, config);
                    }
                }
            }
            hemlockLeaves(reader, pos, rand, config);
            hemlockLeaves(reader, pos.down(1), rand, config);
        }

    }

    private void hemlockLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN)) continue;
            WorldgenUtils.placeLeafAt(reader,pos.offset(dir),rand,config);
        }
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.WESTERN_HEMLOCK_SAPLING.get());
    }
}
