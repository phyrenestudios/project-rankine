package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.blocks.states.TapBarrelFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TapBarrelBlock extends Block {
    public static final EnumProperty<TapBarrelFluids> FLUID = EnumProperty.create("fluid", TapBarrelFluids.class);
    public static final IntegerProperty LEVEL = IntegerProperty.create("level",0,6);
    protected static final VoxelShape FULL = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public TapBarrelBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FLUID, TapBarrelFluids.WATER).with(LEVEL, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FULL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        Item handItem = player.getHeldItemMainhand().getItem();
        if (handItem == Items.BUCKET) {
            Item bucket = null;
            switch (state.get(TapBarrelBlock.FLUID)) {
                case WATER:
                    bucket = Items.WATER_BUCKET;
                    break;
                case LAVA:
                    bucket = Items.LAVA_BUCKET;
                    break;
                case SAP:
                    bucket = RankineItems.SAP_BUCKET.get();
                    break;
                case MAPLE_SAP:
                    bucket = RankineItems.SAP_BUCKET.get();
                    break;
                case RESIN:
                    bucket = RankineItems.RESIN_BUCKET.get();
                    break;
                case LATEX:
                    bucket = RankineItems.LATEX_BUCKET.get();
                    break;
                case JUGLONE:
                    bucket = RankineItems.JUGLONE_BUCKET.get();
                    break;
            }
            if (bucket != null && state.get(LEVEL) > 0) {
                player.getHeldItemMainhand().shrink(1);
                player.addItemStackToInventory(new ItemStack(bucket, 1));
                if (state.get(LEVEL)==1) {
                    worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, state.get(FLUID)).with(LEVEL, 0),3);
                } else {
                    worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, state.get(FLUID)).with(LEVEL, state.get(LEVEL)-1),3);
                }
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == Items.WATER_BUCKET) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.WATER).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.WATER)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == Items.LAVA_BUCKET) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.LAVA).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.LAVA)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == RankineItems.SAP_BUCKET.get()) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.SAP).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.SAP)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == RankineItems.MAPLE_SAP_BUCKET.get()) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.MAPLE_SAP).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.MAPLE_SAP)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == RankineItems.RESIN_BUCKET.get()) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.RESIN).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.RESIN)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == RankineItems.LATEX_BUCKET.get()) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.LATEX).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.LATEX)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else if (handItem == RankineItems.JUGLONE_BUCKET.get()) {
            if (state.get(LEVEL) == 0) {
                worldIn.setBlockState(pos, state.with(TapBarrelBlock.FLUID, TapBarrelFluids.JUGLONE).with(LEVEL, 1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else if (state.get(LEVEL) < 6 && state.get(FLUID).equals(TapBarrelFluids.JUGLONE)) {
                worldIn.setBlockState(pos, state.with(LEVEL, state.get(LEVEL)+1),3);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 0.5F + worldIn.rand.nextFloat() * 0.4F);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else {
            return ActionResultType.FAIL;
        }
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FLUID, TapBarrelFluids.WATER).with(LEVEL,0);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLUID, LEVEL);
    }

    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return blockState.get(LEVEL);
    }

}
