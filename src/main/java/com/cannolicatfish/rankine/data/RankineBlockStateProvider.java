package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
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
        return "Project Rankine - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {


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
                    if (!s.equals("petrified_chorus")) {
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
            String baseStone = name;
            simpleBlock(blk, models().cubeAll(name, new ResourceLocation("rankine", "block/" + baseStone)));
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
        for (Block blk : RankineLists.LEDS) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.MINERAL_WOOL) {
            simpleBlock(blk);
        }
        for (Block blk : RankineLists.FIBER_BLOCK) {
            fiberBlock((FiberBlock) blk);
            //simpleBlock(blk);
        }
        for (Block blk : RankineLists.FIBER_MAT) {
            Block fiberBlock = RankineLists.FIBER_BLOCK.get(RankineLists.FIBER_MAT.indexOf(blk));
            fiberMatBlock((FiberMatBlock) blk, new ResourceLocation("rankine","block/"+fiberBlock.getRegistryName().getPath()));
            //simpleBlock(blk);
        }




        doublePlant(RankineBlocks.ORANGE_LILY.get());
        doublePlant(RankineBlocks.WHITE_LILY.get());
        doublePlant(RankineBlocks.RED_LILY.get());
        doublePlant(RankineBlocks.GOLDENROD.get());
        doublePlant(RankineBlocks.PURPLE_MORNING_GLORY.get());
        doublePlant(RankineBlocks.BLUE_MORNING_GLORY.get());
        doublePlant(RankineBlocks.BLACK_MORNING_GLORY.get());


    }

    private Block getBlock(String s) {
        Block blk = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s));
        if (blk != null) {
            //System.out.println("rankine:"+s);
            return blk;
        } else {
            System.out.println("rankine:"+s+ " does not exist");
            return blk;
        }
    }

    public void rotatableMachineBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ModelFile off = models().withExistingParent(name, mcLoc("block/orientable")).texture("front", new ResourceLocation("rankine", "block/" + name + "_front")).texture("side", new ResourceLocation("rankine", "block/" + name + "_side")).texture("top", new ResourceLocation("rankine", "block/" + name + "_top"));
        ModelFile on = models().withExistingParent(name+"_on", mcLoc("block/orientable")).texture("front", new ResourceLocation("rankine", "block/" + name + "_front_on")).texture("side", new ResourceLocation("rankine", "block/" + name + "_side")).texture("top", new ResourceLocation("rankine", "block/" + name + "_top"));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.LIT, false).modelForState().modelFile(off).rotationY(0).nextModel().modelFile(off).rotationY(90).nextModel().modelFile(off).rotationY(180).nextModel().modelFile(off).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.LIT, true).modelForState().modelFile(on).rotationY(0).nextModel().modelFile(on).rotationY(90).nextModel().modelFile(on).rotationY(180).nextModel().modelFile(on).rotationY(270).addModel();
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
        ModelFile snowy = models().withExistingParent(grassyName, mcLoc("block/cube_bottom_top")).texture("top", new ResourceLocation("rankine", "block/" + grassyName + "_top")).texture("bottom", new ResourceLocation("rankine", "block/" + soilName)).texture("side", new ResourceLocation("minecraft", "block/grass_block_snow"));
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
                    .modelFile(button)
                    .modelFile(pressed)
                    .rotationY(yRot)
                    .uvLock(true)
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

}
