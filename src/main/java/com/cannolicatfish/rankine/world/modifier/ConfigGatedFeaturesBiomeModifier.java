package com.cannolicatfish.rankine.world.modifier;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBiomeModifierTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Optional;

/**
 * Like {@code neoforge:add_features}, but only applies when the named Rankine common-config gate
 * is enabled. In 1.18.2 all of these additions lived in {@code FeatureGeneration#addFeaturesToBiomes}
 * behind {@code if (Config.WORLDGEN.X.get())} checks; biome modifier JSON is static, so the gate
 * check has to move into the modifier itself to keep the config meaningful.
 * <p>
 * PORT-NOTE: {@code biomes} is optional, and an absent field means <em>every</em> biome. Three of
 * 1.18.2's additions (flat bedrock, world replacer, intrusions) were made to every biome the game
 * loaded — {@code addFeaturesToBiomes} guarded them on nothing but {@code biome.getName() != null}
 * and the config value — so they applied in modded dimensions too (the config ships 20 Twilight
 * Forest biomeSettings rows). No tag can name "every biome, including ones from mods that are not
 * installed yet", but {@code BiomeModifier#modify} is invoked for every biome, so an absent biome
 * set expresses it exactly.
 * <p>
 * F34 (batch 4, decision 7A): {@code exclude} is a list of biome sets a matching biome must be in
 * NONE of — a biome is skipped if it is absent from {@code biomes} (when given), OR present in ANY
 * set in {@code exclude}. This is how {@code meteorite}/{@code fumarole_overworld}/
 * {@code wall_mushrooms}/{@code patch_cobbles} reproduce 1.18.2's {@code isOverworld} —
 * {@code !NETHER && !THEEND} — for modded dimensions: {@code biomes = Optional.empty()} (every
 * biome) plus {@code exclude = [#minecraft:is_nether, #minecraft:is_end]}. A single combined tag
 * cannot do this instead, because Minecraft tags can only union memberships, never negate one:
 * there is no tag file that means "not nether and not end", so {@code #c:is_overworld} (NeoForge's
 * own tag) is necessarily an explicit opt-in list — vanilla biomes NeoForge's own datagen chose to
 * include — and can never automatically reach a biome from a mod that has not opted in. Each entry
 * of {@code exclude} decodes independently through {@code HolderSetCodec} (see
 * {@code RegistryCodecs#homogeneousList}), so a tag reference (the normal case) stays LIVE: it is
 * re-resolved against whatever is actually installed wherever the shipped JSON is loaded, not
 * baked to the ID list this mod's own {@code runData} happened to see.
 */
public record ConfigGatedFeaturesBiomeModifier(Optional<HolderSet<Biome>> biomes, List<HolderSet<Biome>> exclude,
        HolderSet<PlacedFeature> features, GenerationStep.Decoration step, String gate) implements BiomeModifier {

    public static final MapCodec<ConfigGatedFeaturesBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(ConfigGatedFeaturesBiomeModifier::biomes),
            RegistryCodecs.homogeneousList(Registries.BIOME).listOf().optionalFieldOf("exclude", List.of()).forGetter(ConfigGatedFeaturesBiomeModifier::exclude),
            RegistryCodecs.homogeneousList(Registries.PLACED_FEATURE).fieldOf("features").forGetter(ConfigGatedFeaturesBiomeModifier::features),
            GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(ConfigGatedFeaturesBiomeModifier::step),
            Codec.STRING.fieldOf("gate").forGetter(ConfigGatedFeaturesBiomeModifier::gate)
    ).apply(builder, ConfigGatedFeaturesBiomeModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) return;
        if (this.biomes.isPresent() && !this.biomes.get().contains(biome)) return;
        for (HolderSet<Biome> excluded : this.exclude) {
            if (excluded.contains(biome)) return;
        }
        if (!gateEnabled(this.gate)) return;
        BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
        this.features.forEach(holder -> generationSettings.addFeature(this.step, holder));
    }

    /** Mirrors the 1.18.2 {@code if (Config.WORLDGEN.X)} guards, one gate name per guard. */
    public static boolean gateEnabled(String gate) {
        switch (gate) {
            case "bedrock_layers":
                return Config.WORLDGEN.BEDROCK_LAYERS.get() != 0;
            case "layer_gen":
                return Config.WORLDGEN.LAYER_GEN.get() != 0;
            case "meteorite":
                return Config.WORLDGEN.METEORITE_CHANCE.get() > 0;
            case "fumarole":
                return Config.WORLDGEN.FUMAROLE_GEN.get() > 0;
            case "intrusion":
                return Config.WORLDGEN.INTRUSION_GEN.get();
            case "mushrooms":
                return Config.WORLDGEN.MUSHROOMS.get();
            case "flora":
                return Config.WORLDGEN.RANKINE_FLORA.get();
            case "trees":
                return Config.WORLDGEN.RANKINE_TREES.get();
            case "fallen_logs":
                // 1.18.2 required BOTH generateTrees and generateMushrooms(=fallenLogs, see Config typo)
                return Config.WORLDGEN.RANKINE_TREES.get() && Config.WORLDGEN.FALLEN_LOGS.get();
            case "cobbles":
                return Config.WORLDGEN.COBBLES_GEN.get();
            case "end_meteorite":
                return Config.WORLDGEN.END_METEORITE_GEN.get();
            case "end_meteorite_trees":
                return Config.WORLDGEN.END_METEORITE_GEN.get() && Config.WORLDGEN.RANKINE_TREES.get();
            case "antimatter":
                return Config.WORLDGEN.ANTIMATTER_GEN.get();
            default:
                return true;
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return RankineBiomeModifierTypes.CONFIG_GATED_ADD_FEATURES.get();
    }
}
