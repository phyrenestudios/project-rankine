package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.material.Material;

public class RankineMetalTrapdoor extends TrapDoorBlock {
    public RankineMetalTrapdoor() {
        super(Block.Properties.create(Material.IRON).setRequiresTool().hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid());
    }
}
