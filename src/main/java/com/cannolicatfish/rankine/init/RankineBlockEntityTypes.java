package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
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
import com.cannolicatfish.rankine.client.renders.PedestalRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ProjectRankine.MODID);

    public static final RegistryObject<BlockEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = BLOCK_ENTITY_TYPES.register("alloy_furnace",
            () -> BlockEntityType.Builder.of(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<CrucibleTile>> CRUCIBLE = BLOCK_ENTITY_TYPES.register("crucible",
            () -> BlockEntityType.Builder.of(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<InductionFurnaceTile>> INDUCTION_FURNACE = BLOCK_ENTITY_TYPES.register("induction_furnace",
            () -> BlockEntityType.Builder.of(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<FusionFurnaceTile>> FUSION_FURNACE = BLOCK_ENTITY_TYPES.register("fusion_furnace",
            () -> BlockEntityType.Builder.of(FusionFurnaceTile::new, RankineBlocks.FUSION_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<EvaporationTowerTile>> EVAPORATION_TOWER = BLOCK_ENTITY_TYPES.register("evaporation_tower",
            () -> BlockEntityType.Builder.of(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GasBottlerTile>> GAS_BOTTLER = BLOCK_ENTITY_TYPES.register("gas_condenser",
            () -> BlockEntityType.Builder.of(GasBottlerTile::new, RankineBlocks.GAS_BOTTLER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GasVentTile>> GAS_VENT = BLOCK_ENTITY_TYPES.register("gas_vent",
            () -> BlockEntityType.Builder.of(GasVentTile::new, RankineBlocks.GAS_VENT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<TreeTapTile>> TREE_TAP = BLOCK_ENTITY_TYPES.register("tree_tap",
            () -> BlockEntityType.Builder.of(TreeTapTile::new, RankineBlocks.TREE_TAP.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<SedimentFanTile>> SEDIMENT_FAN = BLOCK_ENTITY_TYPES.register("sediment_fan",
            () -> BlockEntityType.Builder.of(SedimentFanTile::new, RankineBlocks.SEDIMENT_FAN.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<MixingBarrelTile>> MIXING_BARREL = BLOCK_ENTITY_TYPES.register("mixing_barrel",
            () -> BlockEntityType.Builder.of(MixingBarrelTile::new, RankineBlocks.MIXING_BARREL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<AlloyBlockTile>> ALLOY_BLOCK = BLOCK_ENTITY_TYPES.register("alloy_block",
            () -> BlockEntityType.Builder.of(AlloyBlockTile::new, RankineBlocks.ALLOY_BLOCK.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<TilledSoilTile>> TILLED_SOIL = BLOCK_ENTITY_TYPES.register("tilled_soil",
            () -> BlockEntityType.Builder.of(TilledSoilTile::new, RankineBlocks.TILLED_SOIL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GroundTapTile>> GROUND_TAP = BLOCK_ENTITY_TYPES.register("ground_tap",
            () -> BlockEntityType.Builder.of(GroundTapTile::new, RankineBlocks.GROUND_TAP.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<MaterialTestingTableTile>> MATERIAL_TESTING_TABLE = BLOCK_ENTITY_TYPES.register("material_testing_table",
            () -> BlockEntityType.Builder.of(MaterialTestingTableTile::new, RankineBlocks.MATERIAL_TESTING_TABLE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<BeehiveOvenTile>> BEEHIVE_OVEN_PIT = BLOCK_ENTITY_TYPES.register("beehive_oven",
            () -> BlockEntityType.Builder.of(BeehiveOvenTile::new, RankineBlocks.BEEHIVE_OVEN_PIT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<DistillationTowerTile>> DISTILLATION_TOWER = BLOCK_ENTITY_TYPES.register("distillation_tower",
            () -> BlockEntityType.Builder.of(DistillationTowerTile::new, RankineBlocks.DISTILLATION_TOWER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<CharcoalPitTile>> CHARCOAL_PIT = BLOCK_ENTITY_TYPES.register("charcoal_pit",
            () -> BlockEntityType.Builder.of(CharcoalPitTile::new, RankineBlocks.CHARCOAL_PIT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<ParticleAcceleratorTile>> PARTICLE_ACCELERATOR = BLOCK_ENTITY_TYPES.register("particle_accelerator",
            () -> BlockEntityType.Builder.of(ParticleAcceleratorTile::new, RankineBlocks.PARTICLE_ACCELERATOR.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<PedestalTile>> PEDESTAL = BLOCK_ENTITY_TYPES.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalTile::new, RankineBlocks.BRONZE_PEDESTAL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<RankineSignBlockEntity>> RANKINE_SIGN = BLOCK_ENTITY_TYPES.register("rankine_sign",
            () -> BlockEntityType.Builder.of(RankineSignBlockEntity::new,
                    RankineBlocks.CEDAR_SIGN.get(), RankineBlocks.CEDAR_WALL_SIGN.get(),
                    RankineBlocks.BALSAM_FIR_SIGN.get(), RankineBlocks.BALSAM_FIR_WALL_SIGN.get(),
                    RankineBlocks.EASTERN_HEMLOCK_SIGN.get(), RankineBlocks.EASTERN_HEMLOCK_WALL_SIGN.get(),
                    RankineBlocks.WESTERN_HEMLOCK_SIGN.get(), RankineBlocks.WESTERN_HEMLOCK_WALL_SIGN.get(),
                    RankineBlocks.PINYON_PINE_SIGN.get(), RankineBlocks.PINYON_PINE_WALL_SIGN.get(),
                    RankineBlocks.JUNIPER_SIGN.get(), RankineBlocks.JUNIPER_WALL_SIGN.get(),
                    RankineBlocks.BLACK_BIRCH_SIGN.get(), RankineBlocks.BLACK_BIRCH_WALL_SIGN.get(),
                    RankineBlocks.YELLOW_BIRCH_SIGN.get(), RankineBlocks.YELLOW_BIRCH_WALL_SIGN.get(),
                    RankineBlocks.RED_BIRCH_SIGN.get(), RankineBlocks.RED_BIRCH_WALL_SIGN.get(),
                    RankineBlocks.MAPLE_SIGN.get(), RankineBlocks.MAPLE_WALL_SIGN.get(),
                    RankineBlocks.MAGNOLIA_SIGN.get(), RankineBlocks.MAGNOLIA_WALL_SIGN.get(),
                    RankineBlocks.BLACK_WALNUT_SIGN.get(), RankineBlocks.BLACK_WALNUT_WALL_SIGN.get(),
                    RankineBlocks.COCONUT_PALM_SIGN.get(), RankineBlocks.COCONUT_PALM_WALL_SIGN.get(),
                    RankineBlocks.CORK_OAK_SIGN.get(), RankineBlocks.CORK_OAK_WALL_SIGN.get(),
                    RankineBlocks.SHARINGA_SIGN.get(), RankineBlocks.SHARINGA_WALL_SIGN.get(),
                    RankineBlocks.CINNAMON_SIGN.get(), RankineBlocks.CINNAMON_WALL_SIGN.get(),
                    RankineBlocks.HONEY_LOCUST_SIGN.get(), RankineBlocks.HONEY_LOCUST_WALL_SIGN.get(),
                    RankineBlocks.WEEPING_WILLOW_SIGN.get(), RankineBlocks.WEEPING_WILLOW_WALL_SIGN.get(),
                    RankineBlocks.ERYTHRINA_SIGN.get(), RankineBlocks.ERYTHRINA_WALL_SIGN.get(),
                    RankineBlocks.PETRIFIED_CHORUS_SIGN.get(), RankineBlocks.PETRIFIED_CHORUS_WALL_SIGN.get(),
                    RankineBlocks.CHARRED_SIGN.get(), RankineBlocks.CHARRED_WALL_SIGN.get(),
                    RankineBlocks.BAMBOO_SIGN.get(), RankineBlocks.BAMBOO_WALL_SIGN.get(),
                    RankineBlocks.BAMBOO_CULMS_SIGN.get(), RankineBlocks.BAMBOO_CULMS_WALL_SIGN.get()
                    ).build(null));


    @OnlyIn(Dist.CLIENT)
    public static void registerBlockEntityRenders() {
        BlockEntityRenderers.register(RANKINE_SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(PEDESTAL.get(), PedestalRenderer::new);

    }

}
