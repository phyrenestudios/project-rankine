package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

/**
 * NeoForge data maps.
 *
 * <p>Currently only {@code neoforge:compostables}
 * ({@code data/neoforge/data_maps/item/compostables.json}). 1.18.2 registered compostables in code
 * via {@code ComposterBlock.COMPOSTABLES}; NeoForge 1.21 replaced that map with this data map and
 * reads nothing else — {@code ComposterBlock#getValue} and {@code WorkAtComposter} both call
 * {@code Holder#getData(NeoForgeDataMaps.COMPOSTABLES)} — so without this provider every Rankine
 * leaf, sapling, seed and berry is silently non-compostable.
 *
 * <p>The entries come from {@link VanillaIntegration#compostables()}, the single shared list, so
 * the generated JSON cannot drift from the mod's own idea of what composts.
 */
public class RankineDataMapProvider extends DataMapProvider {

    public RankineDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public String getName() {
        return "Project Rankine - Data Maps";
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
        // canVillagerCompost = false for all of them: 1.18.2's WorkAtComposter had a hard-coded
        // COMPOSTABLE_ITEMS list (wheat/beetroot seeds) that no mod could extend, so farmer
        // villagers never composted Rankine items. 1.21 makes that per-entry data; keeping it
        // false is 1.18 parity. `replace = false` so a datapack can override individual entries.
        VanillaIntegration.compostables().forEach((item, chance) ->
                compostables.add(item.builtInRegistryHolder(), new Compostable(chance, false), false));
    }
}
