package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.plants.LeafLitterBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class GrassySoilBlock extends GrassBlock {
    public static final BooleanProperty DEAD = BooleanProperty.create("dead");

    public GrassySoilBlock() {
        super(AbstractBlock.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT).harvestTool(ToolType.SHOVEL));
        this.setDefaultState(this.stateContainer.getBaseState().with(SNOWY, false).with(DEAD, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SNOWY,DEAD);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isSnowyConditions(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (RankineLists.GRASS_BLOCKS.contains(state.getBlock())) {
                worldIn.setBlockState(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(state.getBlock())).getDefaultState());
            } else {
                worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        } else if (worldIn.getBlockState(pos.up()).getBlock() instanceof LeafLitterBlock && random.nextFloat() < Config.GENERAL.PODZOL_GROW_CHANCE.get()) {
            worldIn.setBlockState(pos,RankineLists.PODZOL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(state.getBlock())).getDefaultState(),2);
            worldIn.removeBlock(pos.up(),false);
        } else {
            if (worldIn.getLight(pos.up()) >= 9) {
                BlockState blockstate = this.getDefaultState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).matchesBlock(Blocks.DIRT) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState().with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)));
                    } else if (RankineLists.SOIL_BLOCKS.contains(worldIn.getBlockState(blockpos).getBlock()) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos, RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(worldIn.getBlockState(blockpos).getBlock())).getDefaultState().with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)).with(DEAD, blockstate.get(DEAD)));
                    }
                }
               // Block ceillingBlock = WorldgenUtils.getCeillingBlock(worldIn,pos,20);
               // if (random.nextFloat() < Config.GENERAL.SAPLING_GROW_CHANCE.get() && (worldIn.getBlockState(pos.up()).isReplaceable(Fluids.WATER) || worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR)) && ceillingBlock instanceof LeavesBlock) {
                   // worldIn.setBlockState(pos.up(), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(ceillingBlock.getRegistryName().toString().replace("leaves","sapling"))).getDefaultState(),2);
                //}
                if (!state.get(DEAD) && worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR) && random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get()) {
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
