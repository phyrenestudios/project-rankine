package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class XenonGasBlock extends AbstractGasBlock implements PitchModulating {
    public XenonGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public XenonGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 0.5f;
    }
}
