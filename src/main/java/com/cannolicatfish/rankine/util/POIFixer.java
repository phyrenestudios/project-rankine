package com.cannolicatfish.rankine.util;

import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class POIFixer {
    private static Method blockStatesInjector;

    static
    {
        try
        {
            POIFixer.blockStatesInjector = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class,"registerBlockStates", PointOfInterestType.class);
            POIFixer.blockStatesInjector.setAccessible(true);
        }
        catch (SecurityException | ObfuscationReflectionHelper.UnableToFindMethodException e)
        {
            e.printStackTrace();
        }
    }

    public static void fixPOITypeBlockStates(PointOfInterestType poiType)
    {
        try
        {
            POIFixer.blockStatesInjector.invoke(PointOfInterestType.class, poiType);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
