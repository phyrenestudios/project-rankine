package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.entities.DryMortarItemEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class DryMortarItem extends Item {

    public DryMortarItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent("Drop in water to make mortar").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack) {
        DryMortarItemEntity result = new DryMortarItemEntity(location.level,location.getX(),location.getY(),location.getZ(), itemstack);
        result.setPickUpDelay(40);
        result.setDeltaMovement(location.getDeltaMovement());
        return result;
    }
}
