package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TransformationStaffItem extends Item {

    public TransformationStaffItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = iworld.getBlockState(blockpos);
        Block activatedBlock = blockstate.getBlock();
        assert playerentity != null;
        ItemStack newBlock = playerentity.getHeldItemOffhand();

        if (newBlock.getItem().getTags().contains(new ResourceLocation("rankine:transformable")) && newBlock.getItem() != activatedBlock.asItem() && newBlock.getItem() != Blocks.AIR.asItem()) {
            for (BlockPos target : BlockPos.getAllInBoxMutable(blockpos.add(-2, -2, -2), blockpos.add(2, 2, 2))) {
                while (!newBlock.isEmpty())
                if (iworld.getBlockState(target).getBlock() == activatedBlock) {
                    iworld.setBlockState(target, ((BlockItem) newBlock.getItem()).getBlock().getDefaultState(), 1);
                    ItemStack itemstack = context.getItem();
                    if (playerentity instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
                        itemstack.damageItem(1, playerentity, (p_219998_1_) -> {
                            p_219998_1_.sendBreakAnimation(context.getHand());
                        });
                    }
                    if (!playerentity.abilities.isCreativeMode) {
                        newBlock.shrink(1);
                    }
                }
            }
                iworld.playSound(playerentity, blockpos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F);
                return ActionResultType.func_233537_a_(iworld.isRemote());
        } else {
            return ActionResultType.FAIL;
        }
    }
}

