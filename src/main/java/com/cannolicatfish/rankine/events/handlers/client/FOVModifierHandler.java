package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.FOVModifierEvent;

public class FOVModifierHandler {
    public static void fovUpdate(FOVModifierEvent event) {
        Player player = event.getEntity();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get() && Config.GENERAL.MOVEMENT_MODIFIERS_FOV.get() && movementSpeed != null) {
            if (movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS) || movementSpeed.hasModifier(RankineAttributes.SAND_MS) || movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.DIRT_MS) || movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) || movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) || movementSpeed.hasModifier(RankineAttributes.SNOW_MS) ||
                    movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                event.setNewfov(Minecraft.getInstance().options.fovEffectScale * (player.isSprinting() ? 1.3f : 1));
            }
        }
    }
}
