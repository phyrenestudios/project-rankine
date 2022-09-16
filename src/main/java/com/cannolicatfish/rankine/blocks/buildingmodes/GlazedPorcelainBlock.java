package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class GlazedPorcelainBlock extends BuildingModeBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    int color;

    public GlazedPorcelainBlock(int color) {
        super(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.0F, 3.0F));
        this.color = color;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, getProperty());
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public int getMaxStyles() {
        return 11;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(getProperty(), ((BuildingModeBlockItem) context.getItemInHand().getItem()).getBuildingMode(context.getItemInHand()));
    }

    public BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
    }
}
