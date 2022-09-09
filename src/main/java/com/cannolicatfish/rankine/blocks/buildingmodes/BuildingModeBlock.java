package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.blocks.states.RankineBlockStateProperties;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class BuildingModeBlock extends Block {

    public BuildingModeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(getProperty(), 1));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getProperty());
    }

    public int getMaxStyles() {
        return 2;
    }

    public IntegerProperty getProperty() {
        return RankineBlockStateProperties.getStyleProperty(getMaxStyles() - 1);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(getProperty(), ((BuildingModeBlockItem) context.getItemInHand().getItem()).getBuildingMode(context.getItemInHand()));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(state.getBlock());
        stack.getOrCreateTag().putShort("building_mode", state.getValue(getProperty()).shortValue());
        return stack;
    }
}
