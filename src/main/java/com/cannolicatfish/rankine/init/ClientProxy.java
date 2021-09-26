package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.laserquarry.LaserQuarryScreen;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.blocks.rankinebox.RankineBoxScreen;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableScreen;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerScreen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
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

    public static void addTranslucent(List<Block> blockList) {
        for (Block block : blockList) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent());
        }
    }

    public static void registerItemProperties() {
        ItemModelsProperties.registerProperty(RankineItems.SHULKER_GAS_VACUUM.get(),
                new ResourceLocation(ProjectRankine.MODID, "gas_held"), (stack, world, living) ->
                        stack.getTag() != null && !stack.getTag().getString("gas").isEmpty() ? 1.0F : 0.0F);
    }
    @Override
    public void init() {
        ScreenManager.registerFactory(RankineBlocks.ALLOY_FURNACE_CONTAINER, AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(RankineBlocks.PISTON_CRUSHER_CONTAINER, PistonCrusherScreen::new);
        ScreenManager.registerFactory(RankineBlocks.EVAPORATION_TOWER_CONTAINER, EvaporationTowerScreen::new);
        ScreenManager.registerFactory(RankineItems.ELEMENT_INDEXER_CONTAINER, ElementIndexerScreen::new);
        ScreenManager.registerFactory(RankineBlocks.TEMPLATE_TABLE_CONTAINER, TemplateTableScreen::new);
        ScreenManager.registerFactory(RankineBlocks.MATERIAL_TESTING_TABLE_CONTAINER, MaterialTestingTableScreen::new);
        ScreenManager.registerFactory(RankineBlocks.CRUCIBLE_CONTAINER, CrucibleScreen::new);
        ScreenManager.registerFactory(RankineBlocks.INDUCTION_FURNACE_CONTAINER, InductionFurnaceScreen::new);
        ScreenManager.registerFactory(RankineBlocks.GYRATORY_CRUSHER_CONTAINER, GyratoryCrusherScreen::new);
        ScreenManager.registerFactory(RankineBlocks.RANKINE_BOX_CONTAINER, RankineBoxScreen::new);
        ScreenManager.registerFactory(RankineBlocks.LASER_QUARRY_CONTAINER, LaserQuarryScreen::new);

        addCutout(RankineLists.WOODEN_DOORS);
        addCutout(RankineLists.METAL_DOORS);
        addCutout(RankineLists.WOODEN_TRAPDOORS);
        addCutout(RankineLists.METAL_TRAPDOORS);
        addCutout(RankineLists.METAL_LADDERS);
        addCutout(RankineLists.LEAVES);
        addCutout(RankineLists.SAPLINGS);
        addCutout(RankineLists.NATIVE_ORES);
        addCutout(RankineLists.CRUSHING_ORES);
        addCutout(RankineLists.SPECIAL_ORES);
        addCutout(Arrays.asList(
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.LEAD_GLASS.get(),
                RankineBlocks.BOROSILICATE_GLASS.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.ASPHALT_0.get(),
                RankineBlocks.ASPHALT_1.get(),
                RankineBlocks.ASPHALT_2.get(),
                RankineBlocks.ASPHALT_3.get(),

                RankineBlocks.TREE_TAP.get(),
                RankineBlocks.TAP_LINE.get(),
                RankineBlocks.TRAMPOLINE.get()

        ));

        addCutoutMipped(RankineLists.GRASSY_SOILS);
        addCutoutMipped(Arrays.asList(
                RankineBlocks.CAST_IRON_BARS.get(),
                RankineBlocks.CAST_IRON_SUPPORT.get(),
                RankineBlocks.CAST_IRON_SUPPORT_SLAB.get(),
                RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get(),
                RankineBlocks.CAST_IRON_SUPPORT_VERTICAL_SLAB.get(),

                RankineBlocks.POTTED_CEDAR_SAPLING.get(),
                RankineBlocks.POTTED_JUNIPER_SAPLING.get(),
                RankineBlocks.POTTED_COCONUT_PALM_SAPLING.get(),
                RankineBlocks.POTTED_PINYON_PINE_SAPLING.get(),
                RankineBlocks.POTTED_BALSAM_FIR_SAPLING.get(),
                RankineBlocks.POTTED_MAGNOLIA_SAPLING.get(),
                RankineBlocks.POTTED_EASTERN_HEMLOCK_SAPLING.get(),
                RankineBlocks.POTTED_MAPLE_SAPLING.get(),
                RankineBlocks.POTTED_YELLOW_BIRCH_SAPLING.get(),
                RankineBlocks.POTTED_BLACK_BIRCH_SAPLING.get(),
                RankineBlocks.POTTED_BLACK_WALNUT_SAPLING.get(),
                RankineBlocks.POTTED_SHARINGA_SAPLING.get(),
                RankineBlocks.POTTED_CORK_OAK_SAPLING.get(),

                RankineBlocks.BANANA_YUCCA_BUSH.get(),
                RankineBlocks.ELDERBERRY_BUSH.get(),
                RankineBlocks.SNOWBERRY_BUSH.get(),
                RankineBlocks.BLUEBERRY_BUSH.get(),
                RankineBlocks.BLACKBERRY_BUSH.get(),
                RankineBlocks.RASPBERRY_BUSH.get(),
                RankineBlocks.BLACKBERRY_BUSH.get(),
                RankineBlocks.CRANBERRY_BUSH.get(),
                RankineBlocks.POKEBERRY_BUSH.get(),
                RankineBlocks.STRAWBERRY_BUSH.get(),
                RankineBlocks.PINEAPPLE_BUSH.get(),
                RankineBlocks.ALOE_PLANT.get(),
                RankineBlocks.CAMPHOR_BASIL_PLANT.get(),
                RankineBlocks.ASPARAGUS_PLANT.get(),
                RankineBlocks.CORN_STALK.get(),
                RankineBlocks.CORN_PLANT.get(),
                RankineBlocks.RICE_PLANT.get(),
                RankineBlocks.COTTON_PLANT.get(),
                RankineBlocks.JUTE_PLANT.get(),
                RankineBlocks.RED_LILY.get(),
                RankineBlocks.ORANGE_LILY.get(),
                RankineBlocks.WHITE_LILY.get(),
                RankineBlocks.BLUE_MORNING_GLORY.get(),
                RankineBlocks.PURPLE_MORNING_GLORY.get(),
                RankineBlocks.BLACK_MORNING_GLORY.get(),
                RankineBlocks.GOLDENROD.get(),



                RankineBlocks.CALCITE_BLOCK.get(),
                RankineBlocks.CALCIUM_SILICATE_BLOCK.get(),
                RankineBlocks.MICA_BLOCK.get(),
                RankineBlocks.SODIUM_CHLORIDE_BLOCK.get(),
                RankineBlocks.PINK_SALT_BLOCK.get()


        ));

        addTranslucent(Arrays.asList(
                RankineBlocks.HYDROGEN_GAS_BLOCK.get(),
                RankineBlocks.HELIUM_GAS_BLOCK.get(),
                RankineBlocks.NITROGEN_GAS_BLOCK.get(),
                RankineBlocks.OXYGEN_GAS_BLOCK.get(),
                RankineBlocks.FLUORINE_GAS_BLOCK.get(),
                RankineBlocks.NEON_GAS_BLOCK.get(),
                RankineBlocks.CHLORINE_GAS_BLOCK.get(),
                RankineBlocks.ARGON_GAS_BLOCK.get(),
                RankineBlocks.KRYPTON_GAS_BLOCK.get(),
                RankineBlocks.XENON_GAS_BLOCK.get(),
                RankineBlocks.RADON_GAS_BLOCK.get(),
                RankineBlocks.OGANESSON_GAS_BLOCK.get(),
                RankineBlocks.LIGHTNING_GLASS.get(),
                RankineBlocks.BLACK_TEKTITE.get(),
                RankineBlocks.GREEN_TEKTITE.get(),
                RankineBlocks.GRAY_TEKTITE.get(),
                RankineBlocks.BROWN_TEKTITE.get()
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
