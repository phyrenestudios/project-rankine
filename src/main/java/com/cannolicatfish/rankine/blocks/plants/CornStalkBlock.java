package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class CornStalkBlock extends BushBlock {

    public static final EnumProperty<TripleBlockSection> SECTION = EnumProperty.create("section", TripleBlockSection.class);

    public CornStalkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SECTION, TripleBlockSection.BOTTOM));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(SECTION)) {
            case BOTTOM:
            case MIDDLE:
            case TOP:
                return VoxelShapes.block();
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        switch (state.getValue(SECTION)) {
            case BOTTOM:
                return worldIn.getBlockState(pos.below()).is(Tags.Blocks.DIRT) || worldIn.getBlockState(pos.below()).is(RankineTags.Blocks.FARMLAND);
            case MIDDLE:
                BlockState blockstate1 = worldIn.getBlockState(pos.below());
                if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate1.is(this) && blockstate1.getValue(SECTION) == TripleBlockSection.BOTTOM;
            case TOP:
                BlockState blockstate2 = worldIn.getBlockState(pos.below());
                if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate2.is(this) && blockstate2.getValue(SECTION) == TripleBlockSection.MIDDLE;
        }
        return super.canSurvive(state, worldIn, pos);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        TripleBlockSection tripleBlockSection = stateIn.getValue(SECTION);
        switch (tripleBlockSection) {
            case BOTTOM:
            case MIDDLE:
                if (facing.getAxis() != Direction.Axis.Y || !(facing == Direction.UP) || facingState.is(this) && facingState.getValue(SECTION) != tripleBlockSection) {
                    return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
                } else {
                    return Blocks.AIR.defaultBlockState();
                }
            case TOP:
                break;
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            worldIn.setBlockAndUpdate(pos.above(1), this.defaultBlockState().setValue(SECTION, TripleBlockSection.MIDDLE));
            worldIn.setBlockAndUpdate(pos.above(2), this.defaultBlockState().setValue(SECTION, TripleBlockSection.TOP));
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClientSide) {
            if (player.isCreative()) {
                removeLowerSections(worldIn, pos, state, player);
            } else {
                //spawnDrops(state, worldIn, pos, (TileEntity)null, player, player.getHeldItemMainhand());
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    protected static void removeLowerSections(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.getValue(SECTION).equals(TripleBlockSection.MIDDLE)) {
            BlockPos blockpos = pos.below(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
        if (state.getValue(SECTION).equals(TripleBlockSection.TOP)) {
            BlockPos blockpos = pos.below(2);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }


    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.makeStuckInBlock(state, new Vector3d((double)0.98F, 1.0D, (double)0.98F));
        }
    }

}
