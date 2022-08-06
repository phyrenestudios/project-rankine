package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class HeliumGasBlock extends AbstractGasBlock implements PitchModulating {
    public HeliumGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public HeliumGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 2f;
    }
}
