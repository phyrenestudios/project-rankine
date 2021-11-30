package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
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

        getOrCreateBuilder(RankineTags.Blocks.STONES_DACITE).add(RankineBlocks.BLACK_DACITE.get(),RankineBlocks.RED_DACITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_ANDESITE).add(RankineBlocks.HORNBLENDE_ANDESITE.get(),Blocks.ANDESITE);
        getOrCreateBuilder(RankineTags.Blocks.STONES_BASALT).add(RankineBlocks.THOLEIITIC_BASALT.get(),Blocks.BASALT);
        getOrCreateBuilder(RankineTags.Blocks.STONES_GRANITE).add(RankineBlocks.GRAY_GRANITE.get(),Blocks.GRANITE);
        getOrCreateBuilder(RankineTags.Blocks.STONES_SANDSTONE).add(RankineBlocks.ARKOSE.get(), RankineBlocks.ITACOLUMITE.get(), RankineBlocks.SOUL_SANDSTONE.get(), RankineBlocks.BLACK_SANDSTONE.get(), RankineBlocks.WHITE_SANDSTONE.get(), RankineBlocks.DESERT_SANDSTONE.get(),Blocks.SANDSTONE,Blocks.RED_SANDSTONE);
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
        getOrCreateBuilder(RankineTags.Blocks.STONES_MARIPOSITE).add(RankineBlocks.MARIPOSITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_QUARTZITE).add(RankineBlocks.QUARTZITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_CHALK).add(RankineBlocks.CHALK.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SHALE).add(RankineBlocks.SHALE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SILTSTONE).add(RankineBlocks.SILTSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SERPENTINITE).add(RankineBlocks.SERPENTINITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_ECLOGITE).add(RankineBlocks.ECLOGITE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SLATE).add(RankineBlocks.SLATE.get());
        getOrCreateBuilder(RankineTags.Blocks.STONES_SHONKINITE).add(RankineBlocks.SHONKINITE.get());

        getOrCreateBuilder(RankineTags.Blocks.IGNEOUS_STONES).addTags(RankineTags.Blocks.STONES_ANDESITE,RankineTags.Blocks.STONES_BASALT,RankineTags.Blocks.STONES_DACITE,RankineTags.Blocks.STONES_GABBRO,RankineTags.Blocks.STONES_PEGMATITE,RankineTags.Blocks.STONES_GRANODIORITE,RankineTags.Blocks.STONES_PORPHYRY,RankineTags.Blocks.STONES_GRANITE,RankineTags.Blocks.STONES_SHONKINITE,RankineTags.Blocks.STONES_PERIDOTITE,RankineTags.Blocks.STONES_KIMBERLITE,RankineTags.Blocks.STONES_KOMATIITE,RankineTags.Blocks.STONES_PUMICE,RankineTags.Blocks.STONES_SCORIA,RankineTags.Blocks.STONES_RHYOLITE);
        getOrCreateBuilder(RankineTags.Blocks.METAMORPHIC_STONES).addTags(RankineTags.Blocks.STONES_GNEISS,RankineTags.Blocks.STONES_SCHIST,RankineTags.Blocks.STONES_MARIPOSITE,RankineTags.Blocks.STONES_MARBLE,RankineTags.Blocks.STONES_PHYLITE,RankineTags.Blocks.STONES_SLATE,RankineTags.Blocks.STONES_SERPENTINITE,RankineTags.Blocks.STONES_ECLOGITE,RankineTags.Blocks.STONES_QUARTZITE);
        getOrCreateBuilder(RankineTags.Blocks.SEDIMENTARY_STONES).addTags(RankineTags.Blocks.STONES_LIMESTONE,RankineTags.Blocks.STONES_DOLOMITE,RankineTags.Blocks.STONES_MUDSTONE,RankineTags.Blocks.STONES_SHALE,RankineTags.Blocks.STONES_SILTSTONE,RankineTags.Blocks.STONES_SANDSTONE,RankineTags.Blocks.STONES_MARLSTONE,RankineTags.Blocks.STONES_CHALK);
        getOrCreateBuilder(Tags.Blocks.STONE).addTags(RankineTags.Blocks.IGNEOUS_STONES,RankineTags.Blocks.METAMORPHIC_STONES,RankineTags.Blocks.SEDIMENTARY_STONES);
        getOrCreateBuilder(Tags.Blocks.GRAVEL).add(RankineBlocks.DARK_GRAVEL.get(),RankineBlocks.LIGHT_GRAVEL.get());
        getOrCreateBuilder(RankineTags.Blocks.TUFF).add(RankineBlocks.ANDESITIC_TUFF.get(),RankineBlocks.BASALTIC_TUFF.get(),RankineBlocks.RHYOLITIC_TUFF.get(),RankineBlocks.KIMBERLITIC_TUFF.get(),RankineBlocks.KOMATIITIC_TUFF.get());
        getOrCreateBuilder(RankineTags.Blocks.CLAY).add(RankineBlocks.FIRE_CLAY.get(),Blocks.CLAY);
        getOrCreateBuilder(RankineTags.Blocks.ICE).add(RankineBlocks.METEORIC_ICE.get(),Blocks.ICE,Blocks.PACKED_ICE,Blocks.BLUE_ICE);
        getOrCreateBuilder(RankineTags.Blocks.HARDENED_GLASS).add(RankineBlocks.LEAD_GLASS.get(),RankineBlocks.BOROSILICATE_GLASS.get(),RankineBlocks.REACTION_CHAMBER_CELL.get());
        getOrCreateBuilder(BlockTags.SAND).add(RankineBlocks.BLACK_SAND.get(),RankineBlocks.WHITE_SAND.get());
        getOrCreateBuilder(Tags.Blocks.SAND).add(RankineBlocks.BLACK_SAND.get(),RankineBlocks.WHITE_SAND.get());
        getOrCreateBuilder(RankineTags.Blocks.CONSTRUCTION_SAND).addTag(Tags.Blocks.SAND).add(RankineBlocks.DESERT_SAND.get());
        getOrCreateBuilder(Tags.Blocks.COBBLESTONE).add(RankineBlocks.SKARN.get(),RankineBlocks.BRECCIA.get());
        getOrCreateBuilder(BlockTags.GOLD_ORES).add(RankineBlocks.NATIVE_GOLD_ORE.get());

        for (Block blk : RankineLists.WOODEN_BOOKSHELVES) {
            getOrCreateBuilder(RankineTags.Blocks.BOOKSHELVES).add(blk);
        }
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
        for (Block blk : RankineLists.STONE_BUTTONS) {
            getOrCreateBuilder(BlockTags.BUTTONS).add(blk);
        }
        for (Block blk : RankineLists.STONE_PRESSURE_PLATES) {
            getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_PRESSURE_PLATES) {
            getOrCreateBuilder(BlockTags.STONE_PRESSURE_PLATES).add(blk);
        }

        for (Block blk : RankineLists.STONE_BRICKS) {
            getOrCreateBuilder(BlockTags.STONE_BRICKS).add(blk);
        }

        for (Block blk : RankineLists.STONES) {
            getOrCreateBuilder(RankineTags.Blocks.BASE_STONE_END).add(blk);
            getOrCreateBuilder(BlockTags.BASE_STONE_OVERWORLD).add(blk);
            getOrCreateBuilder(BlockTags.BASE_STONE_NETHER).add(blk);
            getOrCreateBuilder(RankineTags.Blocks.WG_STONE).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.BASE_STONE_END).add(Blocks.END_STONE);
        for (Block blk : RankineLists.POLISHED_STONES) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE).add(blk);
        }
        for (Block blk : RankineLists.INFESTED_STONES) {
            getOrCreateBuilder(Tags.Blocks.STONE).add(blk);
        }
        getOrCreateBuilder(Tags.Blocks.STONE).addTag(RankineTags.Blocks.POLISHED_STONE);
        for (Block blk : RankineLists.STONE_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_SLAB).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_SLAB).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_SLABS) {
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
        for (Block blk : RankineLists.STONE_WALLS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_WALL).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_WALLS) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_WALL).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_WALLS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_WALL).add(blk);
        }
        for (Block blk : RankineLists.STONE_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_VERTICAL_SLAB).add(blk);
        }
        for (Block blk : RankineLists.POLISHED_STONE_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.POLISHED_STONE_VERTICAL_SLAB).add(blk);
        }
        for (Block blk : RankineLists.STONE_BRICKS_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.STONE_BRICKS_VERTICAL_SLAB).add(blk);
        }

        getOrCreateBuilder(RankineTags.Blocks.GRASS).add(Blocks.GRASS, Blocks.FERN, RankineBlocks.SHORT_GRASS.get(), RankineBlocks.YELLOW_CLOVER.get(), RankineBlocks.WHITE_CLOVER.get(), RankineBlocks.RED_CLOVER.get(), RankineBlocks.CRIMSON_CLOVER.get());

        for (Block blk : Stream.of(RankineLists.LEAF_LITTERS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.LEAF_LITTERS).add(blk);
        }
        for (Block blk : Stream.of(RankineLists.GRASS_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.GRASS_BLOCKS).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.GRASS_BLOCKS).add(Blocks.GRASS_BLOCK);
        getOrCreateBuilder(RankineTags.Blocks.GRASS_BLOCKS).add(RankineBlocks.SOD_BLOCK.get());
        for (Block blk : Stream.of(RankineLists.PATH_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.PATH_BLOCKS).add(blk);
        }
        for (Block blk : Stream.of(RankineLists.MUD_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.MUD).add(blk);
        }
        for (Block blk : Stream.of(RankineLists.MYCELIUM_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.MYCELIUM).add(blk);
            getOrCreateBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(blk);
        }
        for (Block blk : Stream.of(RankineLists.PODZOL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(RankineTags.Blocks.PODZOL).add(blk);
            getOrCreateBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(blk);
        }
        for (Block blk : Stream.of(RankineLists.SOIL_BLOCKS, RankineLists.PODZOL_BLOCKS, RankineLists.MYCELIUM_BLOCKS, RankineLists.MUD_BLOCKS, RankineLists.GRASS_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            getOrCreateBuilder(Tags.Blocks.DIRT).add(blk);
        }
        getOrCreateBuilder(RankineTags.Blocks.FARMLAND).add(Blocks.FARMLAND,RankineBlocks.TILLED_SOIL.get());

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
        for (Block blk : RankineLists.SHEETMETAL_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.SHEETMETAL_VERTICAL_SALBS).add(blk);
        }
        for (Block blk : RankineLists.SHEETMETAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.SHEETMETAL_SALBS).add(blk);
        }
        for (Block blk : RankineLists.SHEETMETALS) {
            String name = blk.getRegistryName().getPath();
            String baseName = Arrays.asList(name.split("_sheetmetal")).get(0);
            getOrCreateBuilder(BlockTags.makeWrapperTag(new ResourceLocation("forge", "sheetmetals/"+baseName).toString())).add(blk);
            getOrCreateBuilder(RankineTags.Blocks.SHEETMETAL).addTag(BlockTags.makeWrapperTag(new ResourceLocation("forge", "sheetmetals/"+baseName).toString()));
        }

        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS_COAL).add(RankineBlocks.LIGNITE_BLOCK.get(),RankineBlocks.SUBBITUMINOUS_COAL_BLOCK.get(),RankineBlocks.BITUMINOUS_COAL_BLOCK.get(),RankineBlocks.ANTHRACITE_COAL_BLOCK.get());
        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS_DIAMOND).add(RankineBlocks.LONSDALEITE_DIAMOND_BLOCK.get());

        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_COKE).add(RankineBlocks.COKE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GRAPHITE).add(RankineBlocks.GRAPHITE_BLOCK.get());

        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_HYDROGEN).add(RankineBlocks.HYDROGEN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_HELIUM).add(RankineBlocks.HELIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LITHIUM).add(RankineBlocks.LITHIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BERYLLIUM).add(RankineBlocks.BERYLLIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BORON).add(RankineBlocks.BORON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CARBON).add(RankineBlocks.CARBON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NITROGEN).add(RankineBlocks.NITROGEN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_OXYGEN).add(RankineBlocks.OXYGEN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FLUORINE).add(RankineBlocks.FLUORINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NEON).add(RankineBlocks.NEON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SODIUM).add(RankineBlocks.SODIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM).add(RankineBlocks.MAGNESIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ALUMINUM).add(RankineBlocks.ALUMINUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SILICON).add(RankineBlocks.SILICON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS).add(RankineBlocks.PHOSPHORUS_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SULFUR).add(RankineBlocks.SULFUR_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CHLORINE).add(RankineBlocks.CHLORINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ARGON).add(RankineBlocks.ARGON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_POTASSIUM).add(RankineBlocks.POTASSIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CALCIUM).add(RankineBlocks.CALCIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SCANDIUM).add(RankineBlocks.SCANDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM).add(RankineBlocks.TITANIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_VANADIUM).add(RankineBlocks.VANADIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CHROMIUM).add(RankineBlocks.CHROMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MANGANESE).add(RankineBlocks.MANGANESE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_COBALT).add(RankineBlocks.COBALT_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL).add(RankineBlocks.NICKEL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_COPPER).add(RankineBlocks.COPPER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ZINC).add(RankineBlocks.ZINC_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GALLIUM).add(RankineBlocks.GALLIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GERMANIUM).add(RankineBlocks.GERMANIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ARSENIC).add(RankineBlocks.ARSENIC_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SELENIUM).add(RankineBlocks.SELENIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BROMINE).add(RankineBlocks.BROMINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_KRYPTON).add(RankineBlocks.KRYPTON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RUBIDIUM).add(RankineBlocks.RUBIDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_STRONTIUM).add(RankineBlocks.STRONTIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_YTTRIUM).add(RankineBlocks.YTTRIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ZIRCONIUM).add(RankineBlocks.ZIRCONIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NIOBIUM).add(RankineBlocks.NIOBIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MOLYBDENUM).add(RankineBlocks.MOLYBDENUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TECHNETIUM).add(RankineBlocks.TECHNETIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RUTHENIUM).add(RankineBlocks.RUTHENIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RHODIUM).add(RankineBlocks.RHODIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PALLADIUM).add(RankineBlocks.PALLADIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SILVER).add(RankineBlocks.SILVER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CADMIUM).add(RankineBlocks.CADMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_INDIUM).add(RankineBlocks.INDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TIN).add(RankineBlocks.TIN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ANTIMONY).add(RankineBlocks.ANTIMONY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TELLURIUM).add(RankineBlocks.TELLURIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_IODINE).add(RankineBlocks.IODINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_XENON).add(RankineBlocks.XENON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CESIUM).add(RankineBlocks.CESIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BARIUM).add(RankineBlocks.BARIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LANTHANUM).add(RankineBlocks.LANTHANUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CERIUM).add(RankineBlocks.CERIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PRASEODYMIUM).add(RankineBlocks.PRASEODYMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NEODYMIUM).add(RankineBlocks.NEODYMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PROMETHIUM).add(RankineBlocks.PROMETHIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SAMARIUM).add(RankineBlocks.SAMARIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_EUROPIUM).add(RankineBlocks.EUROPIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GADOLINIUM).add(RankineBlocks.GADOLINIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TERBIUM).add(RankineBlocks.TERBIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_DYSPROSIUM).add(RankineBlocks.DYSPROSIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_HOLMIUM).add(RankineBlocks.HOLMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ERBIUM).add(RankineBlocks.ERBIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_THULIUM).add(RankineBlocks.THULIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_YTTERBIUM).add(RankineBlocks.YTTERBIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LUTETIUM).add(RankineBlocks.LUTETIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_HAFNIUM).add(RankineBlocks.HAFNIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TANTALUM).add(RankineBlocks.TANTALUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN).add(RankineBlocks.TUNGSTEN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RHENIUM).add(RankineBlocks.RHENIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_OSMIUM).add(RankineBlocks.OSMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_IRIDIUM).add(RankineBlocks.IRIDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PLATINUM).add(RankineBlocks.PLATINUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MERCURY).add(RankineBlocks.MERCURY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_THALLIUM).add(RankineBlocks.THALLIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LEAD).add(RankineBlocks.LEAD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BISMUTH).add(RankineBlocks.BISMUTH_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_POLONIUM).add(RankineBlocks.POLONIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ASTATINE).add(RankineBlocks.ASTATINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RADON).add(RankineBlocks.RADON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FRANCIUM).add(RankineBlocks.FRANCIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RADIUM).add(RankineBlocks.RADIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ACTINIUM).add(RankineBlocks.ACTINIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_THORIUM).add(RankineBlocks.THORIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PROTACTINIUM).add(RankineBlocks.PROTACTINIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_URANIUM).add(RankineBlocks.URANIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM).add(RankineBlocks.NEPTUNIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PLUTONIUM).add(RankineBlocks.PLUTONIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_AMERICIUM).add(RankineBlocks.AMERICIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CURIUM).add(RankineBlocks.CURIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BERKELIUM).add(RankineBlocks.BERKELIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CALIFORNIUM).add(RankineBlocks.CALIFORNIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_EINSTEINIUM).add(RankineBlocks.EINSTEINIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FERMIUM).add(RankineBlocks.FERMIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MENDELEVIUM).add(RankineBlocks.MENDELEVIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NOBELIUM).add(RankineBlocks.NOBELIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LAWRENCIUM).add(RankineBlocks.LAWRENCIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RUTHERFORDIUM).add(RankineBlocks.RUTHERFORDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_DUBNIUM).add(RankineBlocks.DUBNIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SEABORGIUM).add(RankineBlocks.SEABORGIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BOHRIUM).add(RankineBlocks.BOHRIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_HASSIUM).add(RankineBlocks.HASSIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MEITNERIUM).add(RankineBlocks.MEITNERIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_DARMSTADTIUM).add(RankineBlocks.DARMSTADTIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ROENTGENIUM).add(RankineBlocks.ROENTGENIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_COPERNICIUM).add(RankineBlocks.COPERNICIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NIHONIUM).add(RankineBlocks.NIHONIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FLEROVIUM).add(RankineBlocks.FLEROVIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MOSCOVIUM).add(RankineBlocks.MOSCOVIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_LIVERMORIUM).add(RankineBlocks.LIVERMORIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TENNESSINE).add(RankineBlocks.TENNESSINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_OGANESSON).add(RankineBlocks.OGANESSON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ENDOSITUM).add(RankineBlocks.ENDOSITUM_BLOCK.get());

        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PEWTER).add(RankineBlocks.PEWTER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BRONZE).add(RankineBlocks.BRONZE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BRASS).add(RankineBlocks.BRASS_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CAST_IRON).add(RankineBlocks.CAST_IRON_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_INVAR).add(RankineBlocks.INVAR_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CUPRONICKEL).add(RankineBlocks.CUPRONICKEL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_DURALUMIN).add(RankineBlocks.DURALUMIN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM_ALLOY).add(RankineBlocks.MAGNESIUM_ALLOY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_STERLING_SILVER).add(RankineBlocks.STERLING_SILVER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SILVER).add(RankineBlocks.NICKEL_SILVER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ALNICO).add(RankineBlocks.ALNICO_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_STEEL).add(RankineBlocks.STEEL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_STAINLESS_STEEL).add(RankineBlocks.STAINLESS_STEEL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NITINOL).add(RankineBlocks.NITINOL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ROSE_METAL).add(RankineBlocks.ROSE_METAL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_MISCHMETAL).add(RankineBlocks.MISCHMETAL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FERROCERIUM).add(RankineBlocks.FERROCERIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GALINSTAN).add(RankineBlocks.GALINSTAN_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_OSMIRIDIUM).add(RankineBlocks.OSMIRIDIUM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SODIUM_POTASSIUM_ALLOY).add(RankineBlocks.SODIUM_POTASSIUM_ALLOY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_AMALGAM).add(RankineBlocks.AMALGAM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ENDER_AMALGAM).add(RankineBlocks.ENDER_AMALGAM_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD).add(RankineBlocks.ROSE_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GREEN_GOLD).add(RankineBlocks.GREEN_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PURPLE_GOLD).add(RankineBlocks.PURPLE_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_WHITE_GOLD).add(RankineBlocks.WHITE_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BLUE_GOLD).add(RankineBlocks.BLUE_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_BLACK_GOLD).add(RankineBlocks.BLACK_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SUPERALLOY).add(RankineBlocks.NICKEL_SUPERALLOY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_COBALT_SUPERALLOY).add(RankineBlocks.COBALT_SUPERALLOY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY).add(RankineBlocks.TUNGSTEN_HEAVY_ALLOY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM_ALLOY).add(RankineBlocks.TITANIUM_ALLOY_BLOCK.get());

        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_AMBER).add(RankineBlocks.AMBER_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_AQUAMARINE).add(RankineBlocks.AQUAMARINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_CINNABAR).add(RankineBlocks.CINNABAR_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_FLUORITE).add(RankineBlocks.FLUORITE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_GARNET).add(RankineBlocks.GARNET_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_OPAL).add(RankineBlocks.OPAL_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PERIDOT).add(RankineBlocks.PERIDOT_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_RUBY).add(RankineBlocks.RUBY_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_SAPPHIRE).add(RankineBlocks.SAPPHIRE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(RankineBlocks.TOPAZ_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_TOURMALINE).add(RankineBlocks.TOURMALINE_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.STORAGE_BLOCKS_PEARL).add(RankineBlocks.PEARL_BLOCK.get());

        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).addTags(RankineTags.Blocks.STORAGE_BLOCKS_COKE,RankineTags.Blocks.STORAGE_BLOCKS_GRAPHITE,RankineTags.Blocks.STORAGE_BLOCKS_HYDROGEN, RankineTags.Blocks.STORAGE_BLOCKS_HELIUM, RankineTags.Blocks.STORAGE_BLOCKS_LITHIUM, RankineTags.Blocks.STORAGE_BLOCKS_BERYLLIUM, RankineTags.Blocks.STORAGE_BLOCKS_BORON, RankineTags.Blocks.STORAGE_BLOCKS_CARBON, RankineTags.Blocks.STORAGE_BLOCKS_NITROGEN, RankineTags.Blocks.STORAGE_BLOCKS_OXYGEN, RankineTags.Blocks.STORAGE_BLOCKS_FLUORINE, RankineTags.Blocks.STORAGE_BLOCKS_NEON, RankineTags.Blocks.STORAGE_BLOCKS_SODIUM, RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM, RankineTags.Blocks.STORAGE_BLOCKS_ALUMINUM, RankineTags.Blocks.STORAGE_BLOCKS_SILICON, RankineTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS, RankineTags.Blocks.STORAGE_BLOCKS_SULFUR, RankineTags.Blocks.STORAGE_BLOCKS_CHLORINE, RankineTags.Blocks.STORAGE_BLOCKS_ARGON, RankineTags.Blocks.STORAGE_BLOCKS_POTASSIUM, RankineTags.Blocks.STORAGE_BLOCKS_CALCIUM, RankineTags.Blocks.STORAGE_BLOCKS_SCANDIUM, RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM, RankineTags.Blocks.STORAGE_BLOCKS_VANADIUM, RankineTags.Blocks.STORAGE_BLOCKS_CHROMIUM, RankineTags.Blocks.STORAGE_BLOCKS_MANGANESE, RankineTags.Blocks.STORAGE_BLOCKS_COBALT, RankineTags.Blocks.STORAGE_BLOCKS_NICKEL, RankineTags.Blocks.STORAGE_BLOCKS_COPPER, RankineTags.Blocks.STORAGE_BLOCKS_ZINC, RankineTags.Blocks.STORAGE_BLOCKS_GALLIUM, RankineTags.Blocks.STORAGE_BLOCKS_GERMANIUM, RankineTags.Blocks.STORAGE_BLOCKS_ARSENIC, RankineTags.Blocks.STORAGE_BLOCKS_SELENIUM, RankineTags.Blocks.STORAGE_BLOCKS_BROMINE, RankineTags.Blocks.STORAGE_BLOCKS_KRYPTON, RankineTags.Blocks.STORAGE_BLOCKS_RUBIDIUM, RankineTags.Blocks.STORAGE_BLOCKS_STRONTIUM, RankineTags.Blocks.STORAGE_BLOCKS_YTTRIUM, RankineTags.Blocks.STORAGE_BLOCKS_ZIRCONIUM, RankineTags.Blocks.STORAGE_BLOCKS_NIOBIUM, RankineTags.Blocks.STORAGE_BLOCKS_MOLYBDENUM, RankineTags.Blocks.STORAGE_BLOCKS_TECHNETIUM, RankineTags.Blocks.STORAGE_BLOCKS_RUTHENIUM, RankineTags.Blocks.STORAGE_BLOCKS_RHODIUM, RankineTags.Blocks.STORAGE_BLOCKS_PALLADIUM, RankineTags.Blocks.STORAGE_BLOCKS_SILVER, RankineTags.Blocks.STORAGE_BLOCKS_CADMIUM, RankineTags.Blocks.STORAGE_BLOCKS_INDIUM, RankineTags.Blocks.STORAGE_BLOCKS_TIN, RankineTags.Blocks.STORAGE_BLOCKS_ANTIMONY, RankineTags.Blocks.STORAGE_BLOCKS_TELLURIUM, RankineTags.Blocks.STORAGE_BLOCKS_IODINE, RankineTags.Blocks.STORAGE_BLOCKS_XENON, RankineTags.Blocks.STORAGE_BLOCKS_CESIUM, RankineTags.Blocks.STORAGE_BLOCKS_BARIUM, RankineTags.Blocks.STORAGE_BLOCKS_LANTHANUM, RankineTags.Blocks.STORAGE_BLOCKS_CERIUM, RankineTags.Blocks.STORAGE_BLOCKS_PRASEODYMIUM, RankineTags.Blocks.STORAGE_BLOCKS_NEODYMIUM, RankineTags.Blocks.STORAGE_BLOCKS_PROMETHIUM, RankineTags.Blocks.STORAGE_BLOCKS_SAMARIUM, RankineTags.Blocks.STORAGE_BLOCKS_EUROPIUM, RankineTags.Blocks.STORAGE_BLOCKS_GADOLINIUM, RankineTags.Blocks.STORAGE_BLOCKS_TERBIUM, RankineTags.Blocks.STORAGE_BLOCKS_DYSPROSIUM, RankineTags.Blocks.STORAGE_BLOCKS_HOLMIUM, RankineTags.Blocks.STORAGE_BLOCKS_ERBIUM, RankineTags.Blocks.STORAGE_BLOCKS_THULIUM, RankineTags.Blocks.STORAGE_BLOCKS_YTTERBIUM, RankineTags.Blocks.STORAGE_BLOCKS_LUTETIUM, RankineTags.Blocks.STORAGE_BLOCKS_HAFNIUM, RankineTags.Blocks.STORAGE_BLOCKS_TANTALUM, RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, RankineTags.Blocks.STORAGE_BLOCKS_RHENIUM, RankineTags.Blocks.STORAGE_BLOCKS_OSMIUM, RankineTags.Blocks.STORAGE_BLOCKS_IRIDIUM, RankineTags.Blocks.STORAGE_BLOCKS_PLATINUM, RankineTags.Blocks.STORAGE_BLOCKS_MERCURY, RankineTags.Blocks.STORAGE_BLOCKS_THALLIUM, RankineTags.Blocks.STORAGE_BLOCKS_LEAD, RankineTags.Blocks.STORAGE_BLOCKS_BISMUTH, RankineTags.Blocks.STORAGE_BLOCKS_POLONIUM, RankineTags.Blocks.STORAGE_BLOCKS_ASTATINE, RankineTags.Blocks.STORAGE_BLOCKS_RADON, RankineTags.Blocks.STORAGE_BLOCKS_FRANCIUM, RankineTags.Blocks.STORAGE_BLOCKS_RADIUM, RankineTags.Blocks.STORAGE_BLOCKS_ACTINIUM, RankineTags.Blocks.STORAGE_BLOCKS_THORIUM, RankineTags.Blocks.STORAGE_BLOCKS_PROTACTINIUM, RankineTags.Blocks.STORAGE_BLOCKS_URANIUM, RankineTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM, RankineTags.Blocks.STORAGE_BLOCKS_PLUTONIUM, RankineTags.Blocks.STORAGE_BLOCKS_AMERICIUM, RankineTags.Blocks.STORAGE_BLOCKS_CURIUM, RankineTags.Blocks.STORAGE_BLOCKS_BERKELIUM, RankineTags.Blocks.STORAGE_BLOCKS_CALIFORNIUM, RankineTags.Blocks.STORAGE_BLOCKS_EINSTEINIUM, RankineTags.Blocks.STORAGE_BLOCKS_FERMIUM, RankineTags.Blocks.STORAGE_BLOCKS_MENDELEVIUM, RankineTags.Blocks.STORAGE_BLOCKS_NOBELIUM, RankineTags.Blocks.STORAGE_BLOCKS_LAWRENCIUM, RankineTags.Blocks.STORAGE_BLOCKS_RUTHERFORDIUM, RankineTags.Blocks.STORAGE_BLOCKS_DUBNIUM, RankineTags.Blocks.STORAGE_BLOCKS_SEABORGIUM, RankineTags.Blocks.STORAGE_BLOCKS_BOHRIUM, RankineTags.Blocks.STORAGE_BLOCKS_HASSIUM, RankineTags.Blocks.STORAGE_BLOCKS_MEITNERIUM, RankineTags.Blocks.STORAGE_BLOCKS_DARMSTADTIUM, RankineTags.Blocks.STORAGE_BLOCKS_ROENTGENIUM, RankineTags.Blocks.STORAGE_BLOCKS_COPERNICIUM, RankineTags.Blocks.STORAGE_BLOCKS_NIHONIUM, RankineTags.Blocks.STORAGE_BLOCKS_FLEROVIUM, RankineTags.Blocks.STORAGE_BLOCKS_MOSCOVIUM, RankineTags.Blocks.STORAGE_BLOCKS_LIVERMORIUM, RankineTags.Blocks.STORAGE_BLOCKS_TENNESSINE, RankineTags.Blocks.STORAGE_BLOCKS_OGANESSON,RankineTags.Blocks.STORAGE_BLOCKS_ENDOSITUM, RankineTags.Blocks.STORAGE_BLOCKS_PEWTER,RankineTags.Blocks.STORAGE_BLOCKS_BRONZE,RankineTags.Blocks.STORAGE_BLOCKS_BRASS,RankineTags.Blocks.STORAGE_BLOCKS_CAST_IRON,RankineTags.Blocks.STORAGE_BLOCKS_INVAR,RankineTags.Blocks.STORAGE_BLOCKS_CUPRONICKEL,RankineTags.Blocks.STORAGE_BLOCKS_DURALUMIN,RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_STERLING_SILVER,RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SILVER,RankineTags.Blocks.STORAGE_BLOCKS_ALNICO,RankineTags.Blocks.STORAGE_BLOCKS_STEEL,RankineTags.Blocks.STORAGE_BLOCKS_STAINLESS_STEEL,RankineTags.Blocks.STORAGE_BLOCKS_NITINOL,RankineTags.Blocks.STORAGE_BLOCKS_ROSE_METAL,RankineTags.Blocks.STORAGE_BLOCKS_MISCHMETAL,RankineTags.Blocks.STORAGE_BLOCKS_FERROCERIUM,RankineTags.Blocks.STORAGE_BLOCKS_GALINSTAN,RankineTags.Blocks.STORAGE_BLOCKS_OSMIRIDIUM,RankineTags.Blocks.STORAGE_BLOCKS_SODIUM_POTASSIUM_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_AMALGAM,RankineTags.Blocks.STORAGE_BLOCKS_ENDER_AMALGAM,RankineTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_GREEN_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_PURPLE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_WHITE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_BLUE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_BLACK_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SUPERALLOY,RankineTags.Blocks.STORAGE_BLOCKS_COBALT_SUPERALLOY,RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_AQUAMARINE,RankineTags.Blocks.STORAGE_BLOCKS_CINNABAR,RankineTags.Blocks.STORAGE_BLOCKS_FLUORITE,RankineTags.Blocks.STORAGE_BLOCKS_GARNET,RankineTags.Blocks.STORAGE_BLOCKS_OPAL,RankineTags.Blocks.STORAGE_BLOCKS_PERIDOT,RankineTags.Blocks.STORAGE_BLOCKS_RUBY,RankineTags.Blocks.STORAGE_BLOCKS_SAPPHIRE,RankineTags.Blocks.STORAGE_BLOCKS_TOPAZ, RankineTags.Blocks.STORAGE_BLOCKS_TOURMALINE,RankineTags.Blocks.STORAGE_BLOCKS_PEARL);
        getOrCreateBuilder(RankineTags.Blocks.ALLOY_STORAGE_BLOCKS).addTags(RankineTags.Blocks.STORAGE_BLOCKS_PEWTER,RankineTags.Blocks.STORAGE_BLOCKS_BRONZE,RankineTags.Blocks.STORAGE_BLOCKS_BRASS,RankineTags.Blocks.STORAGE_BLOCKS_CAST_IRON,RankineTags.Blocks.STORAGE_BLOCKS_INVAR,RankineTags.Blocks.STORAGE_BLOCKS_CUPRONICKEL,RankineTags.Blocks.STORAGE_BLOCKS_DURALUMIN,RankineTags.Blocks.STORAGE_BLOCKS_MAGNESIUM_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_STERLING_SILVER,RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SILVER,RankineTags.Blocks.STORAGE_BLOCKS_ALNICO,RankineTags.Blocks.STORAGE_BLOCKS_STEEL,RankineTags.Blocks.STORAGE_BLOCKS_STAINLESS_STEEL,RankineTags.Blocks.STORAGE_BLOCKS_NITINOL,RankineTags.Blocks.STORAGE_BLOCKS_ROSE_METAL,RankineTags.Blocks.STORAGE_BLOCKS_MISCHMETAL,RankineTags.Blocks.STORAGE_BLOCKS_FERROCERIUM,RankineTags.Blocks.STORAGE_BLOCKS_GALINSTAN,RankineTags.Blocks.STORAGE_BLOCKS_OSMIRIDIUM,RankineTags.Blocks.STORAGE_BLOCKS_SODIUM_POTASSIUM_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_AMALGAM,RankineTags.Blocks.STORAGE_BLOCKS_ENDER_AMALGAM,RankineTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_GREEN_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_PURPLE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_WHITE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_BLUE_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_BLACK_GOLD,RankineTags.Blocks.STORAGE_BLOCKS_NICKEL_SUPERALLOY,RankineTags.Blocks.STORAGE_BLOCKS_COBALT_SUPERALLOY,RankineTags.Blocks.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY,RankineTags.Blocks.STORAGE_BLOCKS_TITANIUM_ALLOY);


        getOrCreateBuilder(Tags.Blocks.ORES_COAL).add(RankineBlocks.COAL_ORE.get(),RankineBlocks.LIGNITE_ORE.get(),RankineBlocks.SUBBITUMINOUS_ORE.get(),RankineBlocks.BITUMINOUS_ORE.get(),RankineBlocks.ANTHRACITE_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_DIAMOND).add(RankineBlocks.DIAMOND_ORE.get(),RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),RankineBlocks.LONSDALEITE_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_EMERALD).add(RankineBlocks.EMERALD_ORE.get(),RankineBlocks.BERYL_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_LAPIS).add(RankineBlocks.LAPIS_ORE.get(),RankineBlocks.LAZURITE_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_REDSTONE).add(RankineBlocks.REDSTONE_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_QUARTZ).add(RankineBlocks.NETHER_QUARTZ_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_GOLD).add(RankineBlocks.NETHER_GOLD_ORE.get(),RankineBlocks.GOLD_ORE.get(),RankineBlocks.NATIVE_GOLD_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES_IRON).add(RankineBlocks.IRON_ORE.get(),RankineBlocks.MAGNETITE_ORE.get(),RankineBlocks.HEMATITE_ORE.get(),RankineBlocks.PYRITE_ORE.get(),RankineBlocks.KAMACITE_ORE.get(),RankineBlocks.TAENITE_ORE.get(),RankineBlocks.TETRATAENITE_ORE.get(),RankineBlocks.ANTITAENITE_ORE.get(),RankineBlocks.BOG_IRON.get(),RankineBlocks.IRONSTONE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_COPPER).add(RankineBlocks.MALACHITE_ORE.get(),RankineBlocks.CHALCOCITE_ORE.get(),RankineBlocks.PORPHYRY_COPPER.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_LEAD).add(RankineBlocks.GALENA_ORE.get(),RankineBlocks.NATIVE_LEAD_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_SILVER).add(RankineBlocks.ACANTHITE_ORE.get(),RankineBlocks.NATIVE_SILVER_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_BISMUTH).add(RankineBlocks.BISMUTHINITE_ORE.get(),RankineBlocks.NATIVE_BISMUTH_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_TIN).add(RankineBlocks.CASSITERITE_ORE.get(),RankineBlocks.NATIVE_TIN_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_ALUMINUM).add(RankineBlocks.BAUXITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_TUNGSTEN).add(RankineBlocks.WOLFRAMITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_TITANIUM).add(RankineBlocks.ILMENITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_MAGNESIUM).add(RankineBlocks.MAGNESITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_MANGANESE).add(RankineBlocks.PYROLUSITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_STRONTIUM).add(RankineBlocks.CELESTINE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_ZINC).add(RankineBlocks.SPHALERITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_LITHIUM).add(RankineBlocks.PETALITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_SODIUM).add(RankineBlocks.CRYOLITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_ARSENIC).add(RankineBlocks.NATIVE_ARSENIC_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_GALLIUM).add(RankineBlocks.NATIVE_GALLIUM_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_INDIUM).add(RankineBlocks.NATIVE_INDIUM_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_SELENIUM).add(RankineBlocks.NATIVE_SELENIUM_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_TELLURIUM).add(RankineBlocks.NATIVE_TELLURIUM_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_SULFUR).add(RankineBlocks.NATIVE_SULFUR_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_MERCURY).add(RankineBlocks.CINNABAR_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_CHROMIUM).add(RankineBlocks.CHROMITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_COBALT).add(RankineBlocks.COBALTITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_NICKEL).add(RankineBlocks.PENTLANDITE_ORE.get(),RankineBlocks.INTERSPINIFEX_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_YTTRIUM).add(RankineBlocks.XENOTIME_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_ZIRCONIUM).add(RankineBlocks.BADDELEYITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_URANIUM).add(RankineBlocks.URANINITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_MOLYBDENUM).add(RankineBlocks.MOLYBDENITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_CADMIUM).add(RankineBlocks.GREENOCKITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_ANTIMONY).add(RankineBlocks.STIBNITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_RHENIUM).add(RankineBlocks.RHENIITE_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_PHOSPHORUS).add(RankineBlocks.PHOSPHORITE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_GRAPHITE).add(RankineBlocks.PLUMBAGO_ORE.get());
        getOrCreateBuilder(RankineTags.Blocks.ORES_SALT).add(RankineBlocks.HALITE_ORE.get());
        getOrCreateBuilder(Tags.Blocks.ORES).addTags(RankineTags.Blocks.ORES_COPPER,RankineTags.Blocks.ORES_LEAD,RankineTags.Blocks.ORES_SILVER,RankineTags.Blocks.ORES_BISMUTH,RankineTags.Blocks.ORES_TIN,RankineTags.Blocks.ORES_ALUMINUM,RankineTags.Blocks.ORES_TUNGSTEN,RankineTags.Blocks.ORES_TITANIUM,RankineTags.Blocks.ORES_MAGNESIUM,RankineTags.Blocks.ORES_MANGANESE,RankineTags.Blocks.ORES_STRONTIUM,RankineTags.Blocks.ORES_ZINC,RankineTags.Blocks.ORES_LITHIUM,RankineTags.Blocks.ORES_ARSENIC,RankineTags.Blocks.ORES_GALLIUM,RankineTags.Blocks.ORES_INDIUM,RankineTags.Blocks.ORES_SELENIUM,RankineTags.Blocks.ORES_TELLURIUM,RankineTags.Blocks.ORES_SODIUM,RankineTags.Blocks.ORES_SULFUR,RankineTags.Blocks.ORES_MERCURY,RankineTags.Blocks.ORES_CHROMIUM,RankineTags.Blocks.ORES_COBALT,RankineTags.Blocks.ORES_NICKEL,RankineTags.Blocks.ORES_YTTRIUM,RankineTags.Blocks.ORES_URANIUM,RankineTags.Blocks.ORES_MOLYBDENUM,RankineTags.Blocks.ORES_CADMIUM,RankineTags.Blocks.ORES_ANTIMONY,RankineTags.Blocks.ORES_RHENIUM,RankineTags.Blocks.ORES_PHOSPHORUS,RankineTags.Blocks.ORES_GRAPHITE,RankineTags.Blocks.ORES_SALT).add(RankineBlocks.COLTAN_ORE.get(),RankineBlocks.SPERRYLITE_ORE.get(),RankineBlocks.MONAZITE_ORE.get());



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


        getOrCreateBuilder(BlockTags.STAIRS).addTag(RankineTags.Blocks.BRICKS_STAIRS).addTag(RankineTags.Blocks.STONE_STAIRS).addTag(RankineTags.Blocks.POLISHED_STONE_STAIRS).addTag(RankineTags.Blocks.STONE_BRICKS_STAIRS);
        getOrCreateBuilder(BlockTags.WALLS).addTag(RankineTags.Blocks.BRICKS_WALL).addTag(RankineTags.Blocks.STONE_WALL).addTag(RankineTags.Blocks.POLISHED_STONE_WALL).addTag(RankineTags.Blocks.STONE_BRICKS_WALL);
        getOrCreateBuilder(BlockTags.SLABS).addTag(RankineTags.Blocks.BRICKS_SLAB).addTag(RankineTags.Blocks.STONE_SLAB).addTag(RankineTags.Blocks.POLISHED_STONE_SLAB).addTag(RankineTags.Blocks.STONE_BRICKS_SLAB);
        getOrCreateBuilder(RankineTags.Blocks.VERTICAL_SLABS).addTag(RankineTags.Blocks.BRICKS_VERTICAL_SLAB).addTag(RankineTags.Blocks.WOODEN_VERTICAL_SLABS).addTag(RankineTags.Blocks.SHEETMETAL_VERTICAL_SALBS).addTag(RankineTags.Blocks.STONE_VERTICAL_SLAB).addTag(RankineTags.Blocks.POLISHED_STONE_VERTICAL_SLAB).addTag(RankineTags.Blocks.STONE_BRICKS_VERTICAL_SLAB);
        for (Block blk : RankineLists.MISC_VERTICAL_SLABS) {
            getOrCreateBuilder(RankineTags.Blocks.VERTICAL_SLABS).add(blk);
        }
        for (Block blk : RankineLists.MISC_SLABS) {
            getOrCreateBuilder(BlockTags.SLABS).add(blk);
        }
        for (Block blk : RankineLists.MISC_STAIRS) {
            getOrCreateBuilder(BlockTags.STAIRS).add(blk);
        }
        for (Block blk : RankineLists.MISC_WALLS) {
            getOrCreateBuilder(BlockTags.WALLS).add(blk);
        }



        getOrCreateBuilder(BlockTags.BAMBOO_PLANTABLE_ON).addTags(Tags.Blocks.DIRT,Tags.Blocks.GRAVEL,Tags.Blocks.SAND).add(RankineBlocks.SILT.get(),RankineBlocks.DESERT_SAND.get());
        getOrCreateBuilder(BlockTags.ENDERMAN_HOLDABLE).addTags(Tags.Blocks.DIRT,Tags.Blocks.GRAVEL,Tags.Blocks.SAND).add(RankineBlocks.SILT.get(),RankineBlocks.DESERT_SAND.get(),RankineBlocks.KAOLIN.get(),RankineBlocks.FIRE_CLAY.get());
        getOrCreateBuilder(BlockTags.DRAGON_IMMUNE).addTags(RankineTags.Blocks.PETRIFIED_CHORUS_LOGS,RankineTags.Blocks.CHARRED_LOGS,RankineTags.Blocks.ERYTHRINA_LOGS);
        getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).addTags(RankineTags.Blocks.CEDAR_LOGS,RankineTags.Blocks.PINYON_PINE_LOGS,RankineTags.Blocks.JUNIPER_LOGS,RankineTags.Blocks.COCONUT_PALM_LOGS,RankineTags.Blocks.BALSAM_FIR_LOGS,RankineTags.Blocks.EASTERN_HEMLOCK_LOGS,RankineTags.Blocks.MAPLE_LOGS,RankineTags.Blocks.MAGNOLIA_LOGS,RankineTags.Blocks.SHARINGA_LOGS,RankineTags.Blocks.CORK_OAK_LOGS,RankineTags.Blocks.CINNAMON_LOGS,RankineTags.Blocks.BLACK_BIRCH_LOGS,RankineTags.Blocks.YELLOW_BIRCH_LOGS,RankineTags.Blocks.BLACK_WALNUT_LOGS,RankineTags.Blocks.CHARRED_LOGS,RankineTags.Blocks.ERYTHRINA_LOGS);
        getOrCreateBuilder(Tags.Blocks.GLASS).add(RankineBlocks.LIGHTNING_GLASS.get(),RankineBlocks.RED_LIGHTNING_GLASS.get(),RankineBlocks.SOUL_LIGHTNING_GLASS.get(),RankineBlocks.BLACK_LIGHTNING_GLASS.get(),RankineBlocks.WHITE_LIGHTNING_GLASS.get());
        getOrCreateBuilder(RankineTags.Blocks.NUGGET_STONES).addTags(RankineTags.Blocks.TUFF,RankineTags.Blocks.BASE_STONE_END,BlockTags.BASE_STONE_NETHER,BlockTags.BASE_STONE_OVERWORLD);
        getOrCreateBuilder(RankineTags.Blocks.LIGHTNING_VITRIFIED).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,Tags.Blocks.DIRT,Tags.Blocks.GRAVEL);
        getOrCreateBuilder(RankineTags.Blocks.METEORITE_REPLACEABLE).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,Tags.Blocks.DIRT,BlockTags.SAND,RankineTags.Blocks.TERRACOTTA);
        getOrCreateBuilder(RankineTags.Blocks.INTRUSION_PASSABLE).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,BlockTags.BASE_STONE_OVERWORLD,BlockTags.BASE_STONE_NETHER,RankineTags.Blocks.BASE_STONE_END,RankineTags.Blocks.TERRACOTTA).add(Blocks.SOUL_SAND,Blocks.SOUL_SOIL,Blocks.LAVA);
        getOrCreateBuilder(RankineTags.Blocks.FUMAROLE_DEPOSIT).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,Tags.Blocks.DIRT,Tags.Blocks.SAND,BlockTags.BASE_STONE_OVERWORLD,BlockTags.BASE_STONE_NETHER,RankineTags.Blocks.BASE_STONE_END,RankineTags.Blocks.TERRACOTTA).add(Blocks.SOUL_SAND,Blocks.SOUL_SOIL);
        getOrCreateBuilder(RankineTags.Blocks.FLOWER_SEEDS).add(Blocks.POPPY,Blocks.AZURE_BLUET,Blocks.DANDELION,Blocks.BLUE_ORCHID,Blocks.ORANGE_TULIP,Blocks.PINK_TULIP,Blocks.RED_TULIP,Blocks.WHITE_TULIP,Blocks.ALLIUM,Blocks.OXEYE_DAISY,Blocks.LILY_OF_THE_VALLEY,Blocks.CORNFLOWER);
        getOrCreateBuilder(RankineTags.Blocks.LUCK_PENDANT).addTags(RankineTags.Blocks.TUFF,Tags.Blocks.STONE,Tags.Blocks.DIRT,BlockTags.SAND,BlockTags.LOGS,RankineTags.Blocks.TERRACOTTA,Tags.Blocks.GRAVEL);
        getOrCreateBuilder(RankineTags.Blocks.GLASS_CUTTER).addTags(RankineTags.Blocks.HARDENED_GLASS,Tags.Blocks.GLASS,Tags.Blocks.GLASS_PANES);
        getOrCreateBuilder(BlockTags.CLIMBABLE).add(RankineBlocks.ROPE.get(),RankineBlocks.CAST_IRON_SUPPORT.get(),RankineBlocks.INVAR_LADDER.get(),RankineBlocks.CAST_IRON_LADDER.get(),RankineBlocks.DURALUMIN_LADDER.get(),RankineBlocks.BRASS_LADDER.get(),RankineBlocks.CUPRONICKEL_LADDER.get());
        getOrCreateBuilder(BlockTags.GUARDED_BY_PIGLINS).add(RankineBlocks.NATIVE_GOLD_ORE.get()).add(RankineBlocks.GOLD_SHEETMETAL.get()).add(RankineBlocks.GOLD_SHEETMETAL_VERTICAL_SLAB.get()).add(RankineBlocks.BLACK_GOLD_PEDESTAL.get()).add(RankineBlocks.PURPLE_GOLD_PEDESTAL.get()).add(RankineBlocks.ROSE_GOLD_PEDESTAL.get()).add(RankineBlocks.WHITE_GOLD_PEDESTAL.get()).add(RankineBlocks.GREEN_GOLD_PEDESTAL.get()).add(RankineBlocks.BLUE_GOLD_PEDESTAL.get()).add(RankineBlocks.BLACK_GOLD_BLOCK.get()).add(RankineBlocks.PURPLE_GOLD_BLOCK.get()).add(RankineBlocks.ROSE_GOLD_BLOCK.get()).add(RankineBlocks.WHITE_GOLD_BLOCK.get()).add(RankineBlocks.GREEN_GOLD_BLOCK.get()).add(RankineBlocks.BLUE_GOLD_BLOCK.get());
        getOrCreateBuilder(RankineTags.Blocks.TREE_LOGS).addTags(BlockTags.LOGS);
        getOrCreateBuilder(Tags.Blocks.COBBLESTONE).add(RankineBlocks.BRECCIA.get(),RankineBlocks.SKARN.get());
        getOrCreateBuilder(BlockTags.CROPS).add(RankineBlocks.JUTE_PLANT.get(),RankineBlocks.CORN_PLANT.get(),RankineBlocks.RICE_PLANT.get(),RankineBlocks.COTTON_PLANT.get(),RankineBlocks.ASPARAGUS_PLANT.get(),RankineBlocks.CAMPHOR_BASIL_PLANT.get(),RankineBlocks.ALOE_PLANT.get(),RankineBlocks.PINEAPPLE_BUSH.get(),RankineBlocks.CRANBERRY_BUSH.get(),RankineBlocks.POKEBERRY_BUSH.get(),RankineBlocks.BLUEBERRY_BUSH.get(),RankineBlocks.ELDERBERRY_BUSH.get(),RankineBlocks.STRAWBERRY_BUSH.get(),RankineBlocks.SNOWBERRY_BUSH.get(),RankineBlocks.RASPBERRY_BUSH.get(),RankineBlocks.BLACKBERRY_BUSH.get(),RankineBlocks.BANANA_YUCCA_BUSH.get(),RankineBlocks.RYE_PLANT.get(),RankineBlocks.SORGHUM_PLANT.get(),RankineBlocks.BARLEY_PLANT.get(),RankineBlocks.MILLET_PLANT.get(),RankineBlocks.OAT_PLANT.get(),RankineBlocks.SOYBEAN_PLANT.get());
        getOrCreateBuilder(BlockTags.BEE_GROWABLES).add(RankineBlocks.JUTE_PLANT.get(),RankineBlocks.CORN_PLANT.get(),RankineBlocks.RICE_PLANT.get(),RankineBlocks.COTTON_PLANT.get(),RankineBlocks.ASPARAGUS_PLANT.get(),RankineBlocks.CAMPHOR_BASIL_PLANT.get(),RankineBlocks.ALOE_PLANT.get(),RankineBlocks.PINEAPPLE_BUSH.get(),RankineBlocks.CRANBERRY_BUSH.get(),RankineBlocks.POKEBERRY_BUSH.get(),RankineBlocks.BLUEBERRY_BUSH.get(),RankineBlocks.ELDERBERRY_BUSH.get(),RankineBlocks.STRAWBERRY_BUSH.get(),RankineBlocks.SNOWBERRY_BUSH.get(),RankineBlocks.RASPBERRY_BUSH.get(),RankineBlocks.BLACKBERRY_BUSH.get(),RankineBlocks.BANANA_YUCCA_BUSH.get(),RankineBlocks.RYE_PLANT.get(),RankineBlocks.SORGHUM_PLANT.get(),RankineBlocks.BARLEY_PLANT.get(),RankineBlocks.MILLET_PLANT.get(),RankineBlocks.OAT_PLANT.get(),RankineBlocks.SOYBEAN_PLANT.get());
        getOrCreateBuilder(RankineTags.Blocks.TREE_LEAVES).addTags(BlockTags.LEAVES).add(Blocks.WARPED_WART_BLOCK,Blocks.NETHER_WART_BLOCK);
        getOrCreateBuilder(RankineTags.Blocks.HERBICIDAL).addTags(RankineTags.Blocks.LEAF_LITTERS,BlockTags.SMALL_FLOWERS,BlockTags.TALL_FLOWERS,BlockTags.CROPS,BlockTags.CORAL_PLANTS,BlockTags.WALL_CORALS,BlockTags.SAPLINGS).add(Blocks.GRASS,Blocks.TALL_GRASS,Blocks.FERN,Blocks.LARGE_FERN,Blocks.SEAGRASS,Blocks.TALL_SEAGRASS,Blocks.VINE,Blocks.TWISTING_VINES,Blocks.TWISTING_VINES_PLANT,Blocks.WEEPING_VINES_PLANT,Blocks.WEEPING_VINES,Blocks.LILY_PAD,Blocks.FIRE_CORAL_FAN,Blocks.BRAIN_CORAL_FAN,Blocks.BUBBLE_CORAL_FAN,Blocks.HORN_CORAL_FAN,Blocks.TUBE_CORAL_FAN,RankineBlocks.SHORT_GRASS.get(),RankineBlocks.STINGING_NETTLE.get(),RankineBlocks.RED_CLOVER.get(),RankineBlocks.CRIMSON_CLOVER.get(),RankineBlocks.WHITE_CLOVER.get(),RankineBlocks.YELLOW_CLOVER.get());


        getOrCreateBuilder(RankineTags.Blocks.WORLD_STRIP).addTags(RankineTags.Blocks.TUFF,BlockTags.LEAVES,BlockTags.LOGS,BlockTags.SAND,Tags.Blocks.DIRT,Tags.Blocks.GRAVEL,Tags.Blocks.STONE,BlockTags.BASE_STONE_OVERWORLD,BlockTags.BASE_STONE_NETHER,RankineTags.Blocks.BASE_STONE_END,RankineTags.Blocks.TERRACOTTA).add(Blocks.SOUL_SAND,Blocks.SOUL_SOIL,Blocks.LAVA,Blocks.WATER,Blocks.OBSIDIAN);
        getOrCreateBuilder(RankineTags.Blocks.VANILLA_OVERRIDE).add(Blocks.EMERALD_ORE,Blocks.DIAMOND_ORE,Blocks.IRON_ORE,Blocks.COAL_ORE,Blocks.LAPIS_ORE,Blocks.REDSTONE_ORE,Blocks.NETHER_GOLD_ORE,Blocks.NETHER_QUARTZ_ORE,Blocks.GOLD_ORE,Blocks.ANDESITE,Blocks.DIORITE,Blocks.GRANITE);


    }

}
