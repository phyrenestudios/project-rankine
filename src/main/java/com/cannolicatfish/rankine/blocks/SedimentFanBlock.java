package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SedimentFanBlock extends Block {
    public SedimentFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        List<Fluid> adjPos = Arrays.asList(worldIn.getFluidState(pos.south().up()).getFluid(),worldIn.getFluidState(pos.north().up()).getFluid(),
                worldIn.getFluidState(pos.west().up()).getFluid(),worldIn.getFluidState(pos.east().up()).getFluid());
        if (worldIn.getFluidState(pos.up()).isSource()) {
            Direction dir = null;
            if (adjPos.contains(Fluids.FLOWING_WATER))
            switch (adjPos.indexOf(Fluids.FLOWING_WATER)) {
                case 0:
                    dir = Direction.SOUTH;
                    break;
                case 1:
                    dir = Direction.NORTH;
                    break;
                case 2:
                    dir = Direction.WEST;
                    break;
                case 3:
                    dir = Direction.EAST;
                    break;
            }

            if (dir != null) {
                List<Fluid> waterChecks = Arrays.asList(worldIn.getFluidState(pos.up().offset(dir, 2)).getFluid(), worldIn.getFluidState(pos.up().offset(dir, 3)).getFluid());
                if (waterChecks.stream().allMatch((e) -> e == Fluids.FLOWING_WATER)) {
                    Block sediment = worldIn.getBlockState(pos.offset(dir)).getBlock();
                    Block sediment2 = worldIn.getBlockState(pos.offset(dir, 2)).getBlock();
                    List<Block> adjPos2 = Arrays.asList(sediment,sediment2);
                    BlockPos end = pos.up().offset(dir, 3);
                    ItemStack[] items = adjPos2.stream().map(ItemStack::new).toArray(ItemStack[]::new);
                    RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                        if (r.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY)) {
                            return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                        }
                        return null;
                    }).findFirst().orElse(null);
                    if (recipe != null) {
                        ItemStack output = recipe.getRecipeOutput();
                        if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                            worldIn.setBlockState(end, ((BlockItem) output.getItem()).getBlock().getDefaultState(), 2);
                        }
                    } else {
                        worldIn.setBlockState(end, RankineBlocks.BRECCIA.get().getDefaultState(), 2);
                    }
                }
            }
        }

    }
}
