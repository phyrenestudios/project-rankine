package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineStoneButton extends StoneButtonBlock {
    public RankineStoneButton() {
        super(Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.STONE));
    }
}

