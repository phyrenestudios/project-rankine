package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleTile;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.blocks.laserquarry.LaserQuarryContainer;
import com.cannolicatfish.rankine.blocks.laserquarry.LaserQuarryTile;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.enchantment.*;
import com.cannolicatfish.rankine.entities.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import com.cannolicatfish.rankine.potion.ModEffects;
import com.cannolicatfish.rankine.potion.ModPotions;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.recipe.AlloyCraftingRecipe;
import com.cannolicatfish.rankine.util.POIFixer;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.common.*;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("rankine")
public class ProjectRankine {
    public static final String MODID = "rankine";
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static RankineSetup setup = new RankineSetup();
    public static final Logger LOGGER = LogManager.getLogger();

    public ProjectRankine() {
        IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-common.toml"));
        //ModRecipes.init();
        MinecraftForge.EVENT_BUS.register(this);
        Bus.addListener(this::CommonSetup);
        Bus.addListener(this::ClientSetup);
        RankineBlocks.REGISTRY.register(Bus);
        RankineItems.REGISTRY.register(Bus);
        Bus.addListener(this::LoadComplete);

    }


    private void CommonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");
        POIFixer.fixPOITypeBlockStates(RankinePOIs.TEMPLATE_TABLE_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.PISTON_CRUSHER_POI);
        POIFixer.fixPOITypeBlockStates(RankinePOIs.BOTANIST_STATION_POI);
        proxy.init();
        //RankineBiomes.addRankineBiomes();

        //OreGen.setupOreGeneration();
        //DecorationGen.setupDecoration();
        DeferredWorkQueue.runLater(() -> {
            RankineRecipes.registerPredicates();
            RankineRecipes.registerPotionRecipes();
            GlobalEntityTypeAttributes.put(RankineEntityTypes.BEAVER, BeaverEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.MANTLE_GOLEM, MantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.PERIDOT_MANTLE_GOLEM, PeridotMantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.DESMOXYTE, DesmoxyteEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.DEMONYTE, DemonyteEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(RankineEntityTypes.DRAGONYTE, DragonyteEntity.getAttributes().create());
        });
        LOGGER.info("Rankine: \"CommonSetup\" Event Complete!");
    }

    private void ClientSetup(FMLClientSetupEvent event) {
        LOGGER.debug("Rankine: \"ClientSetup Event\" Starting...");

        LOGGER.info("Rankine: \"ClientSetup\" Event Complete!");
    }

