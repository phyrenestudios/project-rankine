package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

// PORT-NOTE: Material.DIRT -> .mapColor(MapColor.DIRT) (DIRT implied no extra behavior flags).
public class GrassySoilPathBlock extends DirtPathBlock {
    // DirtPathBlock.codec() is not wildcarded (returns the concrete MapCodec<DirtPathBlock>), so this subclass's
    // codec() must match that exact signature; the cast is safe since generics are erased at runtime.
    public static final MapCodec<GrassySoilPathBlock> CODEC = MapCodec.unit(GrassySoilPathBlock::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<DirtPathBlock> codec() {
        return (MapCodec<DirtPathBlock>) (MapCodec<?>) CODEC;
    }

    public GrassySoilPathBlock() {
        super(Block.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
    }

    @Override
    protected void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, RankineLists.SOIL_BLOCKS.get(RankineLists.PATH_BLOCKS.indexOf(state.getBlock())).defaultBlockState(), worldIn, pos));
    }
}
