package com.cannolicatfish.rankine.blocks.plants;


import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RankineDoublePlantBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final EnumProperty<DoubleBlockHalf> SECTION = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private int type;

    public RankineDoublePlantBlock(Properties properties, int type) {
        super(properties);
        this.type = type;
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0).with(SECTION, DoubleBlockHalf.LOWER));
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    protected DoubleBlockHalf getSection(BlockState state) {
        return state.get(SECTION);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos.up(1), this.getDefaultState().with(SECTION, DoubleBlockHalf.UPPER), 3);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, this.getDefaultState().with(SECTION, DoubleBlockHalf.LOWER), flags);
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(SECTION, DoubleBlockHalf.UPPER), flags);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MYCELIUM || block == Blocks.FARMLAND;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int i = state.get(AGE);
        if (i < 3 && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, i + 1).with(SECTION, DoubleBlockHalf.LOWER), 2);
            worldIn.setBlockState(pos.up(), state.with(AGE, i + 1).with(SECTION, DoubleBlockHalf.UPPER), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(SECTION) != DoubleBlockHalf.UPPER) {
            return super.isValidPosition(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.matchesBlock(this) && blockstate.get(SECTION) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.get(SECTION);
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.matchesBlock(this) && facingState.get(SECTION) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !this.isMaxAge(state) && getSection(state).equals(DoubleBlockHalf.LOWER);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(AGE);
        boolean flag = i == 3;
        if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        } else if (flag && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            switch (type) {
                case 0:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.ELDERBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 1:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.BLUEBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 2:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.CRANBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 3:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.POKEBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
            }
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);

            switch (state.get(SECTION)) {
                case LOWER:
                    worldIn.setBlockState(pos, state.with(AGE, 1).with(SECTION, DoubleBlockHalf.LOWER), 2);
                    worldIn.setBlockState(pos.up(), state.with(AGE, 1).with(SECTION, DoubleBlockHalf.UPPER), 2);
                    break;
                case UPPER:
                    worldIn.setBlockState(pos.down(), state.with(AGE, 1).with(SECTION, DoubleBlockHalf.LOWER), 2);
                    worldIn.setBlockState(pos, state.with(AGE, 1).with(SECTION, DoubleBlockHalf.UPPER), 2);
                    break;
            }

            return ActionResultType.SUCCESS;
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
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
        if (state.get(SECTION).equals(DoubleBlockHalf.UPPER)) {
            BlockPos blockpos = pos.down(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(SECTION) == DoubleBlockHalf.LOWER) {
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
        }
    }

   // @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DANGER_OTHER;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, SECTION);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        switch (state.get(SECTION)) {
            case LOWER:
                return !this.isMaxAge(state) && (worldIn.getBlockState(pos.up()).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.up()).matchesBlock(this));
            case UPPER:
                return !this.isMaxAge(state);
        }
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + 1;
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        switch (state.get(SECTION)) {
            case LOWER:
                worldIn.setBlockState(pos, this.withAge(i).with(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlockState(pos.up(), this.withAge(i).with(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
            case UPPER:
                worldIn.setBlockState(pos.down(), this.withAge(i).with(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlockState(pos, this.withAge(i).with(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
        }
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }

}
