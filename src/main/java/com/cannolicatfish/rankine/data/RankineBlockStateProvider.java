package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.asphalt.BaseAsphaltBlock;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.buildingmodes.BuildingModeBlock;
import com.cannolicatfish.rankine.blocks.groundtap.GroundTapBlock;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankineDoublePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.states.VerticalSlabStates;
import com.cannolicatfish.rankine.blocks.tap.TreeTapBlock;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

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

        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            simpleBlock(Stone.getStone());
            fancyPolishedBlock(Stone.getPolished());
            fancyStoneBricksBlock(Stone.getBricks());
            fancyMossyStoneBricksBlock(Stone.getMossyBricks());
            slabBlock(Stone.getSlab(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            slabBlock(Stone.getPolishedSlab(), new ResourceLocation("rankine", "block/polished_"+Stone.getBaseName()+"1"), new ResourceLocation("rankine", "block/polished_"+Stone.getBaseName()));
            slabBlock(Stone.getBricksSlab(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks1"), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            slabBlock(Stone.getMossyBricksSlab(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks1"), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            stairsBlock(Stone.getStairs(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            stairsBlock(Stone.getPolishedStairs(), new ResourceLocation("rankine", "block/polished_"+Stone.getBaseName()));
            stairsBlock(Stone.getBricksStairs(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            stairsBlock(Stone.getMossyBricksStairs(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            wallBlock(Stone.getWall(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            wallBlock(Stone.getPolishedWall(), new ResourceLocation("rankine", "block/polished_"+Stone.getBaseName()));
            wallBlock(Stone.getBricksWall(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            wallBlock(Stone.getMossyBricksWall(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            pressurePlateBlock(Stone.getPressurePlate(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            pressurePlateBlock(Stone.getBricksPressurePlate(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()+"_bricks"));
            buttonBlock(Stone.getButton(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            cobble(Stone.getCobble());
            columnBlock(Stone.getColumn(), new ResourceLocation("rankine", "block/"+Stone.getBaseName()));
            simpleBlock(Stone.getInfested(),models().withExistingParent(Stone.getInfested().getRegistryName().getPath(), new ResourceLocation("rankine", "block/"+Stone.getBaseName())));
        }

        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            if (Wood.hasLogs()) {
                axisBlock(Wood.getLog(), getBlockRSL(Wood.getLog()), getBlockRSL(Wood.getLog().getRegistryName().getPath() + "_top"));
                axisBlock(Wood.getStrippedLog(), getBlockRSL(Wood.getStrippedLog()), getBlockRSL(Wood.getStrippedLog().getRegistryName().getPath() + "_top"));
                axisBlock(Wood.getWood(), getBlockRSL(Wood.getLog()), getBlockRSL(Wood.getLog().getRegistryName().getPath()));
                axisBlock(Wood.getStrippedWood(), getBlockRSL(Wood.getStrippedLog()), getBlockRSL(Wood.getStrippedLog().getRegistryName().getPath()));
                hollowLog(Wood.getHollowLog(), "rankine");
            }
            fancyPlanksBlock(Wood.getPlanks());
            slabBlock(Wood.getSlab(), getBlockRSL(Wood.getPlanks().getRegistryName().getPath()+"1"), getBlockRSL(Wood.getPlanks()));
            stairsBlock(Wood.getStairs(), getBlockRSL(Wood.getPlanks()));
            fenceBlock(Wood.getFence(), getBlockRSL(Wood.getPlanks()));
            fenceGateBlock(Wood.getFenceGate(), getBlockRSL(Wood.getPlanks()));
            doorBlock(Wood.getDoor(), new ResourceLocation("rankine","block/"+Wood.getDoor().getRegistryName().getPath()+"_bottom"), new ResourceLocation("rankine","block/"+Wood.getDoor().getRegistryName().getPath()+"_top"));
            trapdoorBlock(Wood.getTrapdoor(), getBlockRSL(Wood.getTrapdoor()), true);
            pressurePlateBlock(Wood.getPressurePlate(), getBlockRSL(Wood.getPlanks()));
            buttonBlock(Wood.getButton(), getBlockRSL(Wood.getPlanks()));
            signBlock(Wood.getSign(), getBlockRSL(Wood.getPlanks()));
            signBlock(Wood.getWallSign(), getBlockRSL(Wood.getPlanks()));
            fancyBookshelvesBlock(Wood);
            if (Wood.isTree()) {
                leavesBlock(Wood.getLeaves());
                leafLitterBlock(Wood.getLeafLitter(), "rankine");
                simpleBlock(Wood.getSapling(), models().cross(Wood.getSapling().getRegistryName().getPath(), modLoc("block/" + Wood.getSapling().getRegistryName().getPath())));
                simpleBlock(Wood.getPottedSapling(), models().withExistingParent(Wood.getPottedSapling().getRegistryName().getPath(), "block/flower_pot_cross").texture("plant", "block/" + Wood.getSapling().getRegistryName().getPath()));
            }
        }



        for (Block blk : Stream.of(
                RankineLists.DRIPSTONES,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.SMOOTH_SANDSTONES,
                RankineLists.GAS_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.MINERAL_BLOCKS
            ).flatMap(Collection::stream).collect(Collectors.toList())) {
            simpleBlock(blk);
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.ENDER_SHIRO.get(),
                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.LIGHT_GRAVEL.get(),
                RankineBlocks.DARK_GRAVEL.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.FIRE_CLAY.get(),
                RankineBlocks.PORCELAIN_CLAY.get(),
                RankineBlocks.TINDER_CONK_MUSHROOM_BLOCK.get(),
                RankineBlocks.LIONS_MANE_MUSHROOM_BLOCK.get(),
                RankineBlocks.OYSTER_MUSHROOM_BLOCK.get(),
                RankineBlocks.CINNABAR_POLYPORE_MUSHROOM_BLOCK.get(),
                RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
        )) {
            simpleBlock(blk);
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.HONEY_MUSHROOM_BLOCK.get(),
                RankineBlocks.ARTIST_CONK_MUSHROOM_BLOCK.get(),
                RankineBlocks.SULFUR_SHELF_MUSHROOM_BLOCK.get(),
                RankineBlocks.TURKEY_TAIL_MUSHROOM_BLOCK.get()
        )) {
            cubeTopBottomBLock(blk);
        }

        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            fancyStoneBricksBlock(blk);
        }
        for (Block blk : RankineLists.ELEMENT_BLOCKS) {
            if (blk == RankineBlocks.HELIUM_BLOCK.get() || blk == RankineBlocks.FLUORINE_BLOCK.get() || blk == RankineBlocks.CHLORINE_BLOCK.get() || blk == RankineBlocks.OXYGEN_BLOCK.get() || blk == RankineBlocks.NITROGEN_BLOCK.get() || blk == RankineBlocks.HYDROGEN_BLOCK.get() || blk == RankineBlocks.NEON_BLOCK.get() || blk == RankineBlocks.ARGON_BLOCK.get() || blk == RankineBlocks.KRYPTON_BLOCK.get() || blk == RankineBlocks.RADON_BLOCK.get() || blk == RankineBlocks.XENON_BLOCK.get() || blk == RankineBlocks.OGANESSON_BLOCK.get()) {
                simpleBlock(blk, models().withExistingParent(blk.getRegistryName().getPath(), modLoc("template_solid_gas")).texture("all", getBlockRSL(blk.getRegistryName().getPath())));
            } else {
                simpleBlock(blk);
            }
        }
        for (Block blk : RankineLists.LIGHTNING_GLASSES) {
            getVariantBuilder(blk)
                    .partialState().with(LightningGlassBlock.GLOWING, false).modelForState().modelFile(models().cubeAll(blk.getRegistryName().getPath(), getBlockRSL(blk))).addModel()
                    .partialState().with(LightningGlassBlock.GLOWING, true).modelForState().modelFile(models().cubeAll(blk.getRegistryName().getPath()+"_glowing", getBlockRSL(blk.getRegistryName().getPath()+"_glowing"))).addModel();
        }
        for (Block blk : RankineLists.BRICKS) {
            fancyBricksBlock(blk);
        }
        for (Block SANDSTONE : RankineLists.SANDSTONES) {
            String pathName = SANDSTONE.getRegistryName().getPath();
            simpleBlock(SANDSTONE,models().cubeBottomTop(pathName,blockTexture(SANDSTONE),getBlockRSL(pathName+"_bottom"),getBlockRSL("smooth_"+pathName)));
        }
        for (Block SANDSTONE : RankineLists.CUT_SANDSTONES) {
            String pathName = SANDSTONE.getRegistryName().getPath();
            simpleBlock(SANDSTONE,models().cubeColumn(pathName,blockTexture(SANDSTONE),getBlockRSL(pathName.replace("cut","smooth"))));
        }
        for (Block SANDSTONE : RankineLists.CHISELED_SANDSTONES) {
            String pathName = SANDSTONE.getRegistryName().getPath();
            simpleBlock(SANDSTONE,models().cubeColumn(pathName,blockTexture(SANDSTONE),getBlockRSL(pathName.replace("chiseled","smooth"))));
        }
        for (Block blk : Stream.of(RankineLists.CUT_SANDSTONE_SLABS,RankineLists.SMOOTH_SANDSTONE_SLABS,RankineLists.SANDSTONE_SLABS,RankineLists.MISC_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            slabBlock(blk,false);
        }
        for (Block blk : Stream.of(RankineLists.SMOOTH_SANDSTONE_WALLS,RankineLists.SANDSTONE_WALLS,RankineLists.MISC_WALLS,RankineLists.CONCRETE_WALLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            wallBlock(blk,false);
        }
        for (Block blk : Stream.of(RankineLists.SMOOTH_SANDSTONE_STAIRS,RankineLists.SANDSTONE_STAIRS,RankineLists.MISC_STAIRS,RankineLists.CONCRETE_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            stairsBlock(blk,false);
        }
        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS_SLABS,RankineLists.BRICKS_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            slabBlock(blk,true);
        }
        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS_WALLS,RankineLists.BRICKS_WALL).flatMap(Collection::stream).collect(Collectors.toList())) {
            wallBlock(blk,true);
        }
        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS_STAIRS,RankineLists.BRICKS_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            stairsBlock(blk,true);
        }
        for (Block blk : Stream.of(RankineLists.MUD_BLOCKS,RankineLists.SOIL_BLOCKS,RankineLists.COARSE_SOIL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            rotationBlock(blk);
        }

        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS_PRESSURE_PLATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            String baseStone = Arrays.asList(name.split("_pressure_plate")).get(0);
            pressurePlateBlock((RankineStonePressurePlate) blk, new ResourceLocation("rankine", "block/" + baseStone));
        }

        sixSideCrossBlock(RankineBlocks.LOCUST_SPINE.get());
        sixSideCrossBlock(RankineBlocks.GWIHABAITE_CRYSTAL.get());
        crossBlock(RankineBlocks.STINGING_NETTLE.get());
        axisBlock((RotatedPillarBlock) RankineBlocks.BONE_CHAR_BLOCK.get());
        simpleBlock(RankineBlocks.ALLUVIUM.get(), models().cubeColumn(RankineBlocks.ALLUVIUM.get().getRegistryName().getPath(), blockTexture(RankineBlocks.ALLUVIUM.get()), getBlockRSL("alluvium_end")));
        simpleBlock(RankineBlocks.SAP_CAULDRON.get(), models().withExistingParent(RankineBlocks.SAP_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("sap_still")));
        simpleBlock(RankineBlocks.MAPLE_SAP_CAULDRON.get(), models().withExistingParent(RankineBlocks.MAPLE_SAP_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("maple_sap_still")));
        simpleBlock(RankineBlocks.MAPLE_SYRUP_CAULDRON.get(), models().withExistingParent(RankineBlocks.MAPLE_SYRUP_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("maple_syrup_still")));
        simpleBlock(RankineBlocks.RESIN_CAULDRON.get(), models().withExistingParent(RankineBlocks.RESIN_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("resin_still")));
        simpleBlock(RankineBlocks.LATEX_CAULDRON.get(), models().withExistingParent(RankineBlocks.LATEX_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("latex_still")));
        simpleBlock(RankineBlocks.JUGLONE_CAULDRON.get(), models().withExistingParent(RankineBlocks.JUGLONE_CAULDRON.get().getRegistryName().getPath(), mcLoc("template_cauldron_full")).texture("content", getBlockRSL("juglone_still")));

        for (Block BLK : RankineLists.LANTERNS) {
            lanternBlock(BLK);
        }
        for (Block BLK : Stream.of(RankineLists.SHEETMETALS,RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getVariantBuilder(BLK).partialState().modelForState().modelFile(models().withExistingParent(BLK.getRegistryName().getPath(), modLoc("alloy_sheetmetal"))).addModel();
        }
        //getVariantBuilder(RankineBlocks.ENDER_SHIRO.get()).partialState().modelForState().modelFile(models().cubeBottomTop(RankineBlocks.ENDER_SHIRO.get().getRegistryName().getPath(), getRSL("ender_shiro_side"), getRSL("minecraft","end_stone"), getRSL("ender_shiro_top"))).addModel();

        getVariantBuilder(RankineBlocks.TILLED_SOIL.get()).forAllStates(state -> {
            int MOISTURE = state.getValue(TilledSoilBlock.MOISTURE);
            TilledSoilTypes TYPE = state.getValue(TilledSoilBlock.SOIL_TYPE);
            ModelFile DRY;
            ModelFile MOIST;
            switch (TYPE) {
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
                     DRY = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName(), mcLoc("block/template_farmland")).texture("dirt", getBlockRSL(TYPE.getSerializedName())).texture("top", getBlockRSL(TYPE.getSerializedName()+"_farmland"));
                     MOIST = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName()+"_moist", mcLoc("block/template_farmland")).texture("dirt", getBlockRSL(TYPE.getSerializedName())).texture("top", getBlockRSL(TYPE.getSerializedName()+"_farmland_moist"));
                    break;
                case SOUL_SOIL:
                    DRY = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName(), mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation("minecraft", "block/"+TYPE.getSerializedName())).texture("top", getBlockRSL(TYPE.getSerializedName()+"_farmland"));
                    MOIST = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName()+"_moist", mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation("minecraft", "block/"+TYPE.getSerializedName())).texture("top", getBlockRSL(TYPE.getSerializedName()+"_farmland_moist"));
                    break;
                case DIRT:
                default:
                    DRY = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName(), mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation("minecraft", "block/"+TYPE.getSerializedName())).texture("top", new ResourceLocation("minecraft:block/farmland"));
                    MOIST = models().withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath()+"_"+TYPE.getSerializedName()+"_moist", mcLoc("block/template_farmland")).texture("dirt", new ResourceLocation("minecraft", "block/"+TYPE.getSerializedName())).texture("top", new ResourceLocation("minecraft:block/farmland_moist"));
                    break;
            }

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


        //Rotation blocks
        for (Block blk : Stream.of(RankineLists.ROTATION_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            rotationBlock(blk);
        }


        for (Block BLOCK : RankineLists.EIGHT_LAYER_BLOCKS) {
            String NAME = BLOCK.getRegistryName().getPath();
            ResourceLocation texture = new ResourceLocation("rankine","block/"+NAME);
            getVariantBuilder(BLOCK).forAllStates(state -> {
                int i = state.getValue(RankineEightLayerBlock.LAYERS);
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

        /*
        for (Block BLOCK : RankineLists.STONE_PILLARS) {
            String NAME = BLOCK.getRegistryName().getPath();
            ResourceLocation texture = new ResourceLocation("rankine","block/"+NAME);
            getVariantBuilder(BLOCK).forAllStatesExcept(state -> {
                int i = state.get(PillarBlock.SIZE);
                return ConfiguredModel.builder()
                        .modelFile(models().withExistingParent(NAME+(i),mcLoc("block/block"))
                                .texture("particle", texture)
                                .texture("side", texture)
                                .texture("end", new ResourceLocation("rankine","block/"+NAME+"_end"))
                                .element().from(8-i,0,8-i).to(8+i,16,8+i)
                                .face(Direction.NORTH).uvs(8-i,0, 8+i, 16).texture("#side").end()
                                .face(Direction.EAST).uvs(8-i, 0, 8+i, 16).texture("#side").end()
                                .face(Direction.SOUTH).uvs(8-i, 0, 8+i, 16).texture("#side").end()
                                .face(Direction.WEST).uvs(8-i, 0, 8+i, 16).texture("#side").end()
                                .face(Direction.UP).uvs(8-i, 8-i, 8+i, 8+i).texture("#end").cullface(Direction.UP).end()
                                .face(Direction.DOWN).uvs(8-i, 8-i, 8+i, 8+i).texture("#end").cullface(Direction.DOWN).end()
                                .end()).rotationY(state.get(PillarBlock.AXIS).equals(Direction.Axis.Z) ? 90 : 0).rotationX(state.get(PillarBlock.AXIS).isHorizontal() ? 90 : 0).build();
                },PillarBlock.WATERLOGGED);
        }

         */
        for (Block BLK : RankineLists.POINTED_DRIPSTONES) {
            createPointedDripstone(BLK);
        }


        for (Block CROP : RankineLists.CROPS_SINGLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int i = state.getValue(CropBlock.AGE);
                if (CROP.equals(RankineBlocks.RICE_PLANT.get()) || CROP.equals(RankineBlocks.SOYBEAN_PLANT.get()) || CROP.equals(RankineBlocks.CAMPHOR_BASIL_PLANT.get())) {
                    return ConfiguredModel.builder().modelFile(models().cross(CROP.getRegistryName().getPath()+"_stage"+i,modLoc("block/"+CROP.getRegistryName().getPath()+"_stage"+i))).build();
                } else {
                    return ConfiguredModel.builder().modelFile(models().crop(CROP.getRegistryName().getPath() + "_stage" + i, modLoc("block/" + CROP.getRegistryName().getPath() + "_stage" + i))).build();
                }
            });
        }

        for (Block CROP : RankineLists.CROPS_DOUBLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int AGE = state.getValue(CropBlock.AGE);
                String SECTION = state.getValue(DoubleCropsBlock.SECTION).toString();
                ModelFile MODEL;
                if (SECTION.equals(DoubleBlockHalf.UPPER.toString()) && (AGE==0 || AGE==1)) {
                    MODEL = models().withExistingParent("air",mcLoc("block/air"));
                } else {
                    if (CROP.equals(RankineBlocks.BARLEY_PLANT.get()) || CROP.equals(RankineBlocks.RYE_PLANT.get())) {
                        MODEL = models().crop(CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE));
                    } else {
                        MODEL = models().cross(CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_stage"+AGE));
                    }
                }
                return ConfiguredModel.builder().modelFile(MODEL).build();
            });
        }
        for (Block CROP : RankineLists.CROPS_TRIPLE) {
            getVariantBuilder(CROP).forAllStates(state -> {
                int AGE = state.getValue(CropBlock.AGE);
                String SECTION = state.getValue(TripleCropsBlock.SECTION).toString();
                ModelFile MODEL;
                if (SECTION.equals(TripleBlockSection.TOP.toString()) && (AGE==0 || AGE==1 || AGE==2) || SECTION.equals(TripleBlockSection.MIDDLE.toString()) && (AGE==0 || AGE==1)) {
                    MODEL = models().withExistingParent("air",mcLoc("block/air"));
                } else {
                    MODEL = CROP.equals(RankineBlocks.JUTE_PLANT.get()) || CROP.equals(RankineBlocks.SORGHUM_PLANT.get()) ?
                            models().crop(CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE)) :
                            models().cross(CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE,modLoc("block/"+CROP.getRegistryName().getPath()+"_"+SECTION+"_age"+AGE));
                }
                return ConfiguredModel.builder().modelFile(MODEL).build();
            });
        }


        for (Block BUSH : RankineLists.BUSH_PLANTS) {
            getVariantBuilder(BUSH).forAllStates(state -> {
                int i = state.getValue(RankinePlantBlock.AGE);
                return ConfiguredModel.builder().modelFile(models().cross(BUSH.getRegistryName().getPath()+"_stage"+i,modLoc("block/"+BUSH.getRegistryName().getPath()+"_stage"+i))).build();
            });
        }
        for (Block BUSH : RankineLists.DOUBLE_BUSH_PLANTS) {
            getVariantBuilder(BUSH).forAllStates(state -> {
                int AGE = state.getValue(RankinePlantBlock.AGE);
                String STAGE = state.getValue(RankineDoublePlantBlock.SECTION).toString();
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


        for (Block blk : RankineLists.QUARTER_SLABS) {
            quarterSlab(blk);
        }
        for (Block blk : RankineLists.HOLLOW_LOGS) {
            hollowLog(blk, "minecraft");
        }
        for (Block blk : RankineLists.LEAF_LITTERS) {
            leafLitterBlock(blk, "minecraft");
        }
        /*
        for (Block blk : RankineLists.GAS_TUBES) {
            String PATH = blk.getRegistryName().getPath();
            ModelFile MODEL = models().withExistingParent(PATH, modLoc("block/template_gas_tube")).texture("gas", getBlockRSL(PATH.replace("tube","block")));
            getVariantBuilder(blk)
                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                    .modelForState().modelFile(MODEL).addModel()
                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                    .modelForState().modelFile(MODEL).rotationX(90).addModel()
                    .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                    .modelForState().modelFile(MODEL).rotationX(90).rotationY(90).addModel();
        }

         */


        //Ores
        for (Block blk : Stream.of(RankineLists.NATIVE_ORES, RankineLists.CRUSHING_ORES, RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String regName = blk.getRegistryName().getPath();

            getVariantBuilder(blk).forAllStates(state -> {
                int i = state.getValue(RankineOreBlock.TYPE);
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



        mixingBarrelBlock(RankineBlocks.MIXING_BARREL.get());
        //grassy soils
        for (Block GRASS : RankineLists.GRASS_BLOCKS) {
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(GRASS));
            grassySoilBlock(GRASS, SOIL);
        }
        for (Block PODZOL : RankineLists.PODZOL_BLOCKS) {
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.PODZOL_BLOCKS.indexOf(PODZOL));
            getVariantBuilder(PODZOL)
                    .partialState().with(BlockStateProperties.SNOWY,false).modelForState().modelFile(models().cubeBottomTop(PODZOL.getRegistryName().getPath(), getBlockRSL(PODZOL.getRegistryName().getPath()), getBlockRSL(SOIL.getRegistryName().getPath()),getBlockRSL("minecraft","podzol_top"))).addModel()
                    .partialState().with(BlockStateProperties.SNOWY,true).modelForState().modelFile(new ModelFile.ExistingModelFile(getBlockRSL(SOIL.getRegistryName().getPath()+"_grass_block_snow"),models().existingFileHelper)).addModel();
        }
        for (Block MYCELIUM : RankineLists.MYCELIUM_BLOCKS) {
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.MYCELIUM_BLOCKS.indexOf(MYCELIUM));
            getVariantBuilder(MYCELIUM)
                    .partialState().with(BlockStateProperties.SNOWY,false).modelForState().modelFile(models().cubeBottomTop(MYCELIUM.getRegistryName().getPath(), getBlockRSL(MYCELIUM.getRegistryName().getPath()), getBlockRSL(SOIL.getRegistryName().getPath()),getBlockRSL("minecraft","mycelium_top"))).addModel()
                    .partialState().with(BlockStateProperties.SNOWY,true).modelForState().modelFile(new ModelFile.ExistingModelFile(getBlockRSL(SOIL.getRegistryName().getPath()+"_grass_block_snow"),models().existingFileHelper)).addModel();
        }
        for (Block PATH : RankineLists.PATH_BLOCKS) {
            String regName = PATH.getRegistryName().getPath();
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.PATH_BLOCKS.indexOf(PATH));
            ResourceLocation TOP = new ResourceLocation("rankine","block/"+regName+"_top");
            ResourceLocation SIDE = new ResourceLocation("rankine","block/"+regName+"_side");
            ResourceLocation BOTTOM = new ResourceLocation("rankine","block/"+SOIL.getRegistryName().getPath());
            pathBlock(PATH, new ResourceLocation("minecraft","block/dirt_path_top"), SIDE, BOTTOM);
        }
        pathBlock(RankineBlocks.MYCELIUM_PATH.get(), new ResourceLocation("rankine","block/mycelium_path_top"), new ResourceLocation("rankine","block/mycelium_path_side"), new ResourceLocation("minecraft","block/dirt"));


        for (Block blk : RankineLists.ALLOY_PEDESTALS) {
            pedestalBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_POLES) {
            metalPoleBlock(blk);
        }
        for (Block blk : RankineLists.ALLOY_BARS) {
            tintedBarsBlock((IronBarsBlock) blk,getBlockRSL("alloy_bars1"),getBlockRSL("alloy_bars_edge"));
        }

        for (Block blk : RankineLists.CRUSHING_HEADS) {
            crushingHeadBlock(blk);
        }

        for (Block blk : RankineLists.GEODES) {
            geodeBlock(blk);
        }
        geodeBlock(RankineBlocks.GEODE.get());

        for (Block blk : RankineLists.ELECTROMAGNETS) {
            electroMagnet(blk);
        }
        for (Block blk : RankineLists.LEDS) {
            onOffBlock(blk);
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

        layerBlock(RankineBlocks.CRIMSON_CLOVER.get());
        layerBlock(RankineBlocks.RED_CLOVER.get());
        layerBlock(RankineBlocks.WHITE_CLOVER.get());
        layerBlock(RankineBlocks.YELLOW_CLOVER.get());

        steppingStoneBlock(RankineBlocks.SLATE_STEPPING_STONES.get(), blockTexture(RankineBlocks.SLATE.getStone()));




        getVariantBuilder(RankineBlocks.SHORT_GRASS.get())
                .partialState().modelForState()
                .modelFile(models().withExistingParent(RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"0", mcLoc("block/tinted_cross")).texture("cross", "block/"+RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"0")).weight(2).nextModel()
                .modelFile(models().withExistingParent(RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"1", mcLoc("block/tinted_cross")).texture("cross", "block/"+RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"1")).weight(1).nextModel()
                .modelFile(models().withExistingParent(RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"2", mcLoc("block/tinted_cross")).texture("cross", "block/"+RankineBlocks.SHORT_GRASS.get().getRegistryName().getPath()+"2")).weight(1).addModel();

        for (Block blk : RankineLists.WALL_MUSHROOMS) {
            wallMushroom(blk);
        }
        for (Block blk : RankineLists.ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("asphalt"), getBlockRSL("asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("asphalt"), getBlockRSL(blk.getRegistryName().getPath()));
            }
        }
        for (Block blk : RankineLists.RED_ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.RED_ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("red_asphalt"),getBlockRSL("red_asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("red_asphalt"),getBlockRSL(blk.getRegistryName().getPath().replace("red_","")));
            }
        }
        for (Block blk : RankineLists.GRAY_ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.GRAY_ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("gray_asphalt"),getBlockRSL("gray_asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("gray_asphalt"),getBlockRSL(blk.getRegistryName().getPath().replace("gray_","")));
            }
        }
        for (Block blk : RankineLists.DARK_GRAY_ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.DARK_GRAY_ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("dark_gray_asphalt"),getBlockRSL("dark_gray_asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("dark_gray_asphalt"),getBlockRSL(blk.getRegistryName().getPath().replace("dark_gray_","")));
            }
        }
        for (Block blk : RankineLists.BLUE_ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.BLUE_ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("blue_asphalt"),getBlockRSL("blue_asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("blue_asphalt"),getBlockRSL(blk.getRegistryName().getPath().replace("blue_","")));
            }
        }
        for (Block blk : RankineLists.GREEN_ASPHALT_BLOCKS) {
            if (blk.equals(RankineBlocks.GREEN_ASPHALT.get())) {
                asphaltBlock(blk, getBlockRSL("green_asphalt"),getBlockRSL("green_asphalt"));
            } else {
                asphaltBlock(blk, getBlockRSL("green_asphalt"),getBlockRSL(blk.getRegistryName().getPath().replace("green_","")));
            }
        }


        squareCross(RankineBlocks.WILLOW_BRANCHLET.get());
        squareCross(RankineBlocks.WILLOW_BRANCHLET_PLANT.get());

        for (Block blk : RankineLists.TALL_FLOWERS) {
            doublePlant(blk);
        }
        for (Block blk : RankineLists.GLAZED_PORCELAIN_BLOCKS) {
            getVariantBuilder(blk).forAllStates(state -> ConfiguredModel.builder().modelFile(models().withExistingParent(blk.getRegistryName().getPath()+state.getValue(((BuildingModeBlock) blk).getProperty()), modLoc("template_glazed_porcelain")).texture("overlay", getBlockRSL("glazed_porcelain"+state.getValue(((BuildingModeBlock) blk).getProperty())))).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build());
        }
        triplePlant(RankineBlocks.CORN_STALK.get());
        pillarFour(RankineBlocks.ROPE.get());
        simpleBlock(RankineBlocks.STICK_BLOCK.get(), models().cubeBottomTop(RankineBlocks.STICK_BLOCK.get().getRegistryName().getPath(), getBlockRSL("stick_block_side"), getBlockRSL("stick_block_top"), getBlockRSL("stick_block_top")));


        //MACHINES
        getVariantBuilder(RankineBlocks.GROUND_TAP.get()).forAllStatesExcept(state -> {
            Direction facing = state.getValue(GroundTapBlock.FACING);
            int yRot = (int) facing.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(RankineBlocks.GROUND_TAP.get().getRegistryName().getPath(), modLoc("block/template_ground_tap")).texture("side","block/metal_pipe"))
                    .rotationY(facing.getAxis() == Direction.Axis.Y ? 0 : (yRot+180)%360)
                    .rotationX(facing == Direction.UP ? 0 : facing == Direction.DOWN ? 180 : 90)
                    .build();
        },BlockStateProperties.WATERLOGGED);

        getVariantBuilder(RankineBlocks.TREE_TAP.get())
                .forAllStates(state -> {
                    boolean CONNECTED = state.getValue(TreeTapBlock.CONNECTED);
                    return ConfiguredModel.builder().modelFile(CONNECTED ? models().getExistingFile(getBlockRSL("tree_tap_connected")) : models().getExistingFile(getBlockRSL("tree_tap"))).rotationY(((int) state.getValue(TreeTapBlock.FACING).toYRot()+180)%360).build();
                });

        getVariantBuilder(RankineBlocks.FLOOD_GATE.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(models().cubeAll(RankineBlocks.FLOOD_GATE.get().getRegistryName().getPath(), getBlockRSL(RankineBlocks.FLOOD_GATE.get().getRegistryName().getPath()))).build(), BlockStateProperties.WATERLOGGED);


        //simpleBlock(RankineBlocks.LASER_QUARRY.get());
        directionalBlock(RankineBlocks.GAS_VENT.get(),models().cubeColumnHorizontal(RankineBlocks.GAS_VENT.get().getRegistryName().getPath(),getBlockRSL("gas_vent_side"),getBlockRSL("gas_vent_end")));
        directionalBlock(RankineBlocks.GAS_BOTTLER.get(),models().orientableVertical(RankineBlocks.GAS_BOTTLER.get().getRegistryName().getPath(),getBlockRSL("gas_bottler_side"),getBlockRSL("gas_bottler_end")));
        cobBlock(RankineBlocks.COB.get());
        ornamentBlock(RankineBlocks.ORNAMENT.get());
        simpleBlock(RankineBlocks.DISTILLATION_TOWER.get());
        simpleBlock(RankineBlocks.AIR_DISTILLATION_PACKING.get());
        simpleBlock(RankineBlocks.REACTION_CHAMBER_CORE.get());
        simpleBlock(RankineBlocks.DIAMOND_ANVIL_CELL.get());
        simpleBlock(RankineBlocks.PARTICLE_ACCELERATOR.get());
        simpleBlock(RankineBlocks.SEDIMENT_FAN.get());
        simpleBlock(RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get());
        simpleBlock(RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get());
        simpleBlock(RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get());
        simpleBlock(RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get());
        directionalBlock(RankineBlocks.HEATING_ELEMENT_1.get(),models().cubeBottomTop("heating_element_1",getBlockRSL("heating_element_side"), getBlockRSL("heating_element_bottom"), getBlockRSL("heating_element_top")));
        simpleBlock(RankineBlocks.PCF.get(), models().orientable(RankineBlocks.PCF.get().getRegistryName().getPath(), getBlockRSL("pcf_side"), getBlockRSL("pcf_front"), getBlockRSL("pcf_top")));
        simpleBlock(RankineBlocks.BOTANIST_STATION.get(), models().orientable(RankineBlocks.BOTANIST_STATION.get().getRegistryName().getPath(), getBlockRSL("botanist_station_side"), getBlockRSL("botanist_station_front"), getBlockRSL("botanist_station_top")));
        simpleBlock(RankineBlocks.TEMPLATE_TABLE.get(), models().orientable(RankineBlocks.TEMPLATE_TABLE.get().getRegistryName().getPath(), getBlockRSL("template_table_side"), getBlockRSL("template_table_front"), getBlockRSL("template_table_top")));
        simpleBlock(RankineBlocks.MATERIAL_TESTING_TABLE.get(), models().orientable(RankineBlocks.MATERIAL_TESTING_TABLE.get().getRegistryName().getPath(), getBlockRSL("material_testing_table_side"), getBlockRSL("material_testing_table_front"), getBlockRSL("material_testing_table_top")));
        simpleBlock(RankineBlocks.EVAPORATION_TOWER.get(), models().cubeBottomTop(RankineBlocks.EVAPORATION_TOWER.get().getRegistryName().getPath(), getBlockRSL("evaporation_tower_side"), getBlockRSL("evaporation_tower_bottom"), getBlockRSL("evaporation_tower_top")));
        onOffBlock(RankineBlocks.CHARCOAL_PIT.get(), models().cubeBottomTop(RankineBlocks.CHARCOAL_PIT.get().getRegistryName().getPath(), getBlockRSL("charcoal_pit_side"), getBlockRSL("charcoal_pit_top"), getBlockRSL("charcoal_pit_top")), models().cubeBottomTop(RankineBlocks.CHARCOAL_PIT.get().getRegistryName().getPath()+"_on", getBlockRSL("charcoal_pit_side"), getBlockRSL("charcoal_pit_top_on"), getBlockRSL("charcoal_pit_top_on")));
        onOffBlock(RankineBlocks.BEEHIVE_OVEN_PIT.get(), models().cubeTop(RankineBlocks.BEEHIVE_OVEN_PIT.get().getRegistryName().getPath(), new ResourceLocation("rankine", "block/refractory_bricks"), new ResourceLocation("rankine", "block/beehive_oven")), models().cubeTop(RankineBlocks.BEEHIVE_OVEN_PIT.get().getRegistryName().getPath()+"_on", new ResourceLocation("rankine", "block/refractory_bricks"), new ResourceLocation("rankine", "block/beehive_oven_on")));
        onOffBlock(RankineBlocks.ALLOY_FURNACE.get(), models().cubeBottomTop(RankineBlocks.ALLOY_FURNACE.get().getRegistryName().getPath(), getBlockRSL("alloy_furnace_front"), getBlockRSL("refractory_bricks"), getBlockRSL("alloy_furnace_top")), models().cubeBottomTop(RankineBlocks.ALLOY_FURNACE.get().getRegistryName().getPath()+"_on", getBlockRSL("alloy_furnace_front_on"), getBlockRSL("refractory_bricks"), getBlockRSL("alloy_furnace_top")));
        rotateableMachineBlock(RankineBlocks.INDUCTION_FURNACE.get(), models().orientable(RankineBlocks.INDUCTION_FURNACE.get().getRegistryName().getPath(), getBlockRSL("induction_furnace_side"), getBlockRSL("induction_furnace_front"), getBlockRSL("induction_furnace_top")), models().orientable(RankineBlocks.INDUCTION_FURNACE.get().getRegistryName().getPath()+"_on", getBlockRSL("induction_furnace_side"), getBlockRSL("induction_furnace_front"), getBlockRSL("induction_furnace_top_on")));
        rotateableMachineBlock(RankineBlocks.FUSION_FURNACE.get(), models().orientable(RankineBlocks.FUSION_FURNACE.get().getRegistryName().getPath(), getBlockRSL("fusion_furnace_side"), getBlockRSL("fusion_furnace_front"), getBlockRSL("fusion_furnace_top")), models().orientable(RankineBlocks.FUSION_FURNACE.get().getRegistryName().getPath()+"_on", getBlockRSL("fusion_furnace_side"), getBlockRSL("fusion_furnace_front_on"), getBlockRSL("fusion_furnace_top")));
        simpleBlock(RankineBlocks.PISTON_CRUSHER.get());
        simpleBlock(RankineBlocks.GYRATORY_CRUSHER.get());

        SVLBlock((SodiumVaporLampBlock) RankineBlocks.SODIUM_VAPOR_LAMP.get());




        //asphaltBlock(RankineBlocks.MANHOLE.get());
        /*
        getVariantBuilder(RankineBlocks.POTHOLE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.POTHOLE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getBlockRSL("asphalt/asphalt_age0"))
                .texture("side", getBlockRSL("asphalt/asphalt_age0"))
                .texture("top", getBlockRSL("asphalt/pothole"))
                .element().from(0.0f,0.0f,0.0f).to(16.0f,12.0f,16.0f)
                .face(Direction.NORTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.EAST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 4, 16, 16).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                .end()).addModel();

         */

        getVariantBuilder(RankineBlocks.TRAMPOLINE.get()).partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.TRAMPOLINE.get().getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getBlockRSL("trampoline"))
                .texture("side", getBlockRSL("trampoline_side"))
                .texture("top", getBlockRSL("trampoline"))
                .element().from(0.0f,12.0f,0.0f).to(16.0f,14.0f,16.0f)
                .face(Direction.NORTH).uvs(0,2, 16, 4).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.EAST).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.EAST).end()
                .face(Direction.SOUTH).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0, 2, 16, 4).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#side").cullface(Direction.DOWN).end()
                .end()).addModel();

        ModelFile STUMP0 = models().withExistingParent("stump0", modLoc("block/template_stump0")).texture("side", getBlockRSL("stump_side")).texture("top", getBlockRSL("stump_top"));
        ModelFile STUMP1 = models().withExistingParent("stump1", modLoc("block/template_stump1")).texture("side", getBlockRSL("stump_side")).texture("top", getBlockRSL("stump_top"));
        ModelFile STUMP2 = models().withExistingParent("stump2", modLoc("block/template_stump2")).texture("side", getBlockRSL("stump_side")).texture("top", getBlockRSL("stump_top"));
        getVariantBuilder(RankineBlocks.STUMP.get())
                .partialState().modelForState().modelFile(STUMP0).rotationY(0).nextModel().modelFile(STUMP0).rotationY(90).nextModel().modelFile(STUMP0).rotationY(180).nextModel().modelFile(STUMP0).rotationY(270)
                .modelFile(STUMP1).rotationY(0).nextModel().modelFile(STUMP1).rotationY(90).nextModel().modelFile(STUMP1).rotationY(180).nextModel().modelFile(STUMP1).rotationY(270)
                .modelFile(STUMP2).rotationY(0).nextModel().modelFile(STUMP2).rotationY(90).nextModel().modelFile(STUMP2).rotationY(180).nextModel().modelFile(STUMP2).rotationY(270).addModel();

        getVariantBuilder(RankineBlocks.SOD_BLOCK.get())
                .partialState().modelForState().modelFile(models().withExistingParent(RankineBlocks.SOD_BLOCK.get().getRegistryName().getPath(), mcLoc("block/block"))
                    .texture("particle", getBlockRSL("sod_block"))
                    .texture("all", getBlockRSL("sod_block"))
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,16.0f)
                    .face(Direction.NORTH).texture("#all").tintindex(0).cullface(Direction.NORTH).end()
                    .face(Direction.EAST).texture("#all").tintindex(0).cullface(Direction.EAST).end()
                    .face(Direction.SOUTH).texture("#all").tintindex(0).cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).texture("#all").tintindex(0).cullface(Direction.WEST).end()
                    .face(Direction.UP).texture("#all").tintindex(0).cullface(Direction.UP).end()
                    .face(Direction.DOWN).texture("#all").tintindex(0).cullface(Direction.DOWN).end()
                    .end()).addModel();




        for (Block BALE : RankineLists.BALES) {
            axisBlock((RotatedPillarBlock) BALE, getBlockRSL(BALE.getRegistryName().getPath()+"_side"),getBlockRSL(BALE.getRegistryName().getPath()+"_top"));
        }

    }

    public static ResourceLocation getBlockRSL(Block blk) {
        return getBlockRSL(blk.getRegistryName().getPath());
    }
    public static ResourceLocation getBlockRSL(String textureName) {
        return new ResourceLocation("rankine","block/"+textureName);
    }
    public static ResourceLocation getBlockRSL(String namespace, String textureName) {
        return new ResourceLocation(namespace,"block/"+textureName);
    }


    public void fluidBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(name, mcLoc("block/block")).texture("particle", getBlockRSL(name+"_still"))).addModel();
    }

    public void signBlock(Block blk, ResourceLocation plank) {
        String name = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(name, modLoc("block/template_sign")).texture("particle", plank)).addModel();
    }

    public void hollowLog(Block blk, String nameSpace) {
        String PATH = blk.getRegistryName().getPath();
        ModelFile MODEL = models().withExistingParent(PATH, modLoc("block/template_hollow_log")).texture("log", getBlockRSL(nameSpace,PATH.replace("hollow_",""))).texture("log_top", getBlockRSL(nameSpace,PATH.replace("hollow_","")+"_top")).texture("stripped_log", getBlockRSL(nameSpace,PATH.replace("hollow_","stripped_")));
        ModelFile MODEL_MOSSY = models().withExistingParent(PATH+"_mossy", modLoc("block/template_hollow_log_mossy")).texture("log", getBlockRSL(nameSpace,PATH.replace("hollow_",""))).texture("log_top", getBlockRSL(nameSpace,PATH.replace("hollow_","")+"_top")).texture("stripped_log", getBlockRSL(nameSpace,PATH.replace("hollow_","stripped_")));
        getVariantBuilder(blk)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y).with(HollowLogBlock.MOSSY, false).modelForState().modelFile(MODEL).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y).with(HollowLogBlock.MOSSY, true).modelForState().modelFile(MODEL).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z).with(HollowLogBlock.MOSSY, false).modelForState().modelFile(MODEL).rotationX(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z).with(HollowLogBlock.MOSSY, true).modelForState().modelFile(MODEL_MOSSY).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X).with(HollowLogBlock.MOSSY, false).modelForState().modelFile(MODEL).rotationX(90).rotationY(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X).with(HollowLogBlock.MOSSY, true).modelForState().modelFile(MODEL_MOSSY).rotationY(90).addModel();

    }
    public void leafLitterBlock(Block blk, String nameSpace) {
        String PATH = blk.getRegistryName().getPath();
        getVariantBuilder(blk).partialState().modelForState()
                .modelFile(models().withExistingParent(PATH, mcLoc("block/block"))
                        .texture("particle", getBlockRSL(nameSpace,PATH.replace("leaf_litter","leaves")))
                        .texture("layer", getBlockRSL(nameSpace,PATH.replace("leaf_litter","leaves")))
                        .element().from(0.0f,0.25f,0.0f).to(16.0f,0.25f,16.0f)
                        .face(Direction.UP).uvs(0, 0, 16,16).tintindex(0).texture("#layer").end()
                        .face(Direction.DOWN).uvs(16, 16, 0, 0).tintindex(0).texture("#layer").end()
                        .end())
                .addModel();
    }

    public void cobble(Block BLK) {
        String PATH = BLK.getRegistryName().getPath();
        ModelFile model = models().withExistingParent(PATH+"1", modLoc("block/template_cobble1")).texture("all", getBlockRSL(PATH.replace("_cobble","")));
        ModelFile model2 = models().withExistingParent(PATH+"2", modLoc("block/template_cobble2")).texture("all", getBlockRSL(PATH.replace("_cobble","")));
        ModelFile model3 = models().withExistingParent(PATH+"3", modLoc("block/template_cobble3")).texture("all", getBlockRSL(PATH.replace("_cobble","")));
        getVariantBuilder(BLK).partialState().modelForState()
                .modelFile(model).rotationY(0).nextModel()
                .modelFile(model).rotationY(90).nextModel()
                .modelFile(model).rotationY(180).nextModel()
                .modelFile(model).rotationY(270).nextModel()
                .modelFile(model2).rotationY(0).nextModel()
                .modelFile(model2).rotationY(90).nextModel()
                .modelFile(model2).rotationY(180).nextModel()
                .modelFile(model2).rotationY(270).nextModel()
                .modelFile(model3).rotationY(0).nextModel()
                .modelFile(model3).rotationY(90).nextModel()
                .modelFile(model3).rotationY(180).nextModel()
                .modelFile(model3).rotationY(270).addModel();
    }

    public void quarterSlab(Block BLK) {
        String path = BLK.getRegistryName().getPath();
        getVariantBuilder(BLK).forAllStates(state -> {
            int SIZE = state.getValue(QuarterSlabBlock.SIZE);

            return ConfiguredModel.builder()
                    .modelFile(
                    !state.getValue(QuarterSlabPoleBlock.POLE) ?
                        models().withExistingParent("block/"+path+"_size"+SIZE, mcLoc("block/block"))
                        .texture("particle", getBlockRSL(path.replace("_slab","")))
                        .texture("all", getBlockRSL(path.replace("_slab","")))
                        .element().from(0.0f,0.0f,0.0f).to(16.0f,4*SIZE,16.0f)
                            .face(Direction.NORTH).uvs(0,16-4*SIZE, 16, 16).texture("#all").cullface(Direction.NORTH).end()
                            .face(Direction.EAST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.EAST).end()
                            .face(Direction.SOUTH).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.SOUTH).end()
                            .face(Direction.WEST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.WEST).end()
                            .face(Direction.UP).uvs(0, 0, 16, 16).texture("#all").end()
                            .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(Direction.DOWN).end()
                            .end() :
                    models().withExistingParent("block/"+path+"_pole_size"+SIZE, mcLoc("block/block"))
                        .texture("particle", getBlockRSL(path.replace("_slab","")))
                        .texture("all", getBlockRSL(path.replace("_slab","")))
                        .element().from(0.0f,0.0f,0.0f).to(16.0f,4*SIZE,16.0f)
                            .face(Direction.NORTH).uvs(0,16-4*SIZE, 16, 16).texture("#all").cullface(Direction.NORTH).end()
                            .face(Direction.EAST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.EAST).end()
                            .face(Direction.SOUTH).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.SOUTH).end()
                            .face(Direction.WEST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.WEST).end()
                            .face(Direction.UP).uvs(0, 0, 16, 16).texture("#all").end()
                            .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(Direction.DOWN).end()
                            .end()
                        .element().from(3.0f,4*SIZE,3.0f).to(13.0f,16.0f,13.0f)
                            .face(Direction.NORTH).uvs(3,0, 13, 16-4*SIZE).texture("#all").cullface(Direction.NORTH).end()
                            .face(Direction.EAST).uvs(3, 0, 13, 16-4*SIZE).texture("#all").cullface(Direction.EAST).end()
                            .face(Direction.SOUTH).uvs(3, 0, 13, 16-4*SIZE).texture("#all").cullface(Direction.SOUTH).end()
                            .face(Direction.WEST).uvs(3, 0, 13, 16-4*SIZE).texture("#all").cullface(Direction.WEST).end()
                            .face(Direction.UP).uvs(3, 4, 13, 13).texture("#all").end()
                            .face(Direction.DOWN).uvs(3, 4, 13, 13).texture("#all").cullface(Direction.DOWN).end()
                            .end()
                    )
                    .build();
        });


    }

    public void steppingStoneBlock(Block BLK, ResourceLocation texture) {
        ModelFile MODEL0 = models().withExistingParent(BLK.getRegistryName().getPath()+"0", modLoc("block/template_stepping_stones0")).texture("all", texture);
        ModelFile MODEL1 = models().withExistingParent(BLK.getRegistryName().getPath()+"1", modLoc("block/template_stepping_stones1")).texture("all", texture);
        ModelFile MODEL2 = models().withExistingParent(BLK.getRegistryName().getPath()+"2", modLoc("block/template_stepping_stones2")).texture("all", texture);
        ModelFile MODEL3 = models().withExistingParent(BLK.getRegistryName().getPath()+"3", modLoc("block/template_stepping_stones3")).texture("all", texture);
        getVariantBuilder(BLK)
                .partialState().modelForState()
                .modelFile(MODEL0).rotationY(0).weight(1).nextModel()
                .modelFile(MODEL0).rotationY(90).weight(1).nextModel()
                .modelFile(MODEL0).rotationY(180).weight(1).nextModel()
                .modelFile(MODEL0).rotationY(270).weight(1).nextModel()
                .modelFile(MODEL1).rotationY(0).weight(2).nextModel()
                .modelFile(MODEL1).rotationY(90).weight(2).nextModel()
                .modelFile(MODEL1).rotationY(180).weight(2).nextModel()
                .modelFile(MODEL1).rotationY(270).weight(2).nextModel()
                .modelFile(MODEL2).rotationY(0).weight(1).nextModel()
                .modelFile(MODEL2).rotationY(90).weight(1).nextModel()
                .modelFile(MODEL2).rotationY(180).weight(1).nextModel()
                .modelFile(MODEL2).rotationY(270).weight(1).nextModel()
                .modelFile(MODEL3).rotationY(0).weight(1).nextModel()
                .modelFile(MODEL3).rotationY(90).weight(1).nextModel()
                .modelFile(MODEL3).rotationY(180).weight(1).nextModel()
                .modelFile(MODEL3).rotationY(270).weight(1).addModel();

    }

    public void createPointedDripstone(Block blk) {
        String path = blk.getRegistryName().getPath();
        getVariantBuilder(blk).forAllStatesExcept(state -> {
            String thickness = state.getValue(PointedDripstoneBlock.THICKNESS).toString();
            String direction = state.getValue(PointedDripstoneBlock.TIP_DIRECTION).toString();
            return ConfiguredModel.builder()
                    .modelFile(models().cross(path+"_"+direction+"_"+thickness,getBlockRSL(path+"_"+direction+"_"+thickness))).build();
        },BlockStateProperties.WATERLOGGED);
    }



    public void asphaltBlock(Block blk, ResourceLocation textureBase, ResourceLocation textureOverlay) {
        String path = blk.getRegistryName().getPath();
        getVariantBuilder(blk).forAllStates(state -> {
            int rot = (int) state.getValue(HorizontalDirectionalBlock.FACING).toYRot();
            int SIZE = state.getValue(BaseAsphaltBlock.SIZE);

            return ConfiguredModel.builder()
                .modelFile(models().withExistingParent("block/"+path+"_size"+SIZE, mcLoc("block/block"))
                .texture("particle", textureBase)
                .texture("all", textureBase)
                .texture("overlay", textureOverlay)
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,4*SIZE,16.0f)
                    .face(Direction.NORTH).uvs(0,16-4*SIZE, 16, 16).texture("#all").cullface(Direction.NORTH).end()
                    .face(Direction.EAST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.EAST).end()
                    .face(Direction.SOUTH).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).uvs(0, 16-4*SIZE, 16, 16).texture("#all").cullface(Direction.WEST).end()
                    .face(Direction.UP).uvs(0, 0, 16, 16).texture("#all").end()
                    .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#all").cullface(Direction.DOWN).end()
                    .end()
                    .element().from(0.0f,0.0f,0.0f).to(16.0f,4*SIZE,16.0f)
                    .face(Direction.NORTH).uvs(0,16-4*SIZE, 16, 16).texture("#overlay").cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).uvs(0, 16-4*SIZE, 16, 16).texture("#overlay").cullface(Direction.SOUTH).end()
                    .face(Direction.UP).uvs(0, 0, 16, 16).texture("#overlay").end()
                    .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#overlay").cullface(Direction.DOWN).end()
                    .end())
                    .rotationY(rot)
                    .build();
        });
    }
    public void wallMushroom(Block BLK) {
        ModelFile Mush1 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"1"), models().existingFileHelper);
        ModelFile Mush2 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"2"), models().existingFileHelper);
        ModelFile Mush3 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"3"), models().existingFileHelper);
        getVariantBuilder(BLK).forAllStates(blockState -> {
            Direction dir = blockState.getValue(RankineWallMushroomBlock.HORIZONTAL_FACING);
            int rotY = (int) dir.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(Mush1).rotationY(rotY).nextModel()
                    .modelFile(Mush2).rotationY(rotY).nextModel()
                    .modelFile(Mush3).rotationY(rotY).build();
        });
        /*
        ModelFile Mush1 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"1"), models().existingFileHelper);
        ModelFile Mush2 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"2"), models().existingFileHelper);
        ModelFile Mush3 = new ModelFile.ExistingModelFile(getBlockRSL(BLK.getRegistryName().getPath()+"3"), models().existingFileHelper);
        getVariantBuilder(BLK).forAllStates(blockState -> {
            Direction dir = blockState.get(RankineWallMushroomBlock.FACING);
            int rotY = dir.getHorizontalIndex() == -1 ? 0 : (int) dir.getHorizontalAngle();
            int rotX = dir.equals(Direction.UP) ? -90 : blockState.get(RankineWallMushroomBlock.FACING).equals(Direction.DOWN) ? 90 : 0;
            return ConfiguredModel.builder()
                    .modelFile(Mush1).rotationX(rotX).rotationY(rotY).nextModel()
                    .modelFile(Mush2).rotationX(rotX).rotationY(rotY).nextModel()
                    .modelFile(Mush3).rotationX(rotX).rotationY(rotY).build();
        });

         */
    }

    public void sixSideCrossBlock(Block BLK) {
        ModelFile CROSS = models().withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/template_cross")).texture("all", "block/"+BLK.getRegistryName().getPath());
        getVariantBuilder(BLK)
                .partialState().with(BlockStateProperties.FACING, Direction.UP).modelForState().modelFile(CROSS).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN).modelForState().modelFile(CROSS).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH).modelForState().modelFile(CROSS).rotationY(0).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST).modelForState().modelFile(CROSS).rotationY(90).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH).modelForState().modelFile(CROSS).rotationY(180).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST).modelForState().modelFile(CROSS).rotationY(270).rotationX(90).addModel();
    }
    public void crossBlock(Block BLK) {
        getVariantBuilder(BLK).partialState().modelForState().modelFile(models().withExistingParent(BLK.getRegistryName().getPath(), mcLoc("block/cross")).texture("cross", "block/"+BLK.getRegistryName().getPath())).addModel();
    }
    public void squareCross(Block BLK) {
        getVariantBuilder(BLK).partialState().modelForState().modelFile(models().withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/template_square")).texture("all", "block/"+BLK.getRegistryName().getPath())).addModel();
    }
    public void cropBlock(Block BLK) {
        getVariantBuilder(BLK).partialState().modelForState().modelFile(models().withExistingParent(BLK.getRegistryName().getPath(), mcLoc("block/crop")).texture("crop", "block/"+BLK.getRegistryName().getPath())).addModel();
    }
    public void tintedCrossBlock(Block BLK, ResourceLocation base, ResourceLocation overlay) {
        getVariantBuilder(BLK).partialState().modelForState().modelFile(models().withExistingParent(BLK.getRegistryName().getPath(), getBlockRSL("template_tinted_cross_overlay")).texture("cross",base).texture("overlay", overlay)).addModel();
    }
    public void leavesBlock(Block BLK) {
        String PATH = BLK.getRegistryName().getPath();
        getVariantBuilder(BLK)
            .partialState().with(RankineLeavesBlock.AGE, 0).modelForState().modelFile(models().withExistingParent(PATH +"_age0", getBlockRSL("minecraft","leaves")).texture("all", getBlockRSL(PATH))).addModel()
            .partialState().with(RankineLeavesBlock.AGE, 1).modelForState().modelFile(models().withExistingParent(PATH +"_age1", getBlockRSL("minecraft","leaves")).texture("all", getBlockRSL(PATH))).addModel()
            .partialState().with(RankineLeavesBlock.AGE, 2).modelForState().modelFile(models().withExistingParent(PATH +"_age3", getBlockRSL("leaves_age5")).texture("leaves", getBlockRSL(PATH)).texture("snowy_overlay", getBlockRSL("snowy_leaves_overlay"))).addModel();
    }
    public void layerBlock(Block BLK) {
        ModelFile model = models().withExistingParent(BLK.getRegistryName().getPath(), mcLoc("block/block"))
                .texture("particle", getBlockRSL(BLK.getRegistryName().getPath()))
                .texture("layer", getBlockRSL(BLK.getRegistryName().getPath()))
                .element().from(0.0f,0.25f,0.0f).to(16.0f,0.25f,16.0f)
                .face(Direction.UP).uvs(16, 16, 0,0).texture("#layer").end()
                .face(Direction.DOWN).uvs(16, 0, 0, 16).texture("#layer").end()
                .end();
        getVariantBuilder(BLK).partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }



    public void electroMagnet(Block block) {
        String name = block.getRegistryName().getPath();
        ModelFile off = models().cubeTop(name, getBlockRSL(name+"_side"), getBlockRSL(name));
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(off)
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + 180) % 360)
                            .build();
                },ElectromagnetBlock.POWERED,ElectromagnetBlock.REPULSION_MODE);
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
    public void grassySoilBlock(Block grass, Block soil) {
        String grassyName = grass.getRegistryName().getPath();
        String soilName = soil.getRegistryName().getPath();
        ModelFile grassy = models().withExistingParent(grassyName, modLoc("block/template_grassy_soil")).texture("soil", new ResourceLocation("rankine", "block/" + soilName)).texture("grassy_soil_top", getBlockRSL("minecraft","grass_block_top")).texture("grassy_soil_side", new ResourceLocation("rankine", "block/" + grassyName + "_side")).texture("grassy_soil_side_overlay", new ResourceLocation("rankine", "block/" + grassyName + "_side_overlay"));
        ModelFile snowy = models().withExistingParent(grassyName+"_snow", mcLoc("block/cube_bottom_top")).texture("top", getBlockRSL("minecraft","grass_block_top")).texture("bottom", new ResourceLocation("rankine", "block/" + soilName)).texture("side", new ResourceLocation("rankine", "block/"+grassyName+"_snow"));
        getVariantBuilder(grass)
                .partialState().with(GrassBlock.SNOWY, false).with(GrassySoilBlock.DEAD,true).modelForState().modelFile(grassy).rotationY(0).nextModel().modelFile(grassy).rotationY(90).nextModel().modelFile(grassy).rotationY(180).nextModel().modelFile(grassy).rotationY(270).addModel()
                .partialState().with(GrassBlock.SNOWY, false).with(GrassySoilBlock.DEAD,false).modelForState().modelFile(grassy).rotationY(0).nextModel().modelFile(grassy).rotationY(90).nextModel().modelFile(grassy).rotationY(180).nextModel().modelFile(grassy).rotationY(270).addModel()
                .partialState().with(GrassBlock.SNOWY, true).with(GrassySoilBlock.DEAD,false).modelForState().modelFile(snowy).addModel()
                .partialState().with(GrassBlock.SNOWY, true).with(GrassySoilBlock.DEAD,true).modelForState().modelFile(snowy).addModel();
    }

    public void rotationBlock(Block blk) {
        ModelFile model = cubeAll(blk);
        getVariantBuilder(blk)
                .partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }

    public void cobBlock(Block blk) {
        ModelFile model = models().getExistingFile(getBlockRSL("cob"));
        getVariantBuilder(blk)
                .partialState().modelForState().modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270).addModel();
    }

    public void ornamentBlock(Block blk) {
        getVariantBuilder(blk).forAllStatesExcept(state -> {
            int style = state.getValue(OrnamentBlock.STYLE);
            ModelFile model = models().getExistingFile(getBlockRSL("ornament"+style));
            return ConfiguredModel.builder()
                .modelFile(model).rotationY(0).nextModel().modelFile(model).rotationY(90).nextModel().modelFile(model).rotationY(180).nextModel().modelFile(model).rotationY(270)
                .build();
        },BlockStateProperties.WATERLOGGED);



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
        getVariantBuilder(blk).forAllStates(state -> ConfiguredModel.builder().modelFile(blkModel).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build());
    }
    public void fiberMatBlock(FiberMatBlock blk, ResourceLocation texture) {
        String NAME = blk.getRegistryName().getPath();
        ModelFile blkModel = models().withExistingParent(NAME, mcLoc("block/carpet")).texture("wool", texture);
        getVariantBuilder(blk).forAllStates(state -> ConfiguredModel.builder().modelFile(blkModel).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()).build());
    }
    public void geodeBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        ModelFile MODEL;
        if (blk.equals(RankineBlocks.GEODE.get())) {
            MODEL = models().withExistingParent(name, modLoc("block/template_geode"));
        } else {
            MODEL = models().withExistingParent(name, modLoc("block/template_cut_geode")).texture("face", modLoc("block/"+name));
        }

        getVariantBuilder(blk).forAllStatesExcept(state -> {
            Direction dir = state.getValue(GeodeBlock.HORIZONTAL_FACING);
            return ConfiguredModel.builder()
                    .modelFile(MODEL)
                    .rotationY(((dir.get2DDataValue() + 2) % 4) * 90)
                    .build();
        },BlockStateProperties.WATERLOGGED);
    }
    public void crushingHeadBlock(Block blk) {
        String name = blk.getRegistryName().getPath();
        ModelFile MODEL = models().withExistingParent(name, modLoc("block/template_crushing_head")).texture("all", blockTexture(blk));
        directionalBlock(blk, MODEL);
    }
    public void pedestalBlock(Block blk) {
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_pedestal")).texture("all", modLoc("block/"+blk.getRegistryName().getPath().replace("_pedestal","_block")))).addModel();
    }
    public void pathBlock(Block blk, ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        getVariantBuilder(blk).partialState().modelForState().modelFile(models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_path_block")).texture("top", top).texture("side", side).texture("bottom", bottom)).addModel();
    }

    public void metalPoleBlock(Block BLK) {
        String path = BLK.getRegistryName().getPath();

        getVariantBuilder(BLK)
                .partialState().with(MetalPoleBlock.STYLE,0).modelForState().modelFile(models().withExistingParent(path+"0", modLoc("block/template_metal_pole")).texture("side", getBlockRSL("template_pole"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,1).modelForState().modelFile(models().withExistingParent(path+"1", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole1"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,2).modelForState().modelFile(models().withExistingParent(path+"2", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole2"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,3).modelForState().modelFile(models().withExistingParent(path+"3", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole3"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,4).modelForState().modelFile(models().withExistingParent(path+"4", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole4"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,5).modelForState().modelFile(models().withExistingParent(path+"5", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole5"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,6).modelForState().modelFile(models().withExistingParent(path+"6", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole6"))).addModel()
                .partialState().with(MetalPoleBlock.STYLE,7).modelForState().modelFile(models().withExistingParent(path+"7", modLoc("block/template_metal_pole_overlay")).texture("side", getBlockRSL("template_pole")).texture("overlay", getBlockRSL("metal_pole7"))).addModel();
    }

    public void tintedBarsBlock(IronBarsBlock block, ResourceLocation pane, ResourceLocation edge) {
        tintedBarsBlockInternal(block, block.getRegistryName().toString(), pane, edge);
    }
    private void tintedBarsBlockInternal(IronBarsBlock block, String baseName, ResourceLocation pane, ResourceLocation edge) {
        ModelFile post = tintedBarsPost(baseName + "_post", pane);
        ModelFile postEnds = tintedBarsPostEnds(baseName + "_post_ends", edge);
        ModelFile side = tintedBarsSide(baseName + "_side", pane, edge);
        ModelFile sideAlt = tintedBarsSideAlt(baseName + "_side_alt", pane, edge);
        ModelFile cap = tintedBarsCap(baseName + "_cap", pane);
        ModelFile capAlt = tintedBarsCapAlt(baseName + "_cap_alt", pane);
        tintetBarsBlock(block, post, postEnds, side, sideAlt, cap, capAlt);
    }
    public ModelFile tintedBarsPost(String name, ResourceLocation bars) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_post")).texture("bars", bars);
    }
    public ModelFile tintedBarsPostEnds(String name, ResourceLocation edge) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_post_ends")).texture("edge", edge);
    }
    public ModelFile tintedBarsSide(String name, ResourceLocation bars, ResourceLocation edge) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_side")).texture("bars", bars).texture("edge", edge);
    }
    public ModelFile tintedBarsSideAlt(String name, ResourceLocation bars, ResourceLocation edge) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_side_alt")).texture("bars", bars).texture("edge", edge);
    }
    public ModelFile tintedBarsCap(String name, ResourceLocation bars) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_cap")).texture("bars", bars);
    }
    public ModelFile tintedBarsCapAlt(String name, ResourceLocation bars) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_bars_cap_alt")).texture("bars", bars);
    }
    public void tintetBarsBlock(IronBarsBlock block, ModelFile post, ModelFile postEnd, ModelFile side, ModelFile sideAlt, ModelFile cap, ModelFile capAlt) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(postEnd).addModel().end();

        builder.part().modelFile(post).addModel().condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(cap).addModel().condition(BlockStateProperties.NORTH, true).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(cap).rotationY(90).addModel().condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.EAST, true).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(capAlt).addModel().condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(capAlt).rotationY(90).addModel().condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.WEST, true).condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(side).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(sideAlt).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(sideAlt).rotationY(90).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(side).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end();

    }



    public void tintedPaneBlock(IronBarsBlock block, ResourceLocation pane, ResourceLocation edge) {
        tintedPaneBlockInternal(block, block.getRegistryName().toString(), pane, edge);
    }

    private void tintedPaneBlockInternal(IronBarsBlock block, String baseName, ResourceLocation pane, ResourceLocation edge) {
        ModelFile post = tintedPanePost(baseName + "_post", pane, edge);
        ModelFile side = tintedPaneSide(baseName + "_side", pane, edge);
        ModelFile sideAlt = tintedPaneSideAlt(baseName + "_side_alt", pane, edge);
        ModelFile noSide = tintedPaneNoSide(baseName + "_noside", pane);
        ModelFile noSideAlt = tintedPaneNoSideAlt(baseName + "_noside_alt", pane);
        tintedPaneBlock(block, post, side, sideAlt, noSide, noSideAlt);
    }

    public void tintedPaneBlock(IronBarsBlock block, ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel().end();
        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal()) {
                boolean alt = dir == Direction.SOUTH;
                builder.part().modelFile(alt || dir == Direction.WEST ? sideAlt : side).rotationY(dir.getAxis() == Direction.Axis.X ? 90 : 0).addModel()
                        .condition(e.getValue(), true).end()
                        .part().modelFile(alt || dir == Direction.EAST ? noSideAlt : noSide).rotationY(dir == Direction.WEST ? 270 : dir == Direction.SOUTH ? 90 : 0).addModel()
                        .condition(e.getValue(), false);
            }
        });
    }
    private ModelFile tintedPane(String name, String parent, ResourceLocation pane, ResourceLocation edge) {
        return models().withExistingParent(name, modLoc(parent))
                .texture("pane", pane)
                .texture("edge", edge);
    }

    public ModelFile tintedPanePost(String name, ResourceLocation pane, ResourceLocation edge) {
        return tintedPane(name, "template_tinted_glass_pane_post", pane, edge);
    }

    public ModelFile tintedPaneSide(String name, ResourceLocation pane, ResourceLocation edge) {
        return tintedPane(name, "template_tinted_glass_pane_side", pane, edge);
    }

    public ModelFile tintedPaneSideAlt(String name, ResourceLocation pane, ResourceLocation edge) {
        return tintedPane(name, "template_tinted_glass_pane_side_alt", pane, edge);
    }

    public ModelFile tintedPaneNoSide(String name, ResourceLocation pane) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_glass_pane_noside")).texture("pane", pane);
    }

    public ModelFile tintedPaneNoSideAlt(String name, ResourceLocation pane) {
        return models().withExistingParent(name, getBlockRSL("template_tinted_glass_pane_noside_alt")).texture("pane", pane);
    }
