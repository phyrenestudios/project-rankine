package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.SparkLighterItem;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
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
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GasBlock extends AirBlock {

    private final GasUtilsEnum gas;

    public GasBlock(GasUtilsEnum gas, Properties properties) {
        super(properties);
        this.gas = gas;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return Config.GASES.GAS_MOVEMENT.get();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (Config.GASES.GAS_MOVEMENT.get()) {
            if (pos.getY() >= 95) {
                worldIn.setBlockState(pos,Blocks.AIR.getDefaultState(),3);
            } else {
                if (worldIn.getBlockState(pos.down()).getBlock() instanceof GasBlock && this.gas.getDensity() > ((GasBlock)worldIn.getBlockState(pos.down()).getBlock()).gas.getDensity()) {
                    worldIn.setBlockState(pos,worldIn.getBlockState(pos.down()),3);
                    worldIn.setBlockState(pos.down(),this.getDefaultState(),3);
                    return;
                }

                if (worldIn.getBlockState(pos.down()).getBlock() instanceof AirBlock && gas.getDensity() > 1) {
                    worldIn.setBlockState(pos,worldIn.getBlockState(pos.down()),3);
                    worldIn.setBlockState(pos.down(),this.getDefaultState(),3);
                    return;
                }

                if (worldIn.getBlockState(pos.up()).getBlock() instanceof AirBlock && !(worldIn.getBlockState(pos.up()).getBlock() instanceof GasBlock) && gas.getDensity() < 1) {
                    worldIn.setBlockState(pos,worldIn.getBlockState(pos.up()),3);
                    if (pos.up().getY() < 95) {
                        worldIn.setBlockState(pos.up(),this.getDefaultState(),3);
                    }
                }
            }
        }


    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() == this) {
            return true;
        }
        return super.isSideInvisible(state, adjacentBlockState, side);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() instanceof AbstractFireBlock) {
            worldIn.createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 1F, Explosion.Mode.NONE);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity ent = (LivingEntity) entityIn;
            boolean creative = (ent instanceof PlayerEntity && ((PlayerEntity) ent).isCreative());
            boolean gasMask = ent.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.GAS_MASK.get();
            if (!creative) {
                if (gas.isSuffocating() && !gasMask) {
                    ent.setAir(Math.max(ent.getAir() - 3,0));
                    if (ent.getAir() == 0) {
                        ent.attackEntityFrom(RankineDamageSources.SUFFOCATION, 2.0F);
                    }
                }
                for (EffectInstance effect : gas.getEffects())
                {
                    if (effect.getPotion().isBeneficial() || !gasMask) {
                        ent.addPotionEffect(effect);
                    }

                }
            }

        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public GasUtilsEnum getGas() {
        return this.gas;
    }
}
