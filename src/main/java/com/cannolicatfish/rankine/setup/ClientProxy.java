package com.cannolicatfish.rankine.setup;

import com.cannolicatfish.rankine.blocks.ModColors;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.world.biome.ModBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
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
import net.minecraftforge.common.BiomeManager;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.ALLOYFURNACE_CONTAINER, AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(ModBlocks.PISTON_CRUSHER_CONTAINER, PistonCrusherScreen::new);
        ScreenManager.registerFactory(ModBlocks.FINERY_FORGE_CONTAINER, FineryForgeScreen::new);
        ScreenManager.registerFactory(ModBlocks.INDUCTION_FURNACE_CONTAINER, InductionFurnaceScreen::new);

        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        blockColors.register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> {
            return p_210229_1_ != null && p_210229_2_ != null ? BiomeColors.getFoliageColor(p_210229_1_, p_210229_2_) : FoliageColors.getDefault();
        }, ModBlocks.COCONUT_PALM_LEAVES);
        blockColors.register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> {
            return ModColors.getPinyon();
        }, ModBlocks.PINYON_PINE_LEAVES);
        blockColors.register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> {
            return p_210229_1_ != null && p_210229_2_ != null ? BiomeColors.getFoliageColor(p_210229_1_, p_210229_2_) : ModColors.getCedar();
        }, ModBlocks.JUNIPER_LEAVES);
        blockColors.register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> {
            return p_210229_1_ != null && p_210229_2_ != null ? BiomeColors.getFoliageColor(p_210229_1_, p_210229_2_) : ModColors.getCedar();
        }, ModBlocks.CEDAR_LEAVES);
        net.minecraftforge.client.ForgeHooksClient.onBlockColorsInit(blockColors);

        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        itemColors.register((p_210235_1_, p_210235_2_) -> {
            BlockState blockstate = ((BlockItem)p_210235_1_.getItem()).getBlock().getDefaultState();
            return blockColors.getColor(blockstate, (ILightReader)null, (BlockPos)null, p_210235_2_);
        }, ModBlocks.COCONUT_PALM_LEAVES, ModBlocks.PINYON_PINE_LEAVES, ModBlocks.CEDAR_LEAVES, ModBlocks.JUNIPER_LEAVES);
        net.minecraftforge.client.ForgeHooksClient.onItemColorsInit(itemColors, blockColors);

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

        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_BEAM, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CAST_IRON_PIPE_JUNCTION, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BRASS_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CONCRETE_PIPE, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CONCRETE_PIPE_JUNCTION, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.BANANA_YUCCA_BUSH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPHAGNUM_MOSS, RenderType.getCutoutMipped());
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
        RenderTypeLookup.setRenderLayer(ModBlocks.COCONUT_PALM_SAPLING, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.PINYON_PINE_SAPLING, RenderType.getCutout());

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.FELSENMEER,100));
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(ModBiomes.OASIS,1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.HIGHLAND_PLATEAU,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(ModBiomes.PINYON_JUNIPER_WOODLANDS,10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ModBiomes.CEDAR_FOREST,10));

        BiomeManager.addSpawnBiome(ModBiomes.PINYON_JUNIPER_WOODLANDS);
        BiomeManager.addSpawnBiome(ModBiomes.CEDAR_FOREST);
        BiomeManager.addSpawnBiome(ModBiomes.HIGHLAND_PLATEAU);




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
