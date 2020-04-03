package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RankineWoodSlab extends SlabBlock {
    public RankineWoodSlab() {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F));
    }
}
