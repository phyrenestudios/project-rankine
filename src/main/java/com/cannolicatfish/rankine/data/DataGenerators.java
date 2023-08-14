package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.data.lang.RankineEnUsLangProvider;
import com.cannolicatfish.rankine.data.loot.RankineLootTableSubProvider;
import com.cannolicatfish.rankine.data.tags.RankineBiomeTagsProvider;
import com.cannolicatfish.rankine.data.tags.RankineBlockTagsProvider;
import com.cannolicatfish.rankine.data.tags.RankineItemTagsProvider;
import com.cannolicatfish.rankine.worldgen.features.RankineTreeFeatures;
import com.cannolicatfish.rankine.worldgen.features.RankineUndergroundFeatures;
import com.cannolicatfish.rankine.worldgen.placements.RankineTreePlacements;
import com.cannolicatfish.rankine.worldgen.placements.RankineUndergroundPlacements;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        gen.addProvider(true, new RankineBlockStateProvider(packOutput, helper));
        gen.addProvider(true, new RankineItemModelProvider(packOutput, helper));

        RankineBlockTagsProvider blockTags = new RankineBlockTagsProvider(packOutput, provider, helper);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new RankineItemTagsProvider(packOutput, provider, blockTags.contentsGetter(), helper));
        gen.addProvider(event.includeServer(), new RankineBiomeTagsProvider(packOutput, provider, helper));


        gen.addProvider(event.includeServer(), new RankineLootTableSubProvider(packOutput));
        gen.addProvider(event.includeClient(), new RankineEnUsLangProvider(packOutput, "en_us"));
        gen.addProvider(event.includeServer(), new RankineRecipesProvider(packOutput));

        gen.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, CompletableFuture.supplyAsync(DataGenerators::getProvider), Set.of(ProjectRankine.MODID)));

    }


    private static HolderLookup.Provider getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.CONFIGURED_FEATURE, context -> {
            RankineTreeFeatures.bootstrap(context);
            RankineUndergroundFeatures.bootstrap(context);
        });
        registryBuilder.add(Registries.PLACED_FEATURE, context -> {
            RankineTreePlacements.bootstrap(context);
            RankineUndergroundPlacements.bootstrap(context);
        });
        registryBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
            HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

            context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ProjectRankine.MODID, "overworld_stone_replacer")), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(RankineUndergroundPlacements.OVERWORLD_STONE_REPLACER)),
                    GenerationStep.Decoration.TOP_LAYER_MODIFICATION
            ));
            context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ProjectRankine.MODID, "honey_locust_tree")), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(RankineTreePlacements.PLACED_HONEY_LOCUST_TREE)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));
            context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ProjectRankine.MODID, "cedar_tree")), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(BiomeTags.IS_TAIGA),
                    HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(RankineTreePlacements.PLACED_CEDAR_TREE)),
                    GenerationStep.Decoration.VEGETAL_DECORATION
            ));

            context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ProjectRankine.MODID, "remove_vanilla_stones")), new ForgeBiomeModifiers.RemoveFeaturesBiomeModifier(
                    biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
                    HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_ANDESITE_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_ANDESITE_UPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_DIORITE_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_DIORITE_UPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_GRANITE_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_GRANITE_UPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_COAL_UPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_COAL_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_IRON_UPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_IRON_MIDDLE),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_IRON_SMALL),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_GOLD_EXTRA),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_GOLD),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_GOLD_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_REDSTONE),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_REDSTONE_LOWER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_DIAMOND),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_DIAMOND_LARGE),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_DIAMOND_BURIED),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_LAPIS),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_LAPIS_BURIED),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_EMERALD),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_COPPER),
                            context.lookup(Registries.PLACED_FEATURE).getOrThrow(OrePlacements.ORE_COPPER_LARGE)),
                    Collections.singleton(GenerationStep.Decoration.UNDERGROUND_ORES)
            ));

        });
        registryBuilder.add(Registries.BIOME, context -> {});
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
    }

}