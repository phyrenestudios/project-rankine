package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        boolean server = event.includeServer();
        boolean client = event.includeClient();

        // Datapack registries first: everything downstream (tags on enchantments/damage types,
        // loot tables, advancements) needs to see the modded dynamic-registry entries.
        RankineDatapackProvider datapackProvider =
                gen.addProvider(server, new RankineDatapackProvider(output, event.getLookupProvider()));
        CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();

        RankineBlockTagsProvider blockTags =
                gen.addProvider(server, new RankineBlockTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        gen.addProvider(server, new RankineFluidTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineBiomeTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineEntityTypeTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineEnchantmentTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineDamageTypeTagsProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankinePoiTypeTagsProvider(output, lookupProvider, existingFileHelper));

        // Registration that used to be plain code in 1.18 and is a datapack file in 1.21:
        // neoforge:compostables (was ComposterBlock.COMPOSTABLES).
        gen.addProvider(server, new RankineDataMapProvider(output, lookupProvider));

        gen.addProvider(server, new RankineLootTableProvider(output, lookupProvider));
        gen.addProvider(server, new RankineAdvancementProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(server, new RankineRecipesProvider(output, lookupProvider));
        gen.addProvider(server, new RankineLootModifierProvider(output, lookupProvider));

        gen.addProvider(client, new RankineBlockStateProvider(output, existingFileHelper));
        gen.addProvider(client, new RankineItemModelProvider(output, existingFileHelper));
        gen.addProvider(client, new RankineLangProvider(output, "en_us"));
    }
}
