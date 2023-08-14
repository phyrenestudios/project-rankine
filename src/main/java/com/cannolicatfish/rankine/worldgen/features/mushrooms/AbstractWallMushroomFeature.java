package com.cannolicatfish.rankine.worldgen.features.mushrooms;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

import java.util.List;

public abstract class AbstractWallMushroomFeature extends Feature<BlockPileConfiguration> {

    protected AbstractWallMushroomFeature(Codec<BlockPileConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockPileConfiguration> config) {
        WorldGenLevel levelIn = config.level();
        BlockPos pos = config.origin();
        levelIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        List<BlockPos> mushroomPos = validDir(levelIn, pos);

        if (!mushroomPos.isEmpty()) {
            BlockState state = config.config().stateProvider.getState(levelIn.getRandom(), pos);
            for (BlockPos b : mushroomPos) {
                levelIn.setBlock(b, state, 3);
            }
            return true;
        }
        return false;
    }

    protected abstract List<BlockPos> validDir(WorldGenLevel levelIn, BlockPos pos);

}
