package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceBlock;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlock;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import com.cannolicatfish.rankine.blocks.asphalt.*;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerBlock;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerContainer;
import com.cannolicatfish.rankine.blocks.batterycharger.BatteryChargerTile;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
import com.cannolicatfish.rankine.blocks.block_groups.*;
import com.cannolicatfish.rankine.blocks.buildingmodes.*;
import com.cannolicatfish.rankine.blocks.cauldrons.*;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleBlock;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleContainer;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleTile;
import com.cannolicatfish.rankine.blocks.dac.DiamondAnvilCellBlock;
import com.cannolicatfish.rankine.blocks.distillationtower.DistillationTowerBlock;
import com.cannolicatfish.rankine.blocks.distillationtower.DistillationTowerTile;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerBlock;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerContainer;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceBlock;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerBlock;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerContainer;
import com.cannolicatfish.rankine.blocks.gasbottler.GasBottlerTile;
import com.cannolicatfish.rankine.blocks.gases.*;
import com.cannolicatfish.rankine.blocks.gasvent.GasVentBlock;
import com.cannolicatfish.rankine.blocks.gasvent.GasVentTile;
import com.cannolicatfish.rankine.blocks.groundtap.GroundTapBlock;
import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceBlock;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceContainer;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelBlock;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelContainer;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableBlock;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableContainer;
import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableTile;
import com.cannolicatfish.rankine.blocks.particleaccelerator.ParticleAcceleratorBlock;
import com.cannolicatfish.rankine.blocks.particleaccelerator.ParticleAcceleratorTile;
import com.cannolicatfish.rankine.blocks.pedestal.PedestalBlock;
import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.cannolicatfish.rankine.blocks.plants.*;
import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanBlock;
import com.cannolicatfish.rankine.blocks.sedimentfan.SedimentFanTile;
import com.cannolicatfish.rankine.blocks.tap.TapLineBlock;
import com.cannolicatfish.rankine.blocks.tap.TreeTapBlock;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableBlock;
import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilTile;
import com.cannolicatfish.rankine.fluids.CarbonDisulfideFlowingFluidBlock;
import com.cannolicatfish.rankine.fluids.MercuryFlowingFluidBlock;
import com.cannolicatfish.rankine.fluids.RankineFlowingFluidBlock;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import com.cannolicatfish.rankine.world.grower.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class RankineBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProjectRankine.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProjectRankine.MODID);

    //Base Properties
    public static Block.Properties IGNEOUS_STONE_BRICKS = Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F * Config.BLOCK_PROPERTIES.BRICKS_HARDNESS_MULT.get().floatValue(), 6.0F * Config.BLOCK_PROPERTIES.BRICKS_RESISTANCE_MULT.get().floatValue());

    public static Block.Properties DEF_STONE = Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F);
    public static Block.Properties DEF_ORE = Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F);
    public static Block.Properties DEF_METAL_BLOCK = Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F);

    //Other Stones
    public static final DeferredBlock<Block> GRANITE_BRICKS = BLOCKS.register("granite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final DeferredBlock<Block> GRANITE_BRICKS_SLAB = BLOCKS.register("granite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS.get())));
    public static final DeferredBlock<Block> GRANITE_BRICKS_STAIRS = BLOCKS.register("granite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS.get())));
    public static final DeferredBlock<Block> GRANITE_BRICKS_WALL = BLOCKS.register("granite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS.get())));
    public static final DeferredBlock<Block> GRANITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("granite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final DeferredBlock<Block> DIORITE_BRICKS = BLOCKS.register("diorite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final DeferredBlock<Block> DIORITE_BRICKS_SLAB = BLOCKS.register("diorite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS.get())));
    public static final DeferredBlock<Block> DIORITE_BRICKS_STAIRS = BLOCKS.register("diorite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS.get())));
    public static final DeferredBlock<Block> DIORITE_BRICKS_WALL = BLOCKS.register("diorite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS.get())));
    public static final DeferredBlock<Block> DIORITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("diorite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final DeferredBlock<Block> ANDESITE_BRICKS = BLOCKS.register("andesite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final DeferredBlock<Block> ANDESITE_BRICKS_SLAB = BLOCKS.register("andesite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS.get())));
    public static final DeferredBlock<Block> ANDESITE_BRICKS_STAIRS = BLOCKS.register("andesite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS.get())));
    public static final DeferredBlock<Block> ANDESITE_BRICKS_WALL = BLOCKS.register("andesite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(ANDESITE_BRICKS.get())));
    public static final DeferredBlock<Block> ANDESITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("andesite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final DeferredBlock<Block> BASALT_BRICKS = BLOCKS.register("basalt_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final DeferredBlock<Block> BASALT_BRICKS_SLAB = BLOCKS.register("basalt_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(BASALT_BRICKS.get())));
    public static final DeferredBlock<Block> BASALT_BRICKS_STAIRS = BLOCKS.register("basalt_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(BASALT_BRICKS.get())));
    public static final DeferredBlock<Block> BASALT_BRICKS_WALL = BLOCKS.register("basalt_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(BASALT_BRICKS.get())));
    public static final DeferredBlock<Block> BASALT_BRICKS_PRESSURE_PLATE = BLOCKS.register("basalt_bricks_pressure_plate", RankineStonePressurePlate::new);


    //STONES
    public static final RankineStone PEGMATITE = RankineStone.newStoneReg("pegmatite", "igneous");
    public static final RankineStone GRAY_GRANITE = RankineStone.newStoneReg("gray_granite", "igneous");
    public static final RankineStone RHYOLITE = RankineStone.newStoneReg("rhyolite", "igneous");
    public static final RankineStone COMENDITE = RankineStone.newStoneReg("comendite", "igneous");
    public static final RankineStone EPISYENITE = RankineStone.newStoneReg("episyenite", "igneous");
    public static final RankineStone NEPHELINE_SYENITE = RankineStone.newStoneReg("nepheline_syenite", "igneous");
    public static final RankineStone PHONOLITE = RankineStone.newStoneReg("phonolite", "igneous");
    public static final RankineStone GRANODIORITE = RankineStone.newStoneReg("granodiorite", "igneous");
    public static final RankineStone RED_PORPHYRY = RankineStone.newStoneReg("red_porphyry", "igneous");
    public static final RankineStone PURPLE_PORPHYRY = RankineStone.newStoneReg("purple_porphyry", "igneous");
    public static final RankineStone BLACK_DACITE = RankineStone.newStoneReg("black_dacite", "igneous");
    public static final RankineStone RED_DACITE = RankineStone.newStoneReg("red_dacite", "igneous");
    public static final RankineStone HORNBLENDE_ANDESITE = RankineStone.newStoneReg("hornblende_andesite", "igneous");
    public static final RankineStone SHONKINITE = RankineStone.newStoneReg("shonkinite", "igneous");
    public static final RankineStone ANORTHOSITE = RankineStone.newStoneReg("anorthosite", "igneous");
    public static final RankineStone NORITE = RankineStone.newStoneReg("norite", "igneous");
    public static final RankineStone TROCTOLITE = RankineStone.newStoneReg("troctolite", "igneous");
    public static final RankineStone GABBRO = RankineStone.newStoneReg("gabbro", "igneous");
    public static final RankineStone DIABASE = RankineStone.newStoneReg("diabase", "igneous");
    public static final RankineStone THOLEIITIC_BASALT = RankineStone.newStoneReg("tholeiitic_basalt", "igneous");
    public static final RankineStone DUNITE = RankineStone.newStoneReg("dunite", "igneous");
    public static final RankineStone HARZBURGITE = RankineStone.newStoneReg("harzburgite", "igneous");
    public static final RankineStone LHERZOLITE = RankineStone.newStoneReg("lherzolite", "igneous");
    public static final RankineStone WEHRLITE = RankineStone.newStoneReg("wehrlite", "igneous");
    public static final RankineStone PYROXENITE = RankineStone.newStoneReg("pyroxenite", "igneous");
    public static final RankineStone KOMATIITE = RankineStone.newStoneReg("komatiite", "igneous");
    public static final RankineStone KIMBERLITE = RankineStone.newStoneReg("kimberlite", "igneous");
    public static final RankineStone SOMMANITE = RankineStone.newStoneReg("sommanite", "igneous");
    public static final RankineStone RINGWOODINE = RankineStone.newStoneReg("ringwoodine", "igneous");
    public static final RankineStone WADSLEYONE = RankineStone.newStoneReg("wadsleyone", "igneous");
    public static final RankineStone BRIDGMANHAM = RankineStone.newStoneReg("bridgmanham", "igneous");
    public static final RankineStone POST_PEROVSKITE = RankineStone.newStoneReg("post_perovskite", "igneous");
    public static final RankineStone BLACK_MARBLE = RankineStone.newStoneReg("black_marble", "metamorphic");
    public static final RankineStone GRAY_MARBLE = RankineStone.newStoneReg("gray_marble", "metamorphic");
    public static final RankineStone WHITE_MARBLE = RankineStone.newStoneReg("white_marble", "metamorphic");
    public static final RankineStone ROSE_MARBLE = RankineStone.newStoneReg("rose_marble", "metamorphic");
    public static final RankineStone SLATE = RankineStone.newStoneReg("slate", "metamorphic");
    public static final RankineStone PHYLLITE = RankineStone.newStoneReg("phyllite", "metamorphic");
    public static final RankineStone MICA_SCHIST = RankineStone.newStoneReg("mica_schist", "metamorphic");
    public static final RankineStone BLUESCHIST = RankineStone.newStoneReg("blueschist", "metamorphic");
    public static final RankineStone GREENSCHIST = RankineStone.newStoneReg("greenschist", "metamorphic");
    public static final RankineStone WHITESCHIST = RankineStone.newStoneReg("whiteschist", "metamorphic");
    public static final RankineStone GNEISS = RankineStone.newStoneReg("gneiss", "metamorphic");
    public static final RankineStone QUARTZITE = RankineStone.newStoneReg("quartzite", "metamorphic");
    public static final RankineStone SERPENTINITE = RankineStone.newStoneReg("serpentinite", "metamorphic");
    public static final RankineStone MARIPOSITE = RankineStone.newStoneReg("mariposite", "metamorphic");
    public static final RankineStone ECLOGITE = RankineStone.newStoneReg("eclogite", "igneous");
    public static final RankineStone LIMESTONE = RankineStone.newStoneReg("limestone", "sedimentary");
    public static final RankineStone DOLOSTONE = RankineStone.newStoneReg("dolostone", "sedimentary");
    public static final RankineStone CHALK = RankineStone.newStoneReg("chalk", "sedimentary");
    public static final RankineStone MARLSTONE = RankineStone.newStoneReg("marlstone", "sedimentary");
    public static final RankineStone SOAPSTONE = RankineStone.newStoneReg("soapstone", "sedimentary");
    public static final RankineStone SHALE = RankineStone.newStoneReg("shale", "sedimentary");
    public static final RankineStone MUDSTONE = RankineStone.newStoneReg("mudstone", "sedimentary");
    public static final RankineStone SILTSTONE = RankineStone.newStoneReg("siltstone", "sedimentary");
    public static final RankineStone ITACOLUMITE = RankineStone.newStoneReg("itacolumite", "sedimentary");
    public static final RankineStone ARKOSE = RankineStone.newStoneReg("arkose", "sedimentary");
    public static final RankineStone GRAYWACKE = RankineStone.newStoneReg("graywacke", "sedimentary");
    public static final RankineStone HONEYSTONE = RankineStone.newStoneReg("honeystone", "sedimentary");

    //WOODS
    public static final RankineWood CEDAR = RankineWood.newWoodReg("cedar", 2.0f,true, true, 2, new CedarTreeGrower());
    public static final RankineWood BALSAM_FIR = RankineWood.newWoodReg("balsam_fir", 2.0f,true,true, 3, new BalsamFirTreeGrower());
    public static final RankineWood EASTERN_HEMLOCK = RankineWood.newWoodReg("eastern_hemlock", 2.0f,true, true, 3, new EasternHemlockTreeGrower());
    public static final RankineWood WESTERN_HEMLOCK = RankineWood.newWoodReg("western_hemlock", 2.0f,true, true, 3, new WesternHemlockTreeGrower());
    public static final RankineWood PINYON_PINE = RankineWood.newWoodReg("pinyon_pine", 2.0f,true, true, 2, new PinyonPineTreeGrower());
    public static final RankineWood JUNIPER = RankineWood.newWoodReg("juniper", 2.0f,true, true, 1, new JuniperTreeGrower());
    public static final RankineWood BLACK_BIRCH = RankineWood.newWoodReg("black_birch", 2.0f,true, true, 2, new BlackBirchTreeGrower());
    public static final RankineWood YELLOW_BIRCH = RankineWood.newWoodReg("yellow_birch", 2.0f,true, true, 2, new YellowBirchTreeGrower());
    public static final RankineWood RED_BIRCH = RankineWood.newWoodReg("red_birch", 2.0f,true, true, 1, new RedBirchTreeGrower());
    public static final RankineWood MAPLE = RankineWood.newWoodReg("maple", 2.5f,true, true, 1, new MapleTreeGrower());
    public static final RankineWood MAGNOLIA = RankineWood.newWoodReg("magnolia", 2.0f,true, true, 1, new MagnoliaTreeGrower());
    public static final RankineWood BLACK_WALNUT = RankineWood.newWoodReg("black_walnut", 2.5f,true, true, 1, new BlackWalnutTreeGrower());
    public static final RankineWood COCONUT_PALM = RankineWood.newWoodReg("coconut_palm", 1.5f,true, true, 1, new CoconutPalmTreeGrower());
    public static final RankineWood CORK_OAK = RankineWood.newWoodReg("cork_oak", 2.0f,true, true, 1, new CorkOakTreeGrower());
    public static final RankineWood SHARINGA = RankineWood.newWoodReg("sharinga", 2.0f,true, true, 1, new SharingaTreeGrower());
    public static final RankineWood CINNAMON = RankineWood.newWoodReg("cinnamon", 2.0f,true, true, 1, new CinnamonTreeGrower());
    public static final RankineWood HONEY_LOCUST = RankineWood.newWoodReg("honey_locust", 3.0f,true, true, 1, new HoneyLocustTreeGrower());
    public static final RankineWood WEEPING_WILLOW = RankineWood.newWoodReg("weeping_willow", 2.0f,true, true, 1, new WeepingWillowTreeGrower());
    public static final RankineWood ERYTHRINA = RankineWood.newWoodReg("erythrina", 2.0f,true, true, 1, new ErythrinaTreeGrower());
    public static final RankineWood PETRIFIED_CHORUS = RankineWood.newWoodReg("petrified_chorus", 2.0f,true, false, 1, null);
    public static final RankineWood CHARRED = RankineWood.newWoodReg("charred", 1.0f,true, false, 1, null);
    public static final RankineWood BAMBOO = RankineWood.newWoodReg("bamboo", 2.0f,false, false, 1, null);
    public static final RankineWood BAMBOO_CULMS = RankineWood.newWoodReg("bamboo_culms", 2.0f,false, false, 1, null);

    //SANDSTONES
    public static final RankineSandstone DESERT_SANDSTONE = RankineSandstone.newSandstoneReg("desert_sandstone");
    public static final RankineSandstone WHITE_SANDSTONE = RankineSandstone.newSandstoneReg("white_sandstone");
    public static final RankineSandstone BLACK_SANDSTONE = RankineSandstone.newSandstoneReg("black_sandstone");
    public static final RankineSandstone SOUL_SANDSTONE = RankineSandstone.newSandstoneReg("soul_sandstone");

    //CEMENTS
    public static final RankineCement WHITE_CEMENT = RankineCement.newCementReg("white_cement", DyeColor.WHITE);
    public static final RankineCement LIGHT_GRAY_CEMENT = RankineCement.newCementReg("light_gray_cement", DyeColor.LIGHT_GRAY);
    public static final RankineCement GRAY_CEMENT = RankineCement.newCementReg("gray_cement", DyeColor.GRAY);
    public static final RankineCement BLACK_CEMENT = RankineCement.newCementReg("black_cement", DyeColor.BLACK);
    public static final RankineCement BROWN_CEMENT = RankineCement.newCementReg("brown_cement", DyeColor.BROWN);
    public static final RankineCement RED_CEMENT = RankineCement.newCementReg("red_cement", DyeColor.RED);
    public static final RankineCement ORANGE_CEMENT = RankineCement.newCementReg("orange_cement", DyeColor.ORANGE);
    public static final RankineCement YELLOW_CEMENT = RankineCement.newCementReg("yellow_cement", DyeColor.YELLOW);
    public static final RankineCement LIME_CEMENT = RankineCement.newCementReg("lime_cement", DyeColor.LIME);
    public static final RankineCement GREEN_CEMENT = RankineCement.newCementReg("green_cement", DyeColor.GREEN);
    public static final RankineCement CYAN_CEMENT = RankineCement.newCementReg("cyan_cement", DyeColor.CYAN);
    public static final RankineCement LIGHT_BLUE_CEMENT = RankineCement.newCementReg("light_blue_cement", DyeColor.LIGHT_BLUE);
    public static final RankineCement BLUE_CEMENT = RankineCement.newCementReg("blue_cement", DyeColor.BLUE);
    public static final RankineCement MAGENTA_CEMENT = RankineCement.newCementReg("magenta_cement", DyeColor.MAGENTA);
    public static final RankineCement PURPLE_CEMENT = RankineCement.newCementReg("purple_cement", DyeColor.PURPLE);
    public static final RankineCement PINK_CEMENT = RankineCement.newCementReg("pink_cement", DyeColor.PINK);
    public static final RankineCement CONCRETE = RankineCement.newCementReg("concrete", DyeColor.BROWN);
    public static final RankineCement ROMAN_CONCRETE = RankineCement.newCementReg("roman_concrete", DyeColor.BROWN);
    public static final RankineCement POLISHED_ROMAN_CONCRETE = RankineCement.newCementReg("polished_roman_concrete", DyeColor.BROWN);
    public static final RankineCement ROMAN_CONCRETE_BRICKS = RankineCement.newCementReg("roman_concrete_bricks", DyeColor.BROWN);

    //BRICKS
    public static final RankineBricks CLAY_BRICKS = RankineBricks.newBricksReg("clay_bricks", MapColor.CLAY);
    public static final RankineBricks KAOLINITE_CLAY_BRICKS = RankineBricks.newBricksReg("kaolinite_bricks", MapColor.TERRACOTTA_WHITE);
    public static final RankineBricks HALLOYSITE_BRICKS = RankineBricks.newBricksReg("halloysite_bricks", MapColor.TERRACOTTA_YELLOW);
    public static final RankineBricks PORCELAIN_CLAY_BRICKS = RankineBricks.newBricksReg("fire_clay_bricks", MapColor.TERRACOTTA_WHITE);
    public static final RankineBricks FIRE_CLAY_BRICKS = RankineBricks.newBricksReg("porcelain_clay_bricks", MapColor.COLOR_BROWN);
    public static final RankineBricks REFRACTORY_BRICKS = RankineBricks.newBricksReg("refractory_bricks", MapColor.TERRACOTTA_BROWN);
    public static final RankineBricks HIGH_REFRACTORY_BRICKS = RankineBricks.newBricksReg("high_refractory_bricks", MapColor.TERRACOTTA_GRAY);
    public static final RankineBricks ULTRA_HIGH_REFRACTORY_BRICKS = RankineBricks.newBricksReg("ultra_high_refractory_bricks", MapColor.TERRACOTTA_CYAN);

    //DRIPSTONES
    public static final RankineDripstone FERRIC_DRIPSTONE = RankineDripstone.newDripstoneReg("ferric_dripstone", MapColor.STONE);
    public static final RankineDripstone MAGNESITIC_DRIPSTONE = RankineDripstone.newDripstoneReg("magnesitic_dripstone", MapColor.STONE);
    public static final RankineDripstone ZIRCONIC_DRIPSTONE = RankineDripstone.newDripstoneReg("zirconic_dripstone", MapColor.STONE);
    public static final RankineDripstone GYPSIC_DRIPSTONE = RankineDripstone.newDripstoneReg("gypsic_dripstone", MapColor.STONE);
    public static final RankineDripstone HALITIC_DRIPSTONE = RankineDripstone.newDripstoneReg("halitic_dripstone", MapColor.STONE);
    public static final RankineDripstone NITRIC_DRIPSTONE = RankineDripstone.newDripstoneReg("nitric_dripstone", MapColor.STONE);
    public static final RankineDripstone BORACITIC_DRIPSTONE = RankineDripstone.newDripstoneReg("boracitic_dripstone", MapColor.STONE);

    public static final DeferredBlock<Block> PACKED_SNOW = BLOCKS.register("packed_snow", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(2.0F).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
    public static final DeferredBlock<Block> PACKED_SNOW_SLAB = BLOCKS.register("packed_snow_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW.get())));
    public static final DeferredBlock<Block> PACKED_SNOW_STAIRS = BLOCKS.register("packed_snow_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW.get())));
    public static final DeferredBlock<Block> PACKED_SNOW_WALL = BLOCKS.register("packed_snow_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW.get())));
    public static final DeferredBlock<Block> ICE_BRICKS = BLOCKS.register("ice_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).friction(0.98F).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> ICE_BRICKS_SLAB = BLOCKS.register("ice_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.ofFullCopy(ICE_BRICKS.get())));
    public static final DeferredBlock<Block> ICE_BRICKS_STAIRS = BLOCKS.register("ice_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.ofFullCopy(ICE_BRICKS.get())));
    public static final DeferredBlock<Block> ICE_BRICKS_WALL = BLOCKS.register("ice_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.ofFullCopy(ICE_BRICKS.get())));

    public static final DeferredBlock<Block> BRECCIA = BLOCKS.register("breccia", () -> new Block(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> BRECCIA_SLAB = BLOCKS.register("breccia_slab", () -> new RankineSlabBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> BRECCIA_STAIRS = BLOCKS.register("breccia_stairs", () -> new RankineStairsBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> BRECCIA_WALL = BLOCKS.register("breccia_wall", () -> new RankineWallBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> SKARN = BLOCKS.register("skarn", () -> new Block(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> SKARN_SLAB = BLOCKS.register("skarn_slab", () -> new RankineSlabBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> SKARN_STAIRS = BLOCKS.register("skarn_stairs", () -> new RankineStairsBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> SKARN_WALL = BLOCKS.register("skarn_wall", () -> new RankineWallBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> PUMICE = BLOCKS.register("pumice", () -> new Block(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));
    public static final DeferredBlock<Block> SCORIA = BLOCKS.register("scoria", () -> new Block(Block.Properties.ofFullCopy(Blocks.COBBLESTONE)));


    //weird mineral blocks
    public static final DeferredBlock<Block> MELLITE_BLOCK = BLOCKS.register("mellite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> MONTROYDITE_BLOCK = BLOCKS.register("montroydite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RINGWOODITE_BLOCK = BLOCKS.register("ringwoodite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> WADSLEYITE_BLOCK = BLOCKS.register("wadsleyite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> FORSTERITE_BLOCK = BLOCKS.register("forsterite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SPINEL_BLOCK = BLOCKS.register("spinel_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> BRIDGMANITE_BLOCK = BLOCKS.register("bridgmanite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> FERROPERICLASE_BLOCK = BLOCKS.register("ferropericlase_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PEROVSKITE_BLOCK = BLOCKS.register("perovskite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));

    //public static final DeferredBlock<Block> FIBER_BED = BLOCKS.register("fiber_bed", () -> new FiberBedBlock(AbstractBlock.Properties.create().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));

    public static final DeferredBlock<Block> FIBER_BLOCK = BLOCKS.register("fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FIBER_BLOCK_SLAB = BLOCKS.register("fiber_block_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FIBER_BLOCK_STAIRS = BLOCKS.register("fiber_block_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FIBER_BLOCK_WALL = BLOCKS.register("fiber_block_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> WHITE_FIBER_BLOCK = BLOCKS.register("white_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> ORANGE_FIBER_BLOCK = BLOCKS.register("orange_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> MAGENTA_FIBER_BLOCK = BLOCKS.register("magenta_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIGHT_BLUE_FIBER_BLOCK = BLOCKS.register("light_blue_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> YELLOW_FIBER_BLOCK = BLOCKS.register("yellow_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIME_FIBER_BLOCK = BLOCKS.register("lime_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> PINK_FIBER_BLOCK = BLOCKS.register("pink_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> GRAY_FIBER_BLOCK = BLOCKS.register("gray_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIGHT_GRAY_FIBER_BLOCK = BLOCKS.register("light_gray_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> CYAN_FIBER_BLOCK = BLOCKS.register("cyan_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> PURPLE_FIBER_BLOCK = BLOCKS.register("purple_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BLUE_FIBER_BLOCK = BLOCKS.register("blue_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> GREEN_FIBER_BLOCK = BLOCKS.register("green_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BROWN_FIBER_BLOCK = BLOCKS.register("brown_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> RED_FIBER_BLOCK = BLOCKS.register("red_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BLACK_FIBER_BLOCK = BLOCKS.register("black_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> FIBER_MAT = BLOCKS.register("fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> WHITE_FIBER_MAT = BLOCKS.register("white_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> ORANGE_FIBER_MAT = BLOCKS.register("orange_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> MAGENTA_FIBER_MAT = BLOCKS.register("magenta_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIGHT_BLUE_FIBER_MAT = BLOCKS.register("light_blue_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> YELLOW_FIBER_MAT = BLOCKS.register("yellow_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIME_FIBER_MAT = BLOCKS.register("lime_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> PINK_FIBER_MAT = BLOCKS.register("pink_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> GRAY_FIBER_MAT = BLOCKS.register("gray_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> LIGHT_GRAY_FIBER_MAT = BLOCKS.register("light_gray_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> CYAN_FIBER_MAT = BLOCKS.register("cyan_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> PURPLE_FIBER_MAT = BLOCKS.register("purple_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BLUE_FIBER_MAT = BLOCKS.register("blue_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> GREEN_FIBER_MAT = BLOCKS.register("green_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BROWN_FIBER_MAT = BLOCKS.register("brown_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> RED_FIBER_MAT = BLOCKS.register("red_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> BLACK_FIBER_MAT = BLOCKS.register("black_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).ignitedByLava().strength(0.1F).sound(SoundType.WOOL)));

    public static final DeferredBlock<Block> DRY_ICE = BLOCKS.register("dry_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(3.5F).friction(0.999F).sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> METEORIC_ICE = BLOCKS.register("meteoric_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).instrument(NoteBlockInstrument.CHIME).strength(3.5F).friction(1.02F).sound(SoundType.GLASS)));
    public static final DeferredBlock<Block> DIAMOND_GEODE = BLOCKS.register("diamond_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> EMERALD_GEODE = BLOCKS.register("emerald_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> AQUAMARINE_GEODE = BLOCKS.register("aquamarine_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> OPAL_GEODE = BLOCKS.register("opal_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> RUBY_GEODE = BLOCKS.register("ruby_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> SAPPHIRE_GEODE = BLOCKS.register("sapphire_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> PERIDOT_GEODE = BLOCKS.register("peridot_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> GARNET_GEODE = BLOCKS.register("garnet_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> TOPAZ_GEODE = BLOCKS.register("topaz_geode", () -> new GeodeBlock(DEF_STONE));
    public static final DeferredBlock<Block> TOURMALINE_GEODE = BLOCKS.register("tourmaline_geode", () -> new GeodeBlock(DEF_STONE));

    public static final DeferredBlock<Block> TRAMPOLINE = BLOCKS.register("trampoline", () -> new TrampolineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).friction(0.8F).strength(1.0F, 2.0F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final DeferredBlock<Block> CARBON_DIOXIDE_FUMAROLE = BLOCKS.register("carbon_dioxide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.CARBON_DIOXIDE,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> HYDROGEN_CHLORIDE_FUMAROLE = BLOCKS.register("hydrogen_chloride_fumarole", () -> new FumaroleBlock(GasUtilsEnum.HYDROGEN_CHLORIDE,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> HYDROGEN_SULFIDE_FUMAROLE = BLOCKS.register("hydrogen_sulfide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.HYDROGEN_SULFIDE,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> SULFUR_DIOXIDE_FUMAROLE = BLOCKS.register("sulfur_dioxide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.SULFUR_DIOXIDE,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> HYDROGEN_GAS_BLOCK = BLOCKS.register("hydrogen_gas_block", () -> new HydrogenGasBlock(RankineItems.HYDROGEN_GAS_BOTTLE,GasUtilsEnum.HYDROGEN, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> HELIUM_GAS_BLOCK = BLOCKS.register("helium_gas_block", () -> new HeliumGasBlock(RankineItems.HELIUM_GAS_BOTTLE,GasUtilsEnum.HELIUM, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> NITROGEN_GAS_BLOCK = BLOCKS.register("nitrogen_gas_block", () -> new NitrogenGasBlock(RankineItems.NITROGEN_GAS_BOTTLE,GasUtilsEnum.NITROGEN, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> OXYGEN_GAS_BLOCK = BLOCKS.register("oxygen_gas_block", () -> new OxygenGasBlock(RankineItems.OXYGEN_GAS_BOTTLE,GasUtilsEnum.OXYGEN, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> FLUORINE_GAS_BLOCK = BLOCKS.register("fluorine_gas_block", () -> new GasBlock(RankineItems.FLUORINE_GAS_BOTTLE,GasUtilsEnum.FLUORINE, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> NEON_GAS_BLOCK = BLOCKS.register("neon_gas_block", () -> new NeonGasBlock(RankineItems.NEON_GAS_BOTTLE,GasUtilsEnum.NEON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> CHLORINE_GAS_BLOCK = BLOCKS.register("chlorine_gas_block", () -> new GasBlock(RankineItems.CHLORINE_GAS_BOTTLE,GasUtilsEnum.CHLORINE, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> ARGON_GAS_BLOCK = BLOCKS.register("argon_gas_block", () -> new GasBlock(RankineItems.ARGON_GAS_BOTTLE,GasUtilsEnum.ARGON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> KRYPTON_GAS_BLOCK = BLOCKS.register("krypton_gas_block", () -> new GasBlock(RankineItems.KRYPTON_GAS_BOTTLE,GasUtilsEnum.KRYPTON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> XENON_GAS_BLOCK = BLOCKS.register("xenon_gas_block", () -> new XenonGasBlock(RankineItems.XENON_GAS_BOTTLE,GasUtilsEnum.XENON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> RADON_GAS_BLOCK = BLOCKS.register("radon_gas_block", () -> new GasBlock(RankineItems.RADON_GAS_BOTTLE,GasUtilsEnum.RADON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> OGANESSON_GAS_BLOCK = BLOCKS.register("oganesson_gas_block", () -> new OganessonGasBlock(RankineItems.OGANESSON_GAS_BOTTLE,GasUtilsEnum.OGANESSON, BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> AMMONIA_GAS_BLOCK = BLOCKS.register("ammonia_gas_block", () -> new GasBlock(RankineItems.AMMONIA_GAS_BOTTLE,GasUtilsEnum.AMMONIA,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> CARBON_DIOXIDE_GAS_BLOCK = BLOCKS.register("carbon_dioxide_gas_block", () -> new GasBlock(RankineItems.CARBON_DIOXIDE_GAS_BOTTLE,GasUtilsEnum.CARBON_DIOXIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> HYDROGEN_CHLORIDE_GAS_BLOCK = BLOCKS.register("hydrogen_chloride_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_CHLORIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_CHLORIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> HYDROGEN_FLUORIDE_GAS_BLOCK = BLOCKS.register("hydrogen_fluoride_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_FLUORIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_FLUORIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> HYDROGEN_SULFIDE_GAS_BLOCK = BLOCKS.register("hydrogen_sulfide_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_SULFIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_SULFIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> SULFUR_DIOXIDE_GAS_BLOCK = BLOCKS.register("sulfur_dioxide_gas_block", () -> new GasBlock(RankineItems.SULFUR_DIOXIDE_GAS_BOTTLE,GasUtilsEnum.SULFUR_DIOXIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));
    public static final DeferredBlock<Block> TUNGSTEN_HEXAFLUORIDE_GAS_BLOCK = BLOCKS.register("tungsten_hexafluoride_gas_block", () -> new GasBlock(RankineItems.TUNGSTEN_HEXAFLUORIDE_GAS_BOTTLE,GasUtilsEnum.TUNGSTEN_HEXAFLUORIDE,BlockBehaviour.Properties.of().noCollission().noDrops().air()));

    //public static final DeferredBlock<Block> HELIUM_GAS_TUBE = BLOCKS.register("helium_gas_tube", () -> new GasTubeBlock(AbstractBlock.Properties.create().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).hardnessAndResistance(0.5F).sound(SoundType.GLASS).notSolid()));
    public static final DeferredBlock<Block> LITHIUM_LANTERN = BLOCKS.register("lithium_lantern", () -> new RankineLanternBlock(16711790));
    public static final DeferredBlock<Block> BORON_LANTERN = BLOCKS.register("boron_lantern", () -> new RankineLanternBlock(7577198));
    public static final DeferredBlock<Block> SODIUM_LANTERN = BLOCKS.register("sodium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> MAGNESIUM_LANTERN = BLOCKS.register("magnesium_lantern", () -> new RankineSparkyLanternBlock(15042972, ParticleTypes.ELECTRIC_SPARK));
    public static final DeferredBlock<Block> ALUMINUM_LANTERN = BLOCKS.register("aluminum_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> PHOSPHORUS_LANTERN = BLOCKS.register("phosphorus_lantern", () -> new RankineLanternBlock(14148300));
    public static final DeferredBlock<Block> POTASSIUM_LANTERN = BLOCKS.register("potassium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> CALCIUM_LANTERN = BLOCKS.register("calcium_lantern", () -> new RankineLanternBlock(16752511));
    public static final DeferredBlock<Block> SCANDIUM_LANTERN = BLOCKS.register("scandium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> VANADIUM_LANTERN = BLOCKS.register("vanadium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> CHROMIUM_LANTERN = BLOCKS.register("chromium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> MANGANESE_LANTERN = BLOCKS.register("manganese_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> COBALT_LANTERN = BLOCKS.register("cobalt_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> NICKEL_LANTERN = BLOCKS.register("nickel_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> COPPER_LANTERN = BLOCKS.register("copper_lantern", () -> new RankineLanternBlock(8435610));
    public static final DeferredBlock<Block> ZINC_LANTERN = BLOCKS.register("zinc_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> GALLIUM_LANTERN = BLOCKS.register("gallium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> GERMANIUM_LANTERN = BLOCKS.register("germanium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> ARSENIC_LANTERN = BLOCKS.register("arsenic_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> SELENIUM_LANTERN = BLOCKS.register("selenium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> RUBIDIUM_LANTERN = BLOCKS.register("rubidium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> STRONTIUM_LANTERN = BLOCKS.register("strontium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> YTTRIUM_LANTERN = BLOCKS.register("yttrium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> ZIRCONIUM_LANTERN = BLOCKS.register("zirconium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> NIOBIUM_LANTERN = BLOCKS.register("niobium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> MOLYBDENUM_LANTERN = BLOCKS.register("molybdenum_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> TECHNETIUM_LANTERN = BLOCKS.register("technetium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> RUTHENIUM_LANTERN = BLOCKS.register("ruthenium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> RHODIUM_LANTERN = BLOCKS.register("rhodium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> CADMIUM_LANTERN = BLOCKS.register("cadmium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> INDIUM_LANTERN = BLOCKS.register("indium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> TIN_LANTERN = BLOCKS.register("tin_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> ANTIMONY_LANTERN = BLOCKS.register("antimony_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> TELLURIUM_LANTERN = BLOCKS.register("tellurium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> CESIUM_LANTERN = BLOCKS.register("cesium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> BARIUM_LANTERN = BLOCKS.register("barium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> LANTHANUM_LANTERN = BLOCKS.register("lanthanum_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> CERIUM_LANTERN = BLOCKS.register("cerium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> PRASEODYMIUM_LANTERN = BLOCKS.register("praseodymium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> NEODYMIUM_LANTERN = BLOCKS.register("neodymium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> PROMETHIUM_LANTERN = BLOCKS.register("promethium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> SAMARIUM_LANTERN = BLOCKS.register("samarium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> EUROPIUM_LANTERN = BLOCKS.register("europium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> GADOLINIUM_LANTERN = BLOCKS.register("gadolinium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> TERBIUM_LANTERN = BLOCKS.register("terbium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> DYSPROSIUM_LANTERN = BLOCKS.register("dysprosium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> HOLMIUM_LANTERN = BLOCKS.register("holmium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> ERBIUM_LANTERN = BLOCKS.register("erbium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> THULIUM_LANTERN = BLOCKS.register("thulium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> YTTERBIUM_LANTERN = BLOCKS.register("ytterbium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> LUTETIUM_LANTERN = BLOCKS.register("lutetium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> HAFNIUM_LANTERN = BLOCKS.register("hafnium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> TANTALUM_LANTERN = BLOCKS.register("tantalum_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> TUNGSTEN_LANTERN = BLOCKS.register("tungsten_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> RHENIUM_LANTERN = BLOCKS.register("rhenium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> OSMIUM_LANTERN = BLOCKS.register("osmium_lantern", () -> new RankineLanternBlock(15042972));
    //public static final DeferredBlock<Block> IRIDIUM_LANTERN = BLOCKS.register("iridium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> THALLIUM_LANTERN = BLOCKS.register("thallium_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> LEAD_LANTERN = BLOCKS.register("lead_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> BISMUTH_LANTERN = BLOCKS.register("bismuth_lantern", () -> new RankineLanternBlock(15042972));
    public static final DeferredBlock<Block> NETHERITE_LANTERN = BLOCKS.register("netherite_lantern", () -> new RankineSparkyLanternBlock(15042972, ParticleTypes.ASH));
    public static final DeferredBlock<Block> ENDOSITUM_LANTERN = BLOCKS.register("endositum_lantern", () -> new RankineSparkyLanternBlock(15042972, ParticleTypes.PORTAL));



    public static final DeferredBlock<Block> SOD_BLOCK = BLOCKS.register("sod_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> SILT = BLOCKS.register("silt", () -> new RankineSandBlock(11770483, Block.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.SAND).strength(0.5F)));
    public static final DeferredBlock<Block> PERMAFROST = BLOCKS.register("permafrost", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(1.5F).sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> HUMUS = BLOCKS.register("humus", SoilBlock::new);
    public static final DeferredBlock<Block> LOAM = BLOCKS.register("loam", SoilBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM = BLOCKS.register("silty_loam", SoilBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND = BLOCKS.register("loamy_sand", SoilBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM = BLOCKS.register("sandy_loam", SoilBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM = BLOCKS.register("clay_loam", SoilBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM = BLOCKS.register("sandy_clay_loam", SoilBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM = BLOCKS.register("silty_clay_loam", SoilBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY = BLOCKS.register("sandy_clay", SoilBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY = BLOCKS.register("silty_clay", SoilBlock::new);
    public static final DeferredBlock<Block> COARSE_HUMUS = BLOCKS.register("coarse_humus", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_LOAM = BLOCKS.register("coarse_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SILTY_LOAM = BLOCKS.register("coarse_silty_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_LOAMY_SAND = BLOCKS.register("coarse_loamy_sand", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SANDY_LOAM = BLOCKS.register("coarse_sandy_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_CLAY_LOAM = BLOCKS.register("coarse_clay_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SANDY_CLAY_LOAM = BLOCKS.register("coarse_sandy_clay_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SILTY_CLAY_LOAM = BLOCKS.register("coarse_silty_clay_loam", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SANDY_CLAY = BLOCKS.register("coarse_sandy_clay", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> COARSE_SILTY_CLAY = BLOCKS.register("coarse_silty_clay", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> HUMUS_MUD = BLOCKS.register("humus_mud", MudBlock::new);
    public static final DeferredBlock<Block> LOAM_MUD = BLOCKS.register("loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM_MUD = BLOCKS.register("silty_loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND_MUD = BLOCKS.register("loamy_sand_mud", MudBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM_MUD = BLOCKS.register("sandy_loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM_MUD = BLOCKS.register("clay_loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM_MUD = BLOCKS.register("sandy_clay_loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM_MUD = BLOCKS.register("silty_clay_loam_mud", MudBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_MUD = BLOCKS.register("sandy_clay_mud", MudBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_MUD = BLOCKS.register("silty_clay_mud", MudBlock::new);
    public static final DeferredBlock<Block> HUMUS_GRASS_PATH = BLOCKS.register("humus_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> LOAM_GRASS_PATH = BLOCKS.register("loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM_GRASS_PATH = BLOCKS.register("silty_loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND_GRASS_PATH = BLOCKS.register("loamy_sand_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM_GRASS_PATH = BLOCKS.register("sandy_loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM_GRASS_PATH = BLOCKS.register("clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM_GRASS_PATH = BLOCKS.register("sandy_clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM_GRASS_PATH = BLOCKS.register("silty_clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_GRASS_PATH = BLOCKS.register("sandy_clay_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_GRASS_PATH = BLOCKS.register("silty_clay_grass_path", GrassySoilPathBlock::new);
    public static final DeferredBlock<Block> HUMUS_GRASS_BLOCK = BLOCKS.register("humus_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> LOAM_GRASS_BLOCK = BLOCKS.register("loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM_GRASS_BLOCK = BLOCKS.register("silty_loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND_GRASS_BLOCK = BLOCKS.register("loamy_sand_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM_GRASS_BLOCK = BLOCKS.register("sandy_loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("clay_loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("sandy_clay_loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("silty_clay_loam_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_GRASS_BLOCK = BLOCKS.register("sandy_clay_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_GRASS_BLOCK = BLOCKS.register("silty_clay_grass_block", GrassySoilBlock::new);
    public static final DeferredBlock<Block> HUMUS_PODZOL = BLOCKS.register("humus_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> LOAM_PODZOL = BLOCKS.register("loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM_PODZOL = BLOCKS.register("silty_loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND_PODZOL = BLOCKS.register("loamy_sand_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM_PODZOL = BLOCKS.register("sandy_loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM_PODZOL = BLOCKS.register("clay_loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM_PODZOL = BLOCKS.register("sandy_clay_loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM_PODZOL = BLOCKS.register("silty_clay_loam_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_PODZOL = BLOCKS.register("sandy_clay_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_PODZOL = BLOCKS.register("silty_clay_podzol", RankinePodzolBlock::new);
    public static final DeferredBlock<Block> HUMUS_MYCELIUM = BLOCKS.register("humus_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> LOAM_MYCELIUM = BLOCKS.register("loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SILTY_LOAM_MYCELIUM = BLOCKS.register("silty_loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> LOAMY_SAND_MYCELIUM = BLOCKS.register("loamy_sand_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SANDY_LOAM_MYCELIUM = BLOCKS.register("sandy_loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> CLAY_LOAM_MYCELIUM = BLOCKS.register("clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_LOAM_MYCELIUM = BLOCKS.register("sandy_clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_LOAM_MYCELIUM = BLOCKS.register("silty_clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SANDY_CLAY_MYCELIUM = BLOCKS.register("sandy_clay_mycelium", RankineMyceliumBlock::new);
    public static final DeferredBlock<Block> SILTY_CLAY_MYCELIUM = BLOCKS.register("silty_clay_mycelium", RankineMyceliumBlock::new);


    public static final DeferredBlock<Block> MYCELIUM_PATH = BLOCKS.register("mycelium_path", () -> new DirtPathBlock(Block.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(0.5F)));

    //gravel path?
    public static final DeferredBlock<Block> ENDER_SHIRO = BLOCKS.register("ender_shiro", () -> new EnderShiroBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.8F).sound(SoundType.ROOTS).randomTicks()));
    public static final DeferredBlock<Block> TILLED_SOIL = BLOCKS.register("tilled_soil", () -> new TilledSoilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> LIGHTNING_GLASS = BLOCKS.register("lightning_glass", () -> new LightningGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> RED_LIGHTNING_GLASS = BLOCKS.register("red_lightning_glass", () -> new LightningGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> SOUL_LIGHTNING_GLASS = BLOCKS.register("soul_lightning_glass", () -> new LightningGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> BLACK_LIGHTNING_GLASS = BLOCKS.register("black_lightning_glass", () -> new LightningGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> WHITE_LIGHTNING_GLASS = BLOCKS.register("white_lightning_glass", () -> new LightningGlassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));



    public static final DeferredBlock<Block> DARK_GRAVEL = BLOCKS.register("dark_gravel", () -> new GravelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> LIGHT_GRAVEL = BLOCKS.register("light_gravel", () -> new GravelBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> ALLUVIUM = BLOCKS.register("alluvium", () -> new RankineSandBlock(14406560, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> BLACK_SAND = BLOCKS.register("black_sand", () -> new RankineSandBlock(00000000, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> WHITE_SAND = BLOCKS.register("white_sand", () -> new RankineSandBlock(16777215, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> DESERT_SAND = BLOCKS.register("desert_sand", () -> new RankineSandBlock(14865335, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> LEAD_GLASS = BLOCKS.register("lead_glass", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(3.0F, 15.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> BOROSILICATE_GLASS = BLOCKS.register("borosilicate_glass", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(6.0F, 30.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> CVD_GLASS = BLOCKS.register("cvd_glass", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(12.0F, 60.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> COB = BLOCKS.register("cob", () -> new Block(Block.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(1.0F)));
    public static final DeferredBlock<Block> REFINED_COB = BLOCKS.register("refined_cob", () -> new Block(Block.Properties.of().mapColor(MapColor.DIRT).sound(SoundType.GRAVEL).strength(2.0F)));
    public static final DeferredBlock<Block> BANDED_IRON_FORMATION = BLOCKS.register("banded_iron_formation", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> PHOSPHORITE = BLOCKS.register("phosphorite", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> LATERITE = BLOCKS.register("laterite", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(1.0F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> SYLVINITE = BLOCKS.register("sylvinite", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> KAOLIN = BLOCKS.register("kaolin", () -> new Block(DEF_STONE));

    public static final DeferredBlock<Block> FUMAROLE_DEPOSIT = BLOCKS.register("fumarole_deposit", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> IRONSTONE = BLOCKS.register("ironstone", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> BOG_IRON = BLOCKS.register("bog_iron", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> PORPHYRY_COPPER = BLOCKS.register("porphyry_copper", () -> new OreBlock(DEF_STONE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> KIMBERLITIC_DIAMOND_ORE = BLOCKS.register("kimberlitic_diamond_ore", () -> new OreBlock(DEF_STONE, UniformInt.of(3,7)));
    public static final DeferredBlock<Block> QUICKLIME_BLOCK = BLOCKS.register("quicklime_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> MAGNESITE_BLOCK = BLOCKS.register("magnesite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> MAGNESIA_BLOCK = BLOCKS.register("magnesia_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> ALUMINA_BLOCK = BLOCKS.register("alumina_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> ANDESITIC_TUFF = BLOCKS.register("andesitic_tuff", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> BASALTIC_TUFF = BLOCKS.register("basaltic_tuff", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> RHYOLITIC_TUFF = BLOCKS.register("rhyolitic_tuff", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> KIMBERLITIC_TUFF = BLOCKS.register("kimberlitic_tuff", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final DeferredBlock<Block> KOMATIITIC_TUFF = BLOCKS.register("komatiitic_tuff", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 2.0F)));

    public static final DeferredBlock<Block> SLATE_STEPPING_STONES = BLOCKS.register("slate_stepping_stones", SteppingStonesBlock::new);



    //public static final DeferredBlock<Block> QUARRY_BARRIER = BLOCKS.register("quarry_barrier", () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5.0F, 20.0F)));
    public static final DeferredBlock<Block> BONE_CHAR_BLOCK = BLOCKS.register("bone_char_block", () -> new RotatedPillarBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.BONE_BLOCK).strength(2.0F)));
    public static final DeferredBlock<Block> FIRE_CLAY = BLOCKS.register("fire_clay", () -> new Block(Block.Properties.of().mapColor(MapColor.CLAY).sound(SoundType.GRAVEL).strength(0.6F)));
    public static final DeferredBlock<Block> PORCELAIN_CLAY = BLOCKS.register("porcelain_clay", () -> new Block(Block.Properties.of().mapColor(MapColor.CLAY).sound(SoundType.GRAVEL).strength(0.6F)));
    public static final DeferredBlock<Block> KAOLINITE_BLOCK = BLOCKS.register("kaolinite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.CLAY).sound(SoundType.GRAVEL).strength(0.6F)));
    public static final DeferredBlock<Block> HALLOYSITE_BLOCK = BLOCKS.register("halloysite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.CLAY).sound(SoundType.GRAVEL).strength(0.6F)));
    public static final DeferredBlock<Block> PORCELAIN = BLOCKS.register("porcelain", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1.0F, 3.0F)));

    public static final DeferredBlock<Block> WHITE_GLAZED_PORCELAIN = BLOCKS.register("white_glazed_porcelain", () -> new GlazedPorcelainBlock(16777215));
    public static final DeferredBlock<Block> ORANGE_GLAZED_PORCELAIN = BLOCKS.register("orange_glazed_porcelain", () -> new GlazedPorcelainBlock(13392384));
    public static final DeferredBlock<Block> MAGENTA_GLAZED_PORCELAIN = BLOCKS.register("magenta_glazed_porcelain", () -> new GlazedPorcelainBlock(8323182));
    public static final DeferredBlock<Block> LIGHT_BLUE_GLAZED_PORCELAIN = BLOCKS.register("light_blue_glazed_porcelain", () -> new GlazedPorcelainBlock(38143));
    public static final DeferredBlock<Block> YELLOW_GLAZED_PORCELAIN = BLOCKS.register("yellow_glazed_porcelain", () -> new GlazedPorcelainBlock(14201856));
    public static final DeferredBlock<Block> LIME_GLAZED_PORCELAIN = BLOCKS.register("lime_glazed_porcelain", () -> new GlazedPorcelainBlock(5046016));
    public static final DeferredBlock<Block> PINK_GLAZED_PORCELAIN = BLOCKS.register("pink_glazed_porcelain", () -> new GlazedPorcelainBlock(16744429));
    public static final DeferredBlock<Block> GRAY_GLAZED_PORCELAIN = BLOCKS.register("gray_glazed_porcelain", () -> new GlazedPorcelainBlock(4210752));
    public static final DeferredBlock<Block> LIGHT_GRAY_GLAZED_PORCELAIN = BLOCKS.register("light_gray_glazed_porcelain", () -> new GlazedPorcelainBlock(8421504));
    public static final DeferredBlock<Block> CYAN_GLAZED_PORCELAIN = BLOCKS.register("cyan_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final DeferredBlock<Block> PURPLE_GLAZED_PORCELAIN = BLOCKS.register("purple_glazed_porcelain", () -> new GlazedPorcelainBlock(5701759));
    public static final DeferredBlock<Block> BLUE_GLAZED_PORCELAIN = BLOCKS.register("blue_glazed_porcelain", () -> new GlazedPorcelainBlock(4991));
    public static final DeferredBlock<Block> GREEN_GLAZED_PORCELAIN = BLOCKS.register("green_glazed_porcelain", () -> new GlazedPorcelainBlock(32526));
    public static final DeferredBlock<Block> BROWN_GLAZED_PORCELAIN = BLOCKS.register("brown_glazed_porcelain", () -> new GlazedPorcelainBlock(8336128));
    public static final DeferredBlock<Block> RED_GLAZED_PORCELAIN = BLOCKS.register("red_glazed_porcelain", () -> new GlazedPorcelainBlock(8323072));
    public static final DeferredBlock<Block> BLACK_GLAZED_PORCELAIN = BLOCKS.register("black_glazed_porcelain", () -> new GlazedPorcelainBlock(0));


    public static final DeferredBlock<Block> BLOOD_OBSIDIAN = BLOCKS.register("blood_obsidian", () -> new RankineObsidianBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> SNOWFLAKE_OBSIDIAN = BLOCKS.register("snowflake_obsidian", () -> new RankineObsidianBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final DeferredBlock<Block> CHECKERED_MARBLE = BLOCKS.register("checkered_marble", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(2)));
    public static final DeferredBlock<Block> CHECKERED_MARBLE_SLAB = BLOCKS.register("checkered_marble_slab", () -> new RankineSlabBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2)));
    public static final DeferredBlock<Block> CHECKERED_MARBLE_STAIRS = BLOCKS.register("checkered_marble_stairs", () -> new RankineStairsBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2)));
    public static final DeferredBlock<Block> CHECKERED_MARBLE_WALL = BLOCKS.register("checkered_marble_wall", () -> new RankineWallBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(1.5F, 6.0F)));

    public static final DeferredBlock<Block> ASPHALT = BLOCKS.register("asphalt", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_YL = BLOCKS.register("asphalt_yellow_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_YDL = BLOCKS.register("asphalt_yellow_dashed_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_YDBL = BLOCKS.register("asphalt_yellow_double_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_DDBL = BLOCKS.register("asphalt_yellow_dashed_double_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_WL = BLOCKS.register("asphalt_white_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_WDL = BLOCKS.register("asphalt_white_dashed_line", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_CW = BLOCKS.register("asphalt_crosswalk", BaseAsphaltBlock::new);
    public static final DeferredBlock<Block> ASPHALT_S = BLOCKS.register("asphalt_solid", BaseAsphaltBlock::new);

    public static final DeferredBlock<Block> RED_ASPHALT = BLOCKS.register("red_asphalt", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_YL = BLOCKS.register("red_asphalt_yellow_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_YDL = BLOCKS.register("red_asphalt_yellow_dashed_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_YDBL = BLOCKS.register("red_asphalt_yellow_double_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_DDBL = BLOCKS.register("red_asphalt_yellow_dashed_double_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_WL = BLOCKS.register("red_asphalt_white_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_WDL = BLOCKS.register("red_asphalt_white_dashed_line", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_CW = BLOCKS.register("red_asphalt_crosswalk", RedAsphaltBlock::new);
    public static final DeferredBlock<Block> RED_ASPHALT_S = BLOCKS.register("red_asphalt_solid", RedAsphaltBlock::new);

    public static final DeferredBlock<Block> GRAY_ASPHALT = BLOCKS.register("gray_asphalt", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_YL = BLOCKS.register("gray_asphalt_yellow_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_YDL = BLOCKS.register("gray_asphalt_yellow_dashed_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_YDBL = BLOCKS.register("gray_asphalt_yellow_double_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_DDBL = BLOCKS.register("gray_asphalt_yellow_dashed_double_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_WL = BLOCKS.register("gray_asphalt_white_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_WDL = BLOCKS.register("gray_asphalt_white_dashed_line", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_CW = BLOCKS.register("gray_asphalt_crosswalk", GrayAsphaltBlock::new);
    public static final DeferredBlock<Block> GRAY_ASPHALT_S = BLOCKS.register("gray_asphalt_solid", GrayAsphaltBlock::new);

    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT = BLOCKS.register("dark_gray_asphalt", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_YL = BLOCKS.register("dark_gray_asphalt_yellow_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_YDL = BLOCKS.register("dark_gray_asphalt_yellow_dashed_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_YDBL = BLOCKS.register("dark_gray_asphalt_yellow_double_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_DDBL = BLOCKS.register("dark_gray_asphalt_yellow_dashed_double_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_WL = BLOCKS.register("dark_gray_asphalt_white_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_WDL = BLOCKS.register("dark_gray_asphalt_white_dashed_line", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_CW = BLOCKS.register("dark_gray_asphalt_crosswalk", DarkGrayAsphaltBlock::new);
    public static final DeferredBlock<Block> DARK_GRAY_ASPHALT_S = BLOCKS.register("dark_gray_asphalt_solid", DarkGrayAsphaltBlock::new);

    public static final DeferredBlock<Block> GREEN_ASPHALT = BLOCKS.register("green_asphalt", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_YL = BLOCKS.register("green_asphalt_yellow_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_YDL = BLOCKS.register("green_asphalt_yellow_dashed_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_YDBL = BLOCKS.register("green_asphalt_yellow_double_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_DDBL = BLOCKS.register("green_asphalt_yellow_dashed_double_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_WL = BLOCKS.register("green_asphalt_white_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_WDL = BLOCKS.register("green_asphalt_white_dashed_line", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_CW = BLOCKS.register("green_asphalt_crosswalk", GreenAsphaltBlock::new);
    public static final DeferredBlock<Block> GREEN_ASPHALT_S = BLOCKS.register("green_asphalt_solid", GreenAsphaltBlock::new);

    public static final DeferredBlock<Block> BLUE_ASPHALT = BLOCKS.register("blue_asphalt", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_YL = BLOCKS.register("blue_asphalt_yellow_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_YDL = BLOCKS.register("blue_asphalt_yellow_dashed_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_YDBL = BLOCKS.register("blue_asphalt_yellow_double_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_DDBL = BLOCKS.register("blue_asphalt_yellow_dashed_double_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_WL = BLOCKS.register("blue_asphalt_white_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_WDL = BLOCKS.register("blue_asphalt_white_dashed_line", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_CW = BLOCKS.register("blue_asphalt_crosswalk", BlueAsphaltBlock::new);
    public static final DeferredBlock<Block> BLUE_ASPHALT_S = BLOCKS.register("blue_asphalt_solid", BlueAsphaltBlock::new);


    public static final DeferredBlock<Block> STICK_BLOCK = BLOCKS.register("stick_block", () -> new StickBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(1.2F)));
    public static final DeferredBlock<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new RankineEightLayerBlock(Block.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(0.25F)));
    public static final DeferredBlock<Block> SAWDUST = BLOCKS.register("sawdust", () -> new RankineEightLayerBlock(Block.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.WOOD).strength(0.25F)));
    public static final DeferredBlock<Block> ASH = BLOCKS.register("ash", () -> new RankineEightLayerBlock(Block.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final DeferredBlock<Block> BONE_ASH = BLOCKS.register("bone_ash", () -> new RankineEightLayerBlock(Block.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final DeferredBlock<Block> POZZOLANA = BLOCKS.register("pozzolana", () -> new RankineEightLayerBlock(Block.Properties.of().mapColor(MapColor.SNOW).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final DeferredBlock<Block> MINERAL_WOOL = BLOCKS.register("mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> WHITE_MINERAL_WOOL = BLOCKS.register("white_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> ORANGE_MINERAL_WOOL = BLOCKS.register("orange_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> MAGENTA_MINERAL_WOOL = BLOCKS.register("magenta_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> LIGHT_BLUE_MINERAL_WOOL = BLOCKS.register("light_blue_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> YELLOW_MINERAL_WOOL = BLOCKS.register("yellow_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> LIME_MINERAL_WOOL = BLOCKS.register("lime_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> PINK_MINERAL_WOOL = BLOCKS.register("pink_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> GRAY_MINERAL_WOOL = BLOCKS.register("gray_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> LIGHT_GRAY_MINERAL_WOOL = BLOCKS.register("light_gray_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> CYAN_MINERAL_WOOL = BLOCKS.register("cyan_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> PURPLE_MINERAL_WOOL = BLOCKS.register("purple_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> BLUE_MINERAL_WOOL = BLOCKS.register("blue_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> GREEN_MINERAL_WOOL = BLOCKS.register("green_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> BROWN_MINERAL_WOOL = BLOCKS.register("brown_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> RED_MINERAL_WOOL = BLOCKS.register("red_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> BLACK_MINERAL_WOOL = BLOCKS.register("black_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));
    public static final DeferredBlock<Block> GUN_COTTON = BLOCKS.register("gun_cotton", () -> new GunCottonBlock(Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1F)));




    public static final DeferredBlock<Block> HOLLOW_OAK_LOG = BLOCKS.register("hollow_oak_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_SPRUCE_LOG = BLOCKS.register("hollow_spruce_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_BIRCH_LOG = BLOCKS.register("hollow_birch_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_JUNGLE_LOG = BLOCKS.register("hollow_jungle_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_ACACIA_LOG = BLOCKS.register("hollow_acacia_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_DARK_OAK_LOG = BLOCKS.register("hollow_dark_oak_log", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_WARPED_STEM = BLOCKS.register("hollow_warped_stem", HollowLogBlock::new);
    public static final DeferredBlock<Block> HOLLOW_CRIMSON_STEM = BLOCKS.register("hollow_crimson_stem", HollowLogBlock::new);

    public static final DeferredBlock<Block> OAK_LEAF_LITTER = BLOCKS.register("oak_leaf_litter", LeafLitterBlock::new);
    public static final DeferredBlock<Block> SPRUCE_LEAF_LITTER = BLOCKS.register("spruce_leaf_litter", LeafLitterBlock::new);
    public static final DeferredBlock<Block> BIRCH_LEAF_LITTER = BLOCKS.register("birch_leaf_litter", LeafLitterBlock::new);
    public static final DeferredBlock<Block> JUNGLE_LEAF_LITTER = BLOCKS.register("jungle_leaf_litter", LeafLitterBlock::new);
    public static final DeferredBlock<Block> ACACIA_LEAF_LITTER = BLOCKS.register("acacia_leaf_litter", LeafLitterBlock::new);
    public static final DeferredBlock<Block> DARK_OAK_LEAF_LITTER = BLOCKS.register("dark_oak_leaf_litter", LeafLitterBlock::new);



    public static final DeferredBlock<Block> STUMP = BLOCKS.register("stump", () -> new StumpBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).requiresCorrectToolForDrops().strength(30.0F, 9.0F)));


    public static final DeferredBlock<Block> BRASS_DOOR = BLOCKS.register("brass_door", RankineMetalDoor::new);
    public static final DeferredBlock<Block> BRONZE_DOOR = BLOCKS.register("bronze_door", RankineMetalDoor::new);
    public static final DeferredBlock<Block> CUPRONICKEL_DOOR = BLOCKS.register("cupronickel_door", RankineMetalDoor::new);
    public static final DeferredBlock<Block> STEEL_DOOR = BLOCKS.register("steel_door", RankineMetalDoor::new);
    public static final DeferredBlock<Block> BRASS_TRAPDOOR = BLOCKS.register("brass_trapdoor", RankineMetalTrapdoor::new);
    public static final DeferredBlock<Block> BRONZE_TRAPDOOR = BLOCKS.register("bronze_trapdoor", RankineMetalTrapdoor::new);
    public static final DeferredBlock<Block> STEEL_TRAPDOOR = BLOCKS.register("steel_trapdoor", RankineMetalTrapdoor::new);
    public static final DeferredBlock<Block> CUPRONICKEL_TRAPDOOR = BLOCKS.register("cupronickel_trapdoor", RankineMetalTrapdoor::new);

    public static final DeferredBlock<Block> ORANGE_LILY = BLOCKS.register("orange_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> RED_LILY = BLOCKS.register("red_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> WHITE_LILY = BLOCKS.register("white_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> PURPLE_MORNING_GLORY = BLOCKS.register("purple_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> BLUE_MORNING_GLORY = BLOCKS.register("blue_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> BLACK_MORNING_GLORY = BLOCKS.register("black_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> PINK_BELLFLOWER = BLOCKS.register("pink_bellflower", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> VIOLET_BELLFLOWER = BLOCKS.register("violet_bellflower", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> GOLDENROD = BLOCKS.register("goldenrod", () -> new TallFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> SHORT_GRASS = BLOCKS.register("short_grass", () -> new ShortGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> STINGING_NETTLE = BLOCKS.register("stinging_nettle", () -> new StingingNettleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> YELLOW_CLOVER = BLOCKS.register("yellow_clover", () -> new CloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> WHITE_CLOVER = BLOCKS.register("white_clover", () -> new CloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> RED_CLOVER = BLOCKS.register("red_clover", () -> new CloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> CRIMSON_CLOVER = BLOCKS.register("crimson_clover", () -> new CloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> WILLOW_BRANCHLET = BLOCKS.register("willow_branchlet", () -> new WillowBranchletTopBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.VINE)));
    public static final DeferredBlock<Block> WILLOW_BRANCHLET_PLANT = BLOCKS.register("willow_branchlet_plant", () -> new WillowBranchletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.VINE)));
    public static final DeferredBlock<Block> LOCUST_SPINE = BLOCKS.register("locust_spine", () -> new LocustSpineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().noCollission().instabreak().sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> OYSTER_MUSHROOM = BLOCKS.register("oyster_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_OYSTER_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> LIONS_MANE_MUSHROOM = BLOCKS.register("lions_mane_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_LIONS_MANE_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> SULFUR_SHELF_MUSHROOM = BLOCKS.register("sulfur_shelf_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_SULFUR_SHELF_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> HONEY_MUSHROOM = BLOCKS.register("honey_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_HONEY_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> ARTIST_CONK_MUSHROOM = BLOCKS.register("artist_conk_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_ARTIST_CONK_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> TINDER_CONK_MUSHROOM = BLOCKS.register("tinder_conk_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_TINDER_CONK_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> TURKEY_TAIL_MUSHROOM = BLOCKS.register("turkey_tail_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_TURKEY_TAIL_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> CINNABAR_POLYPORE_MUSHROOM = BLOCKS.register("cinnabar_polypore_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_CINNABAR_POLYPORE_MUSHROOM.getHolder().get()));
    public static final DeferredBlock<Block> OYSTER_MUSHROOM_BLOCK = BLOCKS.register("oyster_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> LIONS_MANE_MUSHROOM_BLOCK = BLOCKS.register("lions_mane_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SULFUR_SHELF_MUSHROOM_BLOCK = BLOCKS.register("sulfur_shelf_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> HONEY_MUSHROOM_BLOCK = BLOCKS.register("honey_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> ARTIST_CONK_MUSHROOM_BLOCK = BLOCKS.register("artist_conk_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> TINDER_CONK_MUSHROOM_BLOCK = BLOCKS.register("tinder_conk_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> TURKEY_TAIL_MUSHROOM_BLOCK = BLOCKS.register("turkey_tail_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> CINNABAR_POLYPORE_MUSHROOM_BLOCK = BLOCKS.register("cinnabar_polypore_mushroom_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(0.2F).sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> WOOD_TIER_MINING_HEAD = BLOCKS.register("wood_tier_mining_head", MiningHeadBlock::new);
    public static final DeferredBlock<Block> STONE_TIER_MINING_HEAD = BLOCKS.register("stone_tier_mining_head", MiningHeadBlock::new);
    public static final DeferredBlock<Block> IRON_TIER_MINING_HEAD = BLOCKS.register("iron_tier_mining_head", MiningHeadBlock::new);
    public static final DeferredBlock<Block> DIAMOND_TIER_MINING_HEAD = BLOCKS.register("diamond_tier_mining_head", MiningHeadBlock::new);
    public static final DeferredBlock<Block> NETHERITE_TIER_MINING_HEAD = BLOCKS.register("netherite_tier_mining_head", MiningHeadBlock::new);
    public static final DeferredBlock<Block> WOOD_TIER_CRUSHING_HEAD = BLOCKS.register("wood_tier_crushing_head", CrushingHeadBlock::new);
    public static final DeferredBlock<Block> STONE_TIER_CRUSHING_HEAD = BLOCKS.register("stone_tier_crushing_head", CrushingHeadBlock::new);
    public static final DeferredBlock<Block> IRON_TIER_CRUSHING_HEAD = BLOCKS.register("iron_tier_crushing_head", CrushingHeadBlock::new);
    public static final DeferredBlock<Block> DIAMOND_TIER_CRUSHING_HEAD = BLOCKS.register("diamond_tier_crushing_head", CrushingHeadBlock::new);
    public static final DeferredBlock<Block> NETHERITE_TIER_CRUSHING_HEAD = BLOCKS.register("netherite_tier_crushing_head", CrushingHeadBlock::new);



    //ALLOY BLOCKS
    public static final DeferredBlock<Block> ROSE_GOLD_BLOCK = BLOCKS.register("rose_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> WHITE_GOLD_BLOCK = BLOCKS.register("white_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> GREEN_GOLD_BLOCK = BLOCKS.register("green_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BLUE_GOLD_BLOCK = BLOCKS.register("blue_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PURPLE_GOLD_BLOCK = BLOCKS.register("purple_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BLACK_GOLD_BLOCK = BLOCKS.register("black_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PEWTER_BLOCK = BLOCKS.register("pewter_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BRASS_BLOCK = BLOCKS.register("brass_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CAST_IRON_BLOCK = BLOCKS.register("cast_iron_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> INVAR_BLOCK = BLOCKS.register("invar_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CUPRONICKEL_BLOCK = BLOCKS.register("cupronickel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> DURALUMIN_BLOCK = BLOCKS.register("duralumin_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_BLOCK = BLOCKS.register("magnesium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> STERLING_SILVER_BLOCK = BLOCKS.register("sterling_silver_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NICKEL_SILVER_BLOCK = BLOCKS.register("nickel_silver_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TITANIUM_ALLOY_BLOCK = BLOCKS.register("titanium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NITINOL_BLOCK = BLOCKS.register("nitinol_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ALNICO_BLOCK = BLOCKS.register("alnico_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> STAINLESS_STEEL_BLOCK = BLOCKS.register("stainless_steel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_BLOCK = BLOCKS.register("nickel_superalloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_BLOCK = BLOCKS.register("tungsten_heavy_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_BLOCK = BLOCKS.register("cobalt_superalloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_BLOCK = BLOCKS.register("niobium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_BLOCK = BLOCKS.register("zirconium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ROSE_METAL_BLOCK = BLOCKS.register("rose_metal_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MISCHMETAL_BLOCK = BLOCKS.register("mischmetal_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> FERROCERIUM_BLOCK = BLOCKS.register("ferrocerium_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> GALINSTAN_BLOCK = BLOCKS.register("galinstan_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> OSMIRIDIUM_BLOCK = BLOCKS.register("osmiridium_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_BLOCK = BLOCKS.register("sodium_potassium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_BLOCK = BLOCKS.register("neptunium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> AMALGAM_BLOCK = BLOCKS.register("amalgam_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ENDER_AMALGAM_BLOCK = BLOCKS.register("ender_amalgam_block", () -> new AlloyBlock(DEF_METAL_BLOCK));

    public static final DeferredBlock<Block> ALLOY_BLOCK = BLOCKS.register("alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));

    
    public static final DeferredBlock<Block> ROSE_GOLD_PEDESTAL = BLOCKS.register("rose_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> WHITE_GOLD_PEDESTAL = BLOCKS.register("white_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> GREEN_GOLD_PEDESTAL = BLOCKS.register("green_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> BLUE_GOLD_PEDESTAL = BLOCKS.register("blue_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> PURPLE_GOLD_PEDESTAL = BLOCKS.register("purple_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> BLACK_GOLD_PEDESTAL = BLOCKS.register("black_gold_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> PEWTER_PEDESTAL = BLOCKS.register("pewter_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> BRONZE_PEDESTAL = BLOCKS.register("bronze_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> BRASS_PEDESTAL = BLOCKS.register("brass_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> CAST_IRON_PEDESTAL = BLOCKS.register("cast_iron_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> INVAR_PEDESTAL = BLOCKS.register("invar_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> CUPRONICKEL_PEDESTAL = BLOCKS.register("cupronickel_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> DURALUMIN_PEDESTAL = BLOCKS.register("duralumin_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_PEDESTAL = BLOCKS.register("magnesium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> STERLING_SILVER_PEDESTAL = BLOCKS.register("sterling_silver_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> NICKEL_SILVER_PEDESTAL = BLOCKS.register("nickel_silver_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> TITANIUM_ALLOY_PEDESTAL = BLOCKS.register("titanium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> NITINOL_PEDESTAL = BLOCKS.register("nitinol_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> ALNICO_PEDESTAL = BLOCKS.register("alnico_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> STEEL_PEDESTAL = BLOCKS.register("steel_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> STAINLESS_STEEL_PEDESTAL = BLOCKS.register("stainless_steel_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_PEDESTAL = BLOCKS.register("nickel_superalloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_PEDESTAL = BLOCKS.register("tungsten_heavy_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_PEDESTAL = BLOCKS.register("cobalt_superalloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_PEDESTAL = BLOCKS.register("niobium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_PEDESTAL = BLOCKS.register("zirconium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> ROSE_METAL_PEDESTAL = BLOCKS.register("rose_metal_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> MISCHMETAL_PEDESTAL = BLOCKS.register("mischmetal_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> FERROCERIUM_PEDESTAL = BLOCKS.register("ferrocerium_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> GALINSTAN_PEDESTAL = BLOCKS.register("galinstan_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> OSMIRIDIUM_PEDESTAL = BLOCKS.register("osmiridium_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_PEDESTAL = BLOCKS.register("sodium_potassium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_PEDESTAL = BLOCKS.register("neptunium_alloy_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> AMALGAM_PEDESTAL = BLOCKS.register("amalgam_pedestal", PedestalBlock::new);
    public static final DeferredBlock<Block> ENDER_AMALGAM_PEDESTAL = BLOCKS.register("ender_amalgam_pedestal", PedestalBlock::new);


    public static final DeferredBlock<Block> ROSE_GOLD_SHEETMETAL = BLOCKS.register("rose_gold_sheetmetal", () -> new SheetmetalBlock(16756647));
    public static final DeferredBlock<Block> WHITE_GOLD_SHEETMETAL = BLOCKS.register("white_gold_sheetmetal", () -> new SheetmetalBlock(16777207));
    public static final DeferredBlock<Block> GREEN_GOLD_SHEETMETAL = BLOCKS.register("green_gold_sheetmetal", () -> new SheetmetalBlock(15728547));
    public static final DeferredBlock<Block> BLUE_GOLD_SHEETMETAL = BLOCKS.register("blue_gold_sheetmetal", () -> new SheetmetalBlock(8695295));
    public static final DeferredBlock<Block> PURPLE_GOLD_SHEETMETAL = BLOCKS.register("purple_gold_sheetmetal", () -> new SheetmetalBlock(11836415));
    public static final DeferredBlock<Block> BLACK_GOLD_SHEETMETAL = BLOCKS.register("black_gold_sheetmetal", () -> new SheetmetalBlock(3684408));
    public static final DeferredBlock<Block> PEWTER_SHEETMETAL = BLOCKS.register("pewter_sheetmetal", () -> new SheetmetalBlock(11711154));
    public static final DeferredBlock<Block> BRONZE_SHEETMETAL = BLOCKS.register("bronze_sheetmetal", () -> new SheetmetalBlock(15510384));
    public static final DeferredBlock<Block> BRASS_SHEETMETAL = BLOCKS.register("brass_sheetmetal", () -> new SheetmetalBlock(16762368));
    public static final DeferredBlock<Block> CAST_IRON_SHEETMETAL = BLOCKS.register("cast_iron_sheetmetal", () -> new SheetmetalBlock(4408131));
    public static final DeferredBlock<Block> INVAR_SHEETMETAL = BLOCKS.register("invar_sheetmetal", () -> new SheetmetalBlock(13028546));
    public static final DeferredBlock<Block> CUPRONICKEL_SHEETMETAL = BLOCKS.register("cupronickel_sheetmetal", () -> new SheetmetalBlock(11946807));
    public static final DeferredBlock<Block> DURALUMIN_SHEETMETAL = BLOCKS.register("duralumin_sheetmetal", () -> new SheetmetalBlock(8887223));
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_SHEETMETAL = BLOCKS.register("magnesium_alloy_sheetmetal", () -> new SheetmetalBlock(6841956));
    public static final DeferredBlock<Block> STERLING_SILVER_SHEETMETAL = BLOCKS.register("sterling_silver_sheetmetal", () -> new SheetmetalBlock(15723237));
    public static final DeferredBlock<Block> NICKEL_SILVER_SHEETMETAL = BLOCKS.register("nickel_silver_sheetmetal", () -> new SheetmetalBlock(9608841));
    public static final DeferredBlock<Block> TITANIUM_ALLOY_SHEETMETAL = BLOCKS.register("titanium_alloy_sheetmetal", () -> new SheetmetalBlock(13750746));
    public static final DeferredBlock<Block> NITINOL_SHEETMETAL = BLOCKS.register("nitinol_sheetmetal", () -> new SheetmetalBlock(12882104));
    public static final DeferredBlock<Block> ALNICO_SHEETMETAL = BLOCKS.register("alnico_sheetmetal", () -> new SheetmetalBlock(16730178));
    public static final DeferredBlock<Block> STEEL_SHEETMETAL = BLOCKS.register("steel_sheetmetal", () -> new SheetmetalBlock(7634311));
    public static final DeferredBlock<Block> STAINLESS_STEEL_SHEETMETAL = BLOCKS.register("stainless_steel_sheetmetal", () -> new SheetmetalBlock(13292499));
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_SHEETMETAL = BLOCKS.register("nickel_superalloy_sheetmetal", () -> new SheetmetalBlock(8559522));
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_SHEETMETAL = BLOCKS.register("tungsten_heavy_alloy_sheetmetal", () -> new SheetmetalBlock(11113071));
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_SHEETMETAL = BLOCKS.register("cobalt_superalloy_sheetmetal", () -> new SheetmetalBlock(6534878));
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_SHEETMETAL = BLOCKS.register("niobium_alloy_sheetmetal", () -> new SheetmetalBlock(4533618));
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_SHEETMETAL = BLOCKS.register("zirconium_alloy_sheetmetal", () -> new SheetmetalBlock(14271685));
    public static final DeferredBlock<Block> ROSE_METAL_SHEETMETAL = BLOCKS.register("rose_metal_sheetmetal", () -> new SheetmetalBlock(6235970));
    public static final DeferredBlock<Block> MISCHMETAL_SHEETMETAL = BLOCKS.register("mischmetal_sheetmetal", () -> new SheetmetalBlock(7304289));
    public static final DeferredBlock<Block> FERROCERIUM_SHEETMETAL = BLOCKS.register("ferrocerium_sheetmetal", () -> new SheetmetalBlock(7433071));
    public static final DeferredBlock<Block> GALINSTAN_SHEETMETAL = BLOCKS.register("galinstan_sheetmetal", () -> new SheetmetalBlock(12564673));
    public static final DeferredBlock<Block> OSMIRIDIUM_SHEETMETAL = BLOCKS.register("osmiridium_sheetmetal", () -> new SheetmetalBlock(13212593));
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_SHEETMETAL = BLOCKS.register("sodium_potassium_alloy_sheetmetal", () -> new SheetmetalBlock(9403777));
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_SHEETMETAL = BLOCKS.register("neptunium_alloy_sheetmetal", () -> new SheetmetalBlock(5805717));
    public static final DeferredBlock<Block> AMALGAM_SHEETMETAL = BLOCKS.register("amalgam_sheetmetal", () -> new SheetmetalBlock(13881539));
    public static final DeferredBlock<Block> ENDER_AMALGAM_SHEETMETAL = BLOCKS.register("ender_amalgam_sheetmetal", () -> new SheetmetalBlock(492385));

    public static final DeferredBlock<Block> ROSE_GOLD_POLE = BLOCKS.register("rose_gold_pole", () -> new MetalPoleBlock(16756647));
    public static final DeferredBlock<Block> WHITE_GOLD_POLE = BLOCKS.register("white_gold_pole", () -> new MetalPoleBlock(16777207));
    public static final DeferredBlock<Block> GREEN_GOLD_POLE = BLOCKS.register("green_gold_pole", () -> new MetalPoleBlock(15728547));
    public static final DeferredBlock<Block> BLUE_GOLD_POLE = BLOCKS.register("blue_gold_pole", () -> new MetalPoleBlock(8695295));
    public static final DeferredBlock<Block> PURPLE_GOLD_POLE = BLOCKS.register("purple_gold_pole", () -> new MetalPoleBlock(11836415));
    public static final DeferredBlock<Block> BLACK_GOLD_POLE = BLOCKS.register("black_gold_pole", () -> new MetalPoleBlock(3684408));
    public static final DeferredBlock<Block> PEWTER_POLE = BLOCKS.register("pewter_pole", () -> new MetalPoleBlock(11711154));
    public static final DeferredBlock<Block> BRONZE_POLE = BLOCKS.register("bronze_pole", () -> new MetalPoleBlock(15510384));
    public static final DeferredBlock<Block> BRASS_POLE = BLOCKS.register("brass_pole", () -> new MetalPoleBlock(16762368));
    public static final DeferredBlock<Block> CAST_IRON_POLE = BLOCKS.register("cast_iron_pole", () -> new MetalPoleBlock(4408131));
    public static final DeferredBlock<Block> INVAR_POLE = BLOCKS.register("invar_pole", () -> new MetalPoleBlock(13028546));
    public static final DeferredBlock<Block> CUPRONICKEL_POLE = BLOCKS.register("cupronickel_pole", () -> new MetalPoleBlock(11946807));
    public static final DeferredBlock<Block> DURALUMIN_POLE = BLOCKS.register("duralumin_pole", () -> new MetalPoleBlock(8887223));
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_POLE = BLOCKS.register("magnesium_alloy_pole", () -> new MetalPoleBlock(6841956));
    public static final DeferredBlock<Block> STERLING_SILVER_POLE = BLOCKS.register("sterling_silver_pole", () -> new MetalPoleBlock(15723237));
    public static final DeferredBlock<Block> NICKEL_SILVER_POLE = BLOCKS.register("nickel_silver_pole", () -> new MetalPoleBlock(9608841));
    public static final DeferredBlock<Block> TITANIUM_ALLOY_POLE = BLOCKS.register("titanium_alloy_pole", () -> new MetalPoleBlock(13750746));
    public static final DeferredBlock<Block> NITINOL_POLE = BLOCKS.register("nitinol_pole", () -> new MetalPoleBlock(12882104));
    public static final DeferredBlock<Block> ALNICO_POLE = BLOCKS.register("alnico_pole", () -> new MetalPoleBlock(16730178));
    public static final DeferredBlock<Block> STEEL_POLE = BLOCKS.register("steel_pole", () -> new MetalPoleBlock(7634311));
    public static final DeferredBlock<Block> STAINLESS_STEEL_POLE = BLOCKS.register("stainless_steel_pole", () -> new MetalPoleBlock(13292499));
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_POLE = BLOCKS.register("nickel_superalloy_pole", () -> new MetalPoleBlock(8559522));
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_POLE = BLOCKS.register("tungsten_heavy_alloy_pole", () -> new MetalPoleBlock(11113071));
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_POLE = BLOCKS.register("cobalt_superalloy_pole", () -> new MetalPoleBlock(6534878));
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_POLE = BLOCKS.register("niobium_alloy_pole", () -> new MetalPoleBlock(4533618));
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_POLE = BLOCKS.register("zirconium_alloy_pole", () -> new MetalPoleBlock(14271685));
    public static final DeferredBlock<Block> ROSE_METAL_POLE = BLOCKS.register("rose_metal_pole", () -> new MetalPoleBlock(6235970));
    public static final DeferredBlock<Block> MISCHMETAL_POLE = BLOCKS.register("mischmetal_pole", () -> new MetalPoleBlock(7304289));
    public static final DeferredBlock<Block> FERROCERIUM_POLE = BLOCKS.register("ferrocerium_pole", () -> new MetalPoleBlock(7433071));
    public static final DeferredBlock<Block> GALINSTAN_POLE = BLOCKS.register("galinstan_pole", () -> new MetalPoleBlock(12564673));
    public static final DeferredBlock<Block> OSMIRIDIUM_POLE = BLOCKS.register("osmiridium_pole", () -> new MetalPoleBlock(13212593));
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_POLE = BLOCKS.register("sodium_potassium_alloy_pole", () -> new MetalPoleBlock(9403777));
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_POLE = BLOCKS.register("neptunium_alloy_pole", () -> new MetalPoleBlock(5805717));
    public static final DeferredBlock<Block> AMALGAM_POLE = BLOCKS.register("amalgam_pole", () -> new MetalPoleBlock(13881539));
    public static final DeferredBlock<Block> ENDER_AMALGAM_POLE = BLOCKS.register("ender_amalgam_pole", () -> new MetalPoleBlock(492385));

    public static final DeferredBlock<Block> ROSE_GOLD_BARS = BLOCKS.register("rose_gold_bars", () -> new MetalBarsBlock(16756647));
    public static final DeferredBlock<Block> WHITE_GOLD_BARS = BLOCKS.register("white_gold_bars", () -> new MetalBarsBlock(16777207));
    public static final DeferredBlock<Block> GREEN_GOLD_BARS = BLOCKS.register("green_gold_bars", () -> new MetalBarsBlock(15728547));
    public static final DeferredBlock<Block> BLUE_GOLD_BARS = BLOCKS.register("blue_gold_bars", () -> new MetalBarsBlock(8695295));
    public static final DeferredBlock<Block> PURPLE_GOLD_BARS = BLOCKS.register("purple_gold_bars", () -> new MetalBarsBlock(11836415));
    public static final DeferredBlock<Block> BLACK_GOLD_BARS = BLOCKS.register("black_gold_bars", () -> new MetalBarsBlock(3684408));
    public static final DeferredBlock<Block> PEWTER_BARS = BLOCKS.register("pewter_bars", () -> new MetalBarsBlock(11711154));
    public static final DeferredBlock<Block> BRONZE_BARS = BLOCKS.register("bronze_bars", () -> new MetalBarsBlock(15510384));
    public static final DeferredBlock<Block> BRASS_BARS = BLOCKS.register("brass_bars", () -> new MetalBarsBlock(16762368));
    public static final DeferredBlock<Block> CAST_IRON_BARS = BLOCKS.register("cast_iron_bars", () -> new MetalBarsBlock(4408131));
    public static final DeferredBlock<Block> INVAR_BARS = BLOCKS.register("invar_bars", () -> new MetalBarsBlock(13028546));
    public static final DeferredBlock<Block> CUPRONICKEL_BARS = BLOCKS.register("cupronickel_bars", () -> new MetalBarsBlock(11946807));
    public static final DeferredBlock<Block> DURALUMIN_BARS = BLOCKS.register("duralumin_bars", () -> new MetalBarsBlock(8887223));
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_BARS = BLOCKS.register("magnesium_alloy_bars", () -> new MetalBarsBlock(6841956));
    public static final DeferredBlock<Block> STERLING_SILVER_BARS = BLOCKS.register("sterling_silver_bars", () -> new MetalBarsBlock(15723237));
    public static final DeferredBlock<Block> NICKEL_SILVER_BARS = BLOCKS.register("nickel_silver_bars", () -> new MetalBarsBlock(9608841));
    public static final DeferredBlock<Block> TITANIUM_ALLOY_BARS = BLOCKS.register("titanium_alloy_bars", () -> new MetalBarsBlock(13750746));
    public static final DeferredBlock<Block> NITINOL_BARS = BLOCKS.register("nitinol_bars", () -> new MetalBarsBlock(12882104));
    public static final DeferredBlock<Block> ALNICO_BARS = BLOCKS.register("alnico_bars", () -> new MetalBarsBlock(16730178));
    public static final DeferredBlock<Block> STEEL_BARS = BLOCKS.register("steel_bars", () -> new MetalBarsBlock(7634311));
    public static final DeferredBlock<Block> STAINLESS_STEEL_BARS = BLOCKS.register("stainless_steel_bars", () -> new MetalBarsBlock(13292499));
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_BARS = BLOCKS.register("nickel_superalloy_bars", () -> new MetalBarsBlock(8559522));
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_BARS = BLOCKS.register("tungsten_heavy_alloy_bars", () -> new MetalBarsBlock(11113071));
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_BARS = BLOCKS.register("cobalt_superalloy_bars", () -> new MetalBarsBlock(6534878));
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_BARS = BLOCKS.register("niobium_alloy_bars", () -> new MetalBarsBlock(4533618));
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_BARS = BLOCKS.register("zirconium_alloy_bars", () -> new MetalBarsBlock(14271685));
    public static final DeferredBlock<Block> ROSE_METAL_BARS = BLOCKS.register("rose_metal_bars", () -> new MetalBarsBlock(6235970));
    public static final DeferredBlock<Block> MISCHMETAL_BARS = BLOCKS.register("mischmetal_bars", () -> new MetalBarsBlock(7304289));
    public static final DeferredBlock<Block> FERROCERIUM_BARS = BLOCKS.register("ferrocerium_bars", () -> new MetalBarsBlock(7433071));
    public static final DeferredBlock<Block> GALINSTAN_BARS = BLOCKS.register("galinstan_bars", () -> new MetalBarsBlock(12564673));
    public static final DeferredBlock<Block> OSMIRIDIUM_BARS = BLOCKS.register("osmiridium_bars", () -> new MetalBarsBlock(13212593));
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_BARS = BLOCKS.register("sodium_potassium_alloy_bars", () -> new MetalBarsBlock(9403777));
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_BARS = BLOCKS.register("neptunium_alloy_bars", () -> new MetalBarsBlock(5805717));
    public static final DeferredBlock<Block> AMALGAM_BARS = BLOCKS.register("amalgam_bars", () -> new MetalBarsBlock(13881539));
    public static final DeferredBlock<Block> ENDER_AMALGAM_BARS = BLOCKS.register("ender_amalgam_bars", () -> new MetalBarsBlock(492385));

    public static final DeferredBlock<Block> ROSE_GOLD_LADDER = BLOCKS.register("rose_gold_ladder", () -> new MetalLadderBlock(16756647));
    public static final DeferredBlock<Block> WHITE_GOLD_LADDER = BLOCKS.register("white_gold_ladder", () -> new MetalLadderBlock(16777207));
    public static final DeferredBlock<Block> GREEN_GOLD_LADDER = BLOCKS.register("green_gold_ladder", () -> new MetalLadderBlock(15728547));
    public static final DeferredBlock<Block> BLUE_GOLD_LADDER = BLOCKS.register("blue_gold_ladder", () -> new MetalLadderBlock(8695295));
    public static final DeferredBlock<Block> PURPLE_GOLD_LADDER = BLOCKS.register("purple_gold_ladder", () -> new MetalLadderBlock(11836415));
    public static final DeferredBlock<Block> BLACK_GOLD_LADDER = BLOCKS.register("black_gold_ladder", () -> new MetalLadderBlock(3684408));
    public static final DeferredBlock<Block> PEWTER_LADDER = BLOCKS.register("pewter_ladder", () -> new MetalLadderBlock(11711154));
    public static final DeferredBlock<Block> BRONZE_LADDER = BLOCKS.register("bronze_ladder", () -> new MetalLadderBlock(15510384));
    public static final DeferredBlock<Block> BRASS_LADDER = BLOCKS.register("brass_ladder", () -> new MetalLadderBlock(16762368));
    public static final DeferredBlock<Block> CAST_IRON_LADDER = BLOCKS.register("cast_iron_ladder", () -> new MetalLadderBlock(4408131));
    public static final DeferredBlock<Block> INVAR_LADDER = BLOCKS.register("invar_ladder", () -> new MetalLadderBlock(13028546));
    public static final DeferredBlock<Block> CUPRONICKEL_LADDER = BLOCKS.register("cupronickel_ladder", () -> new MetalLadderBlock(11946807));
    public static final DeferredBlock<Block> DURALUMIN_LADDER = BLOCKS.register("duralumin_ladder", () -> new MetalLadderBlock(8887223));
    public static final DeferredBlock<Block> MAGNESIUM_ALLOY_LADDER = BLOCKS.register("magnesium_alloy_ladder", () -> new MetalLadderBlock(6841956));
    public static final DeferredBlock<Block> STERLING_SILVER_LADDER = BLOCKS.register("sterling_silver_ladder", () -> new MetalLadderBlock(15723237));
    public static final DeferredBlock<Block> NICKEL_SILVER_LADDER = BLOCKS.register("nickel_silver_ladder", () -> new MetalLadderBlock(9608841));
    public static final DeferredBlock<Block> TITANIUM_ALLOY_LADDER = BLOCKS.register("titanium_alloy_ladder", () -> new MetalLadderBlock(13750746));
    public static final DeferredBlock<Block> NITINOL_LADDER = BLOCKS.register("nitinol_ladder", () -> new MetalLadderBlock(12882104));
    public static final DeferredBlock<Block> ALNICO_LADDER = BLOCKS.register("alnico_ladder", () -> new MetalLadderBlock(16730178));
    public static final DeferredBlock<Block> STEEL_LADDER = BLOCKS.register("steel_ladder", () -> new MetalLadderBlock(7634311));
    public static final DeferredBlock<Block> STAINLESS_STEEL_LADDER = BLOCKS.register("stainless_steel_ladder", () -> new MetalLadderBlock(13292499));
    public static final DeferredBlock<Block> NICKEL_SUPERALLOY_LADDER = BLOCKS.register("nickel_superalloy_ladder", () -> new MetalLadderBlock(8559522));
    public static final DeferredBlock<Block> TUNGSTEN_HEAVY_ALLOY_LADDER = BLOCKS.register("tungsten_heavy_alloy_ladder", () -> new MetalLadderBlock(11113071));
    public static final DeferredBlock<Block> COBALT_SUPERALLOY_LADDER = BLOCKS.register("cobalt_superalloy_ladder", () -> new MetalLadderBlock(6534878));
    public static final DeferredBlock<Block> NIOBIUM_ALLOY_LADDER = BLOCKS.register("niobium_alloy_ladder", () -> new MetalLadderBlock(4533618));
    public static final DeferredBlock<Block> ZIRCONIUM_ALLOY_LADDER = BLOCKS.register("zirconium_alloy_ladder", () -> new MetalLadderBlock(14271685));
    public static final DeferredBlock<Block> ROSE_METAL_LADDER = BLOCKS.register("rose_metal_ladder", () -> new MetalLadderBlock(6235970));
    public static final DeferredBlock<Block> MISCHMETAL_LADDER = BLOCKS.register("mischmetal_ladder", () -> new MetalLadderBlock(7304289));
    public static final DeferredBlock<Block> FERROCERIUM_LADDER = BLOCKS.register("ferrocerium_ladder", () -> new MetalLadderBlock(7433071));
    public static final DeferredBlock<Block> GALINSTAN_LADDER = BLOCKS.register("galinstan_ladder", () -> new MetalLadderBlock(12564673));
    public static final DeferredBlock<Block> OSMIRIDIUM_LADDER = BLOCKS.register("osmiridium_ladder", () -> new MetalLadderBlock(13212593));
    public static final DeferredBlock<Block> SODIUM_POTASSIUM_ALLOY_LADDER = BLOCKS.register("sodium_potassium_alloy_ladder", () -> new MetalLadderBlock(9403777));
    public static final DeferredBlock<Block> NEPTUNIUM_ALLOY_LADDER = BLOCKS.register("neptunium_alloy_ladder", () -> new MetalLadderBlock(5805717));
    public static final DeferredBlock<Block> AMALGAM_LADDER = BLOCKS.register("amalgam_ladder", () -> new MetalLadderBlock(13881539));
    public static final DeferredBlock<Block> ENDER_AMALGAM_LADDER = BLOCKS.register("ender_amalgam_ladder", () -> new MetalLadderBlock(492385));






    
    //GEM AND MINERALS
    public static final DeferredBlock<Block> LEPIDOLITE_BLOCK = BLOCKS.register("lepidolite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PYRITE_BLOCK = BLOCKS.register("pyrite_block", () -> new SparkingBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> HEDENBERGITE_BLOCK = BLOCKS.register("hedenbergite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CHALCOCITE_BLOCK = BLOCKS.register("chalcocite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> COOPERITE_BLOCK = BLOCKS.register("cooperite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SANIDINE_BLOCK = BLOCKS.register("sanidine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SPODUMENE_BLOCK = BLOCKS.register("spodumene_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> TALC_BLOCK = BLOCKS.register("talc_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SERPENTINE_BLOCK = BLOCKS.register("serpentine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> APATITE_BLOCK = BLOCKS.register("apatite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GYPSUM_BLOCK = BLOCKS.register("gypsum_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LAURITE_BLOCK = BLOCKS.register("laurite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LAUTARITE_BLOCK = BLOCKS.register("lautarite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> REALGAR_BLOCK = BLOCKS.register("realgar_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RUTILE_BLOCK = BLOCKS.register("rutile_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> DIOPSIDE_BLOCK = BLOCKS.register("diopside_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GOETHITE_BLOCK = BLOCKS.register("goethite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> YTTRIUM_MONAZITE_BLOCK = BLOCKS.register("yttrium_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> AZURMALACHITE_BLOCK = BLOCKS.register("azurmalachite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> MALACHITE_BLOCK = BLOCKS.register("malachite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> AZURITE_BLOCK = BLOCKS.register("azurite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LABRADORITE_BLOCK = BLOCKS.register("labradorite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> KYANITE_BLOCK = BLOCKS.register("kyanite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> FLUORITE_BLOCK = BLOCKS.register("fluorite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> AQUAMARINE_BLOCK = BLOCKS.register("aquamarine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SAPPHIRE_BLOCK = BLOCKS.register("sapphire_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> TIGER_IRON_BLOCK = BLOCKS.register("tiger_iron_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> OPAL_BLOCK = BLOCKS.register("opal_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GARNET_BLOCK = BLOCKS.register("garnet_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PERIDOT_BLOCK = BLOCKS.register("peridot_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> AMBER_BLOCK = BLOCKS.register("amber_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> TOURMALINE_BLOCK = BLOCKS.register("tourmaline_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PEARL_BLOCK = BLOCKS.register("pearl_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LONSDALEITE_DIAMOND_BLOCK = BLOCKS.register("lonsdaleite_diamond_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LORANDITE_BLOCK = BLOCKS.register("lorandite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> POLLUCITE_BLOCK = BLOCKS.register("pollucite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CELESTINE_BLOCK = BLOCKS.register("celestine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CINNABAR_BLOCK = BLOCKS.register("cinnabar_block", () -> new CinnabarBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(5.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> PERLITE_BLOCK = BLOCKS.register("perlite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> IRON_CHLORIDE_BLOCK = BLOCKS.register("iron_chloride_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RHODONITE_BLOCK = BLOCKS.register("rhodonite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RHODOCHROSITE_BLOCK = BLOCKS.register("rhodochrosite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GRAPHITE_BLOCK = BLOCKS.register("graphite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ORTHOCLASE_FELDSPAR_BLOCK = BLOCKS.register("orthoclase_feldspar_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> PLAGIOCLASE_FELDSPAR_BLOCK = BLOCKS.register("plagioclase_feldspar_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> OLIVINE_BLOCK = BLOCKS.register("olivine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> PYROXENE_BLOCK = BLOCKS.register("pyroxene_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> BIOTITE_BLOCK = BLOCKS.register("biotite_block", () -> new TranslucentMineralBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final DeferredBlock<Block> MUSCOVITE_BLOCK = BLOCKS.register("muscovite_block", () -> new TranslucentMineralBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final DeferredBlock<Block> AMPHIBOLE_BLOCK = BLOCKS.register("amphibole_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> FULGURITE = BLOCKS.register("fulgurite", () -> new Block(Block.Properties.of().mapColor(MapColor.GRASS).instrument(NoteBlockInstrument.BANJO).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> DOLOMITE_BLOCK = BLOCKS.register("dolomite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> MAGNESIUM_CHLORIDE_BLOCK = BLOCKS.register("magnesium_chloride_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> SODIUM_CHLORIDE_BLOCK = BLOCKS.register("sodium_chloride_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final DeferredBlock<Block> CALCIUM_CHLORIDE_BLOCK = BLOCKS.register("calcium_chloride_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> PINK_SALT_BLOCK = BLOCKS.register("pink_salt_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final DeferredBlock<Block> CALCIUM_SILICATE_BLOCK = BLOCKS.register("calcium_silicate_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final DeferredBlock<Block> SILICON_CARBIDE_BLOCK = BLOCKS.register("silicon_carbide_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ENSTATITE_BLOCK = BLOCKS.register("enstatite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CHROME_ENSTATITE_BLOCK = BLOCKS.register("chrome_enstatite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SCHEELITE_BLOCK = BLOCKS.register("scheelite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> NEPHELINE_BLOCK = BLOCKS.register("nepheline_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PYROCHLORE_BLOCK = BLOCKS.register("pyrochlore_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CASSITERITE_BLOCK = BLOCKS.register("cassiterite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> BAUXITE_BLOCK = BLOCKS.register("bauxite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SPHALERITE_BLOCK = BLOCKS.register("sphalerite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> MAGNETITE_BLOCK = BLOCKS.register("magnetite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> HEMATITE_BLOCK = BLOCKS.register("hematite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PENTLANDITE_BLOCK = BLOCKS.register("pentlandite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GALENA_BLOCK = BLOCKS.register("galena_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GADOLINIUM_MONAZITE_BLOCK = BLOCKS.register("gadolinium_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> VANADINITE_BLOCK = BLOCKS.register("vanadinite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> BISMUTHINITE_BLOCK = BLOCKS.register("bismuthinite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ACANTHITE_BLOCK = BLOCKS.register("acanthite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PYROLUSITE_BLOCK = BLOCKS.register("pyrolusite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CHROMITE_BLOCK = BLOCKS.register("chromite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> MOLYBDENITE_BLOCK = BLOCKS.register("molybdenite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ILMENITE_BLOCK = BLOCKS.register("ilmenite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> COLUMBITE_BLOCK = BLOCKS.register("columbite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> WOLFRAMITE_BLOCK = BLOCKS.register("wolframite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> RHENIITE_BLOCK = BLOCKS.register("rheniite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> TANTALITE_BLOCK = BLOCKS.register("tantalite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> GREENOCKITE_BLOCK = BLOCKS.register("greenockite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> URANINITE_BLOCK = BLOCKS.register("uraninite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> THORITE_BLOCK = BLOCKS.register("thorite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> STIBNITE_BLOCK = BLOCKS.register("stibnite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> XENOTIME_BLOCK = BLOCKS.register("xenotime_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PETALITE_BLOCK = BLOCKS.register("petalite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> COBALTITE_BLOCK = BLOCKS.register("cobaltite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CRYOLITE_BLOCK = BLOCKS.register("cryolite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CHALCOPYRITE_BLOCK = BLOCKS.register("chalcopyrite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> BARITE_BLOCK = BLOCKS.register("barite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> BADDELEYITE_BLOCK = BLOCKS.register("baddeleyite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> THORTVEITITE_BLOCK = BLOCKS.register("thortveitite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CERIUM_MONAZITE_BLOCK = BLOCKS.register("cerium_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> CHLORITE_BLOCK = BLOCKS.register("chlorite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> LANTHANUM_MONAZITE_BLOCK = BLOCKS.register("lanthanum_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> NEODYMIUM_MONAZITE_BLOCK = BLOCKS.register("neodymium_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> SAMARIUM_MONAZITE_BLOCK = BLOCKS.register("samarium_monazite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ZIRCON_BLOCK = BLOCKS.register("zircon_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> HAFNIA_BLOCK = BLOCKS.register("hafnia_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> ZIRCONIA_BLOCK = BLOCKS.register("zirconia_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> PLATINUM_ARSENIDE_BLOCK = BLOCKS.register("platinum_arsenide_block", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final DeferredBlock<Block> COKE_BLOCK = BLOCKS.register("coke_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> LIGNITE_BLOCK = BLOCKS.register("lignite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> SUBBITUMINOUS_COAL_BLOCK = BLOCKS.register("subbituminous_coal_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> BITUMINOUS_COAL_BLOCK = BLOCKS.register("bituminous_coal_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> ANTHRACITE_COAL_BLOCK = BLOCKS.register("anthracite_coal_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> ANDESINE_BLOCK = BLOCKS.register("andesine_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final DeferredBlock<Block> GEODE = BLOCKS.register("geode", () -> new GeodeBlock(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    //ORES
    public static final DeferredBlock<Block> CHALCOCITE_ORE = BLOCKS.register("chalcocite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> PETALITE_ORE = BLOCKS.register("petalite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> LIGNITE_ORE = BLOCKS.register("lignite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> SUBBITUMINOUS_ORE = BLOCKS.register("subbituminous_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,2)));
    public static final DeferredBlock<Block> BITUMINOUS_ORE = BLOCKS.register("bituminous_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> ANTHRACITE_ORE = BLOCKS.register("anthracite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,4)));
    public static final DeferredBlock<Block> MAGNESITE_ORE = BLOCKS.register("magnesite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_SULFUR_ORE = BLOCKS.register("native_sulfur_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,2)));
    public static final DeferredBlock<Block> ILMENITE_ORE = BLOCKS.register("ilmenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> CHROMITE_ORE = BLOCKS.register("chromite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> PYROLUSITE_ORE = BLOCKS.register("pyrolusite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> MAGNETITE_ORE = BLOCKS.register("magnetite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> HEMATITE_ORE = BLOCKS.register("hematite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> COBALTITE_ORE = BLOCKS.register("cobaltite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> PENTLANDITE_ORE = BLOCKS.register("pentlandite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> INTERSPINIFEX_ORE = BLOCKS.register("interspinifex_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> SPHALERITE_ORE = BLOCKS.register("sphalerite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_GALLIUM_ORE = BLOCKS.register("native_gallium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> NATIVE_ARSENIC_ORE = BLOCKS.register("native_arsenic_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> NATIVE_SELENIUM_ORE = BLOCKS.register("native_selenium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> CELESTINE_ORE = BLOCKS.register("celestine_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> XENOTIME_ORE = BLOCKS.register("xenotime_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> BADDELEYITE_ORE = BLOCKS.register("baddeleyite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> COLTAN_ORE = BLOCKS.register("coltan_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> MOLYBDENITE_ORE = BLOCKS.register("molybdenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_SILVER_ORE = BLOCKS.register("native_silver_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> ACANTHITE_ORE = BLOCKS.register("acanthite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> GREENOCKITE_ORE = BLOCKS.register("greenockite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_INDIUM_ORE = BLOCKS.register("native_indium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> NATIVE_TIN_ORE = BLOCKS.register("native_tin_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> CASSITERITE_ORE = BLOCKS.register("cassiterite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> STIBNITE_ORE = BLOCKS.register("stibnite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> NATIVE_TELLURIUM_ORE = BLOCKS.register("native_tellurium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final DeferredBlock<Block> WOLFRAMITE_ORE = BLOCKS.register("wolframite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> RHENIITE_ORE = BLOCKS.register("rheniite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_GOLD_ORE = BLOCKS.register("native_gold_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> NATIVE_LEAD_ORE = BLOCKS.register("native_lead_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> GALENA_ORE = BLOCKS.register("galena_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> NATIVE_BISMUTH_ORE = BLOCKS.register("native_bismuth_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final DeferredBlock<Block> BISMUTHINITE_ORE = BLOCKS.register("bismuthinite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> URANINITE_ORE = BLOCKS.register("uraninite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> PLUMBAGO_ORE = BLOCKS.register("plumbago_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,5)));
    public static final DeferredBlock<Block> SPERRYLITE_ORE = BLOCKS.register("sperrylite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> CINNABAR_ORE = BLOCKS.register("cinnabar_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,4)));
    public static final DeferredBlock<Block> CRYOLITE_ORE = BLOCKS.register("cryolite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> PYRITE_ORE = BLOCKS.register("pyrite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> KAMACITE_ORE = BLOCKS.register("kamacite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> ANTITAENITE_ORE = BLOCKS.register("antitaenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> TAENITE_ORE = BLOCKS.register("taenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> TETRATAENITE_ORE = BLOCKS.register("tetrataenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> LONSDALEITE_ORE = BLOCKS.register("lonsdaleite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(5,10)));
    public static final DeferredBlock<Block> MONAZITE_ORE = BLOCKS.register("monazite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final DeferredBlock<Block> LAZURITE_ORE = BLOCKS.register("lazurite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,5)));
    public static final DeferredBlock<Block> BERYL_ORE = BLOCKS.register("beryl_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(3,7)));

    public static final DeferredBlock<Block> COAL_ORE = BLOCKS.register("coal_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE), UniformInt.of(0,2)));
    public static final DeferredBlock<Block> IRON_ORE = BLOCKS.register("iron_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
    public static final DeferredBlock<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)));
    public static final DeferredBlock<Block> GOLD_ORE = BLOCKS.register("gold_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)));
    public static final DeferredBlock<Block> REDSTONE_ORE = BLOCKS.register("redstone_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_ORE)));
    public static final DeferredBlock<Block> LAPIS_ORE = BLOCKS.register("lapis_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_ORE), UniformInt.of(2,5)));
    public static final DeferredBlock<Block> DIAMOND_ORE = BLOCKS.register("diamond_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE), UniformInt.of(3,7)));
    public static final DeferredBlock<Block> EMERALD_ORE = BLOCKS.register("emerald_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_ORE), UniformInt.of(3,7)));
    public static final DeferredBlock<Block> NETHER_QUARTZ_ORE = BLOCKS.register("nether_quartz_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE), UniformInt.of(2,5)));
    public static final DeferredBlock<Block> NETHER_GOLD_ORE = BLOCKS.register("nether_gold_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE), UniformInt.of(0,1)));

    public static final DeferredBlock<Block> METEORITE = BLOCKS.register("meteorite", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> METEORITE_BRICKS = BLOCKS.register("meteorite_bricks", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> ENSTATITE_CHONDRITE = BLOCKS.register("enstatite_chondrite", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> ENSTATITE_CHONDRITE_BRICKS = BLOCKS.register("enstatite_chondrite_bricks", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> FROZEN_METEORITE = BLOCKS.register("frozen_meteorite", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> FROZEN_METEORITE_BRICKS = BLOCKS.register("frozen_meteorite_bricks", () -> new Block(DEF_STONE));
    public static final DeferredBlock<Block> GREEN_TEKTITE = BLOCKS.register("green_tektite", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GRAY_TEKTITE = BLOCKS.register("gray_tektite", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BLACK_TEKTITE = BLOCKS.register("black_tektite", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BROWN_TEKTITE = BLOCKS.register("brown_tektite", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ANTIMATTER = BLOCKS.register("antimatter", () -> new AntimatterBlock(Block.Properties.of().mapColor(MapColor.NONE).strength(-1.0F, 3600000.8F).noOcclusion().noCollission().noDrops()));
    public static final DeferredBlock<Block> UNAMED_EXPLOSIVE = BLOCKS.register("unamed_explosive", () -> new UnamedExplosiveBlock(Block.Properties.of().mapColor(MapColor.NONE).strength(20.0F, 50.0F)));


    public static final DeferredBlock<Block> CORK = BLOCKS.register("cork", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(1.0F, 2.0F)));
    public static final DeferredBlock<Block> VULCANIZED_RUBBER_BLOCK = BLOCKS.register("vulcanized_rubber_block", () -> new RubberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).friction(0.8F).strength(1.0F, 2.0F).sound(SoundType.SLIME_BLOCK)));
    public static final DeferredBlock<Block> FLOOD_GATE = BLOCKS.register("flood_gate", () -> new FloodGateBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 10.0F).noOcclusion()));


    public static final DeferredBlock<Block> ALUMINUM_SHEETMETAL = BLOCKS.register("aluminum_sheetmetal", () -> new SheetmetalBlock(14734290));
    public static final DeferredBlock<Block> TITANIUM_SHEETMETAL = BLOCKS.register("titanium_sheetmetal", () -> new SheetmetalBlock(11711163));
    public static final DeferredBlock<Block> IRON_SHEETMETAL = BLOCKS.register("iron_sheetmetal", () -> new SheetmetalBlock(14211288));
    public static final DeferredBlock<Block> NICKEL_SHEETMETAL = BLOCKS.register("nickel_sheetmetal", () -> new SheetmetalBlock(13558993));
    public static final DeferredBlock<Block> COPPER_SHEETMETAL = BLOCKS.register("copper_sheetmetal", () -> new SheetmetalBlock(15170646));
    public static final DeferredBlock<Block> TIN_SHEETMETAL = BLOCKS.register("tin_sheetmetal", () -> new SheetmetalBlock(10595770));
    public static final DeferredBlock<Block> TUNGSTEN_SHEETMETAL = BLOCKS.register("tungsten_sheetmetal", () -> new SheetmetalBlock(9272689));
    public static final DeferredBlock<Block> LEAD_SHEETMETAL = BLOCKS.register("lead_sheetmetal", () -> new SheetmetalBlock(9341867));
    public static final DeferredBlock<Block> BISMUTH_SHEETMETAL = BLOCKS.register("bismuth_sheetmetal", () -> new SheetmetalBlock(14401722));
    public static final DeferredBlock<Block> CAST_IRON_SUPPORT = BLOCKS.register("cast_iron_support", () -> new Block(Block.Properties.of().mapColor(MapColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final DeferredBlock<Block> CAST_IRON_SUPPORT_SLAB = BLOCKS.register("cast_iron_support_slab", () -> new RankineSlabBlock(Block.Properties.of().mapColor(MapColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final DeferredBlock<Block> CAST_IRON_SUPPORT_STAIRS = BLOCKS.register("cast_iron_support_stairs", () -> new RankineStairsBlock(Block.Properties.of().mapColor(MapColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final DeferredBlock<Block> CAST_IRON_SUPPORT_WALL = BLOCKS.register("cast_iron_support_wall", () -> new RankineWallBlock(Block.Properties.of().mapColor(MapColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));


    public static final DeferredBlock<Block> BLASTING_POWDER = BLOCKS.register("blasting_powder", () -> new BlastingPowderBlock(Block.Properties.of().mapColor(MapColor.SAND).sound(SoundType.SAND).strength(1)));
    public static final DeferredBlock<Block> GWIHABAITE_CRYSTAL = BLOCKS.register("gwihabaite_crystal", () -> new GwihabaiteBlock(DEF_ORE));

    public static final DeferredBlock<Block> SAP_CAULDRON = BLOCKS.register("sap_cauldron", SapCauldronBlock::new);
    public static final DeferredBlock<Block> MAPLE_SAP_CAULDRON = BLOCKS.register("maple_sap_cauldron", MapleSapCauldronBlock::new);
    public static final DeferredBlock<Block> MAPLE_SYRUP_CAULDRON = BLOCKS.register("maple_syrup_cauldron", MapleSyrupCauldronBlock::new);
    public static final DeferredBlock<Block> LATEX_CAULDRON = BLOCKS.register("latex_cauldron", LatexCauldronBlock::new);
    public static final DeferredBlock<Block> RESIN_CAULDRON = BLOCKS.register("resin_cauldron", ResinCauldronBlock::new);
    public static final DeferredBlock<Block> JUGLONE_CAULDRON = BLOCKS.register("juglone_cauldron", JugloneCauldronBlock::new);

    public static final DeferredBlock<Block> BEEHIVE_OVEN_PIT = BLOCKS.register("beehive_oven_pit", () -> new BeehiveOvenPitBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> MIXING_BARREL = BLOCKS.register("mixing_barrel", () -> new MixingBarrelBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()));
    public static final DeferredBlock<Block> CRUCIBLE_BLOCK = BLOCKS.register("crucible", () -> new CrucibleBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litCrucibleEmission(13))));
    public static final DeferredBlock<Block> MATERIAL_TESTING_TABLE = BLOCKS.register("material_testing_table", () -> new MaterialTestingTableBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(2.0F)));
    public static final DeferredBlock<Block> TEMPLATE_TABLE = BLOCKS.register("template_table", () -> new TemplateTableBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(2.0F)));
    public static final DeferredBlock<Block> ALLOY_FURNACE = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litBlockEmission(13))));
    public static final DeferredBlock<Block> PISTON_CRUSHER = BLOCKS.register("piston_crusher", () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> PCF = BLOCKS.register("pcf", () -> new Block(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> INDUCTION_FURNACE = BLOCKS.register("induction_furnace", () -> new InductionFurnaceBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litBlockEmission(13))));
    public static final DeferredBlock<Block> GYRATORY_CRUSHER = BLOCKS.register("gyratory_crusher", () -> new Block(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> EVAPORATION_TOWER = BLOCKS.register("evaporation_tower", () -> new EvaporationTowerBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> DIAMOND_ANVIL_CELL = BLOCKS.register("diamond_anvil_cell", () -> new DiamondAnvilCellBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> PARTICLE_ACCELERATOR = BLOCKS.register("particle_accelerator", () -> new ParticleAcceleratorBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> BATTERY_CHARGER = BLOCKS.register("battery_charger", () -> new BatteryChargerBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> FUSION_FURNACE = BLOCKS.register("fusion_furnace", () -> new FusionFurnaceBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> DISTILLATION_TOWER = BLOCKS.register("distillation_tower", () -> new DistillationTowerBlock(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> AIR_DISTILLATION_PACKING = BLOCKS.register("air_distillation_packing", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> REACTION_CHAMBER_CORE = BLOCKS.register("reaction_chamber_core", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> REACTION_CHAMBER_CELL = BLOCKS.register("reaction_chamber_cell", () -> new GlassBlock(Block.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).strength(6.0F, 30.0F).sound(SoundType.GLASS).noOcclusion()));

    public static final DeferredBlock<Block> GAS_BOTTLER = BLOCKS.register("gas_bottler", () -> new GasBottlerBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> GAS_VENT = BLOCKS.register("gas_vent", () -> new GasVentBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final DeferredBlock<Block> CHARCOAL_PIT = BLOCKS.register("charcoal_pit", () -> new CharcoalPitBlock(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(1.0F)));
    public static final DeferredBlock<Block> TREE_TAP = BLOCKS.register("tree_tap", () -> new TreeTapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(1.0F).noOcclusion()));
    public static final DeferredBlock<Block> TAP_LINE = BLOCKS.register("tap_line", () -> new TapLineBlock(0.125f, BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().sound(SoundType.WOOL).strength(0.5F).noOcclusion()));
    public static final DeferredBlock<Block> HEATING_ELEMENT_1 = BLOCKS.register("heating_element_1", () -> new HeatingElementBlock(13,Block.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(4)));


    public static final DeferredBlock<Block> RED_LED = BLOCKS.register("red_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> ORANGE_LED = BLOCKS.register("orange_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> YELLOW_LED = BLOCKS.register("yellow_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> LIME_LED = BLOCKS.register("lime_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> GREEN_LED = BLOCKS.register("green_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> CYAN_LED = BLOCKS.register("cyan_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> LIGHT_BLUE_LED = BLOCKS.register("light_blue_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> BLUE_LED = BLOCKS.register("blue_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> MAGENTA_LED = BLOCKS.register("magenta_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> PURPLE_LED = BLOCKS.register("purple_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> PINK_LED = BLOCKS.register("pink_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> BROWN_LED = BLOCKS.register("brown_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> BLACK_LED = BLOCKS.register("black_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> GRAY_LED = BLOCKS.register("gray_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> LIGHT_GRAY_LED = BLOCKS.register("light_gray_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final DeferredBlock<Block> WHITE_LED = BLOCKS.register("white_led", () -> new LEDBlock((BlockBehaviour.Properties.of().mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));


    public static final DeferredBlock<Block> UNAGED_CHEESE = BLOCKS.register("unaged_cheese", () -> new UnagedCheeseBlock(BlockBehaviour.Properties.of().forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> AGED_CHEESE = BLOCKS.register("aged_cheese", () -> new CakeBlock(BlockBehaviour.Properties.of().forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.WOOL)));
    public static final DeferredBlock<Block> ELDERBERRY_BUSH = BLOCKS.register("elderberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 0));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 1));
    public static final DeferredBlock<Block> CRANBERRY_BUSH = BLOCKS.register("cranberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 2));
    public static final DeferredBlock<Block> POKEBERRY_BUSH = BLOCKS.register("pokeberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 3));
    public static final DeferredBlock<Block> SNOWBERRY_BUSH = BLOCKS.register("snowberry_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 1));
    public static final DeferredBlock<Block> RASPBERRY_BUSH = BLOCKS.register("raspberry_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 3));
    public static final DeferredBlock<Block> BLACKBERRY_BUSH = BLOCKS.register("blackberry_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 4));
    public static final DeferredBlock<Block> STRAWBERRY_BUSH = BLOCKS.register("strawberry_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 6));
    public static final DeferredBlock<Block> PINEAPPLE_BUSH = BLOCKS.register("pineapple_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 7));
    public static final DeferredBlock<Block> BANANA_YUCCA_BUSH = BLOCKS.register("banana_yucca_bush", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 8));
    public static final DeferredBlock<Block> ALOE_PLANT = BLOCKS.register("aloe_plant", () -> new RankinePlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 10));;
    public static final DeferredBlock<Block> ASPARAGUS_PLANT = BLOCKS.register("asparagus_plant", () -> new AsparagusPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.HARD_CROP)));
    public static final DeferredBlock<Block> CORN_PLANT = BLOCKS.register("corn_plant", () -> new CornPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.HARD_CROP)));
    public static final DeferredBlock<Block> CORN_STALK = BLOCKS.register("corn_stalk", () -> new CornStalkBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.HARD_CROP)));
    //public static final DeferredBlock<Block> ASPARAGUS_ROOT = BLOCKS.register("asparagus_root",  () -> new AsparagusRootBlock(Block.Properties.create(Material.EARTH).tickRandomly().sound(SoundType.GROUND).hardnessAndResistance(0.5F)));
    public static final DeferredBlock<Block> RICE_PLANT = BLOCKS.register("rice_plant", () -> new RicePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> COTTON_PLANT = BLOCKS.register("cotton_plant", () -> new CottonPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> JUTE_PLANT = BLOCKS.register("jute_plant", () -> new JutePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> CAMPHOR_BASIL_PLANT = BLOCKS.register("camphor_basil_plant", () -> new CamphorBasilPlantBlock(Block.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> OAT_PLANT = BLOCKS.register("oat_plant", () -> new OatPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> RYE_PLANT = BLOCKS.register("rye_plant", () -> new RyePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> BARLEY_PLANT = BLOCKS.register("barley_plant", () -> new BarleyPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> SORGHUM_PLANT = BLOCKS.register("sorghum_plant", () -> new SorghumPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> MILLET_PLANT = BLOCKS.register("millet_plant", () -> new MilletPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> SOYBEAN_PLANT = BLOCKS.register("soybean_plant", () -> new SoybeanPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));


    public static final DeferredBlock<Block> BARLEY_BALE = BLOCKS.register("barley_bale", () -> new HayBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> OAT_BALE = BLOCKS.register("oat_bale", () -> new HayBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> RYE_BALE = BLOCKS.register("rye_bale", () -> new HayBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> SORGHUM_BALE = BLOCKS.register("sorghum_bale", () -> new HayBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));
    public static final DeferredBlock<Block> MILLET_BALE = BLOCKS.register("millet_bale", () -> new HayBlock(Block.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BANJO).strength(0.5F).sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> ORNAMENT = BLOCKS.register("ornament", () -> new OrnamentBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.GLASS).strength(1.0F).noOcclusion()));




    public static final DeferredBlock<Block> ROPE = BLOCKS.register("rope", () -> new RopeBlock(Block.Properties.of().mapColor(MapColor.WOOL).ignitedByLava().sound(SoundType.WOOL).noCollission()));
    public static final DeferredBlock<Block> GROUND_TAP = BLOCKS.register("ground_tap", () -> new GroundTapBlock(Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> METAL_PIPE = BLOCKS.register("metal_pipe", () -> new MetalPipeBlock(0.25f, Block.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.0F)));
    public static final DeferredBlock<Block> BOTANIST_STATION = BLOCKS.register("botanist_station", () -> new Block(Block.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().sound(SoundType.WOOD).strength(2.0F)));

    public static final DeferredBlock<Block> SEDIMENT_FAN = BLOCKS.register("sediment_fan", () -> new SedimentFanBlock(Block.Properties.of().mapColor(MapColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    //public static final DeferredBlock<Block> FLUID_DRAIN = BLOCKS.register("fluid_drain", () -> new FluidDrainBlock(Block.Properties.create(Material.IRON, MapColor.AIR).sound(SoundType.METAL).setRequiresTool().hardnessAndResistance(5.0F,6.0F)));

    public static final DeferredBlock<Block> SIMPLE_ELECTROMAGNET = BLOCKS.register("simple_electromagnet", () -> new ElectromagnetBlock(1));
    public static final DeferredBlock<Block> ALNICO_ELECTROMAGNET = BLOCKS.register("alnico_electromagnet", () -> new ElectromagnetBlock(2));
    public static final DeferredBlock<Block> RARE_EARTH_ELECTROMAGNET = BLOCKS.register("rare_earth_electromagnet", () -> new ElectromagnetBlock(3));

    public static final DeferredBlock<Block> LIQUID_MERCURY = BLOCKS.register("liquid_mercury", () -> new MercuryFlowingFluidBlock(() -> RankineFluids.LIQUID_MERCURY, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> SAP = BLOCKS.register("sap", () -> new RankineFlowingFluidBlock(() -> RankineFluids.SAP, true, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> MAPLE_SAP = BLOCKS.register("maple_sap", () -> new RankineFlowingFluidBlock(() -> RankineFluids.MAPLE_SAP, true, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> LATEX = BLOCKS.register("latex", () -> new RankineFlowingFluidBlock(() -> RankineFluids.LATEX, true, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> RESIN = BLOCKS.register("resin", () -> new RankineFlowingFluidBlock(() -> RankineFluids.RESIN, true, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> JUGLONE = BLOCKS.register("juglone", () -> new RankineFlowingFluidBlock(() -> RankineFluids.JUGLONE, true, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> AQUA_REGIA = BLOCKS.register("aqua_regia", () -> new RankineFlowingFluidBlock(()-> RankineFluids.AQUA_REGIA, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> CARBON_DISULFIDE = BLOCKS.register("carbon_disulfide", () -> new CarbonDisulfideFlowingFluidBlock(()-> RankineFluids.CARBON_DISULFIDE, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> HEXAFLUOROSILICIC_ACID = BLOCKS.register("hexafluorosilicic_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.HEXAFLUOROSILICIC_ACID, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> HYDROBROMIC_ACID = BLOCKS.register("hydrobromic_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.HYDROBROMIC_ACID, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> GRAY_MUD = BLOCKS.register("gray_mud", () -> new RankineFlowingFluidBlock(()-> RankineFluids.GRAY_MUD, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> RED_MUD = BLOCKS.register("red_mud", () -> new RankineFlowingFluidBlock(()-> RankineFluids.RED_MUD, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> SULFURIC_ACID = BLOCKS.register("sulfuric_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.SULFURIC_ACID, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> BLACK_LIQUOR = BLOCKS.register("black_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.BLACK_LIQUOR, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> GREEN_LIQUOR = BLOCKS.register("green_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.GREEN_LIQUOR, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));
    public static final DeferredBlock<Block> WHITE_LIQUOR = BLOCKS.register("white_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.WHITE_LIQUOR, false, Block.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY).noCollission().strength(100.0F).noDrops()));

    //ELEMENT BLOCKS
    public static final DeferredBlock<Block> HYDROGEN_BLOCK = BLOCKS.register("hydrogen_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> HELIUM_BLOCK = BLOCKS.register("helium_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> LITHIUM_BLOCK = BLOCKS.register("lithium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BERYLLIUM_BLOCK = BLOCKS.register("beryllium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BORON_BLOCK = BLOCKS.register("boron_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CARBON_BLOCK = BLOCKS.register("carbon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NITROGEN_BLOCK = BLOCKS.register("nitrogen_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> OXYGEN_BLOCK = BLOCKS.register("oxygen_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> FLUORINE_BLOCK = BLOCKS.register("fluorine_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> NEON_BLOCK = BLOCKS.register("neon_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> SODIUM_BLOCK = BLOCKS.register("sodium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MAGNESIUM_BLOCK = BLOCKS.register("magnesium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SILICON_BLOCK = BLOCKS.register("silicon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PHOSPHORUS_BLOCK = BLOCKS.register("phosphorus_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SULFUR_BLOCK = BLOCKS.register("sulfur_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CHLORINE_BLOCK = BLOCKS.register("chlorine_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> ARGON_BLOCK = BLOCKS.register("argon_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> POTASSIUM_BLOCK = BLOCKS.register("potassium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CALCIUM_BLOCK = BLOCKS.register("calcium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SCANDIUM_BLOCK = BLOCKS.register("scandium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> VANADIUM_BLOCK = BLOCKS.register("vanadium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CHROMIUM_BLOCK = BLOCKS.register("chromium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MANGANESE_BLOCK = BLOCKS.register("manganese_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> COBALT_BLOCK = BLOCKS.register("cobalt_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NICKEL_BLOCK = BLOCKS.register("nickel_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ZINC_BLOCK = BLOCKS.register("zinc_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> GALLIUM_BLOCK = BLOCKS.register("gallium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> GERMANIUM_BLOCK = BLOCKS.register("germanium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ARSENIC_BLOCK = BLOCKS.register("arsenic_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SELENIUM_BLOCK = BLOCKS.register("selenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BROMINE_BLOCK = BLOCKS.register("bromine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> KRYPTON_BLOCK = BLOCKS.register("krypton_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> RUBIDIUM_BLOCK = BLOCKS.register("rubidium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> STRONTIUM_BLOCK = BLOCKS.register("strontium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> YTTRIUM_BLOCK = BLOCKS.register("yttrium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ZIRCONIUM_BLOCK = BLOCKS.register("zirconium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NIOBIUM_BLOCK = BLOCKS.register("niobium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MOLYBDENUM_BLOCK = BLOCKS.register("molybdenum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TECHNETIUM_BLOCK = BLOCKS.register("technetium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RUTHENIUM_BLOCK = BLOCKS.register("ruthenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RHODIUM_BLOCK = BLOCKS.register("rhodium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PALLADIUM_BLOCK = BLOCKS.register("palladium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CADMIUM_BLOCK = BLOCKS.register("cadmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> INDIUM_BLOCK = BLOCKS.register("indium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ANTIMONY_BLOCK = BLOCKS.register("antimony_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TELLURIUM_BLOCK = BLOCKS.register("tellurium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> IODINE_BLOCK = BLOCKS.register("iodine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> XENON_BLOCK = BLOCKS.register("xenon_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> CESIUM_BLOCK = BLOCKS.register("cesium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BARIUM_BLOCK = BLOCKS.register("barium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> LANTHANUM_BLOCK = BLOCKS.register("lanthanum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CERIUM_BLOCK = BLOCKS.register("cerium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PRASEODYMIUM_BLOCK = BLOCKS.register("praseodymium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NEODYMIUM_BLOCK = BLOCKS.register("neodymium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PROMETHIUM_BLOCK = BLOCKS.register("promethium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SAMARIUM_BLOCK = BLOCKS.register("samarium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> EUROPIUM_BLOCK = BLOCKS.register("europium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> GADOLINIUM_BLOCK = BLOCKS.register("gadolinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TERBIUM_BLOCK = BLOCKS.register("terbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> DYSPROSIUM_BLOCK = BLOCKS.register("dysprosium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> HOLMIUM_BLOCK = BLOCKS.register("holmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ERBIUM_BLOCK = BLOCKS.register("erbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> THULIUM_BLOCK = BLOCKS.register("thulium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> YTTERBIUM_BLOCK = BLOCKS.register("ytterbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> LUTETIUM_BLOCK = BLOCKS.register("lutetium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> HAFNIUM_BLOCK = BLOCKS.register("hafnium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TANTALUM_BLOCK = BLOCKS.register("tantalum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TUNGSTEN_BLOCK = BLOCKS.register("tungsten_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RHENIUM_BLOCK = BLOCKS.register("rhenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> OSMIUM_BLOCK = BLOCKS.register("osmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> IRIDIUM_BLOCK = BLOCKS.register("iridium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PLATINUM_BLOCK = BLOCKS.register("platinum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MERCURY_BLOCK = BLOCKS.register("mercury_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> THALLIUM_BLOCK = BLOCKS.register("thallium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BISMUTH_BLOCK = BLOCKS.register("bismuth_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> POLONIUM_BLOCK = BLOCKS.register("polonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ASTATINE_BLOCK = BLOCKS.register("astatine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RADON_BLOCK = BLOCKS.register("radon_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> FRANCIUM_BLOCK = BLOCKS.register("francium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RADIUM_BLOCK = BLOCKS.register("radium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ACTINIUM_BLOCK = BLOCKS.register("actinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> THORIUM_BLOCK = BLOCKS.register("thorium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PROTACTINIUM_BLOCK = BLOCKS.register("protactinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> URANIUM_BLOCK = BLOCKS.register("uranium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NEPTUNIUM_BLOCK = BLOCKS.register("neptunium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> PLUTONIUM_BLOCK = BLOCKS.register("plutonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> AMERICIUM_BLOCK = BLOCKS.register("americium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CURIUM_BLOCK = BLOCKS.register("curium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BERKELIUM_BLOCK = BLOCKS.register("berkelium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> CALIFORNIUM_BLOCK = BLOCKS.register("californium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> EINSTEINIUM_BLOCK = BLOCKS.register("einsteinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> FERMIUM_BLOCK = BLOCKS.register("fermium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MENDELEVIUM_BLOCK = BLOCKS.register("mendelevium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NOBELIUM_BLOCK = BLOCKS.register("nobelium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> LAWRENCIUM_BLOCK = BLOCKS.register("lawrencium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> RUTHERFORDIUM_BLOCK = BLOCKS.register("rutherfordium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> DUBNIUM_BLOCK = BLOCKS.register("dubnium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> SEABORGIUM_BLOCK = BLOCKS.register("seaborgium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> BOHRIUM_BLOCK = BLOCKS.register("bohrium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> HASSIUM_BLOCK = BLOCKS.register("hassium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MEITNERIUM_BLOCK = BLOCKS.register("meitnerium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> DARMSTADTIUM_BLOCK = BLOCKS.register("darmstadtium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> ROENTGENIUM_BLOCK = BLOCKS.register("roentgenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> COPERNICIUM_BLOCK = BLOCKS.register("copernicium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> NIHONIUM_BLOCK = BLOCKS.register("nihonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> FLEROVIUM_BLOCK = BLOCKS.register("flerovium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> MOSCOVIUM_BLOCK = BLOCKS.register("moscovium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> LIVERMORIUM_BLOCK = BLOCKS.register("livermorium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> TENNESSINE_BLOCK = BLOCKS.register("tennessine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final DeferredBlock<Block> OGANESSON_BLOCK = BLOCKS.register("oganesson_block", () -> new Block(DEF_METAL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> ENDOSITUM_BLOCK = BLOCKS.register("endositum_block", () -> new Block(DEF_METAL_BLOCK));


    public static final DeferredBlock<Block> SODIUM_VAPOR_LAMP = BLOCKS.register("sodium_vapor_lamp", () -> new SodiumVaporLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 15).noOcclusion()));

/*
    public static final DeferredBlock<Block> CALCITE_COLUMN = BLOCKS.register("calcite_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(CALCITE_BLOCK.get())));
    public static final DeferredBlock<Block> DOLOMITE_COLUMN = BLOCKS.register("dolomite_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(DOLOMITE_BLOCK.get())));
    public static final DeferredBlock<Block> SALT_COLUMN = BLOCKS.register("salt_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(SODIUM_CHLORIDE_BLOCK.get())));
    public static final DeferredBlock<Block> PINK_SALT_COLUMN = BLOCKS.register("pink_salt_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(PINK_SALT_BLOCK.get())));
    public static final DeferredBlock<Block> OPAL_COLUMN = BLOCKS.register("opal_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(OPAL_BLOCK.get())));
    public static final DeferredBlock<Block> QUARTZ_COLUMN = BLOCKS.register("quartz_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_BLOCK)));


 */

    //OTHER STUFFS

    // PORT-TODO(init): net.neoforged.neoforge.registries.ObjectHolder no longer exists in NeoForge 1.21.1 (the
    // reflective post-registration field-injection mechanism was removed). This block used to hold ~30 lazily
    // populated MenuType/BlockEntityType aliases (ALLOY_FURNACE_CONTAINER, ALLOY_FURNACE_TILE, etc.). The real,
    // modern registrations already live in RankineContainers.java (the *_CONTAINER fields, as
    // DeferredHolder<MenuType<?>, MenuType<X>>) and RankineBlockEntityTypes.java (the BlockEntityType fields,
    // named WITHOUT the _TILE suffix there, e.g. RankineBlockEntityTypes.ALLOY_FURNACE,
    // RankineBlockEntityTypes.GAS_BOTTLER for gas_condenser, RankineBlockEntityTypes.BEEHIVE_OVEN_PIT for
    // beehive_oven). A repo-wide search found zero references anywhere to any of these
    // RankineBlocks.*_CONTAINER / *_TILE fields (not even via the wildcard
    // `import static com.cannolicatfish.rankine.init.RankineBlocks.*;` in DistillationTowerTile.java), so they
    // were already-dead duplicate aliases before this port; removed rather than reintroduced as
    // DeferredHolder-typed fields with no consumers. If something later needs one of them, reference
    // RankineContainers.X_CONTAINER.get() / RankineBlockEntityTypes.X.get() directly at the point of use (both
    // are DeferredHolder and only safe to .get() after the registry RegisterEvent has fired).

    public static Block getBlock(String name) {
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("rankine", name));
        if (block != null) {
            return block;
        } else {
            return Blocks.AIR;
        }
    }


    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static ToIntFunction<BlockState> litCrucibleEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(CrucibleBlock.FLUID) ? p_50760_ : 0;
        };
    }
}
