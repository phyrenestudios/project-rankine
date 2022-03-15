package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.SoilTypes;
import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineSoilTypeBlock extends Block {
    public static final EnumProperty<SoilTypes> SOIL_TYPE = EnumProperty.create("soil_type", SoilTypes.class);

    public RankineSoilTypeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SOIL_TYPE, SoilTypes.DIRT));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SOIL_TYPE);
    }



}
