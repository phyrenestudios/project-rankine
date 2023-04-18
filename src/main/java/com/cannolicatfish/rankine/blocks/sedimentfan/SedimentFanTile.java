package com.cannolicatfish.rankine.blocks.sedimentfan;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;
import java.util.stream.Stream;

import static com.cannolicatfish.rankine.init.RankineBlocks.SEDIMENT_FAN_TILE;

public class SedimentFanTile extends BlockEntity {

    public SedimentFanTile(BlockPos posIn, BlockState stateIn) {
        super(SEDIMENT_FAN_TILE, posIn, stateIn);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, SedimentFanTile tile) {
        if (!level.isAreaLoaded(tile.worldPosition, 1) || level.getGameTime() % 20 != 0) return;
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().isVertical()) continue;
            BlockState adjState = level.getBlockState(pos.relative(dir));
            if (!level.getFluidState(pos.relative(dir,2)).is(FluidTags.WATER) || !level.getFluidState(pos.relative(dir,3)).is(FluidTags.WATER)) continue;
            RockGeneratorRecipe recipe = level.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY)) {
                    Optional<RockGeneratorRecipe> optRecipe = r.matches(new SimpleContainer(adjState.getBlock().asItem().getDefaultInstance()),level) ? Optional.of(r) : Optional.empty();
                    return DataFixUtils.orElseGet(optRecipe.map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem(RegistryAccess.EMPTY);
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    level.setBlock(pos.relative(dir,3), ((BlockItem) output.getItem()).getBlock().defaultBlockState(), 19);
                    level.playSound(null,pos.relative(dir,3), RankineSoundEvents.SEDIMENT_FAN_GEN.get(), SoundSource.BLOCKS,1.0f,1.0f);
                }
                if (level.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) level.removeBlock(pos.relative(dir),false);
            } else if (level.getFluidState(pos.relative(dir)).is(FluidTags.WATER)) {
                level.setBlock(pos.relative(dir,3), RankineBlocks.BRECCIA.get().defaultBlockState(), 19);
                level.playSound(null, pos.relative(dir,3), SoundEvents.SAND_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(Level worldIn, BlockPos pos) {
        RandomSource random = worldIn.getRandom();
        double d0 = (double)pos.getX() + random.nextDouble();
        double d1 = (double)pos.getY() - 0.05D;
        double d2 = (double)pos.getZ() + random.nextDouble();
        worldIn.addAlwaysVisibleParticle(ParticleTypes.WHITE_ASH, d0, d1, d2, 0.0D, 0.1D, 0.0D);
    }
}
