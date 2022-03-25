package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MetalLadderBlock extends LadderBlock {

    private boolean teleport;
    private boolean autoPlace;
    public MetalLadderBlock(Boolean teleport, Boolean autoPlace, Properties builder) {
        super(builder);
        this.teleport = teleport;
        this.autoPlace = autoPlace;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return canAttachTo(worldIn, pos.relative(direction.getOpposite()), direction) || worldIn.getBlockState(pos.below()).is(this);
    }

    private boolean canAttachTo(BlockGetter blockReader, BlockPos pos, Direction direction) {
        BlockState blockstate = blockReader.getBlockState(pos);
        return blockstate.isFaceSturdy(blockReader, pos, direction);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (this.teleport) {
            int n = 1;
            while (world.getBlockState(pos.above(n)).getBlock() == this) {
                n += 1;
            }
            if (!world.isClientSide) {
                player.teleportTo(pos.getX() + .5f, pos.getY() + n, pos.getZ() + .5f);
                return InteractionResult.PASS;
            }
        }
        if (this.autoPlace) {
            if (world.isEmptyBlock(pos.above()) && player.getInventory().contains(new ItemStack(state.getBlock()))) {
                world.setBlock(pos.above(), state, 2);

            }
            return InteractionResult.PASS;
        }
        return super.use(state, world, pos, player, hand, result);
    }

}
