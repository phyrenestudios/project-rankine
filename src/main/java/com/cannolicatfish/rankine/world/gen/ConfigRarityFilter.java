package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineWorldgenTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

/**
 * Rarity filter whose chance is read from the Rankine common config at placement time.
 * <p>
 * PORT-NOTE: in 1.18.2 the placed features were built at runtime with
 * {@code RarityFilter.onAverageOnceEvery(Config...)}. Placed features are datapack JSON now,
 * so the config value would be baked in at datagen time; this modifier restores the dynamic lookup.
 */
public class ConfigRarityFilter extends PlacementFilter {
    public static final MapCodec<ConfigRarityFilter> CODEC = Codec.STRING.fieldOf("gate").xmap(ConfigRarityFilter::new, f -> f.gate);
    private final String gate;

    public ConfigRarityFilter(String gate) {
        this.gate = gate;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        int chance;
        switch (this.gate) {
            case "fumarole":
                chance = Config.WORLDGEN.FUMAROLE_GEN.get();
                break;
            case "meteorite":
                chance = Config.WORLDGEN.METEORITE_CHANCE.get();
                break;
            default:
                chance = 1;
        }
        if (chance <= 0) {
            return false;
        }
        return random.nextFloat() < 1.0F / (float) chance;
    }

    @Override
    public PlacementModifierType<?> type() {
        return RankineWorldgenTypes.CONFIG_RARITY_FILTER.get();
    }
}
