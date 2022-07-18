package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class GrassySoilBlock extends GrassBlock {
    public static final BooleanProperty DEAD = BooleanProperty.create("dead");

    public GrassySoilBlock() {
        super(AbstractBlock.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT).harvestTool(ToolType.SHOVEL));
        this.setDefaultState(this.stateContainer.getBaseState().with(SNOWY, false).with(DEAD, false));
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return !state.get(DEAD);
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
        } else if (worldIn.getLight(pos.up()) >= 9) {
            BlockState blockstate = this.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos).matchesBlock(Blocks.DIRT) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                    worldIn.setBlockState(blockpos, Blocks.GRASS_BLOCK.getDefaultState().with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)));
                } else if (RankineLists.SOIL_BLOCKS.contains(worldIn.getBlockState(blockpos).getBlock()) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
                    worldIn.setBlockState(blockpos, RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(worldIn.getBlockState(blockpos).getBlock())).getDefaultState().with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)).with(DEAD, blockstate.get(DEAD)));
                }
            }

            if (random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get() && !state.get(DEAD)) {
                BlockState aboveState = worldIn.getBlockState(pos.up());
                if (aboveState.matchesBlock(RankineBlocks.SHORT_GRASS.get())) {
                    if (random.nextFloat() < 0.5f) {
                        worldIn.setBlockState(pos.up(), Blocks.GRASS.getDefaultState(), 3);
                    }
                } else if (aboveState.matchesBlock(Blocks.GRASS) && worldIn.getBlockState(pos.up(2)).matchesBlock(Blocks.AIR)) {
                    if (random.nextFloat() < 0.3f) {
                        worldIn.setBlockState(pos.up(), Blocks.TALL_GRASS.getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 3);
                        worldIn.setBlockState(pos.up(2), Blocks.TALL_GRASS.getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
                    }
                } else if (aboveState.matchesBlock(Blocks.AIR)) {
                    Biome BIOME = worldIn.getBiome(pos);
                    if (WorldgenUtils.GEN_BIOMES.contains(BIOME.getRegistryName())) {
                        BlockState BLOCK = WorldgenUtils.VEGETATION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME.getRegistryName())).getRandomElement();
                        worldIn.setBlockState(pos.up(), BLOCK, 3);
                    }
                }
            }
        }

        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get()) {
            Block ceillingBlock = Blocks.AIR;
            int i = 1;
            while (i <= 40) {
                if (!worldIn.isAirBlock(pos.offset(Direction.UP, i)) && !(worldIn.getBlockState(pos.up(i)).isReplaceable(Fluids.WATER))) {
                    ceillingBlock = worldIn.getBlockState(pos.up(i)).getBlock();
                    break;
                }
                ++i;
            }
            if (ceillingBlock instanceof LeavesBlock && !(ceillingBlock instanceof RankineLeavesBlock) && (worldIn.getBlockState(pos.up(i - 1)).isReplaceable(Fluids.WATER) || worldIn.getBlockState(pos.up(i - 1)).matchesBlock(Blocks.AIR))) {
                if (ResourceLocation.tryCreate("rankine:"+ceillingBlock.getRegistryName().getPath().replace("leaves", "leaf_litter")) != null) {
                    worldIn.setBlockState(pos.up(i - 1), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:"+ceillingBlock.getRegistryName().getPath().replace("leaves", "leaf_litter"))).getDefaultState(), 3);
                }
            }
        }

        if (Config.GENERAL.PATH_CREATION.get() && random.nextInt(Config.GENERAL.PATH_CREATION_TIME.get()) == 0) {
            List<LivingEntity> entitiesOnBlock = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.up(), pos.up()).expand(1, 1, 1), (e) -> e instanceof PlayerEntity);
            if (!entitiesOnBlock.isEmpty() && VanillaIntegration.pathBlocks_map.containsKey(state.getBlock()) && !worldIn.getBlockState(pos.up()).getMaterial().blocksMovement()) {
                worldIn.setBlockState(pos, VanillaIntegration.pathBlocks_map.get(state.getBlock()).getDefaultState(), 3);
            }
        }
    }

    private static boolean isSnowyAndNotUnderwater(BlockState state, IWorldReader worldReader, BlockPos pos) {
        return isSnowyConditions(state, worldReader, pos) && !worldReader.getFluidState(pos.up()).isTagged(FluidTags.WATER);
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
