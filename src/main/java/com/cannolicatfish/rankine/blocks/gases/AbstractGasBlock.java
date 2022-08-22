package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class AbstractGasBlock extends AirBlock implements PitchModulating {

    private final float density;
    private final float dissipationChance;
    private final List<MobEffectInstance> effectInstances;
    private final boolean suffocating;
    private final int color;
    private final Tuple<Explosion.BlockInteraction,Float> flammability;


    public AbstractGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, Tuple<Explosion.BlockInteraction,Float> flammabilityIn, int colorIn, Properties properties) {
        super(properties);
        this.density = densityIn;
        this.dissipationChance = dissipationChanceIn;
        this.effectInstances = effectInstancesIn;
        this.suffocating = suffocatingIn;
        this.flammability = flammabilityIn;
        this.color = colorIn;
    }

    public AbstractGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        this(gasUtilsEnum.getDensity(),gasUtilsEnum.getDissipationRate(),gasUtilsEnum.getEffects(),gasUtilsEnum.isSuffocating(),gasUtilsEnum.getFlammability(),gasUtilsEnum.getColor(),properties);
    }

    public Tuple<Explosion.BlockInteraction, Float> getGasFlammability() {
        return flammability;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() == this) {
            return true;
        }
        return super.skipRendering(state, adjacentBlockState, side);
    }

    public boolean canBeReplaced(BlockPlaceContext p_60630_) {
        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState p_60535_, Fluid p_60536_) {
        return true;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() instanceof BaseFireBlock && this.getGasFlammability().getB() > 0F) {
            worldIn.explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.getGasFlammability().getB(), this.getGasFlammability().getA());
        } else if (!newState.isAir()) {
            BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> worldIn.isEmptyBlock(B) && !(worldIn.getBlockState(B).getBlock() instanceof AbstractGasBlock)).orElse(null);
            if (close != null) {
                worldIn.setBlock(close,state,3);
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Config.GASES.GAS_MOVEMENT.get() || Config.GASES.GAS_DISSIPATION.get();
    }

    public float getDensity() {
        return this.density;
    }

    public float getDissipationChance() {
        return dissipationChance;
    }

    public List<MobEffectInstance> getEffectInstances() {
        return effectInstances;
    }

    public boolean isSuffocating() {
        return suffocating;
    }

    public int getColor() {
        return color;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (Config.GASES.GAS_DISSIPATION.get()) {
            if (random.nextFloat() < this.getDissipationChance()) {
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                return;
            }
        }
        if (Config.GASES.GAS_MOVEMENT.get()) {
            if (pos.getY() >= 95) {
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            } else {
                if (worldIn.getBlockState(pos.below()).getBlock() instanceof AbstractGasBlock && this.getDensity() > ((AbstractGasBlock) worldIn.getBlockState(pos.below()).getBlock()).getDensity()) {
                    worldIn.setBlock(pos, worldIn.getBlockState(pos.below()), 3);
                    worldIn.setBlock(pos.below(), this.defaultBlockState(), 3);
                } else if (worldIn.getBlockState(pos.below()).getBlock() instanceof AirBlock && this.getDensity() > 1) {
                    worldIn.setBlock(pos, worldIn.getBlockState(pos.below()), 3);
                    worldIn.setBlock(pos.below(), this.defaultBlockState(), 3);
                } else if (worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock && !(worldIn.getBlockState(pos.above()).getBlock() instanceof AbstractGasBlock) && this.getDensity() < 1) {
                    worldIn.setBlock(pos, worldIn.getBlockState(pos.above()), 3);
                    if (pos.above().getY() < 95) {
                        worldIn.setBlock(pos.above(), this.defaultBlockState(), 3);
                    }
                }

            }
        }
    }

    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity ent = (LivingEntity) entityIn;
            boolean undead = ent.isInvertedHealAndHarm() && Config.GASES.GAS_AFFECT_UNDEAD.get();
            boolean creative = (ent instanceof Player && ((Player) ent).isCreative());
            boolean gasMask = ent.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.GAS_MASK.get() || EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,ent.getItemBySlot(EquipmentSlot.HEAD)) > 0;
            if (!creative) {
                if (this.isSuffocating() && !gasMask) {
                    ent.setAirSupply(Math.max(ent.getAirSupply() - 3,0));
                    if (ent.getAirSupply() == 0) {
                        ent.hurt(RankineDamageSources.SUFFOCATION, 2.0F);
                    }
                }
                for (MobEffectInstance effect : this.getEffectInstances())
                {
                    if (effect.getEffect().isBeneficial() || (!gasMask && !undead)) {
                        ent.addEffect(effect);
                    }

                }
            }

        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public float getPitchMultiplier() {
        return 1/this.getDensity();
    }
}
