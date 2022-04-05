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
import com.cannolicatfish.rankine.blocks.signs.RankineSignTile;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ProjectRankine.MODID);

    public static final RegistryObject<TileEntityType<RankineSignTile>> RANKINE_SIGN = TILE_ENTITIES.register("rankine_sign", () -> TileEntityType.Builder.create(RankineSignTile::new, RankineLists.WOODEN_SIGNS.toArray(new Block[0])).build(null));
    public static final RegistryObject<TileEntityType<PedestalTile>> PEDESTAL_TILE = TILE_ENTITIES.register("pedestal", () -> TileEntityType.Builder.create(PedestalTile::new, RankineLists.ALLOY_PEDESTALS.toArray(new Block[0])).build(null));
    public static final RegistryObject<TileEntityType<AlloyFurnaceTile>> ALLOY_FURNACE = TILE_ENTITIES.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTile::new, RankineBlocks.ALLOY_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<PistonCrusherTile>> PISTON_CRUSHER = TILE_ENTITIES.register("piston_crusher", () -> TileEntityType.Builder.create(PistonCrusherTile::new, RankineBlocks.PISTON_CRUSHER.get()).build(null));
    public static final RegistryObject<TileEntityType<CrucibleTile>> CRUCIBLE = TILE_ENTITIES.register("crucible", () -> TileEntityType.Builder.create(CrucibleTile::new, RankineBlocks.CRUCIBLE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<InductionFurnaceTile>> INDUCTION_FURNACE = TILE_ENTITIES.register("induction_furnace", () -> TileEntityType.Builder.create(InductionFurnaceTile::new, RankineBlocks.INDUCTION_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<FusionFurnaceTile>> FUSION_FURNACE = TILE_ENTITIES.register("fusion_furnace", () -> TileEntityType.Builder.create(FusionFurnaceTile::new, RankineBlocks.FUSION_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<GyratoryCrusherTile>> GYRATORY_CRUSHER = TILE_ENTITIES.register("gyratory_crusher", () -> TileEntityType.Builder.create(GyratoryCrusherTile::new, RankineBlocks.GYRATORY_CRUSHER.get()).build(null));
    public static final RegistryObject<TileEntityType<EvaporationTowerTile>> EVAPORATION_TOWER = TILE_ENTITIES.register("evaporation_tower", () -> TileEntityType.Builder.create(EvaporationTowerTile::new, RankineBlocks.EVAPORATION_TOWER.get()).build(null));
    public static final RegistryObject<TileEntityType<GasBottlerTile>> GAS_CONDENSER = TILE_ENTITIES.register("gas_condenser", () -> TileEntityType.Builder.create(GasBottlerTile::new, RankineBlocks.GAS_BOTTLER.get()).build(null));
    public static final RegistryObject<TileEntityType<GasVentTile>> GAS_VENT = TILE_ENTITIES.register("gas_vent", () -> TileEntityType.Builder.create(GasVentTile::new, RankineBlocks.GAS_VENT.get()).build(null));
    public static final RegistryObject<TileEntityType<TreeTapTile>> TREE_TAP = TILE_ENTITIES.register("tree_tap", () -> TileEntityType.Builder.create(TreeTapTile::new, RankineBlocks.TREE_TAP.get()).build(null));
    public static final RegistryObject<TileEntityType<SedimentFanTile>> SEDIMENT_FAN = TILE_ENTITIES.register("sediment_fan", () -> TileEntityType.Builder.create(SedimentFanTile::new, RankineBlocks.SEDIMENT_FAN.get()).build(null));
    public static final RegistryObject<TileEntityType<MixingBarrelTile>> MIXING_BARREL = TILE_ENTITIES.register("mixing_barrel", () -> TileEntityType.Builder.create(MixingBarrelTile::new, RankineBlocks.MIXING_BARREL.get()).build(null));
    public static final RegistryObject<TileEntityType<AlloyBlockTile>> ALLOY_BLOCK = TILE_ENTITIES.register("alloy_block", () -> TileEntityType.Builder.create(AlloyBlockTile::new, RankineLists.ALLOY_BLOCKS.toArray(new Block[0])).build(null));
    public static final RegistryObject<TileEntityType<TilledSoilTile>> TILLED_SOIL = TILE_ENTITIES.register("tilled_soil", () -> TileEntityType.Builder.create(TilledSoilTile::new, RankineBlocks.TILLED_SOIL.get()).build(null));
    public static final RegistryObject<TileEntityType<GroundTapTile>> GROUND_TAP = TILE_ENTITIES.register("ground_tap", () -> TileEntityType.Builder.create(GroundTapTile::new, RankineBlocks.GROUND_TAP.get()).build(null));
    public static final RegistryObject<TileEntityType<MaterialTestingTableTile>> MATERIAL_TESTING_TABLE = TILE_ENTITIES.register("material_testing_table", () -> TileEntityType.Builder.create(MaterialTestingTableTile::new, RankineBlocks.MATERIAL_TESTING_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<BeehiveOvenTile>> BEEHIVE_OVEN = TILE_ENTITIES.register("beehive_oven", () -> TileEntityType.Builder.create(BeehiveOvenTile::new, RankineBlocks.BEEHIVE_OVEN_PIT.get()).build(null));
    public static final RegistryObject<TileEntityType<DistillationTowerTile>> DISTILLATION_TOWER = TILE_ENTITIES.register("distillation_tower", () -> TileEntityType.Builder.create(DistillationTowerTile::new, RankineBlocks.DISTILLATION_TOWER.get()).build(null));
    public static final RegistryObject<TileEntityType<CharcoalPitTile>> CHARCOAL_PIT = TILE_ENTITIES.register("charcoal_pit", () -> TileEntityType.Builder.create(CharcoalPitTile::new, RankineBlocks.CHARCOAL_PIT.get()).build(null));
    public static final RegistryObject<TileEntityType<ParticleAcceleratorTile>> PARTICLE_ACCELERATOR = TILE_ENTITIES.register("particle_accelerator", () -> TileEntityType.Builder.create(ParticleAcceleratorTile::new, RankineBlocks.PARTICLE_ACCELERATOR.get()).build(null));


    @OnlyIn(Dist.CLIENT)
    public static void registerTileEntityRenders() {
        // tile entities
        ClientRegistry.bindTileEntityRenderer(RANKINE_SIGN.get(), SignTileEntityRenderer::new);
    }
}
