package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.block.AbstractBlock.Properties;

public class TapLineBlock extends Block {
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

    public TapLineBlock(float apothem, Properties properties) {
        super(properties);
        this.shapes = this.makeShapes(apothem);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE));
    }

    private VoxelShape[] makeShapes(float apothem) {
        float f = 0.5F - apothem;
        float f1 = 0.5F + apothem;
        VoxelShape voxelshape = Block.box((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[FACING_VALUES.length];

        for(int i = 0; i < FACING_VALUES.length; ++i) {
            Direction direction = FACING_VALUES[i];
            avoxelshape[i] = VoxelShapes.box(0.5D + Math.min((double)(-apothem), (double)direction.getStepX() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getStepY() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getStepZ() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepX() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepY() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getStepZ() * 0.5D));
        }

        VoxelShape[] avoxelshape1 = new VoxelShape[64];

        for(int k = 0; k < 64; ++k) {
            VoxelShape voxelshape1 = voxelshape;

            for(int j = 0; j < FACING_VALUES.length; ++j) {
                if ((k & 1 << j) != 0) {
                    voxelshape1 = VoxelShapes.or(voxelshape1, avoxelshape[j]);
                }
            }

            avoxelshape1[k] = voxelshape1;
        }

        return avoxelshape1;
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getLevel(), context.getClickedPos());
    }

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.below()).getBlock();
        Block block1 = blockReader.getBlockState(pos.above()).getBlock();
        BlockState bs1 = blockReader.getBlockState(pos.above());
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();
        return this.defaultBlockState().setValue(DOWN, block == this || block == RankineBlocks.FLOOD_GATE.get())
                .setValue(NORTH, block2 == this || block2 == RankineBlocks.FLOOD_GATE.get())
                .setValue(EAST, block3 == this || block3 == RankineBlocks.FLOOD_GATE.get())
                .setValue(SOUTH, block4 == this || block4 == RankineBlocks.FLOOD_GATE.get())
                .setValue(WEST, block5 == this || block5 == RankineBlocks.FLOOD_GATE.get())
                .setValue(UP, block1 == this || (block1 == RankineBlocks.TREE_TAP.get()));
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            boolean flag = false;
            Block fsb = facingState.getBlock();
            switch (facing) {
                case UP:
                    flag = fsb == this || (fsb == RankineBlocks.TREE_TAP.get());
                    break;
                case DOWN:
                case NORTH:
                case SOUTH:
                case EAST:
                case WEST:
                    flag = fsb == this || fsb.is(RankineBlocks.FLOOD_GATE.get());
                    break;
            }
            return stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), flag);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
