package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RankineBiomeTagsProvider extends BiomeTagsProvider {

    public RankineBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Biome Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(RankineTags.Biomes.IS_BIRCH_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST);

        // Exactly 1.18.2's BiomeCategory.ICY. Listed as concrete biomes rather than as a union of
        // #c:is_snowy_plains and #c:is_icy on purpose: c:is_icy also contains frozen_peaks, which was
        // MOUNTAIN in 1.18.2 and which the earlier "mountain" config row already claims.
        // See RankineTags.Biomes.IS_ICY for the per-biome evidence.
        this.tag(RankineTags.Biomes.IS_ICY).add(Biomes.SNOWY_PLAINS).add(Biomes.ICE_SPIKES);

        // Exactly 1.18.2's BiomeCategory.BEACH. Listed as concrete biomes rather than as
        // #minecraft:is_beach plus stony_shore, because the vanilla tag is the thing that is wrong
        // here: it is [beach, snowy_beach] and omits stony_shore, which 1.18.2 called "beach".
        // See RankineTags.Biomes.IS_BEACH for the per-biome evidence.
        this.tag(RankineTags.Biomes.IS_BEACH).add(Biomes.BEACH).add(Biomes.SNOWY_BEACH).add(Biomes.STONY_SHORE);
    }
}
