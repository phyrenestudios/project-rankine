package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.compatibility.AlexMobs;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class BlazingTotemItem extends Item {
    public BlazingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.rankine.totem_of_blazing.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            stack.addEnchantment(Enchantments.VANISHING_CURSE,1);
        }
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if ((group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) && Config.GENERAL.PENDANT_CURSE.get()) {
            ItemStack stack = new ItemStack(this.getItem());
            stack.addEnchantment(Enchantments.VANISHING_CURSE,1);
            items.add(stack);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity living = ((LivingEntity) entityIn);
            if (living.getFireTimer() > 0 && (living.getHeldItemMainhand().equals(stack) || living.getHeldItemOffhand().equals(stack))) {
                worldIn.setBlockState(living.getPosition(), AbstractFireBlock.getFireForPlacement(worldIn, living.getPosition()));
                if ((living.isInLava() && living.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null) || living.getHealth() < living.getMaxHealth() * .25f) {
                    EffectInstance lavaSave = new EffectInstance(Effects.FIRE_RESISTANCE,500);
                    EffectInstance regen = new EffectInstance(Effects.REGENERATION,100,3);
                    if (AlexMobs.isInstalled()){
                        Effect lavaVision = ForgeRegistries.POTIONS.getValue(new ResourceLocation("alexsmobs:lava_vision"));
                        if (lavaVision != null) {
                            EffectInstance lavaVisionEffect = new EffectInstance(lavaVision,500);
                            living.addPotionEffect(lavaVisionEffect);
                        }
                    }
                    living.addPotionEffect(lavaSave);
                    living.addPotionEffect(regen);
                    stack.shrink(1);
                }
            }

        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
