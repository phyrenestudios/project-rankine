package com.cannolicatfish.rankine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class BlockWallCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("blockwall").requires((p_198688_0_) -> {
            return p_198688_0_.hasPermission(2);
        }).then(Commands.argument("pos", BlockPosArgument.blockPos()).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((p_198682_0_) -> {
            return blockWall(p_198682_0_.getSource(), BlockPosArgument.getLoadedBlockPos(p_198682_0_, "pos"));
        }))));
    }

    private static int blockWall(CommandSourceStack source, BlockPos pos) throws CommandSyntaxException {
        ServerLevel serverworld = source.getLevel();
        /*
        for (Block BLK : RankineLists.STONES) {
            int i = RankineLists.STONES.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above().north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above().north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.POLISHED_STONES) {
            int i = RankineLists.POLISHED_STONES.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.STONE_BRICKS) {
            int i = RankineLists.STONE_BRICKS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.LOGS) {
            int i = RankineLists.LOGS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.STRIPPED_LOGS) {
            int i = RankineLists.STRIPPED_LOGS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.PLANKS) {
            int i = RankineLists.PLANKS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i+1),BLK.defaultBlockState());
        }

         */

        return 1;
    }
}
