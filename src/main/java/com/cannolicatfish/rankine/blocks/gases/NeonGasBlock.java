package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class NeonGasBlock extends GasBlock implements PitchModulating {

    public NeonGasBlock(Supplier<? extends Item> gasBottle, GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasBottle,gasUtilsEnum, properties);
    }

    @Override
    public float getPitchMultiplier() {
        return 1.25f;
    }
}
