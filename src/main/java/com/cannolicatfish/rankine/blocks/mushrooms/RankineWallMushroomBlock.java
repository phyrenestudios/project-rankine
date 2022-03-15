package com.cannolicatfish.rankine.blocks.mushrooms;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.RankineBiomeFeatures;
import com.cannolicatfish.rankine.world.gen.feature.mushrooms.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class RankineWallMushroomBlock extends BushBlock implements IGrowable{
   /* protected static final VoxelShape[] SHAPES = new VoxelShape[] {
            Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 3.0D, 13.0D),
            Block.makeCuboidShape(3.0D, 13.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.makeCuboidShape(3.0D, 3.0D, 13.0D, 13.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 3.0D),
            Block.makeCuboidShape(13.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D),
            Block.makeCuboidShape(0.0D, 3.0D, 3.0D, 3.0D, 13.0D, 13.0D)};

    */
    protected static final VoxelShape[] SHAPES = new VoxelShape[] {
            Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 3.0D),
            Block.box(13.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D),
            Block.box(3.0D, 3.0D, 13.0D, 13.0D, 13.0D, 16.0D),
            Block.box(0.0D, 3.0D, 3.0D, 3.0D, 13.0D, 13.0D)};

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RankineWallMushroomBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.SOUTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(HORIZONTAL_FACING).get2DDataValue()];
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(50) == 0) {
            BlockState log = worldIn.getBlockState(pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite()));
            if (log.getBlock() instanceof RotatedPillarBlock && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:hollow_"+log.getBlock().getRegistryName().getPath())) != Blocks.AIR.getBlock()) {
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getClickedFace();
        if (direction.getAxis().isVertical()) return Blocks.AIR.defaultBlockState();
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isSolidRender(worldIn, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.relative(state.getValue(HORIZONTAL_FACING).getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.getMaterial().equals(Material.WOOD) && blockstate.isFaceSturdy(worldIn,blockpos,state.getValue(HORIZONTAL_FACING));
    }


    public boolean grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        world.removeBlock(pos, false);
        if (this == RankineBlocks.TINDER_CONK_MUSHROOM.get()) {
            if (!TinderConkMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.TINDER_CONK_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.LIONS_MANE_MUSHROOM.get()) {
            if (!LionsManeMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.LIONS_MANE_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.TURKEY_TAIL_MUSHROOM.get()) {
            if (!TurkeyTailMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.TURKEY_TAIL_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.SULFUR_SHELF_MUSHROOM.get()) {
            if (!SulfurShelfMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.SULFUR_SHELF_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.CINNABAR_POLYPORE_MUSHROOM.get()) {
            if (!CinnbarPolyporeMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.CINNABAR_POLYPORE_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.HONEY_MUSHROOM.get()) {
            if (!HoneyMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.HONEY_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.OYSTER_MUSHROOM.get()) {
            if (!OysterMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.OYSTER_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.ARTIST_CONK_MUSHROOM.get()) {
            if (!ArtistsConkMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.ARTIST_CONK_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else {
            world.setBlock(pos, state, 3);
            return false;
        }
        return true;
    }

    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D;
    }

    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state, rand);
    }


}
