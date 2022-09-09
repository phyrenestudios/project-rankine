package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class GlazedPorcelainBlock extends BuildingModeBlock {
    int alloyColor;

    public GlazedPorcelainBlock(int color) {
        super(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.0F, 3.0F));
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }

    @Override
    public int getMaxStyles() {
        return 8;
    }
}
