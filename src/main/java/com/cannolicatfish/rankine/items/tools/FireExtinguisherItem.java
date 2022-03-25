package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class FireExtinguisherItem extends Item {

    public FireExtinguisherItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BlockPos pos = playerIn.blockPosition();
        int range = Config.GENERAL.FIRE_EXTINGUISHER_RANGE.get();
        //if (!worldIn.isRemote) {
            for (BlockPos b : BlockPos.betweenClosed(pos.relative(playerIn.getDirection(), range/2).offset(-range/2, -2, -range/2), pos.relative(playerIn.getDirection(), range/2).offset(range/2, range/2, range/2))) {
                if (b.distSqr(pos)<=Math.pow(range,2)) {
                    if (worldIn.getBlockState(b).getBlock() instanceof BaseFireBlock) {
                        worldIn.setBlockAndUpdate(b, Blocks.AIR.defaultBlockState());
                    } else if (worldIn.getBlockState(b).getBlock() instanceof CampfireBlock) {
                        worldIn.setBlockAndUpdate(b, Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT,false));
                    }
                }
            }
            worldIn.playSound(playerIn, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f);
            playerIn.getItemInHand(handIn).hurtAndBreak(1,playerIn,(p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(handIn);
            });
            return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
        //}

    }
}
