package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceBlock;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlock;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import com.cannolicatfish.rankine.blocks.asphalt.*;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
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
import com.cannolicatfish.rankine.blocks.signs.RankineSignBlock;
import com.cannolicatfish.rankine.blocks.signs.RankineWallSignBlock;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.ToIntFunction;

public class RankineBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProjectRankine.MODID);


    //Base Properties
    public static Block.Properties IGNEOUS_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F);
    public static Block.Properties POLISHED_IGNEOUS_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F * Config.BLOCK_PROPERTIES.POLISHED_HARDNESS_MULT.get().floatValue(), 6.0F * Config.BLOCK_PROPERTIES.POLISHED_RESISTANCE_MULT.get().floatValue());
    public static Block.Properties IGNEOUS_STONE_BRICKS = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F * Config.BLOCK_PROPERTIES.BRICKS_HARDNESS_MULT.get().floatValue(), 6.0F * Config.BLOCK_PROPERTIES.BRICKS_RESISTANCE_MULT.get().floatValue());
    public static Block.Properties METAMORPHIC_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F);
    public static Block.Properties POLISHED_METAMORPHIC_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F * Config.BLOCK_PROPERTIES.POLISHED_HARDNESS_MULT.get().floatValue(), 6.0F * Config.BLOCK_PROPERTIES.POLISHED_RESISTANCE_MULT.get().floatValue());
    public static Block.Properties METAMORPHIC_STONE_BRICKS = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F * Config.BLOCK_PROPERTIES.BRICKS_HARDNESS_MULT.get().floatValue(), 6.0F * Config.BLOCK_PROPERTIES.BRICKS_RESISTANCE_MULT.get().floatValue());
    public static Block.Properties SEDIMENTARY_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.0F, 4.0F);
    public static Block.Properties POLISHED_SEDIMENTARY_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.0F * Config.BLOCK_PROPERTIES.POLISHED_HARDNESS_MULT.get().floatValue(), 4.0F * Config.BLOCK_PROPERTIES.POLISHED_RESISTANCE_MULT.get().floatValue());
    public static Block.Properties SEDIMENTARY_STONE_BRICKS = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.0F * Config.BLOCK_PROPERTIES.BRICKS_HARDNESS_MULT.get().floatValue(), 4.0F * Config.BLOCK_PROPERTIES.BRICKS_RESISTANCE_MULT.get().floatValue());


    public static Block.Properties DEF_STONE = Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F);
    public static Block.Properties DEF_ORE = Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4.5F, 3.0F);
    public static Block.Properties DEF_METAL_BLOCK = Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F);
    public static Block.Properties DEF_WOOD = Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F, 3.0F);
    public static Block.Properties DEF_LEAVES = Block.Properties.of(Material.LEAVES).randomTicks().strength(0.2F).sound(SoundType.GRASS).noOcclusion();



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


    public static final RegistryObject<Block> SOUL_SANDSTONE = BLOCKS.register("soul_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> SOUL_SANDSTONE_SLAB = BLOCKS.register("soul_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> SOUL_SANDSTONE_STAIRS = BLOCKS.register("soul_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> SOUL_SANDSTONE_WALL = BLOCKS.register("soul_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE = BLOCKS.register("smooth_soul_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE_SLAB = BLOCKS.register("smooth_soul_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE_STAIRS = BLOCKS.register("smooth_soul_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(SMOOTH_SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_SOUL_SANDSTONE_WALL = BLOCKS.register("smooth_soul_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(SMOOTH_SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_SOUL_SANDSTONE = BLOCKS.register("cut_soul_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> CUT_SOUL_SANDSTONE_SLAB = BLOCKS.register("cut_soul_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(CUT_SOUL_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_SOUL_SANDSTONE = BLOCKS.register("chiseled_soul_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> BLACK_SANDSTONE = BLOCKS.register("black_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> BLACK_SANDSTONE_SLAB = BLOCKS.register("black_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> BLACK_SANDSTONE_STAIRS = BLOCKS.register("black_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> BLACK_SANDSTONE_WALL = BLOCKS.register("black_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_BLACK_SANDSTONE = BLOCKS.register("smooth_black_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> SMOOTH_BLACK_SANDSTONE_SLAB = BLOCKS.register("smooth_black_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_BLACK_SANDSTONE_STAIRS = BLOCKS.register("smooth_black_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(SMOOTH_BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_BLACK_SANDSTONE_WALL = BLOCKS.register("smooth_black_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(SMOOTH_BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_BLACK_SANDSTONE = BLOCKS.register("cut_black_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> CUT_BLACK_SANDSTONE_SLAB = BLOCKS.register("cut_black_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(CUT_BLACK_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_BLACK_SANDSTONE = BLOCKS.register("chiseled_black_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLACK).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> WHITE_SANDSTONE = BLOCKS.register("white_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> WHITE_SANDSTONE_SLAB = BLOCKS.register("white_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> WHITE_SANDSTONE_STAIRS = BLOCKS.register("white_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> WHITE_SANDSTONE_WALL = BLOCKS.register("white_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_WHITE_SANDSTONE = BLOCKS.register("smooth_white_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> SMOOTH_WHITE_SANDSTONE_SLAB = BLOCKS.register("smooth_white_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_WHITE_SANDSTONE_STAIRS = BLOCKS.register("smooth_white_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(SMOOTH_WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_WHITE_SANDSTONE_WALL = BLOCKS.register("smooth_white_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(SMOOTH_WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_WHITE_SANDSTONE = BLOCKS.register("cut_white_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> CUT_WHITE_SANDSTONE_SLAB = BLOCKS.register("cut_white_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(CUT_WHITE_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_WHITE_SANDSTONE = BLOCKS.register("chiseled_white_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> DESERT_SANDSTONE = BLOCKS.register("desert_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> DESERT_SANDSTONE_SLAB = BLOCKS.register("desert_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> DESERT_SANDSTONE_STAIRS = BLOCKS.register("desert_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> DESERT_SANDSTONE_WALL = BLOCKS.register("desert_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DESERT_SANDSTONE = BLOCKS.register("smooth_desert_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> SMOOTH_DESERT_SANDSTONE_SLAB = BLOCKS.register("smooth_desert_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(SMOOTH_DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DESERT_SANDSTONE_STAIRS = BLOCKS.register("smooth_desert_sandstone_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(SMOOTH_DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> SMOOTH_DESERT_SANDSTONE_WALL = BLOCKS.register("smooth_desert_sandstone_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(SMOOTH_DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> CUT_DESERT_SANDSTONE = BLOCKS.register("cut_desert_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.8F)));
    public static final RegistryObject<Block> CUT_DESERT_SANDSTONE_SLAB = BLOCKS.register("cut_desert_sandstone_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(CUT_DESERT_SANDSTONE.get())));
    public static final RegistryObject<Block> CHISELED_DESERT_SANDSTONE = BLOCKS.register("chiseled_desert_sandstone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.8F)));


    //Other Stones
    public static final RegistryObject<Block> GRANITE_BRICKS = BLOCKS.register("granite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final RegistryObject<Block> GRANITE_BRICKS_SLAB = BLOCKS.register("granite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(GRANITE_BRICKS.get())));
    public static final RegistryObject<Block> GRANITE_BRICKS_STAIRS = BLOCKS.register("granite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(GRANITE_BRICKS.get())));
    public static final RegistryObject<Block> GRANITE_BRICKS_WALL = BLOCKS.register("granite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(GRANITE_BRICKS.get())));
    public static final RegistryObject<Block> GRANITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("granite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final RegistryObject<Block> DIORITE_BRICKS = BLOCKS.register("diorite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final RegistryObject<Block> DIORITE_BRICKS_SLAB = BLOCKS.register("diorite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(DIORITE_BRICKS.get())));
    public static final RegistryObject<Block> DIORITE_BRICKS_STAIRS = BLOCKS.register("diorite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(DIORITE_BRICKS.get())));
    public static final RegistryObject<Block> DIORITE_BRICKS_WALL = BLOCKS.register("diorite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(DIORITE_BRICKS.get())));
    public static final RegistryObject<Block> DIORITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("diorite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final RegistryObject<Block> ANDESITE_BRICKS = BLOCKS.register("andesite_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final RegistryObject<Block> ANDESITE_BRICKS_SLAB = BLOCKS.register("andesite_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(ANDESITE_BRICKS.get())));
    public static final RegistryObject<Block> ANDESITE_BRICKS_STAIRS = BLOCKS.register("andesite_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(ANDESITE_BRICKS.get())));
    public static final RegistryObject<Block> ANDESITE_BRICKS_WALL = BLOCKS.register("andesite_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(ANDESITE_BRICKS.get())));
    public static final RegistryObject<Block> ANDESITE_BRICKS_PRESSURE_PLATE = BLOCKS.register("andesite_bricks_pressure_plate", RankineStonePressurePlate::new);
    public static final RegistryObject<Block> BASALT_BRICKS = BLOCKS.register("basalt_bricks", () -> new RankineStoneBricksBlock(IGNEOUS_STONE_BRICKS));
    public static final RegistryObject<Block> BASALT_BRICKS_SLAB = BLOCKS.register("basalt_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> BASALT_BRICKS_STAIRS = BLOCKS.register("basalt_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> BASALT_BRICKS_WALL = BLOCKS.register("basalt_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(BASALT_BRICKS.get())));
    public static final RegistryObject<Block> BASALT_BRICKS_PRESSURE_PLATE = BLOCKS.register("basalt_bricks_pressure_plate", RankineStonePressurePlate::new);

    public static final RegistryObject<Block> PACKED_SNOW = BLOCKS.register("packed_snow", () -> new Block(BlockBehaviour.Properties.of(Material.SNOW).strength(2.0F).requiresCorrectToolForDrops().sound(SoundType.SNOW)));
    public static final RegistryObject<Block> PACKED_SNOW_SLAB = BLOCKS.register("packed_snow_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(PACKED_SNOW.get())));
    public static final RegistryObject<Block> PACKED_SNOW_STAIRS = BLOCKS.register("packed_snow_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(PACKED_SNOW.get())));
    public static final RegistryObject<Block> PACKED_SNOW_WALL = BLOCKS.register("packed_snow_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(PACKED_SNOW.get())));
    public static final RegistryObject<Block> ICE_BRICKS = BLOCKS.register("ice_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID).friction(0.98F).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> ICE_BRICKS_SLAB = BLOCKS.register("ice_bricks_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.copy(ICE_BRICKS.get())));
    public static final RegistryObject<Block> ICE_BRICKS_STAIRS = BLOCKS.register("ice_bricks_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.copy(ICE_BRICKS.get())));
    public static final RegistryObject<Block> ICE_BRICKS_WALL = BLOCKS.register("ice_bricks_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.copy(ICE_BRICKS.get())));

    public static final RegistryObject<Block> BRECCIA = BLOCKS.register("breccia", () -> new Block(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> BRECCIA_SLAB = BLOCKS.register("breccia_slab", () -> new RankineSlabBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> BRECCIA_STAIRS = BLOCKS.register("breccia_stairs", () -> new RankineStairsBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> BRECCIA_WALL = BLOCKS.register("breccia_wall", () -> new RankineWallBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SKARN = BLOCKS.register("skarn", () -> new Block(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SKARN_SLAB = BLOCKS.register("skarn_slab", () -> new RankineSlabBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SKARN_STAIRS = BLOCKS.register("skarn_stairs", () -> new RankineStairsBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SKARN_WALL = BLOCKS.register("skarn_wall", () -> new RankineWallBlock(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> PUMICE = BLOCKS.register("pumice", () -> new Block(Block.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> SCORIA = BLOCKS.register("scoria", () -> new Block(Block.Properties.copy(Blocks.COBBLESTONE)));


    //weird mineral blocks
    public static final RegistryObject<Block> MELLITE_BLOCK = BLOCKS.register("mellite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> MONTROYDITE_BLOCK = BLOCKS.register("montroydite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RINGWOODITE_BLOCK = BLOCKS.register("ringwoodite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> WADSLEYITE_BLOCK = BLOCKS.register("wadsleyite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> FORSTERITE_BLOCK = BLOCKS.register("forsterite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SPINEL_BLOCK = BLOCKS.register("spinel_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> BRIDGMANITE_BLOCK = BLOCKS.register("bridgmanite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> FERROPERICLASE_BLOCK = BLOCKS.register("ferropericlase_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PEROVSKITE_BLOCK = BLOCKS.register("perovskite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));


    public static final RegistryObject<Block> CLAY_BRICKS = BLOCKS.register("clay_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> FIRE_CLAY_BRICKS = BLOCKS.register("fire_clay_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> PORCELAIN_CLAY_BRICKS = BLOCKS.register("porcelain_clay_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> REFRACTORY_BRICKS = BLOCKS.register("refractory_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> HIGH_REFRACTORY_BRICKS = BLOCKS.register("high_refractory_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> ULTRA_HIGH_REFRACTORY_BRICKS = BLOCKS.register("ultra_high_refractory_bricks", () -> new RankineBricksBlock(DEF_STONE));
    public static final RegistryObject<Block> CLAY_BRICKS_WALL = BLOCKS.register("clay_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> FIRE_CLAY_BRICKS_WALL = BLOCKS.register("fire_clay_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> PORCELAIN_CLAY_BRICKS_WALL = BLOCKS.register("porcelain_clay_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> REFRACTORY_BRICKS_WALL = BLOCKS.register("refractory_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> HIGH_REFRACTORY_BRICKS_WALL = BLOCKS.register("high_refractory_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> ULTRA_HIGH_REFRACTORY_BRICKS_WALL = BLOCKS.register("ultra_high_refractory_bricks_wall", () -> new RankineWallBlock(DEF_STONE));
    public static final RegistryObject<Block> CLAY_BRICKS_SLAB = BLOCKS.register("clay_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> FIRE_CLAY_BRICKS_SLAB = BLOCKS.register("fire_clay_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> PORCELAIN_CLAY_BRICKS_SLAB = BLOCKS.register("porcelain_clay_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> REFRACTORY_BRICKS_SLAB = BLOCKS.register("refractory_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> HIGH_REFRACTORY_BRICKS_SLAB = BLOCKS.register("high_refractory_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> ULTRA_HIGH_REFRACTORY_BRICKS_SLAB = BLOCKS.register("ultra_high_refractory_bricks_slab", () -> new RankineSlabBlock(DEF_STONE));
    public static final RegistryObject<Block> CLAY_BRICKS_STAIRS = BLOCKS.register("clay_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));
    public static final RegistryObject<Block> FIRE_CLAY_BRICKS_STAIRS = BLOCKS.register("fire_clay_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));
    public static final RegistryObject<Block> PORCELAIN_CLAY_BRICKS_STAIRS = BLOCKS.register("porcelain_clay_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));
    public static final RegistryObject<Block> REFRACTORY_BRICKS_STAIRS = BLOCKS.register("refractory_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));
    public static final RegistryObject<Block> HIGH_REFRACTORY_BRICKS_STAIRS = BLOCKS.register("high_refractory_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));
    public static final RegistryObject<Block> ULTRA_HIGH_REFRACTORY_BRICKS_STAIRS = BLOCKS.register("ultra_high_refractory_bricks_stairs", () -> new RankineStairsBlock(DEF_STONE));


    //public static final RegistryObject<Block> FIBER_BED = REGISTRY.register("fiber_bed", () -> new FiberBedBlock(AbstractBlock.Properties.create(Material.WOOL, MaterialColor.SNOW).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));

    public static final RegistryObject<Block> FIBER_BLOCK = BLOCKS.register("fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FIBER_BLOCK_SLAB = BLOCKS.register("fiber_block_slab", () -> new RankineSlabBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FIBER_BLOCK_STAIRS = BLOCKS.register("fiber_block_stairs", () -> new RankineStairsBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FIBER_BLOCK_WALL = BLOCKS.register("fiber_block_wall", () -> new RankineWallBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> WHITE_FIBER_BLOCK = BLOCKS.register("white_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ORANGE_FIBER_BLOCK = BLOCKS.register("orange_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> MAGENTA_FIBER_BLOCK = BLOCKS.register("magenta_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIGHT_BLUE_FIBER_BLOCK = BLOCKS.register("light_blue_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> YELLOW_FIBER_BLOCK = BLOCKS.register("yellow_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIME_FIBER_BLOCK = BLOCKS.register("lime_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> PINK_FIBER_BLOCK = BLOCKS.register("pink_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GRAY_FIBER_BLOCK = BLOCKS.register("gray_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIGHT_GRAY_FIBER_BLOCK = BLOCKS.register("light_gray_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> CYAN_FIBER_BLOCK = BLOCKS.register("cyan_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> PURPLE_FIBER_BLOCK = BLOCKS.register("purple_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BLUE_FIBER_BLOCK = BLOCKS.register("blue_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GREEN_FIBER_BLOCK = BLOCKS.register("green_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BROWN_FIBER_BLOCK = BLOCKS.register("brown_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> RED_FIBER_BLOCK = BLOCKS.register("red_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BLACK_FIBER_BLOCK = BLOCKS.register("black_fiber_block", () -> new FiberBlock(BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> FIBER_MAT = BLOCKS.register("fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> WHITE_FIBER_MAT = BLOCKS.register("white_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ORANGE_FIBER_MAT = BLOCKS.register("orange_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> MAGENTA_FIBER_MAT = BLOCKS.register("magenta_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIGHT_BLUE_FIBER_MAT = BLOCKS.register("light_blue_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> YELLOW_FIBER_MAT = BLOCKS.register("yellow_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIME_FIBER_MAT = BLOCKS.register("lime_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> PINK_FIBER_MAT = BLOCKS.register("pink_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GRAY_FIBER_MAT = BLOCKS.register("gray_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LIGHT_GRAY_FIBER_MAT = BLOCKS.register("light_gray_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> CYAN_FIBER_MAT = BLOCKS.register("cyan_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> PURPLE_FIBER_MAT = BLOCKS.register("purple_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BLUE_FIBER_MAT = BLOCKS.register("blue_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GREEN_FIBER_MAT = BLOCKS.register("green_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BROWN_FIBER_MAT = BLOCKS.register("brown_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> RED_FIBER_MAT = BLOCKS.register("red_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BLACK_FIBER_MAT = BLOCKS.register("black_fiber_mat", () -> new FiberMatBlock(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> DRY_ICE = BLOCKS.register("dry_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of(Material.ICE_SOLID).strength(3.5F).friction(0.999F).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> METEORIC_ICE = BLOCKS.register("meteoric_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of(Material.ICE_SOLID).strength(3.5F).friction(1.02F).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> DIAMOND_GEODE = BLOCKS.register("diamond_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> EMERALD_GEODE = BLOCKS.register("emerald_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> AQUAMARINE_GEODE = BLOCKS.register("aquamarine_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> OPAL_GEODE = BLOCKS.register("opal_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> RUBY_GEODE = BLOCKS.register("ruby_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> SAPPHIRE_GEODE = BLOCKS.register("sapphire_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> PERIDOT_GEODE = BLOCKS.register("peridot_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> GARNET_GEODE = BLOCKS.register("garnet_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> TOPAZ_GEODE = BLOCKS.register("topaz_geode", () -> new GeodeBlock(DEF_STONE));
    public static final RegistryObject<Block> TOURMALINE_GEODE = BLOCKS.register("tourmaline_geode", () -> new GeodeBlock(DEF_STONE));

    public static final RegistryObject<Block> TRAMPOLINE = BLOCKS.register("trampoline", () -> new TrampolineBlock(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_BLACK).friction(0.8F).strength(1.0F, 2.0F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> CARBON_DIOXIDE_FUMAROLE = BLOCKS.register("carbon_dioxide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.CARBON_DIOXIDE,BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> HYDROGEN_CHLORIDE_FUMAROLE = BLOCKS.register("hydrogen_chloride_fumarole", () -> new FumaroleBlock(GasUtilsEnum.HYDROGEN_CHLORIDE,BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> HYDROGEN_SULFIDE_FUMAROLE = BLOCKS.register("hydrogen_sulfide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.HYDROGEN_SULFIDE,BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> SULFUR_DIOXIDE_FUMAROLE = BLOCKS.register("sulfur_dioxide_fumarole", () -> new FumaroleBlock(GasUtilsEnum.SULFUR_DIOXIDE,BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> HYDROGEN_GAS_BLOCK = BLOCKS.register("hydrogen_gas_block", () -> new HydrogenGasBlock(RankineItems.HYDROGEN_GAS_BOTTLE,GasUtilsEnum.HYDROGEN, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> HELIUM_GAS_BLOCK = BLOCKS.register("helium_gas_block", () -> new HeliumGasBlock(RankineItems.HELIUM_GAS_BOTTLE,GasUtilsEnum.HELIUM, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> NITROGEN_GAS_BLOCK = BLOCKS.register("nitrogen_gas_block", () -> new NitrogenGasBlock(RankineItems.NITROGEN_GAS_BOTTLE,GasUtilsEnum.NITROGEN, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> OXYGEN_GAS_BLOCK = BLOCKS.register("oxygen_gas_block", () -> new OxygenGasBlock(RankineItems.OXYGEN_GAS_BOTTLE,GasUtilsEnum.OXYGEN, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> FLUORINE_GAS_BLOCK = BLOCKS.register("fluorine_gas_block", () -> new GasBlock(RankineItems.FLUORINE_GAS_BOTTLE,GasUtilsEnum.FLUORINE, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> NEON_GAS_BLOCK = BLOCKS.register("neon_gas_block", () -> new NeonGasBlock(RankineItems.NEON_GAS_BOTTLE,GasUtilsEnum.NEON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> CHLORINE_GAS_BLOCK = BLOCKS.register("chlorine_gas_block", () -> new GasBlock(RankineItems.CHLORINE_GAS_BOTTLE,GasUtilsEnum.CHLORINE, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> ARGON_GAS_BLOCK = BLOCKS.register("argon_gas_block", () -> new GasBlock(RankineItems.ARGON_GAS_BOTTLE,GasUtilsEnum.ARGON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> KRYPTON_GAS_BLOCK = BLOCKS.register("krypton_gas_block", () -> new GasBlock(RankineItems.KRYPTON_GAS_BOTTLE,GasUtilsEnum.KRYPTON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> XENON_GAS_BLOCK = BLOCKS.register("xenon_gas_block", () -> new XenonGasBlock(RankineItems.XENON_GAS_BOTTLE,GasUtilsEnum.XENON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> RADON_GAS_BLOCK = BLOCKS.register("radon_gas_block", () -> new GasBlock(RankineItems.RADON_GAS_BOTTLE,GasUtilsEnum.RADON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> OGANESSON_GAS_BLOCK = BLOCKS.register("oganesson_gas_block", () -> new OganessonGasBlock(RankineItems.OGANESSON_GAS_BOTTLE,GasUtilsEnum.OGANESSON, BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> AMMONIA_GAS_BLOCK = BLOCKS.register("ammonia_gas_block", () -> new GasBlock(RankineItems.AMMONIA_GAS_BOTTLE,GasUtilsEnum.AMMONIA,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> CARBON_DIOXIDE_GAS_BLOCK = BLOCKS.register("carbon_dioxide_gas_block", () -> new GasBlock(RankineItems.CARBON_DIOXIDE_GAS_BOTTLE,GasUtilsEnum.CARBON_DIOXIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> HYDROGEN_CHLORIDE_GAS_BLOCK = BLOCKS.register("hydrogen_chloride_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_CHLORIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_CHLORIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> HYDROGEN_FLUORIDE_GAS_BLOCK = BLOCKS.register("hydrogen_fluoride_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_FLUORIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_FLUORIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> HYDROGEN_SULFIDE_GAS_BLOCK = BLOCKS.register("hydrogen_sulfide_gas_block", () -> new GasBlock(RankineItems.HYDROGEN_SULFIDE_GAS_BOTTLE,GasUtilsEnum.HYDROGEN_SULFIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> SULFUR_DIOXIDE_GAS_BLOCK = BLOCKS.register("sulfur_dioxide_gas_block", () -> new GasBlock(RankineItems.SULFUR_DIOXIDE_GAS_BOTTLE,GasUtilsEnum.SULFUR_DIOXIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));
    public static final RegistryObject<Block> TUNGSTEN_HEXAFLUORIDE_GAS_BLOCK = BLOCKS.register("tungsten_hexafluoride_gas_block", () -> new GasBlock(RankineItems.TUNGSTEN_HEXAFLUORIDE_GAS_BOTTLE,GasUtilsEnum.TUNGSTEN_HEXAFLUORIDE,BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air()));

    //public static final RegistryObject<Block> HELIUM_GAS_TUBE = REGISTRY.register("helium_gas_tube", () -> new GasTubeBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.5F).sound(SoundType.GLASS).notSolid()));

    public static Block.Properties LANTERN_PROP = BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 15).noOcclusion();
    public static final RegistryObject<Block> LITHIUM_LANTERN = BLOCKS.register("lithium_lantern", () -> new LanternBlock(LANTERN_PROP));
    /*
    public static final RegistryObject<Block> BERYLLIUM_LANTERN = REGISTRY.register("beryllium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> BORON_LANTERN = REGISTRY.register("boron_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CARBON_LANTERN = REGISTRY.register("carbon_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> SODIUM_LANTERN = REGISTRY.register("sodium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> MAGNESIUM_LANTERN = REGISTRY.register("magnesium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ALUMINUM_LANTERN = REGISTRY.register("aluminum_lantern", () -> new LanternBlock(LANTERN_PROP));

    public static final RegistryObject<Block> SILICON_LANTERN = REGISTRY.register("silicon_lantern", () -> new LanternBlock(LANTERN_PROP));
     */
    public static final RegistryObject<Block> PHOSPHORUS_LANTERN = BLOCKS.register("phosphorus_lantern", () -> new LanternBlock(LANTERN_PROP));
    /*
    public static final RegistryObject<Block> SULFUR_LANTERN = REGISTRY.register("sulfur_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> POTASSIUM_LANTERN = REGISTRY.register("potassium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CALCIUM_LANTERN = REGISTRY.register("calcium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> SCANDIUM_LANTERN = REGISTRY.register("scandium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TITANIUM_LANTERN = REGISTRY.register("titanium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> VANADIUM_LANTERN = REGISTRY.register("vanadium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CHROMIUM_LANTERN = REGISTRY.register("chromium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> MANGANESE_LANTERN = REGISTRY.register("manganese_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> COBALT_LANTERN = REGISTRY.register("cobalt_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> NICKEL_LANTERN = REGISTRY.register("nickel_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> COPPER_LANTERN = REGISTRY.register("copper_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ZINC_LANTERN = REGISTRY.register("zinc_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> GALLIUM_LANTERN = REGISTRY.register("gallium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> GERMANIUM_LANTERN = REGISTRY.register("germanium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ARSENIC_LANTERN = REGISTRY.register("arsenic_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> SELENIUM_LANTERN = REGISTRY.register("selenium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> BROMINE_LANTERN = REGISTRY.register("bromine_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> KRYPTON_LANTERN = REGISTRY.register("krypton_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> RUBIDIUM_LANTERN = REGISTRY.register("rubidium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> STRONTIUM_LANTERN = REGISTRY.register("strontium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> YTTRIUM_LANTERN = REGISTRY.register("yttrium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ZIRCONIUM_LANTERN = REGISTRY.register("zirconium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> NIOBIUM_LANTERN = REGISTRY.register("niobium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> MOLYBDENUM_LANTERN = REGISTRY.register("molybdenum_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TECHNETIUM_LANTERN = REGISTRY.register("technetium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> RUTHENIUM_LANTERN = REGISTRY.register("ruthenium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> RHODIUM_LANTERN = REGISTRY.register("rhodium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> PALLADIUM_LANTERN = REGISTRY.register("palladium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> SILVER_LANTERN = REGISTRY.register("silver_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CADMIUM_LANTERN = REGISTRY.register("cadmium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> INDIUM_LANTERN = REGISTRY.register("indium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TIN_LANTERN = REGISTRY.register("tin_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ANTIMONY_LANTERN = REGISTRY.register("antimony_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TELLURIUM_LANTERN = REGISTRY.register("tellurium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> IODINE_LANTERN = REGISTRY.register("iodine_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> XENON_LANTERN = REGISTRY.register("xenon_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CESIUM_LANTERN = REGISTRY.register("cesium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> BARIUM_LANTERN = REGISTRY.register("barium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> LANTHANUM_LANTERN = REGISTRY.register("lanthanum_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> CERIUM_LANTERN = REGISTRY.register("cerium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> PRASEODYMIUM_LANTERN = REGISTRY.register("praseodymium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> NEODYMIUM_LANTERN = REGISTRY.register("neodymium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> PROMETHIUM_LANTERN = REGISTRY.register("promethium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> SAMARIUM_LANTERN = REGISTRY.register("samarium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> EUROPIUM_LANTERN = REGISTRY.register("europium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> GADOLINIUM_LANTERN = REGISTRY.register("gadolinium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TERBIUM_LANTERN = REGISTRY.register("terbium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> DYSPROSIUM_LANTERN = REGISTRY.register("dysprosium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> HOLMIUM_LANTERN = REGISTRY.register("holmium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ERBIUM_LANTERN = REGISTRY.register("erbium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> THULIUM_LANTERN = REGISTRY.register("thulium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> YTTERBIUM_LANTERN = REGISTRY.register("ytterbium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> LUTETIUM_LANTERN = REGISTRY.register("lutetium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> HAFNIUM_LANTERN = REGISTRY.register("hafnium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TANTALUM_LANTERN = REGISTRY.register("tantalum_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> TUNGSTEN_LANTERN = REGISTRY.register("tungsten_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> RHENIUM_LANTERN = REGISTRY.register("rhenium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> OSMIUM_LANTERN = REGISTRY.register("osmium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> IRIDIUM_LANTERN = REGISTRY.register("iridium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> PLATINUM_LANTERN = REGISTRY.register("platinum_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> MERCURY_LANTERN = REGISTRY.register("mercury_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> THALLIUM_LANTERN = REGISTRY.register("thallium_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> LEAD_LANTERN = REGISTRY.register("lead_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> BISMUTH_LANTERN = REGISTRY.register("bismuth_lantern", () -> new LanternBlock(LANTERN_PROP));
    public static final RegistryObject<Block> ENDOSITUM_LANTERN = REGISTRY.register("endositum_lantern", () -> new LanternBlock(LANTERN_PROP));


     */
    public static final RegistryObject<Block> SOD_BLOCK = BLOCKS.register("sod_block", () -> new Block(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> SILT = BLOCKS.register("silt", () -> new RankineSandBlock(11770483, Block.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.SAND).strength(0.5F)));
    public static final RegistryObject<Block> PERMAFROST = BLOCKS.register("permafrost", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1.5F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> HUMUS = BLOCKS.register("humus", SoilBlock::new);
    public static final RegistryObject<Block> LOAM = BLOCKS.register("loam", SoilBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM = BLOCKS.register("silty_loam", SoilBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND = BLOCKS.register("loamy_sand", SoilBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM = BLOCKS.register("sandy_loam", SoilBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM = BLOCKS.register("clay_loam", SoilBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM = BLOCKS.register("sandy_clay_loam", SoilBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM = BLOCKS.register("silty_clay_loam", SoilBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY = BLOCKS.register("sandy_clay", SoilBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY = BLOCKS.register("silty_clay", SoilBlock::new);
    public static final RegistryObject<Block> COARSE_HUMUS = BLOCKS.register("coarse_humus", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_LOAM = BLOCKS.register("coarse_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SILTY_LOAM = BLOCKS.register("coarse_silty_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_LOAMY_SAND = BLOCKS.register("coarse_loamy_sand", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SANDY_LOAM = BLOCKS.register("coarse_sandy_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_CLAY_LOAM = BLOCKS.register("coarse_clay_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SANDY_CLAY_LOAM = BLOCKS.register("coarse_sandy_clay_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SILTY_CLAY_LOAM = BLOCKS.register("coarse_silty_clay_loam", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SANDY_CLAY = BLOCKS.register("coarse_sandy_clay", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> COARSE_SILTY_CLAY = BLOCKS.register("coarse_silty_clay", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> HUMUS_MUD = BLOCKS.register("humus_mud", MudBlock::new);
    public static final RegistryObject<Block> LOAM_MUD = BLOCKS.register("loam_mud", MudBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM_MUD = BLOCKS.register("silty_loam_mud", MudBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND_MUD = BLOCKS.register("loamy_sand_mud", MudBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM_MUD = BLOCKS.register("sandy_loam_mud", MudBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM_MUD = BLOCKS.register("clay_loam_mud", MudBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM_MUD = BLOCKS.register("sandy_clay_loam_mud", MudBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM_MUD = BLOCKS.register("silty_clay_loam_mud", MudBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_MUD = BLOCKS.register("sandy_clay_mud", MudBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_MUD = BLOCKS.register("silty_clay_mud", MudBlock::new);
    public static final RegistryObject<Block> HUMUS_GRASS_PATH = BLOCKS.register("humus_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> LOAM_GRASS_PATH = BLOCKS.register("loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM_GRASS_PATH = BLOCKS.register("silty_loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND_GRASS_PATH = BLOCKS.register("loamy_sand_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM_GRASS_PATH = BLOCKS.register("sandy_loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM_GRASS_PATH = BLOCKS.register("clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM_GRASS_PATH = BLOCKS.register("sandy_clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM_GRASS_PATH = BLOCKS.register("silty_clay_loam_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_GRASS_PATH = BLOCKS.register("sandy_clay_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_GRASS_PATH = BLOCKS.register("silty_clay_grass_path", GrassySoilPathBlock::new);
    public static final RegistryObject<Block> HUMUS_GRASS_BLOCK = BLOCKS.register("humus_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> LOAM_GRASS_BLOCK = BLOCKS.register("loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM_GRASS_BLOCK = BLOCKS.register("silty_loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND_GRASS_BLOCK = BLOCKS.register("loamy_sand_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM_GRASS_BLOCK = BLOCKS.register("sandy_loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("clay_loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("sandy_clay_loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM_GRASS_BLOCK = BLOCKS.register("silty_clay_loam_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_GRASS_BLOCK = BLOCKS.register("sandy_clay_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_GRASS_BLOCK = BLOCKS.register("silty_clay_grass_block", GrassySoilBlock::new);
    public static final RegistryObject<Block> HUMUS_PODZOL = BLOCKS.register("humus_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> LOAM_PODZOL = BLOCKS.register("loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM_PODZOL = BLOCKS.register("silty_loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND_PODZOL = BLOCKS.register("loamy_sand_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM_PODZOL = BLOCKS.register("sandy_loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM_PODZOL = BLOCKS.register("clay_loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM_PODZOL = BLOCKS.register("sandy_clay_loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM_PODZOL = BLOCKS.register("silty_clay_loam_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_PODZOL = BLOCKS.register("sandy_clay_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_PODZOL = BLOCKS.register("silty_clay_podzol", RankinePodzolBlock::new);
    public static final RegistryObject<Block> HUMUS_MYCELIUM = BLOCKS.register("humus_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> LOAM_MYCELIUM = BLOCKS.register("loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SILTY_LOAM_MYCELIUM = BLOCKS.register("silty_loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> LOAMY_SAND_MYCELIUM = BLOCKS.register("loamy_sand_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SANDY_LOAM_MYCELIUM = BLOCKS.register("sandy_loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> CLAY_LOAM_MYCELIUM = BLOCKS.register("clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_LOAM_MYCELIUM = BLOCKS.register("sandy_clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_LOAM_MYCELIUM = BLOCKS.register("silty_clay_loam_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SANDY_CLAY_MYCELIUM = BLOCKS.register("sandy_clay_mycelium", RankineMyceliumBlock::new);
    public static final RegistryObject<Block> SILTY_CLAY_MYCELIUM = BLOCKS.register("silty_clay_mycelium", RankineMyceliumBlock::new);


    public static final RegistryObject<Block> MYCELIUM_PATH = BLOCKS.register("mycelium_path", () -> new DirtPathBlock(Block.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).strength(0.5F)));

    //gravel path?
    public static final RegistryObject<Block> ENDER_SHIRO = BLOCKS.register("ender_shiro", () -> new EnderShiroBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(0.8F).sound(SoundType.ROOTS).randomTicks()));
    public static final RegistryObject<Block> TILLED_SOIL = BLOCKS.register("tilled_soil", () -> new TilledSoilBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> LIGHTNING_GLASS = BLOCKS.register("lightning_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> RED_LIGHTNING_GLASS = BLOCKS.register("red_lightning_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> SOUL_LIGHTNING_GLASS = BLOCKS.register("soul_lightning_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> BLACK_LIGHTNING_GLASS = BLOCKS.register("black_lightning_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> WHITE_LIGHTNING_GLASS = BLOCKS.register("white_lightning_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));



    public static final RegistryObject<Block> DARK_GRAVEL = BLOCKS.register("dark_gravel", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> LIGHT_GRAVEL = BLOCKS.register("light_gravel", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> ALLUVIUM = BLOCKS.register("alluvium", () -> new RankineSandBlock(14406560, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> BLACK_SAND = BLOCKS.register("black_sand", () -> new RankineSandBlock(00000000, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> WHITE_SAND = BLOCKS.register("white_sand", () -> new RankineSandBlock(16777215, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> DESERT_SAND = BLOCKS.register("desert_sand", () -> new RankineSandBlock(14865335, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> LEAD_GLASS = BLOCKS.register("lead_glass", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(3.0F, 15.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> BOROSILICATE_GLASS = BLOCKS.register("borosilicate_glass", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(6.0F, 30.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> CVD_GLASS = BLOCKS.register("cvd_glass", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(12.0F, 60.0F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> COB = BLOCKS.register("cob", () -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.GRAVEL).strength(1.0F)));
    public static final RegistryObject<Block> REFINED_COB = BLOCKS.register("refined_cob", () -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).sound(SoundType.GRAVEL).strength(2.0F)));
    public static final RegistryObject<Block> BANDED_IRON_FORMATION = BLOCKS.register("banded_iron_formation", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> PHOSPHORITE = BLOCKS.register("phosphorite", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> LATERITE = BLOCKS.register("laterite", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1.0F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> SYLVINITE = BLOCKS.register("sylvinite", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> KAOLIN = BLOCKS.register("kaolin", () -> new Block(DEF_STONE));

    public static final RegistryObject<Block> FUMAROLE_DEPOSIT = BLOCKS.register("fumarole_deposit", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> IRONSTONE = BLOCKS.register("ironstone", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> BOG_IRON = BLOCKS.register("bog_iron", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> PORPHYRY_COPPER = BLOCKS.register("porphyry_copper", () -> new OreBlock(DEF_STONE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> KIMBERLITIC_DIAMOND_ORE = BLOCKS.register("kimberlitic_diamond_ore", () -> new OreBlock(DEF_STONE, UniformInt.of(3,7)));
    public static final RegistryObject<Block> QUICKLIME_BLOCK = BLOCKS.register("quicklime_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> MAGNESITE_BLOCK = BLOCKS.register("magnesite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> MAGNESIA_BLOCK = BLOCKS.register("magnesia_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> ALUMINA_BLOCK = BLOCKS.register("alumina_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> ANDESITIC_TUFF = BLOCKS.register("andesitic_tuff", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> BASALTIC_TUFF = BLOCKS.register("basaltic_tuff", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> RHYOLITIC_TUFF = BLOCKS.register("rhyolitic_tuff", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> KIMBERLITIC_TUFF = BLOCKS.register("kimberlitic_tuff", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));
    public static final RegistryObject<Block> KOMATIITIC_TUFF = BLOCKS.register("komatiitic_tuff", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 2.0F)));

    public static final RegistryObject<Block> FERRIC_DRIPSTONE_BLOCK = BLOCKS.register("ferric_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> MAGNESITIC_DRIPSTONE_BLOCK = BLOCKS.register("magnesitic_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> ZIRCONIC_DRIPSTONE_BLOCK = BLOCKS.register("zirconic_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> GYPSIC_DRIPSTONE_BLOCK = BLOCKS.register("gypsic_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> HALITIC_DRIPSTONE_BLOCK = BLOCKS.register("halitic_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> NITRIC_DRIPSTONE_BLOCK = BLOCKS.register("nitric_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> BORACITIC_DRIPSTONE_BLOCK = BLOCKS.register("boracitic_dripstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(1.5F, 1.0F)));

    public static final RegistryObject<Block> POINTED_FERRIC_DRIPSTONE = BLOCKS.register("pointed_ferric_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),FERRIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_MAGNESITIC_DRIPSTONE = BLOCKS.register("pointed_magnesitic_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),MAGNESITIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_ZIRCONIC_DRIPSTONE = BLOCKS.register("pointed_zirconic_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),ZIRCONIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_GYPSIC_DRIPSTONE = BLOCKS.register("pointed_gypsic_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),GYPSIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_HALITIC_DRIPSTONE = BLOCKS.register("pointed_halitic_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),HALITIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_NITRIC_DRIPSTONE = BLOCKS.register("pointed_nitric_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),NITRIC_DRIPSTONE_BLOCK.get()));
    public static final RegistryObject<Block> POINTED_BORACITIC_DRIPSTONE = BLOCKS.register("pointed_boracitic_dripstone", () -> new RankinePointedDripstoneBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).noOcclusion().sound(SoundType.POINTED_DRIPSTONE).randomTicks().strength(1.5F, 3.0F).dynamicShape(),BORACITIC_DRIPSTONE_BLOCK.get()));

    public static final RegistryObject<Block> SLATE_STEPPING_STONES = BLOCKS.register("slate_stepping_stones", SteppingStonesBlock::new);


    public static final RegistryObject<Block> WHITE_CEMENT = BLOCKS.register("white_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> WHITE_CEMENT_SLAB = BLOCKS.register("white_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> WHITE_CEMENT_STAIRS = BLOCKS.register("white_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> WHITE_CEMENT_WALL = BLOCKS.register("white_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> ORANGE_CEMENT = BLOCKS.register("orange_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> ORANGE_CEMENT_SLAB = BLOCKS.register("orange_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> ORANGE_CEMENT_STAIRS = BLOCKS.register("orange_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> ORANGE_CEMENT_WALL = BLOCKS.register("orange_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> MAGENTA_CEMENT = BLOCKS.register("magenta_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> MAGENTA_CEMENT_SLAB = BLOCKS.register("magenta_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> MAGENTA_CEMENT_STAIRS = BLOCKS.register("magenta_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> MAGENTA_CEMENT_WALL = BLOCKS.register("magenta_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIGHT_BLUE_CEMENT = BLOCKS.register("light_blue_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> LIGHT_BLUE_CEMENT_SLAB = BLOCKS.register("light_blue_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_BLUE_CEMENT_STAIRS = BLOCKS.register("light_blue_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIGHT_BLUE_CEMENT_WALL = BLOCKS.register("light_blue_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> YELLOW_CEMENT = BLOCKS.register("yellow_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> YELLOW_CEMENT_SLAB = BLOCKS.register("yellow_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> YELLOW_CEMENT_STAIRS = BLOCKS.register("yellow_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> YELLOW_CEMENT_WALL = BLOCKS.register("yellow_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIME_CEMENT = BLOCKS.register("lime_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> LIME_CEMENT_SLAB = BLOCKS.register("lime_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> LIME_CEMENT_STAIRS = BLOCKS.register("lime_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIME_CEMENT_WALL = BLOCKS.register("lime_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> PINK_CEMENT = BLOCKS.register("pink_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> PINK_CEMENT_SLAB = BLOCKS.register("pink_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> PINK_CEMENT_STAIRS = BLOCKS.register("pink_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> PINK_CEMENT_WALL = BLOCKS.register("pink_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> GRAY_CEMENT = BLOCKS.register("gray_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> GRAY_CEMENT_SLAB = BLOCKS.register("gray_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> GRAY_CEMENT_STAIRS = BLOCKS.register("gray_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> GRAY_CEMENT_WALL = BLOCKS.register("gray_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIGHT_GRAY_CEMENT = BLOCKS.register("light_gray_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> LIGHT_GRAY_CEMENT_SLAB = BLOCKS.register("light_gray_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_GRAY_CEMENT_STAIRS = BLOCKS.register("light_gray_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> LIGHT_GRAY_CEMENT_WALL = BLOCKS.register("light_gray_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> CYAN_CEMENT = BLOCKS.register("cyan_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> CYAN_CEMENT_SLAB = BLOCKS.register("cyan_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> CYAN_CEMENT_STAIRS = BLOCKS.register("cyan_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> CYAN_CEMENT_WALL = BLOCKS.register("cyan_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> PURPLE_CEMENT = BLOCKS.register("purple_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> PURPLE_CEMENT_SLAB = BLOCKS.register("purple_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> PURPLE_CEMENT_STAIRS = BLOCKS.register("purple_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> PURPLE_CEMENT_WALL = BLOCKS.register("purple_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BLUE_CEMENT = BLOCKS.register("blue_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> BLUE_CEMENT_SLAB = BLOCKS.register("blue_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> BLUE_CEMENT_STAIRS = BLOCKS.register("blue_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BLUE_CEMENT_WALL = BLOCKS.register("blue_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BROWN_CEMENT = BLOCKS.register("brown_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> BROWN_CEMENT_SLAB = BLOCKS.register("brown_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> BROWN_CEMENT_STAIRS = BLOCKS.register("brown_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BROWN_CEMENT_WALL = BLOCKS.register("brown_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> GREEN_CEMENT = BLOCKS.register("green_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> GREEN_CEMENT_SLAB = BLOCKS.register("green_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> GREEN_CEMENT_STAIRS = BLOCKS.register("green_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> GREEN_CEMENT_WALL = BLOCKS.register("green_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> RED_CEMENT = BLOCKS.register("red_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> RED_CEMENT_SLAB = BLOCKS.register("red_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> RED_CEMENT_STAIRS = BLOCKS.register("red_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> RED_CEMENT_WALL = BLOCKS.register("red_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BLACK_CEMENT = BLOCKS.register("black_cement", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> BLACK_CEMENT_SLAB = BLOCKS.register("black_cement_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(WHITE_CEMENT.get()).noOcclusion()));
    public static final RegistryObject<Block> BLACK_CEMENT_STAIRS = BLOCKS.register("black_cement_stairs", () -> new RankineStairsBlock(Block.Properties.copy(WHITE_CEMENT.get())));
    public static final RegistryObject<Block> BLACK_CEMENT_WALL = BLOCKS.register("black_cement_wall", () -> new RankineWallBlock(Block.Properties.copy(WHITE_CEMENT.get())));

    public static final RegistryObject<Block> CONCRETE = BLOCKS.register("concrete", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> CONCRETE_SLAB = BLOCKS.register("concrete_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(CONCRETE.get()).noOcclusion()));
    public static final RegistryObject<Block> CONCRETE_STAIRS = BLOCKS.register("concrete_stairs", () -> new RankineStairsBlock(Block.Properties.copy(CONCRETE.get())));
    public static final RegistryObject<Block> CONCRETE_WALL = BLOCKS.register("concrete_wall", () -> new RankineWallBlock(Block.Properties.copy(CONCRETE.get())));
    public static final RegistryObject<Block> ROMAN_CONCRETE = BLOCKS.register("roman_concrete", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> ROMAN_CONCRETE_SLAB = BLOCKS.register("roman_concrete_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(ROMAN_CONCRETE.get()).noOcclusion()));
    public static final RegistryObject<Block> ROMAN_CONCRETE_STAIRS = BLOCKS.register("roman_concrete_stairs", () -> new RankineStairsBlock(Block.Properties.copy(ROMAN_CONCRETE.get())));
    public static final RegistryObject<Block> ROMAN_CONCRETE_WALL = BLOCKS.register("roman_concrete_wall", () -> new RankineWallBlock(Block.Properties.copy(ROMAN_CONCRETE.get())));
    public static final RegistryObject<Block> POLISHED_ROMAN_CONCRETE = BLOCKS.register("polished_roman_concrete", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> POLISHED_ROMAN_CONCRETE_SLAB = BLOCKS.register("polished_roman_concrete_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(POLISHED_ROMAN_CONCRETE.get()).noOcclusion()));
    public static final RegistryObject<Block> POLISHED_ROMAN_CONCRETE_STAIRS = BLOCKS.register("polished_roman_concrete_stairs", () -> new RankineStairsBlock(Block.Properties.copy(POLISHED_ROMAN_CONCRETE.get())));
    public static final RegistryObject<Block> POLISHED_ROMAN_CONCRETE_WALL = BLOCKS.register("polished_roman_concrete_wall", () -> new RankineWallBlock(Block.Properties.copy(POLISHED_ROMAN_CONCRETE.get())));
    public static final RegistryObject<Block> ROMAN_CONCRETE_BRICKS = BLOCKS.register("roman_concrete_bricks", () -> new Block(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4)));
    public static final RegistryObject<Block> ROMAN_CONCRETE_BRICKS_SLAB = BLOCKS.register("roman_concrete_bricks_slab", () -> new QuarterSlabPoleBlock(Block.Properties.copy(ROMAN_CONCRETE_BRICKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ROMAN_CONCRETE_BRICKS_STAIRS = BLOCKS.register("roman_concrete_bricks_stairs", () -> new RankineStairsBlock(Block.Properties.copy(ROMAN_CONCRETE_BRICKS.get())));
    public static final RegistryObject<Block> ROMAN_CONCRETE_BRICKS_WALL = BLOCKS.register("roman_concrete_bricks_wall", () -> new RankineWallBlock(Block.Properties.copy(ROMAN_CONCRETE_BRICKS.get())));



    //public static final RegistryObject<Block> QUARRY_BARRIER = REGISTRY.register("quarry_barrier", () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5.0F, 20.0F)));
    public static final RegistryObject<Block> BONE_CHAR_BLOCK = BLOCKS.register("bone_char_block", () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BONE_BLOCK).strength(2.0F)));
    public static final RegistryObject<Block> FIRE_CLAY = BLOCKS.register("fire_clay", () -> new Block(Block.Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(1.0F, 3.0F)));
    public static final RegistryObject<Block> PORCELAIN_CLAY = BLOCKS.register("porcelain_clay", () -> new Block(Block.Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(1.0F, 3.0F)));
    public static final RegistryObject<Block> PORCELAIN = BLOCKS.register("porcelain", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.0F, 3.0F)));

    public static final RegistryObject<Block> WHITE_GLAZED_PORCELAIN = BLOCKS.register("white_glazed_porcelain", () -> new GlazedPorcelainBlock(16777215));
    public static final RegistryObject<Block> ORANGE_GLAZED_PORCELAIN = BLOCKS.register("orange_glazed_porcelain", () -> new GlazedPorcelainBlock(8336128));
    public static final RegistryObject<Block> MAGENTA_GLAZED_PORCELAIN = BLOCKS.register("magenta_glazed_porcelain", () -> new GlazedPorcelainBlock(8323182));
    public static final RegistryObject<Block> LIGHT_BLUE_GLAZED_PORCELAIN = BLOCKS.register("light_blue_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final RegistryObject<Block> YELLOW_GLAZED_PORCELAIN = BLOCKS.register("yellow_glazed_porcelain", () -> new GlazedPorcelainBlock(8350208));
    public static final RegistryObject<Block> LIME_GLAZED_PORCELAIN = BLOCKS.register("lime_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final RegistryObject<Block> PINK_GLAZED_PORCELAIN = BLOCKS.register("pink_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final RegistryObject<Block> GRAY_GLAZED_PORCELAIN = BLOCKS.register("gray_glazed_porcelain", () -> new GlazedPorcelainBlock(4210752));
    public static final RegistryObject<Block> LIGHT_GRAY_GLAZED_PORCELAIN = BLOCKS.register("light_gray_glazed_porcelain", () -> new GlazedPorcelainBlock(8421504));
    public static final RegistryObject<Block> CYAN_GLAZED_PORCELAIN = BLOCKS.register("cyan_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final RegistryObject<Block> PURPLE_GLAZED_PORCELAIN = BLOCKS.register("purple_glazed_porcelain", () -> new GlazedPorcelainBlock(5701759));
    public static final RegistryObject<Block> BLUE_GLAZED_PORCELAIN = BLOCKS.register("blue_glazed_porcelain", () -> new GlazedPorcelainBlock(4991));
    public static final RegistryObject<Block> GREEN_GLAZED_PORCELAIN = BLOCKS.register("green_glazed_porcelain", () -> new GlazedPorcelainBlock(32526));
    public static final RegistryObject<Block> BROWN_GLAZED_PORCELAIN = BLOCKS.register("brown_glazed_porcelain", () -> new GlazedPorcelainBlock(32639));
    public static final RegistryObject<Block> RED_GLAZED_PORCELAIN = BLOCKS.register("red_glazed_porcelain", () -> new GlazedPorcelainBlock(8323072));
    public static final RegistryObject<Block> BLACK_GLAZED_PORCELAIN = BLOCKS.register("black_glazed_porcelain", () -> new GlazedPorcelainBlock(0));


    public static final RegistryObject<Block> BLOOD_OBSIDIAN = BLOCKS.register("blood_obsidian", () -> new RankineObsidianBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> SNOWFLAKE_OBSIDIAN = BLOCKS.register("snowflake_obsidian", () -> new RankineObsidianBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistryObject<Block> CHECKERED_MARBLE = BLOCKS.register("checkered_marble", () -> new Block(Block.Properties.of(Material.STONE).strength(2)));
    public static final RegistryObject<Block> CHECKERED_MARBLE_SLAB = BLOCKS.register("checkered_marble_slab", () -> new RankineSlabBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2)));
    public static final RegistryObject<Block> CHECKERED_MARBLE_STAIRS = BLOCKS.register("checkered_marble_stairs", () -> new RankineStairsBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2)));
    public static final RegistryObject<Block> CHECKERED_MARBLE_WALL = BLOCKS.register("checkered_marble_wall", () -> new RankineWallBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> ASPHALT = BLOCKS.register("asphalt", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_YL = BLOCKS.register("asphalt_yellow_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_YDL = BLOCKS.register("asphalt_yellow_dashed_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_YDBL = BLOCKS.register("asphalt_yellow_double_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_DDBL = BLOCKS.register("asphalt_yellow_dashed_double_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_WL = BLOCKS.register("asphalt_white_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_WDL = BLOCKS.register("asphalt_white_dashed_line", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_CW = BLOCKS.register("asphalt_crosswalk", BaseAsphaltBlock::new);
    public static final RegistryObject<Block> ASPHALT_S = BLOCKS.register("asphalt_solid", BaseAsphaltBlock::new);

    public static final RegistryObject<Block> RED_ASPHALT = BLOCKS.register("red_asphalt", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_YL = BLOCKS.register("red_asphalt_yellow_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_YDL = BLOCKS.register("red_asphalt_yellow_dashed_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_YDBL = BLOCKS.register("red_asphalt_yellow_double_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_DDBL = BLOCKS.register("red_asphalt_yellow_dashed_double_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_WL = BLOCKS.register("red_asphalt_white_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_WDL = BLOCKS.register("red_asphalt_white_dashed_line", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_CW = BLOCKS.register("red_asphalt_crosswalk", RedAsphaltBlock::new);
    public static final RegistryObject<Block> RED_ASPHALT_S = BLOCKS.register("red_asphalt_solid", RedAsphaltBlock::new);

    public static final RegistryObject<Block> GRAY_ASPHALT = BLOCKS.register("gray_asphalt", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_YL = BLOCKS.register("gray_asphalt_yellow_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_YDL = BLOCKS.register("gray_asphalt_yellow_dashed_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_YDBL = BLOCKS.register("gray_asphalt_yellow_double_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_DDBL = BLOCKS.register("gray_asphalt_yellow_dashed_double_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_WL = BLOCKS.register("gray_asphalt_white_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_WDL = BLOCKS.register("gray_asphalt_white_dashed_line", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_CW = BLOCKS.register("gray_asphalt_crosswalk", GrayAsphaltBlock::new);
    public static final RegistryObject<Block> GRAY_ASPHALT_S = BLOCKS.register("gray_asphalt_solid", GrayAsphaltBlock::new);

    public static final RegistryObject<Block> DARK_GRAY_ASPHALT = BLOCKS.register("dark_gray_asphalt", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_YL = BLOCKS.register("dark_gray_asphalt_yellow_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_YDL = BLOCKS.register("dark_gray_asphalt_yellow_dashed_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_YDBL = BLOCKS.register("dark_gray_asphalt_yellow_double_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_DDBL = BLOCKS.register("dark_gray_asphalt_yellow_dashed_double_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_WL = BLOCKS.register("dark_gray_asphalt_white_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_WDL = BLOCKS.register("dark_gray_asphalt_white_dashed_line", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_CW = BLOCKS.register("dark_gray_asphalt_crosswalk", DarkGrayAsphaltBlock::new);
    public static final RegistryObject<Block> DARK_GRAY_ASPHALT_S = BLOCKS.register("dark_gray_asphalt_solid", DarkGrayAsphaltBlock::new);

    public static final RegistryObject<Block> GREEN_ASPHALT = BLOCKS.register("green_asphalt", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_YL = BLOCKS.register("green_asphalt_yellow_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_YDL = BLOCKS.register("green_asphalt_yellow_dashed_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_YDBL = BLOCKS.register("green_asphalt_yellow_double_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_DDBL = BLOCKS.register("green_asphalt_yellow_dashed_double_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_WL = BLOCKS.register("green_asphalt_white_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_WDL = BLOCKS.register("green_asphalt_white_dashed_line", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_CW = BLOCKS.register("green_asphalt_crosswalk", GreenAsphaltBlock::new);
    public static final RegistryObject<Block> GREEN_ASPHALT_S = BLOCKS.register("green_asphalt_solid", GreenAsphaltBlock::new);

    public static final RegistryObject<Block> BLUE_ASPHALT = BLOCKS.register("blue_asphalt", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_YL = BLOCKS.register("blue_asphalt_yellow_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_YDL = BLOCKS.register("blue_asphalt_yellow_dashed_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_YDBL = BLOCKS.register("blue_asphalt_yellow_double_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_DDBL = BLOCKS.register("blue_asphalt_yellow_dashed_double_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_WL = BLOCKS.register("blue_asphalt_white_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_WDL = BLOCKS.register("blue_asphalt_white_dashed_line", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_CW = BLOCKS.register("blue_asphalt_crosswalk", BlueAsphaltBlock::new);
    public static final RegistryObject<Block> BLUE_ASPHALT_S = BLOCKS.register("blue_asphalt_solid", BlueAsphaltBlock::new);


    public static final RegistryObject<Block> STICK_BLOCK = BLOCKS.register("stick_block", () -> new StickBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.2F)));
    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new RankineEightLayerBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.25F)));
    public static final RegistryObject<Block> SAWDUST = BLOCKS.register("sawdust", () -> new RankineEightLayerBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.25F)));
    public static final RegistryObject<Block> ASH = BLOCKS.register("ash", () -> new RankineEightLayerBlock(Block.Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final RegistryObject<Block> BONE_ASH = BLOCKS.register("bone_ash", () -> new RankineEightLayerBlock(Block.Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final RegistryObject<Block> POZZOLANA = BLOCKS.register("pozzolana", () -> new RankineEightLayerBlock(Block.Properties.of(Material.CLAY).sound(SoundType.GRAVEL).strength(0.25F)));
    public static final RegistryObject<Block> MINERAL_WOOL = BLOCKS.register("mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> WHITE_MINERAL_WOOL = BLOCKS.register("white_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> ORANGE_MINERAL_WOOL = BLOCKS.register("orange_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> MAGENTA_MINERAL_WOOL = BLOCKS.register("magenta_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> LIGHT_BLUE_MINERAL_WOOL = BLOCKS.register("light_blue_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> YELLOW_MINERAL_WOOL = BLOCKS.register("yellow_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> LIME_MINERAL_WOOL = BLOCKS.register("lime_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> PINK_MINERAL_WOOL = BLOCKS.register("pink_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> GRAY_MINERAL_WOOL = BLOCKS.register("gray_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> LIGHT_GRAY_MINERAL_WOOL = BLOCKS.register("light_gray_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> CYAN_MINERAL_WOOL = BLOCKS.register("cyan_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> PURPLE_MINERAL_WOOL = BLOCKS.register("purple_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> BLUE_MINERAL_WOOL = BLOCKS.register("blue_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> GREEN_MINERAL_WOOL = BLOCKS.register("green_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> BROWN_MINERAL_WOOL = BLOCKS.register("brown_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> RED_MINERAL_WOOL = BLOCKS.register("red_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> BLACK_MINERAL_WOOL = BLOCKS.register("black_mineral_wool", () -> new MineralWoolBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));
    public static final RegistryObject<Block> GUN_COTTON = BLOCKS.register("gun_cotton", () -> new GunCottonBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).sound(SoundType.WOOL).strength(1F)));


    //WOOD BLOCKS
    public static final WoodType CEDAR = WoodType.create("rankine:cedar");
    public static final WoodType BALSAM_FIR = WoodType.create("rankine:balsam_fir");
    public static final WoodType EASTERN_HEMLOCK = WoodType.create("rankine:eastern_hemlock");
    public static final WoodType WESTERN_HEMLOCK = WoodType.create("rankine:western_hemlock");
    public static final WoodType PINYON_PINE = WoodType.create("rankine:pinyon_pine");
    public static final WoodType JUNIPER = WoodType.create("rankine:juniper");
    public static final WoodType BLACK_BIRCH = WoodType.create("rankine:black_birch");
    public static final WoodType YELLOW_BIRCH = WoodType.create("rankine:yellow_birch");
    public static final WoodType RED_BIRCH = WoodType.create("rankine:red_birch");
    public static final WoodType MAPLE = WoodType.create("rankine:maple");
    public static final WoodType MAGNOLIA = WoodType.create("rankine:magnolia");
    public static final WoodType BLACK_WALNUT = WoodType.create("rankine:black_walnut");
    public static final WoodType COCONUT_PALM = WoodType.create("rankine:coconut_palm");
    public static final WoodType CORK_OAK = WoodType.create("rankine:cork_oak");
    public static final WoodType SHARINGA = WoodType.create("rankine:sharinga");
    public static final WoodType CINNAMON = WoodType.create("rankine:cinnamon");
    public static final WoodType HONEY_LOCUST = WoodType.create("rankine:honey_locust");
    public static final WoodType WEEPING_WILLOW = WoodType.create("rankine:weeping_willow");
    public static final WoodType ERYTHRINA = WoodType.create("rankine:erythrina");
    public static final WoodType PETRIFIED_CHORUS = WoodType.create("rankine:petrified_chorus");
    public static final WoodType CHARRED = WoodType.create("rankine:charred");
    public static final WoodType BAMBOO = WoodType.create("rankine:bamboo");
    public static final WoodType BAMBOO_CULM = WoodType.create("rankine:bamboo_culm");

    public static final RegistryObject<Block> CEDAR_LOG = BLOCKS.register("cedar_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_WOOD = BLOCKS.register("cedar_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CEDAR_LOG = BLOCKS.register("stripped_cedar_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CEDAR_WOOD = BLOCKS.register("stripped_cedar_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_PLANKS = BLOCKS.register("cedar_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_SLAB = BLOCKS.register("cedar_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_STAIRS = BLOCKS.register("cedar_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_BOOKSHELF = BLOCKS.register("cedar_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> CEDAR_FENCE = BLOCKS.register("cedar_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_FENCE_GATE = BLOCKS.register("cedar_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> CEDAR_PRESSURE_PLATE = BLOCKS.register("cedar_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> CEDAR_DOOR = BLOCKS.register("cedar_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> CEDAR_TRAPDOOR = BLOCKS.register("cedar_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> CEDAR_BUTTON = BLOCKS.register("cedar_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> CEDAR_SIGN = BLOCKS.register("cedar_sign", () -> new RankineSignBlock(CEDAR));
    public static final RegistryObject<Block> CEDAR_WALL_SIGN = BLOCKS.register("cedar_wall_sign", () -> new RankineWallSignBlock(CEDAR));
    public static final RegistryObject<Block> BALSAM_FIR_LOG = BLOCKS.register("balsam_fir_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_WOOD = BLOCKS.register("balsam_fir_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BALSAM_FIR_LOG = BLOCKS.register("stripped_balsam_fir_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BALSAM_FIR_WOOD = BLOCKS.register("stripped_balsam_fir_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_PLANKS = BLOCKS.register("balsam_fir_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_SLAB = BLOCKS.register("balsam_fir_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_STAIRS = BLOCKS.register("balsam_fir_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_BOOKSHELF = BLOCKS.register("balsam_fir_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> BALSAM_FIR_FENCE = BLOCKS.register("balsam_fir_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_FENCE_GATE = BLOCKS.register("balsam_fir_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> BALSAM_FIR_PRESSURE_PLATE = BLOCKS.register("balsam_fir_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> BALSAM_FIR_DOOR = BLOCKS.register("balsam_fir_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> BALSAM_FIR_TRAPDOOR = BLOCKS.register("balsam_fir_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> BALSAM_FIR_BUTTON = BLOCKS.register("balsam_fir_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> BALSAM_FIR_SIGN = BLOCKS.register("balsam_fir_sign", () -> new RankineSignBlock(BALSAM_FIR));
    public static final RegistryObject<Block> BALSAM_FIR_WALL_SIGN = BLOCKS.register("balsam_fir_wall_sign", () -> new RankineWallSignBlock(BALSAM_FIR));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_LOG = BLOCKS.register("eastern_hemlock_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_WOOD = BLOCKS.register("eastern_hemlock_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_EASTERN_HEMLOCK_LOG = BLOCKS.register("stripped_eastern_hemlock_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_EASTERN_HEMLOCK_WOOD = BLOCKS.register("stripped_eastern_hemlock_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_PLANKS = BLOCKS.register("eastern_hemlock_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_SLAB = BLOCKS.register("eastern_hemlock_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_STAIRS = BLOCKS.register("eastern_hemlock_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_BOOKSHELF = BLOCKS.register("eastern_hemlock_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_FENCE = BLOCKS.register("eastern_hemlock_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_FENCE_GATE = BLOCKS.register("eastern_hemlock_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_PRESSURE_PLATE = BLOCKS.register("eastern_hemlock_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_DOOR = BLOCKS.register("eastern_hemlock_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_TRAPDOOR = BLOCKS.register("eastern_hemlock_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_BUTTON = BLOCKS.register("eastern_hemlock_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_SIGN = BLOCKS.register("eastern_hemlock_sign", () -> new RankineSignBlock(EASTERN_HEMLOCK));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_WALL_SIGN = BLOCKS.register("eastern_hemlock_wall_sign", () -> new RankineWallSignBlock(EASTERN_HEMLOCK));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_LOG = BLOCKS.register("western_hemlock_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_WOOD = BLOCKS.register("western_hemlock_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_WESTERN_HEMLOCK_LOG = BLOCKS.register("stripped_western_hemlock_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_WESTERN_HEMLOCK_WOOD = BLOCKS.register("stripped_western_hemlock_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_PLANKS = BLOCKS.register("western_hemlock_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_SLAB = BLOCKS.register("western_hemlock_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_STAIRS = BLOCKS.register("western_hemlock_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_BOOKSHELF = BLOCKS.register("western_hemlock_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_FENCE = BLOCKS.register("western_hemlock_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_FENCE_GATE = BLOCKS.register("western_hemlock_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_PRESSURE_PLATE = BLOCKS.register("western_hemlock_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_DOOR = BLOCKS.register("western_hemlock_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_TRAPDOOR = BLOCKS.register("western_hemlock_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_BUTTON = BLOCKS.register("western_hemlock_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_SIGN = BLOCKS.register("western_hemlock_sign", () -> new RankineSignBlock(WESTERN_HEMLOCK));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_WALL_SIGN = BLOCKS.register("western_hemlock_wall_sign", () -> new RankineWallSignBlock(WESTERN_HEMLOCK));
    public static final RegistryObject<Block> PINYON_PINE_LOG = BLOCKS.register("pinyon_pine_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_WOOD = BLOCKS.register("pinyon_pine_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_PINYON_PINE_LOG = BLOCKS.register("stripped_pinyon_pine_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_PINYON_PINE_WOOD = BLOCKS.register("stripped_pinyon_pine_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_PLANKS = BLOCKS.register("pinyon_pine_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_SLAB = BLOCKS.register("pinyon_pine_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_STAIRS = BLOCKS.register("pinyon_pine_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_BOOKSHELF = BLOCKS.register("pinyon_pine_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> PINYON_PINE_FENCE = BLOCKS.register("pinyon_pine_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_FENCE_GATE = BLOCKS.register("pinyon_pine_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> PINYON_PINE_PRESSURE_PLATE = BLOCKS.register("pinyon_pine_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> PINYON_PINE_DOOR = BLOCKS.register("pinyon_pine_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> PINYON_PINE_TRAPDOOR = BLOCKS.register("pinyon_pine_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> PINYON_PINE_BUTTON = BLOCKS.register("pinyon_pine_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> PINYON_PINE_SIGN = BLOCKS.register("pinyon_pine_sign", () -> new RankineSignBlock(PINYON_PINE));
    public static final RegistryObject<Block> PINYON_PINE_WALL_SIGN = BLOCKS.register("pinyon_pine_wall_sign", () -> new RankineWallSignBlock(PINYON_PINE));
    public static final RegistryObject<Block> JUNIPER_LOG = BLOCKS.register("juniper_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_WOOD = BLOCKS.register("juniper_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_JUNIPER_LOG = BLOCKS.register("stripped_juniper_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_JUNIPER_WOOD = BLOCKS.register("stripped_juniper_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_PLANKS = BLOCKS.register("juniper_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_SLAB = BLOCKS.register("juniper_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_STAIRS = BLOCKS.register("juniper_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_BOOKSHELF = BLOCKS.register("juniper_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> JUNIPER_FENCE = BLOCKS.register("juniper_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_FENCE_GATE = BLOCKS.register("juniper_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> JUNIPER_PRESSURE_PLATE = BLOCKS.register("juniper_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> JUNIPER_DOOR = BLOCKS.register("juniper_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> JUNIPER_TRAPDOOR = BLOCKS.register("juniper_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> JUNIPER_BUTTON = BLOCKS.register("juniper_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> JUNIPER_SIGN = BLOCKS.register("juniper_sign", () -> new RankineSignBlock(JUNIPER));
    public static final RegistryObject<Block> JUNIPER_WALL_SIGN = BLOCKS.register("juniper_wall_sign", () -> new RankineWallSignBlock(JUNIPER));
    public static final RegistryObject<Block> BLACK_BIRCH_LOG = BLOCKS.register("black_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_WOOD = BLOCKS.register("black_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BLACK_BIRCH_LOG = BLOCKS.register("stripped_black_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BLACK_BIRCH_WOOD = BLOCKS.register("stripped_black_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_PLANKS = BLOCKS.register("black_birch_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_SLAB = BLOCKS.register("black_birch_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_STAIRS = BLOCKS.register("black_birch_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_BOOKSHELF = BLOCKS.register("black_birch_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> BLACK_BIRCH_FENCE = BLOCKS.register("black_birch_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_FENCE_GATE = BLOCKS.register("black_birch_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_BIRCH_PRESSURE_PLATE = BLOCKS.register("black_birch_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> BLACK_BIRCH_DOOR = BLOCKS.register("black_birch_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> BLACK_BIRCH_TRAPDOOR = BLOCKS.register("black_birch_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> BLACK_BIRCH_BUTTON = BLOCKS.register("black_birch_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> BLACK_BIRCH_SIGN = BLOCKS.register("black_birch_sign", () -> new RankineSignBlock(BLACK_BIRCH));
    public static final RegistryObject<Block> BLACK_BIRCH_WALL_SIGN = BLOCKS.register("black_birch_wall_sign", () -> new RankineWallSignBlock(BLACK_BIRCH));
    public static final RegistryObject<Block> YELLOW_BIRCH_LOG = BLOCKS.register("yellow_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_WOOD = BLOCKS.register("yellow_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_YELLOW_BIRCH_LOG = BLOCKS.register("stripped_yellow_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_YELLOW_BIRCH_WOOD = BLOCKS.register("stripped_yellow_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_PLANKS = BLOCKS.register("yellow_birch_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_SLAB = BLOCKS.register("yellow_birch_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_STAIRS = BLOCKS.register("yellow_birch_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_BOOKSHELF = BLOCKS.register("yellow_birch_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_FENCE = BLOCKS.register("yellow_birch_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_FENCE_GATE = BLOCKS.register("yellow_birch_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> YELLOW_BIRCH_PRESSURE_PLATE = BLOCKS.register("yellow_birch_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_DOOR = BLOCKS.register("yellow_birch_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_TRAPDOOR = BLOCKS.register("yellow_birch_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_BUTTON = BLOCKS.register("yellow_birch_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_SIGN = BLOCKS.register("yellow_birch_sign", () -> new RankineSignBlock(YELLOW_BIRCH));
    public static final RegistryObject<Block> YELLOW_BIRCH_WALL_SIGN = BLOCKS.register("yellow_birch_wall_sign", () -> new RankineWallSignBlock(YELLOW_BIRCH));
    public static final RegistryObject<Block> RED_BIRCH_LOG = BLOCKS.register("red_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_WOOD = BLOCKS.register("red_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_RED_BIRCH_LOG = BLOCKS.register("stripped_red_birch_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_RED_BIRCH_WOOD = BLOCKS.register("stripped_red_birch_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_PLANKS = BLOCKS.register("red_birch_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_SLAB = BLOCKS.register("red_birch_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_STAIRS = BLOCKS.register("red_birch_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_BOOKSHELF = BLOCKS.register("red_birch_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> RED_BIRCH_FENCE = BLOCKS.register("red_birch_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_FENCE_GATE = BLOCKS.register("red_birch_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> RED_BIRCH_PRESSURE_PLATE = BLOCKS.register("red_birch_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> RED_BIRCH_DOOR = BLOCKS.register("red_birch_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> RED_BIRCH_TRAPDOOR = BLOCKS.register("red_birch_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> RED_BIRCH_BUTTON = BLOCKS.register("red_birch_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> RED_BIRCH_SIGN = BLOCKS.register("red_birch_sign", () -> new RankineSignBlock(RED_BIRCH));
    public static final RegistryObject<Block> RED_BIRCH_WALL_SIGN = BLOCKS.register("red_birch_wall_sign", () -> new RankineWallSignBlock(RED_BIRCH));
    public static final RegistryObject<Block> MAPLE_LOG = BLOCKS.register("maple_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_WOOD = BLOCKS.register("maple_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_SLAB = BLOCKS.register("maple_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_STAIRS = BLOCKS.register("maple_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_BOOKSHELF = BLOCKS.register("maple_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> MAPLE_FENCE = BLOCKS.register("maple_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> MAPLE_DOOR = BLOCKS.register("maple_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> MAPLE_BUTTON = BLOCKS.register("maple_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> MAPLE_SIGN = BLOCKS.register("maple_sign", () -> new RankineSignBlock(MAPLE));
    public static final RegistryObject<Block> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", () -> new RankineWallSignBlock(MAPLE));
    public static final RegistryObject<Block> MAGNOLIA_LOG = BLOCKS.register("magnolia_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_WOOD = BLOCKS.register("magnolia_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_MAGNOLIA_LOG = BLOCKS.register("stripped_magnolia_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_MAGNOLIA_WOOD = BLOCKS.register("stripped_magnolia_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_PLANKS = BLOCKS.register("magnolia_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_SLAB = BLOCKS.register("magnolia_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_STAIRS = BLOCKS.register("magnolia_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_BOOKSHELF = BLOCKS.register("magnolia_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> MAGNOLIA_FENCE = BLOCKS.register("magnolia_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_FENCE_GATE = BLOCKS.register("magnolia_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> MAGNOLIA_PRESSURE_PLATE = BLOCKS.register("magnolia_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> MAGNOLIA_DOOR = BLOCKS.register("magnolia_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> MAGNOLIA_TRAPDOOR = BLOCKS.register("magnolia_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> MAGNOLIA_BUTTON = BLOCKS.register("magnolia_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> MAGNOLIA_SIGN = BLOCKS.register("magnolia_sign", () -> new RankineSignBlock(MAGNOLIA));
    public static final RegistryObject<Block> MAGNOLIA_WALL_SIGN = BLOCKS.register("magnolia_wall_sign", () -> new RankineWallSignBlock(MAGNOLIA));
    public static final RegistryObject<Block> BLACK_WALNUT_LOG = BLOCKS.register("black_walnut_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_WOOD = BLOCKS.register("black_walnut_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BLACK_WALNUT_LOG = BLOCKS.register("stripped_black_walnut_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_BLACK_WALNUT_WOOD = BLOCKS.register("stripped_black_walnut_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_PLANKS = BLOCKS.register("black_walnut_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_SLAB = BLOCKS.register("black_walnut_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_STAIRS = BLOCKS.register("black_walnut_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_BOOKSHELF = BLOCKS.register("black_walnut_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> BLACK_WALNUT_FENCE = BLOCKS.register("black_walnut_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_FENCE_GATE = BLOCKS.register("black_walnut_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> BLACK_WALNUT_PRESSURE_PLATE = BLOCKS.register("black_walnut_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> BLACK_WALNUT_DOOR = BLOCKS.register("black_walnut_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> BLACK_WALNUT_TRAPDOOR = BLOCKS.register("black_walnut_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> BLACK_WALNUT_BUTTON = BLOCKS.register("black_walnut_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> BLACK_WALNUT_SIGN = BLOCKS.register("black_walnut_sign", () -> new RankineSignBlock(BLACK_WALNUT));
    public static final RegistryObject<Block> BLACK_WALNUT_WALL_SIGN = BLOCKS.register("black_walnut_wall_sign", () -> new RankineWallSignBlock(BLACK_WALNUT));
    public static final RegistryObject<Block> COCONUT_PALM_LOG = BLOCKS.register("coconut_palm_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_WOOD = BLOCKS.register("coconut_palm_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_COCONUT_PALM_LOG = BLOCKS.register("stripped_coconut_palm_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_COCONUT_PALM_WOOD = BLOCKS.register("stripped_coconut_palm_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_PLANKS = BLOCKS.register("coconut_palm_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_SLAB = BLOCKS.register("coconut_palm_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_STAIRS = BLOCKS.register("coconut_palm_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_BOOKSHELF = BLOCKS.register("coconut_palm_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> COCONUT_PALM_FENCE = BLOCKS.register("coconut_palm_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_FENCE_GATE = BLOCKS.register("coconut_palm_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> COCONUT_PALM_PRESSURE_PLATE = BLOCKS.register("coconut_palm_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> COCONUT_PALM_DOOR = BLOCKS.register("coconut_palm_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> COCONUT_PALM_TRAPDOOR = BLOCKS.register("coconut_palm_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> COCONUT_PALM_BUTTON = BLOCKS.register("coconut_palm_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> COCONUT_PALM_SIGN = BLOCKS.register("coconut_palm_sign", () -> new RankineSignBlock(COCONUT_PALM));
    public static final RegistryObject<Block> COCONUT_PALM_WALL_SIGN = BLOCKS.register("coconut_palm_wall_sign", () -> new RankineWallSignBlock(COCONUT_PALM));
    public static final RegistryObject<Block> CORK_OAK_LOG = BLOCKS.register("cork_oak_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_WOOD = BLOCKS.register("cork_oak_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CORK_OAK_LOG = BLOCKS.register("stripped_cork_oak_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CORK_OAK_WOOD = BLOCKS.register("stripped_cork_oak_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_PLANKS = BLOCKS.register("cork_oak_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_SLAB = BLOCKS.register("cork_oak_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_STAIRS = BLOCKS.register("cork_oak_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_BOOKSHELF = BLOCKS.register("cork_oak_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> CORK_OAK_FENCE = BLOCKS.register("cork_oak_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_FENCE_GATE = BLOCKS.register("cork_oak_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> CORK_OAK_PRESSURE_PLATE = BLOCKS.register("cork_oak_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> CORK_OAK_DOOR = BLOCKS.register("cork_oak_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> CORK_OAK_TRAPDOOR = BLOCKS.register("cork_oak_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> CORK_OAK_BUTTON = BLOCKS.register("cork_oak_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> CORK_OAK_SIGN = BLOCKS.register("cork_oak_sign", () -> new RankineSignBlock(CORK_OAK));
    public static final RegistryObject<Block> CORK_OAK_WALL_SIGN = BLOCKS.register("cork_oak_wall_sign", () -> new RankineWallSignBlock(CORK_OAK));
    public static final RegistryObject<Block> SHARINGA_LOG = BLOCKS.register("sharinga_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_WOOD = BLOCKS.register("sharinga_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_SHARINGA_LOG = BLOCKS.register("stripped_sharinga_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_SHARINGA_WOOD = BLOCKS.register("stripped_sharinga_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_PLANKS = BLOCKS.register("sharinga_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_SLAB = BLOCKS.register("sharinga_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_STAIRS = BLOCKS.register("sharinga_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_BOOKSHELF = BLOCKS.register("sharinga_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> SHARINGA_FENCE = BLOCKS.register("sharinga_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_FENCE_GATE = BLOCKS.register("sharinga_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> SHARINGA_PRESSURE_PLATE = BLOCKS.register("sharinga_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> SHARINGA_DOOR = BLOCKS.register("sharinga_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> SHARINGA_TRAPDOOR = BLOCKS.register("sharinga_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> SHARINGA_BUTTON = BLOCKS.register("sharinga_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> SHARINGA_SIGN = BLOCKS.register("sharinga_sign", () -> new RankineSignBlock(SHARINGA));
    public static final RegistryObject<Block> SHARINGA_WALL_SIGN = BLOCKS.register("sharinga_wall_sign", () -> new RankineWallSignBlock(SHARINGA));
    public static final RegistryObject<Block> CINNAMON_LOG = BLOCKS.register("cinnamon_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_WOOD = BLOCKS.register("cinnamon_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CINNAMON_LOG = BLOCKS.register("stripped_cinnamon_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CINNAMON_WOOD = BLOCKS.register("stripped_cinnamon_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_PLANKS = BLOCKS.register("cinnamon_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_SLAB = BLOCKS.register("cinnamon_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_STAIRS = BLOCKS.register("cinnamon_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_BOOKSHELF = BLOCKS.register("cinnamon_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> CINNAMON_FENCE = BLOCKS.register("cinnamon_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_FENCE_GATE = BLOCKS.register("cinnamon_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> CINNAMON_PRESSURE_PLATE = BLOCKS.register("cinnamon_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> CINNAMON_DOOR = BLOCKS.register("cinnamon_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> CINNAMON_TRAPDOOR = BLOCKS.register("cinnamon_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> CINNAMON_BUTTON = BLOCKS.register("cinnamon_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> CINNAMON_SIGN = BLOCKS.register("cinnamon_sign", () -> new RankineSignBlock(CINNAMON));
    public static final RegistryObject<Block> CINNAMON_WALL_SIGN = BLOCKS.register("cinnamon_wall_sign", () -> new RankineWallSignBlock(CINNAMON));
    public static final RegistryObject<Block> HONEY_LOCUST_LOG = BLOCKS.register("honey_locust_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_WOOD = BLOCKS.register("honey_locust_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_HONEY_LOCUST_LOG = BLOCKS.register("stripped_honey_locust_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_HONEY_LOCUST_WOOD = BLOCKS.register("stripped_honey_locust_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_PLANKS = BLOCKS.register("honey_locust_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_SLAB = BLOCKS.register("honey_locust_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_STAIRS = BLOCKS.register("honey_locust_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_BOOKSHELF = BLOCKS.register("honey_locust_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> HONEY_LOCUST_FENCE = BLOCKS.register("honey_locust_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_FENCE_GATE = BLOCKS.register("honey_locust_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> HONEY_LOCUST_PRESSURE_PLATE = BLOCKS.register("honey_locust_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> HONEY_LOCUST_DOOR = BLOCKS.register("honey_locust_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> HONEY_LOCUST_TRAPDOOR = BLOCKS.register("honey_locust_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> HONEY_LOCUST_BUTTON = BLOCKS.register("honey_locust_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> HONEY_LOCUST_SIGN = BLOCKS.register("honey_locust_sign", () -> new RankineSignBlock(HONEY_LOCUST));
    public static final RegistryObject<Block> HONEY_LOCUST_WALL_SIGN = BLOCKS.register("honey_locust_wall_sign", () -> new RankineWallSignBlock(HONEY_LOCUST));
    public static final RegistryObject<Block> WEEPING_WILLOW_LOG = BLOCKS.register("weeping_willow_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_WOOD = BLOCKS.register("weeping_willow_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_WEEPING_WILLOW_LOG = BLOCKS.register("stripped_weeping_willow_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_WEEPING_WILLOW_WOOD = BLOCKS.register("stripped_weeping_willow_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_PLANKS = BLOCKS.register("weeping_willow_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_SLAB = BLOCKS.register("weeping_willow_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_STAIRS = BLOCKS.register("weeping_willow_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_BOOKSHELF = BLOCKS.register("weeping_willow_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_FENCE = BLOCKS.register("weeping_willow_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_FENCE_GATE = BLOCKS.register("weeping_willow_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> WEEPING_WILLOW_PRESSURE_PLATE = BLOCKS.register("weeping_willow_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_DOOR = BLOCKS.register("weeping_willow_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_TRAPDOOR = BLOCKS.register("weeping_willow_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_BUTTON = BLOCKS.register("weeping_willow_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_SIGN = BLOCKS.register("weeping_willow_sign", () -> new RankineSignBlock(WEEPING_WILLOW));
    public static final RegistryObject<Block> WEEPING_WILLOW_WALL_SIGN = BLOCKS.register("weeping_willow_wall_sign", () -> new RankineWallSignBlock(WEEPING_WILLOW));
    public static final RegistryObject<Block> ERYTHRINA_LOG = BLOCKS.register("erythrina_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_WOOD = BLOCKS.register("erythrina_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_ERYTHRINA_LOG = BLOCKS.register("stripped_erythrina_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_ERYTHRINA_WOOD = BLOCKS.register("stripped_erythrina_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_PLANKS = BLOCKS.register("erythrina_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_SLAB = BLOCKS.register("erythrina_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_STAIRS = BLOCKS.register("erythrina_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_BOOKSHELF = BLOCKS.register("erythrina_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> ERYTHRINA_FENCE = BLOCKS.register("erythrina_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_FENCE_GATE = BLOCKS.register("erythrina_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> ERYTHRINA_PRESSURE_PLATE = BLOCKS.register("erythrina_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> ERYTHRINA_DOOR = BLOCKS.register("erythrina_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> ERYTHRINA_TRAPDOOR = BLOCKS.register("erythrina_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> ERYTHRINA_BUTTON = BLOCKS.register("erythrina_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> ERYTHRINA_SIGN = BLOCKS.register("erythrina_sign", () -> new RankineSignBlock(ERYTHRINA));
    public static final RegistryObject<Block> ERYTHRINA_WALL_SIGN = BLOCKS.register("erythrina_wall_sign", () -> new RankineWallSignBlock(ERYTHRINA));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_LOG = BLOCKS.register("petrified_chorus_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_WOOD = BLOCKS.register("petrified_chorus_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_CHORUS_LOG = BLOCKS.register("stripped_petrified_chorus_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_PETRIFIED_CHORUS_WOOD = BLOCKS.register("stripped_petrified_chorus_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_PLANKS = BLOCKS.register("petrified_chorus_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_SLAB = BLOCKS.register("petrified_chorus_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_STAIRS = BLOCKS.register("petrified_chorus_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_BOOKSHELF = BLOCKS.register("petrified_chorus_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> PETRIFIED_CHORUS_FENCE = BLOCKS.register("petrified_chorus_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_FENCE_GATE = BLOCKS.register("petrified_chorus_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_PRESSURE_PLATE = BLOCKS.register("petrified_chorus_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> PETRIFIED_CHORUS_DOOR = BLOCKS.register("petrified_chorus_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> PETRIFIED_CHORUS_TRAPDOOR = BLOCKS.register("petrified_chorus_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> PETRIFIED_CHORUS_BUTTON = BLOCKS.register("petrified_chorus_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> PETRIFIED_CHORUS_SIGN = BLOCKS.register("petrified_chorus_sign", () -> new RankineSignBlock(PETRIFIED_CHORUS));
    public static final RegistryObject<Block> PETRIFIED_CHORUS_WALL_SIGN = BLOCKS.register("petrified_chorus_wall_sign", () -> new RankineWallSignBlock(PETRIFIED_CHORUS));
    public static final RegistryObject<Block> CHARRED_LOG = BLOCKS.register("charred_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_WOOD = BLOCKS.register("charred_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CHARRED_LOG = BLOCKS.register("stripped_charred_log", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> STRIPPED_CHARRED_WOOD = BLOCKS.register("stripped_charred_wood", () -> new RankineLogBlock(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_PLANKS = BLOCKS.register("charred_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_SLAB = BLOCKS.register("charred_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_STAIRS = BLOCKS.register("charred_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_BOOKSHELF = BLOCKS.register("charred_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> CHARRED_FENCE = BLOCKS.register("charred_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_FENCE_GATE = BLOCKS.register("charred_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> CHARRED_PRESSURE_PLATE = BLOCKS.register("charred_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> CHARRED_DOOR = BLOCKS.register("charred_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> CHARRED_TRAPDOOR = BLOCKS.register("charred_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> CHARRED_BUTTON = BLOCKS.register("charred_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> CHARRED_SIGN = BLOCKS.register("charred_sign", () -> new RankineSignBlock(CHARRED));
    public static final RegistryObject<Block> CHARRED_WALL_SIGN = BLOCKS.register("charred_wall_sign", () -> new RankineWallSignBlock(CHARRED));
    public static final RegistryObject<Block> BAMBOO_PLANKS = BLOCKS.register("bamboo_planks", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_SLAB = BLOCKS.register("bamboo_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_STAIRS = BLOCKS.register("bamboo_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_BOOKSHELF = BLOCKS.register("bamboo_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> BAMBOO_FENCE = BLOCKS.register("bamboo_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_FENCE_GATE = BLOCKS.register("bamboo_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_PRESSURE_PLATE = BLOCKS.register("bamboo_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> BAMBOO_DOOR = BLOCKS.register("bamboo_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> BAMBOO_TRAPDOOR = BLOCKS.register("bamboo_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> BAMBOO_BUTTON = BLOCKS.register("bamboo_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> BAMBOO_SIGN = BLOCKS.register("bamboo_sign", () -> new RankineSignBlock(BAMBOO));
    public static final RegistryObject<Block> BAMBOO_WALL_SIGN = BLOCKS.register("bamboo_wall_sign", () -> new RankineWallSignBlock(BAMBOO));
    public static final RegistryObject<Block> BAMBOO_CULMS = BLOCKS.register("bamboo_culms", () -> new RankinePlanksBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_CULMS_SLAB = BLOCKS.register("bamboo_culms_slab", () -> new RankineWoodenSlabBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_CULMS_STAIRS = BLOCKS.register("bamboo_culms_stairs", () -> new RankineWoodenStairsBlock(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_CULMS_BOOKSHELF = BLOCKS.register("bamboo_culms_bookshelf", RankineBookshelvesBlock::new);
    public static final RegistryObject<Block> BAMBOO_CULMS_FENCE = BLOCKS.register("bamboo_culms_fence", () -> new RankineWoodenFence(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_CULMS_FENCE_GATE = BLOCKS.register("bamboo_culms_fence_gate", () -> new RankineWoodenFenceGate(DEF_WOOD));
    public static final RegistryObject<Block> BAMBOO_CULMS_PRESSURE_PLATE = BLOCKS.register("bamboo_culms_pressure_plate", RankineWoodenPressurePlate::new);
    public static final RegistryObject<Block> BAMBOO_CULMS_DOOR = BLOCKS.register("bamboo_culms_door", RankineWoodenDoor::new);
    public static final RegistryObject<Block> BAMBOO_CULMS_TRAPDOOR = BLOCKS.register("bamboo_culms_trapdoor", RankineWoodenTrapDoor::new);
    public static final RegistryObject<Block> BAMBOO_CULMS_BUTTON = BLOCKS.register("bamboo_culms_button", RankineWoodenButton::new);
    public static final RegistryObject<Block> BAMBOO_CULMS_SIGN = BLOCKS.register("bamboo_culms_sign", () -> new RankineSignBlock(BAMBOO_CULM));
    public static final RegistryObject<Block> BAMBOO_CULMS_WALL_SIGN = BLOCKS.register("bamboo_culms_wall_sign", () -> new RankineWallSignBlock(BAMBOO_CULM));


    public static final RegistryObject<Block> HOLLOW_OAK_LOG = BLOCKS.register("hollow_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_SPRUCE_LOG = BLOCKS.register("hollow_spruce_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BIRCH_LOG = BLOCKS.register("hollow_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_JUNGLE_LOG = BLOCKS.register("hollow_jungle_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_ACACIA_LOG = BLOCKS.register("hollow_acacia_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_DARK_OAK_LOG = BLOCKS.register("hollow_dark_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_WARPED_STEM = BLOCKS.register("hollow_warped_stem", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CRIMSON_STEM = BLOCKS.register("hollow_crimson_stem", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CEDAR_LOG = BLOCKS.register("hollow_cedar_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_PINYON_PINE_LOG = BLOCKS.register("hollow_pinyon_pine_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_JUNIPER_LOG = BLOCKS.register("hollow_juniper_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_COCONUT_PALM_LOG = BLOCKS.register("hollow_coconut_palm_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BALSAM_FIR_LOG = BLOCKS.register("hollow_balsam_fir_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_MAGNOLIA_LOG = BLOCKS.register("hollow_magnolia_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_EASTERN_HEMLOCK_LOG = BLOCKS.register("hollow_eastern_hemlock_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_WESTERN_HEMLOCK_LOG = BLOCKS.register("hollow_western_hemlock_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_YELLOW_BIRCH_LOG = BLOCKS.register("hollow_yellow_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BLACK_BIRCH_LOG = BLOCKS.register("hollow_black_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_RED_BIRCH_LOG = BLOCKS.register("hollow_red_birch_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_MAPLE_LOG = BLOCKS.register("hollow_maple_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_SHARINGA_LOG = BLOCKS.register("hollow_sharinga_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_BLACK_WALNUT_LOG = BLOCKS.register("hollow_black_walnut_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CORK_OAK_LOG = BLOCKS.register("hollow_cork_oak_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CINNAMON_LOG = BLOCKS.register("hollow_cinnamon_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_ERYTHRINA_LOG = BLOCKS.register("hollow_erythrina_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_WEEPING_WILLOW_LOG = BLOCKS.register("hollow_weeping_willow_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_HONEY_LOCUST_LOG = BLOCKS.register("hollow_honey_locust_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_PETRIFIED_CHORUS_LOG = BLOCKS.register("hollow_petrified_chorus_log", HollowLogBlock::new);
    public static final RegistryObject<Block> HOLLOW_CHARRED_LOG = BLOCKS.register("hollow_charred_log", HollowLogBlock::new);



    public static final RegistryObject<Block> STUMP = BLOCKS.register("stump", () -> new StumpBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).requiresCorrectToolForDrops().strength(30.0F, 9.0F)));


    public static final RegistryObject<Block> BRASS_DOOR = BLOCKS.register("brass_door", RankineMetalDoor::new);
    public static final RegistryObject<Block> BRONZE_DOOR = BLOCKS.register("bronze_door", RankineMetalDoor::new);
    public static final RegistryObject<Block> CUPRONICKEL_DOOR = BLOCKS.register("cupronickel_door", RankineMetalDoor::new);
    public static final RegistryObject<Block> STEEL_DOOR = BLOCKS.register("steel_door", RankineMetalDoor::new);
    public static final RegistryObject<Block> BRASS_TRAPDOOR = BLOCKS.register("brass_trapdoor", RankineMetalTrapdoor::new);
    public static final RegistryObject<Block> BRONZE_TRAPDOOR = BLOCKS.register("bronze_trapdoor", RankineMetalTrapdoor::new);
    public static final RegistryObject<Block> STEEL_TRAPDOOR = BLOCKS.register("steel_trapdoor", RankineMetalTrapdoor::new);
    public static final RegistryObject<Block> CUPRONICKEL_TRAPDOOR = BLOCKS.register("cupronickel_trapdoor", RankineMetalTrapdoor::new);

    public static final RegistryObject<Block> ORANGE_LILY = BLOCKS.register("orange_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> RED_LILY = BLOCKS.register("red_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WHITE_LILY = BLOCKS.register("white_lily", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> PURPLE_MORNING_GLORY = BLOCKS.register("purple_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> BLUE_MORNING_GLORY = BLOCKS.register("blue_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> BLACK_MORNING_GLORY = BLOCKS.register("black_morning_glory", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> PINK_BELLFLOWER = BLOCKS.register("pink_bellflower", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> VIOLET_BELLFLOWER = BLOCKS.register("violet_bellflower", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> GOLDENROD = BLOCKS.register("goldenrod", () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SHORT_GRASS = BLOCKS.register("short_grass", () -> new ShortGrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> STINGING_NETTLE = BLOCKS.register("stinging_nettle", () -> new StingingNettleBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> YELLOW_CLOVER = BLOCKS.register("yellow_clover", () -> new CloverBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WHITE_CLOVER = BLOCKS.register("white_clover", () -> new CloverBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> RED_CLOVER = BLOCKS.register("red_clover", () -> new CloverBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> CRIMSON_CLOVER = BLOCKS.register("crimson_clover", () -> new CloverBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WILLOW_BRANCHLET = BLOCKS.register("willow_branchlet", () -> new WillowBranchletTopBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.GRASS).noCollission().instabreak().sound(SoundType.VINE)));
    public static final RegistryObject<Block> WILLOW_BRANCHLET_PLANT = BLOCKS.register("willow_branchlet_plant", () -> new WillowBranchletBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.GRASS).noCollission().instabreak().sound(SoundType.VINE)));
    public static final RegistryObject<Block> LOCUST_SPINE = BLOCKS.register("locust_spine", () -> new LocustSpineBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OYSTER_MUSHROOM = BLOCKS.register("oyster_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_OYSTER_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> LIONS_MANE_MUSHROOM = BLOCKS.register("lions_mane_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_LIONS_MANE_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> SULFUR_SHELF_MUSHROOM = BLOCKS.register("sulfur_shelf_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_SULFUR_SHELF_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> HONEY_MUSHROOM = BLOCKS.register("honey_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_HONEY_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> ARTIST_CONK_MUSHROOM = BLOCKS.register("artist_conk_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_ARTIST_CONK_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> TINDER_CONK_MUSHROOM = BLOCKS.register("tinder_conk_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_TINDER_CONK_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> TURKEY_TAIL_MUSHROOM = BLOCKS.register("turkey_tail_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_TURKEY_TAIL_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> CINNABAR_POLYPORE_MUSHROOM = BLOCKS.register("cinnabar_polypore_mushroom", () -> new RankineWallMushroomBlock(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_RED).noCollission().randomTicks().instabreak().sound(SoundType.GRASS), () -> RankineConfiguredFeatures.CONFIGURED_CINNABAR_POLYPORE_MUSHROOM.getHolder().get()));
    public static final RegistryObject<Block> OYSTER_MUSHROOM_BLOCK = BLOCKS.register("oyster_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> LIONS_MANE_MUSHROOM_BLOCK = BLOCKS.register("lions_mane_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SULFUR_SHELF_MUSHROOM_BLOCK = BLOCKS.register("sulfur_shelf_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_MUSHROOM_BLOCK = BLOCKS.register("honey_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ARTIST_CONK_MUSHROOM_BLOCK = BLOCKS.register("artist_conk_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TINDER_CONK_MUSHROOM_BLOCK = BLOCKS.register("tinder_conk_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> TURKEY_TAIL_MUSHROOM_BLOCK = BLOCKS.register("turkey_tail_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CINNABAR_POLYPORE_MUSHROOM_BLOCK = BLOCKS.register("cinnabar_polypore_mushroom_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(0.2F).sound(SoundType.WOOD)));


    public static final RegistryObject<Block> CEDAR_LEAVES = BLOCKS.register("cedar_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> COCONUT_PALM_LEAVES = BLOCKS.register("coconut_palm_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> PINYON_PINE_LEAVES = BLOCKS.register("pinyon_pine_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> JUNIPER_LEAVES = BLOCKS.register("juniper_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> BALSAM_FIR_LEAVES = BLOCKS.register("balsam_fir_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> MAGNOLIA_LEAVES = BLOCKS.register("magnolia_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_LEAVES = BLOCKS.register("eastern_hemlock_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_LEAVES = BLOCKS.register("western_hemlock_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> YELLOW_BIRCH_LEAVES = BLOCKS.register("yellow_birch_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> BLACK_BIRCH_LEAVES = BLOCKS.register("black_birch_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> RED_BIRCH_LEAVES = BLOCKS.register("red_birch_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> SHARINGA_LEAVES = BLOCKS.register("sharinga_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> BLACK_WALNUT_LEAVES = BLOCKS.register("black_walnut_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> CORK_OAK_LEAVES = BLOCKS.register("cork_oak_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> CINNAMON_LEAVES = BLOCKS.register("cinnamon_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> ERYTHRINA_LEAVES = BLOCKS.register("erythrina_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> WEEPING_WILLOW_LEAVES = BLOCKS.register("weeping_willow_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));
    public static final RegistryObject<Block> HONEY_LOCUST_LEAVES = BLOCKS.register("honey_locust_leaves", () -> new RankineLeavesBlock(DEF_LEAVES));

    public static final RegistryObject<Block> OAK_LEAF_LITTER = BLOCKS.register("oak_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> SPRUCE_LEAF_LITTER = BLOCKS.register("spruce_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> BIRCH_LEAF_LITTER = BLOCKS.register("birch_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> JUNGLE_LEAF_LITTER = BLOCKS.register("jungle_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> ACACIA_LEAF_LITTER = BLOCKS.register("acacia_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> DARK_OAK_LEAF_LITTER = BLOCKS.register("dark_oak_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> CEDAR_LEAF_LITTER = BLOCKS.register("cedar_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> COCONUT_PALM_LEAF_LITTER = BLOCKS.register("coconut_palm_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> PINYON_PINE_LEAF_LITTER = BLOCKS.register("pinyon_pine_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> JUNIPER_LEAF_LITTER = BLOCKS.register("juniper_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> BALSAM_FIR_LEAF_LITTER = BLOCKS.register("balsam_fir_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> MAGNOLIA_LEAF_LITTER = BLOCKS.register("magnolia_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> EASTERN_HEMLOCK_LEAF_LITTER = BLOCKS.register("eastern_hemlock_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> WESTERN_HEMLOCK_LEAF_LITTER = BLOCKS.register("western_hemlock_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> YELLOW_BIRCH_LEAF_LITTER = BLOCKS.register("yellow_birch_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> BLACK_BIRCH_LEAF_LITTER = BLOCKS.register("black_birch_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> RED_BIRCH_LEAF_LITTER = BLOCKS.register("red_birch_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> MAPLE_LEAF_LITTER = BLOCKS.register("maple_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> SHARINGA_LEAF_LITTER = BLOCKS.register("sharinga_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> BLACK_WALNUT_LEAF_LITTER = BLOCKS.register("black_walnut_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> CORK_OAK_LEAF_LITTER = BLOCKS.register("cork_oak_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> CINNAMON_LEAF_LITTER = BLOCKS.register("cinnamon_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> ERYTHRINA_LEAF_LITTER = BLOCKS.register("erythrina_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> WEEPING_WILLOW_LEAF_LITTER = BLOCKS.register("weeping_willow_leaf_litter", LeafLitterBlock::new);
    public static final RegistryObject<Block> HONEY_LOCUST_LEAF_LITTER = BLOCKS.register("honey_locust_leaf_litter", LeafLitterBlock::new);

    private static final Block.Properties DEF_SAPLING = Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0).sound(SoundType.GRASS);
    public static final RegistryObject<Block> CEDAR_SAPLING = BLOCKS.register("cedar_sapling", () -> new RankineSaplingBlock(new CedarTreeGrower(), DEF_SAPLING, 2));
    public static final RegistryObject<Block> COCONUT_PALM_SAPLING = BLOCKS.register("coconut_palm_sapling", () -> new RankineSaplingBlock(new CoconutPalmTreeGrower(), DEF_SAPLING, 3));
    public static final RegistryObject<Block> PINYON_PINE_SAPLING = BLOCKS.register("pinyon_pine_sapling", () -> new RankineSaplingBlock(new PinyonPineTreeGrower(), DEF_SAPLING, 3));
    public static final RegistryObject<Block> JUNIPER_SAPLING = BLOCKS.register("juniper_sapling", () -> new RankineSaplingBlock(new JuniperTreeGrower(), DEF_SAPLING, 3));
    public static final RegistryObject<Block> BALSAM_FIR_SAPLING = BLOCKS.register("balsam_fir_sapling", () -> new RankineSaplingBlock(new BalsamFirTreeGrower(), DEF_SAPLING, 2));
    public static final RegistryObject<Block> MAGNOLIA_SAPLING = BLOCKS.register("magnolia_sapling", () -> new RankineSaplingBlock(new MagnoliaTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> EASTERN_HEMLOCK_SAPLING = BLOCKS.register("eastern_hemlock_sapling", () -> new RankineSaplingBlock(new EasternHemlockTreeGrower(), DEF_SAPLING, 2));
    public static final RegistryObject<Block> WESTERN_HEMLOCK_SAPLING = BLOCKS.register("western_hemlock_sapling", () -> new RankineSaplingBlock(new WesternHemlockTreeGrower(), DEF_SAPLING, 2));
    public static final RegistryObject<Block> YELLOW_BIRCH_SAPLING = BLOCKS.register("yellow_birch_sapling", () -> new RankineSaplingBlock(new YellowBirchTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> BLACK_BIRCH_SAPLING = BLOCKS.register("black_birch_sapling", () -> new RankineSaplingBlock(new BlackBirchTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> RED_BIRCH_SAPLING = BLOCKS.register("red_birch_sapling", () -> new RankineSaplingBlock(new RedBirchTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> MAPLE_SAPLING = BLOCKS.register("maple_sapling", () -> new RankineSaplingBlock(new MapleTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> SHARINGA_SAPLING = BLOCKS.register("sharinga_sapling", () -> new RankineSaplingBlock(new SharingaTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> BLACK_WALNUT_SAPLING = BLOCKS.register("black_walnut_sapling", () -> new RankineSaplingBlock(new BlackWalnutTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> CORK_OAK_SAPLING = BLOCKS.register("cork_oak_sapling", () -> new RankineSaplingBlock(new CorkOakTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> CINNAMON_SAPLING = BLOCKS.register("cinnamon_sapling", () -> new RankineSaplingBlock(new CinnamonTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> ERYTHRINA_SAPLING = BLOCKS.register("erythrina_sapling", () -> new RankineSaplingBlock(new ErythrinaTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> WEEPING_WILLOW_SAPLING = BLOCKS.register("weeping_willow_sapling", () -> new RankineSaplingBlock(new WeepingWillowTreeGrower(), DEF_SAPLING, 1));
    public static final RegistryObject<Block> HONEY_LOCUST_SAPLING = BLOCKS.register("honey_locust_sapling", () -> new RankineSaplingBlock(new HoneyLocustTreeGrower(), DEF_SAPLING, 1));

    public static final RegistryObject<Block> POTTED_CEDAR_SAPLING = BLOCKS.register("potted_cedar_sapling", () -> new FlowerPotBlock(null, CEDAR_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_PINYON_PINE_SAPLING = BLOCKS.register("potted_pinyon_pine_sapling", () -> new FlowerPotBlock(null, PINYON_PINE_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_JUNIPER_SAPLING = BLOCKS.register("potted_juniper_sapling", () -> new FlowerPotBlock(null, JUNIPER_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_COCONUT_PALM_SAPLING = BLOCKS.register("potted_coconut_palm_sapling", () -> new FlowerPotBlock(null, COCONUT_PALM_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_BALSAM_FIR_SAPLING = BLOCKS.register("potted_balsam_fir_sapling", () -> new FlowerPotBlock(null, BALSAM_FIR_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_MAGNOLIA_SAPLING = BLOCKS.register("potted_magnolia_sapling", () -> new FlowerPotBlock(null, MAGNOLIA_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_EASTERN_HEMLOCK_SAPLING = BLOCKS.register("potted_eastern_hemlock_sapling", () -> new FlowerPotBlock(null, EASTERN_HEMLOCK_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_WESTERN_HEMLOCK_SAPLING = BLOCKS.register("potted_western_hemlock_sapling", () -> new FlowerPotBlock(null, WESTERN_HEMLOCK_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_YELLOW_BIRCH_SAPLING = BLOCKS.register("potted_yellow_birch_sapling", () -> new FlowerPotBlock(null, YELLOW_BIRCH_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_BLACK_BIRCH_SAPLING = BLOCKS.register("potted_black_birch_sapling", () -> new FlowerPotBlock(null, BLACK_BIRCH_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_RED_BIRCH_SAPLING = BLOCKS.register("potted_red_birch_sapling", () -> new FlowerPotBlock(null, RED_BIRCH_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling", () -> new FlowerPotBlock(null, MAPLE_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_SHARINGA_SAPLING = BLOCKS.register("potted_sharinga_sapling", () -> new FlowerPotBlock(null, SHARINGA_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_BLACK_WALNUT_SAPLING = BLOCKS.register("potted_black_walnut_sapling", () -> new FlowerPotBlock(null, BLACK_WALNUT_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_CORK_OAK_SAPLING = BLOCKS.register("potted_cork_oak_sapling", () -> new FlowerPotBlock(null, CORK_OAK_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_CINNAMON_SAPLING = BLOCKS.register("potted_cinnamon_sapling", () -> new FlowerPotBlock(null, CINNAMON_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_ERYTHRINA_SAPLING = BLOCKS.register("potted_erythrina_sapling", () -> new FlowerPotBlock(null, CINNAMON_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_WEEPING_WILLOW_SAPLING = BLOCKS.register("potted_weeping_willow_sapling", () -> new FlowerPotBlock(null, WEEPING_WILLOW_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));
    public static final RegistryObject<Block> POTTED_HONEY_LOCUST_SAPLING = BLOCKS.register("potted_honey_locust_sapling", () -> new FlowerPotBlock(null, HONEY_LOCUST_SAPLING, Block.Properties.of(Material.DECORATION).strength(0.0f).noOcclusion()));

    public static final RegistryObject<Block> WOOD_TIER_CRUSHING_HEAD = BLOCKS.register("wood_tier_crushing_head", CrushingHeadBlock::new);
    public static final RegistryObject<Block> STONE_TIER_CRUSHING_HEAD = BLOCKS.register("stone_tier_crushing_head", CrushingHeadBlock::new);
    public static final RegistryObject<Block> IRON_TIER_CRUSHING_HEAD = BLOCKS.register("iron_tier_crushing_head", CrushingHeadBlock::new);
    public static final RegistryObject<Block> DIAMOND_TIER_CRUSHING_HEAD = BLOCKS.register("diamond_tier_crushing_head", CrushingHeadBlock::new);
    public static final RegistryObject<Block> NETHERITE_TIER_CRUSHING_HEAD = BLOCKS.register("netherite_tier_crushing_head", CrushingHeadBlock::new);



    //ALLOY BLOCKS
    public static final RegistryObject<Block> ROSE_GOLD_BLOCK = BLOCKS.register("rose_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> WHITE_GOLD_BLOCK = BLOCKS.register("white_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> GREEN_GOLD_BLOCK = BLOCKS.register("green_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BLUE_GOLD_BLOCK = BLOCKS.register("blue_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PURPLE_GOLD_BLOCK = BLOCKS.register("purple_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BLACK_GOLD_BLOCK = BLOCKS.register("black_gold_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PEWTER_BLOCK = BLOCKS.register("pewter_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BRASS_BLOCK = BLOCKS.register("brass_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CAST_IRON_BLOCK = BLOCKS.register("cast_iron_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> INVAR_BLOCK = BLOCKS.register("invar_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CUPRONICKEL_BLOCK = BLOCKS.register("cupronickel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> DURALUMIN_BLOCK = BLOCKS.register("duralumin_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MAGNESIUM_ALLOY_BLOCK = BLOCKS.register("magnesium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> STERLING_SILVER_BLOCK = BLOCKS.register("sterling_silver_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NICKEL_SILVER_BLOCK = BLOCKS.register("nickel_silver_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TITANIUM_ALLOY_BLOCK = BLOCKS.register("titanium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NITINOL_BLOCK = BLOCKS.register("nitinol_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ALNICO_BLOCK = BLOCKS.register("alnico_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> STAINLESS_STEEL_BLOCK = BLOCKS.register("stainless_steel_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NICKEL_SUPERALLOY_BLOCK = BLOCKS.register("nickel_superalloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TUNGSTEN_HEAVY_ALLOY_BLOCK = BLOCKS.register("tungsten_heavy_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> COBALT_SUPERALLOY_BLOCK = BLOCKS.register("cobalt_superalloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NIOBIUM_ALLOY_BLOCK = BLOCKS.register("niobium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_BLOCK = BLOCKS.register("zirconium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ROSE_METAL_BLOCK = BLOCKS.register("rose_metal_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MISCHMETAL_BLOCK = BLOCKS.register("mischmetal_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> FERROCERIUM_BLOCK = BLOCKS.register("ferrocerium_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> GALINSTAN_BLOCK = BLOCKS.register("galinstan_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> OSMIRIDIUM_BLOCK = BLOCKS.register("osmiridium_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SODIUM_POTASSIUM_ALLOY_BLOCK = BLOCKS.register("sodium_potassium_alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> AMALGAM_BLOCK = BLOCKS.register("amalgam_block", () -> new AlloyBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ENDER_AMALGAM_BLOCK = BLOCKS.register("ender_amalgam_block", () -> new AlloyBlock(DEF_METAL_BLOCK));

    public static final RegistryObject<Block> ALLOY_BLOCK = BLOCKS.register("alloy_block", () -> new AlloyBlock(DEF_METAL_BLOCK));

    
    public static final RegistryObject<Block> ROSE_GOLD_PEDESTAL = BLOCKS.register("rose_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> WHITE_GOLD_PEDESTAL = BLOCKS.register("white_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> GREEN_GOLD_PEDESTAL = BLOCKS.register("green_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> BLUE_GOLD_PEDESTAL = BLOCKS.register("blue_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> PURPLE_GOLD_PEDESTAL = BLOCKS.register("purple_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> BLACK_GOLD_PEDESTAL = BLOCKS.register("black_gold_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> PEWTER_PEDESTAL = BLOCKS.register("pewter_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> BRONZE_PEDESTAL = BLOCKS.register("bronze_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> BRASS_PEDESTAL = BLOCKS.register("brass_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> CAST_IRON_PEDESTAL = BLOCKS.register("cast_iron_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> INVAR_PEDESTAL = BLOCKS.register("invar_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> CUPRONICKEL_PEDESTAL = BLOCKS.register("cupronickel_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> DURALUMIN_PEDESTAL = BLOCKS.register("duralumin_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> MAGNESIUM_ALLOY_PEDESTAL = BLOCKS.register("magnesium_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> STERLING_SILVER_PEDESTAL = BLOCKS.register("sterling_silver_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> NICKEL_SILVER_PEDESTAL = BLOCKS.register("nickel_silver_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> TITANIUM_ALLOY_PEDESTAL = BLOCKS.register("titanium_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> NITINOL_PEDESTAL = BLOCKS.register("nitinol_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> ALNICO_PEDESTAL = BLOCKS.register("alnico_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> STEEL_PEDESTAL = BLOCKS.register("steel_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> STAINLESS_STEEL_PEDESTAL = BLOCKS.register("stainless_steel_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> NICKEL_SUPERALLOY_PEDESTAL = BLOCKS.register("nickel_superalloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> TUNGSTEN_HEAVY_ALLOY_PEDESTAL = BLOCKS.register("tungsten_heavy_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> COBALT_SUPERALLOY_PEDESTAL = BLOCKS.register("cobalt_superalloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> NIOBIUM_ALLOY_PEDESTAL = BLOCKS.register("niobium_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_PEDESTAL = BLOCKS.register("zirconium_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> ROSE_METAL_PEDESTAL = BLOCKS.register("rose_metal_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> MISCHMETAL_PEDESTAL = BLOCKS.register("mischmetal_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> FERROCERIUM_PEDESTAL = BLOCKS.register("ferrocerium_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> GALINSTAN_PEDESTAL = BLOCKS.register("galinstan_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> OSMIRIDIUM_PEDESTAL = BLOCKS.register("osmiridium_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> SODIUM_POTASSIUM_ALLOY_PEDESTAL = BLOCKS.register("sodium_potassium_alloy_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> AMALGAM_PEDESTAL = BLOCKS.register("amalgam_pedestal", PedestalBlock::new);
    public static final RegistryObject<Block> ENDER_AMALGAM_PEDESTAL = BLOCKS.register("ender_amalgam_pedestal", PedestalBlock::new);


    public static final RegistryObject<Block> ROSE_GOLD_SHEETMETAL = BLOCKS.register("rose_gold_sheetmetal", () -> new SheetmetalBlock(16756647));
    public static final RegistryObject<Block> WHITE_GOLD_SHEETMETAL = BLOCKS.register("white_gold_sheetmetal", () -> new SheetmetalBlock(16777207));
    public static final RegistryObject<Block> GREEN_GOLD_SHEETMETAL = BLOCKS.register("green_gold_sheetmetal", () -> new SheetmetalBlock(15728547));
    public static final RegistryObject<Block> BLUE_GOLD_SHEETMETAL = BLOCKS.register("blue_gold_sheetmetal", () -> new SheetmetalBlock(8695295));
    public static final RegistryObject<Block> PURPLE_GOLD_SHEETMETAL = BLOCKS.register("purple_gold_sheetmetal", () -> new SheetmetalBlock(11836415));
    public static final RegistryObject<Block> BLACK_GOLD_SHEETMETAL = BLOCKS.register("black_gold_sheetmetal", () -> new SheetmetalBlock(3684408));
    public static final RegistryObject<Block> PEWTER_SHEETMETAL = BLOCKS.register("pewter_sheetmetal", () -> new SheetmetalBlock(11711154));
    public static final RegistryObject<Block> BRONZE_SHEETMETAL = BLOCKS.register("bronze_sheetmetal", () -> new SheetmetalBlock(15510384));
    public static final RegistryObject<Block> BRASS_SHEETMETAL = BLOCKS.register("brass_sheetmetal", () -> new SheetmetalBlock(16762368));
    public static final RegistryObject<Block> CAST_IRON_SHEETMETAL = BLOCKS.register("cast_iron_sheetmetal", () -> new SheetmetalBlock(2236962));
    public static final RegistryObject<Block> INVAR_SHEETMETAL = BLOCKS.register("invar_sheetmetal", () -> new SheetmetalBlock(13028546));
    public static final RegistryObject<Block> CUPRONICKEL_SHEETMETAL = BLOCKS.register("cupronickel_sheetmetal", () -> new SheetmetalBlock(11946807));
    public static final RegistryObject<Block> DURALUMIN_SHEETMETAL = BLOCKS.register("duralumin_sheetmetal", () -> new SheetmetalBlock(8887223));
    public static final RegistryObject<Block> MAGNESIUM_ALLOY_SHEETMETAL = BLOCKS.register("magnesium_alloy_sheetmetal", () -> new SheetmetalBlock(6841956));
    public static final RegistryObject<Block> STERLING_SILVER_SHEETMETAL = BLOCKS.register("sterling_silver_sheetmetal", () -> new SheetmetalBlock(15723237));
    public static final RegistryObject<Block> NICKEL_SILVER_SHEETMETAL = BLOCKS.register("nickel_silver_sheetmetal", () -> new SheetmetalBlock(9608841));
    public static final RegistryObject<Block> TITANIUM_ALLOY_SHEETMETAL = BLOCKS.register("titanium_alloy_sheetmetal", () -> new SheetmetalBlock(13750746));
    public static final RegistryObject<Block> NITINOL_SHEETMETAL = BLOCKS.register("nitinol_sheetmetal", () -> new SheetmetalBlock(12882104));
    public static final RegistryObject<Block> ALNICO_SHEETMETAL = BLOCKS.register("alnico_sheetmetal", () -> new SheetmetalBlock(16730178));
    public static final RegistryObject<Block> STEEL_SHEETMETAL = BLOCKS.register("steel_sheetmetal", () -> new SheetmetalBlock(7634311));
    public static final RegistryObject<Block> STAINLESS_STEEL_SHEETMETAL = BLOCKS.register("stainless_steel_sheetmetal", () -> new SheetmetalBlock(13292499));
    public static final RegistryObject<Block> NICKEL_SUPERALLOY_SHEETMETAL = BLOCKS.register("nickel_superalloy_sheetmetal", () -> new SheetmetalBlock(8559522));
    public static final RegistryObject<Block> TUNGSTEN_HEAVY_ALLOY_SHEETMETAL = BLOCKS.register("tungsten_heavy_alloy_sheetmetal", () -> new SheetmetalBlock(11113071));
    public static final RegistryObject<Block> COBALT_SUPERALLOY_SHEETMETAL = BLOCKS.register("cobalt_superalloy_sheetmetal", () -> new SheetmetalBlock(6534878));
    public static final RegistryObject<Block> NIOBIUM_ALLOY_SHEETMETAL = BLOCKS.register("niobium_alloy_sheetmetal", () -> new SheetmetalBlock(4533618));
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_SHEETMETAL = BLOCKS.register("zirconium_alloy_sheetmetal", () -> new SheetmetalBlock(14271685));
    public static final RegistryObject<Block> ROSE_METAL_SHEETMETAL = BLOCKS.register("rose_metal_sheetmetal", () -> new SheetmetalBlock(6235970));
    public static final RegistryObject<Block> MISCHMETAL_SHEETMETAL = BLOCKS.register("mischmetal_sheetmetal", () -> new SheetmetalBlock(7304289));
    public static final RegistryObject<Block> FERROCERIUM_SHEETMETAL = BLOCKS.register("ferrocerium_sheetmetal", () -> new SheetmetalBlock(7433071));
    public static final RegistryObject<Block> GALINSTAN_SHEETMETAL = BLOCKS.register("galinstan_sheetmetal", () -> new SheetmetalBlock(12564673));
    public static final RegistryObject<Block> OSMIRIDIUM_SHEETMETAL = BLOCKS.register("osmiridium_sheetmetal", () -> new SheetmetalBlock(13212593));
    public static final RegistryObject<Block> SODIUM_POTASSIUM_ALLOY_SHEETMETAL = BLOCKS.register("sodium_potassium_alloy_sheetmetal", () -> new SheetmetalBlock(9403777));
    public static final RegistryObject<Block> AMALGAM_SHEETMETAL = BLOCKS.register("amalgam_sheetmetal", () -> new SheetmetalBlock(13881539));
    public static final RegistryObject<Block> ENDER_AMALGAM_SHEETMETAL = BLOCKS.register("ender_amalgam_sheetmetal", () -> new SheetmetalBlock(492385));

    public static final RegistryObject<Block> ROSE_GOLD_POLE = BLOCKS.register("rose_gold_pole", () -> new MetalPoleBlock(16756647));
    public static final RegistryObject<Block> WHITE_GOLD_POLE = BLOCKS.register("white_gold_pole", () -> new MetalPoleBlock(16777207));
    public static final RegistryObject<Block> GREEN_GOLD_POLE = BLOCKS.register("green_gold_pole", () -> new MetalPoleBlock(15728547));
    public static final RegistryObject<Block> BLUE_GOLD_POLE = BLOCKS.register("blue_gold_pole", () -> new MetalPoleBlock(8695295));
    public static final RegistryObject<Block> PURPLE_GOLD_POLE = BLOCKS.register("purple_gold_pole", () -> new MetalPoleBlock(11836415));
    public static final RegistryObject<Block> BLACK_GOLD_POLE = BLOCKS.register("black_gold_pole", () -> new MetalPoleBlock(3684408));
    public static final RegistryObject<Block> PEWTER_POLE = BLOCKS.register("pewter_pole", () -> new MetalPoleBlock(11711154));
    public static final RegistryObject<Block> BRONZE_POLE = BLOCKS.register("bronze_pole", () -> new MetalPoleBlock(15510384));
    public static final RegistryObject<Block> BRASS_POLE = BLOCKS.register("brass_pole", () -> new MetalPoleBlock(16762368));
    public static final RegistryObject<Block> CAST_IRON_POLE = BLOCKS.register("cast_iron_pole", () -> new MetalPoleBlock(2236962));
    public static final RegistryObject<Block> INVAR_POLE = BLOCKS.register("invar_pole", () -> new MetalPoleBlock(13028546));
    public static final RegistryObject<Block> CUPRONICKEL_POLE = BLOCKS.register("cupronickel_pole", () -> new MetalPoleBlock(11946807));
    public static final RegistryObject<Block> DURALUMIN_POLE = BLOCKS.register("duralumin_pole", () -> new MetalPoleBlock(8887223));
    public static final RegistryObject<Block> MAGNESIUM_ALLOY_POLE = BLOCKS.register("magnesium_alloy_pole", () -> new MetalPoleBlock(6841956));
    public static final RegistryObject<Block> STERLING_SILVER_POLE = BLOCKS.register("sterling_silver_pole", () -> new MetalPoleBlock(15723237));
    public static final RegistryObject<Block> NICKEL_SILVER_POLE = BLOCKS.register("nickel_silver_pole", () -> new MetalPoleBlock(9608841));
    public static final RegistryObject<Block> TITANIUM_ALLOY_POLE = BLOCKS.register("titanium_alloy_pole", () -> new MetalPoleBlock(13750746));
    public static final RegistryObject<Block> NITINOL_POLE = BLOCKS.register("nitinol_pole", () -> new MetalPoleBlock(12882104));
    public static final RegistryObject<Block> ALNICO_POLE = BLOCKS.register("alnico_pole", () -> new MetalPoleBlock(16730178));
    public static final RegistryObject<Block> STEEL_POLE = BLOCKS.register("steel_pole", () -> new MetalPoleBlock(7634311));
    public static final RegistryObject<Block> STAINLESS_STEEL_POLE = BLOCKS.register("stainless_steel_pole", () -> new MetalPoleBlock(13292499));
    public static final RegistryObject<Block> NICKEL_SUPERALLOY_POLE = BLOCKS.register("nickel_superalloy_pole", () -> new MetalPoleBlock(8559522));
    public static final RegistryObject<Block> TUNGSTEN_HEAVY_ALLOY_POLE = BLOCKS.register("tungsten_heavy_alloy_pole", () -> new MetalPoleBlock(11113071));
    public static final RegistryObject<Block> COBALT_SUPERALLOY_POLE = BLOCKS.register("cobalt_superalloy_pole", () -> new MetalPoleBlock(6534878));
    public static final RegistryObject<Block> NIOBIUM_ALLOY_POLE = BLOCKS.register("niobium_alloy_pole", () -> new MetalPoleBlock(4533618));
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_POLE = BLOCKS.register("zirconium_alloy_pole", () -> new MetalPoleBlock(14271685));
    public static final RegistryObject<Block> ROSE_METAL_POLE = BLOCKS.register("rose_metal_pole", () -> new MetalPoleBlock(6235970));
    public static final RegistryObject<Block> MISCHMETAL_POLE = BLOCKS.register("mischmetal_pole", () -> new MetalPoleBlock(7304289));
    public static final RegistryObject<Block> FERROCERIUM_POLE = BLOCKS.register("ferrocerium_pole", () -> new MetalPoleBlock(7433071));
    public static final RegistryObject<Block> GALINSTAN_POLE = BLOCKS.register("galinstan_pole", () -> new MetalPoleBlock(12564673));
    public static final RegistryObject<Block> OSMIRIDIUM_POLE = BLOCKS.register("osmiridium_pole", () -> new MetalPoleBlock(13212593));
    public static final RegistryObject<Block> SODIUM_POTASSIUM_ALLOY_POLE = BLOCKS.register("sodium_potassium_alloy_pole", () -> new MetalPoleBlock(9403777));
    public static final RegistryObject<Block> AMALGAM_POLE = BLOCKS.register("amalgam_pole", () -> new MetalPoleBlock(13881539));
    public static final RegistryObject<Block> ENDER_AMALGAM_POLE = BLOCKS.register("ender_amalgam_pole", () -> new MetalPoleBlock(492385));

    public static final RegistryObject<Block> ROSE_GOLD_BARS = BLOCKS.register("rose_gold_bars", () -> new MetalBarsBlock(16756647));
    public static final RegistryObject<Block> WHITE_GOLD_BARS = BLOCKS.register("white_gold_bars", () -> new MetalBarsBlock(16777207));
    public static final RegistryObject<Block> GREEN_GOLD_BARS = BLOCKS.register("green_gold_bars", () -> new MetalBarsBlock(15728547));
    public static final RegistryObject<Block> BLUE_GOLD_BARS = BLOCKS.register("blue_gold_bars", () -> new MetalBarsBlock(8695295));
    public static final RegistryObject<Block> PURPLE_GOLD_BARS = BLOCKS.register("purple_gold_bars", () -> new MetalBarsBlock(11836415));
    public static final RegistryObject<Block> BLACK_GOLD_BARS = BLOCKS.register("black_gold_bars", () -> new MetalBarsBlock(3684408));
    public static final RegistryObject<Block> PEWTER_BARS = BLOCKS.register("pewter_bars", () -> new MetalBarsBlock(11711154));
    public static final RegistryObject<Block> BRONZE_BARS = BLOCKS.register("bronze_bars", () -> new MetalBarsBlock(15510384));
    public static final RegistryObject<Block> BRASS_BARS = BLOCKS.register("brass_bars", () -> new MetalBarsBlock(16762368));
    public static final RegistryObject<Block> CAST_IRON_BARS = BLOCKS.register("cast_iron_bars", () -> new MetalBarsBlock(2236962));
    public static final RegistryObject<Block> INVAR_BARS = BLOCKS.register("invar_bars", () -> new MetalBarsBlock(13028546));
    public static final RegistryObject<Block> CUPRONICKEL_BARS = BLOCKS.register("cupronickel_bars", () -> new MetalBarsBlock(11946807));
    public static final RegistryObject<Block> DURALUMIN_BARS = BLOCKS.register("duralumin_bars", () -> new MetalBarsBlock(8887223));
    public static final RegistryObject<Block> MAGNESIUM_ALLOY_BARS = BLOCKS.register("magnesium_alloy_bars", () -> new MetalBarsBlock(6841956));
    public static final RegistryObject<Block> STERLING_SILVER_BARS = BLOCKS.register("sterling_silver_bars", () -> new MetalBarsBlock(15723237));
    public static final RegistryObject<Block> NICKEL_SILVER_BARS = BLOCKS.register("nickel_silver_bars", () -> new MetalBarsBlock(9608841));
    public static final RegistryObject<Block> TITANIUM_ALLOY_BARS = BLOCKS.register("titanium_alloy_bars", () -> new MetalBarsBlock(13750746));
    public static final RegistryObject<Block> NITINOL_BARS = BLOCKS.register("nitinol_bars", () -> new MetalBarsBlock(12882104));
    public static final RegistryObject<Block> ALNICO_BARS = BLOCKS.register("alnico_bars", () -> new MetalBarsBlock(16730178));
    public static final RegistryObject<Block> STEEL_BARS = BLOCKS.register("steel_bars", () -> new MetalBarsBlock(7634311));
    public static final RegistryObject<Block> STAINLESS_STEEL_BARS = BLOCKS.register("stainless_steel_bars", () -> new MetalBarsBlock(13292499));
    public static final RegistryObject<Block> NICKEL_SUPERALLOY_BARS = BLOCKS.register("nickel_superalloy_bars", () -> new MetalBarsBlock(8559522));
    public static final RegistryObject<Block> TUNGSTEN_HEAVY_ALLOY_BARS = BLOCKS.register("tungsten_heavy_alloy_bars", () -> new MetalBarsBlock(11113071));
    public static final RegistryObject<Block> COBALT_SUPERALLOY_BARS = BLOCKS.register("cobalt_superalloy_bars", () -> new MetalBarsBlock(6534878));
    public static final RegistryObject<Block> NIOBIUM_ALLOY_BARS = BLOCKS.register("niobium_alloy_bars", () -> new MetalBarsBlock(4533618));
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_BARS = BLOCKS.register("zirconium_alloy_bars", () -> new MetalBarsBlock(14271685));
    public static final RegistryObject<Block> ROSE_METAL_BARS = BLOCKS.register("rose_metal_bars", () -> new MetalBarsBlock(6235970));
    public static final RegistryObject<Block> MISCHMETAL_BARS = BLOCKS.register("mischmetal_bars", () -> new MetalBarsBlock(7304289));
    public static final RegistryObject<Block> FERROCERIUM_BARS = BLOCKS.register("ferrocerium_bars", () -> new MetalBarsBlock(7433071));
    public static final RegistryObject<Block> GALINSTAN_BARS = BLOCKS.register("galinstan_bars", () -> new MetalBarsBlock(12564673));
    public static final RegistryObject<Block> OSMIRIDIUM_BARS = BLOCKS.register("osmiridium_bars", () -> new MetalBarsBlock(13212593));
    public static final RegistryObject<Block> SODIUM_POTASSIUM_ALLOY_BARS = BLOCKS.register("sodium_potassium_alloy_bars", () -> new MetalBarsBlock(9403777));
    public static final RegistryObject<Block> AMALGAM_BARS = BLOCKS.register("amalgam_bars", () -> new MetalBarsBlock(13881539));
    public static final RegistryObject<Block> ENDER_AMALGAM_BARS = BLOCKS.register("ender_amalgam_bars", () -> new MetalBarsBlock(492385));






    
    //GEM AND MINERALS
    public static final RegistryObject<Block> LEPIDOLITE_BLOCK = BLOCKS.register("lepidolite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PYRITE_BLOCK = BLOCKS.register("pyrite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> HEDENBERGITE_BLOCK = BLOCKS.register("hedenbergite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CHALCOCITE_BLOCK = BLOCKS.register("chalcocite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> COOPERITE_BLOCK = BLOCKS.register("cooperite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SANIDINE_BLOCK = BLOCKS.register("sanidine_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SPODUMENE_BLOCK = BLOCKS.register("spodumene_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> TALC_BLOCK = BLOCKS.register("talc_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SERPENTINE_BLOCK = BLOCKS.register("serpentine_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> APATITE_BLOCK = BLOCKS.register("apatite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GYPSUM_BLOCK = BLOCKS.register("gypsum_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LAURITE_BLOCK = BLOCKS.register("laurite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LAUTARITE_BLOCK = BLOCKS.register("lautarite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> REALGAR_BLOCK = BLOCKS.register("realgar_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RUTILE_BLOCK = BLOCKS.register("rutile_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> DIOPSIDE_BLOCK = BLOCKS.register("diopside_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GOETHITE_BLOCK = BLOCKS.register("goethite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> YTTRIUM_MONAZITE_BLOCK = BLOCKS.register("yttrium_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> AZURMALACHITE_BLOCK = BLOCKS.register("azurmalachite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> MALACHITE_BLOCK = BLOCKS.register("malachite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> AZURITE_BLOCK = BLOCKS.register("azurite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LABRADORITE_BLOCK = BLOCKS.register("labradorite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> KYANITE_BLOCK = BLOCKS.register("kyanite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> FLUORITE_BLOCK = BLOCKS.register("fluorite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> AQUAMARINE_BLOCK = BLOCKS.register("aquamarine_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = BLOCKS.register("sapphire_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> TIGER_IRON_BLOCK = BLOCKS.register("tiger_iron_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> OPAL_BLOCK = BLOCKS.register("opal_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GARNET_BLOCK = BLOCKS.register("garnet_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PERIDOT_BLOCK = BLOCKS.register("peridot_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> AMBER_BLOCK = BLOCKS.register("amber_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> TOURMALINE_BLOCK = BLOCKS.register("tourmaline_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PEARL_BLOCK = BLOCKS.register("pearl_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LONSDALEITE_DIAMOND_BLOCK = BLOCKS.register("lonsdaleite_diamond_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LORANDITE_BLOCK = BLOCKS.register("lorandite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> POLLUCITE_BLOCK = BLOCKS.register("pollucite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CELESTINE_BLOCK = BLOCKS.register("celestine_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CINNABAR_BLOCK = BLOCKS.register("cinnabar_block", () -> new CinnabarBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(5.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> PERLITE_BLOCK = BLOCKS.register("perlite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> IRON_CHLORIDE_BLOCK = BLOCKS.register("iron_chloride_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RHODONITE_BLOCK = BLOCKS.register("rhodonite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RHODOCHROSITE_BLOCK = BLOCKS.register("rhodochrosite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GRAPHITE_BLOCK = BLOCKS.register("graphite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ORTHOCLASE_FELDSPAR_BLOCK = BLOCKS.register("orthoclase_feldspar_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> PLAGIOCLASE_FELDSPAR_BLOCK = BLOCKS.register("plagioclase_feldspar_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> OLIVINE_BLOCK = BLOCKS.register("olivine_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> PYROXENE_BLOCK = BLOCKS.register("pyroxene_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> BIOTITE_BLOCK = BLOCKS.register("biotite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> MUSCOVITE_BLOCK = BLOCKS.register("muscovite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> AMPHIBOLE_BLOCK = BLOCKS.register("amphibole_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> FULGURITE = BLOCKS.register("fulgurite", () -> new Block(Block.Properties.of(Material.GRASS).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> DOLOMITE_BLOCK = BLOCKS.register("dolomite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> MAGNESIUM_CHLORIDE_BLOCK = BLOCKS.register("magnesium_chloride_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> SODIUM_CHLORIDE_BLOCK = BLOCKS.register("sodium_chloride_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final RegistryObject<Block> CALCIUM_CHLORIDE_BLOCK = BLOCKS.register("calcium_chloride_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> PINK_SALT_BLOCK = BLOCKS.register("pink_salt_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F).noOcclusion()));
    public static final RegistryObject<Block> CALCIUM_SILICATE_BLOCK = BLOCKS.register("calcium_silicate_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(0.5F, 0.5F)));
    public static final RegistryObject<Block> SILICON_CARBIDE_BLOCK = BLOCKS.register("silicon_carbide_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ENSTATITE_BLOCK = BLOCKS.register("enstatite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CHROME_ENSTATITE_BLOCK = BLOCKS.register("chrome_enstatite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SCHEELITE_BLOCK = BLOCKS.register("scheelite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> NEPHELINE_BLOCK = BLOCKS.register("nepheline_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PYROCHLORE_BLOCK = BLOCKS.register("pyrochlore_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CASSITERITE_BLOCK = BLOCKS.register("cassiterite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> BAUXITE_BLOCK = BLOCKS.register("bauxite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SPHALERITE_BLOCK = BLOCKS.register("sphalerite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> MAGNETITE_BLOCK = BLOCKS.register("magnetite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> HEMATITE_BLOCK = BLOCKS.register("hematite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PENTLANDITE_BLOCK = BLOCKS.register("pentlandite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GALENA_BLOCK = BLOCKS.register("galena_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GADOLINIUM_MONAZITE_BLOCK = BLOCKS.register("gadolinium_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> VANADINITE_BLOCK = BLOCKS.register("vanadinite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> BISMUTHINITE_BLOCK = BLOCKS.register("bismuthinite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ACANTHITE_BLOCK = BLOCKS.register("acanthite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PYROLUSITE_BLOCK = BLOCKS.register("pyrolusite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CHROMITE_BLOCK = BLOCKS.register("chromite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> MOLYBDENITE_BLOCK = BLOCKS.register("molybdenite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ILMENITE_BLOCK = BLOCKS.register("ilmenite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> COLUMBITE_BLOCK = BLOCKS.register("columbite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> WOLFRAMITE_BLOCK = BLOCKS.register("wolframite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> RHENIITE_BLOCK = BLOCKS.register("rheniite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> TANTALITE_BLOCK = BLOCKS.register("tantalite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> GREENOCKITE_BLOCK = BLOCKS.register("greenockite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> URANINITE_BLOCK = BLOCKS.register("uraninite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> THORITE_BLOCK = BLOCKS.register("thorite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> STIBNITE_BLOCK = BLOCKS.register("stibnite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> XENOTIME_BLOCK = BLOCKS.register("xenotime_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PETALITE_BLOCK = BLOCKS.register("petalite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> COBALTITE_BLOCK = BLOCKS.register("cobaltite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CRYOLITE_BLOCK = BLOCKS.register("cryolite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CHALCOPYRITE_BLOCK = BLOCKS.register("chalcopyrite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> BARITE_BLOCK = BLOCKS.register("barite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> BADDELEYITE_BLOCK = BLOCKS.register("baddeleyite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> THORTVEITITE_BLOCK = BLOCKS.register("thortveitite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CERIUM_MONAZITE_BLOCK = BLOCKS.register("cerium_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> CHLORITE_BLOCK = BLOCKS.register("chlorite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> LANTHANUM_MONAZITE_BLOCK = BLOCKS.register("lanthanum_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> NEODYMIUM_MONAZITE_BLOCK = BLOCKS.register("neodymium_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> SAMARIUM_MONAZITE_BLOCK = BLOCKS.register("samarium_monazite_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ZIRCON_BLOCK = BLOCKS.register("zircon_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> HAFNIA_BLOCK = BLOCKS.register("hafnia_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> ZIRCONIA_BLOCK = BLOCKS.register("zirconia_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> PLATINUM_ARSENIDE_BLOCK = BLOCKS.register("platinum_arsenide_block", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(2.0F, 5.0F)));
    public static final RegistryObject<Block> COKE_BLOCK = BLOCKS.register("coke_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> LIGNITE_BLOCK = BLOCKS.register("lignite_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> SUBBITUMINOUS_COAL_BLOCK = BLOCKS.register("subbituminous_coal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> BITUMINOUS_COAL_BLOCK = BLOCKS.register("bituminous_coal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> ANTHRACITE_COAL_BLOCK = BLOCKS.register("anthracite_coal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> ANDESINE_BLOCK = BLOCKS.register("andesine_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> GEODE = BLOCKS.register("geode", () -> new GeodeBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    //ORES
    public static final RegistryObject<Block> CHALCOCITE_ORE = BLOCKS.register("chalcocite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> PETALITE_ORE = BLOCKS.register("petalite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> LIGNITE_ORE = BLOCKS.register("lignite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> SUBBITUMINOUS_ORE = BLOCKS.register("subbituminous_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,2)));
    public static final RegistryObject<Block> BITUMINOUS_ORE = BLOCKS.register("bituminous_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> ANTHRACITE_ORE = BLOCKS.register("anthracite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,4)));
    public static final RegistryObject<Block> MAGNESITE_ORE = BLOCKS.register("magnesite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_SULFUR_ORE = BLOCKS.register("native_sulfur_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,2)));
    public static final RegistryObject<Block> ILMENITE_ORE = BLOCKS.register("ilmenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> CHROMITE_ORE = BLOCKS.register("chromite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> PYROLUSITE_ORE = BLOCKS.register("pyrolusite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> MAGNETITE_ORE = BLOCKS.register("magnetite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> HEMATITE_ORE = BLOCKS.register("hematite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> COBALTITE_ORE = BLOCKS.register("cobaltite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> PENTLANDITE_ORE = BLOCKS.register("pentlandite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> INTERSPINIFEX_ORE = BLOCKS.register("interspinifex_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> SPHALERITE_ORE = BLOCKS.register("sphalerite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_GALLIUM_ORE = BLOCKS.register("native_gallium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> NATIVE_ARSENIC_ORE = BLOCKS.register("native_arsenic_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> NATIVE_SELENIUM_ORE = BLOCKS.register("native_selenium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> CELESTINE_ORE = BLOCKS.register("celestine_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> XENOTIME_ORE = BLOCKS.register("xenotime_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> BADDELEYITE_ORE = BLOCKS.register("baddeleyite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> COLTAN_ORE = BLOCKS.register("coltan_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> MOLYBDENITE_ORE = BLOCKS.register("molybdenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_SILVER_ORE = BLOCKS.register("native_silver_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> ACANTHITE_ORE = BLOCKS.register("acanthite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> GREENOCKITE_ORE = BLOCKS.register("greenockite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_INDIUM_ORE = BLOCKS.register("native_indium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> NATIVE_TIN_ORE = BLOCKS.register("native_tin_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> CASSITERITE_ORE = BLOCKS.register("cassiterite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> STIBNITE_ORE = BLOCKS.register("stibnite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> NATIVE_TELLURIUM_ORE = BLOCKS.register("native_tellurium_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(1,3)));
    public static final RegistryObject<Block> WOLFRAMITE_ORE = BLOCKS.register("wolframite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> RHENIITE_ORE = BLOCKS.register("rheniite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_GOLD_ORE = BLOCKS.register("native_gold_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> NATIVE_LEAD_ORE = BLOCKS.register("native_lead_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> GALENA_ORE = BLOCKS.register("galena_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> NATIVE_BISMUTH_ORE = BLOCKS.register("native_bismuth_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(0,1)));
    public static final RegistryObject<Block> BISMUTHINITE_ORE = BLOCKS.register("bismuthinite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> URANINITE_ORE = BLOCKS.register("uraninite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> PLUMBAGO_ORE = BLOCKS.register("plumbago_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,5)));
    public static final RegistryObject<Block> SPERRYLITE_ORE = BLOCKS.register("sperrylite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> CINNABAR_ORE = BLOCKS.register("cinnabar_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,4)));
    public static final RegistryObject<Block> CRYOLITE_ORE = BLOCKS.register("cryolite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> PYRITE_ORE = BLOCKS.register("pyrite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> KAMACITE_ORE = BLOCKS.register("kamacite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> ANTITAENITE_ORE = BLOCKS.register("antitaenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> TAENITE_ORE = BLOCKS.register("taenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> TETRATAENITE_ORE = BLOCKS.register("tetrataenite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> LONSDALEITE_ORE = BLOCKS.register("lonsdaleite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(5,10)));
    public static final RegistryObject<Block> MONAZITE_ORE = BLOCKS.register("monazite_ore", () -> new RankineOreBlock(DEF_ORE));
    public static final RegistryObject<Block> LAZURITE_ORE = BLOCKS.register("lazurite_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(2,5)));
    public static final RegistryObject<Block> BERYL_ORE = BLOCKS.register("beryl_ore", () -> new RankineOreBlock(DEF_ORE, UniformInt.of(3,7)));

    public static final RegistryObject<Block> COAL_ORE = BLOCKS.register("coal_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.COAL_ORE), UniformInt.of(0,2)));
    public static final RegistryObject<Block> IRON_ORE = BLOCKS.register("iron_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE)));
    public static final RegistryObject<Block> GOLD_ORE = BLOCKS.register("gold_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> REDSTONE_ORE = BLOCKS.register("redstone_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE)));
    public static final RegistryObject<Block> LAPIS_ORE = BLOCKS.register("lapis_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE), UniformInt.of(2,5)));
    public static final RegistryObject<Block> DIAMOND_ORE = BLOCKS.register("diamond_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE), UniformInt.of(3,7)));
    public static final RegistryObject<Block> EMERALD_ORE = BLOCKS.register("emerald_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE), UniformInt.of(3,7)));
    public static final RegistryObject<Block> NETHER_QUARTZ_ORE = BLOCKS.register("nether_quartz_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE), UniformInt.of(2,5)));
    public static final RegistryObject<Block> NETHER_GOLD_ORE = BLOCKS.register("nether_gold_ore", () -> new RankineOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE), UniformInt.of(0,1)));

    public static final RegistryObject<Block> METEORITE = BLOCKS.register("meteorite", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> METEORITE_BRICKS = BLOCKS.register("meteorite_bricks", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> ENSTATITE_CHONDRITE = BLOCKS.register("enstatite_chondrite", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> ENSTATITE_CHONDRITE_BRICKS = BLOCKS.register("enstatite_chondrite_bricks", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> FROZEN_METEORITE = BLOCKS.register("frozen_meteorite", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> FROZEN_METEORITE_BRICKS = BLOCKS.register("frozen_meteorite_bricks", () -> new Block(DEF_STONE));
    public static final RegistryObject<Block> GREEN_TEKTITE = BLOCKS.register("green_tektite", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GRAY_TEKTITE = BLOCKS.register("gray_tektite", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLACK_TEKTITE = BLOCKS.register("black_tektite", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BROWN_TEKTITE = BLOCKS.register("brown_tektite", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(5.0F, 30.0F).sound(SoundType.GLASS).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ANTIMATTER = BLOCKS.register("antimatter", () -> new AntimatterBlock(Block.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noOcclusion().noCollission().noDrops()));
    public static final RegistryObject<Block> UNAMED_EXPLOSIVE = BLOCKS.register("unamed_explosive", () -> new UnamedExplosiveBlock(Block.Properties.of(Material.BARRIER).strength(20.0F, 50.0F)));


    public static final RegistryObject<Block> CORK = BLOCKS.register("cork", () -> new Block(Block.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(1.0F, 2.0F)));
    public static final RegistryObject<Block> VULCANIZED_RUBBER_BLOCK = BLOCKS.register("vulcanized_rubber_block", () -> new RubberBlock(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_BLACK).friction(0.8F).strength(1.0F, 2.0F).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> FLOOD_GATE = BLOCKS.register("flood_gate", () -> new FloodGateBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 10.0F).noOcclusion()));


    public static final RegistryObject<Block> ALUMINUM_SHEETMETAL = BLOCKS.register("aluminum_sheetmetal", () -> new SheetmetalBlock(14734290));
    public static final RegistryObject<Block> TITANIUM_SHEETMETAL = BLOCKS.register("titanium_sheetmetal", () -> new SheetmetalBlock(11711163));
    public static final RegistryObject<Block> IRON_SHEETMETAL = BLOCKS.register("iron_sheetmetal", () -> new SheetmetalBlock(14211288));
    public static final RegistryObject<Block> NICKEL_SHEETMETAL = BLOCKS.register("nickel_sheetmetal", () -> new SheetmetalBlock(13558993));
    public static final RegistryObject<Block> COPPER_SHEETMETAL = BLOCKS.register("copper_sheetmetal", () -> new SheetmetalBlock(15170646));
    public static final RegistryObject<Block> TIN_SHEETMETAL = BLOCKS.register("tin_sheetmetal", () -> new SheetmetalBlock(10595770));
    public static final RegistryObject<Block> TUNGSTEN_SHEETMETAL = BLOCKS.register("tungsten_sheetmetal", () -> new SheetmetalBlock(9272689));
    public static final RegistryObject<Block> LEAD_SHEETMETAL = BLOCKS.register("lead_sheetmetal", () -> new SheetmetalBlock(9341867));
    public static final RegistryObject<Block> BISMUTH_SHEETMETAL = BLOCKS.register("bismuth_sheetmetal", () -> new SheetmetalBlock(14401722));
    public static final RegistryObject<Block> CAST_IRON_SUPPORT = BLOCKS.register("cast_iron_support", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final RegistryObject<Block> CAST_IRON_SUPPORT_SLAB = BLOCKS.register("cast_iron_support_slab", () -> new RankineSlabBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final RegistryObject<Block> CAST_IRON_SUPPORT_STAIRS = BLOCKS.register("cast_iron_support_stairs", () -> new RankineStairsBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));
    public static final RegistryObject<Block> CAST_IRON_SUPPORT_WALL = BLOCKS.register("cast_iron_support_wall", () -> new RankineWallBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));


    public static final RegistryObject<Block> BLASTING_POWDER = BLOCKS.register("blasting_powder", () -> new BlastingPowderBlock(Block.Properties.of(Material.SAND).sound(SoundType.SAND).strength(1)));
    public static final RegistryObject<Block> GWIHABAITE_CRYSTAL = BLOCKS.register("gwihabaite_crystal", () -> new GwihabaiteBlock(DEF_ORE));

    public static final RegistryObject<Block> SAP_CAULDRON = BLOCKS.register("sap_cauldron", SapCauldronBlock::new);
    public static final RegistryObject<Block> MAPLE_SAP_CAULDRON = BLOCKS.register("maple_sap_cauldron", MapleSapCauldronBlock::new);
    public static final RegistryObject<Block> MAPLE_SYRUP_CAULDRON = BLOCKS.register("maple_syrup_cauldron", MapleSyrupCauldronBlock::new);
    public static final RegistryObject<Block> LATEX_CAULDRON = BLOCKS.register("latex_cauldron", LatexCauldronBlock::new);
    public static final RegistryObject<Block> RESIN_CAULDRON = BLOCKS.register("resin_cauldron", ResinCauldronBlock::new);
    public static final RegistryObject<Block> JUGLONE_CAULDRON = BLOCKS.register("juglone_cauldron", JugloneCauldronBlock::new);

    public static final RegistryObject<Block> BEEHIVE_OVEN_PIT = BLOCKS.register("beehive_oven_pit", () -> new BeehiveOvenPitBlock(REFRACTORY_BRICKS.get(), Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> MIXING_BARREL = BLOCKS.register("mixing_barrel", () -> new MixingBarrelBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).noOcclusion()));
    public static final RegistryObject<Block> CRUCIBLE_BLOCK = BLOCKS.register("crucible", () -> new CrucibleBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litCrucibleEmission(13))));
    public static final RegistryObject<Block> MATERIAL_TESTING_TABLE = BLOCKS.register("material_testing_table", () -> new MaterialTestingTableBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F)));
    public static final RegistryObject<Block> TEMPLATE_TABLE = BLOCKS.register("template_table", () -> new TemplateTableBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F)));
    public static final RegistryObject<Block> ALLOY_FURNACE = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> PISTON_CRUSHER = BLOCKS.register("piston_crusher", () -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> PCF = BLOCKS.register("pcf", () -> new Block(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> INDUCTION_FURNACE = BLOCKS.register("induction_furnace", () -> new InductionFurnaceBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> GYRATORY_CRUSHER = BLOCKS.register("gyratory_crusher", () -> new Block(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> EVAPORATION_TOWER = BLOCKS.register("evaporation_tower", () -> new EvaporationTowerBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> DIAMOND_ANVIL_CELL = BLOCKS.register("diamond_anvil_cell", () -> new DiamondAnvilCellBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> PARTICLE_ACCELERATOR = BLOCKS.register("particle_accelerator", () -> new ParticleAcceleratorBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> FUSION_FURNACE = BLOCKS.register("fusion_furnace", () -> new FusionFurnaceBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> DISTILLATION_TOWER = BLOCKS.register("distillation_tower", () -> new DistillationTowerBlock(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> AIR_DISTILLATION_PACKING = BLOCKS.register("air_distillation_packing", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> REACTION_CHAMBER_CORE = BLOCKS.register("reaction_chamber_core", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> REACTION_CHAMBER_CELL = BLOCKS.register("reaction_chamber_cell", () -> new GlassBlock(Block.Properties.of(Material.GLASS).strength(6.0F, 30.0F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> GAS_BOTTLER = BLOCKS.register("gas_bottler", () -> new GasBottlerBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> GAS_VENT = BLOCKS.register("gas_vent", () -> new GasVentBlock(Block.Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> CHARCOAL_PIT = BLOCKS.register("charcoal_pit", () -> new CharcoalPitBlock(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F)));
    public static final RegistryObject<Block> TREE_TAP = BLOCKS.register("tree_tap", () -> new TreeTapBlock(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F).noOcclusion()));
    public static final RegistryObject<Block> TAP_LINE = BLOCKS.register("tap_line", () -> new TapLineBlock(0.125f, BlockBehaviour.Properties.of(Material.WOOL).sound(SoundType.WOOL).strength(0.5F).noOcclusion()));
    public static final RegistryObject<Block> HEATING_ELEMENT_1 = BLOCKS.register("heating_element_1", () -> new Block(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(4)));


    public static final RegistryObject<Block> RED_LED = BLOCKS.register("red_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> ORANGE_LED = BLOCKS.register("orange_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> YELLOW_LED = BLOCKS.register("yellow_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> LIME_LED = BLOCKS.register("lime_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> GREEN_LED = BLOCKS.register("green_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> CYAN_LED = BLOCKS.register("cyan_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> LIGHT_BLUE_LED = BLOCKS.register("light_blue_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> BLUE_LED = BLOCKS.register("blue_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> MAGENTA_LED = BLOCKS.register("magenta_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> PURPLE_LED = BLOCKS.register("purple_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> PINK_LED = BLOCKS.register("pink_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> BROWN_LED = BLOCKS.register("brown_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> BLACK_LED = BLOCKS.register("black_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> GRAY_LED = BLOCKS.register("gray_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> LIGHT_GRAY_LED = BLOCKS.register("light_gray_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));
    public static final RegistryObject<Block> WHITE_LED = BLOCKS.register("white_led", () -> new LEDBlock((BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).lightLevel(litBlockEmission(15)).strength(1.0F, 6.0F).sound(SoundType.METAL))));


    public static final RegistryObject<Block> UNAGED_CHEESE = BLOCKS.register("unaged_cheese", () -> new UnagedCheeseBlock(BlockBehaviour.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> AGED_CHEESE = BLOCKS.register("aged_cheese", () -> new CakeBlock(BlockBehaviour.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ELDERBERRY_BUSH = BLOCKS.register("elderberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 0, PlantType.PLAINS));
    public static final RegistryObject<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 1, PlantType.PLAINS));
    public static final RegistryObject<Block> CRANBERRY_BUSH = BLOCKS.register("cranberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 2, PlantType.PLAINS));
    public static final RegistryObject<Block> POKEBERRY_BUSH = BLOCKS.register("pokeberry_bush", () -> new RankineDoublePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 3, PlantType.PLAINS));
    public static final RegistryObject<Block> SNOWBERRY_BUSH = BLOCKS.register("snowberry_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 1, PlantType.PLAINS));
    public static final RegistryObject<Block> RASPBERRY_BUSH = BLOCKS.register("raspberry_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 3, PlantType.PLAINS));
    public static final RegistryObject<Block> BLACKBERRY_BUSH = BLOCKS.register("blackberry_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 4, PlantType.PLAINS));
    public static final RegistryObject<Block> STRAWBERRY_BUSH = BLOCKS.register("strawberry_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 6, PlantType.PLAINS));
    public static final RegistryObject<Block> PINEAPPLE_BUSH = BLOCKS.register("pineapple_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 7, PlantType.DESERT));
    public static final RegistryObject<Block> BANANA_YUCCA_BUSH = BLOCKS.register("banana_yucca_bush", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 8, PlantType.DESERT));
    public static final RegistryObject<Block> ALOE_PLANT = BLOCKS.register("aloe_plant", () -> new RankinePlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH), 10, PlantType.DESERT));;
    public static final RegistryObject<Block> ASPARAGUS_PLANT = BLOCKS.register("asparagus_plant", () -> new AsparagusPlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.HARD_CROP)));
    public static final RegistryObject<Block> CORN_PLANT = BLOCKS.register("corn_plant", () -> new CornPlantBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.HARD_CROP)));
    public static final RegistryObject<Block> CORN_STALK = BLOCKS.register("corn_stalk", () -> new CornStalkBlock(Block.Properties.of(Material.PLANT).noCollission().sound(SoundType.HARD_CROP)));
    //public static final RegistryObject<Block> ASPARAGUS_ROOT = REGISTRY.register("asparagus_root",  () -> new AsparagusRootBlock(Block.Properties.create(Material.EARTH).tickRandomly().sound(SoundType.GROUND).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> RICE_PLANT = BLOCKS.register("rice_plant", () -> new RicePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> COTTON_PLANT = BLOCKS.register("cotton_plant", () -> new CottonPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> JUTE_PLANT = BLOCKS.register("jute_plant", () -> new JutePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> CAMPHOR_BASIL_PLANT = BLOCKS.register("camphor_basil_plant", () -> new CamphorBasilPlantBlock(Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> OAT_PLANT = BLOCKS.register("oat_plant", () -> new OatPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> RYE_PLANT = BLOCKS.register("rye_plant", () -> new RyePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> BARLEY_PLANT = BLOCKS.register("barley_plant", () -> new BarleyPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SORGHUM_PLANT = BLOCKS.register("sorghum_plant", () -> new SorghumPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> MILLET_PLANT = BLOCKS.register("millet_plant", () -> new MilletPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Block> SOYBEAN_PLANT = BLOCKS.register("soybean_plant", () -> new SoybeanPlantBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));


    public static final RegistryObject<Block> BARLEY_BALE = BLOCKS.register("barley_bale", () -> new HayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> OAT_BALE = BLOCKS.register("oat_bale", () -> new HayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> RYE_BALE = BLOCKS.register("rye_bale", () -> new HayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SORGHUM_BALE = BLOCKS.register("sorghum_bale", () -> new HayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> MILLET_BALE = BLOCKS.register("millet_bale", () -> new HayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> DURALUMIN_LADDER = BLOCKS.register("duralumin_ladder", () -> new MetalLadderBlock(true, false, Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()));
    public static final RegistryObject<Block> CAST_IRON_LADDER = BLOCKS.register("cast_iron_ladder", () -> new MetalLadderBlock(false, true, Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()));
    public static final RegistryObject<Block> INVAR_LADDER = BLOCKS.register("invar_ladder", () -> new MetalLadderBlock(false, true, Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()));
    public static final RegistryObject<Block> BRASS_LADDER = BLOCKS.register("brass_ladder", () -> new MetalLadderBlock(false, false, Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()));
    public static final RegistryObject<Block> CUPRONICKEL_LADDER = BLOCKS.register("cupronickel_ladder", () -> new MetalLadderBlock(false, true, Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()));

    public static final RegistryObject<Block> ORNAMENT = BLOCKS.register("ornament", () -> new OrnamentBlock(Block.Properties.of(Material.METAL).sound(SoundType.GLASS).strength(1.0F).noOcclusion()));




    public static final RegistryObject<Block> ROPE = BLOCKS.register("rope", () -> new RopeBlock(Block.Properties.of(Material.CLOTH_DECORATION).noCollission()));
    public static final RegistryObject<Block> GROUND_TAP = BLOCKS.register("ground_tap", () -> new GroundTapBlock(Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> METAL_PIPE = BLOCKS.register("metal_pipe", () -> new MetalPipeBlock(0.25f, Block.Properties.of(Material.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.0F)));
    public static final RegistryObject<Block> BOTANIST_STATION = BLOCKS.register("botanist_station", () -> new Block(Block.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.0F)));

    public static final RegistryObject<Block> SEDIMENT_FAN = BLOCKS.register("sediment_fan", () -> new SedimentFanBlock(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    //public static final RegistryObject<Block> FLUID_DRAIN = REGISTRY.register("fluid_drain", () -> new FluidDrainBlock(Block.Properties.create(Material.IRON, MaterialColor.AIR).sound(SoundType.METAL).setRequiresTool().hardnessAndResistance(5.0F,6.0F)));

    public static final RegistryObject<Block> SIMPLE_ELECTROMAGNET = BLOCKS.register("simple_electromagnet", () -> new ElectromagnetBlock(1));
    public static final RegistryObject<Block> ALNICO_ELECTROMAGNET = BLOCKS.register("alnico_electromagnet", () -> new ElectromagnetBlock(2));
    public static final RegistryObject<Block> RARE_EARTH_ELECTROMAGNET = BLOCKS.register("rare_earth_electromagnet", () -> new ElectromagnetBlock(3));

    public static final RegistryObject<Block> LIQUID_MERCURY = BLOCKS.register("liquid_mercury", () -> new MercuryFlowingFluidBlock(() -> RankineFluids.LIQUID_MERCURY, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> SAP = BLOCKS.register("sap", () -> new RankineFlowingFluidBlock(() -> RankineFluids.SAP, true, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> MAPLE_SAP = BLOCKS.register("maple_sap", () -> new RankineFlowingFluidBlock(() -> RankineFluids.MAPLE_SAP, true, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> LATEX = BLOCKS.register("latex", () -> new RankineFlowingFluidBlock(() -> RankineFluids.LATEX, true, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> RESIN = BLOCKS.register("resin", () -> new RankineFlowingFluidBlock(() -> RankineFluids.RESIN, true, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> JUGLONE = BLOCKS.register("juglone", () -> new RankineFlowingFluidBlock(() -> RankineFluids.JUGLONE, true, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> AQUA_REGIA = BLOCKS.register("aqua_regia", () -> new RankineFlowingFluidBlock(()-> RankineFluids.AQUA_REGIA, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> CARBON_DISULFIDE = BLOCKS.register("carbon_disulfide", () -> new CarbonDisulfideFlowingFluidBlock(()-> RankineFluids.CARBON_DISULFIDE, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> HEXAFLUOROSILICIC_ACID = BLOCKS.register("hexafluorosilicic_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.HEXAFLUOROSILICIC_ACID, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> HYDROBROMIC_ACID = BLOCKS.register("hydrobromic_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.HYDROBROMIC_ACID, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> GRAY_MUD = BLOCKS.register("gray_mud", () -> new RankineFlowingFluidBlock(()-> RankineFluids.GRAY_MUD, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> RED_MUD = BLOCKS.register("red_mud", () -> new RankineFlowingFluidBlock(()-> RankineFluids.RED_MUD, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> SULFURIC_ACID = BLOCKS.register("sulfuric_acid", () -> new RankineFlowingFluidBlock(()-> RankineFluids.SULFURIC_ACID, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> BLACK_LIQUOR = BLOCKS.register("black_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.BLACK_LIQUOR, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> GREEN_LIQUOR = BLOCKS.register("green_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.GREEN_LIQUOR, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> WHITE_LIQUOR = BLOCKS.register("white_liquor", () -> new RankineFlowingFluidBlock(()-> RankineFluids.WHITE_LIQUOR, false, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));

    //ELEMENT BLOCKS
    public static final RegistryObject<Block> HYDROGEN_BLOCK = BLOCKS.register("hydrogen_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> HELIUM_BLOCK = BLOCKS.register("helium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LITHIUM_BLOCK = BLOCKS.register("lithium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BERYLLIUM_BLOCK = BLOCKS.register("beryllium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BORON_BLOCK = BLOCKS.register("boron_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CARBON_BLOCK = BLOCKS.register("carbon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NITROGEN_BLOCK = BLOCKS.register("nitrogen_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> OXYGEN_BLOCK = BLOCKS.register("oxygen_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> FLUORINE_BLOCK = BLOCKS.register("fluorine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NEON_BLOCK = BLOCKS.register("neon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SODIUM_BLOCK = BLOCKS.register("sodium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MAGNESIUM_BLOCK = BLOCKS.register("magnesium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SILICON_BLOCK = BLOCKS.register("silicon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PHOSPHORUS_BLOCK = BLOCKS.register("phosphorus_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SULFUR_BLOCK = BLOCKS.register("sulfur_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CHLORINE_BLOCK = BLOCKS.register("chlorine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ARGON_BLOCK = BLOCKS.register("argon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> POTASSIUM_BLOCK = BLOCKS.register("potassium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CALCIUM_BLOCK = BLOCKS.register("calcium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SCANDIUM_BLOCK = BLOCKS.register("scandium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> VANADIUM_BLOCK = BLOCKS.register("vanadium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CHROMIUM_BLOCK = BLOCKS.register("chromium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MANGANESE_BLOCK = BLOCKS.register("manganese_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> COBALT_BLOCK = BLOCKS.register("cobalt_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NICKEL_BLOCK = BLOCKS.register("nickel_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ZINC_BLOCK = BLOCKS.register("zinc_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> GALLIUM_BLOCK = BLOCKS.register("gallium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> GERMANIUM_BLOCK = BLOCKS.register("germanium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ARSENIC_BLOCK = BLOCKS.register("arsenic_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SELENIUM_BLOCK = BLOCKS.register("selenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BROMINE_BLOCK = BLOCKS.register("bromine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> KRYPTON_BLOCK = BLOCKS.register("krypton_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RUBIDIUM_BLOCK = BLOCKS.register("rubidium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> STRONTIUM_BLOCK = BLOCKS.register("strontium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> YTTRIUM_BLOCK = BLOCKS.register("yttrium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ZIRCONIUM_BLOCK = BLOCKS.register("zirconium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NIOBIUM_BLOCK = BLOCKS.register("niobium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MOLYBDENUM_BLOCK = BLOCKS.register("molybdenum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TECHNETIUM_BLOCK = BLOCKS.register("technetium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RUTHENIUM_BLOCK = BLOCKS.register("ruthenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RHODIUM_BLOCK = BLOCKS.register("rhodium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PALLADIUM_BLOCK = BLOCKS.register("palladium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CADMIUM_BLOCK = BLOCKS.register("cadmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> INDIUM_BLOCK = BLOCKS.register("indium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ANTIMONY_BLOCK = BLOCKS.register("antimony_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TELLURIUM_BLOCK = BLOCKS.register("tellurium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> IODINE_BLOCK = BLOCKS.register("iodine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> XENON_BLOCK = BLOCKS.register("xenon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CESIUM_BLOCK = BLOCKS.register("cesium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BARIUM_BLOCK = BLOCKS.register("barium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LANTHANUM_BLOCK = BLOCKS.register("lanthanum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CERIUM_BLOCK = BLOCKS.register("cerium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PRASEODYMIUM_BLOCK = BLOCKS.register("praseodymium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NEODYMIUM_BLOCK = BLOCKS.register("neodymium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PROMETHIUM_BLOCK = BLOCKS.register("promethium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SAMARIUM_BLOCK = BLOCKS.register("samarium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> EUROPIUM_BLOCK = BLOCKS.register("europium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> GADOLINIUM_BLOCK = BLOCKS.register("gadolinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TERBIUM_BLOCK = BLOCKS.register("terbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> DYSPROSIUM_BLOCK = BLOCKS.register("dysprosium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> HOLMIUM_BLOCK = BLOCKS.register("holmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ERBIUM_BLOCK = BLOCKS.register("erbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> THULIUM_BLOCK = BLOCKS.register("thulium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> YTTERBIUM_BLOCK = BLOCKS.register("ytterbium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LUTETIUM_BLOCK = BLOCKS.register("lutetium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> HAFNIUM_BLOCK = BLOCKS.register("hafnium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TANTALUM_BLOCK = BLOCKS.register("tantalum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TUNGSTEN_BLOCK = BLOCKS.register("tungsten_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RHENIUM_BLOCK = BLOCKS.register("rhenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> OSMIUM_BLOCK = BLOCKS.register("osmium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> IRIDIUM_BLOCK = BLOCKS.register("iridium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PLATINUM_BLOCK = BLOCKS.register("platinum_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MERCURY_BLOCK = BLOCKS.register("mercury_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> THALLIUM_BLOCK = BLOCKS.register("thallium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BISMUTH_BLOCK = BLOCKS.register("bismuth_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> POLONIUM_BLOCK = BLOCKS.register("polonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ASTATINE_BLOCK = BLOCKS.register("astatine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RADON_BLOCK = BLOCKS.register("radon_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> FRANCIUM_BLOCK = BLOCKS.register("francium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RADIUM_BLOCK = BLOCKS.register("radium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ACTINIUM_BLOCK = BLOCKS.register("actinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> THORIUM_BLOCK = BLOCKS.register("thorium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PROTACTINIUM_BLOCK = BLOCKS.register("protactinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> URANIUM_BLOCK = BLOCKS.register("uranium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NEPTUNIUM_BLOCK = BLOCKS.register("neptunium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> PLUTONIUM_BLOCK = BLOCKS.register("plutonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> AMERICIUM_BLOCK = BLOCKS.register("americium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CURIUM_BLOCK = BLOCKS.register("curium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BERKELIUM_BLOCK = BLOCKS.register("berkelium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> CALIFORNIUM_BLOCK = BLOCKS.register("californium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> EINSTEINIUM_BLOCK = BLOCKS.register("einsteinium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> FERMIUM_BLOCK = BLOCKS.register("fermium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MENDELEVIUM_BLOCK = BLOCKS.register("mendelevium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NOBELIUM_BLOCK = BLOCKS.register("nobelium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LAWRENCIUM_BLOCK = BLOCKS.register("lawrencium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> RUTHERFORDIUM_BLOCK = BLOCKS.register("rutherfordium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> DUBNIUM_BLOCK = BLOCKS.register("dubnium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> SEABORGIUM_BLOCK = BLOCKS.register("seaborgium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> BOHRIUM_BLOCK = BLOCKS.register("bohrium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> HASSIUM_BLOCK = BLOCKS.register("hassium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MEITNERIUM_BLOCK = BLOCKS.register("meitnerium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> DARMSTADTIUM_BLOCK = BLOCKS.register("darmstadtium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ROENTGENIUM_BLOCK = BLOCKS.register("roentgenium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> COPERNICIUM_BLOCK = BLOCKS.register("copernicium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> NIHONIUM_BLOCK = BLOCKS.register("nihonium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> FLEROVIUM_BLOCK = BLOCKS.register("flerovium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> MOSCOVIUM_BLOCK = BLOCKS.register("moscovium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> LIVERMORIUM_BLOCK = BLOCKS.register("livermorium_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> TENNESSINE_BLOCK = BLOCKS.register("tennessine_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> OGANESSON_BLOCK = BLOCKS.register("oganesson_block", () -> new Block(DEF_METAL_BLOCK));
    public static final RegistryObject<Block> ENDOSITUM_BLOCK = BLOCKS.register("endositum_block", () -> new Block(DEF_METAL_BLOCK));


    public static final RegistryObject<Block> SODIUM_VAPOR_LAMP = BLOCKS.register("sodium_vapor_lamp", () -> new SodiumVaporLampBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((state) -> 15).noOcclusion()));

/*
    public static final RegistryObject<Block> CALCITE_COLUMN = REGISTRY.register("calcite_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(CALCITE_BLOCK.get())));
    public static final RegistryObject<Block> DOLOMITE_COLUMN = REGISTRY.register("dolomite_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(DOLOMITE_BLOCK.get())));
    public static final RegistryObject<Block> SALT_COLUMN = REGISTRY.register("salt_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(SODIUM_CHLORIDE_BLOCK.get())));
    public static final RegistryObject<Block> PINK_SALT_COLUMN = REGISTRY.register("pink_salt_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(PINK_SALT_BLOCK.get())));
    public static final RegistryObject<Block> OPAL_COLUMN = REGISTRY.register("opal_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(OPAL_BLOCK.get())));
    public static final RegistryObject<Block> QUARTZ_COLUMN = REGISTRY.register("quartz_column", () -> new MineralColumnBlock(AbstractBlock.Properties.from(Blocks.QUARTZ_BLOCK)));


 */

    //OTHER STUFFS

    @ObjectHolder("rankine:alloy_furnace")
    public static MenuType<AlloyFurnaceContainer> ALLOY_FURNACE_CONTAINER;

    @ObjectHolder("rankine:alloy_furnace")
    public static BlockEntityType<AlloyFurnaceTile> ALLOY_FURNACE_TILE;

    @ObjectHolder("rankine:induction_furnace")
    public static MenuType<InductionFurnaceContainer> INDUCTION_FURNACE_CONTAINER;

    @ObjectHolder("rankine:induction_furnace")
    public static BlockEntityType<InductionFurnaceTile> INDUCTION_FURNACE_TILE;

    @ObjectHolder("rankine:fusion_furnace")
    public static MenuType<FusionFurnaceContainer> FUSION_FURNACE_CONTAINER;

    @ObjectHolder("rankine:fusion_furnace")
    public static BlockEntityType<FusionFurnaceTile> FUSION_FURNACE_TILE;

    @ObjectHolder("rankine:alloy_block")
    public static BlockEntityType<AlloyBlockTile> ALLOY_BLOCK_TILE;
    /*
        @ObjectHolder("rankine:laser_quarry")
        public static ContainerType<LaserQuarryContainer> LASER_QUARRY_CONTAINER;

        @ObjectHolder("rankine:laser_quarry")
        public static TileEntityType<LaserQuarryTile> LASER_QUARRY_TILE;


     */

    @ObjectHolder("rankine:evaporation_tower")
    public static MenuType<EvaporationTowerContainer> EVAPORATION_TOWER_CONTAINER;

    @ObjectHolder("rankine:evaporation_tower")
    public static BlockEntityType<EvaporationTowerTile> EVAPORATION_TOWER_TILE;

    @ObjectHolder("rankine:gas_condenser")
    public static MenuType<GasBottlerContainer> GAS_CONDENSER_CONTAINER;

    @ObjectHolder("rankine:gas_condenser")
    public static BlockEntityType<GasBottlerTile> GAS_CONDENSER_TILE;
    @ObjectHolder("rankine:gas_vent")
    public static BlockEntityType<GasVentTile> GAS_VENT_TILE;
    @ObjectHolder("rankine:sediment_fan")
    public static BlockEntityType<SedimentFanTile> SEDIMENT_FAN_TILE;
    @ObjectHolder("rankine:tree_tap")
    public static BlockEntityType<TreeTapTile> TREE_TAP_TILE;

    @ObjectHolder("rankine:crucible")
    public static MenuType<CrucibleContainer> CRUCIBLE_CONTAINER;

    @ObjectHolder("rankine:crucible")
    public static BlockEntityType<CrucibleTile> CRUCIBLE_TILE;

    @ObjectHolder("rankine:mixing_barrel")
    public static MenuType<MixingBarrelContainer> MIXING_BARREL_CONTAINER;

    @ObjectHolder("rankine:mixing_barrel")
    public static BlockEntityType<MixingBarrelTile> MIXING_BARREL_TILE;

    @ObjectHolder("rankine:template_table")
    public static MenuType<TemplateTableContainer> TEMPLATE_TABLE_CONTAINER;

    @ObjectHolder("rankine:material_testing_table")
    public static BlockEntityType<MaterialTestingTableTile> MATERIAL_TESTING_TABLE_TILE;

    @ObjectHolder("rankine:material_testing_table")
    public static MenuType<MaterialTestingTableContainer> MATERIAL_TESTING_TABLE_CONTAINER;

    @ObjectHolder("rankine:ground_tap")
    public static BlockEntityType<GroundTapTile> GROUND_TAP_TILE;

    @ObjectHolder("rankine:beehive_oven")
    public static BlockEntityType<BeehiveOvenTile> BEEHIVE_OVEN_TILE;

    @ObjectHolder("rankine:distillation_tower")
    public static BlockEntityType<DistillationTowerTile> DISTILLATION_TOWER_TILE;

    @ObjectHolder("rankine:tilled_soil")
    public static BlockEntityType<TilledSoilTile> TILLED_SOIL_TILE;

    @ObjectHolder("rankine:pedestal")
    public static BlockEntityType<PedestalTile> PEDESTAL_TILE;
    @ObjectHolder("rankine:particle_accelerator")
    public static BlockEntityType<ParticleAcceleratorTile> PARTICLE_ACCELERATOR_TILE;
    @ObjectHolder("rankine:charcoal_pit")
    public static BlockEntityType<CharcoalPitTile> CHARCOAL_PIT_TILE;


    public static Block getBlock(String name) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", name));
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
