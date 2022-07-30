package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.*;
import com.cannolicatfish.rankine.world.gen.mushrooms.*;
import com.cannolicatfish.rankine.world.gen.ores.DefaultOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.ores.DiskOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.ores.RankineOreFeatureConfig;
import com.cannolicatfish.rankine.world.gen.ores.SphericalOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.trees.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ProjectRankine.MODID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> RANKINE_SIMPLE_BLOCK = FEATURES.register("rankine_simple_block", () -> new RankineSimpleBlockFeature(SimpleBlockConfiguration.CODEC));

    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DEFAULT_ORE_VEIN = FEATURES.register("default_ore_vein", () -> new DefaultOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> SPHERICAL_ORE_VEIN = FEATURES.register("spherical_ore_vein", () -> new SphericalOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DISK_ORE_VEIN = FEATURES.register("disk_ore_vein", () -> new DiskOreVeinFeature(RankineOreFeatureConfig.CODEC));

    public static final RegistryObject<Feature<MeteoriteFeatureConfig>> METEORITE = FEATURES.register("meteorite", () -> new MeteoriteFeature(MeteoriteFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FUMAROLE = FEATURES.register("fumarole", () -> new FumaroleFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> INTRUSION = FEATURES.register("intrusion", () -> new IntrusionFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WALL_MUSHROOMS = FEATURES.register("wall_mushrooms_feature", () -> new WallMushroomsFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(SimpleBlockConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WORLD_REPLACER = FEATURES.register("world_replacer_feature", () -> new WorldReplacerFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> POST_WORLD_REPLACER = FEATURES.register("post_world_replacer_feature", () -> new PostWorldReplacerFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FLAT_BEDROCK_FEATURE = FEATURES.register("flat_bedrock", () -> new FlatBedrockFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_METEORITE_FEATURE = FEATURES.register("end_meteorite_feature", () -> new EndMeteoriteFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ANTIMATTER_BLOB_FEATURE = FEATURES.register("antimatter_blob_feature", () -> new AntimatterFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>> PATCH_COBBLES = FEATURES.register("patch_cobbles", () -> new CobblePatchFeature(RandomPatchConfiguration.CODEC));



    public static final RegistryObject<Feature<TreeConfiguration>> CEDAR_TREE = FEATURES.register("cedar_tree", () -> new CedarTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> EASTERN_HEMLOCK_TREE = FEATURES.register("eastern_hemlock_tree", () -> new EasternHemlockTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> WESTERN_HEMLOCK_TREE = FEATURES.register("western_hemlock_tree", () -> new WesternHemlockTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BALSAM_FIR_TREE = FEATURES.register("balsam_fir_tree", () -> new BalsamFirTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> COCONUT_PALM_TREE = FEATURES.register("coconut_palm_tree_feature", () -> new CoconutPalmTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BIRCH_TREE = FEATURES.register("birch_tree_feature", () -> new BirchTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> PETRIFIED_CHORUS_TREE = FEATURES.register("petrified_chorus_tree_feature", () -> new PetrifiedChorusTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> MAGNOLIA_TREE = FEATURES.register("magnolia_tree_feature", () -> new MagnoliaTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> ERYTHRINA_TREE = FEATURES.register("erythrina_tree_feature", () -> new ErythrinaTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> BLACK_WALNUT_TREE = FEATURES.register("black_walnut_tree_feature", () -> new BlackWalnutTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> JUNIPER_TREE = FEATURES.register("juniper_tree_feature", () -> new JuniperTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> PINYON_PINE_TREE = FEATURES.register("pinyon_pine_tree_feature", () -> new PinyonPineTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> WEEPING_WILLOW_TREE = FEATURES.register("weeping_willow_tree_feature", () -> new WeepingWillowTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> HONEY_LOCUST_TREE = FEATURES.register("honey_locust_tree_feature", () -> new HoneyLocustTreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> MAPLE_TREE = FEATURES.register("maple_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> SHARINGA_TREE = FEATURES.register("sharinga_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> CORK_OAK_TREE = FEATURES.register("cork_oak_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>> CINNAMON_TREE = FEATURES.register("cinnamon_tree_feature", () -> new TreeFeature(TreeConfiguration.CODEC));

    public static final RegistryObject<Feature<BlockPileConfiguration>> TINDER_CONK = FEATURES.register("tinder_conk_feature", () -> new TinderConkMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> LIONS_MANE = FEATURES.register("lions_mane_feature", () -> new LionsManeMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> TURKEY_TAIL = FEATURES.register("turkey_tail_feature", () -> new TurkeyTailMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> SULFUR_SHELF = FEATURES.register("sulfur_shelf_feature", () -> new SulfurShelfMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> HONEY = FEATURES.register("honey_feature", () -> new HoneyMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> ARTIST_CONK = FEATURES.register("artist_conk_feature", () -> new ArtistsConkMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> OYSTER = FEATURES.register("oyster_feature", () -> new OysterMushroomFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> CINNABAR_POLYPORE = FEATURES.register("cinnabar_polypore_feature", () -> new CinnbarPolyporeMushroomFeature(BlockPileConfiguration.CODEC));




}

