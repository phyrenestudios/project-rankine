package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.*;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnace;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceContainer;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForge;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeContainer;
import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeTile;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPit;
import com.cannolicatfish.rankine.blocks.crucible.Crucible;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForge;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeContainer;
import com.cannolicatfish.rankine.blocks.fineryforge.FineryForgeTile;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusher;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherContainer;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherTile;
import com.cannolicatfish.rankine.fluids.ModFluids;
import com.cannolicatfish.rankine.items.DuckweedItem;
import com.cannolicatfish.rankine.items.FuelBlockItem;
import com.cannolicatfish.rankine.items.ModFoods;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.world.trees.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.BiFunction;

public class ModBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ProjectRankine.MODID);

    //Register a block without an item, add("name", new Block(...))
    private static <T extends Block> T add(String name, T block) {
        return add(name, block, null);
    }

    //Register a block with an item, add("name", new Block(...), new Item.Properties())
    private static <T extends Block> T add(String name, T block, Item.Properties properties) {
        return add(name, block, properties, BlockItem::new);
    }

    //Register a block with a custom item, add("name", new Block(...), new Item.Properties(), ItemName::new)
    private static <T extends Block> T add(String name, T block, Item.Properties properties, BiFunction<Block, Item.Properties, BlockItem> itemConstructor) {
        REGISTRY.register(name, () -> block);
        if (itemConstructor != null && properties != null) ModItems.REGISTRY.register(name, () -> itemConstructor.apply(block, properties));
        return block;
    }

    //Register a block with a FuelBlockItem, add("name", new Block(...), new Item.Properties(), fuelValue)
    private static <T extends Block> T add(String name, T block, Item.Properties properties, int fuelValue) {
        REGISTRY.register(name, () -> block);
        if (properties != null) ModItems.REGISTRY.register(name, () -> new FuelBlockItem(block, properties, fuelValue));
        return block;
    }

    //Register a block with a BlockNamedItem, add("name", "itemName", new Block(...), new Item.Properties(), BlockNamedItem::new)
    private static <T extends Block> T add(String name, String itemName, T block, Item.Properties properties, BiFunction<Block, Item.Properties, BlockItem> itemConstructor) {
        REGISTRY.register(name, () -> block);
        if (itemConstructor != null && properties != null) ModItems.REGISTRY.register(itemName, () -> itemConstructor.apply(block, properties));
        return block;
    }


    //Base Properties
    public static Block.Properties DEF_STONE = Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F);
    public static Block.Properties DEF_ORE = Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 3.0F);
    public static Block.Properties DEF_METAL_BLOCK = Block.Properties.create(Material.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.0F,6.0F).sound(SoundType.METAL).harvestLevel(0);
    public static Block.Properties DEF_WOOD = Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 3.0F).harvestLevel(0);
    public static Block.Properties DEF_LEAVES = Block.Properties.create(Material.LEAVES).tickRandomly().hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid();

    //Creative Tabs
    public static Item.Properties BLOCKS = new Item.Properties().group(ProjectRankine.setup.rankineBlocks);
    public static Item.Properties METALLURGY = new Item.Properties().group(ProjectRankine.setup.rankineMetals);
    public static Item.Properties MISC = new Item.Properties().group(ProjectRankine.setup.rankineTools);
    //public static Item.Properties MAGIC = new Item.Properties().group(ProjectRankine.setup.rankineMagic);




