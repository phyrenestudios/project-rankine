package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.worldgen.biome_modifiers.ExampleBiomeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineBiomeModifierSerializers {
     public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ProjectRankine.MODID);

     public static RegistryObject<Codec<ExampleBiomeModifier>> EXAMPLE_CODEC = BIOME_MODIFIER_SERIALIZERS.register("example", () ->
                RecordCodecBuilder.create(builder -> builder.group(
                        // declare fields
                        Biome.LIST_CODEC.fieldOf("biomes").forGetter(ExampleBiomeModifier::biomes),
                        PlacedFeature.CODEC.fieldOf("feature").forGetter(ExampleBiomeModifier::feature)
                        // declare constructor
                ).apply(builder, ExampleBiomeModifier::new)));

}
