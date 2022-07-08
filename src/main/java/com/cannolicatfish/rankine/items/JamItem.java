package com.cannolicatfish.rankine.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class JamItem extends Item {
    public JamItem(Properties properties) {
        super(properties);
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }


    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
        if (entityLiving instanceof Player && !((Player)entityLiving).getAbilities().instabuild) {
            itemstack.shrink(1);
            ((Player) entityLiving).getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
        }
        return itemstack;
    }
}
