package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class SphagnumMossBlock extends BushBlock {

    public static final IntegerProperty AGE;
    private static final VoxelShape[] SHAPES;

    public SphagnumMossBlock(Properties builder) {
        super(builder);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[(Integer)state.get(AGE)];
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = (Integer)state.get(AGE);
        if (i < 3 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(10) == 0)) {
            state = (BlockState)state.with(AGE, i + 1);
            worldIn.setBlockState(pos, state, 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

        super.tick(state, worldIn, pos, rand);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = BlockStateProperties.AGE_0_3;
        SHAPES = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};
    }
}
