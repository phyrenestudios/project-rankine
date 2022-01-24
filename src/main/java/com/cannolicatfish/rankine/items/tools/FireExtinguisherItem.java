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

public class FireExtinguisherItem extends Item {

    public FireExtinguisherItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.getPosition();
        int range = Config.GENERAL.FIRE_EXTINGUISHER_RANGE.get();
        //if (!worldIn.isRemote) {
            for (BlockPos b : BlockPos.getAllInBoxMutable(pos.offset(playerIn.getHorizontalFacing(), range/2).add(-range/2, -2, -range/2), pos.offset(playerIn.getHorizontalFacing(), range/2).add(range/2, range/2, range/2))) {
                if (b.distanceSq(pos)<=Math.pow(range,2)) {
                    if (worldIn.getBlockState(b).getBlock() instanceof AbstractFireBlock) {
                        worldIn.setBlockState(b, Blocks.AIR.getDefaultState());
                    } else if (worldIn.getBlockState(b).getBlock() instanceof CampfireBlock) {
                        worldIn.setBlockState(b, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT,false));
                    }
                }
            }
            worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0f, 1.0f);
            playerIn.getHeldItem(handIn).damageItem(1,playerIn,(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(handIn);
            });
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        //}

    }
}
