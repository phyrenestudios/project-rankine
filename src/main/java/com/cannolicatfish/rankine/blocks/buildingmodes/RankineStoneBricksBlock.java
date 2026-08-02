package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Brick hardness and blast resistance are configurable multipliers over the base stone values.
 * <p>
 * In 1.18.2 the multipliers were baked into the shared {@code Block.Properties} at class-init time.
 * That is no longer possible: NeoForge fires the registry events (where blocks are constructed)
 * <em>before</em> configs load, and never loads them at all during datagen, so reading a config
 * value that early throws. The base values now live in the Properties and the multipliers are
 * applied here at runtime instead, which keeps both config options working.
 */
public class RankineStoneBricksBlock extends BuildingModeBlock {

    /** Matches the base values passed in RankineBlocks.IGNEOUS_STONE_BRICKS. */
    private static final float BASE_RESISTANCE = 6.0F;

    public RankineStoneBricksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStyles() {
        return 2;
    }

    /** Blocks can be queried before configs load (and during datagen); fall back to the default there. */
    private static double multiplier(ModConfigSpec.DoubleValue value, double fallback) {
        try {
            return value.get();
        } catch (IllegalStateException notLoadedYet) {
            return fallback;
        }
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float progress = super.getDestroyProgress(state, player, level, pos);
        double mult = multiplier(Config.BLOCK_PROPERTIES.BRICKS_HARDNESS_MULT, 1.5D);
        // Progress is inversely proportional to hardness, so a higher multiplier mines slower.
        return mult <= 0 ? progress : (float) (progress / mult);
    }

    @Override
    public float getExplosionResistance() {
        return (float) (BASE_RESISTANCE * multiplier(Config.BLOCK_PROPERTIES.BRICKS_RESISTANCE_MULT, 2.0D));
    }
}
