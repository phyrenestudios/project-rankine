package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyShovelItem;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BreakSpeedHandler {

    public static void onBreakSpeed( PlayerEvent.BreakSpeed event) {
        Item heldItem = event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem ) && event.getState().isIn(BlockTags.LOGS) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }
        ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem());
        if (configSpec != null && configSpec.get()) {
            event.setNewSpeed(0f);
        }

        if (event.getPlayer().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_HASTENING.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        if (event.getPlayer().getEntityWorld().getBlockState(event.getPos().up()).matchesBlock(RankineBlocks.STUMP.get()) && !(event.getPlayer().getHeldItemMainhand().getItem() instanceof AlloyShovelItem)) {
            event.setNewSpeed(event.getNewSpeed()/2f);
        }

        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE, event.getPlayer().getHeldItemMainhand()) > 0) {
            int enchant = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand());
            int height = event.getPos().getY();

            float maxPercent = .40f + (enchant - 1) * .10f;
            int minHeight = 10;
            int maxHeight = 45 + (enchant - 1) * 35;
            if (height < minHeight) {
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * maxPercent);
            } else if (height < maxHeight){
                float minPercent = .10f + (enchant - 1) * 0.05f;

                float[] s = RankineMathHelper.linspace(maxPercent, minPercent, maxHeight-minHeight);
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * s[height - 10]);
            }
        }

        if (event.getPlayer().world.getBlockState(event.getPos().up()).matchesBlock(RankineBlocks.STUMP.get()) && !event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem().isIn(RankineTags.Items.SHOVELS)) {
            event.setNewSpeed(event.getNewSpeed() / 5);
        }
    }

    public static void treeChop(PlayerEvent.BreakSpeed event) {
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        World worldIn = player.world;
        BlockState state = event.getState();

        if (Config.GENERAL.TREE_CHOPPING.get() && !player.isCreative() && !player.isSneaking() && player.getHeldItemMainhand().getItem().isIn(RankineTags.Items.TREE_CHOPPERS) && state.isIn(RankineTags.Blocks.TREE_LOGS)) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            boolean alive = false;

            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (BlockPos b : BlockPos.getAllInBoxMutable(cp.add(-1,-1,-1), cp.add(1,1,1))) {
                        BlockState target = worldIn.getBlockState(b.toImmutable());
                        if (worldIn.getBlockState(cp).isIn(RankineTags.Blocks.TREE_LOGS) && target.isIn(RankineTags.Blocks.TREE_LOGS)) {
                            toCheck.add(b.toImmutable());
                        } else if (target.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                            if (target.getBlock() instanceof LeavesBlock) {
                                if (!target.get(LeavesBlock.PERSISTENT)) { /*&& target.get(LeavesBlock.DISTANCE) <= 5*/
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
}
