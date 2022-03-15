package com.cannolicatfish.rankine.mixin.minecraft;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

//@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

    /**
     * @author CannoliCatfish
     * @reason Changes AnimalEntity spawning behavior to include any block in the block tag "forge:grass".
     */
  //  @Overwrite
    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(RankineTags.Blocks.GRASS_BLOCKS) && worldIn.getRawBrightness(pos, 0) > 8;
    }
}
