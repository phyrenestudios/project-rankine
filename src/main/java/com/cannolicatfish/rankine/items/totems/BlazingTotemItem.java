package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.compatibility.AlexMobs;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

// PORT-TODO(init): creative-tab entries — old fillItemCategory added a Curse of Vanishing enchanted
// variant (search tab + rankineTools tab) when Config.GENERAL.PENDANT_CURSE is set; move to RankineCreativeTabs.
public class BlazingTotemItem extends Item {
    public BlazingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_blazing.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
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

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity living = ((LivingEntity) entityIn);
            if (living.getRemainingFireTicks() > 0 && (living.getMainHandItem().equals(stack) || living.getOffhandItem().equals(stack))) {
                worldIn.setBlockAndUpdate(living.blockPosition(), BaseFireBlock.getState(worldIn, living.blockPosition()));
                if ((living.isInLava() && living.getEffect(MobEffects.FIRE_RESISTANCE) == null) || living.getHealth() < living.getMaxHealth() * .25f) {
                    MobEffectInstance lavaSave = new MobEffectInstance(MobEffects.FIRE_RESISTANCE,500);
                    MobEffectInstance regen = new MobEffectInstance(MobEffects.REGENERATION,100,3);
                    if (AlexMobs.isInstalled()){
                        Holder<MobEffect> lavaVision = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse("alexsmobs:lava_vision")).orElse(null);
                        if (lavaVision != null) {
                            MobEffectInstance lavaVisionEffect = new MobEffectInstance(lavaVision,500);
                            living.addEffect(lavaVisionEffect);
                        }
                    }
                    living.addEffect(lavaSave);
                    living.addEffect(regen);
                    stack.shrink(1);
                }
            }

        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
