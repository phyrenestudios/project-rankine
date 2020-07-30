package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class CreepingBlock extends Block {
    private final float GROWTH;
    public CreepingBlock(float growthSpeed, Properties properties) {
        super(properties);
        this.GROWTH = growthSpeed;
    }

   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
       super.tick(state, worldIn, pos, rand);

       if (rand.nextFloat() < GROWTH) {
           pos = pos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
           int i = 2;
           for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
               if (worldIn.getBlockState(blockpos).getBlock() == this) {
                   --i;
                   if (i <= 0) {
                       return;
                   }
               }
           }
           if (worldIn.isAirBlock(pos)) {
               worldIn.setBlockState(pos, state, 2);
           }
       }


    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }


    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
        world.createExplosion(igniter, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 2.4F, Explosion.Mode.BREAK);
        world.removeBlock(pos, false);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.isBlockPowered(pos)) {
            catchFire(state, worldIn, pos, null, null);
        }
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 280;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
        } else {
            catchFire(state, worldIn, pos, p_225533_6_.getFace(), player);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.damageItem(1, player, (p_220287_1_) -> {
                        p_220287_1_.sendBreakAnimation(handIn);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            Entity entity = abstractarrowentity.func_234616_v_();
            if (abstractarrowentity.isBurning()) {
                BlockPos blockpos = hit.getPos();
                catchFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            }
        }
    }

    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            catchFire(worldIn.getBlockState(pos), worldIn, pos, null,null);
        }
    }

    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }


}
