package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

public class ItemExpireHandler {

    // 1.21.1: getEntityItem() -> getEntity(); entity.level field -> level(); recipe lookup returns
    // Optional<RecipeHolder<R>> and takes a RecipeInput (SingleRecipeInput) instead of a Container.
    public static void onItemPickup(ItemExpireEvent event) {
        if (event.getEntity().isInWater()) {
            Level levelIn = event.getEntity().level();
            RockGeneratorRecipe recipe = levelIn.getRecipeManager()
                    .getRecipeFor(RankineRecipeTypes.ROCK_GENERATOR.get(), new SingleRecipeInput(event.getEntity().getItem()), levelIn)
                    .map(RecipeHolder::value).orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem blockItem) {
                    BlockPos.findClosestMatch(event.getEntity().getOnPos(), 1, 4, pos -> (levelIn.getBlockState(pos.below()).isFaceSturdy(levelIn, pos.below(), Direction.UP) && !levelIn.isWaterAt(pos.below()))
                            && (levelIn.isWaterAt(pos))).ifPresent(placementPos -> levelIn.setBlock(placementPos, blockItem.getBlock().defaultBlockState(), 3));
                }

            }
        }
    }
}
