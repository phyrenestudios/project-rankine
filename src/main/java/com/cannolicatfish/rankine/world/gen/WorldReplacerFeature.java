package com.cannolicatfish.rankine.world.gen;

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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
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

public class WorldReplacerFeature extends Feature<NoneFeatureConfiguration> {
    public static final int NOISE_SCALE = Config.WORLDGEN.NOISE_SCALE.get();
    public static final int LAYER_THICKNESS = Config.WORLDGEN.LAYER_THICKNESS.get();
    public static final double LAYER_BEND = Config.WORLDGEN.LAYER_BEND.get();
    public static List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;
    public static final PerlinSimplexNoise NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(4321L)), ImmutableList.of(0));
    public static final PerlinSimplexNoise INTRUSION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(9183)), ImmutableList.of(0));

    public WorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {

        WorldGenLevel reader = p_159749_.level();

        ChunkAccess chunk = reader.getChunk(p_159749_.origin());
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                Biome targetBiome = reader.getBiome(new BlockPos(x, reader.getMaxBuildHeight(), z)).value();
                int biomeIndex = GEN_BIOMES.indexOf(targetBiome.getRegistryName());
                if (biomeIndex != -1) {
                    ResourceLocation biomeName = targetBiome.getRegistryName();
                    double stoneNoise = NOISE.getValue((double) x / NOISE_SCALE, (double) z / NOISE_SCALE, false);
                    List<String> blockList = LAYER_LISTS.get(GEN_BIOMES.indexOf(biomeName));
                    for (int y = -64; y <= endY; ++y) {
                        BlockState StoneBS = getStone(blockList,y,stoneNoise);
                        if (StoneBS == null) return false;
                        BlockPos TARGET_POS = new BlockPos(x,y,z);
                        BlockState TARGET_BS = reader.getBlockState(TARGET_POS);

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
                                } else if (TARGET_BS.hasProperty(BlockStateProperties.SLAB_TYPE)) {
                                    if (canReplaceStone(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(TARGET_BS.getBlock().getRegistryName().toString().replace("_slab",""))).defaultBlockState())) {
                                        reader.setBlock(TARGET_POS, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(TARGET_BS.getBlock().getRegistryName().toString()+"_slab")).defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM), 3);
                                    }
                                }
                                break;
                        }
                    }
                }
            }

        }

        return true;
    }

    private static boolean canReplaceStone(BlockState target) {
        switch (Config.WORLDGEN.LAYER_GEN.get()) {
            case 1:
                return target.getBlock().equals(Blocks.STONE) || target.getBlock().equals(Blocks.DEEPSLATE);
            case 2:
                return target.is(BlockTags.BASE_STONE_OVERWORLD);
            case 3:
            default:
                return target.is(BlockTags.BASE_STONE_OVERWORLD) && !target.getBlock().getRegistryName().getNamespace().equals("rankine");

        }
    }

    public static BlockState getStone(List<String> blockList,int y,double stoneNoise) {
        try {
            return ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blockList.get((int) Mth.clamp(Math.floor((y+stoneNoise/LAYER_BEND+80)/LAYER_THICKNESS),0,blockList.size()-1)))).defaultBlockState();
        } catch (Exception e) {
            System.out.print("invalid stone layer entry detected");
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
        for (int i = 1; i <=Config.WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.above(i)).is(RankineTags.Blocks.NETHER_TOPS)) return true;
        }
        return false;
    }
    private static boolean placeSandstone(WorldGenLevel reader, BlockPos pos) {
        for (int i = -1*Config.WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i <=Config.WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.above(i)).is(BlockTags.SOUL_SPEED_BLOCKS)) return true;
        }
        return false;
    }
}


