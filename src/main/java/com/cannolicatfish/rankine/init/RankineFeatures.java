package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.feature.ores.DefaultOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.DiskOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.ores.RankineOreFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.ores.SphericalOreVeinFeature;
import com.cannolicatfish.rankine.world.gen.feature.trees.BalsamFirTreeFeature;
import com.cannolicatfish.rankine.world.gen.feature.trees.CoconutPalmTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
    public static final RegistryObject<Feature<ReplacerFeatureConfig>> FLAT_BEDROCK_FEATURE = REGISTRY.register("flat_bedrock_feature", () -> new FlatBedrockFeature(ReplacerFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> INTRUSION = REGISTRY.register("intrusion_feature", () -> new IntrusionFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> WORLD_REPLACER_FEATURE = REGISTRY.register("world_replacer_feature", () -> new WorldReplacerFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> SNOW_REPLACER = REGISTRY.register("snow_replacer_feature", () -> new SnowyPeaksFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TEST_TREE = REGISTRY.register("test_tree", () -> new BalsamFirTreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BaseTreeFeatureConfig>> COCONUT_PALM_TREE = REGISTRY.register("coconut_palm_tree_feature", () -> new CoconutPalmTreeFeature(BaseTreeFeatureConfig.CODEC));




}

