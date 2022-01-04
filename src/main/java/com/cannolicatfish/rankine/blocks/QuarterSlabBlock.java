package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class QuarterSlabBlock extends Block {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,3);

    public QuarterSlabBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 2));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,4*state.get(SIZE),16.0D);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(SIZE, Math.min(3, BuildingToolItem.getBuildingMode(heldItem)+1));
        }
        return this.getDefaultState();
    }

}
