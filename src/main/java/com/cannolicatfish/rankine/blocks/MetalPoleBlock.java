package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
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
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class MetalPoleBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty STYLE = IntegerProperty.create("style",0,7);
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
        VoxelShape main = box(5,0,5,11,16,11);
        return main;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);

    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(STYLE)) {
            case 1, 5 -> 15;
            case 2, 6 -> 10;
            case 3, 7 -> 7;
            default -> 0;
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int style = state.getValue(STYLE);
        if (player.getItemInHand(handIn).is(Tags.Items.SHEARS)) {
            if (style == 0) return InteractionResult.PASS;
            levelIn.setBlockAndUpdate(pos,state.setValue(STYLE,0));
            levelIn.playSound(null,pos, SoundEvents.LEASH_KNOT_BREAK, SoundSource.BLOCKS,0.8f,1.2f);
            player.swing(handIn,true);
            player.getItemInHand(handIn).hurtAndBreak(1, player, (p_219998_1_) -> {
                p_219998_1_.broadcastBreakEvent(handIn);
            });
            return InteractionResult.sidedSuccess(levelIn.isClientSide);
        } else if (player.getItemInHand(handIn).is(ItemTags.LEAVES)) {
            if (style + 4 > 7) return super.use(state, levelIn, pos, player, handIn, hit);
            return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,style+4), SoundEvents.LEASH_KNOT_PLACE);
        } else if (player.getItemInHand(handIn).is(Items.TORCH)) {
            if (style == 0) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,1), SoundEvents.LEASH_KNOT_PLACE);
            if (style == 4) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,5), SoundEvents.LEASH_KNOT_PLACE);
        } else if (player.getItemInHand(handIn).is(Items.SOUL_TORCH)) {
            if (style == 0) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,2), SoundEvents.LEASH_KNOT_PLACE);
            if (style == 4) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,6), SoundEvents.LEASH_KNOT_PLACE);
        } else if (player.getItemInHand(handIn).is(Items.REDSTONE_TORCH)) {
            if (style == 0) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,3), SoundEvents.LEASH_KNOT_PLACE);
            if (style == 4) return completeAction(levelIn, pos, player, handIn, state.setValue(STYLE,7), SoundEvents.LEASH_KNOT_PLACE);
        }

        if (player.isShiftKeyDown()) {
            int n = 1;
            while (levelIn.getBlockState(pos.below(n)).getBlock() == this) {
                n += 1;
            }
            BlockPos newpos = pos.offset(0, -n + 1, 0);
            if (levelIn.isEmptyBlock(newpos.north()) && levelIn.isEmptyBlock(newpos.north().above())) {
                newpos = newpos.north();
            } else if (levelIn.isEmptyBlock(newpos.east()) && levelIn.isEmptyBlock(newpos.east().above())) {
                newpos = newpos.east();
            } else if (levelIn.isEmptyBlock(newpos.south()) && levelIn.isEmptyBlock(newpos.south().above())) {
                newpos = newpos.south();
            } else if (levelIn.isEmptyBlock(newpos.west()) && levelIn.isEmptyBlock(newpos.west().above())) {
                newpos = newpos.west();
            }
            player.teleportTo(newpos.getX() + .5f, newpos.getY(), newpos.getZ() + .5f);
            if (n > 5) player.hurt(levelIn.damageSources().fall(), 1.0F);
            player.playNotifySound(SoundEvents.ANVIL_FALL, SoundSource.BLOCKS, 1.0f, 0.8f + 0.3f*levelIn.getRandom().nextFloat());
            return InteractionResult.PASS;
        }

        return super.use(state, levelIn, pos, player, handIn, hit);
    }

    private InteractionResult completeAction(Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockState stateIn, SoundEvent soundIn) {
        levelIn.setBlockAndUpdate(posIn, stateIn);
        levelIn.playSound(null, posIn, soundIn, SoundSource.BLOCKS,0.8f,1.0f + 0.3f*levelIn.getRandom().nextFloat());
        playerIn.swing(handIn,true);
        if (!playerIn.isCreative()) playerIn.getItemInHand(handIn).shrink(1);
        return InteractionResult.sidedSuccess(levelIn.isClientSide);
    }

}
