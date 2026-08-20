package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.blocks.states.RankineBlockStateProperties;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    /**
     * Pick-block keeps the style, exactly as 1.18.2's
     * {@code stack.getOrCreateTag().putShort("building_mode", ...)} did (BuildingModeBlock.java:46
     * at 859f87773).
     * <p>
     * The value goes into {@link RankineDataComponents#BUILDING_MODE}, the component
     * {@link BuildingModeBlockItem#getBuildingMode} reads and {@link BuildingModeBlockItem#use}
     * writes. It used to be written into {@code minecraft:custom_data} under the old NBT key, which
     * nothing reads, so a picked block came back as mode 1 and re-placed as the wrong variant.
     */
    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(state.getBlock());
        stack.set(RankineDataComponents.BUILDING_MODE.get(), state.getValue(getProperty()));
        return stack;
    }
}
