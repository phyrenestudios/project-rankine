package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NetherStoneReplacerFeature extends Feature<NoFeatureConfig> {


    public NetherStoneReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }
    public static final int NOISE_SCALE = WGConfig.LAYERS.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = WGConfig.LAYERS.NOISE_OFFSET.get();


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                Biome BIOME = reader.getBiome(new BlockPos(x, 0, z));
                int height= 127;
                if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
                    layering(WorldgenUtils.LAYER_LISTS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())), height, reader, x, z, endY);
                }
            }
        }
        return true;
    }

    private static int getLayerHeights(int x, int z, int index, int height) {
        double noise = Biome.INFO_NOISE.noiseAt((double)x / NOISE_SCALE + index * NOISE_OFFSET, (double)z / NOISE_SCALE + index * NOISE_OFFSET, false);
        return height + (int) Math.round((noise/0.25));
    }


    private static void layering(List<String> blockList, int avgHeight, ISeedReader reader, int x, int z, int endY) {
        int stoneCount = blockList.size();
        for (int i = 0; i < stoneCount; ++i) {
            if (i == 0) {
                replaceStone(reader, x, z, 0, getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else if (i < stoneCount-1) {
                replaceStone(reader, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), getLayerHeights(x,z,i,avgHeight/stoneCount * (i+1)), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            } else {
                replaceStone(reader, x, z, getLayerHeights(x,z,i-1,avgHeight/stoneCount * (i)), endY, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockList.get(i))).getDefaultState());
            }
        }
    }

    private static void replaceStone(ISeedReader reader, int x, int z, int StartY, int EndY, BlockState Block) {
        List<net.minecraft.block.Block> TOPS = Arrays.asList(Blocks.CRIMSON_NYLIUM,Blocks.WARPED_NYLIUM,Blocks.AIR);
        for (int y = StartY; y <= EndY; ++y) {
            BlockPos targetPos = new BlockPos(x, y, z);
            BlockState target = reader.getBlockState(targetPos);
     //       if (WGConfig.MISC.DARK_GRAVEL.get() && target == Blocks.GRAVEL.getDefaultState()) {
       //         reader.setBlockState(targetPos, RankineBlocks.DARK_GRAVEL.get().getDefaultState(), 19);
       //     }
            if (target == Blocks.NETHERRACK.getDefaultState()) {
                if (reader.getBlockState(targetPos.up(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.up(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.up(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.up(4)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.down(1)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.down(2)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.down(3)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) || reader.getBlockState(targetPos.down(4)).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                    reader.setBlockState(targetPos, RankineBlocks.SOUL_SANDSTONE.get().getDefaultState(), 2);
                } else if (!TOPS.contains(reader.getBlockState(targetPos.up(1)).getBlock()) && !TOPS.contains(reader.getBlockState(targetPos.up(2)).getBlock()) && !TOPS.contains(reader.getBlockState(targetPos.up(3)).getBlock())) {
                    reader.setBlockState(targetPos, Block, 2);
                }
            }
        }
    }

}
