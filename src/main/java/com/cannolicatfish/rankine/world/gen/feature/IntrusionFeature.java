package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {

    //   private static final BlockState INTRUSION = ModBlocks.KIMBERLITE.getDefaultState();

    public IntrusionFeature(Function<Dynamic<?>, ? extends ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        BlockState INTRUSION = null;
        float CHANCE = rand.nextFloat();
        int BOT = 0;
        int TOP = 0;

        if (CHANCE < 0.075f) {
            INTRUSION = ModBlocks.KIMBERLITE.getDefaultState();
            BOT = 0;
            TOP = 25;
        } else if (CHANCE < 0.12f) {
            INTRUSION = ModBlocks.GRANITE.getDefaultState();
            BOT = 41;
            TOP = 256;
        } else if (CHANCE < 0.2f) {
            INTRUSION = ModBlocks.DIORITE.getDefaultState();
            BOT = 41;
            TOP = 256;
        }

        IChunk chunk = worldIn.getChunk(pos);
        int startX = chunk.getPos().getXStart() + rand.nextInt(6);
        int startZ = chunk.getPos().getZStart() + rand.nextInt(6);
        int endX = chunk.getPos().getXEnd() - rand.nextInt(6);
        int endZ = chunk.getPos().getZEnd() - rand.nextInt(6);
        int startY;
        int endY;
        if (worldIn.getDimension().getType() == DimensionType.OVERWORLD) {
            startY = BOT;
            endY = TOP - rand.nextInt(10);
        } else {
            startY = BOT + rand.nextInt(60);
            endY = TOP;
        }

        if (INTRUSION != null) {
            for (int x = startX; x <= endX; ++x) {
                for (int z = startZ; z <= endZ; ++z) {
                    for (int y = startY; y <= endY; ++y) {
                        if (y == startY && y - 1 > 0 && worldIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                            worldIn.setBlockState(new BlockPos(x, y - 1, z), INTRUSION, 2);
                            if (y - 2 > 0 && worldIn.getBlockState(new BlockPos(x, y - 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                                worldIn.setBlockState(new BlockPos(x, y - 2, z), INTRUSION, 2);
                            }
                        }
                        if (y == endY && y + 1 < 128 && worldIn.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                            worldIn.setBlockState(new BlockPos(x, y + 1, z), INTRUSION, 2);
                            if (y + 2 < 128 && worldIn.getBlockState(new BlockPos(x, y + 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                                worldIn.setBlockState(new BlockPos(x, y + 2, z), INTRUSION, 2);
                            }
                        }
                        if (INTRUSION.getBlock() == ModBlocks.KIMBERLITE) {
                            if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                                if (rand.nextFloat() < 0.04) {
                                    worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.DIAMOND_ORE.getDefaultState().with(RankineOre.TYPE,17), 2);
                                } else {
                                    worldIn.setBlockState(new BlockPos(x, y, z), INTRUSION, 2);
                                }
                            }
                        } else {
                            if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                                if (rand.nextInt(4) != 1) {
                                    worldIn.setBlockState(new BlockPos(x, y, z), INTRUSION, 2);
                                }
                            }
                        }


                    }
                }
            }
        }
            return true;
    }
}
