package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class VolcanoFeature extends Feature<NoFeatureConfig> {
    public VolcanoFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
   /*     float CHANCE = rand.nextFloat();
        BlockState BASE = null;
        BlockState TUFF = null;
        int MAX_RADIUS = rand.nextInt(Config.VOLCANO_SIZE.get()) + 10;

        if (CHANCE < Config.VOLCANO_CHANCE.get()/5 *1) {
            BASE = ModBlocks.HORNBLENDE_ANDESITE.getDefaultState();
            TUFF = ModBlocks.ANDESITIC_TUFF.getDefaultState();
        } else if (CHANCE < Config.VOLCANO_CHANCE.get()/5 *2) {
            BASE = ModBlocks.RHYOLITE.getDefaultState();
            TUFF = ModBlocks.RHYOLITIC_TUFF.getDefaultState();
        } else if (CHANCE < Config.VOLCANO_CHANCE.get()/5 *3) {
            BASE = ModBlocks.RED_DACITE.getDefaultState();
            TUFF = ModBlocks.DACITIC_TUFF.getDefaultState();
        } else if (CHANCE < Config.VOLCANO_CHANCE.get()/5 *4) {
            BASE = ModBlocks.BLACK_DACITE.getDefaultState();
            TUFF = ModBlocks.DACITIC_TUFF.getDefaultState();
        } else if (CHANCE < Config.VOLCANO_CHANCE.get()/5 *5) {
            BASE = ModBlocks.THOLEIITIC_BASALT.getDefaultState();
            TUFF = ModBlocks.THOLEIITIC_BASALTIC_TUFF.getDefaultState();
        }

        int RADIUS = MAX_RADIUS;

       //if (BASE != null) {
        for (int y = 64; y <= pos.getY(); ++y) {
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(pos.getX()-MAX_RADIUS, y, pos.getZ()-MAX_RADIUS), new BlockPos(pos.getX()+MAX_RADIUS, y, pos.getZ()+MAX_RADIUS))) {
                if (blockpos.distanceSq(new BlockPos(pos.getX(), pos.getY(), pos.getZ())) <= Math.pow(MAX_RADIUS + 0.5, 2) && reader.getBlockState(blockpos) == Blocks.AIR.getDefaultState()) {
                    float chance = rand.nextFloat();
                    if (chance < 0.2) {
                        reader.setBlockState(blockpos, TUFF, 4);
                    } else {
                        reader.setBlockState(blockpos, BASE, 4);
                    }
                }
                --y;
            }
        }


            for (int y = 0; y <= 256 - pos.getY(); ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-RADIUS, y, -RADIUS), pos.add(RADIUS, y, RADIUS))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), pos.getY()+y, pos.getZ())) <= Math.pow(RADIUS + 0.5, 2)) {
                        float chance = rand.nextFloat();
                        if (chance < 0.2) {
                            reader.setBlockState(blockpos, TUFF, 4);
                        } else {
                            reader.setBlockState(blockpos, BASE, 4);
                        }
                    }
                }
                if (RADIUS > MAX_RADIUS/3) {
                     RADIUS = (int) (RADIUS * 0.9);
                } else {
                    if (rand.nextFloat() < 0.5) {
                        --RADIUS;
                    }
                }
                if (RADIUS <= 0) {
                    break;
                }
            }
       //}
    */    return false;
    }
}
