package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineFlowingFluidBlock extends FlowingFluidBlock {
    public RankineFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        Entity entity = entityIn.isVehicle() && entityIn.getControllingPassenger() != null ? entityIn.getControllingPassenger() : entityIn;
        float f = entity == entityIn ? 0.35F : 0.4F;
        Vector3d v = (FluidHelper.handleFluidAcceleration(entityIn,0.114D));
        float f1 = MathHelper.sqrt(v.x * v.x * (double)0.2F + v.y * v.y + v.z * v.z * (double)0.2F) * f;
        if (f1 > 1.0F) {
            f1 = 1.0F;
        }
        entity.playSound(SoundEvents.GENERIC_SWIM, f1, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
        entity.getDeltaMovement().add(v);
        super.entityInside(state, worldIn, pos, entityIn);
    }
}
