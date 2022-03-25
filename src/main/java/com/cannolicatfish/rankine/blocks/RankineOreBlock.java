package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.recipe.helper.ConfigHelper;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RankineOreBlock extends Block {
    public int type = 0;
    private List<String> hlpath = new ArrayList<>();
    private int hl = -1;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0, WorldgenUtils.ORE_TEXTURES.size() -1);
    public RankineOreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE,0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockState target = world.getBlockState(context.getClickedPos().relative(context.getClickedFace().getOpposite()));
        if (target.getBlock() instanceof  RankineOreBlock) {
            return this.defaultBlockState().setValue(TYPE, target.getValue(TYPE));
        } else if (WorldgenUtils.ORE_STONES.contains(target.getBlock())) {
            return this.defaultBlockState().setValue(TYPE, WorldgenUtils.ORE_STONES.indexOf(target.getBlock()));
        }
        return this.defaultBlockState().setValue(TYPE,0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
        //return state.get(TYPE) == 0;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        for (Direction d : Direction.values()) {
            BlockState BS = worldIn.getBlockState(pos.relative(d));
            if (BS.getBlock() instanceof RankineOreBlock && BS.getValue(TYPE) != 0) {
                worldIn.setBlockAndUpdate(pos,state.setValue(TYPE, BS.getValue(TYPE)));
                break;
            } else if (BS.getBlock() != Blocks.STONE && WorldgenUtils.ORE_STONES.contains(BS.getBlock())) {
                worldIn.setBlockAndUpdate(pos,state.setValue(TYPE, WorldgenUtils.ORE_STONES.indexOf(BS.getBlock())));
                break;
            }
        }
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    protected int getExperience(Random rand) {
        if (this == RankineBlocks.LIGNITE_ORE.get()  || this == RankineBlocks.SUBBITUMINOUS_ORE.get() || this == RankineBlocks.NATIVE_TIN_ORE.get() || this == RankineBlocks.NATIVE_SILVER_ORE.get() || this == RankineBlocks.NATIVE_LEAD_ORE.get()|| this == RankineBlocks.NATIVE_GOLD_ORE.get() || this == RankineBlocks.STIBNITE_ORE.get()) {
            return Mth.nextInt(rand, 0, 2);
        } else if (this == RankineBlocks.PORPHYRY_COPPER.get() || this == RankineBlocks.NATIVE_SULFUR_ORE.get() || this == RankineBlocks.NATIVE_BISMUTH_ORE.get() || this == RankineBlocks.NATIVE_ARSENIC_ORE.get()) {
            return Mth.nextInt(rand, 1, 4);
        } else if (this == RankineBlocks.ANTHRACITE_ORE.get() || this == RankineBlocks.BITUMINOUS_ORE.get() || this == RankineBlocks.LAZURITE_ORE.get() || this == RankineBlocks.NATIVE_INDIUM_ORE.get() || this == RankineBlocks.NATIVE_GALLIUM_ORE.get() || this == RankineBlocks.NATIVE_SELENIUM_ORE.get() || this == RankineBlocks.NATIVE_TELLURIUM_ORE.get()) {
            return Mth.nextInt(rand, 2, 5);
        } else if (this == RankineBlocks.BERYL_ORE.get()|| this == RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get() || this == RankineBlocks.PLUMBAGO_ORE.get()) {
            return Mth.nextInt(rand, 3, 7);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? Mth.nextInt(rand, 0, 1) : 0;
        }
    }

}
