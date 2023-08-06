package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class CoarseSoilBlock extends Block {

    public CoarseSoilBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL));
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (ToolActions.HOE_TILL == toolAction) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getSoilBlock().defaultBlockState();
        } else if (ToolActions.SHOVEL_FLATTEN == toolAction) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getPathBlock().defaultBlockState();
        }
        return null;
    }

}
