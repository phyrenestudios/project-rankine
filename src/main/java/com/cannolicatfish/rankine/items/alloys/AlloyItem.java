package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyItem extends Item {

    public AlloyItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getComposition(stack).size() != 0)
        {
            tooltip.add((new StringTextComponent(getComposition(stack).getCompound(0).get("comp").getString())).applyTextStyle(TextFormatting.GRAY));
        }
    }

    public static ListNBT getComposition(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredComposition", 10) : new ListNBT();
    }

    public static void addAlloy(ItemStack p_92115_0_, AlloyData stack) {
        ListNBT listnbt = getComposition(p_92115_0_);
        boolean flag = true;


        if (flag) {
            CompoundNBT compoundnbt1 = new CompoundNBT();
            compoundnbt1.putString("comp", stack.alloyComposition);
            listnbt.add(compoundnbt1);
        }

        p_92115_0_.getOrCreateTag().put("StoredComposition", listnbt);
    }

    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
    public static ItemStack getAlloyItemStack(AlloyData p_92111_0_) {
        ItemStack itemstack = new ItemStack(ModItems.BRONZE_ALLOY);
        addAlloy(itemstack, p_92111_0_);
        return itemstack;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineMetals) {
            items.add(getAlloyItemStack(new AlloyData("80Cu-20Sn")));
        }
    }


}
