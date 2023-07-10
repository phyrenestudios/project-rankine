package com.cannolicatfish.rankine.worldgen.features.misc;

import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldReplacerFeature extends Feature<NoneFeatureConfiguration> {
/*
    List<StoneLayer> stoneLayers;
    public static double quartzNoiseValue;
    public static double alkalinityNoiseValue;
    public static double maficNoiseValue;
    public static double intrusionSelectorValue;
    public static double layerSelectorValue;
    public static double layerSelectorValue2;
    private static double quartzNoiseScale = 500D;
    private static double alkalinityNoiseScale = 500D;
    private static double maficNoiseScale = 500D;
    private static double intrusionSelectorNoiseScale = 1000D;
    private static double layerSelectorNoiseScale = 2000D;

    private static double intrusionScale = 50D;

    private static final int layerCount = 5;
    private static final int layerThickness = 32;

    private static final RandomSource rand = RandomSource.create();
    private static final PerlinSimplexNoise intrusionNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise quartzNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise alkalinityNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise maficNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise intrusionSelectorNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise layerSelectorNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));

 */

    public WorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel levelIn = context.level();
        ChunkAccess chunkIn = levelIn.getChunk(context.origin());
        return OverworldReplacer.replace(levelIn, chunkIn);
    }

        /*
        for (int x = chunkIn.getPos().getMinBlockX(); x <= chunkIn.getPos().getMaxBlockX(); ++x) {
            for (int z = chunkIn.getPos().getMinBlockZ(); z <= chunkIn.getPos().getMaxBlockZ(); ++z) {
                int endY = levelIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                quartzNoiseValue = quartzNoise.getValue(x/quartzNoiseScale, z/quartzNoiseScale, false);
                alkalinityNoiseValue = alkalinityNoise.getValue(x/alkalinityNoiseScale, z/alkalinityNoiseScale, false);
                maficNoiseValue = maficNoise.getValue(x/maficNoiseScale, z/maficNoiseScale, false);
                intrusionSelectorValue = intrusionSelectorNoise.getValue(x/intrusionSelectorNoiseScale, z/intrusionSelectorNoiseScale, false);
                layerSelectorValue = layerSelectorNoise.getValue(x/layerSelectorNoiseScale, z/layerSelectorNoiseScale, false);
                layerSelectorValue2 = layerSelectorNoise.getValue(x/layerSelectorNoiseScale, z/layerSelectorNoiseScale, true);


                for (int y = levelIn.getMinBuildHeight(); y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    BlockState TARGET_BS = levelIn.getBlockState(TARGET_POS);

                    if (TARGET_BS.is(BlockTags.BASE_STONE_OVERWORLD)) {
                        Block layerBlock = getLayerBlock(levelIn, y);
                        if (isIntrusion(x,y,z, intrusionNoise)) {
                            levelIn.setBlock(TARGET_POS, getIntrusionBlock(levelIn).defaultBlockState(), 3);
                        } else if (isIntrusionShell(x,y,z, intrusionNoise)) {
                            levelIn.setBlock(TARGET_POS, getIntrusionShellBlock(levelIn, layerBlock).defaultBlockState(), 3);
                        } else {
                            levelIn.setBlock(TARGET_POS, layerBlock.defaultBlockState(), 3);
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

    private static Block getIntrusionShellBlock(LevelAccessor levelIn, Block parentStone) {
        List<IntrusionShell> validBlocks = levelIn.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_SHELL_REGISTRY_KEY).stream()
                .filter(i -> i.getParentBlock() == parentStone)
                .toList();

        if (validBlocks.isEmpty()) {
            return getIntrusionBlock(levelIn);
        }

        return validBlocks.get((int) (validBlocks.size() * (intrusionSelectorValue+1)/2.0D)).getShellBlock();

    }

    private static Block getIntrusionBlock(LevelAccessor levelIn) {
        List<Intrusion> validBlocks = levelIn.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_REGISTRY_KEY).stream()
                .filter(i -> i.getQuartzMin() < quartzNoiseValue && i.getQuartzMax() >= quartzNoiseValue)
                .filter(i -> i.getAlkalinityMin() < alkalinityNoiseValue && i.getAlkalinityMax() >= alkalinityNoiseValue)
                .filter(i -> i.getMaficMin() < maficNoiseValue && i.getMaficMax() >= maficNoiseValue)
                .toList();

        if (validBlocks.isEmpty()) {
            return getLayerBlock(levelIn, 0);
        }

        Intrusion finalIntrusion = validBlocks.get((int) Math.floor(((intrusionSelectorValue+1)/2.0D * validBlocks.size())));
        return finalIntrusion.getOreBlock() != Blocks.AIR && levelIn.getRandom().nextFloat() < finalIntrusion.getOreChance() ? finalIntrusion.getOreBlock() : finalIntrusion.getBlock();


    }

    public static Block getLayerBlock(LevelAccessor levelIn, int y) {
        return RankineBlocks.ANORTHOSITE.getStone();




        //List<StoneLayer> validBlocks = levelIn.registryAccess().registryOrThrow(RankineWorldgen.STONE_LAYER_REGISTRY_KEY).stream().collect(Collectors.toList());
        //Collections.shuffle(validBlocks);


       /*
        if (y < 0) {
            return validBlocks.get((int) (validBlocks.size() * (layerSelectorValue+1)/2.0D)).geLayerBlock();
        } else {
            return validBlocks.get((int) (validBlocks.size() * (layerSelectorValue2+1)/2.0D)).geLayerBlock();
        }

        */


}


