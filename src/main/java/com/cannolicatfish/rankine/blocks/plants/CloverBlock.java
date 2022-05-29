package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CloverBlock extends BushBlock {
    public CloverBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.getRandom().nextFloat() < (player.getLuck()+1)/1000f && !level.isClientSide && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !level.restoringBlockSnapshots) {
            ItemEntity itementity = new ItemEntity(level, (double) pos.getX() + (double)(level.random.nextFloat() * 0.5F) + 0.25D,
                    (double) pos.getY() + (double)(level.random.nextFloat() * 0.5F) + 0.25D, (double) pos.getZ() + (double)(level.random.nextFloat() * 0.5F) + 0.25D, new ItemStack(RankineItems.FOUR_LEAF_CLOVER.get()));
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
