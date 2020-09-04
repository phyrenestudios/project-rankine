package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.naming.directory.ModificationItem;
import java.util.Random;


public class RemedyItem extends Item {
    public RemedyItem(Properties properties) {
        super(properties);
    }

    Random rand = new Random();

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;

        if (!worldIn.isRemote) {
            if (stack.getItem() == ModItems.BISMUTH_REMEDY) {
                entityLiving.removePotionEffect(Effects.NAUSEA);
            }
            if (stack.getItem() == ModItems.CEDAR_REMEDY) {
                entityLiving.curePotionEffects(stack);
                //entityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 20, 0));
            }

            //chance for remedies to fail
            if (rand.nextFloat() < 0.2) {
                entityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20 * 20, 0));
            } else {
                if (stack.getItem() == ModItems.HERBAL_REMEDY) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 15 * 20, 1));
                }
                if (stack.getItem() == ModItems.BERRY_REMEDY) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.HASTE, 30 * 20, 0));
                }
                if (stack.getItem() == ModItems.WINTERGREEN_REMEDY) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 15 * 20, 0));
                }
                if (stack.getItem() == ModItems.SILVER_REMEDY) {
                    entityLiving.removePotionEffect(Effects.POISON);
                    entityLiving.addPotionEffect(new EffectInstance(Effects.REGENERATION, 10 * 20, 0));
                }
                if (stack.getItem() == ModItems.COBALT_REMEDY) {
                    entityLiving.removePotionEffect(Effects.WEAKNESS);
                    entityLiving.addPotionEffect(new EffectInstance(Effects.STRENGTH, 10 * 20, 0));
                }
            }
        }

        if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }

        if (playerentity != null) {
            playerentity.addStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        if (playerentity == null || !playerentity.abilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerentity != null) {
                playerentity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }

}
