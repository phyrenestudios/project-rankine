package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.SphericalExplosion;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlastingPowderBlock extends FallingBlock {
    // FallingBlock#codec() is abstract in 1.21.1, so the MapCodec below is mandatory.
    public static final MapCodec<BlastingPowderBlock> CODEC = simpleCodec(BlastingPowderBlock::new);

    /**
     * F31 (batch 4). 1.18.2 (859f87773:BlastingPowderBlock.java:36) passed
     * {@code Explosion.BlockInteraction.BREAK} — full drops, no decay, unconditionally. See
     * {@link com.cannolicatfish.rankine.entities.ReactiveItemEntity#EXPLOSION_MODE} and
     * {@link com.cannolicatfish.rankine.entities.SphericalExplosion#vanillaExplode} for the same
     * fix applied to the same family of bug.
     */
    public static final Explosion.BlockInteraction EXPLOSION_MODE = Explosion.BlockInteraction.DESTROY;

    @Override
    public MapCodec<? extends BlastingPowderBlock> codec() {
        return CODEC;
    }

    public BlastingPowderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
        world.removeBlock(pos, false);
        // F31: Level.ExplosionInteraction.BLOCK resolves through GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY
        // (default ON) to the decaying DESTROY_WITH_DECAY, NOT baseline's unconditional full-drop BREAK.
        // The old comment here had this backwards. Use the literal mode via vanillaExplode instead.
        SphericalExplosion.vanillaExplode(world, igniter, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 2.4F, false, EXPLOSION_MODE);
    }


    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        // PORT-NOTE: java.util.Random -> the level's RandomSource (1.18.2 allocated a fresh Random here).
        RandomSource random = worldIn.getRandom();
        if (random.nextFloat() <= 0.05f) {
            if (placer instanceof Player) {
                if (!((Player)placer).getAbilities().instabuild) {
                    onCaughtFire(state, worldIn, pos, null, null);
                }
            } else {
                onCaughtFire(state, worldIn, pos, null, null);
            }
        }
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

    // PORT-NOTE: playerWillDestroy now returns the BlockState (so callers can chain); behaviour unchanged.
    @Override
    public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        RandomSource random = worldIn.getRandom();
        if (random.nextFloat() <= 0.50f && !player.getAbilities().instabuild)
        {
            onCaughtFire(worldIn.getBlockState(pos), worldIn, pos, null,player);
        }
        return super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onLand(Level worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity p_176502_5_)  {
        onCaughtFire(this.defaultBlockState(), worldIn, pos, null, null);
    }


    /**
     * Return whether this block can drop from an explosion.
     */
    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return true;
    }
}
