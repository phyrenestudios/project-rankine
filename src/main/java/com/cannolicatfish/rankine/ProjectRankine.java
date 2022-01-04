package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleTile;
import com.cannolicatfish.rankine.blocks.distillationtower.DistillationTowerTile;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerContainer;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerTile;
import com.cannolicatfish.rankine.blocks.gasvent.GasVentTile;
import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherContainer;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableContainer;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.blocks.rankinebox.RankineBoxContainer;
import com.cannolicatfish.rankine.blocks.rankinebox.RankineBoxTile;
import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanTile;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilTile;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.enchantment.*;
import com.cannolicatfish.rankine.entities.*;
import com.cannolicatfish.rankine.fluids.*;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import com.cannolicatfish.rankine.potion.RankineEffects;
import com.cannolicatfish.rankine.potion.RankinePotions;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.POIFixer;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.util.colors.*;
import com.cannolicatfish.rankine.world.RankineBiomeFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod("rankine")
public class ProjectRankine {
    public static final String MODID = "rankine";
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static RankineSetup setup = new RankineSetup();
    public static final Logger LOGGER = LogManager.getLogger();

    public ProjectRankine() {
        IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG, "rankine-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG, "rankine-client.toml");

        MinecraftForge.EVENT_BUS.register(this);
        ForgeMod.enableMilkFluid();
        Bus.addListener(this::CommonSetup);
        Bus.addListener(this::ClientSetup);

        RankineBlocks.REGISTRY.register(Bus);
        RankineItems.REGISTRY.register(Bus);
        RankineFeatures.REGISTRY.register(Bus);
        RankineSoundEvents.REGISTRY.register(Bus);
        //Bus.addListener(this::construct);

        Bus.addListener(this::LoadComplete);

        WorldgenUtils.initOreTextures();


    }

    private void CommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");

        WorldgenUtils.initConfigs();
        RankinePacketHandler.register();
        POIFixer.fixPOITypeBlockStates(RankinePOIs.TEMPLATE_TABLE_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.PISTON_CRUSHER_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.BOTANIST_STATION_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.GEM_CUTTER_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.ROCK_COLLECTOR_POI);
        proxy.init();

        DeferredWorkQueue.runLater(() -> {
            RankineRecipes.registerPredicates();
            RankineRecipes.registerPotionRecipes();
            RankineRecipes.registerDispenserBehaviors();
            RankineBiomeFeatures.registerConfiguredFeatures();
            //WoodType.register(RankineBlocks.CEDAR);
        });

