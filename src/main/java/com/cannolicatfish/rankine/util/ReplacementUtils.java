package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReplacementUtils {
    public static final int NOISE_SCALE = Config.WORLDGEN.NOISE_SCALE.get();
    public static final int LAYER_THICKNESS = Config.WORLDGEN.LAYER_THICKNESS.get();
    public static final double LAYER_BEND = Config.WORLDGEN.LAYER_BEND.get();
    public static final List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static final List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;
    private static double STONE_NOISE = 0;
    private static double SAND_NOISE = 0;
    private static Holder<Biome> BIOME;
    private static final Map<Block, Function<SoilBlocks,Block>> soilReplacerMap = new HashMap<>();
    private static final Map<Block, Block> vanillaOresMap = new HashMap<>();

    static {

        soilReplacerMap.put(Blocks.GRASS_BLOCK, soil -> {
            return soil.getGrassBlock();
        });
        soilReplacerMap.put(Blocks.DIRT, soil -> {
            return soil.getSoilBlock();
        });
        soilReplacerMap.put(Blocks.DIRT_PATH, soil -> {
            return soil.getPathBlock();
        });
        soilReplacerMap.put(Blocks.MUD, soil -> {
            return soil.getMudBlock();
        });
        soilReplacerMap.put(Blocks.COARSE_DIRT, soil -> {
            return soil.getCoarseSoilBlock();
        });
        soilReplacerMap.put(Blocks.MYCELIUM, soil -> {
            return soil.getMyceliumBlock();
        });
        soilReplacerMap.put(Blocks.PODZOL, soil -> {
            return soil.getPodzolBlock();
        });
        soilReplacerMap.put(Blocks.ROOTED_DIRT, soil -> {
            return soil.getRootedSoilBlock();
        });
        soilReplacerMap.put(Blocks.FARMLAND, soil -> {
            return soil.getFarmlandBlock();
        });


        /*
        vanillaOresMap.put(Blocks.IRON_ORE, RankineBlocks.IRON_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_IRON_ORE, RankineBlocks.IRON_ORE.get());
        vanillaOresMap.put(Blocks.COAL_ORE, RankineBlocks.COAL_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_COAL_ORE, RankineBlocks.COAL_ORE.get());
        vanillaOresMap.put(Blocks.COPPER_ORE, RankineBlocks.COPPER_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_COPPER_ORE, RankineBlocks.COPPER_ORE.get());
        vanillaOresMap.put(Blocks.REDSTONE_ORE, RankineBlocks.REDSTONE_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_REDSTONE_ORE, RankineBlocks.REDSTONE_ORE.get());
        vanillaOresMap.put(Blocks.LAPIS_ORE, RankineBlocks.LAPIS_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_LAPIS_ORE, RankineBlocks.LAPIS_ORE.get());
        vanillaOresMap.put(Blocks.GOLD_ORE, RankineBlocks.GOLD_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_GOLD_ORE, RankineBlocks.GOLD_ORE.get());
        vanillaOresMap.put(Blocks.DIAMOND_ORE, RankineBlocks.DIAMOND_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_DIAMOND_ORE, RankineBlocks.DIAMOND_ORE.get());
        vanillaOresMap.put(Blocks.EMERALD_ORE, RankineBlocks.EMERALD_ORE.get());
        vanillaOresMap.put(Blocks.DEEPSLATE_EMERALD_ORE, RankineBlocks.EMERALD_ORE.get());
        vanillaOresMap.put(Blocks.NETHER_QUARTZ_ORE, RankineBlocks.NETHER_QUARTZ_ORE.get());
        vanillaOresMap.put(Blocks.NETHER_GOLD_ORE, RankineBlocks.NETHER_GOLD_ORE.get());

         */

        /*
        replacerMap.put(Blocks.GRAVEL, index -> WorldgenUtils.GRAVELS.get(index) != Blocks.AIR ? WorldgenUtils.GRAVELS.get(index) : Blocks.AIR);
        replacerMap.put(Blocks.SAND, index -> (WorldgenUtils.SANDS.get(index) != Blocks.AIR) ? WorldgenUtils.SANDS.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SAND.get() : Blocks.AIR);
        replacerMap.put(RankineBlocks.SOUL_SANDSTONE.getSandstone(), index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.8f ? RankineBlocks.BLACK_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SANDSTONE, index -> (WorldgenUtils.SANDSTONES.get(index) != Blocks.AIR) ? WorldgenUtils.SANDSTONES.get(index) : SAND_NOISE > 0.5f ? RankineBlocks.WHITE_SANDSTONE.getSandstone() : Blocks.AIR);
        replacerMap.put(Blocks.SMOOTH_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.SMOOTH_RED_SANDSTONE, index -> ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ForgeRegistries.BLOCKS.getKey(WorldgenUtils.SANDSTONES.get(index)).toString().replace(":",":smooth_"))));
        replacerMap.put(Blocks.TUFF, index -> BIOME.is(BiomeTags.IS_OCEAN) ? RankineBlocks.BASALTIC_TUFF.get() :
                (BIOME.is(BiomeTags.IS_BADLANDS) || BIOME.is(Biomes.DESERT)) ? RankineBlocks.RHYOLITIC_TUFF.get() :
                        (BIOME.is(BiomeTags.IS_MOUNTAIN) || BIOME.is(BiomeTags.IS_HILL)) ? RankineBlocks.ANDESITIC_TUFF.get() : Blocks.AIR);

         */
    }

    public static void performRetrogenReplacement(ChunkAccess chunkIn) {

        if (chunkIn.getWorldForge() == null) return;
        for (int x = chunkIn.getPos().getMinBlockX(); x <= chunkIn.getPos().getMaxBlockX(); ++x) {
            for (int z = chunkIn.getPos().getMinBlockZ(); z <= chunkIn.getPos().getMaxBlockZ(); ++z) {
                SoilBlocks soil = getSoilType(x,z);
                for (int y = chunkIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z); y >= chunkIn.getMinBuildHeight(); --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunkIn.getBlockState(TARGET_POS);
                    if (TARGET_BS.isAir()) continue;
                    Block TARGET = TARGET_BS.getBlock();

                    if (soilReplacerMap.containsKey(TARGET) && soil != null) {
                        chunkIn.setBlockState(TARGET_POS, soilReplacerMap.get(TARGET).apply(soil).defaultBlockState(), false);
                        if (TARGET_BS.is(Blocks.DIRT) && chunkIn.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                            chunkIn.setBlockState(TARGET_POS.below(), soil.getCoarseSoilBlock().defaultBlockState(), false);
                        }
                    } else if (TARGET_BS.is(BlockTags.BASE_STONE_OVERWORLD)) {
                        Block layerBlock = OverworldReplacer.getWorleyBlock(chunkIn, TARGET_POS);
                        //if (!isIntrusion(levelIn, layerBlock, x,y,z, intrusionNoise)) {
                        chunkIn.setBlockState(TARGET_POS, layerBlock.defaultBlockState(), false);
                        //}
                    }
                }
                
            }
        }
        chunkIn.setUnsaved(true);
    }

    private static SoilBlocks getSoilType(int x, int z) {
        return SoilBlocks.LOAM;
    }

}
