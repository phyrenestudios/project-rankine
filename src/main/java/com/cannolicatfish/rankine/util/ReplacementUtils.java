package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineWorldgen;
import com.cannolicatfish.rankine.stone_features.SoilLayer;
import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.common.Tags;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReplacementUtils {

    private static final RandomSource rand = RandomSource.create();
    private static final PerlinSimplexNoise soilSelectorNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));


    private static final Map<Block, Function<SoilLayer,Block>> soilReplacerMap = new HashMap<>();
    private static final Map<Block, Block> vanillaOresMap = new HashMap<>();

    static {

        soilReplacerMap.put(Blocks.GRASS_BLOCK, soil -> {
            return soil.getOLayer() ;
        });
        soilReplacerMap.put(Blocks.DIRT_PATH, soil -> {
            return soil.getPath();
        });
        soilReplacerMap.put(Blocks.MUD, soil -> {
            return soil.getMud();
        });
        soilReplacerMap.put(Blocks.COARSE_DIRT, soil -> {
            return soil.getCoarseDirt();
        });
        soilReplacerMap.put(Blocks.MYCELIUM, soil -> {
            return soil.getMycelium();
        });
        soilReplacerMap.put(Blocks.PODZOL, soil -> {
            return soil.getPodzol();
        });
        soilReplacerMap.put(Blocks.ROOTED_DIRT, soil -> {
            return soil.getRootedDirt();
        });
        soilReplacerMap.put(Blocks.FARMLAND, soil -> {
            return soil.getFarmland();
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

    public static List<SoilLayer> validBlocks = new ArrayList<>();
    public static void init(LevelAccessor levelIn) {
        validBlocks.clear();
        for (SoilLayer l :levelIn.registryAccess().registryOrThrow(RankineWorldgen.SOIL_LAYER_REGISTRY_KEY).stream().toList()) {
            validBlocks.add(l);
        }
    }

    public static void performRetrogenReplacement(ChunkAccess chunkIn) {
        if (chunkIn.getWorldForge() == null) return;
        for (int x = chunkIn.getPos().getMinBlockX(); x <= chunkIn.getPos().getMaxBlockX(); ++x) {
            for (int z = chunkIn.getPos().getMinBlockZ(); z <= chunkIn.getPos().getMaxBlockZ(); ++z) {
                SoilLayer soil = getSoilType(chunkIn, new BlockPos(x,0,z));
                Holder<Biome> biomeIn = chunkIn.getWorldForge().getBiome(new BlockPos(x,chunkIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z),z));
                for (int y = chunkIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z); y >= chunkIn.getMinBuildHeight(); --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    BlockState TARGET_BS = chunkIn.getBlockState(TARGET_POS);
                    if (TARGET_BS.isAir()) continue;
                    Block TARGET = TARGET_BS.getBlock();

                    if (TARGET_BS.is(Blocks.DIRT)) {
                        if (biomeIn.is(BiomeTags.IS_RIVER)) {
                            chunkIn.setBlockState(TARGET_POS, RankineBlocks.ALLUVIUM.get().defaultBlockState(), false);
                            continue;
                        } else if (biomeIn.is(BiomeTags.IS_OCEAN)) {
                            chunkIn.setBlockState(TARGET_POS, RankineBlocks.SILT.get().defaultBlockState(), false);
                            continue;
                        } else if (biomeIn.is(Tags.Biomes.IS_COLD) && soil.getPermafrost() != Blocks.AIR) {
                            chunkIn.setBlockState(TARGET_POS, soil.getPermafrost().defaultBlockState(), false);
                        } else if (soil.getALayer() != Blocks.AIR) {
                            chunkIn.setBlockState(TARGET_POS, soil.getALayer().defaultBlockState(), false);
                        }
                        if (soil.getBLayer() != Blocks.AIR && chunkIn.getBlockState(TARGET_POS.below()).is(Tags.Blocks.STONE)) {
                            chunkIn.setBlockState(TARGET_POS.below(), soil.getBLayer().defaultBlockState(), false);
                        }
                    } else if (TARGET_BS.is(Blocks.GRASS_BLOCK) && biomeIn.is(BiomeTags.IS_RIVER) && soil.getMud() != Blocks.AIR) {
                        chunkIn.setBlockState(TARGET_POS, soil.getMud().defaultBlockState(), false);
                    } else if (soilReplacerMap.containsKey(TARGET) && soil != null && soilReplacerMap.get(TARGET).apply(soil) != Blocks.AIR) {
                        chunkIn.setBlockState(TARGET_POS, soilReplacerMap.get(TARGET).apply(soil).defaultBlockState(), false);
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

    private static SoilLayer getSoilType(ChunkAccess chunkIn, BlockPos posIn) {
        Vector2f closestPoint = WorleyNoise2D.closestPoint(soilSelectorNoise, 250F, true, posIn);
        if (!validBlocks.isEmpty() && closestPoint != null) return validBlocks.get((int) Math.floor((soilSelectorNoise.getValue(closestPoint.x(), closestPoint.y(), false)+1)/2*validBlocks.size()));
        return null;
    }
}
