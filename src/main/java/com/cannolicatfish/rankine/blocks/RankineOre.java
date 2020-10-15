package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class RankineOre extends Block {
    public int type = 0;
    public Item nugget;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0,27);
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
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    protected int getExperience(Random rand) {
        if (this == ModBlocks.LIGNITE_ORE  || this == ModBlocks.SUBBITUMINOUS_ORE|| this == ModBlocks.NATIVE_COPPER_ORE || this == ModBlocks.NATIVE_TIN_ORE || this == ModBlocks.NATIVE_SILVER_ORE || this == ModBlocks.NATIVE_LEAD_ORE || this == ModBlocks.NATIVE_ALUMINUM_ORE || this == ModBlocks.NATIVE_GOLD_ORE || this == ModBlocks.NETHER_GOLD_ORE || this == ModBlocks.HALITE_ORE || this == ModBlocks.PINK_HALITE_ORE) {
            return MathHelper.nextInt(rand, 0, 2);
        } else if (this == ModBlocks.NATIVE_SULFUR_ORE || this == ModBlocks.NATIVE_BISMUTH_ORE || this == ModBlocks.NATIVE_ARSENIC_ORE) {
            return MathHelper.nextInt(rand, 1, 4);
        } else if (this == ModBlocks.MOISSANITE_ORE || this == ModBlocks.FLUORITE_ORE ||this == ModBlocks.ANTHRACITE_ORE || this == ModBlocks.BITUMINOUS_ORE || this == ModBlocks.QUARTZ_ORE || this == ModBlocks.LAZURITE_ORE || this == ModBlocks.NATIVE_INDIUM_ORE || this == ModBlocks.NATIVE_GALLIUM_ORE || this == ModBlocks.NATIVE_SELENIUM_ORE || this == ModBlocks.NATIVE_TELLURIUM_ORE) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == ModBlocks.EMERALD_ORE || this == ModBlocks.AQUAMARINE_ORE || this == ModBlocks.DIAMOND_ORE || this == ModBlocks.OPAL_ORE || this == ModBlocks.PLUMBAGO_ORE) {
            return MathHelper.nextInt(rand, 3, 7);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? MathHelper.nextInt(rand, 0, 1) : 0;
        }
    }

}
