package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class WorldReplacerFeature extends Feature<NoFeatureConfig> {
    public static final int NOISE_SCALE = Config.MISC_WORLDGEN.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.MISC_WORLDGEN.NOISE_OFFSET.get();
    public static final int LAYER_THICKNESS = Config.MISC_WORLDGEN.LAYER_THICKNESS.get();
    public static List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;
    public WorldReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        for (int x = chunk.getPos().getXStart(); x <= chunk.getPos().getXEnd(); ++x) {
            for (int z = chunk.getPos().getZStart(); z <= chunk.getPos().getZEnd(); ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

                //STONE REPLACER
                Biome BIOME = reader.getBiome(new BlockPos(x, 0, z));
                if (GEN_BIOMES.contains(BIOME.getRegistryName())) {
                    List<String> blockList = LAYER_LISTS.get(GEN_BIOMES.indexOf(BIOME.getRegistryName()));
                    int stoneCount = blockList.size();
                    int currentHeight = 0;
                    int newHeight;
                    for (int i = 0; i < stoneCount; ++i) {
                        BlockState stone = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(blockList.get(i))).getDefaultState();
                        newHeight = currentHeight + LAYER_THICKNESS;
                        if (i == 0) {
                            newHeight = getLayerHeights(x,z,i)-5;
                        } else if (i == stoneCount-1) {
                            newHeight = endY;
                        }

                        replaceStone(reader, BIOME, x, z, MathHelper.clamp(currentHeight,0,endY), MathHelper.clamp(newHeight,0,endY), stone);
                        currentHeight = newHeight;
                    }
                }


            }
        }
        return true;
    }

    private static int getLayerHeights(int x, int z, int index) {
        double noise = Biome.INFO_NOISE.noiseAt(((double)x + index * NOISE_OFFSET) / NOISE_SCALE, ((double)z + index * NOISE_OFFSET) / NOISE_SCALE, false);
        return (int) Math.round(noise/Config.MISC_WORLDGEN.LAYER_WIDTH.get());
    }

    private static void replaceStone(ISeedReader reader, Biome targetBiome, int x, int z, int StartY, int EndY, BlockState StoneBS) {
        for (int y = StartY; y <= EndY; ++y) {
            BlockPos TARGET_POS = new BlockPos(x,y,z);
            BlockState TARGET_BS = reader.getBlockState(TARGET_POS);
            Block TARGET_BLOCK = TARGET_BS.getBlock();

            switch (targetBiome.getCategory()) {
                case NETHER:
                    if (TARGET_BS == Blocks.NETHERRACK.getDefaultState()) {
                        if (!leaveNetherrack(reader,TARGET_POS,targetBiome)) {
                            if (placeSandstone(reader,TARGET_POS)) {
                                reader.setBlockState(TARGET_POS, RankineBlocks.SOUL_SANDSTONE.get().getDefaultState(), 3);
                            } else if (TARGET_BLOCK instanceof RankineOreBlock && TARGET_BS.get(RankineOreBlock.TYPE) == 0 && WorldgenUtils.ORE_STONES.contains(StoneBS.getBlock())) {
                                reader.setBlockState(TARGET_POS, TARGET_BLOCK.getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock())), 3);
                            } else if (TARGET_BLOCK.isIn(BlockTags.BASE_STONE_NETHER)) {
                                reader.setBlockState(TARGET_POS, StoneBS, 3);
                            }
                        }
                    }
                    break;
                case THEEND:
                    if (TARGET_BLOCK instanceof RankineOreBlock && TARGET_BS.get(RankineOreBlock.TYPE) == 0 && WorldgenUtils.ORE_STONES.contains(StoneBS.getBlock())) {
                        reader.setBlockState(TARGET_POS, TARGET_BLOCK.getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock())), 3);
                    } else if (TARGET_BLOCK.isIn(RankineTags.Blocks.BASE_STONE_END)) {
                        reader.setBlockState(TARGET_POS, StoneBS, 3);
                    }
                    break;
                default:
                    if (TARGET_BLOCK instanceof RankineOreBlock && TARGET_BS.get(RankineOreBlock.TYPE) == 0 && WorldgenUtils.ORE_STONES.contains(StoneBS.getBlock())) {
                        reader.setBlockState(TARGET_POS, TARGET_BLOCK.getDefaultState().with(RankineOreBlock.TYPE, WorldgenUtils.ORE_STONES.indexOf(StoneBS.getBlock())), 3);
                    } else if (TARGET_BLOCK.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                        reader.setBlockState(TARGET_POS, StoneBS, 3);
                    }
                    break;
            }
        }
    }

    private static boolean leaveNetherrack(ISeedReader reader, BlockPos pos, Biome biome) {
        if (biome.getRegistryName().toString().equals(Biomes.BASALT_DELTAS.getLocation().toString()) || biome.getRegistryName().toString().equals(Biomes.SOUL_SAND_VALLEY.getLocation().toString())) return false;
        for (int i = -1*Config.MISC_WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i <=Config.MISC_WORLDGEN.NETHERRACK_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.up(i)).isIn(RankineTags.Blocks.NETHER_TOPS)) return true;
        }
        return false;
    }
    private static boolean placeSandstone(ISeedReader reader, BlockPos pos) {
        for (int i = -1*Config.MISC_WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i <=Config.MISC_WORLDGEN.SOUL_SANDSTONE_LAYER_THICKNESS.get(); i++) {
            if (reader.getBlockState(pos.up(i)).isIn(BlockTags.SOUL_SPEED_BLOCKS)) return true;
        }
        return false;
    }
}


