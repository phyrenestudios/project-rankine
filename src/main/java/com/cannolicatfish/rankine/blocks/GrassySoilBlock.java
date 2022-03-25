package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GrassySoilBlock extends GrassBlock {
    public static final BooleanProperty DEAD = BooleanProperty.create("dead");

    public GrassySoilBlock() {
        super(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, false).setValue(DEAD, false));
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return !state.getValue(DEAD);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY,DEAD);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!isSnowyConditions(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (RankineLists.GRASS_BLOCKS.contains(state.getBlock())) {
                worldIn.setBlockAndUpdate(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(state.getBlock())).defaultBlockState());
            } else {
                worldIn.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        } else if (random.nextFloat() < Config.GENERAL.PODZOL_GROW_CHANCE.get() && worldIn.getBlockState(pos.above()).getBlock() instanceof LeafLitterBlock) {
            worldIn.setBlock(pos,RankineLists.PODZOL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(state.getBlock())).defaultBlockState(),2);
            worldIn.removeBlock(pos.above(),false);
        } else if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get()) {
            Block ceillingBlock = Blocks.AIR;
            int i = 1;
            while (i <= 40) {
                if (!worldIn.isEmptyBlock(pos.relative(Direction.UP, i)) && !(worldIn.getBlockState(pos.above(i)).canBeReplaced(Fluids.WATER))) {
                    ceillingBlock = worldIn.getBlockState(pos.above(i)).getBlock();
                    break;
                }
                ++i;
            }
            if (ceillingBlock instanceof LeavesBlock && !(ceillingBlock instanceof RankineLeavesBlock) && (worldIn.getBlockState(pos.above(i - 1)).canBeReplaced(Fluids.WATER) || worldIn.getBlockState(pos.above(i - 1)).is(Blocks.AIR))) {
                worldIn.setBlock(pos.above(i - 1), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:"+ceillingBlock.getRegistryName().getPath().toString().replace("leaves", "leaf_litter"))).defaultBlockState(), 3);
            }
        } else if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
            BlockState blockstate = this.defaultBlockState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos).is(Blocks.DIRT) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                    worldIn.setBlockAndUpdate(blockpos, Blocks.GRASS_BLOCK.defaultBlockState().setValue(SNOWY, worldIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                } else if (RankineLists.SOIL_BLOCKS.contains(worldIn.getBlockState(blockpos).getBlock()) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                    worldIn.setBlockAndUpdate(blockpos, RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(worldIn.getBlockState(blockpos).getBlock())).defaultBlockState().setValue(SNOWY, worldIn.getBlockState(blockpos.above()).is(Blocks.SNOW)).setValue(DEAD, blockstate.getValue(DEAD)));
                }
            }
            if (random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get() && !state.getValue(DEAD) && worldIn.getBlockState(pos.above()).is(Blocks.AIR)) {
                Biome BIOME = worldIn.getBiome(pos);
                BlockState BLOCK = WorldgenUtils.VEGETATION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
                worldIn.setBlock(pos.above(), BLOCK, 3);
            }
        }
    }

    private static boolean isSnowyAndNotUnderwater(BlockState state, LevelReader worldReader, BlockPos pos) {
        return isSnowyConditions(state, worldReader, pos) && !worldReader.getFluidState(pos.above()).is(FluidTags.WATER);
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

}
