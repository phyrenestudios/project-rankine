package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.potion.MercuryPoisonEffect;
import com.cannolicatfish.rankine.potion.ModEffects;
import com.cannolicatfish.rankine.potion.ModPotions;
import com.cannolicatfish.rankine.world.gen.StructureGen;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeContainer;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeTile;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.enchantment.AtomizeEnchantment;
import com.cannolicatfish.rankine.enchantment.BlastEnchantment;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeContainer;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.dimension.MantleBiome;
import com.cannolicatfish.rankine.dimension.MantleModDimension;
import com.cannolicatfish.rankine.enchantment.LightningAspectEnchantment;
import com.cannolicatfish.rankine.world.biome.*;
import com.cannolicatfish.rankine.dimension.ModDimensions;
import com.cannolicatfish.rankine.entities.ModEntityTypes;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.world.gen.feature.RankineFeatures;
import com.cannolicatfish.rankine.world.gen.OreGen;
import com.cannolicatfish.rankine.world.gen.DecorationGen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.*;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
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

        Bus.addListener(this::setup);
        ModBlocks.REGISTRY.register(Bus);
        ModItems.REGISTRY.register(Bus);
        RankineBiomes.REGISTRY.register(Bus);


        Bus.addListener(this::LoadComplete);
        Bus.addListener(RankineBiomes::registerTypes);
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");
        setup.init();
        proxy.init();
        DecorationGen.setupDecoration();
        OreGen.setupOreGeneration();
        LOGGER.info("Rankine: \"CommonSetup\" Event Complete!");
    }

    private void LoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.debug("Rankine: \"Load Complete Event\" Starting...");
        StructureGen.setupStructureGen();
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
            //event.getRegistry().register(ModEntityTypes.RANKINE_BOAT);
            event.getRegistry().register(ModEntityTypes.FLINT_SPEAR);
            event.getRegistry().register(ModEntityTypes.BRONZE_SPEAR);
            event.getRegistry().register(ModEntityTypes.IRON_SPEAR);
            event.getRegistry().register(ModEntityTypes.STEEL_SPEAR);
            event.getRegistry().register(ModEntityTypes.MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"mantle_golem"));
            event.getRegistry().register(ModEntityTypes.DIAMOND_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"diamond_mantle_golem"));
            event.getRegistry().register(ModEntityTypes.PERIDOT_MANTLE_GOLEM.setRegistryName(ProjectRankine.MODID,"peridot_mantle_golem"));
            event.getRegistry().register(ModEntityTypes.STEAMER.setRegistryName(ProjectRankine.MODID,"steamer"));
            event.getRegistry().register(ModEntityTypes.DESMOXYTE.setRegistryName(ProjectRankine.MODID,"desmoxyte"));
            event.getRegistry().register(ModEntityTypes.DEMONYTE.setRegistryName(ProjectRankine.MODID,"demonyte"));
            event.getRegistry().register(ModEntityTypes.DRAGONYTE.setRegistryName(ProjectRankine.MODID,"dragonyte"));
            event.getRegistry().register(ModEntityTypes.BEAVER.setRegistryName(ProjectRankine.MODID,"beaver"));
            event.getRegistry().register(ModEntityTypes.SHROUDED_KING.setRegistryName(ProjectRankine.MODID,"shrouded_king"));
            event.getRegistry().register(ModEntityTypes.RANKINE_BOAT.setRegistryName(ProjectRankine.MODID,"cedar_boat"));
        }

        @SubscribeEvent
        public static void clientSetupEvent(FMLClientSetupEvent event)
        {
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FLINT_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BRONZE_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.IRON_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.STEEL_SPEAR, SpearRenderFactory.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MANTLE_GOLEM,MantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DIAMOND_MANTLE_GOLEM, DiamondMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PERIDOT_MANTLE_GOLEM,PeridotMantleGolemRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.STEAMER,SteamerRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DESMOXYTE, DesmoxyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DEMONYTE, DemonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRAGONYTE, DragonyteRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BEAVER, BeaverRenderer.instance);
            RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHROUDED_KING, ShroudedKingRenderer.instance);
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
                    .bucket(() -> ModItems.LIQUID_MERCURY_BRASS_BUCKET).block(() -> ModBlocks.LIQUID_MERCURY_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_mercury"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_MERCURY, () -> ModFluids.LIQUID_MERCURY_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xFFFFFFFF))
                    .bucket(() -> ModItems.LIQUID_MERCURY_BRASS_BUCKET).block(() -> ModBlocks.LIQUID_MERCURY_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_mercury_flowing"));
        }

        @SubscribeEvent
        public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
            event.getRegistry().register(new BlastEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"blast"));
            event.getRegistry().register(new AtomizeEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"atomize"));
            event.getRegistry().register(new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"lightning_aspect"));
        }

        @SubscribeEvent
        public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
            event.getRegistry().register(new MantleBiome().setRegistryName(RankineBiomes.BIOME_ID));
        }

        @SubscribeEvent
        public static void registerModDimensions(final RegistryEvent.Register<ModDimension> event) {
            event.getRegistry().register(new MantleModDimension().setRegistryName(ModDimensions.DIMENSION_ID));
        }

        @SubscribeEvent
        public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event)
        {
            //registers the structures/features.
            //If you don't do this, you'll crash.
            RankineFeatures.registerFeatures(event);
  //          LOGGER.log(Level.INFO, "features/structures registered.");
        }
    }


}

