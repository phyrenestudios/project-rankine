package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;


@Mod.EventBusSubscriber
public class TreeChoppingEvents {

    //private static List<BlockPos> leaves = new ArrayList<>();

    @SubscribeEvent
    public static void treeChop(BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = worldIn.getBlockState(pos);
        PlayerEntity player = event.getPlayer();

        if (!worldIn.isRemote && player.getHeldItemMainhand().getItem() instanceof AxeItem && state.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Set<BlockPos> logs = new HashSet<>();
            List<BlockPos> leaves = new ArrayList<>();
            Stack<BlockPos> toCheck = new Stack<>();
            boolean alive = false;


            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (BlockPos b : BlockPos.getAllInBoxMutable(cp.add(-1,-1,-1), cp.add(1,1,1))) {
                        BlockState target = worldIn.getBlockState(b.toImmutable());
                        if (target.getBlock().getTags().contains(new ResourceLocation("minecraft:logs"))) {
                            toCheck.add(b.toImmutable());
                            logs.add(b.toImmutable());
                        } else if (target.getBlock().getTags().contains(new ResourceLocation("minecraft:leaves"))) {
                            if (!target.get(LeavesBlock.PERSISTENT) && target.get(LeavesBlock.DISTANCE) <= 5) {
                                for (BlockPos log : logs) {
                                    if (log.distanceSq(b) <= 16) {
                                        toCheck.add(b.toImmutable());
                                        leaves.add(b.toImmutable());
                                        alive = true;
                                    }
                                }
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
                    worldIn.destroyBlock(b, true);
                }
                for (BlockPos b : leaves) {
                    worldIn.getBlockState(b).randomTick(worldIn, b, worldIn.getRandom());
                    //worldIn.getPendingBlockTicks().scheduleTick(b,worldIn.getBlockState(b).getBlock(),1);
                }
                //worldIn.playSound(player, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                player.getHeldItemMainhand().damageItem(logs.size()-1, player, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }

        }
    }


/*
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        World worldIn = event.world;

        if (!todo.isEmpty() && !worldIn.isRemote) {
            int toBreak = Math.min(leaves.size()-1, Config.GENERAL.LEAF_DECAY_SPEED.get());
            //worldIn.playSound(worldIn.getClosestPlayer(leaves.get(0).getX(),leaves.get(0).getY(),leaves.get(0).getZ(),32.0D,true), leaves.get(0), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            int i = 0;
            while (i < toBreak) {
                BlockPos p = leaves.get(0);
                worldIn.removeBlock(p, false);
                if (worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                    for (ItemStack items : Block.getDrops(worldIn.getBlockState(p), (ServerWorld) worldIn, p, null)) {
                        spawnAsEntity(worldIn, p, new ItemStack(items.getItem(), 1));
                    }
                }
                leaves.remove(p);
                ++i;
            }

        }


    }



 */







}
