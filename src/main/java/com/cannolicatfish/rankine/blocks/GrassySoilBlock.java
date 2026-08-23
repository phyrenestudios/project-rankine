package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

// PORT-NOTE: Material.GRASS (implicit MaterialColor.GRASS) -> .mapColor(MapColor.GRASS). Cross-checked against
// vanilla Blocks.GRASS_BLOCK, which carries no .instrument() in 1.21.1, so none is added here.
// SpreadingSnowyDirtBlock#codec() is abstract in 1.21.1, so the MapCodec below is mandatory.
public class GrassySoilBlock extends GrassBlock {
    // GrassBlock.codec() is not wildcarded (returns the concrete MapCodec<GrassBlock>), so this subclass's codec()
    // must match that exact signature; the cast is safe since generics are erased at runtime.
    public static final MapCodec<GrassySoilBlock> CODEC = MapCodec.unit(GrassySoilBlock::new);
    public static final BooleanProperty DEAD = BooleanProperty.create("dead");

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<GrassBlock> codec() {
        return (MapCodec<GrassBlock>) (MapCodec<?>) CODEC;
    }

    public GrassySoilBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
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
    protected void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
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
                // PORT-NOTE: Block#getRegistryName() is gone -> BuiltInRegistries.BLOCK.getKey(block);
                // ForgeRegistries.BLOCKS.getValue(rl) -> BuiltInRegistries.BLOCK.get(rl).
                ResourceLocation litter = ResourceLocation.tryParse("rankine:" + BuiltInRegistries.BLOCK.getKey(ceillingBlock).getPath().replace("leaves", "leaf_litter"));
                if (litter != null) {
                    levelIn.setBlock(pos.above(i - 1), BuiltInRegistries.BLOCK.get(litter).defaultBlockState(), 3);
                }
            }
        }

        if (Config.GENERAL.PATH_CREATION.get() && random.nextInt(Config.GENERAL.PATH_CREATION_TIME.get()) == 0) {
            List<LivingEntity> entitiesOnBlock = levelIn.getEntitiesOfClass(LivingEntity.class, RankineMathHelper.boxBetween(pos.above(), pos.above()).expandTowards(1, 1, 1), (e) -> e instanceof Player);
            if (!entitiesOnBlock.isEmpty() && VanillaIntegration.pathBlocks_map.containsKey(state.getBlock()) && !levelIn.getBlockState(pos.above()).blocksMotion()) {
                levelIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(state.getBlock()).defaultBlockState(), 3);
            }
        }

    }

    // PORT-NOTE: java.util.Random -> RandomSource; other ported callers pass level.random / the randomTick source.
    public void growGrassBlock(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
        BlockState aboveState = levelIn.getBlockState(pos.above());
        if (aboveState.is(RankineBlocks.SHORT_GRASS.get())) {
            if (random.nextFloat() < 0.5f) {
                levelIn.setBlock(pos.above(), Blocks.SHORT_GRASS.defaultBlockState(), 3);
            }
        } else if (aboveState.is(Blocks.SHORT_GRASS) && levelIn.getBlockState(pos.above(2)).is(Blocks.AIR)) {
            if (random.nextFloat() < 0.3f) {
                levelIn.setBlock(pos.above(), Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 3);
                levelIn.setBlock(pos.above(2), Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
            }
        } else if (aboveState.is(Blocks.AIR)) {
            // PORT-NOTE: Biome#getRegistryName() is gone; WorldgenUtils.biomeLocation(Holder<Biome>) is the
            // established replacement used by the rest of this port.
            ResourceLocation BIOME = WorldgenUtils.biomeLocation(levelIn.getBiome(pos));
            if (WorldgenUtils.GEN_BIOMES.contains(BIOME)) {
                BlockState BLOCK = WorldgenUtils.VEGETATION_COLLECTIONS.get(WorldgenUtils.GEN_BIOMES.indexOf(BIOME)).getRandomElement();
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
            // PORT-NOTE: LayerLightEngine was renamed LightEngine in 1.20+.
            int i = LightEngine.getLightBlockInto(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(worldReader, blockpos));
            return i < worldReader.getMaxLightLevel();
        }
    }

}
