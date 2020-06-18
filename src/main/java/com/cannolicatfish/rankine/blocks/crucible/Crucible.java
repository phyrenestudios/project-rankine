package com.cannolicatfish.rankine.blocks.crucible;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Crucible extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), IBooleanFunction.ONLY_FIRST);


    public Crucible(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)));

    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LEVEL) > 0 ? super.getLightValue(state) : 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return INSIDE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else {
            int i = state.get(LEVEL);
            Item item = itemstack.getItem();
            if (item == ModItems.PIG_IRON_INGOT && i != 1 && i != 2 && (worldIn.getBlockState(pos.down()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.LAVA)) {
                if (i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    this.setPigIronLevel(worldIn, pos, state, 3);
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.SUCCESS;
            } if (item == ModItems.WROUGHT_IRON_INGOT && (worldIn.getBlockState(pos.down()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.LAVA))
            {
                if (i > 0 && !worldIn.isRemote){
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, new ItemStack(ModItems.STEEL_ALLOY));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.STEEL_ALLOY))) {
                            player.dropItem(new ItemStack(ModItems.STEEL_ALLOY), false);
                        } else if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
                        }
                    }
                    this.setPigIronLevel(worldIn, pos, state, i-1);
                }
                return ActionResultType.SUCCESS;
            }
            /*
            else if (item == ModItems.BRASS_BUCKET) {
                if (i == 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, new ItemStack(ModItems.LIQUID_PIG_IRON_BRASS_BUCKET));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.LIQUID_PIG_IRON_BRASS_BUCKET))) {
                            player.dropItem(new ItemStack(ModItems.LIQUID_PIG_IRON_BRASS_BUCKET), false);
                        }
                    }
                    this.setPigIronLevel(worldIn, pos, state, 0);
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return true;
                }*/
            else {
                    return ActionResultType.FAIL;
                }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LEVEL) >= 1) {
            double d0 = (double)pos.getX() + 0.5;
            double d1 = (double)pos.getY() + 0.3D * stateIn.get(LEVEL);
            double d2 = (double)pos.getZ() + 0.5;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            Direction.Axis direction$axis = Direction.Axis.Y;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = rand.nextDouble() * 0.6D - 0.3D;
            double d8 = d4;
            if (rand.nextDouble() < 0.4D && (worldIn.getBlockState(pos.down()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.LAVA))
            {
                worldIn.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return blockState.get(LEVEL);
    }

    public void setPigIronLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
