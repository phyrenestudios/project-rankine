package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class OganessonGasBlock extends AbstractGasBlock implements PitchModulating {
    public OganessonGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public OganessonGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 0f;
    }
}
