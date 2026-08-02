package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 1.21.1: BlockEvent moved to event.level; getWorld() -> getLevel().
 * {@code RecipeType#tryMatch} is gone (RecipeType is now a plain marker created with
 * RecipeType.simple), so matching is done explicitly against RecipeHolder values with a
 * RecipeInput built from the neighbouring blocks (same 6-slot layout as the old SimpleContainer).
 */
public class FluidPlaceBlockHandler {
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            applyRockGen(event, RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS, null);
        } else if (event.getState() == Blocks.BASALT.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            applyRockGen(event, RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS, Blocks.BLACKSTONE);
        } else if (event.getState() == Blocks.STONE.defaultBlockState()) {
            applyRockGen(event, RockGeneratorUtils.RockGenType.METAMORPHIC, RankineBlocks.SKARN.get());
        } else if (event.getState() == Blocks.OBSIDIAN.defaultBlockState()) {
            applyRockGen(event, RockGeneratorUtils.RockGenType.VOLCANIC, null);
        }
    }

    /**
     * @param fallback block placed when no recipe matched (1.18.2 only did this for basalt and stone),
     *                 or null to leave the vanilla state untouched.
     */
    private static void applyRockGen(BlockEvent.FluidPlaceBlockEvent event, RockGeneratorUtils.RockGenType genType, Block fallback) {
        Level worldIn = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        Map<BlockPos, Block> posMap = new HashMap<>();
        for (Direction d : Direction.values()) {
            posMap.put(pos.relative(d), worldIn.getBlockState(pos.relative(d)).getBlock());
        }
        ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
        RecipeInput input = new RecipeWrapper(new InvWrapper(new SimpleContainer(items)));

        RockGeneratorRecipe recipe = null;
        for (RecipeHolder<RockGeneratorRecipe> holder : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get())) {
            RockGeneratorRecipe r = holder.value();
            if (r.getGenType().equals(genType) && r.matches(input, worldIn)) {
                recipe = r;
                break;
            }
        }

        if (recipe != null) {
            ItemStack output = recipe.getResultItem();
            if (!output.isEmpty() && output.getItem() instanceof BlockItem blockItem) {
                event.setNewState(blockItem.getBlock().defaultBlockState());
                if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                    BlockPos b = recipe.getRandomInput(posMap);
                    if (b != null) {
                        worldIn.removeBlock(b, false);
                    }
                }
            }
        } else if (fallback != null) {
            event.setNewState(fallback.defaultBlockState());
        }
    }
}
