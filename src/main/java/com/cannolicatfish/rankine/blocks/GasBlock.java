package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

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
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
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
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.INVISIBLE;
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
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() instanceof AbstractFireBlock) {
            worldIn.explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 1F, Explosion.Mode.NONE);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity ent = (LivingEntity) entityIn;
            boolean undead = ent.isInvertedHealAndHarm() && Config.GASES.GAS_AFFECT_UNDEAD.get();
            boolean creative = (ent instanceof PlayerEntity && ((PlayerEntity) ent).isCreative());
            boolean gasMask = ent.getItemBySlot(EquipmentSlotType.HEAD).getItem() == RankineItems.GAS_MASK.get() || EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,ent.getItemBySlot(EquipmentSlotType.HEAD)) > 0;
            if (!creative) {
                if (gas.isSuffocating() && !gasMask) {
                    ent.setAirSupply(Math.max(ent.getAirSupply() - 3,0));
                    if (ent.getAirSupply() == 0) {
                        ent.hurt(RankineDamageSources.SUFFOCATION, 2.0F);
                    }
                }
                for (EffectInstance effect : gas.getEffects())
                {
                    if (effect.getEffect().isBeneficial() || (!gasMask && !undead)) {
                        ent.addEffect(effect);
                    }

                }
            }

        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public GasUtilsEnum getGas() {
        return this.gas;
    }
}
