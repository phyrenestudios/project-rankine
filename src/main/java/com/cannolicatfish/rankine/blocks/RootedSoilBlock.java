package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class RootedSoilBlock extends Block implements BonemealableBlock {

    public RootedSoilBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.ROOTED_DIRT));
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (ToolActions.HOE_TILL == toolAction) {
            if (!simulate && !context.getLevel().isClientSide) {
                Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(), new ItemStack(Items.HANGING_ROOTS));
            }
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getSoilBlock().defaultBlockState();
        } else if (ToolActions.SHOVEL_FLATTEN == toolAction) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getPathBlock().defaultBlockState();
        }
        return null;
    }

    public boolean isValidBonemealTarget(LevelReader p_256100_, BlockPos p_255943_, BlockState p_255655_, boolean p_256455_) {
        return p_256100_.getBlockState(p_255943_.below()).isAir();
    }

    public boolean isBonemealSuccess(Level p_221979_, RandomSource p_221980_, BlockPos p_221981_, BlockState p_221982_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_221974_, RandomSource p_221975_, BlockPos p_221976_, BlockState p_221977_) {
        p_221974_.setBlockAndUpdate(p_221976_.below(), Blocks.HANGING_ROOTS.defaultBlockState());
    }
}
