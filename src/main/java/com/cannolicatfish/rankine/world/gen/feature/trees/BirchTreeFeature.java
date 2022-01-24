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

public class BirchTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public BirchTreeFeature(Codec<BaseTreeFeatureConfig> config) {
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
                            if (!isAirOrLeaves(reader, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
                int branchCount = 3;
                int dir = rand.nextInt(8);
                for(int i = 0; i <= trunkHeight; ++i) {
                    if (isAirOrLeaves(reader, pos.up(i))) {
                        this.placeLogAt(reader, pos.up(i), rand, config, Direction.Axis.Y);
                    }
                    if (branchCount > 0 && i > trunkHeight*0.4f && i < trunkHeight*0.8f) {
                        birchBranch(reader,pos.up(i),rand,config, trunkHeight-i,dir);
                        dir = (dir + rand.nextInt(2)+2) % 8;
                        branchCount--;
                    }

                }
                birchLeaves(reader, pos.up(trunkHeight), rand, config);

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

    private void birchLeaves(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        List<BlockPos> leaves = new ArrayList<>();

        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-1,-1,-1),pos.add(1,1,1))) {
            leaves.add(b.toImmutable());
        }
        leaves.add(pos.down(2));
        leaves.add(pos.down(2).north());
        leaves.add(pos.down(2).east());
        leaves.add(pos.down(2).west());
        leaves.add(pos.down(2).south());
        leaves.add(pos.up(3));
        leaves.add(pos.up(2));
        leaves.add(pos.up(2).south());
        leaves.add(pos.up(2).east());
        leaves.add(pos.up(2).west());
        leaves.add(pos.up(2).north());

        for (BlockPos b : leaves) {
            if (isAirOrLeaves(reader, b)) {
                this.placeLeafAt(reader, b, rand, config);
            }
        }
    }

    private void birchBranch(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, int branchHeight, int dir) {

        if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,1))) {
            placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,1),rand,config, Direction.Axis.Y);
        }
        if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,1).up())) {
            placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,1).up(),rand,config, Direction.Axis.Y);
        }
        int topHeight = rand.nextInt(branchHeight)+3;
        for (int i = 1; i<topHeight; ++i) {
            if (isAirOrLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,2).up(i))) {
                placeLogAt(reader,WorldgenUtils.eightBlockDirection(pos,dir,2).up(i),rand,config, Direction.Axis.Y);
            }
        }
        birchLeaves(reader, WorldgenUtils.eightBlockDirection(pos,dir,2).up(topHeight), rand, config);

    }


    private void placeLogAt(IWorldWriter reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, Direction.Axis axis) {
        this.setLogState(reader, pos, config.trunkProvider.getBlockState(rand, pos).with(BlockStateProperties.AXIS, axis));
    }

    private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        if (isAirOrLeaves(world, pos)) {
            this.setLogState(world, pos, config.leavesProvider.getBlockState(rand, pos).with(LeavesBlock.DISTANCE, 1));
        }
    }

    protected final void setLogState(IWorldWriter reader, BlockPos pos, BlockState state) {
        reader.setBlockState(pos, state, 18);
    }

    public static boolean isAir(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.IBlockReader)) {
            return reader.hasBlockState(pos, BlockState::isAir);
        }
        else {
            return reader.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader) reader, pos));
        }
    }

    public static boolean isAirOrLeaves(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (reader instanceof net.minecraft.world.IWorldReader) {
            return reader.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) reader, pos));
        }
        return reader.hasBlockState(pos, (state) -> {
            return state.isAir() || state.isIn(BlockTags.LEAVES);
        });
    }

    public static void setDirtAt(IWorld reader, BlockPos pos) {
        Block block = reader.getBlockState(pos).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
            reader.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
        }
    }

    public static boolean isValidGround(IWorld world, BlockPos pos) {
        return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) RankineBlocks.BALSAM_FIR_SAPLING.get());
    }
}
