package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantmentTypes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class PryingEnchantment extends Enchantment {
    public PryingEnchantment(Enchantment.Rarity p_i46721_1_, EquipmentSlot... p_i46721_2_) {
        super(p_i46721_1_, RankineEnchantmentTypes.CROWBAR, p_i46721_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }

    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity && (!(target instanceof Player) || Config.GENERAL.PLAYER_PRYING_ENCHANTMENT.get()))
        {
            int player = target instanceof Player ? 1 : 0;
            LivingEntity ent = (LivingEntity) target;
            if (ent.hasItemInSlot(EquipmentSlot.MAINHAND) && ent.getCommandSenderWorld().getRandom().nextFloat() <= Math.pow(2f,level + 2 - ent.getItemBySlot(EquipmentSlot.MAINHAND).getRarity().ordinal() - player)/100) {

                ent.spawnAtLocation(ent.getItemBySlot(EquipmentSlot.MAINHAND));
                ent.setItemSlot(EquipmentSlot.MAINHAND,ItemStack.EMPTY);
            }
        }
    }
}
