package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.*;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GrassySoilBlock extends GrassBlock {

    public GrassySoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isSnowyConditions(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (VanillaIntegration.grass_dirt_map.get(state.getBlock()) != null) {
                worldIn.setBlockState(pos, VanillaIntegration.grass_dirt_map.get(state.getBlock()).getDefaultState());
            } else {
                worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        } else {
            if (worldIn.getLight(pos.up()) >= 9) {
                BlockState blockstate = this.getDefaultState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (VanillaIntegration.dirt_grass_map.get(state.getBlock()) != null && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, VanillaIntegration.dirt_grass_map.get(state.getBlock()).getDefaultState().with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)),2);
                    }
                }
                Block ceillingBlock = WorldgenUtils.getCeillingBlock(worldIn,pos,20);
                if (random.nextFloat() < Config.GENERAL.SAPLING_GROW_CHANCE.get() && (worldIn.getBlockState(pos.up()).isReplaceable(Fluids.WATER) || worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR)) && ceillingBlock instanceof LeavesBlock) {
                    worldIn.setBlockState(pos.up(), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(ceillingBlock.getRegistryName().toString().replace("leaves","sapling"))).getDefaultState(),2);
                }
                if (worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR) && random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get()) {
                    Biome BIOME = worldIn.getBiome(pos);
                    BlockState BLOCK = WorldgenUtils.VEGETATION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
                    worldIn.setBlockState(pos.up(),BLOCK,2);
                }
            }
        }
    }

    private static boolean isSnowyAndNotUnderwater(BlockState state, IWorldReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return isSnowyConditions(state, worldReader, pos) && !worldReader.getFluidState(blockpos).isTagged(FluidTags.WATER);
    }
    private static boolean isSnowyConditions(BlockState state, IWorldReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        if (blockstate.matchesBlock(Blocks.SNOW) && blockstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = LightEngine.func_215613_a(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getOpacity(worldReader, blockpos));
            return i < worldReader.getMaxLightLevel();
        }
    }

}
