package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankinePOIs;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Point-of-interest type tags.
 *
 * <p>Without {@code minecraft:acquirable_job_site} no villager can ever take a Rankine profession:
 * an unemployed villager only looks for job sites matching
 * {@code VillagerProfession.ALL_ACQUIRABLE_JOBS}, which in 1.21 is exactly
 * {@code holder.is(PoiTypeTags.ACQUIRABLE_JOB_SITE)}. 1.18.2 had no such tag — a profession's POI
 * was acquirable purely by virtue of being registered — so this is the datapack file that has to
 * replace what registration used to do for free. All five professions' trades were dead code
 * without it.
 *
 * <p>Deliberately does NOT touch {@code minecraft:village}: vanilla's own {@code village.json} is
 * {@code ["#minecraft:acquirable_job_site", "minecraft:home", "minecraft:meeting"]}, so these POIs
 * join it transitively and adding them again would be a duplicate entry.
 *
 * <p>{@code super.addTags} is deliberately not called — it would copy the entire vanilla job-site
 * list into Rankine's tag file.
 */
public class RankinePoiTypeTagsProvider extends PoiTypeTagsProvider {

    public RankinePoiTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProjectRankine.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Project Rankine - Point of Interest Type Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .add(RankinePOIs.TEMPLATE_TABLE_POI.getKey())          // Metallurgist
                .add(RankinePOIs.MATERIAL_TESTING_TABLE_POI.getKey())  // Mineralogist
                .add(RankinePOIs.BOTANIST_STATION_POI.getKey())        // Botanist
                .add(RankinePOIs.GEM_CUTTER_POI.getKey())              // Gem Cutter
                .add(RankinePOIs.ROCK_COLLECTOR_POI.getKey());         // Rock Collector
    }
}
