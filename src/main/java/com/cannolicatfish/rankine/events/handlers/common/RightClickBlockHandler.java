package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyHoeItem;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 1.21.1 notes:
 * - getPlayer() -> getEntity(), getWorld() -> getLevel().
 * - PotionUtils.getPotion(stack) -> the POTION_CONTENTS data component.
 * - Block#getRegistryName() -> BuiltInRegistries.BLOCK.getKey(block); forge: tag namespace -> c:.
 * - Forge reverse-tag lookups -> ItemStack#getTags().
 * - RecipeType#tryMatch / Container recipes -> getRecipeFor(type, RecipeInput, level) returning
 *   Optional&lt;RecipeHolder&lt;R&gt;&gt;.
 * - hurtAndBreak(amount, entity, slot) replaces hurtAndBreak + broadcastBreakEvent.
 * - Items.GRASS -> Items.SHORT_GRASS.
 * - PORT-NOTE: event.setResult(Event.Result.ALLOW) was inert on RightClickBlock in 1.18.2
 *   (only useBlock/useItem and cancellation are consulted) and Event.Result is gone in NeoForge —
 *   the calls are dropped, preserving behavior.
 */
public class RightClickBlockHandler {

    private static final Map<Block, BlockState> flintLightMap = new HashMap<>();
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
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
            if (blockId == null) continue;
            // 1.21.1: the "forge" tag namespace is now "c"; tag existence can't be probed at
            // class-init time any more, so the TagKey is simply created (unknown tags stay empty).
            TagKey<Item> nuggetTag = TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath("c", "nuggets/" + blockId.getPath().replace("_lantern","")));
            lanternMap.put(nuggetTag, (LanternBlock) block);
        }
        lanternMap.put(Tags.Items.NUGGETS_IRON, (LanternBlock) Blocks.LANTERN);
        lanternMap.put(RankineTags.Items.NUGGETS_SULFUR, (LanternBlock) Blocks.SOUL_LANTERN);
    }

    private static EquipmentSlot slotFor(InteractionHand hand) {
        return hand == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
    }

    public static void rightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getFace() == null) return;
        Player playerIn = event.getEntity();
        Level levelIn = event.getLevel();
        BlockPos posIn = event.getPos();
        BlockState stateIn = levelIn.getBlockState(posIn);
        ItemStack itemStack = playerIn.getItemInHand(event.getHand());

        // Mud creation
        if (itemStack.getItem() instanceof PotionItem) {
            PotionContents contents = itemStack.get(DataComponents.POTION_CONTENTS);
            if (contents != null && contents.is(Potions.WATER)) {
                if (RankineLists.SOIL_BLOCKS.contains(levelIn.getBlockState(posIn).getBlock())) {
                    levelIn.setBlockAndUpdate(posIn, RankineLists.MUD_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(levelIn.getBlockState(posIn).getBlock())).defaultBlockState());
                    playerIn.getInventory().add(ItemUtils.createFilledResult(itemStack, playerIn, new ItemStack(Items.GLASS_BOTTLE)));
                    return;
                }
            }
        }


        if (playerIn.getMainHandItem().is(RankineTags.Items.FLINT) && playerIn.getOffhandItem().is(RankineTags.Items.FLINT) && Config.GENERAL.FLINT_FIRE.get()) {
            BlockPos pos = event.getPos();
            BlockPos blockpos1 = event.getPos().relative(event.getFace());
            RandomSource rand = levelIn.random;
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
            for (TagKey<Item> tag : itemStack.getTags().toList()) {
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
        Level worldIn = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockState targetBS = worldIn.getBlockState(pos);
        Block b = targetBS.getBlock();

        if(item instanceof AxeItem) {
            //Extra items from stripping recipe
            RecipeInput strippingInput = new RecipeWrapper(new InvWrapper(new SimpleContainer(new ItemStack(b))));
            StrippingRecipe irecipe = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.STRIPPING.get(), strippingInput, worldIn).map(RecipeHolder::value).orElse(null);
            if (irecipe != null) {
                if (worldIn.getRandom().nextFloat() < irecipe.getChance()) {
                    Block.popResource(worldIn, pos, irecipe.getResult());
                }
            }
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(b);
            if (targetBS.is(BlockTags.LOGS) && blockId != null && !blockId.toString().contains("stripped") && Config.GENERAL.STRIPPABLES_STICKS.get() && worldIn.getRandom().nextFloat() < 0.3) {
                Block.popResource(worldIn, pos, new ItemStack(Items.STICK, 1));
            }

            if(stripping_map.get(b) != null) {
                if(b instanceof RotatedPillarBlock) {
                    Direction.Axis axis = targetBS.getValue(RotatedPillarBlock.AXIS);
                    worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlock(pos, stripping_map.get(b).defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
                    stack.hurtAndBreak(1, player, slotFor(event.getHand()));
                    player.swing(event.getHand());
                }
            }
        } else if (item instanceof ShovelItem) {
            if (VanillaIntegration.pathBlocks_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(b).defaultBlockState(), 2);
                stack.hurtAndBreak(1, player, slotFor(event.getHand()));
                player.swing(event.getHand());
            }
        } else if (item instanceof HoeItem) {
            if (VanillaIntegration.hoeables_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, RankineBlocks.TILLED_SOIL.get().defaultBlockState().setValue(TilledSoilBlock.MOISTURE, 0).setValue(TilledSoilBlock.SOIL_TYPE, VanillaIntegration.hoeables_map.get(b)), 3);
                stack.hurtAndBreak(1, player, slotFor(event.getHand()));
                player.swing(event.getHand());
            } else if (b instanceof DoubleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.LOWER) {
                        worldIn.destroyBlock(pos,true);
                        if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.UPPER) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOSPORE, stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3, blockPos ->!blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof TripleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.BOTTOM) {
                        worldIn.destroyBlock(pos,true);
                        if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.MIDDLE) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.TOP) {
                        worldIn.destroyBlock(pos.below(2),true);
                        if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos.below(2))) {
                            worldIn.setBlockAndUpdate(pos.below(2), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOSPORE, stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3, blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof CropBlock && item instanceof AlloyHoeItem) {

                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(CropBlock.AGE) == 7) {
                    worldIn.destroyBlock(pos,true);
                    if (b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,pos)) {
                        worldIn.setBlockAndUpdate(pos,b.defaultBlockState().setValue(CropBlock.AGE, 0));
                    }
                    if (RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOSPORE, stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && b.defaultBlockState().setValue(CropBlock.AGE, 0).canSurvive(worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            }
        }
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level world = event.getLevel();
        Direction direction = event.getFace();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        Player player = event.getEntity();

        if (stack.is(RankineTags.Items.KNIVES) && direction != null && hand == InteractionHand.MAIN_HAND) {
            Block target = state.getBlock();
            if (state.is(RankineTags.Blocks.GRASS_BLOCKS) && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                if (RankineLists.GRASS_BLOCKS.contains(target)) {
                    world.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(target)).defaultBlockState(), 3);
                } else {
                    world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                }
                player.swing(hand);
                if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    Block.popResource(world, pos.above(), new ItemStack(Items.SHORT_GRASS, 1));
                    if (world.getRandom().nextFloat() < 0.2) Block.popResource(world, pos.above(), new ItemStack(RankineItems.GRASS_SEEDS.get(), 1));
                }
                if (!world.isClientSide) {
                    player.getItemInHand(hand).hurtAndBreak(1, player, slotFor(hand));
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
                player.getItemInHand(hand).hurtAndBreak(1, player, slotFor(hand));
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
                player.getMainHandItem().hurtAndBreak(1, player, slotFor(player.swingingArm));
                world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            }
        } else if (stack.is(RankineTags.Items.SLUICING_TOOLS) && direction != null && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            RecipeInput sluicingInput = new RecipeWrapper(new InvWrapper(new SimpleContainer(new ItemStack(world.getBlockState(pos).getBlock()), stack)));
            SluicingRecipe recipe = world.getRecipeManager().getRecipeFor(RankineRecipeTypes.SLUICING.get(), sluicingInput, world).map(RecipeHolder::value).orElse(null);
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

                    // PORT-NOTE: Item#canBeDepleted() is gone; ItemStack#isDamageableItem() is the
                    // 1.21.1 equivalent (it additionally skips UNBREAKABLE stacks, which hurtAndBreak
                    // would have no-opped on anyway).
                    if (stack.isDamageableItem()) {
                        player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                    }
                }
            }
        }

    }

}
