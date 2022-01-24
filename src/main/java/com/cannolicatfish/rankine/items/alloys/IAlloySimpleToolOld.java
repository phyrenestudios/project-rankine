package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface IAlloySimpleToolOld extends IAlloyItem {
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

                hlmin = Math.min(hl,hlmin);
                hlmax = Math.max(hl,hlmax);
                dmgmin = Math.min(dmg,dmgmin);
                dmgmax = Math.max(dmg,dmgmax);
                asmin = Math.min(as,asmin);
                asmax = Math.max(as,asmax);
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
}
