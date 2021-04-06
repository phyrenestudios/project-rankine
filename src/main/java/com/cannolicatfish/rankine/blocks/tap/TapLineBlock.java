package com.cannolicatfish.rankine.blocks.tap;

import com.cannolicatfish.rankine.blocks.states.TapLineShapes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

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
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE).with(UP, Boolean.FALSE).with(DOWN, Boolean.FALSE));
    }

    private VoxelShape[] makeShapes(float apothem) {
        float f = 0.125F - apothem;
        float f1 = 0.125F + apothem;
        VoxelShape voxelshape = Block.makeCuboidShape((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[FACING_VALUES.length];

        for(int i = 0; i < FACING_VALUES.length; ++i) {
            Direction direction = FACING_VALUES[i];
            avoxelshape[i] = VoxelShapes.create(0.5D + Math.min((double)(-apothem), (double)direction.getXOffset() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getYOffset() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getZOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getXOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getYOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getZOffset() * 0.5D));
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

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.down()).getBlock();
        Block block1 = blockReader.getBlockState(pos.up()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();
        return this.getDefaultState().with(DOWN, block == this).with(UP, block1 == this || block1 == RankineBlocks.TREE_TAP.get()).with(NORTH, block2 == this || block2 == RankineBlocks.TAP_BARREL.get()).with(EAST, block3 == this || block3 == RankineBlocks.TAP_BARREL.get()).with(SOUTH, block4 == this || block4 == RankineBlocks.TAP_BARREL.get()).with(WEST, block5 == this || block5 == RankineBlocks.TAP_BARREL.get());
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            boolean flag = facingState.getBlock() == this || facingState.getBlock().matchesBlock(RankineBlocks.TREE_TAP.get()) || facingState.getBlock().matchesBlock(RankineBlocks.TAP_BARREL.get());
            return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), flag);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
