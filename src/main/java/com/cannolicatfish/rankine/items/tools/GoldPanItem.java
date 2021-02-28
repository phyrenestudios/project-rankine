package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.util.WeightedCollection;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
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
        SluicingRecipe recipe = world.getRecipeManager().getRecipe(RankineRecipeTypes.SLUICING, new Inventory(new ItemStack(world.getBlockState(pos).getBlock())), world).orElse(null);
        if (recipe != null) {
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.4F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.6F + 0.8F);
            world.playSound(context.getPlayer(), pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.2F + 0.8F);
            ItemStack stack = recipe.getSluicingResult(world);
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
}
