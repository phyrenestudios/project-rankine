package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class PermafrostBlock extends Block {
    public PermafrostBlock(Properties properties) {
        super(properties);
    }

   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
       super.tick(state, worldIn, pos, rand);
       World world = worldIn.getWorld();
        if (world.getLightFor(LightType.BLOCK, pos.up()) > 11) {
            this.permafrostMelt(state, world, pos);
        }
    }

    protected void permafrostMelt(BlockState p_196454_1_, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.COARSE_DIRT.getDefaultState(),2);
        worldIn.neighborChanged(pos, Blocks.COARSE_DIRT, pos);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public boolean isToolEffective(BlockState state, ToolType tool) {
        return tool == ToolType.SHOVEL;
    }



}
