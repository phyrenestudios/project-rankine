package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.enchantment.*;
import com.cannolicatfish.rankine.entities.*;
import com.cannolicatfish.rankine.entities.boss.ShroudedKingEntity;
import com.cannolicatfish.rankine.entities.boss.SolarFlareEntity;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerItem;
import com.cannolicatfish.rankine.potion.ModEffects;
import com.cannolicatfish.rankine.potion.ModPotions;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeContainer;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeTile;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeContainer;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.world.gen.OreGen;
import com.cannolicatfish.rankine.world.gen.DecorationGen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
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
    public static ModSetup setup = new ModSetup();
    public static final Logger LOGGER = LogManager.getLogger();

    public ProjectRankine() {
        IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-common.toml"));

        MinecraftForge.EVENT_BUS.register(this);

        Bus.addListener(this::CommonSetup);
        Bus.addListener(this::ClientSetup);
        ModBlocks.REGISTRY.register(Bus);
        ModItems.REGISTRY.register(Bus);
        ModRecipes.REGISTRY.register(Bus);
        Bus.addListener(this::LoadComplete);

    }


    private void CommonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");
        proxy.init();
        //RankineBiomes.addRankineBiomes();
        ModRecipes.init();
        OreGen.setupOreGeneration();
        DecorationGen.setupDecoration();
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.BEAVER, BeaverEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.STEAMER, SteamerEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.MANTLE_GOLEM, MantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.PERIDOT_MANTLE_GOLEM, PeridotMantleGolemEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DESMOXYTE, DesmoxyteEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DEMONYTE, DemonyteEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DRAGONYTE, DragonyteEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SHROUDED_KING, ShroudedKingEntity.getAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SOLAR_FLARE, SolarFlareEntity.getAttributes().create());
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
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(AlloyFurnaceTile::new, ModBlocks.ALLOY_FURNACE).build(null).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));
            event.getRegistry().register(TileEntityType.Builder.create(PistonCrusherTile::new, ModBlocks.PISTON_CRUSHER).build(null).setRegistryName(ProjectRankine.MODID,"piston_crusher"));
            event.getRegistry().register(TileEntityType.Builder.create(CoalForgeTile::new, ModBlocks.COAL_FORGE).build(null).setRegistryName(ProjectRankine.MODID,"coal_forge"));
            event.getRegistry().register(TileEntityType.Builder.create(FineryForgeTile::new, ModBlocks.FINERY_FORGE).build(null).setRegistryName(ProjectRankine.MODID,"finery_forge"));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(ModEntityTypes.FLINT_SPEAR);
            event.getRegistry().register(ModEntityTypes.BRONZE_SPEAR);
            event.getRegistry().register(ModEntityTypes.IRON_SPEAR);
            event.getRegistry().register(ModEntityTypes.METEORIC_IRON_SPEAR);
            event.getRegistry().register(ModEntityTypes.STEEL_SPEAR);
            event.getRegistry().register(ModEntityTypes.ROSE_GOLD_SPEAR);
            event.getRegistry().register(ModEntityTypes.WHITE_GOLD_SPEAR);
            event.getRegistry().register(ModEntityTypes.GREEN_GOLD_SPEAR);
            event.getRegistry().register(ModEntityTypes.BLUE_GOLD_SPEAR);
            event.getRegistry().register(ModEntityTypes.PURPLE_GOLD_SPEAR);
            event.getRegistry().register(ModEntityTypes.AMALGAM_SPEAR);
            event.getRegistry().register(ModEntityTypes.DIAMOND_SPEAR);
            event.getRegistry().register(ModEntityTypes.NETHERITE_SPEAR);
            event.getRegistry().register(ModEntityTypes.NICKEL_SUPERALLOY_SPEAR);
            event.getRegistry().register(ModEntityTypes.REACTIVE_ITEM.setRegistryName(ProjectRankine.MODID,"reactive_item"));
      //      event.getRegistry().register(ModEntityTypes.JAR_BLUE_FOXFIRE.setRegistryName(ProjectRankine.MODID,"jar_blue_foxfire"));
      //      event.getRegistry().register(ModEntityTypes.JAR_GREEN_FOXFIRE.setRegistryName(ProjectRankine.MODID,"jar_green_foxfire"));
            event.getRegistry().register(ModEntityTypes.MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"mantle_golem"));
            event.getRegistry().register(ModEntityTypes.DIAMOND_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"diamond_mantle_golem"));
            event.getRegistry().register(ModEntityTypes.PERIDOT_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"peridot_mantle_golem"));
            event.getRegistry().register(ModEntityTypes.STEAMER.setRegistryName(ProjectRankine.MODID,"steamer"));
            event.getRegistry().register(ModEntityTypes.DESMOXYTE.setRegistryName(ProjectRankine.MODID,"desmoxyte"));
            event.getRegistry().register(ModEntityTypes.DEMONYTE.setRegistryName(ProjectRankine.MODID,"demonyte"));
            event.getRegistry().register(ModEntityTypes.DRAGONYTE.setRegistryName(ProjectRankine.MODID,"dragonyte"));
            event.getRegistry().register(ModEntityTypes.BEAVER.setRegistryName(ProjectRankine.MODID,"beaver"));
            event.getRegistry().register(ModEntityTypes.SHROUDED_KING.setRegistryName(ProjectRankine.MODID,"shrouded_king"));
            event.getRegistry().register(ModEntityTypes.SOLAR_FLARE.setRegistryName(ProjectRankine.MODID,"solar_flare"));
            event.getRegistry().register(ModEntityTypes.RANKINE_BOAT.setRegistryName(ProjectRankine.MODID,"cedar_boat"));
        }

        @SubscribeEvent
        public static void clientSetupEvent(FMLClientSetupEvent event)
        {
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FLINT_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BRONZE_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.IRON_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.METEORIC_IRON_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.STEEL_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ROSE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WHITE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GREEN_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BLUE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PURPLE_GOLD_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.AMALGAM_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DIAMOND_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.NETHERITE_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.NICKEL_SUPERALLOY_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.REACTIVE_ITEM, ReactiveItemRenderer::new);
        //    RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JAR_BLUE_FOXFIRE, ReactiveItemRenderer::new);
       //     RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JAR_GREEN_FOXFIRE, ReactiveItemRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MANTLE_GOLEM,MantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PERIDOT_MANTLE_GOLEM,PeridotMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.STEAMER,SteamerRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DESMOXYTE, DesmoxyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DEMONYTE, DemonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRAGONYTE, DragonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BEAVER, BeaverRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHROUDED_KING, ShroudedKingRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SOLAR_FLARE, SolarFlareRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RANKINE_BOAT,RankineBoatRenderer.instance);
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
                return new CoalForgeContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"coal_forge"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new FineryForgeContainer(windowId, ProjectRankine.proxy.getClientWorld(), pos, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"finery_forge"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                return new ElementIndexerContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer());
            }).setRegistryName(ProjectRankine.MODID,"element_indexer"));
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
                    .bucket(() -> ModItems.LIQUID_MERCURY_BUCKET).block(() -> ModBlocks.LIQUID_MERCURY_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_mercury"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_MERCURY, () -> ModFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xFFFFFFFF))
                    .bucket(() -> ModItems.LIQUID_MERCURY_BUCKET).block(() -> ModBlocks.LIQUID_MERCURY_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_mercury_flowing"));
        }

        @SubscribeEvent
        public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
            event.getRegistry().register(new PunctureEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"puncture"));
            event.getRegistry().register(new SwingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"swing"));
            event.getRegistry().register(new DazeEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"daze"));
            event.getRegistry().register(new BlastEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"blast"));
            event.getRegistry().register(new AtomizeEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"atomize"));
            event.getRegistry().register(new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"lightning_aspect"));
        }
    }


}

