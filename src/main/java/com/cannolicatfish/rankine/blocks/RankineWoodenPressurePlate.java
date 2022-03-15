package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.PressurePlateBlock.Sensitivity;

public class RankineWoodenPressurePlate extends PressurePlateBlock {
    public RankineWoodenPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F));
    }
}