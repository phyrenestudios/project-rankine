package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FloodGateBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FloodGateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (p_60541_.getValue(WATERLOGGED)) {
            p_60544_.getFluidTicks().willTickThisTick(p_60545_, Fluids.WATER);
        }

        return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public static boolean placeFluid(Level worldIn, BlockPos pos, BlockState bs) {
        if (worldIn.getBlockState(pos.below()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.below(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.north()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.north(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.east()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.east(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.south()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.south(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.west()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.west(),bs,3);
            return true;
        }
        return false;
    }

    public static boolean inInfiniteSource(Level worldIn, BlockPos pos) {
        int waterSides = 0;
        for (Direction d : Direction.values()) {
            if (worldIn.getBlockState(pos.relative(d)).is(Blocks.WATER)) {
                waterSides += 1;
            }
        }
        if (waterSides > 1) {
            if (Config.GENERAL.DISABLE_WATER.get()) {
                return pos.getY() > WorldgenUtils.waterTableHeight(worldIn, pos);
            } else {
                return true;
            }
        }
        return false;
    }

}
