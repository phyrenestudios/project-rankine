package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.feature.mushrooms.*;
import com.cannolicatfish.rankine.world.gen.feature.ores.DefaultOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.DiskOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.RankineOreFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ores.SphericalOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.trees.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ProjectRankine.MODID);

    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DEFAULT_ORE = FEATURES.register("default_ore_feature", () -> new DefaultOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> SPHERE_ORE = FEATURES.register("sphere_ore_feature", () -> new SphericalOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DISK_ORE = FEATURES.register("disk_ore_feature", () -> new DiskOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SphereReplaceConfig>> LAND_DISK = FEATURES.register("land_disk_feature", () -> new LandDiskFeature(SphereReplaceConfig.CODEC));
    public static final RegistryObject<Feature<MeteoriteFeatureConfig>> METEORITE_FEATURE = FEATURES.register("meteorite_feature", () -> new MeteoriteFeature(MeteoriteFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> END_METEORITE_FEATURE = FEATURES.register("end_meteorite_feature", () -> new EndMeteoriteFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> ANTIMATTER_BLOB_FEATURE = FEATURES.register("antimatter_blob_feature", () -> new AntimatterFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> FUMAROLE_FEATURE = FEATURES.register("fumarole_feature", () -> new FumaroleFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> COLUMN_FEATURE = FEATURES.register("column_feature", () -> new ColumnFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WALL_MUSHROOMS = FEATURES.register("wall_mushrooms_feature", () -> new WallMushroomsFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<ReplacerFeatureConfig>> FLAT_BEDROCK_FEATURE = FEATURES.register("flat_bedrock_feature", () -> new FlatBedrockFeature(ReplacerFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> INTRUSION = FEATURES.register("intrusion_feature", () -> new IntrusionFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WORLD_REPLACER_FEATURE = FEATURES.register("world_replacer_feature", () -> new WorldReplacerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> POST_WORLD_REPLACER_FEATURE = FEATURES.register("post_world_replacer_feature", () -> new PostWorldReplacerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> COBBLE_PATCH = FEATURES.register("cobble_patch_feature", () -> new CobblePatchFeature(BlockClusterFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> EASTERN_HEMLOCK_TREE = FEATURES.register("eastern_hemlock_tree", () -> new EasternHemlockTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WESTERN_HEMLOCK_TREE = FEATURES.register("western_hemlock_tree", () -> new WesternHemlockTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BALSAM_FIR_TREE = FEATURES.register("balsam_fir_tree", () -> new BalsamFirTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> COCONUT_PALM_TREE = FEATURES.register("coconut_palm_tree_feature", () -> new CoconutPalmTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BIRCH_TREE = FEATURES.register("birch_tree_feature", () -> new BirchTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> PETRIFIED_CHORUS_TREE = FEATURES.register("petrified_chorus_tree_feature", () -> new PetrifiedChorusTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAGNOLIA_TREE = FEATURES.register("magnolia_tree_feature", () -> new MagnoliaTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> ERYTHRINA_TREE = FEATURES.register("erythrina_tree_feature", () -> new ErythrinaTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BLACK_WALNUT_TREE = FEATURES.register("black_walnut_tree_feature", () -> new BlackWalnutTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> RED_CEDAR_TREE = FEATURES.register("red_cedar_tree_feature", () -> new RedCedarTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> JUNIPER_TREE = FEATURES.register("juniper_tree_feature", () -> new JuniperTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> PINYON_PINE_TREE = FEATURES.register("pinyon_pine_tree_feature", () -> new PinyonPineTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WEEPING_WILLOW_TREE = FEATURES.register("weeping_willow_tree_feature", () -> new WeepingWillowTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> HONEY_LOCUST_TREE = FEATURES.register("honey_locust_tree_feature", () -> new HoneyLocustTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAPLE_TREE = FEATURES.register("maple_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> SHARINGA_TREE = FEATURES.register("sharinga_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CORK_OAK_TREE = FEATURES.register("cork_oak_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CINNAMON_TREE = FEATURES.register("cinnamon_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> TINDER_CONK_FEATURE = FEATURES.register("tinder_conk_feature", () -> new TinderConkMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> LIONS_MANE_FEATURE = FEATURES.register("lions_mane_feature", () -> new LionsManeMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> TURKEY_TAIL_FEATURE = FEATURES.register("turkey_tail_feature", () -> new TurkeyTailMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> SULFUR_SHELF_FEATURE = FEATURES.register("sulfur_shelf_feature", () -> new SulfurShelfMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> HONEY_FEATURE = FEATURES.register("honey_feature", () -> new HoneyMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> ARTIST_CONK_FEATURE = FEATURES.register("artist_conk_feature", () -> new ArtistsConkMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> OYSTER_FEATURE = FEATURES.register("oyster_feature", () -> new OysterMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> CINNABAR_POLYPORE_FEATURE = FEATURES.register("cinnabar_polypore_feature", () -> new CinnbarPolyporeMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));





}

