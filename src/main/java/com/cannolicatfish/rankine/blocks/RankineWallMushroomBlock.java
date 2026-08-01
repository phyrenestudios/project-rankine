package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

// PORT-TODO(RankineWallMushroomBlock): vanilla's own MushroomBlock now stores a ResourceKey<ConfiguredFeature<?,?>>
// (resolved via level.registryAccess() at grow time) instead of a Supplier<Holder<...>>. We keep the existing
// Supplier<Holder<...>> field/constructor signature unchanged since instances are constructed from the init/
// package (outside this file's ownership) and changing the public constructor would break that call site.
// Consequently codec() below cannot faithfully round-trip the feature supplier (Suppliers aren't codec-serializable
// and nothing in-engine actually decodes a registered mod Block through this codec at runtime) - it satisfies the
// now-mandatory BlockBehaviour#codec() contract without attempting to reconstruct the feature reference.
public class RankineWallMushroomBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<RankineWallMushroomBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(propertiesCodec()).apply(instance, properties -> new RankineWallMushroomBlock(properties, () -> {
                throw new UnsupportedOperationException("RankineWallMushroomBlock cannot be reconstructed from its codec; it is only ever constructed directly during registration.");
            }))
    );

    @Override
    public MapCodec<? extends RankineWallMushroomBlock> codec() {
        return CODEC;
    }

    private final Supplier<Holder<? extends ConfiguredFeature<?, ?>>> featureSupplier;
    protected static final VoxelShape[] SHAPES = new VoxelShape[] {
            Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 3.0D),
            Block.box(13.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D),
            Block.box(3.0D, 3.0D, 13.0D, 13.0D, 13.0D, 16.0D),
            Block.box(0.0D, 3.0D, 3.0D, 3.0D, 13.0D, 13.0D)};

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RankineWallMushroomBlock(BlockBehaviour.Properties properties, Supplier<Holder<? extends ConfiguredFeature<?, ?>>> p_153984_) {
        super(properties);
        this.featureSupplier = p_153984_;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.SOUTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(HORIZONTAL_FACING).get2DDataValue()];
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (random.nextInt(Config.GENERAL.MUSHROOM_HOLLOW_LOG_RATE.get()) == 0) {
            BlockState log = worldIn.getBlockState(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()));
            if (log.getBlock() instanceof RotatedPillarBlock) {
                ResourceLocation hollowId = ResourceLocation.tryParse("rankine:hollow_" + BuiltInRegistries.BLOCK.getKey(log.getBlock()).getPath());
                Block hollow = BuiltInRegistries.BLOCK.get(hollowId);
                if (hollow != Blocks.AIR) {
                    worldIn.setBlockAndUpdate(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()), hollow.defaultBlockState().setValue(HollowLogBlock.AXIS, log.getValue(RotatedPillarBlock.AXIS)));
                }
            }
        }
        if (Config.GENERAL.MUSHROOM_SPREAD.get()) {
            if (random.nextInt(15) == 0) {
                int i = 6;

                for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
                    if (worldIn.getBlockState(blockpos).is(this)) {
                        --i;
                        if (i <= 0) {
                            return;
                        }
                    }
                }

                BlockPos blockpos1 = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - random.nextInt(2), random.nextInt(3) - 1);
                // PORT-TODO(RankineWallMushroomBlock): Material.WOOD is gone; substituted with a SoundType.WOOD
                // check as the closest available "is a wood block" heuristic (Material.WOOD blocks and
                // SoundType.WOOD blocks overlap almost completely in vanilla and modded content).
                if (worldIn.getBlockState(blockpos1).getSoundType() == SoundType.WOOD) {
                    Direction dir = WorldgenUtils.randomHorizontalDirection(random);
                    if (state.setValue(HORIZONTAL_FACING,dir).canSurvive(worldIn, blockpos1.relative(dir))) {
                        worldIn.setBlock(blockpos1.relative(dir), state.setValue(HORIZONTAL_FACING,dir), 2);
                    }
                }


            }
        }

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis().isVertical() && context.getPlayer() != null) return Blocks.AIR.defaultBlockState();
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction.equals(Direction.UP) || direction.equals(Direction.DOWN) ? Direction.SOUTH : direction);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.isSolidRender(worldIn, pos);
    }

    // PORT-TODO(RankineWallMushroomBlock): Material.WOOD is gone; substituted with a SoundType.WOOD check (see
    // note above in randomTick) as the closest available 1:1 stand-in.
    @Override
    protected boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.getSoundType() == SoundType.WOOD && blockstate.isFaceSturdy(worldIn,blockpos,state.getValue(HORIZONTAL_FACING));
    }


    public void growMushroom(ServerLevel worldIn, BlockPos pos, BlockState p_54862_, RandomSource p_54863_) {
        if (!this.featureSupplier.get().value().place(worldIn, worldIn.getChunkSource().getGenerator(), p_54863_, pos)) {
            worldIn.setBlock(pos,p_54862_,3);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_54870_, BlockPos pos, BlockState p_54872_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_54875_, RandomSource p_54876_, BlockPos pos, BlockState p_54878_) {
        return (double)p_54876_.nextFloat() < 0.4D;
    }

    @Override
    public void performBonemeal(ServerLevel p_54865_, RandomSource p_54866_, BlockPos pos, BlockState p_54868_) {
        this.growMushroom(p_54865_, pos, p_54868_, p_54866_);
    }


}
