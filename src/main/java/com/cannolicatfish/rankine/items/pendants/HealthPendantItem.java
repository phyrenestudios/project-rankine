package com.cannolicatfish.rankine.items.pendants;


import com.cannolicatfish.rankine.init.RankineAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealthPendantItem extends Item{
    public HealthPendantItem(Properties properties) {
        super(properties);
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            ModifiableAttributeInstance att = player.getAttribute(Attributes.MAX_HEALTH);
            if (player.getHeldItemOffhand().getItem() == this && !att.hasModifier(RankineAttributes.HEALTH_PENDANT)) {
                att.applyNonPersistentModifier(RankineAttributes.HEALTH_PENDANT);
            } else if (player.getHeldItemOffhand().getItem() != this && att.hasModifier(RankineAttributes.HEALTH_PENDANT)) {
                att.removeModifier(RankineAttributes.HEALTH_PENDANT);
            }
        }
    }

}
