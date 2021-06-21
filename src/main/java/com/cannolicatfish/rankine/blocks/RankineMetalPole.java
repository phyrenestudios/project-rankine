package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RankineMetalPole extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RankineMetalPole(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape main = makeCuboidShape(5,0,5,11,16,11);
        if (state.get(NORTH)) {
            VoxelShape north = makeCuboidShape(6,10,0,10,14,5);
            main = VoxelShapes.combineAndSimplify(main, north, IBooleanFunction.ONLY_FIRST);
        }
        if (state.get(SOUTH)) {
            VoxelShape south = makeCuboidShape(6,10,11,10,14,16);
            main = VoxelShapes.combineAndSimplify(main, south, IBooleanFunction.ONLY_FIRST);
        }
        if (state.get(EAST)) {
            VoxelShape east = makeCuboidShape(0,10,6,5,14,10);
            main = VoxelShapes.combineAndSimplify(main, east, IBooleanFunction.ONLY_FIRST);
        }
        if (state.get(WEST)) {
            VoxelShape west = makeCuboidShape(11,10,6,16,14,10);
            main = VoxelShapes.combineAndSimplify(main, west, IBooleanFunction.ONLY_FIRST);
        }
        return main;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return this.makeConnections(context.getWorld(), context.getPos()).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        BlockState bs1 = blockReader.getBlockState(pos.north());
        BlockState bs2 = blockReader.getBlockState(pos.east());
        BlockState bs3 = blockReader.getBlockState(pos.south());
        BlockState bs4 = blockReader.getBlockState(pos.west());
        return this.getDefaultState()
                .with(NORTH,  bs1 == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HANGING, false).with(SodiumVaporLampBlock.HORIZONTAL_FACING, Direction.NORTH))
                .with(EAST, bs2 == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HANGING, false).with(SodiumVaporLampBlock.HORIZONTAL_FACING, Direction.EAST))
                .with(SOUTH, bs3 == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HANGING, false).with(SodiumVaporLampBlock.HORIZONTAL_FACING, Direction.SOUTH))
                .with(WEST, bs4 == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HANGING, false).with(SodiumVaporLampBlock.HORIZONTAL_FACING, Direction.WEST));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        boolean flag;
        switch (facing) {
            case NORTH:
                flag = facingState == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HORIZONTAL_FACING, facing).with(SodiumVaporLampBlock.HANGING, false);
                return stateIn.with(NORTH, flag);
            case SOUTH:
                flag = facingState == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HORIZONTAL_FACING, facing).with(SodiumVaporLampBlock.HANGING, false);
                return stateIn.with(SOUTH, flag);
            case EAST:
                flag = facingState == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HORIZONTAL_FACING, facing).with(SodiumVaporLampBlock.HANGING, false);
                return stateIn.with(EAST, flag);
            case WEST:
                flag = facingState == RankineBlocks.SODIUM_VAPOR_LAMP.get().getDefaultState().with(SodiumVaporLampBlock.HORIZONTAL_FACING, facing).with(SodiumVaporLampBlock.HANGING, false);
                return stateIn.with(WEST, flag);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);

    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isSneaking()) {
            int n = 1;
            while (worldIn.getBlockState(pos.down(n)).getBlock() == this.getBlock()) {
                n += 1;
            }
            if (!worldIn.isRemote) {
                BlockPos newpos = pos.add(0,-n+1,0);
                if (worldIn.isAirBlock(newpos.north()) && worldIn.isAirBlock(newpos.north().up())) {
                    newpos = newpos.north();
                } else if (worldIn.isAirBlock(newpos.east()) && worldIn.isAirBlock(newpos.east().up())) {
                    newpos = newpos.east();
                } else if (worldIn.isAirBlock(newpos.south()) && worldIn.isAirBlock(newpos.south().up())) {
                    newpos = newpos.south();
                } else if (worldIn.isAirBlock(newpos.west()) && worldIn.isAirBlock(newpos.west().up())) {
                    newpos = newpos.west();
                }
                player.setPositionAndUpdate(newpos.getX() + .5f, newpos.getY(), newpos.getZ() + .5f);
                return ActionResultType.FAIL;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
