package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.HashMap;
import java.util.Map;

public class FluidPlaceBlockHandler {
    public static void onFluidInteraction( BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getState() == Blocks.COBBLESTONE.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.offset(d),worldIn.getBlockState(pos.offset(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap(( r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            }

        } else if (event.getState() == Blocks.BASALT.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.offset(d),worldIn.getBlockState(pos.offset(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(Blocks.BLACKSTONE.getDefaultState());
            }
        } else if (event.getState() == Blocks.STONE.getDefaultState()) {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.offset(d),worldIn.getBlockState(pos.offset(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(RankineBlocks.SKARN.get().getDefaultState());
            }
        } else if (event.getState() == Blocks.OBSIDIAN.getDefaultState()) {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.offset(d),worldIn.getBlockState(pos.offset(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            }
        }
    }
}
