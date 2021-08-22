package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class AlloyEnchantmentHandlerOld {
    static AlloyEnchantmentHandlerOld EMPTY = new AlloyEnchantmentHandlerOld();
    private final List<ResourceLocation> axeEnchants;
    private final List<ResourceLocation> hammerEnchants;
    private final List<ResourceLocation> hoeEnchants;
    private final List<ResourceLocation> pickaxeEnchants;
    private final List<ResourceLocation> shovelEnchants;
    private final List<ResourceLocation> spearEnchants;
    private final List<ResourceLocation> swordEnchants;
    private final int start;
    private final int interval;
    private final int maxLvl;
    public AlloyEnchantmentHandlerOld(List<ResourceLocation> axeEnchantsIn, List<ResourceLocation> hammerEnchantsIn, List<ResourceLocation> hoeEnchantsIn,
                                      List<ResourceLocation> pickaxeEnchantsIn, List<ResourceLocation> shovelEnchantsIn, List<ResourceLocation> spearEnchantsIn,
                                      List<ResourceLocation> swordEnchantsIn, int start, int interval, int maxLvl) {
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

    public AlloyEnchantmentHandlerOld(List<ResourceLocation> axeEnchantsIn, List<ResourceLocation> hammerEnchantsIn, List<ResourceLocation> hoeEnchantsIn,
                                      List<ResourceLocation> pickaxeEnchantsIn, List<ResourceLocation> shovelEnchantsIn, List<ResourceLocation> spearEnchantsIn,
                                      List<ResourceLocation> swordEnchantsIn) {
        this(axeEnchantsIn,hammerEnchantsIn,hoeEnchantsIn,pickaxeEnchantsIn,shovelEnchantsIn,spearEnchantsIn,swordEnchantsIn,15,5,5);
    }

    public AlloyEnchantmentHandlerOld() {
        this(Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),15,5,5);
    }

    public AlloyEnchantmentHandlerOld(int start, int interval, int maxLvl) {
        this(Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),Collections.emptyList(),start,interval,maxLvl);
    }

    public List<ResourceLocation> getEnchantmentsForItem(ItemStack stack) {
        return getEnchantmentsForItem(stack.getItem());
    }

    public List<ResourceLocation> getEnchantmentsForItem(Item item) {
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
        if (en.isCurse()) {
            return 1;
        }
        else if (enchantability < start)
        {
            return 0;
        } else if (enchantability >= start + interval*maxLvl) {
            return Math.min(maxLvl,en.getMaxLevel());
        }
        for (int i = 1; i < maxLvl + 1; i++) {
            if (enchantability >= start + (interval*(i-1)) && enchantability < start + (interval*i)) {
                return Math.min(i,en.getMaxLevel());
            }
        }
        return 0;
    }
}
