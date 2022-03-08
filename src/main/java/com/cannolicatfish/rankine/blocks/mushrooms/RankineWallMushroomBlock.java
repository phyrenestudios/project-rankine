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
            Block.makeCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 3.0D),
            Block.makeCuboidShape(13.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D),
            Block.makeCuboidShape(3.0D, 3.0D, 13.0D, 13.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 3.0D, 3.0D, 3.0D, 13.0D, 13.0D)};

    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RankineWallMushroomBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.SOUTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(HORIZONTAL_FACING).getHorizontalIndex()];
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(50) == 0) {
            BlockState log = worldIn.getBlockState(pos.offset(state.get(HORIZONTAL_FACING).getOpposite()));
            if (log.getBlock() instanceof RotatedPillarBlock && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:hollow_"+log.getBlock().getRegistryName().getPath())) != Blocks.AIR.getBlock()) {
                worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING).getOpposite()), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:hollow_"+log.getBlock().getRegistryName().getPath())).getDefaultState().with(HollowLogBlock.AXIS,log.get(RotatedPillarBlock.AXIS)));
            }
        }
        if (random.nextInt(15) == 0) {
            int i = 6;

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-3, -3, -3), pos.add(3, 3, 3))) {
                if (worldIn.getBlockState(blockpos).matchesBlock(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - random.nextInt(2), random.nextInt(3) - 1);
            if (worldIn.getBlockState(blockpos1).getMaterial().equals(Material.WOOD)) {
                Direction dir = WorldgenUtils.randomHorizontalDirection(random);
                if (state.with(HORIZONTAL_FACING,dir).isValidPosition(worldIn, blockpos1.offset(dir))) {
                    worldIn.setBlockState(blockpos1.offset(dir), state.with(HORIZONTAL_FACING,dir), 2);
                }
            }


        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        if (direction.getAxis().isVertical()) return Blocks.AIR.getDefaultState();
        return this.getDefaultState().with(HORIZONTAL_FACING, direction);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isOpaqueCube(worldIn, pos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.getMaterial().equals(Material.WOOD) && blockstate.isSolidSide(worldIn,blockpos,state.get(HORIZONTAL_FACING));
    }


    public boolean grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        world.removeBlock(pos, false);
        if (this == RankineBlocks.TINDER_CONK_MUSHROOM.get()) {
            if (!TinderConkMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.TINDER_CONK_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.LIONS_MANE_MUSHROOM.get()) {
            if (!LionsManeMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.LIONS_MANE_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.TURKEY_TAIL_MUSHROOM.get()) {
            if (!TurkeyTailMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.TURKEY_TAIL_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.SULFUR_SHELF_MUSHROOM.get()) {
            if (!SulfurShelfMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.SULFUR_SHELF_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.CINNABAR_POLYPORE_MUSHROOM.get()) {
            if (!CinnbarPolyporeMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.CINNABAR_POLYPORE_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.HONEY_MUSHROOM.get()) {
            if (!HoneyMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.HONEY_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.OYSTER_MUSHROOM.get()) {
            if (!OysterMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.OYSTER_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.ARTIST_CONK_MUSHROOM.get()) {
            if (!ArtistsConkMushroomFeature.growMushroom(world,rand,pos, (BlockStateProvidingFeatureConfig) RankineBiomeFeatures.ARTIST_CONK_MUSHROOM.getConfig(),state.get(HORIZONTAL_FACING))) {
                world.setBlockState(pos, state, 3);
                return false;
            }
        } else {
            world.setBlockState(pos, state, 3);
            return false;
        }
        return true;
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state, rand);
    }


}
