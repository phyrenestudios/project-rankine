package com.cannolicatfish.rankine.items.totems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CobblingTotemItem extends Item {
    public CobblingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_cobbling.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        stack.setDamageValue(stack.getMaxDamage()-1);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        Direction opp = context.getClickedFace();
        BlockPos pos = context.getClickedPos().relative(opp);
        if ((worldIn.getBlockState(pos).isAir() || worldIn.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) && context.getPlayer() != null && context.getItemInHand().getDamageValue() < context.getItemInHand().getMaxDamage() - 1) {
            worldIn.setBlock(pos, Blocks.COBBLESTONE.defaultBlockState(),3);
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
            if (context.getItemInHand().getDamageValue() < context.getItemInHand().getMaxDamage()) {
                context.getItemInHand().hurtAndBreak(1,context.getPlayer(),(p_220040_1_) -> {
                    p_220040_1_.broadcastBreakEvent(context.getHand());
                });
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }
}
