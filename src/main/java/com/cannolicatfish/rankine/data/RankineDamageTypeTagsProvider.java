package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Restores the 1.18.2 damage-source flags that are data-driven in 1.21.1:
 * {@code uranium_shot} bypassed armor, {@code suffocating} bypassed armor and magic.
 */
public class RankineDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public RankineDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Damage Type Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(RankineDamageSources.URANIUM_SHOT)
                .add(RankineDamageSources.SUFFOCATION);
        tag(DamageTypeTags.BYPASSES_EFFECTS)
                .add(RankineDamageSources.SUFFOCATION);
    }
}
