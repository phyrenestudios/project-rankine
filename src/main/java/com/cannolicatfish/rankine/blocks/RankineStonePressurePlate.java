package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;

public class RankineStonePressurePlate extends PressurePlateBlock {
    public RankineStonePressurePlate() {
        super(Sensitivity.MOBS, Properties.of(Material.STONE).sound(SoundType.STONE).noCollission().strength(0.5F), BlockSetType.STONE);
    }
}