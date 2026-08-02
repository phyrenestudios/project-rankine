package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class LeftClickBlockHandler {
    // 1.21.1: getPlayer() -> getEntity(); getWorld() -> getLevel();
    // RankineEnchantmentHelper.getExcavateEnchantment -> getLevel(level, EXCAVATE, stack).
    public static void leftClickTools(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player.isCreative()) return;
        Level worldIn = event.getLevel();
        BlockPos pos = event.getPos();

        if (player.getMainHandItem().getItem() instanceof HammerItem hammer) {
            ItemStack stack = player.getMainHandItem();

            if (player.getAttackStrengthScale(0) >= (1f)) {
                player.resetAttackStrengthTicker();
                if (RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.EXCAVATE, stack) > 0) {
                    hammer.getExcavationResult(pos, worldIn, player, stack);
                } else {
                    hammer.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
                }
            } else {
                player.resetAttackStrengthTicker();
            }
        } else if (player.getMainHandItem().getItem() instanceof CrowbarItem crowbar) {
            ItemStack stack = player.getMainHandItem();

            if (player.getAttackStrengthScale(0) >= (1f)) {
                player.resetAttackStrengthTicker();
                crowbar.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
            } else {
                player.resetAttackStrengthTicker();
            }
        }
    }
}
