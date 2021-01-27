package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.WeightedCollection;
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

public class GoldPanItem extends Item {

    public GoldPanItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();
        float r = random.nextFloat();
        if (world.getBlockState(pos).getBlock() == RankineBlocks.ALLUVIUM.get() || world.getBlockState(pos).getBlock() == RankineBlocks.BLACK_SAND.get()) {
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.4F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.6F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.2F + 0.8F);
            ItemStack stack = ItemStack.EMPTY;
            if (world.getBlockState(pos).getBlock() == RankineBlocks.ALLUVIUM.get())
            {
                stack = returnAlluviumCollection().getRandomElement();
            } else if (world.getBlockState(pos).getBlock() == RankineBlocks.BLACK_SAND.get()){
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
        col.add(1,new ItemStack(RankineItems.THORIUM_NUGGET.get(),3));
        col.add(1.5f,new ItemStack(RankineItems.RUBY.get()));
        col.add(1.5f,new ItemStack(RankineItems.SAPPHIRE.get()));
        col.add(1.5f,new ItemStack(RankineItems.PERIDOT.get()));
        col.add(2,new ItemStack(RankineItems.SAMARIUM_MONAZITE.get()));
        col.add(2,new ItemStack(RankineItems.ZIRCON.get()));
        col.add(3,new ItemStack(RankineItems.CERIUM_MONAZITE.get()));
        col.add(5,new ItemStack(RankineItems.IRIDIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.OSMIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.RHODIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.RUTHENIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.PLATINUM_NUGGET.get(),3));
        col.add(6,new ItemStack(RankineItems.TIN_NUGGET.get(),3));
        col.add(8,new ItemStack(Items.GOLD_NUGGET,6));
        col.add(10,new ItemStack(Items.CLAY_BALL,2));
        return col;
    }

    public static WeightedCollection<ItemStack> returnBlackSandCollection(){
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        col.add(1,new ItemStack(Items.DIAMOND));
        col.add(1.5f,new ItemStack(RankineItems.RUBY.get()));
        col.add(1.5f,new ItemStack(RankineItems.SAPPHIRE.get()));
        col.add(2,new ItemStack(RankineItems.THORITE.get()));
        col.add(2,new ItemStack(RankineItems.GARNET.get()));
        col.add(2,new ItemStack(RankineItems.LANTHANUM_MONAZITE.get()));
        col.add(2,new ItemStack(RankineItems.NEODYMIUM_MONAZITE.get()));
        col.add(2,new ItemStack(RankineItems.ZIRCON.get()));
        col.add(2,new ItemStack(Items.QUARTZ,2));
        col.add(4,new ItemStack(RankineItems.URANIUM_NUGGET.get(),3));
        col.add(4,new ItemStack(RankineItems.TITANIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.IRIDIUM_NUGGET.get(),3));
        col.add(5,new ItemStack(RankineItems.PALLADIUM_NUGGET.get(),3));
        col.add(7,new ItemStack(RankineItems.OSMIUM_NUGGET.get(),3));
        col.add(8,new ItemStack(Items.IRON_NUGGET,6));
        return col;
    }
}
