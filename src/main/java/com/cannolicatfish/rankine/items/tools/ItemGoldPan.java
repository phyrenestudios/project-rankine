package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.swing.*;

public class ItemGoldPan extends Item {
    public ItemGoldPan(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        float r = random.nextFloat();
        if (world.getBlockState(pos).getBlock() == ModBlocks.ALLUVIUM || world.getBlockState(pos).getBlock() == ModBlocks.BLACK_SAND) {
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.4F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.6F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.2F + 0.8F);
            world.removeBlock(pos, false);
            ItemStack stack;
            if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots) {
                if (r <= 0.01f) {
                    stack = new ItemStack(Items.DIAMOND, 1);
                } else if (r <= 0.03f) {
                    stack = new ItemStack(ModItems.MONAZITE, 1);
                } else if (r <= 0.05f) {
                    stack = new ItemStack(ModItems.ZIRCON, 1);
                } else if (r <= 0.07f) {
                    stack = new ItemStack(ModItems.RUBY, 1);
                } else if (r <= 0.085f) {
                    stack = new ItemStack(ModItems.SAPPHIRE, 1);
                } else if (r <= 0.1f) {
                    stack = new ItemStack(ModItems.GARNET, 1);
                } else if (r <= 0.12f) {
                    stack = new ItemStack(ModItems.URANIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.14f) {
                    stack = new ItemStack(ModItems.TITANIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.16f) {
                    stack = new ItemStack(ModItems.IRIDIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.18f) {
                    stack = new ItemStack(ModItems.RHODIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.2f) {
                    stack = new ItemStack(ModItems.RUTHENIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.25f) {
                    stack = new ItemStack(ModItems.PALLADIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.3f) {
                    stack = new ItemStack(ModItems.PLATINUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.4f) {
                    stack = new ItemStack(ModItems.OSMIUM_NUGGET, random.nextInt(5) + 2);
                } else if (r <= 0.45f) {
                    stack = new ItemStack(ModItems.TIN_NUGGET, random.nextInt(5) + 3);
                } else if (r <= 0.55f) {
                    stack = new ItemStack(Items.IRON_NUGGET, random.nextInt(5) + 3);
                } else if (r <= 0.65f) {
                    stack = new ItemStack(Items.GOLD_NUGGET, random.nextInt(5) + 4);
                } else if (r <= 0.75f){
                    stack = new ItemStack(Items.CLAY_BALL, random.nextInt(5) + 1);
                } else if (r <= 0.85f){
                    stack = new ItemStack(Blocks.GRAVEL, 1);
                }
                else {
                    stack = new ItemStack(Blocks.SAND, 1);
                }
                double d0 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
                itementity.setDefaultPickupDelay();
                world.addEntity(itementity);
                context.getItem().damageItem(1, player, (p_219998_1_) -> {
                    p_219998_1_.sendBreakAnimation(context.getHand());
                });
                return ActionResultType.SUCCESS;
            }

        }
        return ActionResultType.FAIL;
    }
}
