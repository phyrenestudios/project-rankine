package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

public class ItemExpireHandler {

    public static void onItemPickup(ItemExpireEvent event) {
        if (event.getEntity().isInWater()) {
            RockGeneratorRecipe recipe = event.getEntity().getLevel().getRecipeManager().getRecipeFor(RankineRecipeTypes.ROCK_GENERATOR,new SimpleContainer(event.getEntity().getItem()),event.getEntity().getLevel()).orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem blockItem) {
                    Level levelIn = event.getEntity().getLevel();
                    BlockPos.findClosestMatch(event.getEntity().getOnPos(), 1, 4, pos -> (levelIn.getBlockState(pos.below()).isFaceSturdy(levelIn, pos.below(), Direction.UP) && !levelIn.isWaterAt(pos.below()))
                            && (levelIn.isWaterAt(pos))).ifPresent(placementPos -> levelIn.setBlock(placementPos, blockItem.getBlock().defaultBlockState(), 3));
                }

            }
        }
    }
}
