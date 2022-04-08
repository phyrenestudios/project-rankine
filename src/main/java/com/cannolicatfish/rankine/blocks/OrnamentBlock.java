package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class OrnamentBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty STYLE = IntegerProperty.create("style",0,2);


    public OrnamentBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(STYLE)) {
            default:
            case 0:
                return Block.box(5.0D, 7.0D, 5.0D, 11.0D, 13.0D, 11.0D);
            case 1:
                return Block.box(6.0D, 5.0D, 6.0D, 10.0D, 13.0D, 10.0D);
            case 2:
                return Block.box(6.0D, 9.0D, 6.0D, 10.0D, 13.0D, 10.0D);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED,STYLE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(STYLE, Math.min(2,BuildingToolItem.getBuildingMode(heldItem))).setValue(WATERLOGGED, flag);
        }
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
        }

        return Direction.UP == facing && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.relative(Direction.UP), Direction.UP.getOpposite()) || worldIn.getBlockState(pos.relative(Direction.UP)).is(BlockTags.LEAVES);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!(projectile instanceof Snowball)) {
            worldIn.destroyBlock(hit.getBlockPos(),false);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
