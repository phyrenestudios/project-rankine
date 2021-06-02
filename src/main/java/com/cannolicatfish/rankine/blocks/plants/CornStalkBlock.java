package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CornStalkBlock extends BushBlock {

    public static final EnumProperty<TripleBlockSection> SECTION = EnumProperty.create("section", TripleBlockSection.class);

    public CornStalkBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SECTION, TripleBlockSection.BOTTOM));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SECTION)) {
            case BOTTOM:
            case MIDDLE:
            case TOP:
                return VoxelShapes.fullCube();
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        switch (state.get(SECTION)) {
            case BOTTOM:
                return super.isValidPosition(state, worldIn, pos);
            case MIDDLE:
                BlockState blockstate1 = worldIn.getBlockState(pos.down());
                if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate1.matchesBlock(this) && blockstate1.get(SECTION) == TripleBlockSection.BOTTOM;
            case TOP:
                BlockState blockstate2 = worldIn.getBlockState(pos.down());
                if (state.getBlock() != this) return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate2.matchesBlock(this) && blockstate2.get(SECTION) == TripleBlockSection.MIDDLE;
        }
        return super.isValidPosition(state, worldIn, pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos.up(1), this.getDefaultState().with(SECTION, TripleBlockSection.MIDDLE));
            worldIn.setBlockState(pos.up(2), this.getDefaultState().with(SECTION, TripleBlockSection.TOP));
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
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
        if (state.get(SECTION).equals(TripleBlockSection.MIDDLE)) {
            BlockPos blockpos = pos.down(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
        }
        if (state.get(SECTION).equals(TripleBlockSection.TOP)) {
            BlockPos blockpos = pos.down(2);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            }
        }
    }


    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.setMotionMultiplier(state, new Vector3d((double)0.9F, 1.0D, (double)0.9F));
        }
    }

}
