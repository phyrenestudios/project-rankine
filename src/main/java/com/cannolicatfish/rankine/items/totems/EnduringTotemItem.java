package com.cannolicatfish.rankine.items.totems;


import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class EnduringTotemItem extends Item{
    public EnduringTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("item.rankine.totem_of_enduring.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player) {
            Player player = (Player) entityIn;
            AttributeInstance att = player.getAttribute(Attributes.MAX_HEALTH);
            if (player.getOffhandItem().getItem() == this && !att.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
                att.addPermanentModifier(RankineAttributes.ENDURING_TOTEM);
            } else if (player.getOffhandItem().getItem() != this && att.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
                att.removeModifier(RankineAttributes.ENDURING_TOTEM);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.playNotifySound(RankineSoundEvents.TOTEM_OF_ENDURING_USE.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 1200);
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        playerIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION,100, 0));
        return InteractionResultHolder.success(itemstack);
    }



    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            stack.enchant(Enchantments.VANISHING_CURSE,1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if ((group == CreativeModeTab.TAB_SEARCH || group == ProjectRankine.setup.rankineTools) && Config.GENERAL.PENDANT_CURSE.get()) {
            ItemStack stack = new ItemStack(this);
            stack.enchant(Enchantments.VANISHING_CURSE,1);
            items.add(stack);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
