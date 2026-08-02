package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GunCottonBlock extends Block {
    public static final MapCodec<GunCottonBlock> CODEC = simpleCodec(GunCottonBlock::new);

    @Override
    public MapCodec<? extends GunCottonBlock> codec() {
        return CODEC;
    }

    public GunCottonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        // PORT-NOTE: Explosion.BlockInteraction.BREAK is gone; Level#explode now takes a Level.ExplosionInteraction.
        // BLOCK resolves to DESTROY_WITH_DECAY/DESTROY per the block-explosion-drop-decay gamerule, i.e. the old
        // BREAK behaviour (blocks destroyed, drops preserved).
        world.explode(igniter, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 1.5F, Level.ExplosionInteraction.BLOCK);
        world.removeBlock(pos, false);
    }

    @Override
    protected void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
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

    // PORT-NOTE: Block#use split into useWithoutItem/useItemOn. This branch is item-driven, so it becomes
    // useItemOn; the old `super.use(...)` fallback is now `super.useItemOn(...)`.
    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemstack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.useItemOn(itemstack, state, worldIn, pos, player, handIn, p_225533_6_);
        } else {
            onCaughtFire(state, worldIn, pos, p_225533_6_.getDirection(), player);
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    // PORT-NOTE: the Consumer<ItemStack>/broadcastBreakEvent overload is gone; the slot-based
                    // overload broadcasts the break itself.
                    itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(handIn));
                } else {
                    itemstack.shrink(1);
                }
            }

            return ItemInteractionResult.SUCCESS;
        }
    }

    @Override
    protected void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
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

    /**
     * Return whether this block can drop from an explosion.
     */
    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return true;
    }
}
