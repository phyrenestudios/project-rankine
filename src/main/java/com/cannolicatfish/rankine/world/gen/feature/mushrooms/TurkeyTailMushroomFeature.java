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

public class TurkeyTailMushroomFeature extends Feature<BlockStateProvidingFeatureConfig> {


    public TurkeyTailMushroomFeature(Codec<BlockStateProvidingFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config) {
        return false;
    }

    public static boolean growMushroom(ISeedReader reader, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config, Direction dir) {
        boolean flag = true;
        for (BlockPos b : BlockPos.getAllInBoxMutable(pos.offset(dir.rotateY(),2).down(),pos.offset(dir).offset(dir.rotateYCCW(),2).up())) {
            if (!WorldgenUtils.isAirOrLeaves(reader,b)) {
                flag = false;
            }
        }

        if (flag) {
            BlockState state = config.stateProvider.getBlockState(rand, pos);

            build(reader,pos,state,dir.rotateY(), rand.nextInt(2)+1);
            build(reader,pos,state,dir.rotateYCCW(), rand.nextInt(2)+1);
            build(reader,pos.up().offset(dir),state,dir.rotateY(), rand.nextInt(2)+1);
            build(reader,pos.up().offset(dir),state,dir.rotateYCCW(), rand.nextInt(2)+1);
            build(reader,pos.down().offset(dir),state,dir.rotateY(), rand.nextInt(2)+1);
            build(reader,pos.down().offset(dir),state,dir.rotateYCCW(), rand.nextInt(2)+1);

            return true;
        }
        return false;
    }

    private static void build(ISeedReader reader, BlockPos pos, BlockState state, Direction dir, int length) {
        for (int i = 0; i <= length; ++i) {
            reader.setBlockState(pos.offset(dir,i),state,19);
        }
    }
}
