package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.crucible.Crucible;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForge;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeContainer;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusher;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import net.minecraft.block.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {



    @ObjectHolder("rankine:potted_cedar_sapling")
    public static FlowerPotBlock POTTED_CEDAR_SAPLING;

    @ObjectHolder("rankine:potted_pinyon_pine_sapling")
    public static FlowerPotBlock POTTED_PINYON_PINE_SAPLING;

    @ObjectHolder("rankine:potted_coconut_palm_sapling")
    public static FlowerPotBlock POTTED_COCONUT_PALM_SAPLING;

    @ObjectHolder("rankine:potted_juniper_sapling")
    public static FlowerPotBlock POTTED_JUNIPER_SAPLING;

    @ObjectHolder("rankine:potted_balsam_fir_sapling")
    public static FlowerPotBlock POTTED_BALSAM_FIR_SAPLING;

    @ObjectHolder("rankine:swamp_grass")
    public static SwampGrassBlock SWAMP_GRASS;

    @ObjectHolder("rankine:duckweed")
    public static DuckweedBlock DUCKWEED;

    @ObjectHolder("rankine:permafrost")
    public static PermafrostBlock PERMAFROST;

    @ObjectHolder("rankine:vegetated_permafrost")
    public static VegetatedPermafrostBlock VEGETATED_PERMAFROST;

    @ObjectHolder("rankine:opal_ore")
    public static Block OPAL_ORE;

    @ObjectHolder("rankine:ironstone")
    public static Block IRONSTONE;

    @ObjectHolder("rankine:smooth_ironstone")
    public static Block SMOOTH_IRONSTONE;

    @ObjectHolder("rankine:ironstone_bricks")
    public static Block IRONSTONE_BRICKS;

    @ObjectHolder("rankine:muddy_dirt")
    public static Block MUDDY_DIRT;

    @ObjectHolder("rankine:muddy_grass")
    public static Block MUDDY_GRASS;

    @ObjectHolder("rankine:juniper_sapling")
    public static RankineSapling JUNIPER_SAPLING;

    @ObjectHolder("rankine:smooth_andesite")
    public static Block SMOOTH_ANDESITE;

    @ObjectHolder("rankine:smooth_granite")
    public static Block SMOOTH_GRANITE;

    @ObjectHolder("rankine:smooth_diorite")
    public static Block SMOOTH_DIORITE;

    @ObjectHolder("rankine:smooth_rhyolite")
    public static Block SMOOTH_RHYOLITE;

    @ObjectHolder("rankine:smooth_basalt")
    public static Block SMOOTH_BASALT;

    @ObjectHolder("rankine:smooth_gneiss")
    public static Block SMOOTH_GNEISS;

    @ObjectHolder("rankine:smooth_marble")
    public static Block SMOOTH_MARBLE;

    @ObjectHolder("rankine:smooth_limestone")
    public static Block SMOOTH_LIMESTONE;

    @ObjectHolder("rankine:smooth_shale")
    public static Block SMOOTH_SHALE;

    @ObjectHolder("rankine:smooth_peridotite")
    public static Block SMOOTH_PERIDOTITE;

    @ObjectHolder("rankine:smooth_kimberlite")
    public static Block SMOOTH_KIMBERLITE;

    @ObjectHolder("rankine:smooth_komatiite")
    public static Block SMOOTH_KOMATIITE;

    @ObjectHolder("rankine:smooth_ringwoodite")
    public static Block SMOOTH_RINGWOODITE;

    @ObjectHolder("rankine:smooth_wadsleyite")
    public static Block SMOOTH_WADSLEYITE;

    @ObjectHolder("rankine:smooth_ferropericlase")
    public static Block SMOOTH_FERROPERICLASE;

    @ObjectHolder("rankine:smooth_bridgmanite")
    public static Block SMOOTH_BRIDGMANITE;

    @ObjectHolder("rankine:smooth_perovskite")
    public static Block SMOOTH_PEROVSKITE;

    @ObjectHolder("rankine:lignite_block")
    public static Block LIGNITE_BLOCK;

    @ObjectHolder("rankine:bituminous_coal_block")
    public static Block BITUMINOUS_COAL_BLOCK;

    @ObjectHolder("rankine:anthracite_coal_block")
    public static Block ANTHRACITE_COAL_BLOCK;

    @ObjectHolder("rankine:vanadium_block")
    public static Block VANADIUM_BLOCK;

    @ObjectHolder("rankine:beehive_oven_pit")
    public static Block BEEHIVE_OVEN_PIT;

    @ObjectHolder("rankine:limestone_nodule")
    public static Block LIMESTONE_NODULE;

    @ObjectHolder("rankine:stick_block")
    public static Block STICK_BLOCK;

    @ObjectHolder("rankine:sphagnum_moss")
    public static Block SPHAGNUM_MOSS;

    @ObjectHolder("rankine:blasting_powder")
    public static Block BLASTING_POWDER;

    @ObjectHolder("rankine:heart_of_the_mantle")
    public static Block HEART_OF_THE_MANTLE;

    @ObjectHolder("rankine:cedar_planks")
    public static Block CEDAR_PLANKS;

    @ObjectHolder("rankine:balsam_fir_log")
    public static LogBlock BALSAM_FIR_LOG;

    @ObjectHolder("rankine:balsam_fir_wood")
    public static RotatedPillarBlock BALSAM_FIR_WOOD;

    @ObjectHolder("rankine:juniper_wood")
    public static RotatedPillarBlock JUNIPER_WOOD;

    @ObjectHolder("rankine:pinyon_pine_wood")
    public static RotatedPillarBlock PINYON_PINE_WOOD;

    @ObjectHolder("rankine:coconut_palm_wood")
    public static RotatedPillarBlock COCONUT_PALM_WOOD;

    @ObjectHolder("rankine:cedar_log")
    public static LogBlock CEDAR_LOG;

    @ObjectHolder("rankine:stripped_balsam_fir_log")
    public static RotatedPillarBlock STRIPPED_BALSAM_FIR_LOG;

    @ObjectHolder("rankine:stripped_balsam_fir_wood")
    public static RotatedPillarBlock STRIPPED_BALSAM_FIR_WOOD;

    @ObjectHolder("rankine:stripped_juniper_log")
    public static RotatedPillarBlock STRIPPED_JUNIPER_LOG;

    @ObjectHolder("rankine:stripped_juniper_wood")
    public static RotatedPillarBlock STRIPPED_JUNIPER_WOOD;

    @ObjectHolder("rankine:stripped_pinyon_pine_log")
    public static RotatedPillarBlock STRIPPED_PINYON_PINE_LOG;

    @ObjectHolder("rankine:stripped_pinyon_pine_wood")
    public static RotatedPillarBlock STRIPPED_PINYON_PINE_WOOD;

    @ObjectHolder("rankine:stripped_coconut_palm_log")
    public static RotatedPillarBlock STRIPPED_COCONUT_PALM_LOG;

    @ObjectHolder("rankine:stripped_coconut_palm_wood")
    public static RotatedPillarBlock STRIPPED_COCONUT_PALM_WOOD;

    @ObjectHolder("rankine:stripped_cedar_log")
    public static RotatedPillarBlock STRIPPED_CEDAR_LOG;

    @ObjectHolder("rankine:stripped_cedar_wood")
    public static RotatedPillarBlock STRIPPED_CEDAR_WOOD;

    @ObjectHolder("rankine:cedar_leaves")
    public static LeavesBlock CEDAR_LEAVES;

    @ObjectHolder("rankine:balsam_fir_leaves")
    public static LeavesBlock BALSAM_FIR_LEAVES;

    @ObjectHolder("rankine:cedar_door")
    public static DoorBlock CEDAR_DOOR;

    @ObjectHolder("rankine:cedar_trapdoor")
    public static TrapDoorBlock CEDAR_TRAPDOOR;

    @ObjectHolder("rankine:cedar_wood")
    public static RotatedPillarBlock CEDAR_WOOD;

    @ObjectHolder("rankine:cedar_stairs")
    public static StairsBlock CEDAR_STAIRS;

    @ObjectHolder("rankine:cedar_slab")
    public static SlabBlock CEDAR_SLAB;

    @ObjectHolder("rankine:cedar_fence")
    public static FenceBlock CEDAR_FENCE;

    @ObjectHolder("rankine:cedar_fence_gate")
    public static FenceGateBlock CEDAR_FENCE_GATE;

    @ObjectHolder("rankine:cedar_button")
    public static WoodButtonBlock CEDAR_BUTTON;

    @ObjectHolder("rankine:cedar_pressure_plate")
    public static PressurePlateBlock CEDAR_PRESSURE_PLATE;

    @ObjectHolder("rankine:cedar_sapling")
    public static RankineSapling CEDAR_SAPLING;

    @ObjectHolder("rankine:balsam_fir_sapling")
    public static RankineSapling BALSAM_FIR_SAPLING;

    @ObjectHolder("rankine:coconut_palm_planks")
    public static Block COCONUT_PALM_PLANKS;

    @ObjectHolder("rankine:balsam_fir_planks")
    public static Block BALSAM_FIR_PLANKS;

    @ObjectHolder("rankine:juniper_planks")
    public static Block JUNIPER_PLANKS;

    @ObjectHolder("rankine:coconut_palm_sapling")
    public static RankineSapling COCONUT_PALM_SAPLING;

    @ObjectHolder("rankine:coconut_palm_log")
    public static LogBlock COCONUT_PALM_LOG;

    @ObjectHolder("rankine:coconut_palm_leaves") 
    public static LeavesBlock COCONUT_PALM_LEAVES;

    @ObjectHolder("rankine:pinyon_pine_planks")
    public static Block PINYON_PINE_PLANKS;

    @ObjectHolder("rankine:pinyon_pine_log")
    public static LogBlock PINYON_PINE_LOG;

    @ObjectHolder("rankine:pinyon_pine_leaves")
    public static LeavesBlock PINYON_PINE_LEAVES;

    @ObjectHolder("rankine:pinyon_pine_sapling")
    public static RankineSapling PINYON_PINE_SAPLING;

    @ObjectHolder("rankine:juniper_log")
    public static LogBlock JUNIPER_LOG;

    @ObjectHolder("rankine:juniper_leaves")
    public static LeavesBlock JUNIPER_LEAVES;

    @ObjectHolder("rankine:sandy_dirt")
    public static Block SANDY_DIRT;

    @ObjectHolder("rankine:alloy_furnace")
    public static Block ALLOY_FURNACE;

    @ObjectHolder("rankine:wrought_iron_sheetmetal")
    public static Block WROUGHT_IRON_SHEETMETAL;

    @ObjectHolder("rankine:copper_sheetmetal")
    public static Block COPPER_SHEETMETAL;

    @ObjectHolder("rankine:tin_sheetmetal")
    public static Block TIN_SHEETMETAL;

    @ObjectHolder("rankine:bronze_sheetmetal")
    public static Block BRONZE_SHEETMETAL;

    @ObjectHolder("rankine:steel_sheetmetal")
    public static Block STEEL_SHEETMETAL;

    @ObjectHolder("rankine:alloy_furnace")
    public static ContainerType<AlloyFurnaceContainer> ALLOY_FURNACE_CONTAINER;

    @ObjectHolder("rankine:alloy_furnace")
    public static TileEntityType<AlloyFurnaceTile> ALLOY_FURNACE_TILE;

    /*
    @ObjectHolder("rankine:brass_pipe")
    public static PipeBlock BRASS_PIPE;

    @ObjectHolder("rankine:concrete_pipe")
    public static PipeBlock CONCRETE_PIPE;

    @ObjectHolder("rankine:concrete_pipe_junction")
    public static PipeBlock CONCRETE_PIPE_JUNCTION;

    @ObjectHolder("rankine:cast_iron_pipe")
    public static PipeBlock CAST_IRON_PIPE;

    @ObjectHolder("rankine:cast_iron_pipe_junction")
    public static PipeBlock CAST_IRON_PIPE_JUNCTION;

    @ObjectHolder("rankine:cast_iron_beam")
    public static SupportBlock CAST_IRON_BEAM;
     */

    @ObjectHolder("rankine:refractory_bricks")
    public static Block REFRACTORY_BRICKS;

    @ObjectHolder("rankine:magnesium_refractory_bricks")
    public static Block MAGNESIUM_REFRACTORY_BRICKS;

    @ObjectHolder("rankine:limestone")
    public static Block LIMESTONE;

    @ObjectHolder("rankine:limestone_bricks")
    public static Block LIMESTONE_BRICKS;

    @ObjectHolder("rankine:basalt")
    public static Block BASALT;

    @ObjectHolder("rankine:basalt_bricks")
    public static Block BASALT_BRICKS;

    @ObjectHolder("rankine:rhyolite")
    public static Block RHYOLITE;

    @ObjectHolder("rankine:granite")
    public static Block GRANITE;

    @ObjectHolder("rankine:diorite")
    public static Block DIORITE;

    @ObjectHolder("rankine:andesite")
    public static Block ANDESITE;

    @ObjectHolder("rankine:marble")
    public static Block MARBLE;

    @ObjectHolder("rankine:marble_bricks")
    public static Block MARBLE_BRICKS;

    @ObjectHolder("rankine:gneiss")
    public static Block GNEISS;

    @ObjectHolder("rankine:gneiss_bricks")
    public static Block GNEISS_BRICKS;

    @ObjectHolder("rankine:shale")
    public static Block SHALE;

    @ObjectHolder("rankine:niter")
    public static CrystalBlock NITER;

    @ObjectHolder("rankine:gravel_concrete")
    public static EntityModifierBlock GRAVEL_CONCRETE;

    @ObjectHolder("rankine:shale_bricks")
    public static Block SHALE_BRICKS;

    @ObjectHolder("rankine:clay_bricks")
    public static Block CLAY_BRICKS;


    @ObjectHolder("rankine:andesite_bricks")
    public static Block ANDESITE_BRICKS;

    @ObjectHolder("rankine:granite_bricks")
    public static Block GRANITE_BRICKS;

    @ObjectHolder("rankine:diorite_bricks")
    public static Block DIORITE_BRICKS;

    @ObjectHolder("rankine:bauxite_ore")
    public static RankineOre BAUXITE_ORE;

    @ObjectHolder("rankine:malachite_ore")
    public static RankineOre MALACHITE_ORE;

    @ObjectHolder("rankine:cassiterite_ore")
    public static RankineOre CASSITERITE_ORE;

    @ObjectHolder("rankine:sphalerite_ore")
    public static RankineOre SPHALERITE_ORE;

    @ObjectHolder("rankine:cinnabar_ore")
    public static RankineOre CINNABAR_ORE;

    @ObjectHolder("rankine:magnesite_ore")
    public static RankineOre MAGNESITE_ORE;

    @ObjectHolder("rankine:magnetite_ore")
    public static RankineOre MAGNETITE_ORE;

    @ObjectHolder("rankine:ilmenite_ore")
    public static RankineOre ILMENITE_ORE;

    @ObjectHolder("rankine:galena_ore")
    public static RankineOre GALENA_ORE;

    @ObjectHolder("rankine:acanthite_ore")
    public static RankineOre ACANTHITE_ORE;

    @ObjectHolder("rankine:molybdenite_ore")
    public static RankineOre MOLYBDENITE_ORE;

    @ObjectHolder("rankine:pyrolusite_ore")
    public static RankineOre PYROLUSITE_ORE;

    @ObjectHolder("rankine:chromite_ore")
    public static RankineOre CHROMITE_ORE;

    @ObjectHolder("rankine:columbite_ore")
    public static RankineOre COLUMBITE_ORE;

    @ObjectHolder("rankine:tantalite_ore")
    public static RankineOre TANTALITE_ORE;

    @ObjectHolder("rankine:wolframite_ore")
    public static RankineOre WOLFRAMITE_ORE;

    @ObjectHolder("rankine:bismite_ore")
    public static RankineOre BISMITE_ORE;

    @ObjectHolder("rankine:pentlandite_ore")
    public static RankineOre PENTLANDITE_ORE;

    @ObjectHolder("rankine:native_copper_ore")
    public static RankineOre NATIVE_COPPER_ORE;

    @ObjectHolder("rankine:native_tin_ore")
    public static RankineOre NATIVE_TIN_ORE;

    @ObjectHolder("rankine:lignite_ore")
    public static RankineOre LIGNITE_ORE;

    @ObjectHolder("rankine:subbituminous_ore")
    public static RankineOre SUBBITUMINOUS_ORE;

    @ObjectHolder("rankine:bituminous_ore")
    public static RankineOre BITUMINOUS_ORE;

    @ObjectHolder("rankine:vanadinite_ore")
    public static Block VANADINITE_ORE;

    @ObjectHolder("rankine:anthracite_ore")
    public static RankineOre ANTHRACITE_ORE;

    @ObjectHolder("rankine:meteoric_iron_ore")
    public static Block METEORIC_IRON_ORE;

    @ObjectHolder("rankine:native_gold_ore")
    public static RankineOre NATIVE_GOLD_ORE;

    @ObjectHolder("rankine:diamond_ore")
    public static RankineOre DIAMOND_ORE;

    @ObjectHolder("rankine:emerald_ore")
    public static RankineOre EMERALD_ORE;

    @ObjectHolder("rankine:redstone_ore")
    public static RankineOre REDSTONE_ORE;

    @ObjectHolder("rankine:lazurite_ore")
    public static RankineOre LAZURITE_ORE;

    @ObjectHolder("rankine:plumbago_ore")
    public static RankineOre PLUMBAGO_ORE;

    @ObjectHolder("rankine:moissanite_ore")
    public static RankineOre MOISSANITE_ORE;

    @ObjectHolder("rankine:sperrylite_ore")
    public static RankineOre SPERRYLITE_ORE;

    @ObjectHolder("rankine:piston_crusher")
    public static PistonCrusher PISTON_CRUSHER;

    @ObjectHolder("rankine:piston_crusher")
    public static ContainerType<PistonCrusherContainer> PISTON_CRUSHER_CONTAINER;

    @ObjectHolder("rankine:piston_crusher")
    public static TileEntityType<PistonCrusherTile> PISTON_CRUSHER_TILE;

    @ObjectHolder("rankine:crucible")
    public static Crucible CRUCIBLE;

    @ObjectHolder("rankine:finery_forge")
    public static FineryForge FINERY_FORGE;

    @ObjectHolder("rankine:finery_forge")
    public static ContainerType<FineryForgeContainer> FINERY_FORGE_CONTAINER;

    @ObjectHolder("rankine:finery_forge")
    public static TileEntityType<FineryForgeTile> FINERY_FORGE_TILE;

/*
    @ObjectHolder("rankine:induction_furnace")
    public static InductionFurnace INDUCTION_FURNACE;

    @ObjectHolder("rankine:induction_furnace")
    public static ContainerType<InductionFurnaceContainer> INDUCTION_FURNACE_CONTAINER;

    @ObjectHolder("rankine:induction_furnace")
    public static TileEntityType<InductionFurnaceTile> INDUCTION_FURNACE_TILE;

    @ObjectHolder("rankine:piston_steam_engine")
    public static PistonSteamEngine PISTON_STEAM_ENGINE;

    @ObjectHolder("rankine:piston_steam_engine")
    public static ContainerType<PistonSteamEngineContainer> PISTON_STEAM_ENGINE_CONTAINER;

    @ObjectHolder("rankine:piston_steam_engine")
    public static TileEntityType<PistonSteamEngineTile> PISTON_STEAM_ENGINE_TILE;

    @ObjectHolder("rankine:steam_turbine")
    public static SteamTurbine STEAM_TURBINE;

    @ObjectHolder("rankine:steam_turbine")
    public static ContainerType<SteamTurbineContainer> STEAM_TURBINE_CONTAINER;

    @ObjectHolder("rankine:steam_turbine")
    public static TileEntityType<SteamTurbineTile> STEAM_TURBINE_TILE;

     */

    @ObjectHolder("rankine:rose_gold_block")
    public static Block ROSE_GOLD_BLOCK;

    @ObjectHolder("rankine:white_gold_block")
    public static Block WHITE_GOLD_BLOCK;

    @ObjectHolder("rankine:green_gold_block")
    public static Block GREEN_GOLD_BLOCK;

    @ObjectHolder("rankine:blue_gold_block")
    public static Block BLUE_GOLD_BLOCK;

    @ObjectHolder("rankine:purple_gold_block")
    public static Block PURPLE_GOLD_BLOCK;

    @ObjectHolder("rankine:platinum_block")
    public static Block PLATINUM_BLOCK;


    @ObjectHolder("rankine:osmium_block")
    public static Block OSMIUM_BLOCK;


    @ObjectHolder("rankine:iridium_block")
    public static Block IRIDIUM_BLOCK;


    @ObjectHolder("rankine:pig_iron_block")
    public static Block PIG_IRON_BLOCK;

    @ObjectHolder("rankine:wrought_iron_block")
    public static Block WROUGHT_IRON_BLOCK;

    @ObjectHolder("rankine:cast_iron_block")
    public static Block CAST_IRON_BLOCK;

    @ObjectHolder("rankine:chromium_block")
    public static Block CHROMIUM_BLOCK;

    @ObjectHolder("rankine:titanium_block")
    public static Block TITANIUM_BLOCK;

    @ObjectHolder("rankine:silver_block")
    public static Block SILVER_BLOCK;

    @ObjectHolder("rankine:steel_block")
    public static Block STEEL_BLOCK;

    @ObjectHolder("rankine:stainless_steel_block")
    public static Block STAINLESS_STEEL_BLOCK;

    @ObjectHolder("rankine:nitinol_block")
    public static Block NITINOL_BLOCK;

    @ObjectHolder("rankine:duralumin_block")
    public static Block DURALUMIN_BLOCK;

    @ObjectHolder("rankine:brass_block")
    public static Block BRASS_BLOCK;

    @ObjectHolder("rankine:amalgam_block")
    public static Block AMALGAM_BLOCK;

    @ObjectHolder("rankine:cupronickel_block")
    public static Block CUPRONICKEL_BLOCK;

    @ObjectHolder("rankine:nickel_silver_block")
    public static Block NICKEL_SILVER_BLOCK;

    @ObjectHolder("rankine:nichrome_block")
    public static Block NICHROME_BLOCK;

    @ObjectHolder("rankine:alnico_block")
    public static Block ALNICO_BLOCK;

    @ObjectHolder("rankine:copper_block")
    public static Block COPPER_BLOCK;

    @ObjectHolder("rankine:tin_block")
    public static Block TIN_BLOCK;

    @ObjectHolder("rankine:nickel_block")
    public static Block NICKEL_BLOCK;

    @ObjectHolder("rankine:magnesium_block")
    public static Block MAGNESIUM_BLOCK;

    @ObjectHolder("rankine:molybdenum_block")
    public static Block MOLYBDENUM_BLOCK;

    @ObjectHolder("rankine:zinc_block")
    public static Block ZINC_BLOCK;

    @ObjectHolder("rankine:cobalt_block")
    public static Block COBALT_BLOCK;

    @ObjectHolder("rankine:invar_block")
    public static Block INVAR_BLOCK;

    @ObjectHolder("rankine:tungsten_block")
    public static Block TUNGSTEN_BLOCK;

    @ObjectHolder("rankine:bronze_block")
    public static Block BRONZE_BLOCK;

    @ObjectHolder("rankine:aluminum_bronze_block")
    public static Block ALUMINUM_BRONZE_BLOCK;

    @ObjectHolder("rankine:manganese_block")
    public static Block MANGANESE_BLOCK;

    @ObjectHolder("rankine:niobium_block")
    public static Block NIOBIUM_BLOCK;

    @ObjectHolder("rankine:aluminum_block")
    public static Block ALUMINUM_BLOCK;

    @ObjectHolder("rankine:lead_block")
    public static Block LEAD_BLOCK;

    @ObjectHolder("rankine:magnalium_block")
    public static Block MAGNALIUM_BLOCK;

    @ObjectHolder("rankine:tantalum_block")
    public static Block TANTALUM_BLOCK;

    @ObjectHolder("rankine:graphite_block")
    public static Block GRAPHITE_BLOCK;

    @ObjectHolder("rankine:bismuth_block")
    public static Block BISMUTH_BLOCK;

    @ObjectHolder("rankine:opal_block")
    public static Block OPAL_BLOCK;

    @ObjectHolder("rankine:garnet_block")
    public static Block GARNET_BLOCK;

    @ObjectHolder("rankine:olivine_block")
    public static Block OLIVINE_BLOCK;

    @ObjectHolder("rankine:feldspar_block")
    public static Block FELDSPAR_BLOCK;

    @ObjectHolder("rankine:dolomite_block")
    public static Block DOLOMITE_BLOCK;

    @ObjectHolder("rankine:calcite_block")
    public static RankineTransparent CALCITE_BLOCK;

    @ObjectHolder("rankine:salt_block")
    public static RankineTransparent SALT_BLOCK;

    @ObjectHolder("rankine:pyroxene_block")
    public static Block PYROXENE_BLOCK;

    @ObjectHolder("rankine:sulfur_block")
    public static Block SULFUR_BLOCK;

    @ObjectHolder("rankine:coke_block")
    public static Block COKE_BLOCK;

    @ObjectHolder("rankine:inconel_block")
    public static Block INCONEL_BLOCK;

    @ObjectHolder("rankine:rose_metal_block")
    public static Block ROSE_METAL_BLOCK;

    @ObjectHolder("rankine:calcium_silicate_block")
    public static Block CALCIUM_SILICATE_BLOCK;

    @ObjectHolder("rankine:silicon_block")
    public static Block SILICON_BLOCK;

    @ObjectHolder("rankine:silicon_carbide_block")
    public static Block SILICON_CARBIDE_BLOCK;

    @ObjectHolder("rankine:peridot_block")
    public static Block PERIDOT_BLOCK;

    @ObjectHolder("rankine:rope")
    public static RopeBlock ROPE;


    @ObjectHolder("rankine:peridotite")
    public static Block PERIDOTITE;

    @ObjectHolder("rankine:ringwoodite")
    public static Block RINGWOODITE;

    @ObjectHolder("rankine:wadsleyite")
    public static Block WADSLEYITE;

    @ObjectHolder("rankine:bridgmanite")
    public static Block BRIDGMANITE;

    @ObjectHolder("rankine:komatiite")
    public static Block KOMATIITE;

    @ObjectHolder("rankine:kimberlite")
    public static Block KIMBERLITE;

    @ObjectHolder("rankine:ferropericlase")
    public static Block FERROPERICLASE;

    @ObjectHolder("rankine:perovskite")
    public static Block PEROVSKITE;

    @ObjectHolder("rankine:aluminum_ladder")
    public static MetalLadder ALUMINUM_LADDER;

    @ObjectHolder("rankine:liquid_pig_iron_block")
    public static FlowingFluidBlock LIQUID_PIG_IRON_BLOCK;

    @ObjectHolder("rankine:elderberry_bush")
    public static RankineBerryBushBlock ELDERBERRY_BUSH;

    @ObjectHolder("rankine:snowberry_bush")
    public static RankineBerryBushBlock SNOWBERRY_BUSH;

    @ObjectHolder("rankine:banana_yucca_bush")
    public static RankineBerryBushBlock BANANA_YUCCA_BUSH;
}
