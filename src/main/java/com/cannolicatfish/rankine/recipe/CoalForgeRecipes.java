package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.*;
import javafx.util.Pair;
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

    public ItemStack getResult(ItemStack input1, ItemStack input2, ItemStack template) {
        ItemStack result;
        if (input1.getItem() == Items.STICK && (input2.getItem() == ModItems.BRONZE_ALLOY || input2.getItem() == ModItems.ALUMINUM_BRONZE_ALLOY)) {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BRONZE_PICKAXE);
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BRONZE_AXE);
                return result;
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.BRONZE_SHOVEL);
                return result;
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.BRONZE_HOE);
                return result;
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.BRONZE_SWORD);
                return result;
            }
            if (template.getItem() == ModItems.HAMMER_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 5)
            {
                result = new ItemStack(ModItems.BRONZE_HAMMER);
                return result;
            }
            if (template.getItem() == ModItems.SPEAR_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BRONZE_SPEAR);
                return result;
            }

        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.ROSE_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_PICKAXE);
                PeriodicTableUtils utils = new PeriodicTableUtils();
                AlloyUtils alloyUtils = new RoseGoldAlloyUtils();
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                String comp = input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();
                for (Enchantment e: getEnchantments(comp, alloyUtils,result.getItem()))
                {
                    result.addEnchantment(e,alloyUtils.getEnchantmentLevel(e,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
                }
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_AXE);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_HOE);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_SWORD);
                result.addEnchantment(Enchantments.SHARPNESS, 2);
            }
        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.WHITE_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.WHITE_GOLD_PICKAXE);
                PeriodicTableUtils utils = new PeriodicTableUtils();
                AlloyUtils alloyUtils = new WhiteGoldAlloyUtils();
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                String comp = input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();
                for (Enchantment e: getEnchantments(comp, alloyUtils,result.getItem()))
                {
                    result.addEnchantment(e,alloyUtils.getEnchantmentLevel(e,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
                }
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.WHITE_GOLD_AXE);
                result.addEnchantment(Enchantments.FORTUNE, 2);
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.WHITE_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.FORTUNE, 2);
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.WHITE_GOLD_HOE);
                result.addEnchantment(Enchantments.FORTUNE, 2);
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.WHITE_GOLD_SWORD);
                result.addEnchantment(Enchantments.LOOTING, 2);
            }
        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.GREEN_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.GREEN_GOLD_PICKAXE);
                PeriodicTableUtils utils = new PeriodicTableUtils();
                AlloyUtils alloyUtils = new GreenGoldAlloyUtils();
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                String comp = input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();
                for (Enchantment e: getEnchantments(comp, alloyUtils,result.getItem()))
                {
                    result.addEnchantment(e,alloyUtils.getEnchantmentLevel(e,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
                }
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.GREEN_GOLD_AXE);
                result.addEnchantment(Enchantments.MENDING, 1);
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.GREEN_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.MENDING, 1);
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.GREEN_GOLD_HOE);
                result.addEnchantment(Enchantments.MENDING, 1);
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.GREEN_GOLD_SWORD);
                result.addEnchantment(Enchantments.MENDING, 1);
            }
        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.BLUE_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BLUE_GOLD_PICKAXE);
                PeriodicTableUtils utils = new PeriodicTableUtils();
                AlloyUtils alloyUtils = new BlueGoldAlloyUtils();
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                String comp = input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();
                for (Enchantment e: getEnchantments(comp, alloyUtils,result.getItem()))
                {
                    result.addEnchantment(e,alloyUtils.getEnchantmentLevel(e,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
                }
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BLUE_GOLD_AXE);
                result.addEnchantment(Enchantments.UNBREAKING, 2);
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.BLUE_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.UNBREAKING, 2);
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.BLUE_GOLD_HOE);
                result.addEnchantment(Enchantments.UNBREAKING, 2);
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.BLUE_GOLD_SWORD);
                result.addEnchantment(Enchantments.UNBREAKING, 2);
            }
        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.PURPLE_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.PURPLE_GOLD_PICKAXE);
                PeriodicTableUtils utils = new PeriodicTableUtils();
                AlloyUtils alloyUtils = new PurpleGoldAlloyUtils();
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                String comp = input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();
                for (Enchantment e: getEnchantments(comp, alloyUtils,result.getItem()))
                {
                    result.addEnchantment(e,alloyUtils.getEnchantmentLevel(e,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
                }
                return result;
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.PURPLE_GOLD_AXE);
                result.addEnchantment(Enchantments.SILK_TOUCH, 1);
                return result;
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.PURPLE_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.SILK_TOUCH, 1);
                return result;
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.PURPLE_GOLD_HOE);
                result.addEnchantment(Enchantments.SILK_TOUCH, 1);
                return result;
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.PURPLE_GOLD_SWORD);
                result.addEnchantment(Enchantments.KNOCKBACK, 2);
                return result;
            }
        }

        return ItemStack.EMPTY;
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
