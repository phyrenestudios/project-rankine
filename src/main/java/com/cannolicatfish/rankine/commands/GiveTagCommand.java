package com.cannolicatfish.rankine.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GiveTagCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rankinedetails").requires((p_198496_0_) -> {
            return p_198496_0_.hasPermission(2);
        }).then(Commands.argument("item", ItemArgument.item()).executes((p_198492_0_) -> {
            return getInfo(p_198492_0_.getSource(), ItemArgument.getItem(p_198492_0_, "item"));
        })));
    }

    private static int getInfo(CommandSourceStack source, ItemInput itemIn) throws CommandSyntaxException {
        ServerPlayer serverplayerentity = source.getPlayerOrException();
        Item item = itemIn.getItem();

        source.sendSuccess(new TranslatableComponent(item.getDescriptionId()), true);
        for (TagKey<Item> tag : ForgeRegistries.ITEMS.tags().getReverseTag(item).map(IReverseTag::getTagKeys).orElseGet(Stream::of).collect(Collectors.toList())) {
            source.sendSuccess(new TextComponent(tag.location().toString()), true);
        }
        return 1;
    }
}
