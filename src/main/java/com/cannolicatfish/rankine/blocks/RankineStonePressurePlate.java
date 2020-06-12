package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RankineStonePressurePlate extends PressurePlateBlock {
    public RankineStonePressurePlate() {
        super(Sensitivity.MOBS, Properties.create(Material.ROCK).sound(SoundType.STONE).doesNotBlockMovement().hardnessAndResistance(2.0F));
    }
}