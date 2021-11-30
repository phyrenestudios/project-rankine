package com.cannolicatfish.rankine.world.gen.feature.ores;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
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
                double ElipDist = Math.pow(BP.getX() - pos.getX(), 2) + 9*Math.pow(BP.getY() - pos.getY(), 2) + Math.pow(BP.getZ() - pos.getZ(), 2);
                double RadiusEffect = 1 - ElipDist / Math.pow(config.size,2);
                if (RadiusEffect > 0 && rand.nextDouble() < RadiusEffect) {
                    if (config.target.getPredicate().test(reader.getBlockState(BP))) {
                        Block BLK = config.state.getBlock();
                        if (BLK instanceof RankineOreBlock && WorldgenUtils.ORE_STONES.contains(reader.getBlockState(BP).getBlock())) {
                            reader.setBlockState(BP, BLK.getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(BLK)), 2);
                        } else {
                            reader.setBlockState(BP, BLK.getDefaultState(), 2);
                        }
                    }
                }
            }
        }


        return true;
    }

}
