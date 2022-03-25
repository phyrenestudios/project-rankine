package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineFlowingFluidBlock extends LiquidBlock {
    public RankineFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        Entity entity = entityIn.isVehicle() && entityIn.getControllingPassenger() != null ? entityIn.getControllingPassenger() : entityIn;
        float f = entity == entityIn ? 0.35F : 0.4F;
        Vec3 v = (FluidHelper.handleFluidAcceleration(entityIn,0.114D));
        float f1 = Mth.sqrt((float) (v.x * v.x * (double)0.2F + v.y * v.y + v.z * v.z * (double)0.2F)) * f;
        if (f1 > 1.0F) {
            f1 = 1.0F;
        }
        entity.playSound(SoundEvents.GENERIC_SWIM, f1, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
        entity.getDeltaMovement().add(v);
        super.entityInside(state, worldIn, pos, entityIn);
    }
}
