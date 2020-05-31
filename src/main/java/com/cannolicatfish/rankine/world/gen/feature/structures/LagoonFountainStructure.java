package com.cannolicatfish.rankine.world.gen.feature.structures;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class LagoonFountainStructure extends Structure<NoFeatureConfig>
{
    public LagoonFountainStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> config)
    {
        super(config);
    }


    @Override
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
    {

        int maxDistance = 25;
        int minDistance = 15;

        int xTemp = x + maxDistance * spacingOffsetsX;
        int zTemp = z + maxDistance * spacingOffsetsZ;
        int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
        int zTemp2 = zTemp < 0 ? zTemp - maxDistance + 1 : zTemp;
        int validChunkX = xTemp2 / maxDistance;
        int validChunkZ = zTemp2 / maxDistance;

        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), validChunkX, validChunkZ, this.getSeedModifier());
        validChunkX = validChunkX * maxDistance;
        validChunkZ = validChunkZ * maxDistance;
        validChunkX = validChunkX + random.nextInt(maxDistance - minDistance);
        validChunkZ = validChunkZ + random.nextInt(maxDistance - minDistance);

        return new ChunkPos(validChunkX, validChunkZ);
    }

    @Override
    public String getStructureName()
    {
        return ProjectRankine.MODID + ":lagoon_fountain";
    }

    @Override
    public int getSize()
    {
        return 0;
    }

    @Override
    public IStartFactory getStartFactory()
    {
        return LagoonFountainStructure.Start::new;
    }

    protected int getSeedModifier()
    {
        return 1717171;
    }


    @Override
    public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
    {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);

        //Checks to see if current chunk is valid to spawn in.
        if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
        {
            //Checks if the biome can spawn this structure.
            if (chunkGen.hasStructure(biome, this))
            {
                return true;
            }
        }

        return false;
    }

    /*
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart
    {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
        {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }


        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
        {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];

            //Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + rand.nextInt(15);
            int z = (chunkZ << 4) + rand.nextInt(15);

            //Finds the y value of the terrain at location.
            int surfaceY = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(x, surfaceY, z);

            //Now adds the structure pieces to this.components with all details such as where each part goes
            //so that the structure can be added to the world by worldgen.
            LagoonFountainPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            //Sets the bounds of the structure.
            this.recalculateStructureSize();

         //   StructureTutorialMain.LOGGER.log(Level.DEBUG, "Rundown House at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }
}
