package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GrassySoilBlock extends GrassBlock {
    public Block SOIL;
    public Block PATH;

    public GrassySoilBlock(Block Soil, Block Path, Properties properties) {
        super(properties);
        this.SOIL = Soil;
        this.PATH = Path;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() instanceof ShovelItem) {
            worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            worldIn.setBlockState(pos, PATH.getDefaultState(), 2);
            player.getHeldItem(handIn).damageItem(1, player, (entity) -> {
                entity.sendBreakAnimation(handIn);
            });
            player.swingArm(handIn);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isSnowyConditions(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            worldIn.setBlockState(pos, SOIL.getDefaultState());
        } else {
            if (worldIn.getLight(pos.up()) >= 9) {
                BlockState blockstate = this.getDefaultState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).getBlock() instanceof SoilBlock && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, blockstate.with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)));
                    }
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

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return entityType == EntityType.SHEEP || entityType == EntityType.COW || entityType == EntityType.PIG || entityType == EntityType.CHICKEN;
    }



}
