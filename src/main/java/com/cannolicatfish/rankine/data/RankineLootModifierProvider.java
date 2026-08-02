package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class RankineLootModifierProvider extends GlobalLootModifierProvider {

    public RankineLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ProjectRankine.MODID);
    }

    @Override
    public String getName() {
        return "Project Rankine - Global Loot Modifiers";
    }

    @Override
    protected void start() {
        // 1.18.2 shipped no datagen-generated global loot modifiers; the surf-rod modifier is
        // registered from a handwritten JSON. Kept as an extension point.
    }
}
