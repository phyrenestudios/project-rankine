package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.ArrayList;
import java.util.List;

public class CoalForgeRecipes {
    private static final CoalForgeRecipes INSTANCE = new CoalForgeRecipes();

    public static CoalForgeRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getResult(ItemStack input1, ItemStack input2, ItemStack template)
    {
        return ModRecipes.getForgingOutput(input1,input2,template);
    }

    public List<PeriodicTableUtils.Element> getElements(String c)
    {
        //String c = getComposition(stack).getCompound(0).get("comp").getString();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        String[] comp = c.split("-");
        List<PeriodicTableUtils.Element> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("[^A-Za-z]+", "");
            list.add(utils.getElementBySymbol(str));
        }
        return list;
    }

    public List<Integer> getPercents(String c)
    {
        String[] comp = c.split("-");
        List<Integer> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("\\D+", "");
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public List<Enchantment> getEnchantments(String c, AlloyUtils alloy, Item item)
    {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        List<Enchantment> enchantments = new ArrayList<>();
        List<Enchantment> elementEn = utils.getEnchantments(getElements(c),getPercents(c));
        for (Enchantment e: elementEn)
        {
            if (e != null)
            {
                enchantments.add(e);
            }
        }
        Enchantment en = alloy.getEnchantmentBonus(item);
        if (en != null)
        {
            enchantments.add(en);
        }
        return enchantments;
    }
}
