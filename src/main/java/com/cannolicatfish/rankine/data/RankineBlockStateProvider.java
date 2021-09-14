package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.asphalt.BaseAsphaltBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.*;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.*;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
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
        getVariantBuilder(RankineBlocks.ENDER_SHIRO.get()).partialState().modelForState().modelFile(models().cubeBottomTop(RankineBlocks.ENDER_SHIRO.get().getRegistryName().toString(), getRSL("ender_shiro_side"), getRSL("minecraft","end_stone"), getRSL("ender_shiro_top"))).addModel();

        getVariantBuilder(RankineBlocks.TILLED_SOIL.get()).forAllStates(state -> {
            int MOISTURE = state.get(TilledSoilBlock.MOISTURE);
            TilledSoilTypes TYPE = state.get(TilledSoilBlock.SOIL_TYPE);
            String namespace;
            switch (TYPE) {
                case END_SOIL:
                case LOAM:
                case HUMUS:
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
        for (Block blk : Stream.of(RankineLists.STANDARD_BLOCKS, RankineLists.MINERAL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            simpleBlock(blk);
        }
        //Rotation blocks
        for (Block blk : Stream.of(RankineLists.ROTATION_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            rotationBlock(blk);
        }

        slabBlock(RankineBlocks.SOD_BLOCK_SLAB.get());
        verticalSlabBlock(RankineBlocks.SOD_BLOCK_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.SOD_BLOCK_STAIRS.get());
        wallBlock(RankineBlocks.SOD_BLOCK_WALL.get());
        slabBlock(RankineBlocks.FIBER_BLOCK_SLAB.get());
        verticalSlabBlock(RankineBlocks.FIBER_BLOCK_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.FIBER_BLOCK_STAIRS.get());
        wallBlock(RankineBlocks.FIBER_BLOCK_WALL.get());
        simpleBlock(RankineBlocks.CAST_IRON_SUPPORT.get());
        slabBlock(RankineBlocks.CAST_IRON_SUPPORT_SLAB.get());
        verticalSlabBlock(RankineBlocks.CAST_IRON_SUPPORT_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get());
        simpleBlock(RankineBlocks.CEMENT.get());
        slabBlock(RankineBlocks.CEMENT_SLAB.get());
        verticalSlabBlock(RankineBlocks.CEMENT_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.CEMENT_STAIRS.get());
        wallBlock(RankineBlocks.CEMENT_WALL.get());
        simpleBlock(RankineBlocks.CONCRETE.get());
        slabBlock(RankineBlocks.CONCRETE_SLAB.get());
        verticalSlabBlock(RankineBlocks.CONCRETE_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.CONCRETE_STAIRS.get());
        wallBlock(RankineBlocks.CONCRETE_WALL.get());
        simpleBlock(RankineBlocks.CHECKERED_MARBLE.get());
        slabBlock(RankineBlocks.CHECKERED_MARBLE_SLAB.get());
        verticalSlabBlock(RankineBlocks.CHECKERED_MARBLE_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.CHECKERED_MARBLE_STAIRS.get());
        wallBlock(RankineBlocks.CHECKERED_MARBLE_WALL.get());
        simpleBlock(RankineBlocks.ROMAN_CONCRETE.get());
        slabBlock(RankineBlocks.ROMAN_CONCRETE_SLAB.get());
        verticalSlabBlock(RankineBlocks.ROMAN_CONCRETE_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.ROMAN_CONCRETE_STAIRS.get());
        wallBlock(RankineBlocks.ROMAN_CONCRETE_WALL.get());
        fancyPolishedBlock(RankineBlocks.POLISHED_ROMAN_CONCRETE.get());
        slabBlock(RankineBlocks.POLISHED_ROMAN_CONCRETE_SLAB.get());
        verticalSlabBlock(RankineBlocks.POLISHED_ROMAN_CONCRETE_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.POLISHED_ROMAN_CONCRETE_STAIRS.get());
        wallBlock(RankineBlocks.POLISHED_ROMAN_CONCRETE_WALL.get());
        simpleBlock(RankineBlocks.ROMAN_CONCRETE_BRICKS.get());
        slabBlock(RankineBlocks.ROMAN_CONCRETE_BRICKS_SLAB.get());
        verticalSlabBlock(RankineBlocks.ROMAN_CONCRETE_BRICKS_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.ROMAN_CONCRETE_BRICKS_STAIRS.get());
        wallBlock(RankineBlocks.ROMAN_CONCRETE_BRICKS_WALL.get());
        simpleBlock(RankineBlocks.SKARN.get());
        slabBlock(RankineBlocks.SKARN_SLAB.get());
        verticalSlabBlock(RankineBlocks.SKARN_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.SKARN_STAIRS.get());
        wallBlock(RankineBlocks.SKARN_WALL.get());
        simpleBlock(RankineBlocks.BRECCIA.get());
        slabBlock(RankineBlocks.BRECCIA_SLAB.get());
        verticalSlabBlock(RankineBlocks.BRECCIA_VERTICAL_SLAB.get());
        stairsBlock(RankineBlocks.BRECCIA_STAIRS.get());
        wallBlock(RankineBlocks.BRECCIA_WALL.get());

        for (Block BLOCK : RankineLists.BRICKS) {
            simpleBlock(BLOCK);
        }
        for (Block BLOCK : RankineLists.BRICKS_SLAB) {
            slabBlock(BLOCK);
        }
        for (Block BLOCK : RankineLists.BRICKS_VERTICAL_SLAB) {
            verticalSlabBlock(BLOCK);
        }
        for (Block BLOCK : RankineLists.BRICKS_STAIRS) {
            stairsBlock(BLOCK);
        }
        for (Block BLOCK : RankineLists.BRICKS_WALL) {
            wallBlock(BLOCK);
        }

        for (Block BLOCK : RankineLists.EIGHT_LAYER_BLOCKS) {
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

        for (Block blk : RankineLists.PLANKS) {
            fancyPlanksBlock(blk);
        }
        for (Block blk : RankineLists.LEAVES) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.SAPLINGS) {
            String name = blk.getRegistryName().getPath();
            simpleBlock(blk, models().cross(name, modLoc("block/"+name)));
        }
        for (Block blk : RankineLists.FLOWER_POTS) {
            String name = blk.getRegistryName().getPath();
            simpleBlock(blk, models().withExistingParent(name, "block/flower_pot_cross").texture("plant", "block/"+name.replace("potted_","")));
        }
        for (Block blk : Stream.of(RankineLists.LOGS, RankineLists.STRIPPED_LOGS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            axisBlock((RotatedPillarBlock) blk, modLoc("block/"+name), modLoc("block/"+name+"_top"));
        }
        for (Block blk : Stream.of(RankineLists.WOODS, RankineLists.STRIPPED_WOODS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            axisBlock((RotatedPillarBlock) blk, modLoc("block/"+name.replace("wood","log")), modLoc("block/"+name.replace("wood","log")));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_SLABS.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            slabBlock((RankineSlabBlock) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()), new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_VERTICAL_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_VERTICAL_SLABS.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            verticalSlabBlock((RankineVerticalSlabBlock) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_FENCES).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_FENCES.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            fenceBlock((RankineWoodenFence) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_FENCE_GATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_FENCE_GATES.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            fenceGateBlock((RankineWoodenFenceGate) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_STAIRS.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            stairsBlock((RankineStairsBlock) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_BUTTONS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_BUTTONS.indexOf(blk));
            buttonBlock((RankineWoodenButton) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_PRESSURE_PLATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block BASE = RankineLists.PLANKS.get(RankineLists.WOODEN_PRESSURE_PLATES.indexOf(blk));
            String name = blk.getRegistryName().getPath();
            pressurePlateBlock((RankineWoodenPressurePlate) blk, new ResourceLocation("rankine","block/"+BASE.getRegistryName().getPath()));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_TRAPDOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            trapdoorBlock((RankineWoodenTrapDoor) blk, new ResourceLocation("rankine","block/"+name), true);
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_DOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            doorBlock((RankineWoodenDoor) blk, new ResourceLocation("rankine","block/"+name+"_bottom"), new ResourceLocation("rankine","block/"+name+"_top"));
        }

        //Ores
        for (Block blk : Stream.of(RankineLists.NATIVE_ORES, RankineLists.CRUSHING_ORES, RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String regName = blk.getRegistryName().getPath();

            getVariantBuilder(blk).forAllStates(state -> {
                int i = state.get(RankineOreBlock.TYPE);
                try {
                    List<String> backgrounds = Arrays.asList(WorldgenUtils.ORE_TEXTURES.get(i).split(":"));
                    String mod = backgrounds.get(0);
                    String background = backgrounds.get(1);
                    return ConfiguredModel.builder().modelFile(rankineOre(regName + i, mod, background, regName)).build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        }

        //STONES
        for (Block blk : Stream.of(RankineLists.STONE).flatMap(Collection::stream).collect(Collectors.toList())) {
            simpleBlock(blk);
        }
        for (Block blk : Stream.of(RankineLists.POLISHED_STONE).flatMap(Collection::stream).collect(Collectors.toList())) {
            fancyPolishedBlock(blk);
        }
        for (Block blk : Stream.of(RankineLists.STONE_BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            fancyBricksBlock(blk);
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
            verticalSlabBlock((RankineVerticalSlabBlock) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_PRESSURE_PLATE, RankineLists.STONE_BRICKS_PRESSURE_PLATE).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_pressure_plate")).get(0);
            pressurePlateBlock((RankineStonePressurePlate) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }
        for (Block blk : Stream.of(RankineLists.STONE_BUTTON).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_button")).get(0);
            buttonBlock((RankineStoneButton) blk, new ResourceLocation("rankine", "block/" + baseStone));
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
        for (Block PATH : RankineLists.PATH_BLOCKS) {
            String regName = PATH.getRegistryName().getPath();
            Block SOIL = RankineLists.SOILS.get(RankineLists.PATH_BLOCKS.indexOf(PATH));
            ResourceLocation TOP = new ResourceLocation("rankine","block/"+regName+"_top");
            ResourceLocation SIDE = new ResourceLocation("rankine","block/"+regName+"_side");
            ResourceLocation BOTTOM = new ResourceLocation("rankine","block/"+SOIL.getRegistryName().getPath());
            pathBlock(PATH, new ResourceLocation("minecraft","block/grass_path_top"), SIDE, BOTTOM);
        }
        pathBlock(RankineBlocks.END_GRASS_PATH.get(), new ResourceLocation("rankine","block/mycelium_path_top"), new ResourceLocation("rankine","block/mycelium_path_side"), new ResourceLocation("minecraft","block/dirt"));
        pathBlock(RankineBlocks.MYCELIUM_PATH.get(), new ResourceLocation("rankine","block/mycelium_path_top"), new ResourceLocation("rankine","block/mycelium_path_side"), new ResourceLocation("minecraft","block/dirt"));


        //Sheetmetals
        for (Block blk : RankineLists.SHEETMETALS) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.SHEETMETAL_VERTICAL_SLAB) {
            String regName = blk.getRegistryName().getPath();
            String baseMetal = Arrays.asList(regName.split("_vertical_slab")).get(0);
            verticalSlabBlock((RankineVerticalSlabBlock) blk, new ResourceLocation("rankine", "block/" + baseMetal));
        }

        //Alloy Blocks
        for (Block blk : RankineLists.ALLOY_BLOCKS) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_PEDESTALS) {
            pedestalBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_POLES) {
            metalPoleBlock((MetalPoleBlock) blk,modLoc("block/"+blk.getRegistryName().getPath().replace("pole","block")));
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

        asphaltBlock(RankineBlocks.ASPHALT_0.get(), 16.0f);
        asphaltBlock(RankineBlocks.ASPHALT_1.get(), 12.0f);
        asphaltBlock(RankineBlocks.ASPHALT_2.get(), 8.0f);
        asphaltBlock(RankineBlocks.ASPHALT_3.get(), 4.0f);


        //asphaltBlock(RankineBlocks.MANHOLE.get());
        getVariantBuilder(RankineBlocks.POTHOLE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.POTHOLE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getRSL("asphalt/asphalt_age0"))
                .texture("side", getRSL("asphalt/asphalt_age0"))
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

        getVariantBuilder(RankineBlocks.CEMENT_POLE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.CEMENT_POLE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getRSL("cement"))
                .texture("all", getRSL("cement"))
                .element().from(0.0f,0.0f,0.0f).to(16.0f,8.0f,16.0f)
                .face(Direction.NORTH).uvs(0,8, 16, 16).texture("#all").end()
                .face(Direction.EAST).uvs(0, 8, 16, 16).texture("#all").end()
                .face(Direction.SOUTH).uvs(0, 8, 16, 16).texture("#all").end()
                .face(Direction.WEST).uvs(0, 8, 16, 16).texture("#all").end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#all").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(Direction.DOWN).end()
                .end()
                .element().from(4.0f,8.0f,12.0f).to(4.0f,16.0f,12.0f)
                .face(Direction.NORTH).uvs(4,0,12,8).texture("#all").end()
                .face(Direction.EAST).uvs(4,0,12,8).texture("#all").end()
                .face(Direction.SOUTH).uvs(4,0,12,8).texture("#all").end()
                .face(Direction.WEST).uvs(4,0,12,8).texture("#all").end()
                .face(Direction.UP).uvs(4,0,12,8).texture("#all").end()
                .face(Direction.DOWN).uvs(4,0,12,8).texture("#all").end()
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

    public void asphaltBlock(Block blk, Float HEIGHT) {
        getVariantBuilder(blk).forAllStates(state -> {
            int rot = (int) state.get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalAngle();
            int AGE = state.get(BaseAsphaltBlock.AGE);
            String lineType = state.get(BaseAsphaltBlock.LINE_TYPE).toString();

            return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent("block/asphalt/"+blk.getRegistryName().getPath()+"_age"+AGE+"_"+lineType, mcLoc("block/block"))
                    .texture("particle", getRSL("asphalt/asphalt_age"+AGE))
                    .texture("base", getRSL("asphalt/asphalt_age"+AGE))
                    .texture("overlay", getRSL("asphalt/"+lineType))
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,HEIGHT,16.0f)
                        .face(Direction.NORTH).uvs(0,16-HEIGHT, 16, 16).texture("#base").cullface(Direction.NORTH).end()
                        .face(Direction.EAST).uvs(0, 16-HEIGHT, 16, 16).texture("#base").cullface(Direction.EAST).end()
                        .face(Direction.SOUTH).uvs(0, 16-HEIGHT, 16, 16).texture("#base").cullface(Direction.SOUTH).end()
                        .face(Direction.WEST).uvs(0, 16-HEIGHT, 16, 16).texture("#base").cullface(Direction.WEST).end()
                        .face(Direction.UP).uvs(0, 0, 16, 16).texture("#base").end()
                        .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#base").cullface(Direction.DOWN).end()
                        .end()
                    .element().from(0.0f,HEIGHT,0.0f).to(16.0f,HEIGHT,16.0f)
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
        ModelFile grassy = models().withExistingParent(grassyName, modLoc("block/template_grassy_soil")).texture("soil", new ResourceLocation("rankine", "block/" + soilName)).texture("grassy_soil_top", new ResourceLocation("rankine", "block/" + grassyName + "_top")).texture("grassy_soil_side", new ResourceLocation("rankine", "block/" + grassyName + "_side")).texture("grassy_soil_side_overlay", new ResourceLocation("rankine", "block/" + grassyName + "_side_overlay"));
        ModelFile snowy = models().withExistingParent(grassyName+"_snow", mcLoc("block/cube_bottom_top")).texture("top", new ResourceLocation("rankine", "block/" + grassyName + "_top")).texture("bottom", new ResourceLocation("rankine", "block/" + soilName)).texture("side", new ResourceLocation("rankine", "block/"+grassyName+"_snow"));
        getVariantBuilder(grass)
                .partialState().with(GrassBlock.SNOWY, false).modelForState().modelFile(grassy).rotationY(0).nextModel().modelFile(grassy).rotationY(90).nextModel().modelFile(grassy).rotationY(180).nextModel().modelFile(grassy).rotationY(270).addModel()
                .partialState().with(GrassBlock.SNOWY, true).modelForState().modelFile(snowy).addModel();
    }

    public void rotationBlock(Block blk) {
        ModelFile model = cubeAll(blk);
        getVariantBuilder(blk)
                .partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }

    public ModelBuilder<BlockModelBuilder> rankineOre(String name, String mod, String background, String overlay) {
        if (WorldgenUtils.ORE_TEXTURES.contains(background)) {
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

    public void metalPoleBlock(MetalPoleBlock blk, ResourceLocation texture) {
        fourWayBlock(blk, metalPole(blk.getRegistryName().getPath(), texture), metalPoleSide(blk.getRegistryName().getPath() + "_pole_side", texture));
    }
    public void fourWayBlock(MetalPoleBlock block, ModelFile post, ModelFile side) {
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
    public void verticalSlabBlock(RankineVerticalSlabBlock BLK, ResourceLocation all) {
        ModelFile straightSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_straight", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,8.0f)
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile doubleSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_double", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).texture("#all").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile innerSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_inner", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,0.0f).to(8.0f,16.0f,8.0f)
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile outerSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_outer", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,8.0f)
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end()
                .element().from(0.0f,0.0f,8.0f).to(8.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").cullface(Direction.NORTH).end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#all").cullface(Direction.WEST).end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();

        verticalSlabBlock(BLK, straightSlab, doubleSlab, innerSlab, outerSlab);

    }
    public void verticalSlabBlock(RankineVerticalSlabBlock block, ModelFile straightSlab, ModelFile doubleSlab, ModelFile innerSlab, ModelFile outerSlab) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.get(RankineVerticalSlabBlock.HORIZONTAL_FACING);
            VerticalSlabStates type = state.get(RankineVerticalSlabBlock.TYPE);
            ModelFile MODEL;
            switch (type) {
                case STRAIGHT:
                    MODEL = straightSlab;
                    break;
                case DOUBLE:
                    MODEL = doubleSlab;
                    break;
                case INNER:
                    MODEL = innerSlab;
                    break;
                case OUTER:
                    MODEL = outerSlab;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            int yRot = (int) facing.getHorizontalAngle();
            return ConfiguredModel.builder()
                    .modelFile(MODEL)
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


    public void buttonBlock(AbstractButtonBlock blk, ResourceLocation texture) {
        String regName = blk.getRegistryName().getPath();
        buttonInventory(regName, texture);
        buttonBlock(blk, models().withExistingParent(regName, mcLoc("block/button")).texture("texture", texture), models().withExistingParent(regName+"_pressed", mcLoc("block/button_pressed")).texture("texture", texture));
    }
    public ModelBuilder<BlockModelBuilder> buttonInventory(String name, ResourceLocation texture) {
        return models().withExistingParent(name+"_inventory", mcLoc("block/button_inventory")).texture("texture", texture);
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
                    .rotationX(face == AttachFace.CEILING ? 180 : face == AttachFace.FLOOR ? 0 : 270)
                    .uvLock(true)
                    .build();
        });
    }

    public void SVLBlock(SodiumVaporLampBlock blk) {
        ModelFile HANG = models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_sodium_vapor_lamp")).texture("base", getRSL("cast_iron_block")).texture("lamp", getRSL("sodium_vapor_lamp"));
        ModelFile WALL = models().withExistingParent(blk.getRegistryName().getPath()+"wall", modLoc("block/template_sodium_vapor_lamp_wall")).texture("base", getRSL("cast_iron_block")).texture("lamp", getRSL("sodium_vapor_lamp")).texture("pole", getRSL("cast_iron_pole"));

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


    public void fancyPolishedBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation polished = modLoc("block/"+name);
        getVariantBuilder(block)
                .partialState().with(RankinePolishedStoneBlock.POLISH_TYPE, PolishedStonesBuildingStates.NORMAL)
                .modelForState().modelFile(models().withExistingParent(name, mcLoc("block/cube_all")).texture("all", polished)).addModel()
                .partialState().with(RankinePolishedStoneBlock.POLISH_TYPE, PolishedStonesBuildingStates.OFFSET)
                .modelForState().modelFile(models().withExistingParent(name+"_offset", mcLoc("block/block"))
                    .texture("particle", polished)
                    .texture("all", polished)
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,8.0f,16.0f)
                    .face(Direction.NORTH).uvs(0,0,16,8).texture("#all").cullface(Direction.NORTH).end()
                    .face(Direction.EAST).uvs(0,0,16,8).texture("#all").cullface(Direction.EAST).end()
                    .face(Direction.SOUTH).uvs(0,0,16,8).texture("#all").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).uvs(0,0,16,8).texture("#all").cullface(Direction.WEST).end()
                    .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                    .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                    .end()
                    .element().from(0.0f,8.0f,0.0f).to(16.0f,16.0f,16.0f)
                    .face(Direction.NORTH).uvs(0,8,16,16).texture("#all").cullface(Direction.NORTH).end()
                    .face(Direction.EAST).uvs(0,8,16,16).texture("#all").cullface(Direction.EAST).end()
                    .face(Direction.SOUTH).uvs(0,8,16,16).texture("#all").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).uvs(0,8,16,16).texture("#all").cullface(Direction.WEST).end()
                    .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                    .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                    .end()).addModel()
                .partialState().with(RankinePolishedStoneBlock.POLISH_TYPE, PolishedStonesBuildingStates.VERTICAL_OFFSET)
                .modelForState().modelFile(models().withExistingParent(name+"_vertical_offset", mcLoc("block/block"))
                        .texture("particle", polished)
                        .texture("all", polished)
                        .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,16.0f)
                        .face(Direction.NORTH).uvs(0,8,16,16).texture("#all").cullface(Direction.NORTH).end()
                        .face(Direction.EAST).uvs(0,8,16,16).texture("#all").cullface(Direction.EAST).end()
                        .face(Direction.SOUTH).uvs(0,8,16,16).texture("#all").cullface(Direction.SOUTH).end()
                        .face(Direction.WEST).uvs(0,8,16,16).texture("#all").cullface(Direction.WEST).end()
                        .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                        .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                        .end()).addModel();
    }
    public void fancyBricksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation large = modLoc("block/"+name);
        getVariantBuilder(block)
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksBuildingStates.LARGE)
                .modelForState().modelFile(models().withExistingParent(name, mcLoc("block/cube_all")).texture("all", large)).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksBuildingStates.VERTICAL_LARGE)
                .modelForState().modelFile(models().withExistingParent(name+"_vertical", modLoc("block/template_rotation")).texture("all", large)).addModel();
    }
    public void fancyPlanksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation normal = modLoc("block/"+name);
        getVariantBuilder(block)
                .partialState().with(RankinePlanksBlock.PLANKS_TYPE, PlanksBuildingStates.NORMAL)
                .modelForState().modelFile(models().withExistingParent(name, mcLoc("block/cube_all")).texture("all", normal)).addModel()
                .partialState().with(RankinePlanksBlock.PLANKS_TYPE, PlanksBuildingStates.VERTICAL)
                .modelForState().modelFile(models().withExistingParent(name+"_vertical", modLoc("block/template_rotation")).texture("all", normal)).addModel();
    }

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

    
    public void slabBlock(Block BLK) {
        String regName = BLK.getRegistryName().getPath();
        slabBlock((RankineSlabBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_slab","")), new ResourceLocation("rankine","block/"+regName.replace("_slab","")));
    }    
    public void verticalSlabBlock(Block BLK) {
        String regName = BLK.getRegistryName().getPath();
        verticalSlabBlock((RankineVerticalSlabBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_vertical_slab","")));
    }
    public void stairsBlock(Block BLK) {
        String regName = BLK.getRegistryName().getPath();
        stairsBlock((RankineStairsBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_stairs","")));
    }
    public void wallBlock(Block BLK) {
        String regName = BLK.getRegistryName().getPath();
        wallBlock((RankineWallBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_wall","")));
    }
    
}
