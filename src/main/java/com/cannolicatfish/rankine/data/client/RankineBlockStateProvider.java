package com.cannolicatfish.rankine.data.client;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.states.StoneBricksStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
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
import java.util.List;

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


        for (String s : Arrays.asList("cast_iron_support","fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks","checkered_marble","checkered_dacite","checkered_porphyry")) {
            if (!s.equals("cast_iron_support")) {
                wallBlock((RankineWallBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_wall")), new ResourceLocation("rankine","block/"+s));
            }
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
            slabBlock((RankineSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_slab")), new ResourceLocation("rankine","block/"+s), new ResourceLocation("rankine","block/"+s));
            stairsBlock((RankineStairsBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_stairs")), s, new ResourceLocation("rankine","block/"+s));
            verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s), new ResourceLocation("rankine:block/"+s));
        }


        //Mineral Blocks
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_block")));
        }

        for (String s : RankineLists.WOODS) {
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
                wallBlock((RankineWallBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_wall")), new ResourceLocation("rankine", "block/" + s));
            } else {
                if (!s.equals("bamboo")) {
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_log")), modLoc("block/"+s+"_log"), modLoc("block/"+s+"_log_top"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","stripped_"+s+"_log")), modLoc("block/"+"stripped_"+s+"_log"), modLoc("block/"+"stripped_"+s+"_log_top"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_wood")), modLoc("block/"+s+"_log"), modLoc("block/"+s+"_log"));
                    axisBlock((RotatedPillarBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","stripped_"+s+"_wood")), modLoc("block/"+"stripped_"+s+"_log"), modLoc("block/"+"stripped_"+s+"_log"));
                    simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_leaves")));
                    simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_sapling")), models().cross(s+"_sapling", modLoc("block/"+s+"_sapling")));
                    simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","potted_"+s+"_sapling")), models().withExistingParent("potted_"+s+"_sapling", "block/flower_pot_cross").texture("plant", "block/"+s+"_sapling"));
                } else {
                    wallBlock((RankineWallBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_wall")), new ResourceLocation("rankine", "block/" + s+"_planks"));
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

        //Stones
        for (String x : RankineLists.STONES) {
            for (String s : Arrays.asList(x,"polished_"+x,x+"_bricks")) {
                simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s)));
                slabBlock((RankineSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_slab")), new ResourceLocation("rankine", "block/" + s), new ResourceLocation("rankine", "block/" + s));
                stairsBlock((RankineStairsBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_stairs")), s, new ResourceLocation("rankine", "block/" + s));
                wallBlock((RankineWallBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_wall")), new ResourceLocation("rankine", "block/" + s));
                verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", s + "_vertical_slab")), new ResourceLocation("rankine:block/" + s), new ResourceLocation("rankine:block/" + s));
            }
            pressurePlateBlock((RankineStonePressurePlate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", x + "_pressure_plate")), new ResourceLocation("rankine", "block/" + x));
            pressurePlateBlock((RankineStonePressurePlate) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", x + "_bricks_pressure_plate")), new ResourceLocation("rankine", "block/" + x +"_bricks"));
            buttonBlock(x, (RankineStoneButton) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", x + "_button")), new ResourceLocation("rankine", "block/" + x));

        }


        //Normal Blocks
        for (String s : RankineLists.NORMAL_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
        }
        //Rotation blocks
        for (String s : RankineLists.ROTATION_BLOCKS) {
            horizontalBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)),  new ResourceLocation("rankine", "block/" + s));
        }
        //Sheetmetals
        for (String s : RankineLists.SHEETMETALS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
            verticalSlabBlock((RankineVerticalSlabBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_vertical_slab")), new ResourceLocation("rankine:block/"+s), new ResourceLocation("rankine:block/"+s));
        }

        //Alloy Blocks
        for (String s : RankineLists.ALLOYS) {
            if (!s.equals("solder")) {
                //Alloy Blocks
                simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_block")));
                //Pedestals
                pedestalBlock(s);
                //Poles
                metalPoleBlock(s,modLoc("block/"+s+"_block"));
                //Bars
            }
        }
        paneBlock((PaneBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine","cast_iron"+"_bars")), new ResourceLocation("rankine","block/"+"cast_iron"+"_bars"), new ResourceLocation("rankine","block/"+"cast_iron"+"_bars"));

        //Element Blocks
        for (String s : RankineLists.ELEMENTS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s+"_block")));
        }
        //GEODES
        for (String s : RankineLists.GEODES) {
            geodeBlock(s);
        }


        for (String s : RankineLists.GAS_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
        }
        for (String s : RankineLists.MINERAL_WOOL) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
        }
        for (String s : RankineLists.FIBER_BLOCKS) {
            simpleBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)));
        }
        for (String s : RankineLists.FIBER_MATS) {
            fiberMatBlock((FiberMatBlock) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s)), new ResourceLocation("rankine", "block/"+s.replace("_mat","_block")));
        }


        fancyBricksBlock(RankineBlocks.TEST.get());


    }



    public void horizontalBlock(Block block, ResourceLocation texture) {
        ModelFile model = models().withExistingParent(block.getRegistryName().getPath(), mcLoc("block/cube_all")).texture("all", texture);
        getVariantBuilder(block)
                .partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }

    public ModelBuilder<BlockModelBuilder> rankineOre(String name, String mod, String background, String overlay) {
        return models().withExistingParent(name, modLoc("block/template_rankine_ore")).texture("background", mod+":block/"+background).texture("overlay", "block/"+overlay);
    }
    public ModelBuilder<BlockModelBuilder> geodeBlock(String name) {
        return models().withExistingParent(name, modLoc("block/template_geode")).texture("face", modLoc("block/"+name));
    }
    public ModelBuilder<BlockModelBuilder> pedestalBlock(String name) {
        return models().withExistingParent(name+"_pedestal", modLoc("block/template_pedestal")).texture("all", modLoc("block/"+name+"_block"));
    }

    public void metalPoleBlock(String baseName, ResourceLocation texture) {
        RankineMetalPole block = (RankineMetalPole) ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine", baseName+"_pole"));
        fourWayBlock(block, metalPole(baseName + "_pole", texture), metalPoleSide(baseName + "_pole_side", texture));
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

    public void fiberMatBlock(FiberMatBlock block, ResourceLocation texture) {
        models().withExistingParent(block.getRegistryName().getPath(), mcLoc("block/carpet"))
                .texture("wool", texture);
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
        verticalSlabBlock(block, models().withExistingParent(block.getRegistryName().getPath(), modLoc("block/template_vertical_slab")).texture("side", side).texture("bottom", side).texture("top", side), models().withExistingParent(block.getRegistryName().getPath()+"_top", modLoc("block/template_vertical_slab_top")).texture("side", side).texture("bottom", side).texture("top", side), models().getExistingFile(doubleslab));
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
        ModelFile BRICKS = models().withExistingParent(name+"_bricks", mcLoc("block/cube_all")).texture("all", "block/"+name+"_bricks");
        ModelFile SMALL_BRICKS = models().withExistingParent(name+"_small_bricks", mcLoc("block/cube_all")).texture("all", "block/"+name+"_small_bricks");
        getVariantBuilder(block)
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(BRICKS).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(BRICKS).rotationX(90).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(BRICKS).rotationX(90).rotationY(90).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.SMALL_BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(SMALL_BRICKS).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.SMALL_BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(SMALL_BRICKS).rotationX(90).addModel()
                .partialState().with(RankineStoneBricksBlock.BRICK_TYPE, StoneBricksStates.SMALL_BRICKS).with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(SMALL_BRICKS).rotationX(90).rotationY(90).addModel();
    };


}
