package com.cannolicatfish.rankine.data.tags;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class RankineBiomeTagsProvider extends BiomeTagsProvider {

    public RankineBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper fileHelper) {
        super(output, registries, ProjectRankine.MODID, fileHelper);
    }

    public String getName() {
        return "Project Rankine - Biome Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(RankineTags.Biomes.IS_BIRCH_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST);

    }


}
