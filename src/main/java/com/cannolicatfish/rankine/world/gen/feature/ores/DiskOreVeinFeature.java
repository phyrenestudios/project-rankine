package com.cannolicatfish.rankine.world.gen.feature.ores;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class DiskOreVeinFeature extends Feature<RankineOreFeatureConfig> {

    public DiskOreVeinFeature(Codec<RankineOreFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, RankineOreFeatureConfig config) {
        if (rand.nextFloat() < 1 - config.chance) {
            return false;
        }
        for (BlockPos BP : BlockPos.getAllInBoxMutable(pos.add(-config.size, -config.size/2, -config.size), pos.add(config.size, config.size/2, config.size))) {
            if (rand.nextFloat() < config.density * (rand.nextFloat() / 2 + 0.75)) {
                double ElipDist = Math.sqrt(Math.pow(BP.getX() - pos.getX(), 2) + 16 * Math.pow(BP.getY() - pos.getY(), 2) + Math.pow(BP.getZ() - pos.getZ(), 2));
                double Dist = Math.sqrt(BP.add(0.5,0.5,0.5).distanceSq(pos.getX(), pos.getY(), pos.getZ(), true));
                double RadiusEffect = 1 - Math.pow(Dist,2) / Math.pow(ElipDist,2);
                if (RadiusEffect > 0 && rand.nextDouble() < RadiusEffect) {
                    if (config.target.getPredicate().test(reader.getBlockState(BP))) {
                        reader.setBlockState(BP, config.state.getBlock().getDefaultState(), 2);
                    }
                }
            }
        }


        return true;
    }

}
