package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class MetalDetector extends Item {

    public MetalDetector(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        IWorld iworld = context.getWorld();
        Direction e = context.getFace();
        IBlockReader reader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = iworld.getBlockState(blockpos);
        if (playerentity != null) {
            context.getItem().damageItem(1, playerentity, (p_219998_1_) -> {
                p_219998_1_.sendBreakAnimation(context.getHand());
            });
        }
        boolean found = false;
        for (int x = 0; x < Config.METAL_DETECTOR_RANGE.get(); x++) {
            if (e.getOpposite() == Direction.DOWN)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.UP)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.NORTH)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.SOUTH)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.EAST)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.WEST)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock().getTags().contains(new ResourceLocation("forge:ores"))){
                    found = true;
                    break;
                }
            }


        }
        if (found)
        {
            //context.getPlayer().sendMessage(new StringTextComponent("Ore found!"));
            iworld.playSound(context.getPlayer(),blockpos, SoundEvents.BLOCK_NOTE_BLOCK_BELL,SoundCategory.PLAYERS,1.0F, random.nextFloat() * 0.4F + 0.8F);

        }
        return ActionResultType.SUCCESS;
    }
}
