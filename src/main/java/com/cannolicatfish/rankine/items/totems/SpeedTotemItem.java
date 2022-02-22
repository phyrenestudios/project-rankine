package com.cannolicatfish.rankine.items.totems;


import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SpeedTotemItem extends Item{
    public SpeedTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.rankine.totem_of_timesaving.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            ModifiableAttributeInstance att = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (player.getHeldItemOffhand().getItem() == this && !att.hasModifier(RankineAttributes.SWIFTNESS_TOTEM)) {
                att.applyNonPersistentModifier(RankineAttributes.SWIFTNESS_TOTEM);
            } else if (player.getHeldItemOffhand().getItem() != this && att.hasModifier(RankineAttributes.SWIFTNESS_TOTEM)) {
                att.removeModifier(RankineAttributes.SWIFTNESS_TOTEM);
            }
        }
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
