package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.feature.mushrooms.*;
import com.cannolicatfish.rankine.world.gen.feature.ores.DefaultOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.DiskOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.RankineOreFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ores.SphericalOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.trees.*;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class RankineFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, ProjectRankine.MODID);

    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DEFAULT_ORE = REGISTRY.register("default_ore_feature", () -> new DefaultOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> SPHERE_ORE = REGISTRY.register("sphere_ore_feature", () -> new SphericalOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DISK_ORE = REGISTRY.register("disk_ore_feature", () -> new DiskOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<DiskConfiguration>> LAND_DISK = REGISTRY.register("land_disk_feature", () -> new LandDiskFeature(DiskConfiguration.CODEC));
    public static final RegistryObject<Feature<MeteoriteFeatureConfig>> METEORITE_FEATURE = REGISTRY.register("meteorite_feature", () -> new MeteoriteFeature(MeteoriteFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_METEORITE_FEATURE = REGISTRY.register("end_meteorite_feature", () -> new EndMeteoriteFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ANTIMATTER_BLOB_FEATURE = REGISTRY.register("antimatter_blob_feature", () -> new AntimatterFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FUMAROLE_FEATURE = REGISTRY.register("fumarole_feature", () -> new FumaroleFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> COLUMN_FEATURE = REGISTRY.register("column_feature", () -> new ColumnFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WALL_MUSHROOMS = REGISTRY.register("wall_mushrooms_feature", () -> new WallMushroomsFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ReplacerFeatureConfig>> FLAT_BEDROCK_FEATURE = REGISTRY.register("flat_bedrock_feature", () -> new FlatBedrockFeature(ReplacerFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> INTRUSION = REGISTRY.register("intrusion_feature", () -> new IntrusionFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WORLD_REPLACER_FEATURE = REGISTRY.register("world_replacer_feature", () -> new WorldReplacerFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> POST_WORLD_REPLACER_FEATURE = REGISTRY.register("post_world_replacer_feature", () -> new PostWorldReplacerFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>> COBBLE_PATCH = REGISTRY.register("cobble_patch_feature", () -> new CobblePatchFeature(RandomPatchConfiguration.CODEC));

    public static final RegistryObject<Feature<TreeConfiguration>> EASTERN_HEMLOCK_TREE = REGISTRY.register("eastern_hemlock_tree", () -> new EasternHemlockTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> WESTERN_HEMLOCK_TREE = REGISTRY.register("western_hemlock_tree", () -> new WesternHemlockTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BALSAM_FIR_TREE = REGISTRY.register("balsam_fir_tree", () -> new BalsamFirTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> COCONUT_PALM_TREE = REGISTRY.register("coconut_palm_tree_feature", () -> new CoconutPalmTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BIRCH_TREE = REGISTRY.register("birch_tree_feature", () -> new BirchTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> PETRIFIED_CHORUS_TREE = REGISTRY.register("petrified_chorus_tree_feature", () -> new PetrifiedChorusTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> MAGNOLIA_TREE = REGISTRY.register("magnolia_tree_feature", () -> new MagnoliaTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> ERYTHRINA_TREE = REGISTRY.register("erythrina_tree_feature", () -> new ErythrinaTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BLACK_WALNUT_TREE = REGISTRY.register("black_walnut_tree_feature", () -> new BlackWalnutTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> RED_CEDAR_TREE = REGISTRY.register("red_cedar_tree_feature", () -> new RedCedarTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> JUNIPER_TREE = REGISTRY.register("juniper_tree_feature", () -> new JuniperTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> PINYON_PINE_TREE = REGISTRY.register("pinyon_pine_tree_feature", () -> new PinyonPineTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> WEEPING_WILLOW_TREE = REGISTRY.register("weeping_willow_tree_feature", () -> new WeepingWillowTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> HONEY_LOCUST_TREE = REGISTRY.register("honey_locust_tree_feature", () -> new HoneyLocustTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> MAPLE_TREE = REGISTRY.register("maple_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> SHARINGA_TREE = REGISTRY.register("sharinga_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> CORK_OAK_TREE = REGISTRY.register("cork_oak_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> CINNAMON_TREE = REGISTRY.register("cinnamon_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static final RegistryObject<Feature<BlockPileConfiguration>> TINDER_CONK_FEATURE = REGISTRY.register("tinder_conk_feature", () -> new TinderConkMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> LIONS_MANE_FEATURE = REGISTRY.register("lions_mane_feature", () -> new LionsManeMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> TURKEY_TAIL_FEATURE = REGISTRY.register("turkey_tail_feature", () -> new TurkeyTailMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> SULFUR_SHELF_FEATURE = REGISTRY.register("sulfur_shelf_feature", () -> new SulfurShelfMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> HONEY_FEATURE = REGISTRY.register("honey_feature", () -> new HoneyMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> ARTIST_CONK_FEATURE = REGISTRY.register("artist_conk_feature", () -> new ArtistsConkMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> OYSTER_FEATURE = REGISTRY.register("oyster_feature", () -> new OysterMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> CINNABAR_POLYPORE_FEATURE = REGISTRY.register("cinnabar_polypore_feature", () -> new CinnbarPolyporeMushroomFeature(BlockPileConfiguration.CODEC));





}

