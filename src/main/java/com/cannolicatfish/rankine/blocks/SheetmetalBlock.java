package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class SheetmetalBlock extends Block implements SimpleWaterloggedBlock {
    int alloyColor;

    public SheetmetalBlock(int color) {
        super(Properties.of(Material.METAL, MaterialColor.NONE).requiresCorrectToolForDrops().strength(4.0F, 10.0F).sound(SoundType.METAL));
        this.alloyColor = color;
    }

    public int getColor() {
        return this.alloyColor;
    }


}
