package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.advancements.AlloyEnchantabilityPredicate;
import com.cannolicatfish.rankine.advancements.ExactCompositionPredicate;
import com.cannolicatfish.rankine.advancements.HarvestLevelPredicate;
import com.cannolicatfish.rankine.advancements.HasEnchantmentPredicate;
import com.cannolicatfish.rankine.advancements.IncludesCompositionPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Rankine's custom advancement predicates.
 *
 * <p>1.18.2 registered these via {@code ItemPredicate.register(...)} (see old
 * {@code RankineRecipes.registerPredicates()}, which must be deleted — PORT-TODO(init)).
 * In 1.21.1 {@code ItemPredicate} is a final record; custom item checks are
 * {@link ItemSubPredicate}s registered on {@code Registries.ITEM_SUB_PREDICATE_TYPE}.
 * The mod has no {@code SimpleCriterionTrigger}s, so nothing is registered on TRIGGER_TYPE.
 *
 * <p>Registry path names are IDENTICAL to the 1.18.2 predicate type ids, but note the JSON shape
 * changed with vanilla: they now appear under an item predicate's "predicates" map, e.g.
 * {@code "predicates": {"rankine:harvest_level_check": {"level": {"min": 2}}}}.
 */
public class RankineCriteriaTriggers {

    public static final DeferredRegister<ItemSubPredicate.Type<?>> ITEM_SUB_PREDICATE_TYPES =
            DeferredRegister.create(Registries.ITEM_SUB_PREDICATE_TYPE, ProjectRankine.MODID);

    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<HarvestLevelPredicate>> HARVEST_LEVEL_CHECK =
            ITEM_SUB_PREDICATE_TYPES.register("harvest_level_check", () -> new ItemSubPredicate.Type<>(HarvestLevelPredicate.CODEC));

    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<AlloyEnchantabilityPredicate>> ENCHANTABILITY_CHECK =
            ITEM_SUB_PREDICATE_TYPES.register("enchantability_check", () -> new ItemSubPredicate.Type<>(AlloyEnchantabilityPredicate.CODEC));

    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<ExactCompositionPredicate>> EXACT_COMPOSITION_CHECK =
            ITEM_SUB_PREDICATE_TYPES.register("exact_composition_check", () -> new ItemSubPredicate.Type<>(ExactCompositionPredicate.CODEC));

    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<IncludesCompositionPredicate>> INCLUDES_COMPOSITION_CHECK =
            ITEM_SUB_PREDICATE_TYPES.register("includes_composition_check", () -> new ItemSubPredicate.Type<>(IncludesCompositionPredicate.CODEC));

    public static final DeferredHolder<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<HasEnchantmentPredicate>> HAS_ENCHANTMENT_CHECK =
            ITEM_SUB_PREDICATE_TYPES.register("has_enchantment_check", () -> new ItemSubPredicate.Type<>(HasEnchantmentPredicate.CODEC));
}
