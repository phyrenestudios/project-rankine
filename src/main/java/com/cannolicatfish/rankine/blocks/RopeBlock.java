package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block {
    public static final MapCodec<RopeBlock> CODEC = simpleCodec(RopeBlock::new);

    @Override
    public MapCodec<? extends RopeBlock> codec() {
        return CODEC;
    }

    VoxelShape voxelshape = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public RopeBlock(Properties p_i49976_1_) {
        super(p_i49976_1_);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return voxelshape;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.above()).is(this) || worldIn.getBlockState(pos.above()).canOcclude();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (context.isDescending()) {
            return voxelshape;
        } else {
            return Shapes.empty();
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stackIn, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getMainHandItem().getItem().equals(this.asItem())) {
            for (int i = 1; i < pos.getY() - worldIn.getMinBuildHeight(); i++) {
                if (worldIn.getBlockState(pos.below(i)).canBeReplaced(new BlockPlaceContext(player, handIn, player.getItemInHand(handIn), hit))) {
                    worldIn.setBlockAndUpdate(pos.below(i), RankineBlocks.ROPE.get().defaultBlockState());
                    if (!player.isCreative()) player.getMainHandItem().shrink(1);
                    worldIn.playSound(null, pos.below(i), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    return ItemInteractionResult.SUCCESS;
                } else if (worldIn.getBlockState(pos.below(i)).getBlock() != RankineBlocks.ROPE.get()) {
                    break;
                }
            }

        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        int ropeCount = 0;
        int i=1;
        while (worldIn.getBlockState(pos.below(i)).is(this)) {
            worldIn.destroyBlock(pos.below(i),false);
            ropeCount += 1;
            i += 1;
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !player.isCreative()) {
            Block.popResource(worldIn, pos, new ItemStack(RankineBlocks.ROPE.get(),ropeCount));
        }
        return super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }
}
