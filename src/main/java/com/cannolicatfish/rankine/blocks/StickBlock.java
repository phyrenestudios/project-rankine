package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.BeaverEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.AbstractBlock.Properties;

public class StickBlock extends RotatedPillarBlock {
    public StickBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSpeedFactor() {return 2.0f;}

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 250;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (fallDistance > 5 && (entityIn instanceof PlayerEntity)) {
            if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.STICK, 2 + worldIn.random.nextInt(2)));
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
                worldIn.playLocalSound(d0, d1, d2, SoundEvents.WOOD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                worldIn.removeBlock(pos, false);
            }
        }
        if (entityIn instanceof FallingBlockEntity) {
            List<Block> anvils = Arrays.asList(Blocks.ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DAMAGED_ANVIL);
            if (anvils.contains(((FallingBlockEntity) entityIn).getBlockState().getBlock())) {
                if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    float f = 0.5F;
                    double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.STICK, 2 + worldIn.random.nextInt(2)));
                    itementity.setDefaultPickUpDelay();
                    worldIn.addFreshEntity(itementity);
                    worldIn.playLocalSound(d0, d1, d2, SoundEvents.WOOD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                    worldIn.removeBlock(pos, false);
                }
            }
        }
    }
}
