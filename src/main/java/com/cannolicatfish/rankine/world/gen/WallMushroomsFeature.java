package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.RankineWallMushroomBlock;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class WallMushroomsFeature extends Feature<NoneFeatureConfiguration> {


    public WallMushroomsFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        BlockPos posShift = pos.offset(8,0,8);
        Block MUSH = RankineLists.WALL_MUSHROOMS.get(rand.nextInt(RankineLists.WALL_MUSHROOMS.size()));
        BlockPos surface = new BlockPos(posShift.getX(), reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, posShift.getX(), posShift.getZ()), posShift.getZ());
        for (BlockPos bp : BlockPos.betweenClosed(surface.offset(-5,-3,-5),surface.offset(5,3,5))) {
            if (reader.getBlockState(bp).is(BlockTags.LOGS) && rand.nextFloat()<0.5) {
                Direction dir = WorldgenUtils.randomHorizontalDirection(rand);
                if (reader.getBlockState(bp.relative(dir)).is(Blocks.AIR)) reader.setBlock(bp.relative(dir), MUSH.defaultBlockState().setValue(RankineWallMushroomBlock.HORIZONTAL_FACING,dir),19);
            }
        }
        return true;
    }

}
