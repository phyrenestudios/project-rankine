package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineLangProvider extends LanguageProvider {
    private final String locale;
    public RankineLangProvider(DataGenerator gen, String locale) {
        super(gen, ProjectRankine.MODID, locale);
        this.locale = locale;
    }

    @Override
    public String getName() {
        return "Rankine Lang: " + locale;
    }

    @Override
    protected void addTranslations() {
        for (Block blk : Stream.of(
                RankineLists.STONE,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_BRICKS,
                RankineLists.STONE_SLAB,
                RankineLists.POLISHED_STONE_SLAB,
                RankineLists.STONE_BRICKS_SLAB,
                RankineLists.STONE_VERTICAL_SLAB,
                RankineLists.POLISHED_STONE_VERTICAL_SLAB,
                RankineLists.STONE_BRICKS_VERTICAL_SLAB,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALL,
                RankineLists.POLISHED_STONE_WALL,
                RankineLists.STONE_BRICKS_WALL,
                RankineLists.STONE_PRESSURE_PLATE,
                RankineLists.STONE_BRICKS_PRESSURE_PLATE,
                RankineLists.STONE_BUTTON,
                RankineLists.STONE_PILLARS,
                RankineLists.BRICKS,
                RankineLists.BRICKS_SLAB,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_VERTICAL_SLAB,
                RankineLists.BRICKS_WALL,
                RankineLists.SHEETMETALS,
                RankineLists.SHEETMETAL_VERTICAL_SLAB,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.FLOWER_POTS,
                RankineLists.TALL_FLOWERS,
                RankineLists.SAPLINGS,
                RankineLists.LEAVES,
                RankineLists.PLANKS,
                RankineLists.LOGS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.WOODEN_SLABS,
                RankineLists.WOODEN_STAIRS,
                RankineLists.WOODEN_VERTICAL_SLABS,
                RankineLists.WOODEN_DOORS,
                RankineLists.WOODEN_TRAPDOORS,
                RankineLists.WOODEN_FENCES,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.WOODEN_BUTTONS,
                RankineLists.WOODEN_PRESSURE_PLATES,
                RankineLists.MINERAL_STONES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_DOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.CROPS_SINGLE,
                RankineLists.CROPS_DOUBLE,
                RankineLists.CROPS_TRIPLE,
                RankineLists.BUSH_PLANTS,
                RankineLists.DOUBLE_BUSH_PLANTS,
                RankineLists.EIGHT_LAYER_BLOCKS,
                RankineLists.FLUID_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.SOILS,
                RankineLists.GRASSY_SOILS,
                RankineLists.PATH_BLOCKS,
                RankineLists.NATIVE_ORES,
                RankineLists.CRUSHING_ORES,
                RankineLists.SPECIAL_ORES,
                RankineLists.MINERAL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            add(blk, parseLangName(blk.getRegistryName().getPath()));
        }

        for (Item item : Stream.of(
                RankineLists.WOODEN_BOATS,
                RankineLists.ARROWS,
                RankineLists.ALLOY_TOOLS,
                RankineLists.FLINT_TOOLS,
                RankineLists.BRONZE_TOOLS,
                RankineLists.PEWTER_TOOLS,
                RankineLists.INVAR_TOOLS,
                RankineLists.STEEL_TOOLS,
                RankineLists.STAINLESS_STEEL_TOOLS,
                RankineLists.COBALT_SUPERALLOY_TOOLS,
                RankineLists.NICKEL_SUPERALLOY_TOOLS,
                RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS,
                RankineLists.ROSE_GOLD_TOOLS,
                RankineLists.WHITE_GOLD_TOOLS,
                RankineLists.BLUE_GOLD_TOOLS,
                RankineLists.GREEN_GOLD_TOOLS,
                RankineLists.BLACK_GOLD_TOOLS,
                RankineLists.PURPLE_GOLD_TOOLS,
                RankineLists.AMALGAM_TOOLS,
                RankineLists.TITANIUM_ALLOY_TOOLS,
                RankineLists.ELEMENT_INGOTS,
                RankineLists.ELEMENT_NUGGETS,
                RankineLists.ALLOYS,
                RankineLists.GAS_BOTTLES,
                RankineLists.MINERAL_ITEMS,
                RankineLists.JAMS,
                RankineLists.GRAINS).flatMap(Collection::stream).collect(Collectors.toList())) {
            add(item, parseLangName(item.getRegistryName().getPath()));
        }
    }

    private String parseLangName(String registryName) {
        String LangName = "";
        for (String s : registryName.split("_")) {
            if (LangName.equals("")) {
                LangName = s.substring(0,1).toUpperCase() + s.substring(1);
            } else {
                LangName = LangName + " " + s.substring(0,1).toUpperCase() + s.substring(1);
            }
        }
        return LangName;
    }


}
