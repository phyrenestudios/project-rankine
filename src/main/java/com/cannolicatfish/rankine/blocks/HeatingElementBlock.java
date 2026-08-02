package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class HeatingElementBlock extends DirectionalBlock {
    // DirectionalBlock#codec() is abstract in 1.21.1. The ctor carries an extra `radius` field on top of the
    // properties, so this needs a RecordCodecBuilder rather than simpleCodec.
    public static final MapCodec<HeatingElementBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("radius").forGetter(b -> b.radius),
                    propertiesCodec()
            ).apply(instance, HeatingElementBlock::new)
    );

    @Override
    public MapCodec<? extends HeatingElementBlock> codec() {
        return CODEC;
    }

    static HashMap<Block, Block> MeltMap = new HashMap<>();
    static {
        MeltMap.put(Blocks.SNOW, Blocks.AIR);
        MeltMap.put(Blocks.SNOW_BLOCK, Blocks.AIR);
        MeltMap.put(Blocks.POWDER_SNOW, Blocks.AIR);
        MeltMap.put(Blocks.ICE, Blocks.WATER);
        MeltMap.put(Blocks.FROSTED_ICE, Blocks.WATER);
        MeltMap.put(Blocks.PACKED_ICE, Blocks.WATER);
        MeltMap.put(Blocks.BLUE_ICE, Blocks.WATER);
        MeltMap.put(RankineBlocks.METEORIC_ICE.get(), Blocks.WATER);
        MeltMap.put(RankineBlocks.DRY_ICE.get(), RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get());
        MeltMap.put(RankineBlocks.FROZEN_METEORITE.get(), RankineBlocks.METEORITE.get());
    }
    int radius;

    public HeatingElementBlock(int radius, Properties properties) {
        super(properties);
        this.radius = radius;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void neighborChanged(BlockState p_60509_, Level levelIn, BlockPos posIn, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        if (!levelIn.isClientSide() && levelIn.hasNeighborSignal(posIn)) {
            Block blk;
            for (BlockPos b : BlockPos.betweenClosed(posIn.offset(-radius,-radius,-radius), posIn.offset(radius,radius,radius))) {
                blk = levelIn.getBlockState(b).getBlock();
                if (MeltMap.containsKey(blk)) {
                    levelIn.setBlockAndUpdate(b, MeltMap.get(blk).defaultBlockState());
                }
            }
            levelIn.playSound(null, posIn, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.8f, 1.0f);
        }
        super.neighborChanged(p_60509_, levelIn, posIn, p_60512_, p_60513_, p_60514_);
    }

}