package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RankineOreBlock extends OreBlock {
    public int type = 0;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0, WorldgenUtils.ORE_TEXTURES.size() -1);
    public RankineOreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE,0));
    }

    public RankineOreBlock(Properties p_153992_, UniformInt p_153993_) {
        super(p_153992_,p_153993_);
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

}
