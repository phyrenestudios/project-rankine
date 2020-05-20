package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ColoredGoldPickaxe extends PickaxeItem {
    private int type;
    public ColoredGoldPickaxe(Properties p_i48478_4_, int type) {
        super(ItemTier.GOLD, 1, -2.8F, p_i48478_4_);
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
                ItemStack pick = new ItemStack(this.getItem());
                if (type == 1) // Rose Gold
                {
                    pick.addEnchantment(Enchantments.EFFICIENCY, 2);
                }

                if (type == 2) // White Gold
                {
                    pick.addEnchantment(Enchantments.FORTUNE, 2);
                }

                if (type == 3) // Green Gold
                {
                    pick.addEnchantment(Enchantments.MENDING, 2);
                }

                if (type == 4) // Blue Gold
                {
                    pick.addEnchantment(Enchantments.UNBREAKING, 2);
                }

                if (type == 5) // Purple Gold
                {
                    pick.addEnchantment(Enchantments.SILK_TOUCH, 1);
                }
                items.add(pick);
            }
        }
}
