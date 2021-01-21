package com.cannolicatfish.rankine.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.sun.javafx.geom.Vec3d;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.village.PointOfInterestType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class POIFixer {
    private static Method blockStatesInjector;

    static
    {
        try
        {
            POIFixer.blockStatesInjector = PointOfInterestType.class.getDeclaredMethod("registerBlockStates", PointOfInterestType.class);
            POIFixer.blockStatesInjector.setAccessible(true);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public static void fixPOITypeBlockStates(PointOfInterestType poiType)
    {
        try
        {
            POIFixer.blockStatesInjector.invoke(null, poiType);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
