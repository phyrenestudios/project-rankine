package com.cannolicatfish.rankine.potion;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModPotions {
    public static final Potion MERCURY_POISON = new Potion(new EffectInstance(ModEffects.MERCURY_POISONING,800)).setRegistryName(ProjectRankine.MODID,"mercury_poison");

}
