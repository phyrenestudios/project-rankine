package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SheetmetalFacadeBlock extends Block {
    int alloyColor;

    public SheetmetalFacadeBlock(int color) {
        super(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(4.0F, 10.0F).sound(SoundType.METAL));
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }


}
