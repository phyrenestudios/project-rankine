package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.cannolicatfish.rankine.util.colors.AlloyItemColor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class AlloyArmorItem extends DyeableArmorItem implements IAlloyArmor, IDyeableArmorItem {
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
        return getDamage(stack) * 1f / this.getAlloyArmorDurability(returnCompositionString(stack,this.alloy),this.alloy,this.slot);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyArmorDurability(returnCompositionString(stack,this.alloy),this.alloy,this.slot);
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
                tooltip.add((new StringTextComponent("Durability: " + (getAlloyArmorDurability(returnCompositionString(stack,this.alloy),this.alloy,this.slot) - getDamage(stack)) + "/" + getAlloyArmorDurability(returnCompositionString(stack,this.alloy),this.alloy,this.slot))).mergeStyle(TextFormatting.DARK_GREEN));
                tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.GRAY));
                if (Config.ALLOYS.ALLOY_CORROSION.get())
                {
                    tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack,this.alloy) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
                if (Config.ALLOYS.ALLOY_HEAT.get())
                {
                    tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack,this.alloy) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineMetals) {
            items.add(getAlloyItemStack(new AlloyData(this.alloy.getDefComposition()),this.getItem()));
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au") && nbt != null && !nbt.getString("nameAdd").isEmpty() && !nbt.getString("nameAdd").equals("false")) {
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
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }
        if (!hasColor(stack)) {
            setColor(stack,new AlloyItemColor().getColor(stack,0));
        }
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }
        if (!hasColor(stack)) {
            setColor(stack,new AlloyItemColor().getColor(stack,0));
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    @Override
    public void removeColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("color")) {
            compoundnbt.remove("color");
        }

    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color", color);
    }

}
