package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class LeafLitterBlock extends FallingBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    public LeafLitterBlock() {
        super(Block.Properties.create(Material.PLANTS).zeroHardnessAndResistance().notSolid().sound(SoundType.PLANT));
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        int j = state.get(AGE);
        if (j == 15) {
            if (worldIn.getLightSubtracted(pos.up(), 0) <= 9) {
                BlockState groundBS = worldIn.getBlockState(pos.down());
                if (groundBS.matchesBlock(Blocks.COBBLESTONE)) {
                    worldIn.setBlockState(pos.down(), Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3);
                } else if (groundBS.matchesBlock(Blocks.STONE_BRICKS)) {
                    worldIn.setBlockState(pos.down(), Blocks.MOSSY_STONE_BRICKS.getDefaultState(), 3);
                } else if (groundBS.matchesBlock(Blocks.GRASS_BLOCK)) {
                    worldIn.setBlockState(pos.down(), Blocks.PODZOL.getDefaultState(), 3);
                } else if (RankineLists.GRASS_BLOCKS.contains(groundBS.getBlock())) {
                    worldIn.setBlockState(pos.down(), RankineLists.PODZOL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(groundBS.getBlock())).getDefaultState(), 3);
                }
            }
            worldIn.destroyBlock(pos, false);
        } else if (worldIn.getRandom().nextInt(Config.GENERAL.LEAF_LITTER_GROWTH.get()) == 0) {
            worldIn.setBlockState(pos, state.with(AGE, j + 1), 3);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
            fallingblockentity.shouldDropItem = false;
            this.onStartFalling(fallingblockentity);
            worldIn.addEntity(fallingblockentity);
        } else if (!isValidPosition(state,worldIn,pos)) {
            worldIn.removeBlock(pos,false);
        }
    }

    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable() || state.getBlock() instanceof LeafLitterBlock;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isNormalCube(worldIn,pos.down());
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Override
    public boolean isReplaceable(BlockState state, Fluid fluid) {
        return super.isReplaceable(state, fluid);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 200;
    }
}
