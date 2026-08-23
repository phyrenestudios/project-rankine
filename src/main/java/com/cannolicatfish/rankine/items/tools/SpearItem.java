package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.entities.SpearEntity;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item.Properties;

public class SpearItem extends Item {
    private final float attackDamage;
    private final float attackSpeedIn;
    public ResourceLocation type;
    private Tier tier;

    public SpearItem(Tier tier, float attackDamageIn, float attackSpeedIn, ResourceLocation type, Properties properties) {
        super(properties.durability(tier.getUses())
                .attributes(createAttributes(tier, attackDamageIn, attackSpeedIn))
                .component(DataComponents.TOOL, createToolProperties()));
        this.attackSpeedIn = attackSpeedIn;
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        this.type = type;
        this.tier = tier;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamageIn, float attackSpeedIn) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamageIn + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeedIn, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    /** Matches the old mineBlock behavior (2 durability per block broken) via the TOOL component. */
    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0F, 2);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.isEnchanted();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerentity) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 10) {
                float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, playerentity);
                if (!(f > 0.0F) || playerentity.isInWaterOrRain()) {
                    if (!isTooDamagedToUse(stack)) {
                        Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
                                .orElse(SoundEvents.TRIDENT_THROW);
                        if (!worldIn.isClientSide) {
                            stack.hurtAndBreak(1, playerentity, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                            if (f == 0.0F) {
                                // PORT-TODO(entities): verify SpearEntity ctor (Level, LivingEntity, ItemStack, EntityType<SpearEntity>, ResourceLocation, float)
                                SpearEntity spearentity = new SpearEntity(worldIn, playerentity, stack, RankineEntityTypes.ALLOY_SPEAR.get(), type, this.attackDamage);
                                spearentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 2.5F, 1.0F);
                                if (playerentity.getAbilities().instabuild) {
                                    spearentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                }

                                worldIn.addFreshEntity(spearentity);
                                worldIn.playSound((Player) null, spearentity, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                                if (!playerentity.getAbilities().instabuild) {
                                    playerentity.getInventory().removeItem(stack);
                                }
                            }
                        }

                        playerentity.awardStat(Stats.ITEM_USED.get(this));
                        if (f > 0.0F) {
                            float f7 = playerentity.getYRot();
                            float f1 = playerentity.getXRot();
                            float f2 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f1 * ((float) Math.PI / 180F));
                            float f3 = -Mth.sin(f1 * ((float) Math.PI / 180F));
                            float f4 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f1 * ((float) Math.PI / 180F));
                            float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
                            f2 *= f / f5;
                            f3 *= f / f5;
                            f4 *= f / f5;
                            playerentity.push((double) f2, (double) f3, (double) f4);
                            playerentity.startAutoSpinAttack(20, this.attackDamage, stack);
                            if (playerentity.onGround()) {
                                float f6 = 1.1999999F;
                                playerentity.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                            }

                            worldIn.playSound((Player) null, playerentity, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (isTooDamagedToUse(itemstack)) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, playerIn) > 0.0F && !playerIn.isInWaterOrRain()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
    }

    /** Old 1.18.2 threshold (no -1 like vanilla TridentItem) kept for 1:1 behavior. */
    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage();
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    /**
     * The ANVIL / enchanted-book path. 1.18.2 had no such allowance — {@code canEnchant} used the
     * plain {@code TRIDENT} category, which a Rankine spear is not — but this is decision 3B
     * ("allow BOTH"), so the port's extra allowance is deliberately kept.
     */
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.IMPALING) || enchantment.is(Enchantments.LOYALTY)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    /**
     * The ENCHANTING TABLE path — and the one 1.18.2 actually had.
     * {@code SpearItem#canApplyAtEnchantingTable} (SpearItem.java:189 at {@code 859f87773}) returned
     * true for Impaling and Loyalty, and that is the hook Forge patched into
     * {@code EnchantmentHelper#getAvailableEnchantmentResults}, so a spear could roll both at a
     * table despite not being a Trident. 1.21's equivalent is {@code isPrimaryItemFor}
     * (IItemExtension.java:453), which the port never overrode: the default there ANDs
     * {@code supportsEnchantment} with {@code stack.is(enchantment.primaryItems())}, and Impaling's
     * primary_items is {@code #minecraft:enchantable/trident} — a tag Rankine never populates. So
     * the table stopped offering both while the anvil started, i.e. the allowance moved rather than
     * being added.
     *
     * <p>Returning true here bypasses the tag entirely, which is the point: the 1.18.2 rule was
     * item-side, not tag-side. Everything else still defers to super, so the spear gains nothing
     * beyond the two enchantments the baseline named.
     */
    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.IMPALING) || enchantment.is(Enchantments.LOYALTY)) {
            return true;
        }
        return super.isPrimaryItemFor(stack, enchantment);
    }
}
