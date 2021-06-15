package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Set;

public class CrowbarItem extends ToolItem {
    private static final Set<Block> effectiveBlocks = ImmutableSet.of(Blocks.ACACIA_PLANKS);

    public CrowbarItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Properties p_i48512_5_) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocks, p_i48512_5_);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        if (canHarvestBlock(state) && !worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots)
        {
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(state.getBlock().asItem(), 1));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
            worldIn.removeBlock(pos, false);
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }

        return true;
    }


    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getBlock().getTags().contains(new ResourceLocation("rankine:crowbar"))) {
            return i >= blockIn.getHarvestLevel();
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity && isSelected)
        {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (player.swingingHand == Hand.OFF_HAND)
            {
                player.resetCooldown();
                player.swingingHand = Hand.MAIN_HAND;
            }
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) entity;
            World worldIn = player.getEntityWorld();
            RayTraceResult raytraceresult = rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);
            BlockPos pos;
            if (raytraceresult instanceof BlockRayTraceResult)
            {
                final BlockRayTraceResult rayTraceResult = (BlockRayTraceResult) raytraceresult;
                pos = rayTraceResult.getPos();
            } else
            {
                pos = new BlockPos(raytraceresult.getHitVec().x,raytraceresult.getHitVec().y,raytraceresult.getHitVec().z);
            }
            if (player.getCooledAttackStrength(0) >= (1f - .15*getSwingModifier(stack)))
            {
                player.resetCooldown();
                onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
            }
        }
        return false;
    }
    /*
        @Override
        public ActionResultType onItemUse(ItemUseContext context) {
            World worldIn = context.getWorld();
            BlockPos blockpos = context.getPos();
            BlockState bs = worldIn.getBlockState(blockpos);
            if (worldIn.getTileEntity(blockpos) == null && worldIn.getFluidState(blockpos).isEmpty() && bs.isSolid() && context.getFace() != Direction.UP && context.getFace() != Direction.DOWN && bs.getBlockHardness(worldIn,blockpos) >= 0) {
                if (context.getPlayer() == null || context.getPlayer().getPosition().getY() <= blockpos.getY()){
                    worldIn.setBlockState(blockpos.offset(context.getFace()),worldIn.getBlockState(blockpos),2);
                    worldIn.setBlockState(blockpos,Blocks.AIR.getDefaultState(),2);
                    SoundType soundtype = worldIn.getBlockState(blockpos).getSoundType(worldIn, blockpos, null);
                    worldIn.playSound(blockpos.getX(),blockpos.getY(),blockpos.getZ(), soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                }
            }
            return super.onItemUse(context);
        }
    */
    public static int getSwingModifier(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SWING, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.FORTUNE ) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }
}
