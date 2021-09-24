package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class AntimatterBlock extends Block {
    public AntimatterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        worldIn.createExplosion(null, pos.getX()+0.5, pos.getY()+2, pos.getZ()+0.5, 10.0F, Explosion.Mode.BREAK);
        worldIn.removeBlock(pos, false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getHeldItem(handIn).getItem() == Items.NETHER_STAR) {
            worldIn.setBlockState(pos, RankineBlocks.UNAMED_EXPLOSIVE.get().getDefaultState(),3);
        } else if (player.getHeldItem(handIn).getItem() == Items.DRAGON_EGG) {
            worldIn.setBlockState(pos,Blocks.DRAGON_EGG.getDefaultState(),3);
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
