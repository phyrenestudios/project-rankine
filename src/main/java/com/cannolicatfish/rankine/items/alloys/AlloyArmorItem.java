package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class AlloyArmorItem extends ArmorItem implements IAlloyArmor {
    private final AlloyUtils alloy;
    public AlloyArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, AlloyUtils alloy, Properties builderIn) {
        super(materialIn, slot, builderIn);
        this.alloy = alloy;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (getComposition(repair).size() != 0 && getComposition(toRepair).size() != 0 && (repair.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || repair.getItem() == this)) {
            INBT s = getComposition(repair).getCompound(0).get("comp");
            INBT r = getComposition(toRepair).getCompound(0).get("comp");
            return s != null && s.equals(r);
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getDamage(stack) * 1f / this.getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy);
    }

    @Override
    public AlloyUtils returnAlloyUtils() {
        return this.alloy;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (returnCompositionString(stack,this.alloy) != null)
        {
            if (!Screen.hasShiftDown())
            {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown())
            {
                tooltip.add((new StringTextComponent("Composition: " + returnCompositionString(stack,this.alloy))).mergeStyle(alloy.getAlloyGroupColor()));
                tooltip.add((new StringTextComponent("Durability: " + (getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy) - getDamage(stack)) + "/" + getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.DARK_GREEN));
                tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineMetals) {
            items.add(getAlloyItemStack(new AlloyData(this.alloy.getDefComposition()),this.getItem()));
        }
    }
}
