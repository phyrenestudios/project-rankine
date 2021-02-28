package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.WGConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class SillFeature extends Feature<NoFeatureConfig> {
    private BlockState target;
    public SillFeature(Codec<NoFeatureConfig> p_i49915_1_, BlockState target) {
        super(p_i49915_1_);
        this.target = target;
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (rand.nextFloat() < WGConfig.MISC.SILL_CHANCE.get()) {
            //int height = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            int height = rand.nextInt(30) + 30;
            int radius = 10 + rand.nextInt(5);
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(pos.getX() - radius, height - 1, pos.getZ() - radius), new BlockPos(pos.getX() + radius, height, pos.getZ() + radius))) {
                if (reader.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("minecraft:base_stone_overworld")) && blockpos.distanceSq(new BlockPos(pos.getX(), height, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                    reader.setBlockState(blockpos, target, 2);
                }
            }
        }
        return true;
    }
}
