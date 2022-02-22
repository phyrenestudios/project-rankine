package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AntimatterBlock extends Block {
    public AntimatterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        explode(worldIn, pos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() != RankineItems.PENNING_TRAP.get()) {
            explode(worldIn, pos);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public static void explode(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            worldIn.createExplosion(null, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, 15.0F, Explosion.Mode.BREAK);
            worldIn.removeBlock(pos, false);
        }
    }
}