        LOGGER.info("Rankine: \"CommonSetup\" Event Complete!");
    }

    private void ClientSetup(FMLClientSetupEvent event) {
        LOGGER.debug("Rankine: \"ClientSetup Event\" Starting...");

        event.enqueueWork(ClientProxy::registerItemProperties);

        LOGGER.info("Rankine: \"ClientSetup\" Event Complete!");
    }

    private void LoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.debug("Rankine: \"Load Complete Event\" Starting...");
        VanillaIntegration.init();
        LOGGER.info("Rankine: \"Load Complete\" Event Complete!");
    }



    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onItemColorRegistry(final ColorHandlerEvent.Item event) {
            for (Block b : RankineLists.GRASS_BLOCKS) {
                event.getItemColors().register(new GrassItemBaseColor(), b.asItem());
            }
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SHORT_GRASS.get());

            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK.get());
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK_SLAB.get());
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK_VERTICAL_SLAB.get());
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK_STAIRS.get());
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK_WALL.get());

            event.getItemColors().register(new LeavesItemBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());


            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_GEAR::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_DUST::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_INGOT::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_NUGGET::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_PLATE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_ROD::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_WIRE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_PICKAXE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_AXE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_SHOVEL::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_SWORD::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_HOE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_SPEAR::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_HAMMER::get);
            event.getItemColors().register(new TemplateItemColor(), RankineItems.ALLOY_TEMPLATE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_HELMET::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_CHESTPLATE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_LEGGINGS::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_BOOTS::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_ARROW::get);
            event.getItemColors().register(new SGVDItemColor(), RankineItems.SHULKER_GAS_VACUUM::get);
        }


        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onBlockColorRegistry(final ColorHandlerEvent.Block event) {
            event.getBlockColors().register(new CrucibleColor(), RankineBlocks.CRUCIBLE_BLOCK.get());
            for (Block b : RankineLists.GRASS_BLOCKS) {
                event.getBlockColors().register(new GrassBlockBaseColor(), b);
            }
            event.getBlockColors().register(new OrnamentColor(), RankineBlocks.ORNAMENT.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SHORT_GRASS.get());

            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK_SLAB.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK_VERTICAL_SLAB.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK_WALL.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK_STAIRS.get());

            event.getBlockColors().register(new LeavesBlockBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());


        }

        @SubscribeEvent
        public static void addEntityAttributes(final EntityAttributeCreationEvent event) {
            event.put(RankineEntityTypes.BEAVER, BeaverEntity.getAttributes().create());
            event.put(RankineEntityTypes.MANTLE_GOLEM, MantleGolemEntity.getAttributes().create());
            event.put(RankineEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemEntity.getAttributes().create());
            event.put(RankineEntityTypes.PERIDOT_MANTLE_GOLEM, PeridotMantleGolemEntity.getAttributes().create());
            event.put(RankineEntityTypes.DESMOXYTE, DesmoxyteEntity.getAttributes().create());
            event.put(RankineEntityTypes.DEMONYTE, DemonyteEntity.getAttributes().create());
            event.put(RankineEntityTypes.DRAGONYTE, DragonyteEntity.getAttributes().create());
        }



        @SubscribeEvent
        public static void onPOIRegistry(final RegistryEvent.Register<PointOfInterestType> event) {
            event.getRegistry().register(RankinePOIs.TEMPLATE_TABLE_POI.setRegistryName(ProjectRankine.MODID,"template_table_poi"));
            event.getRegistry().register(RankinePOIs.PISTON_CRUSHER_POI.setRegistryName(ProjectRankine.MODID,"piston_crusher_poi"));
            event.getRegistry().register(RankinePOIs.BOTANIST_STATION_POI.setRegistryName(ProjectRankine.MODID,"potted_plant_poi"));
            event.getRegistry().register(RankinePOIs.GEM_CUTTER_POI.setRegistryName(ProjectRankine.MODID,"gem_cutter_poi"));
            event.getRegistry().register(RankinePOIs.ROCK_COLLECTOR_POI.setRegistryName(ProjectRankine.MODID,"rock_collector_poi"));
        }

        @SubscribeEvent
        public static void onPlacementRegistry(final RegistryEvent.Register<Placement<?>> event) {
            //event.getRegistry().register(RankineBiomeFeatures.REPLACER_PLACEMENT.setRegistryName(ProjectRankine.MODID,"replacer_placement"));
            //event.getRegistry().register(RankineBiomeFeatures.INTRUSION_PLACEMENT.setRegistryName(ProjectRankine.MODID,"intrusion_placement"));
        }

        @SubscribeEvent
        public static void onVillagerProfessionRegistry(final RegistryEvent.Register<VillagerProfession> event) {
            event.getRegistry().register(RankineVillagerProfessions.METALLURGIST.setRegistryName(ProjectRankine.MODID,"metallurgist"));
            event.getRegistry().register(RankineVillagerProfessions.MINERALOGIST.setRegistryName(ProjectRankine.MODID,"mineralogist"));
            event.getRegistry().register(RankineVillagerProfessions.BOTANIST.setRegistryName(ProjectRankine.MODID,"botanist"));
            event.getRegistry().register(RankineVillagerProfessions.GEM_CUTTER.setRegistryName(ProjectRankine.MODID,"gem_cutter"));
            event.getRegistry().register(RankineVillagerProfessions.ROCK_COLLECTOR.setRegistryName(ProjectRankine.MODID,"rock_collector"));
        }

        @SubscribeEvent
         public static void onRecipeSerializersRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            event.getRegistry().register(AlloyCraftingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"alloy_crafting"));
            event.getRegistry().register(CrushingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"crushing"));
            event.getRegistry().register(AlloyingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"alloying"));
            event.getRegistry().register(BeehiveOvenRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"beehive_oven"));
            event.getRegistry().register(SluicingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"sluicing"));
            event.getRegistry().register(ElementRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"element"));
            event.getRegistry().register(CrucibleRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"crucible"));
            event.getRegistry().register(EvaporationRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"evaporation"));
            event.getRegistry().register(FusionFurnaceRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"fusion_furnace"));
            event.getRegistry().register(RockGeneratorRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"rock_generator"));
            event.getRegistry().register(TreetappingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"treetapping"));
            event.getRegistry().register(AirDistillationRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"air_distillation"));
            event.getRegistry().register(MixingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"mixing"));

            event.getRegistry().register(JamRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"crafting_special_jam"));
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get()).build(null).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(PistonCrusherTile::new, RankineBlocks.PISTON_CRUSHER.get()).build(null).setRegistryName(ProjectRankine.MODID,"piston_crusher"));
            event.getRegistry().register(TileEntityType.Builder.create(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get()).build(null).setRegistryName(ProjectRankine.MODID,"crucible"));
            event.getRegistry().register(TileEntityType.Builder.create(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get()).build(null).setRegistryName(ProjectRankine.MODID,"induction_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(FusionFurnaceTile::new, RankineBlocks.FUSION_FURNACE.get()).build(null).setRegistryName(ProjectRankine.MODID,"fusion_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(GyratoryCrusherTile::new, RankineBlocks.GYRATORY_CRUSHER.get()).build(null).setRegistryName(ProjectRankine.MODID,"gyratory_crusher"));
            event.getRegistry().register(TileEntityType.Builder.create(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get()).build(null).setRegistryName(ProjectRankine.MODID,"evaporation_tower"));
            event.getRegistry().register(TileEntityType.Builder.create(GasBottlerTile::new, RankineBlocks.GAS_BOTTLER.get()).build(null).setRegistryName(ProjectRankine.MODID,"gas_condenser"));
            event.getRegistry().register(TileEntityType.Builder.create(GasVentTile::new, RankineBlocks.GAS_VENT.get()).build(null).setRegistryName(ProjectRankine.MODID,"gas_vent"));
            event.getRegistry().register(TileEntityType.Builder.create(TreeTapTile::new, RankineBlocks.TREE_TAP.get()).build(null).setRegistryName(ProjectRankine.MODID,"tree_tap"));
            event.getRegistry().register(TileEntityType.Builder.create(SedimentFanTile::new, RankineBlocks.SEDIMENT_FAN.get()).build(null).setRegistryName(ProjectRankine.MODID,"sediment_fan"));
            event.getRegistry().register(TileEntityType.Builder.create(RankineBoxTile::new, RankineBlocks.RANKINE_BOX.get()).build(null).setRegistryName(ProjectRankine.MODID,"rankine_box"));
            event.getRegistry().register(TileEntityType.Builder.create(MixingBarrelTile::new, RankineBlocks.MIXING_BARREL.get()).build(null).setRegistryName(ProjectRankine.MODID,"mixing_barrel"));
            //event.getRegistry().register(TileEntityType.Builder.create(LaserQuarryTile::new, RankineBlocks.LASER_QUARRY.get()).build(null).setRegistryName(ProjectRankine.MODID,"laser_quarry"));
            event.getRegistry().register(TileEntityType.Builder.create(AlloyBlockTile::new, RankineBlocks.BRONZE_BLOCK.get()).build(null).setRegistryName(ProjectRankine.MODID,"bronze_alloy_block"));
            //event.getRegistry().register(TileEntityType.Builder.create(SodiumVaporLampTile::new, RankineBlocks.SODIUM_VAPOR_LAMP.get()).build(null).setRegistryName(ProjectRankine.MODID,"sodium_vapor_lamp"));
            event.getRegistry().register(TileEntityType.Builder.create(TilledSoilTile::new, RankineBlocks.TILLED_SOIL.get()).build(null).setRegistryName(ProjectRankine.MODID,"tilled_soil"));
            event.getRegistry().register(TileEntityType.Builder.create(GroundTapTile::new, RankineBlocks.GROUND_TAP.get()).build(null).setRegistryName(ProjectRankine.MODID,"ground_tap"));
            event.getRegistry().register(TileEntityType.Builder.create(MaterialTestingTableTile::new, RankineBlocks.MATERIAL_TESTING_TABLE.get()).build(null).setRegistryName(ProjectRankine.MODID,"material_testing_table"));
            event.getRegistry().register(TileEntityType.Builder.create(BeehiveOvenTile::new, RankineBlocks.BEEHIVE_OVEN_PIT.get()).build(null).setRegistryName(ProjectRankine.MODID,"beehive_oven"));
            event.getRegistry().register(TileEntityType.Builder.create(DistillationTowerTile::new, RankineBlocks.DISTILLATION_TOWER.get()).build(null).setRegistryName(ProjectRankine.MODID,"distillation_tower"));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(RankineEntityTypes.FLINT_SPEAR);
            event.getRegistry().register(RankineEntityTypes.PEWTER_SPEAR);
            event.getRegistry().register(RankineEntityTypes.BRONZE_SPEAR);
            event.getRegistry().register(RankineEntityTypes.IRON_SPEAR);
            event.getRegistry().register(RankineEntityTypes.INVAR_SPEAR);
            event.getRegistry().register(RankineEntityTypes.STEEL_SPEAR);
            event.getRegistry().register(RankineEntityTypes.ROSE_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.WHITE_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.GREEN_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.BLUE_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.PURPLE_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.BLACK_GOLD_SPEAR);
            event.getRegistry().register(RankineEntityTypes.AMALGAM_SPEAR);
            event.getRegistry().register(RankineEntityTypes.DIAMOND_SPEAR);
            event.getRegistry().register(RankineEntityTypes.NETHERITE_SPEAR);
            event.getRegistry().register(RankineEntityTypes.NICKEL_SUPERALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.COBALT_SUPERALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.TUNGSTEN_HEAVY_ALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.TITANIUM_ALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.ALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.STAINLESS_STEEL_SPEAR);
            event.getRegistry().register(RankineEntityTypes.ROPE_COIL_ARROW);
            event.getRegistry().register(RankineEntityTypes.THORIUM_ARROW);
            event.getRegistry().register(RankineEntityTypes.MAGNESIUM_ARROW);
            event.getRegistry().register(RankineEntityTypes.ALLOY_ARROW);
            event.getRegistry().register(RankineEntityTypes.REACTIVE_ITEM.setRegistryName(ProjectRankine.MODID,"reactive_item"));
            event.getRegistry().register(RankineEntityTypes.CANNONBALL.setRegistryName(ProjectRankine.MODID,"cannonball"));
            event.getRegistry().register(RankineEntityTypes.MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"mantle_golem"));
            event.getRegistry().register(RankineEntityTypes.DIAMOND_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"diamond_mantle_golem"));
            event.getRegistry().register(RankineEntityTypes.PERIDOT_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"peridot_mantle_golem"));
            event.getRegistry().register(RankineEntityTypes.DESMOXYTE.setRegistryName(ProjectRankine.MODID,"desmoxyte"));
            event.getRegistry().register(RankineEntityTypes.DEMONYTE.setRegistryName(ProjectRankine.MODID,"demonyte"));
            event.getRegistry().register(RankineEntityTypes.DRAGONYTE.setRegistryName(ProjectRankine.MODID,"dragonyte"));
            event.getRegistry().register(RankineEntityTypes.BEAVER.setRegistryName(ProjectRankine.MODID,"beaver"));
            event.getRegistry().register(RankineEntityTypes.RANKINE_BOAT.setRegistryName(ProjectRankine.MODID,"cedar_boat"));
        }

        @SubscribeEvent
        public static void clientSetupEvent(FMLClientSetupEvent event)
        {
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.FLINT_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.PEWTER_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.BRONZE_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.IRON_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.INVAR_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.STEEL_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.ROSE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.WHITE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.GREEN_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.BLUE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.PURPLE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.BLACK_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.AMALGAM_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.DIAMOND_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.NETHERITE_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.NICKEL_SUPERALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.COBALT_SUPERALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.TUNGSTEN_HEAVY_ALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.STAINLESS_STEEL_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.TITANIUM_ALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.ALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.REACTIVE_ITEM, ReactiveItemRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.MANTLE_GOLEM,MantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.PERIDOT_MANTLE_GOLEM,PeridotMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.BEAVER, BeaverRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.DESMOXYTE, DesmoxyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.DEMONYTE, DemonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.DRAGONYTE, DragonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.RANKINE_BOAT,RankineBoatRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.ROPE_COIL_ARROW, RopeCoilArrowRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.THORIUM_ARROW,ThoriumArrowRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.MAGNESIUM_ARROW,MagnesiumArrowRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.ALLOY_ARROW,AlloyArrowRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.CANNONBALL, CannonballRenderer::new);
        }
        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new AlloyFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new PistonCrusherContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"piston_crusher"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new CrucibleContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"crucible"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new MixingBarrelContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"mixing_barrel"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new InductionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"induction_furnace"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new FusionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"fusion_furnace"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new GyratoryCrusherContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"gyratory_crusher"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new EvaporationTowerContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"evaporation_tower"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new GasBottlerContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"gas_condenser"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new RankineBoxContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"rankine_box"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new MaterialTestingTableContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"material_testing_table"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new TemplateTableContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"template_table"));


            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new ElementIndexerContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"element_indexer"));
