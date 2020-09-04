package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class UnagedCheeseBlock extends Block {
    public UnagedCheeseBlock(Properties properties) {
        super(properties);
    }

   @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
       super.tick(state, worldIn, pos, rand);
       World world = worldIn.getWorld();
        if (rand.nextFloat() < 0.02) {
            this.ageCheese(state, world, pos);
        }
    }

    protected void ageCheese(BlockState p_196454_1_, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, ModBlocks.AGED_CHEESE.getDefaultState(),2);
        worldIn.neighborChanged(pos, ModBlocks.AGED_CHEESE, pos);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }
}
