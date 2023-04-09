package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StickBlock extends RotatedPillarBlock {
    public StickBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSpeedFactor() {return 2.0f;}

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 250;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (fallDistance > 5 && (entityIn instanceof Player)) {
            if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.STICK, 2 + worldIn.random.nextInt(2)));
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
                worldIn.playLocalSound(d0, d1, d2, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);
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
                    worldIn.playLocalSound(d0, d1, d2, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    worldIn.removeBlock(pos, false);
                }
            }
        }
    }
}
