package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RankineOre extends Block {
    public int type = 0;
    public Item nugget;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0,24);
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
    

}
