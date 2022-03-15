package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineStoneButton extends StoneButtonBlock {
    public RankineStoneButton() {
        super(Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.STONE));
    }
}

