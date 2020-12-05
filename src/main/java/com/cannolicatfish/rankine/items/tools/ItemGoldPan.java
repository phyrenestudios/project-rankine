package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.util.WeightedCollection;
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
            ItemStack stack = ItemStack.EMPTY;
            if (world.getBlockState(pos).getBlock() == ModBlocks.ALLUVIUM)
            {
                stack = returnAlluviumCollection().getRandomElement();
            } else if (world.getBlockState(pos).getBlock() == ModBlocks.BLACK_SAND){
                stack = returnBlackSandCollection().getRandomElement();
            }
            world.removeBlock(pos, false);

            if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots) {

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

    public static WeightedCollection<ItemStack> returnAlluviumCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(Items.DIAMOND));
        col.add(1.5f,new ItemStack(ModItems.RUBY));
        col.add(1.5f,new ItemStack(ModItems.SAPPHIRE));
        col.add(1.5f,new ItemStack(ModItems.PERIDOT));
        col.add(2,new ItemStack(ModItems.SAMARIUM_MONAZITE));
        col.add(2,new ItemStack(ModItems.ZIRCON));
        col.add(3,new ItemStack(ModItems.CERIUM_MONAZITE));
        col.add(5,new ItemStack(ModItems.IRIDIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.OSMIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.RHODIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.RUTHENIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.PLATINUM_NUGGET,3));
        col.add(6,new ItemStack(ModItems.TIN_NUGGET,3));
        col.add(8,new ItemStack(Items.GOLD_NUGGET,6));
        col.add(10,new ItemStack(Items.CLAY_BALL,2));
        return col;
    }

    public static WeightedCollection<ItemStack> returnBlackSandCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(Items.DIAMOND));
        col.add(1.5f,new ItemStack(ModItems.RUBY));
        col.add(1.5f,new ItemStack(ModItems.SAPPHIRE));
        col.add(2,new ItemStack(ModItems.GARNET));
        col.add(2,new ItemStack(ModItems.LANTHANUM_MONAZITE));
        col.add(2,new ItemStack(ModItems.NEODYMIUM_MONAZITE));
        col.add(2,new ItemStack(ModItems.ZIRCON));
        col.add(2,new ItemStack(Items.QUARTZ,2));
        col.add(4,new ItemStack(ModItems.THORIUM_NUGGET,3));
        col.add(4,new ItemStack(ModItems.URANIUM_NUGGET,3));
        col.add(4,new ItemStack(ModItems.TITANIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.IRIDIUM_NUGGET,3));
        col.add(5,new ItemStack(ModItems.PALLADIUM_NUGGET,3));
        col.add(7,new ItemStack(ModItems.OSMIUM_NUGGET,3));
        col.add(8,new ItemStack(Items.IRON_NUGGET,6));
        return col;
    }
}
