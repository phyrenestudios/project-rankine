package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineConfiguredFeatures;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankinePlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates every datapack (dynamic) registry Rankine owns. In 1.21.1 enchantments, damage types,
 * configured/placed features and biome modifiers are all datapack registries, so they are emitted
 * as JSON here instead of being registered in code.
 */
public class RankineDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, RankineEnchantmentBootstrap::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, RankineConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, RankinePlacedFeatures::bootstrap)
            .add(Registries.DAMAGE_TYPE, RankineDamageSources::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, RankineBiomeModifiers::bootstrap);

    public RankineDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ProjectRankine.MODID));
    }

    @Override
    public String getName() {
        return "Project Rankine - Datapack Registries";
    }
}
