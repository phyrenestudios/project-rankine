package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.recipe.helper.ConfigHelper;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RankineOreBlock extends Block {
    public int type = 0;
    private List<String> hlpath = new ArrayList<>();
    private int hl = -1;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0, WGConfig.MISC.ORE_STONES.get().size() -1);
    public RankineOreBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE,0));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        if (context.getPlayer().isSneaking()) {
            return this.getDefaultState().with(TYPE, world.getBlockState(context.getPos().offset(context.getFace().getOpposite())).get(TYPE));
        } else {
            return this.getDefaultState().with(TYPE,0);
        }

    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        if (hl == -1 && this.getRegistryName() != null) {
            hl = modifyHarvestLevel(state);
        }
        return hl;
    }

    private int modifyHarvestLevel(BlockState state) {
        if (this.hlpath.size() == 0 && this.getRegistryName() != null) {
            this.hlpath.add("oregen");
            StringBuilder s = new StringBuilder();
            int count = 0;
            for (String s1 : this.getRegistryName().getPath().split("_")) {
                if (count == 0) {
                    s.append(s1);
                } else {
                    s.append(s1.substring(0, 1).toUpperCase()).append(s1.substring(1));
                }
                count++;
            }
            this.hlpath.add(s.toString());
            s.append("HL");
            this.hlpath.add(s.toString());
        }
        //System.out.println("GENERATED PATH: " + this.hlpath);
        int x = ConfigHelper.getOreHarvestLevel(this.hlpath);
        if (x == -1) {
            //System.out.println("HL NOT FOUND");
            return super.getHarvestLevel(state);
        } else {
            return x;
        }
    }

    protected int getExperience(Random rand) {
        if (this == RankineBlocks.LIGNITE_ORE.get()  || this == RankineBlocks.SUBBITUMINOUS_ORE.get() || this == RankineBlocks.NATIVE_TIN_ORE.get() || this == RankineBlocks.NATIVE_SILVER_ORE.get() || this == RankineBlocks.NATIVE_LEAD_ORE.get()|| this == RankineBlocks.NATIVE_GOLD_ORE.get() || this == RankineBlocks.HALITE_ORE.get()) {
            return MathHelper.nextInt(rand, 0, 2);
        } else if (this == RankineBlocks.NATIVE_SULFUR_ORE.get() || this == RankineBlocks.NATIVE_BISMUTH_ORE.get() || this == RankineBlocks.NATIVE_ARSENIC_ORE.get()) {
            return MathHelper.nextInt(rand, 1, 4);
        } else if (this == RankineBlocks.ANTHRACITE_ORE.get() || this == RankineBlocks.BITUMINOUS_ORE.get() || this == RankineBlocks.QUARTZ_ORE.get() || this == RankineBlocks.LAZURITE_ORE.get() || this == RankineBlocks.NATIVE_INDIUM_ORE.get() || this == RankineBlocks.NATIVE_GALLIUM_ORE.get() || this == RankineBlocks.NATIVE_SELENIUM_ORE.get() || this == RankineBlocks.NATIVE_TELLURIUM_ORE.get()) {
            return MathHelper.nextInt(rand, 2, 5);
        } else if (this == RankineBlocks.EMERALD_ORE.get()|| this == RankineBlocks.DIAMOND_ORE.get() || this == RankineBlocks.PLUMBAGO_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 7);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? MathHelper.nextInt(rand, 0, 1) : 0;
        }
    }

}
