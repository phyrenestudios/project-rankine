package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.PressurePlateBlock.Sensitivity;

public class RankineStonePressurePlate extends PressurePlateBlock {
    public RankineStonePressurePlate() {
        super(Sensitivity.MOBS, Properties.of(Material.STONE).sound(SoundType.STONE).noCollission().strength(0.5F));
    }
}