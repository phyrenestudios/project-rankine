package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RankineStairs extends StairsBlock {

    private final Double modifier;
    public RankineStairs(BlockState state, Properties properties) {
        super(state, properties);
        this.modifier = 1.0D;
    }
    public RankineStairs(Double modifier, BlockState state, Properties properties) {
        super(state, properties);
        this.modifier = modifier;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(modifier, 1.0D, modifier));
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
