package com.cannolicatfish.rankine.client;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerScreen;
import com.cannolicatfish.rankine.blocks.block_groups.RankineBricks;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelScreen;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableScreen;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableScreen;
import com.cannolicatfish.rankine.init.RankineContainers;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerScreen;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Client-only setup, replacing the deleted 1.18.2 IProxy/ClientProxy pattern.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetup::registerItemProperties);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(RankineContainers.MIXING_BARREL_CONTAINER.get(), MixingBarrelScreen::new);
        event.register(RankineContainers.ALLOY_FURNACE_CONTAINER.get(), AlloyFurnaceScreen::new);
        event.register(RankineContainers.EVAPORATION_TOWER_CONTAINER.get(), EvaporationTowerScreen::new);
        event.register(RankineContainers.GAS_CONDENSER_CONTAINER.get(), GasBottlerScreen::new);
        event.register(RankineContainers.ELEMENT_INDEXER_CONTAINER.get(), ElementIndexerScreen::new);
        event.register(RankineContainers.TEMPLATE_TABLE_CONTAINER.get(), TemplateTableScreen::new);
        event.register(RankineContainers.MATERIAL_TESTING_TABLE_CONTAINER.get(), MaterialTestingTableScreen::new);
        event.register(RankineContainers.BATTERY_CHARGER_CONTAINER.get(), BatteryChargerScreen::new);
        event.register(RankineContainers.CRUCIBLE_CONTAINER.get(), CrucibleScreen::new);
        event.register(RankineContainers.INDUCTION_FURNACE_CONTAINER.get(), InductionFurnaceScreen::new);
        event.register(RankineContainers.FUSION_FURNACE_CONTAINER.get(), FusionFurnaceScreen::new);
    }

    public static void registerItemProperties() {
        for (Item ITEM : Stream.of(RankineLists.WOODEN_TOOLS,RankineLists.STONE_TOOLS, RankineLists.FLINT_TOOLS, RankineLists.BRONZE_TOOLS, RankineLists.ALLOY_TOOLS, RankineLists.PEWTER_TOOLS, RankineLists.INVAR_TOOLS, RankineLists.TITANIUM_ALLOY_TOOLS, RankineLists.NIOBIUM_ALLOY_TOOLS, RankineLists.ZIRCONIUM_ALLOY_TOOLS, RankineLists.STEEL_TOOLS, RankineLists.STAINLESS_STEEL_TOOLS, RankineLists.COBALT_SUPERALLOY_TOOLS, RankineLists.NICKEL_SUPERALLOY_TOOLS, RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS, RankineLists.BLACK_GOLD_TOOLS, RankineLists.BLUE_GOLD_TOOLS, RankineLists.GREEN_GOLD_TOOLS, RankineLists.ROSE_GOLD_TOOLS, RankineLists.PURPLE_GOLD_TOOLS, RankineLists.WHITE_GOLD_TOOLS, RankineLists.OSMIRIDIUM_TOOLS, RankineLists.AMALGAM_TOOLS, RankineLists.ENDER_AMALGAM_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (ITEM instanceof SpearItem) {
                ItemProperties.register(ITEM, ResourceLocation.parse("throwing"), (stack, world, living, id) ->
                        living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
            }
        }

        for (Block BLK : Stream.of(RankineLists.ALLOY_LADDERS,RankineLists.GLAZED_PORCELAIN_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            registerBuildingModeProperty(BLK);
        }

        for (RankineBricks Bricks : RankineLists.RANKINE_BRICKS) {
            registerBuildingModeProperty(Bricks.getBricksBlock());
        }
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            registerBuildingModeProperty(Wood.getPlanks());
            registerBuildingModeProperty(Wood.getBookshelf());
        }
        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            registerBuildingModeProperty(Stone.getPolished());
            registerBuildingModeProperty(Stone.getBricks());
            registerBuildingModeProperty(Stone.getMossyBricks());
        }

        // PORT-TODO(items): 1.18.2 checked stack.getTag().getString("gas"); update once the items
        // agent decides where GasBottleItem/ShulkerGasVacuum store the held gas (CUSTOM_DATA assumed).
        ItemProperties.register(RankineItems.SHULKER_GAS_VACUUM.get(),
                ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "gas_held"), (stack, world, living, id) -> {
                    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                    return data != null && !data.copyTag().getString("gas").isEmpty() ? 1.0F : 0.0F;
                });
    }

    // PORT-TODO(items): 1.18.2 returned 1.0F when stack.getTag() == null, else the building mode.
    // CUSTOM_DATA presence is the closest analog; revisit if BuildingModeBlockItem gets a dedicated
    // data component.
    private static void registerBuildingModeProperty(Block block) {
        ItemProperties.register(block.asItem(), ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "building_mode"),
                (stack, world, living, id) -> hasBuildingModeData(stack) ? (float) ((BuildingModeBlockItem) block.asItem()).getBuildingMode(stack) : 1.0F);
    }

    private static boolean hasBuildingModeData(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

    /*
     * PORT-TODO(datagen-rendertype): ItemBlockRenderTypes.setRenderLayer is GONE — render types
     * must be set in the block model JSONs via datagen ("render_type": "minecraft:cutout" etc.,
     * BlockStateProvider .renderType(...)). The 1.18.2 ClientProxy assigned:
     *
     * cutout:
     *   - RankineStoneBricksBlock instances from every RankineLists.RANKINE_STONES group
     *   - per RankineLists.RANKINE_WOODS: trapdoors, doors, hollow logs, saplings, leaves, leaf litters
     *   - RankineLists.LEAF_LITTERS, MINING_HEADS, METAL_DOORS, METAL_TRAPDOORS, ALLOY_LADDERS,
     *     ALLOY_POLES, ALLOY_BARS, HOLLOW_LOGS, GLAZED_PORCELAIN_BLOCKS, VANILLA_BRICKS,
     *     NATIVE_ORES, CRUSHING_ORES, SPECIAL_ORES, ASPHALT_BLOCKS, RED_ASPHALT_BLOCKS,
     *     GRAY_ASPHALT_BLOCKS, DARK_GRAY_ASPHALT_BLOCKS, BLUE_ASPHALT_BLOCKS, GREEN_ASPHALT_BLOCKS
     *   - RankineBlocks: FLOOD_GATE, ORNAMENT, LEAD_GLASS, BOROSILICATE_GLASS, CVD_GLASS,
     *     REACTION_CHAMBER_CELL, WILLOW_BRANCHLET, WILLOW_BRANCHLET_PLANT, TREE_TAP, TAP_LINE, TRAMPOLINE
     *   - each RankineLists.RANKINE_DRIPSTONES pointed dripstone
     *
     * cutout_mipped:
     *   - flower pots from RANKINE_WOODS
     *   - RankineLists.GRASS_BLOCKS, CROPS_SINGLE, CROPS_DOUBLE, CROPS_TRIPLE, BUSH_PLANTS,
     *     DOUBLE_BUSH_PLANTS, TALL_FLOWERS, LANTERNS
     *   - RankineBlocks: CAST_IRON_SUPPORT (+slab/stairs), COB, CORN_STALK, SHORT_GRASS,
     *     LOCUST_SPINE, GWIHABAITE_CRYSTAL, STINGING_NETTLE, RED_CLOVER, CRIMSON_CLOVER,
     *     WHITE_CLOVER, YELLOW_CLOVER, CALCIUM_SILICATE_BLOCK, BIOTITE_BLOCK, MUSCOVITE_BLOCK,
     *     SODIUM_CHLORIDE_BLOCK, PINK_SALT_BLOCK
     *
     * translucent:
     *   - RankineLists.GAS_BLOCKS, LIGHTNING_GLASSES
     *   - RankineBlocks: SODIUM_CHLORIDE_BLOCK, PINK_SALT_BLOCK, ICE_BRICKS (+slab/stairs/wall),
     *     all *_GAS_BLOCKs (hydrogen..oganesson, ammonia, carbon dioxide, hydrogen chloride,
     *     hydrogen fluoride, hydrogen sulfide, sulfur dioxide, tungsten hexafluoride),
     *     all element blocks (HYDROGEN_BLOCK..OGANESSON_BLOCK), ANTIMATTER,
     *     BLACK/GREEN/GRAY/BROWN_TEKTITE, MUSCOVITE_BLOCK, BIOTITE_BLOCK
     */
}
