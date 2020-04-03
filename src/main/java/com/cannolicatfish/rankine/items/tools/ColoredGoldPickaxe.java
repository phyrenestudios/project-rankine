package com.cannolicatfish.rankine.items.tools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
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
            stack.addEnchantment(Enchantments.FORTUNE, 2);
        }
    }

}
