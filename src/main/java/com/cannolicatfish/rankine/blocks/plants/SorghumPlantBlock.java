package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class SorghumPlantBlock extends TripleCropsBlock {
    private static final VoxelShape[] BOTTOM_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] MIDDLE_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] TOP_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D)};

    public SorghumPlantBlock(Properties properties) {
        super(properties);
    }

    protected IItemProvider getBaseSeedId() {
        return RankineItems.SORGHUM_SEEDS.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(SECTION)) {
            case BOTTOM:
                return BOTTOM_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
            case MIDDLE:
                return MIDDLE_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
            case TOP:
                return TOP_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && state.getValue(AGE) > 2) {
            entityIn.makeStuckInBlock(state, new Vector3d((double)0.98F, 1.0D, (double)0.98F));
        }
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

}