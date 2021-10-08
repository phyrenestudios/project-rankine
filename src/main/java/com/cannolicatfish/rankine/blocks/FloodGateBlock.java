package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class FloodGateBlock extends Block {
    public FloodGateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    public static void placeFluid(World worldIn, BlockPos pos, BlockState bs) {
        if (worldIn.getBlockState(pos.down()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.down(),bs,3);
        } else if (worldIn.getBlockState(pos.north()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.north(),bs,3);
        } else if (worldIn.getBlockState(pos.east()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.east(),bs,3);
        } else if (worldIn.getBlockState(pos.south()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.south(),bs,3);
        } else if (worldIn.getBlockState(pos.west()).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.west(),bs,3);
        }
    }

    public static boolean inInfiniteSource(World worldIn, BlockPos pos) {
        int waterSides = 0;
        for (Direction d : Direction.values()) {
            if (worldIn.getBlockState(pos.offset(d)).matchesBlock(Blocks.WATER)) {
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
