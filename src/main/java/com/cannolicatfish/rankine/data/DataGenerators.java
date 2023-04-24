package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.data.lang.RankineEnUsLangProvider;
import com.cannolicatfish.rankine.data.loot.RankineLootTableSubProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
            //RankineBlockTagsProvider blockTagsGeneration = new RankineBlockTagsProvider(gen, event.getExistingFileHelper());
            /*gen.addProvider(blockTagsGeneration);
            gen.addProvider(new RankineItemTagsProvider(gen, blockTagsGeneration, event.getExistingFileHelper()));
            gen.addProvider(new RankineBiomeTagsProvider(gen, ProjectRankine.MODID, event.getExistingFileHelper()));
            gen.addProvider(new RankineEntityTypeTagsProvider(gen, event.getExistingFileHelper()));
            gen.addProvider(new RankineAdvancementProvider(gen));
            gen.addProvider(new RankineRecipesProvider(gen));
            /*


             */
        gen.addProvider(true, new RankineBlockStateProvider(packOutput, event.getExistingFileHelper()));
        gen.addProvider(true, new RankineItemModelProvider(packOutput, event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new RankineLootTableSubProvider(packOutput));
        gen.addProvider(event.includeClient(), new RankineEnUsLangProvider(packOutput, "en_us"));
    }
}