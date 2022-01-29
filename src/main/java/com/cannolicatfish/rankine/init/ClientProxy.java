package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerScreen;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelScreen;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.blocks.rankinebox.RankineBoxScreen;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableScreen;
import com.cannolicatfish.rankine.items.alloys.AlloySurfRodItem;
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

        ItemModelsProperties.registerProperty(RankineItems.ALLOY_SURF_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) -> {
            if (p_239422_2_ == null) {
                return 0.0F;
            } else {
                boolean flag = p_239422_2_.getHeldItemMainhand() == p_239422_0_;
                boolean flag1 = p_239422_2_.getHeldItemOffhand() == p_239422_0_;
                if (p_239422_2_.getHeldItemMainhand().getItem() instanceof AlloySurfRodItem) {
                    flag1 = false;
                }

                return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity)p_239422_2_).fishingBobber != null ? 1.0F : 0.0F;
            }
        });
    }
    @Override
    public void init() {
        ScreenManager.registerFactory(RankineBlocks.MIXING_BARREL_CONTAINER, MixingBarrelScreen::new);
        ScreenManager.registerFactory(RankineBlocks.ALLOY_FURNACE_CONTAINER, AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(RankineBlocks.PISTON_CRUSHER_CONTAINER, PistonCrusherScreen::new);
        ScreenManager.registerFactory(RankineBlocks.EVAPORATION_TOWER_CONTAINER, EvaporationTowerScreen::new);
        ScreenManager.registerFactory(RankineBlocks.GAS_CONDENSER_CONTAINER, GasBottlerScreen::new);
        ScreenManager.registerFactory(RankineItems.ELEMENT_INDEXER_CONTAINER, ElementIndexerScreen::new);
        ScreenManager.registerFactory(RankineBlocks.TEMPLATE_TABLE_CONTAINER, TemplateTableScreen::new);
        ScreenManager.registerFactory(RankineBlocks.MATERIAL_TESTING_TABLE_CONTAINER, MaterialTestingTableScreen::new);
        ScreenManager.registerFactory(RankineBlocks.CRUCIBLE_CONTAINER, CrucibleScreen::new);
        ScreenManager.registerFactory(RankineBlocks.INDUCTION_FURNACE_CONTAINER, InductionFurnaceScreen::new);
        ScreenManager.registerFactory(RankineBlocks.FUSION_FURNACE_CONTAINER, FusionFurnaceScreen::new);
        ScreenManager.registerFactory(RankineBlocks.GYRATORY_CRUSHER_CONTAINER, GyratoryCrusherScreen::new);
        ScreenManager.registerFactory(RankineBlocks.RANKINE_BOX_CONTAINER, RankineBoxScreen::new);

        addCutout(RankineLists.WOODEN_DOORS);
        addCutout(RankineLists.METAL_DOORS);
        addCutout(RankineLists.WOODEN_TRAPDOORS);
        addCutout(RankineLists.METAL_TRAPDOORS);
        addCutout(RankineLists.METAL_LADDERS);
        addCutout(RankineLists.ALLOY_POLES);
        addCutout(RankineLists.LEAVES);
        addCutout(RankineLists.LEAF_LITTERS);
        addCutout(RankineLists.SAPLINGS);
        addCutout(RankineLists.STONE_BRICKS);
        addCutout(RankineLists.NATIVE_ORES);
        addCutout(RankineLists.CRUSHING_ORES);
        addCutout(RankineLists.SPECIAL_ORES);
        addCutout(RankineLists.ASPHALT_BLOCKS);
        addCutout(Arrays.asList(
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.LEAD_GLASS.get(),
                RankineBlocks.BOROSILICATE_GLASS.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.WILLOW_BRANCHLET.get(),
                RankineBlocks.WILLOW_BRANCHLET_PLANT.get(),

                RankineBlocks.TREE_TAP.get(),
                RankineBlocks.TAP_LINE.get(),
                RankineBlocks.TRAMPOLINE.get()

        ));

        addCutoutMipped(RankineLists.GRASS_BLOCKS);
        addCutoutMipped(RankineLists.FLOWER_POTS);
        addCutoutMipped(RankineLists.CROPS_SINGLE);
        addCutoutMipped(RankineLists.CROPS_DOUBLE);
        addCutoutMipped(RankineLists.CROPS_TRIPLE);
        addCutoutMipped(RankineLists.BUSH_PLANTS);
        addCutoutMipped(RankineLists.DOUBLE_BUSH_PLANTS);
        addCutoutMipped(RankineLists.TALL_FLOWERS);
        addCutoutMipped(RankineLists.LANTERNS);
        addCutoutMipped(Arrays.asList(
                RankineBlocks.CAST_IRON_BARS.get(),
                RankineBlocks.CAST_IRON_SUPPORT.get(),
                RankineBlocks.CAST_IRON_SUPPORT_SLAB.get(),
                RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get(),
                RankineBlocks.CAST_IRON_SUPPORT_VERTICAL_SLAB.get(),

                RankineBlocks.COB.get(),
                RankineBlocks.CORN_STALK.get(),
                RankineBlocks.SHORT_GRASS.get(),
                RankineBlocks.LOCUST_SPINE.get(),
                RankineBlocks.GWIHABAITE_CRYSTAL.get(),
                RankineBlocks.STINGING_NETTLE.get(),
                RankineBlocks.RED_CLOVER.get(),
                RankineBlocks.CRIMSON_CLOVER.get(),
                RankineBlocks.WHITE_CLOVER.get(),
                RankineBlocks.YELLOW_CLOVER.get(),

                RankineBlocks.CALCITE_BLOCK.get(),
                RankineBlocks.CALCIUM_SILICATE_BLOCK.get(),
                RankineBlocks.MICA_BLOCK.get(),
                RankineBlocks.SODIUM_CHLORIDE_BLOCK.get(),
                RankineBlocks.PINK_SALT_BLOCK.get()


        ));

        addTranslucent(RankineLists.GAS_BLOCKS);
        addTranslucent(RankineLists.LIGHTNING_GLASSES);
        addTranslucent(Arrays.asList(
                //RankineBlocks.HELIUM_GAS_TUBE.get(),
                RankineBlocks.SALT_COLUMN.get(),
                RankineBlocks.SODIUM_CHLORIDE_BLOCK.get(),
                RankineBlocks.PINK_SALT_COLUMN.get(),
                RankineBlocks.PINK_SALT_BLOCK.get(),
                RankineBlocks.ICE_BRICKS.get(),
                RankineBlocks.ICE_BRICKS_SLAB.get(),
                RankineBlocks.ICE_BRICKS_STAIRS.get(),
                RankineBlocks.ICE_BRICKS_VERTICAL_SLAB.get(),
                RankineBlocks.ICE_BRICKS_WALL.get(),

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
                RankineBlocks.AMMONIA_GAS_BLOCK.get(),
                RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_CHLORIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_FLUORIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_SULFIDE_GAS_BLOCK.get(),
                RankineBlocks.SULFUR_DIOXIDE_GAS_BLOCK.get(),
                RankineBlocks.ANTIMATTER.get(),
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
