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
            return ActionResultType.PASS;
        PedestalTile tile = (PedestalTile) world.getTileEntity(pos);
        if(!world.isRemote && tile != null) {

            if (tile.getStackInSlot(0) != null && player.getHeldItem(handIn).isEmpty()) {
                if(world.getBlockState(pos.up()).getMaterial() != Material.AIR)
                    return ActionResultType.SUCCESS;
                ItemEntity item = new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), tile.getStackInSlot(0));
                world.addEntity(item);
                tile.clear();
            } else if (!player.inventory.getCurrentItem().isEmpty()) {
                if(tile.getStackInSlot(0) != null){
                    ItemEntity item = new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), tile.getStackInSlot(0));
                    world.addEntity(item);
                }
                ItemStack stack = player.getHeldItem(handIn).copy();
                stack.setCount(1);
                tile.setInventorySlotContents(0,stack);
                player.getHeldItem(handIn).shrink(1);
            }
            world.notifyBlockUpdate(pos, state, state, 2);
        }
        return  ActionResultType.SUCCESS;
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
}
