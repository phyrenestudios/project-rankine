package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public abstract class RankineSurfaceBuilder<C extends ISurfaceBuilderConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<?>> {


    public static final BlockState SAND = Blocks.SAND.getDefaultState();
    public static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();

    public static final BlockState SALT = ModBlocks.SALT_BLOCK.getDefaultState();
    public static final BlockState RHYOLITE = ModBlocks.RHYOLITE.getDefaultState();
    public static final BlockState ANORTHROSITE = ModBlocks.ANORTHOSITE.getDefaultState();
    public static final BlockState SANDY_DIRT = ModBlocks.SANDY_DIRT.getDefaultState();
    public static final BlockState BLACK_SAND = ModBlocks.BLACK_SAND.getDefaultState();
    public static final BlockState SCORIA = ModBlocks.SCORIA.getDefaultState();
    public static final BlockState BASALT = ModBlocks.THOLEIITIC_BASALT.getDefaultState();


    public static final SurfaceBuilderConfig SALT_SAND_SAND_CONFIG = new SurfaceBuilderConfig(SALT, SAND, SAND);
    public static final SurfaceBuilderConfig RHY_STONE_GRAVEL = new SurfaceBuilderConfig(RHYOLITE, ANORTHROSITE, GRAVEL);
    public static final SurfaceBuilderConfig SD_SD_GRAVEL = new SurfaceBuilderConfig(SANDY_DIRT, SANDY_DIRT, GRAVEL);
    public static final SurfaceBuilderConfig VOLCANO = new SurfaceBuilderConfig(BLACK_SAND, BASALT, SCORIA);


    public static final SurfaceBuilder<SurfaceBuilderConfig> SALT_SPIKES = new SaltSpikesSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);
    public static final SurfaceBuilder<SurfaceBuilderConfig> FELSENMEER = new FelsenmeerSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);


}
