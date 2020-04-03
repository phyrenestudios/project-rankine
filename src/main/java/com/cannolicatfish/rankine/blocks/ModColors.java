package com.cannolicatfish.rankine.blocks;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ModColors {


    public static int getCedar()
    {
        return 11585891;
    }

    public static int getPinyon() {return 10920800;}

}
