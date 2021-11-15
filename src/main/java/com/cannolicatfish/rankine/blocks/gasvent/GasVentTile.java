package com.cannolicatfish.rankine.blocks.gasvent;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

import static com.cannolicatfish.rankine.init.RankineBlocks.BEEHIVE_OVEN_TILE;
import static com.cannolicatfish.rankine.init.RankineBlocks.GAS_VENT_TILE;

public class GasVentTile extends TileEntity implements ITickableTileEntity {

    public GasVentTile() {
        super(GAS_VENT_TILE);
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        BlockState BS = world.getBlockState(pos);
        Block toMove = world.getBlockState(pos.offset(BS.get(BlockStateProperties.FACING).getOpposite())).getBlock();
        if (toMove instanceof GasBlock && world.getBlockState(pos.offset(BS.get(BlockStateProperties.FACING))).matchesBlock(Blocks.AIR)) {
            world.removeBlock(pos.offset(BS.get(BlockStateProperties.FACING).getOpposite()), false);
            world.setBlockState(pos.offset(BS.get(BlockStateProperties.FACING)),toMove.getDefaultState());
        }
    }

}
