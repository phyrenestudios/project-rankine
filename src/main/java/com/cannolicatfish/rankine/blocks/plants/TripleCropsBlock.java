package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class TripleCropsBlock extends CropsBlock {
    public static final EnumProperty<TripleBlockSection> SECTION = EnumProperty.create("section", TripleBlockSection.class);
    private static final VoxelShape[] BOTTOM_SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] MIDDLE_SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] TOP_SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public TripleCropsBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SECTION, TripleBlockSection.BOTTOM).with(AGE, 0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, SECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SECTION)) {
            case BOTTOM:
                return BOTTOM_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
            case MIDDLE:
                return MIDDLE_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
            case TOP:
                return TOP_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
        }
        return super.getShape(state, worldIn, pos, context);
    }

    protected TripleBlockSection getSection(BlockState state) {
        return state.get(SECTION);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.matchesBlock(Blocks.FARMLAND);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        switch (state.get(SECTION)) {
            case BOTTOM:
                return super.isValidPosition(state, worldIn, pos);
            case MIDDLE:
                BlockState blockstate1 = worldIn.getBlockState(pos.down());
                if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate1.matchesBlock(this) && blockstate1.get(SECTION) == TripleBlockSection.BOTTOM;
            case TOP:
                BlockState blockstate2 = worldIn.getBlockState(pos.down());
                if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate2.matchesBlock(this) && blockstate2.get(SECTION) == TripleBlockSection.MIDDLE;
        }
        return super.isValidPosition(state, worldIn, pos);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        TripleBlockSection tripleblocksection = stateIn.get(SECTION);
        if (facing.getAxis() != Direction.Axis.Y || tripleblocksection == TripleBlockSection.BOTTOM != (facing == Direction.UP) || tripleblocksection == TripleBlockSection.MIDDLE != (facing == Direction.UP) || facingState.matchesBlock(this) && facingState.get(SECTION) != tripleblocksection) {
            return tripleblocksection == TripleBlockSection.BOTTOM && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !this.isMaxAge(state) && getSection(state).equals(TripleBlockSection.BOTTOM);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            if ((worldIn.getBlockState(pos.up(1)).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.up(1)).matchesBlock(this)) && (worldIn.getBlockState(pos.up(2)).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.up(2)).matchesBlock(this))) {
                int i = this.getAge(state);
                if (i < this.getMaxAge()) {
                    float f = getGrowthChance(this, worldIn, pos);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                        worldIn.setBlockState(pos, this.withAge(i + 1).with(SECTION, TripleBlockSection.BOTTOM), 2);
                        worldIn.setBlockState(pos.up(1), this.withAge(i + 1).with(SECTION, TripleBlockSection.MIDDLE), 2);
                        worldIn.setBlockState(pos.up(2), this.withAge(i + 1).with(SECTION, TripleBlockSection.TOP), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                    }
                }
            }
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        switch (state.get(SECTION)) {
            case BOTTOM:
                worldIn.setBlockState(pos, this.withAge(i).with(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlockState(pos.up(1), this.withAge(i).with(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlockState(pos.up(2), this.withAge(i).with(SECTION, TripleBlockSection.TOP), 2);
                break;
            case MIDDLE:
                worldIn.setBlockState(pos.down(), this.withAge(i).with(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlockState(pos, this.withAge(i).with(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlockState(pos.up(), this.withAge(i).with(SECTION, TripleBlockSection.TOP), 2);
                break;
            case TOP:
                worldIn.setBlockState(pos.down(2), this.withAge(i).with(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlockState(pos.down(1), this.withAge(i).with(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlockState(pos, this.withAge(i).with(SECTION, TripleBlockSection.TOP), 2);
                break;
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        switch (state.get(SECTION)) {
            case BOTTOM:
            case MIDDLE:
                return !this.isMaxAge(state) && (worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.up()).matchesBlock(this));
            case TOP:
                return !this.isMaxAge(state);
        }
        return !this.isMaxAge(state);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            if (player.isCreative()) {
                removeLowerSections(worldIn, pos, state, player);
            } else {
                spawnDrops(state, worldIn, pos, (TileEntity)null, player, player.getHeldItemMainhand());
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    protected static void removeLowerSections(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(SECTION).equals(TripleBlockSection.TOP)) {
            BlockPos blockpos = pos.down(2);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
        } else if (state.get(SECTION).equals(TripleBlockSection.MIDDLE)) {
            BlockPos blockpos = pos.down(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public long getPositionRandom(BlockState state, BlockPos pos) {
        return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(SECTION) == TripleBlockSection.BOTTOM ? 0 : 1).getY(), pos.getZ());
    }

}