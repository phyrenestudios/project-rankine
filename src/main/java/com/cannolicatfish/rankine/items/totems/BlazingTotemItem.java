package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.compatibility.AlexMobs;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class BlazingTotemItem extends Item {
    public BlazingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("item.rankine.totem_of_blazing.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
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
                        MobEffect lavaVision = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("alexsmobs:lava_vision"));
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
