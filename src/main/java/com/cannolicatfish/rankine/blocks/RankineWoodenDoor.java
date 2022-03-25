package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class RankineWoodenDoor extends DoorBlock
{
    public RankineWoodenDoor() {
        super(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
