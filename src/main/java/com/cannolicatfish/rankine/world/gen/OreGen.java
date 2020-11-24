package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModFeatures;
import com.cannolicatfish.rankine.world.gen.feature.*;
import com.cannolicatfish.rankine.world.gen.placement.IntrusionPlacement;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class OreGen {

    //public static final Feature<RankineOreFeatureConfig> RANKINE_ORE = new RankineOreFeature(RankineOreFeatureConfig.field_236566_a_);
    //public static final Feature<RankineMultiOreFeatureConfig> MULTI_RANKINE_ORE = new RankineMultiOreFeature(RankineMultiOreFeatureConfig.field_236566_a_);


    public static void setupOreGeneration() {
        /*
        flatBedrock();

        FlatReplaceGenDef(Blocks.SAND, ModBlocks.SALT_BLOCK, 50, 63, Arrays.asList(ModBiomes.SALT_PLAINS, ModBiomes.SALT_SPIKES));

        addCrystal();
        intrusionGenDef();
        alluviumGen();

        //Extras
        replaceGenDef(Blocks.STONE, Blocks.RED_SANDSTONE, 61, 80, getBiomesFromCategory(Collections.singletonList(Biome.Category.MESA), true));
        rockGenCountDef(Blocks.STONE.getDefaultState(), ModBlocks.SCORIA.getDefaultState(), 20, 6, 50, 128, Collections.singletonList(ModBiomes.CRACKED_CRUST));
        rockGenCountDef(Blocks.STONE.getDefaultState(), ModBlocks.PUMICE.getDefaultState(), 20, 5, 50, 128, Collections.singletonList(ModBiomes.CRACKED_CRUST));

        stoneGen(1, getBiomesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.MUSHROOM), true));
        stoneGen(2, getBiomesFromCategory(Collections.singletonList(Biome.Category.BEACH), true));
        stoneGen(3, Collections.singletonList(ModBiomes.CRACKED_CRUST));
        stoneGen(0, getBiomesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.BEACH, Biome.Category.MUSHROOM), false));
        netherStoneGen(getBiomesFromCategory(Collections.singletonList(Biome.Category.NETHER), true));

        rockGenCountDef(Blocks.NETHERRACK.getDefaultState(), ModBlocks.SCORIA.getDefaultState(), 40, 2, 0, 128, Collections.singletonList(Biomes.CRIMSON_FOREST));
        rockGenCountDef(Blocks.NETHERRACK.getDefaultState(), ModBlocks.PUMICE.getDefaultState(), 40, 2, 0, 128, Collections.singletonList(Biomes.CRIMSON_FOREST));
        rockGenCountDef(ModBlocks.LIMESTONE.getDefaultState(), ModBlocks.LIMESTONE_NODULE.getDefaultState(), 6, 20, 31, 70, getBiomesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.SWAMP), false));
        rockGenCountDef(Blocks.SANDSTONE.getDefaultState(), ModBlocks.IRONSTONE.getDefaultState(), 40, 4, 50, 128, getBiomesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), true));
        rockGenCountDef(Blocks.RED_SANDSTONE.getDefaultState(), ModBlocks.IRONSTONE.getDefaultState(), 40, 4, 50, 128, getBiomesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), true));
        rockGenCountDef(ModBlocks.IRONSTONE.getDefaultState(), ModBlocks.OPAL_ORE.getDefaultState().with(RankineOre.TYPE, 13), 8, 30, 50, 128, getBiomesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), true));
        rockGenCountDef(Blocks.DIRT.getDefaultState(), ModBlocks.PERMAFROST.getDefaultState(), 20, 1, 55, 70, getBiomesFromCategory(Collections.singletonList(Biome.Category.ICY), true));

        //tier native
        OWGenDefCount(ModBlocks.NATIVE_COPPER_ORE, 12, 4, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.NATIVE_TIN_ORE, 12, 4, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.NATIVE_LEAD_ORE, 12, 2, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.NATIVE_SILVER_ORE, 12, 2, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.NATIVE_ALUMINUM_ORE, 12, 2, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.NATIVE_GOLD_ORE, 10, 4, 15, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.STIBNITE_ORE, 10, 3, 51, 128, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        rockGenCountDef(ModBlocks.LIMESTONE.getDefaultState(), ModBlocks.HALITE_ORE.getDefaultState().with(RankineOre.TYPE, 10), 30, 2, 40, 70, getBiomesFromCategory(Arrays.asList(Biome.Category.BEACH, Biome.Category.OCEAN, Biome.Category.DESERT, Biome.Category.MESA), true));
        rockGenCountDef(ModBlocks.ANORTHOSITE.getDefaultState(), ModBlocks.PINK_HALITE_ORE.getDefaultState().with(RankineOre.TYPE, 12), 30, 2, 70, 128, getBiomesFromCategory(Collections.singletonList(Biome.Category.EXTREME_HILLS), true));


        //coals
        OWGenDefCount(ModBlocks.LIGNITE_ORE, 30, 1, 71, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SP);
        OWGenDefCount(ModBlocks.LIGNITE_ORE, 20, 3, 51, 71, RankineOreFeatureConfig.RankineFillerBlockType.NO_SP);
        OWGenDefCount(ModBlocks.SUBBITUMINOUS_ORE, 20, 3, 31, 50, RankineOreFeatureConfig.RankineFillerBlockType.NO_SP);
        OWGenDefCount(ModBlocks.BITUMINOUS_ORE, 20, 3, 0, 30, RankineOreFeatureConfig.RankineFillerBlockType.NO_SP);

        //tier 1
        OWGenDefCount(ModBlocks.MALACHITE_ORE, 20, 1, 31, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.CASSITERITE_ORE, 20, 1, 31, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.BAUXITE_ORE, 20, 1, 31, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.SPHALERITE_ORE, 20, 1, 31, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);

        //tier 2
        OWGenDefCount(ModBlocks.MAGNETITE_ORE, 30, 3, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NO_SP);
        OWGenDefCount(ModBlocks.MAGNESITE_ORE, 20, 2, 11, 70, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.PENTLANDITE_ORE, 20, 1, 11, 70, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        chunkMultiGenDef(ModBlocks.GALENA_ORE, 20, 1F, 11, 70, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP, 0.3f);
        OWGenDefCount(ModBlocks.ACANTHITE_ORE, 20, 1, 11, 70, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.PYROLUSITE_ORE, 20, 1, 11, 70, RankineOreFeatureConfig.RankineFillerBlockType.NO_SMP);
        OWGenDefCount(ModBlocks.CINNABAR_ORE, 20, 4, 20, 90, RankineOreFeatureConfig.RankineFillerBlockType.IGNEOUS);

        //tier 3
        OWGenDefChance(ModBlocks.ILMENITE_ORE, 5, 2, 0, 15, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        OWGenDefChance(ModBlocks.CHROMITE_ORE, 5, 2, 0, 15, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        OWGenDefChance(ModBlocks.WOLFRAMITE_ORE, 5, 2, 0, 15, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);

        //miac
        OWGenDefCount(ModBlocks.LAZURITE_ORE, 15, 2, 20, 50, RankineOreFeatureConfig.RankineFillerBlockType.NO_SHALE);
        OWGenDefCount(ModBlocks.EMERALD_ORE, 5, 1, 11, 30, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.AQUAMARINE_ORE, 5, 1, 11, 30, RankineOreFeatureConfig.RankineFillerBlockType.OVERWORLD);
        OWGenDefCount(ModBlocks.PLUMBAGO_ORE, 8, 3, 0, 20, RankineOreFeatureConfig.RankineFillerBlockType.MARBLE);

        //NETHER
        NetherGenDefCount(ModBlocks.NATIVE_ARSENIC_ORE, 6, 20, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.NATIVE_SULFUR_ORE, 6, 20, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.NATIVE_BISMUTH_ORE, 6, 20, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.NETHER_GOLD_ORE, 10, 15, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.QUARTZ_ORE, 20, 11, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.ANTHRACITE_ORE, 15, 8, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.COLUMBITE_ORE, 15, 4, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.TANTALITE_ORE, 15, 4, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.GREENOCKITE_ORE, 15, 4, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.ILMENITE_ORE, 20, 6, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.CHROMITE_ORE, 20, 6, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.WOLFRAMITE_ORE, 20, 6, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.MOISSANITE_ORE, 20, 4, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);
        NetherGenDefCount(ModBlocks.SPERRYLITE_ORE, 20, 8, 0, 128, RankineOreFeatureConfig.RankineFillerBlockType.NETHER);


        //END
        EndGenDefCount(ModBlocks.NATIVE_GALLIUM_ORE, 8, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.NATIVE_INDIUM_ORE, 8, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.NATIVE_TELLURIUM_ORE, 8, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.NATIVE_SELENIUM_ORE, 8, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.FLUORITE_ORE, 10, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.URANINITE_ORE, 14, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);
        EndGenDefCount(ModBlocks.XENOTIME_ORE, 8, 12, 0, 100, RankineOreFeatureConfig.RankineFillerBlockType.END);*/


    }

    //---------------------------------------------------------------------------------------------------------------------------------------------

    private static List<ResourceLocation> getBiomeNamesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<ResourceLocation> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome.getRegistryName());
                    }
                    if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                        b.add(biome.getRegistryName());
                    }
                }
            }
            else if (!include && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                b.add(biome.getRegistryName());
            }
        }
        return b;
    }
