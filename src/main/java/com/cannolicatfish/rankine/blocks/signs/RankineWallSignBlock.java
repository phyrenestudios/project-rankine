package com.cannolicatfish.rankine.blocks.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineWallSignBlock extends WallSignBlock {
    public RankineWallSignBlock(WoodType type) {
        super(Properties.of(Material.WOOD, Blocks.OAK_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new RankineSignBlockEntity(p_154556_,p_154557_);
    }

}
