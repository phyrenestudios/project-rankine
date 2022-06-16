package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface IAlloyProjectile extends IAlloySpecialItem {

    @Override
    default void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        CompoundNBT listnbt = new CompoundNBT();
        listnbt.putFloat("projectileDamage",createValueForProjectileDamage(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ATTACK_DAMAGE)));
        stack.getOrCreateTag().put("StoredAlloyStats", listnbt);
    }

    @Override
    default void addAlloyInformation(ItemStack stack, @javax.annotation.Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((new StringTextComponent("Any Composition").mergeStyle(TextFormatting.GOLD)));
                } else {
                    tooltip.add((new StringTextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD)));
                }
                if (!this.needsRefresh(stack)) {
                    tooltip.add((new StringTextComponent("Damage: " + df.format(getAlloyArrowDamage(stack))).mergeStyle(TextFormatting.GRAY)));
                }
            }
        }
    }

    @Override
    default List<AlloyModifier.ModifierType> getDefaultStats() {
        return null;
    }

    default float getAlloyArrowDamage(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getFloat("projectileDamage");
        } else {
            return 1;
        }
    }

    default float createValueForProjectileDamage(Map<ElementRecipe,Integer> elementMap, AlloyingRecipe alloy, @javax.annotation.Nullable AlloyModifier modifier)
    {
        float dmgmin = 0;
        float dmgmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            float dmg = set.getKey().getDamage(set.getValue());
            dmgmin = Math.min(dmg,dmgmin);
            dmgmax = Math.max(dmg,dmgmax);
        }
        if (alloy != null) {
            float dmg = alloy.getBonusDamage();
            dmgmin = Math.min(dmgmin,dmgmin+dmg);
            dmgmax = Math.max(dmgmax,dmgmax+dmg);
        }
        float dmg = dmgmax + dmgmin;
        if (modifier != null) {
            dmg = Math.max(0,modifier.returnModification(dmg));
        }

        int hlmin = 0;
        int hlmax = 0;
        for (Map.Entry<ElementRecipe,Integer> set : elementMap.entrySet()) {
            int hl = set.getKey().getMiningLevel(set.getValue());
            hlmin = Math.min(hl,hlmin);
            hlmax = Math.max(hl,hlmax);
        }
        if (alloy != null) {
            int hl = alloy.getBonusMiningLevel();
            hlmin = Math.min(hlmin,hlmin+hl);
            hlmax = Math.max(hlmax,hlmax+hl);
        }
        int hl = Math.max(hlmax + hlmin,0);
        if (modifier != null) {
            hl =  (int) Math.max(0,modifier.returnModification(hl));
        }

        return 2f + 0.5f*hl + 0.5f*dmg;
    }
}
