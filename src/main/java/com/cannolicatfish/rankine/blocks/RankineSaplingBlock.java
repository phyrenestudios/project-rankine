package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

// PORT-NOTE: AbstractTreeGrower is gone. TreeGrower is a final class in 1.21.1, so Rankine's 19 grower subclasses
// were replaced by the constants in world/grower/RankineTreeGrowers.java; this block just takes a TreeGrower now.
public class RankineSaplingBlock extends SaplingBlock {
    public static final MapCodec<RankineSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    TreeGrower.CODEC.fieldOf("tree").forGetter(b -> b.tree),
                    propertiesCodec(),
                    Codec.INT.fieldOf("sapling_type").forGetter(b -> b.type)
            ).apply(instance, RankineSaplingBlock::new)
    );

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    private final TreeGrower tree;
    private int type;

    @Override
    public MapCodec<? extends RankineSaplingBlock> codec() {
        return CODEC;
    }

    public RankineSaplingBlock(TreeGrower treeIn, Properties properties, int type) {
        super(treeIn, properties);
        this.tree = treeIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
        this.type = type;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        switch (type) {
            case 3:
                return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(RankineTags.Blocks.COARSE_DIRT);
            case 2:
                return state.is(BlockTags.DIRT) || state.is(RankineTags.Blocks.COARSE_DIRT);
            case 1:
            default:
                return state.is(BlockTags.DIRT);
        }
    }

}
