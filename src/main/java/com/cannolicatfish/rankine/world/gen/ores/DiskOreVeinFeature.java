package com.cannolicatfish.rankine.world.gen.ores;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class DiskOreVeinFeature extends Feature<RankineOreFeatureConfig> {

    public DiskOreVeinFeature(Codec<RankineOreFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(FeaturePlaceContext<RankineOreFeatureConfig> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        RankineOreFeatureConfig config = p_159749_.config();
        BlockPos posShift = pos.offset(8,0,8);

        if (rand.nextFloat() < 1 - config.chance) {
            return false;
        }
        for (BlockPos BP : BlockPos.betweenClosed(posShift.offset(-config.size, -config.size/2, -config.size), posShift.offset(config.size, config.size/2, config.size))) {
            if (rand.nextFloat() < config.density * (rand.nextFloat() / 2 + 0.75)) {
                double ElipDist = Math.pow(BP.getX() - posShift.getX(), 2) + 9*Math.pow(BP.getY() - posShift.getY(), 2) + Math.pow(BP.getZ() - posShift.getZ(), 2);
                double RadiusEffect = 1 - ElipDist / Math.pow(config.size,2);
                if (RadiusEffect > 0 && rand.nextDouble() < RadiusEffect) {
                    if (config.target.getPredicate().test(reader.getBlockState(BP))) {
                        Block BLK = config.state.getBlock();
                        if (BLK instanceof RankineOreBlock && WorldgenUtils.ORE_STONES.contains(reader.getBlockState(BP).getBlock())) {
                            reader.setBlock(BP, BLK.defaultBlockState().setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(BLK)), 2);
                        } else {
                            reader.setBlock(BP, BLK.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }


        return true;
    }

}
