package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.SphericalExplosion;
import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.ItemInteractionResult;
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
    public static final MapCodec<UnamedExplosiveBlock> CODEC = simpleCodec(UnamedExplosiveBlock::new);

    /**
     * F31 (batch 4). 1.18.2 (859f87773:UnamedExplosiveBlock.java:34-38) passed
     * {@code Explosion.BlockInteraction.BREAK} at all five blast centers — full drops, no decay,
     * unconditionally. See
     * {@link com.cannolicatfish.rankine.entities.ReactiveItemEntity#EXPLOSION_MODE} and
     * {@link SphericalExplosion#vanillaExplode} for the same fix applied to the same family of bug.
     */
    public static final Explosion.BlockInteraction EXPLOSION_MODE = Explosion.BlockInteraction.DESTROY;

    @Override
    public MapCodec<? extends UnamedExplosiveBlock> codec() {
        return CODEC;
    }

    public UnamedExplosiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        // F31: Level.ExplosionInteraction.BLOCK resolves through GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY
        // (default ON) to the decaying DESTROY_WITH_DECAY, NOT baseline's unconditional full-drop BREAK.
        // The old comment here had this backwards. Use the literal mode via vanillaExplode instead.
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX(), pos.getY() + 1, pos.getZ(), 6F, false, EXPLOSION_MODE);
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX()+6, pos.getY() + 1, pos.getZ()+6, 6F, false, EXPLOSION_MODE);
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX()-6, pos.getY() + 1, pos.getZ()+6, 6F, false, EXPLOSION_MODE);
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX()+6, pos.getY() + 1, pos.getZ()-6, 6F, false, EXPLOSION_MODE);
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX()-6, pos.getY() + 1, pos.getZ()-6, 6F, false, EXPLOSION_MODE);
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
    protected ItemInteractionResult useItemOn(ItemStack itemstack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            onCaughtFire(state, worldIn, pos, p_225533_6_.getDirection(), player);
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(handIn));
                } else {
                    itemstack.shrink(1);
                }
            }

            return ItemInteractionResult.SUCCESS;
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

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isClientSide) {
            onCaughtFire(worldIn.getBlockState(pos), worldIn, pos, null,null);
        }
    }
}
