package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.entities.DryMortarItemEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class DryMortarItem extends Item {

    public DryMortarItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Drop in water to make mortar").mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        DryMortarItemEntity result = new DryMortarItemEntity(location.world,location.getPosX(),location.getPosY(),location.getPosZ(), itemstack);
        result.setPickupDelay(40);
        result.setMotion(location.getMotion());
        return result;
    }
}
