package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class RopeBlock extends Block {

    VoxelShape voxelshape = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public RopeBlock(Properties p_i49976_1_) {
        super(p_i49976_1_);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return voxelshape;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(this) || worldIn.getBlockState(pos.above()).canOcclude();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (context.isDescending()) {
            return voxelshape;
        } else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int height = pos.getY();
        if (player.getMainHandItem().getItem().equals(this.asItem())) {
            for (int i = 1; i < height; i++) {
                if (worldIn.getBlockState(pos.below(i)).canBeReplaced(new BlockItemUseContext(player, handIn, player.getItemInHand(handIn), hit))) {
                    worldIn.setBlockAndUpdate(pos.below(i), RankineBlocks.ROPE.get().defaultBlockState());
                    if (!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                } else if (worldIn.getBlockState(pos.below(i)).getBlock() != RankineBlocks.ROPE.get()) {
                    break;
                }
            }

        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        int ropeCount = 0;
        int i=1;
        while (worldIn.getBlockState(pos.below(i)).is(this)) {
            worldIn.destroyBlock(pos.below(i),false);
            ropeCount += 1;
            i += 1;
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !player.isCreative()) {
            double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(RankineBlocks.ROPE.get(),ropeCount));
            itementity.setNoPickUpDelay();
            worldIn.addFreshEntity(itementity);
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        return false;
    }
}
