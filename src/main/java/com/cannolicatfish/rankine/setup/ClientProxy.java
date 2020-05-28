package com.cannolicatfish.rankine.setup;

import com.cannolicatfish.rankine.blocks.ModColors;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeScreen;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.world.biome.ModBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.ALLOY_FURNACE_CONTAINER, AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(ModBlocks.PISTON_CRUSHER_CONTAINER, PistonCrusherScreen::new);
        ScreenManager.registerFactory(ModBlocks.COAL_FORGE_CONTAINER, CoalForgeScreen::new);
        ScreenManager.registerFactory(ModBlocks.FINERY_FORGE_CONTAINER, FineryForgeScreen::new);


        RenderTypeLookup.setRenderLayer(ModBlocks.ACANTHITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.ANTHRACITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BAUXITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BISMITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BITUMINOUS_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CASSITERITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHROMITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CINNABAR_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.COLUMBITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.DIAMOND_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.EMERALD_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.GALENA_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.ILMENITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.LAZURITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGNITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGNESITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGNETITE_ORE,RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MALACHITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MOISSANITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MOLYBDENITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.NATIVE_COPPER_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.NATIVE_GOLD_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.NATIVE_TIN_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PENTLANDITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PLUMBAGO_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PYROLUSITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.REDSTONE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPERRYLITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPHALERITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUBBITUMINOUS_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.TANTALITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.WOLFRAMITE_ORE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.VANADINITE_ORE, RenderType.getCutoutMipped());

        /*
        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_BEAM, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_PIPE_JUNCTION, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BRASS_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CONCRETE_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CONCRETE_PIPE_JUNCTION, RenderType.getCutoutMipped());
         */
        RenderTypeLookup.setRenderLayer(ModBlocks.BANANA_YUCCA_BUSH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPHAGNUM_MOSS, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SWAMP_GRASS, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.DUCKWEED, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.ELDERBERRY_BUSH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SNOWBERRY_BUSH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNIPER_LEAVES,RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.COCONUT_PALM_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CEDAR_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PINYON_PINE_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CALCITE_BLOCK, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.CALCIUM_SILICATE_BLOCK, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.SALT_BLOCK, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.NITER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CEDAR_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNIPER_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.COCONUT_PALM_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.PINYON_PINE_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.BALSAM_FIR_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_CEDAR_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_JUNIPER_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_COCONUT_PALM_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_PINYON_PINE_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_BALSAM_FIR_SAPLING, RenderType.getCutout());




        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.FELSENMEER,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.HIGHLAND_PLATEAU,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.PINYON_JUNIPER_WOODLANDS,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.CEDAR_FOREST,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.DEAD_SWAMP,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(ModBiomes.SHOAL,10));


        BiomeManager.addSpawnBiome(ModBiomes.PINYON_JUNIPER_WOODLANDS);
        BiomeManager.addSpawnBiome(ModBiomes.CEDAR_FOREST);
        BiomeManager.addSpawnBiome(ModBiomes.HIGHLAND_PLATEAU);
        BiomeManager.addSpawnBiome(ModBiomes.FELSENMEER);
        BiomeManager.addSpawnBiome(ModBiomes.DEAD_SWAMP);
        BiomeManager.addSpawnBiome(ModBiomes.SHOAL);




    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

}
