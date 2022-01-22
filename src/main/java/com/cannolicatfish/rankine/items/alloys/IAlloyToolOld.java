package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public interface IAlloyToolOld extends IAlloyItem {

    @Override
    default void createAlloyNBT(ItemStack stack, World worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        if (stack.getTag() != null && stack.getTag().getBoolean("RegenerateAlloy")) {
            stack.getTag().remove("RegenerateAlloy");
        }
        ListNBT alloyData = IAlloyItem.getAlloyNBT(stack);
        List<ElementRecipe> elements = this.getElementRecipes(composition,worldIn);
        List<Integer> percents = this.getPercents(composition);

        CompoundNBT listnbt = new CompoundNBT();
        int dur = 0;
        float ms = 0;
        int ench = 0;
        float cr = 0;
        float hr = 0;
        float tough = 0;

        int hlmin = 0;
        int hlmax = 0;
        float dmgmin = 0;
        float dmgmax = 0;
        float asmin = 0;
        float asmax = 0;
        for (int i = 0; i < elements.size(); i++) {
            ElementRecipe element = elements.get(i);
            int percentage = percents.get(i);

            dur += element.getDurability(percentage);
            ms += element.getMiningSpeed(percentage);
            ench += element.getEnchantability(percentage);
            cr += element.getCorrosionResistance(percentage);
            hr += element.getHeatResistance(percentage);
            tough += element.getToughness(percentage);

            int hl = element.getMiningLevel(percentage);
            float dmg = element.getDamage(percentage);
            float as = element.getAttackSpeed(percentage);

            hlmin = Math.min(hl,hlmin);
            hlmax = Math.max(hl,hlmax);
            dmgmin = Math.min(dmg,dmgmin);
            dmgmax = Math.max(dmg,dmgmax);
            asmin = Math.min(as,asmin);
            asmax = Math.max(as,asmax);
        }



        if (alloyRecipe != null) {
            Optional<? extends IRecipe<?>> opt = worldIn.getRecipeManager().getRecipe(alloyRecipe);
            if (opt.isPresent()) {
                AlloyingRecipe recipe = (AlloyingRecipe) opt.get();
                dur += recipe.getBonusDurability();
                ms += recipe.getBonusMiningSpeed();
                ench += recipe.getBonusEnchantability();
                cr += recipe.getBonusCorrosionResistance();
                hr += recipe.getBonusHeatResistance();
                tough += recipe.getBonusToughness();

                int hl = recipe.getBonusMiningLevel();
                float dmg = recipe.getBonusDamage();
                float as = recipe.getBonusAttackSpeed();

                hlmin = Math.min(hlmin,hlmin+hl);
                hlmax = Math.max(hlmax,hlmax+hl);
                dmgmin = Math.min(dmgmin,dmgmin+dmg);
                dmgmax = Math.max(dmgmax,dmgmax+dmg);
                asmin = Math.min(asmin,asmin+as);
                asmax = Math.max(asmax,asmax+as);
            }
        }

        int hl = Math.max(hlmax + hlmin,0);
        float dmg = dmgmax + dmgmin;
        float as = asmax + asmin;
        dur = Math.max(1,dur);
        ench = Math.max(0,ench);
        cr = Math.min(Math.max(0,cr),1);
        hr = Math.min(Math.max(0,hr),1);
        tough = Math.min(Math.max(-1,tough),1);
        listnbt.putString("comp",composition);
        if (alloyRecipe != null) {
            listnbt.putString("recipe",alloyRecipe.toString());
        }
        listnbt.putInt("durability",dur);
        listnbt.putFloat("miningSpeed",Math.round(ms*100)/100f);
        listnbt.putInt("harvestLevel",hl);
        listnbt.putInt("enchantability",ench);
        listnbt.putFloat("attackDamage",Math.round(dmg*100)/100f);
        listnbt.putFloat("attackSpeed",Math.round(as*100)/100f);
        listnbt.putFloat("corrResist",Math.round(cr*100)/100f);
        listnbt.putFloat("heatResist",Math.round(hr*100)/100f);
        listnbt.putFloat("toughness",Math.round(tough*100)/100f);
        alloyData.add(listnbt);
        stack.getOrCreateTag().put("StoredAlloy", listnbt);

        if (nameOverride != null && stack.getTag() != null) {
            stack.getTag().putString("nameOverride",nameOverride);
        }
    }

    default void applyAlloyEnchantments(ItemStack stack, World worldIn) {
        int start = 10;
        int interval = 5;
        int maxLvl = 5;
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null && worldIn.getRecipeManager().getRecipe(rs).isPresent()) {
            AlloyingRecipe recipe = (AlloyingRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
            start = recipe.getMinEnchantability();
            interval = recipe.getEnchantInterval();
            maxLvl = recipe.getMaxEnchantLevelIn();
            for (Enchantment e: AlloyEnchantmentUtils.getAlloyEnchantments(recipe,stack,worldIn))
            {
                int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
                if (enchLvl > 0 && EnchantmentHelper.getEnchantmentLevel(e,stack) == 0) {
                    stack.addEnchantment(e,Math.min(e.getMaxLevel(),enchLvl));
                }
            }
        }
        for (Enchantment e: AlloyEnchantmentUtils.getElementEnchantments(getElementRecipes(IAlloyItem.getAlloyComposition(stack),worldIn),getPercents(IAlloyItem.getAlloyComposition(stack)),stack,worldIn))
        {
            int enchLvl = Math.min(Math.floorDiv(Math.max(getAlloyEnchantability(stack) - start + interval,0),interval),maxLvl);
            if (enchLvl > 0 && EnchantmentHelper.getEnchantmentLevel(e,stack) == 0) {
                stack.addEnchantment(e,Math.min(e.getMaxLevel(),enchLvl));
            }
        }

    }

    default int getAlloyDurability(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("durability");
        } else {
            return 1;
        }

    }

    default float getAlloyEfficiency(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("miningSpeed");
        } else {
            return 1;
        }
    }

    default int getAlloyEnchantability(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("enchantability");
        } else {
            return 1;
        }
    }

    default int getAlloyHarvestLevel(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getInt("harvestLevel");
        } else {
            return 0;
        }
    }

    default float getAlloyAttackDamage(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("attackDamage");
        } else {
            return 1;
        }
    }

    default float getAlloyAttackSpeed(ItemStack stack) {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("attackSpeed");
        } else {
            return 1;
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

    default float getToughness(ItemStack stack)
    {
        if (!Config.ALLOYS.ALLOY_TOUGHNESS.get())
        {
            return 0;
        }
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getFloat("toughness");
        } else {
            return 0;
        }
    }

    default int calcDurabilityLoss(ItemStack stack, World worldIn, LivingEntity entityLiving, boolean isEfficient)
    {
        boolean memory = false;
        Random rand = new Random();
        int i = 1;
        float toughness = getToughness(stack);
        if (toughness > 0 && rand.nextFloat() < toughness)
        {
            i += -1;
        }
        if (toughness < 0 && rand.nextFloat() < Math.abs(toughness))
        {
            i += 1;
        }
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

    default void applyModifiersFromRecipe(ItemStack stack, AlloyModifierRecipe recipe) {
        for (AlloyModifier mod : recipe.getModifiers()) {
            applyBonusModifier(stack,mod);
        }
    }

    default void applyBonusModifier(ItemStack stack, AlloyModifier modifier) {
        if (stack.getTag() != null && (!stack.getTag().contains("AlloyModifiers") || !stack.getTag().getCompound("AlloyModifiers").contains(modifier.getName())) && modifier.canApplyModification(stack.getItem())) {
            if (!stack.getTag().contains("AlloyModifiers")) {
                stack.getTag().put("AlloyModifiers",new CompoundNBT());
            }
            switch (modifier.getType()) {
                case DURABILITY:
                    stack.getTag().getCompound("StoredAlloy").putInt("durability", (int) Math.max(0,modifier.returnModification(this.getAlloyDurability(stack))));
                    stack.getTag().getCompound("AlloyModifiers").putInt(modifier.getName(), (int) modifier.getValue());
                    return;
                case HARVEST_LEVEL:
                    stack.getTag().getCompound("StoredAlloy").putInt("harvestLevel", Math.min((int) modifier.returnModification(this.getAlloyHarvestLevel(stack)),5));
                    stack.getTag().getCompound("AlloyModifiers").putInt(modifier.getName(), (int) modifier.getValue());
                    return;
                case ENCHANTABILITY:
                    stack.getTag().getCompound("StoredAlloy").putInt("enchantability", (int) Math.max(0,modifier.returnModification(this.getAlloyEnchantability(stack))));
                    stack.getTag().getCompound("AlloyModifiers").putInt(modifier.getName(), (int) modifier.getValue());
                    return;
                case MINING_SPEED:
                    stack.getTag().getCompound("StoredAlloy").putFloat("miningSpeed", modifier.returnModification(this.getAlloyEfficiency(stack)));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
                    return;
                case ATTACK_DAMAGE:
                    stack.getTag().getCompound("StoredAlloy").putFloat("attackDamage", modifier.returnModification(this.getAlloyAttackDamage(stack)));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
                    return;
                case ATTACK_SPEED:
                    stack.getTag().getCompound("StoredAlloy").putFloat("attackSpeed", modifier.returnModification(this.getAlloyAttackSpeed(stack)));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
                    return;
                case CORROSION_RESISTANCE:
                    stack.getTag().getCompound("StoredAlloy").putFloat("corrResist", Math.min(1,Math.max(0,modifier.returnModification(this.getCorrResist(stack)))));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
                    return;
                case HEAT_RESISTANCE:
                    stack.getTag().getCompound("StoredAlloy").putFloat("heatResist", Math.min(1,Math.max(0,modifier.returnModification(this.getHeatResist(stack)))));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
                    return;
                case TOUGHNESS:
                    stack.getTag().getCompound("StoredAlloy").putFloat("toughness", Math.min(1,Math.max(-1,modifier.returnModification(this.getToughness(stack)))));
                    stack.getTag().getCompound("AlloyModifiers").putFloat(modifier.getName(), modifier.getValue());
            }
            stack.getTag().getCompound("AlloyModifiers").putString("modifierName", modifier.getName());
        }

    }

}
