package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;

public class RankineWoodenPressurePlate extends PressurePlateBlock {
    public RankineWoodenPressurePlate(BlockSetType blockSetType) {
        super(Sensitivity.EVERYTHING, Properties.of(Material.WOOD).sound(SoundType.WOOD).noCollission().strength(0.5F),blockSetType);
    }
}