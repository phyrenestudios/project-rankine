package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class RankinePodzolBlock extends SnowyDirtBlock {
    public RankinePodzolBlock() {
        super(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.OBSIDIAN).hardnessAndResistance(0.5F).sound(SoundType.GROUND));
    }
}
