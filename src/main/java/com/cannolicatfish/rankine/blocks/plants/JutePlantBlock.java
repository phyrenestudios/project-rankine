package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class JutePlantBlock extends TripleCropsBlock {

    public JutePlantBlock(Properties properties) {
        super(properties);
    }

    protected ItemLike getBaseSeedId() {
        return RankineItems.JUTE_SEEDS.get();
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && state.getValue(AGE) > 2) {
            entityIn.makeStuckInBlock(state, new Vec3((double)0.95F, 1.0D, (double)0.95F));
        }
    }

}
