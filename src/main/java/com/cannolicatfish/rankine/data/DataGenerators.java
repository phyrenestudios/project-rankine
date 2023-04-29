package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.data.lang.RankineEnUsLangProvider;
import com.cannolicatfish.rankine.data.loot.RankineLootTableSubProvider;
import com.cannolicatfish.rankine.data.tags.RankineBiomeTagsProvider;
import com.cannolicatfish.rankine.data.tags.RankineBlockTagsProvider;
import com.cannolicatfish.rankine.data.tags.RankineItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
    }
}