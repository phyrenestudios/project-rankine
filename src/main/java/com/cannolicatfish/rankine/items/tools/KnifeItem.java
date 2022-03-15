package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.UUID;

public class KnifeItem extends SwordItem {
    private final float attackDamage;
    private final float attackSpeed;

    public KnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamageBonus();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 20.0F;
        } else if (state.is(BlockTags.LEAVES)) {
            return 10.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.CORAL && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }


    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).getOffhandItem().getItem() == this) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 0) return;
            ((PlayerEntity) entityLiving).getCooldowns().addCooldown(this, 10);
        }
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (handIn == Hand.OFF_HAND) {
            playerIn.startUsingItem(handIn);
            return ActionResult.consume(itemstack);
        } else {
            return ActionResult.pass(playerIn.getItemInHand(handIn));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        applyHitEffects(stack,target,attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    protected void applyHitEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {


        int i = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.POISON_ASPECT,stack);
        if (i > 0) {
            if (target.getMobType() == CreatureAttribute.UNDEAD) {
                target.addEffect(new EffectInstance(Effects.WEAKNESS,i * 60));
            } else {
                target.addEffect(new EffectInstance(Effects.POISON,i * 60));
            }

        }
    }

    @Override
    public net.minecraft.util.ActionResultType interactLivingEntity(ItemStack stack, net.minecraft.entity.player.PlayerEntity playerIn, LivingEntity entity, net.minecraft.util.Hand hand) {
        if (entity.level.isClientSide) return net.minecraft.util.ActionResultType.PASS;
        if (entity instanceof net.minecraftforge.common.IForgeShearable) {
            net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
            BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
            if (target.isShearable(stack, entity.level, pos)) {
                java.util.List<ItemStack> drops = target.onSheared(playerIn, stack, entity.level, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.BLOCK_FORTUNE, stack));
                java.util.Random rand = new java.util.Random();
                drops.forEach(d -> {
                    net.minecraft.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
                    ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                });
                entity.hurt(DamageSource.GENERIC,2);
                stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(hand));
            }
            return net.minecraft.util.ActionResultType.SUCCESS;
        }
        return net.minecraft.util.ActionResultType.PASS;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(worldIn.getBlockState(pos).getBlock() instanceof LeavesBlock && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GRAFTING,stack) >= 1 && !worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
                && !worldIn.restoringBlockSnapshots) {
            ResourceLocation orig = worldIn.getBlockState(pos).getBlock().getRegistryName();
            if (orig != null) {
                ResourceLocation rs = new ResourceLocation(orig.getNamespace(), orig.getPath().split("_leaves")[0] + "_sapling");
                Block sapling = ForgeRegistries.BLOCKS.getValue(rs);
                if (sapling != null) {
                    double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(sapling));
                    itementity.setDefaultPickUpDelay();
                    worldIn.addFreshEntity(itementity);
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SWEEPING_EDGE || enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.KNOCKBACK) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }
}



