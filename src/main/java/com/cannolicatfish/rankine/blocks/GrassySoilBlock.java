package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class GrassySoilBlock extends GrassBlock {
    public static final BooleanProperty DEAD = BooleanProperty.create("dead");

    public GrassySoilBlock() {
        super(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, false).setValue(DEAD, false));
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return !state.getValue(DEAD);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY,DEAD);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
        if (!isSnowyConditions(state, levelIn, pos)) {
            if (!levelIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (RankineLists.GRASS_BLOCKS.contains(state.getBlock())) {
                levelIn.setBlockAndUpdate(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(state.getBlock())).defaultBlockState());
            } else {
                levelIn.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
            return;
        }

        if (levelIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
            BlockState blockstate = this.defaultBlockState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (levelIn.getBlockState(blockpos).is(Blocks.DIRT) && isSnowyAndNotUnderwater(blockstate, levelIn, blockpos)) {
                    levelIn.setBlockAndUpdate(blockpos, Blocks.GRASS_BLOCK.defaultBlockState().setValue(SNOWY, levelIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                } else if (RankineLists.SOIL_BLOCKS.contains(levelIn.getBlockState(blockpos).getBlock()) && isSnowyAndNotUnderwater(blockstate, levelIn, blockpos)) {
                    levelIn.setBlockAndUpdate(blockpos, RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(levelIn.getBlockState(blockpos).getBlock())).defaultBlockState().setValue(SNOWY, levelIn.getBlockState(blockpos.above()).is(Blocks.SNOW)).setValue(DEAD, blockstate.getValue(DEAD)));
                }
            }
            if (random.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get() && !state.getValue(DEAD)) {
                growGrassBlock(state,levelIn,pos,random);
            }
        }

        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get()) {
            Block ceillingBlock = Blocks.AIR;
            int i = 1;
            while (i <= 40) {
                if (!levelIn.isEmptyBlock(pos.relative(Direction.UP, i)) && !(levelIn.getBlockState(pos.above(i)).canBeReplaced(Fluids.WATER))) {
                    ceillingBlock = levelIn.getBlockState(pos.above(i)).getBlock();
                    break;
                }
                ++i;
            }
            if (ceillingBlock instanceof LeavesBlock && !(ceillingBlock instanceof RankineLeavesBlock) && (levelIn.getBlockState(pos.above(i - 1)).canBeReplaced(Fluids.WATER) || levelIn.getBlockState(pos.above(i - 1)).is(Blocks.AIR))) {
                if (ResourceLocation.tryParse("rankine:"+ForgeRegistries.BLOCKS.getKey(ceillingBlock).getPath().replace("leaves", "leaf_litter")) != null) {
                    levelIn.setBlock(pos.above(i - 1), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:"+ForgeRegistries.BLOCKS.getKey(ceillingBlock).getPath().replace("leaves", "leaf_litter"))).defaultBlockState(), 3);
                }
            }
        }

        if (Config.GENERAL.PATH_CREATION.get() && random.nextInt(Config.GENERAL.PATH_CREATION_TIME.get()) == 0) {
            List<LivingEntity> entitiesOnBlock = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(pos.above(), pos.above()).expandTowards(1, 1, 1), (e) -> e instanceof Player);
            if (!entitiesOnBlock.isEmpty() && VanillaIntegration.pathBlocks_map.containsKey(state.getBlock()) && !levelIn.getBlockState(pos.above()).getMaterial().blocksMotion()) {
                levelIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(state.getBlock()).defaultBlockState(), 3);
            }
        }

    }

    public void growGrassBlock(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
        BlockState aboveState = levelIn.getBlockState(pos.above());
        if (aboveState.is(RankineBlocks.SHORT_GRASS.get())) {
            if (random.nextFloat() < 0.5f) {
                levelIn.setBlock(pos.above(), Blocks.GRASS.defaultBlockState(), 3);
            }
        } else if (aboveState.is(Blocks.GRASS) && levelIn.getBlockState(pos.above(2)).is(Blocks.AIR)) {
            if (random.nextFloat() < 0.3f) {
                levelIn.setBlock(pos.above(), Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 3);
                levelIn.setBlock(pos.above(2), Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
            }
        } else if (aboveState.is(Blocks.AIR)) {
            Biome BIOME = levelIn.getBiome(pos).value();
            if (WorldgenUtils.GEN_BIOMES.contains(ForgeRegistries.BIOMES.getKey(BIOME))) {
                BlockState BLOCK = WorldgenUtils.VEGETATION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(ForgeRegistries.BIOMES.getKey(BIOME))).getRandomElement();
                levelIn.setBlock(pos.above(), BLOCK, 3);
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
