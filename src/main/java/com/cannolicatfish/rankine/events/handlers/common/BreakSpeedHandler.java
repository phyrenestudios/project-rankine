package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import static net.minecraft.block.Block.spawnAsEntity;

public class BreakSpeedHandler {

    public static void onBreakSpeed( PlayerEvent.BreakSpeed event) {
        BlockState targetBS = event.getState();
        Item heldItem = event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem ) && event.getState().isIn(BlockTags.LOGS) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }

        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && heldItem == Items.WOODEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && heldItem == Items.WOODEN_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && heldItem == Items.WOODEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && heldItem == Items.WOODEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && heldItem == Items.WOODEN_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && heldItem == Items.STONE_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && heldItem == Items.STONE_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && heldItem == Items.STONE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && heldItem == Items.STONE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && heldItem == Items.STONE_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && heldItem == Items.IRON_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && heldItem == Items.IRON_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && heldItem == Items.IRON_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && heldItem == Items.IRON_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && heldItem == Items.IRON_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && heldItem == Items.GOLDEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && heldItem == Items.GOLDEN_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && heldItem == Items.GOLDEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && heldItem == Items.GOLDEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && heldItem == Items.GOLDEN_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && heldItem == Items.DIAMOND_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && heldItem == Items.DIAMOND_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && heldItem == Items.DIAMOND_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && heldItem == Items.DIAMOND_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && heldItem == Items.DIAMOND_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && heldItem == Items.NETHERITE_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && heldItem == Items.NETHERITE_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && heldItem == Items.NETHERITE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && heldItem == Items.NETHERITE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && heldItem == Items.NETHERITE_HOE) { event.setNewSpeed(0f); }

        if (event.getPlayer().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_HASTENING.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        if (targetBS.isIn(RankineTags.Blocks.COBBLES) || targetBS.matchesBlock(RankineBlocks.STUMP.get())) {
            event.setNewSpeed(event.getNewSpeed()/2f);
        }

        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE, event.getPlayer().getHeldItemMainhand()) > 0) {
            int enchant = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand());
            int height = event.getPos().getY();

            float maxPercent = .40f + (enchant - 1) * .10f;
            int minHeight = 10;
            float finalSpeed;
            int maxHeight = 45 + (enchant - 1) * 35;
            if (height < minHeight) {
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * maxPercent);
            } else if (height < maxHeight){
                float minPercent = .10f + (enchant - 1) * 0.05f;

                float[] s = RankineMathHelper.linspace(maxPercent, minPercent, maxHeight-minHeight);
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * s[height - 10]);
            }
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
