package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyShovelItem extends ShovelItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;

    public AlloyShovelItem(Tier tier, float attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Item.Properties builder) {
        super(tier, builder.attributes(ShovelItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public TagKey<Block> getAlloyMineableTag() {
        return BlockTags.MINEABLE_WITH_SHOVEL;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_SHOVEL) && !state.is(RankineToolMaterials.incorrectBlocksForLevel(getAlloyHarvestLevel(stack)));
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return Component.translatable(this.getDescriptionId(stack),Component.translatable(IAlloyItem.getNameOverride(stack)));
        }
        return Component.translatable(this.getDescriptionId(stack),Component.translatable(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    @Override
    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(BlockTags.MINEABLE_WITH_SHOVEL) ? getAlloyMiningSpeed(p_41004_) : 1.0F;
    }

    // PORT-TODO: Material.STONE approximation — Material is gone in 1.21.
    private static boolean isStoneLike(BlockState state) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(BlockTags.BASE_STONE_NETHER) || state.is(Tags.Blocks.STONES);
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
            if (RankineEnchantmentHelper.getLevel(world, RankineEnchantments.ENDOLITHIC, context.getItemInHand()) > 0 && isStoneLike(blockstate) && playerentity != null) {
                for (int x = 2; x <= 32; x++) {
                    BlockPos newPos = blockpos.above(x);
                    if (world.getBlockState(newPos).isAir() && world.getBlockState(newPos.above(1)).isAir() && world.getBlockState(newPos.below(1)).canOcclude()){
                        playerentity.teleportTo(newPos.getX(), Mth.clamp(newPos.getY() + 0.5,0.0D, (double)(world.getHeight() - 1)),newPos.getZ());
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),playerentity,false) * 2, playerentity, LivingEntity.getSlotForHand(context.getHand()));
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
            } else if (RankineEnchantmentHelper.getLevel(world, RankineEnchantments.ENDOLITHIC, context.getItemInHand()) > 0 && isStoneLike(blockstate) && playerentity != null) {
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
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),playerentity,false) * 2, playerentity, LivingEntity.getSlotForHand(context.getHand()));
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
                        context.getItemInHand().hurtAndBreak(calcDurabilityLoss(context.getItemInHand(),context.getLevel(),context.getPlayer(),true), playerentity, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }

                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,false), attacker, EquipmentSlot.MAINHAND);
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, EquipmentSlot.MAINHAND);
        }

        return true;
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,context.level(),tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,context.level(),tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
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

    // PORT-TODO(init): fillItemCategory is gone — dynamic creative-tab variants
    // (AlloyCustomHelper.getItemsFromAlloying/getItemsFromAlloyCrafting when defaultAlloyRecipe == null)
    // must be added in RankineCreativeTabs' displayItems.

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
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.is(Tags.Items.INGOTS) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = String.valueOf(IAlloyItem.getAlloyRecipe(repair));
            String r2 = String.valueOf(IAlloyItem.getAlloyRecipe(toRepair));
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
}
