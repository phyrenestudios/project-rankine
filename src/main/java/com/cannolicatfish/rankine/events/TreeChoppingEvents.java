package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;


@Mod.EventBusSubscriber
public class TreeChoppingEvents {

    @SubscribeEvent
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


    @SubscribeEvent
    public static void treeChop(BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = worldIn.getBlockState(pos);
        PlayerEntity player = event.getPlayer();

        if (Config.GENERAL.TREE_CHOPPING.get() && !player.isCreative() && !player.isSneaking() && !worldIn.isRemote && player.getHeldItemMainhand().getItem().isIn(RankineTags.Items.TREE_CHOPPERS) && state.isIn(RankineTags.Blocks.TREE_LOGS)) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Set<BlockPos> logs = new HashSet<>();
            Set<BlockPos> leaves = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            boolean alive = false;
            int forceBreak = Config.GENERAL.FORCE_BREAK.get();




            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (BlockPos b : BlockPos.getAllInBoxMutable(cp.add(-1,-1,-1), cp.add(1,1,1))) {
                        BlockState target = worldIn.getBlockState(b.toImmutable());
                        if (target.isIn(RankineTags.Blocks.TREE_LOGS)) {
                            toCheck.add(b.toImmutable());
                            logs.add(b.toImmutable());
                        }
                    }
                    for (BlockPos leaf : BlockPos.getAllInBoxMutable(cp.add(-forceBreak,-forceBreak,-forceBreak), cp.add(forceBreak,forceBreak+1,forceBreak))) {
                        if (!leaves.contains(leaf)) {
                            BlockState target = worldIn.getBlockState(leaf.toImmutable());
                            if (target.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                                if (target.getBlock() instanceof LeavesBlock) {
                                    if (!target.get(LeavesBlock.PERSISTENT) /*&& target.get(LeavesBlock.DISTANCE) <= 5*/) {
                                        leaves.add(leaf.toImmutable());
                                    }
                                } else {
                                    leaves.add(leaf.toImmutable());
                                }
                                alive = true;
                            }
                        }
                    }
                    if (logs.size() > Config.GENERAL.MAX_TREE.get() || logs.size() > player.getHeldItemMainhand().getMaxDamage() - player.getHeldItemMainhand().getDamage()) {
                        break;
                    }
                }
            }

            if (alive) {
                for (BlockPos b : logs) {
                    if (Config.GENERAL.STUMP_CREATION.get() && worldIn.getBlockState(b.down()).getBlock().isIn(Tags.Blocks.DIRT)) {
                        worldIn.setBlockState(b, RankineBlocks.STUMP.get().getDefaultState(),2);
                    } else {
                        worldIn.destroyBlock(b, true);
                    }

                }

                for (BlockPos b : leaves) {
                    BlockState LEAF = worldIn.getBlockState(b);
                    LeavesBlock.spawnDrops(LEAF,worldIn,pos);
                    worldIn.removeBlock(b,false);
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.LEAF_LITTER_GEN_TREES.get() && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(LEAF.getBlock().getRegistryName().toString().replace("leaves", "leaf_litter"))) != null) {
                        worldIn.setBlockState(b, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:"+LEAF.getBlock().getRegistryName().getPath().replace("leaves", "leaf_litter"))).getDefaultState());
                    }
                }
                worldIn.playSound(null,pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS,1.0f,0.8f);


            }

            if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                player.getHeldItemMainhand().damageItem(logs.size()-1, player, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }

            event.setCanceled(true);
        }


    }



}
