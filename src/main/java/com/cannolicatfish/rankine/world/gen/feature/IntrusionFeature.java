package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.compatibility.TerraForged;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        float CHANCE = rand.nextFloat();
        BlockState INTRUSION = null;

        int startY = 0;
        int endY = 0;
        int radius = 0;

        if (TerraForged.isInstalled()) {
            if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get()) {
                INTRUSION = RankineBlocks.KIMBERLITE.get().getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
                radius = 6 - rand.nextInt(2);
            }
        } else {
            if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get()) {
                INTRUSION = RankineBlocks.KIMBERLITE.get().getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            } else if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get() + Config.OVERWORLD_INTRUSION_CHANCE.get() / 4) {
                INTRUSION = RankineBlocks.GRANODIORITE.get().getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            } else if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get() + Config.OVERWORLD_INTRUSION_CHANCE.get() / 4*2) {
                INTRUSION = Blocks.DIORITE.getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            } else if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get() + Config.OVERWORLD_INTRUSION_CHANCE.get() / 4*3) {
                INTRUSION = Blocks.GRANITE.getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            } else if (CHANCE < Config.KIMBERLITE_INTRUSION_CHANCE.get() + Config.OVERWORLD_INTRUSION_CHANCE.get() / 4*4) {
                INTRUSION = RankineBlocks.RED_GRANITE.get().getDefaultState();
                endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
            }
            radius = 6 - rand.nextInt(3);
        }


        if (INTRUSION!= null) {
            for (int y = startY; y <= endY; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("rankine:intrusion_passable"))) {
                            if (INTRUSION == RankineBlocks.KIMBERLITE.get().getDefaultState()) {
                                float chance = rand.nextFloat();
                                if (chance < Config.ILMENITE_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.ILMENITE_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 28), 4);
                                } else if (chance < Config.DIAMON_CHANCE.get().floatValue() && y <= 50) {
                                    reader.setBlockState(blockpos, RankineBlocks.DIAMOND_ORE.get().getDefaultState().with(RankineOreBlock.TYPE, 28), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < 0.1) {
                    radius -= 1;
                    if (radius <= 0) {
                        return true;
                    }
                }
            }
        }
        return true;
    }


}
