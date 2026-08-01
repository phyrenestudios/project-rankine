package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SoilBlock extends Block {
    public static final MapCodec<SoilBlock> CODEC = MapCodec.unit(SoilBlock::new);

    @Override
    public MapCodec<? extends SoilBlock> codec() {
        return CODEC;
    }

    public SoilBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level worldIn = context.getLevel();
        //if (WorldgenUtils.isWet((ISeedReader) worldIn, context.getPos())) {
        //    worldIn.setBlockState(context.getPos(), VanillaIntegration.soil_mud_map.get(((BlockItem) context.getItem().getItem()).getBlock()).getDefaultState(), 2);
       // }
        return super.getStateForPlacement(context);
    }
}
