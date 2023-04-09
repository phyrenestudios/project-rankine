package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineFlowingFluidBlock extends LiquidBlock {
    private boolean evaporates;

    public RankineFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, boolean evaporates, Properties properties) {
        super(supplier, properties);
        this.evaporates = evaporates;
    }

    @Override
    public void tick(BlockState p_60462_, ServerLevel levelIn, BlockPos posIn, Random rand) {
        if (levelIn.dimensionType().ultraWarm() && evaporates) {
            if (!levelIn.isClientSide) {
                levelIn.removeBlock(posIn, true);
            }
            int i = posIn.getX();
            int j = posIn.getY();
            int k = posIn.getZ();
            levelIn.playSound(null, posIn, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + rand.nextFloat() - rand.nextFloat() * 0.8F);

            for(int l = 0; l < 8; ++l) {
                levelIn.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
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
