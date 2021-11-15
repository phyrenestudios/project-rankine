package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public interface IAlloyArrow extends IAlloyItem{

    default int getAlloyArrowDamage(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("durability");
        } else {
            return 1;
        }
    }
}
