package com.cannolicatfish.rankine.blocks.sedimentfan;

import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanTile;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SedimentFanBlock extends BaseEntityBlock {
    public SedimentFanBlock(Properties properties) {
        super(properties);
    }

    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new SedimentFanTile(p_153215_,p_153216_);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockStateIn, BlockEntityType<T> blockEntityTypeIn) {
        return worldIn.isClientSide ? null : createTickerHelper(blockEntityTypeIn, RankineBlockEntityTypes.SEDIMENT_FAN.get(), SedimentFanTile::tick);
    }
}
