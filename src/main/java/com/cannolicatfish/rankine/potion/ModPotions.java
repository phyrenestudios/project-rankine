package com.cannolicatfish.rankine.potion;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.potion.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModPotions {
    public static final Potion MERCURY_POISON = new Potion(new EffectInstance(ModEffects.MERCURY_POISONING,800)).setRegistryName(ProjectRankine.MODID,"mercury_poison");
    public static Method MIXES;

    private static void addMix(Potion potionEntry, Item potionIngredient, Potion potionResult)
    {
        if (MIXES == null)
        {
            MIXES = ObfuscationReflectionHelper.findMethod(PotionBrewing.class, "addMix", Potion.class, Item.class, Potion.class);
            MIXES.setAccessible(true);
        }
        try {
            MIXES.invoke(null, potionEntry, potionIngredient, potionResult);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void addBrewingRecipes() {
        addMix(Potions.AWKWARD, ModItems.MERCURY_INGOT, ModPotions.MERCURY_POISON);
    }
}
