package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
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
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RankinePlantBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    private static final VoxelShape AGE0 = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape AGE3 = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private int type;
    private PlantType plantType;
    public RankinePlantBlock(Properties p_i49971_1_, int type, PlantType plantType) {
        super(p_i49971_1_);
        this.type = type;
        this.plantType = plantType;
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return plantType;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        if (type == 7 || type == 8 || type == 10) {
            return block.isIn(Tags.Blocks.DIRT) || block.isIn(Tags.Blocks.SAND) || block == RankineBlocks.DESERT_SAND.get();
        }
        return block.isIn(Tags.Blocks.DIRT);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(AGE) == 0) {
            return AGE0;
        } else {
            return state.get(AGE) < 3 ? AGE3 : super.getShape(state, worldIn, pos, context);
        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int i = state.get(AGE);
        if (i < 3 && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            entityIn.setMotionMultiplier(state, new Vector3d((double)0.98F, 0.98D, (double)0.98F));
            if (!worldIn.isRemote && state.get(AGE) > 0 && (entityIn.lastTickPosX != entityIn.getPosX() || entityIn.lastTickPosZ != entityIn.getPosZ())) {
                double d0 = Math.abs(entityIn.getPosX() - entityIn.lastTickPosX);
                double d1 = Math.abs(entityIn.getPosZ() - entityIn.lastTickPosZ);
                if (d0 >= (double)0.01F || d1 >= (double)0.01F) {
                    if (state.getBlock() == RankineBlocks.RASPBERRY_BUSH.get() || state.getBlock() == RankineBlocks.BLACKBERRY_BUSH.get()) {
                        entityIn.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                    if (state.getBlock() == RankineBlocks.SNOWBERRY_BUSH.get() || state.getBlock() == RankineBlocks.CRANBERRY_BUSH.get() || state.getBlock() == RankineBlocks.PINEAPPLE_BUSH.get()) {
                        entityIn.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 0.5F);
                    }
                }
            }
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
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.SNOWBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 2:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.BLUEBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 3:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.RASPBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 4:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.BLACKBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 5:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.CRANBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 6:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.STRAWBERRIES.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 7:
                    if (worldIn.getRandom().nextFloat() < 0.01) {
                        spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.PINEAPPLE_SLEEVES.get(), 1));
                    } else {
                        spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.PINEAPPLE.get(), 1 + worldIn.rand.nextInt(2)));
                    }
                    break;
                case 8:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.BANANA_YUCCA.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
                case 10:
                    spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.ALOE.get(), 1 + worldIn.rand.nextInt(2)));
                    break;
            }

            if (type == 0 || type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6 || type == 7 || type == 8) {
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            } else {
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.1F + worldIn.rand.nextFloat() * 0.4F);
            }
            worldIn.setBlockState(pos, state.with(AGE, 1), 2);
            return ActionResultType.SUCCESS;
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

   // @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DANGER_OTHER;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
        int i = Math.min(3, p_225535_4_.get(AGE) + 1);
        p_225535_1_.setBlockState(p_225535_3_, p_225535_4_.with(AGE, i), 2);
    }


}
