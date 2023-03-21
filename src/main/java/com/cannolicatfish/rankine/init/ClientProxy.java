package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.blocks.LeafLitterBlock;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerScreen;
import com.cannolicatfish.rankine.blocks.block_groups.RankineBricks;
import com.cannolicatfish.rankine.blocks.block_groups.RankineDripstone;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineStoneBricksBlock;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelScreen;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableScreen;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableScreen;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerScreen;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientProxy implements IProxy {

    public static void addCutout(List<Block> blockList) {
        for (Block block : blockList) {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        }
    }

    public static void addCutoutMipped(List<Block> blockList) {
        for (Block block : blockList) {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped());
        }
    }

    public static void addTranslucent(List<Block> blockList) {
        for (Block block : blockList) {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
        }
    }

    public static void registerItemProperties() {
        for (Item ITEM : Stream.of(RankineLists.WOODEN_TOOLS,RankineLists.STONE_TOOLS, RankineLists.FLINT_TOOLS, RankineLists.BRONZE_TOOLS, RankineLists.ALLOY_TOOLS, RankineLists.PEWTER_TOOLS, RankineLists.INVAR_TOOLS, RankineLists.TITANIUM_ALLOY_TOOLS, RankineLists.NIOBIUM_ALLOY_TOOLS, RankineLists.ZIRCONIUM_ALLOY_TOOLS, RankineLists.STEEL_TOOLS, RankineLists.STAINLESS_STEEL_TOOLS, RankineLists.COBALT_SUPERALLOY_TOOLS, RankineLists.NICKEL_SUPERALLOY_TOOLS, RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS, RankineLists.BLACK_GOLD_TOOLS, RankineLists.BLUE_GOLD_TOOLS, RankineLists.GREEN_GOLD_TOOLS, RankineLists.ROSE_GOLD_TOOLS, RankineLists.PURPLE_GOLD_TOOLS, RankineLists.WHITE_GOLD_TOOLS, RankineLists.OSMIRIDIUM_TOOLS, RankineLists.AMALGAM_TOOLS, RankineLists.ENDER_AMALGAM_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (ITEM instanceof SpearItem) {
                ItemProperties.register(ITEM, new ResourceLocation("throwing"), (stack, world, living, id) ->
                        living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
            }
        }

        for (Block BLK : Stream.of(RankineLists.ALLOY_LADDERS,RankineLists.GLAZED_PORCELAIN_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            ItemProperties.register(BLK.asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) BLK.asItem()).getBuildingMode(stack) : 1.0F);
        }

        for (RankineBricks Bricks : RankineLists.RANKINE_BRICKS) {
            ItemProperties.register(Bricks.getBricksBlock().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Bricks.getBricksBlock().asItem()).getBuildingMode(stack) : 1.0F);
        }
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            ItemProperties.register(Wood.getPlanks().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Wood.getPlanks().asItem()).getBuildingMode(stack) : 1.0F);
            ItemProperties.register(Wood.getBookshelf().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Wood.getBookshelf().asItem()).getBuildingMode(stack) : 1.0F);
        }
        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            ItemProperties.register(Stone.getPolished().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Stone.getPolished().asItem()).getBuildingMode(stack) : 1.0F);
            ItemProperties.register(Stone.getBricks().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Stone.getBricks().asItem()).getBuildingMode(stack) : 1.0F);
            ItemProperties.register(Stone.getMossyBricks().asItem(), new ResourceLocation(ProjectRankine.MODID, "building_mode"), (stack, world, living, id) -> stack.getTag() != null ? (float) ((BuildingModeBlockItem) Stone.getMossyBricks().asItem()).getBuildingMode(stack) : 1.0F);
        }

        ItemProperties.register(RankineItems.SHULKER_GAS_VACUUM.get(),
                new ResourceLocation(ProjectRankine.MODID, "gas_held"), (stack, world, living, id) ->
                        stack.getTag() != null && !stack.getTag().getString("gas").isEmpty() ? 1.0F : 0.0F);

    }
    @Override
    public void init() {
        MenuScreens.register(RankineBlocks.MIXING_BARREL_CONTAINER, MixingBarrelScreen::new);
        MenuScreens.register(RankineBlocks.ALLOY_FURNACE_CONTAINER, AlloyFurnaceScreen::new);
        MenuScreens.register(RankineBlocks.EVAPORATION_TOWER_CONTAINER, EvaporationTowerScreen::new);
        MenuScreens.register(RankineBlocks.GAS_CONDENSER_CONTAINER, GasBottlerScreen::new);
        MenuScreens.register(RankineItems.ELEMENT_INDEXER_CONTAINER, ElementIndexerScreen::new);
        MenuScreens.register(RankineBlocks.TEMPLATE_TABLE_CONTAINER, TemplateTableScreen::new);
        MenuScreens.register(RankineBlocks.MATERIAL_TESTING_TABLE_CONTAINER, MaterialTestingTableScreen::new);
        MenuScreens.register(RankineBlocks.BATTERY_CHARGER_CONTAINER, BatteryChargerScreen::new);
        MenuScreens.register(RankineBlocks.CRUCIBLE_CONTAINER, CrucibleScreen::new);
        MenuScreens.register(RankineBlocks.INDUCTION_FURNACE_CONTAINER, InductionFurnaceScreen::new);
        MenuScreens.register(RankineBlocks.FUSION_FURNACE_CONTAINER, FusionFurnaceScreen::new);

        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            List<Block> blockList = new ArrayList<>();
            for (Block blk : Stone.getStoneBlocks()) {
                if (blk instanceof RankineStoneBricksBlock) {
                    blockList.add(blk);
                }
            }
            addCutout(blockList);
        }
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            List<Block> cutoutList = new ArrayList<>();
            List<Block> cutoutMippedList = new ArrayList<>();
            for (Block blk : Wood.getWoodBlocks()) {
                if (blk instanceof TrapDoorBlock || blk instanceof DoorBlock || blk instanceof HollowLogBlock | blk instanceof SaplingBlock || blk instanceof LeavesBlock || blk instanceof LeafLitterBlock) {
                    cutoutList.add(blk);
                } else if (blk instanceof FlowerPotBlock) {
                    cutoutMippedList.add(blk);
                }
            }
            addCutout(cutoutList);
            addCutoutMipped(cutoutMippedList);
        }
        addCutout(RankineLists.LEAF_LITTERS);

        for (RankineDripstone Dripstone : RankineLists.RANKINE_DRIPSTONES) {
            ItemBlockRenderTypes.setRenderLayer(Dripstone.getPointedDripstone(), RenderType.cutout());
        }

        addCutout(RankineLists.MINING_HEADS);
        addCutout(RankineLists.METAL_DOORS);
        addCutout(RankineLists.METAL_TRAPDOORS);
        addCutout(RankineLists.ALLOY_LADDERS);
        addCutout(RankineLists.ALLOY_POLES);
        addCutout(RankineLists.ALLOY_BARS);
        addCutout(RankineLists.HOLLOW_LOGS);
        addCutout(RankineLists.GLAZED_PORCELAIN_BLOCKS);
        addCutout(RankineLists.VANILLA_BRICKS);
        addCutout(RankineLists.NATIVE_ORES);
        addCutout(RankineLists.CRUSHING_ORES);
        addCutout(RankineLists.SPECIAL_ORES);
        addCutout(RankineLists.ASPHALT_BLOCKS);
        addCutout(RankineLists.RED_ASPHALT_BLOCKS);
        addCutout(RankineLists.GRAY_ASPHALT_BLOCKS);
        addCutout(RankineLists.DARK_GRAY_ASPHALT_BLOCKS);
        addCutout(RankineLists.BLUE_ASPHALT_BLOCKS);
        addCutout(RankineLists.GREEN_ASPHALT_BLOCKS);
        addCutout(Arrays.asList(
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.LEAD_GLASS.get(),
                RankineBlocks.BOROSILICATE_GLASS.get(),
                RankineBlocks.CVD_GLASS.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.WILLOW_BRANCHLET.get(),
                RankineBlocks.WILLOW_BRANCHLET_PLANT.get(),

                RankineBlocks.TREE_TAP.get(),
                RankineBlocks.TAP_LINE.get(),
                RankineBlocks.TRAMPOLINE.get()

        ));

        addCutoutMipped(RankineLists.GRASS_BLOCKS);
        addCutoutMipped(RankineLists.CROPS_SINGLE);
        addCutoutMipped(RankineLists.CROPS_DOUBLE);
        addCutoutMipped(RankineLists.CROPS_TRIPLE);
        addCutoutMipped(RankineLists.BUSH_PLANTS);
        addCutoutMipped(RankineLists.DOUBLE_BUSH_PLANTS);
        addCutoutMipped(RankineLists.TALL_FLOWERS);
        addCutoutMipped(RankineLists.LANTERNS);
        addCutoutMipped(Arrays.asList(
                RankineBlocks.CAST_IRON_SUPPORT.get(),
                RankineBlocks.CAST_IRON_SUPPORT_SLAB.get(),
                RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get(),

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
                RankineBlocks.CALCIUM_SILICATE_BLOCK.get(),
                RankineBlocks.BIOTITE_BLOCK.get(),
                RankineBlocks.MUSCOVITE_BLOCK.get(),
                RankineBlocks.SODIUM_CHLORIDE_BLOCK.get(),
                RankineBlocks.PINK_SALT_BLOCK.get()


        ));

        addTranslucent(RankineLists.GAS_BLOCKS);
        addTranslucent(RankineLists.LIGHTNING_GLASSES);
        addTranslucent(Arrays.asList(
                RankineBlocks.SODIUM_CHLORIDE_BLOCK.get(),
                RankineBlocks.PINK_SALT_BLOCK.get(),
                RankineBlocks.ICE_BRICKS.get(),
                RankineBlocks.ICE_BRICKS_SLAB.get(),
                RankineBlocks.ICE_BRICKS_STAIRS.get(),
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
                RankineBlocks.HYDROGEN_BLOCK.get(),
                RankineBlocks.HELIUM_BLOCK.get(),
                RankineBlocks.NITROGEN_BLOCK.get(),
                RankineBlocks.OXYGEN_BLOCK.get(),
                RankineBlocks.FLUORINE_BLOCK.get(),
                RankineBlocks.NEON_BLOCK.get(),
                RankineBlocks.CHLORINE_BLOCK.get(),
                RankineBlocks.ARGON_BLOCK.get(),
                RankineBlocks.KRYPTON_BLOCK.get(),
                RankineBlocks.XENON_BLOCK.get(),
                RankineBlocks.RADON_BLOCK.get(),
                RankineBlocks.OGANESSON_BLOCK.get(),
                RankineBlocks.AMMONIA_GAS_BLOCK.get(),
                RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_CHLORIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_FLUORIDE_GAS_BLOCK.get(),
                RankineBlocks.HYDROGEN_SULFIDE_GAS_BLOCK.get(),
                RankineBlocks.SULFUR_DIOXIDE_GAS_BLOCK.get(),
                RankineBlocks.TUNGSTEN_HEXAFLUORIDE_GAS_BLOCK.get(),
                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.BLACK_TEKTITE.get(),
                RankineBlocks.GREEN_TEKTITE.get(),
                RankineBlocks.GRAY_TEKTITE.get(),
                RankineBlocks.BROWN_TEKTITE.get()
        ));

        ItemBlockRenderTypes.setRenderLayer(RankineBlocks.MUSCOVITE_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RankineBlocks.BIOTITE_BLOCK.get(), RenderType.translucent());
    }





    @Override
    public Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    @Override
    public Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

}
