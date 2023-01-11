package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class RankineLootModifierProvider extends GlobalLootModifierProvider {
    public RankineLootModifierProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn, ProjectRankine.MODID);
    }

    public String getName() {
        return "Project Rankine - Global Loot Modifiers";
    }

    @Override
    protected void start() {

    }
}
