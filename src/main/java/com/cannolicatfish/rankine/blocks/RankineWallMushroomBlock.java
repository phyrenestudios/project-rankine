package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;
import java.util.function.Supplier;

public class RankineWallMushroomBlock extends BushBlock implements BonemealableBlock{
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(HORIZONTAL_FACING).get2DDataValue()];
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (random.nextInt(50) == 0) {
            BlockState log = worldIn.getBlockState(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()));
            if (log.getBlock() instanceof RotatedPillarBlock && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:hollow_"+log.getBlock().getRegistryName().getPath())) != Blocks.AIR) {
                worldIn.setBlockAndUpdate(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:hollow_"+log.getBlock().getRegistryName().getPath())).defaultBlockState().setValue(HollowLogBlock.AXIS,log.getValue(RotatedPillarBlock.AXIS)));
            }
        }
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
            if (worldIn.getBlockState(blockpos1).getMaterial().equals(Material.WOOD)) {
                Direction dir = WorldgenUtils.randomHorizontalDirection(random);
                if (state.setValue(HORIZONTAL_FACING,dir).canSurvive(worldIn, blockpos1.relative(dir))) {
                    worldIn.setBlock(blockpos1.relative(dir), state.setValue(HORIZONTAL_FACING,dir), 2);
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

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.getMaterial().equals(Material.WOOD) && blockstate.isFaceSturdy(worldIn,blockpos,state.getValue(HORIZONTAL_FACING));
    }


    public void growMushroom(ServerLevel worldIn, BlockPos pos, BlockState p_54862_, Random p_54863_) {
        if (!this.featureSupplier.get().value().place(worldIn, worldIn.getChunkSource().getGenerator(), p_54863_, pos)) {
            worldIn.setBlock(pos,p_54862_,3);
        }
    }

    public boolean isValidBonemealTarget(BlockGetter p_54870_, BlockPos pos, BlockState p_54872_, boolean p_54873_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_54875_, Random p_54876_, BlockPos pos, BlockState p_54878_) {
        return (double)p_54876_.nextFloat() < 0.4D;
    }

    public void performBonemeal(ServerLevel p_54865_, Random p_54866_, BlockPos pos, BlockState p_54868_) {
        this.growMushroom(p_54865_, pos, p_54868_, p_54866_);
    }


}
