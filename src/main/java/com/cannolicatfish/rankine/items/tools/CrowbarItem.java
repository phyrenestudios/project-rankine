package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
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

    public CrowbarItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties p_i48512_5_) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocks, p_i48512_5_);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(1, entityLiving, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        if ((canHarvestBlock(state) || (canHarvestWithRetrieval(stack,state) && worldIn.getTileEntity(pos) == null && worldIn.getFluidState(pos).isEmpty())) && !worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots)
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

    public boolean canHarvestWithRetrieval(ItemStack stack, BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.RETRIEVAL,stack) >= 1 && !blockIn.getBlock().getTags().contains(new ResourceLocation("rankine:crowbar_resistant"))) {
            return i >= blockIn.getHarvestLevel();
        }
        return false;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getBlock().getTags().contains(new ResourceLocation("rankine:crowbar")) && !blockIn.getBlock().getTags().contains(new ResourceLocation("rankine:crowbar_resistant"))) {
            return i >= blockIn.getHarvestLevel();
        }
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.SILK_TOUCH || enchantment == Enchantments.FORTUNE ) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack,enchantment);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState bs = worldIn.getBlockState(blockpos);
        ItemStack stack = context.getItem();
        Direction face = context.getFace();
        PlayerEntity player = context.getPlayer();
        if (worldIn.getTileEntity(blockpos) == null && worldIn.getFluidState(blockpos).isEmpty() && bs.isSolid() && face != Direction.UP && face != Direction.DOWN && bs.getBlockHardness(worldIn,blockpos) >= 0) {
            if (player != null && player.getPosition().getY() <= blockpos.getY() && !player.getPosition().equals(blockpos.offset(face)) && player.isOnGround()){
                BlockState state = worldIn.getBlockState(blockpos);
                state = state.getBlock() instanceof GrassBlock ? state.getBlock().getDefaultState() : state;
                worldIn.setBlockState(blockpos.offset(face),state,3);
                worldIn.setBlockState(blockpos,Blocks.AIR.getDefaultState(),3);
                SoundType soundtype = worldIn.getBlockState(blockpos).getSoundType(worldIn, blockpos, null);
                worldIn.playSound(blockpos.getX(),blockpos.getY(),blockpos.getZ(), soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                stack.damageItem(1, player, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
                return ActionResultType.SUCCESS;
            }
        }
        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LIFT,stack) >= 1 && player != null && !player.isOnGround() && face != Direction.UP && face != Direction.DOWN &&
                worldIn.getBlockState(blockpos.up()).isAir() && worldIn.getBlockState(blockpos.up(2)).isAir() ) {
            player.setPositionAndUpdate(blockpos.getX() + 0.5f,blockpos.getY() + 1,blockpos.getZ() + 0.5f);
            player.getCooldownTracker().setCooldown(this, 120);
            stack.damageItem(1, player, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

}
