package com.cannolicatfish.rankine;
import com.cannolicatfish.rankine.world.gen.StructureGen;
import com.cannolicatfish.rankine.world.trees.*;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForge;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeContainer;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeTile;
import com.cannolicatfish.rankine.blocks.trees.*;
import com.cannolicatfish.rankine.client.renders.*;
import com.cannolicatfish.rankine.enchantment.AtomizeEnchantment;
import com.cannolicatfish.rankine.enchantment.BlastEnchantment;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnace;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPit;
import com.cannolicatfish.rankine.blocks.crucible.Crucible;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForge;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeContainer;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusher;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.dimension.MantleBiome;
import com.cannolicatfish.rankine.dimension.MantleModDimension;
import com.cannolicatfish.rankine.enchantment.LightningAspectEnchantment;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxe;
import com.cannolicatfish.rankine.items.alloys.AlloySword;
import com.cannolicatfish.rankine.items.alloys.OldAlloyItem;
import com.cannolicatfish.rankine.util.BronzeAlloyUtils;
import com.cannolicatfish.rankine.world.biome.*;
import com.cannolicatfish.rankine.dimension.ModDimensions;
import com.cannolicatfish.rankine.entities.ModEntityTypes;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.items.*;
import com.cannolicatfish.rankine.items.tools.*;
import com.cannolicatfish.rankine.setup.ClientProxy;
import com.cannolicatfish.rankine.setup.IProxy;
import com.cannolicatfish.rankine.setup.ModSetup;
import com.cannolicatfish.rankine.setup.ServerProxy;
import com.cannolicatfish.rankine.world.feature.RankineFeatures;
import com.cannolicatfish.rankine.world.gen.OreGen;
import com.cannolicatfish.rankine.world.gen.TreeGen;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
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
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

@Mod("rankine")
public class ProjectRankine {
    public static final String MODID = "rankine";
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static ModSetup setup = new ModSetup();
    private static final Logger LOGGER = LogManager.getLogger();

