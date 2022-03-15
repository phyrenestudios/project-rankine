package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class FloodGateBlock extends Block {
    public FloodGateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public static boolean placeFluid(World worldIn, BlockPos pos, BlockState bs) {
        if (worldIn.getBlockState(pos.below()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.below(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.north()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.north(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.east()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.east(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.south()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.south(),bs,3);
            return true;
        } else if (worldIn.getBlockState(pos.west()).is(Blocks.AIR)) {
            worldIn.setBlock(pos.west(),bs,3);
            return true;
        }
        return false;
    }

    public static boolean inInfiniteSource(World worldIn, BlockPos pos) {
        int waterSides = 0;
        for (Direction d : Direction.values()) {
            if (worldIn.getBlockState(pos.relative(d)).is(Blocks.WATER)) {
                waterSides += 1;
            }
        }
        if (waterSides > 1) {
            if (Config.GENERAL.DISABLE_WATER.get()) {
                return pos.getY() > WorldgenUtils.waterTableHeight(worldIn, pos);
            } else {
                return true;
            }
        }
        return false;
    }

}
