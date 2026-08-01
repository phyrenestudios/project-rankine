package com.cannolicatfish.rankine.blocks.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ShortGrassBlock extends BushBlock implements BonemealableBlock, net.neoforged.neoforge.common.IShearable {
    public static final MapCodec<ShortGrassBlock> CODEC = simpleCodec(ShortGrassBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public ShortGrassBlock(BlockBehaviour.Properties properties) {
        // 1.21.1: BlockBehaviour#getOffsetType() no longer exists; the XYZ offset (previously an override here) is baked into the Properties.
        super(properties.offsetType(BlockBehaviour.OffsetType.XYZ));
    }

    @Override
    public MapCodec<ShortGrassBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        // 1.20.3+ renamed minecraft:grass (the one-tall plant) to minecraft:short_grass (Blocks.SHORT_GRASS).
        if (Blocks.SHORT_GRASS.defaultBlockState().canSurvive(worldIn, pos) && worldIn.isEmptyBlock(pos.above())) {
            worldIn.setBlock(pos,Blocks.SHORT_GRASS.defaultBlockState(),2);
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }
}
