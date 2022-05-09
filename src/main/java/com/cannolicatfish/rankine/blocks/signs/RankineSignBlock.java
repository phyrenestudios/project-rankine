package com.cannolicatfish.rankine.blocks.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;

public class RankineSignBlock extends StandingSignBlock {
    public RankineSignBlock(WoodType type) {
        super(BlockBehaviour.Properties.of(Material.WOOD, Blocks.OAK_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new RankineSignBlockEntity(p_154556_,p_154557_);
    }

}
