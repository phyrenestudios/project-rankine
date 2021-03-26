package com.cannolicatfish.rankine.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class TapBucketItem extends MilkBucketItem {

    public int type;
    public TapBucketItem(int type, Properties builder) {
        super(builder);
        this.type = type;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote && this.type == 0 && worldIn.getRandom().nextFloat() <= 0.5) {
            entityLiving.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 0));
        } else if (!worldIn.isRemote && this.type == 1) {
            entityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
            entityLiving.addPotionEffect(new EffectInstance(Effects.POISON, 100, 0));
        } else if (!worldIn.isRemote && this.type == 2) {
            entityLiving.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 1));
        }

        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }


}
