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
import java.util.stream.Stream;

/**
 * Enchantment tags: the rankine:exclusive_set/* sets (the 1.18.2 checkCompatibility overrides) plus
 * the vanilla availability memberships that replace the old {@code isTreasureOnly} /
 * {@code isDiscoverable} / {@code isTradeable} / {@code isAllowedOnBooks} flags.
 *
 * <p>The 1.18.2 → 1.21 mapping this class encodes, verified against baseline {@code 859f87773}:
 * <table>
 *   <tr><th>1.18.2</th><th>1.21 tag</th></tr>
 *   <tr><td>{@code isDiscoverable() && !isTreasureOnly()}</td><td>{@code in_enchanting_table}</td></tr>
 *   <tr><td>{@code isTradeable()}</td><td>{@code tradeable}</td></tr>
 *   <tr><td>{@code isTreasureOnly()}</td><td>{@code treasure} (drives {@code double_trade_price})</td></tr>
 *   <tr><td>{@code isDiscoverable()}</td><td>{@code on_random_loot}</td></tr>
 *   <tr><td>{@code isDiscoverable() && !isTreasureOnly()}</td><td>{@code on_mob_spawn_equipment}, {@code on_traded_equipment}</td></tr>
 *   <tr><td>{@code canApplyAtEnchantingTable(stack)}</td><td>not a tag: {@code isPrimaryItemFor(stack)}</td></tr>
 * </table>
 *
 * <p>The last row is why the table pool is two decisions, not one. 1.18.2's table filter was
 * {@code (!isTreasureOnly() || allowTreasure) && isDiscoverable() && canApplyAtEnchantingTable(stack)}
 * — Forge patched the stack-sensitive third term into {@code getAvailableEnchantmentResults}.
 * 1.21 splits those: {@code EnchantmentMenu#getEnchantmentList} builds the candidate stream from
 * {@code in_enchanting_table} (a per-ENCHANTMENT decision, this file) and
 * {@code getAvailableEnchantmentResults} then filters it with {@code stack::isPrimaryItemFor} (a
 * per-STACK decision, the item classes). An enchantment therefore belongs in the tag when 1.18.2
 * would have considered it at all, even if its per-stack half almost always said no.
 */
public class RankineEnchantmentTagsProvider extends EnchantmentTagsProvider {

    /**
     * The enchanting-table pool: the 1.18.2 enchantments that were discoverable, not treasure, and
     * whose {@code canApplyAtEnchantingTable} could return true for at least one stack.
     *
     * <p>{@code antiquated} is the only member whose per-stack half was ever restrictive: 1.18.2
     * let the table offer it on an {@code IAlloyTool} alloyed from {@code pewter_alloying} and on
     * nothing else. That half is not expressible as a tag — a tag keys on the item type, and the
     * alloy lives in a per-stack data component — so it lives where 1.21 puts per-stack decisions,
     * in {@code isPrimaryItemFor}, which every alloy tool routes through
     * {@code IAlloyTool#alloyGateAllows(stack, ench, true, …)}. Listing it here restores the half
     * this file owns; the alloy gate that was already written for the other half then becomes
     * reachable instead of dead code.
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
            RankineEnchantments.RETRIEVAL,
            RankineEnchantments.ANTIQUATED);

    /**
     * The six enchantments whose 1.18.2 class overrides {@code canApplyAtEnchantingTable(stack)} to
     * return {@code false} for every stack — the table could not offer them on anything, so no
     * per-stack hook can restore them and they have to stay out of the pool tag itself.
     *
     * <p>They are discoverable and non-treasure, so they keep their loot and trade memberships
     * below; the table is the only door 1.18.2 shut. This is the whole difference between them and
     * {@link #NON_TREASURE_ENCHANTMENTS}: {@code isDiscoverable()} is true for both groups.
     */
    private static final List<ResourceKey<Enchantment>> TABLE_BANNED_ENCHANTMENTS = List.of(
            RankineEnchantments.DUNE_WALKER,
            RankineEnchantments.SNOW_DRIFTER,
            RankineEnchantments.SPEED_SKATER,
            RankineEnchantments.SWIFT_SWIMMER,
            RankineEnchantments.GAS_PROTECTION,
            RankineEnchantments.AQUA_LENSE);

