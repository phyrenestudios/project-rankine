package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyShovelItem;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BreakSpeedHandler {

    public static void treeChop(PlayerEvent.BreakSpeed event) {
        if (event.getPosition().isEmpty()) return;
        BlockPos pos = event.getPosition().get();
        Player player = event.getEntity();
        Level worldIn = player.level;
        BlockState state = event.getState();

        if (Config.GENERAL.TREE_CHOPPING.get() && !player.isCreative() && !player.isShiftKeyDown() && player.getMainHandItem().is(RankineTags.Items.TREE_CHOPPERS) && state.is(RankineTags.Blocks.TREE_LOGS)) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            boolean alive = false;

            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (BlockPos b : BlockPos.betweenClosed(cp.offset(-1,-1,-1), cp.offset(1,1,1))) {
                        BlockState target = worldIn.getBlockState(b.immutable());
                        if (worldIn.getBlockState(cp).is(RankineTags.Blocks.TREE_LOGS) && target.is(RankineTags.Blocks.TREE_LOGS)) {
                            toCheck.add(b.immutable());
                        } else if (target.is(RankineTags.Blocks.TREE_LEAVES)) {
                            if (target.getBlock() instanceof LeavesBlock) {
                                if (!target.getValue(LeavesBlock.PERSISTENT)) { /*&& target.get(LeavesBlock.DISTANCE) <= 5*/
                                    alive = true;
                                }
                            } else {
                                alive = true;
                            }
                        }
                    }
                }
            }
            if (alive) event.setNewSpeed((event.getNewSpeed() / checkedBlocks.size()) * Config.GENERAL.TREE_CHOP_SPEED.get().floatValue());
        }

    }

    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState targetBS = event.getState();
        Item heldItem = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem) && event.getState().is(BlockTags.LOGS) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }

        ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getEntity().getMainHandItem().getItem());
        if (configSpec != null && configSpec.get()) {
            event.setNewSpeed(0f);
        }

        if (event.getEntity().getOffhandItem().getItem() == RankineItems.TOTEM_OF_HASTENING.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.QUAKE.get(),event.getEntity().getMainHandItem()) > 0) {
            int enchant = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.QUAKE.get(),event.getEntity().getMainHandItem());
            if (event.getPosition().isEmpty()) return;
            int height = event.getPosition().get().getY();

            float maxPercent = .40f + (enchant - 1) * .10f;
            int minHeight = 10;
            float finalSpeed;
            int maxHeight = 45 + (enchant - 1) * 35;
            if (height < minHeight) {
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * maxPercent);
            } else if (height < maxHeight){
                float minPercent = .10f + (enchant - 1) * 0.05f;

                float[] s = RankineMathHelper.linspace(maxPercent,minPercent,maxHeight-minHeight);
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * s[height - 10]);
            }
        }
        if (event.getPosition().isEmpty()) return;
        if (event.getEntity().getLevel().getBlockState(event.getPosition().get().above()).is(RankineBlocks.STUMP.get()) && !(event.getEntity().getMainHandItem().getItem() instanceof AlloyShovelItem)) {
            event.setNewSpeed(event.getNewSpeed()/2f);
        }
    }
}
