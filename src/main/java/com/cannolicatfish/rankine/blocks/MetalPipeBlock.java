package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MetalPipeBlock extends Block {

    private static final Direction[] FACING_VALUES = Direction.values();
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (directions) -> {
        directions.put(Direction.NORTH, NORTH);
        directions.put(Direction.EAST, EAST);
        directions.put(Direction.SOUTH, SOUTH);
        directions.put(Direction.WEST, WEST);
        directions.put(Direction.UP, UP);
        directions.put(Direction.DOWN, DOWN);
    });
    protected final VoxelShape[] shapes;

    public MetalPipeBlock(float apothem, Properties properties) {
        super(properties);
        this.shapes = this.makeShapes(apothem);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    private VoxelShape[] makeShapes(float apothem) {
        float f = apothem;
        float f1 = 1 - apothem;
        VoxelShape voxelshape = Block.box((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[FACING_VALUES.length];

        for(int i = 0; i < FACING_VALUES.length; ++i) {
            Direction direction = FACING_VALUES[i];
            avoxelshape[i] = Shapes.box(0.5D + Math.min((double)(-apothem), (double)direction.getStepX() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getStepY() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getStepZ() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepX() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepY() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepZ() * 0.5D));
        }

        VoxelShape[] avoxelshape1 = new VoxelShape[64];

        for(int k = 0; k < 64; ++k) {
            VoxelShape voxelshape1 = voxelshape;

            for(int j = 0; j < FACING_VALUES.length; ++j) {
                if ((k & 1 << j) != 0) {
                    voxelshape1 = Shapes.or(voxelshape1, avoxelshape[j]);
                }
            }

            avoxelshape1[k] = voxelshape1;
        }

        return avoxelshape1;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.shapes[this.getShapeIndex(state)];
    }

    protected int getShapeIndex(BlockState state) {
        int i = 0;

        for(int j = 0; j < FACING_VALUES.length; ++j) {
            if (state.getValue(FACING_TO_PROPERTY_MAP.get(FACING_VALUES[j]))) {
                i |= 1 << j;
            }
        }

        return i;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.makeConnections(context.getLevel(), context.getClickedPos());
    }

    public BlockState makeConnections(BlockGetter blockReader, BlockPos pos) {
        return this.defaultBlockState()
                .setValue(DOWN, isAttachable(blockReader.getBlockState(pos.below())))
                .setValue(NORTH, isAttachable(blockReader.getBlockState(pos.north())))
                .setValue(EAST, isAttachable(blockReader.getBlockState(pos.east())))
                .setValue(SOUTH, isAttachable(blockReader.getBlockState(pos.south())))
                .setValue(WEST, isAttachable(blockReader.getBlockState(pos.west())))
                .setValue(UP, isAttachable(blockReader.getBlockState(pos.above())));
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(levelIn, currentPos)) {
            levelIn.getBlockTicks().willTickThisTick(currentPos, this);
            return super.updateShape(stateIn, facing, facingState, levelIn, currentPos, facingPos);
        } else {
            return stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), levelIn.isStateAtPosition(facingPos, this::isAttachable));
        }
    }

    private boolean isAttachable(BlockState stateIn) {
        return stateIn.is(this) || stateIn.is(RankineBlocks.GROUND_TAP.get()) || stateIn.is(RankineBlocks.FLOOD_GATE.get());
    }

}
