package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class NoduleBlock extends Block {
    public NoduleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (player.getHeldItem(handIn).isEmpty() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots)
        {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.FLINT));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
            worldIn.setBlockState(pos, RankineBlocks.LIMESTONE.get().getDefaultState(),2);
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.PASS;
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && canHarvestBlock(state,worldIn,pos,player) && !player.isCreative())
        {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(Items.FLINT));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
