package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.entities.CannonballEntity;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.BuildingToolItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class StoneColumnBlock extends FallingBlock implements IWaterLoggable {
    public static final IntegerProperty SIZE = IntegerProperty.create("size",1,7);
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StoneColumnBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, 1).with(WATERLOGGED, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int size = state.get(SIZE);
        return Block.makeCuboidShape(8-size,0,8-size,8+size,16,8+size);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;

        ItemStack heldItem = context.getPlayer().getHeldItemOffhand();
        if (heldItem.getItem() == RankineItems.BUILDING_TOOL.get()) {
            return this.getDefaultState().with(SIZE, Math.min(7,BuildingToolItem.getBuildingMode(heldItem)+1)).with(WATERLOGGED, flag);
        }
        return this.getDefaultState().with(WATERLOGGED, flag);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState p_196271_3_, IWorld worldIn, BlockPos pos, BlockPos p_196271_6_) {
        if ((direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) && worldIn instanceof World) {
            updateColumn(state, (World) worldIn, pos);
        }
        return super.updatePostPlacement(state, direction, p_196271_3_, worldIn, pos, p_196271_6_);
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (projectile instanceof CannonballEntity) {
            worldIn.removeBlock(hit.getPos(),false);
            updateColumn(state, worldIn,hit.getPos().down());
        }
        super.onProjectileCollision(worldIn, state, hit, projectile);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        //if (!isValidPosition(state,worldIn,pos) && pos.getY() >= 0) {
        //    spawnFallingBlock(worldIn,pos);
        //}
    } // (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) &&


    private void updateColumn(BlockState state, World worldIn, BlockPos pos) {
        int i = 0;
        while (worldIn.getBlockState(pos.down(i)).matchesBlock(this)) {
            ++i;
        }
        if (!isValidPosition(state, worldIn, pos.down(i-1))) {
            BlockPos.Mutable blockpos$mutableblockpos = pos.down(i-1).toMutable();
            while (worldIn.getBlockState(blockpos$mutableblockpos).matchesBlock(this)) {
                spawnFallingBlock(worldIn, blockpos$mutableblockpos);
                blockpos$mutableblockpos.move(Direction.UP);
            }

        }
    }

    private void spawnFallingBlock(World worldIn, BlockPos pos) {
        FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
        this.onStartFalling(fallingblockentity);
        worldIn.addEntity(fallingblockentity);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        boolean flagU = true;
        boolean flagD = true;

        int i = 1;
        while (worldIn.getBlockState(pos.down(i)).matchesBlock(this)) {
            ++i;
        }
        if (!worldIn.getBlockState(pos.down(i)).isSolid()) {
            flagD = false;
        }
        int j = 1;
        while (worldIn.getBlockState(pos.up(j)).matchesBlock(this)) {
            ++j;
        }
        if (!worldIn.getBlockState(pos.up(j)).isSolid()) {
            flagU = false;
        }


        return flagD || flagU;
    }

    @Override
    public void onEndFalling(World p_176502_1_, BlockPos p_176502_2_, BlockState p_176502_3_, BlockState p_176502_4_, FallingBlockEntity p_176502_5_) {
        p_176502_1_.destroyBlock(p_176502_2_,false);
        super.onEndFalling(p_176502_1_, p_176502_2_, p_176502_3_, p_176502_4_, p_176502_5_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(30) == 0) {
            BlockPos blockpos = pos.down();
            if (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) {
                double d0 = (double)pos.getX() + rand.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, stateIn), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos) {
        return 7828079;
    }

    @Override
    public PushReaction getPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    protected void onStartFalling(FallingBlockEntity fallingEntity) {
        fallingEntity.setHurtEntities(true);
    }

}
