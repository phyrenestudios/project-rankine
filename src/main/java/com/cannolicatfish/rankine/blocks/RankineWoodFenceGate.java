package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RankineWoodFenceGate extends FenceGateBlock {
    public RankineWoodFenceGate() {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F));
    }
}
