package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class FireExtinguisherItem extends Item {

    public FireExtinguisherItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.blockPosition();
        int range = Config.GENERAL.FIRE_EXTINGUISHER_RANGE.get();
        //if (!worldIn.isRemote) {
            for (BlockPos b : BlockPos.betweenClosed(pos.relative(playerIn.getDirection(), range/2).offset(-range/2, -2, -range/2), pos.relative(playerIn.getDirection(), range/2).offset(range/2, range/2, range/2))) {
                if (b.distSqr(pos)<=Math.pow(range,2)) {
                    if (worldIn.getBlockState(b).getBlock() instanceof AbstractFireBlock) {
                        worldIn.setBlockAndUpdate(b, Blocks.AIR.defaultBlockState());
                    } else if (worldIn.getBlockState(b).getBlock() instanceof CampfireBlock) {
                        worldIn.setBlockAndUpdate(b, Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT,false));
                    }
                }
            }
            worldIn.playSound(playerIn, pos, SoundEvents.GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0f, 1.0f);
            playerIn.getItemInHand(handIn).hurtAndBreak(1,playerIn,(p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(handIn);
            });
            return ActionResult.success(playerIn.getItemInHand(handIn));
        //}

    }
}
