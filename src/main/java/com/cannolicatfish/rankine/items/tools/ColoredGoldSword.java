package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ColoredGoldSword extends SwordItem {
    private int type;
    public ColoredGoldSword(Item.Properties builder, int type) {
        super(ItemTier.GOLD, 3, -2.4F, builder);
        this.type = type;
    }
    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (type == 1) // Rose Gold
        {
            stack.addEnchantment(Enchantments.SHARPNESS, 2);
        }

        if (type == 2) // White Gold
        {
            stack.addEnchantment(Enchantments.LOOTING, 2);
        }

        if (type == 3) // Green Gold
        {
            stack.addEnchantment(Enchantments.MENDING, 1);
        }

        if (type == 4) // Blue Gold
        {
            stack.addEnchantment(Enchantments.UNBREAKING, 2);
        }

        if (type == 5) // Purple Gold
        {
            stack.addEnchantment(Enchantments.KNOCKBACK, 2);
        }
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.itemGroup) {
            ItemStack sword = new ItemStack(this.getItem());
            if (type == 1) // Rose Gold
            {
                sword.addEnchantment(Enchantments.SHARPNESS, 2);
            }

            if (type == 2) // White Gold
            {
                sword.addEnchantment(Enchantments.LOOTING, 2);
            }

            if (type == 3) // Green Gold
            {
                sword.addEnchantment(Enchantments.MENDING, 1);
            }

            if (type == 4) // Blue Gold
            {
                sword.addEnchantment(Enchantments.UNBREAKING, 2);
            }

            if (type == 5) // Purple Gold
            {
                sword.addEnchantment(Enchantments.KNOCKBACK, 2);
            }
            items.add(sword);
        }
    }
}