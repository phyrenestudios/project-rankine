package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class WorldReplacerFeature extends Feature<NoneFeatureConfiguration> {
    public static final int NOISE_SCALE = Config.MISC_WORLDGEN.NOISE_SCALE.get();
    //public static final int NOISE_OFFSET = Config.MISC_WORLDGEN.NOISE_OFFSET.get();
    public static final int LAYER_THICKNESS = Config.MISC_WORLDGEN.LAYER_THICKNESS.get();
    public static final double LAYER_BEND = Config.MISC_WORLDGEN.LAYER_BEND.get();
    public static List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;

    public static final PerlinSimplexNoise INTRUSION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(9183)), ImmutableList.of(0));

    public WorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {

        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();

        ChunkAccess chunk = reader.getChunk(pos);
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                double stoneNoise = Biome.BIOME_INFO_NOISE.getValue(((double)x) / NOISE_SCALE, ((double)z) / NOISE_SCALE, false);
                Biome targetBiome = reader.getBiome(new BlockPos(x, reader.getMaxBuildHeight(), z)).value();

                if (GEN_BIOMES.contains(targetBiome.getRegistryName())) {
                    ResourceLocation biomeName = targetBiome.getRegistryName();
                    for (int y = -64; y <= endY; ++y) {
                        BlockState StoneBS = getStone(biomeName, stoneNoise, y);
                        if (StoneBS == null) return false;
                        BlockPos TARGET_POS = new BlockPos(x,y,z);
                        BlockState TARGET_BS = reader.getBlockState(TARGET_POS);
                        Block TARGET_BLOCK = TARGET_BS.getBlock();
                        if (Config.MISC_WORLDGEN.REPLACE_VANILLA_ORES.get() && vanillaOre(TARGET_BLOCK) != Blocks.AIR.defaultBlockState()) {
                            int TARGET_INDEX = WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock());
                            reader.setBlock(TARGET_POS, vanillaOre(TARGET_BLOCK).setValue(RankineOreBlock.TYPE, TARGET_INDEX == -1 ? 0 : TARGET_INDEX), 3);
                            continue;
                        } else if (TARGET_BLOCK instanceof RankineOreBlock && WorldgenUtils.ORE_STONES.contains(StoneBS.getBlock())) {
                            if (TARGET_BS.getValue(RankineOreBlock.TYPE) != 67 && TARGET_BS.getValue(RankineOreBlock.TYPE) != 68 && TARGET_BS.getValue(RankineOreBlock.TYPE) != 69) {
                                int TARGET_INDEX = WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock());
                                reader.setBlock(TARGET_POS, TARGET_BLOCK.defaultBlockState().setValue(RankineOreBlock.TYPE, TARGET_INDEX == -1 ? 0 : TARGET_INDEX), 3);
                            }
                            continue;
                        }

                        switch (Biome.getBiomeCategory(Holder.direct(targetBiome))) {
                            case NETHER:
                                if (TARGET_BS == Blocks.NETHERRACK.defaultBlockState()) {
                                    if (!leaveNetherrack(reader,TARGET_POS,targetBiome)) {
                                        if (placeSandstone(reader,TARGET_POS)) {
                                            reader.setBlock(TARGET_POS, RankineBlocks.SOUL_SANDSTONE.get().defaultBlockState(), 3);
                                        } else if (TARGET_BS.is(BlockTags.BASE_STONE_NETHER)) {
                                            reader.setBlock(TARGET_POS, StoneBS, 3);
                                        }
                                    }
                                }
                                break;
                            case THEEND:
                                if (TARGET_BS.is(RankineTags.Blocks.BASE_STONE_END)) {
                                    reader.setBlock(TARGET_POS, StoneBS, 3);
                                }
                                break;
                            default:
                                if (canReplaceStone(TARGET_BS)) {
                                    reader.setBlock(TARGET_POS, StoneBS, 3);
                                }
                                break;
                        }
                    }
                }
            }

        }

        return true;
    }

    private static BlockState vanillaOre(Block ore) {
        /*
        if (ore.equals(Blocks.IRON_ORE)) {
            return RankineBlocks.IRON_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.GOLD_ORE)) {
            return RankineBlocks.GOLD_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.DIAMOND_ORE)) {
            return RankineBlocks.DIAMOND_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.EMERALD_ORE)) {
            return RankineBlocks.EMERALD_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.COAL_ORE)) {
            return RankineBlocks.COAL_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.LAPIS_ORE)) {
            return RankineBlocks.LAPIS_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.REDSTONE_ORE)) {
            return RankineBlocks.REDSTONE_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.NETHER_GOLD_ORE)) {
            return RankineBlocks.NETHER_GOLD_ORE.get().defaultBlockState();
        } else if (ore.equals(Blocks.NETHER_QUARTZ_ORE)) {
            return RankineBlocks.NETHER_QUARTZ_ORE.get().defaultBlockState();
        }

         */

        return Blocks.AIR.defaultBlockState();
    }

    private static boolean canReplaceStone(BlockState target) {
        switch (Config.MISC_WORLDGEN.LAYER_GEN.get()) {
            case 1:
                return target.getBlock().equals(Blocks.STONE) || target.getBlock().equals(Blocks.DEEPSLATE);
            case 2:
                return target.is(BlockTags.BASE_STONE_OVERWORLD);
            case 3:
            default:
                return target.is(BlockTags.BASE_STONE_OVERWORLD) && !target.getBlock().getRegistryName().getNamespace().equals("rankine");

        }
    }

    private static BlockState getStone(ResourceLocation biomeName, double noise, int y) {
        List<String> blockList = LAYER_LISTS.get(GEN_BIOMES.indexOf(biomeName));
        int i = (int) Mth.clamp(Math.floor((y+noise/LAYER_BEND+80)/LAYER_THICKNESS),0,blockList.size()-1);
        try {
            return ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blockList.get(i))).defaultBlockState();
        } catch (Exception e) {
            System.out.print("invalid stone layer entry of " + blockList.get(i) + " for " + biomeName);
            return null;
        }
    }

    private static boolean inIntrusion(int x, int y, int z) {
       /* for (BlockPos b : BlockPos.getAllInBoxMutable(-3+x,0,-3+z,3+x,0,3+z)) {
            if (Math.pow(x-b.getX(),2)+Math.pow(z-b.getZ(),2) <= 12) {
                if (INTRUSION_NOISE.noiseAt(b.getX()/10f,b.getZ()/10f,false) > 0.9) {
                    return true;
                }
            }
        }

        return false;


        */
        return INTRUSION_NOISE.getValue(x/10f,z/10f,false) > 0.85;

    }

    private static boolean leaveNetherrack(WorldGenLevel reader, BlockPos pos, Biome biome) {
        if (biome.getRegistryName().toString().equals(Biomes.BASALT_DELTAS.location().toString()) || biome.getRegistryName().toString().equals(Biomes.SOUL_SAND_VALLEY.location().toString())) return false;
        for (int i = 1; i <=Config.MISC_WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.above(i)).is(RankineTags.Blocks.NETHER_TOPS)) return true;
        }
        return false;
    }
    private static boolean placeSandstone(WorldGenLevel reader, BlockPos pos) {
        for (int i = -1*Config.MISC_WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i <=Config.MISC_WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.above(i)).is(BlockTags.SOUL_SPEED_BLOCKS)) return true;
        }
        return false;
    }
}


