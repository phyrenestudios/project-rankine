package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

/**
 * 1.21.1: PlayerEvent.BreakSpeed#getPlayer() -> getEntity(); getPos() -> getPosition()
 * (Optional&lt;BlockPos&gt;); player.level field -> level(); Forge tag manager lookups ->
 * BlockState#is(TagKey); enchantment levels via RankineEnchantmentHelper.
 */
public class BreakSpeedHandler {

    public static void treeChop(PlayerEvent.BreakSpeed event) {
        Optional<BlockPos> position = event.getPosition();
        if (position.isEmpty()) return;
        BlockPos pos = position.get();
        Player player = event.getEntity();
        Level worldIn = player.level();
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
        Player player = event.getEntity();
        Level level = player.level();
        Item heldItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem) && event.getState().is(BlockTags.LOGS) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }

        ModConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(player.getMainHandItem().getItem());
        if (configSpec != null && configSpec.get()) {
            event.setNewSpeed(0f);
        }

        if (player.getOffhandItem().getItem() == RankineItems.TOTEM_OF_HASTENING.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        Optional<BlockPos> position = event.getPosition();
        int enchant = RankineEnchantmentHelper.getLevel(level, RankineEnchantments.QUAKE, player.getMainHandItem());
        if (enchant > 0 && position.isPresent()) {
            int height = position.get().getY();

            float maxPercent = .40f + (enchant - 1) * .10f;
            int minHeight = 10;
            int maxHeight = 45 + (enchant - 1) * 35;
            if (height < minHeight) {
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * maxPercent);
            } else if (height < maxHeight){
                float minPercent = .10f + (enchant - 1) * 0.05f;

                float[] s = RankineMathHelper.linspace(maxPercent,minPercent,maxHeight-minHeight);
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * s[height - 10]);
            }
        }

        if (position.isPresent() && level.getBlockState(position.get().above()).is(RankineBlocks.STUMP.get()) && !(player.getMainHandItem().getItem() instanceof AlloyShovelItem)) {
            event.setNewSpeed(event.getNewSpeed()/2f);
        }
    }
}
