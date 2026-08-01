package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * 1.21.1 capability wiring. BlockEntities no longer override {@code getCapability}; each machine
 * exposes a plain {@code getItemHandler(@Nullable Direction)} accessor (marked {@code // PORT-CAP:}
 * in blocks/) and the sided IItemHandler capability is registered here on the MOD bus.
 *
 * <p>Sidedness is preserved by the accessors themselves — they return null for {@code side == null}
 * exactly like the old {@code getCapability} did (except PedestalTile, which was unsided and
 * returns its handler for every side including null).
 *
 * <p>Only ITEM_HANDLER was ever exposed in 1.18.2 — no fluid/energy capability was provided by any
 * Rankine BlockEntity, so none is registered here.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CapabilityRegistrationHandler {

    private CapabilityRegistrationHandler() {}

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.ALLOY_FURNACE.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.BATTERY_CHARGER.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.CRUCIBLE.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.EVAPORATION_TOWER.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.FUSION_FURNACE.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.GAS_BOTTLER.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.INDUCTION_FURNACE.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.MIXING_BARREL.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.MATERIAL_TESTING_TABLE.get(),
                (be, side) -> be.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.PEDESTAL.get(),
                (be, side) -> be.getItemHandler(side));

        // PORT-TODO(blocks): LaserQuarryTile has a PORT-CAP getItemHandler accessor but no registered
        // BlockEntityType in RankineBlockEntityTypes (the 1.18.2 LASER_QUARRY_TILE field was already
        // dead code). Add the type there and register it here if the laser quarry is meant to ship.

        // Not registered because they never exposed ITEM_HANDLER in 1.18.2 either:
        // GAS_VENT, TREE_TAP, SEDIMENT_FAN, ALLOY_BLOCK, TILLED_SOIL, GROUND_TAP, BEEHIVE_OVEN_PIT,
        // DISTILLATION_TOWER, CHARCOAL_PIT, PARTICLE_ACCELERATOR, RANKINE_SIGN.
    }
}
