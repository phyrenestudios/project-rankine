package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class SoilPathBlock extends DirtPathBlock {
    SoilBlocks soilType;

    public SoilPathBlock(SoilBlocks soilType) {
        super(Block.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
        this.soilType = soilType;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (ToolActions.HOE_TILL == toolAction && (context.getLevel().getBlockState(context.getClickedPos().above()).isAir())) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getFarmlandBlock().defaultBlockState();
        }
        return null;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, soilType.getSoilBlock().defaultBlockState(), worldIn, pos));
    }
}