/*
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

 */


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
                .element().from(0.0f,0.0f,8.0f).to(16.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile doubleSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_double", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,0.0f).to(16.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile innerSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_inner", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(8.0f,0.0f,8.0f).to(16.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();
        ModelFile outerSlab = models().withExistingParent(BLK.getRegistryName().getPath()+"_outer", mcLoc("block/block"))
                .texture("particle", all)
                .texture("all", all)
                .element().from(0.0f,0.0f,8.0f).to(16.0f,16.0f,16.0f)
                .face(Direction.NORTH).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end()
                .element().from(8.0f,0.0f,0.0f).to(16.0f,16.0f,8.0f)
                .face(Direction.NORTH).texture("#all").end()
                .face(Direction.EAST).texture("#all").end()
                .face(Direction.SOUTH).texture("#all").end()
                .face(Direction.WEST).texture("#all").end()
                .face(Direction.UP).texture("#all").cullface(Direction.UP).end()
                .face(Direction.DOWN).texture("#all").cullface(Direction.DOWN).end()
                .end();

        verticalSlabBlock(BLK, straightSlab, doubleSlab, innerSlab, outerSlab);

    }
    public void verticalSlabBlock(RankineVerticalSlabBlock block, ModelFile straightSlab, ModelFile doubleSlab, ModelFile innerSlab, ModelFile outerSlab) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(RankineVerticalSlabBlock.HORIZONTAL_FACING);
            VerticalSlabStates type = state.getValue(RankineVerticalSlabBlock.TYPE);
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
            int yRot = (int) facing.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(MODEL)
                    .rotationY(yRot)
                    .uvLock(true)
                    .build();
        }, RankineVerticalSlabBlock.WATERLOGGED);
    }


    public void rotateableMachineBlock(Block blk, ModelFile OFF, ModelFile ON) {
        getVariantBuilder(blk).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            boolean on = state.getValue(BlockStateProperties.LIT);
            int yRot = (int) facing.getClockWise().getClockWise().toYRot();
            return ConfiguredModel.builder()
                    .modelFile(on ? ON : OFF)
                    .rotationY(yRot)
                    .build();
        });
    }


    public void buttonBlock(ButtonBlock blk, ResourceLocation texture) {
        String regName = blk.getRegistryName().getPath();
        buttonInventory(regName, texture);
        buttonBlock(blk, models().withExistingParent(regName, mcLoc("block/button")).texture("texture", texture), models().withExistingParent(regName+"_pressed", mcLoc("block/button_pressed")).texture("texture", texture));
    }
    public ModelBuilder<BlockModelBuilder> buttonInventory(String name, ResourceLocation texture) {
        return models().withExistingParent(name+"_inventory", mcLoc("block/button_inventory")).texture("texture", texture);
    }

    public void buttonBlock(ButtonBlock block, ModelFile button, ModelFile pressed) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            AttachFace face = state.getValue(ButtonBlock.FACE);
            Direction facing = state.getValue(ButtonBlock.FACING);
            Boolean powered = state.getValue(ButtonBlock.POWERED);
            int yRot = (int) facing.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(powered ? pressed : button)
                    .rotationY(yRot)
                    .rotationX(face == AttachFace.CEILING ? 180 : face == AttachFace.FLOOR ? 0 : 270)
                    .uvLock(true)
                    .build();
        });
    }

    public void SVLBlock(SodiumVaporLampBlock blk) {
        ModelFile HANG = models().withExistingParent(blk.getRegistryName().getPath(), modLoc("block/template_sodium_vapor_lamp")).texture("base", getBlockRSL("cast_iron_block")).texture("lamp", getBlockRSL("sodium_vapor_lamp"));
        ModelFile WALL = models().withExistingParent(blk.getRegistryName().getPath()+"wall", modLoc("block/template_sodium_vapor_lamp_wall")).texture("base", getBlockRSL("cast_iron_block")).texture("lamp", getBlockRSL("sodium_vapor_lamp")).texture("pole", getBlockRSL("cast_iron_pole"));

        getVariantBuilder(blk).forAllStatesExcept(state -> {
            Boolean hanging = state.getValue(SodiumVaporLampBlock.HANGING);
            Direction facing = state.getValue(SodiumVaporLampBlock.HORIZONTAL_FACING);
            int yRot = (int) facing.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(hanging ? HANG : WALL)
                    .rotationY((yRot+180)%360)
                    .build();
        }, SodiumVaporLampBlock.WATERLOGGED);
    }

    public void sixWayOnOff(BaseMachineBlock blk, ModelFile ON, ModelFile OFF) {
        getVariantBuilder(blk).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.FACING);
            boolean on = state.getValue(BaseMachineBlock.LIT);
            int yRot = (int) facing.getClockWise().getClockWise().toYRot();
            return ConfiguredModel.builder()
                    .modelFile(on ? ON : OFF)
                    .rotationY(yRot)
                    .build();
        });
    }



    public void mixingBarrelBlock(Block BLOCK) {
        String NAME = BLOCK.getRegistryName().getPath();
        getVariantBuilder(BLOCK).forAllStates(state -> {
            int i = state.getValue(MixingBarrelBlock.ANGLE);
            return ConfiguredModel.builder()
                .modelFile(models().withExistingParent(NAME+((i+3)%4),mcLoc("block/block"))
                    .texture("particle", getBlockRSL("mixing_barrel_side"))
                    .texture("side",  getBlockRSL("mixing_barrel_side"))
                    .texture("top",  getBlockRSL("mixing_barrel_top"))
                    .texture("base",  getBlockRSL("concrete"))
                    .element().from(0,0,0).to(16,2,16)
                        .face(Direction.NORTH).uvs(0,0, 16, 2).texture("#base").cullface(Direction.NORTH).end()
                        .face(Direction.EAST).uvs(0, 0, 16, 2).texture("#base").cullface(Direction.EAST).end()
                        .face(Direction.SOUTH).uvs(0, 0, 16, 2).texture("#base").cullface(Direction.SOUTH).end()
                        .face(Direction.WEST).uvs(0, 0, 16, 2).texture("#base").cullface(Direction.WEST).end()
                        .face(Direction.UP).uvs(0, 0, 16, 16).texture("#base").end()
                        .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#base").cullface(Direction.DOWN).end()
                        .end()
                    .element().from(2,2,2).to(14,16,14)
                        .rotation().origin(8,0,8).axis(Direction.Axis.Y).angle((float) (i*22.5-22.5)).end()
                        .face(Direction.NORTH).uvs(2,1,14,15).texture("#side").end()
                        .face(Direction.EAST).uvs(2,1,14,15).texture("#side").end()
                        .face(Direction.SOUTH).uvs(2,1,14,15).texture("#side").end()
                        .face(Direction.WEST).uvs(2,1,14,15).texture("#side").end()
                        .face(Direction.UP).uvs(2, 2, 14, 14).texture("#top").end()
                        .face(Direction.DOWN).uvs(2, 2, 14, 14).texture("#side").cullface(Direction.DOWN).end()
                        .end()).build();
        });
    }









    public void fancyPolishedBlock(Block BLK) {
        String name = BLK.getRegistryName().getPath();
        ResourceLocation polished = modLoc("block/"+name);
        IntegerProperty STYLE = ((BuildingModeBlock) BLK).getProperty();
        getVariantBuilder(BLK)
                .partialState().with(STYLE, 1)
                .modelForState().modelFile(models().withExistingParent(name+1, mcLoc("block/cube_all")).texture("all", polished)).addModel()
                .partialState().with(STYLE, 2)
                .modelForState().modelFile(models().withExistingParent(name+2, mcLoc("block/block"))
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
                .partialState().with(STYLE, 3)
                .modelForState().modelFile(models().withExistingParent(name+3, modLoc("block/polished_stone_offset_ns")).texture("all",blockTexture(BLK))).addModel()
                .partialState().with(STYLE, 4)
                .modelForState().modelFile(models().withExistingParent(name+4, modLoc("block/polished_stone_offset_ew")).texture("all",blockTexture(BLK))).addModel();
    }

    public void fancyStoneBricksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation large = modLoc("block/"+name);
        IntegerProperty STYLE = ((BuildingModeBlock) block).getProperty();
        getVariantBuilder(block)
                .partialState().with(STYLE, 1)
                .modelForState().modelFile(models().withExistingParent(name+1, mcLoc("block/cube_all")).texture("all", large)).addModel()
                .partialState().with(STYLE, 2)
                .modelForState().modelFile(models().withExistingParent(name+2, modLoc("block/template_rotation")).texture("all", large)).addModel();
    }
    public void fancyMossyStoneBricksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation large = modLoc("block/"+name.replace("mossy_",""));
        IntegerProperty STYLE = ((BuildingModeBlock) block).getProperty();
        getVariantBuilder(block)
                .partialState().with(STYLE, 1)
                    .modelForState().modelFile(models().withExistingParent(name+1, modLoc("block/template_cube_all_overlay")).texture("all", large).texture("overlay", getBlockRSL("stone_bricks_mossy_overlay"))).addModel()
                .partialState().with(STYLE, 2)
                    .modelForState().modelFile(models().withExistingParent(name+2, modLoc("block/template_cube_all_rotated_overlay")).texture("all", large).texture("overlay", getBlockRSL("stone_bricks_mossy_overlay"))).addModel();
    }
    public void fancyBricksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation normal = modLoc("block/"+name);
        ResourceLocation alt = modLoc("block/"+name+"_alt");
        IntegerProperty STYLE = ((BuildingModeBlock) block).getProperty();
        getVariantBuilder(block)
                .partialState().with(STYLE, 1)
                    .modelForState().modelFile(models().withExistingParent(name+1, mcLoc("block/cube_all")).texture("all", normal)).addModel()
                .partialState().with(STYLE, 2)
                    .modelForState().modelFile(models().withExistingParent(name+2, modLoc("block/template_rotation")).texture("all", normal)).addModel()
                .partialState().with(STYLE, 3)
                    .modelForState().modelFile(models().withExistingParent(name+3, mcLoc("block/cube_all")).texture("all", alt)).addModel()
                .partialState().with(STYLE, 4)
                    .modelForState().modelFile(models().withExistingParent(name+4, modLoc("block/template_rotation")).texture("all", alt)).addModel();
    }
    public void fancyPlanksBlock(Block block) {
        String name = block.getRegistryName().getPath();
        ResourceLocation normal = modLoc("block/"+name);
        IntegerProperty STYLE = ((BuildingModeBlock) block).getProperty();
        getVariantBuilder(block)
                .partialState().with(STYLE, 1)
                    .modelForState().modelFile(models().withExistingParent(name+1, mcLoc("block/cube_all")).texture("all", normal)).addModel()
                .partialState().with(STYLE, 2)
                    .modelForState().modelFile(models().withExistingParent(name+2, modLoc("block/template_rotation")).texture("all", normal)).addModel()
                .partialState().with(STYLE, 3)
                    .modelForState().modelFile(models().withExistingParent(name+3, mcLoc("block/cube_all")).texture("all", normal)).addModel()
                .partialState().with(STYLE, 4)
                    .modelForState().modelFile(models().withExistingParent(name+4, mcLoc("block/cube_all")).texture("all", normal)).addModel();
    }
    public void fancyBookshelvesBlock(RankineWood wood) {
        String name = wood.getBookshelf().getRegistryName().getPath();
        String plankName = wood.getPlanks().getRegistryName().getPath();
        IntegerProperty STYLE = ((BuildingModeBlock) wood.getBookshelf()).getProperty();
        getVariantBuilder(wood.getBookshelf())
                .partialState().with(STYLE, 1)
                    .modelForState().modelFile(models().withExistingParent(name+1, mcLoc("block/cube_column")).texture("end", getBlockRSL(plankName)).texture("side", getBlockRSL(name))).addModel()
                .partialState().with(STYLE, 2)
                    .modelForState().modelFile(models().withExistingParent(name+2, mcLoc("block/cube_column")).texture("end", getBlockRSL(plankName)).texture("side", getBlockRSL(name))).addModel();
            //    .partialState().with(STYLE, 3)
            //        .modelForState().modelFile(models().withExistingParent(name, mcLoc("block/cube_column")).texture("end", getBlockRSL(plankName)).texture("side", getBlockRSL(name))).addModel()
            //    .partialState().with(STYLE, 4)
            //        .modelForState().modelFile(models().withExistingParent(name, mcLoc("block/cube_column")).texture("end", getBlockRSL(plankName)).texture("side", getBlockRSL(name))).addModel();

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
    public void columnBlock(Block BLK, ResourceLocation texture) {
        String NAME = BLK.getRegistryName().getPath();
        getVariantBuilder(BLK).forAllStatesExcept(state -> {
            int i = state.getValue(StoneColumnBlock.SIZE);
            return ConfiguredModel.builder()
                    .modelFile(models().withExistingParent(NAME+(i),mcLoc("block/block"))
                            .texture("particle", texture)
                            .texture("all", texture)
                            .element().from(8-i,0,8-i).to(8+i,16,8+i)
                            .face(Direction.NORTH).uvs(8-i,0, 8+i, 16).texture("#all").end()
                            .face(Direction.EAST).uvs(8-i, 0, 8+i, 16).texture("#all").end()
                            .face(Direction.SOUTH).uvs(8-i, 0, 8+i, 16).texture("#all").end()
                            .face(Direction.WEST).uvs(8-i, 0, 8+i, 16).texture("#all").end()
                            .face(Direction.UP).uvs(8-i, 8-i, 8+i, 8+i).texture("#all").cullface(Direction.UP).end()
                            .face(Direction.DOWN).uvs(8-i, 8-i, 8+i, 8+i).texture("#all").cullface(Direction.DOWN).end()
                            .end()).build();
        },StoneColumnBlock.WATERLOGGED);
    }

    public void lanternBlock(Block BLK) {
        String NAME = BLK.getRegistryName().getPath();
        getVariantBuilder(BLK)
                .partialState().with(LanternBlock.HANGING,false).modelForState().modelFile(models().withExistingParent(NAME,getBlockRSL("minecraft","template_lantern")).texture("lantern",blockTexture(BLK))).addModel()
                .partialState().with(LanternBlock.HANGING,true).modelForState().modelFile(models().withExistingParent(NAME+"_hanging",getBlockRSL("minecraft","template_hanging_lantern")).texture("lantern",blockTexture(BLK))).addModel();

    }

    public void cubeTopBottomBLock(Block BLK) {
        String pathName = BLK.getRegistryName().getPath();
        simpleBlock(BLK,models().cubeBottomTop(pathName,blockTexture(BLK),getBlockRSL(pathName+"_end"),getBlockRSL(pathName+"_end")));
    }

    public void slabBlock(Block BLK, boolean special) {
        String regName = BLK.getRegistryName().getPath();
        slabBlock((RankineSlabBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_slab",special ? "1" : "")), new ResourceLocation("rankine","block/"+regName.replace("_slab","")));
    }
    public void stairsBlock(Block BLK, boolean special) {
        String regName = BLK.getRegistryName().getPath();
        stairsBlock((RankineStairsBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_stairs","")));
    }
    public void wallBlock(Block BLK, boolean special) {
        String regName = BLK.getRegistryName().getPath();
        wallBlock((RankineWallBlock) BLK, new ResourceLocation("rankine","block/"+regName.replace("_wall","")));
    }
    
}
