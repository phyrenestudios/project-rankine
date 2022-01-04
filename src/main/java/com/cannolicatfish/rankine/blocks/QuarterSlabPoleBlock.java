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

public class QuarterSlabPoleBlock extends QuarterSlabBlock {
    public static final BooleanProperty POLE = BooleanProperty.create("pole");

    public QuarterSlabPoleBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 2).with(POLE,false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE,POLE);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.or(Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,4*state.get(SIZE),16.0D), state.get(POLE).booleanValue() ? Block.makeCuboidShape(4,4*state.get(SIZE),4,12,16,12) : VoxelShapes.empty());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(SIZE, Math.min(3, BuildingToolItem.getBuildingMode(heldItem)+1)).with(POLE,context.getWorld().getBlockState(context.getPos().up()).getBlock() instanceof MetalPoleBlock || context.getWorld().getBlockState(context.getPos().up()).getBlock() instanceof WallBlock );
        }
        return this.getDefaultState();
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (Direction.UP == facing) {
            return facingState.getBlock() instanceof MetalPoleBlock || facingState.getBlock() instanceof WallBlock ? stateIn.with(POLE,true): stateIn.with(POLE,false);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
