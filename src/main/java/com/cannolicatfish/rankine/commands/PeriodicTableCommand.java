package com.cannolicatfish.rankine.commands;

import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;

public class PeriodicTableCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("periodic").requires((p_198688_0_) -> {
            return p_198688_0_.hasPermission(2);
        }).then(Commands.argument("pos", BlockPosArgument.blockPos()).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((p_198682_0_) -> {
            return periodicTable(p_198682_0_.getSource(), BlockPosArgument.getLoadedBlockPos(p_198682_0_, "pos"));
        }))));
    }

    private static int periodicTable(CommandSourceStack source, BlockPos pos) throws CommandSyntaxException {
        ServerLevel serverworld = source.getLevel();
        List<BlockPos> posLIst = Arrays.asList(
            pos, pos.north(17),
            pos.below(), pos.below().north(), pos.below().north(12), pos.below().north(13), pos.below().north(14), pos.below().north(15), pos.below().north(16), pos.below().north(17),
            pos.below(2), pos.below(2).north(), pos.below(2).north(12), pos.below(2).north(13), pos.below(2).north(14), pos.below(2).north(15), pos.below(2).north(16), pos.below(2).north(17),
            pos.below(3), pos.below(3).north(), pos.below(3).north(2), pos.below(3).north(3), pos.below(3).north(4), pos.below(3).north(5), pos.below(3).north(6), pos.below(3).north(8), pos.below(3).north(9), pos.below(3).north(11), pos.below(3).north(12), pos.below(3).north(13), pos.below(3).north(14), pos.below(3).north(15), pos.below(3).north(16), pos.below(3).north(17),
            pos.below(4), pos.below(4).north(), pos.below(4).north(2), pos.below(4).north(3), pos.below(4).north(4), pos.below(4).north(5), pos.below(4).north(6), pos.below(4).north(7), pos.below(4).north(8), pos.below(4).north(9), pos.below(4).north(10), pos.below(4).north(11), pos.below(4).north(12), pos.below(4).north(13), pos.below(4).north(14), pos.below(4).north(15), pos.below(4).north(16), pos.below(4).north(17),
            pos.below(5), pos.below(5).north(), pos.below(5).north(2),
                pos.below(8).north(2), pos.below(8).north(3), pos.below(8).north(4), pos.below(8).north(5), pos.below(8).north(6), pos.below(8).north(7), pos.below(8).north(8), pos.below(8).north(9), pos.below(8).north(10), pos.below(8).north(11), pos.below(8).north(12), pos.below(8).north(13), pos.below(8).north(14), pos.below(8).north(15),
                pos.below(5).north(3), pos.below(5).north(4), pos.below(5).north(5), pos.below(5).north(6), pos.below(5).north(7), pos.below(5).north(8), pos.below(5).north(9), pos.below(5).north(11), pos.below(5).north(12), pos.below(5).north(13), pos.below(5).north(14), pos.below(5).north(15), pos.below(5).north(16), pos.below(5).north(17),
            pos.below(6), pos.below(6).north(), pos.below(6).north(2),
                pos.below(9).north(2), pos.below(9).north(3), pos.below(9).north(4), pos.below(9).north(5), pos.below(9).north(6), pos.below(9).north(7), pos.below(9).north(8), pos.below(9).north(9), pos.below(9).north(10), pos.below(9).north(11), pos.below(9).north(12), pos.below(9).north(13), pos.below(9).north(14), pos.below(9).north(15),
                pos.below(6).north(3), pos.below(6).north(4), pos.below(6).north(5), pos.below(6).north(6), pos.below(6).north(7), pos.below(6).north(8), pos.below(6).north(9), pos.below(6).north(10), pos.below(6).north(11), pos.below(6).north(12), pos.below(6).north(13), pos.below(6).north(14), pos.below(6).north(15), pos.below(6).north(16), pos.below(6).north(17),
            
            pos.below(6).north(18)
        );

        for (Block blk : RankineLists.ELEMENT_BLOCKS) {
            serverworld.setBlockAndUpdate(posLIst.get(RankineLists.ELEMENT_BLOCKS.indexOf(blk)), blk.defaultBlockState());
        }
        return 1;
    }


}
