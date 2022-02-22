package com.cannolicatfish.rankine.items.totems;

import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CobblingTotemItem extends Item {
    public CobblingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.rankine.totem_of_cobbling.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        stack.setDamage(stack.getMaxDamage()-1);
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        Direction opp = context.getFace();
        BlockPos pos = context.getPos().offset(opp);
        if ((worldIn.getBlockState(pos).isAir() || worldIn.getBlockState(pos).isReplaceable(new BlockItemUseContext(context))) && context.getPlayer() != null && context.getItem().getDamage() < context.getItem().getMaxDamage() - 1) {
            worldIn.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(),3);
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
            if (context.getItem().getDamage() < context.getItem().getMaxDamage()) {
                context.getItem().damageItem(1,context.getPlayer(),(p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(context.getHand());
                });
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }
}
