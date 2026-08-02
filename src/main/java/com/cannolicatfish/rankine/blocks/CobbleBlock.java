package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

// PORT-NOTE: Material.STONE -> .mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).
// Block#getOffsetType() was removed; the XYZ offset is now declared on the properties via .offsetType(...).
public class CobbleBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<CobbleBlock> CODEC = MapCodec.unit(CobbleBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public MapCodec<? extends CobbleBlock> codec() {
        return CODEC;
    }

    public CobbleBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1.0F, 3.0F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XYZ));
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D);
    }

    @Override
    protected BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (stateIn.getValue(WATERLOGGED)) {
                worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
            }

            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate1 = this.defaultBlockState();
        LevelReader iworldreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        if (blockstate1.canSurvive(iworldreader, blockpos)) {
            return blockstate1.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }


        return null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos posIn) {
        return canSupportCenter(levelIn, posIn.below(), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // PORT-NOTE: Block#use(...) split into useWithoutItem/useItemOn. This interaction never looked at the held
    // stack, so it lives in useWithoutItem; a held item still reaches it because the default useItemOn returns
    // PASS_TO_DEFAULT_BLOCK_INTERACTION (main hand only, which is where the 1.18.2 pickup was usable anyway).
    @Override
    protected InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos pos, Player player, BlockHitResult p_60508_) {
        popResource(p_60504_,pos,new ItemStack(p_60503_.getBlock().asItem()));
        p_60504_.removeBlock(pos,false);
        p_60504_.playSound(player,pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, p_60504_.getRandom().nextFloat() * 0.4F + 1.0F);
        return InteractionResult.SUCCESS;
    }
}
