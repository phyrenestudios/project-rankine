package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class BlockWallCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("blockwall").requires((p_198688_0_) -> {
            return p_198688_0_.hasPermissionLevel(2);
        }).then(Commands.argument("pos", BlockPosArgument.blockPos()).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((p_198682_0_) -> {
            return blockWall(p_198682_0_.getSource(), BlockPosArgument.getLoadedBlockPos(p_198682_0_, "pos"));
        }))));
    }

    private static int blockWall(CommandSource source, BlockPos pos) throws CommandSyntaxException {
        ServerWorld serverworld = source.getWorld();
        for (Block BLK : RankineLists.STONES) {
            int i = RankineLists.STONES.indexOf(BLK);
            serverworld.setBlockState(pos.north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.north(2*i+1),BLK.getDefaultState());
            serverworld.setBlockState(pos.up().north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.up().north(2*i+1),BLK.getDefaultState());
        }
        for (Block BLK : RankineLists.POLISHED_STONES) {
            int i = RankineLists.POLISHED_STONES.indexOf(BLK);
            serverworld.setBlockState(pos.up(2).north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(2).north(2*i+1),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(3).north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(3).north(2*i+1),BLK.getDefaultState());
        }
        for (Block BLK : RankineLists.STONE_BRICKS) {
            int i = RankineLists.STONE_BRICKS.indexOf(BLK);
            serverworld.setBlockState(pos.up(4).north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(4).north(2*i+1),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(5).north(2*i),BLK.getDefaultState());
            serverworld.setBlockState(pos.up(5).north(2*i+1),BLK.getDefaultState());
        }

        return 1;
    }
}
