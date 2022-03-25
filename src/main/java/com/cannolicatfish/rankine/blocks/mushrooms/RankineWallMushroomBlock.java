package com.cannolicatfish.rankine.blocks.mushrooms;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.RankineBiomeFeatures;
import com.cannolicatfish.rankine.world.gen.feature.mushrooms.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RankineWallMushroomBlock extends BushBlock implements BonemealableBlock{
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

    public RankineWallMushroomBlock(BlockBehaviour.Properties properties) {
        super(properties);
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
        if (direction.getAxis().isVertical()) return Blocks.AIR.defaultBlockState();
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction);
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


    public boolean grow(ServerLevel world, BlockPos pos, BlockState state, Random rand) {
        world.removeBlock(pos, false);
        if (this == RankineBlocks.TINDER_CONK_MUSHROOM.get()) {
            if (!TinderConkMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.TINDER_CONK_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.LIONS_MANE_MUSHROOM.get()) {
            if (!LionsManeMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.LIONS_MANE_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.TURKEY_TAIL_MUSHROOM.get()) {
            if (!TurkeyTailMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.TURKEY_TAIL_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.SULFUR_SHELF_MUSHROOM.get()) {
            if (!SulfurShelfMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.SULFUR_SHELF_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.CINNABAR_POLYPORE_MUSHROOM.get()) {
            if (!CinnbarPolyporeMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.CINNABAR_POLYPORE_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.HONEY_MUSHROOM.get()) {
            if (!HoneyMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.HONEY_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.OYSTER_MUSHROOM.get()) {
            if (!OysterMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.OYSTER_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else if (this == RankineBlocks.ARTIST_CONK_MUSHROOM.get()) {
            if (!ArtistsConkMushroomFeature.growMushroom(world,rand,pos, (BlockPileConfiguration) RankineBiomeFeatures.ARTIST_CONK_MUSHROOM.config(),state.getValue(HORIZONTAL_FACING))) {
                world.setBlock(pos, state, 3);
                return false;
            }
        } else {
            world.setBlock(pos, state, 3);
            return false;
        }
        return true;
    }

    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D;
    }

    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state, rand);
    }


}
