package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShortGrassBlock extends BushBlock implements BonemealableBlock, net.minecraftforge.common.IForgeShearable {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public ShortGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        if (Blocks.GRASS.defaultBlockState().canSurvive(worldIn, pos) && worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlock(pos,Blocks.GRASS.defaultBlockState(),2);
        }
    }

    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XYZ;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }
}
