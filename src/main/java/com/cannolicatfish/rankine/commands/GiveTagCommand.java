package com.cannolicatfish.rankine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class GiveTagCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(Commands.literal("rankinedetails").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermission(2);
        }).then(Commands.argument("item", ItemArgument.item(context)).executes((p_198492_0_) -> {
            return getInfo(p_198492_0_.getSource(), ItemArgument.getItem(p_198492_0_, "item"));
        })));
    }

    private static int getInfo(CommandSourceStack source, ItemInput itemIn) throws CommandSyntaxException {
        source.getPlayerOrException();
        Item item = itemIn.getItem();

        source.sendSuccess(() -> Component.translatable(item.getDescriptionId()), true);
        // 1.21.1: reverse tag lookup via the item's registry holder
        for (TagKey<Item> tag : item.builtInRegistryHolder().tags().toList()) {
            source.sendSuccess(() -> Component.literal(tag.location().toString()), true);
        }
        return 1;
    }
}
