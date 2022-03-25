package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class QuarterSlabPoleBlock extends QuarterSlabBlock {
    public static final BooleanProperty POLE = BooleanProperty.create("pole");

    public QuarterSlabPoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 2).setValue(POLE,false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE,POLE);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(0.0D,0.0D,0.0D,16.0D,4*state.getValue(SIZE),16.0D), state.getValue(POLE).booleanValue() ? Block.box(4,4*state.getValue(SIZE),4,12,16,12) : Shapes.empty());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(SIZE, Math.min(3, BuildingToolItem.getBuildingMode(heldItem)+1)).setValue(POLE,context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof MetalPoleBlock || context.getLevel().getBlockState(context.getClickedPos().above()).getBlock() instanceof WallBlock );
        }
        return this.defaultBlockState();
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (Direction.UP == facing) {
            return facingState.getBlock() instanceof MetalPoleBlock || facingState.getBlock() instanceof WallBlock ? stateIn.setValue(POLE,true): stateIn.setValue(POLE,false);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
