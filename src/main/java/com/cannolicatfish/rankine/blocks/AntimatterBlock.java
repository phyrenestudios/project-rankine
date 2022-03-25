package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AntimatterBlock extends Block {
    public AntimatterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        explode(worldIn, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() != RankineItems.PENNING_TRAP.get()) {
            explode(worldIn, pos);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    public static void explode(Level worldIn, BlockPos pos) {
        if (!worldIn.isClientSide) {
            worldIn.explode(null, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, 15.0F, Explosion.BlockInteraction.BREAK);
            worldIn.removeBlock(pos, false);






        }
    }
}
