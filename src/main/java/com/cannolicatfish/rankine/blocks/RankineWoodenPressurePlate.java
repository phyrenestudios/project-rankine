package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineWoodenPressurePlate): Forge/vanilla's explicit PressurePlateBlock.Sensitivity constructor
// argument is gone; sensitivity (EVERYTHING vs MOBS-only) now derives from BlockSetType#pressurePlateSensitivity().
// BlockSetType.OAK resolves to EVERYTHING, matching the old Sensitivity.EVERYTHING behavior 1:1.
public class RankineWoodenPressurePlate extends PressurePlateBlock {
    public static final MapCodec<RankineWoodenPressurePlate> CODEC = simpleCodec(RankineWoodenPressurePlate::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<PressurePlateBlock> codec() {
        return (MapCodec<PressurePlateBlock>) (MapCodec<?>) CODEC;
    }

    public RankineWoodenPressurePlate(Properties properties) {
        super(BlockSetType.OAK, properties);
    }

    public RankineWoodenPressurePlate() {
        this(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F));
    }
}