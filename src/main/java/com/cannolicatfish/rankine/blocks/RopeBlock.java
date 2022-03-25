package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RopeBlock extends Block {

    VoxelShape voxelshape = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public RopeBlock(Properties p_i49976_1_) {
        super(p_i49976_1_);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return voxelshape;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(this) || worldIn.getBlockState(pos.above()).canOcclude();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (context.isDescending()) {
            return voxelshape;
        } else {
            return Shapes.empty();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int height = pos.getY();
        if (player.getMainHandItem().getItem().equals(this.asItem())) {
            for (int i = 1; i < height; i++) {
                if (worldIn.getBlockState(pos.below(i)).canBeReplaced(new BlockPlaceContext(player, handIn, player.getItemInHand(handIn), hit))) {
                    worldIn.setBlockAndUpdate(pos.below(i), RankineBlocks.ROPE.get().defaultBlockState());
                    if (!player.isCreative()) {
                        player.getMainHandItem().shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                } else if (worldIn.getBlockState(pos.below(i)).getBlock() != RankineBlocks.ROPE.get()) {
                    break;
                }
            }

        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
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
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }
}
