package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.SphericalExplosion;
import com.cannolicatfish.rankine.init.RankineItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.EventHooks;

public class AntimatterBlock extends Block {
    public static final MapCodec<AntimatterBlock> CODEC = simpleCodec(AntimatterBlock::new);

    /** Blast radius of the antimatter explosion, unchanged from 1.18.2 (AntimatterBlock.java:40 at 859f87773). */
    public static final float EXPLOSION_RADIUS = 12f;

    /** See {@link #explode}. Named so a test can bind to the real value instead of restating a literal. */
    public static final Explosion.BlockInteraction EXPLOSION_MODE = Explosion.BlockInteraction.DESTROY_WITH_DECAY;

    @Override
    public MapCodec<? extends AntimatterBlock> codec() {
        return CODEC;
    }

    public AntimatterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        explode(worldIn, pos, null);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (stack.getItem() != RankineItems.PENNING_TRAP.get()) explode(worldIn, pos, player);
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void entityInside(BlockState p_60495_, Level levelIn, BlockPos posIn, Entity entityIn) {
        explode(levelIn, posIn, entityIn);
    }

    /**
     * Radius-12 spherical blast. {@link Explosion.BlockInteraction#DESTROY_WITH_DECAY}, not
     * {@code DESTROY}: 1.19.3 split the old decaying {@code DESTROY} in two and the port kept the
     * name rather than the meaning.
     * <p>
     * Baseline evidence is in the mod's own explosion class, so it does not depend on vanilla
     * naming: at {@code 859f87773}, {@code SphericalExplosion#finalizeExplosion} (SphericalExplosion.java:149)
     * added {@code LootContextParams.EXPLOSION_RADIUS} exactly when it was handed
     * {@code BlockInteraction.DESTROY}, and this call site handed it {@code DESTROY}
     * (AntimatterBlock.java:40) — so antimatter drops decayed in 1.18.2. In 1.21.1 the same class
     * delegates to {@code BlockState#onExplosionHit}, which adds that parameter only for
     * {@code DESTROY_WITH_DECAY} (BlockBehaviour.java:189-190); plain {@code DESTROY} means "break
     * everything, full drops". Without the radius parameter the {@code minecraft:explosion_decay}
     * loot function is a no-op, so a radius-12 blast dropped every block it broke instead of ~1/12
     * of them.
     * <p>
     * The rest of the mod was ported correctly and corroborates the mapping: {@code GasUtilsEnum}
     * maps 1.18.2 {@code NONE} to {@code KEEP}, and the cannonball/carcass/enderball entities map
     * the mob-griefing {@code DESTROY} to {@code Level.ExplosionInteraction.MOB}, which
     * {@code Level#explode} resolves to {@code DESTROY_WITH_DECAY} (Level.java:733-741).
     */
    public static void explode(Level levelIn, BlockPos posIn, Entity entityIn) {
        SphericalExplosion explosion = new SphericalExplosion(levelIn, entityIn, null, null, posIn.getX(), posIn.getY(), posIn.getZ(), EXPLOSION_RADIUS, false, EXPLOSION_MODE, ParticleTypes.PORTAL);
        if (!EventHooks.onExplosionStart(levelIn, explosion)) {
            if (!levelIn.isClientSide) explosion.explode();
            explosion.finalizeExplosion(true);
            levelIn.removeBlock(posIn, false);
        }
    }
}