/*
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new LaserQuarryContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"laser_quarry"));

 */

        //    event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
        //        BlockPos pos = data.readBlockPos();
         //       return new SodiumVaporLampContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
         //   }).setRegistryName(ProjectRankine.MODID,"sodium_vapor_lamp"));
        }

        @SubscribeEvent
        public static void registerEffects(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(
                    RankineEffects.MERCURY_POISONING,
                    RankineEffects.CONDUCTIVE,
                    RankineEffects.RADIATION_POISONING
            );
        }

        @SubscribeEvent
        public static void registerPotions(final RegistryEvent.Register<Potion> event) {
            event.getRegistry().registerAll(
                    RankinePotions.MERCURY_POISON,
                    RankinePotions.CONDUCTIVE_POTION
            );
        }

        @SubscribeEvent
        public static void registerFluids(final RegistryEvent.Register<Fluid> event) {
            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.LIQUID_MERCURY, () -> RankineFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(LiquidMercuryFluid.FLUID_STILL, LiquidMercuryFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(LiquidMercuryFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.LIQUID_MERCURY_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LIQUID_MERCURY.get())).setRegistryName(ProjectRankine.MODID,"liquid_mercury"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.LIQUID_MERCURY, () -> RankineFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(LiquidMercuryFluid.FLUID_STILL, LiquidMercuryFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(LiquidMercuryFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.LIQUID_MERCURY_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LIQUID_MERCURY.get())).setRegistryName(ProjectRankine.MODID,"liquid_mercury_flowing"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.SAP, () -> RankineFluids.FLOWING_SAP, FluidAttributes.builder(SapFluid.FLUID_STILL,SapFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(SapFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.SAP_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.SAP.get())).setRegistryName(ProjectRankine.MODID,"sap"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.SAP, () -> RankineFluids.FLOWING_SAP, FluidAttributes.builder(SapFluid.FLUID_STILL,SapFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(SapFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.SAP_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.SAP.get())).setRegistryName(ProjectRankine.MODID,"flowing_sap"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.MAPLE_SAP, () -> RankineFluids.FLOWING_MAPLE_SAP, FluidAttributes.builder(MapleSapFluid.FLUID_STILL,MapleSapFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(MapleSapFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.MAPLE_SAP_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.MAPLE_SAP.get())).setRegistryName(ProjectRankine.MODID,"maple_sap"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.MAPLE_SAP, () -> RankineFluids.FLOWING_MAPLE_SAP, FluidAttributes.builder(MapleSapFluid.FLUID_STILL,MapleSapFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(MapleSapFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.MAPLE_SAP_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.MAPLE_SAP.get())).setRegistryName(ProjectRankine.MODID,"flowing_maple_sap"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.LATEX, () -> RankineFluids.FLOWING_LATEX, FluidAttributes.builder(LatexFluid.FLUID_STILL,LatexFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(LatexFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.LATEX_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LATEX.get())).setRegistryName(ProjectRankine.MODID,"latex"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.LATEX, () -> RankineFluids.FLOWING_LATEX, FluidAttributes.builder(LatexFluid.FLUID_STILL,LatexFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(LatexFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.LATEX_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LATEX.get())).setRegistryName(ProjectRankine.MODID,"flowing_latex"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.JUGLONE, () -> RankineFluids.FLOWING_JUGLONE, FluidAttributes.builder(JugloneFluid.FLUID_STILL,JugloneFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(JugloneFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.JUGLONE_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.JUGLONE.get())).setRegistryName(ProjectRankine.MODID,"juglone"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.JUGLONE, () -> RankineFluids.FLOWING_JUGLONE, FluidAttributes.builder(JugloneFluid.FLUID_STILL,JugloneFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(JugloneFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.JUGLONE_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.JUGLONE.get())).setRegistryName(ProjectRankine.MODID,"flowing_juglone"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.RESIN, () -> RankineFluids.FLOWING_RESIN, FluidAttributes.builder(ResinFluid.FLUID_STILL,ResinFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(ResinFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.RESIN_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.RESIN.get())).setRegistryName(ProjectRankine.MODID,"resin"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.RESIN, () -> RankineFluids.FLOWING_RESIN, FluidAttributes.builder(ResinFluid.FLUID_STILL,ResinFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(ResinFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.RESIN_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.RESIN.get())).setRegistryName(ProjectRankine.MODID,"flowing_resin"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.AQUA_REGIA, () -> RankineFluids.FLOWING_AQUA_REGIA, FluidAttributes.builder(AquaRegiaFluid.FLUID_STILL,AquaRegiaFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(AquaRegiaFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.AQUA_REGIA_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.AQUA_REGIA.get())).setRegistryName(ProjectRankine.MODID,"aqua_regia"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.AQUA_REGIA, () -> RankineFluids.FLOWING_AQUA_REGIA, FluidAttributes.builder(AquaRegiaFluid.FLUID_STILL,AquaRegiaFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(AquaRegiaFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.AQUA_REGIA_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.AQUA_REGIA.get())).setRegistryName(ProjectRankine.MODID,"flowing_aqua_regia"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.CARBON_DISULFIDE, () -> RankineFluids.FLOWING_CARBON_DISULFIDE, FluidAttributes.builder(CarbonDisulfideFluid.FLUID_STILL,CarbonDisulfideFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(CarbonDisulfideFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.CARBON_DISULFIDE_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.CARBON_DISULFIDE.get())).setRegistryName(ProjectRankine.MODID,"carbon_disulfide"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.CARBON_DISULFIDE, () -> RankineFluids.FLOWING_CARBON_DISULFIDE, FluidAttributes.builder(CarbonDisulfideFluid.FLUID_STILL,CarbonDisulfideFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(CarbonDisulfideFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.CARBON_DISULFIDE_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.CARBON_DISULFIDE.get())).setRegistryName(ProjectRankine.MODID,"flowing_carbon_disulfide"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.HEXAFLUOROSILICIC_ACID, () -> RankineFluids.FLOWING_HEXAFLUOROSILICIC_ACID, FluidAttributes.builder(HexafluorosilicicAcidFluid.FLUID_STILL,HexafluorosilicicAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(HexafluorosilicicAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.HEXAFLUOROSILICIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"hexafluorosilicic_acid"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.HEXAFLUOROSILICIC_ACID, () -> RankineFluids.FLOWING_HEXAFLUOROSILICIC_ACID, FluidAttributes.builder(HexafluorosilicicAcidFluid.FLUID_STILL,HexafluorosilicicAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(HexafluorosilicicAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.HEXAFLUOROSILICIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"flowing_hexafluorosilicic_acid"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.HYDROBROMIC_ACID, () -> RankineFluids.FLOWING_HYDROBROMIC_ACID, FluidAttributes.builder(HydrobromicAcidFluid.FLUID_STILL,HydrobromicAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(HydrobromicAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.HYDROBROMIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.HYDROBROMIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"hydrobromic_acid"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.HYDROBROMIC_ACID, () -> RankineFluids.FLOWING_HYDROBROMIC_ACID, FluidAttributes.builder(HydrobromicAcidFluid.FLUID_STILL,HydrobromicAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(HydrobromicAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.HYDROBROMIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.HYDROBROMIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"flowing_hydrobromic_acid"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.GRAY_MUD, () -> RankineFluids.FLOWING_GRAY_MUD, FluidAttributes.builder(GrayMudFluid.FLUID_STILL,GrayMudFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(GrayMudFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.GRAY_MUD_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.GRAY_MUD.get())).setRegistryName(ProjectRankine.MODID,"gray_mud"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.GRAY_MUD, () -> RankineFluids.FLOWING_GRAY_MUD, FluidAttributes.builder(GrayMudFluid.FLUID_STILL,GrayMudFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(GrayMudFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.GRAY_MUD_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.GRAY_MUD.get())).setRegistryName(ProjectRankine.MODID,"flowing_gray_mud"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.RED_MUD, () -> RankineFluids.FLOWING_RED_MUD, FluidAttributes.builder(RedMudFluid.FLUID_STILL,RedMudFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(RedMudFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.RED_MUD_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.RED_MUD.get())).setRegistryName(ProjectRankine.MODID,"red_mud"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.RED_MUD, () -> RankineFluids.FLOWING_RED_MUD, FluidAttributes.builder(RedMudFluid.FLUID_STILL,RedMudFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(RedMudFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.RED_MUD_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.RED_MUD.get())).setRegistryName(ProjectRankine.MODID,"flowing_red_mud"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.SULFURIC_ACID, () -> RankineFluids.FLOWING_SULFURIC_ACID, FluidAttributes.builder(SulfuricAcidFluid.FLUID_STILL,SulfuricAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(SulfuricAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.SULFURIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.SULFURIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"sulfuric_acid"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.SULFURIC_ACID, () -> RankineFluids.FLOWING_SULFURIC_ACID, FluidAttributes.builder(SulfuricAcidFluid.FLUID_STILL,SulfuricAcidFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(SulfuricAcidFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.SULFURIC_ACID_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.SULFURIC_ACID.get())).setRegistryName(ProjectRankine.MODID,"flowing_sulfuric_acid"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.BLACK_LIQUOR, () -> RankineFluids.FLOWING_BLACK_LIQUOR, FluidAttributes.builder(BlackLiquorFluid.FLUID_STILL,BlackLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(BlackLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.BLACK_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.BLACK_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"black_liquor"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.BLACK_LIQUOR, () -> RankineFluids.FLOWING_BLACK_LIQUOR, FluidAttributes.builder(BlackLiquorFluid.FLUID_STILL,BlackLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(BlackLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.BLACK_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.BLACK_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"flowing_black_liquor"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.GREEN_LIQUOR, () -> RankineFluids.FLOWING_GREEN_LIQUOR, FluidAttributes.builder(GreenLiquorFluid.FLUID_STILL,GreenLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(GreenLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.GREEN_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.GREEN_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"green_liquor"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.GREEN_LIQUOR, () -> RankineFluids.FLOWING_GREEN_LIQUOR, FluidAttributes.builder(GreenLiquorFluid.FLUID_STILL,GreenLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(GreenLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.GREEN_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.GREEN_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"flowing_green_liquor"));

            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> RankineFluids.WHITE_LIQUOR, () -> RankineFluids.FLOWING_WHITE_LIQUOR, FluidAttributes.builder(WhiteLiquorFluid.FLUID_STILL,WhiteLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(WhiteLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.WHITE_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.WHITE_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"white_liquor"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> RankineFluids.WHITE_LIQUOR, () -> RankineFluids.FLOWING_WHITE_LIQUOR, FluidAttributes.builder(WhiteLiquorFluid.FLUID_STILL,WhiteLiquorFluid.FLUID_FLOWING).color(0xFFFFFFFF).overlay(WhiteLiquorFluid.OVERLAY).sound(SoundEvents.ITEM_BUCKET_FILL,SoundEvents.ITEM_BUCKET_EMPTY))
                    .bucket(RankineItems.WHITE_LIQUOR_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.WHITE_LIQUOR.get())).setRegistryName(ProjectRankine.MODID,"flowing_white_liquor"));
        }

        @SubscribeEvent
        public static void registerItemRemappings(final RegistryEvent.MissingMappings<Item> event) {
            Map<ResourceLocation, Item> itemRemappings = RankineRemappings.getItemRemappings();
            ImmutableList<RegistryEvent.MissingMappings.Mapping<Item>> mappings = event.getAllMappings();
            for (RegistryEvent.MissingMappings.Mapping<Item> map : mappings) {
                if (itemRemappings.containsKey(map.key)) {
                    map.remap(itemRemappings.get(map.key));
                }
            }
        }

        @SubscribeEvent
        public static void registerBlockRemappings(final RegistryEvent.MissingMappings<Block> event) {
            Map<ResourceLocation, Block> blockRemappings = RankineRemappings.getBlockRemappings();
            ImmutableList<RegistryEvent.MissingMappings.Mapping<Block>> mappings = event.getAllMappings();
            for (RegistryEvent.MissingMappings.Mapping<Block> map : mappings) {
                if (blockRemappings.containsKey(map.key)) {
                    map.remap(blockRemappings.get(map.key));
                }
            }
        }


        @SubscribeEvent
        public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
            final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
            final EquipmentSlotType[] HAND_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND};

            event.getRegistry().register(new PunctureEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"puncture"));
            event.getRegistry().register(new SwingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"swing"));
            event.getRegistry().register(new DazeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"daze"));
            event.getRegistry().register(new AtomizeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"atomize"));
            event.getRegistry().register(new ExcavateEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"excavate"));
            event.getRegistry().register(new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"impact"));
            event.getRegistry().register(new QuakeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"quake"));
            event.getRegistry().register(new ForagingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"foraging"));
            event.getRegistry().register(new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"lightning_aspect"));

            event.getRegistry().register(new DuneWalkerEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"dune_walker"));
            event.getRegistry().register(new SnowDrifterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"snow_drifter"));
            event.getRegistry().register(new SpeedSkaterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"speed_skater"));
            event.getRegistry().register(new GasProtectionEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"gas_protection"));
            event.getRegistry().register(new AntiquatedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"antiquated"));
            event.getRegistry().register(new CleanseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"cleanse"));

            event.getRegistry().register(new EndpointEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"endpoint"));
            event.getRegistry().register(new EndobioticEnchantment(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS).setRegistryName(ProjectRankine.MODID,"endobiotic"));


            event.getRegistry().register(new GhastRegenerationEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"ghast_regeneration"));
            event.getRegistry().register(new WitheringCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"withering_curse"));
            event.getRegistry().register(new ShapeMemoryEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"shape_memory"));
            event.getRegistry().register(new GuardEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS).setRegistryName(ProjectRankine.MODID,"guard"));


            event.getRegistry().register(new PreparationEnchantment(Enchantment.Rarity.COMMON, HAND_SLOTS).setRegistryName(ProjectRankine.MODID,"preparation"));
            event.getRegistry().register(new PoisonAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"poison_aspect"));
            event.getRegistry().register(new BackstabEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"backstab"));
            event.getRegistry().register(new GraftingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"grafting"));
            event.getRegistry().register(new RetaliateEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS).setRegistryName(ProjectRankine.MODID,"retaliate"));
            event.getRegistry().register(new RetreatEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS).setRegistryName(ProjectRankine.MODID,"retreat"));


            event.getRegistry().register(new LeverageEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"leverage"));
            event.getRegistry().register(new PryingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"prying"));
            event.getRegistry().register(new LiftEnchantment(Enchantment.Rarity.VERY_RARE, HAND_SLOTS).setRegistryName(ProjectRankine.MODID,"lift"));
            event.getRegistry().register(new RetrievalEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"retrieval"));
        }

    }


}

