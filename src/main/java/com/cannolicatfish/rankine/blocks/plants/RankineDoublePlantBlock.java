package com.cannolicatfish.rankine.blocks.plants;


import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RankineDoublePlantBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> SECTION = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private int type;
    private PlantType plantType;
    public RankineDoublePlantBlock(Properties properties, int type, PlantType plantType) {
        super(properties);
        this.type = type;
        this.plantType = plantType;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(SECTION, DoubleBlockHalf.LOWER));
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return plantType;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    protected DoubleBlockHalf getSection(BlockState state) {
        return state.getValue(SECTION);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            worldIn.setBlock(pos.above(1), this.defaultBlockState().setValue(SECTION, DoubleBlockHalf.UPPER), 3);
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 3).setValue(SECTION, DoubleBlockHalf.LOWER), flags);
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, 3).setValue(SECTION, DoubleBlockHalf.UPPER), flags);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MYCELIUM || block == Blocks.FARMLAND;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int i = state.getValue(AGE);
        if (i < 3 && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE, i + 1).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
            worldIn.setBlock(pos.above(), state.setValue(AGE, i + 1).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(SECTION) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(SECTION) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.getValue(SECTION);
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.is(this) && facingState.getValue(SECTION) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state) && getSection(state).equals(DoubleBlockHalf.LOWER);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            entityIn.makeStuckInBlock(state, new Vec3((double)0.95F, 0.95D, (double)0.95F));
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
                    popResource(worldIn, pos, new ItemStack(RankineItems.BLUEBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 2:
                    popResource(worldIn, pos, new ItemStack(RankineItems.CRANBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
                case 3:
                    popResource(worldIn, pos, new ItemStack(RankineItems.POKEBERRIES.get(), 1 + worldIn.random.nextInt(2)));
                    break;
            }
            worldIn.playSound((Player) null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);

            switch (state.getValue(SECTION)) {
                case LOWER:
                    worldIn.setBlock(pos, state.setValue(AGE, 1).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                    worldIn.setBlock(pos.above(), state.setValue(AGE, 1).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                    break;
                case UPPER:
                    worldIn.setBlock(pos.below(), state.setValue(AGE, 1).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                    worldIn.setBlock(pos, state.setValue(AGE, 1).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                    break;
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, worldIn, pos, player, handIn, hit);
        }
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide) {
            if (player.isCreative()) {
                removeLowerSections(worldIn, pos, state, player);
            } else {
                dropResources(state, worldIn, pos, (BlockEntity)null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    protected static void removeLowerSections(Level world, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(SECTION).equals(DoubleBlockHalf.UPPER)) {
            BlockPos blockpos = pos.below(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == DoubleBlockHalf.LOWER) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

   // @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, SECTION);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        switch (state.getValue(SECTION)) {
            case LOWER:
                return !this.isMaxAge(state) && (worldIn.getBlockState(pos.above()).is(Blocks.AIR) || worldIn.getBlockState(pos.above()).is(this));
            case UPPER:
                return !this.isMaxAge(state);
        }
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + 1;
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        switch (state.getValue(SECTION)) {
            case LOWER:
                worldIn.setBlock(pos, this.withAge(i).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlock(pos.above(), this.withAge(i).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
            case UPPER:
                worldIn.setBlock(pos.below(), this.withAge(i).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlock(pos, this.withAge(i).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
        }
    }

    public BlockState withAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

}
