package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
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
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class IntrusionFeature extends Feature<NoFeatureConfig> {
    public IntrusionFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos posShift = pos.offset(2,0,2);
        Biome targetBiome = reader.getBiome(posShift);
        ResourceLocation biomeName = targetBiome.getRegistryName();
        if (WorldgenUtils.GEN_BIOMES.contains(biomeName)) {
            BlockState INTRUSION;
            try {
                INTRUSION = WorldgenUtils.INTRUSION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).getRandomElement();
            } catch (NullPointerException e) {
                System.out.println("Invalid intrusion entry for " + biomeName);
                INTRUSION = Blocks.AIR.defaultBlockState();
            }

            if (!INTRUSION.is(Blocks.AIR)) {
                if (targetBiome.getBiomeCategory() == Biome.Category.NETHER) {
                    int radius = Config.MISC_WORLDGEN.NETHER_INTRUSION_RADIUS.get() + rand.nextInt(3);
                    int startY = 126;
                    int endY = 1;
                    int shiftx = 0;
                    int shiftz = 0;
                    BlockPos pos1 = posShift.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos2 = posShift.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos3 = posShift.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos posAvg;

                    for (int y = startY; y >= endY; --y) {
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get()) {
                            shiftx += rand.nextInt(3) - 1;
                            shiftz += rand.nextInt(3) - 1;
                        } else {
                            shiftx = 0;
                            shiftz = 0;
                        }
                        pos1 = pos1.offset(shiftx, y - pos1.getY(), shiftz);
                        pos2 = pos2.offset(shiftx, y - pos2.getY(), shiftz);
                        pos3 = pos3.offset(shiftx, y - pos3.getY(), shiftz);
                        posAvg = WorldgenUtils.averagePos(pos1, pos2, pos3);

                        for (BlockPos b : BlockPos.betweenClosed(posAvg.offset(-2 * radius, 0, -2 * radius), posAvg.offset(2 * radius, 0, 2 * radius))) {
                            if (WorldgenUtils.inArea(b, radius, true, pos1, pos2, pos3)) {
                                if (reader.getBlockState(b).is(RankineTags.Blocks.INTRUSION_PASSABLE) || reader.isEmptyBlock(b)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock())).defaultBlockState();
                                        if (ORE.getBlock() instanceof RankineOreBlock) {
                                            ORE = ORE.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                        }
                                        reader.setBlock(b, ORE, 2);
                                    } else {
                                        reader.setBlock(b, INTRUSION, 2);
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
                    //int radius = Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_RADIUS.get();
                    int startY = 1;
                    int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, posShift.getX(), posShift.getZ());
                    posShift = new BlockPos(posShift.getX(),startY,posShift.getZ());

                    for (int y = startY; y <= endY; ++y) {
                        double radius = (Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_RADIUS.get() + 1.25*rand.nextFloat() - 0.5);
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get()) {
                            posShift = posShift.offset(rand.nextBoolean() ? 1 : 0,1,rand.nextBoolean() ? 1 : 0);
                        } else {
                            posShift = posShift.offset(0,1,0);
                        }
                        for (BlockPos b : BlockPos.betweenClosed(posShift.offset(-(radius+1), 0, -(radius+1)), posShift.offset(radius+1, 0, radius+1))) {
                            if (Math.pow(posShift.getX()-b.getX(),2)+Math.pow(posShift.getZ()-b.getZ(),2) <= radius*radius+0.3) {
                                if (reader.getBlockState(b).is(RankineTags.Blocks.INTRUSION_PASSABLE)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock())).defaultBlockState();
                                        if (ORE.getBlock() instanceof RankineOreBlock) {
                                            ORE = ORE.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                        }
                                        if (ORE.is(Tags.Blocks.ORES_DIAMOND) && y > endY * 0.5) {
                                            reader.setBlock(b, INTRUSION, 3);
                                        } else {
                                            reader.setBlock(b, ORE, 3);
                                        }
                                    } else if (rand.nextFloat() < Config.MISC_WORLDGEN.INTRUSION_CINNABAR_ORE.get()) {
                                        reader.setBlock(b, RankineBlocks.CINNABAR_ORE.get().defaultBlockState().setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock())), 3);
                                    } else {
                                        reader.setBlock(b, INTRUSION, 3);
                                    }
                                } else if (reader.getBlockState(b).getBlock() instanceof RankineOreBlock && WorldgenUtils.ORE_STONES.contains(INTRUSION.getBlock())) {
                                    reader.setBlock(b, reader.getBlockState(b).setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock())), 3);
                                }
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
