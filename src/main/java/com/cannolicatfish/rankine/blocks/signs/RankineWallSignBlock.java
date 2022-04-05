package com.cannolicatfish.rankine.blocks.signs;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class RankineWallSignBlock extends WallSignBlock {
    public RankineWallSignBlock(WoodType type) {
        super(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), type);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new RankineSignTile();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
