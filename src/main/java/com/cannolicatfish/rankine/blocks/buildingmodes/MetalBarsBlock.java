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
        super(AbstractBlock.Properties.create(Material.IRON, MaterialColor.AIR).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).notSolid());
        this.alloyColor = color;
        //this.setDefaultState(this.stateContainer.getBaseState().with(MODE, 0));
    }

    public int getColor() {
        return this.alloyColor;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        BlockState state1 =this.getDefaultState();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
        //    state1 = state1.with(MODE, Math.min(BuildingToolItem.getBuildingMode(heldItem),4));
        }
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.south();
        BlockPos blockpos3 = blockpos.west();
        BlockPos blockpos4 = blockpos.east();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return state1.with(NORTH, this.canAttachTo(blockstate, blockstate.isSolidSide(iblockreader, blockpos1, Direction.SOUTH))).with(SOUTH, this.canAttachTo(blockstate1, blockstate1.isSolidSide(iblockreader, blockpos2, Direction.NORTH))).with(WEST, this.canAttachTo(blockstate2, blockstate2.isSolidSide(iblockreader, blockpos3, Direction.EAST))).with(EAST, this.canAttachTo(blockstate3, blockstate3.isSolidSide(iblockreader, blockpos4, Direction.WEST))).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);

    }

    //protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    //    builder.add(MODE);
    //}

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof PaneBlock) {
            if (!side.getAxis().isHorizontal()) {
                return true;
            }

            if (state.get(FACING_TO_PROPERTY_MAP.get(side)) && adjacentBlockState.get(FACING_TO_PROPERTY_MAP.get(side.getOpposite()))) {
                return true;
            }
        }

        return super.isSideInvisible(state, adjacentBlockState, side);
    }
}
