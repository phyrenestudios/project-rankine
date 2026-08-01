package com.cannolicatfish.rankine.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

/**
 * PORT-TODO: DiskConfiguration changed in 1.19+ — it is now
 * (RuleBasedBlockStateProvider stateProvider, BlockPredicate target, IntProvider radius, int halfHeight).
 * The old "targets list + single state" behavior is reproduced through the target predicate / state provider.
 * This feature is currently unregistered (kept from 1.18.2 as-is).
 */
public class LandDiskFeature extends Feature<DiskConfiguration> {
    public LandDiskFeature(Codec<DiskConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DiskConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = p_159749_.random();
        DiskConfiguration config = p_159749_.config();
        boolean flag = false;
        int i = config.radius().sample(rand);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for(int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
            for(int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
                int l = j - pos.getX();
                int i1 = k - pos.getZ();
                if (l * l + i1 * i1 <= i * i) {
                    for(int j1 = pos.getY() - config.halfHeight(); j1 <= pos.getY() + config.halfHeight(); ++j1) {
                        mutable.set(j, j1, k);
                        if (config.target().test(reader, mutable)) {
                            BlockState state = config.stateProvider().getState(reader, rand, mutable);
                            reader.setBlock(mutable, state, 2);
                            flag = true;
                        }
                    }
                }
            }
        }

        return flag;
    }
}
