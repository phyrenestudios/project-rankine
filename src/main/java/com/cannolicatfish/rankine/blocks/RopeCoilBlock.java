package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RopeCoilBlock extends Block {
    public RopeCoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        for (int i = 1; i < 9; i++) {
            if (worldIn.isAirBlock(pos.down(i))) {
                worldIn.setBlockState(pos.down(i), ModBlocks.ROPE.getDefaultState());
            } else {
                break;
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int height = pos.getY();
        if (player.getHeldItemMainhand().getItem() == ModBlocks.ROPE.asItem()) {
            for (int i = 1; i < height; i++) {
                if (worldIn.isAirBlock(pos.down(i))) {
                    worldIn.setBlockState(pos.down(i), ModBlocks.ROPE.getDefaultState());
                    if (!player.isCreative()) {
                        player.getHeldItemMainhand().shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                } else if (worldIn.getBlockState(pos.down(i)).getBlock() != ModBlocks.ROPE) {
                    break;
                }
            }

        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        int ropeCount = 0;
        int i=1;
        while (!worldIn.getBlockState(new BlockPos(pos.down(i))).isSolid()) {
            if (worldIn.getBlockState(pos.down(i)).getBlock() == ModBlocks.ROPE) {
                worldIn.destroyBlock(pos.down(i),false);
                ropeCount += 1;
            }
            i += 1;
        }
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !player.isCreative()) {
            if (ropeCount < 8) {
                ropeCount = 8;    
            }
            double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(ModBlocks.ROPE,ropeCount));
            itementity.setNoPickupDelay();
            worldIn.addEntity(itementity);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
