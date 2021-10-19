package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
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

import java.util.Random;

public class NetherIntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public NetherIntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        Biome BIOME = reader.getBiome(pos);

        if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
            int radius = WGConfig.INTRUSIONS.NETHER_INTRUSION_RADIUS.get() + rand.nextInt(5) - 2;
            int startY = 126;
            int endY = 1;

            BlockState INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
            if (!INTRUSION.matchesBlock(Blocks.AIR)) {
                int x1 = rand.nextInt(radius) - radius / 2;
                int x2 = rand.nextInt(radius) - radius / 2;
                int x3 = rand.nextInt(radius) - radius / 2;
                int x4 = rand.nextInt(radius) - radius / 2;
                int z1 = rand.nextInt(radius) - radius / 2;
                int z2 = rand.nextInt(radius) - radius / 2;
                int z3 = rand.nextInt(radius) - radius / 2;
                int z4 = rand.nextInt(radius) - radius / 2;


                for (int y = startY; y >= endY; --y) {
                    BlockPos pos1 = pos.add(x1, y, z1);
                    BlockPos pos2 = pos.add(x2, y, z2);
                    BlockPos pos3 = pos.add(x3, y, z3);
                    BlockPos pos4 = pos.add(x4, y, z4);
                    for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-3 * radius, y, -3 * radius), pos.add(3 * radius, y, 3 * radius))) {
                        if (WorldgenUtils.inArea(new BlockPos((x1 + x2 + x3 + x4) / 4, y, (z1 + z2 + z3 + z4) / 4), 2, pos1, pos2, pos3, pos4)) {
                            if (reader.getBlockState(b).isIn(RankineTags.Blocks.INTRUSION_PASSABLE) || reader.isAirBlock(b)) {
                                reader.setBlockState(b, Blocks.AIR.getDefaultState(), 2);
                            }
                        } else if (WorldgenUtils.inArea(b, radius, pos1, pos2, pos3, pos4)) {
                            if (reader.getBlockState(b).isIn(RankineTags.Blocks.INTRUSION_PASSABLE) || reader.isAirBlock(b)) {
                                if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock()))) {
                                    BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock())).getDefaultState();
                                    if (ORE.getBlock() instanceof RankineOreBlock) {
                                        ORE = ORE.with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                    }
                                    reader.setBlockState(b, ORE, 2);
                                } else {
                                    reader.setBlockState(b, INTRUSION, 2);
                                }
                            }
                        }
                    }
                    if (rand.nextFloat() < WGConfig.INTRUSIONS.NETHER_INTRUSION_SHRIFT.get()) {
                        int randX = rand.nextInt(3) - 1;
                        int randZ = rand.nextInt(3) - 1;
                        x1 += randX;
                        x2 += randX;
                        x3 += randX;
                        x4 += randX;
                        z1 += randZ;
                        z2 += randZ;
                        z3 += randZ;
                        z4 += randZ;
                    }
                    if (rand.nextFloat() < WGConfig.INTRUSIONS.NETHER_INTRUSION_SHRINK.get()) {
                        radius -= 1;
                        if (radius <= 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }



}
