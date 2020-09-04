package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RopeCoilBlock extends Block {
    public RopeCoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        for (int i = 1; i < 8; i++)
        {
            if (worldIn.isAirBlock(pos.down(i)))
            {
                worldIn.setBlockState(pos.down(i), ModBlocks.ROPE.getDefaultState());
            } else
            {
                break;
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int height = pos.getY();
        if (player.getHeldItemMainhand().getItem() == ModBlocks.ROPE.asItem())
        {

            for (int i = 1; i < height; i++)
            {
                if (worldIn.isAirBlock(pos.down(i)))
                {
                    worldIn.setBlockState(pos.down(i), ModBlocks.ROPE.getDefaultState());
                    if (!player.isCreative()) {
                        player.getHeldItemMainhand().shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                } else if (worldIn.getBlockState(pos.down(i)).getBlock() != ModBlocks.ROPE)
                {
                    break;
                }
            }

        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        for (int i = 1; i < 8; i++)
        {
            if (worldIn.getBlockState(pos.down(i)).getBlock() == ModBlocks.ROPE)
            {
                worldIn.destroyBlock(pos.down(i),false);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
