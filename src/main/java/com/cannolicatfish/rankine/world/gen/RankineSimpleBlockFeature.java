package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class RankineSimpleBlockFeature  extends Feature<SimpleBlockConfiguration> {
    public RankineSimpleBlockFeature(Codec<SimpleBlockConfiguration> p_66808_) {
        super(p_66808_);
    }

    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> p_160341_) {
        SimpleBlockConfiguration simpleblockconfiguration = p_160341_.config();
        WorldGenLevel worldgenlevel = p_160341_.level();
        BlockPos blockpos = p_160341_.origin();
        BlockState blockstate = simpleblockconfiguration.toPlace().getState(p_160341_.random(), blockpos);
        if (blockstate.canSurvive(worldgenlevel, blockpos)) {
            if (blockstate.getBlock() instanceof DoublePlantBlock) {
                if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
                    return false;
                }
                DoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
            } else if (blockstate.getBlock() instanceof RankineDoublePlantBlock) {
                if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
                    return false;
                }
                RankineDoublePlantBlock.placeAt(worldgenlevel, blockstate, blockpos, 2);
            } else {
                worldgenlevel.setBlock(blockpos, blockstate, 2);
            }

            return true;
        } else {
            return false;
        }
    }
}
