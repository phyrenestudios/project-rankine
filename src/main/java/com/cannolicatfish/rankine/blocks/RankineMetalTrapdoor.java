package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

// PORT-NOTE: Material is gone -> .mapColor(MapColor.METAL) (Material.METAL's implicit color). TrapDoorBlock now
// requires a BlockSetType; BlockSetType.IRON reproduces the 1.18.2 Material.METAL behavior (no hand opening,
// iron sounds), so the explicit .sound(SoundType.METAL) is now redundant but kept for parity.
public class RankineMetalTrapdoor extends TrapDoorBlock {
    public static final MapCodec<RankineMetalTrapdoor> CODEC = MapCodec.unit(RankineMetalTrapdoor::new);

    @Override
    public MapCodec<? extends RankineMetalTrapdoor> codec() {
        return CODEC;
    }

    public RankineMetalTrapdoor() {
        super(BlockSetType.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion());
    }
}
