package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.events.handlers.common.CreativeTabsHandler;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.packets.RankinePacketHandler;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.util.colors.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod(ProjectRankine.MODID)
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

        RankineSoundEvents.SOUNDS.register(Bus);
        RankineFluidTypes.FLUID_TYPES.register(Bus);
        RankineFluids.FLUIDS.register(Bus);
        RankineBlocks.BLOCKS.register(Bus);
        RankineMobEffects.MOB_EFFECTS.register(Bus);
        RankineItems.ITEMS.register(Bus);
        RankineBlocks.ITEMS.register(Bus);
        RankineEntityTypes.ENTITY_TYPES.register(Bus);
        RankinePotions.POTIONS.register(Bus);
        RankineEnchantments.ENCHANTMENTS.register(Bus);
        RankineBlockEntityTypes.BLOCK_ENTITY_TYPES.register(Bus);
        RankineMenus.MENUS.register(Bus);
        RankineRecipeTypes.RECIPE_TYPES.register(Bus);
        RankineRecipeSerializers.RECIPE_SERIALIZERS.register(Bus);
        RankineVillagerProfessions.VILLAGER_PROFESSIONS.register(Bus);
        RankinePOIs.POI_TYPES.register(Bus);

        //RankineFeatures.FEATURES.register(Bus);
        //RankineConfiguredFeatures.CONFIGURED_FEATURES.register(Bus);
        //RankinePlacedFeatures.PLACED_FEATURES.register(Bus);

        Bus.addListener(this::LoadComplete);

        WorldgenUtils.initOreTextures();

    }

    private void CommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");

        //WorldgenUtils.initConfigs();
        RankinePacketHandler.register();
        proxy.init();


        event.enqueueWork(() -> {
            RankineRecipes.registerPredicates();
            RankineRecipes.registerPotionRecipes();
            RankineRecipes.registerDispenserBehaviors();
        });
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            WoodType.register(Wood.getWoodType());
        }

        LOGGER.info("Rankine: \"CommonSetup\" Event Complete!");
    }

    private void ClientSetup(FMLClientSetupEvent event) {
        LOGGER.debug("Rankine: \"ClientSetup Event\" Starting...");

        event.enqueueWork(ClientProxy::registerItemProperties);
        event.enqueueWork(() -> {
            RankineBlockEntityTypes.registerBlockEntityRenders();

            for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
                Sheets.addWoodType(Wood.getWoodType());
            }
        });
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
        public static void registerTabs(CreativeModeTabEvent.Register event) {
            CreativeTabsHandler.registerTabs(event);
        }

        @SubscribeEvent
        public static void creativeTabs(CreativeModeTabEvent.BuildContents event) {
            CreativeTabsHandler.addItemsToTabs(event);
        }


        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void onItemColorRegistry(final RegisterColorHandlersEvent.Item event) {
            for (Block b : RankineLists.GRASS_BLOCKS) {
                event.getItemColors().register(new GrassItemBaseColor(), b.asItem());
            }
            for (Block b : Stream.of(RankineLists.GLAZED_PORCELAIN_BLOCKS,RankineLists.ALLOY_POLES,RankineLists.ALLOY_BARS,RankineLists.ALLOY_LADDERS,RankineLists.SHEETMETALS,RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
                event.getItemColors().register(new NonAlloyItemColor(), b);
            }
            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SHORT_GRASS.get());

            event.getItemColors().register(new GrassItemBaseColor(), RankineItems.SOD_BLOCK.get());

            event.getItemColors().register(new LeavesItemBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());

            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_BLOCK::get);
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
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_KNIFE::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_CROWBAR::get);
            event.getItemColors().register(new AlloyItemColor(), RankineItems.ALLOY_BLUNDERBUSS::get);
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
        public static void onBlockColorRegistry(final RegisterColorHandlersEvent.Block event) {
            event.getBlockColors().register(new CrucibleColor(), RankineBlocks.CRUCIBLE_BLOCK.get());
            for (Block b : RankineLists.GRASS_BLOCKS) {
                event.getBlockColors().register(new GrassBlockBaseColor(), b);
            }
            event.getBlockColors().register(new AlloyBlockColor(), RankineBlocks.ALLOY_BLOCK.get());
            event.getBlockColors().register(new OrnamentColor(), RankineBlocks.ORNAMENT.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SHORT_GRASS.get());
            event.getBlockColors().register(new GrassBlockBaseColor(), RankineBlocks.SOD_BLOCK.get());
            event.getBlockColors().register(new LeavesBlockBaseColor(), RankineBlocks.BIRCH_LEAF_LITTER.get(), RankineBlocks.SPRUCE_LEAF_LITTER.get(), RankineBlocks.ACACIA_LEAF_LITTER.get(), RankineBlocks.JUNGLE_LEAF_LITTER.get(), RankineBlocks.DARK_OAK_LEAF_LITTER.get(), RankineBlocks.OAK_LEAF_LITTER.get());

            for (Block b : Stream.of(RankineLists.GLAZED_PORCELAIN_BLOCKS,RankineLists.ALLOY_POLES,RankineLists.ALLOY_BARS,RankineLists.ALLOY_LADDERS,RankineLists.SHEETMETALS,RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
                event.getBlockColors().register(new NonAlloyBlockColor(), b);
            }
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(RankineEntityTypes.ALLOY_SPEAR.get(), SpearEntityRenderer.instance);
            event.registerEntityRenderer(RankineEntityTypes.REACTIVE_ITEM.get(), ReactiveItemRenderer::new);
            event.registerEntityRenderer(RankineEntityTypes.RANKINE_BOAT.get(), (p_174094_) -> new RankineBoatRenderer(p_174094_, false));
            event.registerEntityRenderer(RankineEntityTypes.ROPE_COIL_ARROW.get(), RopeCoilArrowRenderer.instance);
            event.registerEntityRenderer(RankineEntityTypes.ALLOY_ARROW.get(),AlloyArrowRenderer.instance);
            event.registerEntityRenderer(RankineEntityTypes.CANNONBALL.get(), CannonballRenderer::new);
            event.registerEntityRenderer(RankineEntityTypes.ENDERBALL.get(), EnderballRenderer::new);
            event.registerEntityRenderer(RankineEntityTypes.CARCASS.get(), CarcassRenderer::new);
            event.registerEntityRenderer(RankineEntityTypes.BALLOON.get(), BalloonRenderer::new);
        }
/*
        @SubscribeEvent
        public static void registerItemRemappings(final MissingMappingsEvent event) {
            Map<ResourceLocation, Item> itemRemappings = RankineRemappings.getItemRemappings();
            List<MissingMappingsEvent.Mapping<Item>> mappings = event.getMappings(Registries.ITEM,ProjectRankine.MODID);
            for (MissingMappingsEvent.Mapping<Item> map : mappings) {
                if (itemRemappings.containsKey(map.getKey())) {
                    map.remap(itemRemappings.get(map.getKey()));
                }
            }

            Map<ResourceLocation, Block> blockRemappings = RankineRemappings.getBlockRemappings();
            for (MissingMappingsEvent.Mapping<Item> map : mappings) {
                if (blockRemappings.containsKey(map.getKey())) {
                    map.remap(blockRemappings.get(map.getKey()).asItem());
                }
            }
        }

        @SubscribeEvent
        public static void registerBlockRemappings(final MissingMappingsEvent event) {
            Map<ResourceLocation, Block> blockRemappings = RankineRemappings.getBlockRemappings();
            List<MissingMappingsEvent.Mapping<Block>> mappings = event.getMappings(Registries.BLOCK,ProjectRankine.MODID);
            for (MissingMappingsEvent.Mapping<Block> map : mappings) {
                if (blockRemappings.containsKey(map.getKey())) {
                    map.remap(blockRemappings.get(map.getKey()));
                }
            }
        }*/

    }

}

