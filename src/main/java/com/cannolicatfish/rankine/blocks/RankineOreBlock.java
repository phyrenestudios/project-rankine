package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class RankineOreBlock extends Block {
    public int type = 0;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0,61);
    public RankineOreBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE,0));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(TYPE,0);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    protected int getExperience(Random rand) {
        if (this == RankineBlocks.LIGNITE_ORE.get()  || this == RankineBlocks.SUBBITUMINOUS_ORE.get()|| this == RankineBlocks.NATIVE_COPPER_ORE.get() || this == RankineBlocks.NATIVE_TIN_ORE.get() || this == RankineBlocks.NATIVE_SILVER_ORE.get() || this == RankineBlocks.NATIVE_LEAD_ORE.get() || this == RankineBlocks.NATIVE_ALUMINUM_ORE.get() || this == RankineBlocks.NATIVE_GOLD_ORE.get() || this == RankineBlocks.HALITE_ORE.get() || this == RankineBlocks.PINK_HALITE_ORE.get()) {
            return MathHelper.nextInt(rand, 0, 2);
        } else if (this == RankineBlocks.NATIVE_SULFUR_ORE.get() || this == RankineBlocks.NATIVE_BISMUTH_ORE.get() || this == RankineBlocks.NATIVE_ARSENIC_ORE.get()) {
            return MathHelper.nextInt(rand, 1, 4);
        } else if (this == RankineBlocks.MOISSANITE_ORE.get() || this == RankineBlocks.FLUORITE_ORE.get() ||this == RankineBlocks.ANTHRACITE_ORE.get() || this == RankineBlocks.BITUMINOUS_ORE.get() || this == RankineBlocks.QUARTZ_ORE.get() || this == RankineBlocks.LAZURITE_ORE.get() || this == RankineBlocks.NATIVE_INDIUM_ORE.get() || this == RankineBlocks.NATIVE_GALLIUM_ORE.get() || this == RankineBlocks.NATIVE_SELENIUM_ORE.get() || this == RankineBlocks.NATIVE_TELLURIUM_ORE.get()) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == RankineBlocks.MAJORITE_ORE.get() || this == RankineBlocks.EMERALD_ORE.get() || this == RankineBlocks.AQUAMARINE_ORE.get() || this == RankineBlocks.DIAMOND_ORE.get() || this == RankineBlocks.OPAL_ORE.get() || this == RankineBlocks.PLUMBAGO_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 7);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? MathHelper.nextInt(rand, 0, 1) : 0;
        }
    }

}
