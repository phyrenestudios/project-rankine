package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class NeonGasBlock extends AbstractGasBlock implements PitchModulating {
    public NeonGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public NeonGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 1.25f;
    }
}
