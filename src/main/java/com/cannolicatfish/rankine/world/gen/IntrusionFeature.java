package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class IntrusionFeature extends Feature<NoneFeatureConfiguration> {
    public IntrusionFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin().offset(8,0,8);
        Random rand = reader.getRandom();
        Biome targetBiome = reader.getBiome(pos).value();
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
                if (Biome.getBiomeCategory(Holder.direct(targetBiome)) == Biome.BiomeCategory.NETHER) {
                    int radius = Config.MISC_WORLDGEN.NETHER_INTRUSION_RADIUS.get() + rand.nextInt(3);
                    int startY = 126;
                    int endY = 1;
                    int shiftx = 0;
                    int shiftz = 0;
                    BlockPos pos1 = pos.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos2 = pos.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
                    BlockPos pos3 = pos.offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2);
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
                    int startY = -63;
                    int endY = (int) (reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX(), pos.getZ()) * (rand.nextFloat(0.3f) + 0.6f));
                    pos = new BlockPos(pos.getX(),startY,pos.getZ());

                    for (int y = startY; y <= endY; ++y) {
                        double radius = (Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_RADIUS.get() + 1.25*rand.nextFloat() - 0.5);
                        if (rand.nextFloat() < Config.MISC_WORLDGEN.OVERWORLD_INTRUSION_SHIFT.get()) {
                            pos = pos.offset(rand.nextBoolean() ? 1 : 0,1,rand.nextBoolean() ? 1 : 0);
                        } else {
                            pos = pos.offset(0,1,0);
                        }
                        for (BlockPos b : BlockPos.betweenClosed(pos.offset(-(radius+1), 0, -(radius+1)), pos.offset(radius+1, 0, radius+1))) {
                            if (Math.pow(pos.getX()-b.getX(),2)+Math.pow(pos.getZ()-b.getZ(),2) <= radius*radius+0.3) {
                                if (reader.getBlockState(b).is(RankineTags.Blocks.INTRUSION_PASSABLE)) {
                                    if (rand.nextFloat() < WorldgenUtils.INTRUSION_ORE_CHANCES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock()))) {
                                        BlockState ORE = WorldgenUtils.INTRUSION_ORES.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).get(WorldgenUtils.INTRUSION_BLOCKS.get(WorldgenUtils.GEN_BIOMES.indexOf(biomeName)).indexOf(INTRUSION.getBlock())).defaultBlockState();
                                        if (ORE.getBlock() instanceof RankineOreBlock) {
                                            ORE = ORE.setValue(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(INTRUSION.getBlock()));
                                        }
                                        if (ORE.is(Tags.Blocks.ORES_DIAMOND) && y > endY * 0.4) {
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
