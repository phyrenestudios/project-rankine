package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RankinePlantBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape AGE0 = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    private static final VoxelShape AGE3 = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private int type;
    private PlantType plantType;
    public RankinePlantBlock(Properties p_i49971_1_, int type, PlantType plantType) {
        super(p_i49971_1_);
        this.type = type;
        this.plantType = plantType;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return plantType;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        if (type == 7 || type == 8 || type == 10) {
            return Tags.Blocks.DIRT.contains(block) || Tags.Blocks.SAND.contains(block) || block == RankineBlocks.DESERT_SAND.get();
        }
        return Tags.Blocks.DIRT.contains(block);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (state.getValue(AGE) == 0) {
            return AGE0;
        } else {
            return state.getValue(AGE) < 3 ? AGE3 : super.getShape(state, worldIn, pos, context);
        }
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int i = state.getValue(AGE);
        if (i < 3 && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            entityIn.makeStuckInBlock(state, new Vec3((double)0.98F, 0.98D, (double)0.98F));
            if (!worldIn.isClientSide && state.getValue(AGE) > 0 && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
                double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
                double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
                if (d0 >= (double)0.01F || d1 >= (double)0.01F) {
                    if (state.getBlock() == RankineBlocks.RASPBERRY_BUSH.get() || state.getBlock() == RankineBlocks.BLACKBERRY_BUSH.get()) {
                        entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                    if (state.getBlock() == RankineBlocks.SNOWBERRY_BUSH.get() || state.getBlock() == RankineBlocks.CRANBERRY_BUSH.get() || state.getBlock() == RankineBlocks.PINEAPPLE_BUSH.get()) {
                        entityIn.hurt(DamageSource.SWEET_BERRY_BUSH, 0.5F);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        } else if (flag && !worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
            switch (type) {
                case 0:
                    popResource(worldIn, pos, new ItemStack(RankineItems.ELDERBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 1:
                    popResource(worldIn, pos, new ItemStack(RankineItems.SNOWBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 2:
                    popResource(worldIn, pos, new ItemStack(RankineItems.BLUEBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 3:
                    popResource(worldIn, pos, new ItemStack(RankineItems.RASPBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 4:
                    popResource(worldIn, pos, new ItemStack(RankineItems.BLACKBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 5:
                    popResource(worldIn, pos, new ItemStack(RankineItems.CRANBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 6:
                    popResource(worldIn, pos, new ItemStack(RankineItems.STRAWBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 7:
                    if (worldIn.getRandom().nextFloat() < 0.01) {
                        popResource(worldIn, pos, new ItemStack(RankineItems.PINEAPPLE_SLEEVES.get(), 1));
                    } else {
                        popResource(worldIn, pos, new ItemStack(RankineItems.PINEAPPLE.get(), 1 + worldIn.random.nextInt(2)));
                    }
                    break;
                case 8:
                    popResource(worldIn, pos, new ItemStack(RankineItems.BANANA_YUCCA.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 10:
                    popResource(worldIn, pos, new ItemStack(RankineItems.ALOE.get(), 1 + worldIn.random.nextInt(2)));
                    break;
            }

            if (type == 0 || type == 1 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6 || type == 7 || type == 8) {
                worldIn.playSound((Player) null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            } else {
                worldIn.playSound((Player) null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.1F + worldIn.random.nextFloat() * 0.4F);
            }
            worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, worldIn, pos, player, handIn, hit);
        }
    }

   // @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
        int i = Math.min(3, p_225535_4_.getValue(AGE) + 1);
        p_225535_1_.setBlock(p_225535_3_, p_225535_4_.setValue(AGE, i), 2);
    }


}
