package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;

public class RankineStonePressurePlate extends PressurePlateBlock {
    public RankineStonePressurePlate() {
        super(Sensitivity.MOBS, Properties.of(Material.STONE).sound(SoundType.STONE).noCollission().strength(0.5F));
    }
}