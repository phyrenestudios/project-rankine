package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeScreen;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerScreen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ClientProxy implements IProxy {

    public static void addCutout(List<Block> blockList) {
        for (Block block : blockList) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }

    public static void addCutoutMipped(List<Block> blockList) {
        for (Block block : blockList) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
        }
    }

    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.ALLOY_FURNACE_CONTAINER, AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(ModBlocks.PISTON_CRUSHER_CONTAINER, PistonCrusherScreen::new);
        ScreenManager.registerFactory(ModBlocks.COAL_FORGE_CONTAINER, CoalForgeScreen::new);
        ScreenManager.registerFactory(ModBlocks.FINERY_FORGE_CONTAINER, FineryForgeScreen::new);
        ScreenManager.registerFactory(ModItems.ELEMENT_INDEXER_CONTAINER, ElementIndexerScreen::new);

        addCutout(Arrays.asList(
                ModBlocks.ACANTHITE_ORE,
                ModBlocks.NATIVE_BISMUTH_ORE,
                ModBlocks.NATIVE_GALLIUM_ORE,
                ModBlocks.NATIVE_INDIUM_ORE,
                ModBlocks.NATIVE_SELENIUM_ORE,
                ModBlocks.NATIVE_ARSENIC_ORE,
                ModBlocks.NATIVE_SULFUR_ORE,
                ModBlocks.NATIVE_TELLURIUM_ORE,
                ModBlocks.ANTHRACITE_ORE,
                ModBlocks.BAUXITE_ORE,
                ModBlocks.BISMUTHINITE_ORE,
                ModBlocks.BITUMINOUS_ORE,
                ModBlocks.CASSITERITE_ORE,
                ModBlocks.CHROMITE_ORE,
                ModBlocks.CINNABAR_ORE,
                ModBlocks.OPAL_ORE,
                ModBlocks.COLUMBITE_ORE,
                ModBlocks.DIAMOND_ORE,
                ModBlocks.EMERALD_ORE,
                ModBlocks.GALENA_ORE,
                ModBlocks.ILMENITE_ORE,
                ModBlocks.LAZURITE_ORE,
                ModBlocks.LIGNITE_ORE,
                ModBlocks.MALACHITE_ORE,
                ModBlocks.MAGNESITE_ORE,
                ModBlocks.MAGNETITE_ORE,
                ModBlocks.MOLYBDENITE_ORE,
                ModBlocks.NATIVE_COPPER_ORE,
                ModBlocks.NATIVE_GOLD_ORE,
                ModBlocks.NATIVE_TIN_ORE,
                ModBlocks.NATIVE_ALUMINUM_ORE,
                ModBlocks.NATIVE_LEAD_ORE,
                ModBlocks.NATIVE_SILVER_ORE,
                ModBlocks.PENTLANDITE_ORE,
                ModBlocks.PLUMBAGO_ORE,
                ModBlocks.PYROLUSITE_ORE,
                ModBlocks.FLUORITE_ORE,
                ModBlocks.SPERRYLITE_ORE,
                ModBlocks.AQUAMARINE_ORE,
                ModBlocks.SPHALERITE_ORE,
                ModBlocks.MOISSANITE_ORE,
                ModBlocks.QUARTZ_ORE,
                ModBlocks.NETHER_GOLD_ORE,
                ModBlocks.SUBBITUMINOUS_ORE,
                ModBlocks.TANTALITE_ORE,
                ModBlocks.WOLFRAMITE_ORE,
                ModBlocks.VANADINITE_ORE,
                ModBlocks.GREENOCKITE_ORE,
                ModBlocks.XENOTIME_ORE,
                ModBlocks.STIBNITE_ORE,
                ModBlocks.URANINITE_ORE,

                ModBlocks.LEAD_GLASS,
                ModBlocks.ETCHED_GLASS0,
                ModBlocks.ETCHED_GLASS1,
                ModBlocks.ETCHED_GLASS2,

                ModBlocks.CEDAR_SAPLING,
                ModBlocks.JUNIPER_SAPLING,
                ModBlocks.COCONUT_PALM_SAPLING,
                ModBlocks.PINYON_PINE_SAPLING,
                ModBlocks.BALSAM_FIR_SAPLING,
                ModBlocks.MAGNOLIA_SAPLING,
                ModBlocks.EASTERN_HEMLOCK_SAPLING,
                ModBlocks.YELLOW_BIRCH_SAPLING,
                ModBlocks.BLACK_BIRCH_SAPLING,

                ModBlocks.CEDAR_TRAPDOOR,
                ModBlocks.COCONUT_PALM_TRAPDOOR,
                ModBlocks.PINYON_PINE_TRAPDOOR,
                ModBlocks.JUNIPER_TRAPDOOR,
                ModBlocks.MAGNOLIA_TRAPDOOR,
                ModBlocks.BALSAM_FIR_TRAPDOOR,
                ModBlocks.BAMBOO_TRAPDOOR,
                ModBlocks.EASTERN_HEMLOCK_TRAPDOOR,
                ModBlocks.CEDAR_DOOR,
                ModBlocks.COCONUT_PALM_DOOR,
                ModBlocks.PINYON_PINE_DOOR,
                ModBlocks.JUNIPER_DOOR,
                ModBlocks.MAGNOLIA_DOOR,
                ModBlocks.BALSAM_FIR_DOOR,
                ModBlocks.EASTERN_HEMLOCK_DOOR,
                ModBlocks.BAMBOO_DOOR,
                ModBlocks.BRASS_DOOR,
                ModBlocks.BRASS_TRAPDOOR,
                ModBlocks.ALUMINUM_LADDER,
                ModBlocks.NITER
        ));

        addCutoutMipped(Arrays.asList(
                ModBlocks.POTTED_CEDAR_SAPLING,
                ModBlocks.POTTED_JUNIPER_SAPLING,
                ModBlocks.POTTED_COCONUT_PALM_SAPLING,
                ModBlocks.POTTED_PINYON_PINE_SAPLING,
                ModBlocks.POTTED_BALSAM_FIR_SAPLING,
                ModBlocks.POTTED_MAGNOLIA_SAPLING,
                ModBlocks.POTTED_EASTERN_HEMLOCK_SAPLING,
                ModBlocks.POTTED_YELLOW_BIRCH_SAPLING,
                ModBlocks.BANANA_YUCCA_BUSH,
                ModBlocks.SWAMP_GRASS,
                ModBlocks.SHORT_GRASS,
                ModBlocks.WHITE_CLOVER,
                ModBlocks.PURPLE_CLOVER,
                ModBlocks.BLUE_FOXFIRE,
                ModBlocks.GREEN_FOXFIRE,
                ModBlocks.DUCKWEED,
                ModBlocks.ELDERBERRY_BUSH,
                ModBlocks.SNOWBERRY_BUSH,
                ModBlocks.PINK_FOXFIRE,
                ModBlocks.YELLOW_FOXFIRE,
                ModBlocks.BLUEBERRY_BUSH,
                ModBlocks.BLACKBERRY_BUSH,
                ModBlocks.RASPBERRY_BUSH,
                ModBlocks.BLACKBERRY_BUSH,
                ModBlocks.CRANBERRY_BUSH,
                ModBlocks.STRAWBERRY_BUSH,
                ModBlocks.PINEAPPLE_BUSH,
                ModBlocks.CEDAR_LEAVES,
                ModBlocks.PINYON_PINE_LEAVES,
                ModBlocks.JUNIPER_LEAVES,
                ModBlocks.BALSAM_FIR_LEAVES,
                ModBlocks.COCONUT_PALM_LEAVES,
                ModBlocks.MAGNOLIA_LEAVES,
                ModBlocks.EASTERN_HEMLOCK_LEAVES,
                ModBlocks.CALCITE_BLOCK,
                ModBlocks.CALCIUM_SILICATE_BLOCK,
                ModBlocks.SALT_BLOCK,
                ModBlocks.PINK_SALT_BLOCK
        ));
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
