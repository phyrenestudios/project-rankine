package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyHoeItem;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class RightClickBlockHandler {

    private static Map<Block, BlockState> flintLightMap = new HashMap<>();
    public static Map<Block, Block> stripping_map = new HashMap<Block, Block>();
    public static Map<TagKey<Item>, LanternBlock> lanternMap = new HashMap<TagKey<Item>, LanternBlock>();

    static {
        flintLightMap.put(Blocks.CAMPFIRE, Blocks.CAMPFIRE.defaultBlockState().setValue(BlockStateProperties.LIT,true));
        flintLightMap.put(Blocks.SOUL_CAMPFIRE, Blocks.SOUL_CAMPFIRE.defaultBlockState().setValue(BlockStateProperties.LIT,true));
        flintLightMap.put(RankineBlocks.CHARCOAL_PIT.get(), RankineBlocks.CHARCOAL_PIT.get().defaultBlockState().setValue(BlockStateProperties.LIT,true));
        flintLightMap.put(RankineBlocks.BEEHIVE_OVEN_PIT.get(), RankineBlocks.BEEHIVE_OVEN_PIT.get().defaultBlockState().setValue(BlockStateProperties.LIT,true));

        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            stripping_map.put(Wood.getLog(), Wood.getStrippedLog());
            stripping_map.put(Wood.getWood(), Wood.getStrippedWood());
        }

        for (Block block : RankineLists.LANTERNS) {
            if (!ForgeRegistries.ITEMS.tags().isKnownTagName(ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation("forge:nuggets/"+block.getRegistryName().getPath().replace("_lantern",""))))) break;
            lanternMap.put(ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation("forge:nuggets/"+block.getRegistryName().getPath().replace("_lantern",""))), (LanternBlock) block);
        }
        lanternMap.put(Tags.Items.NUGGETS_IRON, (LanternBlock) Blocks.LANTERN);
        lanternMap.put(RankineTags.Items.NUGGETS_SULFUR, (LanternBlock) Blocks.SOUL_LANTERN);
    }


    public static void rightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getFace() == null) return;
        Player playerIn = event.getEntity();
        Level levelIn = event.getLevel();
        BlockPos posIn = event.getPos();
        BlockState stateIn = levelIn.getBlockState(posIn);
        ItemStack itemStack = playerIn.getItemInHand(event.getHand());

        // Mud creation
        if (itemStack.getItem() instanceof PotionItem && PotionUtils.getPotion(itemStack).equals(Potions.WATER)) {
            if (RankineLists.SOIL_BLOCKS.contains(levelIn.getBlockState(posIn).getBlock())) {
                levelIn.setBlockAndUpdate(posIn, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(levelIn.getBlockState(posIn).getBlock())).defaultBlockState());
                playerIn.getInventory().add(ItemUtils.createFilledResult(itemStack, playerIn, new ItemStack(Items.GLASS_BOTTLE)));
                return;
            }
        }


        if (playerIn.getMainHandItem().is(RankineTags.Items.FLINT) && playerIn.getOffhandItem().is(RankineTags.Items.FLINT) && Config.GENERAL.FLINT_FIRE.get()) {
            BlockPos pos = event.getPos();
            BlockPos blockpos1 = event.getPos().relative(event.getFace());
            Random rand = levelIn.random;
            BlockState blockState = levelIn.getBlockState(pos);
            boolean flag = false;
            if (rand.nextFloat() > Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                playerIn.swing(InteractionHand.MAIN_HAND);
                return;
            }
            if (!levelIn.isClientSide()) {
                if (flintLightMap.containsKey(blockState.getBlock())) {
                    levelIn.setBlockAndUpdate(pos, flintLightMap.get(blockState.getBlock()));
                    flag = true;
                } else if (BaseFireBlock.canBePlacedAt(levelIn, blockpos1, event.getFace())) {
                    levelIn.setBlockAndUpdate(pos.relative(event.getFace()), BaseFireBlock.getState(levelIn, blockpos1));
                    flag = true;
                }
            }

            if (flag) {
                if (!levelIn.isClientSide()) {
                    playerIn.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                    playerIn.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
                }
                playerIn.swing(InteractionHand.MAIN_HAND);
                levelIn.playSound(playerIn, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            }
            return;
        }

        //Metal Lanterns
        if (stateIn.getBlock() instanceof LanternBlock && itemStack.is(Tags.Items.NUGGETS)) {
            for (TagKey<Item> tag : ForgeRegistries.ITEMS.tags().getReverseTag(itemStack.getItem()).map(IReverseTag::getTagKeys).orElseGet(Stream::of).toList()) {
                if (lanternMap.containsKey(tag)) {
                    levelIn.setBlockAndUpdate(posIn, lanternMap.get(tag).defaultBlockState().setValue(LanternBlock.HANGING, stateIn.getValue(LanternBlock.HANGING)));
                    if (!playerIn.isCreative()) itemStack.shrink(1);
                    playerIn.swing(event.getHand());
                    levelIn.playSound(null, posIn, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return;
                }
            }
            return;
        }


        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Level worldIn = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        BlockState targetBS = worldIn.getBlockState(pos);
        Block b = targetBS.getBlock();

        if(item instanceof AxeItem) {
            //Extra items from stripping recipe
            StrippingRecipe irecipe = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.STRIPPING, new SimpleContainer(new ItemStack(b)), worldIn).orElse(null);
            if (irecipe != null) {
                if (worldIn.getRandom().nextFloat() < irecipe.getChance()) {
                    Block.popResource(event.getWorld(), event.getPos(), irecipe.getResult());
                }
            }
            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS).contains(b) && !b.getRegistryName().toString().contains("stripped") && Config.GENERAL.STRIPPABLES_STICKS.get() && worldIn.getRandom().nextFloat() < 0.3) {
                Block.popResource(event.getWorld(), event.getPos(), new ItemStack(Items.STICK, 1));
            }

            if(stripping_map.get(b) != null) {
                if(b instanceof RotatedPillarBlock) {
                    Direction.Axis axis = targetBS.getValue(RotatedPillarBlock.AXIS);
                    worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlock(pos, stripping_map.get(b).defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
                    stack.hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(event.getHand());
                    });
                    player.swing(event.getHand());
                    event.setResult(Event.Result.ALLOW);
                }
            }
        } else if (item instanceof ShovelItem) {
            if (VanillaIntegration.pathBlocks_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(b).defaultBlockState(), 2);
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(event.getHand());
                });
                player.swing(event.getHand());
                event.setResult(Event.Result.ALLOW);
            }
        } else if (item instanceof HoeItem) {
            if (VanillaIntegration.hoeables_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, RankineBlocks.TILLED_SOIL.get().defaultBlockState().setValue(TilledSoilBlock.MOISTURE, 0).setValue(TilledSoilBlock.SOIL_TYPE, VanillaIntegration.hoeables_map.get(b)), 3);
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(event.getHand());
                });
                player.swing(event.getHand());
                event.setResult(Event.Result.ALLOW);
            } else if (b instanceof DoubleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.LOWER) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.UPPER) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE.get(),stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3, blockPos ->!blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof TripleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.BOTTOM) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.MIDDLE) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.TOP) {
                        worldIn.destroyBlock(pos.below(2),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below(2))) {
                            worldIn.setBlockAndUpdate(pos.below(2), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE.get(),stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3, blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof CropBlock && item instanceof AlloyHoeItem) {

                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(CropBlock.AGE) == 7) {
                    worldIn.destroyBlock(pos,true);
                    if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                        worldIn.setBlockAndUpdate(pos,b.defaultBlockState().setValue(CropBlock.AGE, 0));
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE.get(),stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            }
        }
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level world = event.getWorld();
        Direction direction = event.getFace();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        Player player = event.getPlayer();

        if (ForgeRegistries.ITEMS.tags().getTag(RankineTags.Items.KNIVES).contains(stack.getItem()) && direction != null && hand == InteractionHand.MAIN_HAND) {
            Block target = state.getBlock();
            if ((ForgeRegistries.BLOCKS.tags().getTag(RankineTags.Blocks.GRASS_BLOCKS).contains(target)) && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                if (RankineLists.GRASS_BLOCKS.contains(target)) {
                    world.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(target)).defaultBlockState(), 3);
                } else {
                    world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                }
                player.swing(hand);
                if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    Block.popResource(world, pos.above(), new ItemStack(Items.GRASS, 1));
                    if (world.getRandom().nextFloat() < 0.2) Block.popResource(world, pos.above(), new ItemStack(RankineItems.GRASS_SEEDS.get(), 1));
                }
                if (!world.isClientSide) {
                    player.getItemInHand(hand).hurtAndBreak(1, player, (p_220038_0_) -> {
                        p_220038_0_.broadcastBreakEvent(hand);
                    });
                }
            } else if (target == RankineBlocks.AGED_CHEESE.get()) {
                player.swing(hand);
                if (state.getValue(CakeBlock.BITES) < 6) {
                    world.setBlockAndUpdate(pos, state.setValue(CakeBlock.BITES, state.getValue(CakeBlock.BITES) + 1));
                    player.addItem(new ItemStack(RankineItems.CHEESE.get(), 1));
                } else {
                    player.addItem(new ItemStack(RankineItems.CHEESE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getItemInHand(hand).hurtAndBreak(1, player, (p_220040_1_) -> {
                    p_220040_1_.broadcastBreakEvent(hand);
                });
                world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            } else if (state.getBlock() == Blocks.CAKE) {
                player.swing(hand);
                if (state.getValue(CakeBlock.BITES) < 6) {
                    world.setBlockAndUpdate(pos, state.setValue(CakeBlock.BITES, state.getValue(CakeBlock.BITES) + 1));
                    player.addItem(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                } else {
                    player.addItem(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getMainHandItem().hurtAndBreak(1, player, (p_220040_1_) -> {
                    p_220040_1_.broadcastBreakEvent(player.swingingArm);
                });
                world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            }
        } else if (ForgeRegistries.ITEMS.tags().getTag(RankineTags.Items.SLUICING_TOOLS).contains(stack.getItem()) && direction != null && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            SluicingRecipe recipe = world.getRecipeManager().getRecipeFor(RankineRecipeTypes.SLUICING, new SimpleContainer(new ItemStack(world.getBlockState(pos).getBlock()), stack), world).orElse(null);
            if (recipe != null) {
                float r = world.getRandom().nextFloat();
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.4F + 0.8F);
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.6F + 0.8F);
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.2F + 0.8F);
                ItemStack out = recipe.getSluicingResult(world);
                world.removeBlock(pos, false);
                player.swing(hand,true);
                if (!world.isClientSide) {
                    Block.popResource(world,pos,out);
                    if (Config.GENERAL.SLUICING_COOLDOWN.get()) {
                        player.getCooldowns().addCooldown(stack.getItem(), recipe.getCooldownTicks());
                    }

                    if (stack.getItem().canBeDepleted()) {
                        player.getMainHandItem().hurtAndBreak(1, player, (p_220038_0_) -> {
                            p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }
                }
            }
        }

    }

}
