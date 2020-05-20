package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ColoredGoldHoe extends HoeItem {
    private int type;
    public ColoredGoldHoe(Item.Properties builder, int type) {
        super(ItemTier.GOLD, -3.0F, builder);
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
            stack.addEnchantment(Enchantments.MENDING, 1);
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
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack hoe = new ItemStack(this.getItem());
            if (type == 1) // Rose Gold
            {
                hoe.addEnchantment(Enchantments.EFFICIENCY, 2);
            }

            if (type == 2) // White Gold
            {
                hoe.addEnchantment(Enchantments.FORTUNE, 2);
            }

            if (type == 3) // Green Gold
            {
                hoe.addEnchantment(Enchantments.MENDING, 1);
            }

            if (type == 4) // Blue Gold
            {
                hoe.addEnchantment(Enchantments.UNBREAKING, 2);
            }

            if (type == 5) // Purple Gold
            {
                hoe.addEnchantment(Enchantments.SILK_TOUCH, 1);
            }
            items.add(hoe);
        }
    }
}

