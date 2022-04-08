package com.cannolicatfish.rankine.world.trees;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.world.gen.RankineConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class EasternHemlockTree extends AbstractTreeGrower {

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random randomIn, boolean p_225546_2_) {
        return Holder.direct(RankineConfiguredFeatures.EASTERN_HEMLOCK_TREE);
    }
}
