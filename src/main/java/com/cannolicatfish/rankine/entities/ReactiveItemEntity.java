package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ReactiveItemEntity extends ItemEntity {

    /**
     * F31 (batch 4). 1.18.2 (859f87773:ReactiveItemEntity.java:67) passed
     * {@code Explosion.BlockInteraction.BREAK} — the pre-1.19.3 name for what is now plain
     * {@code DESTROY} (full drops, no decay). The port had mapped it to
     * {@code Level.ExplosionInteraction.BLOCK}, which resolves through
     * {@code GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY} (default ON) to the decaying
     * {@code DESTROY_WITH_DECAY} instead — an inversion: dropping an alkali metal into water went
     * from full drops to ~1/radius. See {@link SphericalExplosion#vanillaExplode}.
     */
    public static final Explosion.BlockInteraction EXPLOSION_MODE = Explosion.BlockInteraction.DESTROY;

    private float radius;
    private boolean canBreakBlocks;
    private Item newItem;
    private Block releasedGas;

    public ReactiveItemEntity(EntityType<? extends ItemEntity> p_i50217_1_, Level p_i50217_2_) {
        super(p_i50217_1_, p_i50217_2_);
        this.radius = 1f;
        this.canBreakBlocks = false;
    }

    public ReactiveItemEntity(Level worldIn, double x, double y, double z) {
        this(EntityType.ITEM, worldIn);
        this.setPos(x, y, z);
        this.setYRot(this.random.nextFloat() * 360.0F);
        this.radius = 1f;
        this.canBreakBlocks = false;
        this.setDeltaMovement(this.random.nextDouble() * 0.2D - 0.1D, 0.2D, this.random.nextDouble() * 0.2D - 0.1D);
    }

    public ReactiveItemEntity(Level worldIn, double x, double y, double z, ItemStack stack) {
        super(EntityType.ITEM,worldIn);
        this.setPos(x, y, z);
        this.setItem(stack);
        this.radius = 1f;
        this.canBreakBlocks = false;
        this.lifespan = stack.getEntityLifespan(worldIn);
        this.newItem = Items.AIR;
        this.releasedGas = Blocks.AIR;
    }

    public ReactiveItemEntity(Level worldIn, double x, double y, double z, float radius, boolean canBreakBlocks, ItemStack stack, Item newItem, Block releasedGas) {
        super(EntityType.ITEM,worldIn);
        this.setPos(x, y, z);
        this.setItem(stack);
        this.radius = radius;
        this.canBreakBlocks = canBreakBlocks;
        this.lifespan = stack.getEntityLifespan(worldIn);
        this.newItem = newItem;
        this.releasedGas = releasedGas;
    }

    @Override
    public void tick() {

        if (this.wasTouchingWater)
        {
            BlockPos pos = this.blockPosition();
            // F31: NONE has no gamerule indirection (Level.java:734 maps it unconditionally to
            // KEEP), so the else branch below was already correct. The break branch needs the
            // literal, gamerule-independent EXPLOSION_MODE — see its javadoc and
            // SphericalExplosion#vanillaExplode.
            if (canBreakBlocks)
            {
                SphericalExplosion.vanillaExplode(this.getCommandSenderWorld(), null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.radius, false, EXPLOSION_MODE);
            } else {
                this.getCommandSenderWorld().explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.radius, Level.ExplosionInteraction.NONE);
            }
            if (!level().isClientSide && !level().restoringBlockSnapshots && !newItem.equals(Items.AIR)) {
                double d0 = (double) (level().random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (level().random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (level().random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(level(), (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(newItem,this.getItem().getCount()));
                itementity.setDefaultPickUpDelay();
                level().addFreshEntity(itementity);
            }
            if (!level().isClientSide && !releasedGas.equals(Blocks.AIR)) {
                for (int i = 0; i < Math.max(1,Math.round(this.radius)); i++) {
                    BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> level().isEmptyBlock(B) && !(level().getBlockState(B).getBlock() instanceof GasBlock)).orElse(null);
                    if (close == null) {
                        break;
                    } else {
                        level().setBlock(close,this.releasedGas.defaultBlockState(),2);
                    }
                }
            }
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles spawn packets.
}
