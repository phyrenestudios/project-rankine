package com.cannolicatfish.rankine.data;
import com.cannolicatfish.rankine.data.loot.RankineLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.data.client.RankineBlockStateProvider;
import com.cannolicatfish.rankine.data.client.RankineItemModelProvider;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new RankineBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new RankineItemModelProvider(gen, existingFileHelper));
        //gen.addProvider(new RankineLootTableProvider(gen));

    }
}