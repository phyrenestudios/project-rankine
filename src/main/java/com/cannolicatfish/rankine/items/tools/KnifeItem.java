package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class KnifeItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public KnifeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
    }

    protected static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        for (var entry : stack.getTagEnchantments().entrySet()) {
            if (entry.getKey().is(key)) return entry.getIntValue();
        }
        return 0;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 20.0F;
        } else if (state.is(BlockTags.LEAVES)) {
            return 10.0F;
        } else {
            // PORT-TODO: Material.VEGETABLE approximation — Material is gone in 1.21; SWORD_EFFICIENT
            // is the vanilla tag covering the old 1.5F sword-speed blocks.
            return !state.is(BlockTags.SWORD_EFFICIENT) ? 1.0F : 1.5F;
        }
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player && ((Player)entityLiving).getOffhandItem().getItem() == this) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i < 0) return;
            ((Player) entityLiving).getCooldowns().addCooldown(this, 10);
        }
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (handIn == InteractionHand.OFF_HAND) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        applyHitEffects(stack,target,attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    protected void applyHitEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {


        int i = getEnchantmentLevel(stack, RankineEnchantments.POISON_ASPECT);
        if (i > 0) {
            if (target.getType().is(EntityTypeTags.UNDEAD)) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,i * 60));
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.POISON,i * 60));
            }

        }
    }

    @Override
    public net.minecraft.world.InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity.level().isClientSide) return net.minecraft.world.InteractionResult.PASS;
        if (entity instanceof net.neoforged.neoforge.common.IShearable target) {
            BlockPos pos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
            if (target.isShearable(playerIn, stack, entity.level(), pos)) {
                // Note: fortune bonus parameter no longer exists on IShearable#onSheared in 1.21.
                java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.level(), pos);
                java.util.Random rand = new java.util.Random();
                drops.forEach(d -> {
                    net.minecraft.world.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                    if (ent != null) {
                        ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                    }
                });
                entity.hurt(entity.level().damageSources().generic(),2);
                stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(hand));
            }
            return net.minecraft.world.InteractionResult.SUCCESS;
        }
        return net.minecraft.world.InteractionResult.PASS;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(worldIn.getBlockState(pos).getBlock() instanceof LeavesBlock && getEnchantmentLevel(stack, RankineEnchantments.GRAFTING) >= 1 && !worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
                && !worldIn.restoringBlockSnapshots) {
            ResourceLocation orig = BuiltInRegistries.BLOCK.getKey(worldIn.getBlockState(pos).getBlock());
            if (orig != null) {
                ResourceLocation rs = ResourceLocation.fromNamespaceAndPath(orig.getNamespace(), orig.getPath().split("_leaves")[0] + "_sapling");
                Optional<Block> sapling = BuiltInRegistries.BLOCK.getOptional(rs);
                if (sapling.isPresent()) {
                    double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(sapling.get()));
                    itementity.setDefaultPickUpDelay();
                    worldIn.addFreshEntity(itementity);
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.SWEEPING_EDGE) || enchantment.is(Enchantments.FIRE_ASPECT) || enchantment.is(Enchantments.KNOCKBACK)) {
            return false;
        }
        return super.supportsEnchantment(stack,enchantment);
    }
}
