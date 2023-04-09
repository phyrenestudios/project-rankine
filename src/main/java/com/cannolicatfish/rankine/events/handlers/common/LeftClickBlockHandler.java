package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickBlockHandler {
    public static void leftClickTools(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().isCreative()) return;
        if (event.getEntity().getMainHandItem().getItem() instanceof HammerItem) {
            ItemStack stack = event.getEntity().getMainHandItem();
            HammerItem hammer = (HammerItem) stack.getItem();
            Level worldIn = event.getLevel();
            BlockPos pos = event.getPos();
            Player player = event.getEntity();

            if (event.getEntity().getAttackStrengthScale(0) >= (1f)) {
                event.getEntity().resetAttackStrengthTicker();
                if (RankineEnchantmentHelper.getExcavateEnchantment(stack) > 0) {
                    hammer.getExcavationResult(pos, worldIn, player, stack);
                } else {
                    hammer.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
                }
            } else {
                event.getEntity().resetAttackStrengthTicker();
            }
        } else if (event.getEntity().getMainHandItem().getItem() instanceof CrowbarItem) {
            ItemStack stack = event.getEntity().getMainHandItem();
            CrowbarItem crowbar = (CrowbarItem) stack.getItem();
            Level worldIn = event.getLevel();
            BlockPos pos = event.getPos();
            Player player = event.getEntity();

            if (event.getEntity().getAttackStrengthScale(0) >= (1f)) {
                event.getEntity().resetAttackStrengthTicker();
                crowbar.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
            } else {
                event.getEntity().resetAttackStrengthTicker();
            }
        }
    }
}
