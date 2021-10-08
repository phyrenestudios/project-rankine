package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CamphorBasilPlantBlock extends CropsBlock {
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)};

    public CamphorBasilPlantBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected IItemProvider getSeedsItem() {
        return RankineItems.CAMPHOR_BASIL_SEEDS.get();
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(this.getAgeProperty())];
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Tags.Blocks.DIRT);
    }
}