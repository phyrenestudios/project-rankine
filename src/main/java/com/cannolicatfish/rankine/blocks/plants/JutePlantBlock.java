package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class JutePlantBlock extends TripleCropsBlock {

    public JutePlantBlock(Properties properties) {
        super(properties);
    }

    protected IItemProvider getBaseSeedId() {
        return RankineItems.JUTE_SEEDS.get();
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && state.getValue(AGE) > 2) {
            entityIn.makeStuckInBlock(state, new Vector3d((double)0.95F, 1.0D, (double)0.95F));
        }
    }

}
