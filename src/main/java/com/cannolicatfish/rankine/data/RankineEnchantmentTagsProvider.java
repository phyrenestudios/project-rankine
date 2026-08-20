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
 * 1.21 splits those: {@code EnchantmentMenu#getEnchantmentList} (EnchantmentMenu.java:201) builds
 * the candidate stream from {@code in_enchanting_table} (a per-ENCHANTMENT decision, this file) and
 * {@code getAvailableEnchantmentResults} then filters it with {@code stack::isPrimaryItemFor}
 * (EnchantmentHelper.java:608 — a per-STACK decision, the enchantment definition and the item
 * classes). An enchantment therefore belongs in the tag when 1.18.2 would have <em>considered</em>
 * it at all, even when its per-stack half never said yes: the pool tag is about discoverability,
 * not applicability, and putting an applicability rule in it makes that rule invisible to the
 * loot, mob-gear and trade-gear paths, which run the same per-stack filter.
 */
public class RankineEnchantmentTagsProvider extends EnchantmentTagsProvider {

    /**
     * The 24 whose 1.18.2 {@code canApplyAtEnchantingTable} could return true for at least one
     * stack, so the table really could offer them on something.
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
    private static final List<ResourceKey<Enchantment>> TABLE_OFFERABLE_ENCHANTMENTS = List.of(
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
     * The six whose 1.18.2 class overrides {@code canApplyAtEnchantingTable(stack)} to return
     * {@code false} for every stack. {@code isDiscoverable()} is true for them exactly as it is for
     * {@link #TABLE_OFFERABLE_ENCHANTMENTS}, so 1.18.2 <em>considered</em> them at the table and in
     * every random-selection path, and they belong in the same tags — the difference is entirely in
     * the per-stack half, which is not a tag decision.
     *
     * <p>That half is carried by an empty {@code primary_items} on their definitions
     * ({@code RankineEnchantmentBootstrap#NO_PRIMARY_ITEMS}), which is the literal 1.21 spelling of
     * the override: {@code isPrimaryItemFor} is
     * {@code primaryItems.isEmpty() || stack.is(primaryItems.get())}, and a present-but-empty set
     * fails for every stack. Keeping the ban here instead — by withholding tag membership — would
     * have been the same outcome at the table but a <em>stronger</em> one everywhere else, because
     * {@code minecraft:tradeable} is composed of {@code #minecraft:non_treasure} and the villager
     * book trade never consulted {@code canApplyAtEnchantingTable} at all.
     *
     * <p>Listing them also restores one thing tag exclusion could not: 1.18.2's table offered any
     * discoverable non-treasure enchantment on a plain {@code minecraft:book} through a separate
     * {@code flag && isAllowedOnBooks()} branch (no Rankine class overrode {@code isAllowedOnBooks}),
     * and 1.21 keeps that special case ahead of the {@code primary_items} test in
     * {@code IItemExtension#isPrimaryItemFor} (IItemExtension.java:454). Out of the pool tag, the
     * book branch was unreachable; in it, books work again and armour still does not.
     */
    private static final List<ResourceKey<Enchantment>> EMPTY_PRIMARY_ITEM_ENCHANTMENTS = List.of(
            RankineEnchantments.DUNE_WALKER,
            RankineEnchantments.SNOW_DRIFTER,
            RankineEnchantments.SPEED_SKATER,
            RankineEnchantments.SWIFT_SWIMMER,
            RankineEnchantments.GAS_PROTECTION,
            RankineEnchantments.AQUA_LENSE);

    /**
     * The 30 that were discoverable and not treasure in 1.18.2, i.e. everything the table pool and
     * the three random-selection tags should contain. Derived rather than restated so the two
     * halves above cannot drift out of it.
     */
    private static final List<ResourceKey<Enchantment>> NON_TREASURE_ENCHANTMENTS =
            Stream.concat(TABLE_OFFERABLE_ENCHANTMENTS.stream(), EMPTY_PRIMARY_ITEM_ENCHANTMENTS.stream()).toList();

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

        // minecraft:treasure is left empty on purpose — at the baseline not one of the 44 classes
        // overrides isTreasureOnly(), isTradeable() or isAllowedOnBooks(), so every Rankine
        // enchantment was non-treasure, tradeable and book-legal. Calling any of them treasure would
        // double its villager book price and — via vanilla's tradeable.json being
        // ["#minecraft:non_treasure", …] — bar it from trades outright.

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

    /** All 44 Rankine enchantments (30 + 14), in a stable order. */
    private static List<ResourceKey<Enchantment>> allEnchantments() {
        return Stream.concat(NON_TREASURE_ENCHANTMENTS.stream(), NOT_DISCOVERABLE.stream()).toList();
    }
}
