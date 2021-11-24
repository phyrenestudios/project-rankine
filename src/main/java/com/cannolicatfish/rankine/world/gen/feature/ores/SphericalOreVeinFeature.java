package com.cannolicatfish.rankine.world.gen.feature.ores;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SphericalOreVeinFeature extends Feature<RankineOreFeatureConfig> {

    public SphericalOreVeinFeature(Codec<RankineOreFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, RankineOreFeatureConfig config) {
        if (rand.nextFloat() < 1 - config.chance) {
            return false;
        }

        for (BlockPos BP : BlockPos.getAllInBoxMutable(pos.add(-config.size, -config.size, -config.size), pos.add(config.size, config.size, config.size))) {
            if (rand.nextFloat() < config.density * (rand.nextFloat() / 2 + 0.75)) {
                float Dist = (float) Math.sqrt(BP.add(0.5,0.5,0.5).distanceSq(pos.getX(), pos.getY(), pos.getZ(), true));
                float RadiusEffect = (float) (1 - Math.pow(Dist-1,2) / Math.pow(config.size-0.5,2));
                if (RadiusEffect > 0 && rand.nextFloat() < RadiusEffect) {
                    if (config.target.getPredicate().test(reader.getBlockState(BP))) {
                        reader.setBlockState(BP, config.state.getBlock().getDefaultState(), 2);
                    }
                }
            }
        }

        return true;
    }

}
