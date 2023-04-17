package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineTags;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.Set;

public class CrowbarItem extends DiggerItem {
    private static final Set<Block> effectiveBlocks = ImmutableSet.of(Blocks.ACACIA_PLANKS);

    public CrowbarItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties p_i48512_5_) {
        super(attackDamageIn, attackSpeedIn, tier, RankineTags.Blocks.MINEABLE_WITH_CROWBAR, p_i48512_5_);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if ((isCorrectToolForDrops(state) || (canHarvestWithRetrieval(stack,state) && worldIn.getBlockEntity(pos) == null && worldIn.getFluidState(pos).isEmpty())) && !worldIn.isClientSide && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
            Block.popResource(worldIn, pos, new ItemStack(state.getBlock().asItem(), 1));
            worldIn.removeBlock(pos, false);
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            if (soundtype != null) worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
            if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                stack.hurtAndBreak(1, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        return true;
    }

    public boolean canHarvestWithRetrieval(ItemStack stack, BlockState blockIn) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETRIEVAL.get(), stack) >= 1 && !blockIn.is(RankineTags.Blocks.CROWBAR_RESISTANT);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(RankineTags.Blocks.MINEABLE_WITH_CROWBAR) && !state.is(RankineTags.Blocks.CROWBAR_RESISTANT) && TierSortingRegistry.isCorrectTierForDrops(this.getTier(),state);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.BLOCK_EFFICIENCY || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.BLOCK_FORTUNE ) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState bs = worldIn.getBlockState(blockpos);
        ItemStack stack = context.getItemInHand();
        Direction face = context.getClickedFace();
        Player player = context.getPlayer();
        if (worldIn.getBlockState(blockpos).getBlock() instanceof DoorBlock && bs.hasProperty(DoorBlock.OPEN)) {
            worldIn.setBlock(blockpos,bs.setValue(DoorBlock.OPEN,!bs.getValue(DoorBlock.OPEN)),3);
            if (bs.hasProperty(DoorBlock.HALF)) {
                if (bs.getValue(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
                    BlockState upper = worldIn.getBlockState(blockpos.above());
                    worldIn.setBlock(blockpos.above(),upper.setValue(DoorBlock.OPEN,!bs.getValue(DoorBlock.OPEN)),3);
                } else {
                    BlockState down = worldIn.getBlockState(blockpos.below());
                    worldIn.setBlock(blockpos.below(),down.setValue(DoorBlock.OPEN,!bs.getValue(DoorBlock.OPEN)),3);
                }
            }
            if (bs.hasProperty(DoorBlock.POWERED)) {
                worldIn.playLocalSound(blockpos.getX(),blockpos.getY(),blockpos.getZ(), !bs.getValue(DoorBlock.OPEN) ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, worldIn.getRandom().nextFloat() * 0.1F + 0.9F, false);
            } else {
                worldIn.playLocalSound(blockpos.getX(),blockpos.getY(),blockpos.getZ(), !bs.getValue(DoorBlock.OPEN) ? SoundEvents.WOODEN_DOOR_CLOSE : SoundEvents.WOODEN_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, worldIn.getRandom().nextFloat() * 0.1F + 0.9F, false);
            }
            stack.hurtAndBreak(1, player, (p_220038_0_) -> {
                p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            return InteractionResult.SUCCESS;

        }
        if (player != null && !player.isOnGround() && worldIn.getBlockState(blockpos.above()).isAir() && worldIn.getBlockState(blockpos.above(2)).isAir() ) {
            Vec3 oldVector = player.getDeltaMovement().normalize();
            int liftLevel = RankineEnchantmentHelper.getLiftEnchantment(stack);
            if (liftLevel > 0) {
                player.setDeltaMovement(oldVector.x*Math.sqrt(liftLevel), liftLevel*0.4D, oldVector.z*Math.sqrt(liftLevel));
            } else {
                player.moveTo(BlockPos.containing(blockpos.above().getCenter()), player.getYRot(), player.getXRot());
            }
            player.getCooldowns().addCooldown(this, 100);
            stack.hurtAndBreak(1, player, (p_220038_0_) -> {
                p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            return InteractionResult.SUCCESS;
        }

        if (worldIn.getBlockEntity(blockpos) == null && worldIn.getFluidState(blockpos).isEmpty() && bs.canOcclude() && face.getAxis() != Direction.Axis.Y && bs.getDestroySpeed(worldIn,blockpos) >= 0) {
            if (player != null && (player.blockPosition().getY() <= blockpos.getY() || Config.GENERAL.CROWBAR_FROM_ABOVE.get()) && !player.blockPosition().equals(blockpos.relative(face)) && player.isOnGround()){
                BlockState state = worldIn.getBlockState(blockpos);
                state = state.getBlock() instanceof GrassBlock ? state.getBlock().defaultBlockState() : state;
                worldIn.setBlockAndUpdate(blockpos.relative(face),state);
                worldIn.removeBlock(blockpos,false);
                SoundType soundtype = worldIn.getBlockState(blockpos).getSoundType(worldIn, blockpos, null);
                worldIn.playLocalSound(blockpos.getX(),blockpos.getY(),blockpos.getZ(), soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                stack.hurtAndBreak(1, player, (p_220038_0_) -> {
                    p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

}
