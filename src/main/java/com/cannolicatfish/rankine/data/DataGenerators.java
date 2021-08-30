package com.cannolicatfish.rankine.data;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import com.cannolicatfish.rankine.ProjectRankine;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeServer()) {
            RankineBlockTagsProvider blockTagsGeneration = new RankineBlockTagsProvider(gen, event.getExistingFileHelper());
            gen.addProvider(blockTagsGeneration);
            gen.addProvider(new RankineItemTagsProvider(gen, blockTagsGeneration, event.getExistingFileHelper()));

            gen.addProvider(new RankineBlockLootTables(gen));
            gen.addProvider(new RankineRecipesProvider(gen));

        }
        if (event.includeClient()) {
            gen.addProvider(new RankineBlockStateProvider(gen, event.getExistingFileHelper()));
            //gen.addProvider(new LangGen(gen));
            gen.addProvider(new RankineItemModelProvider(gen, event.getExistingFileHelper()));
        }
    }
}