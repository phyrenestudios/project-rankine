package com.cannolicatfish.rankine.world.gen.feature.mushrooms;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class LionsManeMushroomFeature extends Feature<BlockStateProvidingFeatureConfig> {


    public LionsManeMushroomFeature(Codec<BlockStateProvidingFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
        return false;
    }

    public static boolean growMushroom(ISeedReader reader, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config, Direction dir) {
        boolean flag = true;
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.offset(dir.rotateY()),pos.offset(dir).offset(dir.rotateYCCW()).up())) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getBlockState(rand, pos);
            Direction dir2 = rand.nextBoolean() ? dir.rotateY() : dir.rotateYCCW();

            reader.setBlockState(pos, state, 3);
            reader.setBlockState(pos.up(), state, 3);
            reader.setBlockState(pos.offset(dir2), state, 3);
            reader.setBlockState(pos.up().offset(dir2), state, 3);

            reader.setBlockState(pos.offset(dir), state, 3);
            reader.setBlockState(pos.up().offset(dir), state, 3);
            reader.setBlockState(pos.offset(dir).offset(dir2), state, 3);
            reader.setBlockState(pos.up().offset(dir).offset(dir2), state, 3);

            return true;
        }
        return false;
    }
}
