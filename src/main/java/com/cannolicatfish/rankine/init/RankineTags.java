package com.cannolicatfish.rankine.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public final class RankineTags {
    public static final class Blocks {
        //MOD
        public static final ITag.INamedTag<Block> NUGGET_STONES = mod("nugget_stones");
        public static final ITag.INamedTag<Block> GEODES = mod("geodes");
        public static final ITag.INamedTag<Block> LEDS = mod("leds");
        public static final ITag.INamedTag<Block> CEDAR_LOGS = mod("cedar_logs");
        public static final ITag.INamedTag<Block> PINYON_PINE_LOGS = mod("pinyon_pine_logs");
        public static final ITag.INamedTag<Block> JUNIPER_LOGS = mod("juniper_logs");
        public static final ITag.INamedTag<Block> COCONUT_PALM_LOGS = mod("coconut_palm_logs");
        public static final ITag.INamedTag<Block> BALSAM_FIR_LOGS = mod("balsam_fir_logs");
        public static final ITag.INamedTag<Block> EASTERN_HEMLOCK_LOGS = mod("eastern_hemlock_logs");
        public static final ITag.INamedTag<Block> MAPLE_LOGS = mod("maple_logs");
        public static final ITag.INamedTag<Block> BLACK_BIRCH_LOGS = mod("black_birch_logs");
        public static final ITag.INamedTag<Block> YELLOW_BIRCH_LOGS = mod("yellow_birch_logs");
        public static final ITag.INamedTag<Block> SHARINGA_LOGS = mod("sharinga_logs");
        public static final ITag.INamedTag<Block> CORK_OAK_LOGS = mod("cork_oak_logs");
        public static final ITag.INamedTag<Block> CINNAMON_LOGS = mod("cinnamon_logs");
        public static final ITag.INamedTag<Block> BLACK_WALNUT_LOGS = mod("black_walnut_logs");
        public static final ITag.INamedTag<Block> MAGNOLIA_LOGS = mod("magnolia_logs");
        public static final ITag.INamedTag<Block> PETRIFIED_CHORUS_LOGS = mod("petrified_chorus_logs");



        //FORGE
        public static final ITag.INamedTag<Block> CLAY = forge("clay");
        public static final ITag.INamedTag<Block> HARDENED_GLASS = forge("hardened_glass");
        public static final ITag.INamedTag<Block> TUFF = forge("tuff");
        public static final ITag.INamedTag<Block> TERRACOTTA = forge("terracotta");
        public static final ITag.INamedTag<Block> GLAZED_TERRACOTTA = forge("glazed_terracotta");
        public static final ITag.INamedTag<Block> CONCRETE_POWDER = forge("concrete_powder");
        public static final ITag.INamedTag<Block> CONCRETE = forge("concrete");
        public static final ITag.INamedTag<Block> MINERAL_WOOL = forge("mineral_wool");
        public static final ITag.INamedTag<Block> SHEETMETAL = forge("sheetmetal");
        public static final ITag.INamedTag<Block> SHEETMETAL_VERTICAL_SALBS = forge("sheetmetal_vertical_slabs");

        public static final ITag.INamedTag<Block> BRICKS = forge("bricks");
        public static final ITag.INamedTag<Block> BRICKS_SLAB = forge("bricks_slabs");
        public static final ITag.INamedTag<Block> BRICKS_STAIRS = forge("bricks_stairs");
        public static final ITag.INamedTag<Block> BRICKS_WALL = forge("bricks_walls");
        public static final ITag.INamedTag<Block> BRICKS_VERTICAL_SLAB = forge("bricks_vertical_slabs");

        public static final ITag.INamedTag<Block> WOODEN_VERTICAL_SLABS = forge("wooden_vertical_slabs");
        public static final ITag.INamedTag<Block> VERTICAL_SLABS = forge("vertical_slabs");

        public static final ITag.INamedTag<Block> POLISHED_STONE = forge("polished_stone");
        public static final ITag.INamedTag<Block> STONE_SLAB = forge("stone_slabs");
        public static final ITag.INamedTag<Block> POLISHED_STONE_SLAB = forge("polished_stone_slabs");
        public static final ITag.INamedTag<Block> STONE_BRICKS_SLAB = forge("stone_bricks_slabs");
        public static final ITag.INamedTag<Block> STONE_STAIRS = forge("stone_stairs");
        public static final ITag.INamedTag<Block> POLISHED_STONE_STAIRS = forge("polished_stone_stairs");
        public static final ITag.INamedTag<Block> STONE_BRICKS_STAIRS = forge("stone_bricks_stairs");
        public static final ITag.INamedTag<Block> STONE_WALL = forge("stone_walls");
        public static final ITag.INamedTag<Block> POLISHED_STONE_WALL = forge("polished_stone_walls");
        public static final ITag.INamedTag<Block> STONE_BRICKS_WALL = forge("stone_bricks_walls");
        public static final ITag.INamedTag<Block> STONE_VERTICAL_SLAB = forge("stone_vertical_slabs");
        public static final ITag.INamedTag<Block> POLISHED_STONE_VERTICAL_SLAB = forge("polished_stone_vertical_slabs");
        public static final ITag.INamedTag<Block> STONE_BRICKS_VERTICAL_SLAB = forge("stone_bricks_vertical_slabs");

        public static final ITag.INamedTag<Block> BASE_STONE_END = forge("base_stone_end");
        public static final ITag.INamedTag<Block> WG_STONE = forge("wg_stone");

        public static final ITag.INamedTag<Block> STONES_PEGMATITE = forge("stones/pegmatite");
        public static final ITag.INamedTag<Block> STONES_GRANITE = forge("stones/granite");
        public static final ITag.INamedTag<Block> STONES_RHYOLITE = forge("stones/rhyolite");
        public static final ITag.INamedTag<Block> STONES_GRANODIORITE = forge("stones/granodiorite");
        public static final ITag.INamedTag<Block> STONES_PORPHYRY = forge("stones/porphyry");
        public static final ITag.INamedTag<Block> STONES_ANDESITE = forge("stones/andesite");
        public static final ITag.INamedTag<Block> STONES_BASALT = forge("stones/basalt");
        public static final ITag.INamedTag<Block> STONES_DACITE = forge("stones/dacite");
        public static final ITag.INamedTag<Block> STONES_GABBRO = forge("stones/gabbro");
        public static final ITag.INamedTag<Block> STONES_KIMBERLITE = forge("stones/kimberlite");
        public static final ITag.INamedTag<Block> STONES_KOMATIITE = forge("stones/komatiite");
        public static final ITag.INamedTag<Block> STONES_PUMICE = forge("stones/pumice");
        public static final ITag.INamedTag<Block> STONES_SCORIA = forge("stones/scoria");
        public static final ITag.INamedTag<Block> STONES_SHONKINITE = forge("stones/shonkinite");
        public static final ITag.INamedTag<Block> STONES_PERIDOTITE = forge("stones/peridotite");
        public static final ITag.INamedTag<Block> STONES_MARBLE = forge("stones/marble");
        public static final ITag.INamedTag<Block> STONES_GNEISS = forge("stones/gneiss");
        public static final ITag.INamedTag<Block> STONES_SCHIST = forge("stones/schist");
        public static final ITag.INamedTag<Block> STONES_PHYLITE = forge("stones/phylite");
        public static final ITag.INamedTag<Block> STONES_SLATE = forge("stones/slate");
        public static final ITag.INamedTag<Block> STONES_SKARN = forge("stones/skarn");
        public static final ITag.INamedTag<Block> STONES_LIMESTONE = forge("stones/limestone");
        public static final ITag.INamedTag<Block> STONES_DOLOMITE = forge("stones/dolomite");
        public static final ITag.INamedTag<Block> STONES_CHALK = forge("stones/chalk");
        public static final ITag.INamedTag<Block> STONES_SHALE = forge("stones/shale");
        public static final ITag.INamedTag<Block> STONES_SILTSTONE = forge("stones/siltstone");
        public static final ITag.INamedTag<Block> STONES_BRECCIA = forge("stones/breccia");
        public static final ITag.INamedTag<Block> STONES_MUDSTONE = forge("stones/mudstone");
        public static final ITag.INamedTag<Block> STONES_SANDSTONE = forge("stones/sandstone");
        public static final ITag.INamedTag<Block> STONES_SERPENTINITE = forge("stones/serpetinite");
        public static final ITag.INamedTag<Block> STONES_MARLSTONE = forge("stones/marlstone");

        public static final ITag.INamedTag<Block> IGNEOUS_STONES = forge("igneous_stones");
        public static final ITag.INamedTag<Block> METAMORPHIC_STONES = forge("metamorphic_stones");
        public static final ITag.INamedTag<Block> SEDIMENTARY_STONES = forge("sedimentary_stones");


    }



    public static final class Items {
        public static final ITag.INamedTag<Item> GEODES = modItem("geodes");
        public static final ITag.INamedTag<Item> LEDS = modItem("leds");
        public static final ITag.INamedTag<Item> CEDAR_LOGS = modItem("cedar_logs");
        public static final ITag.INamedTag<Item> PINYON_PINE_LOGS = modItem("pinyon_pine_logs");
        public static final ITag.INamedTag<Item> JUNIPER_LOGS = modItem("juniper_logs");
        public static final ITag.INamedTag<Item> COCONUT_PALM_LOGS = modItem("coconut_palm_logs");
        public static final ITag.INamedTag<Item> BALSAM_FIR_LOGS = modItem("balsam_fir_logs");
        public static final ITag.INamedTag<Item> EASTERN_HEMLOCK_LOGS = modItem("eastern_hemlock_logs");
        public static final ITag.INamedTag<Item> MAPLE_LOGS = modItem("maple_logs");
        public static final ITag.INamedTag<Item> BLACK_BIRCH_LOGS = modItem("black_birch_logs");
        public static final ITag.INamedTag<Item> YELLOW_BIRCH_LOGS = modItem("yellow_birch_logs");
        public static final ITag.INamedTag<Item> SHARINGA_LOGS = modItem("sharinga_logs");
        public static final ITag.INamedTag<Item> CORK_OAK_LOGS = modItem("cork_oak_logs");
        public static final ITag.INamedTag<Item> CINNAMON_LOGS = modItem("cinnamon_logs");
        public static final ITag.INamedTag<Item> BLACK_WALNUT_LOGS = modItem("black_walnut_logs");
        public static final ITag.INamedTag<Item> MAGNOLIA_LOGS = modItem("magnolia_logs");
        public static final ITag.INamedTag<Item> PETRIFIED_CHORUS_LOGS = modItem("petrified_chorus_logs");
        //public static final ITag.INamedTag<Item> SCORCHED_LOGS = modItem("scorched_logs");

        public static final ITag.INamedTag<Item> FORAGING_ITEMS = modItem("foraging_items");
        public static final ITag.INamedTag<Item> COLORED_GOLD_TOOLS = modItem("colored_gold_tools");
        public static final ITag.INamedTag<Item> FORAGING_TOOLS = modItem("foraging_tools");





        //FORGE
        public static final ITag.INamedTag<Item> WOODEN_TOOLS = forgeItem("wooden_tools");
        public static final ITag.INamedTag<Item> STONE_TOOLS = forgeItem("stone_tools");
        public static final ITag.INamedTag<Item> IRON_TOOLS = forgeItem("iron_tools");
        public static final ITag.INamedTag<Item> GOLDEN_TOOLS = forgeItem("golden_tools");
        public static final ITag.INamedTag<Item> DIAMOND_TOOLS = forgeItem("diamond_tools");
        public static final ITag.INamedTag<Item> NETHERITE_TOOLS = forgeItem("netherite_tools");
        public static final ITag.INamedTag<Item> FLINT_TOOLS = forgeItem("flint_tools");
        public static final ITag.INamedTag<Item> PEWTER_TOOLS = forgeItem("pewter_tools");
        public static final ITag.INamedTag<Item> BRONZE_TOOLS = forgeItem("bronze_tools");
        public static final ITag.INamedTag<Item> AMALGAM_TOOLS = forgeItem("amalgam_tools");
        public static final ITag.INamedTag<Item> ALLOY_TOOLS = forgeItem("alloy_tools");
        public static final ITag.INamedTag<Item> INVAR_TOOLS = forgeItem("invar_tools");
        public static final ITag.INamedTag<Item> STEEL_TOOLS = forgeItem("steel_tools");
        public static final ITag.INamedTag<Item> STAINLESS_STEEL_TOOLS = forgeItem("stainless_steel_tools");
        public static final ITag.INamedTag<Item> TITANIUM_ALLOY_TOOLS = forgeItem("titanium_alloy_tools");
        public static final ITag.INamedTag<Item> COBALT_SUPERALLOY_TOOLS = forgeItem("cobalt_superalloy_tools");
        public static final ITag.INamedTag<Item> NICKEL_SUPERALLOY_TOOLS = forgeItem("nickel_superalloy_tools");
        public static final ITag.INamedTag<Item> TUNGSTEN_HEAVY_ALLOY_TOOLS = forgeItem("tungsten_heavy_alloy_tools");
        public static final ITag.INamedTag<Item> ROSE_GOLD_TOOLS = forgeItem("rose_gold_tools");
        public static final ITag.INamedTag<Item> WHITE_GOLD_TOOLS = forgeItem("white_gold_tools");
        public static final ITag.INamedTag<Item> GREEN_GOLD_TOOLS = forgeItem("green_gold_tools");
        public static final ITag.INamedTag<Item> PURPLE_GOLD_TOOLS = forgeItem("purple_gold_tools");
        public static final ITag.INamedTag<Item> BLUE_GOLD_TOOLS = forgeItem("blue_gold_tools");
        public static final ITag.INamedTag<Item> BLACK_GOLD_TOOLS = forgeItem("black_gold_tools");

        public static final ITag.INamedTag<Item> AXES = forgeItem("axes");
        public static final ITag.INamedTag<Item> PICKAXES = forgeItem("pickaxes");
        public static final ITag.INamedTag<Item> HOES = forgeItem("hoes");
        public static final ITag.INamedTag<Item> SHOVELS = forgeItem("shovels");
        public static final ITag.INamedTag<Item> SWORDS = forgeItem("swords");
        public static final ITag.INamedTag<Item> HAMMERS = forgeItem("hammers");
        public static final ITag.INamedTag<Item> SPEARS = forgeItem("spears");
        public static final ITag.INamedTag<Item> KNIVES = forgeItem("knives");
        public static final ITag.INamedTag<Item> CROWBARS = forgeItem("crowbars");
        public static final ITag.INamedTag<Item> FISHING_RODS = forgeItem("fishing_rods");
        public static final ITag.INamedTag<Item> BOWS = forgeItem("bows");
        public static final ITag.INamedTag<Item> SHIELDS = forgeItem("shields");


        public static final ITag.INamedTag<Item> SEEDS_FLOWER = forgeItem("seeds/flower");
        public static final ITag.INamedTag<Item> SEEDS_CORN = forgeItem("seeds/corn");
        public static final ITag.INamedTag<Item> SEEDS_ASPARAGUS = forgeItem("seeds/asparagus");
        public static final ITag.INamedTag<Item> SEEDS_JUTE = forgeItem("seeds/jute");
        public static final ITag.INamedTag<Item> SEEDS_RICE = forgeItem("seeds/rice");
        public static final ITag.INamedTag<Item> SEEDS_CAMPHOR_BASIL = forgeItem("seeds/camphor_basil");
        public static final ITag.INamedTag<Item> SEEDS_COTTON = forgeItem("seeds/cotton");







        public static final ITag.INamedTag<Item> FELDSPAR = forgeItem("feldspar");
        public static final ITag.INamedTag<Item> SALT = forgeItem("salt");
        public static final ITag.INamedTag<Item> COKE = forgeItem("coke");
        public static final ITag.INamedTag<Item> DOUGH = forgeItem("dough");
        public static final ITag.INamedTag<Item> GRAPHITE = forgeItem("graphite");
        public static final ITag.INamedTag<Item> ASH = forgeItem("ash");
        public static final ITag.INamedTag<Item> RUBBER = forgeItem("rubber");
        public static final ITag.INamedTag<Item> SAWDUST = forgeItem("sawdust");
        public static final ITag.INamedTag<Item> SLAG = forgeItem("slag");
        public static final ITag.INamedTag<Item> TRONA = forgeItem("trona");
        public static final ITag.INamedTag<Item> SALTPETER = forgeItem("saltpeter");
        public static final ITag.INamedTag<Item> SILICON = forgeItem("silicon");
        public static final ITag.INamedTag<Item> ASTATINE = forgeItem("astatine");
        public static final ITag.INamedTag<Item> SULFUR = forgeItem("sulfur");
        public static final ITag.INamedTag<Item> PHOSPHORUS = forgeItem("phosphorus");
        public static final ITag.INamedTag<Item> FLUORITE = forgeItem("fluorite");
        public static final ITag.INamedTag<Item> ROPE = forgeItem("rope");
        public static final ITag.INamedTag<Item> CHEESE = forgeItem("cheese");
        public static final ITag.INamedTag<Item> BREAD = forgeItem("bread");
        public static final ITag.INamedTag<Item> FLOUR = forgeItem("flour");
        public static final ITag.INamedTag<Item> CLAY_BALLS = forgeItem("clay_balls");
        public static final ITag.INamedTag<Item> RODS_GRAPHITE = forgeItem("rods/graphite");
        public static final ITag.INamedTag<Item> RODS_CARBON = forgeItem("rods/carbon");


        public static final ITag.INamedTag<Item> CLAY = forgeItem("clay");
        public static final ITag.INamedTag<Item> HARDENED_GLASS = forgeItem("hardened_glass");
        public static final ITag.INamedTag<Item> TUFF = forgeItem("tuff");
        public static final ITag.INamedTag<Item> TERRACOTTA = forgeItem("terracotta");
        public static final ITag.INamedTag<Item> GLAZED_TERRACOTTA = forgeItem("glazed_terracotta");
        public static final ITag.INamedTag<Item> CONCRETE_POWDER = forgeItem("concrete_powder");
        public static final ITag.INamedTag<Item> CONCRETE = forgeItem("concrete");
        public static final ITag.INamedTag<Item> MINERAL_WOOL = forgeItem("mineral_wool");
        public static final ITag.INamedTag<Item> SHEETMETAL = forgeItem("sheetmetal");
        public static final ITag.INamedTag<Item> SHEETMETAL_VERTICAL_SALBS = forgeItem("sheetmetal_vertical_slabs");

        public static final ITag.INamedTag<Item> BRICKS = forgeItem("bricks");
        public static final ITag.INamedTag<Item> BRICKS_SLABS = forgeItem("bricks_slabs");
        public static final ITag.INamedTag<Item> BRICKS_STAIRS = forgeItem("bricks_stairs");
        public static final ITag.INamedTag<Item> BRICKS_WALLS = forgeItem("bricks_walls");
        public static final ITag.INamedTag<Item> BRICKS_VERTICAL_SLABS = forgeItem("bricks_vertical_slabs");

        public static final ITag.INamedTag<Item> WOODEN_VERTICAL_SLABS = forgeItem("wooden_vertical_slabs");
        public static final ITag.INamedTag<Item> VERTICAL_SLABS = forgeItem("vertical_slabs");

        public static final ITag.INamedTag<Item> POLISHED_STONE = forgeItem("polished_stone");
        public static final ITag.INamedTag<Item> STONE_SLAB = forgeItem("stone_slabs");
        public static final ITag.INamedTag<Item> POLISHED_STONE_SLAB = forgeItem("polished_stone_slabs");
        public static final ITag.INamedTag<Item> STONE_BRICKS_SLAB = forgeItem("stone_bricks_slabs");
        public static final ITag.INamedTag<Item> STONE_STAIRS = forgeItem("stone_stairs");
        public static final ITag.INamedTag<Item> POLISHED_STONE_STAIRS = forgeItem("polished_stone_stairs");
        public static final ITag.INamedTag<Item> STONE_BRICKS_STAIRS = forgeItem("stone_bricks_stairs");
        public static final ITag.INamedTag<Item> STONE_WALL = forgeItem("stone_walls");
        public static final ITag.INamedTag<Item> POLISHED_STONE_WALL = forgeItem("polished_stone_walls");
        public static final ITag.INamedTag<Item> STONE_BRICKS_WALL = forgeItem("stone_bricks_walls");
        public static final ITag.INamedTag<Item> STONE_VERTICAL_SLAB = forgeItem("stone_vertical_slabs");
        public static final ITag.INamedTag<Item> POLISHED_STONE_VERTICAL_SLAB = forgeItem("polished_stone_vertical_slabs");
        public static final ITag.INamedTag<Item> STONE_BRICKS_VERTICAL_SLAB = forgeItem("stone_bricks_vertical_slabs");
        public static final ITag.INamedTag<Item> STONE_PRESSURE_PLATES = forgeItem("stone_pressure_plates");

        public static final ITag.INamedTag<Item> STONES_PEGMATITE = forgeItem("stones/pegmatite");
        public static final ITag.INamedTag<Item> STONES_GRANITE = forgeItem("stones/granite");
        public static final ITag.INamedTag<Item> STONES_RHYOLITE = forgeItem("stones/rhyolite");
        public static final ITag.INamedTag<Item> STONES_GRANODIORITE = forgeItem("stones/granodiorite");
        public static final ITag.INamedTag<Item> STONES_PORPHYRY = forgeItem("stones/porphyry");
        public static final ITag.INamedTag<Item> STONES_ANDESITE = forgeItem("stones/andesite");
        public static final ITag.INamedTag<Item> STONES_BASALT = forgeItem("stones/basalt");
        public static final ITag.INamedTag<Item> STONES_DACITE = forgeItem("stones/dacite");
        public static final ITag.INamedTag<Item> STONES_GABBRO = forgeItem("stones/gabbro");
        public static final ITag.INamedTag<Item> STONES_KIMBERLITE = forgeItem("stones/kimberlite");
        public static final ITag.INamedTag<Item> STONES_KOMATIITE = forgeItem("stones/komatiite");
        public static final ITag.INamedTag<Item> STONES_PUMICE = forgeItem("stones/pumice");
        public static final ITag.INamedTag<Item> STONES_SCORIA = forgeItem("stones/scoria");
        public static final ITag.INamedTag<Item> STONES_SHONKINITE = forgeItem("stones/shonkinite");
        public static final ITag.INamedTag<Item> STONES_PERIDOTITE = forgeItem("stones/peridotite");
        public static final ITag.INamedTag<Item> STONES_MARBLE = forgeItem("stones/marble");
        public static final ITag.INamedTag<Item> STONES_GNEISS = forgeItem("stones/gneiss");
        public static final ITag.INamedTag<Item> STONES_SCHIST = forgeItem("stones/schist");
        public static final ITag.INamedTag<Item> STONES_PHYLITE = forgeItem("stones/phylite");
        public static final ITag.INamedTag<Item> STONES_SLATE = forgeItem("stones/slate");
        public static final ITag.INamedTag<Item> STONES_SKARN = forgeItem("stones/skarn");
        public static final ITag.INamedTag<Item> STONES_LIMESTONE = forgeItem("stones/limestone");
        public static final ITag.INamedTag<Item> STONES_DOLOMITE = forgeItem("stones/dolomite");
        public static final ITag.INamedTag<Item> STONES_CHALK = forgeItem("stones/chalk");
        public static final ITag.INamedTag<Item> STONES_SHALE = forgeItem("stones/shale");
        public static final ITag.INamedTag<Item> STONES_SILTSTONE = forgeItem("stones/siltstone");
        public static final ITag.INamedTag<Item> STONES_BRECCIA = forgeItem("stones/breccia");
        public static final ITag.INamedTag<Item> STONES_MUDSTONE = forgeItem("stones/mudstone");
        public static final ITag.INamedTag<Item> STONES_SANDSTONE = forgeItem("stones/sandstone");
        public static final ITag.INamedTag<Item> STONES_SERPENTINITE = forgeItem("stones/serpetinite");
        public static final ITag.INamedTag<Item> STONES_MARLSTONE = forgeItem("stones/marlstone");

        public static final ITag.INamedTag<Item> IGNEOUS_STONES = forgeItem("igneous_stones");
        public static final ITag.INamedTag<Item> METAMORPHIC_STONES = forgeItem("metamorphic_stones");
        public static final ITag.INamedTag<Item> SEDIMENTARY_STONES = forgeItem("sedimentary_stones");


    }
    //private Blocks() {}
    private static ITag.INamedTag<Block> forge(String path) {
        return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
    }
    private static ITag.INamedTag<Block> mod(String path) {
        return BlockTags.makeWrapperTag(new ResourceLocation("rankine", path).toString());
    }
    private static ITag.INamedTag<Item> forgeItem(String path) {
        return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
    }
    private static ITag.INamedTag<Item> modItem(String path) {
        return ItemTags.makeWrapperTag(new ResourceLocation("rankine", path).toString());
    }



    //private ModTags() {}
}
