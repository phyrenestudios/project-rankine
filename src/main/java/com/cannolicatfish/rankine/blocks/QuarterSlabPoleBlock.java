package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class QuarterSlabPoleBlock extends QuarterSlabBlock {
    public static final BooleanProperty POLE = BooleanProperty.create("pole");

    public QuarterSlabPoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 2).setValue(POLE,false));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE,POLE);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.or(Block.box(0.0D,0.0D,0.0D,16.0D,4*state.getValue(SIZE),16.0D), state.getValue(POLE).booleanValue() ? Block.box(4,4*state.getValue(SIZE),4,12,16,12) : VoxelShapes.empty());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(SIZE, Math.min(3, BuildingToolItem.getBuildingMode(heldItem)+1)).setValue(POLE,context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof MetalPoleBlock || context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof WallBlock );
        }
        return this.defaultBlockState();
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (Direction.UP == facing) {
            return facingState.getBlock() instanceof MetalPoleBlock || facingState.getBlock() instanceof WallBlock ? stateIn.setValue(POLE,true): stateIn.setValue(POLE,false);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
