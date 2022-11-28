package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

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
    public BlockState updateShape(BlockState p_60541_, Direction directionIn, BlockState p_60543_, LevelAccessor levelIn, BlockPos posIn, BlockPos p_60546_) {
        if (p_60541_.getValue(WATERLOGGED)) {
            levelIn.getFluidTicks().willTickThisTick(posIn, Fluids.WATER);
        } else {
           //if (directionIn == Direction.UP) {
               FluidState fluidstate = levelIn.getFluidState(posIn.above());
               if (fluidstate.isSource() && placeFluid(levelIn, posIn, fluidstate)) {
                   BlockState bs = levelIn.getBlockState(posIn.above());
                   levelIn.setBlock(posIn.above(), bs.hasProperty(BlockStateProperties.WATERLOGGED) ? bs.setValue(BlockStateProperties.WATERLOGGED, false) : Blocks.AIR.defaultBlockState(), 3);
               }
           //}
        }
        return super.updateShape(p_60541_, directionIn, p_60543_, levelIn, posIn, p_60546_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    private static boolean placeFluid(LevelAccessor levelIn, BlockPos  posIn, FluidState fluidIn) {
        if (levelIn.getBlockState(posIn.below()).canBeReplaced(fluidIn.getType())) {
            levelIn.setBlock(posIn.below(), fluidIn.createLegacyBlock(), 3);
            return true;
        }

        Set<BlockPos> checkedBlocks = new HashSet<>();
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(posIn.below(), 1));

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = queue.poll();
            BlockPos blockpos = tuple.getA();
            int dist = tuple.getB();

            for(Direction direction : Direction.values()) {
                if (direction.equals(Direction.UP)) continue;
                BlockPos blockpos1 = blockpos.relative(direction);
                if (checkedBlocks.contains(blockpos1)) continue;
                if (!levelIn.hasChunkAt(blockpos1)) continue;
                if (levelIn.getFluidState(blockpos1).is(fluidIn.getType())) {
                    if (dist < Config.MACHINES.FLOOD_GATE_RANGE.get()) {
                        checkedBlocks.add(blockpos1);
                        queue.add(new Tuple<>(blockpos1, dist + 1));
                    }
                } else if (levelIn.getBlockState(blockpos1).canBeReplaced(fluidIn.getType())) {
                    levelIn.setBlock(blockpos1, fluidIn.createLegacyBlock(), 3);
                    return true;
                }
            }
        }

        return false;
    }


}
