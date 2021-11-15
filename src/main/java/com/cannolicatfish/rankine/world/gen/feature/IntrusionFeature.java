package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.Config;
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

import java.util.Random;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {
        Biome BIOME = reader.getBiome(pos);

        if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
            if (BIOME.getCategory() == Biome.Category.NETHER) {
                int radius = Config.MISC_WORLDGEN.NETHER_INTRUSION_RADIUS.get() + rand.nextInt(5) - 2;
                int startY = 126;
                int endY = 1;
                int x1 = rand.nextInt(radius) - radius / 2;
                int x2 = rand.nextInt(radius) - radius / 2;
                int x3 = rand.nextInt(radius) - radius / 2;
                int x4 = rand.nextInt(radius) - radius / 2;
                int z1 = rand.nextInt(radius) - radius / 2;
                int z2 = rand.nextInt(radius) - radius / 2;
                int z3 = rand.nextInt(radius) - radius / 2;
                int z4 = rand.nextInt(radius) - radius / 2;
                
                BlockState INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
                if (!INTRUSION.matchesBlock(Blocks.AIR)) {
                    for (int y = startY; y >= endY; --y) {

                        int shiftx = rand.nextFloat() < Config.MISC_WORLDGEN.NETHER_INTRUSION_SHIFT.get() ? rand.nextInt(2)-1 : 0;
                        int shiftz = rand.nextFloat() < Config.MISC_WORLDGEN.NETHER_INTRUSION_SHIFT.get() ? rand.nextInt(2)-1 : 0;

                        BlockPos pos1 = pos.add(x1+shiftx,y,z1+shiftz);
                        BlockPos pos2 = pos.add(x2+shiftx,y,z2+shiftz);
                        BlockPos pos3 = pos.add(x3+shiftx,y,z3+shiftz);
                        BlockPos pos4 = pos.add(x4+shiftx,y,z4+shiftz);

                        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-3 * radius, y, -3 * radius), pos.add(3 * radius, y, 3 * radius))) {
                            if (WorldgenUtils.inArea(b,radius,pos1,pos2,pos3,pos4)) {
                                if (reader.getBlockState(b).isIn(RankineTags.Blocks.INTRUSION_PASSABLE) || reader.isAirBlock(b)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock())).getDefaultState();
                                        if (ORE.getBlock() instanceof  RankineOreBlock) {
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
                            int avgX = (x1+x2+x3+x4)/4;
                            int avgZ = (z1+z2+z3+z4)/4;
                            x1 = x1 < avgX ? x1 + 1 : x1 - 1;
                            x2 = x2 < avgX ? x2 + 1 : x2 - 1;
                            x3 = x3 < avgX ? x3 + 1 : x3 - 1;
                            x4 = x4 < avgX ? x4 + 1 : x4 - 1;
                            z1 = z1 < avgX ? z1 + 1 : z1 - 1;
                            z2 = z2 < avgX ? z2 + 1 : z2 - 1;
                            z3 = z3 < avgX ? z3 + 1 : z3 - 1;
                            z4 = z4 < avgX ? z4 + 1 : z4 - 1;
                        }
                    }
                    return true;
                }
            } else {
                int radius = Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_RADIUS.get() + rand.nextInt(5) - 2;
                int startY = 1;
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());

                BlockState INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
                if (!INTRUSION.matchesBlock(Blocks.AIR)) {
                    for (int y = startY; y <= endY; ++y) {
                        int x1 = rand.nextInt(radius) - radius / 2;
                        int x2 = rand.nextInt(radius) - radius / 2;
                        int x3 = rand.nextInt(radius) - radius / 2;
                        int x4 = rand.nextInt(radius) - radius / 2;
                        int z1 = rand.nextInt(radius) - radius / 2;
                        int z2 = rand.nextInt(radius) - radius / 2;
                        int z3 = rand.nextInt(radius) - radius / 2;
                        int z4 = rand.nextInt(radius) - radius / 2;
                        int shiftx = rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get() ? rand.nextInt(2)-1 : 0;
                        int shiftz = rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get() ? rand.nextInt(2)-1 : 0;

                        BlockPos pos1 = pos.add(x1+shiftx,y,z1+shiftz);
                        BlockPos pos2 = pos.add(x2+shiftx,y,z2+shiftz);
                        BlockPos pos3 = pos.add(x3+shiftx,y,z3+shiftz);
                        BlockPos pos4 = pos.add(x4+shiftx,y,z4+shiftz);

                        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-3 * radius, y, -3 * radius), pos.add(3 * radius, y, 3 * radius))) {
                            if (WorldgenUtils.inArea(b,radius,pos1,pos2,pos3,pos4)) {
                                if (reader.getBlockState(b).isIn(RankineTags.Blocks.INTRUSION_PASSABLE)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).indexOf(INTRUSION.getBlock())).getDefaultState();
                                        if (ORE.getBlock() instanceof  RankineOreBlock) {
                                            ORE = ORE.with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                        }
                                        reader.setBlockState(b, ORE, 2);
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
