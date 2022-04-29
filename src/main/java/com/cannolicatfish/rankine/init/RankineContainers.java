package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerContainer;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.items.indexer.ElementIndexerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ProjectRankine.MODID);

    public static RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> IForgeContainerType.create((windowId, inv, data) -> new AlloyFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<PistonCrusherContainer>> PISTON_CRUSHER_CONTAINER = CONTAINERS.register("piston_crusher", () ->IForgeContainerType.create((windowId, inv, data) -> new PistonCrusherContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<CrucibleContainer>> CRUCIBLE_CONTAINER = CONTAINERS.register("crucible", () ->IForgeContainerType.create((windowId, inv, data) -> new CrucibleContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<MixingBarrelContainer>> MIXING_BARREL_CONTAINER = CONTAINERS.register("mixing_barrel", () ->IForgeContainerType.create((windowId, inv, data) -> new MixingBarrelContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<InductionFurnaceContainer>> INDUCTION_FURNACE_CONTAINER = CONTAINERS.register("induction_furnace", () ->IForgeContainerType.create((windowId, inv, data) -> new InductionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<FusionFurnaceContainer>> FUSION_FURNACE_CONTAINER = CONTAINERS.register("fusion_furnace", () ->IForgeContainerType.create((windowId, inv, data) -> new FusionFurnaceContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<GyratoryCrusherContainer>> GYRATORY_CRUSHER_CONTAINER = CONTAINERS.register("gyratory_crusher", () ->IForgeContainerType.create((windowId, inv, data) -> new GyratoryCrusherContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<EvaporationTowerContainer>> EVAPORATION_TOWER_CONTAINER = CONTAINERS.register("evaporation_tower", () ->IForgeContainerType.create((windowId, inv, data) -> new EvaporationTowerContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<GasBottlerContainer>> GAS_CONDENSER_CONTAINER = CONTAINERS.register("gas_condenser", () ->IForgeContainerType.create((windowId, inv, data) -> new GasBottlerContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<MaterialTestingTableContainer>> MATERIAL_TESTING_TABLE_CONTAINER = CONTAINERS.register("material_testing_table", () ->IForgeContainerType.create((windowId, inv, data) -> new MaterialTestingTableContainer(windowId, ProjectRankine.proxy.getClientWorld(), data.readBlockPos(), inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<TemplateTableContainer>> TEMPLATE_TABLE_CONTAINER = CONTAINERS.register("template_table", () ->IForgeContainerType.create((windowId, inv, data) -> new TemplateTableContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer())));
    public static RegistryObject<ContainerType<ElementIndexerContainer>> ELEMENT_INDEXER_CONTAINER = CONTAINERS.register("element_indexer", () ->IForgeContainerType.create((windowId, inv, data) -> new ElementIndexerContainer(windowId, inv, ProjectRankine.proxy.getClientPlayer())));


}
