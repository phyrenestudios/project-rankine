package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class GasBlock extends AbstractGasBlock {

    private final java.util.function.Supplier<? extends Item> gasBottleSupplier;

    public GasBlock(Supplier<? extends Item> gasBottle, float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, Tuple<Level.ExplosionInteraction,Float> flammabilityIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, flammabilityIn, colorIn, properties);
        gasBottleSupplier = gasBottle;
    }

    public GasBlock(Supplier<? extends Item> gasBottle,GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasUtilsEnum, properties);
        gasBottleSupplier = gasBottle;
    }

    public Item getGasBottle() {
        return gasBottleSupplier.get();
    }
}
