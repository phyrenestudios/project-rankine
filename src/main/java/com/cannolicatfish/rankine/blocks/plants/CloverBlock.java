package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CloverBlock extends BushBlock {
    public CloverBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (worldIn.getRandom().nextFloat() < (player.getLuck()+1)/1000f && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D,
                    (double) pos.getY() + (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D, (double) pos.getZ() + (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D, new ItemStack(RankineItems.FOUR_LEAF_CLOVER.get()));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
