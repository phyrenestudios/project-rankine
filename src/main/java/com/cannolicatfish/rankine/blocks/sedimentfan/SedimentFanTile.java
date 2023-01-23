package com.cannolicatfish.rankine.blocks.sedimentfan;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.cannolicatfish.rankine.init.RankineBlocks.SEDIMENT_FAN_TILE;

public class SedimentFanTile extends BlockEntity {

    public SedimentFanTile(BlockPos posIn, BlockState stateIn) {
        super(SEDIMENT_FAN_TILE, posIn, stateIn);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, SedimentFanTile tile) {
        if (!level.isAreaLoaded(tile.worldPosition, 1) || level.getGameTime() % 20 != 0) return;
        List<Direction> dir = Arrays.asList(Direction.NORTH,Direction.SOUTH,Direction.EAST,Direction.WEST);
        List<BlockState> adjPos = Arrays.asList(level.getBlockState(pos.north()), level.getBlockState(pos.south()),level.getBlockState(pos.east()),level.getBlockState(pos.west()));
        List<FluidState> waterPos = Arrays.asList(level.getFluidState(pos.relative(Direction.NORTH,2)), level.getFluidState(pos.relative(Direction.SOUTH,2)),level.getFluidState(pos.relative(Direction.EAST,2)),level.getFluidState(pos.relative(Direction.WEST,2)));
        for (int i = 0; i < adjPos.size(); i++) {
            BlockState adjState = adjPos.get(i);
            FluidState adjWater = waterPos.get(i);
            if (adjWater.is(Fluids.WATER) || adjWater.is(Fluids.FLOWING_WATER)) {
                RockGeneratorRecipe recipe = level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                    if (r.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY)) {
                        return DataFixUtils.orElseGet(RankineRecipeTypes.ROCK_GENERATOR.tryMatch(r, level, new SimpleContainer(adjState.getBlock().asItem().getDefaultInstance())).map(Stream::of),Stream::empty);
                    }
                    return null;
                }).findFirst().orElse(null);
                if (recipe != null) {
                    ItemStack output = recipe.getResultItem();
                    if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                        level.setBlock(pos.relative(dir.get(i),2), ((BlockItem) output.getItem()).getBlock().defaultBlockState(), 19);
                        level.playSound(null,pos.relative(dir.get(i),2), RankineSoundEvents.SEDIMENT_FAN_GEN.get(), SoundSource.BLOCKS,1.0f,1.0f);
                    }
                    level.removeBlock(pos.relative(dir.get(i)),false);
                } else {
                    level.setBlock(pos.relative(dir.get(i),2), RankineBlocks.BRECCIA.get().defaultBlockState(), 19);
                    level.playSound(null, pos.relative(dir.get(i),2), SoundEvents.SAND_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(Level worldIn, BlockPos pos) {
        Random random = worldIn.getRandom();
        double d0 = (double)pos.getX() + random.nextDouble();
        double d1 = (double)pos.getY() - 0.05D;
        double d2 = (double)pos.getZ() + random.nextDouble();
        worldIn.addAlwaysVisibleParticle(ParticleTypes.WHITE_ASH, d0, d1, d2, 0.0D, 0.1D, 0.0D);
    }
}
