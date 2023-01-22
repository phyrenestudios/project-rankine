package com.cannolicatfish.rankine.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BatteryItem extends Item {
    public BatteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 65535;
    }

    private int getTier() {
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("rankine.battery.charge",stack.getMaxDamage()-stack.getDamageValue(),stack.getMaxDamage()).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    public static int getTier(ItemStack stack) {
        if (stack.getItem() instanceof BatteryItem) {
            return ((BatteryItem) stack.getItem()).getTier();
        } else {
            return 0;
        }
    }

    public static boolean hasPowerRequired(ItemStack stack, int powerRequired) {
        if (stack.getItem() instanceof BatteryItem) {
            return stack.getDamageValue() + powerRequired <= stack.getMaxDamage();
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

}
