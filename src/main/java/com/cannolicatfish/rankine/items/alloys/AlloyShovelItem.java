package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.google.common.collect.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AlloyShovelItem extends ShovelItem implements IAlloyTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.DIRT_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;

    public AlloyShovelItem(Tier tier, float attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }


    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        Direction e = context.getClickedFace();
        Player playerentity = context.getPlayer();
        if (e == Direction.DOWN) {
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOLITHIC,context.getItemInHand()) > 0 && blockstate.getMaterial().equals(Material.STONE) && playerentity != null) {
                for (int x = 2; x <= 32; x++) {
                    BlockPos newPos = blockpos.above(x);
                    if (world.getBlockState(newPos).isAir() && world.getBlockState(newPos.above(1)).isAir() && world.getBlockState(newPos.below(1)).canOcclude()){
                        playerentity.teleportTo(newPos.getX(), Mth.clamp(newPos.getY() + 0.5,0.0D, (double)(world.getHeight() - 1)),newPos.getZ());
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),playerentity,false) * 2, playerentity, (p_220040_1_) -> {
                            p_220040_1_.broadcastBreakEvent(context.getHand());
                        });
                        SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        world.playSound((Player)null,blockpos.getX(), blockpos.getY(), blockpos.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        playerentity.playSound(soundevent, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(world.isClientSide);
                    }
                }
            }
            return InteractionResult.PASS;
        } else {
            BlockState blockstate1 = FLATTENABLES.get(blockstate.getBlock());
            BlockState blockstate2 = null;
            if (blockstate1 != null && world.isEmptyBlock(blockpos.above())) {
                world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
                if (!world.isClientSide()) {
                    world.levelEvent((Player)null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(playerentity,world, blockpos, blockstate);
                blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.FALSE);
            } else if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOLITHIC,context.getItemInHand()) > 0 && blockstate.getMaterial().equals(Material.STONE) && playerentity != null) {
                for (int x = 2; x <= 32; x++) {
                    BlockPos newPos = blockpos;
                    switch (e.getOpposite()) {
                        case UP:
                            newPos = blockpos.above(x);
                            break;
                        case DOWN:
                            newPos = blockpos.below(x);
                            break;
                        case NORTH:
                            newPos = blockpos.north(x);
                            break;
                        case SOUTH:
                            newPos = blockpos.south(x);
                            break;
                        case EAST:
                            newPos = blockpos.east(x);
                            break;
                        case WEST:
                            newPos = blockpos.west(x);
                            break;
                    }
                    if (world.getBlockState(newPos).isAir() && world.getBlockState(newPos.above(1)).isAir() && world.getBlockState(newPos.below(1)).canOcclude()){
                        playerentity.teleportTo(newPos.getX(), Mth.clamp(newPos.getY() + 0.5,0.0D, (double)(world.getHeight() - 1)),newPos.getZ());
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),playerentity,false) * 2, playerentity, (p_220040_1_) -> {
                            p_220040_1_.broadcastBreakEvent(context.getHand());
                        });
                        SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        world.playSound((Player)null,blockpos.getX(), blockpos.getY(), blockpos.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        playerentity.playSound(soundevent, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(world.isClientSide);
                    }
                }
            }

            if (blockstate2 != null) {
                if (!world.isClientSide) {
                    world.setBlock(blockpos, blockstate2, 11);
                    if (playerentity != null) {
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),context.getPlayer(),true), playerentity, (p_220040_1_) -> {
                            p_220040_1_.broadcastBreakEvent(context.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }


    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,false), attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group) && this.defaultAlloyRecipe == null) {
            items.addAll(AlloyCustomHelper.getItemsFromAlloying(this));
            items.addAll(AlloyCustomHelper.getItemsFromAlloyCrafting(this));
        } else if (this.allowdedIn(group)) {
            super.fillItemCategory(group,items);
        }
    }

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
}