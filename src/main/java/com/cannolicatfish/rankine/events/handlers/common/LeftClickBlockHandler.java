package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickBlockHandler {
    public static void leftClickTools(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getPlayer().getMainHandItem().getItem() instanceof HammerItem) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            HammerItem hammer = (HammerItem) stack.getItem();
            Level worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();

            if (event.getPlayer().getAttackStrengthScale(0) >= (1f)) {
                event.getPlayer().resetAttackStrengthTicker();
                if (HammerItem.getExcavateModifier(stack) != 0) {
                    hammer.getExcavationResult(pos, worldIn, player, stack);
                } else {
                    hammer.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
                }
            } else {
                event.getPlayer().resetAttackStrengthTicker();
            }
        } else if (event.getPlayer().getMainHandItem().getItem() instanceof CrowbarItem) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            CrowbarItem crowbar = (CrowbarItem) stack.getItem();
            Level worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();

            if (event.getPlayer().getAttackStrengthScale(0) >= (1f)) {
                event.getPlayer().resetAttackStrengthTicker();
                crowbar.mineBlock(stack, worldIn, worldIn.getBlockState(pos), pos, player);
            } else {
                event.getPlayer().resetAttackStrengthTicker();
            }
        }
    }
}
