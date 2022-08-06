package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MetalPoleBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty STYLE = IntegerProperty.create("style",0,1);
    int alloyColor;

    public MetalPoleBlock(int color) {
        super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(4.0F, 6.0F).sound(SoundType.METAL).noOcclusion());
        this.alloyColor = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(STYLE,0).setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STYLE,WATERLOGGED);
    }

    public int getColor() {
        return this.alloyColor;
    }


    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        //VoxelShape BOX = makeCuboidShape(0,0,0,0,0,0);
        VoxelShape main = box(5,0,5,11,16,11);
        /*
        if (state.get(NORTH)) {
            BOX = makeCuboidShape(5,12,0,11,16,5);
        }
        if (state.get(SOUTH)) {
            BOX = makeCuboidShape(5,10,11,11,16,16);
        }
        if (state.get(WEST)) {
            BOX = makeCuboidShape(0,10,5,5,16,11);
        }
        if (state.get(EAST)) {
            BOX = makeCuboidShape(11,10,5,16,16,11);
        }
        main = VoxelShapes.combineAndSimplify(main, BOX, IBooleanFunction.OR);

         */
        return main;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
/*
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

 */

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
        }
        /*
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

         */
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);

    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem().equals(RankineItems.GARLAND.get()) && state.getValue(STYLE) != 1 && !worldIn.isClientSide) {
            worldIn.setBlockAndUpdate(pos,state.setValue(STYLE,1));
            worldIn.playSound(null,pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS,0.8f,0.8f);
            player.swing(handIn,true);
            if (!player.isCreative()) player.getItemInHand(handIn).shrink(1);

        } else if (player.isShiftKeyDown()) {
            int n = 1;
            while (worldIn.getBlockState(pos.below(n)).getBlock() == this) {
                n += 1;
            }
            if (!worldIn.isClientSide) {
                BlockPos newpos = pos.offset(0,-n+1,0);
                if (worldIn.isEmptyBlock(newpos.north()) && worldIn.isEmptyBlock(newpos.north().above())) {
                    newpos = newpos.north();
                } else if (worldIn.isEmptyBlock(newpos.east()) && worldIn.isEmptyBlock(newpos.east().above())) {
                    newpos = newpos.east();
                } else if (worldIn.isEmptyBlock(newpos.south()) && worldIn.isEmptyBlock(newpos.south().above())) {
                    newpos = newpos.south();
                } else if (worldIn.isEmptyBlock(newpos.west()) && worldIn.isEmptyBlock(newpos.west().above())) {
                    newpos = newpos.west();
                }
                player.teleportTo(newpos.getX() + .5f, newpos.getY(), newpos.getZ() + .5f);
                if (n>5) {
                    player.hurt(DamageSource.FALL, 1.0F);
                }
            }
            player.playNotifySound(SoundEvents.METAL_FALL, SoundSource.BLOCKS,0.8f,1.0f);
            return InteractionResult.PASS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }


}
