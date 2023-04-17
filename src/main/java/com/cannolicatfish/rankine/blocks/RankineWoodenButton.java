package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;

public class RankineWoodenButton extends ButtonBlock {
    public RankineWoodenButton(BlockSetType blockSetType) {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F), blockSetType, 30, true);
    }
}

