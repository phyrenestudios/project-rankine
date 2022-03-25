package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.SoilTypes;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RankineSoilTypeBlock extends Block {
    public static final EnumProperty<SoilTypes> SOIL_TYPE = EnumProperty.create("soil_type", SoilTypes.class);

    public RankineSoilTypeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SOIL_TYPE, SoilTypes.DIRT));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOIL_TYPE);
    }



}
