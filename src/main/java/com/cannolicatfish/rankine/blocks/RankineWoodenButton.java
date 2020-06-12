package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

public class RankineWoodenButton extends WoodButtonBlock {
    public RankineWoodenButton() {
        super(Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
    }
}

