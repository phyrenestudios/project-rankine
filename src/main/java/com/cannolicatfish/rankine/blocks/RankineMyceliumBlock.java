package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_enums.SoilBlocks;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class RankineMyceliumBlock extends MyceliumBlock {
    SoilBlocks soilType;

    public RankineMyceliumBlock(SoilBlocks soilType) {
        super(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).randomTicks().strength(0.6F).sound(SoundType.GRASS));
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

        if (levelIn.getBlockState(posIn.above()).is(Blocks.AIR) && randomIn.nextFloat() < Config.GENERAL.GRASS_GROW_CHANCE.get()) {
            levelIn.setBlock(posIn.above(),randomIn.nextBoolean() ? Blocks.RED_MUSHROOM.defaultBlockState() : Blocks.BROWN_MUSHROOM.defaultBlockState(),2);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter levelIn, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable instanceof SugarCaneBlock) {
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = levelIn.getBlockState(pos.relative(direction));
                FluidState fluidstate = levelIn.getFluidState(pos.relative(direction));
                if (fluidstate.is(FluidTags.WATER) || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
            return false;
        }
        return plantable.getPlantType(levelIn, pos.relative(facing)).equals(PlantType.PLAINS) || plantable.getPlantType(levelIn, pos.relative(facing)).equals(PlantType.BEACH);
    }

}
