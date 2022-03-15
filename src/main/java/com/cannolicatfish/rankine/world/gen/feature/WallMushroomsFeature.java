package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.mushrooms.RankineWallMushroomBlock;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class WallMushroomsFeature extends Feature<NoFeatureConfig> {


    public WallMushroomsFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos posShift = pos.offset(8,0,8);
        Block MUSH = RankineLists.WALL_MUSHROOMS.get(rand.nextInt(RankineLists.WALL_MUSHROOMS.size()));
        BlockPos surface = new BlockPos(posShift.getX(), reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, posShift.getX(), posShift.getZ()), posShift.getZ());
        for (BlockPos bp : BlockPos.betweenClosed(surface.offset(-5,-3,-5),surface.offset(5,3,5))) {
            if (reader.getBlockState(bp).is(BlockTags.LOGS) && rand.nextFloat()<0.5) {
                Direction dir = WorldgenUtils.randomHorizontalDirection(rand);
                if (reader.getBlockState(bp.relative(dir)).is(Blocks.AIR)) reader.setBlock(bp.relative(dir), MUSH.defaultBlockState().setValue(RankineWallMushroomBlock.HORIZONTAL_FACING,dir),19);
            }
        }
        return true;
    }

}
