package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ColoredGoldShovel extends ShovelItem {
    private int type;
    public ColoredGoldShovel(Properties builder, int type) {
        super(ItemTier.GOLD, 1.5F, -3.0F, builder);
        this.type = type;
    }
    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (type == 1) // Rose Gold
        {
            stack.addEnchantment(Enchantments.EFFICIENCY, 2);
        }

        if (type == 2) // White Gold
        {
            stack.addEnchantment(Enchantments.FORTUNE, 2);
        }

        if (type == 3) // Green Gold
        {
            stack.addEnchantment(Enchantments.MENDING, 2);
        }

        if (type == 4) // Blue Gold
        {
            stack.addEnchantment(Enchantments.UNBREAKING, 2);
        }

        if (type == 5) // Purple Gold
        {
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
        }
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.itemGroup) {
            ItemStack shovel = new ItemStack(this.getItem());
            if (type == 1) // Rose Gold
            {
                shovel.addEnchantment(Enchantments.EFFICIENCY, 2);
            }

            if (type == 2) // White Gold
            {
                shovel.addEnchantment(Enchantments.FORTUNE, 2);
            }

            if (type == 3) // Green Gold
            {
                shovel.addEnchantment(Enchantments.MENDING, 2);
            }

            if (type == 4) // Blue Gold
            {
                shovel.addEnchantment(Enchantments.UNBREAKING, 2);
            }

            if (type == 5) // Purple Gold
            {
                shovel.addEnchantment(Enchantments.SILK_TOUCH, 1);
            }
            items.add(shovel);
        }
    }
}
