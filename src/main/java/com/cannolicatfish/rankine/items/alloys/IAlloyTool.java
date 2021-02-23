package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface IAlloyTool {

    PeriodicTableUtils utils = new PeriodicTableUtils();

/*
    default double getDurabilityForDisplay(ItemStack stack,) {
        if (getComposition(stack).size() != 0) {
            return getDamage(stack) * 1f / this.getMaxDamage(stack);
        } else {
            return getDamage(stack) * 1f / this.getTier().getMaxUses();
        }
    }
*/

    default int getAlloyDurability(String comp, AlloyUtils alloy)
    {
        return utils.calcDurability(getElements(comp),getPercents(comp)) + alloy.getDurabilityBonus();
    }

    default float getAlloyEfficiency(String comp, AlloyUtils alloy)
    {
        return utils.calcMiningSpeed(getElements(comp), getPercents(comp)) + alloy.getMiningSpeedBonus();
    }

    default int getAlloyEnchantability(String comp, AlloyUtils alloy) {
        return utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloy.getEnchantabilityBonus();
    }

    default int getAlloyMiningLevel(String comp, AlloyUtils alloy)
    {
        return utils.calcMiningLevel(getElements(comp), getPercents(comp)) + alloy.getMiningLevelBonus();
    }


    default float getAlloyWear(float wmodifier, int currentDurability, int maxDurability) {
        return wmodifier - wmodifier*(((float) maxDurability - (float) currentDurability)/ (float) maxDurability);
    }

    default float getWearModifierMining(float eff)
    {
        return eff * Config.ALLOYS.ALLOY_WEAR_MINING_AMT.get().floatValue();
    }

    default float getWearModifierDmg(float dmg)
    {
        return dmg * Config.ALLOYS.ALLOY_WEAR_DAMAGE_AMT.get().floatValue();

    }


    // modstat should be equal to either efficiency or damage depending on type of tool
    default float getWearAsPercent(float modstat, float wear)
    {
        return (modstat - wear)/modstat * 100;
    }

    default float getAlloyAttackDamage(String comp, AlloyUtils alloy) {
        return utils.calcDamage(getElements(comp), getPercents(comp)) + alloy.getAttackDamageBonus();
    }

    default float getAlloyAttackSpeed(String comp, AlloyUtils alloy) {
        return Math.max(utils.calcAttackSpeed(getElements(comp), getPercents(comp)) + alloy.getAttackSpeedBonus(), 0);
    }

    default float getCorrResist(ItemStack stack, AlloyUtils alloy)
    {
        if (!Config.ALLOYS.ALLOY_CORROSION.get())
        {
            return 100;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return Math.max(Math.min(utils.calcCorrResist(getElements(comp),getPercents(comp)) + alloy.getCorrResistBonus(), 1),0);
        } else
        {
            return alloy.getCorrResistBonus();
        }

    }


    default float getHeatResist(ItemStack stack, AlloyUtils alloy)
    {
        if (!Config.ALLOYS.ALLOY_HEAT.get())
        {
            return 100;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return Math.max(Math.min(utils.calcHeatResist(getElements(comp),getPercents(comp)) + alloy.getHeatResistBonus(),1),0);
        } else
        {
            return alloy.getHeatResistBonus();
        }
    }

    default float getToughness(ItemStack stack, AlloyUtils alloy)
    {
        if (!Config.ALLOYS.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            return utils.calcToughness(getElements(comp),getPercents(comp)) + alloy.getToughnessBonus();
        } else
        {
            return alloy.getToughnessBonus();
        }
    }

    default int calcDurabilityLoss(ItemStack stack, AlloyUtils alloy, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        Random rand = new Random();
        int i = 1;
        float toughness = getToughness(stack,alloy);
        if (toughness > 0 && rand.nextFloat() < toughness)
        {
            i += -1;
        }
        if (toughness < 0 && rand.nextFloat() < Math.abs(toughness))
        {
            i += 1;
        }
        if (rand.nextFloat() > getHeatResist(stack,alloy) && (entityLiving.isInLava() || entityLiving.getFireTimer() > 0 || worldIn.getDimensionKey() == World.THE_NETHER)) {
            i += 1;
        }
        if ((rand.nextFloat() > getCorrResist(stack,alloy) && entityLiving.isWet()))
        {
            i += 1;
        }
        if (!isEfficient)
        {
            i *= 2;
        }
        return i;
    }




    default TextFormatting getWearColor(float wear)
    {
        if (wear >= 80f)
        {
            return TextFormatting.AQUA;
        } else if (wear >= 60f)
        {
            return TextFormatting.GREEN;
        } else if (wear >= 40f)
        {
            return TextFormatting.YELLOW;
        } else if (wear >= 20f)
        {
            return TextFormatting.RED;
        } else{
            return TextFormatting.GRAY;
        }


    }

    default String returnCompositionString(ItemStack stack, AlloyUtils alloy) {
        INBT nbt = getComposition(stack).getCompound(0).get("comp");
        return nbt != null ? nbt.getString() : alloy.getDefComposition();
    }

    default String returnCompositionString(ItemStack stack) {
        INBT nbt = getComposition(stack).getCompound(0).get("comp");
        return nbt != null ? nbt.getString() : "100Fe";
    }

    default ListNBT getComposition(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredComposition", 10) : new ListNBT();
    }

    default void addAlloy(ItemStack p_92115_0_, AlloyData stack) {
        ListNBT listnbt = getComposition(p_92115_0_);

        CompoundNBT compoundnbt1 = new CompoundNBT();
        compoundnbt1.putString("comp", stack.alloyComposition);
        listnbt.add(compoundnbt1);

        p_92115_0_.getOrCreateTag().put("StoredComposition", listnbt);
    }

    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
    default ItemStack getAlloyItemStack(AlloyData alloyData, Item item) {
        ItemStack itemstack = new ItemStack(item);
        addAlloy(itemstack, alloyData);
        return itemstack;
    }

    default List<PeriodicTableUtils.Element> getElements(String c)
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

    default List<Integer> getPercents(String c)
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

    default List<Enchantment> getEnchantments(String c, Item item, AlloyUtils alloy)
    {
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

    AlloyUtils returnAlloyUtils();
}
