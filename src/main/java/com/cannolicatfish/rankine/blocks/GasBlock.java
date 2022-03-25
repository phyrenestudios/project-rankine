package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class GasBlock extends AirBlock {

    private final GasUtilsEnum gas;

    public GasBlock(GasUtilsEnum gas, Properties properties) {
        super(properties);
        this.gas = gas;
    }

    public GasUtilsEnum getGasEnum() {
        return gas;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Config.GASES.GAS_MOVEMENT.get() || Config.GASES.GAS_DISSIPATION.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (Config.GASES.GAS_DISSIPATION.get()) {
            if (random.nextFloat() < this.gas.getDissipationRate()) {
                worldIn.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
                return;
            }
        }
        if (Config.GASES.GAS_MOVEMENT.get()) {
            if (pos.getY() >= 95) {
                worldIn.setBlock(pos,Blocks.AIR.defaultBlockState(),3);
            } else {
                if (worldIn.getBlockState(pos.below()).getBlock() instanceof GasBlock && this.gas.getDensity() > ((GasBlock)worldIn.getBlockState(pos.below()).getBlock()).gas.getDensity()) {
                    worldIn.setBlock(pos,worldIn.getBlockState(pos.below()),3);
                    worldIn.setBlock(pos.below(),this.defaultBlockState(),3);
                } else if (worldIn.getBlockState(pos.below()).getBlock() instanceof AirBlock && gas.getDensity() > 1) {
                    worldIn.setBlock(pos,worldIn.getBlockState(pos.below()),3);
                    worldIn.setBlock(pos.below(),this.defaultBlockState(),3);
                } else if (worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock && !(worldIn.getBlockState(pos.above()).getBlock() instanceof GasBlock) && gas.getDensity() < 1) {
                    worldIn.setBlock(pos,worldIn.getBlockState(pos.above()),3);
                    if (pos.above().getY() < 95) {
                        worldIn.setBlock(pos.above(),this.defaultBlockState(),3);
                    }
                }

            }
        }


    }



    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() == this) {
            return true;
        }
        return super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() instanceof BaseFireBlock) {
            worldIn.explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 1F, Explosion.BlockInteraction.NONE);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity ent = (LivingEntity) entityIn;
            boolean undead = ent.isInvertedHealAndHarm() && Config.GASES.GAS_AFFECT_UNDEAD.get();
            boolean creative = (ent instanceof Player && ((Player) ent).isCreative());
            boolean gasMask = ent.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.GAS_MASK.get() || EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,ent.getItemBySlot(EquipmentSlot.HEAD)) > 0;
            if (!creative) {
                if (gas.isSuffocating() && !gasMask) {
                    ent.setAirSupply(Math.max(ent.getAirSupply() - 3,0));
                    if (ent.getAirSupply() == 0) {
                        ent.hurt(RankineDamageSources.SUFFOCATION, 2.0F);
                    }
                }
                for (MobEffectInstance effect : gas.getEffects())
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

    public GasUtilsEnum getGas() {
        return this.gas;
    }
}
