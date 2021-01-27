package com.cannolicatfish.rankine.items.pendants;


import com.cannolicatfish.rankine.init.RankineAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpeedPendantItem extends Item{
    public SpeedPendantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            ModifiableAttributeInstance att = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (player.getHeldItemOffhand().getItem() == this && !att.hasModifier(RankineAttributes.SPEED_PENDANT_MS)) {
                att.applyNonPersistentModifier(RankineAttributes.SPEED_PENDANT_MS);
            } else if (player.getHeldItemOffhand().getItem() != this && att.hasModifier(RankineAttributes.SPEED_PENDANT_MS)) {
                att.removeModifier(RankineAttributes.SPEED_PENDANT_MS);
            }
        }
    }
}
