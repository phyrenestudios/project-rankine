package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

public class LightningGlassBlock extends GlassBlock {
    public static final BooleanProperty GLOWING = BooleanProperty.create("glowing");

    public LightningGlassBlock(Properties p_53640_) {
        super(p_53640_);
        this.registerDefaultState(this.defaultBlockState().setValue(GLOWING, Boolean.FALSE));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLOWING);
    }

    @Override
    public InteractionResult use(BlockState stateIn, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult p_60508_) {
        if (!stateIn.getValue(GLOWING) && playerIn.getItemInHand(handIn).is(Tags.Items.DUSTS_GLOWSTONE)) {
            if (!playerIn.isCreative()) playerIn.getItemInHand(handIn).shrink(1);
            playerIn.swing(handIn);
            levelIn.setBlockAndUpdate(posIn, stateIn.setValue(GLOWING, true));
        }

        return super.use(stateIn, levelIn, posIn, playerIn, handIn, p_60508_);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(GLOWING)) {
            return 10;
        }
        return super.getLightEmission(state, level, pos);
    }

}
