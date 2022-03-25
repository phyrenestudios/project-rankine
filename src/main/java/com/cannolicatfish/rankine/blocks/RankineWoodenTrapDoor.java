package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineWoodenTrapDoor extends TrapDoorBlock {
    public RankineWoodenTrapDoor() {
        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion());
    }
}
