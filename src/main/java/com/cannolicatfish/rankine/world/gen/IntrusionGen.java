package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.world.biome.RankineBiomes;
import com.cannolicatfish.rankine.world.gen.feature.IntrusionFeature;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IntrusionGen {

    public static void addIntrusions() {
  //      intrusionGenDef(ModBlocks.KIMBERLITE, Collections.emptyList(), false, 0, 25, .05f);
  //      intrusionGenDef(ModBlocks.GRANITE, Collections.emptyList(), false, 0, 90, .2f);
  //      intrusionGenDef(ModBlocks.DIORITE, Collections.emptyList(), false, 51, 256, .1f);
        Random random = new Random();


        for (Biome b : ForgeRegistries.BIOMES) {

            // Kimberlite
            if (random.nextFloat() < 0.1F) {
                b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                        new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), ModBlocks.KIMBERLITE.getDefaultState(), 0, 25)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.1F, 0, 0, 25))));
            }

            // Diorite
            if (random.nextFloat() < 0.2F) {
                b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                        new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), ModBlocks.DIORITE.getDefaultState(), 46, 256)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.2F, 46, 0, 2256))));
            }

            // Granite
            if (random.nextFloat() < 0.1F) {
                b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                        new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), ModBlocks.GRANITE.getDefaultState(), 46, 90)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.1F, 46, 0, 90))));
            }
        }

    }













/*

    private static void intrusionGenDef(Block block, List<Biome.Category> biomes, boolean genType, int lowerBound, int upperBound, float chance) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (genType && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE) {
                if (biomes.contains(biome.getCategory())) // if biome is supposed to be included (reverse)
                {
        }
            }
            if (!genType && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE)
            {
                if (!biomes.contains(biome.getCategory())) // if biomes in biomesExcluded are not supposed to be included
                {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), block.getDefaultState(), lowerBound, upperBound)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, lowerBound, 0, upperBound))));
                }
            }
        }
    }
*/

}
