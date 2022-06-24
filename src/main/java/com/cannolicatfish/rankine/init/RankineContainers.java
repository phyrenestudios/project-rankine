package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableContainer;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ProjectRankine.MODID);

    public static RegistryObject<MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> new AlloyFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<CrucibleContainer>> CRUCIBLE_CONTAINER = CONTAINERS.register("crucible", () -> IForgeMenuType.create((windowId, inv, data) -> new CrucibleContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<MixingBarrelContainer>> MIXING_BARREL_CONTAINER = CONTAINERS.register("mixing_barrel", () -> IForgeMenuType.create((windowId, inv, data) -> new MixingBarrelContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<InductionFurnaceContainer>> INDUCTION_FURNACE_CONTAINER = CONTAINERS.register("induction_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> new InductionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<FusionFurnaceContainer>> FUSION_FURNACE_CONTAINER = CONTAINERS.register("fusion_furnace", () -> IForgeMenuType.create((windowId, inv, data) -> new FusionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<EvaporationTowerContainer>> EVAPORATION_TOWER_CONTAINER = CONTAINERS.register("evaporation_tower", () -> IForgeMenuType.create((windowId, inv, data) -> new EvaporationTowerContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<GasBottlerContainer>> GAS_CONDENSER_CONTAINER = CONTAINERS.register("gas_condenser", () -> IForgeMenuType.create((windowId, inv, data) -> new GasBottlerContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<MaterialTestingTableContainer>> MATERIAL_TESTING_TABLE_CONTAINER = CONTAINERS.register("material_testing_table", () -> IForgeMenuType.create((windowId, inv, data) -> new MaterialTestingTableContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<TemplateTableContainer>> TEMPLATE_TABLE_CONTAINER = CONTAINERS.register("template_table", () -> IForgeMenuType.create((windowId, inv, data) -> new TemplateTableContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<MenuType<ElementIndexerContainer>> ELEMENT_INDEXER_CONTAINER = CONTAINERS.register("element_indexer", () -> IForgeMenuType.create((windowId, inv, data) -> new ElementIndexerContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer())));


}
