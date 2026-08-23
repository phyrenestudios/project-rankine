package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyHoeItem;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxeItem;
import com.cannolicatfish.rankine.recipe.ForagingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.*;

/**
 * 1.21.1 notes:
 * - BlockEvent moved to net.neoforged.neoforge.event.level.
 * - Material is gone: the old {@code getMaterial() == Material.DIRT/SAND} stump check becomes
 *   {@code RankineTags.Blocks.STUMP_BASES} (F30 batch 4) — NOT {@code BlockTags.DIRT}/
 *   {@code BlockTags.SAND}, which include grass_block/mycelium (Material.GRASS in 1.18.2, never a
 *   stump base) and exclude gravel/soul_sand (Material.SAND in 1.18.2, always one).
 * - Block#getRegistryName() is gone -> BuiltInRegistries.BLOCK.getKey(block).
 * - Forge tag manager lookups -> BlockState#is(TagKey); Tags.Biomes.IS_END -> BiomeTags.IS_END.
 * - hurtAndBreak(amount, entity, slot) replaces hurtAndBreak + broadcastBreakEvent.
 * - Enchantment levels (vanilla and Rankine) resolve through RankineEnchantmentHelper.
 */
public class BlockBreakHandler {
    public static void blockBreakingEvents(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level levelIn = player.level();
        RandomSource rand = levelIn.random;
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
                        BlockState below = levelIn.getBlockState(b.below());
                        // F30: 1.18.2 gated this on Material.DIRT/Material.SAND, which excluded
                        // grass_block/mycelium (Material.GRASS). #minecraft:dirt is not the same set
                        // — it additionally carries grass_block/mycelium/moss_block, and this mod's
                        // own tags provider adds its GRASS_BLOCKS/MYCELIUM_BLOCKS to it too — so a
                        // tree standing on ordinary grass (the normal case in every overworld biome)
                        // was silently stumping instead of dropping its bottom log.
                        // RankineTags.Blocks.STUMP_BASES reproduces the exact 1.18.2 membership.
                        if (Config.GENERAL.STUMP_CREATION.get() && below.is(RankineTags.Blocks.STUMP_BASES)) {
                            levelIn.setBlock(b, RankineBlocks.STUMP.get().defaultBlockState(),3);
                        } else {
                            if (RankineEnchantmentHelper.getLevel(levelIn, RankineEnchantments.ENDOTHERMIC, player.getMainHandItem()) > 0) {
                                Block.popResource(levelIn,b,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(levelIn,levelIn.getBlockState(b))));
                                levelIn.destroyBlock(b,false);
                            } else {
                                if (offHandItem == RankineItems.TOTEM_OF_PROMISING.get()) {
                                    if (event.getState().is(RankineTags.Blocks.PROMISING_TOTEM_BLOCKS)) {
                                        if (rand.nextFloat() < Config.GENERAL.TOTEM_PROMISING_CHANCE.get()) {
                                            Block.dropResources(levelIn.getBlockState(b),levelIn,pos);
                                        }
                                    }
                                }
                                levelIn.destroyBlock(b, true);
                            }
                        }

                    }

                    for (BlockPos b : leaves) {
                        BlockState LEAF = levelIn.getBlockState(b);
                        LeavesBlock.dropResources(LEAF,levelIn,pos);
                        levelIn.removeBlock(b,false);
                        ResourceLocation leafId = BuiltInRegistries.BLOCK.getKey(LEAF.getBlock());
                        if (levelIn.getRandom().nextFloat() < Config.GENERAL.LEAF_LITTER_GEN_TREES.get() && leafId != null) {
                            ResourceLocation litterId = ResourceLocation.fromNamespaceAndPath("rankine", leafId.getPath().replace("leaves", "leaf_litter"));
                            if (BuiltInRegistries.BLOCK.containsKey(litterId)) {
                                levelIn.setBlockAndUpdate(b, BuiltInRegistries.BLOCK.get(litterId).defaultBlockState());
                            }
                        }
                    }
                    levelIn.playSound(null,pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS,1.0f,0.8f);
                    player.getMainHandItem().hurtAndBreak(logs.size()-1, player, EquipmentSlot.MAINHAND);
                    event.setCanceled(true);
                    return;
                }
            }


            //Foraging
            if (mainHandItem.getItem() instanceof AlloyHoeItem || mainHandItem.getItem().equals(RankineItems.FLINT_HOE.get()) && !(RankineEnchantmentHelper.getLevel(levelIn, Enchantments.SILK_TOUCH, mainHandItem) > 0)) {
                ResourceLocation biomeName = levelIn.getBiome(pos).unwrapKey().map(k -> k.location()).orElse(null);
                ItemStack itemStack = ForagingRecipe.getForagingResult(levelIn, biomeName, targetBlockState, RankineEnchantmentHelper.getLevel(levelIn, RankineEnchantments.FORAGING, mainHandItem) > 0);
                if (!itemStack.isEmpty()) {
                    Block.popResource(levelIn, pos, itemStack);
                    levelIn.destroyBlock(pos, false);
                }
            }

            if (targetBlockState.is(BlockTags.LOGS_THAT_BURN) && RankineEnchantmentHelper.getLevel(levelIn, RankineEnchantments.ENDOTHERMIC, player.getMainHandItem()) > 0 && !levelIn.isClientSide) {
                levelIn.removeBlock(pos,false);
                Block.popResource(levelIn,pos,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(levelIn,levelIn.getBlockState(pos))));
                player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                event.setCanceled(true);
                //return;
            }



            if (targetBlock.equals(Blocks.GLOWSTONE)) {
                if (RankineEnchantmentHelper.getLevel(levelIn, Enchantments.SILK_TOUCH, mainHandItem) > 0) return;
                levelIn.destroyBlock(pos, true);
                Block gas = Arrays.asList(RankineBlocks.ARGON_GAS_BLOCK.get(),RankineBlocks.NEON_GAS_BLOCK.get(),RankineBlocks.KRYPTON_GAS_BLOCK.get()).get(rand.nextInt(3));
                if (levelIn.getBiome(pos).is(BiomeTags.IS_NETHER) && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()) {
                    levelIn.setBlockAndUpdate(pos, gas.defaultBlockState());
                } else if (levelIn.getBiome(pos).is(BiomeTags.IS_END) && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()*5) {
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
                if ((player.getMainHandItem().isEmpty() && player.hasCorrectToolForDrops(levelIn.getBlockState(pos), levelIn, pos) && levelIn.getBlockEntity(pos) == null && levelIn.getFluidState(pos).isEmpty()) && !levelIn.isClientSide) {
                    levelIn.removeBlock(pos, false);
                    Block.popResource(levelIn,pos,new ItemStack(levelIn.getBlockState(pos).getBlock().asItem(), 1));
                    SoundType soundtype = levelIn.getBlockState(pos).getSoundType(levelIn, pos, null);
                    levelIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                }
            }

            //Nugget Drops
            if (targetBlockState.is(net.neoforged.neoforge.common.Tags.Blocks.STONES)) {
                if (mainHandItem.getItem() instanceof AlloyPickaxeItem) {
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
                    EquipmentSlot swingSlot = player.swingingArm == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                    player.getItemInHand(player.swingingArm).hurtAndBreak(1, player, swingSlot);
                }
            }

        } //end creative check


    }
}
