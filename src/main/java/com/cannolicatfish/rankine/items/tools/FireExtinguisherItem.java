package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FireExtinguisherItem extends Item {

    public FireExtinguisherItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.getPosition();
        int range = Config.GENERAL.FIRE_EXTINGUISHER_RANGE.get();
        if (!worldIn.isRemote) {
            for (BlockPos b : BlockPos.getAllInBoxMutable(pos.offset(playerIn.getHorizontalFacing(), range/2).add(-range/2, -2, -range/2), pos.offset(playerIn.getHorizontalFacing(), range/2).add(range/2, range/2, range/2))) {
                if (b.distanceSq(pos)<=Math.pow(range,2) && worldIn.getBlockState(b).getBlock() instanceof AbstractFireBlock) {
                    worldIn.setBlockState(b, Blocks.AIR.getDefaultState());
                }
            }
            worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            playerIn.getHeldItem(handIn).damageItem(1,playerIn,(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(handIn);
            });
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
