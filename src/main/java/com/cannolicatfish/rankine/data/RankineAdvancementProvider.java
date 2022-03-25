package com.cannolicatfish.rankine.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;


public class RankineAdvancementProvider extends AdvancementProvider {

    public RankineAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    public String getName() {
        return "Project Rankine - Advancements";
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {



        super.registerAdvancements(consumer, fileHelper);
    }

}
