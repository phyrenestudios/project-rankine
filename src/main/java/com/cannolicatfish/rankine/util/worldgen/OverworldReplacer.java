package com.cannolicatfish.rankine.util.worldgen;

import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.RankineWorldgen;
import com.cannolicatfish.rankine.stone_features.Intrusion;
import com.cannolicatfish.rankine.stone_features.IntrusionShell;
import com.cannolicatfish.rankine.stone_features.StoneLayer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.joml.Vector3f;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class OverworldReplacer {

    public static double quartzNoiseValue;
    public static double alkalinityNoiseValue;
    public static double maficNoiseValue;
    public static double intrusionSelectorValue;
    private static double quartzNoiseScale = 500D;
    private static double alkalinityNoiseScale = 500D;
    private static double maficNoiseScale = 500D;
    private static double intrusionSelectorNoiseScale = 1000D;

    private static double intrusionScale = 50D;

    private static final RandomSource rand = RandomSource.create();
    private static final PerlinSimplexNoise intrusionNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise quartzNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise alkalinityNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise maficNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise intrusionSelectorNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise layerSelectorNoise1 = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise layerSelectorNoise2 = new PerlinSimplexNoise(rand, ImmutableList.of(0));
    private static final PerlinSimplexNoise layerShapingNoise = new PerlinSimplexNoise(rand, ImmutableList.of(0));

    private static final List<Block> upperContinentalBlocks = new ArrayList<>();
    private static final List<Block> midContinentalBlocks = new ArrayList<>();
    private static final List<Block> deepContinentalBlocks = new ArrayList<>();
    private static final List<Block> upperOceanicBlocks = new ArrayList<>();
    private static final List<Block> deepOceanicBlocks = new ArrayList<>();

    public static void init(LevelAccessor levelIn) {
        upperContinentalBlocks.clear();
        midContinentalBlocks.clear();
        deepContinentalBlocks.clear();
        upperOceanicBlocks.clear();
        deepOceanicBlocks.clear();
        for (StoneLayer l : levelIn.registryAccess().registryOrThrow(RankineWorldgen.STONE_LAYER_REGISTRY_KEY).stream().toList()) {
            if (l.geLayerBlock() == Blocks.AIR) continue;
            if (l.isUpperContinental()) upperContinentalBlocks.add(l.geLayerBlock());
            if (l.isMidContinental()) midContinentalBlocks.add(l.geLayerBlock());
            if (l.isDeepContinental()) deepContinentalBlocks.add(l.geLayerBlock());
            if (l.isUpperOceanic()) upperOceanicBlocks.add(l.geLayerBlock());
            if (l.isDeepOceanic()) deepOceanicBlocks.add(l.geLayerBlock());
        }
    }

    private static final float worleyScale = 500f;
    private static final short layerThickness = 40;
    private static final short waveOffset = 50;
    public static Block getWorleyBlock(ChunkAccess chunkIn, BlockPos posIn) {
        Pair<Float,Short> layerNums =  getLayer(chunkIn, posIn);
        Vector3f inputRegion = getInputRegion(posIn, layerNums.getB() % 2 == 0);
        Vector3f inputVec = getInputOffsetVec(posIn, layerNums.getB() % 2 == 0).add(0f, layerNums.getA(),0f).sub(inputRegion);
        Vector3f closestPoint = null;
        float minDist = 5.0f;
        for (short i=-1; i<=1; i+=1) {
            for (short j = -1; j <= 1; j += 1) {
                Vector3f neighborOffset = new Vector3f(i,0,j);
                Vector3f neighborPoint = randomVec3XZ(add(neighborOffset, inputRegion), layerNums.getB());
                float dist = add(neighborPoint, neighborOffset).sub(inputVec).length();
                if (dist < minDist) {
                    minDist = dist;
                    closestPoint = add(neighborPoint, add(neighborOffset, inputRegion));
                }
            }
        }

        List<Block> validBlocks;
        if (chunkIn.getWorldForge().getBiome(new BlockPos(posIn.getX(), chunkIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, posIn.getX(), posIn.getZ()), posIn.getZ())).is(RankineTags.Biomes.OCEANIC_CRUST)) {
            validBlocks = layerNums.getB() <= 0 ? deepOceanicBlocks : upperOceanicBlocks;
        } else {
            validBlocks = layerNums.getB() <= -2 ? deepContinentalBlocks : layerNums.getB() <= 1 ? midContinentalBlocks : upperContinentalBlocks;
        }

        if (!validBlocks.isEmpty() && closestPoint != null) return validBlocks.get((int) Math.floor((layerSelectorNoise1.getValue(closestPoint.x(), closestPoint.z(), false)+1)/2*validBlocks.size()));
        return Blocks.STONE;
    }

    private static Pair<Float,Short> getLayer(ChunkAccess chunkIn, BlockPos posIn) {
        double noise = layerShapingNoise.getValue(posIn.getX()/300D, posIn.getZ()/300D, false);
        if (noise < 0.5) {
            noise = lerp(-1.0D, 0.0D, 0.5D, 1.00, noise);
        } else if (noise < 0.8) {
            noise = lerp(0.5D, 1.00, 0.8D, 1.6D, noise);
        } else {
            noise = lerp(0.8D, 1.6D, 1.00, 3.0D, noise);
        }
        double thicknessNoise = layerShapingNoise.getValue((posIn.getX()-30)/300D, (posIn.getZ()-30)/300D, false)+1;
        double adjustedY = posIn.getY() - chunkIn.getWorldForge().getMinBuildHeight() - noise * waveOffset;
        return new Pair<>((float) adjustedY/layerThickness % 1f, (short) Math.floor(adjustedY / (layerThickness)));

    }

    private static double lerp(double xa, double ya, double xb, double yb, double x) {
        if (x == xa) return ya;
        if (x == xb) return yb;
        return (yb - ya) / (xb - xa) * (x - xa) + ya;
    }

    private static Vector3f getInputOffsetVec(BlockPos posIn, boolean shift) {
        int xshift = (int) (worleyScale*0.1*(layerSelectorNoise1.getValue(posIn.getX()*0.02+33, posIn.getZ()*0.02+33, false)+1)/2);
        int zshift =  (int) (worleyScale*0.1*(layerSelectorNoise1.getValue(posIn.getX()*0.02+11, posIn.getZ()*0.02+11, false)+1)/2);
        return new Vector3f((posIn.getX()+xshift)/worleyScale, 0, (posIn.getZ()+zshift)/worleyScale);
    }
    private static Vector3f getInputRegion(BlockPos posIn, boolean shift) {
        return new Vector3f((float) Math.floor(posIn.getX()/worleyScale), 0, (float) Math.floor(posIn.getZ()/worleyScale));
    }
    private static Vector3f randomVec3XZ(Vector3f vecIn, int layer) {
        return new Vector3f((float) (layerSelectorNoise1.getValue(vecIn.x()+16*layer, vecIn.z()+16*layer, false)+1f)/2f,(float) (layerSelectorNoise1.getValue(vecIn.x()+160*layer, vecIn.z()+160*layer, false)+1f)/2f,(float) (layerSelectorNoise2.getValue(vecIn.x()+16*layer, vecIn.z()+16*layer, false)+1f)/2f);
    }
    private static Vector3f add(Vector3f vec1, Vector3f vec2) {
        return new Vector3f(vec1.x()+vec2.x(), vec1.y()+vec2.y(), vec1.z()+vec2.z());
    }

    public static boolean replace(WorldGenLevel levelIn, ChunkAccess chunkIn) {
        for (int x = chunkIn.getPos().getMinBlockX(); x <= chunkIn.getPos().getMaxBlockX(); ++x) {
            for (int z = chunkIn.getPos().getMinBlockZ(); z <= chunkIn.getPos().getMaxBlockZ(); ++z) {
                int endY = levelIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                //quartzNoiseValue = quartzNoise.getValue(x/quartzNoiseScale, z/quartzNoiseScale, false);
                //alkalinityNoiseValue = alkalinityNoise.getValue(x/alkalinityNoiseScale, z/alkalinityNoiseScale, false);
                //maficNoiseValue = maficNoise.getValue(x/maficNoiseScale, z/maficNoiseScale, false);
                //intrusionSelectorValue = intrusionSelectorNoise.getValue(x/intrusionSelectorNoiseScale, z/intrusionSelectorNoiseScale, false);

                for (int y = levelIn.getMinBuildHeight(); y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    BlockState TARGET_BS = levelIn.getBlockState(TARGET_POS);
                    if (TARGET_BS.is(BlockTags.BASE_STONE_OVERWORLD)) {
                        Block layerBlock = getWorleyBlock(chunkIn, TARGET_POS);
                        //if (!isIntrusion(levelIn, layerBlock, x,y,z, intrusionNoise)) {
                            levelIn.setBlock(TARGET_POS, layerBlock.defaultBlockState(), 3);
                        //}
                    }
                }

            }
        }
        return true;
    }

    private static boolean isIntrusion(LevelAccessor levelIn, Block parentStone, int x, int y, int z, PerlinSimplexNoise intrusionNoise) {
        double noise = intrusionNoise.getValue(x/intrusionScale, z/intrusionScale, false);
        if (y < noise*300-220) {
            if (y > noise*300-230) {
                List<IntrusionShell> validShellBlocks = levelIn.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_SHELL_REGISTRY_KEY).stream()
                        .filter(i -> i.getParentBlock() == parentStone)
                        .toList();

                if (!validShellBlocks.isEmpty()) {
                    levelIn.setBlock(new BlockPos(x,y,z), validShellBlocks.get((int) (validShellBlocks.size() * (intrusionSelectorValue + 1) / 2.0D)).getShellBlock().defaultBlockState(), 3);
                    return true;
                }
            }

            List<Intrusion> validBlocks = levelIn.registryAccess().registryOrThrow(RankineWorldgen.INTRUSION_REGISTRY_KEY).stream()
                    .filter(i -> i.getQuartzMin() < quartzNoiseValue && i.getQuartzMax() >= quartzNoiseValue)
                    .filter(i -> i.getAlkalinityMin() < alkalinityNoiseValue && i.getAlkalinityMax() >= alkalinityNoiseValue)
                    .filter(i -> i.getMaficMin() < maficNoiseValue && i.getMaficMax() >= maficNoiseValue)
                    .toList();

            if (!validBlocks.isEmpty()) {
                Intrusion finalIntrusion = validBlocks.get((int) Math.floor(((intrusionSelectorValue+1)/2.0D * validBlocks.size())));
                levelIn.setBlock(new BlockPos(x,y,z), finalIntrusion.getOreBlock() != Blocks.AIR && levelIn.getRandom().nextFloat() < finalIntrusion.getOreChance() ? finalIntrusion.getOreBlock().defaultBlockState() : finalIntrusion.getBlock().defaultBlockState(), 3);
                return true;
            }
        }
        return false;
    }

/*
    public static Block getLayerBlock(LevelAccessor levelIn, BlockPos posIn) {
        double xCord = (posIn.getX() % layerSelectorNoiseScale) / layerSelectorNoiseScale;
        double zCord = (posIn.getZ() % layerSelectorNoiseScale) / layerSelectorNoiseScale;
        Triplet<Block, Double, Double> closest = null;
        double shortest = 2.0D;
        for (Triplet<Block, Double, Double> t : stoneLayerBlocksVoronio) {
            double distance = Math.abs(xCord-t.getB()) + Math.abs(zCord-t.getC());
            if (distance < shortest) {
                shortest = distance;
                closest = t;
            }
        }

        if (closest != null) {
            return closest.getA();
        }

        return Blocks.STONE;
    }

 */

}
