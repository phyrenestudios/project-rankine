package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class RankineEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public RankineEntityTypeTagsProvider(DataGenerator p_i50784_1_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i50784_1_, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Entity Type Tags";
    }

    @Override
    protected void addTags() {
        tag(EntityTypeTags.ARROWS).add(RankineEntityTypes.ALLOY_ARROW,RankineEntityTypes.ROPE_COIL_ARROW);

    }


}
