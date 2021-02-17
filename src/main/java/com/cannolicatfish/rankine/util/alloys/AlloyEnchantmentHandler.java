package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AlloyEnchantmentHandler {
    static AlloyEnchantmentHandler EMPTY = new AlloyEnchantmentHandler();
    private final List<Enchantment> axeEnchants;
    private final List<Enchantment> hammerEnchants;
    private final List<Enchantment> hoeEnchants;
    private final List<Enchantment> pickaxeEnchants;
    private final List<Enchantment> shovelEnchants;
    private final List<Enchantment> spearEnchants;
    private final List<Enchantment> swordEnchants;
    private final int start;
    private final int interval;
    private final int maxLvl;
    public AlloyEnchantmentHandler(List<Enchantment> axeEnchantsIn, List<Enchantment> hammerEnchantsIn, List<Enchantment> hoeEnchantsIn,
                                   List<Enchantment> pickaxeEnchantsIn, List<Enchantment> shovelEnchantsIn, List<Enchantment> spearEnchantsIn,
                                   List<Enchantment> swordEnchantsIn, int start, int interval, int maxLvl) {
        this.axeEnchants = axeEnchantsIn;
        this.hammerEnchants = hammerEnchantsIn;
        this.hoeEnchants = hoeEnchantsIn;
        this.pickaxeEnchants = pickaxeEnchantsIn;
        this.shovelEnchants = shovelEnchantsIn;
        this.spearEnchants = spearEnchantsIn;
        this.swordEnchants = swordEnchantsIn;
        this.start = start;
        this.interval = interval;
        this.maxLvl = maxLvl;
    }

    public AlloyEnchantmentHandler(List<Enchantment> axeEnchantsIn, List<Enchantment> hammerEnchantsIn, List<Enchantment> hoeEnchantsIn,
                                   List<Enchantment> pickaxeEnchantsIn, List<Enchantment> shovelEnchantsIn, List<Enchantment> spearEnchantsIn,
                                   List<Enchantment> swordEnchantsIn) {
        this(axeEnchantsIn,hammerEnchantsIn,hoeEnchantsIn,pickaxeEnchantsIn,shovelEnchantsIn,spearEnchantsIn,swordEnchantsIn,15,5,5);
    }
    public AlloyEnchantmentHandler() {
        this(Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),15,5,5);
    }

    public AlloyEnchantmentHandler(int start, int interval, int maxLvl) {
        this(Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),start,interval,maxLvl);
    }

    public List<Enchantment> getEnchantmentsForItem(ItemStack stack) {
        return getEnchantmentsForItem(stack.getItem());
    }

    public List<Enchantment> getEnchantmentsForItem(Item item) {
        if (item instanceof AxeItem)
        {
            return this.axeEnchants;
        } else if (item instanceof HammerItem) {
            return this.hammerEnchants;
        } else if (item instanceof HoeItem) {
            return this.hoeEnchants;
        } else if (item instanceof PickaxeItem) {
            return this.pickaxeEnchants;
        } else if (item instanceof ShovelItem) {
            return this.shovelEnchants;
        } else if (item instanceof SpearItem) {
            return this.spearEnchants;
        } else if (item instanceof SwordItem) {
            return this.swordEnchants;
        }
        return Collections.emptyList();
    }

    public int returnEnchantmentLevel(Enchantment en, int enchantability) {
        int lvl = 1;
        if (enchantability < start)
        {
            return 0;
        }
        for (int i = 1; i < maxLvl + 1; i++) {
            if (enchantability >= start + (interval*i) && enchantability < start + (interval*i+1)) {
                return Math.min(lvl,en.getMaxLevel());
            }
            lvl += 1;
        }
        return Math.min(lvl,en.getMaxLevel());
    }
}
