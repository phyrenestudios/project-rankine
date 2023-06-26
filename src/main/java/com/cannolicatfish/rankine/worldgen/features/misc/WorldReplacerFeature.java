package com.cannolicatfish.rankine.worldgen.features.misc;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineWorldgen;
import com.cannolicatfish.rankine.stone_features.Intrusion;
import com.cannolicatfish.rankine.stone_features.IntrusionShell;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.List;

public class WorldReplacerFeature extends Feature<NoneFeatureConfiguration> {

    public static double quartzNoiseValue;
    public static double alkalinityNoiseValue;
    public static double maficNoiseValue;
    public static double intrusionSelectorValue;
    private static double intrusionScale = 50D;
    private static double quartzNoiseScale = 1000D;
    private static double alkalinityNoiseScale = 1000D;
    private static double maficNoiseScale = 1000D;
    private static double intrusionSelectorNoiseScale = 1500D;

    /*
    public static final int NOISE_SCALE = Config.WORLDGEN.NOISE_SCALE.get();
    public static final int LAYER_THICKNESS = Config.WORLDGEN.LAYER_THICKNESS.get();
    public static final double LAYER_BEND = Config.WORLDGEN.LAYER_BEND.get();
    public static List<ResourceLocation> GEN_BIOMES = WorldgenUtils.GEN_BIOMES;
    public static List<List<String>> LAYER_LISTS = WorldgenUtils.LAYER_LISTS;
    public static final PerlinSimplexNoise INTRUSION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(9183)), ImmutableList.of(0));

    public static final PerlinSimplexNoise NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(4321L)), ImmutableList.of(0));

     */

    public WorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        ChunkAccess chunk = reader.getChunk(p_159749_.origin());
        PerlinSimplexNoise intrusionNoise = new PerlinSimplexNoise(reader.getRandom(), ImmutableList.of(0));
        PerlinSimplexNoise quartzNoise = new PerlinSimplexNoise(reader.getRandom(), ImmutableList.of(0));
        PerlinSimplexNoise alkalinityNoise = new PerlinSimplexNoise(reader.getRandom(), ImmutableList.of(0));
        PerlinSimplexNoise maficNoise = new PerlinSimplexNoise(reader.getRandom(), ImmutableList.of(0));
        PerlinSimplexNoise intrusionSelectorNoise = new PerlinSimplexNoise(reader.getRandom(), ImmutableList.of(0));

        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                quartzNoiseValue = quartzNoise.getValue(x/quartzNoiseScale, z/quartzNoiseScale, false);
                alkalinityNoiseValue = alkalinityNoise.getValue(x/alkalinityNoiseScale, z/alkalinityNoiseScale, false);
                maficNoiseValue = maficNoise.getValue(x/maficNoiseScale, z/maficNoiseScale, false);
                intrusionSelectorValue = intrusionSelectorNoise.getValue(x/intrusionSelectorNoiseScale, z/intrusionSelectorNoiseScale, false);
                for (int y = reader.getMinBuildHeight(); y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    BlockState TARGET_BS = reader.getBlockState(TARGET_POS);

                    if (TARGET_BS.is(BlockTags.BASE_STONE_OVERWORLD)) {
                        Block layerBlock = getLayerBlock(x,y,z);
                        if (isIntrusion(x,y,z, intrusionNoise)) {
                            reader.setBlock(TARGET_POS, getIntrusionBlock(reader).defaultBlockState(), 3);
                        } else if (isIntrusionShell(x,y,z, intrusionNoise)) {
                            reader.setBlock(TARGET_POS, getIntrusionShellBlock(reader, layerBlock).defaultBlockState(), 3);
                        } else {
                            reader.setBlock(TARGET_POS, layerBlock.defaultBlockState(), 3);
                        }
                    }
                }

            }
        }

        return true;
    }

    private static boolean isIntrusion(int x, int y, int z, PerlinSimplexNoise intrusionNoise) {
        double noise = intrusionNoise.getValue(x/intrusionScale, z/intrusionScale, false);
        //if (noise > 0.8) {
            return y < noise*300-230;
        //}
        //return false;
    }


    private static boolean isIntrusionShell(int x, int y, int z, PerlinSimplexNoise intrusionNoise) {
        double noise = intrusionNoise.getValue(x/intrusionScale, z/intrusionScale, false);
        //if (noise > 0.8) {
            return y < noise*300-220;
        //}
        //return false;
    }

    private static Block getIntrusionShellBlock(WorldGenLevel reader, Block parentStone) {
        List<IntrusionShell> validBlocks = reader.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_SHELL_REGISTRY_KEY).stream()
                .filter(i -> i.getParentBlock() == parentStone)
                .toList();

        if (validBlocks.isEmpty()) {
            return getIntrusionBlock(reader);
        }

        return validBlocks.get(0).getShellBlock();

    }

    private static Block getIntrusionBlock(WorldGenLevel reader) {
        List<Intrusion> validBlocks = reader.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_REGISTRY_KEY).stream()
                .filter(i -> i.getQuartzMin() < quartzNoiseValue && i.getQuartzMax() >= quartzNoiseValue)
                .filter(i -> i.getAlkalinityMin() < alkalinityNoiseValue && i.getAlkalinityMax() >= alkalinityNoiseValue)
                .filter(i -> i.getMaficMin() < maficNoiseValue && i.getMaficMax() >= maficNoiseValue)
                .toList();

        if (validBlocks.isEmpty()) {
            return RankineBlocks.KIMBERLITE.getStone();
        }

        Intrusion finalIntrusion = validBlocks.get((int) Math.floor(((intrusionSelectorValue+0.99)/2 * validBlocks.size())));
        return finalIntrusion.getOreBlock() != Blocks.AIR && reader.getRandom().nextFloat() < finalIntrusion.getOreChance() ? finalIntrusion.getOreBlock() : finalIntrusion.getBlock();


    }

    public static Block getLayerBlock(int x, int y, int z) {

        return RankineBlocks.ANORTHOSITE.getStone();
    }

/*
    private static boolean canReplaceStone(BlockState target) {
        switch (Config.WORLDGEN.LAYER_GEN.get()) {
            case 1:
                return target.getBlock().equals(Blocks.STONE) || target.getBlock().equals(Blocks.DEEPSLATE);
            case 2:
                return target.is(BlockTags.BASE_STONE_OVERWORLD);
            case 3:
            default:
                return target.is(BlockTags.BASE_STONE_OVERWORLD) && !ForgeRegistries.BLOCKS.getKey(target.getBlock()).getNamespace().equals("rankine");

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


        return INTRUSION_NOISE.getValue(x/10f,z/10f,false) > 0.85;

    }

    private static boolean leaveNetherrack(WorldGenLevel reader, BlockPos pos, Biome biome) {
        if (ForgeRegistries.BIOMES.getKey(biome).toString().equals(Biomes.BASALT_DELTAS.location().toString()) || ForgeRegistries.BIOMES.getKey(biome).toString().equals(Biomes.SOUL_SAND_VALLEY.location().toString())) return false;
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
        */
}


