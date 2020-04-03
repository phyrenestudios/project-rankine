package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
        for (int x = 1; x < 16; x++) {
            if (e.getOpposite() == Direction.DOWN)
            {
                if (reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                    reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE){
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.UP)
            {
                if (reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.up(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE) {
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.NORTH)
            {
                if (reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.north(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE) {
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.SOUTH)
            {
                if (reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.south(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE) {
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.EAST)
            {
                if (reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.east(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE) {
                    found = true;
                    break;
                }
            }
            if (e.getOpposite() == Direction.WEST)
            {
                if (reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.MAGNETITE_ORE || reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.PENTLANDITE_ORE || reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.MALACHITE_ORE ||
                        reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.BAUXITE_ORE || reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.CASSITERITE_ORE || reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.GALENA_ORE ||
                        reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.NATIVE_GOLD_ORE || reader.getBlockState(blockpos.west(x)).getBlock() == ModBlocks.ACANTHITE_ORE || reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_COPPER_ORE ||
                        reader.getBlockState(blockpos.down(x)).getBlock() == ModBlocks.NATIVE_TIN_ORE) {
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
