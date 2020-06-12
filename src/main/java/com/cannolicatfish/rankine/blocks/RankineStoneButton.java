package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

public class RankineStoneButton extends StoneButtonBlock {
    public RankineStoneButton() {
        super(Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(2.0F).sound(SoundType.STONE));
    }
}

