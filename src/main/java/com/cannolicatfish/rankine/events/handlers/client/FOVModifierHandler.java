package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;

/**
 * 1.21.1: FOVModifierEvent -> ComputeFovModifierEvent; setNewfov -> setNewFovModifier;
 * AttributeInstance#hasModifier takes the modifier's ResourceLocation id.
 */
public class FOVModifierHandler {
    public static void fovUpdate(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get() && Config.GENERAL.MOVEMENT_MODIFIERS_FOV.get() && movementSpeed != null) {
            if (movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS.id()) || movementSpeed.hasModifier(RankineAttributes.SAND_MS.id()) || movementSpeed.hasModifier(RankineAttributes.BRICKS_MS.id()) ||
                    movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS.id()) || movementSpeed.hasModifier(RankineAttributes.DIRT_MS.id()) || movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS.id()) ||
                    movementSpeed.hasModifier(RankineAttributes.WOODEN_MS.id()) || movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS.id()) || movementSpeed.hasModifier(RankineAttributes.SNOW_MS.id()) ||
                    movementSpeed.hasModifier(RankineAttributes.MUD_MS.id())) {
                event.setNewFovModifier((float) (Minecraft.getInstance().options.fovEffectScale().get() * (player.isSprinting() ? 1.3f : 1)));
            }
        }
    }
}
