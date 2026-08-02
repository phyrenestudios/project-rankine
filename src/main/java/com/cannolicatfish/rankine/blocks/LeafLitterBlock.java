package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

// PORT-NOTE: Material.PLANT -> .mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).
// FallingBlock#codec() is abstract in 1.21.1, so the MapCodec below is mandatory, not decorative.
public class LeafLitterBlock extends FallingBlock {
    public static final MapCodec<LeafLitterBlock> CODEC = MapCodec.unit(LeafLitterBlock::new);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

    @Override
    protected MapCodec<? extends LeafLitterBlock> codec() {
        return CODEC;
    }

    public LeafLitterBlock() {
        super(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion().sound(SoundType.GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource p_60554_) {
        int j = state.getValue(AGE);
        if (j == 15) {
            if (levelIn.getRawBrightness(pos.above(),0) <= 9) {
                BlockState groundBS = levelIn.getBlockState(pos.below());
                if (groundBS.is(Blocks.COBBLESTONE)) {
                    levelIn.setBlock(pos.below(), Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3);
                } else if (groundBS.is(Blocks.STONE_BRICKS)) {
                    levelIn.setBlock(pos.below(), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                } else if (groundBS.is(Blocks.GRASS_BLOCK)) {
                    levelIn.setBlock(pos.below(), Blocks.PODZOL.defaultBlockState(), 3);
                } else if (RankineLists.GRASS_BLOCKS.contains(groundBS.getBlock())) {
                    levelIn.setBlock(pos.below(), RankineLists.PODZOL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(groundBS.getBlock())).defaultBlockState(), 3);
                }
            }
            levelIn.destroyBlock(pos, false);
        } else if (levelIn.getRandom().nextInt(Config.GENERAL.LEAF_LITTER_GROWTH.get()) == 0) {
            levelIn.setBlock(pos, state.setValue(AGE, j + 1), 3);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource rand) {
        if (levelIn.isEmptyBlock(pos.below()) || canFallThrough(levelIn.getBlockState(pos.below())) && pos.getY() >= 0) {
            FallingBlockEntity.fall(levelIn, pos, state).dropItem = false;
        } else if (!canSurvive(state,levelIn,pos)) {
            levelIn.removeBlock(pos,false);
        }
    }

    // PORT-NOTE: BlockState#getMaterial() is gone. FallingBlock#isFree(BlockState) is exactly the old
    // `isAir() || is(FIRE) || material.isLiquid() || material.isReplaceable()` test, so this stays 1:1.
    public static boolean canFallThrough(BlockState state) {
        return isFree(state) || state.getBlock() instanceof LeafLitterBlock;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).isCollisionShapeFullBlock(worldIn,pos.below());
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return super.canBeReplaced(state, fluid);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 200;
    }
}
