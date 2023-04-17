package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.CannonballEntity;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneColumnBlock extends FallingBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,7);
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StoneColumnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 1).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int size = state.getValue(SIZE);
        return Block.box(8-size,0,8-size,8+size,16,8+size);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getOffhandItem();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.defaultBlockState().setValue(SIZE, Math.min(7,BuildingToolItem.getBuildingMode(heldItem)+1)).setValue(WATERLOGGED, flag);
        }
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_196271_3_, LevelAccessor worldIn, BlockPos pos, BlockPos p_196271_6_) {
        if ((direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) && worldIn instanceof Level) {
            updateColumn(state, (Level) worldIn, pos);
        }
        return super.updateShape(state, direction, p_196271_3_, worldIn, pos, p_196271_6_);
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (projectile instanceof CannonballEntity) {
            worldIn.removeBlock(hit.getBlockPos(),false);
            updateColumn(state, worldIn,hit.getBlockPos().below());
        }
        super.onProjectileHit(worldIn, state, hit, projectile);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        //if (!isValidPosition(state,worldIn,pos) && pos.getY() >= 0) {
        //    spawnFallingBlock(worldIn,pos);
        //}
    } // (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) &&


    private void updateColumn(BlockState state, Level worldIn, BlockPos pos) {
        int i = 0;
        while (worldIn.getBlockState(pos.below(i)).is(this)) {
            ++i;
        }
        if (!canSurvive(state, worldIn, pos.below(i-1))) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.below(i-1).mutable();
            while (worldIn.getBlockState(blockpos$mutableblockpos).is(this)) {
                FallingBlockEntity.fall(worldIn,pos,state);
                blockpos$mutableblockpos.move(Direction.UP);
            }

        }
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        boolean flagU = true;
        boolean flagD = true;

        int i = 1;
        while (worldIn.getBlockState(pos.below(i)).is(this)) {
            ++i;
        }
        if (!worldIn.getBlockState(pos.below(i)).canOcclude()) {
            flagD = false;
        }
        int j = 1;
        while (worldIn.getBlockState(pos.above(j)).is(this)) {
            ++j;
        }
        if (!worldIn.getBlockState(pos.above(j)).canOcclude()) {
            flagU = false;
        }


        return flagD || flagU;
    }

    @Override
    public void onLand(Level p_176502_1_, BlockPos p_176502_2_, BlockState p_176502_3_, BlockState p_176502_4_, FallingBlockEntity p_176502_5_) {
        p_176502_1_.destroyBlock(p_176502_2_,false);
        super.onLand(p_176502_1_, p_176502_2_, p_176502_3_, p_176502_4_, p_176502_5_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(30) == 0) {
            BlockPos blockpos = pos.below();
            if (worldIn.isEmptyBlock(blockpos) || isFree(worldIn.getBlockState(blockpos))) {
                double d0 = (double)pos.getX() + rand.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, stateIn), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos) {
        return 7828079;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    protected void falling(FallingBlockEntity fallingEntity) {
        fallingEntity.setHurtsEntities(2,12);
    }

}
