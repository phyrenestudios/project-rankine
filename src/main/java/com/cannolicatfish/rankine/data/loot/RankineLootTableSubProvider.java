package com.cannolicatfish.rankine.data.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class RankineLootTableSubProvider extends LootTableProvider {
    public RankineLootTableSubProvider(PackOutput packOutput) {
        super(packOutput, Collections.emptySet(), VanillaLootTableProvider.create(packOutput).getTables());
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new SubProviderEntry(RankineBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                //other loot sub providers (fishing, entities)
        );
    }
/*
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationtracker, p_218436_2_, p_218436_3_));
    }

 */
}


