package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;

public class RankineWoodenPressurePlate extends PressurePlateBlock {
    public RankineWoodenPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F));
    }
}