    public ProjectRankine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("rankine-common.toml"));

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::setup);
        RankineBiomes.BIOMES.register(modEventBus);


    }


    private void setup(final FMLCommonSetupEvent event)
    {
        setup.init();
        proxy.init();
        RankineBiomes.registerBiomesToDictionary();
        TreeGen.setupTreeGeneration();
        OreGen.setupOreGeneration();
        StructureGen.setupStructureGen();
    }


    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {


        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {

            //Earthy blocks
            event.getRegistry().register(new SandyDirtBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)).setRegistryName(ProjectRankine.MODID,"sandy_dirt"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)).setRegistryName(ProjectRankine.MODID,"muddy_dirt"));
            event.getRegistry().register(new MuddyGrassBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)).setRegistryName(ProjectRankine.MODID,"muddy_grass"));
            event.getRegistry().register(new PermafrostBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(1.5F).sound(SoundType.GROUND)).setRegistryName(ProjectRankine.MODID,"permafrost"));
            event.getRegistry().register(new VegetatedPermafrostBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(1.5F).sound(SoundType.GROUND)).setRegistryName(ProjectRankine.MODID,"vegetated_permafrost"));

            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"granite"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"andesite"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"diorite"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"limestone"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"basalt"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"rhyolite"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"marble"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"gneiss"));
            event.getRegistry().register(new RankineStone(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"shale"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"peridotite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"ringwoodite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"wadsleyite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"bridgmanite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"komatiite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"kimberlite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"ferropericlase"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"perovskite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"ironstone"));

            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_granite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_andesite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_diorite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_limestone"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_shale"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"smooth_basalt"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"smooth_marble"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(3)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"smooth_ironstone"));

            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"granite_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"andesite_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"diorite_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"limestone_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"basalt_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"marble_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(2)).setRegistryName(ProjectRankine.MODID,"gneiss_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"shale_bricks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"ironstone_bricks"));

            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_granite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_andesite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_diorite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_limestone_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_shale_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_basalt_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_marble_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite_slab"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(0)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite_slab"));

            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_granite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_andesite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_diorite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_limestone_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_shale_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_basalt_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_marble_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite_stairs"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite_stairs"));

            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID,"refractory_bricks"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"refractory_bricks_slab"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0), Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"refractory_bricks_stairs"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks_slab"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0), Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks_stairs"));
            event.getRegistry().register(new Crucible(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F).lightValue(7)).setRegistryName(ProjectRankine.MODID,"crucible"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"clay_bricks"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"clay_bricks_slab"));
            event.getRegistry().register(new RankineStairs(Block.getStateById(0), Block.Properties.create(Material.CLAY)).setRegistryName(ProjectRankine.MODID,"clay_bricks_stairs"));



            //woody blocks
            event.getRegistry().register(new LogBlock(MaterialColor.AIR,Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "cedar_log"));
            event.getRegistry().register(new LogBlock(MaterialColor.AIR,Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "pinyon_pine_log"));
            event.getRegistry().register(new LogBlock(MaterialColor.AIR,Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "juniper_log"));
            event.getRegistry().register(new LogBlock(MaterialColor.AIR,Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "coconut_palm_log"));
            event.getRegistry().register(new LogBlock(MaterialColor.AIR,Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "balsam_fir_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID,"cedar_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "pinyon_pine_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "juniper_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "coconut_palm_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID,"balsam_fir_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_cedar_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_pinyon_pine_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_juniper_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_coconut_palm_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_balsam_fir_log"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_cedar_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_pinyon_pine_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_juniper_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_coconut_palm_wood"));
            event.getRegistry().register(new RotatedPillarBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID, "stripped_balsam_fir_wood"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID, "cedar_planks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID, "pinyon_pine_planks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID, "juniper_planks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID, "coconut_palm_planks"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID, "balsam_fir_planks"));
            event.getRegistry().register(new LeavesBlock(Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()).setRegistryName(ProjectRankine.MODID, "cedar_leaves"));
            event.getRegistry().register(new LeavesBlock(Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()).setRegistryName(ProjectRankine.MODID, "coconut_palm_leaves"));
            event.getRegistry().register(new LeavesBlock(Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()).setRegistryName(ProjectRankine.MODID, "pinyon_pine_leaves"));
            event.getRegistry().register(new LeavesBlock(Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()).setRegistryName(ProjectRankine.MODID, "juniper_leaves"));
            event.getRegistry().register(new LeavesBlock(Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()).setRegistryName(ProjectRankine.MODID, "balsam_fir_leaves"));

            Block.Properties sapling = Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.PLANT);
            event.getRegistry().register(new RankineSapling(new CedarTree(ModBlocks.CEDAR_LOG, ModBlocks.CEDAR_LEAVES, ModBlocks.CEDAR_SAPLING),sapling, 3).setRegistryName(ProjectRankine.MODID,"cedar_sapling"));
            event.getRegistry().register(new RankineSapling(new CoconutPalmTree(ModBlocks.COCONUT_PALM_LOG, ModBlocks.COCONUT_PALM_LEAVES, ModBlocks.COCONUT_PALM_SAPLING),sapling, 2).setRegistryName(ProjectRankine.MODID,"coconut_palm_sapling"));
            event.getRegistry().register(new RankineSapling(new PinyonPineTree(ModBlocks.PINYON_PINE_LOG, ModBlocks.PINYON_PINE_LEAVES, ModBlocks.PINYON_PINE_SAPLING),sapling, 2).setRegistryName(ProjectRankine.MODID,"pinyon_pine_sapling"));
            event.getRegistry().register(new RankineSapling(new JuniperTree(ModBlocks.JUNIPER_LOG, ModBlocks.JUNIPER_LEAVES, ModBlocks.JUNIPER_SAPLING),sapling, 2).setRegistryName(ProjectRankine.MODID,"juniper_sapling"));
            event.getRegistry().register(new RankineSapling(new BalsamFirTree(ModBlocks.BALSAM_FIR_LOG, ModBlocks.BALSAM_FIR_LEAVES, ModBlocks.BALSAM_FIR_SAPLING),sapling, 3).setRegistryName(ProjectRankine.MODID,"balsam_fir_sapling"));
            event.getRegistry().register(new FlowerPotBlock(null, () -> ModBlocks.CEDAR_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()).setRegistryName(ProjectRankine.MODID,"potted_cedar_sapling"));
            event.getRegistry().register(new FlowerPotBlock(null, () -> ModBlocks.PINYON_PINE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()).setRegistryName(ProjectRankine.MODID,"potted_pinyon_pine_sapling"));
            event.getRegistry().register(new FlowerPotBlock(null, () -> ModBlocks.JUNIPER_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()).setRegistryName(ProjectRankine.MODID,"potted_juniper_sapling"));
            event.getRegistry().register(new FlowerPotBlock(null, () -> ModBlocks.COCONUT_PALM_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()).setRegistryName(ProjectRankine.MODID,"potted_coconut_palm_sapling"));
            event.getRegistry().register(new FlowerPotBlock(null, () -> ModBlocks.BALSAM_FIR_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()).setRegistryName(ProjectRankine.MODID,"potted_balsam_fir_sapling"));


            event.getRegistry().register(new RankineStairs(Block.getStateById(0),Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"cedar_stairs"));
            event.getRegistry().register(new RankineSlab(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(ProjectRankine.MODID,"cedar_slab"));
            event.getRegistry().register(new RankineWoodFence().setRegistryName(ProjectRankine.MODID,"cedar_fence"));
            event.getRegistry().register(new RankineWoodFenceGate().setRegistryName(ProjectRankine.MODID,"cedar_fence_gate"));
            event.getRegistry().register(new RankineWoodButton().setRegistryName(ProjectRankine.MODID,"cedar_button"));
            event.getRegistry().register(new RankineWoodPressurePlate().setRegistryName(ProjectRankine.MODID,"cedar_pressure_plate"));
            event.getRegistry().register(new RankineWoodDoor().setRegistryName(ProjectRankine.MODID,"cedar_door"));
            event.getRegistry().register(new RankineWoodTrapDoor().setRegistryName(ProjectRankine.MODID,"cedar_trapdoor"));
            event.getRegistry().register(new StickBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.2F)).setRegistryName(ProjectRankine.MODID, "stick_block"));

            /*
            event.getRegistry().register(new PipeBlock(Block.Properties.create(Material.IRON)).setRegistryName(ProjectRankine.MODID,"brass_pipe"));
            event.getRegistry().register(new PipeBlock(Block.Properties.create(Material.ROCK)).setRegistryName(ProjectRankine.MODID,"concrete_pipe"));
            event.getRegistry().register(new PipeBlock(Block.Properties.create(Material.ROCK)).setRegistryName(ProjectRankine.MODID,"concrete_pipe_junction"));
            event.getRegistry().register(new PipeBlock(Block.Properties.create(Material.IRON)).setRegistryName(ProjectRankine.MODID,"cast_iron_pipe"));
            event.getRegistry().register(new PipeBlock(Block.Properties.create(Material.IRON)).setRegistryName(ProjectRankine.MODID,"cast_iron_pipe_junction"));
            event.getRegistry().register(new SupportBlock(Block.Properties.create(Material.IRON)).setRegistryName(ProjectRankine.MODID,"cast_iron_beam"));
             */


            event.getRegistry().register(new NoduleBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"limestone_nodule"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"vanadinite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE), ModItems.ALUMINUM_NUGGET).setRegistryName(ProjectRankine.MODID,"bauxite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE), ModItems.COPPER_NUGGET).setRegistryName(ProjectRankine.MODID,"malachite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE), ModItems.TIN_NUGGET).setRegistryName(ProjectRankine.MODID,"cassiterite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE), ModItems.ZINC_NUGGET).setRegistryName(ProjectRankine.MODID,"sphalerite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE), Items.REDSTONE).setRegistryName(ProjectRankine.MODID,"cinnabar_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.MAGNESIUM_NUGGET).setRegistryName(ProjectRankine.MODID,"magnesite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), Items.IRON_NUGGET).setRegistryName(ProjectRankine.MODID,"magnetite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.TITANIUM_NUGGET).setRegistryName(ProjectRankine.MODID,"ilmenite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.LEAD_NUGGET).setRegistryName(ProjectRankine.MODID,"galena_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.SILVER_NUGGET).setRegistryName(ProjectRankine.MODID,"acanthite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.MOLYBDENUM_NUGGET).setRegistryName(ProjectRankine.MODID,"molybdenite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.MANGANESE_NUGGET).setRegistryName(ProjectRankine.MODID,"pyrolusite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.CHROMIUM_NUGGET).setRegistryName(ProjectRankine.MODID,"chromite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.NIOBIUM_NUGGET).setRegistryName(ProjectRankine.MODID,"columbite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.TANTALUM_NUGGET).setRegistryName(ProjectRankine.MODID,"tantalite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE), ModItems.TITANIUM_NUGGET).setRegistryName(ProjectRankine.MODID,"wolframite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.BISMUTH_NUGGET).setRegistryName(ProjectRankine.MODID,"bismite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE), ModItems.NICKEL_NUGGET).setRegistryName(ProjectRankine.MODID,"pentlandite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE), ModItems.COPPER_NUGGET).setRegistryName(ProjectRankine.MODID,"native_copper_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE), ModItems.TIN_NUGGET).setRegistryName(ProjectRankine.MODID,"native_tin_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"plumbago_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"moissanite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"sperrylite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(0).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"lignite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"subbituminous_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"bituminous_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"anthracite_ore"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"meteoric_iron_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"native_gold_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"redstone_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"lazurite_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"diamond_ore"));
            event.getRegistry().register(new RankineOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"emerald_ore"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"opal_ore"));



            //metal blocks
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"copper_sheetmetal"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"tin_sheetmetal"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"bronze_sheetmetal"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"wrought_iron_sheetmetal"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2).harvestLevel(3).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"steel_sheetmetal"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"rose_gold_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"white_gold_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"green_gold_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"blue_gold_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"purple_gold_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"chromium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"titanium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"silver_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"platinum_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"osmium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"iridium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"zinc_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"magnesium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"manganese_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"molybdenum_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"nickel_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"cobalt_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"tungsten_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"invar_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"bronze_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"aluminum_bronze_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"steel_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"stainless_steel_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"pig_iron_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"alnico_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"magnalium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"duralumin_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"brass_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"cupronickel_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"nichrome_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"nickel_silver_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"nitinol_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"amalgam_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"rose_metal_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"wrought_iron_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"cast_iron_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"copper_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"tin_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"niobium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"aluminum_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"vanadium_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"lead_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"bismuth_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"tantalum_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"graphite_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"opal_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"garnet_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"feldspar_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"dolomite_block"));
            event.getRegistry().register(new RankineTransparent(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1).notSolid()).setRegistryName(ProjectRankine.MODID,"calcite_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"olivine_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"peridot_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"pyroxene_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"inconel_block"));
            event.getRegistry().register(new EntityModifierBlock(Block.Properties.create(Material.SAND).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"gravel_concrete"));
            event.getRegistry().register(new RankineTransparent(Block.Properties.create(Material.SAND).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1).notSolid()).setRegistryName(ProjectRankine.MODID,"salt_block"));
            event.getRegistry().register(new RankineTransparent(Block.Properties.create(Material.EARTH).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1).notSolid()).setRegistryName(ProjectRankine.MODID,"calcium_silicate_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"silicon_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"sulfur_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"silicon_carbide_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"coke_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"lignite_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"bituminous_coal_block"));
            event.getRegistry().register(new Block(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"anthracite_coal_block"));



            event.getRegistry().register(new CrystalBlock(Block.Properties.create(Material.IRON).sound(SoundType.GLASS).harvestLevel(1).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"niter"));
            event.getRegistry().register(new RopeBlock(Block.Properties.create(Material.CARPET).doesNotBlockMovement()).setRegistryName(ProjectRankine.MODID,"rope"));
            event.getRegistry().register(new MetalLadder(Block.Properties.create(Material.CARPET)).setRegistryName(ProjectRankine.MODID,"aluminum_ladder"));
            event.getRegistry().register(new CoalForge());
            event.getRegistry().register(new FineryForge());
            event.getRegistry().register(new PistonCrusher());
            event.getRegistry().register(new AlloyFurnace());

            event.getRegistry().register(new FlowingFluidBlock(()-> ModFluids.LIQUID_PIG_IRON,Block.Properties.create(Material.LAVA).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()).setRegistryName(ProjectRankine.MODID,"liquid_pig_iron_block"));
            event.getRegistry().register(new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),0).setRegistryName(ProjectRankine.MODID,"elderberry_bush"));
            event.getRegistry().register(new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),1).setRegistryName(ProjectRankine.MODID,"snowberry_bush"));
            event.getRegistry().register(new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),2).setRegistryName(ProjectRankine.MODID,"banana_yucca_bush"));
            event.getRegistry().register(new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),3).setRegistryName(ProjectRankine.MODID,"pineapple_bush"));
            event.getRegistry().register(new SphagnumMossBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.NETHER_WART)).setRegistryName(ProjectRankine.MODID,"sphagnum_moss"));
            event.getRegistry().register(new SwampGrassBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)).setRegistryName(ProjectRankine.MODID,"swamp_grass"));
            event.getRegistry().register(new DuckweedBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)).setRegistryName(ProjectRankine.MODID,"duckweed"));

            event.getRegistry().register(new MantleTeleporterBlock(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)).setRegistryName(ProjectRankine.MODID,"heart_of_the_mantle"));
            event.getRegistry().register(new BlastingPowderBlock(Block.Properties.create(Material.SAND).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL).hardnessAndResistance(1)).setRegistryName(ProjectRankine.MODID,"blasting_powder"));
            event.getRegistry().register(new BeehiveOvenPit(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F)).setRegistryName(ProjectRankine.MODID,"beehive_oven_pit"));
            //event.getRegistry().register(new AlnicoChest(Block.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE)).setRegistryName(ProjectRankine.MODID,"alnico_chest"));
        }




        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);

