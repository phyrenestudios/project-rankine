package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class RankineLanternBlock extends LanternBlock {
    int alloyColor;

    public RankineLanternBlock(int color) {
        super(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 15).noOcclusion());
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }
}
