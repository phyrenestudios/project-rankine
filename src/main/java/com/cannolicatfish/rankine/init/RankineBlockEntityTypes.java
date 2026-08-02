package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerTile;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleTile;
import com.cannolicatfish.rankine.blocks.distillationtower.DistillationTowerTile;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerTile;
import com.cannolicatfish.rankine.blocks.gasvent.GasVentTile;
import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableTile;
import com.cannolicatfish.rankine.blocks.particleaccelerator.ParticleAcceleratorTile;
import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanTile;
import com.cannolicatfish.rankine.blocks.signs.RankineSignBlockEntity;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilTile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ProjectRankine.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = BLOCK_ENTITY_TYPES.register("alloy_furnace",
            () -> BlockEntityType.Builder.of(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get())
                    .build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BatteryChargerTile>> BATTERY_CHARGER = BLOCK_ENTITY_TYPES.register("battery_charger",
            () -> BlockEntityType.Builder.of(BatteryChargerTile::new, RankineBlocks.BATTERY_CHARGER.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrucibleTile>> CRUCIBLE = BLOCK_ENTITY_TYPES.register("crucible",
            () -> BlockEntityType.Builder.of(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InductionFurnaceTile>> INDUCTION_FURNACE = BLOCK_ENTITY_TYPES.register("induction_furnace",
            () -> BlockEntityType.Builder.of(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FusionFurnaceTile>> FUSION_FURNACE = BLOCK_ENTITY_TYPES.register("fusion_furnace",
            () -> BlockEntityType.Builder.of(FusionFurnaceTile::new, RankineBlocks.FUSION_FURNACE.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EvaporationTowerTile>> EVAPORATION_TOWER = BLOCK_ENTITY_TYPES.register("evaporation_tower",
            () -> BlockEntityType.Builder.of(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GasBottlerTile>> GAS_BOTTLER = BLOCK_ENTITY_TYPES.register("gas_condenser",
            () -> BlockEntityType.Builder.of(GasBottlerTile::new, RankineBlocks.GAS_BOTTLER.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GasVentTile>> GAS_VENT = BLOCK_ENTITY_TYPES.register("gas_vent",
            () -> BlockEntityType.Builder.of(GasVentTile::new, RankineBlocks.GAS_VENT.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreeTapTile>> TREE_TAP = BLOCK_ENTITY_TYPES.register("tree_tap",
            () -> BlockEntityType.Builder.of(TreeTapTile::new, RankineBlocks.TREE_TAP.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SedimentFanTile>> SEDIMENT_FAN = BLOCK_ENTITY_TYPES.register("sediment_fan",
            () -> BlockEntityType.Builder.of(SedimentFanTile::new, RankineBlocks.SEDIMENT_FAN.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MixingBarrelTile>> MIXING_BARREL = BLOCK_ENTITY_TYPES.register("mixing_barrel",
            () -> BlockEntityType.Builder.of(MixingBarrelTile::new, RankineBlocks.MIXING_BARREL.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlloyBlockTile>> ALLOY_BLOCK = BLOCK_ENTITY_TYPES.register("alloy_block",
            // Every AlloyBlock creates an AlloyBlockTile, but 1.18.2 only listed the generic
            // rankine:alloy_block here. That mismatch was merely logged as a warning back then;
            // 1.21 throws IllegalStateException from LevelChunk when a block entity's type does not
            // accept the state, so placing e.g. a steel_block would fail. Bind all of them.
            () -> BlockEntityType.Builder.of(AlloyBlockTile::new,
                            RankineLists.ALLOY_BLOCKS.toArray(new net.minecraft.world.level.block.Block[0]))
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TilledSoilTile>> TILLED_SOIL = BLOCK_ENTITY_TYPES.register("tilled_soil",
            () -> BlockEntityType.Builder.of(TilledSoilTile::new, RankineBlocks.TILLED_SOIL.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GroundTapTile>> GROUND_TAP = BLOCK_ENTITY_TYPES.register("ground_tap",
            () -> BlockEntityType.Builder.of(GroundTapTile::new, RankineBlocks.GROUND_TAP.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MaterialTestingTableTile>> MATERIAL_TESTING_TABLE = BLOCK_ENTITY_TYPES.register("material_testing_table",
            () -> BlockEntityType.Builder.of(MaterialTestingTableTile::new, RankineBlocks.MATERIAL_TESTING_TABLE.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BeehiveOvenTile>> BEEHIVE_OVEN_PIT = BLOCK_ENTITY_TYPES.register("beehive_oven",
            () -> BlockEntityType.Builder.of(BeehiveOvenTile::new, RankineBlocks.BEEHIVE_OVEN_PIT.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DistillationTowerTile>> DISTILLATION_TOWER = BLOCK_ENTITY_TYPES.register("distillation_tower",
            () -> BlockEntityType.Builder.of(DistillationTowerTile::new, RankineBlocks.DISTILLATION_TOWER.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CharcoalPitTile>> CHARCOAL_PIT = BLOCK_ENTITY_TYPES.register("charcoal_pit",
            () -> BlockEntityType.Builder.of(CharcoalPitTile::new, RankineBlocks.CHARCOAL_PIT.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ParticleAcceleratorTile>> PARTICLE_ACCELERATOR = BLOCK_ENTITY_TYPES.register("particle_accelerator",
            () -> BlockEntityType.Builder.of(ParticleAcceleratorTile::new, RankineBlocks.PARTICLE_ACCELERATOR.get())
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalTile>> PEDESTAL = BLOCK_ENTITY_TYPES.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalTile::new, RankineLists.ALLOY_PEDESTALS.toArray(new Block[0]))
                    .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RankineSignBlockEntity>> RANKINE_SIGN = BLOCK_ENTITY_TYPES.register("rankine_sign",
            () -> BlockEntityType.Builder.of(RankineSignBlockEntity::new,
                    RankineBlocks.CEDAR.getSign(), RankineBlocks.CEDAR.getWallSign(),
                    RankineBlocks.BALSAM_FIR.getSign(), RankineBlocks.BALSAM_FIR.getWallSign(),
                    RankineBlocks.EASTERN_HEMLOCK.getSign(), RankineBlocks.EASTERN_HEMLOCK.getWallSign(),
                    RankineBlocks.WESTERN_HEMLOCK.getSign(), RankineBlocks.WESTERN_HEMLOCK.getWallSign(),
                    RankineBlocks.PINYON_PINE.getSign(), RankineBlocks.PINYON_PINE.getWallSign(),
                    RankineBlocks.JUNIPER.getSign(), RankineBlocks.JUNIPER.getWallSign(),
                    RankineBlocks.BLACK_BIRCH.getSign(), RankineBlocks.BLACK_BIRCH.getWallSign(),
                    RankineBlocks.YELLOW_BIRCH.getSign(), RankineBlocks.YELLOW_BIRCH.getWallSign(),
                    RankineBlocks.RED_BIRCH.getSign(), RankineBlocks.RED_BIRCH.getWallSign(),
                    RankineBlocks.MAPLE.getSign(), RankineBlocks.MAPLE.getWallSign(),
                    RankineBlocks.MAGNOLIA.getSign(), RankineBlocks.MAGNOLIA.getWallSign(),
                    RankineBlocks.BLACK_WALNUT.getSign(), RankineBlocks.BLACK_WALNUT.getWallSign(),
                    RankineBlocks.COCONUT_PALM.getSign(), RankineBlocks.COCONUT_PALM.getWallSign(),
                    RankineBlocks.CORK_OAK.getSign(), RankineBlocks.CORK_OAK.getWallSign(),
                    RankineBlocks.SHARINGA.getSign(), RankineBlocks.SHARINGA.getWallSign(),
                    RankineBlocks.CINNAMON.getSign(), RankineBlocks.CINNAMON.getWallSign(),
                    RankineBlocks.HONEY_LOCUST.getSign(), RankineBlocks.HONEY_LOCUST.getWallSign(),
                    RankineBlocks.WEEPING_WILLOW.getSign(), RankineBlocks.WEEPING_WILLOW.getWallSign(),
                    RankineBlocks.ERYTHRINA.getSign(), RankineBlocks.ERYTHRINA.getWallSign(),
                    RankineBlocks.PETRIFIED_CHORUS.getSign(), RankineBlocks.PETRIFIED_CHORUS.getWallSign(),
                    RankineBlocks.CHARRED.getSign(), RankineBlocks.CHARRED.getWallSign(),
                    RankineBlocks.BAMBOO.getSign(), RankineBlocks.BAMBOO.getWallSign(),
                    RankineBlocks.BAMBOO_CULMS.getSign(), RankineBlocks.BAMBOO_CULMS.getWallSign()
                    ).build(null));


    // PORT-TODO(client): moved out of this class — the client agent must register these in
    // EntityRenderersEvent.RegisterRenderers (mod bus, Dist.CLIENT), e.g. in client/ClientSetup:
    //   BlockEntityRenderers.register(RankineBlockEntityTypes.RANKINE_SIGN.get(), SignRenderer::new);
    //   BlockEntityRenderers.register(RankineBlockEntityTypes.PEDESTAL.get(), PedestalRenderer::new);
    // (former registerBlockEntityRenders() body; PedestalRenderer lives in client/renders/PedestalRenderer)

}
