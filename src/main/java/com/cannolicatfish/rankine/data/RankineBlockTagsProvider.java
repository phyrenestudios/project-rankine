package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockTagsProvider extends BlockTagsProvider {

    public RankineBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Block Tags";
    }

    @Override
    protected void registerTags() {
        //MINECRAFT
        getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).addTag(RankineTags.Blocks.CEDAR_LOGS).addTag(RankineTags.Blocks.PINYON_PINE_LOGS).addTag(RankineTags.Blocks.JUNIPER_LOGS).addTag(RankineTags.Blocks.COCONUT_PALM_LOGS).addTag(RankineTags.Blocks.BALSAM_FIR_LOGS).addTag(RankineTags.Blocks.EASTERN_HEMLOCK_LOGS).addTag(RankineTags.Blocks.MAPLE_LOGS).addTag(RankineTags.Blocks.MAGNOLIA_LOGS).addTag(RankineTags.Blocks.SHARINGA_LOGS).addTag(RankineTags.Blocks.CORK_OAK_LOGS).addTag(RankineTags.Blocks.CINNAMON_LOGS).addTag(RankineTags.Blocks.BLACK_BIRCH_LOGS).addTag(RankineTags.Blocks.YELLOW_BIRCH_LOGS).addTag(RankineTags.Blocks.BLACK_WALNUT_LOGS);
        getOrCreateBuilder(BlockTags.CLIMBABLE).add(RankineBlocks.ROPE.get(),RankineBlocks.CAST_IRON_SUPPORT.get(),RankineBlocks.CAST_IRON_LADDER.get(),RankineBlocks.DURALUMIN_LADDER.get(),RankineBlocks.BRASS_LADDER.get(),RankineBlocks.CUPRONICKEL_LADDER.get());
        getOrCreateBuilder(BlockTags.GOLD_ORES).add(RankineBlocks.NATIVE_GOLD_ORE.get());
        getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(RankineBlocks.NATIVE_GOLD_ORE.get()).add(RankineBlocks.GOLD_SHEETMETAL.get()).add(RankineBlocks.GOLD_SHEETMETAL_VERTICAL_SLAB.get()).add(RankineBlocks.BLACK_GOLD_PEDESTAL.get()).add(RankineBlocks.PURPLE_GOLD_PEDESTAL.get()).add(RankineBlocks.ROSE_GOLD_PEDESTAL.get()).add(RankineBlocks.WHITE_GOLD_PEDESTAL.get()).add(RankineBlocks.GREEN_GOLD_PEDESTAL.get()).add(RankineBlocks.BLUE_GOLD_PEDESTAL.get()).add(RankineBlocks.BLACK_GOLD_BLOCK.get()).add(RankineBlocks.PURPLE_GOLD_BLOCK.get()).add(RankineBlocks.ROSE_GOLD_BLOCK.get()).add(RankineBlocks.WHITE_GOLD_BLOCK.get()).add(RankineBlocks.GREEN_GOLD_BLOCK.get()).add(RankineBlocks.BLUE_GOLD_BLOCK.get());
        getOrCreateBuilder(BlockTags.SAND).add(RankineBlocks.BLACK_SAND.get(),RankineBlocks.WHITE_SAND.get());
        getOrCreateBuilder(Tags.Blocks.COBBLESTONE).add(RankineBlocks.SKARN.get(),RankineBlocks.BRECCIA.get());
        getOrCreateBuilder(BlockTags.DRAGON_IMMUNE).addTags(RankineTags.Blocks.PETRIFIED_CHORUS_LOGS,RankineTags.Blocks.CHARRED_LOGS,RankineTags.Blocks.ERYTHRINA_LOGS);

        for (Block blk : RankineLists.FIBER_MAT) {
            getOrCreateBuilder(BlockTags.CARPETS).add(blk);
        }
        for (Block blk : RankineLists.METAL_DOORS) {
            getOrCreateBuilder(BlockTags.DOORS).add(blk);
        }
        for (Block blk : RankineLists.METAL_TRAPDOORS) {
            getOrCreateBuilder(BlockTags.TRAPDOORS).add(blk);
        }
        for (Block blk : RankineLists.FLOWER_POTS) {
            getOrCreateBuilder(BlockTags.FLOWER_POTS).add(blk);
        }
        for (Block blk : RankineLists.SAPLINGS) {
            getOrCreateBuilder(BlockTags.SAPLINGS).add(blk);
        }
        for (Block blk : RankineLists.TALL_FLOWERS) {
            getOrCreateBuilder(BlockTags.TALL_FLOWERS).add(blk);
        }
        for (Block blk : RankineLists.LEAVES) {
            getOrCreateBuilder(BlockTags.LEAVES).add(blk);
        }
        for (Block blk : RankineLists.PLANKS) {
            getOrCreateBuilder(BlockTags.PLANKS).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_BUTTONS) {
            getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_DOORS) {
            getOrCreateBuilder(BlockTags.WOODEN_DOORS).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_FENCES) {
            getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_SLABS) {
            getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_STAIRS) {
            getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_PRESSURE_PLATES) {
            getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_FENCE_GATES) {
            getOrCreateBuilder(BlockTags.FENCE_GATES).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_TRAPDOORS) {
            getOrCreateBuilder(BlockTags.WOODEN_TRAPDOORS).add(blk);
        }
        for (Block blk : RankineLists.STONE_BUTTON) {
            getOrCreateBuilder(BlockTags.BUTTONS).add(blk);
        }
        for (Block blk : RankineLists.STONE_PRESSURE_PLATE) {
            getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_PRESSURE_PLATE) {
            getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(blk);
        }
        getOrCreateBuilder(BlockTags.STAIRS).addTag(RankineTags.Blocks.BRICKS_STAIRS).addTag(RankineTags.Blocks.STONE_STAIRS).addTag(RankineTags.Blocks.POLISHED_STONE_STAIRS).addTag(RankineTags.Blocks.STONE_BRICKS_STAIRS);
        getOrCreateBuilder(BlockTags.WALLS).addTag(RankineTags.Blocks.BRICKS_WALL).addTag(RankineTags.Blocks.STONE_WALL).addTag(RankineTags.Blocks.POLISHED_STONE_WALL).addTag(RankineTags.Blocks.STONE_BRICKS_WALL);
        getOrCreateBuilder(BlockTags.SLABS).addTag(RankineTags.Blocks.BRICKS_SLAB).addTag(RankineTags.Blocks.STONE_SLAB).addTag(RankineTags.Blocks.POLISHED_STONE_SLAB).addTag(RankineTags.Blocks.STONE_BRICKS_SLAB);
        for (Block blk : RankineLists.STONE_BRICKS) {
            getOrCreateBuilder(BlockTags.STONE_BRICKS).add(blk);
        }










        //LOGS
        getOrCreateBuilder(RankineTags.Blocks.CEDAR_LOGS).add(RankineBlocks.CEDAR_LOG.get(),RankineBlocks.CEDAR_WOOD.get(),RankineBlocks.STRIPPED_CEDAR_LOG.get(),RankineBlocks.STRIPPED_CEDAR_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.PINYON_PINE_LOGS).add(RankineBlocks.PINYON_PINE_LOG.get(),RankineBlocks.PINYON_PINE_WOOD.get(),RankineBlocks.STRIPPED_PINYON_PINE_LOG.get(),RankineBlocks.STRIPPED_PINYON_PINE_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.JUNIPER_LOGS).add(RankineBlocks.JUNIPER_LOG.get(),RankineBlocks.JUNIPER_WOOD.get(),RankineBlocks.STRIPPED_JUNIPER_LOG.get(),RankineBlocks.STRIPPED_JUNIPER_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.COCONUT_PALM_LOGS).add(RankineBlocks.COCONUT_PALM_LOG.get(),RankineBlocks.COCONUT_PALM_WOOD.get(),RankineBlocks.STRIPPED_COCONUT_PALM_LOG.get(),RankineBlocks.STRIPPED_COCONUT_PALM_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.BALSAM_FIR_LOGS).add(RankineBlocks.BALSAM_FIR_LOG.get(),RankineBlocks.BALSAM_FIR_WOOD.get(),RankineBlocks.STRIPPED_BALSAM_FIR_LOG.get(),RankineBlocks.STRIPPED_BALSAM_FIR_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.EASTERN_HEMLOCK_LOGS).add(RankineBlocks.EASTERN_HEMLOCK_LOG.get(),RankineBlocks.EASTERN_HEMLOCK_WOOD.get(),RankineBlocks.STRIPPED_EASTERN_HEMLOCK_LOG.get(),RankineBlocks.STRIPPED_EASTERN_HEMLOCK_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.MAGNOLIA_LOGS).add(RankineBlocks.MAGNOLIA_LOG.get(),RankineBlocks.MAGNOLIA_WOOD.get(),RankineBlocks.STRIPPED_MAGNOLIA_LOG.get(),RankineBlocks.STRIPPED_MAGNOLIA_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.MAPLE_LOGS).add(RankineBlocks.MAPLE_LOG.get(),RankineBlocks.MAPLE_WOOD.get(),RankineBlocks.STRIPPED_MAPLE_LOG.get(),RankineBlocks.STRIPPED_MAPLE_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.BLACK_BIRCH_LOGS).add(RankineBlocks.BLACK_BIRCH_LOG.get(),RankineBlocks.BLACK_BIRCH_WOOD.get(),RankineBlocks.STRIPPED_BLACK_BIRCH_LOG.get(),RankineBlocks.STRIPPED_BLACK_BIRCH_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.YELLOW_BIRCH_LOGS).add(RankineBlocks.YELLOW_BIRCH_LOG.get(),RankineBlocks.YELLOW_BIRCH_WOOD.get(),RankineBlocks.STRIPPED_YELLOW_BIRCH_LOG.get(),RankineBlocks.STRIPPED_YELLOW_BIRCH_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.SHARINGA_LOGS).add(RankineBlocks.SHARINGA_LOG.get(),RankineBlocks.SHARINGA_WOOD.get(),RankineBlocks.STRIPPED_SHARINGA_LOG.get(),RankineBlocks.STRIPPED_SHARINGA_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.CORK_OAK_LOGS).add(RankineBlocks.CORK_OAK_LOG.get(),RankineBlocks.CORK_OAK_WOOD.get(),RankineBlocks.STRIPPED_CORK_OAK_LOG.get(),RankineBlocks.STRIPPED_CORK_OAK_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.BLACK_WALNUT_LOGS).add(RankineBlocks.BLACK_WALNUT_LOG.get(),RankineBlocks.BLACK_WALNUT_WOOD.get(),RankineBlocks.STRIPPED_BLACK_WALNUT_LOG.get(),RankineBlocks.STRIPPED_BLACK_WALNUT_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.CINNAMON_LOGS).add(RankineBlocks.CINNAMON_LOG.get(),RankineBlocks.CINNAMON_WOOD.get(),RankineBlocks.STRIPPED_CINNAMON_LOG.get(),RankineBlocks.STRIPPED_CINNAMON_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.PETRIFIED_CHORUS_LOGS).add(RankineBlocks.PETRIFIED_CHORUS_LOG.get(),RankineBlocks.PETRIFIED_CHORUS_WOOD.get(),RankineBlocks.STRIPPED_PETRIFIED_CHORUS_LOG.get(),RankineBlocks.STRIPPED_PETRIFIED_CHORUS_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.CHARRED_LOGS).add(RankineBlocks.CHARRED_LOG.get(),RankineBlocks.CHARRED_WOOD.get(),RankineBlocks.STRIPPED_CHARRED_LOG.get(),RankineBlocks.STRIPPED_CHARRED_WOOD.get());
        getOrCreateBuilder(RankineTags.Blocks.ERYTHRINA_LOGS).add(RankineBlocks.ERYTHRINA_LOG.get(),RankineBlocks.ERYTHRINA_WOOD.get(),RankineBlocks.STRIPPED_ERYTHRINA_LOG.get(),RankineBlocks.STRIPPED_ERYTHRINA_WOOD.get());


        getOrCreateBuilder(RankineTags.Blocks.NUGGET_STONES).addTags(RankineTags.Blocks.TUFF,RankineTags.Blocks.BASE_STONE_END,BlockTags.BASE_STONE_NETHER,BlockTags.BASE_STONE_OVERWORLD);
        getOrCreateBuilder(RankineTags.Blocks.LIGHTNING_VITRIFIED).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,Tags.Blocks.DIRT);

        /*
        for (Block blk : RankineLists.ORE_STONES) {
            getOrCreateBuilder(RankineTags.Blocks.ORE_TEXTURES).add(blk);
        }

         */



        getOrCreateBuilder(RankineTags.Blocks.IGNEOUS_STONES).addTags(RankineTags.Blocks.STONES_ANDESITE,RankineTags.Blocks.STONES_BASALT,RankineTags.Blocks.STONES_DACITE,RankineTags.Blocks.STONES_GABBRO,RankineTags.Blocks.STONES_GRANODIORITE,RankineTags.Blocks.STONES_PORPHYRY,RankineTags.Blocks.STONES_GRANITE,RankineTags.Blocks.STONES_SHONKINITE,RankineTags.Blocks.STONES_PERIDOTITE,RankineTags.Blocks.STONES_KIMBERLITE,RankineTags.Blocks.STONES_KOMATIITE,RankineTags.Blocks.STONES_PUMICE,RankineTags.Blocks.STONES_SCORIA,RankineTags.Blocks.STONES_RHYOLITE);
        getOrCreateBuilder(RankineTags.Blocks.METAMORPHIC_STONES).addTags(RankineTags.Blocks.STONES_GNEISS,RankineTags.Blocks.STONES_SCHIST,RankineTags.Blocks.STONES_SKARN,RankineTags.Blocks.STONES_MARBLE,RankineTags.Blocks.STONES_PHYLITE,RankineTags.Blocks.STONES_SLATE,RankineTags.Blocks.STONES_SERPENTINITE);
        getOrCreateBuilder(RankineTags.Blocks.SEDIMENTARY_STONES).addTags(RankineTags.Blocks.STONES_LIMESTONE,RankineTags.Blocks.STONES_DOLOMITE,RankineTags.Blocks.STONES_MUDSTONE,RankineTags.Blocks.STONES_SHALE,RankineTags.Blocks.STONES_SILTSTONE,RankineTags.Blocks.STONES_SANDSTONE,RankineTags.Blocks.STONES_BRECCIA,RankineTags.Blocks.STONES_MARLSTONE,RankineTags.Blocks.STONES_CHALK);
        getOrCreateBuilder(Tags.Blocks.STONE).addTags(RankineTags.Blocks.IGNEOUS_STONES,RankineTags.Blocks.METAMORPHIC_STONES,RankineTags.Blocks.SEDIMENTARY_STONES);
        getOrCreateBuilder(Tags.Blocks.GRAVEL).add(RankineBlocks.DARK_GRAVEL.get(),RankineBlocks.LIGHT_GRAVEL.get());



        getOrCreateBuilder(RankineTags.Blocks.TUFF).add(RankineBlocks.ANDESITIC_TUFF.get(),RankineBlocks.BASALTIC_TUFF.get(),RankineBlocks.RHYOLITIC_TUFF.get(),RankineBlocks.KIMBERLITIC_TUFF.get(),RankineBlocks.KOMATIITIC_TUFF.get());
        getOrCreateBuilder(RankineTags.Blocks.CLAY).add(RankineBlocks.FIRE_CLAY.get(),Blocks.CLAY);
        getOrCreateBuilder(RankineTags.Blocks.HARDENED_GLASS).add(RankineBlocks.LEAD_GLASS.get());

        for (Block blk : Stream.of(RankineLists.SOILS, RankineLists.GRASSY_SOILS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(Tags.Blocks.DIRT).add(blk);
        }
        for (Block blk : RankineLists.BRICKS) {
            getOrCreateBuilder(RankineTags.Blocks.BRICKS).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.BRICKS).add(Blocks.BRICKS);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS).add(Blocks.NETHER_BRICKS);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS).add(Blocks.RED_NETHER_BRICKS);
        for (Block blk : RankineLists.BRICKS_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.BRICKS_SLAB).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_SLAB).add(Blocks.BRICK_SLAB);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_SLAB).add(Blocks.NETHER_BRICK_SLAB);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_SLAB).add(Blocks.RED_NETHER_BRICK_SLAB);
        for (Block blk : RankineLists.BRICKS_STAIRS) {
            getOrCreateBuilder(RankineTags.Blocks.BRICKS_STAIRS).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_STAIRS).add(Blocks.BRICK_STAIRS);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_STAIRS).add(Blocks.NETHER_BRICK_STAIRS);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_STAIRS).add(Blocks.RED_NETHER_BRICK_STAIRS);
        for (Block blk : RankineLists.BRICKS_WALL) {
            getOrCreateBuilder(RankineTags.Blocks.BRICKS_WALL).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_WALL).add(Blocks.BRICK_WALL);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_WALL).add(Blocks.NETHER_BRICK_WALL);
        getOrCreateBuilder(RankineTags.Blocks.BRICKS_WALL).add(Blocks.RED_NETHER_BRICK_WALL);
        for (Block blk : RankineLists.BRICKS_VERTICAL_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.BRICKS_VERTICAL_SLAB).add(blk);
        }
        for (Block blk : RankineLists.WOODEN_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.WOODEN_VERTICAL_SLABS).add(blk);
        }
        for (Block blk : RankineLists.TERRACOTTA) {
            getOrCreateBuilder(RankineTags.Blocks.TERRACOTTA).add(blk);
        }
        for (Block blk : RankineLists.GLAZED_TERRACOTTA) {
            getOrCreateBuilder(RankineTags.Blocks.GLAZED_TERRACOTTA).add(blk);
        }
        for (Block blk : RankineLists.CONCRETE) {
            getOrCreateBuilder(RankineTags.Blocks.CONCRETE).add(blk);
        }
        for (Block blk : RankineLists.CONCRETE_POWDER) {
            getOrCreateBuilder(RankineTags.Blocks.CONCRETE_POWDER).add(blk);
        }
        for (Block blk : RankineLists.GEODES) {
            getOrCreateBuilder(RankineTags.Blocks.GEODES).add(blk);
        }
        for (Block blk : RankineLists.LEDS) {
            getOrCreateBuilder(RankineTags.Blocks.LEDS).add(blk);
        }
        for (Block blk : RankineLists.MINERAL_WOOL) {
            getOrCreateBuilder(RankineTags.Blocks.MINERAL_WOOL).add(blk);
        }
        for (Block blk : RankineLists.SHEETMETAL_VERTICAL_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.SHEETMETAL_VERTICAL_SALBS).add(blk);
        }
        for (Block blk : RankineLists.SHEETMETALS) {
            String name = blk.getRegistryName().getPath();
            String baseName = Arrays.asList(name.split("_sheetmetal")).get(0);
            getOrCreateBuilder(BlockTags.makeWrapperTag(new ResourceLocation("forge", "sheetmetals/"+baseName).toString())).add(blk);
            getOrCreateBuilder(RankineTags.Blocks.SHEETMETAL).addTag(BlockTags.makeWrapperTag(new ResourceLocation("forge", "sheetmetals/"+baseName).toString()));
        }

        getOrCreateBuilder(RankineTags.Blocks.STONES_DACITE).add(RankineBlocks.BLACK_DACITE.get(),RankineBlocks.RED_DACITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_ANDESITE).add(RankineBlocks.HORNBLENDE_ANDESITE.get(),Blocks.ANDESITE);
        getOrCreateBuilder(RankineTags.Blocks.STONES_BASALT).add(RankineBlocks.THOLEIITIC_BASALT.get(),Blocks.BASALT);
        getOrCreateBuilder(RankineTags.Blocks.STONES_GRANITE).add(RankineBlocks.GRAY_GRANITE.get(),Blocks.GRANITE);
        getOrCreateBuilder(RankineTags.Blocks.STONES_SANDSTONE).add(RankineBlocks.ARKOSE.get(), RankineBlocks.ITACOLUMITE.get(), RankineBlocks.SOUL_SANDSTONE.get(),Blocks.SANDSTONE,Blocks.RED_SANDSTONE);
        getOrCreateBuilder(RankineTags.Blocks.STONES_PEGMATITE).add(RankineBlocks.PEGMATITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_BRECCIA).add(RankineBlocks.BRECCIA.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_PERIDOTITE).add(RankineBlocks.PERIDOTITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_PHYLITE).add(RankineBlocks.PHYLLITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_PORPHYRY).add(RankineBlocks.RED_PORPHYRY.get()).add(RankineBlocks.PURPLE_PORPHYRY.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_PUMICE).add(RankineBlocks.PUMICE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SCORIA).add(RankineBlocks.SCORIA.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SCHIST).add(RankineBlocks.MICA_SCHIST.get()).add(RankineBlocks.GREENSCHIST.get()).add(RankineBlocks.BLUESCHIST.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_DOLOMITE).add(RankineBlocks.DOLOSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_MARBLE).add(RankineBlocks.WHITE_MARBLE.get()).add(RankineBlocks.GRAY_MARBLE.get()).add(RankineBlocks.BLACK_MARBLE.get()).add(RankineBlocks.ROSE_MARBLE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_GABBRO).add(RankineBlocks.GABBRO.get()).add(RankineBlocks.ANORTHOSITE.get()).add(RankineBlocks.NORITE.get()).add(RankineBlocks.PYROXENITE.get()).add(RankineBlocks.TROCTOLITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_MARLSTONE).add(RankineBlocks.MARLSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_MUDSTONE).add(RankineBlocks.MUDSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_RHYOLITE).add(RankineBlocks.RHYOLITE.get(),RankineBlocks.COMENDITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_GRANODIORITE).add(RankineBlocks.GRANODIORITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_KIMBERLITE).add(RankineBlocks.KIMBERLITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_KOMATIITE).add(RankineBlocks.KOMATIITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_GNEISS).add(RankineBlocks.GNEISS.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_LIMESTONE).add(RankineBlocks.LIMESTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SKARN).add(RankineBlocks.SKARN.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_CHALK).add(RankineBlocks.CHALK.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SHALE).add(RankineBlocks.SHALE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SILTSTONE).add(RankineBlocks.SILTSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SERPENTINITE).add(RankineBlocks.SERPENTINITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SLATE).add(RankineBlocks.SLATE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SHONKINITE).add(RankineBlocks.SHONKINITE.get());





        for (Block blk : RankineLists.STONE) {
            //String name = blk.getRegistryName().getPath();
            //getOrCreateBuilder(BlockTags.makeWrapperTag(new ResourceLocation("forge", "stones/"+name).toString())).add(blk);
            //getOrCreateBuilder(Tags.Blocks.STONE).addTag(BlockTags.makeWrapperTag(new ResourceLocation("forge", "stones/"+name).toString()));
            getOrCreateBuilder(RankineTags.Blocks.BASE_STONE_END).add(blk);
            getOrCreateBuilder(BlockTags.BASE_STONE_OVERWORLD).add(blk);
            getOrCreateBuilder(BlockTags.BASE_STONE_NETHER).add(blk);
            getOrCreateBuilder(RankineTags.Blocks.WG_STONE).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE).add(blk);
        }
        getOrCreateBuilder(Tags.Blocks.STONE).addTag(RankineTags.Blocks.POLISHED_STONE);
        for (Block blk : RankineLists.STONE_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_SLAB).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_SLAB).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_SLAB).add(blk);
        }
        for (Block blk : RankineLists.STONE_STAIRS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_STAIRS).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_STAIRS) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_STAIRS).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_STAIRS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_STAIRS).add(blk);
        }
        for (Block blk : RankineLists.STONE_WALL) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_WALL).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_WALL) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_WALL).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_WALL) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_WALL).add(blk);
        }
        for (Block blk : RankineLists.STONE_VERTICAL_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_VERTICAL_SLAB).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_VERTICAL_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_VERTICAL_SLAB).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_VERTICAL_SLAB) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_VERTICAL_SLAB).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.VERTICAL_SLABS).addTag(RankineTags.Blocks.BRICKS_VERTICAL_SLAB).addTag(RankineTags.Blocks.WOODEN_VERTICAL_SLABS).addTag(RankineTags.Blocks.SHEETMETAL_VERTICAL_SALBS).addTag(RankineTags.Blocks.STONE_VERTICAL_SLAB).addTag(RankineTags.Blocks.POLISHED_STONE_VERTICAL_SLAB).addTag(RankineTags.Blocks.STONE_BRICKS_VERTICAL_SLAB);





    }

}
