package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineWoodenTrapDoor extends TrapDoorBlock {
    public RankineWoodenTrapDoor() {
        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