    private void LoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.debug("Rankine: \"Load Complete Event\" Starting...");
        //StructureGen.setupStructureGen();
        VanillaIntegration.init();
        LOGGER.info("Rankine: \"Load Complete\" Event Complete!");
    }



    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onPOIRegistry(final RegistryEvent.Register<PointOfInterestType> event) {
            event.getRegistry().register(RankinePOIs.TEMPLATE_TABLE_POI.setRegistryName(ProjectRankine.MODID,"template_table_poi"));
            event.getRegistry().register(RankinePOIs.PISTON_CRUSHER_POI.setRegistryName(ProjectRankine.MODID,"piston_crusher_poi"));
            event.getRegistry().register(RankinePOIs.BOTANIST_STATION_POI.setRegistryName(ProjectRankine.MODID,"potted_plant_poi"));
        }

        @SubscribeEvent
        public static void onVillagerProfessionRegistry(final RegistryEvent.Register<VillagerProfession> event) {
            event.getRegistry().register(RankineVillagerProfessions.METALLURGIST.setRegistryName(ProjectRankine.MODID,"metallurgist"));
            event.getRegistry().register(RankineVillagerProfessions.MINERALOGIST.setRegistryName(ProjectRankine.MODID,"mineralogist"));
            event.getRegistry().register(RankineVillagerProfessions.BOTANIST.setRegistryName(ProjectRankine.MODID,"botanist"));
        }

        @SubscribeEvent
         public static void onRecipeSerializersRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            event.getRegistry().register(AlloyCraftingRecipe.SERIALIZER.setRegistryName(ProjectRankine.MODID,"alloy_crafting"));
         }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get()).build(null).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(PistonCrusherTile::new, RankineBlocks.PISTON_CRUSHER.get()).build(null).setRegistryName(ProjectRankine.MODID,"piston_crusher"));
            event.getRegistry().register(TileEntityType.Builder.create(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get()).build(null).setRegistryName(ProjectRankine.MODID,"crucible"));
            event.getRegistry().register(TileEntityType.Builder.create(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get()).build(null).setRegistryName(ProjectRankine.MODID,"induction_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get()).build(null).setRegistryName(ProjectRankine.MODID,"evaporation_tower"));
            event.getRegistry().register(TileEntityType.Builder.create(LaserQuarryTile::new, RankineBlocks.LASER_QUARRY.get()).build(null).setRegistryName(ProjectRankine.MODID,"laser_quarry"));

        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(RankineEntityTypes.FLINT_SPEAR);
            event.getRegistry().register(RankineEntityTypes.PEWTER_SPEAR);
            event.getRegistry().register(RankineEntityTypes.BRONZE_SPEAR);
            event.getRegistry().register(RankineEntityTypes.IRON_SPEAR);
            event.getRegistry().register(RankineEntityTypes.METEORIC_IRON_SPEAR);
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
            event.getRegistry().register(RankineEntityTypes.ALLOY_SPEAR);
            event.getRegistry().register(RankineEntityTypes.STAINLESS_STEEL_SPEAR);
            event.getRegistry().register(RankineEntityTypes.THORIUM_ARROW);
            event.getRegistry().register(RankineEntityTypes.REACTIVE_ITEM.setRegistryName(ProjectRankine.MODID,"reactive_item"));
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
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.METEORIC_IRON_SPEAR, SpearRenderFactory.instance);
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
            RenderingRegistry.registerEntityRenderingHandler(RankineEntityTypes.THORIUM_ARROW,ThoriumArrowRenderer.instance);
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
                return new InductionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"induction_furnace"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new EvaporationTowerContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"evaporation_tower"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new TemplateTableContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"template_table"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new ElementIndexerContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"element_indexer"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new LaserQuarryContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"laser_quarry"));
        }

        @SubscribeEvent
        public static void registerEffects(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(
                    ModEffects.MERCURY_POISONING
            );
        }

        @SubscribeEvent
        public static void registerPotions(final RegistryEvent.Register<Potion> event) {
            event.getRegistry().registerAll(
                    ModPotions.MERCURY_POISON
            );
        }

        @SubscribeEvent
        public static void registerFluids(final RegistryEvent.Register<Fluid> event) {
            final ResourceLocation FLUID_STILL = new ResourceLocation("rankine:block/liquid_mercury_still");
            final ResourceLocation FLUID_FLOWING = new ResourceLocation("rankine:block/liquid_mercury_flow");
            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_MERCURY, () -> ModFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xFFFFFFFF))
                    .bucket(RankineItems.LIQUID_MERCURY_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LIQUID_MERCURY_BLOCK.get())).setRegistryName(ProjectRankine.MODID,"liquid_mercury"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_MERCURY, () -> ModFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xFFFFFFFF))
                    .bucket(RankineItems.LIQUID_MERCURY_BUCKET).block(() -> (FlowingFluidBlock) RankineBlocks.LIQUID_MERCURY_BLOCK.get())).setRegistryName(ProjectRankine.MODID,"liquid_mercury_flowing"));
        }

        @SubscribeEvent
        public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
            event.getRegistry().register(new PunctureEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"puncture"));
            event.getRegistry().register(new SwingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"swing"));
            event.getRegistry().register(new DazeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"daze"));
            event.getRegistry().register(new BlastEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"blast"));
            event.getRegistry().register(new SmithingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"smithing"));
            event.getRegistry().register(new AtomizeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"atomize"));
            event.getRegistry().register(new ExcavateEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"excavate"));
            event.getRegistry().register(new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"impact"));
            event.getRegistry().register(new QuakeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"quake"));
            event.getRegistry().register(new ForagingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"foraging"));
            event.getRegistry().register(new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"lightning_aspect"));
            event.getRegistry().register(new DuneWalkerEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"dune_walker"));
            event.getRegistry().register(new SnowDrifterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"snow_drifter"));
            event.getRegistry().register(new SpeedSkaterEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET).setRegistryName(ProjectRankine.MODID,"speed_skater"));
        }
/*
        @SubscribeEvent
        public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {

        }*/
    }


}

