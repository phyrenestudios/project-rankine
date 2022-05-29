package com.cannolicatfish.rankine.blocks.mixingbarrel;

import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class MixingBarrelBlock extends BaseEntityBlock {
    //public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static IntegerProperty ANGLE = IntegerProperty.create("angle",0,3);


    public MixingBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ANGLE, 1));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANGLE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        //return VoxelShapes.combineAndSimplify(makeCuboidShape(0,0,0,16,2,16),makeCuboidShape(2,2,2,14,16,14), IBooleanFunction.OR);
        return Shapes.block();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide) {
            LazyOptional<IFluidHandlerItem> item = FluidUtil.getFluidHandler(player.getItemInHand(handIn));
            MixingBarrelTile mixingBarrelTile = (MixingBarrelTile) worldIn.getBlockEntity(pos);
            if (item.isPresent() && mixingBarrelTile != null) {
                FluidActionResult emptyContainerAndStow = FluidUtil.tryEmptyContainerAndStow(player.getItemInHand(handIn), mixingBarrelTile.inputTank, new InvWrapper(player.getInventory()), mixingBarrelTile.inputTank.getCapacity(), player, true);
                if (emptyContainerAndStow.isSuccess()) {
                    player.setItemInHand(handIn, emptyContainerAndStow.getResult());
                    worldIn.blockEntityChanged(pos);
                    return InteractionResult.CONSUME;
                }

                FluidActionResult fillContainerAndStowInput = FluidUtil.tryFillContainerAndStow(player.getItemInHand(handIn), mixingBarrelTile.inputTank, new InvWrapper(player.getInventory()), mixingBarrelTile.inputTank.getFluidAmount(), player, true);
                if (fillContainerAndStowInput.isSuccess()) {
                    player.setItemInHand(handIn, fillContainerAndStowInput.getResult());
                    worldIn.blockEntityChanged(pos);
                    return InteractionResult.CONSUME;
                }
            }
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof MenuProvider) {
                    if (player.isCrouching()) {
                        CompoundTag nbt = tileEntity.getTileData();
                        /*int mixTime = nbt.getInt("MixTime");
                        int mixTimeTotal = nbt.getInt("MixTimeTotal");
                        System.out.println(mixTime + "/" + mixTimeTotal);
                        nbt.putInt("MixTime",Math.min(mixTime+8,mixTimeTotal));
                        nbt.putInt("RedstonePower",8);*/
                        int angle = worldIn.getBlockState(pos).getValue(MixingBarrelBlock.ANGLE);
                        if (angle == 3) {
                            worldIn.setBlock(pos, RankineBlocks.MIXING_BARREL.get().defaultBlockState().setValue(MixingBarrelBlock.ANGLE,0),3);
                        } else {
                            worldIn.setBlock(pos, RankineBlocks.MIXING_BARREL.get().defaultBlockState().setValue(MixingBarrelBlock.ANGLE,angle+1),3);
                        }
                    } else {
                        NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
                    }
                } else {
                    throw new IllegalStateException("Our named container provider is missing!");
                }
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.SUCCESS;
        }
    }

    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof MixingBarrelTile) {
                Containers.dropContents(worldIn, pos, (MixingBarrelTile)tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new MixingBarrelTile(p_153215_,p_153216_);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockStateIn, BlockEntityType<T> blockEntityTypeIn) {
        return worldIn.isClientSide ? null : createTickerHelper(blockEntityTypeIn, RankineBlockEntityTypes.MIXING_BARREL.get(), MixingBarrelTile::tick);
    }
}
