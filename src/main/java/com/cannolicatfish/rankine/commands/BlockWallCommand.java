package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.blocks.block_enums.RankineWood;
import com.cannolicatfish.rankine.blocks.block_enums.StoneBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
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
        int i = 0;
        for (StoneBlocks baseStone : StoneBlocks.values()) {
            serverworld.setBlockAndUpdate(pos.north(2*i), baseStone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.north(2*i+1), baseStone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(1).north(2*i), baseStone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(1).north(2*i+1), baseStone.getStone().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i), baseStone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(2).north(2*i+1), baseStone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i), baseStone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(3).north(2*i+1), baseStone.getPolished().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i), baseStone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(4).north(2*i+1), baseStone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i), baseStone.getBricks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(5).north(2*i+1), baseStone.getBricks().defaultBlockState());
            i++;
        }

        i = 0;
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i),Wood.getLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(6).north(2*i+1),Wood.getLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i),Wood.getLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(7).north(2*i+1),Wood.getLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i),Wood.getStrippedLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(8).north(2*i+1),Wood.getStrippedLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i),Wood.getStrippedLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(9).north(2*i+1),Wood.getStrippedLog().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i),Wood.getPlanks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(10).north(2*i+1),Wood.getPlanks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i),Wood.getPlanks().defaultBlockState());
            serverworld.setBlockAndUpdate(pos.above(11).north(2*i+1),Wood.getPlanks().defaultBlockState());
            i++;
        }


        return 1;
    }
}
