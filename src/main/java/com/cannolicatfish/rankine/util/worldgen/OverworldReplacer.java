package com.cannolicatfish.rankine.util.worldgen;

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
import org.joml.Vector2d;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class OverworldReplacer {

    List<StoneLayer> stoneLayers;
    public static double quartzNoiseValue;
    public static double alkalinityNoiseValue;
    public static double maficNoiseValue;
    public static double intrusionSelectorValue;
    //public static double layerSelectorValue;
    //public static double layerSelectorValue2;
    private static double quartzNoiseScale = 500D;
    private static double alkalinityNoiseScale = 500D;
    private static double maficNoiseScale = 500D;
    private static double intrusionSelectorNoiseScale = 1000D;

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

    private static final List<Triplet<Block, Double, Double>> stoneLayerBlocksVoronio = new ArrayList<>();
    private static final List<Block> stoneLayerBlocks = new ArrayList<>();

    public static void init(LevelAccessor levelIn) {
        RandomSource levelRand = RandomSource.create(42);
        for (StoneLayer l : levelIn.registryAccess().registryOrThrow(RankineWorldgen.STONE_LAYER_REGISTRY_KEY).stream().toList()) {
            if (l.geLayerBlock() != Blocks.AIR) {
                //stoneLayerBlocksVoronio.add(new Triplet<>(l.geLayerBlock(), levelRand.nextDouble()*2-1, levelRand.nextDouble()*2-1));
                stoneLayerBlocks.add(l.geLayerBlock());
            }
        }
    }



    private static final double worleyScale = 40D;
    private static Block getWorleyBlock(BlockPos posIn) {
        Vector2d inputRegionPos = new Vector2d( posIn.getX() - (posIn.getX()<0 ? worleyScale - Math.abs(posIn.getX() % worleyScale) : Math.abs(posIn.getX() % worleyScale)), posIn.getZ() - (posIn.getZ()<0 ? worleyScale - Math.abs(posIn.getZ() % worleyScale) : Math.abs(posIn.getZ() % worleyScale)));
        //Vector2d inputPos = new Vector2d(posIn.getX(),posIn.getZ());
        Vector2d closestRegion = null;
        double shortestRegionDistance = 3.0D*worleyScale;
        for (int i=-1; i<=1; i+=1) {
            for (int j=-1; j<=1; j+=1) {
                Vector2d regionJitterPoint = getRegionJitterPoint(new Vector2d(i*worleyScale+inputRegionPos.x(), j*worleyScale+inputRegionPos.y()));
                double regionalDistance = getDistance(regionJitterPoint, posIn);
                if (regionalDistance < shortestRegionDistance) {
                    shortestRegionDistance = regionalDistance;
                    closestRegion = regionJitterPoint;
                }
            }
        }

        return stoneLayerBlocks.get((int) Math.floor((layerSelectorNoise.getValue(closestRegion.x(), closestRegion.y(), false)+1)/2*stoneLayerBlocks.size()));

    }

    private static double getDistance(Vector2d vec1, BlockPos posIn) {
        return vec1.distance(posIn.getX(), posIn.getZ());
    }

    private static Vector2d getRegionJitterPoint(Vector2d inputRegionPos) {
        return inputRegionPos.add(worleyScale*(layerSelectorNoise.getValue(inputRegionPos.x(), inputRegionPos.y(), false)+1)/2, worleyScale*(layerSelectorNoise.getValue(inputRegionPos.x()+16, inputRegionPos.y()+16, false)+1)/2);
    }


    public static boolean replace(WorldGenLevel levelIn, ChunkAccess chunkIn) {
        for (int x = chunkIn.getPos().getMinBlockX(); x <= chunkIn.getPos().getMaxBlockX(); ++x) {
            for (int z = chunkIn.getPos().getMinBlockZ(); z <= chunkIn.getPos().getMaxBlockZ(); ++z) {
                int endY = levelIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
                quartzNoiseValue = quartzNoise.getValue(x/quartzNoiseScale, z/quartzNoiseScale, false);
                alkalinityNoiseValue = alkalinityNoise.getValue(x/alkalinityNoiseScale, z/alkalinityNoiseScale, false);
                maficNoiseValue = maficNoise.getValue(x/maficNoiseScale, z/maficNoiseScale, false);
                intrusionSelectorValue = intrusionSelectorNoise.getValue(x/intrusionSelectorNoiseScale, z/intrusionSelectorNoiseScale, false);

                for (int y = levelIn.getMinBuildHeight(); y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    BlockState TARGET_BS = levelIn.getBlockState(TARGET_POS);
                    Block layerBlock = getWorleyBlock(TARGET_POS);

                    if (TARGET_BS.is(BlockTags.BASE_STONE_OVERWORLD)) {
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
