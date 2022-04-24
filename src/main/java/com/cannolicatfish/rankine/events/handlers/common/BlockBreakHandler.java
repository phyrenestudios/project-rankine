package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static net.minecraft.block.Block.spawnAsEntity;

public class BlockBreakHandler {
    public static void blockBreakingEvents( BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        Random rand = worldIn.rand;
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        Block target = worldIn.getBlockState(pos).getBlock();
        Item mainHandItem = player.getHeldItemMainhand().getItem();
        Item offHandItem = player.getHeldItemOffhand().getItem();
        float CHANCE = worldIn.getRandom().nextFloat();


        if (!player.abilities.isCreativeMode) {
            if (Config.GENERAL.TREE_CHOPPING.get() && !player.isSneaking() && !worldIn.isRemote && player.getHeldItemMainhand().getItem().isIn(RankineTags.Items.TREE_CHOPPERS) && worldIn.getBlockState(pos).isIn(RankineTags.Blocks.TREE_LOGS)) {
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
                            BlockState logBS = worldIn.getBlockState(b.toImmutable());
                            if (logBS.isIn(RankineTags.Blocks.TREE_LOGS)) {
                                toCheck.add(b.toImmutable());
                                logs.add(b.toImmutable());
                            } else if (!alive && logBS.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                                if (logBS.getBlock() instanceof LeavesBlock) {
                                    if (!logBS.get(LeavesBlock.PERSISTENT)) {
                                        alive = true;
                                    }
                                } else {
                                    alive = true;
                                }
                            }
                        }
                        for (BlockPos leaf : BlockPos.getAllInBoxMutable(cp.add(-forceBreak,-forceBreak,-forceBreak), cp.add(forceBreak,forceBreak+1,forceBreak))) {
                            if (!leaves.contains(leaf)) {
                                BlockState logBS = worldIn.getBlockState(leaf.toImmutable());
                                if (logBS.isIn(RankineTags.Blocks.TREE_LEAVES)) {
                                    if (logBS.getBlock() instanceof LeavesBlock) {
                                        if (!logBS.get(LeavesBlock.PERSISTENT) /*&& logBS.get(LeavesBlock.DISTANCE) <= 5*/) {
                                            leaves.add(leaf.toImmutable());
                                        }
                                    } else {
                                        leaves.add(leaf.toImmutable());
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
                        if (Config.GENERAL.STUMP_CREATION.get() && (worldIn.getBlockState(b.down()).getMaterial().equals(Material.EARTH) || worldIn.getBlockState(b.down()).getMaterial().equals(Material.SAND))) {
                            worldIn.setBlockState(b, RankineBlocks.STUMP.get().getDefaultState(), 3);
                        } else {
                            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOTHERMIC, player.getHeldItemMainhand()) > 0) {
                                Block.spawnAsEntity(worldIn,b,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(worldIn, worldIn.getBlockState(b).getBlock())));
                                worldIn.destroyBlock(b,false);
                            } else {
                                worldIn.destroyBlock(b, true);
                            }
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
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);

                    if (worldIn.getBlockState(pos).getBlockHardness(worldIn, pos) != 0.0F) {
                        player.getHeldItemMainhand().damageItem(logs.size()-1, player, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }

                    event.setCanceled(true);
                    return;

                }


            }

            if (target.isIn(BlockTags.LOGS_THAT_BURN) && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOTHERMIC, player.getHeldItemMainhand()) > 0 && !worldIn.isRemote) {
                worldIn.removeBlock(pos,false);
                Block.spawnAsEntity(worldIn,pos,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(worldIn,target)));
                player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });

                event.setCanceled(true);
                //return;
            }



            if (target.matchesBlock(Blocks.GLOWSTONE) && !worldIn.isRemote) {
                Block gas = Arrays.asList(RankineBlocks.ARGON_GAS_BLOCK.get(), RankineBlocks.NEON_GAS_BLOCK.get(), RankineBlocks.KRYPTON_GAS_BLOCK.get()).get(rand.nextInt(3));
                if (worldIn.getBiome(pos).getCategory() == Biome.Category.NETHER && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()) {
                    worldIn.setBlockState(pos, gas.getDefaultState(),3);
                } else if (worldIn.getBiome(pos).getCategory() == Biome.Category.THEEND && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()*5) {
                    worldIn.setBlockState(pos, gas.getDefaultState(),3);
                } else if (rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()/5f) {
                    worldIn.setBlockState(pos, gas.getDefaultState(),3);
                }
            }


            //Luck Pendant
            if (offHandItem == RankineItems.TOTEM_OF_PROMISING.get()) {
                if (event.getState().isIn(RankineTags.Blocks.PROMISING_TOTEM_BLOCKS)) {
                    if (rand.nextFloat() < Config.GENERAL.TOTEM_PROMISING_CHANCE.get()) {
                        Block.spawnDrops(worldIn.getBlockState(pos),worldIn,pos);
                    }
                }
            } else if (offHandItem == RankineItems.TOTEM_OF_SOFTENING.get()) {
                if ((player.getHeldItemMainhand().isEmpty() && worldIn.getBlockState(pos).canHarvestBlock(worldIn,pos,player) && worldIn.getTileEntity(pos) == null && worldIn.getFluidState(pos).isEmpty()) && !worldIn.isRemote) {
                    worldIn.removeBlock(pos, false);
                    Block.spawnAsEntity(worldIn,pos,new ItemStack(worldIn.getBlockState(pos).getBlock().asItem(), 1));
                    SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
                    worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                }
            }

            //Alloy shovel perk
            /*
            if (target instanceof FallingBlock && mainHandItem instanceof AlloyShovelItem) {
                for (int i = 1; i <=5; ++i) {
                    if (mainHandItem.canHarvestBlock(worldIn.getBlockState(pos.down(i)))) {
                        worldIn.destroyBlock(pos.up(i), true);
                    }
                }
            }

             */


            //Nugget Drops
            if (Tags.Blocks.STONE.contains(target)) {
                if (mainHandItem instanceof PickaxeItem) {
                    BlockPos foundPos = null;
                    for (int x = 1; x < Config.GENERAL.NUGGET_DISTANCE.get(); x++) {
                        if (worldIn.getBlockState(pos.down(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.down(x);
                        } else if (worldIn.getBlockState(pos.up(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.up(x);
                        } else if (worldIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.south(x);
                        } else if (worldIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.north(x);
                        } else if (worldIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.east(x);
                        } else if (worldIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.west(x);
                        }
                        if (foundPos != null && rand.nextFloat() < Config.GENERAL.NUGGET_CHANCE.get() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                            Block b = worldIn.getBlockState(foundPos).getBlock();
                            ItemStack nug = ItemStack.EMPTY;
                            if (VanillaIntegration.oreNuggetMap.containsKey(b)) {
                                nug = new ItemStack(VanillaIntegration.oreNuggetMap.get(b));
                            }
                            if (!nug.isEmpty()) {
                                spawnAsEntity(worldIn, pos, nug);
                                break;
                            }
                        }
                    }

                    //Geodes
                    if (worldIn.getRandom().nextFloat() <= Config.GENERAL.GEODE_CHANCE.get() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                        spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.GEODE.get(), 1));
                    }

                }//end pick check

                //Flint drop
                if (player.getHeldItem(Hand.MAIN_HAND).getItem().isIn(RankineTags.Items.CRUDE_TOOLS)) {
                    if (CHANCE < Config.GENERAL.FLINT_DROP_CHANCE.get()) {
                        spawnAsEntity(worldIn,pos,new ItemStack(Items.FLINT,1));
                    }
                }

            } //end stone check

            //knife stuff
            if (mainHandItem.isIn(RankineTags.Items.KNIVES)) {
                if (target.isIn(RankineTags.Blocks.KNIFE_SHEARABLE)) {
                    worldIn.destroyBlock(pos,false);
                    spawnAsEntity(worldIn, pos, new ItemStack(target.asItem()));
                    if (!worldIn.isRemote) {
                        player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }
                }
            }

            //Foraging Enchantment
            if (target.isIn(Tags.Blocks.DIRT)) {
                ItemStack heldItemStack = player.getHeldItem(Hand.MAIN_HAND);

                if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.FORAGING, heldItemStack) > 0) {
                    ItemStack FOOD;
                    Biome.Category cat = worldIn.getBiome(event.getPos()).getCategory();
                    List<Item> possibleItems;
                    switch (cat) {
                        case JUNGLE:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(), Items.STRING, Items.POTATO, Items.CARROT, Items.BEETROOT, RankineItems.PINEAPPLE.get(), Items.COCOA_BEANS, Items.MELON_SEEDS);
                            break;
                        case SWAMP:
                        case MUSHROOM:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.BROWN_MUSHROOM,Items.RED_MUSHROOM);
                            break;
                        case EXTREME_HILLS:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT, RankineItems.FIRE_CLAY_BALL.get(), RankineItems.SNOWBERRIES.get());
                            break;
                        case RIVER:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.CLAY_BALL,Items.KELP,Items.SUGAR_CANE);
                            break;
                        case PLAINS:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.PUMPKIN_SEEDS,Items.FEATHER,Items.LEATHER);
                            break;
                        case DESERT:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(), RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.RABBIT_HIDE,Items.BONE);
                            break;
                        default:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT);
                            break;
                    }

                    if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get()) {
                        FOOD = new ItemStack(possibleItems.get(event.getWorld().getRandom().nextInt(possibleItems.size())));
                    } else {
                        return;
                    }
                    spawnAsEntity(worldIn,pos,FOOD);
                }
            }




        } //end creative check


    }

}
