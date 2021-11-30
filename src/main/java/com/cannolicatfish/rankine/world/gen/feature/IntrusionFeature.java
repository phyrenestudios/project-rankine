package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class IntrusionFeature extends Feature<NoFeatureConfig> {
    public IntrusionFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Biome BIOME = reader.getBiome(pos);
        if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
            BlockState INTRUSION;
            try {
                INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
            } catch (Exception e) {
                INTRUSION = Blocks.AIR.getDefaultState();
            }

            if (!INTRUSION.matchesBlock(Blocks.AIR)) {
                if (BIOME.getCategory() == Biome.Category.NETHER) {
                    int radius = Config.MISC_WORLDGEN.NETHER_INTRUSION_RADIUS.get() + rand.nextInt(3);
                    int startY = 126;
                    int endY = 1;
                    int shiftx = 0;
                    int shiftz = 0;
                    BlockPos pos1 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos2 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos3 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos posAvg;

                    for (int y = startY; y >= endY; --y) {
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get()) {
                            shiftx += rand.nextInt(3) - 1;
                            shiftz += rand.nextInt(3) - 1;
                        } else {
                            shiftx = 0;
                            shiftz = 0;
                        }
                        pos1 = pos1.add(shiftx, y - pos1.getY(), shiftz);
                        pos2 = pos2.add(shiftx, y - pos2.getY(), shiftz);
                        pos3 = pos3.add(shiftx, y - pos3.getY(), shiftz);
                        posAvg = WorldgenUtils.averagePos(pos1, pos2, pos3);

                        for (BlockPos b : BlockPos.getAllInBoxMutable(posAvg.add(-2 * radius, 0, -2 * radius), posAvg.add(2 * radius, 0, 2 * radius))) {
                            if (WorldgenUtils.inArea(b, radius, pos1, pos2, pos3)) {
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
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.NETHER_INTRUSION_SHRINK.get()) {
                            radius -= 1;
                            if (radius <= 0) {
                                return true;
                            }
                        }
                    }
                    return true;
                } else {
                    int radius = Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_RADIUS.get() + rand.nextInt(3);
                    int startY = 1;
                    int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());
                    int shiftx = 0;
                    int shiftz = 0;
                    BlockPos pos1 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos2 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos3 = pos.add(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos posAvg;

                    for (int y = startY; y <= endY; ++y) {
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get()) {
                            shiftx += rand.nextInt(3) - 1;
                            shiftz += rand.nextInt(3) - 1;
                        } else {
                            shiftx = 0;
                            shiftz = 0;
                        }
                        pos1 = pos1.add(shiftx, y - pos1.getY(), shiftz);
                        pos2 = pos2.add(shiftx, y - pos2.getY(), shiftz);
                        pos3 = pos3.add(shiftx, y - pos3.getY(), shiftz);
                        posAvg = WorldgenUtils.averagePos(pos1, pos2, pos3);

                        for (BlockPos b : BlockPos.getAllInBoxMutable(posAvg.add(-2 * radius, 0, -2 * radius), posAvg.add(2 * radius, 0, 2 * radius))) {
                            if (WorldgenUtils.inArea(b, radius, pos1, pos2, pos3)) {
                                if (reader.getBlockState(b).isIn(RankineTags.Blocks.INTRUSION_PASSABLE)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock())).getDefaultState();
                                        if (ORE.getBlock() instanceof RankineOreBlock) {
                                            ORE = ORE.with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                        }
                                        if (ORE.isIn(Tags.Blocks.ORES_DIAMOND) && y > endY * 0.5) {
                                            reader.setBlockState(b, INTRUSION, 2);
                                        } else {
                                            reader.setBlockState(b, ORE, 2);
                                        }
                                    } else {
                                        reader.setBlockState(b, INTRUSION, 2);
                                    }
                                }
                            }
                        }
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHRINK.get()) {
                            radius -= 1;
                            if (radius <= 0) {
                                return true;
                            }
                        }
                    }
                    return true;

                }
            }
        }
        return false;

    }

}
