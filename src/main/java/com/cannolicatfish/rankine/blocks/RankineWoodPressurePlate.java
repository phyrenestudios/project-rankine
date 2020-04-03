package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RankineWoodPressurePlate extends PressurePlateBlock {
    public RankineWoodPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.create(Material.WOOD).sound(SoundType.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5F));
    }
}