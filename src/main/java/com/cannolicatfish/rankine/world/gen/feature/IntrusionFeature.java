package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        Biome.Category CAT = reader.getBiome(pos).getCategory();

        if (WorldgenUtils.GEN_BIOMES.contains(CAT)) {
            BlockState INTRUSION;
            int radius = WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_RADIUS.get() - rand.nextInt(3);
            int startY = 0;
            int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());

            INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(CAT)).getRandomElement();
            if (!INTRUSION.matchesBlock(Blocks.AIR)) {
                int x1 = rand.nextInt(radius) - radius / 2;
                int x2 = rand.nextInt(radius) - radius / 2;
                int x3 = rand.nextInt(radius) - radius / 2;
                int x4 = rand.nextInt(radius) - radius / 2;
                int z1 = rand.nextInt(radius) - radius / 2;
                int z2 = rand.nextInt(radius) - radius / 2;
                int z3 = rand.nextInt(radius) - radius / 2;
                int z4 = rand.nextInt(radius) - radius / 2;


                for (int y = startY; y <= endY; ++y) {
                    for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-3 * radius, y, -3 * radius), pos.add(3 * radius, y, 3 * radius))) {
                        if (b.distanceSq(new BlockPos(pos.getX() + x1, y, pos.getZ() + z1)) <= Math.pow(radius + 0.5, 2) || b.distanceSq(new BlockPos(pos.getX() + x2, y, pos.getZ() + z2)) <= Math.pow(radius + 0.5, 2) || b.distanceSq(new BlockPos(pos.getX() + x3, y, pos.getZ() + z3)) <= Math.pow(radius + 0.5, 2) || b.distanceSq(new BlockPos(pos.getX() + x4, y, pos.getZ() + z4)) <= Math.pow(radius + 0.5, 2)) {
                            if (reader.getBlockState(b).getBlock().getTags().contains(new ResourceLocation("rankine:intrusion_passable"))) {
                                if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(CAT)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(CAT)).indexOf(INTRUSION.getBlock()))) {
                                    reader.setBlockState(b, WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(CAT)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(CAT)).indexOf(INTRUSION.getBlock())).getDefaultState(), 4);
                                } else {
                                    reader.setBlockState(b, INTRUSION, 4);
                                }
                            }
                        }
                    }
                    if (rand.nextFloat() < WGConfig.INTRUSIONS.OVERWORLD_INTRUSION_SHRINK.get()) {
                        x1 += rand.nextInt(3) - 1;
                        x2 += rand.nextInt(3) - 1;
                        x3 += rand.nextInt(3) - 1;
                        x4 += rand.nextInt(3) - 1;
                        z1 += rand.nextInt(3) - 1;
                        z2 += rand.nextInt(3) - 1;
                        z3 += rand.nextInt(3) - 1;
                        z4 += rand.nextInt(3) - 1;
                    }
                    if (rand.nextFloat() < 0.08) {
                        radius -= 1;
                        if (radius <= 0) {
                            return true;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }


}
