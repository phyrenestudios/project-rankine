package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * 1.21: enchantments are data-driven registry entries. These are the ResourceKeys for every
 * Rankine enchantment; the actual definitions are registered by
 * {@link com.cannolicatfish.rankine.data.RankineEnchantmentBootstrap} via datagen.
 * Registry path names are IDENTICAL to 1.18.2 for world compatibility.
 */
public class RankineEnchantments {

    public static final ResourceKey<Enchantment> PUNCTURE = key("puncture");
    public static final ResourceKey<Enchantment> SWING = key("swing");
    public static final ResourceKey<Enchantment> DAZE = key("daze");
    public static final ResourceKey<Enchantment> ATOMIZE = key("atomize");
    public static final ResourceKey<Enchantment> EXCAVATE = key("excavate");
    public static final ResourceKey<Enchantment> IMPACT = key("impact");
    public static final ResourceKey<Enchantment> QUAKE = key("quake");
    public static final ResourceKey<Enchantment> FORAGING = key("foraging");
    public static final ResourceKey<Enchantment> LIGHTNING_ASPECT = key("lightning_aspect");
    public static final ResourceKey<Enchantment> ANTIQUATED = key("antiquated");
    public static final ResourceKey<Enchantment> CLEANSE = key("cleanse");

    public static final ResourceKey<Enchantment> DUNE_WALKER = key("dune_walker");
    public static final ResourceKey<Enchantment> SNOW_DRIFTER = key("snow_drifter");
    public static final ResourceKey<Enchantment> SPEED_SKATER = key("speed_skater");
    public static final ResourceKey<Enchantment> SWIFT_SWIMMER = key("swift_swimmer");
    public static final ResourceKey<Enchantment> GAS_PROTECTION = key("gas_protection");
    public static final ResourceKey<Enchantment> AQUA_LENSE = key("aqua_lense");

    public static final ResourceKey<Enchantment> ENDOBIOTIC = key("endobiotic");
    public static final ResourceKey<Enchantment> ENDOTOXIN = key("endotoxin");
    public static final ResourceKey<Enchantment> ENDPOINT = key("endpoint");
    public static final ResourceKey<Enchantment> ENDOSPORE = key("endospore");
    public static final ResourceKey<Enchantment> ENDURE = key("endure");
    public static final ResourceKey<Enchantment> ENDGAME = key("endgame");
    public static final ResourceKey<Enchantment> ENDOLITHIC = key("endolithic");
    public static final ResourceKey<Enchantment> ENDLESS = key("endless");
    public static final ResourceKey<Enchantment> ENDEAVOR = key("endeavor");
    public static final ResourceKey<Enchantment> ENDOTHERMIC = key("endothermic");
    public static final ResourceKey<Enchantment> ENDPLAY = key("endplay");

    public static final ResourceKey<Enchantment> GHASTLY_REGENERATION = key("ghastly_regeneration");
    public static final ResourceKey<Enchantment> WITHERING_CURSE = key("withering_curse");
    public static final ResourceKey<Enchantment> SHAPE_MEMORY = key("shape_memory");
    public static final ResourceKey<Enchantment> GUARD = key("guard");

    public static final ResourceKey<Enchantment> ACCURACY = key("accuracy");
    public static final ResourceKey<Enchantment> PREPARATION = key("preparation");
    public static final ResourceKey<Enchantment> POISON_ASPECT = key("poison_aspect");
    public static final ResourceKey<Enchantment> BACKSTAB = key("backstab");
    public static final ResourceKey<Enchantment> GRAFTING = key("grafting");
    public static final ResourceKey<Enchantment> RETALIATE = key("retaliate");
    public static final ResourceKey<Enchantment> RETREAT = key("retreat");

    public static final ResourceKey<Enchantment> LEVERAGE = key("leverage");
    public static final ResourceKey<Enchantment> TORQUE = key("torque");
    public static final ResourceKey<Enchantment> PRYING = key("prying");
    public static final ResourceKey<Enchantment> FULCRUM = key("fulcrum");
    public static final ResourceKey<Enchantment> RETRIEVAL = key("retrieval");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name));
    }
}
