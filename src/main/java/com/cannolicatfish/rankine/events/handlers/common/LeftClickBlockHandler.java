package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LeftClickBlockHandler {
    public static void onLeftClick( PlayerInteractEvent.LeftClickBlock event) {
        if (event.getPlayer().getHeldItemMainhand().getItem() instanceof HammerItem) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            HammerItem hammer = (HammerItem) stack.getItem();
            World worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();

            if (event.getPlayer().getCooledAttackStrength(0) >= (1f)) {
                event.getPlayer().resetCooldown();
                if (HammerItem.getExcavateModifier(stack) != 0)
                {
                    hammer.getExcavationResult(pos,worldIn,player,stack);
                } else {
                    hammer.onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
                }
            } else {
                event.getPlayer().resetCooldown();
            }
        } else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof CrowbarItem) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            CrowbarItem crowbar = (CrowbarItem) stack.getItem();
            World worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();

            if (event.getPlayer().getCooledAttackStrength(0) >= (1f)) {
                event.getPlayer().resetCooldown();
                crowbar.onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
            } else {
                event.getPlayer().resetCooldown();
            }
        }
    }

}
