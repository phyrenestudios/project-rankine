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
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, ProjectRankine.MODID);

    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DEFAULT_ORE = REGISTRY.register("default_ore_feature", () -> new DefaultOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> SPHERE_ORE = REGISTRY.register("sphere_ore_feature", () -> new SphericalOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<RankineOreFeatureConfig>> DISK_ORE = REGISTRY.register("disk_ore_feature", () -> new DiskOreVeinFeature(RankineOreFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SphereReplaceConfig>> LAND_DISK = REGISTRY.register("land_disk_feature", () -> new LandDiskFeature(SphereReplaceConfig.CODEC));
    public static final RegistryObject<Feature<MeteoriteFeatureConfig>> METEORITE_FEATURE = REGISTRY.register("meteorite_feature", () -> new MeteoriteFeature(MeteoriteFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> END_METEORITE_FEATURE = REGISTRY.register("end_meteorite_feature", () -> new EndMeteoriteFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> ANTIMATTER_BLOB_FEATURE = REGISTRY.register("antimatter_blob_feature", () -> new AntimatterFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> FUMAROLE_FEATURE = REGISTRY.register("fumarole_feature", () -> new FumaroleFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> COLUMN_FEATURE = REGISTRY.register("column_feature", () -> new ColumnFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WALL_MUSHROOMS = REGISTRY.register("wall_mushrooms_feature", () -> new WallMushroomsFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<ReplacerFeatureConfig>> FLAT_BEDROCK_FEATURE = REGISTRY.register("flat_bedrock_feature", () -> new FlatBedrockFeature(ReplacerFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> INTRUSION = REGISTRY.register("intrusion_feature", () -> new IntrusionFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WORLD_REPLACER_FEATURE = REGISTRY.register("world_replacer_feature", () -> new WorldReplacerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> POST_WORLD_REPLACER_FEATURE = REGISTRY.register("post_world_replacer_feature", () -> new PostWorldReplacerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> COBBLE_PATCH = REGISTRY.register("cobble_patch_feature", () -> new CobblePatchFeature(BlockClusterFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> EASTERN_HEMLOCK_TREE = REGISTRY.register("eastern_hemlock_tree", () -> new EasternHemlockTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WESTERN_HEMLOCK_TREE = REGISTRY.register("western_hemlock_tree", () -> new WesternHemlockTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BALSAM_FIR_TREE = REGISTRY.register("balsam_fir_tree", () -> new BalsamFirTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> COCONUT_PALM_TREE = REGISTRY.register("coconut_palm_tree_feature", () -> new CoconutPalmTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BIRCH_TREE = REGISTRY.register("birch_tree_feature", () -> new BirchTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> PETRIFIED_CHORUS_TREE = REGISTRY.register("petrified_chorus_tree_feature", () -> new PetrifiedChorusTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAGNOLIA_TREE = REGISTRY.register("magnolia_tree_feature", () -> new MagnoliaTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> ERYTHRINA_TREE = REGISTRY.register("erythrina_tree_feature", () -> new ErythrinaTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BLACK_WALNUT_TREE = REGISTRY.register("black_walnut_tree_feature", () -> new BlackWalnutTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> RED_CEDAR_TREE = REGISTRY.register("red_cedar_tree_feature", () -> new RedCedarTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> JUNIPER_TREE = REGISTRY.register("juniper_tree_feature", () -> new JuniperTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> PINYON_PINE_TREE = REGISTRY.register("pinyon_pine_tree_feature", () -> new PinyonPineTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> WEEPING_WILLOW_TREE = REGISTRY.register("weeping_willow_tree_feature", () -> new WeepingWillowTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> HONEY_LOCUST_TREE = REGISTRY.register("honey_locust_tree_feature", () -> new HoneyLocustTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAPLE_TREE = REGISTRY.register("maple_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> SHARINGA_TREE = REGISTRY.register("sharinga_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CORK_OAK_TREE = REGISTRY.register("cork_oak_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> CINNAMON_TREE = REGISTRY.register("cinnamon_tree_feature", () -> new TreeFeature(BaseTreeFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> TINDER_CONK_FEATURE = REGISTRY.register("tinder_conk_feature", () -> new TinderConkMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> LIONS_MANE_FEATURE = REGISTRY.register("lions_mane_feature", () -> new LionsManeMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> TURKEY_TAIL_FEATURE = REGISTRY.register("turkey_tail_feature", () -> new TurkeyTailMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> SULFUR_SHELF_FEATURE = REGISTRY.register("sulfur_shelf_feature", () -> new SulfurShelfMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> HONEY_FEATURE = REGISTRY.register("honey_feature", () -> new HoneyMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> ARTIST_CONK_FEATURE = REGISTRY.register("artist_conk_feature", () -> new ArtistsConkMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> OYSTER_FEATURE = REGISTRY.register("oyster_feature", () -> new OysterMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> CINNABAR_POLYPORE_FEATURE = REGISTRY.register("cinnabar_polypore_feature", () -> new CinnbarPolyporeMushroomFeature(BlockStateProvidingFeatureConfig.CODEC));





}

