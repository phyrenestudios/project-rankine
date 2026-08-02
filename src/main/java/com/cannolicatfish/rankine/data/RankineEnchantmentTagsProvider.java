package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentTags;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Enchantment tags: the rankine:exclusive_set/* sets (the 1.18.2 checkCompatibility overrides) plus
 * the vanilla in_enchanting_table / non_treasure / tradeable / treasure / curse memberships that
 * replace the old {@code isTreasureOnly}/{@code isAllowedOnBooks}/{@code isTradeable} flags.
 */
public class RankineEnchantmentTagsProvider extends EnchantmentTagsProvider {

    /**
     * Obtainable in the enchanting table, from villagers, and on loot books: the old
     * non-treasure Rankine enchantments. {@code antiquated} is deliberately excluded.
     */
    private static final List<ResourceKey<Enchantment>> NON_TREASURE_ENCHANTMENTS = List.of(
            RankineEnchantments.PUNCTURE,
            RankineEnchantments.SWING,
            RankineEnchantments.DAZE,
            RankineEnchantments.ATOMIZE,
            RankineEnchantments.EXCAVATE,
            RankineEnchantments.IMPACT,
            RankineEnchantments.QUAKE,
            RankineEnchantments.FORAGING,
            RankineEnchantments.LIGHTNING_ASPECT,
            RankineEnchantments.CLEANSE,
            RankineEnchantments.GUARD,
            RankineEnchantments.ACCURACY,
            RankineEnchantments.PREPARATION,
            RankineEnchantments.POISON_ASPECT,
            RankineEnchantments.BACKSTAB,
            RankineEnchantments.GRAFTING,
            RankineEnchantments.RETALIATE,
            RankineEnchantments.RETREAT,
            RankineEnchantments.LEVERAGE,
            RankineEnchantments.TORQUE,
            RankineEnchantments.PRYING,
            RankineEnchantments.FULCRUM,
            RankineEnchantments.RETRIEVAL);

    /** Treasure-only enchantments (never in the enchanting table). */
    private static final List<ResourceKey<Enchantment>> TREASURE_ENCHANTMENTS = List.of(
            RankineEnchantments.DUNE_WALKER,
            RankineEnchantments.SNOW_DRIFTER,
            RankineEnchantments.SPEED_SKATER,
            RankineEnchantments.SWIFT_SWIMMER,
            RankineEnchantments.GAS_PROTECTION,
            RankineEnchantments.AQUA_LENSE,
            RankineEnchantments.ENDOBIOTIC,
            RankineEnchantments.ENDOTOXIN,
            RankineEnchantments.ENDPOINT,
            RankineEnchantments.ENDOSPORE,
            RankineEnchantments.ENDURE,
            RankineEnchantments.ENDGAME,
            RankineEnchantments.ENDOLITHIC,
            RankineEnchantments.ENDLESS,
            RankineEnchantments.ENDEAVOR,
            RankineEnchantments.ENDOTHERMIC,
            RankineEnchantments.ENDPLAY,
            RankineEnchantments.GHASTLY_REGENERATION,
            RankineEnchantments.WITHERING_CURSE,
            RankineEnchantments.SHAPE_MEMORY,
            RankineEnchantments.ANTIQUATED);

    public RankineEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ProjectRankine.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Project Rankine - Enchantment Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ---- exclusivity sets (old Enchantment#checkCompatibility) ----
        tag(RankineEnchantmentTags.ATOMIZE_EXCLUSIVE).add(Enchantments.FORTUNE);
        tag(RankineEnchantmentTags.QUAKE_EXCLUSIVE).add(Enchantments.EFFICIENCY);
        tag(RankineEnchantmentTags.ENDPOINT_EXCLUSIVE).add(Enchantments.LOYALTY);
        tag(RankineEnchantmentTags.STANCE_EXCLUSIVE)
                .add(RankineEnchantments.RETALIATE)
                .add(RankineEnchantments.RETREAT);

        // ---- vanilla availability tags ----
        for (ResourceKey<Enchantment> key : NON_TREASURE_ENCHANTMENTS) {
            tag(EnchantmentTags.IN_ENCHANTING_TABLE).add(key);
            tag(EnchantmentTags.NON_TREASURE).add(key);
            tag(EnchantmentTags.TRADEABLE).add(key);
        }

        for (ResourceKey<Enchantment> key : TREASURE_ENCHANTMENTS) {
            tag(EnchantmentTags.TREASURE).add(key);
        }

        tag(EnchantmentTags.CURSE).add(RankineEnchantments.WITHERING_CURSE);
    }
}
