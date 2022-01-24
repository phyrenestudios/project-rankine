package com.cannolicatfish.rankine.blocks.mushrooms;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
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
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(HORIZONTAL_FACING).getHorizontalIndex()];
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(50) == 0) {
            BlockState log = worldIn.getBlockState(pos.offset(state.get(HORIZONTAL_FACING).getOpposite()));
            if (log.getBlock() instanceof RotatedPillarBlock && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:hollow_"+log.getBlock().getRegistryName().getPath())) != Blocks.AIR.getBlock()) {
                worldIn.setBlockState(pos.offset(state.get(HORIZONTAL_FACING).getOpposite()), ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate("rankine:hollow_"+log.getBlock().getRegistryName().getPath())).getDefaultState().with(HollowLogBlock.AXIS,log.get(RotatedPillarBlock.AXIS)));
            }
        }
    }

        /**
         * Performs a random tick on a block.
         */
    /*
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextInt(25) == 0) {
            int i = 5;
            int j = 4;

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
                if (worldIn.getBlockState(blockpos).matchesBlock(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for(int k = 0; k < 4; ++k) {
                if (worldIn.isAirBlock(blockpos1) && state.isValidPosition(worldIn, blockpos1)) {
                    pos = blockpos1;
                }

                blockpos1 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (worldIn.isAirBlock(blockpos1) && state.isValidPosition(worldIn, blockpos1)) {
                worldIn.setBlockState(blockpos1, state, 2);
            }
        }

    }

     */
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        if (direction.getAxis().isVertical()) return Blocks.AIR.getDefaultState();
        return this.getDefaultState().with(HORIZONTAL_FACING, direction);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isOpaqueCube(worldIn, pos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.getMaterial().equals(Material.WOOD) && blockstate.isSolidSide(worldIn,blockpos,state.get(HORIZONTAL_FACING));
    }

    public boolean grow(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        world.removeBlock(pos, false);
        ConfiguredFeature<?, ?> configuredfeature;
        if (this == Blocks.BROWN_MUSHROOM) {
            configuredfeature = Features.HUGE_BROWN_MUSHROOM;
        } else {
            if (this != Blocks.RED_MUSHROOM) {
                world.setBlockState(pos, state, 3);
                return false;
            }

            configuredfeature = Features.HUGE_RED_MUSHROOM;
        }

        if (configuredfeature.generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos)) {
            return true;
        } else {
            world.setBlockState(pos, state, 3);
            return false;
        }
    }

    /**
     * Whether this IGrowable can grow
     */
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
