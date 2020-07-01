package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ErodedBadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class RankineSurfaceBuilder<C extends ISurfaceBuilderConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<?>> {


    public static final BlockState SAND = Blocks.SAND.getDefaultState();
    public static final BlockState SALT = ModBlocks.SALT_BLOCK.getDefaultState();


    public static final SurfaceBuilderConfig SALT_SAND_SAND_CONFIG = new SurfaceBuilderConfig(SALT, SAND, SAND);

    public static final SurfaceBuilder<SurfaceBuilderConfig> SALT_SPIKES = new SaltSpikesSurfaceBuilder(SurfaceBuilderConfig::deserialize);


}
