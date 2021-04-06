package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public interface IAlloyArrow {

    PeriodicTableUtils utils = PeriodicTableUtils.getInstance();

    default float getAlloyArrowDamage(String comp, AlloyUtils alloy) {
        return utils.calcArrowDamage(getElements(comp), getPercents(comp)) + alloy.getAttackDamageBonus() * 0.5f;
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
