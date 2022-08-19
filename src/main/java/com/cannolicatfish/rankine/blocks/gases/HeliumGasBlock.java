package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class HeliumGasBlock extends GasBlock implements PitchModulating {
    public HeliumGasBlock(Supplier<? extends Item> gasBottle, float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(gasBottle,densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public HeliumGasBlock(Supplier<? extends Item> gasBottle, GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasBottle,gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 2f;
    }
}
