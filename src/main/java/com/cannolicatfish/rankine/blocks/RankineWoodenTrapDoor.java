package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;

public class RankineWoodenTrapDoor extends TrapDoorBlock {
    public RankineWoodenTrapDoor(BlockSetType blockSetType) {
        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(3.0F).noOcclusion(), blockSetType);
    }
}
