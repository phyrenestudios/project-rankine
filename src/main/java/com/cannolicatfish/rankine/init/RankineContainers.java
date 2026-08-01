package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableContainer;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, ProjectRankine.MODID);

    public static DeferredHolder<MenuType<?>, MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> IMenuTypeExtension.create((windowId, inv, data) -> new AlloyFurnaceContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<CrucibleContainer>> CRUCIBLE_CONTAINER = CONTAINERS.register("crucible", () -> IMenuTypeExtension.create((windowId, inv, data) -> new CrucibleContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<MixingBarrelContainer>> MIXING_BARREL_CONTAINER = CONTAINERS.register("mixing_barrel", () -> IMenuTypeExtension.create((windowId, inv, data) -> new MixingBarrelContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<InductionFurnaceContainer>> INDUCTION_FURNACE_CONTAINER = CONTAINERS.register("induction_furnace", () -> IMenuTypeExtension.create((windowId, inv, data) -> new InductionFurnaceContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<FusionFurnaceContainer>> FUSION_FURNACE_CONTAINER = CONTAINERS.register("fusion_furnace", () -> IMenuTypeExtension.create((windowId, inv, data) -> new FusionFurnaceContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<EvaporationTowerContainer>> EVAPORATION_TOWER_CONTAINER = CONTAINERS.register("evaporation_tower", () -> IMenuTypeExtension.create((windowId, inv, data) -> new EvaporationTowerContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<GasBottlerContainer>> GAS_CONDENSER_CONTAINER = CONTAINERS.register("gas_condenser", () -> IMenuTypeExtension.create((windowId, inv, data) -> new GasBottlerContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<BatteryChargerContainer>> BATTERY_CHARGER_CONTAINER = CONTAINERS.register("battery_charger", () -> IMenuTypeExtension.create((windowId, inv, data) -> new BatteryChargerContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<MaterialTestingTableContainer>> MATERIAL_TESTING_TABLE_CONTAINER = CONTAINERS.register("material_testing_table", () -> IMenuTypeExtension.create((windowId, inv, data) -> new MaterialTestingTableContainer(windowId, inv.player.level(), data.readBlockPos(), inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<TemplateTableContainer>> TEMPLATE_TABLE_CONTAINER = CONTAINERS.register("template_table", () -> IMenuTypeExtension.create((windowId, inv, data) -> new TemplateTableContainer(windowId, inv, inv.player)));
    public static DeferredHolder<MenuType<?>, MenuType<ElementIndexerContainer>> ELEMENT_INDEXER_CONTAINER = CONTAINERS.register("element_indexer", () -> IMenuTypeExtension.create((windowId, inv, data) -> new ElementIndexerContainer(windowId, inv, inv.player)));

    // PORT-TODO(client): register menu screens in RegisterMenuScreensEvent (mod bus, Dist.CLIENT).
    // Menu -> Screen pairs (screens under blocks/** and items/indexer):
    //   ALLOY_FURNACE_CONTAINER          -> AlloyFurnaceScreen
    //   CRUCIBLE_CONTAINER               -> CrucibleScreen
    //   MIXING_BARREL_CONTAINER          -> MixingBarrelScreen
    //   INDUCTION_FURNACE_CONTAINER      -> InductionFurnaceScreen
    //   FUSION_FURNACE_CONTAINER         -> FusionFurnaceScreen
    //   EVAPORATION_TOWER_CONTAINER      -> EvaporationTowerScreen
    //   GAS_CONDENSER_CONTAINER          -> GasBottlerScreen
    //   BATTERY_CHARGER_CONTAINER        -> BatteryChargerScreen
    //   MATERIAL_TESTING_TABLE_CONTAINER -> MaterialTestingTableScreen
    //   TEMPLATE_TABLE_CONTAINER         -> TemplateTableScreen
    //   ELEMENT_INDEXER_CONTAINER        -> ElementIndexerScreen
    // (implemented in client/ClientSetup.java; kept here as the authoritative list)

}
