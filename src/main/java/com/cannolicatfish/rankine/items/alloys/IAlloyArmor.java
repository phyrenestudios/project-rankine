package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public interface IAlloyArmor extends IAlloyItem {

    PeriodicTableUtils utils = PeriodicTableUtils.getInstance();

    @Override
    default void createAlloyNBT(ItemStack stack, World worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        if (stack.getTag() != null && stack.getTag().getBoolean("RegenerateAlloy")) {
            stack.getTag().remove("RegenerateAlloy");
        }
        EquipmentSlotType slotType;
        if (stack.getItem() instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) stack.getItem();
            slotType = armor.getEquipmentSlot();
        } else {
            slotType = EquipmentSlotType.CHEST;
        }

        ListNBT alloyData = IAlloyItem.getAlloyNBT(stack);
        List<ElementRecipe> elements = this.getElementRecipes(composition,worldIn);
        List<Integer> percents = this.getPercents(composition);

        CompoundNBT listnbt = new CompoundNBT();
        int dur = 0;
        int ench = 0;
        float cr = 0;
        float hr = 0;
        float tough = 0;
        float kr = 0;

        int hlmin = 0;
        int hlmax = 0;
        for (int i = 0; i < elements.size(); i++) {
            ElementRecipe element = elements.get(i);
            int percentage = percents.get(i);

            dur += element.getDurability(percentage);
            ench += element.getEnchantability(percentage);
            cr += element.getCorrosionResistance(percentage);
            hr += element.getHeatResistance(percentage);
            tough += element.getToughness(percentage);
            kr += element.getKnockbackResistance(percentage);
            int hl = element.getMiningLevel(percentage);

            hlmin = Math.min(hl,hlmin);
            hlmax = Math.max(hl,hlmax);
        }



        if (alloyRecipe != null) {
            Optional<? extends IRecipe<?>> opt = worldIn.getRecipeManager().getRecipe(alloyRecipe);
            if (opt.isPresent()) {
                AlloyingRecipe recipe = (AlloyingRecipe) opt.get();
                dur += recipe.getBonusDurability();
                ench += recipe.getBonusEnchantability();
                cr += recipe.getBonusCorrosionResistance();
                hr += recipe.getBonusHeatResistance();
                tough += recipe.getBonusToughness();
                kr += recipe.getBonusKnockbackResistance();

                int hl = recipe.getBonusMiningLevel();

                hlmin = Math.min(hl,hlmin);
                hlmax = Math.max(hl,hlmax);
            }
        }

        int hl = hlmax - hlmin;

        listnbt.putString("comp",composition);
        if (alloyRecipe != null) {
            listnbt.putString("recipe",alloyRecipe.toString());
        }
        listnbt.putInt("durability",calcArmorDurability(slotType,dur));
        listnbt.putInt("damageResist",calcDamageReduceAmount(slotType,hl));
        listnbt.putInt("enchantability",ench);
        listnbt.putFloat("corrResist",Math.round(cr*100)/100f);
        listnbt.putFloat("heatResist",Math.round(hr*100)/100f);
        listnbt.putFloat("knockbackResist",Math.round(kr*100)/100f);
        listnbt.putFloat("toughness",calcArmorToughness(Math.round(tough*100)/100f));
        alloyData.add(listnbt);
        stack.getOrCreateTag().put("StoredAlloy", listnbt);

        if (nameOverride != null && stack.getTag() != null) {
            stack.getTag().putString("nameOverride",nameOverride);
        }
    }

    default int calcArmorDurability(EquipmentSlotType slotType, int durability)
    {
        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        if (durability <= 100) {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * durability/10f);
        } else {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * (10 + (durability-100)/50f));
        }
    }

    default int calcArmorToughness(float tough)
    {
        if (tough >= 0.4) {
            return 4;
        } else if (tough >= 0.3) {
            return 3;
        } else if (tough >= 0.2){
            return 2;
        } else if (tough >= 0.1){
            return 1;
        } else {
            return 0;
        }
    }

    default int calcDamageReduceAmount(EquipmentSlotType slotType, int hl)
    {
        int base = slotType == EquipmentSlotType.CHEST ? 3 : slotType == EquipmentSlotType.LEGS ? 2 : 1;
        if (slotType == EquipmentSlotType.CHEST || slotType == EquipmentSlotType.LEGS) {
            return Math.min(base + hl,10);
        } else if (slotType == EquipmentSlotType.FEET) {
            if (hl >= 5) {
                return base + 2;
            } else if (hl >= 3) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            if (hl >= 5) {
                return base + 3;
            } else if (hl >= 3) {
                return base + 2;
            } else if (hl >= 1) {
                return base + 1;
            } else {
                return base;
            }
        }
    }

    default int getAlloyArmorDurability(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("durability");
        } else {
            return 1;
        }
    }

    default int getAlloyEnchantability(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("enchantability");
        } else {
            return 0;
        }
    }

    default int getAlloyDamageReduceAmount(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("damageResist");
        } else {
            return 0;
        }
    }

    default int getAlloyArmorToughness(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("toughness");
        } else {
            return 0;
        }
    }

    default float getCorrResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_CORROSION.get())
        {
            return 100;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("corrResist");
        } else {
            return 0;
        }

    }


    default float getHeatResist(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_HEAT.get())
        {
            return 100;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("heatResist");
        } else {
            return 0;
        }
    }

    default int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        boolean memory = false;
        Random rand = new Random();
        int i = 1;
        if (rand.nextFloat() > getHeatResist(stack) && (entityLiving.isInLava() || entityLiving.getFireTimer() > 0 || worldIn.getDimensionKey() == World.THE_NETHER)) {
            i += Config.ALLOYS.ALLOY_HEAT_AMT.get();
            memory = true;
        }
        if ((rand.nextFloat() > getCorrResist(stack) && entityLiving.isWet()))
        {
            i += Config.ALLOYS.ALLOY_CORROSION_AMT.get();
        }
        if (!isEfficient)
        {
            i *= 2;
        }

        if (memory && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SHAPE_MEMORY,stack) >= 1) {
            stack.setDamage(Math.max(stack.getDamage() - i,0));
            i = 0;
        }
        return i;
    }
}
