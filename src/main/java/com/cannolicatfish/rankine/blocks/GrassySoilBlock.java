package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class GrassySoilBlock extends GrassBlock {
    SoilBlocks soilType;

    public GrassySoilBlock(SoilBlocks soilType) {
        super(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
        this.soilType = soilType;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (ToolActions.HOE_TILL == toolAction && (context.getLevel().getBlockState(context.getClickedPos().above()).isAir())) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getFarmlandBlock().defaultBlockState();
        } else if (ToolActions.SHOVEL_FLATTEN == toolAction) {
            return SoilBlocks.getSoilFromBlock(state.getBlock()).getPathBlock().defaultBlockState();
        }
        return null;
    }

    private static boolean canBeGrass(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
        BlockPos blockpos = p_56826_.above();
        BlockState blockstate = p_56825_.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(p_56825_, blockpos));
            return i < p_56825_.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
        BlockPos blockpos = p_56830_.above();
        return canBeGrass(p_56828_, p_56829_, p_56830_) && !p_56829_.getFluidState(blockpos).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState stateIn, ServerLevel levelIn, BlockPos posIn, RandomSource randomIn) {
        if (!canBeGrass(stateIn, levelIn, posIn)) {
            if (!levelIn.isAreaLoaded(posIn, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            levelIn.setBlockAndUpdate(posIn, soilType.getSoilBlock().defaultBlockState());
        } else {
            if (!levelIn.isAreaLoaded(posIn, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (levelIn.getMaxLocalRawBrightness(posIn.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = posIn.offset(randomIn.nextInt(3) - 1, randomIn.nextInt(5) - 3, randomIn.nextInt(3) - 1);
                    if ((levelIn.getBlockState(blockpos).is(Blocks.DIRT) || SoilBlocks.getSoilFromBlock(levelIn.getBlockState(blockpos).getBlock()) != null) && canPropagate(blockstate, levelIn, blockpos)) {
                        levelIn.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, levelIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
                    }
                }
            }

        }

        if (randomIn.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get()) {
            Block ceillingBlock = Blocks.AIR;
            int i = 1;
            while (i <= 40) {
                if (!levelIn.isEmptyBlock(posIn.relative(Direction.UP, i)) && !(levelIn.getBlockState(posIn.above(i)).canBeReplaced(Fluids.WATER))) {
                    ceillingBlock = levelIn.getBlockState(posIn.above(i)).getBlock();
                    break;
                }
                ++i;
            }
            if (ceillingBlock instanceof LeavesBlock && !(ceillingBlock instanceof RankineLeavesBlock) && (levelIn.getBlockState(posIn.above(i - 1)).canBeReplaced(Fluids.WATER) || levelIn.getBlockState(posIn.above(i - 1)).is(Blocks.AIR))) {
                if (ResourceLocation.tryParse("rankine:"+ForgeRegistries.BLOCKS.getKey(ceillingBlock).getPath().replace("leaves", "leaf_litter")) != null) {
                    levelIn.setBlock(posIn.above(i - 1), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:"+ForgeRegistries.BLOCKS.getKey(ceillingBlock).getPath().replace("leaves", "leaf_litter"))).defaultBlockState(), 3);
                }
            }
        } else if (randomIn.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get()) {
            growGrassBlock(stateIn,levelIn,posIn,randomIn);
        }
    }

        /*
        if (Config.GENERAL.PATH_CREATION.get() && random.nextInt(Config.GENERAL.PATH_CREATION_TIME.get()) == 0) {
            List<LivingEntity> entitiesOnBlock = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(pos.above(), pos.above()).expandTowards(1, 1, 1), (e) -> e instanceof Player);
            if (!entitiesOnBlock.isEmpty() && VanillaIntegration.pathBlocks_map.containsKey(state.getBlock()) && !levelIn.getBlockState(pos.above()).getMaterial().blocksMotion()) {
                levelIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(state.getBlock()).defaultBlockState(), 3);
            }
        }
         */


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


}
