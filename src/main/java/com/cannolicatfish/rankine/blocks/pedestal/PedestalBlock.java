package com.cannolicatfish.rankine.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class PedestalBlock extends Block {

    public PedestalBlock() {
        super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.0F, 10.0F).harvestLevel(0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.or(
                Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Block.makeCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 5.0D, 13.0D),
                Block.makeCuboidShape(4.0D, 5.0D, 4.0D, 12.0D, 11.0D, 12.0D),
                Block.makeCuboidShape(3.0D, 11.0D, 3.0D, 13.0D, 14.0D, 13.0D),
                Block.makeCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(handIn != Hand.MAIN_HAND)
            return ActionResultType.FAIL;
        PedestalTile tile = (PedestalTile) world.getTileEntity(pos);
        if(tile != null) {
            if (!world.isRemote) {
                ItemStack tileStack = tile.getStackInSlot(0);
                ItemStack playerStack = player.getHeldItem(handIn).copy();
                boolean flag = false;
                if (!tile.isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(world, tile.getPos().getX() + 0.5, tile.getPos().getY() + 1.0, tile.getPos().getZ() + 0.5, tileStack);
                    itemEntity.setDefaultPickupDelay();
                    world.addEntity(itemEntity);
                    tile.clear();
                    flag = true;
                }
                if (!playerStack.isEmpty()) {
                    playerStack.setCount(1);
                    tile.setInventorySlotContents(0, playerStack);
                    player.getHeldItem(handIn).shrink(1);
                    flag = true;
                }
                if (flag) {
                    world.notifyBlockUpdate(pos, state, state, 3);
                    world.updateComparatorOutputLevel(pos, this);
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.CONSUME;
        }
        return super.onBlockActivated(state, world, pos, player, handIn, hit);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.matchesBlock(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof PedestalTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (PedestalTile)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PedestalTile();
    }


    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof PedestalTile) {
            return ((PedestalTile)tileentity).getComparatorSignalLevel();
        }
        return 0;
    }
}