    /**
     * The 14 enchantments whose 1.18.2 class overrides {@code isDiscoverable()} to false: the 11
     * ender-amalgam enchantments plus Ghastly Regeneration, Shape Memory and Withering Curse.
     *
     * <p>1.18.2 read that flag in two places — {@code EnchantmentHelper#getAvailableEnchantmentResults}
     * (the enchanting table and {@code EnchantWithLevelsFunction}) and {@code EnchantRandomlyFunction}
     * — which 1.21 replaced with {@code in_enchanting_table} and {@code on_random_loot} /
     * {@code on_mob_spawn_equipment} / {@code on_traded_equipment} respectively. It was NOT read by
     * the villager book trade, which used {@code isTradeable()} only; that is why these stay
     * tradeable while being excluded from every random-selection tag.
     */
    private static final List<ResourceKey<Enchantment>> NOT_DISCOVERABLE = List.of(
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
            RankineEnchantments.SHAPE_MEMORY,
            RankineEnchantments.WITHERING_CURSE);

    /**
     * Everything kept out of {@code minecraft:non_treasure}, and so out of the table pool: the six
     * the table always refused plus the 14 it never considered. Those are the only two reasons
     * 1.18.2 had, so this is exactly their union — it is derived rather than restated so the two
     * cannot drift apart.
     *
     * <p>The name is deliberate: this is NOT {@code minecraft:treasure}. At the baseline not one of
     * the 44 classes overrides {@code isTreasureOnly()}, {@code isTradeable()} or
     * {@code isAllowedOnBooks()}, so every Rankine enchantment was non-treasure, tradeable and
     * book-legal. Calling these treasure would double their villager book price and — via vanilla's
     * {@code tradeable.json} being {@code ["#minecraft:non_treasure", …]} — bar them from trades.
     */
    private static final List<ResourceKey<Enchantment>> OFF_TABLE_ENCHANTMENTS =
            Stream.concat(TABLE_BANNED_ENCHANTMENTS.stream(), NOT_DISCOVERABLE.stream()).toList();

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
        // Vanilla's own copies of these tags are compositions, and datapack tags merge, so anything
        // put in non_treasure is transitively in in_enchanting_table, tradeable, on_random_loot,
        // on_mob_spawn_equipment and on_traded_equipment. The tags below are still written out in
        // full rather than relying on that: it keeps each file readable as the intended set and
        // survives Mojang re-composing the vanilla files.
        for (ResourceKey<Enchantment> key : NON_TREASURE_ENCHANTMENTS) {
            tag(EnchantmentTags.IN_ENCHANTING_TABLE).add(key);
            tag(EnchantmentTags.NON_TREASURE).add(key);
        }

        // minecraft:treasure is left empty on purpose — no Rankine enchantment overrode
        // isTreasureOnly() in 1.18.2, and treasure membership only drives double_trade_price.

        for (ResourceKey<Enchantment> key : allEnchantments()) {
            // isTradeable() was the base-class default (true) everywhere, including on the curse:
            // 1.18.2 librarians could sell a book of any Rankine enchantment.
            tag(EnchantmentTags.TRADEABLE).add(key);

            if (!NOT_DISCOVERABLE.contains(key)) {
                // 1.18.2 EnchantRandomlyFunction filtered on isDiscoverable() alone; the
                // EnchantWithLevelsFunction / mob-spawn / villager-gear paths additionally
                // required !isTreasureOnly(), which no Rankine enchantment was. Same set.
                tag(EnchantmentTags.ON_RANDOM_LOOT).add(key);
                tag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT).add(key);
                tag(EnchantmentTags.ON_TRADED_EQUIPMENT).add(key);
            }
        }

        tag(EnchantmentTags.CURSE).add(RankineEnchantments.WITHERING_CURSE);
    }

    /** Every Rankine enchantment, in a stable order. */
    private static List<ResourceKey<Enchantment>> allEnchantments() {
        return Stream.concat(NON_TREASURE_ENCHANTMENTS.stream(), OFF_TABLE_ENCHANTMENTS.stream()).toList();
    }
}
