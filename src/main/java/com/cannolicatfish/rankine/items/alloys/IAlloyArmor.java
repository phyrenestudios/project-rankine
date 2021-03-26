package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public interface IAlloyArmor {
    PeriodicTableUtils utils = PeriodicTableUtils.getInstance();

    default int getAlloyDurability(String comp, AlloyUtils alloy)
    {
        return utils.calcDurability(getElements(comp),getPercents(comp)) + alloy.getDurabilityBonus();
    }

    default int getAlloyEnchantability(String comp, AlloyUtils alloy) {
        return utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloy.getEnchantabilityBonus();
    }

    default int getAlloyDamageReduceAmount(String comp, AlloyUtils alloy, EquipmentSlotType type)
    {
        return utils.calcDamageReduceAmount(getElements(comp),getPercents(comp),type) ;
    }

    default int getAlloyArmorToughness(String comp, AlloyUtils alloy)
    {
        return utils.calcArmorToughness(getElements(comp),getPercents(comp));
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

    AlloyUtils returnAlloyUtils();
}
