package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlloyItem extends Item {

    String defComp;
    public AlloyItem(String composition, Properties properties) {
        super(properties);
        this.defComp = composition;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (getComposition(stack).size() > 0 && defComp.equals("80Hg-20Au") && nbt != null && !nbt.getString("nameAdd").isEmpty() && !nbt.getString("nameAdd").equals("false")) {
            String name = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
            String[] sp = name.split(" ");
            if (sp.length > 0) {
                name = sp[sp.length - 1];
            }
            return new StringTextComponent(stack.getTag().getString("nameAdd") + " " + name);
        }
        return super.getDisplayName(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getComposition(stack).size() != 0)
        {
            String comp = getComposition(stack).getCompound(0).get("comp").getString();
            tooltip.add(new StringTextComponent(comp).mergeStyle(TextFormatting.GRAY));
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

    public String getDefComp() {
        return this.defComp;
    }

    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
    public ItemStack getAlloyItemStack(AlloyData p_92111_0_) {
        ItemStack itemstack = new ItemStack(this.getItem());
        addAlloy(itemstack, p_92111_0_);
        return itemstack;
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


    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineMetals) {
            items.add(getAlloyItemStack(new AlloyData(defComp)));
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (getComposition(stack).size() > 0 && defComp.equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getComposition(stack).size() > 0 && defComp.equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
