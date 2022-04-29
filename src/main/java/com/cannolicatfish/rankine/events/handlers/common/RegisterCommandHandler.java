package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.commands.BlockWallCommand;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import net.minecraftforge.event.RegisterCommandsEvent;

public class RegisterCommandHandler {

    public static void registerCommands( RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
        GiveTagCommand.register(event.getDispatcher());
        BlockWallCommand.register(event.getDispatcher());
    }
}
