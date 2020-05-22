package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.world.biome.RankineBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.*;

public class MantleBiomeProvider extends BiomeProvider {
    public final Biome biome;
    private static final List<Biome> SPAWN = Collections.singletonList(RankineBiomes.MANTLE);
    public MantleBiomeProvider()
    {
        super(new HashSet<>(SPAWN));
        biome = RankineBiomes.MANTLE;
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return SPAWN;
    }

    public Biome[] getBiomes(int x, int y, int width, int height, boolean cacheFlag) {
        Biome[] biomes = new Biome[width * height];
        Arrays.fill(biomes, biome);
        return biomes;
    }

    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        return Collections.singleton(biome);
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int y, int range, List<Biome> biomes, Random random) {
        return new BlockPos(x, 65, y);
    }

    @Override
    public boolean hasStructure(Structure<?> structureIn) {
        return false;
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (topBlocksCache.isEmpty()) {
            topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
        }

        return topBlocksCache;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return biome;
    }
}
