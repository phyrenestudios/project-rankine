package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MercuryFlowingFluidBlock extends RankineFlowingFluidBlock{
    public MercuryFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity ent = (LivingEntity) entityIn;
            boolean flag = (entityIn instanceof Player && ((Player) entityIn).isCreative());
            if (!flag) {
                MobEffectInstance cur = ent.getEffect(RankineEffects.MERCURY_POISONING);
                ent.addEffect(new MobEffectInstance(RankineEffects.MERCURY_POISONING, Math.min(1600,cur == null ? 5 : cur.getDuration() + 5), 0, false, false, true));
            }
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }
}
