package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;

public class RankineWoodTrapDoor extends TrapDoorBlock {
    public RankineWoodTrapDoor() {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(3.0F));
    }
}
