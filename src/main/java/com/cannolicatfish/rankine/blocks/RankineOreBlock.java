package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

// PORT-TODO(RankineOreBlock): Forge/vanilla's OreBlock class is gone, renamed to DropExperienceBlock, which only
// exposes a single (IntProvider xpRange, Properties) constructor (no more zero-arg-xp convenience ctor). The old
// no-xp-arg constructor is preserved here by defaulting to UniformInt.of(0, 0), matching vanilla OreBlock's old
// default.
public class RankineOreBlock extends DropExperienceBlock {
    public int type = 0;
    public static final IntegerProperty TYPE = IntegerProperty.create("type",0, WorldgenUtils.ORE_TEXTURES.size() -1);

    public static final MapCodec<RankineOreBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    IntProvider.codec(0, 10).fieldOf("experience").forGetter(b -> b.xpRange),
                    propertiesCodec()
            ).apply(instance, RankineOreBlock::new)
    );

    private final IntProvider xpRange;

    @Override
    public MapCodec<? extends RankineOreBlock> codec() {
        return CODEC;
    }

    // Canonical constructor matching the codec's (xpRange, properties) field order.
    private RankineOreBlock(IntProvider xpRange, Properties properties) {
        super(xpRange, properties);
        this.xpRange = xpRange;
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE,0));
    }

    public RankineOreBlock(Properties properties) {
        this(UniformInt.of(0, 0), properties);
    }

    public RankineOreBlock(Properties p_153992_, UniformInt p_153993_) {
        this(p_153993_, p_153992_);
    }

    @Override
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

}
