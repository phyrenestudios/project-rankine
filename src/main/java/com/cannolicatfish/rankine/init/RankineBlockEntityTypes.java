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
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableTile;
import com.cannolicatfish.rankine.blocks.particleaccelerator.ParticleAcceleratorTile;
import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanTile;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilTile;
import com.cannolicatfish.rankine.entities.SpearEntity;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ProjectRankine.MODID);

    public static final RegistryObject<BlockEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = REGISTRY.register("alloy_furnace",
            () -> BlockEntityType.Builder.of(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<PistonCrusherTile>> PISTON_CRUSHER = REGISTRY.register("piston_crusher",
            () -> BlockEntityType.Builder.of(PistonCrusherTile::new, RankineBlocks.PISTON_CRUSHER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<CrucibleTile>> CRUCIBLE = REGISTRY.register("crucible",
            () -> BlockEntityType.Builder.of(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<InductionFurnaceTile>> INDUCTION_FURNACE = REGISTRY.register("induction_furnace",
            () -> BlockEntityType.Builder.of(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<FusionFurnaceTile>> FUSION_FURNACE = REGISTRY.register("fusion_furnace",
            () -> BlockEntityType.Builder.of(FusionFurnaceTile::new, RankineBlocks.FUSION_FURNACE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GyratoryCrusherTile>> GYRATORY_CRUSHER = REGISTRY.register("gyratory_crusher",
            () -> BlockEntityType.Builder.of(GyratoryCrusherTile::new, RankineBlocks.GYRATORY_CRUSHER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<EvaporationTowerTile>> EVAPORATION_TOWER = REGISTRY.register("evaporation_tower",
            () -> BlockEntityType.Builder.of(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GasBottlerTile>> GAS_BOTTLER = REGISTRY.register("gas_condenser",
            () -> BlockEntityType.Builder.of(GasBottlerTile::new, RankineBlocks.GAS_BOTTLER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GasVentTile>> GAS_VENT = REGISTRY.register("gas_vent",
            () -> BlockEntityType.Builder.of(GasVentTile::new, RankineBlocks.GAS_VENT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<TreeTapTile>> TREE_TAP = REGISTRY.register("tree_tap",
            () -> BlockEntityType.Builder.of(TreeTapTile::new, RankineBlocks.TREE_TAP.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<SedimentFanTile>> SEDIMENT_FAN = REGISTRY.register("sediment_fan",
            () -> BlockEntityType.Builder.of(SedimentFanTile::new, RankineBlocks.SEDIMENT_FAN.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<MixingBarrelTile>> MIXING_BARREL = REGISTRY.register("mixing_barrel",
            () -> BlockEntityType.Builder.of(MixingBarrelTile::new, RankineBlocks.MIXING_BARREL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<AlloyBlockTile>> ALLOY_BLOCK = REGISTRY.register("alloy_block",
            () -> BlockEntityType.Builder.of(AlloyBlockTile::new, RankineBlocks.ALLOY_BLOCK.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<TilledSoilTile>> TILLED_SOIL = REGISTRY.register("tilled_soil",
            () -> BlockEntityType.Builder.of(TilledSoilTile::new, RankineBlocks.TILLED_SOIL.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<GroundTapTile>> GROUND_TAP = REGISTRY.register("ground_tap",
            () -> BlockEntityType.Builder.of(GroundTapTile::new, RankineBlocks.GROUND_TAP.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<MaterialTestingTableTile>> MATERIAL_TESTING_TABLE = REGISTRY.register("material_testing_table",
            () -> BlockEntityType.Builder.of(MaterialTestingTableTile::new, RankineBlocks.MATERIAL_TESTING_TABLE.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<BeehiveOvenTile>> BEEHIVE_OVEN_PIT = REGISTRY.register("beehive_oven",
            () -> BlockEntityType.Builder.of(BeehiveOvenTile::new, RankineBlocks.BEEHIVE_OVEN_PIT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<DistillationTowerTile>> DISTILLATION_TOWER = REGISTRY.register("distillation_tower",
            () -> BlockEntityType.Builder.of(DistillationTowerTile::new, RankineBlocks.DISTILLATION_TOWER.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<CharcoalPitTile>> CHARCOAL_PIT = REGISTRY.register("charcoal_pit",
            () -> BlockEntityType.Builder.of(CharcoalPitTile::new, RankineBlocks.CHARCOAL_PIT.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<ParticleAcceleratorTile>> PARTICLE_ACCELERATOR = REGISTRY.register("particle_accelerator",
            () -> BlockEntityType.Builder.of(ParticleAcceleratorTile::new, RankineBlocks.PARTICLE_ACCELERATOR.get())
                    .build(null));

    public static final RegistryObject<BlockEntityType<PedestalTile>> PEDESTAL = REGISTRY.register("pedestal",
            () -> BlockEntityType.Builder.of(PedestalTile::new, RankineBlocks.BRONZE_PEDESTAL.get())
                    .build(null));


}
