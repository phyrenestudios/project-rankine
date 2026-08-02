package com.cannolicatfish.rankine.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 1.21.1 loot table provider. The actual tables live in {@link RankineBlockLootTables}, which is a
 * {@code BlockLootSubProvider} now; loot tables are keyed by {@code ResourceKey<LootTable>}.
 */
public class RankineLootTableProvider extends LootTableProvider {

    public RankineLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output,
                Set.<ResourceKey<LootTable>>of(),
                List.of(new SubProviderEntry(RankineBlockLootTables::new, LootContextParamSets.BLOCK)),
                registries);
    }
}
