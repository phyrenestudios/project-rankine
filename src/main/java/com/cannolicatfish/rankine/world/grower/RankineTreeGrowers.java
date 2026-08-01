package com.cannolicatfish.rankine.world.grower;

import com.cannolicatfish.rankine.init.RankineConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

/**
 * Static holder for Rankine's sapling growers.
 * <p>
 * PORT-NOTE: {@code AbstractTreeGrower} subclassing is gone in 1.21 — {@link TreeGrower} is a final
 * class that maps a name to configured-feature {@link net.minecraft.resources.ResourceKey}s and
 * self-registers into its static {@code GROWERS} map (used by {@code TreeGrower.CODEC}). The 19
 * former {@code *TreeGrower} classes are replaced by the constants below; each resolves the exact
 * same configured feature its 1.18.2 counterpart returned from {@code getConfiguredFeature}.
 * <p>
 * The grower name strings are the codec keys, so they must stay unique and stable — they are
 * prefixed with {@code rankine_} to avoid collisions with vanilla ("oak", "birch", ...).
 * <p>
 * Call sites: {@code RankineWood.newWoodReg(..., AbstractTreeGrower)} and
 * {@code RankineSaplingBlock(AbstractTreeGrower, ...)} must change their parameter type to
 * {@link TreeGrower} and pass e.g. {@code RankineTreeGrowers.CEDAR} instead of
 * {@code new CedarTreeGrower()} (see the report's cross-agent PORT-TODO for blocks).
 */
public class RankineTreeGrowers {

    public static final TreeGrower CEDAR = simple("rankine_cedar", RankineConfiguredFeatures.CONFIGURED_CEDAR_TREE);
    public static final TreeGrower BALSAM_FIR = simple("rankine_balsam_fir", RankineConfiguredFeatures.CONFIGURED_BALSAM_FIR_TREE);
    public static final TreeGrower EASTERN_HEMLOCK = simple("rankine_eastern_hemlock", RankineConfiguredFeatures.CONFIGURED_EASTERN_HEMLOCK_TREE);
    public static final TreeGrower WESTERN_HEMLOCK = simple("rankine_western_hemlock", RankineConfiguredFeatures.CONFIGURED_WESTERN_HEMLOCK_TREE);
    public static final TreeGrower PINYON_PINE = simple("rankine_pinyon_pine", RankineConfiguredFeatures.CONFIGURED_PINYON_PINE_TREE);
    public static final TreeGrower JUNIPER = simple("rankine_juniper", RankineConfiguredFeatures.CONFIGURED_JUNIPER_TREE);
    public static final TreeGrower BLACK_BIRCH = simple("rankine_black_birch", RankineConfiguredFeatures.CONFIGURED_BLACK_BIRCH_TREE);
    public static final TreeGrower YELLOW_BIRCH = simple("rankine_yellow_birch", RankineConfiguredFeatures.CONFIGURED_YELLOW_BIRCH_TREE);
    public static final TreeGrower RED_BIRCH = simple("rankine_red_birch", RankineConfiguredFeatures.CONFIGURED_RED_BIRCH_TREE);
    public static final TreeGrower MAPLE = simple("rankine_maple", RankineConfiguredFeatures.CONFIGURED_MAPLE_TREE);
    public static final TreeGrower MAGNOLIA = simple("rankine_magnolia", RankineConfiguredFeatures.CONFIGURED_MAGNOLIA_TREE);
    public static final TreeGrower BLACK_WALNUT = simple("rankine_black_walnut", RankineConfiguredFeatures.CONFIGURED_BLACK_WALNUT_TREE);
    public static final TreeGrower COCONUT_PALM = simple("rankine_coconut_palm", RankineConfiguredFeatures.CONFIGURED_COCONUT_PALM_TREE);
    public static final TreeGrower CORK_OAK = simple("rankine_cork_oak", RankineConfiguredFeatures.CONFIGURED_CORK_OAK_TREE);
    public static final TreeGrower SHARINGA = simple("rankine_sharinga", RankineConfiguredFeatures.CONFIGURED_SHARINGA_TREE);
    public static final TreeGrower CINNAMON = simple("rankine_cinnamon", RankineConfiguredFeatures.CONFIGURED_CINNAMON_TREE);
    public static final TreeGrower HONEY_LOCUST = simple("rankine_honey_locust", RankineConfiguredFeatures.CONFIGURED_HONEY_LOCUST_TREE);
    public static final TreeGrower WEEPING_WILLOW = simple("rankine_weeping_willow", RankineConfiguredFeatures.CONFIGURED_WEEPING_WILLOW_TREE);
    public static final TreeGrower ERYTHRINA = simple("rankine_erythrina", RankineConfiguredFeatures.CONFIGURED_ERYTHRINA_TREE);

    private static TreeGrower simple(String name, net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> tree) {
        return new TreeGrower(name, Optional.empty(), Optional.of(tree), Optional.empty());
    }

    /** Forces class initialization so the growers register themselves into {@code TreeGrower.GROWERS}. */
    public static void init() {}
}
