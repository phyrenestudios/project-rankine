package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class RankineBiomeTagsProvider extends BiomeTagsProvider {

    public RankineBiomeTagsProvider(DataGenerator p_211094_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_211094_, modId, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Biome Tags";
    }

    @Override
    protected void addTags() {
        this.tag(RankineTags.Biomes.IS_END).add(Biomes.THE_END).add(Biomes.END_HIGHLANDS).add(Biomes.END_BARRENS).add(Biomes.END_MIDLANDS).add(Biomes.SMALL_END_ISLANDS);
        this.tag(RankineTags.Biomes.IS_SAVANNA).add(Biomes.SAVANNA).add(Biomes.SAVANNA_PLATEAU).add(Biomes.WINDSWEPT_SAVANNA);
        this.tag(RankineTags.Biomes.IS_PLAINS).add(Biomes.PLAINS).add(Biomes.SUNFLOWER_PLAINS);
        this.tag(RankineTags.Biomes.IS_BIRCH_FOREST).add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST);

    }


}
