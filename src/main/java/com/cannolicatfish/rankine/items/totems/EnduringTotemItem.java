package com.cannolicatfish.rankine.items.totems;


import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EnduringTotemItem extends Item{
    public EnduringTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.rankine.totem_of_enduring.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            ModifiableAttributeInstance att = player.getAttribute(Attributes.MAX_HEALTH);
            if (player.getHeldItemOffhand().getItem() == this && !att.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
                att.applyPersistentModifier(RankineAttributes.ENDURING_TOTEM);
            } else if (player.getHeldItemOffhand().getItem() != this && att.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
                att.removeModifier(RankineAttributes.ENDURING_TOTEM);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.playSound(RankineSoundEvents.TOTEM_OF_ENDURING_USE.get(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 1200);
        playerIn.addStat(Stats.ITEM_USED.get(this));
        playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION,100, 0));
        return ActionResult.resultSuccess(itemstack);
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
}
