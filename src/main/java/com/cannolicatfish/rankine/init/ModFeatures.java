package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.placement.IntrusionPlacement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.OptionalInt;

public class ModFeatures {

    // FEATURE CONFIGS
    public static final BaseTreeFeatureConfig CEDAR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.CEDAR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.CEDAR_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(6, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(1, 0), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(5, 2, 0),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig TALL_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(10, 4, 0),
            new TwoLayerFeature(2, 0, 0)))
            .setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    public static final BaseTreeFeatureConfig LARGE_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LEAVES.getDefaultState()),
            new MegaPineFoliagePlacer(FeatureSpread.func_242253_a(0, 0), FeatureSpread.func_242253_a(0, 0), FeatureSpread.func_242253_a(3, 6)),
            new GiantTrunkPlacer(9, 4, 12),
            new TwoLayerFeature(1, 1, 2)))
            .setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    public static final BaseTreeFeatureConfig EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(4, 4, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig SMALL_EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(0, 0), FeatureSpread.func_242253_a(0, 0)),
            new StraightTrunkPlacer(2, 1, 0),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig LARGE_EASTERN_HEMLOCK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.EASTERN_HEMLOCK_LEAVES.getDefaultState()),
            new MegaPineFoliagePlacer(FeatureSpread.func_242253_a(0, 0), FeatureSpread.func_242253_a(0, 0), FeatureSpread.func_242253_a(4, 13)),
            new GiantTrunkPlacer(11, 2, 14),
            new TwoLayerFeature(1, 1, 2)))
            .setDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Blocks.PODZOL.getDefaultState()))))
            .build();

    public static final BaseTreeFeatureConfig MAGNOLIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(1, 0), FeatureSpread.func_242253_a(0, 0)),
            new ForkyTrunkPlacer(3, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig LARGE_MAGNOLIA_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.MAGNOLIA_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(0, 0)),
            new ForkyTrunkPlacer(5, 2, 2),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig PINYON_PINE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.PINYON_PINE_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(0, 0)),
            new ForkyTrunkPlacer(5, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig JUNIPER_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.JUNIPER_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.JUNIPER_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(1, 0), FeatureSpread.func_242253_a(0, 0)),
            new ForkyTrunkPlacer(2, 0, 0),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig COCONUT_PALM_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.COCONUT_PALM_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.COCONUT_PALM_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(1, 0), FeatureSpread.func_242253_a(0, 0)),
            new ForkyTrunkPlacer(10, 2, 2),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();


    //OTHER TREE CONFIGS
    public static final BaseTreeFeatureConfig DEAD_BALSAM_FIR_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BALSAM_FIR_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(1, 0), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig SMALL_SPRUCE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)),
            new StraightTrunkPlacer(3, 1, 1),
            new TwoLayerFeature(2, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig LAGOOAN_OAK = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()),
            new AcaciaFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(1, 0)),
            new ForkyTrunkPlacer(2, 1, 2),
            new TwoLayerFeature(1, 0, 2)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.MAPLE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.MAPLE_LEAVES.getDefaultState()),
            new FancyFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(4, 0), 4),
            new FancyTrunkPlacer(4, 9, 0),
            new TwoLayerFeature(0, 0, 0,
                    OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING)
            .build();

    public static final BaseTreeFeatureConfig YELLOW_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.YELLOW_BIRCH_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.YELLOW_BIRCH_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(0, 0), 3),
            new StraightTrunkPlacer(6, 3, 0),
            new TwoLayerFeature(1, 0, 1)))
            .setIgnoreVines()
            .build();

    public static final BaseTreeFeatureConfig BLACK_BIRCH_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.BLACK_BIRCH_LOG.getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.BLACK_BIRCH_LEAVES.getDefaultState()),
            new BlobFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242253_a(0, 0), 3),
            new StraightTrunkPlacer(6, 3, 0),
            new TwoLayerFeature(1, 0, 1)))
            .setIgnoreVines()
            .build();

    public static final BlockClusterFeatureConfig ELDERBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.ELDERBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig SNOWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.SNOWBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig BLUEBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.BLUEBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig RASPBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.RASPBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig BLACKBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.BLACKBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig CRANBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CRANBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig STRAWBERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.STRAWBERRY_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig PINEAPPLE_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.PINEAPPLE_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK,Blocks.SAND, Blocks.RED_SAND)).func_227317_b_().build();
    public static final BlockClusterFeatureConfig BANANA_YUCCA_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.BANANA_YUCCA_BUSH.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND)).func_227317_b_().build();


    // LOCAL_MODIFICATIONS
    public static final ConfiguredFeature<?, ?> METEORITE = new MeteoriteFeature(MeteoriteFeatureConfig.CODEC).withConfiguration(new MeteoriteFeatureConfig(ModBlocks.METEORITE.getDefaultState(), 1))
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(100)));

    // VEGETAL_DECORATION


    public static final ConfiguredFeature<?, ?> ELDERBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(ELDERBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> SNOWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(SNOWBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLUEBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(BLUEBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> RASPBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(RASPBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BLACKBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(BLACKBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> CRANBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(CRANBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> STRAWBERRY_BUSH = Feature.RANDOM_PATCH.withConfiguration(STRAWBERRY_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> PINEAPPLE_BUSH = Feature.RANDOM_PATCH.withConfiguration(PINEAPPLE_BUSH_PATCH_CONFIG);
    public static final ConfiguredFeature<?, ?> BANANA_YUCCA_BUSH = Feature.RANDOM_PATCH.withConfiguration(BANANA_YUCCA_BUSH_PATCH_CONFIG);


    public static final ConfiguredFeature<?, ?> YELLOW_BIRCH_TREE = Feature.TREE.withConfiguration(YELLOW_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> BLACK_BIRCH_TREE = Feature.TREE.withConfiguration(BLACK_BIRCH_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> EASTERN_HEMLOCK_TREE = Feature.TREE.withConfiguration(EASTERN_HEMLOCK_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> CEDAR_TREE = Feature.TREE.withConfiguration(CEDAR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> COCONUT_PALM_TREE = Feature.TREE.withConfiguration(COCONUT_PALM_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> PINYON_PINE_TREE = Feature.TREE.withConfiguration(PINYON_PINE_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> BALSAM_FIR_TREE = Feature.TREE.withConfiguration(BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> DEAD_BALSAM_FIR_TREE = Feature.TREE.withConfiguration(DEAD_BALSAM_FIR_TREE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));

    // UNDERGROUND_ORES

    public static final Feature<RankineOreFeatureConfig> RANKINE_ORE = new RankineOreFeature(RankineOreFeatureConfig.CODEC);
    public static final Feature<RankineMultiOreFeatureConfig> MULTI_RANKINE_ORE = new RankineMultiOreFeature(RankineMultiOreFeatureConfig.CODEC);

    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK = new FlatBedrockFeature(ReplacerFeatureConfig.CODEC).withConfiguration(
            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.BEDROCK_LAYERS.get())).withPlacement(new ReplacerPlacement(NoPlacementConfig.CODEC).configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?, ?> FLAT_BEDROCK_NETHER = new FlatBedrockFeature(ReplacerFeatureConfig.CODEC).withConfiguration(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.BEDROCK_LAYERS.get())).withPlacement(new ReplacerPlacement(NoPlacementConfig.CODEC).configure(IPlacementConfig.NO_PLACEMENT_CONFIG));

    public static final ConfiguredFeature<?, ?> GRAVEL_DISKS = Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), FeatureSpread.func_242253_a(2,5), 2,
            Lists.newArrayList(Blocks.DIRT.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final ConfiguredFeature<?, ?> SAND_DISKS = Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.SAND.getDefaultState(), FeatureSpread.func_242253_a(2,3), 2,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final ConfiguredFeature<?, ?> CLAY_DISKS = Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), FeatureSpread.func_242253_a(2,2), 1,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final ConfiguredFeature<?, ?> ORE_ALLUVIUM = Feature.DISK.withConfiguration(new SphereReplaceConfig(ModBlocks.ALLUVIUM.getDefaultState(), FeatureSpread.func_242253_a(1,2), 1,
            Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

    public static final ConfiguredFeature<?, ?> ORE_INTRUSION = new IntrusionFeature(ReplacerFeatureConfig.CODEC).withConfiguration(
            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 1, 256)).withPlacement(new IntrusionPlacement(ChanceConfig.CODEC).configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?, ?> NETHER_ORE_INTRUSION = new NetherIntrusionFeature(ReplacerFeatureConfig.CODEC).withConfiguration(
            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.AIR.getDefaultState(), 1, 256)).withPlacement(new IntrusionPlacement(ChanceConfig.CODEC).configure(new ChanceConfig(2)));
    public static final ConfiguredFeature<?,?> DEFAULT_STONE_GEN = new StoneReplacerFeature(StoneReplacerFeatureConfig.CODEC).withConfiguration(
            new StoneReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, 0)).withPlacement(new ReplacerPlacement(NoPlacementConfig.CODEC).configure(IPlacementConfig.NO_PLACEMENT_CONFIG));
    public static final ConfiguredFeature<?,?> ANDESITIC_TUFF = new ModularOreFeature(OreFeatureConfig.CODEC, ModBlocks.HORNBLENDE_ANDESITE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.ANDESITIC_TUFF.getDefaultState(), 40))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(64, 0, 128))).square().func_242731_b(1);
    public static final ConfiguredFeature<?,?> ANDESITE_VAR = new ModularOreFeature(OreFeatureConfig.CODEC, ModBlocks.HORNBLENDE_ANDESITE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.ANDESITE.getDefaultState(), 180))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(64, 0, 128))).square().func_242731_b(2);
    public static final ConfiguredFeature<?,?> ORE_NODULE = new ModularOreFeature(OreFeatureConfig.CODEC, ModBlocks.LIMESTONE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.LIMESTONE_NODULE.getDefaultState(), 6))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(31, 0, 70))).square().func_242731_b(20);
    public static final ConfiguredFeature<?,?> ORE_IRONSTONE = new ModularOreFeature(OreFeatureConfig.CODEC, Blocks.SANDSTONE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.IRONSTONE.getDefaultState(), 50))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(60, 0, 128))).square().func_242731_b(5);
    public static final ConfiguredFeature<?,?> ORE_IRONSTONE_RED = new ModularOreFeature(OreFeatureConfig.CODEC, Blocks.RED_SANDSTONE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.IRONSTONE.getDefaultState(), 50))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(60, 0, 128))).square().func_242731_b(5);
    public static final ConfiguredFeature<?,?> ORE_OPAL = new ModularOreFeature(OreFeatureConfig.CODEC, ModBlocks.IRONSTONE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.OPAL_ORE.getDefaultState().with(RankineOre.TYPE, 23), 10))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(60, 0, 128))).square().func_242731_b(30);
    public static final ConfiguredFeature<?,?> ORE_PHOSPHORITE = new ModularOreFeature(OreFeatureConfig.CODEC, Blocks.SANDSTONE.getDefaultState()).withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.PHOSPHORITE.getDefaultState(), 20))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(60, 0, 128))).square().func_242731_b(10);



    //public static final ConfiguredFeature<?,?> ORE_IRONSTONE = MULTI_RANKINE_ORE.withConfiguration(new RankineMultiOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.SANDSTONE, ModBlocks.IRONSTONE.getStateContainer().getBaseState(), 40, 0.3f))
      //      .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(11, 0, 70))).square().func_242731_b(1);


    // NATIVE ORES
    public static final ConfiguredFeature<?,?> ORE_NATIVE_COPPER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_COPPER_ORE.getStateContainer().getBaseState(), Config.NATIVE_COPPER_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_COPPER_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_COPPER_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_COPPER_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_TIN = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_TIN_ORE.getStateContainer().getBaseState(), Config.NATIVE_TIN_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_TIN_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_TIN_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_TIN_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_LEAD = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_LEAD_ORE.getStateContainer().getBaseState(), Config.NATIVE_LEAD_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_LEAD_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_LEAD_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_LEAD_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_SILVER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_SILVER_ORE.getStateContainer().getBaseState(), Config.NATIVE_SILVER_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_SILVER_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_SILVER_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_SILVER_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_ALUMINUM = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_ALUMINUM_ORE.getStateContainer().getBaseState(), Config.NATIVE_ALUMINUM_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_ALUMINUM_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_ALUMINUM_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_ALUMINUM_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_GOLD = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_GOLD_ORE.getStateContainer().getBaseState(), Config.NATIVE_GOLD_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_GOLD_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_GOLD_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_GOLD_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_STIBNITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.STIBNITE_ORE.getStateContainer().getBaseState(), Config.STIBNITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.STIBNITE_ORE_MIN_HEIGHT.get(), 0, Config.STIBNITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.STIBNITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_BISMUTH = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.NATIVE_BISMUTH_ORE.getStateContainer().getBaseState(), Config.NATIVE_BISMUTH_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_BISMUTH_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_BISMUTH_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_BISMUTH_ORE_COUNT.get());

    // COAL ORES
    public static final ConfiguredFeature<?,?> ORE_LIGNITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.LIGNITE_ORE.getStateContainer().getBaseState(), Config.LIGNITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.LIGNITE_ORE_MIN_HEIGHT.get(), 0, Config.LIGNITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.LIGNITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_SUBBITUMINOUS_COAL = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.SUBBITUMINOUS_ORE.getStateContainer().getBaseState(), Config.SUBBITUMINOUS_COAL_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.SUBBITUMINOUS_COAL_ORE_MIN_HEIGHT.get(), 0, Config.SUBBITUMINOUS_COAL_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.SUBBITUMINOUS_COAL_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_BITUMINOUS_COAL = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.BITUMINOUS_ORE.getStateContainer().getBaseState(), Config.BITUMINOUS_COAL_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.BITUMINOUS_COAL_ORE_MIN_HEIGHT.get(), 0, Config.BITUMINOUS_COAL_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.BITUMINOUS_COAL_ORE_COUNT.get());

    // BRONZE TIER ORES
    public static final ConfiguredFeature<?,?> ORE_MALACHITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.MALACHITE_ORE.getStateContainer().getBaseState(), Config.MALACHITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.MALACHITE_ORE_MIN_HEIGHT.get(), 0, Config.MALACHITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.MALACHITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_CASSITERITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.CASSITERITE_ORE.getStateContainer().getBaseState(), Config.CASSITERITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.CASSITERITE_ORE_MIN_HEIGHT.get(), 0, Config.CASSITERITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.CASSITERITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_BAUXITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.BAUXITE_ORE.getStateContainer().getBaseState(), Config.BAUXITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.BAUXITE_ORE_MIN_HEIGHT.get(), 0, Config.BAUXITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.BAUXITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_SPHALERITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.SPHALERITE_ORE.getStateContainer().getBaseState(), Config.SPHALERITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.SPHALERITE_ORE_MIN_HEIGHT.get(), 0, Config.SPHALERITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.SPHALERITE_ORE_COUNT.get());

    // IRON TIER ORES
    public static final ConfiguredFeature<?,?> ORE_MAGNESITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.MAGNESITE_ORE.getStateContainer().getBaseState(), Config.MAGNESITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.MAGNESITE_ORE_MIN_HEIGHT.get(), 0, Config.MAGNESITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.MAGNESITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_MAGNETITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.MAGNETITE_ORE.getStateContainer().getBaseState(), Config.MAGNETITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.MAGNETITE_ORE_MIN_HEIGHT.get(), 0, Config.MAGNETITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.MAGNETITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_PENTLANDITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.PENTLANDITE_ORE.getStateContainer().getBaseState(), Config.PENTLANDITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.PENTLANDITE_ORE_MIN_HEIGHT.get(), 0, Config.PENTLANDITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.PENTLANDITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_PYROLUSITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.PYROLUSITE_ORE.getStateContainer().getBaseState(), Config.PYROLUSITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.PYROLUSITE_ORE_MIN_HEIGHT.get(), 0, Config.PYROLUSITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.PYROLUSITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_ACANTHITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.ACANTHITE_ORE.getStateContainer().getBaseState(), Config.ACANTHITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.ACANTHITE_ORE_MIN_HEIGHT.get(), 0, Config.ACANTHITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.ACANTHITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_GALENA = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.GALENA_ORE.getStateContainer().getBaseState(), Config.GALENA_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.GALENA_ORE_MIN_HEIGHT.get(), 0, Config.GALENA_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.GALENA_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_PETALITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.PETALITE_ORE.getStateContainer().getBaseState(), Config.PETALITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.PETALITE_ORE_MIN_HEIGHT.get(), 0, Config.PETALITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.PETALITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_CINNABAR = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.IGNEOUS, ModBlocks.CINNABAR_ORE.getStateContainer().getBaseState(), Config.CINNABAR_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.CINNABAR_ORE_MIN_HEIGHT.get(), 0, Config.CINNABAR_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.CINNABAR_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_CHROMITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE, ModBlocks.CHROMITE_ORE.getStateContainer().getBaseState(), Config.CHROMITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.CHROMITE_ORE_MIN_HEIGHT.get(), 0, Config.CHROMITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.CHROMITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_CELESTINE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.SEDIMENTARY, ModBlocks.CELESTINE_ORE.getStateContainer().getBaseState(), Config.CELESTINE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.CELESTINE_ORE_MIN_HEIGHT.get(), 0, Config.CELESTINE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.CELESTINE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_VANADINITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NO_SPM, ModBlocks.VANADINITE_ORE.getStateContainer().getBaseState(), Config.VANADINITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.VANADINITE_ORE_MIN_HEIGHT.get(), 0, Config.VANADINITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.VANADINITE_ORE_COUNT.get());


    // MISC ORES
    public static final ConfiguredFeature<?,?> ORE_LAZURITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.LIMESTONE, ModBlocks.LAZURITE_ORE.getStateContainer().getBaseState(), Config.LAZURITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.LAZURITE_ORE_MIN_HEIGHT.get(), 0, Config.LAZURITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.LAZURITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_EMERALD = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.EMERALD_ORE.getStateContainer().getBaseState(), Config.EMERALD_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.EMERALD_ORE_MIN_HEIGHT.get(), 0, Config.EMERALD_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.EMERALD_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_AQUAMARINE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.AQUAMARINE_ORE.getStateContainer().getBaseState(), Config.AQUAMARINE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.AQUAMARINE_ORE_MIN_HEIGHT.get(), 0, Config.AQUAMARINE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.AQUAMARINE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_MAJORITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE, ModBlocks.MAJORITE_ORE.getStateContainer().getBaseState(), Config.MAJORITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.MAJORITE_ORE_MIN_HEIGHT.get(), 0, Config.MAJORITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.MAJORITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_PLUMBAGO = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.MARBLE, ModBlocks.PLUMBAGO_ORE.getStateContainer().getBaseState(), Config.PLUMBAGO_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.PLUMBAGO_ORE_MIN_HEIGHT.get(), 0, Config.PLUMBAGO_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.PLUMBAGO_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_HALITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.HALITE_ORE.getStateContainer().getBaseState(), Config.HALITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.HALITE_ORE_MIN_HEIGHT.get(), 0, Config.HALITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.HALITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_PINK_HALITE = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD, ModBlocks.PINK_HALITE_ORE.getStateContainer().getBaseState(), Config.PINK_HALITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.PINK_HALITE_ORE_MIN_HEIGHT.get(), 0, Config.PINK_HALITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.PINK_HALITE_ORE_COUNT.get());


    // NETHER ORES
    public static final ConfiguredFeature<?,?> ORE_NATIVE_ARSENIC_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NK_B, ModBlocks.NATIVE_ARSENIC_ORE.getStateContainer().getBaseState(), Config.NATIVE_ARSENIC_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_ARSENIC_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_ARSENIC_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_ARSENIC_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_SULFUR_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NK_B, ModBlocks.NATIVE_SULFUR_ORE.getStateContainer().getBaseState(), Config.NATIVE_SULFUR_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_SULFUR_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_SULFUR_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_SULFUR_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_ANTHRACITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NK_B, ModBlocks.ANTHRACITE_ORE.getStateContainer().getBaseState(), Config.ANTHRACITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.ANTHRACITE_ORE_MIN_HEIGHT.get(), 0, Config.ANTHRACITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.ANTHRACITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_BISMUTHINITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.BISMUTHINITE_ORE.getStateContainer().getBaseState(), Config.BISMUTHINITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.BISMUTHINITE_ORE_MIN_HEIGHT.get(), 0, Config.BISMUTHINITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.BISMUTHINITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_GREENOCKITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.GREENOCKITE_ORE.getStateContainer().getBaseState(), Config.GREENOCKITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.GREENOCKITE_ORE_MIN_HEIGHT.get(), 0, Config.GREENOCKITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.GREENOCKITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_COLUMBITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.COLUMBITE_ORE.getStateContainer().getBaseState(), Config.COLUMBITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.COLUMBITE_ORE_MIN_HEIGHT.get(), 0, Config.COLUMBITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.COLUMBITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_TANTALITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.TANTALITE_ORE.getStateContainer().getBaseState(), Config.TANTALITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.TANTALITE_ORE_MIN_HEIGHT.get(), 0, Config.TANTALITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.TANTALITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_MOISSANITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.MOISSANITE_ORE.getStateContainer().getBaseState(), Config.MOISSANITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.MOISSANITE_ORE_MIN_HEIGHT.get(), 0, Config.MOISSANITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.MOISSANITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_ILMENITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.ILMENITE_ORE.getStateContainer().getBaseState(), Config.ILMENITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.ILMENITE_ORE_MIN_HEIGHT.get(), 0, Config.ILMENITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.ILMENITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_WOLFRAMITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.WOLFRAMITE_ORE.getStateContainer().getBaseState(), Config.WOLFRAMITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.WOLFRAMITE_ORE_MIN_HEIGHT.get(), 0, Config.WOLFRAMITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.WOLFRAMITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_SPERRYLITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.SPERRYLITE_ORE.getStateContainer().getBaseState(), Config.SPERRYLITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.SPERRYLITE_ORE_MIN_HEIGHT.get(), 0, Config.SPERRYLITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.SPERRYLITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_COBALTITE_NETHER = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.NETHERRACK, ModBlocks.COBALTITE_ORE.getStateContainer().getBaseState(), Config.COBALTITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.COBALTITE_ORE_MIN_HEIGHT.get(), 0, Config.COBALTITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.COBALTITE_ORE_COUNT.get());


    // END ORES
    public static final ConfiguredFeature<?,?> ORE_NATIVE_GALLIUM_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.NATIVE_GALLIUM_ORE.getStateContainer().getBaseState(), Config.NATIVE_GALLIUM_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_GALLIUM_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_GALLIUM_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_GALLIUM_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_INDIUM_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.NATIVE_INDIUM_ORE.getStateContainer().getBaseState(), Config.NATIVE_INDIUM_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_INDIUM_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_INDIUM_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_INDIUM_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_SELENIUM_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.NATIVE_SELENIUM_ORE.getStateContainer().getBaseState(), Config.NATIVE_SELENIUM_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_SELENIUM_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_SELENIUM_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_SELENIUM_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_NATIVE_TELLURIUM_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.NATIVE_TELLURIUM_ORE.getStateContainer().getBaseState(), Config.NATIVE_TELLURIUM_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.NATIVE_TELLURIUM_ORE_MIN_HEIGHT.get(), 0, Config.NATIVE_TELLURIUM_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.NATIVE_TELLURIUM_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_URANINITE_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.URANINITE_ORE.getStateContainer().getBaseState(), Config.URANINITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.URANINITE_ORE_MIN_HEIGHT.get(), 0, Config.URANINITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.URANINITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_FLUORITE_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.FLUORITE_ORE.getStateContainer().getBaseState(), Config.FLUORITE_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.FLUORITE_ORE_MIN_HEIGHT.get(), 0, Config.FLUORITE_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.FLUORITE_ORE_COUNT.get());
    public static final ConfiguredFeature<?,?> ORE_XENOTIME_END = RANKINE_ORE.withConfiguration(new RankineOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType.END, ModBlocks.XENOTIME_ORE.getStateContainer().getBaseState(), Config.XENOTIME_ORE_SIZE.get()))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(Config.XENOTIME_ORE_MIN_HEIGHT.get(), 0, Config.XENOTIME_ORE_MAX_HEIGHT.get()))).square().func_242731_b(Config.XENOTIME_ORE_COUNT.get());

}
