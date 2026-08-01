package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineStonePressurePlate): Forge/vanilla's explicit PressurePlateBlock.Sensitivity constructor
// argument is gone; pressure-plate sensitivity (EVERYTHING vs MOBS-only) is now derived from the BlockSetType
// itself via BlockSetType#pressurePlateSensitivity(). BlockSetType.STONE resolves to MOBS, matching the old
// Sensitivity.MOBS behavior 1:1.
public class RankineStonePressurePlate extends PressurePlateBlock {
    public static final MapCodec<RankineStonePressurePlate> CODEC = simpleCodec(RankineStonePressurePlate::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<PressurePlateBlock> codec() {
        return (MapCodec<PressurePlateBlock>) (MapCodec<?>) CODEC;
    }

    public RankineStonePressurePlate(Properties properties) {
        super(BlockSetType.STONE, properties);
    }

    public RankineStonePressurePlate() {
        this(Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).noCollission().strength(0.5F));
    }
}