package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RankineMyceliumBlock extends MyceliumBlock {
    public RankineMyceliumBlock() {
        super(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).randomTicks().strength(0.6F).sound(SoundType.GRASS));
    }

    private static boolean isSnowyConditions(BlockState state, LevelReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(worldReader, blockpos));
            return i < worldReader.getMaxLightLevel();
        }
    }

    private static boolean isSnowyAndNotUnderwater(BlockState state, LevelReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return isSnowyConditions(state, worldReader, pos) && !worldReader.getFluidState(blockpos).is(FluidTags.WATER);
    }

    /**
     * Performs a random tick on a block.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!isSnowyConditions(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            worldIn.setBlockAndUpdate(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.MYCELIUM_BLOCKS.indexOf(state.getBlock())).defaultBlockState());
        } else {
            if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).is(Blocks.DIRT) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, worldIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                    } else if (RankineLists.SOIL_BLOCKS.contains(worldIn.getBlockState(blockpos).getBlock()) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockAndUpdate(blockpos, RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(worldIn.getBlockState(blockpos).getBlock())).defaultBlockState().setValue(SNOWY, worldIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                    }
                }

                if (worldIn.getBlockState(pos.above()).is(Blocks.AIR) && random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get()) {
                    worldIn.setBlock(pos.above(),random.nextBoolean() ? Blocks.RED_MUSHROOM.defaultBlockState() : Blocks.BROWN_MUSHROOM.defaultBlockState(),2);
                }
            }

        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(world, pos.relative(facing)).equals(PlantType.CAVE);
    }
}
