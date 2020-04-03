package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemToxin extends Item {
    private static int type;
    public ItemToxin(String registryName, int type) {
        super(new Item.Properties().group(ProjectRankine.setup.itemGroup).maxStackSize(64));
        this.type = type;
        setRegistryName(ProjectRankine.MODID, registryName);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (type == 0) //Mercury
        {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WEAKNESS, 7 * 20, 0));
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 7 * 20, 0));
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.BLINDNESS, 7 * 20, 0));
            if (isSelected == true)
            {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 7 * 20, 0));
            }
        }
        if (type == 1)
        {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.WITHER, 7 * 20, 0));
        }

    }
}
