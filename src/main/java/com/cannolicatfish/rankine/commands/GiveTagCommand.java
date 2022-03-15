package com.cannolicatfish.rankine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class GiveTagCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("rankinedetails").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermission(2);
        }).then(Commands.argument("item", ItemArgument.item()).executes((p_198492_0_) -> {
            return getInfo(p_198492_0_.getSource(), ItemArgument.getItem(p_198492_0_, "item"));
        })));
    }

    private static int getInfo(CommandSource source, ItemInput itemIn) throws CommandSyntaxException {
        ServerPlayerEntity serverplayerentity = source.getPlayerOrException();
        Item item = itemIn.getItem();

        source.sendSuccess(new TranslationTextComponent(item.getDescriptionId()), true);
        source.sendSuccess(new StringTextComponent(item.getTags().toString()),true);

        return 1;
    }
}
