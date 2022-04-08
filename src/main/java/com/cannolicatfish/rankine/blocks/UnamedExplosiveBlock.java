package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class UnamedExplosiveBlock extends Block {
    public UnamedExplosiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
        world.explode(igniter, pos.getX(), pos.getY() + 1, pos.getZ(), 6F, Explosion.BlockInteraction.BREAK);
        world.explode(igniter, pos.getX()+6, pos.getY() + 1, pos.getZ()+6, 6F, Explosion.BlockInteraction.BREAK);
        world.explode(igniter, pos.getX()-6, pos.getY() + 1, pos.getZ()+6, 6F, Explosion.BlockInteraction.BREAK);
        world.explode(igniter, pos.getX()+6, pos.getY() + 1, pos.getZ()-6, 6F, Explosion.BlockInteraction.BREAK);
        world.explode(igniter, pos.getX()-6, pos.getY() + 1, pos.getZ()-6, 6F, Explosion.BlockInteraction.BREAK);
        world.removeBlock(pos, false);

    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.hasNeighborSignal(pos)) {
            onCaughtFire(state, worldIn, pos, null, null);
        }
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 280;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(state, worldIn, pos, player, handIn, p_225533_6_);
        } else {
            onCaughtFire(state, worldIn, pos, p_225533_6_.getDirection(), player);
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(handIn);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!worldIn.isClientSide && projectile instanceof AbstractArrow) {
            AbstractArrow abstractarrowentity = (AbstractArrow)projectile;
            Entity entity = abstractarrowentity.getOwner();
            if (abstractarrowentity.isOnFire()) {
                BlockPos blockpos = hit.getBlockPos();
                onCaughtFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            }
        }

    }

    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isClientSide) {
            onCaughtFire(worldIn.getBlockState(pos), worldIn, pos, null,null);
        }
    }
}
