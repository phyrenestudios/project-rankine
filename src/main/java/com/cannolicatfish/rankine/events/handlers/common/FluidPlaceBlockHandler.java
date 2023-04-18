package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class FluidPlaceBlockHandler {
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            Level worldIn = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Map<BlockPos, Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS)) {
                    Optional<RockGeneratorRecipe> optRecipe = r.matches(new SimpleContainer(items),worldIn) ? Optional.of(r) : Optional.empty();
                    return DataFixUtils.orElseGet(optRecipe.map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            }

        } else if (event.getState() == Blocks.BASALT.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            Level worldIn = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS)) {
                    Optional<RockGeneratorRecipe> optRecipe = r.matches(new SimpleContainer(items),worldIn) ? Optional.of(r) : Optional.empty();
                    return DataFixUtils.orElseGet(optRecipe.map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(Blocks.BLACKSTONE.defaultBlockState());
            }
        } else if (event.getState() == Blocks.STONE.defaultBlockState()) {
            Level worldIn = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC)) {
                    Optional<RockGeneratorRecipe> optRecipe = r.matches(new SimpleContainer(items),worldIn) ? Optional.of(r) : Optional.empty();
                    return DataFixUtils.orElseGet(optRecipe.map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(RankineBlocks.SKARN.get().defaultBlockState());
            }
        } else if (event.getState() == Blocks.OBSIDIAN.defaultBlockState()) {
            Level worldIn = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC)) {
                    Optional<RockGeneratorRecipe> optRecipe = r.matches(new SimpleContainer(items),worldIn) ? Optional.of(r) : Optional.empty();
                    return DataFixUtils.orElseGet(optRecipe.map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
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
