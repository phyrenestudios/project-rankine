package com.cannolicatfish.rankine.blocks;

import jdk.nashorn.internal.ir.annotations.Ignore;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RankineOre extends Block {
    public int type = 0;
    public Item nugget;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0,13);
    public RankineOre(Properties properties, Item nugget) {
        super(properties);
        this.nugget = nugget;
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE,0));
    }
    public RankineOre(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE,0));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(TYPE,0);
    }

    public void spawnAdditionalDrops(BlockState state, World worldIn, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }


    public ItemStack getNugget()
    {
        return new ItemStack(nugget, 1);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

}
