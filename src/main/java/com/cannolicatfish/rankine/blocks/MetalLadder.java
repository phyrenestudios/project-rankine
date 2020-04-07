package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MetalLadder extends LadderBlock {
    public MetalLadder(Properties builder) {
        super(builder);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        int n = 1;
        IBlockReader reader = world.getWorld();
        while (reader.getBlockState(pos.up(n)).getBlock() == this.getBlock())
        {
            n += 1;
        }
        if (!world.isRemote && player.getHeldItem(hand).getItem() == Items.AIR && player.isOnLadder()) {
            player.setPositionAndUpdate(pos.getX() + .5f,pos.getY() + n,pos.getZ() + .5f);
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }
}
