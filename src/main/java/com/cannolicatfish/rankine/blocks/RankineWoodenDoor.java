package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RankineWoodenDoor extends DoorBlock
{
    public RankineWoodenDoor() {
        super(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
