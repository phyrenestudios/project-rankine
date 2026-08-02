package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentBehaviors;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 1.21.1 event mapping: LivingDamageEvent -> LivingDamageEvent.Pre (post-armor, still mutable —
 * same point in the damage sequence as the old event). getAmount/setAmount -> getNewDamage/setNewDamage.
 * The new LivingDamageEvent.Post hook carries the old Enchantment#doPostAttack behaviors, which
 * vanilla no longer invokes for datapack enchantments.
 */
public class LivingDamageHandler {
    public static void onLivingDamaged(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                int curSlot = i;
                if (s.getItem() instanceof AlloyArmorItem armor) {

                    if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
                        s.hurtAndBreak(1, player, LivingHurtHandler.armorSlot(curSlot));
                    } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                        s.hurtAndBreak(1 + RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOBIOTIC, s)*3, player, LivingHurtHandler.armorSlot(curSlot));
                    }
                }

            }
            // NOTE: this condition is always true (a source cannot be both WITHER and MAGIC) —
            // preserved verbatim from 1.18.2.
            if (!(event.getSource().is(DamageTypes.WITHER) && event.getSource().is(DamageTypes.MAGIC))) {
                boolean wither = false;
                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (!itemstack.isEmpty() && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
                        wither = true;
                        break;
                    }
                }
                if (wither) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER,100));
                }
            }
        }
    }

    public static void onDamageEntity(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ModConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(player.getMainHandItem().getItem());
            if (configSpec != null && configSpec.get()) {
                event.setNewDamage(1.0f);
            }

            Level playerLevel = player.level();
            if (!playerLevel.isClientSide) {
                LivingEntity receiver = event.getEntity();
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (mainHandStack.getItem() instanceof HammerItem) {
                    if ((receiver instanceof Blaze || receiver instanceof AbstractGolem || receiver instanceof AbstractSkeleton || receiver instanceof SkeletonHorse || receiver instanceof Guardian)) {
                        int endLevel = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.ENDEAVOR, mainHandStack);
                        event.setNewDamage(event.getNewDamage() + event.getNewDamage()/2f + 1.5f*endLevel);
                        if (endLevel > 0 && playerLevel.getRandom().nextFloat() < (0.15f*endLevel) && playerLevel instanceof ServerLevel serverLevel) {
                            // 1.21.1: LootTables moved to the reloadable registries; LootContext.Builder -> LootParams.Builder
                            LootTable loot = serverLevel.getServer().reloadableRegistries().getLootTable(receiver.getLootTable());
                            LootParams params = new LootParams.Builder(serverLevel)
                                    .withParameter(LootContextParams.THIS_ENTITY, receiver)
                                    .withParameter(LootContextParams.ORIGIN, receiver.position())
                                    .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().playerAttack(player))
                                    .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                                    .create(LootContextParamSets.ENTITY);
                            List<ItemStack> s = loot.getRandomItems(params);
                            if (s.size() > 1) {
                                receiver.spawnAtLocation(s.get(receiver.level().getRandom().nextInt(s.size())));
                            } else if (s.size() == 1) {
                                receiver.spawnAtLocation(s.get(0));
                            }
                        }
                    }
                }
                if ((player.getMainHandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getOffhandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()))) {
                    float damage = event.getNewDamage() + event.getNewDamage() * 0.5f;
                    event.setNewDamage(damage);
                }
                int endotoxin = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.ENDOTOXIN, mainHandStack);
                if (endotoxin >= 1) {
                    if ((receiver instanceof EnderMan || receiver instanceof Shulker || receiver instanceof Endermite || receiver.getCommandSenderWorld().dimension().equals(Level.END))) {
                        event.setNewDamage(event.getNewDamage() + 2.5f*endotoxin);
                    }
                }

                int cleanse = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.CLEANSE, mainHandStack);
                if (cleanse >= 1) {
                    float damage = cleanse * receiver.getActiveEffects().size();
                    event.setNewDamage(event.getNewDamage() + damage);
                    boolean flag = damage >= 1;
                    if (flag) {
                        receiver.removeAllEffects();
                        receiver.playSound(SoundEvents.GRINDSTONE_USE,1.0f, 1.0f);
                    }
                }

                int backstab = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.BACKSTAB, mainHandStack);
                if (backstab >= 1) {
                    if (receiver.getDirection().equals(player.getDirection())) {
                        receiver.playSound(SoundEvents.TRIDENT_HIT,1.0f, 1.0f);
                        float damage = event.getNewDamage() + event.getNewDamage() * backstab;
                        event.setNewDamage(damage);
                    }
                }

                int torque = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.TORQUE, mainHandStack);
                if (torque > 0) {

                    // 1.21.1: EntityDimensions is a record — height/width are accessor methods.
                    float size = receiver.getDimensions(receiver.getPose()).height() * receiver.getDimensions(receiver.getPose()).width();
                    float mod = -1.5f + torque;
                    float damage = event.getNewDamage() + Math.max(0,Math.min(size + mod, 1.5f *torque));
                    event.setNewDamage(damage);
                }

                // 1.18.2 computed an unused effectChance for AlloySwordItem here; dead code, dropped.
            }




            for (ItemStack armor : event.getEntity().getArmorSlots()) {
                if (armor.getItem() instanceof AlloyArmorItem alloyArmor) {
                    // 1.21.1: ItemStack#getEquipmentSlot is nullable, resolve via the wearer.
                    EquipmentSlot slot = event.getEntity().getEquipmentSlotForItem(armor);
                    int i = alloyArmor.calcDurabilityLoss(armor, event.getEntity().getCommandSenderWorld(), event.getEntity(), true);
                    armor.hurtAndBreak(i, player, slot);
                }
            }
        }

    }

    /**
     * 1.21.1: datapack enchantments have no doPostAttack hook, so the old
     * PryingEnchantment/PunctureEnchantment behavior runs here instead.
     * Post fires after the damage was actually applied, matching the old post-attack ordering.
     */
    public static void onPostDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        Level level = attacker.level();
        if (level.isClientSide) return;
        ItemStack weapon = attacker.getMainHandItem();

        int prying = RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PRYING, weapon);
        if (prying > 0) {
            RankineEnchantmentBehaviors.applyPrying(attacker, event.getEntity(), prying);
        }

        if (weapon.getItem() instanceof SpearItem) {
            int puncture = RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PUNCTURE, weapon);
            if (puncture > 0) {
                RankineEnchantmentBehaviors.applyPuncture(attacker, event.getEntity(), puncture);
            }
        }
    }
}
