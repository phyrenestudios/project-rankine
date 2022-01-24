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

public class CoconutPalmTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public CoconutPalmTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                BlockPos topPos = pos;
                BlockPos base = pos;
                int dir = rand.nextInt(8);
                int len = rand.nextInt(3)+2;
                for(int i = 0; i <= trunkHeight; ++i) {
                    WorldgenUtils.checkLog(reader, base.up(i), rand, config, Direction.Axis.Y);
                    topPos = base.up(i+1);
                    if (i == len) {
                        base = WorldgenUtils.eightBlockDirection(base,dir,1);
                        len = len + rand.nextInt(3)+2;
                        if (i < trunkHeight) {
                            WorldgenUtils.checkLog(reader, base.up(i), rand, config, Direction.Axis.Y);
                        }
                    }
                }
                for (Direction d : Direction.values()) {
                    if (d.getAxis().equals(Direction.Axis.Y)) continue;
                    WorldgenUtils.checkLog(reader,topPos.offset(d),rand,config,d.getAxis());
                }
                palmLeaves(reader, topPos, rand, config);
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

    private void palmLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-2,0,-2),pos.add(2,0,2))) {
            if (WorldgenUtils.inRadiusCenter(pos.up(b.getY()-pos.getY()),b,2.5D)) leaves.add(b.toImmutable());
        }
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-1,1,-1),pos.add(1,1,1))) {
            leaves.add(b.toImmutable());
        }
        leaves.add(pos.up().north(2));
        leaves.add(pos.up().east(2));
        leaves.add(pos.up().west(2));
        leaves.add(pos.up().south(2));

        for (Direction d : Direction.values()) {
            if (d.getAxis().equals(Direction.Axis.Y)) continue;
            leaves.addAll(palmFraun(reader, pos.offset(d,2).offset(d.rotateY()), rand, d));
            leaves.addAll(palmFraun(reader, pos.offset(d,2).offset(d.rotateYCCW()), rand, d));
        }

        for (BlockPos b : leaves) {
            WorldgenUtils.placeLeafAt(reader, b, rand, config);
        }

    }

    private static List<BlockPos> palmFraun(ISeedReader reader, BlockPos pos, Random rand, Direction dir) {
        List<BlockPos> leaves = new ArrayList<>();
        int leaf = rand.nextInt(2);
        if (leaf == 0) {
            leaves.add(pos.down());
            leaves.add(pos.down().offset(dir));
            //if (rand.nextFloat() < 0.5) {
                leaves.add(pos.down(2).offset(dir));
            //}
            if (rand.nextFloat() < 0.5) {
                leaves.add(pos.down(2).offset(dir,2));
            }
            if (rand.nextFloat() < 0.5) {
                leaves.add(pos.down(2).offset(dir).offset(dir.rotateYCCW()));
            }
            if (rand.nextFloat() < 0.5) {
                leaves.add(pos.down(2).offset(dir).offset(dir.rotateY()));
            }
        } else {
            leaves.add(pos.offset(dir));
            leaves.add(pos.down().offset(dir));
            //if (rand.nextFloat() < 0.5) {
                leaves.add(pos.down().offset(dir,2));
            //}
            if (rand.nextFloat() < 0.7) {
                leaves.add(pos.down().offset(dir).offset(dir.rotateY()));
            }
            if (rand.nextFloat() < 0.7) {
                leaves.add(pos.down().offset(dir).offset(dir.rotateYCCW()));
            }

        }

        return leaves;
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.COCONUT_PALM_SAPLING.get());
    }
}
