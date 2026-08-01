package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.blocks.states.RankineBlockStateProperties;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
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

    @Override
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
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(state.getBlock());
        // PORT-TODO(datacomponents): 1.18 used stack.getOrCreateTag().putShort("building_mode", ...). Stored in
        // minecraft:custom_data with the same key for now; switch to the RankineDataComponents building-mode
        // component once BuildingModeBlockItem (items package) defines its component storage.
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putShort("building_mode", state.getValue(getProperty()).shortValue()));
        return stack;
    }
}
