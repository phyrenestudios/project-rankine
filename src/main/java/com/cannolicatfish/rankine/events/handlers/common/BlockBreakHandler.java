package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxeItem;
import com.cannolicatfish.rankine.items.alloys.AlloyShovelItem;
import com.cannolicatfish.rankine.recipe.ForagingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BlockBreakHandler {
    public static void blockBreakingEvents(BlockEvent.BreakEvent event) {
        Level levelIn = event.getPlayer().getLevel();
        Random rand = levelIn.random;
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        BlockState targetBlockState = levelIn.getBlockState(pos);
        Block targetBlock = levelIn.getBlockState(pos).getBlock();
        ItemStack mainHandItem = player.getMainHandItem();
        Item offHandItem = player.getOffhandItem().getItem();
        float CHANCE = levelIn.getRandom().nextFloat();


        if (!player.getAbilities().instabuild) {
            if (Config.GENERAL.TREE_CHOPPING.get() && !player.isShiftKeyDown() && !levelIn.isClientSide && player.getMainHandItem().is(RankineTags.Items.TREE_CHOPPERS) && levelIn.getBlockState(pos).is(RankineTags.Blocks.TREE_LOGS)) {
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
                        for (BlockPos b : BlockPos.betweenClosed(cp.offset(-1,-1,-1), cp.offset(1,1,1))) {
                            BlockState logBS = levelIn.getBlockState(b.immutable());
                            if (logBS.is(RankineTags.Blocks.TREE_LOGS)) {
                                toCheck.add(b.immutable());
                                logs.add(b.immutable());
                            } else if (!alive && logBS.is(RankineTags.Blocks.TREE_LEAVES)) {
                                if (logBS.getBlock() instanceof LeavesBlock) {
                                    if (!logBS.getValue(LeavesBlock.PERSISTENT)) {
                                        alive = true;
                                    }
                                } else {
                                    alive = true;
                                }
                            }
                        }
                        for (BlockPos leaf : BlockPos.betweenClosed(cp.offset(-forceBreak,-forceBreak,-forceBreak), cp.offset(forceBreak,forceBreak+1,forceBreak))) {
                            if (!leaves.contains(leaf)) {
                                BlockState logBS = levelIn.getBlockState(leaf.immutable());
                                if (logBS.is(RankineTags.Blocks.TREE_LEAVES)) {
                                    if (logBS.getBlock() instanceof LeavesBlock) {
                                        if (!logBS.getValue(LeavesBlock.PERSISTENT) /*&& logBS.get(LeavesBlock.DISTANCE) <= 5*/) {
                                            leaves.add(leaf.immutable());
                                        }
                                    } else {
                                        leaves.add(leaf.immutable());
                                    }
                                }
                            }
                        }
                        if (logs.size() > Config.GENERAL.MAX_TREE.get() || logs.size() > player.getMainHandItem().getMaxDamage() - player.getMainHandItem().getDamageValue()) {
                            break;
                        }
                    }
                }

                if (alive) {
                    for (BlockPos b : logs) {
                        if (Config.GENERAL.STUMP_CREATION.get() && (levelIn.getBlockState(b.below()).getMaterial().equals(Material.DIRT) || levelIn.getBlockState(b.below()).getMaterial().equals(Material.SAND))) {
                            levelIn.setBlock(b, RankineBlocks.STUMP.get().defaultBlockState(),3);
                        } else {
                            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTHERMIC,player.getMainHandItem()) > 0) {
                                Block.popResource(levelIn,b,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(levelIn,levelIn.getBlockState(b))));
                                levelIn.destroyBlock(b,false);
                            } else {
                                levelIn.destroyBlock(b, true);
                            }
                        }

                    }

                    for (BlockPos b : leaves) {
                        BlockState LEAF = levelIn.getBlockState(b);
                        LeavesBlock.dropResources(LEAF,levelIn,pos);
                        levelIn.removeBlock(b,false);
                        if (levelIn.getRandom().nextFloat() < Config.GENERAL.LEAF_LITTER_GEN_TREES.get() && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(LEAF.getBlock().getRegistryName().toString().replace("leaves", "leaf_litter"))) != null) {
                            levelIn.setBlockAndUpdate(b, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:"+LEAF.getBlock().getRegistryName().getPath().replace("leaves", "leaf_litter"))).defaultBlockState());
                        }
                    }
                    levelIn.playSound(null,pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS,1.0f,0.8f);

                    if (levelIn.getBlockState(pos).getDestroySpeed(levelIn, pos) != 0.0F) {
                        player.getMainHandItem().hurtAndBreak(logs.size()-1, player, (p_220038_0_) -> {
                            p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }

                    event.setCanceled(true);
                    return;
                }
            }


            //Foraging
            if (mainHandItem.getItem() instanceof AlloyShovelItem || mainHandItem.getItem().equals(RankineItems.FLINT_SHOVEL.get()) && !(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, mainHandItem) > 0)) {
                ItemStack itemStack = ForagingRecipe.getForagingResult(levelIn, levelIn.getBiome(pos).value().getRegistryName(), targetBlockState, EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.FORAGING, mainHandItem) > 0);
                if (!itemStack.isEmpty()) {
                    Block.popResource(levelIn, pos, itemStack);
                    levelIn.destroyBlock(pos, false);
                }
            }

            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS_THAT_BURN).contains(targetBlock) && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTHERMIC,player.getMainHandItem()) > 0 && !levelIn.isClientSide) {
                levelIn.removeBlock(pos,false);
                Block.popResource(levelIn,pos,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(levelIn,levelIn.getBlockState(pos))));
                player.getMainHandItem().hurtAndBreak(1, player, (p_220038_0_) -> {
                    p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });

                event.setCanceled(true);
                //return;
            }



            if (targetBlock.equals(Blocks.GLOWSTONE)) {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, mainHandItem) > 0) return;
                levelIn.destroyBlock(pos, true);
                Block gas = Arrays.asList(RankineBlocks.ARGON_GAS_BLOCK.get(),RankineBlocks.NEON_GAS_BLOCK.get(),RankineBlocks.KRYPTON_GAS_BLOCK.get()).get(rand.nextInt(3));
                if (levelIn.getBiome(pos).is(BiomeTags.IS_NETHER) && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()) {
                    levelIn.setBlockAndUpdate(pos, gas.defaultBlockState());
                } else if (levelIn.getBiome(pos).is(Tags.Biomes.IS_END) && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()*5) {
                    levelIn.setBlockAndUpdate(pos, gas.defaultBlockState());
                } else if (rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()/5f) {
                    levelIn.setBlockAndUpdate(pos, gas.defaultBlockState());
                }
                return;
            }


            //Luck Pendant
            if (offHandItem == RankineItems.TOTEM_OF_PROMISING.get()) {
                if (event.getState().is(RankineTags.Blocks.PROMISING_TOTEM_BLOCKS)) {
                    if (rand.nextFloat() < Config.GENERAL.TOTEM_PROMISING_CHANCE.get()) {
                        Block.dropResources(levelIn.getBlockState(pos),levelIn,pos);
                    }
                }
            } else if (offHandItem == RankineItems.TOTEM_OF_SOFTENING.get()) {
                if ((player.getMainHandItem().isEmpty() && levelIn.getBlockState(pos).canHarvestBlock(levelIn,pos,player) && levelIn.getBlockEntity(pos) == null && levelIn.getFluidState(pos).isEmpty()) && !levelIn.isClientSide) {
                    levelIn.removeBlock(pos, false);
                    Block.popResource(levelIn,pos,new ItemStack(levelIn.getBlockState(pos).getBlock().asItem(), 1));
                    SoundType soundtype = levelIn.getBlockState(pos).getSoundType(levelIn, pos, null);
                    levelIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                }
            }

            //Nugget Drops
            if (ForgeRegistries.BLOCKS.tags().getTag(Tags.Blocks.STONE).contains(targetBlock)) {
                if (mainHandItem.getItem() instanceof AlloyPickaxeItem) {
                    /*BlockPos foundPos = null;
                    for (int x = 1; x < Config.GENERAL.NUGGET_DISTANCE.get(); x++) {
                        if (levelIn.getBlockState(pos.below(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.below(x);
                        } else if (levelIn.getBlockState(pos.above(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.above(x);
                        } else if (levelIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.south(x);
                        } else if (levelIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.north(x);
                        } else if (levelIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.east(x);
                        } else if (levelIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.west(x);
                        }
                        if (foundPos != null && rand.nextFloat() < Config.GENERAL.NUGGET_CHANCE.get() && !levelIn.isClientSide && levelIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !levelIn.restoringBlockSnapshots) {
                            Block b = levelIn.getBlockState(foundPos).getBlock();
                            ItemStack nug = ItemStack.EMPTY;
                            if (VanillaIntegration.oreNuggetMap.containsKey(b)) {
                                nug = new ItemStack(VanillaIntegration.oreNuggetMap.get(b));
                            }
                            if (!nug.isEmpty()) {
                                Block.popResource(levelIn, pos, nug);
                                break;
                            }
                        }
                    }*/

                    //Geodes
                    if (levelIn.getRandom().nextFloat() <= Config.GENERAL.GEODE_CHANCE.get() && !levelIn.isClientSide && levelIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !levelIn.restoringBlockSnapshots) {
                        Block.popResource(levelIn, pos, new ItemStack(RankineItems.GEODE.get(), 1));
                    }

                }//end pick check

                //Flint drop
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(RankineTags.Items.CRUDE_TOOLS)) {
                    if (CHANCE < Config.GENERAL.FLINT_DROP_CHANCE.get()) {
                        Block.popResource(levelIn,pos,new ItemStack(Items.FLINT,1));
                    }
                }

            } //end stone check

            //knife stuff
            if (mainHandItem.is(RankineTags.Items.KNIVES)) {
                if (levelIn.getBlockState(pos).is(RankineTags.Blocks.KNIFE_SHEARABLE)) {
                    levelIn.destroyBlock(pos,false);
                    if (levelIn.getBlockState(pos).is(Blocks.TALL_SEAGRASS)) {
                        Block.popResource(levelIn, pos, new ItemStack(Items.SEAGRASS,2));
                    } else {
                        Block.popResource(levelIn, pos, new ItemStack(targetBlock.asItem()));
                    }
                    player.getItemInHand(event.getPlayer().swingingArm).hurtAndBreak(1, player, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(event.getPlayer().swingingArm);
                    });
                }
            }

        } //end creative check


    }
}
