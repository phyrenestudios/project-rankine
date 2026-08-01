package com.cannolicatfish.rankine.items.totems;


import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

// PORT-TODO(init): creative-tab entries — old fillItemCategory added a Curse of Vanishing enchanted
// variant (search tab + rankineTools tab) when Config.GENERAL.PENDANT_CURSE is set; move to RankineCreativeTabs.
public class EnduringTotemItem extends Item{
    public EnduringTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_enduring.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player) {
            Player player = (Player) entityIn;
            AttributeInstance att = player.getAttribute(Attributes.MAX_HEALTH);
            if (player.getOffhandItem().getItem() == this && !att.hasModifier(RankineAttributes.ENDURING_TOTEM.id())) {
                att.addPermanentModifier(RankineAttributes.ENDURING_TOTEM);
            } else if (player.getOffhandItem().getItem() != this && att.hasModifier(RankineAttributes.ENDURING_TOTEM.id())) {
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
            stack.enchant(worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.VANISHING_CURSE),1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
