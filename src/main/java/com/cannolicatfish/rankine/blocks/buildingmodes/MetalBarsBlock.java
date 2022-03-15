package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MetalBarsBlock extends PaneBlock {
    //public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 4);
    int alloyColor;

    public MetalBarsBlock(int color) {
        super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion());
        this.alloyColor = color;
        //this.setDefaultState(this.stateContainer.getBaseState().with(MODE, 0));
    }

    public int getColor() {
        return this.alloyColor;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        BlockState state1 =this.defaultBlockState();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
        //    state1 = state1.with(MODE, Math.min(BuildingToolItem.getBuildingMode(heldItem),4));
        }
        IBlockReader iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.south();
        BlockPos blockpos3 = blockpos.west();
        BlockPos blockpos4 = blockpos.east();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return state1.setValue(NORTH, this.attachsTo(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH))).setValue(SOUTH, this.attachsTo(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.NORTH))).setValue(WEST, this.attachsTo(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.EAST))).setValue(EAST, this.attachsTo(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.WEST))).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

    }

    //protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    //    builder.add(MODE);
    //}

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof PaneBlock) {
            if (!side.getAxis().isHorizontal()) {
                return true;
            }

            if (state.getValue(PROPERTY_BY_DIRECTION.get(side)) && adjacentBlockState.getValue(PROPERTY_BY_DIRECTION.get(side.getOpposite()))) {
                return true;
            }
        }

        return super.skipRendering(state, adjacentBlockState, side);
    }
}
