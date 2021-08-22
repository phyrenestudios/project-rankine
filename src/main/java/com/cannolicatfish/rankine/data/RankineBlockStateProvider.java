package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.asphalt.BaseAsphaltBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockStateProvider extends BlockStateProvider {

    public RankineBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProjectRankine.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Project Rankine - BlockStates/Models";
    }

    @Override
    protected void registerStatesAndModels() {

        axisBlock((RotatedPillarBlock) RankineBlocks.BONE_CHAR_BLOCK.get());
        axisBlock((RotatedPillarBlock) RankineBlocks.GRAY_GRANITE_PILLAR.get());


        getVariantBuilder(RankineBlocks.TILLED_SOIL.get()).forAllStates(state -> {
            int MOISTURE = state.get(TilledSoilBlock.MOISTURE);
            TilledSoilTypes TYPE = state.get(TilledSoilBlock.SOIL_TYPE);
            String namespace;
            switch (TYPE) {
                case END_SOIL:
                case LOAM:
                case LOAMY_SAND:
                case CLAY_LOAM:
                case SANDY_CLAY_LOAM:
                case SILTY_CLAY_LOAM:
                case SILTY_LOAM:
                case SANDY_CLAY:
                case SILTY_CLAY:
                case SANDY_LOAM:
                    namespace = "rankine";
                    break;
                case DIRT:
                case COARSE_DIRT:
                case SOUL_SOIL:
                default:
                    namespace = "minecraft";
                    break;
            }
            ModelFile DRY = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getString(), mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation(namespace, "block/"+TYPE.getString())).texture("top", new ResourceLocation("minecraft:block/farmland"));
            ModelFile MOIST = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getString()+"_moist", mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation(namespace, "block/"+TYPE.getString())).texture("top", new ResourceLocation("minecraft:block/farmland_moist"));

            return MOISTURE == 7 ? ConfiguredModel.builder().modelFile(MOIST).build() : ConfiguredModel.builder().modelFile(DRY).build();
            });
        /*
        getVariantBuilder(RankineBlocks.AGED_CHEESE.get()).forAllStates(state -> {
            int BITES = state.get(CakeBlock.BITES);
            return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(RankineBlocks.AGED_CHEESE.get().getRegistryName().getPath()+"_slice"+(BITES),mcLoc("block/block"))
                            .texture("particle", texture)
                            .texture("top", texture)
                            .texture("side", texture)
                            .texture("bottom", texture)
                            .element().from(0,0,0).to(16,i*2,16)
                            .face(Direction.NORTH).uvs(0,16-i*2, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                            .face(Direction.EAST).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.EAST).end()
                            .face(Direction.SOUTH).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                            .face(Direction.WEST).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.WEST).end()
                            .face(Direction.UP).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.UP).end()
                            .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                            .end()).build(); });

         */


        for (String s : Arrays.asList("cast_iron_support","fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks","checkered_marble")) {
            if (!s.equals("cast_iron_support")) {
                wallBlock((RankineWallBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_wall")), new ResourceLocation("rankine","block/"+s));
            }
            if (!s.equals("fiber_block")) {
                simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
            }
            slabBlock((RankineSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_slab")), new ResourceLocation("rankine","block/"+s), new ResourceLocation("rankine","block/"+s));
            stairsBlock((RankineStairsBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_stairs")), s, new ResourceLocation("rankine","block/"+s));
            verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s), new ResourceLocation("rankine:block/"+s));
        }



        for (Block BLOCK : RankineLists.SIXTEEN_LAYER_BLOCKS) {
            String NAME = BLOCK.getRegistryName().getPath();
            ResourceLocation texture = new ResourceLocation("rankine","block/"+NAME);
            getVariantBuilder(BLOCK).forAllStates(state -> {
                int i = state.get(RankineEightLayerBlock.LAYERS);
                return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(NAME+"_height"+(i*2),mcLoc("block/thin_block"))
                                .texture("particle", texture)
                                .texture("side", texture)
                                .element().from(0,0,0).to(16,i*2,16)
                                .face(Direction.NORTH).uvs(0,16-i*2, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                                .face(Direction.EAST).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.EAST).end()
                                .face(Direction.SOUTH).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                                .face(Direction.WEST).uvs(0, 16-i*2, 16, 16).texture("#side").cullface(Direction.WEST).end()
                                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#side").end()
                                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                                .end()).build(); });
        }



        for (Block CROP : RankineLists.CROPS_SINGLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int i = state.get(CropsBlock.AGE);
                return ConfiguredModel.builder().modelFile(models().cross(CROP.getRegistryName().getPath()+"_stage"+i,modLoc("block/"+CROP.getRegistryName().getPath()+"_stage"+i))).build();
            });
        }

        for (Block CROP : RankineLists.CROPS_DOUBLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int AGE = state.get(CropsBlock.AGE);
                String SECTION = state.get(DoubleCropsBlock.SECTION).toString();
                ModelFile MODEL;
                if (SECTION.equals(DoubleBlockHalf.UPPER.toString()) && (AGE==0 || AGE==1)) {
                    MODEL = models().withExistingParent("air",mcLoc("block/air"));
                } else {
                    MODEL = models().cross(CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE));
                }
                return ConfiguredModel.builder().modelFile(MODEL).build();
            });
        }
        for (Block CROP : RankineLists.CROPS_TRIPLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int AGE = state.get(CropsBlock.AGE);
                String SECTION = state.get(TripleCropsBlock.SECTION).toString();
                ModelFile MODEL;
                if (SECTION.equals(TripleBlockSection.TOP.toString()) && (AGE==0 || AGE==1 || AGE==2) || SECTION.equals(TripleBlockSection.MIDDLE.toString()) && (AGE==0 || AGE==1)) {
                    MODEL = models().withExistingParent("air",mcLoc("block/air"));
                } else {
                    MODEL = CROP.equals(RankineBlocks.JUTE_PLANT) ?
                            models().crop(CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE)) :
                            models().cross(CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE));
                }
                return ConfiguredModel.builder().modelFile(MODEL).build();
            });
        }


        for (Block BUSH : RankineLists.BUSH_PLANTS) {
            getVariantBuilder(BUSH).forAllStates(state -> {
                int i = state.get(RankinePlantBlock.AGE);
                return ConfiguredModel.builder().modelFile(models().cross(BUSH.getRegistryName().getPath()+"_stage"+i,modLoc("block/"+BUSH.getRegistryName().getPath()+"_stage"+i))).build();
            });
        }
        for (Block BUSH : RankineLists.DOUBLE_BUSH_PLANTS) {
            getVariantBuilder(BUSH).forAllStates(state -> {
                int AGE = state.get(RankinePlantBlock.AGE);
                String STAGE = state.get(RankineDoublePlantBlock.SECTION).toString();
                return ConfiguredModel.builder().modelFile(models().cross(BUSH.getRegistryName().getPath()+"_"+STAGE+"_stage"+AGE,modLoc("block/"+BUSH.getRegistryName().getPath()+"_"+STAGE+"_stage"+AGE))).build();
            });
        }
        for (Block blk : RankineLists.METAL_DOORS) {
            String name = blk.getRegistryName().getPath();
            doorBlock((RankineMetalDoor) blk, new ResourceLocation("rankine","block/"+name+"_bottom"), new ResourceLocation("rankine","block/"+name+"_top"));
        }
        for (Block blk : RankineLists.METAL_TRAPDOORS) {
            String name = blk.getRegistryName().getPath();
            trapdoorBlock((RankineMetalTrapdoor) blk, new ResourceLocation("rankine","block/"+name), true);
        }


        //Mineral Blocks
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_block")));
        }



        for (String s : RankineLists.WOODS_S) {
            if (s.equals("bamboo_culms")) {
                simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
                slabBlock((RankineSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_slab")), new ResourceLocation("rankine","block/"+s), new ResourceLocation("rankine","block/"+s));
                stairsBlock((RankineStairsBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_stairs")), s, new ResourceLocation("rankine","block/"+s));
                fenceBlock((RankineWoodenFence) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_fence")), new ResourceLocation("rankine","block/"+s));
                fenceGateBlock((RankineWoodenFenceGate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_fence_gate")), new ResourceLocation("rankine","block/"+s));
                doorBlock((RankineWoodenDoor) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_door")), new ResourceLocation("rankine","block/"+s+"_door_bottom"), new ResourceLocation("rankine","block/"+s+"_door_top"));
                trapdoorBlock((RankineWoodenTrapDoor) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_trapdoor")), new ResourceLocation("rankine","block/"+s), true);
                verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s), new ResourceLocation("rankine:block/"+s));
                buttonBlock(s, (RankineWoodenButton) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_button")), new ResourceLocation("rankine", "block/" + s));
                pressurePlateBlock((RankineWoodenPressurePlate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_pressure_plate")), new ResourceLocation("rankine", "block/" + s));
            } else {
                if (!s.equals("bamboo")) {
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_log")), modLoc("block/"+s+"_log"), modLoc("block/"+s+"_log_top"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","stripped_"+s+"_log")), modLoc("block/"+"stripped_"+s+"_log"), modLoc("block/"+"stripped_"+s+"_log_top"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_wood")), modLoc("block/"+s+"_log"), modLoc("block/"+s+"_log"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","stripped_"+s+"_wood")), modLoc("block/"+"stripped_"+s+"_log"), modLoc("block/"+"stripped_"+s+"_log"));
                    if (!s.equals("petrified_chorus") && !s.equals("charred")) {
                        simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_leaves")));
                        simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_sapling")), models().cross(s+"_sapling", modLoc("block/"+s+"_sapling")));
                        simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","potted_"+s+"_sapling")), models().withExistingParent("potted_"+s+"_sapling", "block/flower_pot_cross").texture("plant", "block/"+s+"_sapling"));
                    }
                }
                simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_planks")));
                slabBlock((RankineSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_slab")), new ResourceLocation("rankine","block/"+s+"_planks"), new ResourceLocation("rankine","block/"+s+"_planks"));
                stairsBlock((RankineStairsBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_stairs")), s, new ResourceLocation("rankine","block/"+s+"_planks"));
                fenceBlock((RankineWoodenFence) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_fence")), new ResourceLocation("rankine","block/"+s+"_planks"));
                fenceGateBlock((RankineWoodenFenceGate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_fence_gate")), new ResourceLocation("rankine","block/"+s+"_planks"));
                doorBlock((RankineWoodenDoor) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_door")), new ResourceLocation("rankine","block/"+s+"_door_bottom"), new ResourceLocation("rankine","block/"+s+"_door_top"));
                trapdoorBlock((RankineWoodenTrapDoor) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_trapdoor")), new ResourceLocation("rankine","block/"+s+"_planks"), true);
                verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s+"_planks"), new ResourceLocation("rankine:block/"+s+"_planks"));
                buttonBlock(s, (RankineWoodenButton) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_button")), new ResourceLocation("rankine", "block/" + s+"_planks"));
                pressurePlateBlock((RankineWoodenPressurePlate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_pressure_plate")), new ResourceLocation("rankine", "block/" + s+"_planks"));
            }

        }

        //Ores
        for (String ore : RankineLists.ORES) {

            getVariantBuilder(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", ore))).forAllStates(state -> {
                int i = state.get(RankineOreBlock.TYPE);
                List<String> backgrounds =  Arrays.asList(WGConfig.MISC.ORE_STONES.get().get(i).split(":"));
                String mod = backgrounds.get(0);
                String background = backgrounds.get(1);
                return ConfiguredModel.builder().modelFile(rankineOre(ore + i, mod, background, ore)).build();
            });
        }

        //STONES
        for (Block blk : Stream.of(RankineLists.STONE, RankineLists.POLISHED_STONE, RankineLists.STONE_BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            simpleBlock(blk, models().cubeAll(name, new ResourceLocation("rankine", "block/" + name)));
        }
        for (Block blk : Stream.of(RankineLists.STONE_SLAB, RankineLists.POLISHED_STONE_SLAB, RankineLists.STONE_BRICKS_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_slab")).get(0);
            slabBlock((RankineSlabBlock) blk, new ResourceLocation("rankine", "block/" + baseStone), new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_STAIRS, RankineLists.POLISHED_STONE_STAIRS, RankineLists.STONE_BRICKS_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_stairs")).get(0);
            stairsBlock((RankineStairsBlock) blk, baseStone, new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_WALL, RankineLists.POLISHED_STONE_WALL, RankineLists.STONE_BRICKS_WALL).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_wall")).get(0);
            wallBlock((RankineWallBlock) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_VERTICAL_SLAB, RankineLists.POLISHED_STONE_VERTICAL_SLAB, RankineLists.STONE_BRICKS_VERTICAL_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_vertical_slab")).get(0);
            verticalSlabBlock((RankineVerticalSlabBlock) blk, new ResourceLocation("rankine", "block/" + baseStone), new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_PRESSURE_PLATE, RankineLists.STONE_BRICKS_PRESSURE_PLATE).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_pressure_plate")).get(0);
            pressurePlateBlock((RankineStonePressurePlate) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }        
        for (Block blk : Stream.of(RankineLists.STONE_BUTTON).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_button")).get(0);
            buttonBlock(baseStone, (RankineStoneButton) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }


        //Normal Blocks
        for (String s : RankineLists.NORMAL_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
        }
        //Rotation blocks
        for (String s : RankineLists.ROTATION_BLOCKS) {
            rotationBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)),  new ResourceLocation("rankine", "block/" + s));
        }
        //Soil blocks
        for (Block SOIL : RankineLists.SOILS) {
            soilBlock(SOIL);
        }
        //grassy soils
        for (Block GRASS : RankineLists.GRASSY_SOILS) {
            Block SOIL = RankineLists.SOILS.get(RankineLists.GRASSY_SOILS.indexOf(GRASS));
            grassySoilBlock(GRASS, SOIL);
        }
        grassySoilBlock(RankineBlocks.END_GRASS_BLOCK.get(), RankineBlocks.END_SOIL.get());
        for (Block PATH : RankineLists.GRASSY_PATH_BLOCKS) {
            String name = PATH.getRegistryName().getPath();
            Block SOIL = RankineLists.SOILS.get(RankineLists.GRASSY_PATH_BLOCKS.indexOf(PATH));
            new ResourceLocation("rankine","block/"+name+"_top");
            new ResourceLocation("rankine","block/"+name+"_side");
            new ResourceLocation("rankine","block/"+SOIL.getRegistryName().getPath());
            pathBlock(PATH, new ResourceLocation("rankine","block/"+name+"_top"), new ResourceLocation("rankine","block/"+name+"_side"), new ResourceLocation("rankine","block/"+SOIL.getRegistryName().getPath()));
        }
        pathBlock(RankineBlocks.MYCELIUM_PATH.get(), new ResourceLocation("rankine","block/mycelium_path_top"), new ResourceLocation("rankine","block/mycelium_path_side"), new ResourceLocation("minecraft","block/dirt"));


        //Sheetmetals
        for (String s : RankineLists.SHEETMETALS_S) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
            verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s), new ResourceLocation("rankine:block/"+s));
        }

        //Alloy Blocks
        for (Block blk : RankineLists.ALLOY_BLOCKS) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_PEDESTALS) {
            pedestalBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_POLES) {
            metalPoleBlock((RankineMetalPole) blk,modLoc("block/"+blk.getRegistryName().getPath().replace("pole","block")));
        }


        paneBlock((PaneBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","cast_iron"+"_bars")), new ResourceLocation("rankine","block/"+"cast_iron"+"_bars"), new ResourceLocation("rankine","block/"+"cast_iron"+"_bars"));


        for (String s : RankineLists.ELEMENTS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_block")));
        }
        for (Block blk : RankineLists.GEODES) {
            geodeBlock(blk);
        }
        for (Block blk : RankineLists.GAS_BLOCKS) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.MINERAL_STONES) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.LEDS) {
            onOffBlock(blk);
        }
        for (Block blk : RankineLists.MINERAL_WOOL) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.METAL_LADDERS) {
            ladderBlock(blk);
        }
        for (Block blk : RankineLists.FIBER_BLOCK) {
            fiberBlock((FiberBlock) blk);
        }
        for (Block blk : RankineLists.FIBER_MAT) {
            Block fiberBlock = RankineLists.FIBER_BLOCK.get(RankineLists.FIBER_MAT.indexOf(blk));
            fiberMatBlock((FiberMatBlock) blk, new ResourceLocation("rankine","block/"+fiberBlock.getRegistryName().getPath()));
        }
        for (Block blk : RankineLists.FLUID_BLOCKS) {
            fluidBlock(blk);
        }




        doublePlant(RankineBlocks.ORANGE_LILY.get());
        doublePlant(RankineBlocks.WHITE_LILY.get());
        doublePlant(RankineBlocks.RED_LILY.get());
        doublePlant(RankineBlocks.GOLDENROD.get());
        doublePlant(RankineBlocks.PURPLE_MORNING_GLORY.get());
        doublePlant(RankineBlocks.BLUE_MORNING_GLORY.get());
        doublePlant(RankineBlocks.BLACK_MORNING_GLORY.get());
        triplePlant(RankineBlocks.CORN_STALK.get());
        pillarFour(RankineBlocks.ROPE.get());
        simpleBlock(RankineBlocks.STICK_BLOCK.get(), models().cubeBottomTop(RankineBlocks.STICK_BLOCK.get().getRegistryName().getPath(), getRSL("stick_block_side"), getRSL("stick_block_top"), getRSL("stick_block_top")));


        //MACHINES
        simpleBlock(RankineBlocks.LASER_QUARRY.get());
        simpleBlock(RankineBlocks.RANKINE_BOX.get());
        simpleBlock(RankineBlocks.DIAMOND_ANVIL_CELL.get());
        simpleBlock(RankineBlocks.SEDIMENT_FAN.get());
        simpleBlock(RankineBlocks.GAS_VENT.get());
        simpleBlock(RankineBlocks.PCF.get(), models().orientable(RankineBlocks.PCF.get().getRegistryName().getPath(), getRSL("pcf_side"), getRSL("pcf_front"), getRSL("pcf_top")));
        simpleBlock(RankineBlocks.BOTANIST_STATION.get(), models().orientable(RankineBlocks.BOTANIST_STATION.get().getRegistryName().getPath(), getRSL("botanist_station_side"), getRSL("botanist_station_front"), getRSL("botanist_station_top")));
        simpleBlock(RankineBlocks.TEMPLATE_TABLE.get(), models().orientable(RankineBlocks.TEMPLATE_TABLE.get().getRegistryName().getPath(), getRSL("template_table_side"), getRSL("template_table_front"), getRSL("template_table_top")));
        simpleBlock(RankineBlocks.EVAPORATION_TOWER.get(), models().cubeBottomTop(RankineBlocks.EVAPORATION_TOWER.get().getRegistryName().getPath(), getRSL("evaporation_tower_side"), getRSL("evaporation_tower_bottom"), getRSL("evaporation_tower_top")));
        simpleBlock(RankineBlocks.LASER_PYLON_BASE.get(), models().cubeBottomTop(RankineBlocks.LASER_PYLON_BASE.get().getRegistryName().getPath(), getRSL("laser_pylon_base"), getRSL("laser_pylon_bottom"), getRSL("laser_pylon_bottom")));
        simpleBlock(RankineBlocks.LASER_PYLON_TOP.get(), models().cubeBottomTop(RankineBlocks.LASER_PYLON_TOP.get().getRegistryName().getPath(), getRSL("laser_pylon_top"), getRSL("laser_pylon_bottom"), getRSL("stainless_steel_block")));
        onOffBlock(RankineBlocks.CHARCOAL_PIT.get(), models().cubeBottomTop(RankineBlocks.CHARCOAL_PIT.get().getRegistryName().getPath(), getRSL("charcoal_pit_side"), getRSL("charcoal_pit_top"), getRSL("charcoal_pit_top")), models().cubeBottomTop(RankineBlocks.CHARCOAL_PIT.get().getRegistryName().getPath()+"_on", getRSL("charcoal_pit_side"), getRSL("charcoal_pit_top_on"), getRSL("charcoal_pit_top_on")));
        onOffBlock(RankineBlocks.BEEHIVE_OVEN_PIT.get(), models().cubeTop(RankineBlocks.BEEHIVE_OVEN_PIT.get().getRegistryName().getPath(), new ResourceLocation("rankine", "block/refractory_bricks"), new ResourceLocation("rankine", "block/beehive_oven")), models().cubeTop(RankineBlocks.BEEHIVE_OVEN_PIT.get().getRegistryName().getPath()+"_on", new ResourceLocation("rankine", "block/refractory_bricks"), new ResourceLocation("rankine", "block/beehive_oven_on")));
        onOffBlock(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get(), models().cubeTop(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get().getRegistryName().getPath(), new ResourceLocation("rankine", "block/high_refractory_bricks"), new ResourceLocation("rankine", "block/high_beehive_oven")), models().cubeTop(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get().getRegistryName().getPath()+"_on", new ResourceLocation("rankine", "block/high_refractory_bricks"), new ResourceLocation("rankine", "block/high_beehive_oven_on")));
        onOffBlock(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get(), models().cubeTop(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get().getRegistryName().getPath(), new ResourceLocation("rankine", "block/ultra_high_refractory_bricks"), new ResourceLocation("rankine", "block/ultra_high_beehive_oven")), models().cubeTop(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get().getRegistryName().getPath()+"_on", new ResourceLocation("rankine", "block/ultra_high_refractory_bricks"), new ResourceLocation("rankine", "block/ultra_high_beehive_oven_on")));
        onOffBlock(RankineBlocks.ALLOY_FURNACE.get(), models().cubeBottomTop(RankineBlocks.ALLOY_FURNACE.get().getRegistryName().getPath(), getRSL("alloy_furnace_front"), getRSL("refractory_bricks"), getRSL("alloy_furnace_top")), models().cubeBottomTop(RankineBlocks.ALLOY_FURNACE.get().getRegistryName().getPath()+"_on", getRSL("refractory_bricks"), getRSL("alloy_furnace_top"), getRSL("alloy_furnace_front_on")));
        rotateableMachineBlock(RankineBlocks.INDUCTION_FURNACE.get(), models().orientable(RankineBlocks.INDUCTION_FURNACE.get().getRegistryName().getPath(), getRSL("induction_furnace_side"), getRSL("induction_furnace_front"), getRSL("induction_furnace_top")), models().orientable(RankineBlocks.INDUCTION_FURNACE.get().getRegistryName().getPath()+"_on", getRSL("induction_furnace_side"), getRSL("induction_furnace_front"), getRSL("induction_furnace_top_on")));
        rotateableMachineBlock(RankineBlocks.PISTON_CRUSHER.get(), models().orientable(RankineBlocks.PISTON_CRUSHER.get().getRegistryName().getPath(), getRSL("piston_crusher_side"), getRSL("piston_crusher_front"), getRSL("piston_crusher_top")), models().orientable(RankineBlocks.PISTON_CRUSHER.get().getRegistryName().getPath()+"_on", getRSL("piston_crusher_side"), getRSL("piston_crusher_front_on"), getRSL("piston_crusher_top")));
        rotateableMachineBlock(RankineBlocks.GYRATORY_CRUSHER.get(), models().orientable(RankineBlocks.GYRATORY_CRUSHER.get().getRegistryName().getPath(), getRSL("gyratory_crusher_side"), getRSL("gyratory_crusher_front"), getRSL("gyratory_crusher_top")), models().orientable(RankineBlocks.GYRATORY_CRUSHER.get().getRegistryName().getPath()+"_on", getRSL("gyratory_crusher_side"), getRSL("gyratory_crusher_front"), getRSL("gyratory_crusher_top_on")));

        electroMagnet(RankineBlocks.ALNICO_ELECTROMAGNET.get());
        electroMagnet(RankineBlocks.RARE_EARTH_ELECTROMAGNET.get());
        SVLBlock((SodiumVaporLampBlock) RankineBlocks.SODIUM_VAPOR_LAMP.get());

        asphaltBlock(RankineBlocks.ASPHALT.get());
        asphaltBlock(RankineBlocks.WORN_ASPHALT.get());
        asphaltBlock(RankineBlocks.WEATHERED_ASPHALT.get());
        asphaltBlock(RankineBlocks.CRACKED_ASPHALT.get());
        //asphaltBlock(RankineBlocks.MANHOLE.get());
        getVariantBuilder(RankineBlocks.POTHOLE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.POTHOLE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getRSL("asphalt/weathered_asphalt"))
                .texture("side", getRSL("asphalt/weathered_asphalt"))
                .texture("top", getRSL("asphalt/pothole"))
                .element().from(0.0f,0.0f,0.0f).to(16.0f,12.0f,16.0f)
                .face(Direction.NORTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.EAST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                .end()).addModel();

        getVariantBuilder(RankineBlocks.TRAMPOLINE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.TRAMPOLINE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getRSL("trampoline"))
                .texture("side", getRSL("trampoline_side"))
                .texture("top", getRSL("trampoline"))
                .element().from(0.0f,12.0f,0.0f).to(16.0f,14.0f,16.0f)
                .face(Direction.NORTH).uvs(0,2, 16, 4).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.EAST).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                .end()).addModel();


        getVariantBuilder(RankineBlocks.STUMP.get())
                .partialState().with(StumpBlock.ROT, false).modelForState().modelFile(models().withExistingParent("stump", modLoc("block/template_stump")).texture("side", getRSL("stump_side")).texture("top", getRSL("stump_top"))).addModel()
                .partialState().with(StumpBlock.ROT, true).modelForState().modelFile(models().withExistingParent("rotten_stump", modLoc("block/template_stump")).texture("side", getRSL("rotten_stump_side")).texture("top", getRSL("rotten_stump_top"))).addModel();






    }

    private ResourceLocation getRSL(String textureName) {
        return new ResourceLocation("rankine","block/"+textureName);
    }
    private ResourceLocation getRSL(String namespace, String textureName) {
        return new ResourceLocation(namespace,"block/"+textureName);
    }


    public void fluidBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(name, mcLoc("block/block")).texture("particle", getRSL(name+"_still"))).addModel();
    }

    public void asphaltBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).forAllStates(state -> {
            int rot = (int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle();
            String lineType = state.get(BaseAsphaltBlock.LINE_TYPE).toString();

            return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(blk.getRegistryName().getPath()+"_"+lineType, mcLoc("block/block"))
                    .texture("particle", getRSL("asphalt/"+name))
                    .texture("base", getRSL("asphalt/"+name))
                    .texture("overlay", getRSL("asphalt/"+lineType))
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,13.0f,16.0f)
                        .face(Direction.NORTH).uvs(0,3, 16, 16).texture("#base").cullface(Direction.NORTH).end()
                        .face(Direction.EAST).uvs(0, 3, 16, 16).texture("#base").cullface(Direction.EAST).end()
                        .face(Direction.SOUTH).uvs(0, 3, 16, 16).texture("#base").cullface(Direction.SOUTH).end()
                        .face(Direction.WEST).uvs(0, 3, 16, 16).texture("#base").cullface(Direction.WEST).end()
                        .face(Direction.UP).uvs(0, 0, 16, 16).texture("#base").end()
                        .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#base").cullface(Direction.DOWN).end()
                        .end()
                    .element().from(0.0f,13.0f,0.0f).to(16.0f,13.0f,16.0f)
                        .face(Direction.UP).uvs(0, 0, 16, 16).texture("#overlay").end()
                        .end())
                    .rotationY(rot).build();
        });
    }

    public void electroMagnet(Block block) {
        String name = block.getRegistryName().getPath();
        ModelFile off = models().cubeTop(name, getRSL(name+"_side"), getRSL(name));
        ModelFile on = models().cubeTop(name+"_on", getRSL(name+"_side"), getRSL(name+"_on"));
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(state.get(ElectromagnetBlock.POWERED) ? on : off)
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.getHorizontalAngle()) + 180) % 360)
                            .build();
                });
    }

    public void ladderBlock(Block blk) {
        String NAME = blk.getRegistryName().getPath();
        ModelFile LADDER = models().withExistingParent(NAME, mcLoc("block/block"))
                .texture("texture", new ResourceLocation("rankine", "block/" + NAME))
                .texture("particle", new ResourceLocation("rankine", "block/" + NAME))
                .element().from(0,0,15.2f).to(16,16,15.2f)
                .face(Direction.NORTH).uvs(0,0, 16, 16).texture("#texture").end()
                .face(Direction.SOUTH).uvs(16, 0, 0, 16).texture("#texture").end()
                .end();
        getVariantBuilder(blk)
                .partialState().with(MetalLadderBlock.FACING, Direction.NORTH).modelForState().modelFile(LADDER).rotationY(0).addModel()
                .partialState().with(MetalLadderBlock.FACING, Direction.EAST).modelForState().modelFile(LADDER).rotationY(90).addModel()
                .partialState().with(MetalLadderBlock.FACING, Direction.SOUTH).modelForState().modelFile(LADDER).rotationY(180).addModel()
                .partialState().with(MetalLadderBlock.FACING, Direction.WEST).modelForState().modelFile(LADDER).rotationY(270).addModel();
    }
    public void pillarFour(Block blk) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(name, modLoc("block/template_pillar_four")).texture("all", modLoc("block/"+name))).addModel();
    }
    public void onOffBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ModelFile off = models().withExistingParent(name, mcLoc("block/cube_all")).texture("all", new ResourceLocation("rankine", "block/" + name));
        ModelFile on = models().withExistingParent(name+"_on", mcLoc("block/cube_all")).texture("all", new ResourceLocation("rankine", "block/" + name + "_on"));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.LIT, false).modelForState().modelFile(off).addModel()
                .partialState().with(BlockStateProperties.LIT, true).modelForState().modelFile(on).addModel();
    }
    public void onOffBlock(Block blk, ModelFile off, ModelFile on) {
        getVariantBuilder(blk)
                .partialState().with(BlockStateProperties.LIT, false).modelForState().modelFile(off).addModel()
                .partialState().with(BlockStateProperties.LIT, true).modelForState().modelFile(on).addModel();
    }
    public void soilBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ModelFile dry = models().withExistingParent(name, mcLoc("block/cube_all")).texture("all", new ResourceLocation("rankine", "block/" + name));
        ModelFile wet = models().withExistingParent(name+"_wet", mcLoc("block/cube_all")).texture("all", new ResourceLocation("rankine", "block/" + name + "_wet"));
        getVariantBuilder(block)
                .partialState().with(SoilBlock.WET, false).modelForState().modelFile(dry).rotationY(0).nextModel().modelFile(dry).rotationY(90).nextModel().modelFile(dry).rotationY(180).nextModel().modelFile(dry).rotationY(270).addModel()
                .partialState().with(SoilBlock.WET, true).modelForState().modelFile(wet).rotationY(0).nextModel().modelFile(wet).rotationY(90).nextModel().modelFile(wet).rotationY(180).nextModel().modelFile(wet).rotationY(270).addModel();
    }
    public void grassySoilBlock(Block grass, Block soil) {
        String grassyName = grass.getRegistryName().getPath();
        String soilName = soil.getRegistryName().getPath();
        ModelFile grassy = models().withExistingParent(grassyName, modLoc("block/template_grassy_soil")).texture("soil", new ResourceLocation("rankine", "block/" + soilName)).texture("grassy_soil_top", new ResourceLocation("rankine", "block/" + grassyName + "_top")).texture("grassy_soil_side", new ResourceLocation("minecraft", "block/grass_block_side")).texture("grassy_soil_side_overlay", new ResourceLocation("minecraft", "block/grass_block_side_overlay"));
        ModelFile snowy = models().withExistingParent(grassyName+"_snow", mcLoc("block/cube_bottom_top")).texture("top", new ResourceLocation("rankine", "block/" + grassyName + "_top")).texture("bottom", new ResourceLocation("rankine", "block/" + soilName)).texture("side", new ResourceLocation("minecraft", "block/grass_block_snow"));
        getVariantBuilder(grass)
                .partialState().with(GrassBlock.SNOWY, false).modelForState().modelFile(grassy).rotationY(0).nextModel().modelFile(grassy).rotationY(90).nextModel().modelFile(grassy).rotationY(180).nextModel().modelFile(grassy).rotationY(270).addModel()
                .partialState().with(GrassBlock.SNOWY, true).modelForState().modelFile(snowy).addModel();
    }

    public void rotationBlock(Block block, ResourceLocation texture) {
        ModelFile model = models().withExistingParent(block.getRegistryName().getPath(), mcLoc("block/cube_all")).texture("all", texture);
        getVariantBuilder(block)
                .partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }

    public ModelBuilder<BlockModelBuilder> rankineOre(String name, String mod, String background, String overlay) {
        if (RankineLists.STONESSS.contains(background)) {
            return models().withExistingParent(name, modLoc("block/template_rankine_ore")).texture("background", mod+":block/"+background).texture("overlay", "block/"+overlay);
        } else {
            return models().withExistingParent(name, modLoc("block/template_rankine_ore")).texture("background", mod+":block/"+background).texture("overlay", "block/"+overlay);
        }
    }
    public void fiberBlock(FiberBlock blk) {
        String NAME = blk.getRegistryName().getPath();
        ModelFile blkModel = models().withExistingParent(NAME, modLoc("block/template_fiber_block")).texture("pattern", modLoc("block/"+NAME));
        getVariantBuilder(blk).forAllStates(state -> ConfiguredModel.builder().modelFile(blkModel).rotationY((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle()).build());
    }
    public void fiberMatBlock(FiberMatBlock blk, ResourceLocation texture) {
        String NAME = blk.getRegistryName().getPath();
        ModelFile blkModel = models().withExistingParent(NAME, mcLoc("block/carpet")).texture("wool", texture);
        getVariantBuilder(blk).forAllStates(state -> ConfiguredModel.builder().modelFile(blkModel).rotationY((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle()).build());
    }
    public void geodeBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(name, modLoc("block/template_geode")).texture("face", modLoc("block/"+name))).addModel();
    }
    public void pedestalBlock(Block blk) {
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_pedestal")).texture("all", modLoc("block/"+blk.getRegistryName().getPath().replace("_pedestal","_block")))).addModel();
    }
    public void pathBlock(Block blk, ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_path_block")).texture("top", top).texture("side", side).texture("bottom", bottom)).addModel();
    }

    public void metalPoleBlock(RankineMetalPole blk, ResourceLocation texture) {
        fourWayBlock(blk, metalPole(blk.getRegistryName().getPath(), texture), metalPoleSide(blk.getRegistryName().getPath() + "_pole_side", texture));
    }
    public void fourWayBlock(RankineMetalPole block, ModelFile post, ModelFile side) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel().end();
        fourWayMultipart(builder, side);
    }
    public ModelBuilder<BlockModelBuilder> metalPole(String name, ResourceLocation texture) {
        return models().withExistingParent(name, modLoc("block/template_metal_pole")).texture("side", texture);
    }
    public ModelBuilder<BlockModelBuilder> metalPoleSide(String name, ResourceLocation texture) {
        return models().withExistingParent(name, modLoc("block/template_metal_pole_side")).texture("side", texture);
    }


    @Override
    public void fenceBlock(FenceBlock block, ResourceLocation texture) {
        super.fenceBlock(block, texture);
        models().withExistingParent(block.getRegistryName().getPath() + "_inventory", mcLoc("block/fence_inventory"))
                .texture("texture", texture);
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        super.wallBlock(block, texture);
        models().withExistingParent(block.getRegistryName().getPath() + "_inventory", mcLoc("block/wall_inventory"))
                .texture("wall", texture);
    }


    public void pressurePlateBlock(PressurePlateBlock block, ResourceLocation texture) {
        getVariantBuilder(block)
                .partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(models().withExistingParent(block.getRegistryName().getPath()+"_down", mcLoc("block/pressure_plate_down")).texture("texture", texture)).addModel()
                .partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(models().withExistingParent(block.getRegistryName().getPath()+"_up", mcLoc("block/pressure_plate_up")).texture("texture", texture)).addModel();
    }


    //VERTICAL SLABS
    public void verticalSlabBlock(RankineVerticalSlabBlock block, ResourceLocation doubleslab, ResourceLocation texture) {
        verticalSlabBlock(block, doubleslab, texture, texture, texture);
    }
    public void verticalSlabBlock(RankineVerticalSlabBlock block, ResourceLocation doubleslab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        verticalSlabBlock(block, models().withExistingParent(block.getRegistryName().getPath(), modLoc("block/template_vertical_slab")).texture("side", side).texture("bottom", bottom).texture("top", top), models().withExistingParent(block.getRegistryName().getPath()+"_top", modLoc("block/template_vertical_slab_top")).texture("side", side).texture("bottom", bottom).texture("top", top), models().getExistingFile(doubleslab));
    }
    public void verticalSlabBlock(RankineVerticalSlabBlock block, ModelFile bottom, ModelFile top, ModelFile doubleslab) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.get(RankineVerticalSlabBlock.HORIZONTAL_FACING);
            SlabType half = state.get(RankineVerticalSlabBlock.TYPE);
            int yRot = (int) facing.rotateY().rotateY().getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(half == SlabType.DOUBLE ? doubleslab : half == SlabType.TOP  ? top : bottom)
                    .rotationY(yRot)
                    .uvLock(true)
                    .build();
        }, StairsBlock.WATERLOGGED);
    }
    public void rotateableMachineBlock(Block blk, ModelFile OFF, ModelFile ON) {
        getVariantBuilder(blk).forAllStates(state -> {
            Direction facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
            boolean on = state.get(BlockStateProperties.LIT);
            int yRot = (int) facing.rotateY().rotateY().getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(on ? ON : OFF)
                    .rotationY(yRot)
                    .build();
        });
    }


    public void buttonBlock(String name, AbstractButtonBlock block, ResourceLocation texture) {
        buttonInventory(name, texture);
        buttonBlock(block, models().withExistingParent(name+"_button", mcLoc("block/button")).texture("texture", texture), models().withExistingParent(name+"_button_pressed", mcLoc("block/button_pressed")).texture("texture", texture));
    }
    public ModelBuilder<BlockModelBuilder> buttonInventory(String name, ResourceLocation texture) {
        return models().withExistingParent(name+"_button_inventory", mcLoc("block/button_inventory")).texture("texture", texture);
    }

    public void buttonBlock(AbstractButtonBlock block, ModelFile button, ModelFile pressed) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            AttachFace face = state.get(AbstractButtonBlock.FACE);
            Direction facing = state.get(AbstractButtonBlock.HORIZONTAL_FACING);
            Boolean powered = state.get(AbstractButtonBlock.POWERED);
            int yRot = (int) facing.getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(powered ? pressed : button)
                    .rotationY(yRot)
                    .rotationX(face == AttachFace.CEILING ? 180 : face == AttachFace.FLOOR ? 0 : 90)
                    .uvLock(true)
                    .build();
        });
    }

    public void SVLBlock(SodiumVaporLampBlock blk) {
        ModelFile HANG = models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_sodium_vapor_lamp")).texture("base", getRSL("cast_iron_block")).texture("lamp", getRSL("sodium_vapor_lamp"));
        ModelFile WALL = models().withExistingParent(blk.getRegistryName().getPath()+"wall", modLoc("block/template_sodium_vapor_lamp_wall")).texture("base", getRSL("cast_iron_block")).texture("lamp", getRSL("sodium_vapor_lamp"));

        getVariantBuilder(blk).forAllStatesExcept(state -> {
            Boolean hanging = state.get(SodiumVaporLampBlock.HANGING);
            Direction facing = state.get(SodiumVaporLampBlock.HORIZONTAL_FACING);
            int yRot = (int) facing.getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(hanging ? HANG : WALL)
                    .rotationY((yRot+180)%360)
                    .build();
        }, SodiumVaporLampBlock.WATERLOGGED);
    }

    public void sixWayOnOff(BaseMachineBlock blk, ModelFile ON, ModelFile OFF) {
        getVariantBuilder(blk).forAllStates(state -> {
            Direction facing = state.get(BlockStateProperties.FACING);
            boolean on = state.get(BaseMachineBlock.LIT);
            int yRot = (int) facing.rotateY().rotateY().getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(on ? ON : OFF)
                    .rotationY(yRot)
                    .build();
        });
    }

    public void fancyBricksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation large = modLoc("block/"+name+"_large");
        ResourceLocation small = modLoc("block/"+name+"_small");
        ResourceLocation special = modLoc("block/"+name+"_special");
        getVariantBuilder(block)
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.LARGE)
                .modelForState().modelFile(models().withExistingParent(name+"_large", mcLoc("block/cube_all")).texture("all", large)).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.VERTICAL_LARGE)
                .modelForState().modelFile(models().withExistingParent(name+"_vertical_large", modLoc("block/template_rotation")).texture("all", large)).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.SMALL)
                .modelForState().modelFile(models().withExistingParent(name+"_small", mcLoc("block/cube_all")).texture("all", small)).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.VERTICAL_SMALL)
                .modelForState().modelFile(models().withExistingParent(name+"_vertical_small", modLoc("block/template_rotation")).texture("all", small)).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.SPECIAL)
                .modelForState().modelFile(models().withExistingParent(name+"_special", mcLoc("block/cube_all")).texture("all", special)).addModel();
    };

    public void doublePlant(Block block) {
        String name = block.getRegistryName().getPath();
        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                    .modelForState().modelFile(models().withExistingParent(name+"_top", mcLoc("block/cross")).texture("cross", "block/"+name+"_top")).addModel()
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                    .modelForState().modelFile(models().withExistingParent(name+"_bottom", mcLoc("block/cross")).texture("cross", "block/"+name+"_bottom")).addModel();
    }
    public void triplePlant(Block block) {
        String name = block.getRegistryName().getPath();
        getVariantBuilder(block)
                .partialState().with(TripleCropsBlock.SECTION, TripleBlockSection.TOP)
                .modelForState().modelFile(models().withExistingParent(name+"_top", mcLoc("block/cross")).texture("cross", "block/"+name+"_top")).addModel()
                .partialState().with(TripleCropsBlock.SECTION, TripleBlockSection.MIDDLE)
                .modelForState().modelFile(models().withExistingParent(name+"_middle", mcLoc("block/cross")).texture("cross", "block/"+name+"_middle")).addModel()
                .partialState().with(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)
                .modelForState().modelFile(models().withExistingParent(name+"_bottom", mcLoc("block/cross")).texture("cross", "block/"+name+"_bottom")).addModel();
    }

}
