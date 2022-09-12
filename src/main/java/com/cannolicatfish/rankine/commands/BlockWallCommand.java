package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.blocks.RankineStone;
import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

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
        int i = 0;
        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            serverworld.setBlockAndUpdate(pos.north(2*i), Stone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.north(2*i+1), Stone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(1).north(2*i), Stone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(1).north(2*i+1), Stone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i), Stone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i+1), Stone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i), Stone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i+1), Stone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i), Stone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i+1), Stone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i), Stone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i+1), Stone.getBricks().defaultBlockState());
            i++;
        }

        for (Block BLK : RankineLists.LOGS) {
            i = RankineLists.LOGS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.STRIPPED_LOGS) {
            i = RankineLists.STRIPPED_LOGS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i+1),BLK.defaultBlockState());
        }
        for (Block BLK : RankineLists.PLANKS) {
            i = RankineLists.PLANKS.indexOf(BLK);
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i+1),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i),BLK.defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i+1),BLK.defaultBlockState());
        }


        return 1;
    }
}
