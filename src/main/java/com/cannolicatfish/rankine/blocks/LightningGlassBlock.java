package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;

public class LightningGlassBlock extends TransparentBlock {
    public static final MapCodec<LightningGlassBlock> CODEC = simpleCodec(LightningGlassBlock::new);
    public static final BooleanProperty GLOWING = BooleanProperty.create("glowing");

    @Override
    public MapCodec<? extends LightningGlassBlock> codec() {
        return CODEC;
    }

    public LightningGlassBlock(Properties p_53640_) {
        super(p_53640_);
        this.registerDefaultState(this.defaultBlockState().setValue(GLOWING, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLOWING);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stackIn, BlockState stateIn, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult p_60508_) {
        if (!stateIn.getValue(GLOWING) && stackIn.is(Tags.Items.DUSTS_GLOWSTONE)) {
            if (!playerIn.isCreative()) stackIn.shrink(1);
            playerIn.swing(handIn);
            levelIn.setBlockAndUpdate(posIn, stateIn.setValue(GLOWING, true));
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(GLOWING)) {
            return 10;
        }
        return super.getLightEmission(state, level, pos);
    }

}
