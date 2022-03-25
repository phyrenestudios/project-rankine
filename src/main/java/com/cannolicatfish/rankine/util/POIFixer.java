package com.cannolicatfish.rankine.util;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class POIFixer {
    private static Method blockStatesInjector;

    static
    {
        try
        {
            POIFixer.blockStatesInjector = ObfuscationReflectionHelper.findMethod(PoiType.class,"registerBlockStates", PoiType.class);
            POIFixer.blockStatesInjector.setAccessible(true);
        }
        catch (SecurityException | ObfuscationReflectionHelper.UnableToFindMethodException e)
        {
            e.printStackTrace();
        }
    }

    public static void fixPOITypeBlockStates(PoiType poiType)
    {
        try
        {
            POIFixer.blockStatesInjector.invoke(PoiType.class, poiType);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
