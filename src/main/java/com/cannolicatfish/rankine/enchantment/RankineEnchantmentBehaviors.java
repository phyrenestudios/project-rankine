package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * In-code behavior of Rankine enchantments that used to live in Enchantment subclass
 * overrides (doPostAttack etc.). Enchantment subclassing is gone in 1.21, so event
 * handlers must call these statics instead. Levels are obtained via
 * {@link RankineEnchantmentHelper}.
 */
public class RankineEnchantmentBehaviors {

    /**
     * Old PryingEnchantment#doPostAttack: chance for the target to drop its main-hand item.
     * Chance = 2^(level + 2 - rarityOrdinal - (1 if player)) / 100.
     *
     * PORT-TODO(events): call RankineEnchantmentBehaviors.applyPrying(attacker, target, level)
     * from the post-attack hook (e.g. LivingDamageEvent.Post or LivingIncomingDamageEvent
     * follow-up) when the attacker's weapon has RankineEnchantments.PRYING
     * (level via RankineEnchantmentHelper.getLevel(attacker, RankineEnchantments.PRYING, weapon)).
     */
    public static void applyPrying(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity ent && (!(target instanceof Player) || Config.GENERAL.PLAYER_PRYING_ENCHANTMENT.get())) {
            int player = target instanceof Player ? 1 : 0;
            if (ent.hasItemInSlot(EquipmentSlot.MAINHAND)
                    && ent.level().getRandom().nextFloat() <= Math.pow(2f, level + 2 - ent.getItemBySlot(EquipmentSlot.MAINHAND).getRarity().ordinal() - player) / 100) {
                ent.spawnAtLocation(ent.getItemBySlot(EquipmentSlot.MAINHAND));
                ent.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
        }
    }

    /**
     * Old PunctureEnchantment#doPostAttack: bonus magic damage of 0.5 per level.
     *
     * PORT-TODO(events): call RankineEnchantmentBehaviors.applyPuncture(attacker, target, level)
     * from the post-attack hook (e.g. LivingDamageEvent.Post) when the attacker's spear has
     * RankineEnchantments.PUNCTURE.
     */
    public static void applyPuncture(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity ent) {
            ent.hurt(ent.damageSources().magic(), level * 0.5f);
        }
    }
}
