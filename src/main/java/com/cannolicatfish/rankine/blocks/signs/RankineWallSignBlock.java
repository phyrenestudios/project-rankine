package com.cannolicatfish.rankine.blocks.signs;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class RankineWallSignBlock extends WallSignBlock {

    public RankineWallSignBlock(Properties props, WoodType type) {
        super(props, type);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new RankineSignTileEntity();
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.createNewTileEntity(world);
    }

}