/*
    private static void stoneGen(int biomeType, List<Biome> biomes) {
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new StoneReplacerFeature(StoneReplacerFeatureConfig.field_236449_a_).withConfiguration(
                    new StoneReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, biomeType)).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    private static void netherStoneGen(List<Biome> biomes) {
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new NetherReplacerFeature(StoneReplacerFeatureConfig.field_236449_a_).withConfiguration(
                    new StoneReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.AIR.getDefaultState(), 0, 0)).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    private static void FlatReplaceGenDef(Block oldBlock, Block newBlock, int lowerBound, int upperBound, List<Biome> biomes) {
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new FlatReplacerFeature(ReplacerFeatureConfig.field_236449_a_).withConfiguration(
                    new ReplacerFeatureConfig(oldBlock.getDefaultState(), newBlock.getDefaultState(), lowerBound, upperBound)).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    private static void replaceGenDef(Block oldBlock, Block newBlock, int lowerBound, int upperBound, List<Biome> biomes) {
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ReplacerFeature(ReplacerFeatureConfig.field_236449_a_).withConfiguration(
                    new ReplacerFeatureConfig(oldBlock.getDefaultState(), newBlock.getDefaultState(), lowerBound, upperBound)).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    private static void OWGenDefChance(RankineOre block, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void OWGenDefCount(RankineOre block, int veinSize, int count, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(count, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void NetherGenDefChance(RankineOre block, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void NetherGenDefCount(RankineOre block, int veinSize, int count, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(count, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void EndGenDefChance(RankineOre block, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.THEEND) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void EndGenDefCount(RankineOre block, int veinSize, int count, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.THEEND) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RANKINE_ORE.withConfiguration(
                        new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(count, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void chunkMultiGenDef(RankineOre block, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type, float replaceChance) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MULTI_RANKINE_ORE.withConfiguration(
                        new RankineMultiOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize, replaceChance)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
            }
        }
    }

    private static void rockGenCountDef(BlockState oldBlock, BlockState newBlock, int veinSize, int count, int minHeight, int maxHeight, List<Biome> biomes) {
        final Feature<OreFeatureConfig> MODULE = new ModularOreFeature(OreFeatureConfig.field_236566_a_, oldBlock);
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MODULE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, newBlock, veinSize))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(count, minHeight, 0, maxHeight))));
        }
    }

    private static void rockGenChanceDef(BlockState oldBlock, BlockState newBlock, int veinSize, float chance, int minHeight, int maxHeight, List<Biome> biomes) {
        final Feature<OreFeatureConfig> MODULE = new ModularOreFeature(OreFeatureConfig.field_236566_a_, oldBlock);
        for (Biome b : biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MODULE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, newBlock, veinSize))
                    .withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
        }
    }


    // ---------------------------------------------------------------

    private static void intrusionGenDef() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionFeature(ReplacerFeatureConfig.field_236449_a_).withConfiguration(
                    new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 1, 90)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(1, 1, 0, 90))));
        }
    }

    private static void addCrystal() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.MESA || biome.getCategory() == Biome.Category.SAVANNA) {
                biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, new CrystalFeature(NoFeatureConfig.field_236558_a_).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(6))));
            }
        }
    }

    private static void alluviumGen() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == Biome.Category.OCEAN || biome.getCategory() == Biome.Category.RIVER) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(ModBlocks.ALLUVIUM.getDefaultState(), 4, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), ModBlocks.ALLUVIUM.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).withPlacement(Placement.COUNT_TOP_SOLID.configure(new FrequencyConfig(1))));
            }
        }
    }

    private static void flatBedrock() {
        if (Config.FLAT_BEDROCK.get()) {
            for (Biome b : ForgeRegistries.BIOMES) {
                if (b.getCategory() != Biome.Category.NETHER && b.getCategory() != Biome.Category.THEEND) {
                    b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new FlatBedrockFeature(ReplacerFeatureConfig.field_236449_a_).withConfiguration(
                            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.BEDROCK_LAYERS.get())).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
                }
                if (b.getCategory() == Biome.Category.NETHER) {
                    b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new FlatNetherBedrockFeature(ReplacerFeatureConfig.field_236449_a_).withConfiguration(
                            new ReplacerFeatureConfig(Blocks.NETHERRACK.getDefaultState(), Blocks.BEDROCK.getDefaultState(), 0, Config.BEDROCK_LAYERS.get())).withPlacement(new ReplacerPlacement(NoPlacementConfig.field_236555_a_).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
                }
            }
        }
    }*/

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> flatBedrock() {
        if (Config.FLAT_BEDROCK.get())
        {
            return Arrays.asList(
                    new AbstractMap.SimpleEntry<>(ModFeatures.FLAT_BEDROCK,
                            getBiomeNamesFromCategory(Collections.emptyList(),false)),
                    new AbstractMap.SimpleEntry<>(ModFeatures.FLAT_BEDROCK_NETHER,
                            getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)));
        } else
        {
            return Collections.emptyList();
        }
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getUndergroundOreFeatures() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ALLUVIUM,
                    getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.RIVER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_INTRUSION,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.OCEAN_STONE_GEN,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN,Biome.Category.MUSHROOM),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BEACH_STONE_GEN,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.BEACH),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.DEFAULT_STONE_GEN,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.OCEAN, Biome.Category.BEACH,Biome.Category.MUSHROOM),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NODULE,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.SWAMP), false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_IRONSTONE,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_IRONSTONE_RED,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_OPAL,
                        getBiomeNamesFromCategory(Arrays.asList(Biome.Category.DESERT, Biome.Category.MESA), false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_COPPER,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TIN,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_LEAD,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SILVER,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ALUMINUM,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GOLD,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_STIBNITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_HALITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PINK_HALITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LIGNITE_LOWER,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LIGNITE_UPPER,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SUBBITUMINOUS,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BITUMINOUS,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MALACHITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CASSITERITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_BAUXITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPHALERITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNETITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MAGNESITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PENTLANDITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GALENA_MULTI,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ACANTHITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PYROLUSITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CINNABAR,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ILMENITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CHROMITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_WOLFRAMITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_LAZURITE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_EMERALD,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_AQUAMARINE,
                        getBiomeNamesFromCategory(Collections.emptyList(),false)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PLUMBAGO,
                        getBiomeNamesFromCategory(Collections.emptyList(),false))
                );

    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getNetherOreFeatures() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(ModFeatures.NETHER_STONE_GEN,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SCORIA_NETHER,
                        Collections.singletonList(new ResourceLocation("minecraft:crimson_forest"))),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_PUMICE_NETHER,
                       Collections.singletonList(new ResourceLocation("minecraft:crimson_forest"))),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_ARSENIC_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SULFUR_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_BISMUTH_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ANTHRACITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_COLUMBITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_TANTALITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_GREENOCKITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_ILMENITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_CHROMITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_WOLFRAMITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_MOISSANITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_SPERRYLITE_NETHER,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.NETHER),true))
        );
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getEndOreFeatures() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_GALLIUM_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_INDIUM_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_TELLURIUM_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_NATIVE_SELENIUM_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_FLUORITE_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_URANINITE_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.ORE_XENOTIME_END,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.THEEND),true))
        );
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void removeFeatures(final BiomeLoadingEvent event) {
        if (event.getName() != null) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeIf(featureSupplier -> featureSupplier.toString().contains("net.minecraft.util.registry.WorldSettingsImport"));
            //System.out.println(event.getName() + ": " + (event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addOreGenFeatures(final BiomeLoadingEvent event)
    {
        if (event.getName() != null) {
            // TO-DO: Re-add vanilla features to proper biomes?
            if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND)
            {
                List<ConfiguredFeature<?,?>> vanillaFeatures = Arrays.asList(Features.ORE_DIRT,Features.ORE_GRAVEL,Features.DISK_CLAY,Features.DISK_GRAVEL,Features.DISK_SAND);

                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> bedrock = flatBedrock();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : bedrock)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }

                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> ores = getUndergroundOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : ores)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            }
            else if (event.getCategory() == Biome.Category.NETHER)
            {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> netherOreFeatures= getNetherOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : netherOreFeatures)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            } else {
                List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> endOreFeatures = getEndOreFeatures();
                for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : endOreFeatures)
                {
                    if (entry.getValue().contains(event.getName()))
                    {
                        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES.ordinal(),entry::getKey);
                    }
                }
            }




        }
    }
}