//RANKINE BLOCKS CREATIVE TAB -----------------------------------------------------------------------------

            //earthy blocks
            event.getRegistry().register(new BlockItem(ModBlocks.SANDY_DIRT,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"sandy_dirt"));
            event.getRegistry().register(new BlockItem(ModBlocks.MUDDY_DIRT,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"muddy_dirt"));
            event.getRegistry().register(new BlockItem(ModBlocks.MUDDY_GRASS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"muddy_grass"));
            event.getRegistry().register(new BlockItem(ModBlocks.PERMAFROST,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"permafrost"));
            event.getRegistry().register(new BlockItem(ModBlocks.VEGETATED_PERMAFROST,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"vegetated_permafrost"));

            //stone blocks
            event.getRegistry().register(new BlockItem(ModBlocks.GRANITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"granite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GRANITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_granite"));
            event.getRegistry().register(new BlockItem(ModBlocks.GRANITE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"granite_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.DIORITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"diorite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_DIORITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_diorite"));
            event.getRegistry().register(new BlockItem(ModBlocks.DIORITE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"diorite_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.ANDESITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"andesite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_ANDESITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_andesite"));
            event.getRegistry().register(new BlockItem(ModBlocks.ANDESITE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"andesite_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.LIMESTONE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"limestone"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_LIMESTONE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_limestone"));
            event.getRegistry().register(new BlockItem(ModBlocks.LIMESTONE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"limestone_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.BASALT,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"basalt"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BASALT,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_basalt"));
            event.getRegistry().register(new BlockItem(ModBlocks.BASALT_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"basalt_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.RHYOLITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"rhyolite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RHYOLITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite"));
            //rhyolite bricks
            event.getRegistry().register(new BlockItem(ModBlocks.MARBLE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"marble"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_MARBLE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_marble"));
            event.getRegistry().register(new BlockItem(ModBlocks.MARBLE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"marble_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.GNEISS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"gneiss"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GNEISS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss"));
            event.getRegistry().register(new BlockItem(ModBlocks.GNEISS_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"gneiss_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.SHALE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"shale"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_SHALE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_shale"));
            event.getRegistry().register(new BlockItem(ModBlocks.SHALE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"shale_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.IRONSTONE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"ironstone"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_IRONSTONE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ironstone"));
            event.getRegistry().register(new BlockItem(ModBlocks.IRONSTONE_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"ironstone_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.PERIDOTITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"peridotite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PERIDOTITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite"));
            event.getRegistry().register(new BlockItem(ModBlocks.RINGWOODITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"ringwoodite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RINGWOODITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite"));
            event.getRegistry().register(new BlockItem(ModBlocks.WADSLEYITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"wadsleyite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_WADSLEYITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite"));
            event.getRegistry().register(new BlockItem(ModBlocks.BRIDGMANITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"bridgmanite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BRIDGMANITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite"));
            event.getRegistry().register(new BlockItem(ModBlocks.KOMATIITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"komatiite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KOMATIITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite"));
            event.getRegistry().register(new BlockItem(ModBlocks.KIMBERLITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"kimberlite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KIMBERLITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite"));
            event.getRegistry().register(new BlockItem(ModBlocks.FERROPERICLASE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"ferropericlase"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_FERROPERICLASE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase"));
            event.getRegistry().register(new BlockItem(ModBlocks.PEROVSKITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"perovskite"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PEROVSKITE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite"));


            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GRANITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_granite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GRANITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_granite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_DIORITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_diorite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_DIORITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_diorite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_ANDESITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_andesite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_ANDESITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_andesite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_LIMESTONE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_limestone_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_LIMESTONE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_limestone_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BASALT_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_basalt_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BASALT_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_basalt_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RHYOLITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RHYOLITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_rhyolite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_MARBLE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_marble_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_MARBLE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_marble_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GNEISS_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_GNEISS_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_gneiss_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_SHALE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_shale_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_SHALE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_shale_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PERIDOTITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PERIDOTITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_peridotite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RINGWOODITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_RINGWOODITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ringwoodite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_WADSLEYITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_WADSLEYITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_wadsleyite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BRIDGMANITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_BRIDGMANITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_bridgmanite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KOMATIITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KOMATIITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_komatiite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KIMBERLITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_KIMBERLITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_kimberlite_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_FERROPERICLASE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_FERROPERICLASE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_ferropericlase_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PEROVSKITE_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.SMOOTH_PEROVSKITE_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"smooth_perovskite_stairs"));

            event.getRegistry().register(new BlockItem(ModBlocks.CLAY_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"clay_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.CLAY_BRICKS_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"clay_bricks_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.CLAY_BRICKS_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"clay_bricks_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.REFRACTORY_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"refractory_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.REFRACTORY_BRICKS_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"refractory_bricks_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.REFRACTORY_BRICKS_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"refractory_bricks_stairs"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNESIUM_REFRACTORY_BRICKS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNESIUM_REFRACTORY_BRICKS_SLAB,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks_slab"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNESIUM_REFRACTORY_BRICKS_STAIRS,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_bricks_stairs"));


            //wood blocks
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"cedar_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.PINYON_PINE_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"pinyon_pine_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.JUNIPER_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"juniper_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.COCONUT_PALM_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"coconut_palm_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.BALSAM_FIR_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"balsam_fir_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_CEDAR_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_cedar_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_PINYON_PINE_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_pinyon_pine_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_JUNIPER_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_juniper_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_COCONUT_PALM_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_coconut_palm_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_BALSAM_FIR_LOG, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_balsam_fir_log"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"cedar_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.PINYON_PINE_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"pinyon_pine_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.JUNIPER_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"juniper_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.COCONUT_PALM_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"coconut_palm_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.BALSAM_FIR_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"balsam_fir_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_CEDAR_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_cedar_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_PINYON_PINE_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_pinyon_pine_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_JUNIPER_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_juniper_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_COCONUT_PALM_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_coconut_palm_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STRIPPED_BALSAM_FIR_WOOD, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"stripped_balsam_fir_wood"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_PLANKS, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"cedar_planks"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.PINYON_PINE_PLANKS, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"pinyon_pine_planks"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.JUNIPER_PLANKS, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"juniper_planks"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.COCONUT_PALM_PLANKS, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"coconut_palm_planks"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.BALSAM_FIR_PLANKS, new Item.Properties().group(setup.itemGroup),300).setRegistryName(ProjectRankine.MODID,"balsam_fir_planks"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_DOOR, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_door"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_TRAPDOOR, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_trapdoor"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_STAIRS, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_stairs"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_SLAB, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_slab"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_FENCE, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_fence"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_FENCE_GATE, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_fence_gate"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_BUTTON, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_button"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_PRESSURE_PLATE, new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_pressure_plate"));
            //event.getRegistry().register(new RankineBoatItem(RankineBoat.Type.COCONUT, (new Item.Properties()).maxStackSize(1).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"coconut_palm_boat"));
            event.getRegistry().register(new BlockItem(ModBlocks.BLASTING_POWDER,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"blasting_powder"));
            event.getRegistry().register(new BlockItem(ModBlocks.HEART_OF_THE_MANTLE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"heart_of_the_mantle"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.STICK_BLOCK, new Item.Properties().group(setup.itemGroup),400).setRegistryName(ProjectRankine.MODID,"stick_block"));


            //PLANTS
            event.getRegistry().register(new BlockItem(ModBlocks.CEDAR_LEAVES, new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"cedar_leaves"));
            event.getRegistry().register(new BlockItem(ModBlocks.PINYON_PINE_LEAVES, new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"pinyon_pine_leaves"));
            event.getRegistry().register(new BlockItem(ModBlocks.JUNIPER_LEAVES, new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"juniper_leaves"));
            event.getRegistry().register(new BlockItem(ModBlocks.COCONUT_PALM_LEAVES, new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"coconut_palm_leaves"));
            event.getRegistry().register(new BlockItem(ModBlocks.BALSAM_FIR_LEAVES, new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"balsam_fir_leaves"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.CEDAR_SAPLING,new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"cedar_sapling"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.PINYON_PINE_SAPLING,new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"pinyon_pine_sapling"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.JUNIPER_SAPLING,new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"juniper_sapling"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.COCONUT_PALM_SAPLING,new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"coconut_palm_sapling"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.BALSAM_FIR_SAPLING,new Item.Properties().group(setup.itemGroup),200).setRegistryName(ProjectRankine.MODID,"balsam_fir_sapling"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.SPHAGNUM_MOSS,new Item.Properties().maxStackSize(64).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"sphagnum_moss"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.SWAMP_GRASS,new Item.Properties().maxStackSize(64).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"swamp_grass"));
            event.getRegistry().register(new DuckweedItem(ModBlocks.DUCKWEED,new Item.Properties().maxStackSize(64).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"duckweed"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.ELDERBERRY_BUSH,new Item.Properties().maxStackSize(64).food(ModFoods.ELDERBERRIES).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"elderberries"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.SNOWBERRY_BUSH,new Item.Properties().maxStackSize(64).food(ModFoods.SNOWBERRIES).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"snowberries"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.PINEAPPLE_BUSH,new Item.Properties().maxStackSize(64).food(ModFoods.PINEAPPLE).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"pineapple"));
            event.getRegistry().register(new BlockNamedItem(ModBlocks.BANANA_YUCCA_BUSH,new Item.Properties().maxStackSize(64).food(ModFoods.YUCCA).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"banana_yucca"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).food(ModFoods.COCONUT).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"coconut"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).food(ModFoods.PINA_COLADA).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"pina_colada"));



            //ITEMS
            event.getRegistry().register(new BlockItem(ModBlocks.ALUMINUM_LADDER,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"aluminum_ladder"));
            event.getRegistry().register(new RopeItem(ModBlocks.ROPE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"rope"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(1).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"overworld_core"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"beaver_pelt"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.MANTLE_GOLEM,0xB2B16A, 0x7A592E,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "mantle_golem_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.DIAMOND_MANTLE_GOLEM, 0x435184,0xA1FBE8,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "diamond_mantle_golem_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.PERIDOT_MANTLE_GOLEM,0xFF423C, 0x6BBE1F,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "peridot_mantle_golem_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.DESMOXYTE,0x2D4F64, 0xAC6D10,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "desmoxyte_spawn_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.DEMONYTE,0x161617, 0x512259,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "demonyte_spawn_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.DRAGONYTE,0xC28215, 0x8F1826,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "dragonyte_spawn_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.STEAMER,0xE7E7E7, 0x6B0000,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "steamer_spawn_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.BEAVER,0x765F4C, 0x463F39,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "beaver_spawn_egg"));
            event.getRegistry().register(new SpawnEggItem(ModEntityTypes.SHROUDED_KING,0x000000, 0xA82C00,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID, "shrouded_king_spawn_egg"));

            /*
            event.getRegistry().register(new BlockItem(ModBlocks.BRASS_PIPE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"brass_pipe"));
            event.getRegistry().register(new BlockItem(ModBlocks.CONCRETE_PIPE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"concrete_pipe"));
            event.getRegistry().register(new BlockItem(ModBlocks.CONCRETE_PIPE_JUNCTION,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"concrete_pipe_junction"));
            event.getRegistry().register(new BlockItem(ModBlocks.CAST_IRON_PIPE,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"cast_iron_pipe"));
            event.getRegistry().register(new BlockItem(ModBlocks.CAST_IRON_PIPE_JUNCTION,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"cast_iron_pipe_junction"));
            event.getRegistry().register(new BlockItem(ModBlocks.CAST_IRON_BEAM,new Item.Properties().group(setup.itemGroup)).setRegistryName(ProjectRankine.MODID,"cast_iron_beam"));
*/

//RANKINE METALS CREATIVE TAB --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

            //ores
            event.getRegistry().register(new BlockItem(ModBlocks.NATIVE_COPPER_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"native_copper_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.NATIVE_TIN_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"native_tin_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.NATIVE_GOLD_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"native_gold_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.METEORIC_IRON_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"meteoric_iron_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.MALACHITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"malachite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.CASSITERITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cassiterite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.BAUXITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bauxite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.SPHALERITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"sphalerite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNETITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnetite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.CINNABAR_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cinnabar_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.PENTLANDITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pentlandite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNESITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.GALENA_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"galena_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.VANADINITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"vanadinite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.BISMITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bismite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.ACANTHITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"acanthite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.PYROLUSITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pyrolusite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.CHROMITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"chromite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.MOLYBDENITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"molybdenite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.WOLFRAMITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"wolframite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.ILMENITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"ilmenite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.COLUMBITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"columbite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.TANTALITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tantalite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.PLUMBAGO_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"plumbago_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.MOISSANITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"moissanite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.SPERRYLITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"sperrylite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.LIGNITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lignite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.SUBBITUMINOUS_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"subbituminous_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.BITUMINOUS_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bituminous_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.ANTHRACITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"anthracite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.REDSTONE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"redstone_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.LAZURITE_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lazurite_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.DIAMOND_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"diamond_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.EMERALD_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"emerald_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.OPAL_ORE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"opal_ore"));
            event.getRegistry().register(new BlockItem(ModBlocks.LIMESTONE_NODULE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"limestone_nodule"));

            //crushed things
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_hydroxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tin_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"alumina"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"zinc_sulfide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"iron_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nickel_sulfide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesia"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lead_sulfide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"vanadinite"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bismuth_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silver_sulfide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"manganese_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"chromium_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"molybdenum_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tungsten_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"titania"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"niobium_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tantalum_oxide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cobaltite"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID, "platinum_arsenide"));


            //ELEMENT NUGGETS
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tin_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"aluminum_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"zinc_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nickel_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesium_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lead_nugget"));
            //vanadium nugget
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bismuth_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silver_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"manganese_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"chromium_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"molybdenum_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tungsten_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"titanium_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"niobium_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tantalum_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cobalt_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"iridium_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"platinum_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"osmium_nugget"));
            //arsenic nugget
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pig_iron_nugget"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"wrought_iron_nugget"));


            //INGOTS
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tin_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"aluminum_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"zinc_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nickel_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lead_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bismuth_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"vanadium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silver_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"manganese_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"chromium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"molybdenum_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tungsten_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"titanium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"niobium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tantalum_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cobalt_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"iridium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"osmium_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"platinum_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"arsenic_ingot"));
            event.getRegistry().register(new ItemToxin("mercury",0));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pig_iron_ingot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"wrought_iron_ingot"));

            //ALLOY INGOTS
            event.getRegistry().register(new OldAlloyItem("cast_iron_ingot"));
            event.getRegistry().register(new AlloyItem("bronze_alloy"));
            event.getRegistry().register(new OldAlloyItem("aluminum_bronze_alloy"));
            event.getRegistry().register(new OldAlloyItem("brass_alloy"));
            event.getRegistry().register(new OldAlloyItem("nichrome_alloy"));
            event.getRegistry().register(new OldAlloyItem("steel_alloy"));
            event.getRegistry().register(new OldAlloyItem("stainless_steel_alloy"));
            event.getRegistry().register(new OldAlloyItem("solder_alloy"));
            event.getRegistry().register(new OldAlloyItem("alnico_alloy"));
            event.getRegistry().register(new OldAlloyItem("nitinol_alloy"));
            event.getRegistry().register(new OldAlloyItem("amalgam_alloy"));
            event.getRegistry().register(new OldAlloyItem("cupronickel_alloy"));
            event.getRegistry().register(new OldAlloyItem("nickel_silver_alloy"));
            event.getRegistry().register(new OldAlloyItem("invar_alloy"));
            event.getRegistry().register(new OldAlloyItem("inconel_alloy"));
            event.getRegistry().register(new OldAlloyItem("rose_metal_alloy"));
            event.getRegistry().register(new OldAlloyItem("magnalium_alloy"));
            event.getRegistry().register(new OldAlloyItem("duralumin_alloy"));
            event.getRegistry().register(new OldAlloyItem("osmiridium_alloy"));
            event.getRegistry().register(new OldAlloyItem("rose_gold_alloy"));
            event.getRegistry().register(new OldAlloyItem("white_gold_alloy"));
            event.getRegistry().register(new OldAlloyItem("green_gold_alloy"));
            event.getRegistry().register(new OldAlloyItem("blue_gold_alloy"));
            event.getRegistry().register(new OldAlloyItem("purple_gold_alloy"));


            //METAL STORAGE BLOCKS
            event.getRegistry().register(new BlockItem(ModBlocks.COPPER_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.TIN_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tin_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ALUMINUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"aluminum_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ZINC_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"zinc_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.NICKEL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nickel_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNESIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.LEAD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"lead_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.BISMUTH_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bismuth_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.VANADIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"vanadium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.SILVER_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silver_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.MANGANESE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"manganese_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.CHROMIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"chromium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.MOLYBDENUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"molybdenum_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.TUNGSTEN_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tungsten_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.TITANIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"titanium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.NIOBIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"niobium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.TANTALUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tantalum_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.COBALT_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cobalt_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.IRIDIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"iridium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.OSMIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"osmium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.PLATINUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"platinum_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.PIG_IRON_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pig_iron_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.WROUGHT_IRON_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"wrought_iron_block"));


            //ALLOY METAL BLOCKS
            event.getRegistry().register(new BlockItem(ModBlocks.CAST_IRON_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cast_iron_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.BRONZE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bronze_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ALUMINUM_BRONZE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"aluminum_bronze_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.BRASS_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"brass_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.INVAR_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"invar_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.STEEL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"steel_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.STAINLESS_STEEL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"stainless_steel_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ALNICO_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"alnico_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.MAGNALIUM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnalium_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.DURALUMIN_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"duralumin_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.CUPRONICKEL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cupronickel_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.NICHROME_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nichrome_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.NICKEL_SILVER_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nickel_silver_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.NITINOL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"nitinol_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.AMALGAM_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"amalgam_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROSE_METAL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"rose_metal_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.INCONEL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"inconel_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.ROSE_GOLD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"rose_gold_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.WHITE_GOLD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"white_gold_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.GREEN_GOLD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"green_gold_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.BLUE_GOLD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"blue_gold_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.PURPLE_GOLD_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"purple_gold_block"));


            //NONMETAL STORAGE BLOCKS
            event.getRegistry().register(new BlockItem(ModBlocks.GRAPHITE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"graphite_block"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.COKE_BLOCK,new Item.Properties().group(setup.rankineMetals),2400*9).setRegistryName(ProjectRankine.MODID,"coke_block"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.LIGNITE_BLOCK,new Item.Properties().group(setup.rankineMetals),1200*9).setRegistryName(ProjectRankine.MODID,"lignite_block"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.BITUMINOUS_COAL_BLOCK,new Item.Properties().group(setup.rankineMetals),2000*9).setRegistryName(ProjectRankine.MODID,"bituminous_coal_block"));
            event.getRegistry().register(new FuelBlockItem(ModBlocks.ANTHRACITE_COAL_BLOCK,new Item.Properties().group(setup.rankineMetals),2400*9).setRegistryName(ProjectRankine.MODID,"anthracite_coal_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.GRAVEL_CONCRETE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"gravel_concrete"));
            event.getRegistry().register(new BlockItem(ModBlocks.SALT_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"salt_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.SULFUR_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"sulfur_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.SILICON_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silicon_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.SILICON_CARBIDE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silicon_carbide_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.CALCIUM_SILICATE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"calcium_silicate_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.OPAL_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"opal_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.GARNET_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"garnet_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.FELDSPAR_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"feldspar_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.DOLOMITE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"dolomite_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.CALCITE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"calcite_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.OLIVINE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"olivine_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.PERIDOT_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"peridot_block"));
            event.getRegistry().register(new BlockItem(ModBlocks.PYROXENE_BLOCK,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pyroxene_block"));

            //misc blocks
            event.getRegistry().register(new BlockItem(ModBlocks.COPPER_SHEETMETAL,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_sheetmetal"));
            event.getRegistry().register(new BlockItem(ModBlocks.TIN_SHEETMETAL,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"tin_sheetmetal"));
            event.getRegistry().register(new BlockItem(ModBlocks.BRONZE_SHEETMETAL,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bronze_sheetmetal"));
            event.getRegistry().register(new BlockItem(ModBlocks.WROUGHT_IRON_SHEETMETAL,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"wrought_iron_sheetmetal"));
            event.getRegistry().register(new BlockItem(ModBlocks.STEEL_SHEETMETAL,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"steel_sheetmetal"));
            event.getRegistry().register(new BlockItem(ModBlocks.BEEHIVE_OVEN_PIT,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"beehive_oven_pit"));
            event.getRegistry().register(new BlockItem(ModBlocks.ALLOY_FURNACE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));
            event.getRegistry().register(new BlockItem(ModBlocks.PISTON_CRUSHER,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"piston_crusher"));
            event.getRegistry().register(new BlockItem(ModBlocks.COAL_FORGE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"coal_forge"));
            event.getRegistry().register(new BlockItem(ModBlocks.FINERY_FORGE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"finery_forge"));
            event.getRegistry().register(new BlockItem(ModBlocks.CRUCIBLE,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"crucible"));
            //event.getRegistry().register(new BlockItem(ModBlocks.ALNICO_CHEST,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"alnico_chest"));

            //ITEMS
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"clay_brick"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"refractory_brick"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"magnesium_refractory_brick"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"zircon_refractory_brick"));
            event.getRegistry().register(new BlockItem(ModBlocks.NITER,new Item.Properties().group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"niter"));
            event.getRegistry().register(new FuelItem(new Item.Properties().maxStackSize(64).group(setup.rankineMetals),1200).setRegistryName(ProjectRankine.MODID,"lignite"));
            event.getRegistry().register(new FuelItem(new Item.Properties().maxStackSize(64).group(setup.rankineMetals),2000).setRegistryName(ProjectRankine.MODID,"bituminous_coal"));
            event.getRegistry().register(new FuelItem(new Item.Properties().maxStackSize(64).group(setup.rankineMetals), 2400).setRegistryName(ProjectRankine.MODID,"coke"));
            event.getRegistry().register(new FuelItem(new Item.Properties().maxStackSize(64).group(setup.rankineMetals), 2400).setRegistryName(ProjectRankine.MODID,"anthracite_coal"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"calcite"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"dolomite"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"feldspar"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"olivine"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"pyroxene"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"bloom_iron"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"slag"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"salt"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silicon"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"sulfur"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"saltpeter"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"graphite"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"calcium_silicate"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"silicon_carbide"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"crushed_coal"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cinnabar"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"opal"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"garnet"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"peridot"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"mortar"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"quicklime"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(1).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"element"));

            event.getRegistry().register(new ItemBattery("battery0"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"graphite_electrode"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"cast_iron_panel"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"copper_wire"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"surface_condenser"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineMetals)).setRegistryName(ProjectRankine.MODID,"inconel_turbine_blade"));





//RANKINE TOOLS CREATIVE TAB --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


            //NORMAL TOOLS
            event.getRegistry().register(new ItemHammer(2, -3.2F, RankineToolMaterials.FLINT, new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"stone_hammer"));
            event.getRegistry().register(new BoneShovel(ItemTier.STONE, 1.5F, -3.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bone_shovel"));
            event.getRegistry().register(new PickaxeItem(RankineToolMaterials.FLINT, 1, -2.8F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName("flint_pickaxe"));
            event.getRegistry().register(new AxeItem(RankineToolMaterials.FLINT, 4.0F, -3.2F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName("flint_axe"));
            event.getRegistry().register(new ShovelItem(RankineToolMaterials.FLINT, 1.5F, -3.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"flint_shovel"));
            event.getRegistry().register(new ItemSpear(RankineToolMaterials.FLINT, 2, -2.9F, 0,(new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"flint_spear"));
            event.getRegistry().register(new ItemKnife(RankineToolMaterials.FLINT, 1, -2F, (new Item.Properties()).maxDamage(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"flint_knife"));
            event.getRegistry().register(new AlloySword(RankineToolMaterials.BRONZE, 3, -2.4F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_sword"));
            event.getRegistry().register(new ShovelItem(RankineToolMaterials.BRONZE, 1.5F, -3.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_shovel"));
            event.getRegistry().register(new AlloyPickaxe(RankineToolMaterials.BRONZE, 1, -2.8F, 0f,0.25f,0.05f, (new Item.Properties()).group(setup.rankineTools), new BronzeAlloyUtils()).setRegistryName(ProjectRankine.MODID,"bronze_pickaxe"));
            //event.getRegistry().register(new ProspectingPickaxe(RankineToolMaterials.BRONZE, 1, -3F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_prospecting_pickaxe"));
            event.getRegistry().register(new AxeItem(RankineToolMaterials.BRONZE, 4.0F, -3.2F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_axe"));
            event.getRegistry().register(new HoeItem(RankineToolMaterials.BRONZE, -2.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_hoe"));
            event.getRegistry().register(new ItemHammer(2, -3.2F, RankineToolMaterials.BRONZE, new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_hammer"));
            event.getRegistry().register(new ItemSpear(RankineToolMaterials.BRONZE, 2, -2.9F, 1, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"bronze_spear"));
            event.getRegistry().register(new ItemSpear(ItemTier.IRON, 2, -2.9F, 2, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"iron_spear"));
            event.getRegistry().register(new ItemHammer(2, -3.2F, ItemTier.IRON, new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"iron_hammer"));
            event.getRegistry().register(new ItemScraper(new Item.Properties().maxStackSize(1).maxDamage(256).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"iron_scraper"));
            event.getRegistry().register(new SwordItem(RankineToolMaterials.STEEL, 3, -2.4F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_sword"));
            event.getRegistry().register(new ShovelItem(RankineToolMaterials.STEEL, 1.5F, -3.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_shovel"));
            event.getRegistry().register(new PickaxeItem(RankineToolMaterials.STEEL, 1, -2.8F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_pickaxe"));
            event.getRegistry().register(new AxeItem(RankineToolMaterials.STEEL, 4.0F, -3.2F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_axe"));
            event.getRegistry().register(new ItemHammer(2, -3.2F, RankineToolMaterials.STEEL, new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_hammer"));
            event.getRegistry().register(new HoeItem(RankineToolMaterials.STEEL, 0.0F, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_hoe"));
            event.getRegistry().register(new ItemSpear(RankineToolMaterials.STEEL, 2, -2.9F, 3, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"steel_spear"));

            //GOLD TOOLS
            event.getRegistry().register(new ColoredGoldSword(new Item.Properties().group(setup.rankineTools),1).setRegistryName(ProjectRankine.MODID,"rose_gold_sword"));
            event.getRegistry().register(new ColoredGoldShovel(new Item.Properties().group(setup.rankineTools),1).setRegistryName(ProjectRankine.MODID,"rose_gold_shovel"));
            event.getRegistry().register(new ColoredGoldPickaxe(new Item.Properties().group(setup.rankineTools),1).setRegistryName(ProjectRankine.MODID,"rose_gold_pickaxe"));
            event.getRegistry().register(new ColoredGoldAxe(new Item.Properties().group(setup.rankineTools),1).setRegistryName(ProjectRankine.MODID,"rose_gold_axe"));
            event.getRegistry().register(new ColoredGoldHoe(new Item.Properties().group(setup.rankineTools),1).setRegistryName(ProjectRankine.MODID,"rose_gold_hoe"));
            event.getRegistry().register(new ColoredGoldSword(new Item.Properties().group(setup.rankineTools),2).setRegistryName(ProjectRankine.MODID,"white_gold_sword"));
            event.getRegistry().register(new ColoredGoldShovel(new Item.Properties().group(setup.rankineTools),2).setRegistryName(ProjectRankine.MODID,"white_gold_shovel"));
            event.getRegistry().register(new ColoredGoldPickaxe(new Item.Properties().group(setup.rankineTools),2).setRegistryName(ProjectRankine.MODID,"white_gold_pickaxe"));
            event.getRegistry().register(new ColoredGoldAxe(new Item.Properties().group(setup.rankineTools),2).setRegistryName(ProjectRankine.MODID,"white_gold_axe"));
            event.getRegistry().register(new ColoredGoldHoe(new Item.Properties().group(setup.rankineTools),2).setRegistryName(ProjectRankine.MODID,"white_gold_hoe"));
            event.getRegistry().register(new ColoredGoldSword(new Item.Properties().group(setup.rankineTools),3).setRegistryName(ProjectRankine.MODID,"green_gold_sword"));
            event.getRegistry().register(new ColoredGoldShovel(new Item.Properties().group(setup.rankineTools),3).setRegistryName(ProjectRankine.MODID,"green_gold_shovel"));
            event.getRegistry().register(new ColoredGoldPickaxe(new Item.Properties().group(setup.rankineTools),3).setRegistryName(ProjectRankine.MODID,"green_gold_pickaxe"));
            event.getRegistry().register(new ColoredGoldAxe(new Item.Properties().group(setup.rankineTools),3).setRegistryName(ProjectRankine.MODID,"green_gold_axe"));
            event.getRegistry().register(new ColoredGoldHoe(new Item.Properties().group(setup.rankineTools),3).setRegistryName(ProjectRankine.MODID,"green_gold_hoe"));
            event.getRegistry().register(new ColoredGoldSword(new Item.Properties().group(setup.rankineTools),4).setRegistryName(ProjectRankine.MODID,"blue_gold_sword"));
            event.getRegistry().register(new ColoredGoldShovel(new Item.Properties().group(setup.rankineTools),4).setRegistryName(ProjectRankine.MODID,"blue_gold_shovel"));
            event.getRegistry().register(new ColoredGoldPickaxe(new Item.Properties().group(setup.rankineTools),4).setRegistryName(ProjectRankine.MODID,"blue_gold_pickaxe"));
            event.getRegistry().register(new ColoredGoldAxe(new Item.Properties().group(setup.rankineTools),4).setRegistryName(ProjectRankine.MODID,"blue_gold_axe"));
            event.getRegistry().register(new ColoredGoldHoe(new Item.Properties().group(setup.rankineTools),4).setRegistryName(ProjectRankine.MODID,"blue_gold_hoe"));
            event.getRegistry().register(new ColoredGoldSword(new Item.Properties().group(setup.rankineTools),5).setRegistryName(ProjectRankine.MODID,"purple_gold_sword"));
            event.getRegistry().register(new ColoredGoldShovel(new Item.Properties().group(setup.rankineTools),5).setRegistryName(ProjectRankine.MODID,"purple_gold_shovel"));
            event.getRegistry().register(new ColoredGoldPickaxe(new Item.Properties().group(setup.rankineTools),5).setRegistryName(ProjectRankine.MODID,"purple_gold_pickaxe"));
            event.getRegistry().register(new ColoredGoldAxe(new Item.Properties().group(setup.rankineTools),5).setRegistryName(ProjectRankine.MODID,"purple_gold_axe"));
            event.getRegistry().register(new ColoredGoldHoe(new Item.Properties().group(setup.rankineTools),5).setRegistryName(ProjectRankine.MODID,"purple_gold_hoe"));

            event.getRegistry().register(new FlintlockPistol(new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"flintlock_pistol"));
            event.getRegistry().register(new LeadShotItem(new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"lead_shot"));
            event.getRegistry().register(new ThermometerItem(new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"thermometer"));
            event.getRegistry().register(new MetalDetector(new Item.Properties().group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"metal_detector"));

            //BUCKETS
            event.getRegistry().register(new WoodBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(16).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"wood_bucket"));
            event.getRegistry().register(new WoodBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.WOOD_BUCKET)).maxStackSize(1).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"water_wood_bucket"));
            event.getRegistry().register(new MilkWoodBucket(new Item.Properties().group(setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1)).setRegistryName(ProjectRankine.MODID,"milk_wood_bucket"));
            event.getRegistry().register(new BrassBucket(() -> Fluids.EMPTY, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(16).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"brass_bucket"));
            event.getRegistry().register(new BrassBucket(() -> Fluids.WATER, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"water_brass_bucket"));
            event.getRegistry().register(new BrassBucket(() -> Fluids.LAVA, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"lava_brass_bucket"));
            event.getRegistry().register(new BrassBucket(() -> ModFluids.LIQUID_PIG_IRON, (new Item.Properties().containerItem(ModItems.BRASS_BUCKET)).maxStackSize(1).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"liquid_pig_iron_brass_bucket"));
            event.getRegistry().register(new MilkBrassBucket(new Item.Properties().group(setup.rankineTools).containerItem(ModItems.WOOD_BUCKET).maxStackSize(1)).setRegistryName(ProjectRankine.MODID,"milk_brass_bucket"));

            //TEMPLATES
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"shovel_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"hoe_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"spear_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"pickaxe_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"sword_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"axe_template"));
            event.getRegistry().register(new Item(new Item.Properties().maxStackSize(64).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"hammer_template"));

            event.getRegistry().register(new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.HEAD, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"brigandine_helmet"));
            event.getRegistry().register(new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.CHEST, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"brigandine_chestplate"));
            event.getRegistry().register(new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.LEGS, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"brigandine_leggings"));
            event.getRegistry().register(new ArmorItem(RankineArmorMaterials.BRIGANDINE, EquipmentSlotType.FEET, (new Item.Properties()).group(setup.rankineTools)).setRegistryName(ProjectRankine.MODID,"brigandine_boots"));

        }


        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(AlloyFurnaceTile::new, ModBlocks.ALLOY_FURNACE).build(null).setRegistryName(ProjectRankine.MODID,"alloy_furnace"));
            //event.getRegistry().register(TileEntityType.Builder.create(LimeKilnTile::new,ModBlocks.LIME_KILN).build(null).setRegistryName(ProjectRankine.MODID,"lime_kiln"));
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
        public static void registerFluids(final RegistryEvent.Register<Fluid> event) {
            final ResourceLocation FLUID_STILL = new ResourceLocation("rankine:block/liquid_pig_iron_still");
            final ResourceLocation FLUID_FLOWING = new ResourceLocation("rankine:block/liquid_pig_iron");
            event.getRegistry().register(new ForgeFlowingFluid.Source(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_PIG_IRON, () -> ModFluids.LIQUID_PIG_IRON_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xEEBD4900))
                    .bucket(() -> ModItems.LIQUID_PIG_IRON_BRASS_BUCKET).block(() -> ModBlocks.LIQUID_PIG_IRON_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_pig_iron"));
            event.getRegistry().register(new ForgeFlowingFluid.Flowing(new ForgeFlowingFluid.Properties(() -> ModFluids.LIQUID_PIG_IRON, () -> ModFluids.LIQUID_PIG_IRON_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xEEBD4900))
                    .bucket(() -> ModItems.LIQUID_PIG_IRON_BRASS_BUCKET).block(() -> ModBlocks.LIQUID_PIG_IRON_BLOCK)).setRegistryName(ProjectRankine.MODID,"liquid_pig_iron_flowing"));
        }

        @SubscribeEvent
        public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
            event.getRegistry().register(new BlastEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"blast"));
            event.getRegistry().register(new AtomizeEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"atomize"));
            event.getRegistry().register(new LightningAspectEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND).setRegistryName(ProjectRankine.MODID,"lightning_aspect"));
        }

        /*
        @SubscribeEvent
        public static void onRegisterBiome(RegistryEvent.Register<Biome> event)
        {
            registerBiome(event, new CedarForestBiome(), "cedar_forest", 10,
                    BiomeManager.BiomeType.COOL, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
            registerBiome(event, new CedarForestBiome(), "pinyon_juniper_woodlands", 10,
                    BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.OVERWORLD);
        }

        private static void registerBiome(RegistryEvent.Register<Biome> event, Biome biome, String registryName, int spawnWeight, BiomeManager.BiomeType spawnType, BiomeDictionary.Type... types)
        {
            event.getRegistry().register(biome.setRegistryName(new ResourceLocation(MODID, registryName)));
            BiomeDictionary.addTypes(biome, types);
            BiomeManager.addBiome(spawnType, new BiomeManager.BiomeEntry(biome, spawnWeight));
        }


         */

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
            LOGGER.log(Level.INFO, "features/structures registered.");
        }
    }


    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey)
    {
        entry.setRegistryName(new ResourceLocation(ProjectRankine.MODID, registryKey));
        registry.register(entry);
        return entry;
    }


}