//-------------------------------------
//BLOCKS CREATIVE TAB
//-------------------------------------

    //Stone Blocks
    public static final Block RED_GRANITE_BRICKS = add("red_granite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block QUARTZ_DIORITE_BRICKS = add("quartz_diorite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRAY_ANDESITE_BRICKS = add("gray_andesite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE = add("granite", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_GRANITE = add("smooth_granite", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_BRICKS = add("granite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE = add("diorite", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_DIORITE = add("smooth_diorite", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_BRICKS = add("diorite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE = add("andesite", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANDESITE = add("smooth_andesite", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_BRICKS = add("andesite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE = add("limestone", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_LIMESTONE = add("smooth_limestone", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_BRICKS = add("limestone_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE = add("shale", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_SHALE = add("smooth_shale", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_BRICKS = add("shale_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE = add("anorthosite", new RankineStone(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANORTHOSITE = add("smooth_anorthosite", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_BRICKS = add("anorthosite_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block IRONSTONE = add("ironstone", new RankineStone(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_IRONSTONE = add("smooth_ironstone", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block IRONSTONE_BRICKS = add("ironstone_bricks", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block BASALT = add("basalt", new RankineStone(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_BASALT = add("smooth_basalt", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block BASALT_BRICKS = add("basalt_bricks", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE = add("rhyolite", new RankineStone(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_RHYOLITE = add("smooth_rhyolite", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_BRICKS = add("rhyolite_bricks", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE = add("marble", new RankineStone(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_MARBLE = add("smooth_marble", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_BRICKS = add("marble_bricks", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS = add("gneiss", new RankineStone(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_GNEISS = add("smooth_gneiss", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_BRICKS = add("gneiss_bricks", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block PERIDOTITE = add("peridotite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PERIDOTITE = add("smooth_peridotite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PERIDOTITE_BRICKS = add("peridotite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE = add("ringwoodite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_RINGWOODITE = add("smooth_ringwoodite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_BRICKS = add("ringwoodite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE = add("wadsleyite",new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_WADSLEYITE = add("smooth_wadsleyite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_BRICKS = add("wadsleyite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE = add("bridgmanite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_BRIDGMANITE = add("smooth_bridgmanite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_BRICKS = add("bridgmanite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE = add("komatiite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KOMATIITE = add("smooth_komatiite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_BRICKS = add("komatiite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE = add("kimberlite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KIMBERLITE = add("smooth_kimberlite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_BRICKS = add("kimberlite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE = add("ferropericlase", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_FERROPERICLASE = add("smooth_ferropericlase", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_BRICKS = add("ferropericlase_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE = add("perovskite", new RankineStone(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PEROVSKITE = add("smooth_perovskite", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_BRICKS = add("perovskite_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PUMICE = add("pumice", new RankineStone(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_PUMICE = add("smooth_pumice", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block PUMICE_BRICKS = add("pumice_bricks", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA = add("scoria", new RankineStone(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_SCORIA = add("smooth_scoria", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_BRICKS = add("scoria_bricks", new Block(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block CLAY_BRICKS = add("clay_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block REFRACTORY_BRICKS = add("refractory_bricks", new Block(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block MAGNESIUM_REFRACTORY_BRICKS = add("magnesium_refractory_bricks", new Block(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block ZIRCON_REFRACTORY_BRICKS = add("zircon_refractory_bricks", new Block(DEF_STONE.harvestLevel(3)), BLOCKS);

    //Stone Slabs
    public static final Block RED_GRANITE_BRICKS_SLAB = add("red_granite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block QUARTZ_DIORITE_BRICKS_SLAB = add("quartz_diorite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRAY_ANDESITE_BRICKS_SLAB = add("gray_andesite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_SLAB = add("granite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_GRANITE_SLAB = add("smooth_granite_slab",new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_BRICKS_SLAB = add("granite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_SLAB = add("diorite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_DIORITE_SLAB = add("smooth_diorite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_BRICKS_SLAB = add("diorite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_SLAB = add("andesite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANDESITE_SLAB = add("smooth_andesite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_BRICKS_SLAB = add("andesite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_SLAB = add("limestone_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_LIMESTONE_SLAB = add("smooth_limestone_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_BRICKS_SLAB = add("limestone_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_SLAB = add("shale_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_SHALE_SLAB = add("smooth_shale_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_BRICKS_SLAB = add("shale_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_SLAB = add("anorthosite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANORTHOSITE_SLAB = add("smooth_anorthosite_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_BRICKS_SLAB = add("anorthosite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block IRONSTONE_SLAB = add("ironstone_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_IRONSTONE_SLAB = add("smooth_ironstone_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block IRONSTONE_BRICKS_SLAB = add("ironstone_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block BASALT_SLAB = add("basalt_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_BASALT_SLAB = add("smooth_basalt_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block BASALT_BRICKS_SLAB = add("basalt_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_SLAB = add("rhyolite_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_RHYOLITE_SLAB = add("smooth_rhyolite_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_BRICKS_SLAB = add("rhyolite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_SLAB = add("marble_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_MARBLE_SLAB = add("smooth_marble_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_BRICKS_SLAB = add("marble_bricks_slab",new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_SLAB = add("gneiss_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_GNEISS_SLAB = add("smooth_gneiss_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_BRICKS_SLAB = add("gneiss_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block PERIDOTITE_SLAB = add("peridotite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PERIDOTITE_SLAB = add("smooth_peridotite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PERIDOTITE_BRICKS_SLAB = add("peridotite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_SLAB = add("ringwoodite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_RINGWOODITE_SLAB = add("smooth_ringwoodite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_BRICKS_SLAB = add("ringwoodite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_SLAB = add("wadsleyite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_WADSLEYITE_SLAB = add("smooth_wadsleyite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_BRICKS_SLAB = add("wadsleyite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_SLAB = add("bridgmanite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_BRIDGMANITE_SLAB = add("smooth_bridgmanite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_BRICKS_SLAB = add("bridgmanite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_SLAB = add("komatiite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KOMATIITE_SLAB = add("smooth_komatiite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_BRICKS_SLAB = add("komatiite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_SLAB = add("kimberlite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KIMBERLITE_SLAB = add("smooth_kimberlite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_BRICKS_SLAB = add("kimberlite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_SLAB = add("ferropericlase_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_FERROPERICLASE_SLAB = add("smooth_ferropericlase_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_BRICKS_SLAB = add("ferropericlase_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_SLAB = add("perovskite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PEROVSKITE_SLAB = add("smooth_perovskite_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_BRICKS_SLAB = add("perovskite_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PUMICE_SLAB = add("pumice_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_PUMICE_SLAB = add("smooth_pumice_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block PUMICE_BRICKS_SLAB = add("pumice_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_SLAB = add("scoria_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_SCORIA_SLAB = add("smooth_scoria_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_BRICKS_SLAB = add("scoria_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block CLAY_BRICKS_SLAB = add("clay_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block REFRACTORY_BRICKS_SLAB = add("refractory_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block MAGNESIUM_REFRACTORY_BRICKS_SLAB = add("magnesium_refractory_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block ZIRCON_REFRACTORY_BRICKS_SLAB = add("zircon_refractory_bricks_slab", new RankineSlab(DEF_STONE.harvestLevel(3)), BLOCKS);

    //Stone Stairs
    public static final Block RED_GRANITE_BRICKS_STAIRS = add("red_granite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block QUARTZ_DIORITE_BRICKS_STAIRS = add("quartz_diorite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRAY_ANDESITE_BRICKS_STAIRS = add("gray_andesite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_STAIRS = add("granite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_GRANITE_STAIRS = add("smooth_granite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_BRICKS_STAIRS = add("granite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_STAIRS = add("diorite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_DIORITE_STAIRS = add("smooth_diorite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_BRICKS_STAIRS = add("diorite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_STAIRS = add("andesite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANDESITE_STAIRS = add("smooth_andesite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_BRICKS_STAIRS = add("andesite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_STAIRS = add("limestone_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_LIMESTONE_STAIRS = add("smooth_limestone_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_BRICKS_STAIRS = add("limestone_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_STAIRS = add("shale_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_SHALE_STAIRS = add("smooth_shale_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_BRICKS_STAIRS = add("shale_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_STAIRS = add("anorthosite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANORTHOSITE_STAIRS = add("smooth_anorthosite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_BRICKS_STAIRS = add("anorthosite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block IRONSTONE_STAIRS = add("ironstone_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_IRONSTONE_STAIRS = add("smooth_ironstone_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block IRONSTONE_BRICKS_STAIRS = add("ironstone_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block BASALT_STAIRS = add("basalt_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_BASALT_STAIRS = add("smooth_basalt_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block BASALT_BRICKS_STAIRS = add("basalt_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_STAIRS = add("rhyolite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_RHYOLITE_STAIRS = add("smooth_rhyolite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_BRICKS_STAIRS = add("rhyolite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_STAIRS = add("marble_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_MARBLE_STAIRS = add("smooth_marble_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_BRICKS_STAIRS = add("marble_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_STAIRS = add("gneiss_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_GNEISS_STAIRS = add("smooth_gneiss_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_BRICKS_STAIRS = add("gneiss_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block PERIDOTITE_STAIRS = add("peridotite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PERIDOTITE_STAIRS = add("smooth_peridotite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PERIDOTITE_BRICKS_STAIRS = add("peridotite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_STAIRS = add("ringwoodite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_RINGWOODITE_STAIRS = add("smooth_ringwoodite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_BRICKS_STAIRS = add("ringwoodite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_STAIRS = add("wadsleyite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_WADSLEYITE_STAIRS = add("smooth_wadsleyite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_BRICKS_STAIRS = add("wadsleyite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_STAIRS = add("bridgmanite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_BRIDGMANITE_STAIRS = add("smooth_bridgmanite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_BRICKS_STAIRS = add("bridgmanite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_STAIRS = add("komatiite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KOMATIITE_STAIRS = add("smooth_komatiite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_BRICKS_STAIRS = add("komatiite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_STAIRS = add("kimberlite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KIMBERLITE_STAIRS = add("smooth_kimberlite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_BRICKS_STAIRS = add("kimberlite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_STAIRS = add("ferropericlase_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_FERROPERICLASE_STAIRS = add("smooth_ferropericlase_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_BRICKS_STAIRS = add("ferropericlase_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_STAIRS = add("perovskite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PEROVSKITE_STAIRS = add("smooth_perovskite_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_BRICKS_STAIRS = add("perovskite_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PUMICE_STAIRS = add("pumice_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_PUMICE_STAIRS = add("smooth_pumice_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block PUMICE_BRICKS_STAIRS = add("pumice_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_STAIRS = add("scoria_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_SCORIA_STAIRS = add("smooth_scoria_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_BRICKS_STAIRS = add("scoria_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block CLAY_BRICKS_STAIRS = add("clay_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block REFRACTORY_BRICKS_STAIRS = add("refractory_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block MAGNESIUM_REFRACTORY_BRICKS_STAIRS = add("magnesium_refractory_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block ZIRCON_REFRACTORY_BRICKS_STAIRS = add("zircon_refractory_bricks_stairs", new RankineStairs(Block.getStateById(0),DEF_STONE.harvestLevel(3)), BLOCKS);
    //Stone Walls
    public static final Block RED_GRANITE_BRICKS_WALL = add("red_granite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block QUARTZ_DIORITE_BRICKS_WALL = add("quartz_diorite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRAY_ANDESITE_BRICKS_WALL = add("gray_andesite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_WALL = add("granite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_GRANITE_WALL = add("smooth_granite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block GRANITE_BRICKS_WALL = add("granite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_WALL = add("diorite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_DIORITE_WALL = add("smooth_diorite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block DIORITE_BRICKS_WALL = add("diorite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_WALL = add("andesite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANDESITE_WALL = add("smooth_andesite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANDESITE_BRICKS_WALL = add("andesite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_WALL = add("limestone_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_LIMESTONE_WALL = add("smooth_limestone_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block LIMESTONE_BRICKS_WALL = add("limestone_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_WALL = add("shale_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_SHALE_WALL = add("smooth_shale_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SHALE_BRICKS_WALL = add("shale_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_WALL = add("anorthosite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ANORTHOSITE_WALL = add("smooth_anorthosite_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block ANORTHOSITE_BRICKS_WALL = add("anorthosite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(0)), BLOCKS);
    public static final Block IRONSTONE_WALL = add("ironstone_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_IRONSTONE_WALL = add("smooth_ironstone_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block IRONSTONE_BRICKS_WALL = add("ironstone_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block BASALT_WALL = add("basalt_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_BASALT_WALL = add("smooth_basalt_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block BASALT_BRICKS_WALL = add("basalt_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_WALL = add("rhyolite_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_RHYOLITE_WALL = add("smooth_rhyolite_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block RHYOLITE_BRICKS_WALL = add("rhyolite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_WALL = add("marble_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_MARBLE_WALL = add("smooth_marble_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block MARBLE_BRICKS_WALL = add("marble_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_WALL = add("gneiss_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block SMOOTH_GNEISS_WALL = add("smooth_gneiss_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block GNEISS_BRICKS_WALL = add("gneiss_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(2)), BLOCKS);
    public static final Block PERIDOTITE_WALL = add("peridotite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PERIDOTITE_WALL = add("smooth_peridotite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PERIDOTITE_BRICKS_WALL = add("peridotite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_WALL = add("ringwoodite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_RINGWOODITE_WALL = add("smooth_ringwoodite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block RINGWOODITE_BRICKS_WALL = add("ringwoodite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_WALL = add("wadsleyite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_WADSLEYITE_WALL = add("smooth_wadsleyite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block WADSLEYITE_BRICKS_WALL = add("wadsleyite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_WALL = add("bridgmanite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_BRIDGMANITE_WALL = add("smooth_bridgmanite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block BRIDGMANITE_BRICKS_WALL = add("bridgmanite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_WALL = add("komatiite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KOMATIITE_WALL = add("smooth_komatiite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KOMATIITE_BRICKS_WALL = add("komatiite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_WALL = add("kimberlite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_KIMBERLITE_WALL = add("smooth_kimberlite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block KIMBERLITE_BRICKS_WALL = add("kimberlite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_WALL = add("ferropericlase_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_FERROPERICLASE_WALL = add("smooth_ferropericlase_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block FERROPERICLASE_BRICKS_WALL = add("ferropericlase_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_WALL = add("perovskite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block SMOOTH_PEROVSKITE_WALL = add("smooth_perovskite_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PEROVSKITE_BRICKS_WALL = add("perovskite_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(3)), BLOCKS);
    public static final Block PUMICE_WALL = add("pumice_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_PUMICE_WALL = add("smooth_pumice_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block PUMICE_BRICKS_WALL = add("pumice_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_WALL = add("scoria_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SMOOTH_SCORIA_WALL = add("smooth_scoria_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);
    public static final Block SCORIA_BRICKS_WALL = add("scoria_bricks_wall", new WallBlock(DEF_STONE.harvestLevel(1)), BLOCKS);

    //Earth Blocks
    public static final Block SANDY_DIRT = add("sandy_dirt", new SandyDirtBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)), BLOCKS);
    public static final Block ALLUVIUM = add("alluvium", new SandBlock(14406560, AbstractBlock.Properties.create(Material.SAND, MaterialColor.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND)), BLOCKS);
    public static final Block BLACK_SAND = add("black_sand", new SandBlock(00000000, AbstractBlock.Properties.create(Material.SAND, MaterialColor.SAND).hardnessAndResistance(0.5F).sound(SoundType.SAND)), BLOCKS);
    //public static final Block MUDDY_DIRT = add("muddy_dirt", new  Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)), MAIN, BLOCKS);
    //public static final Block MUDDY_GRASS = add("muddy_grass", new MuddyGrassBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)), MAIN, BLOCKS);
    public static final Block PERMAFROST = add("permafrost", new PermafrostBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(1.5F).sound(SoundType.GROUND)), BLOCKS);
    //public static final Block VEGETATED_PERMAFROST = add("vegetated_permafrost", new VegetatedPermafrostBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(1.5F).sound(SoundType.GROUND)), MAIN, BLOCKS);
    public static final Block QUICKLIME_BLOCK = add("quicklime_block", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(0).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block MAGNESITE_BLOCK = add("magnesite_block", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(0).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block MAGNESIA_BLOCK = add("magnesia_block", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(0).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block ANDESITIC_TUFF = add("andesitic_tuff", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block BASALTIC_TUFF = add("basaltic_tuff", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block RHYOLITIC_TUFF = add("rhyolitic_tuff", new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 2.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)), BLOCKS);
    public static final Block GRAVEL_CONCRETE = add("gravel_concrete", new MovementModifierBlock(1.2D,Block.Properties.create(Material.SAND).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)), BLOCKS);
    public static final Block ROMAN_CONCRETE = add("roman_concrete", new MovementModifierBlock(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_SLAB = add("roman_concrete_slab", new RankineSlab(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_STAIRS = add("roman_concrete_stairs", new RankineStairs(1.4D,Block.getStateById(0),Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_WALL = add("roman_concrete_wall", new WallBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ROMAN_CONCRETE = add("smooth_roman_concrete", new MovementModifierBlock(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ROMAN_CONCRETE_SLAB = add("smooth_roman_concrete_slab", new RankineSlab(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ROMAN_CONCRETE_STAIRS = add("smooth_roman_concrete_stairs", new RankineStairs(1.4D,Block.getStateById(0),Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block SMOOTH_ROMAN_CONCRETE_WALL = add("smooth_roman_concrete_wall", new WallBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_BRICKS = add("roman_concrete_bricks", new MovementModifierBlock(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_BRICKS_SLAB = add("roman_concrete_bricks_slab", new RankineSlab(1.4D,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_BRICKS_STAIRS = add("roman_concrete_bricks_stairs", new RankineStairs(1.4D,Block.getStateById(0),Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);
    public static final Block ROMAN_CONCRETE_BRICKS_WALL = add("roman_concrete_bricks_wall", new WallBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).harvestLevel(0)), BLOCKS);


    //WOOD BLOCKS
    public static final Block CEDAR_LOG = add("cedar_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_LOG = add("pinyon_pine_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_LOG = add("juniper_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_LOG = add("coconut_palm_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_LOG = add("balsam_fir_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_LOG = add("magnolia_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_LOG = add("eastern_hemlock_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block YELLOW_BIRCH_LOG = add("yellow_birch_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);

    public static final Block CEDAR_WOOD = add("cedar_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_WOOD = add("pinyon_pine_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_WOOD = add("juniper_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_WOOD = add("coconut_palm_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_WOOD = add("balsam_fir_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_WOOD = add("magnolia_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_WOOD = add("eastern_hemlock_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block YELLOW_BIRCH_WOOD = add("yellow_birch_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);

    public static final Block STRIPPED_CEDAR_LOG = add("stripped_cedar_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_PINYON_PINE_LOG = add("stripped_pinyon_pine_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_JUNIPER_LOG = add("stripped_juniper_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_COCONUT_PALM_LOG = add("stripped_coconut_palm_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_BALSAM_FIR_LOG = add("stripped_balsam_fir_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_MAGNOLIA_LOG = add("stripped_magnolia_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_EASTERN_HEMLOCK_LOG = add("stripped_eastern_hemlock_log", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);

    public static final Block STRIPPED_CEDAR_WOOD = add("stripped_cedar_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_PINYON_PINE_WOOD = add("stripped_pinyon_pine_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_JUNIPER_WOOD = add("stripped_juniper_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_COCONUT_PALM_WOOD = add("stripped_coconut_palm_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);;
    public static final Block STRIPPED_BALSAM_FIR_WOOD = add("stripped_balsam_fir_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_MAGNOLIA_WOOD = add("stripped_magnolia_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);
    public static final Block STRIPPED_EASTERN_HEMLOCK_WOOD = add("stripped_eastern_hemlock_wood", new RotatedPillarBlock(DEF_WOOD), BLOCKS, 300);

    public static final Block CEDAR_PLANKS = add("cedar_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_PLANKS = add("pinyon_pine_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_PLANKS = add("juniper_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_PLANKS = add("coconut_palm_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_PLANKS = add("balsam_fir_planks",new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_PLANKS = add("magnolia_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_PLANKS = add("eastern_hemlock_planks", new Block(DEF_WOOD), BLOCKS, 300);
    public static final Block BAMBOO_PLANKS = add("bamboo_planks",new Block(DEF_WOOD), BLOCKS, 400);
    public static final Block BAMBOO_CULMS = add("bamboo_clums", new Block(DEF_WOOD), BLOCKS, 400);

    public static final Block CEDAR_SLAB = add("cedar_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block PINYON_PINE_SLAB = add("pinyon_pine_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block JUNIPER_SLAB = add("juniper_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block COCONUT_PALM_SLAB = add("coconut_palm_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block BALSAM_FIR_SLAB = add("balsam_fir_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block MAGNOLIA_SLAB = add("magnolia_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block EASTERN_HEMLOCK_SLAB = add("eastern_hemlock_slab", new RankineSlab(DEF_WOOD), BLOCKS, 150);
    public static final Block BAMBOO_SLAB = add("bamboo_slab", new RankineSlab(DEF_WOOD), BLOCKS, 200);
    public static final Block BAMBOO_CULMS_SLAB = add("bamboo_clums_slab", new RankineSlab(DEF_WOOD), BLOCKS, 200);

    public static final Block CEDAR_STAIRS = add("cedar_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_STAIRS = add("pinyon_pine_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_STAIRS = add("juniper_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_STAIRS = add("coconut_palm_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_STAIRS = add("balsam_fir_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_STAIRS = add("magnolia_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_STAIRS = add("eastern_hemlock_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 300);
    public static final Block BAMBOO_STAIRS = add("bamboo_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 400);
    public static final Block BAMBOO_CULMS_STAIRS = add("bamboo_clums_stairs", new RankineStairs(Block.getStateById(0), DEF_WOOD), BLOCKS, 400);

    public static final Block CEDAR_FENCE = add("cedar_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_FENCE = add("pinyon_pine_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_FENCE = add("juniper_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_FENCE = add("coconut_palm_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_FENCE = add("balsam_fir_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_FENCE = add("magnolia_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_FENCE = add("eastern_hemlock_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 300);
    public static final Block BAMBOO_FENCE = add("bamboo_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 400);
    public static final Block BAMBOO_CULMS_FENCE = add("bamboo_clums_fence", new RankineWoodenFence(DEF_WOOD), BLOCKS, 400);

    public static final Block CEDAR_FENCE_GATE = add("cedar_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block PINYON_PINE_FENCE_GATE = add("pinyon_pine_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block JUNIPER_FENCE_GATE = add("juniper_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block COCONUT_PALM_FENCE_GATE = add("coconut_palm_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block BALSAM_FIR_FENCE_GATE = add("balsam_fir_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block MAGNOLIA_FENCE_GATE = add("magnolia_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_FENCE_GATE = add("eastern_hemlock_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 300);
    public static final Block BAMBOO_FENCE_GATE = add("bamboo_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 400);
    public static final Block BAMBOO_CULMS_FENCE_GATE = add("bamboo_clums_fence_gate", new RankineWoodenFenceGate(DEF_WOOD), BLOCKS, 400);

    public static final Block BAMBOO_WALL = add("bamboo_wall", new WallBlock(DEF_WOOD), BLOCKS, 400);
    public static final Block BAMBOO_CULMS_WALL = add("bamboo_clums_wall", new WallBlock(DEF_WOOD), BLOCKS, 400);

    public static final Block CEDAR_PRESSURE_PLATE = add("cedar_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block PINYON_PINE_PRESSURE_PLATE = add("pinyon_pine_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block JUNIPER_PRESSURE_PLATE = add("juniper_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block COCONUT_PALM_PRESSURE_PLATE = add("coconut_palm_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block BALSAM_FIR_PRESSURE_PLATE = add("balsam_fir_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block MAGNOLIA_PRESSURE_PLATE = add("magnolia_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block EASTERN_HEMLOCK_PRESSURE_PLATE = add("eastern_hemlock_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 600);
    public static final Block BAMBOO_PRESSURE_PLATE = add("bamboo_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 800);
    public static final Block BAMBOO_CULMS_PRESSURE_PLATE = add("bamboo_clums_pressure_plate", new RankineWoodenPressurePlate(), BLOCKS, 1800);

    public static final Block CEDAR_BUTTON = add("cedar_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block PINYON_PINE_BUTTON = add("pinyon_pine_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block JUNIPER_BUTTON = add("juniper_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block COCONUT_PALM_BUTTON = add("coconut_palm_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block BALSAM_FIR_BUTTON = add("balsam_fir_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block MAGNOLIA_BUTTON = add("magnolia_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_BUTTON = add("eastern_hemlock_button", new RankineWoodenButton(), BLOCKS, 300);
    public static final Block BAMBOO_BUTTON = add("bamboo_button", new RankineWoodenButton(), BLOCKS, 400);
    public static final Block BAMBOO_CULMS_BUTTON = add("bamboo_clums_button", new RankineWoodenButton(), BLOCKS, 900);

    public static final Block CEDAR_DOOR = add("cedar_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block PINYON_PINE_DOOR = add("pinyon_pine_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block JUNIPER_DOOR = add("juniper_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block COCONUT_PALM_DOOR = add("coconut_palm_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block BALSAM_FIR_DOOR = add("balsam_fir_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block EASTERN_HEMLOCK_DOOR = add("eastern_hemlock_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block MAGNOLIA_DOOR = add("magnolia_door", new RankineWoodenDoor(), BLOCKS, 600);
    public static final Block BAMBOO_DOOR = add("bamboo_door", new RankineWoodenDoor(), BLOCKS, 600);

    public static final Block CEDAR_TRAPDOOR = add("cedar_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block PINYON_PINE_TRAPDOOR = add("pinyon_pine_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block JUNIPER_TRAPDOOR = add("juniper_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block COCONUT_PALM_TRAPDOOR = add("coconut_palm_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block BALSAM_FIR_TRAPDOOR = add("balsam_fir_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block EASTERN_HEMLOCK_TRAPDOOR = add("eastern_hemlock_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block MAGNOLIA_TRAPDOOR = add("magnolia_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);
    public static final Block BAMBOO_TRAPDOOR = add("bamboo_trapdoor", new RankineWoodenTrapDoor(), BLOCKS, 300);

    public static final Block STICK_BLOCK = add("stick_block", new StickBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.2F)), BLOCKS, 800);
    public static final Block MINERAL_WOOL = add("mineral_wool", new MineralWoolBlock(Block.Properties.create(Material.WOOL, MaterialColor.ADOBE).sound(SoundType.CLOTH).hardnessAndResistance(1F)), BLOCKS);

    public static final Block CEDAR_LEAVES = add("cedar_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);
    public static final Block COCONUT_PALM_LEAVES = add("coconut_palm_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);
    public static final Block PINYON_PINE_LEAVES = add("pinyon_pine_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);;
    public static final Block JUNIPER_LEAVES = add("juniper_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);
    public static final Block BALSAM_FIR_LEAVES = add("balsam_fir_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);
    public static final Block MAGNOLIA_LEAVES = add("magnolia_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);
    public static final Block EASTERN_HEMLOCK_LEAVES = add("eastern_hemlock_leaves", new LeavesBlock(DEF_LEAVES), BLOCKS);

    private static final Block.Properties sapling = Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.PLANT);
    public static final Block CEDAR_SAPLING = add("cedar_sapling", new RankineSapling(new CedarTree(), sapling, 3), BLOCKS);
    public static final Block COCONUT_PALM_SAPLING = add("coconut_palm_sapling", new RankineSapling(new CoconutPalmTree(), sapling, 2), BLOCKS);
    public static final Block PINYON_PINE_SAPLING = add("pinyon_pine_sapling", new RankineSapling(new PinyonPineTree(), sapling, 2), BLOCKS);
    public static final Block JUNIPER_SAPLING = add("juniper_sapling", new RankineSapling(new JuniperTree(), sapling, 2), BLOCKS);
    public static final Block BALSAM_FIR_SAPLING = add("balsam_fir_sapling", new RankineSapling(new BalsamFirTree(), sapling, 3), BLOCKS);
    public static final Block MAGNOLIA_SAPLING = add("magnolia_sapling", new RankineSapling(new MagnoliaTree(), sapling, 3), BLOCKS);
    public static final Block EASTERN_HEMLOCK_SAPLING = add("eastern_hemlock_sapling", new RankineSapling(new EasternHemlockTree(), sapling, 3), BLOCKS);
    public static final FlowerPotBlock POTTED_CEDAR_SAPLING = add("potted_cedar_sapling", new FlowerPotBlock(null, () -> CEDAR_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_PINYON_PINE_SAPLING = add("potted_pinyon_pine_sapling", new FlowerPotBlock(null, () -> PINYON_PINE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_JUNIPER_SAPLING = add("potted_juniper_sapling", new FlowerPotBlock(null, () -> JUNIPER_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_COCONUT_PALM_SAPLING = add("potted_coconut_palm_sapling", new FlowerPotBlock(null, () -> COCONUT_PALM_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_BALSAM_FIR_SAPLING = add("potted_balsam_fir_sapling", new FlowerPotBlock(null, () -> BALSAM_FIR_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_MAGNOLIA_SAPLING = add("potted_magnolia_sapling", new FlowerPotBlock(null, () -> MAGNOLIA_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    public static final FlowerPotBlock POTTED_EASTERN_HEMLOCK_SAPLING = add("potted_eastern_hemlock_sapling", new FlowerPotBlock(null, () -> EASTERN_HEMLOCK_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).notSolid()));
    
    //METALLURGY--------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //ORES
    public static final RankineOre LIGNITE_ORE = add("lignite_ore", new RankineOre(DEF_ORE.harvestLevel(0)), METALLURGY);
    public static final RankineOre SUBBITUMINOUS_ORE = add("subbituminous_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre BITUMINOUS_ORE = add("bituminous_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre ANTHRACITE_ORE = add("anthracite_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre MAGNESITE_ORE = add("magnesite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.MAGNESIUM_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_ALUMINUM_ORE = add("native_aluminum_ore", new RankineOre(DEF_ORE.harvestLevel(0)), METALLURGY);
    public static final RankineOre BAUXITE_ORE = add("bauxite_ore", new RankineOre(DEF_ORE.harvestLevel(1), ModItems.ALUMINUM_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_SULFUR_ORE = add("native_sulfur_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre ILMENITE_ORE = add("ilmenite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.TITANIUM_NUGGET), METALLURGY);
    public static final RankineOre VANADINITE_ORE = add("vanadinite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.VANADIUM_INGOT), METALLURGY);
    public static final RankineOre CHROMITE_ORE = add("chromite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.CHROMIUM_NUGGET), METALLURGY);
    public static final RankineOre PYROLUSITE_ORE = add("pyrolusite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.MANGANESE_NUGGET), METALLURGY);
    public static final RankineOre MAGNETITE_ORE = add("magnetite_ore", new RankineOre(DEF_ORE.harvestLevel(2), Items.IRON_NUGGET), METALLURGY);
    public static final RankineOre PENTLANDITE_ORE = add("pentlandite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.NICKEL_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_COPPER_ORE = add("native_copper_ore", new RankineOre(DEF_ORE.harvestLevel(0), ModItems.COPPER_NUGGET), METALLURGY);
    public static final RankineOre MALACHITE_ORE = add("malachite_ore", new RankineOre(DEF_ORE.harvestLevel(1), ModItems.COPPER_NUGGET), METALLURGY);
    public static final RankineOre SPHALERITE_ORE = add("sphalerite_ore", new RankineOre(DEF_ORE.harvestLevel(1), ModItems.ZINC_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_GALLIUM_ORE = add("native_gallium_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre NATIVE_ARSENIC_ORE = add("native_arsenic_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre NATIVE_SELENIUM_ORE = add("native_selenium_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre COLUMBITE_ORE = add("columbite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.NIOBIUM_NUGGET), METALLURGY);
    public static final RankineOre MOLYBDENITE_ORE = add("molybdenite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.MOLYBDENUM_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_SILVER_ORE = add("native_silver_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre ACANTHITE_ORE = add("acanthite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.SILVER_NUGGET), METALLURGY);
    public static final RankineOre GREENOCKITE_ORE = add("greenockite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.CADMIUM_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_INDIUM_ORE = add("native_indium_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre NATIVE_TIN_ORE = add("native_tin_ore", new RankineOre(DEF_ORE.harvestLevel(0), ModItems.TIN_NUGGET), METALLURGY);
    public static final RankineOre CASSITERITE_ORE = add("cassiterite_ore", new RankineOre(DEF_ORE.harvestLevel(1), ModItems.TIN_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_TELLURIUM_ORE = add("native_tellurium_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre TANTALITE_ORE = add("tantalite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.TANTALUM_NUGGET), METALLURGY);
    public static final RankineOre WOLFRAMITE_ORE = add("wolframite_ore", new RankineOre(DEF_ORE.harvestLevel(3), ModItems.TITANIUM_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_GOLD_ORE = add("native_gold_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre NETHER_GOLD_ORE = add("nether_gold_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre NATIVE_LEAD_ORE = add("native_lead_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre GALENA_ORE = add("galena_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.LEAD_NUGGET), METALLURGY);
    public static final RankineOre NATIVE_BISMUTH_ORE = add("native_bismuth_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre BISMITE_ORE = add("bismite_ore", new RankineOre(DEF_ORE.harvestLevel(2), ModItems.BISMUTH_NUGGET), METALLURGY);
    public static final RankineOre URANINITE_ORE = add("uraninite_ore", new RankineOre(DEF_ORE.harvestLevel(4)), METALLURGY);
    public static final RankineOre PLUMBAGO_ORE = add("plumbago_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre MOISSANITE_ORE = add("moissanite_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre SPERRYLITE_ORE = add("sperrylite_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre LAZURITE_ORE = add("lazurite_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre DIAMOND_ORE = add("diamond_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre EMERALD_ORE = add("emerald_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre OPAL_ORE = add("opal_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre AQUAMARINE_ORE = add("aquamarine_ore", new RankineOre(DEF_ORE.harvestLevel(2)), METALLURGY);
    public static final RankineOre QUARTZ_ORE = add("quartz_ore", new RankineOre(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final RankineOre FLUORITE_ORE = add("fluorite_ore", new RankineOre(DEF_ORE.harvestLevel(3)), METALLURGY);
    public static final RankineOre CINNABAR_ORE = add("cinnabar_ore", new RankineOre(DEF_ORE.harvestLevel(1), Items.REDSTONE), METALLURGY);
    public static final NoduleBlock LIMESTONE_NODULE = add("limestone_nodule", new NoduleBlock(DEF_ORE.harvestLevel(0)), METALLURGY);
    public static final Block METEORITE = add("meteorite", new Block(DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final Block KAMACITE = add("kamacite", new CompositionBlock(0,DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final Block ANTITAENITE = add("antitaenite", new CompositionBlock(1,DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final Block TAENITE = add("taenite", new CompositionBlock(2,DEF_ORE.harvestLevel(1)), METALLURGY);
    public static final Block TETRATAENITE = add("tetrataenite", new CompositionBlock(3,DEF_ORE.harvestLevel(1)), METALLURGY);
    //metal blocks
    public static final Block ROSE_GOLD_BLOCK = add("rose_gold_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block WHITE_GOLD_BLOCK = add("white_gold_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block GREEN_GOLD_BLOCK = add("green_gold_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BLUE_GOLD_BLOCK = add("blue_gold_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PURPLE_GOLD_BLOCK = add("purple_gold_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block INVAR_BLOCK = add("invar_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BRONZE_BLOCK = add("bronze_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ALUMINUM_BRONZE_BLOCK = add("aluminum_bronze_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block OSMIRIDIUM_BLOCK = add("osmiridium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block STEEL_BLOCK = add("steel_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block STAINLESS_STEEL_BLOCK = add("stainless_steel_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PIG_IRON_BLOCK = add("pig_iron_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ALNICO_BLOCK = add("alnico_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MAGNALIUM_BLOCK = add("magnalium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block DURALUMIN_BLOCK = add("duralumin_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BRASS_BLOCK = add("brass_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CUPRONICKEL_BLOCK = add("cupronickel_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NICHROME_BLOCK = add("nichrome_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NICKEL_SILVER_BLOCK = add("nickel_silver_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NITINOL_BLOCK = add("nitinol_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block AMALGAM_BLOCK = add("amalgam_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ROSE_METAL_BLOCK = add("rose_metal_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block WROUGHT_IRON_BLOCK = add("wrought_iron_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CAST_IRON_BLOCK = add("cast_iron_block", new Block(DEF_METAL_BLOCK), METALLURGY);

    public static final Block HYDROGEN_BLOCK = add("hydrogen_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block HELIUM_BLOCK = add("helium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LITHIUM_BLOCK = add("lithium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BERYLLIUM_BLOCK = add("beryllium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BORON_BLOCK = add("boron_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CARBON_BLOCK = add("carbon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NITROGEN_BLOCK = add("nitrogen_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block OXYGEN_BLOCK = add("oxygen_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block FLUORINE_BLOCK = add("fluorine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NEON_BLOCK = add("neon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SODIUM_BLOCK = add("sodium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MAGNESIUM_BLOCK = add("magnesium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ALUMINUM_BLOCK = add("aluminum_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SILICON_BLOCK = add("silicon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PHOSPHORUS_BLOCK = add("phosphorus_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SULFUR_BLOCK = add("sulfur_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CHLORINE_BLOCK = add("chlorine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ARGON_BLOCK = add("argon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block POTASSIUM_BLOCK = add("potassium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CALCIUM_BLOCK = add("calcium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SCANDIUM_BLOCK = add("scandium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TITANIUM_BLOCK = add("titanium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block VANADIUM_BLOCK = add("vanadium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CHROMIUM_BLOCK = add("chromium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MANGANESE_BLOCK = add("manganese_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block COBALT_BLOCK = add("cobalt_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NICKEL_BLOCK = add("nickel_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block COPPER_BLOCK = add("copper_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ZINC_BLOCK = add("zinc_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block GALLIUM_BLOCK = add("gallium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block GERMANIUM_BLOCK = add("germanium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ARSENIC_BLOCK = add("arsenic_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SELENIUM_BLOCK = add("selenium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BROMINE_BLOCK = add("bromine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block KRYPTON_BLOCK = add("krypton_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RUBIDIUM_BLOCK = add("rubidium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block STRONTIUM_BLOCK = add("strontium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block YTTRIUM_BLOCK = add("yttrium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ZIRCONIUM_BLOCK = add("zirconium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NIOBIUM_BLOCK = add("niobium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MOLYBDENUM_BLOCK = add("molybdenum_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TECHNETIUM_BLOCK = add("technetium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RUTHENIUM_BLOCK = add("ruthenium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RHODIUM_BLOCK = add("rhodium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PALLADIUM_BLOCK = add("palladium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SILVER_BLOCK = add("silver_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CADMIUM_BLOCK = add("cadmium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block INDIUM_BLOCK = add("indium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TIN_BLOCK = add("tin_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ANTIMONY_BLOCK = add("antimony_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TELLURIUM_BLOCK = add("tellurium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block IODINE_BLOCK = add("iodine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block XENON_BLOCK = add("xenon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CESIUM_BLOCK = add("cesium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BARIUM_BLOCK = add("barium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LANTHANUM_BLOCK = add("lanthanum_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CERIUM_BLOCK = add("cerium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PRASEODYMIUM_BLOCK = add("praseodymium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NEODYMIUM_BLOCK = add("neodymium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PROMETHIUM_BLOCK = add("promethium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SAMARIUM_BLOCK = add("samarium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block EUROPIUM_BLOCK = add("europium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block GADOLINIUM_BLOCK = add("gadolinium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TEBRIUM_BLOCK = add("tebrium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block DYSPROSIUM_BLOCK = add("dysprosium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block HOLMIUM_BLOCK = add("holmium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ERIBIUM_BLOCK = add("eribium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block THULIUM_BLOCK = add("thulium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block YTTERBIUM_BLOCK = add("ytterbium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LUTETIUM_BLOCK = add("lutetium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block HAFNIUM_BLOCK = add("hafnium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TANTALUM_BLOCK = add("tantalum_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TUNGSTEN_BLOCK = add("tungsten_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RHENIUM_BLOCK = add("rhenium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block OSMIUM_BLOCK = add("osmium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block IRIDIUM_BLOCK = add("iridium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PLATINUM_BLOCK = add("platinum_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MERCURY_BLOCK = add("mercury_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block THALLIUM_BLOCK = add("thallium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LEAD_BLOCK = add("lead_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BISMUTH_BLOCK = add("bismuth_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block POLONIUM_BLOCK = add("polonium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ASTATINE_BLOCK = add("astatine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RADON_BLOCK = add("radon_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block FRANCIUM_BLOCK = add("francium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RADIUM_BLOCK = add("radium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ACTINIUM_BLOCK = add("actinium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block THORIUM_BLOCK = add("thorium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PROTACTINIUM_BLOCK = add("protactinium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block URANIUM_BLOCK = add("uranium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NEPTUNIUM_BLOCK = add("neptunium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PLUTONIUM_BLOCK = add("plutonium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block AMERICIUM_BLOCK = add("americium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CURIUM_BLOCK = add("curium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BERKELIUM_BLOCK = add("berkelium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CALIFORNIUM_BLOCK = add("californium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block EINSTEINIUM_BLOCK = add("einsteinium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block FERMIUM_BLOCK = add("fermium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MENDELEVIUM_BLOCK = add("mendelevium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NOBELIUM_BLOCK = add("nobelium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LAWRENCIUM_BLOCK = add("lawrencium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block RUTHERFORDIUM_BLOCK = add("rutherfordium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block DUBNIUM_BLOCK = add("dubnium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SEABORGIUM_BLOCK = add("seaborgium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BOHRIUM_BLOCK = add("bohrium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block HASSIUM_BLOCK = add("hassium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MEITERIUM_BLOCK = add("meiterium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block DARMSTADTIUM_BLOCK = add("darmstadtium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block ROENTGENIUM_BLOCK = add("roentgenium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block COPERNICIUM_BLOCK = add("copernicium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NIHONIUM_BLOCK = add("nihonium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block FLEROVIUM_BLOCK = add("flerovium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block MOSCOVIUM_BLOCK = add("moscovium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block LIVERMORIUM_BLOCK = add("livermorium_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TENNESSINE_BLOCK = add("tennessine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block OGANESSON_BLOCK = add("oganesson_block", new Block(DEF_METAL_BLOCK), METALLURGY);

    public static final Block GRAPHITE_BLOCK = add("graphite_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block OPAL_BLOCK = add("opal_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block GARNET_BLOCK = add("garnet_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block FELDSPAR_BLOCK = add("feldspar_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block DOLOMITE_BLOCK = add("dolomite_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block CALCITE_BLOCK = add("calcite_block", new RankineTransparent(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(0.5F, 0.5F).notSolid().harvestLevel(0)), METALLURGY);
    public static final Block OLIVINE_BLOCK = add("olivine_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PERIDOT_BLOCK = add("peridot_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block PYROXENE_BLOCK = add("pyroxene_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block NICKEL_SUPERALLOY_BLOCK = add("nickel_superalloy_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block SALT_BLOCK = add("salt_block", new RankineTransparent(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(0.5F, 0.5F).notSolid().harvestLevel(0)), METALLURGY);
    public static final Block PINK_SALT_BLOCK = add("pink_salt_block", new RankineTransparent(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(0.5F, 0.5F).notSolid().harvestLevel(0)), METALLURGY);
    public static final Block CALCIUM_SILICATE_BLOCK = add("calcium_silicate_block", new RankineTransparent(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(0.5F, 0.5F).notSolid().harvestLevel(0)), METALLURGY);
    public static final Block SILICON_CARBIDE_BLOCK = add("silicon_carbide_block", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block COKE_BLOCK = add("coke_block", new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).harvestLevel(0)), METALLURGY, 2400*9);
    public static final Block LIGNITE_BLOCK = add("lignite_block", new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).harvestLevel(0)), METALLURGY, 1200*9);
    public static final Block BITUMINOUS_COAL_BLOCK = add("bituminous_coal_block", new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).harvestLevel(0)), METALLURGY, 2000*9);
    public static final Block ANTHRACITE_COAL_BLOCK = add("anthracite_coal_block", new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).harvestLevel(0)), METALLURGY, 2400*9);
    public static final Block NITER = add("niter", new CrystalBlock(Block.Properties.create(Material.IRON).sound(SoundType.GLASS).harvestLevel(1).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)), METALLURGY);

    public static final Block COPPER_SHEETMETAL = add("copper_sheetmetal", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block TIN_SHEETMETAL = add("tin_sheetmetal", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block BRONZE_SHEETMETAL = add("bronze_sheetmetal", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block WROUGHT_IRON_SHEETMETAL = add("wrought_iron_sheetmetal", new Block(DEF_METAL_BLOCK), METALLURGY);
    public static final Block STEEL_SHEETMETAL = add("steel_sheetmetal", new Block(DEF_METAL_BLOCK), METALLURGY);

    public static final Block CREEPER_BLOCK = add("creeper_block", new CreepingBlock(1.0F,Block.Properties.create(Material.SAND).sound(SoundType.SAND).hardnessAndResistance(1)), METALLURGY);
    public static final BlastingPowderBlock BLASTING_POWDER = add("blasting_powder", new BlastingPowderBlock(Block.Properties.create(Material.SAND).sound(SoundType.SAND).hardnessAndResistance(1)), METALLURGY);
    public static final MantleTeleporterBlock HEART_OF_THE_MANTLE = add("heart_of_the_mantle", new MantleTeleporterBlock(Block.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2).harvestLevel(1)), METALLURGY);

    public static final BeehiveOvenPit BEEHIVE_OVEN_PIT = add("beehive_oven_pit", new BeehiveOvenPit(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0)), METALLURGY);
    public static final BeehiveOvenPit MAGNESIUM_BEEHIVE_OVEN_PIT = add("magnesium_beehive_oven_pit", new BeehiveOvenPit(0.4f,ModBlocks.MAGNESIUM_REFRACTORY_BRICKS,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0)), METALLURGY);
    public static final BeehiveOvenPit ZIRCON_BEEHIVE_OVEN_PIT = add("zircon_beehive_oven_pit", new BeehiveOvenPit(0.6f,ModBlocks.ZIRCON_REFRACTORY_BRICKS,Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0)), METALLURGY);
    public static final Crucible CRUCIBLE = add("crucible", new Crucible(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0).setLightLevel((p_235418_0_) -> 7)), METALLURGY);
    public static final AlloyFurnace ALLOY_FURNACE = add("alloy_furnace", new AlloyFurnace(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0).setLightLevel((p_235418_0_) -> 13)), METALLURGY);
    public static final CoalForge COAL_FORGE = add("coal_forge", new CoalForge(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0).setLightLevel((p_235418_0_) -> 13)), METALLURGY);
    public static final FineryForge FINERY_FORGE = add("finery_forge", new FineryForge(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0).setLightLevel((p_235418_0_) -> 7)), METALLURGY);
    public static final PistonCrusher PISTON_CRUSHER = add("piston_crusher", new PistonCrusher(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F).harvestLevel(0).setLightLevel((p_235418_0_) -> 7)), METALLURGY);
    public static final Block UNAGED_CHEESE = add("unaged_cheese", new UnagedCheeseBlock(Block.Properties.create(Material.GOURD).sound(SoundType.STEM).hardnessAndResistance(1.0F, 1.0F).harvestLevel(0)), MISC);
    public static final Block AGED_CHEESE = add("aged_cheese", new Block(Block.Properties.create(Material.GOURD).sound(SoundType.STEM).hardnessAndResistance(1.0F, 1.0F).harvestLevel(0)), MISC);
    public static final RankineBerryBushBlock ELDERBERRY_BUSH = add("elderberry_bush", "elderberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),0), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.ELDERBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock SNOWBERRY_BUSH = add("snowberry_bush", "snowberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),1), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.SNOWBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock BLUEBERRY_BUSH = add("blueberry_bush", "blueberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),2), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.BLUEBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock RASPBERRY_BUSH = add("raspberry_bush", "raspberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),3), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.BLACKBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock BLACKBERRY_BUSH = add("blackberry_bush", "blackberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),4), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.RASPBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock CRANBERRY_BUSH = add("cranberry_bush", "cranberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),5), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.CRANBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock STRAWBERRY_BUSH = add("strawberry_bush", "strawberries", new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),6), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.STRAWBERRIES), BlockNamedItem::new);
    public static final RankineBerryBushBlock PINEAPPLE_BUSH = add("pineapple_bush", "pineapple",  new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),7), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.PINEAPPLE), BlockNamedItem::new);
    public static final RankineBerryBushBlock BANANA_YUCCA_BUSH = add("banana_yucca_bush", "banana_yucca",  new RankineBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH),8), new Item.Properties().group(ProjectRankine.setup.rankineTools).food(ModFoods.YUCCA), BlockNamedItem::new);
    public static final Block ALUMINUM_LADDER = add("aluminum_ladder", new MetalLadder(Block.Properties.create(Material.CARPET)), MISC);
    public static final RopeBlock ROPE = add("rope", new RopeBlock(Block.Properties.create(Material.CARPET).doesNotBlockMovement()), MISC);
    public static final SwampGrassBlock SWAMP_GRASS = add("swamp_grass", new SwampGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)), MISC);
    public static final ShortGrassBlock SHORT_GRASS = add("short_grass", new ShortGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)), MISC);
    public static final ShortGrassBlock WHITE_CLOVER = add("white_clover", new ShortGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)), MISC);
    public static final ShortGrassBlock PURPLE_CLOVER = add("purple_clover", new ShortGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)), MISC);
    public static final DuckweedBlock DUCKWEED = add("duckweed", new DuckweedBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f)), MISC, DuckweedItem::new);
    public static final FoxfireBlock BLUE_FOXFIRE = add("blue_foxfire", new FoxfireBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f).setLightLevel((p_235418_0_) -> 4)), MISC);
    public static final FoxfireBlock GREEN_FOXFIRE = add("green_foxfire", new FoxfireBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0.0f).setLightLevel((p_235418_0_) -> 4)), MISC);


    public static final FlowingFluidBlock LIQUID_MERCURY_BLOCK = add("liquid_mercury_block", new FlowingFluidBlock(()-> ModFluids.LIQUID_MERCURY,Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final Block MYSTICAL_SUNDIAL =  add("mystical_sundial", new Block(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0F, 1.0F).harvestLevel(1)), MISC);



    //OTHER STUFFS
    @ObjectHolder("rankine:alloy_furnace")
    public static ContainerType<AlloyFurnaceContainer> ALLOY_FURNACE_CONTAINER;

    @ObjectHolder("rankine:alloy_furnace")
    public static TileEntityType<AlloyFurnaceTile> ALLOY_FURNACE_TILE;


    @ObjectHolder("rankine:piston_crusher")
    public static ContainerType<PistonCrusherContainer> PISTON_CRUSHER_CONTAINER;

    @ObjectHolder("rankine:piston_crusher")
    public static TileEntityType<PistonCrusherTile> PISTON_CRUSHER_TILE;


    @ObjectHolder("rankine:coal_forge")
    public static ContainerType<CoalForgeContainer> COAL_FORGE_CONTAINER;

    @ObjectHolder("rankine:coal_forge")
    public static TileEntityType<CoalForgeTile> COAL_FORGE_TILE;


    @ObjectHolder("rankine:finery_forge")
    public static ContainerType<FineryForgeContainer> FINERY_FORGE_CONTAINER;

    @ObjectHolder("rankine:finery_forge")
    public static TileEntityType<FineryForgeTile> FINERY_FORGE_TILE;
}
