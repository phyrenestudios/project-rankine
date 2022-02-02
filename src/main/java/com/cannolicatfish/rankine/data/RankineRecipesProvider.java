package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.data.builders.AlloyCraftingRecipeBuilder;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.recipe.JamRecipe;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.function.Consumer;

public class RankineRecipesProvider extends RecipeProvider {

    public RankineRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Project Rankine - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //MINECRAFT OVERRIDE
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.LOGS_THAT_BURN), RankineItems.ASH.get(), 0.1F, 200).addCriterion("has_logs", hasItem(ItemTags.LOGS_THAT_BURN)).build(consumer, "minecraft:charcoal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICKS).key('#', Items.BRICK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_brick", hasItem(Items.BRICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICKS, 2).key('#', Blocks.END_STONE).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BRICKS, 2).key('#', Blocks.QUARTZ_BLOCK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICKS, 2).key('#', Blocks.STONE).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_stone", hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICKS).key('#', Items.NETHER_BRICK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_netherbrick", hasItem(Items.NETHER_BRICK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.RED_NETHER_BRICKS).addIngredient(Blocks.NETHER_BRICKS).addIngredient(Items.NETHER_WART).addCriterion("has_nether_wart", hasItem(Items.NETHER_WART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICKS).key('#', Items.PRISMARINE_SHARD).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DARK_PRISMARINE).addIngredient(Blocks.PRISMARINE_BRICKS).addIngredient(Tags.Items.DYES_BLACK).addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(consumer);


        //ALTERNATIVE RECIPES
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 3).patternLine("C").patternLine("S").key('C', RankineItems.LIGNITE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineItems.LIGNITE.get())).setGroup("torch").build(consumer, "rankine:torch_from_lignite");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 4).patternLine("C").patternLine("S").key('C', RankineItems.SUBBITUMINOUS_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineItems.SUBBITUMINOUS_COAL.get())).setGroup("torch").build(consumer, "rankine:torch_from_subbituminous_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 6).patternLine("C").patternLine("S").key('C', RankineItems.BITUMINOUS_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineItems.BITUMINOUS_COAL.get())).setGroup("torch").build(consumer, "rankine:torch_from_bituminous_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 8).patternLine("C").patternLine("S").key('C', RankineItems.ANTHRACITE_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineItems.ANTHRACITE_COAL.get())).setGroup("torch").build(consumer, "rankine:torch_from_anthracite_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 8).patternLine("C").patternLine("S").key('C', RankineItems.COKE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineItems.COKE.get())).setGroup("torch").build(consumer, "rankine:torch_from_coke");

        ShapedRecipeBuilder.shapedRecipe(Items.BUCKET, 2).patternLine("I I").patternLine(" I ").key('I', RankineItems.STEEL_INGOT.get()).addCriterion("has_ingredient", hasItem(RankineItems.STEEL_INGOT.get())).build(consumer, "rankine:bucket_from_steel");
        ShapedRecipeBuilder.shapedRecipe(Items.BUCKET, 1).patternLine("I I").patternLine(" I ").key('I', RankineItems.BRASS_INGOT.get()).addCriterion("has_ingredient", hasItem(RankineItems.BRASS_INGOT.get())).build(consumer, "rankine:bucket_from_brass");
        ShapedRecipeBuilder.shapedRecipe(Items.BUCKET, 1).patternLine("I I").patternLine(" I ").key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:bucket_from_crafting_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.DIAMOND_HORSE_ARMOR, 1).patternLine("I I").patternLine("III").patternLine("ITI").key('I', Tags.Items.GEMS_DIAMOND).key('T', RankineItems.SADDLE_TREE.get()).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:diamond_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shapedRecipe(Items.GOLDEN_HORSE_ARMOR, 1).patternLine("I I").patternLine("III").patternLine("ITI").key('I', Tags.Items.INGOTS_GOLD).key('T', RankineItems.SADDLE_TREE.get()).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:gold_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shapedRecipe(Items.IRON_HORSE_ARMOR, 1).patternLine("I I").patternLine("III").patternLine("ITI").key('I', Tags.Items.INGOTS_IRON).key('T', RankineItems.SADDLE_TREE.get()).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:iron_horse_armor_from_saddle_tree");

        ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER,4).addIngredient(Items.CHARCOAL).addIngredient(RankineTags.Items.SULFUR).addIngredient(Items.BONE_MEAL).addCriterion("has_ingredient", hasItem(RankineItems.SULFUR.get())).build(consumer, "rankine:gunpowder_from_bonemeal");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER,1).addIngredient(Items.CHARCOAL).addIngredient(RankineTags.Items.SULFUR).addIngredient(RankineTags.Items.SALTPETER).addIngredient(RankineTags.Items.SALTPETER).addCriterion("has_ingredient", hasItem(RankineItems.SULFUR.get())).build(consumer, "rankine:gunpowder_from_saltpeter");

        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL,2).addIngredient(RankineItems.ANTHRACITE_COAL.get()).addCriterion("has_ingredient", hasItem(RankineItems.ANTHRACITE_COAL.get())).setGroup("coal").build(consumer, "rankine:coal_from_anthracite");
        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL,3).addIngredient(RankineItems.BITUMINOUS_COAL.get()).addIngredient(RankineItems.BITUMINOUS_COAL.get()).addCriterion("has_ingredient", hasItem(RankineItems.BITUMINOUS_COAL.get())).setGroup("coal").build(consumer, "rankine:coal_from_bituminous");
        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL,1).addIngredient(RankineItems.SUBBITUMINOUS_COAL.get()).addCriterion("has_ingredient", hasItem(RankineItems.SUBBITUMINOUS_COAL.get())).setGroup("coal").build(consumer, "rankine:coal_from_subbituminous");
        ShapelessRecipeBuilder.shapelessRecipe(Items.COAL,2).addIngredient(RankineItems.LIGNITE.get()).addIngredient(RankineItems.LIGNITE.get()).addIngredient(RankineItems.LIGNITE.get()).addCriterion("has_ingredient", hasItem(RankineItems.LIGNITE.get())).setGroup("coal").build(consumer, "rankine:coal_from_lignite");

        ShapedRecipeBuilder.shapedRecipe(Items.EXPERIENCE_BOTTLE, 1).patternLine(" C ").patternLine("IMI").patternLine(" I ").key('C',RankineItems.CORK.get()).key('I', RankineTags.Items.HARDENED_GLASS).key('M', RankineTags.Items.INGOTS_MISCHMETAL).addCriterion("has_ingredient", hasItem(RankineItems.CORK.get())).build(consumer, "rankine:xpbottle_from_mischmetal");
        ShapedRecipeBuilder.shapedRecipe(Items.GLASS_BOTTLE, 1).patternLine(" C ").patternLine("I I").patternLine(" I ").key('C',RankineItems.CORK.get()).key('I', Tags.Items.GLASS).addCriterion("has_ingredient", hasItem(RankineItems.CORK.get())).build(consumer, "rankine:glass_bottle_from_cork");
        ShapedRecipeBuilder.shapedRecipe(Items.BELL, 1).patternLine("SRS").patternLine("SBS").patternLine("S S").key('S', Tags.Items.STONE).key('R', Tags.Items.RODS_WOODEN).key('B', RankineItems.BRASS_INGOT.get()).addCriterion("has_ingredient", hasItem(RankineItems.BRASS_INGOT.get())).build(consumer, "rankine:bell_from_brass");
        ShapedRecipeBuilder.shapedRecipe(Items.LODESTONE, 1).patternLine("SSS").patternLine("SLS").patternLine("SSS").key('S', Items.CHISELED_STONE_BRICKS).key('L', RankineItems.LODESTONE.get()).addCriterion("has_ingredient", hasItem(RankineItems.LODESTONE.get())).build(consumer, "rankine:lodestone_from_lodestone");
        ShapedRecipeBuilder.shapedRecipe(Items.BLAST_FURNACE, 1).patternLine("SBS").patternLine("SFS").patternLine("BBB").key('S',RankineTags.Items.SHEETMETAL).key('F', Items.FURNACE).key('B', ItemTags.STONE_BRICKS).addCriterion("has_ingredient", hasItem(RankineTags.Items.SHEETMETAL)).build(consumer, "rankine:blast_furnace__from_sheetmetal");
        ShapedRecipeBuilder.shapedRecipe(Items.SADDLE, 1).patternLine("SSS").patternLine("SLS").key('S', RankineItems.SADDLE_TREE.get()).key('L', Tags.Items.LEATHER).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:saddle_from_saddle_tree");
        //ShapedRecipeBuilder.shapedRecipe(Items.SCAFFOLDING, 1).patternLine("RRR").patternLine("SLS").key('S', RankineItems.SADDLE_TREE.get()).key('L', Tags.Items.LEATHER).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:saddle_from_saddle_tree");
        ShapedRecipeBuilder.shapedRecipe(Items.STRING, 1).patternLine("SSS").key('S', RankineTags.Items.CROPS_COTTON).addCriterion("has_ingredient", hasItem(RankineTags.Items.CROPS_COTTON)).build(consumer, "rankine:string_from_cotton");

        ShapelessRecipeBuilder.shapelessRecipe(Items.LEATHER,1).addIngredient(RankineItems.SYNTHETIC_LEATHER.get()).addIngredient(RankineItems.SYNTHETIC_LEATHER.get()).addIngredient(RankineItems.SYNTHETIC_LEATHER.get()).addIngredient(RankineItems.SYNTHETIC_LEATHER.get()).addCriterion("has_ingredient", hasItem(RankineItems.SYNTHETIC_LEATHER.get())).build(consumer, "rankine:leather_from_synthetic_leather");
        ShapelessRecipeBuilder.shapelessRecipe(Items.LEATHER,1).addIngredient(RankineItems.BEAVER_PELT.get()).addIngredient(RankineItems.BEAVER_PELT.get()).addIngredient(RankineItems.BEAVER_PELT.get()).addIngredient(RankineItems.BEAVER_PELT.get()).addCriterion("has_ingredient", hasItem(RankineItems.BEAVER_PELT.get())).build(consumer, "rankine:leather_from_beaver_pelt");

        ShapelessRecipeBuilder.shapelessRecipe(Items.MUSHROOM_STEW,1).addIngredient(RankineTags.Items.EDIBLE_MUSHROOMS).addIngredient(RankineTags.Items.EDIBLE_MUSHROOMS).addIngredient(RankineTags.Items.EDIBLE_MUSHROOMS).addIngredient(Items.BOWL).addCriterion("has_ingredient", hasItem(RankineTags.Items.EDIBLE_MUSHROOMS)).build(consumer, "rankine:mushroom_stew_from_edible_mushrooms");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW,1).addIngredient(Items.BAKED_POTATO).addIngredient(Items.COOKED_RABBIT).addIngredient(Items.BOWL).addIngredient(Items.CARROT).addIngredient(RankineTags.Items.EDIBLE_MUSHROOMS).addCriterion("has_ingredient", hasItem(Items.COOKED_RABBIT)).build(consumer, "rankine:rabbit_stew_from_edible_mushrooms");


        ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR,2).addIngredient(RankineItems.BONE_CHAR.get()).addIngredient(Items.SUGAR_CANE).addCriterion("has_ingredient", hasItem(RankineItems.BONE_CHAR.get())).build(consumer, "rankine:sugar_from_bone_char");
        ShapelessRecipeBuilder.shapelessRecipe(Items.SUGAR,2).addIngredient(RankineItems.MAPLE_SYRUP.get()).addCriterion("has_ingredient", hasItem(RankineItems.MAPLE_SYRUP.get())).build(consumer, "rankine:sugar_from_maple_syrup");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL,4).addIngredient(Items.WATER_BUCKET).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addIngredient(RankineTags.Items.SULFUR).addCriterion("has_ingredient", hasItem(RankineTags.Items.SULFUR)).build(consumer, "rankine:bonemeal_from_ash");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE_MEAL,12).addIngredient(Items.WATER_BUCKET).addIngredient(RankineItems.BONE_ASH.get()).addIngredient(RankineItems.BONE_ASH.get()).addIngredient(RankineTags.Items.SULFUR).addCriterion("has_ingredient", hasItem(RankineTags.Items.SULFUR)).build(consumer, "rankine:bonemeal_from_bone_ash");

        //ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 1).patternLine("SS ").patternLine("SB ").patternLine("  S").key('S', Tags.Items.STRING).key('L', RankineItems.LODESTONE.get()).addCriterion("has_ingredient", hasItem(RankineItems.LODESTONE.get())).build(consumer, "rankine:lodestone_from_lodestone");

        ShapedRecipeBuilder.shapedRecipe(Items.CHAIN, 1).patternLine("N").patternLine("I").patternLine("N").key('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:chain_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.HOPPER, 1).patternLine("I I").patternLine("ICI").patternLine(" I ").key('C', Tags.Items.CHESTS).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:hopper_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.RAIL, 16).patternLine("I I").patternLine("ISI").patternLine("I I").key('S', Tags.Items.RODS_WOODEN).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:rail_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.POWERED_RAIL, 6).patternLine("I I").patternLine("ISI").patternLine("IRI").key('S', Tags.Items.RODS_WOODEN).key('I', RankineTags.Items.COLORED_GOLD_INGOTS).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.COLORED_GOLD_INGOTS)).build(consumer, "rankine:powered_rail_from_golds");
        ShapedRecipeBuilder.shapedRecipe(Items.POWERED_RAIL, 8).patternLine("I I").patternLine("ISI").patternLine("IRI").key('S', Tags.Items.RODS_WOODEN).key('I', RankineTags.Items.RODS_GRAPHITE).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.COLORED_GOLD_INGOTS)).build(consumer, "rankine:powered_rail_from_graphite");
        ShapedRecipeBuilder.shapedRecipe(Items.DETECTOR_RAIL, 6).patternLine("I I").patternLine("IRI").patternLine("IPI").key('P', RankineTags.Items.STONE_PRESSURE_PLATES).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:detector_rail_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.ACTIVATOR_RAIL, 6).patternLine("ISI").patternLine("ITI").patternLine("ISI").key('S', Tags.Items.RODS_WOODEN).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).key('T', Items.REDSTONE_TORCH).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:activator_rail_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.MINECART, 1).patternLine("I I").patternLine("III").key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:minecart_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.SHEARS, 1).patternLine("I ").patternLine(" I").key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer, "rankine:shears_from_metals");
        ShapedRecipeBuilder.shapedRecipe(Items.NAME_TAG, 1).patternLine(" I ").patternLine("I I").patternLine("CI ").key('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).key('C',RankineItems.CORK.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STAINLESS_STEEL)).build(consumer, "rankine:nametag_from_stainless_steel");
        ShapedRecipeBuilder.shapedRecipe(Items.DAYLIGHT_DETECTOR, 6).patternLine("C").patternLine("S").key('S', ItemTags.WOODEN_SLABS).key('C',RankineItems.CADMIUM_TELLURIDE_CELL.get()).addCriterion("has_ingredient", hasItem(RankineItems.CADMIUM_TELLURIDE_CELL.get())).build(consumer, "rankine:daylight_detector_from_cell");



        //RANKINE
        for (Block SHEET : RankineLists.SHEETMETALS) {
            String baseName = SHEET.getRegistryName().getPath();
            Block SLAB = RankineLists.SHEETMETAL_SLABS.get(RankineLists.SHEETMETALS.indexOf(SHEET));
            Block VSLAB = RankineLists.SHEETMETAL_VERTICAL_SLABS.get(RankineLists.SHEETMETALS.indexOf(SHEET));
            slab(consumer,SLAB.asItem(),SHEET.asItem(), "sheetmetal_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).setGroup("sheetmetal_slab_from_vslab").addCriterion("has_ingredient", hasItem(SHEET)).build(consumer,"rankine:"+baseName+"_sheetmetal_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).setGroup("sheetmetal_vslab_from_slab").addCriterion("has_ingredient", hasItem(SHEET)).build(consumer,"rankine:"+baseName+"_sheetmetal_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SHEET).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(SHEET)).build(consumer,"rankine:"+baseName+"_sheetmetal_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(SHEET).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(SHEET)).build(consumer,"rankine:"+baseName+"_sheetmetal_from_slab");
        }
        for (Block SANDSTONE : RankineLists.SANDSTONES) {
            String baseName = SANDSTONE.getRegistryName().getPath();
            Block CHISELED = RankineLists.CHISELED_SANDSTONES.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block SAND = RankineLists.SANDS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block SLAB = RankineLists.SANDSTONE_SLABS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block VSLAB = RankineLists.SANDSTONE_VERTICAL_SLABS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block STAIRS = RankineLists.SANDSTONE_STAIRS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block WALL = RankineLists.SANDSTONE_WALLS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            ShapedRecipeBuilder.shapedRecipe(SANDSTONE, 1).patternLine("SS").patternLine("SS").key('S', SAND).addCriterion("has_ingredient", hasItem(SAND)).build(consumer);
            ShapedRecipeBuilder.shapedRecipe(CHISELED, 1).patternLine("S").patternLine("S").key('S', SLAB).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer);
            slab(consumer,SLAB.asItem(),SANDSTONE.asItem(), "sandstone_slab");
            stairs(consumer,STAIRS.asItem(),SANDSTONE.asItem(), "sandstone_stairs");
            wall(consumer,WALL.asItem(),SANDSTONE.asItem(), "sandstone_wall");
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).setGroup("sandstone_slab_from_vslab").addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).setGroup("sandstone_vslab_from_slab").addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SANDSTONE).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(SANDSTONE).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SANDSTONE,6).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SANDSTONE), SLAB, 2).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SANDSTONE), VSLAB, 2).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SANDSTONE), STAIRS).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SANDSTONE), WALL).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"__stonecutting");

        }
        for (Block SMOOTH_SANDSTONE : RankineLists.SMOOTH_SANDSTONES) {
            String baseName = SMOOTH_SANDSTONE.getRegistryName().getPath();
            Block SANDSTONE = RankineLists.SANDSTONES.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block SLAB = RankineLists.SMOOTH_SANDSTONE_SLABS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block VSLAB = RankineLists.SMOOTH_SANDSTONE_VERTICAL_SLABS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block STAIRS = RankineLists.SMOOTH_SANDSTONE_STAIRS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block WALL = RankineLists.SMOOTH_SANDSTONE_WALLS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(SANDSTONE), SMOOTH_SANDSTONE, 0.1F, 200).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer);
            slab(consumer,SLAB.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_slab");
            stairs(consumer,STAIRS.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_stairs");
            wall(consumer,WALL.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_wall");
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).setGroup("smooth_sandstone_slab_from_vslab").addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).setGroup("smooth_sandstone_vslab_from_slab").addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SMOOTH_SANDSTONE).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(SMOOTH_SANDSTONE).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SMOOTH_SANDSTONE,6).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SMOOTH_SANDSTONE), SLAB, 2).addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SMOOTH_SANDSTONE), VSLAB, 2).addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SMOOTH_SANDSTONE), STAIRS).addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(SMOOTH_SANDSTONE), WALL).addCriterion("has_ingredient", hasItem(SMOOTH_SANDSTONE)).build(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"__stonecutting");

        }
        for (Block CUT_SANDSTONE : RankineLists.CUT_SANDSTONES) {
            String baseName = CUT_SANDSTONE.getRegistryName().getPath();
            Block SANDSTONE = RankineLists.SANDSTONES.get(RankineLists.CUT_SANDSTONES.indexOf(CUT_SANDSTONE));
            Block SLAB = RankineLists.CUT_SANDSTONE_SLABS.get(RankineLists.CUT_SANDSTONES.indexOf(CUT_SANDSTONE));
            Block VSLAB = RankineLists.CUT_SANDSTONE_VERTICAL_SLABS.get(RankineLists.CUT_SANDSTONES.indexOf(CUT_SANDSTONE));
            ShapedRecipeBuilder.shapedRecipe(CUT_SANDSTONE, 1).patternLine("SS").patternLine("SS").key('S', SANDSTONE).addCriterion("has_ingredient", hasItem(SANDSTONE)).build(consumer);
            slab(consumer,SLAB.asItem(),CUT_SANDSTONE.asItem(), "cut_sandstone_slab");
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).setGroup("cut_sandstone_slab_from_vslab").addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).setGroup("cut_sandstone_vslab_from_slab").addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(CUT_SANDSTONE).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(CUT_SANDSTONE).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer,"rankine:"+baseName+"_from_slab");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(CUT_SANDSTONE), SLAB, 2).addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(CUT_SANDSTONE), VSLAB, 2).addCriterion("has_ingredient", hasItem(CUT_SANDSTONE)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
        }
        for (Block BRICK : RankineLists.BRICKS) {
            String baseName = BRICK.getRegistryName().getPath();
            Block SLAB = RankineLists.BRICKS_SLAB.get(RankineLists.BRICKS.indexOf(BRICK));
            Block VSLAB = RankineLists.BRICKS_VERTICAL_SLAB.get(RankineLists.BRICKS.indexOf(BRICK));
            Block STAIRS = RankineLists.BRICKS_STAIRS.get(RankineLists.BRICKS.indexOf(BRICK));
            Block WALL = RankineLists.BRICKS_WALL.get(RankineLists.BRICKS.indexOf(BRICK));
            slab(consumer,SLAB.asItem(),BRICK.asItem(), "bricks_slab");
            stairs(consumer,STAIRS.asItem(),BRICK.asItem(), "bricks_stairs");
            wall(consumer,WALL.asItem(),BRICK.asItem(), "bricks_wall");
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).setGroup("bricks_slab_from_vslab").addCriterion("has_ingredient", hasItem(BRICK)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).setGroup("bricks_vslab_from_slab").addCriterion("has_ingredient", hasItem(BRICK)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BRICK).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(BRICK)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(BRICK).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(BRICK)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BRICK,6).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(BRICK)).build(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BRICK), SLAB, 2).addCriterion("has_ingredient", hasItem(BRICK)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BRICK), VSLAB, 2).addCriterion("has_ingredient", hasItem(BRICK)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BRICK), STAIRS).addCriterion("has_ingredient", hasItem(BRICK)).build(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BRICK), WALL).addCriterion("has_ingredient", hasItem(BRICK)).build(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"__stonecutting");

        }
        for (Block BLOCK : RankineLists.MISC_BLOCKS) {
            String baseName = BLOCK.getRegistryName().getPath();
            Block SLAB = RankineLists.MISC_SLABS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block VSLAB = RankineLists.MISC_VERTICAL_SLABS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block STAIRS = RankineLists.MISC_STAIRS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block WALL = RankineLists.MISC_WALLS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            slab(consumer,SLAB.asItem(),BLOCK.asItem());
            stairs(consumer,STAIRS.asItem(),BLOCK.asItem());
            wall(consumer,WALL.asItem(),BLOCK.asItem());
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK,6).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_stairs");

            if (Arrays.asList(RankineBlocks.SKARN.get(),RankineBlocks.BRECCIA.get()).contains(BLOCK)) {
                SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), SLAB, 2).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
                SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), VSLAB, 2).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
                SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), STAIRS).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"__stonecutting");
                SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), WALL).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"__stonecutting");

            }
        }
        for (Block BLOCK : RankineLists.CONCRETE_BLOCKS) {
            String baseName = BLOCK.getRegistryName().getPath();
            Block SLAB = RankineLists.QUARTER_SLABS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            Block VSLAB = RankineLists.CONCRETE_VERTICAL_SLABS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            Block STAIRS = RankineLists.CONCRETE_STAIRS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            Block WALL = RankineLists.CONCRETE_WALLS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            slab(consumer,SLAB.asItem(),BLOCK.asItem());
            stairs(consumer,STAIRS.asItem(),BLOCK.asItem());
            wall(consumer,WALL.asItem(),BLOCK.asItem());
            ShapelessRecipeBuilder.shapelessRecipe(SLAB).addIngredient(VSLAB).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(VSLAB).addIngredient(SLAB).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK).addIngredient(VSLAB).addIngredient(VSLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK).addIngredient(SLAB).addIngredient(SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(BLOCK,6).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).addIngredient(STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer,"rankine:"+baseName+"_from_stairs");

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), SLAB, 2).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), VSLAB, 2).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_vertical_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), STAIRS).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(BLOCK), WALL).addCriterion("has_ingredient", hasItem(BLOCK)).build(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"__stonecutting");

        }








        ShapedRecipeBuilder.shapedRecipe(RankineItems.DIVING_BOOTS.get(), 1).patternLine("R R").patternLine("I I").patternLine("I I").key('I', RankineTags.Items.INGOTS_BRASS).key('R', RankineTags.Items.RUBBER).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.DIVING_CHESTPLATE.get(), 1).patternLine("IRI").patternLine("III").patternLine("III").key('I', RankineTags.Items.INGOTS_BRASS).key('R', RankineTags.Items.RUBBER).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.DIVING_LEGGINGS.get(), 1).patternLine("IRI").patternLine("I I").patternLine("I I").key('I', RankineTags.Items.INGOTS_BRASS).key('R', RankineTags.Items.RUBBER).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.DIVING_HELMET.get(), 1).patternLine("III").patternLine("IGI").patternLine("RRR").key('I', RankineTags.Items.INGOTS_BRASS).key('R', RankineTags.Items.RUBBER).key('G', Tags.Items.GLASS).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);


        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),4).addIngredient(RankineItems.JUTE.get()).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_jute");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.VINE).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_vine");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.WEEPING_VINES).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_weeping_vines");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.TWISTING_VINES).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_twisting_vines");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.TALL_GRASS).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_tall_grass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.LARGE_FERN).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_large_fern");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.GRASS).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_grass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.SEAGRASS).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_seagrass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.FERN).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_fern");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(RankineItems.SHORT_GRASS.get()).addIngredient(RankineItems.SHORT_GRASS.get()).addCriterion("has_ingredient", hasItem(Items.STICK)).setGroup("plant_fiber").build(consumer, "rankine:plant_fiber_from_short_grass");

        ShapedRecipeBuilder.shapedRecipe(RankineItems.GF_BREAD.get(), 1).patternLine("###").key('#', RankineItems.MILLET.get()).setGroup("gf_bread").addCriterion("has_ingredient", hasItem(RankineItems.MILLET.get())).build(consumer,"rankine:gf_bread_from_millet");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.GF_BREAD.get(), 1).patternLine("###").key('#', RankineItems.OATS.get()).setGroup("gf_bread").addCriterion("has_ingredient", hasItem(RankineItems.OATS.get())).build(consumer,"rankine:gf_bread_from_oats");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.GF_BREAD.get(), 1).patternLine("###").key('#', RankineItems.SORGHUM.get()).setGroup("gf_bread").addCriterion("has_ingredient", hasItem(RankineItems.SORGHUM.get())).build(consumer,"rankine:gf_bread_from_sorghum");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.GF_BREAD.get(), 1).patternLine("###").key('#', RankineItems.RICE.get()).setGroup("gf_bread").addCriterion("has_ingredient", hasItem(RankineItems.RICE.get())).build(consumer,"rankine:gf_bread_from_rice");
        ShapedRecipeBuilder.shapedRecipe(Items.BREAD, 1).patternLine("###").key('#', RankineItems.RYE.get()).setGroup("bread").addCriterion("has_ingredient", hasItem(RankineItems.RYE.get())).build(consumer,"rankine:bread_from_rye");
        ShapedRecipeBuilder.shapedRecipe(Items.BREAD, 1).patternLine("###").key('#', RankineItems.BARLEY.get()).setGroup("bread").addCriterion("has_ingredient", hasItem(RankineItems.BARLEY.get())).build(consumer,"rankine:bread_from_barley");


        //pumice soap recipes
        ShapelessRecipeBuilder.shapelessRecipe(Items.COBBLESTONE,1).addIngredient(Items.MOSSY_COBBLESTONE).addIngredient(RankineItems.PUMICE_SOAP.get()).addCriterion("has_ingredient", hasItem(RankineItems.PUMICE_SOAP.get())).build(consumer, "rankine:cobblestone_from_pumice_soap");
        ShapelessRecipeBuilder.shapelessRecipe(Items.STONE_BRICKS,1).addIngredient(Items.MOSSY_STONE_BRICKS).addIngredient(RankineItems.PUMICE_SOAP.get()).addCriterion("has_ingredient", hasItem(RankineItems.PUMICE_SOAP.get())).build(consumer, "rankine:stone_bricks_from_pumice_soap");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLAZE_POWDER,1).addIngredient(Items.MAGMA_CREAM).addIngredient(RankineItems.PUMICE_SOAP.get()).addCriterion("has_ingredient", hasItem(RankineItems.PUMICE_SOAP.get())).build(consumer, "rankine:blaze_powder_from_puumice_soap");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PISTON,1).addIngredient(Items.STICKY_PISTON).addIngredient(RankineItems.PUMICE_SOAP.get()).addCriterion("has_ingredient", hasItem(RankineItems.PUMICE_SOAP.get())).build(consumer, "rankine:piston_from_pumice_soap");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MINERAL_WOOL.get(),1).addIngredient(RankineTags.Items.MINERAL_WOOL).addIngredient(RankineItems.BLEACH.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.MINERAL_WOOL)).build(consumer, "rankine:mineral_wool_from_colors");
        ShapelessRecipeBuilder.shapelessRecipe(Items.TERRACOTTA,1).addIngredient(RankineTags.Items.TERRACOTTA).addIngredient(RankineItems.BLEACH.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.MINERAL_WOOL)).build(consumer, "rankine:terracotta_from_colors");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GLASS,1).addIngredient(Tags.Items.GLASS).addIngredient(RankineItems.BLEACH.get()).addCriterion("has_ingredient", hasItem(Tags.Items.GLASS)).build(consumer, "rankine:glass_from_colors");
        //

        ShapedRecipeBuilder.shapedRecipe(Blocks.COBBLESTONE, 1).patternLine("###").patternLine("###").patternLine("###").key('#', RankineTags.Items.COBBLES).addCriterion("has_ingredient", hasItem(RankineTags.Items.COBBLES)).build(consumer,"rankine:cobblestone_from_cobbles");
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.SOD_BLOCK.get(), 4).patternLine("##").patternLine("##").key('#', RankineTags.Items.GRASS_BLOCKS).addCriterion("has_ingredient", hasItem(RankineTags.Items.GRASS_BLOCKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.KAOLIN.get(), 1).patternLine("##").patternLine("##").key('#', RankineItems.KAOLINITE.get()).addCriterion("has_ingredient", hasItem(RankineItems.KAOLINITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FIRE_CLAY.get(), 1).patternLine("##").patternLine("##").key('#', RankineItems.FIRE_CLAY_BALL.get()).addCriterion("has_ingredient", hasItem(RankineItems.KAOLINITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.PACKED_SNOW.get(), 2).patternLine("B#").patternLine("#B").key('#', Items.SNOW_BLOCK).key('B', Items.SNOWBALL).addCriterion("has_ingredient", hasItem(Items.SNOWBALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ICE_BRICKS.get(), 2).patternLine("B#").patternLine("#B").key('#', RankineTags.Items.ICE).key('B', Items.SNOWBALL).addCriterion("has_ingredient", hasItem(Items.SNOWBALL)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ASPHALT.get(),6).addIngredient(RankineItems.BITUMEN.get()).addIngredient(Items.GRAVEL).addIngredient(Items.GRAVEL).addIngredient(Items.SAND).addCriterion("has_ingredient", hasItem(RankineItems.BITUMEN.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ROMAN_CONCRETE.get(),8).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineItems.ALUMINA.get()).addIngredient(Tags.Items.GEMS_QUARTZ).addIngredient(RankineItems.CEMENT_MIX.get()).addIngredient(RankineItems.CEMENT_MIX.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.TUFF)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BANDAGE.get(),3).addIngredient(RankineItems.RESIN_BUCKET.get()).addIngredient(RankineItems.ALOE.get()).addIngredient(Items.PAPER).addIngredient(Items.PAPER).addIngredient(Items.PAPER).addCriterion("has_ingredient", hasItem(RankineItems.ALOE.get())).setGroup("bandage").build(consumer, "rankine:bandage_from_paper");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BANDAGE.get(),6).addIngredient(RankineItems.RESIN_BUCKET.get()).addIngredient(RankineItems.ALOE.get()).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addCriterion("has_ingredient", hasItem(RankineItems.ALOE.get())).setGroup("bandage").build(consumer, "rankine:bandage_from_cotton");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BANDAGE.get(),12).addIngredient(RankineItems.RESIN_BUCKET.get()).addIngredient(RankineItems.ALOE.get()).addIngredient(RankineItems.STINGING_NETTLE.get()).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addCriterion("has_ingredient", hasItem(RankineItems.ALOE.get())).setGroup("bandage").build(consumer, "rankine:bandage_from_nettle");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GUN_COTTON.get(),8).addIngredient(RankineTags.Items.SALTPETER).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addIngredient(RankineTags.Items.CROPS_COTTON).addCriterion("has_ingredient", hasItem(RankineTags.Items.SALTPETER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CINNAMON_TOAST.get(),1).addIngredient(RankineItems.TOAST.get()).addIngredient(RankineItems.CINNAMON.get()).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineItems.TOAST.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PINA_COLADA.get(),1).addIngredient(RankineTags.Items.ICE).addIngredient(RankineItems.COCONUT.get()).addIngredient(RankineTags.Items.PINEAPPLE).addCriterion("has_ingredient", hasItem(RankineItems.PINEAPPLE.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PULP.get(),1).addIngredient(Items.WATER_BUCKET).addIngredient(RankineTags.Items.CLAY_BALLS).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addCriterion("has_ingredient", hasItem(RankineTags.Items.SAWDUST)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PULP.get(),4).addIngredient(Items.WATER_BUCKET).addIngredient(RankineItems.SODIUM_HYDROXIDE.get()).addIngredient(RankineItems.SODIUM_SULFIDE.get()).addIngredient(RankineTags.Items.CLAY_BALLS).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addIngredient(RankineTags.Items.SAWDUST).addCriterion("has_ingredient", hasItem(RankineTags.Items.SAWDUST)).build(consumer,"rankine:pulp_kraft");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.TRAIL_MIX.get(),1).addIngredient(RankineTags.Items.BERRIES).addIngredient(RankineItems.ROASTED_WALNUT.get()).addIngredient(RankineItems.TOASTED_COCONUT.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PAPER,1).addIngredient(RankineItems.ALLOY_TEMPLATE.get()).addCriterion("has_ingredient", hasItem(RankineItems.ALLOY_TEMPLATE.get())).build(consumer, "rankine:alloy_template_clear");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GROUND_TAP.get(),1).addIngredient(RankineItems.METAL_PIPE.get()).addCriterion("has_ingredient", hasItem(RankineItems.METAL_PIPE.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.VULCANIZED_RUBBER.get(),2).addIngredient(RankineItems.DRY_RUBBER.get()).addIngredient(Items.BONE_MEAL).addIngredient(RankineTags.Items.SULFUR).addIngredient(RankineTags.Items.NUGGETS_CARBON).addCriterion("has_ingredient", hasItem(RankineItems.DRY_RUBBER.get())).build(consumer, "rankine:vulcanized_rubber_from_sulfur");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.VULCANIZED_RUBBER.get(),2).addIngredient(RankineItems.DRY_RUBBER.get()).addIngredient(Items.BONE_MEAL).addIngredient(RankineItems.SODIUM_SULFIDE.get()).addIngredient(RankineTags.Items.NUGGETS_CARBON).addCriterion("has_ingredient", hasItem(RankineItems.DRY_RUBBER.get())).build(consumer, "rankine:vulcanized_rubber_from_sodium_sulfide");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.COMPRESSED_BIOMASS.get(),1).addIngredient(RankineItems.BIOMASS.get()).addIngredient(RankineItems.BIOMASS.get()).addIngredient(RankineItems.BIOMASS.get()).addIngredient(RankineItems.BIOMASS.get()).addCriterion("has_ingredient", hasItem(RankineItems.BIOMASS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SYNTHETIC_LEATHER.get(), 1).patternLine("PPP").patternLine("CSC").patternLine("PPP").key('S', RankineTags.Items.SALTPETER).key('C', RankineItems.CAMPHOR_BASIL_LEAF.get()).key('P', RankineItems.PULP.get()).addCriterion("has_ingredient", hasItem(RankineItems.PULP.get())).build(consumer);


        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CARBON_NUGGET.get(),1).addIngredient(RankineTags.Items.COKE).addCriterion("has_ingredient", hasItem(RankineTags.Items.COKE)).setGroup("carbon_nugget").build(consumer, "rankine:carbon_from_coke");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CARBON_NUGGET.get(),3).addIngredient(RankineTags.Items.GRAPHITE).addCriterion("has_ingredient", hasItem(RankineTags.Items.GRAPHITE)).setGroup("carbon_nugget").build(consumer, "rankine:carbon_from_graphite");

        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MORTAR.get(),4).addIngredient(RankineItems.CEMENT_MIX.get()).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineItems.CEMENT_MIX.get())).setGroup("mortar").build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_KNIFE.get(), 1).patternLine(" F").patternLine("F ").key('F', Items.FLINT).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_AXE.get(), 1).patternLine("RF").patternLine("S ").key('F', Items.FLINT).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_PICKAXE.get(), 1).patternLine("FRF").patternLine(" S ").patternLine(" S ").key('F', Items.FLINT).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_SPEAR.get(), 1).patternLine(" FF").patternLine(" RF").patternLine("S  ").key('F', Items.FLINT).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_HOE.get(), 1).patternLine("RF").patternLine("S ").patternLine("S ").key('F', Items.FLINT).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FLINT_SHOVEL.get(), 1).patternLine("F").patternLine("R").patternLine("S").key('F', Items.FLINT).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.FLINT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.WOODEN_MALLET.get(), 1).patternLine("PPP").patternLine("PSP").patternLine(" S ").key('P', ItemTags.PLANKS).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.STONE_MALLET.get(), 1).patternLine("PPP").patternLine("PSP").patternLine(" S ").key('P', Tags.Items.COBBLESTONE).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Tags.Items.COBBLESTONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.BUILDING_TOOL.get(), 1).patternLine("PRP").patternLine("PRP").patternLine(" S ").key('P', Tags.Items.STONE).key('S', Tags.Items.RODS_WOODEN).key('R', RankineTags.Items.ROPE).addCriterion("has_ingredient", hasItem(Tags.Items.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ROPE_COIL_ARROW.get(), 1).patternLine("T").patternLine("S").patternLine("F").key('F', Tags.Items.FEATHERS).key('T', RankineTags.Items.ROPE).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(RankineTags.Items.ROPE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.MAGNESIUM_ARROW.get(), 4).patternLine("T").patternLine("S").patternLine("F").key('F', Tags.Items.FEATHERS).key('T', RankineTags.Items.INGOTS_MAGNESIUM).key('S', RankineTags.Items.RODS_GRAPHITE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_MAGNESIUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.MAGNESIUM_ARROW.get(), 4).patternLine("T").patternLine("S").patternLine("F").key('F', Tags.Items.FEATHERS).key('T', RankineTags.Items.INGOTS_MAGNESIUM_ALLOY).key('S', RankineTags.Items.RODS_GRAPHITE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_MAGNESIUM_ALLOY)).build(consumer,"rankine:magnesium_arrow_from_magnesium_alloy");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.FIRE_EXTINGUISHER.get(), 1).patternLine("INI").patternLine("IWI").patternLine("III").key('W', Items.WATER_BUCKET).key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).key('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.PROSPECTING_STICK.get(), 1).patternLine(" SN").patternLine(" RS").patternLine("S  ").key('N', Tags.Items.NUGGETS).key('R', RankineItems.ROPE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Tags.Items.NUGGETS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.HARDNESS_TESTER.get(), 1).patternLine("  R").patternLine(" S ").patternLine("G  ").key('G', Tags.Items.GLASS).key('R', Tags.Items.STONE).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Tags.Items.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ELEMENT_INDEXER.get(), 1).patternLine("III").patternLine("ICI").patternLine("III").key('I', RankineTags.Items.CRAFTING_METAL_INGOTS).key('C', RankineItems.CADMIUM_TELLURIDE_CELL.get()).addCriterion("has_ingredient", hasItem(RankineItems.CADMIUM_TELLURIDE_CELL.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_COBBLING.get(), 1).patternLine("MHM").patternLine("WML").patternLine(" M ").key('H', RankineTags.Items.HAMMERS).key('M', RankineTags.Items.INGOTS_ROSE_METAL).key('W', Items.WATER_BUCKET).key('L', Items.LAVA_BUCKET).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ROSE_METAL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ALTIMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', RankineTags.Items.INGOTS_BRASS).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.BIOMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', RankineTags.Items.INGOTS_ROSE_METAL).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ROSE_METAL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.PHOTOMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', RankineTags.Items.INGOTS_NICKEL_SILVER).key('R', Items.DAYLIGHT_DETECTOR).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_NICKEL_SILVER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SPEEDOMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', RankineTags.Items.INGOTS_DURALUMIN).key('R', Tags.Items.DUSTS_REDSTONE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_DURALUMIN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.THERMOMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', Tags.Items.GLASS).key('R', RankineTags.Items.MERCURY).addCriterion("has_ingredient", hasItem(RankineTags.Items.MERCURY)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.MAGNETOMETER.get(), 1).patternLine(" I ").patternLine("IRI").patternLine(" I ").key('I', Tags.Items.DUSTS_REDSTONE).key('R', RankineTags.Items.INGOTS_POTASSIUM).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_POTASSIUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_HASTENING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_ROSE_GOLD).key('R', Tags.Items.STORAGE_BLOCKS_EMERALD).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ROSE_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_ENDURING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_BLUE_GOLD).key('R', RankineTags.Items.STORAGE_BLOCKS_GARNET).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BLUE_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_REPULSING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_BLACK_GOLD).key('R', RankineTags.Items.STORAGE_BLOCKS_RUBY).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BLACK_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_TIMESAVING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_STERLING_SILVER).key('R', RankineTags.Items.STORAGE_BLOCKS_SAPPHIRE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STERLING_SILVER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_PROMISING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_WHITE_GOLD).key('R', RankineTags.Items.STORAGE_BLOCKS_TOPAZ).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_WHITE_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_SOFTENING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_PURPLE_GOLD).key('R', RankineTags.Items.STORAGE_BLOCKS_PEARL).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_PURPLE_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_LEVITATING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_OSMIRIDIUM).key('R', RankineTags.Items.STORAGE_BLOCKS_OPAL).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_OSMIRIDIUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_INFUSING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_MISCHMETAL).key('R', RankineTags.Items.STORAGE_BLOCKS_PERIDOT).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_MISCHMETAL)).build(consumer);



        ShapedRecipeBuilder.shapedRecipe(RankineItems.ICE_SKATES.get(), 1).patternLine("L  ").patternLine("RRL").patternLine("NNN").key('L', Tags.Items.LEATHER).key('R', RankineItems.ROPE.get()).key('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).addCriterion("has_ingredient", hasItem(RankineItems.ROPE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SANDALS.get(), 1).patternLine("L  ").patternLine("LRR").key('L', Tags.Items.LEATHER).key('R', RankineItems.ROPE.get()).addCriterion("has_ingredient", hasItem(RankineItems.ROPE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SNOWSHOES.get(), 1).patternLine("SRS").patternLine("SRS").patternLine(" S ").key('S', Tags.Items.RODS_WOODEN).key('R', RankineItems.ROPE.get()).addCriterion("has_ingredient", hasItem(RankineItems.ROPE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.GAS_MASK.get(), 1).patternLine("RRR").patternLine("MRM").patternLine("CMC").key('R', RankineTags.Items.RUBBER).key('C', RankineTags.Items.CARBON).key('M', RankineItems.MICA.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.RUBBER)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RankineItems.DOWSING_ROD.get(), 1).patternLine("#R#").patternLine(" # ").key('#', ItemTags.PLANKS).key('R', RankineTags.Items.ROPE).addCriterion("has_ingredient", hasItem(RankineTags.Items.ROPE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.WOODEN_GOLD_PAN.get(), 1).patternLine("###").patternLine(" # ").key('#', ItemTags.PLANKS).addCriterion("has_ingredient", hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.PEWTER_GOLD_PAN.get(), 1).patternLine("###").patternLine(" # ").key('#', RankineTags.Items.INGOTS_PEWTER).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_PEWTER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.STEEL_GOLD_PAN.get(), 1).patternLine("###").patternLine(" # ").key('#', RankineTags.Items.INGOTS_STEEL).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.RARE_EARTH_MAGNET.get(), 1).patternLine("I I").patternLine("###").key('#', RankineTags.Items.INGOTS_SAMARIUM).key('I', RankineTags.Items.INGOTS_COBALT).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ALNICO)).build(consumer,"rankine:rare_earth_magnet_sm");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.RARE_EARTH_MAGNET.get(), 1).patternLine("I I").patternLine("#B#").key('I', Tags.Items.INGOTS_IRON).key('#', RankineTags.Items.INGOTS_NEODYMIUM).key('B', RankineTags.Items.BORON).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ALNICO)).build(consumer, "rankine:rare_earth_magnet_nd");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ALNICO_MAGNET.get(), 1).patternLine("# #").patternLine("###").key('#', RankineTags.Items.INGOTS_ALNICO).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ALNICO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.GARLAND.get(), 3).patternLine("###").key('#', ItemTags.LEAVES).addCriterion("has_ingredient", hasItem(ItemTags.LEAVES)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ORNAMENT.get(), 8).patternLine(" C ").patternLine("NGN").patternLine(" N ").key('C', Items.CHAIN).key('G', Tags.Items.GLASS).key('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_NUGGETS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SADDLE_TREE.get(), 1).patternLine("III").patternLine("PPP").patternLine("SSS").key('S', RankineTags.Items.INGOTS_STEEL).key('P', ItemTags.PLANKS).key('I', RankineTags.Items.INGOTS_ALUMINUM).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:saddle_tree_from_aluminum");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.SADDLE_TREE.get(), 1).patternLine("III").patternLine("PPP").patternLine("SSS").key('S', RankineTags.Items.INGOTS_STEEL).key('P', ItemTags.PLANKS).key('I', Tags.Items.INGOTS_IRON).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:saddle_tree_from_iron");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.CANNONBALL.get(), 3).patternLine(" I ").patternLine("III").patternLine(" I ").key('I', RankineTags.Items.INGOTS_LEAD).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_LEAD)).build(consumer,"rankine:lead_cannonball");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.CANNONBALL.get(), 3).patternLine(" I ").patternLine("III").patternLine(" I ").key('I', RankineTags.Items.INGOTS_BISMUTH).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BISMUTH)).build(consumer,"rankine:bismuth_cannonball");
        ShapedRecipeBuilder.shapedRecipe(RankineItems.CANNONBALL.get(), 3).patternLine(" I ").patternLine("III").patternLine(" I ").key('I', RankineTags.Items.INGOTS_CAST_IRON).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_CAST_IRON)).build(consumer,"rankine:cast_iron_cannonball");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CARCASS.get()).addIngredient(RankineItems.CANNONBALL.get()).addIngredient(RankineTags.Items.SULFUR).addIngredient(RankineTags.Items.SALTPETER).addIngredient(Items.GUNPOWDER).addIngredient(RankineTags.Items.NUGGETS_ANTIMONY).addCriterion("has_ingredient", hasItem(RankineTags.Items.CANNONBALLS)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(RankineItems.GRAPHITE_ELECTRODE.get(), 1).patternLine("  I").patternLine(" I ").patternLine("I  ").key('I', RankineTags.Items.GRAPHITE).addCriterion("has_ingredient", hasItem(RankineTags.Items.GRAPHITE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.HARD_CARBON_ELECTRODE.get(), 1).patternLine("  I").patternLine(" I ").patternLine("I  ").key('I', RankineTags.Items.CARBON).addCriterion("has_ingredient", hasItem(RankineTags.Items.CARBON)).build(consumer);

        //Machines
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BEEHIVE_OVEN_PIT.get(), 1).patternLine(" S ").patternLine("SLS").patternLine(" S ").key('S', RankineBlocks.REFRACTORY_BRICKS.get()).key('L', Tags.Items.STORAGE_BLOCKS_COAL).addCriterion("has_ingredient", hasItem(RankineItems.REFRACTORY_BRICKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ALLOY_FURNACE.get(), 1).patternLine("BSB").patternLine("BSB").patternLine("BCB").key('B', RankineBlocks.REFRACTORY_BRICKS.get()).key('S', RankineTags.Items.SHEETMETAL).key('C', RankineTags.Items.CAMPFIRES).addCriterion("has_ingredient", hasItem(RankineItems.REFRACTORY_BRICKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.MIXING_BARREL.get(), 1).patternLine("R").patternLine("B").patternLine("S").key('B', Blocks.BARREL).key('S', ItemTags.WOODEN_SLABS).key('R', Tags.Items.RODS_WOODEN).addCriterion("has_ingredient", hasItem(Items.BARREL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CHARCOAL_PIT.get(), 1).patternLine(" S ").patternLine("SLS").patternLine(" S ").key('S', Tags.Items.RODS_WOODEN).key('L', ItemTags.LOGS_THAT_BURN).addCriterion("has_ingredient", hasItem(ItemTags.LOGS_THAT_BURN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.GAS_BOTTLER.get(), 1).patternLine("XSX").patternLine("S S").patternLine("XSX").key('X', RankineTags.Items.SHEETMETAL).key('S', RankineTags.Items.INGOTS_BRASS).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.GAS_VENT.get(), 1).patternLine("XSX").patternLine("SBS").patternLine("XSX").key('X', RankineTags.Items.SHEETMETAL).key('S', RankineItems.BOROSILICATE_GLASS.get()).key('B', Items.GLASS_BOTTLE).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FLOOD_GATE.get(), 1).patternLine(" S ").patternLine("SSS").patternLine(" S ").key('S', RankineTags.Items.CRAFTING_METAL_INGOTS).addCriterion("has_ingredient", hasItem(RankineTags.Items.CRAFTING_METAL_INGOTS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CRUCIBLE_BLOCK.get(), 1).patternLine("R R").patternLine("R R").patternLine("RCR").key('R', RankineItems.HIGH_REFRACTORY_BRICKS.get()).key('C', Items.CAULDRON).addCriterion("has_ingredient", hasItem(RankineItems.HIGH_REFRACTORY_BRICKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.MATERIAL_TESTING_TABLE.get(), 1).patternLine("TT").patternLine("BB").patternLine("BB").key('B', ItemTags.PLANKS).key('T', ItemTags.STONE_BRICKS).addCriterion("has_ingredient", hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TEMPLATE_TABLE.get(), 1).patternLine("TT").patternLine("BB").patternLine("BB").key('B', ItemTags.PLANKS).key('T', Items.PAPER).addCriterion("has_ingredient", hasItem(ItemTags.PLANKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.RARE_EARTH_ELECTROMAGNET.get(), 1).patternLine("WBW").patternLine("WMW").key('W', RankineItems.ALLOY_WIRE.get()).key('B', RankineItems.LITHIUM_ION_BATTERY.get()).key('M', RankineItems.RARE_EARTH_MAGNET.get()).addCriterion("has_ingredient", hasItem(RankineItems.RARE_EARTH_MAGNET.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineItems.ALNICO_ELECTROMAGNET.get(), 1).patternLine("WBW").patternLine("WMW").key('W', RankineItems.ALLOY_WIRE.get()).key('B', RankineItems.LEAD_ACID_BATTERY.get()).key('M', RankineItems.ALNICO_MAGNET.get()).addCriterion("has_ingredient", hasItem(RankineItems.RARE_EARTH_MAGNET.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.AIR_DISTILLATION_PACKING.get(), 1).patternLine("##").patternLine("##").key('#', RankineItems.STAINLESS_STEEL_SHEETMETAL.get()).addCriterion("has_ingredient", hasItem(RankineItems.STAINLESS_STEEL_SHEETMETAL.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.DISTILLATION_TOWER.get(), 1).patternLine("IBI").patternLine("I#I").patternLine("IMI").key('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).key('B', RankineTags.Items.ICE).key('M', Items.MAGMA_BLOCK).key('#', RankineItems.MICA_BLOCK.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STAINLESS_STEEL)).build(consumer);

        //Smithing Recipes
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Items.LEATHER_BOOTS),Ingredient.fromTag(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_BOOTS.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:brigadine_boots_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Items.LEATHER_LEGGINGS),Ingredient.fromTag(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_LEGGINGS.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:brigadine_leggings_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Items.LEATHER_CHESTPLATE),Ingredient.fromTag(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_CHESTPLATE.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:brigadine_chestplate_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Items.LEATHER_HELMET),Ingredient.fromTag(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_HELMET.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer,"rankine:brigadine_helmet_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(RankineItems.DIVING_BOOTS.get()),Ingredient.fromStacks(new ItemStack(Items.PRISMARINE_SHARD,8)),RankineItems.CONDUIT_DIVING_BOOTS.get()).addCriterion("has_ingredient", hasItem(Items.CONDUIT)).build(consumer,"rankine:conduit_diving_boots_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(RankineItems.DIVING_LEGGINGS.get()),Ingredient.fromStacks(new ItemStack(Items.PRISMARINE_CRYSTALS,8)),RankineItems.CONDUIT_DIVING_LEGGINGS.get()).addCriterion("has_ingredient", hasItem(Items.CONDUIT)).build(consumer,"rankine:conduit_diving_leggings_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(RankineItems.DIVING_CHESTPLATE.get()),Ingredient.fromItems(Items.CONDUIT),RankineItems.CONDUIT_DIVING_CHESTPLATE.get()).addCriterion("has_ingredient", hasItem(Items.CONDUIT)).build(consumer,"rankine:conduit_diving_chestplate_from_smithing");
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(RankineItems.DIVING_HELMET.get()),Ingredient.fromItems(Items.SPONGE),RankineItems.CONDUIT_DIVING_HELMET.get()).addCriterion("has_ingredient", hasItem(Items.CONDUIT)).build(consumer,"rankine:conduit_diving_helmet_from_smithing");
        

        
        //furnace recipes
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.FIRE_CLAY_BALL.get()), RankineItems.REFRACTORY_BRICK.get(), 0.2F, 200).addCriterion("has_ingredient", hasItem(RankineTags.Items.CLAY_BALLS)).build(consumer, "rankine:refractory_brick_from_fire_clay_ball_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.BAMBOO), RankineItems.DRIED_BAMBOO.get(), 0.1F, 25).addCriterion("has_ingredient", hasItem(Items.BAMBOO)).build(consumer, "rankine:dried_bamboo_from_bamboo_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.BONE), RankineItems.BONE_ASH.get(), 0.1F, 200).addCriterion("has_ingredient", hasItem(Items.BONE)).build(consumer, "rankine:bone_ash_from_bone_smelting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.PULP.get()), Items.PAPER, 0.1F, 100).addCriterion("has_ingredient", hasItem(RankineItems.PULP.get())).build(consumer, "rankine:paper_from_pulp_smelting");

        //blasting recipes
        for (Item MINERAL : RankineLists.BLASTING_MINERALS) {
            String baseName = MINERAL.getRegistryName().getPath();
            Item INGOT = RankineLists.BLASTING_INGOTS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            Item MINERAL_BLOCK = RankineLists.BLASTING_MINERAL_BLOCKS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            Item INGOT_BLOCK = RankineLists.BLASTING_BLOCKS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(MINERAL), INGOT, 0.5F, 100).addCriterion("has_"+baseName, hasItem(MINERAL)).build(consumer, "rankine:"+INGOT.getRegistryName().getPath()+"_from_"+baseName+"_blasting");
            //CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(MINERAL_BLOCK), INGOT_BLOCK, 5.0F, 800).addCriterion("has_"+baseName, hasItem(MINERAL)).build(consumer, "rankine:"+INGOT_BLOCK.getRegistryName().getPath()+"_from_"+baseName+"_block_blasting");
            //beehive oven
        }
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.APATITE.get()), RankineItems.PHOSPHORUS_NUGGET.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineItems.APATITE.get())).build(consumer, "rankine:phosphorus_nugget_from_apatite_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.BADDELEYITE.get()), RankineItems.ZIRCONIA.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineItems.BADDELEYITE.get())).build(consumer, "rankine:zirconia_from_baddeleyite_blasting");
        //CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.APATITE_BLOCK.get()), RankineItems.PHOSPHORUS.get(), 5.0F, 800).addCriterion("has_ingredient", hasItem(RankineItems.APATITE.get())).build(consumer, RankineItems.PHOSPHORUS.get().getRegistryName().getPath()+"_from_apatite_block_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.METEORIC_IRON.get()), Items.IRON_INGOT, 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineItems.METEORIC_IRON.get())).build(consumer, "rankine:iron_from_meteoric_iron_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.TRONA.get()), RankineItems.SODIUM_CARBONATE.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineItems.TRONA.get())).build(consumer, "rankine:sodium_carbonate_from_trona_blasting");




        for (Block MAT : RankineLists.FIBER_MAT) {
            Block BLOCK = RankineLists.FIBER_BLOCK.get(RankineLists.FIBER_MAT.indexOf(MAT));
            ShapedRecipeBuilder.shapedRecipe(MAT, 3).patternLine("##").key('#', BLOCK).setGroup("fiber_mat").addCriterion("has_block", InventoryChangeTrigger.Instance.forItems(RankineItems.FIBER_BLOCK.get())).build(consumer);
        }
        twoXtwo(consumer, RankineItems.ROPE.get(), RankineItems.PLANT_FIBER.get(), 1, "has_plant_fiber", RankineItems.PLANT_FIBER.get());
        threeXthree(consumer, RankineItems.FIBER_BLOCK.get(), RankineItems.PLANT_FIBER.get(), 1, "has_plant_fiber", RankineItems.PLANT_FIBER.get());
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.WHITE_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_WHITE).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ORANGE_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_ORANGE).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.YELLOW_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_YELLOW).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.RED_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_RED).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MAGENTA_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_MAGENTA).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PINK_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_PINK).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PURPLE_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_PURPLE).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLUE_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_BLUE).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CYAN_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_CYAN).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GREEN_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_GREEN).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIGHT_BLUE_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_LIGHT_BLUE).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BROWN_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_BROWN).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIME_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_LIME).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIGHT_GRAY_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_LIGHT_GRAY).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GRAY_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_GRAY).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLACK_FIBER_BLOCK.get()).addIngredient(RankineItems.FIBER_BLOCK.get()).addIngredient(Tags.Items.DYES_BLACK).addCriterion("has_fiber_block", hasItem(RankineItems.FIBER_BLOCK.get())).build(consumer);
        led(consumer, RankineItems.WHITE_LED.get(), Tags.Items.DYES_WHITE);
        led(consumer, RankineItems.ORANGE_LED.get(), Tags.Items.DYES_ORANGE);
        led(consumer, RankineItems.YELLOW_LED.get(), Tags.Items.DYES_YELLOW);
        led(consumer, RankineItems.RED_LED.get(), Tags.Items.DYES_RED);
        led(consumer, RankineItems.PINK_LED.get(), Tags.Items.DYES_PINK);
        led(consumer, RankineItems.PURPLE_LED.get(), Tags.Items.DYES_PURPLE);
        led(consumer, RankineItems.MAGENTA_LED.get(), Tags.Items.DYES_MAGENTA);
        led(consumer, RankineItems.BLUE_LED.get(), Tags.Items.DYES_BLUE);
        led(consumer, RankineItems.CYAN_LED.get(), Tags.Items.DYES_CYAN);
        led(consumer, RankineItems.LIGHT_BLUE_LED.get(), Tags.Items.DYES_LIGHT_BLUE);
        led(consumer, RankineItems.LIME_LED.get(), Tags.Items.DYES_LIME);
        led(consumer, RankineItems.GREEN_LED.get(), Tags.Items.DYES_GREEN);
        led(consumer, RankineItems.BROWN_LED.get(), Tags.Items.DYES_BROWN);
        led(consumer, RankineItems.LIGHT_GRAY_LED.get(), Tags.Items.DYES_LIGHT_GRAY);
        led(consumer, RankineItems.GRAY_LED.get(), Tags.Items.DYES_GRAY);
        led(consumer, RankineItems.BLACK_LED.get(), Tags.Items.DYES_BLACK);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MINERAL_WOOL.get()).addIngredient(RankineTags.Items.MINERAL_WOOL).addIngredient(RankineItems.BLEACH.get()).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.WHITE_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.WHITE_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ORANGE_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.ORANGE_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.YELLOW_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.YELLOW_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.RED_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.RED_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MAGENTA_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.MAGENTA_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PINK_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.PINK_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PURPLE_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.PURPLE_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLUE_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.BLUE_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CYAN_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.CYAN_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GREEN_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.GREEN_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIGHT_BLUE_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.LIGHT_BLUE_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BROWN_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.BROWN_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIME_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.LIME_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.LIGHT_GRAY_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.LIGHT_GRAY_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.GRAY_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.GRAY_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLACK_MINERAL_WOOL.get()).addIngredient(RankineItems.MINERAL_WOOL.get()).addIngredient(Items.BLACK_WOOL).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);


        twoXtwo(consumer, Items.PURPLE_DYE, RankineItems.ELDERBERRIES.get(), 1, "has_ingredient", RankineItems.ELDERBERRIES.get(), "purple_dye_from_elderberries");
        twoXtwo(consumer, Items.BLUE_DYE, RankineItems.BLUEBERRIES.get(), 1, "has_ingredient", RankineItems.BLUEBERRIES.get(), "blue_dye_from_blueberries");
        twoXtwo(consumer, Items.BLACK_DYE, RankineItems.BLACKBERRIES.get(), 1, "has_ingredient", RankineItems.BLACKBERRIES.get(), "black_dye_from_blackberries");
        twoXtwo(consumer, Items.MAGENTA_DYE, RankineItems.RASPBERRIES.get(), 1, "has_ingredient", RankineItems.RASPBERRIES.get(), "magenta_dye_from_raspberries");
        twoXtwo(consumer, Items.WHITE_DYE, RankineItems.SNOWBERRIES.get(), 1, "has_ingredient", RankineItems.SNOWBERRIES.get(), "white_dye_from_snowberries");
        twoXtwo(consumer, Items.RED_DYE, RankineItems.CRANBERRIES.get(), 1, "has_ingredient", RankineItems.CRANBERRIES.get(), "red_dye_from_cranberries");
        twoXtwo(consumer, Items.MAGENTA_DYE, RankineItems.POKEBERRIES.get(), 1, "has_ingredient", RankineItems.POKEBERRIES.get(), "magenta_dye_from_pokeberries");
        twoXtwo(consumer, Items.LIME_DYE, RankineItems.ALOE.get(), 1, "has_ingredient", RankineItems.ALOE.get(), "lime_dye_from_aloe");
        twoXtwo(consumer, Items.BROWN_DYE, RankineItems.BANANA_YUCCA.get(), 1, "has_ingredient", RankineItems.BANANA_YUCCA.get(), "brow_dye_from_yucca");
        twoXtwo(consumer, Items.ORANGE_DYE, RankineItems.PINEAPPLE.get(), 1, "has_ingredient", RankineItems.PINEAPPLE.get(), "orange_dye_from_pineapple");
        twoXtwo(consumer, Items.PINK_DYE, RankineItems.STRAWBERRIES.get(), 1, "has_ingredient", RankineItems.STRAWBERRIES.get(), "pink_dye_from_strawberries");
        twoXtwo(consumer, Items.GREEN_DYE, RankineItems.MALACHITE.get(), 16, "has_ingredient", RankineItems.MALACHITE.get(), "green_dye_from_malachite");
        twoXtwo(consumer, Items.WHITE_DYE, RankineItems.RUTILE.get(), 16, "has_ingredient", RankineItems.RUTILE.get(), "white_dye_from_ilmenite");
        twoXtwo(consumer, Items.YELLOW_DYE, RankineItems.CHROMITE.get(), 16, "has_ingredient", RankineItems.CHROMITE.get(), "yellow_dye_from_chromite");
        twoXtwo(consumer, Items.LIGHT_BLUE_DYE, RankineItems.AZURITE.get(), 16, "has_ingredient", RankineItems.AZURITE.get(), "light_blue_dye_from_azurite");
        //twoXtwo(consumer, Items.GREEN_DYE, RankineItems.MALACHITE.get(), 16, "has_ingredient", RankineItems.MALACHITE.get(), "_dye_from_");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(RankineItems.BONE_CHAR.get()).addCriterion("has_ingredient", hasItem(RankineItems.BONE_CHAR.get())).build(consumer, "rankine:black_dye_from_bone_char");
        ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(RankineItems.GOLDENROD.get()).addCriterion("has_ingredient", hasItem(RankineItems.GOLDENROD.get())).build(consumer, "rankine:yellow_dye_from_goldenrod");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(RankineItems.RED_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.RED_LILY.get())).build(consumer, "rankine:red_dye_from_red_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE).addIngredient(RankineItems.ORANGE_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.ORANGE_LILY.get())).build(consumer, "rankine:orange_dye_from_orange_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(RankineItems.WHITE_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.WHITE_LILY.get())).build(consumer, "rankine:white_dye_from_white_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(RankineItems.BLACK_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.BLACK_MORNING_GLORY.get())).build(consumer, "rankine:black_dye_from_black_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(RankineItems.BLUE_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.BLUE_MORNING_GLORY.get())).build(consumer, "rankine:blue_dye_from_blue_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(RankineItems.PURPLE_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.PURPLE_MORNING_GLORY.get())).build(consumer, "rankine:purple_dye_from_purple_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE).addIngredient(Items.WATER_BUCKET).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addCriterion("has_ingredient", hasItem(RankineItems.ASH.get())).build(consumer, "rankine:gray_dye_from_ash");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE,4).addIngredient(RankineTags.Items.NUGGETS_YTTRIUM).addIngredient(RankineTags.Items.NUGGETS_YTTRIUM).addIngredient(RankineTags.Items.NUGGETS_INDIUM).addIngredient(RankineTags.Items.NUGGETS_INDIUM).addIngredient(RankineTags.Items.NUGGETS_MANGANESE).addIngredient(RankineTags.Items.NUGGETS_MANGANESE).addCriterion("has_ingredient", hasItem(RankineTags.Items.NUGGETS_MANGANESE)).build(consumer, "rankine:blue_dye_from_metals");


        CustomRecipeBuilder.customRecipe(JamRecipe.SERIALIZER).build(consumer, "fruit_jam");

        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CLAY_BRICKS.get()).key('#', Items.CLAY_BALL).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_clay", hasItem(Items.CLAY_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FIRE_CLAY_BRICKS.get()).key('#', RankineItems.FIRE_CLAY_BALL.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_clay", hasItem(RankineItems.FIRE_CLAY_BALL.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.KAOLIN_BRICKS.get()).key('#', RankineItems.KAOLINITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_clay", hasItem(RankineItems.KAOLINITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.REFRACTORY_BRICKS.get()).key('#', RankineItems.REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_refractory_brick", hasItem(RankineItems.REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.HIGH_REFRACTORY_BRICKS.get()).key('#', RankineItems.HIGH_REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_high_refractory_brick", hasItem(RankineItems.HIGH_REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get()).key('#', RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_refractory_brick", hasItem(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.METEORITE_BRICKS.get()).key('#', RankineBlocks.METEORITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_meteorite", hasItem(RankineBlocks.METEORITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get()).key('#', RankineBlocks.ENSTATITE_CHONDRITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_enstatite_chondrite", hasItem(RankineBlocks.ENSTATITE_CHONDRITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FROZEN_METEORITE_BRICKS.get()).key('#', RankineBlocks.FROZEN_METEORITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_meteorite", hasItem(RankineBlocks.FROZEN_METEORITE.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.METEORITE.get()), RankineBlocks.METEORITE_BRICKS.get(), 1).addCriterion("has_meteorite", hasItem(RankineBlocks.METEORITE.get())).build(consumer, "rankine:meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.FROZEN_METEORITE.get()), RankineBlocks.FROZEN_METEORITE_BRICKS.get(), 1).addCriterion("has_frozen_meteorite", hasItem(RankineBlocks.FROZEN_METEORITE.get())).build(consumer, "rankine:frozen_meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.ENSTATITE_CHONDRITE.get()), RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get(), 1).addCriterion("has_meteorite", hasItem(RankineBlocks.ENSTATITE_CHONDRITE.get())).build(consumer, "rankine:enstatite_bricks_from_stonecutting");




        for (Block HOLLOW : RankineLists.HOLLOW_LOGS) {
            String PATH = HOLLOW.getRegistryName().getPath();
            String nameSpace;
            if (Arrays.asList("hollow_oak_log","hollow_birch_log","hollow_spruce_log","hollow_acacia_log","hollow_jungle_log","hollow_dark_oak_log","hollow_crimson_stem","hollow_warped_stem").contains(PATH)) {
                nameSpace = "minecraft";
            } else {
                nameSpace = "rankine";
            }
            Block LOG = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(nameSpace+":"+PATH.replace("hollow_","")));
            if (LOG != null) {
                ShapedRecipeBuilder.shapedRecipe(HOLLOW.asItem(), 16).patternLine("###").patternLine("# #").patternLine("###").key('#', LOG).setGroup("rankine:hollow_logs").addCriterion("has_ingredient", hasItem(LOG)).build(consumer);
            }
        }
        for (Block BLK : RankineLists.LEAF_LITTERS) {
            String PATH = BLK.getRegistryName().getPath();
            String nameSpace;
            if (Arrays.asList("oak_leaf_litter","birch_leaf_litter","spruce_leaf_litter","acacia_leaf_litter","jungle_leaf_litter","dark_oak_leaf_litter","crimson_leaf_litter","warped_leaf_litter").contains(PATH)) {
                nameSpace = "minecraft";
            } else {
                nameSpace = "rankine";
            }
            Block LEAF = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(nameSpace+":"+PATH.replace("leaf_litter","leaves")));
            if (LEAF != null) {
                ShapedRecipeBuilder.shapedRecipe(BLK.asItem(), 4).patternLine("##").key('#', LEAF).setGroup("rankine:leaf_litters").addCriterion("has_ingredient", hasItem(LEAF)).build(consumer);
            }
        }
        for (Block COARSE_SOIL : RankineLists.COARSE_SOIL_BLOCKS) {
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.COARSE_SOIL_BLOCKS.indexOf(COARSE_SOIL));
            ShapedRecipeBuilder.shapedRecipe(COARSE_SOIL.asItem(), 4).patternLine("#G").patternLine("G#").key('#', SOIL).key('G', Tags.Items.GRAVEL).setGroup("rankine:coarse_soil").addCriterion("has_ingredient", hasItem(SOIL)).build(consumer);

        }

        for (Item MINERAL_ITEM : RankineLists.MINERAL_ITEMS) {
            Item MINERAL_BLOCK = RankineLists.MINERAL_BLOCKS.get(RankineLists.MINERAL_ITEMS.indexOf(MINERAL_ITEM)).asItem();
            threeXthree(consumer, MINERAL_BLOCK, MINERAL_ITEM, 1, "has_ingredient", MINERAL_ITEM);
            OneToX(consumer, MINERAL_ITEM, MINERAL_BLOCK, 9, "has_ingredient", MINERAL_ITEM, MINERAL_ITEM.getRegistryName().getPath()+"_from_block");
        }

        //ELEMENTS
        for (Item NUGGET : RankineLists.ELEMENT_NUGGETS) {
            Block BLOCK = RankineLists.ELEMENT_BLOCKS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ELEMENT_INGOTS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            threeXthree(consumer, BLOCK.asItem(), INGOT, 1, "has_ingredient", INGOT);
            threeXthree(consumer, INGOT, NUGGET, 1, "has_ingredient", NUGGET, INGOT.getRegistryName().getPath()+"_from_"+NUGGET.getRegistryName().getPath());
            OneToX(consumer, INGOT, BLOCK.asItem(), 9, "has_ingredient", BLOCK.asItem(), INGOT.getRegistryName().getPath()+"_from_"+BLOCK.getRegistryName().getPath());
            OneToX(consumer, NUGGET, INGOT, 9, "has_ingredient", INGOT);
        }
        OneToX(consumer, RankineItems.NETHERITE_NUGGET.get(), Items.NETHERITE_INGOT, 9, "has_ingredient", Items.NETHERITE_INGOT);
        threeXthree(consumer, Items.NETHERITE_INGOT, RankineItems.NETHERITE_NUGGET.get(), 1, "has_ingredient", Items.NETHERITE_INGOT, "netherite_ingot_from_netherite_nugget");
        //Misc Blocks
        OneToX(consumer, RankineItems.VULCANIZED_RUBBER.get(), RankineItems.VULCANIZED_RUBBER_BLOCK.get(), 9, "has_ingredient", RankineItems.VULCANIZED_RUBBER.get(), "vulcanized_rubber_from_block");
        threeXthree(consumer, RankineItems.VULCANIZED_RUBBER_BLOCK.get(), RankineItems.VULCANIZED_RUBBER.get(), 1, "has_ingredient", RankineItems.VULCANIZED_RUBBER.get());
        OneToX(consumer, RankineItems.BONE_CHAR.get(), RankineItems.BONE_CHAR_BLOCK.get(), 9, "has_ingredient", RankineItems.BONE_CHAR_BLOCK.get(), "bone_char_from_block");
        threeXthree(consumer, RankineItems.BONE_CHAR_BLOCK.get(), RankineItems.BONE_CHAR.get(), 1, "has_ingredient", RankineItems.BONE_CHAR_BLOCK.get());

        //WOODS
        for (Block LOG : RankineLists.LOGS) {
            Block WOOD = RankineLists.WOODS.get(RankineLists.LOGS.indexOf(LOG));
            Block STRIPPED_LOG = RankineLists.STRIPPED_LOGS.get(RankineLists.LOGS.indexOf(LOG));
            Block STRIPPED_WOOD = RankineLists.STRIPPED_WOODS.get(RankineLists.LOGS.indexOf(LOG));
            twoXtwo(consumer,WOOD.asItem(),LOG.asItem(),3,"has_log",LOG.asItem());
            twoXtwo(consumer,STRIPPED_WOOD.asItem(),STRIPPED_LOG.asItem(),3,"has_log",LOG.asItem());
        }

        OneToXTag(consumer,RankineItems.CEDAR_PLANKS.get(),"planks",RankineTags.Items.CEDAR_LOGS,4,"has_log",RankineItems.CEDAR_LOG.get());
        OneToXTag(consumer,RankineItems.BALSAM_FIR_PLANKS.get(),"planks",RankineTags.Items.BALSAM_FIR_LOGS,4,"has_log",RankineItems.BALSAM_FIR_LOG.get());
        OneToXTag(consumer,RankineItems.COCONUT_PALM_PLANKS.get(),"planks",RankineTags.Items.COCONUT_PALM_LOGS,4,"has_log",RankineItems.COCONUT_PALM_LOG.get());
        OneToXTag(consumer,RankineItems.JUNIPER_PLANKS.get(),"planks",RankineTags.Items.JUNIPER_LOGS,4,"has_log",RankineItems.JUNIPER_LOG.get());
        OneToXTag(consumer,RankineItems.PINYON_PINE_PLANKS.get(),"planks",RankineTags.Items.PINYON_PINE_LOGS,4,"has_log",RankineItems.PINYON_PINE_LOG.get());
        OneToXTag(consumer,RankineItems.MAGNOLIA_PLANKS.get(),"planks",RankineTags.Items.MAGNOLIA_LOGS,4,"has_log",RankineItems.MAGNOLIA_LOG.get());
        OneToXTag(consumer,RankineItems.EASTERN_HEMLOCK_PLANKS.get(),"planks",RankineTags.Items.EASTERN_HEMLOCK_LOGS,4,"has_log",RankineItems.EASTERN_HEMLOCK_LOG.get());
        OneToXTag(consumer,RankineItems.WESTERN_HEMLOCK_PLANKS.get(),"planks",RankineTags.Items.WESTERN_HEMLOCK_LOGS,4,"has_log",RankineItems.WESTERN_HEMLOCK_LOG.get());
        OneToXTag(consumer,RankineItems.YELLOW_BIRCH_PLANKS.get(),"planks",RankineTags.Items.YELLOW_BIRCH_LOGS,4,"has_log",RankineItems.YELLOW_BIRCH_LOG.get());
        OneToXTag(consumer,RankineItems.BLACK_BIRCH_PLANKS.get(),"planks",RankineTags.Items.BLACK_BIRCH_LOGS,4,"has_log",RankineItems.BLACK_BIRCH_LOG.get());
        OneToXTag(consumer,RankineItems.RED_BIRCH_PLANKS.get(),"planks",RankineTags.Items.RED_BIRCH_LOGS,4,"has_log",RankineItems.RED_BIRCH_LOG.get());
        OneToXTag(consumer,RankineItems.SHARINGA_PLANKS.get(),"planks",RankineTags.Items.SHARINGA_LOGS,4,"has_log",RankineItems.SHARINGA_LOG.get());
        OneToXTag(consumer,RankineItems.BLACK_WALNUT_PLANKS.get(),"planks",RankineTags.Items.BLACK_WALNUT_LOGS,4,"has_log",RankineItems.BLACK_WALNUT_LOG.get());
        OneToXTag(consumer,RankineItems.CINNAMON_PLANKS.get(),"planks",RankineTags.Items.CINNAMON_LOGS,4,"has_log",RankineItems.CINNAMON_LOG.get());
        OneToXTag(consumer,RankineItems.CORK_OAK_PLANKS.get(),"planks",RankineTags.Items.CORK_OAK_LOGS,4,"has_log",RankineItems.CORK_OAK_LOG.get());
        OneToXTag(consumer,RankineItems.ERYTHRINA_PLANKS.get(),"planks",RankineTags.Items.ERYTHRINA_LOGS,4,"has_log",RankineItems.ERYTHRINA_LOG.get());
        OneToXTag(consumer,RankineItems.CHARRED_PLANKS.get(),"planks",RankineTags.Items.CHARRED_LOGS,4,"has_log",RankineItems.CHARRED_LOG.get());
        OneToXTag(consumer,RankineItems.MAPLE_PLANKS.get(),"planks",RankineTags.Items.MAPLE_LOGS,4,"has_log",RankineItems.MAPLE_LOG.get());
        OneToXTag(consumer,RankineItems.HONEY_LOCUST_PLANKS.get(),"planks",RankineTags.Items.HONEY_LOCUST_LOGS,4,"has_log",RankineItems.HONEY_LOCUST_LOG.get());
        OneToXTag(consumer,RankineItems.WEEPING_WILLOW_PLANKS.get(),"planks",RankineTags.Items.WEEPING_WILLOW_LOGS,4,"has_log",RankineItems.WEEPING_WILLOW_LOG.get());
        OneToXTag(consumer,RankineItems.PETRIFIED_CHORUS_PLANKS.get(),"planks",RankineTags.Items.PETRIFIED_CHORUS_LOGS,4,"has_log",RankineItems.PETRIFIED_CHORUS_LOG.get());
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BAMBOO_CULMS.get(), 1).patternLine("##").patternLine("##").key('#', Items.BAMBOO).addCriterion("has_ingredient", hasItem(Items.BAMBOO)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BAMBOO_PLANKS.get(), 1).patternLine("##").patternLine("##").key('#', RankineItems.DRIED_BAMBOO.get()).addCriterion("has_ingredient", hasItem(RankineItems.DRIED_BAMBOO.get())).build(consumer);

        for (Block PLANK : RankineLists.PLANKS) {
            String baseName = PLANK.getRegistryName().getPath();
            Block WOODEN_SLAB = RankineLists.WOODEN_SLABS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_VERTICAL_SLAB = RankineLists.WOODEN_VERTICAL_SLABS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_STAIRS = RankineLists.WOODEN_STAIRS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_PRESSURE_PLATE = RankineLists.WOODEN_PRESSURE_PLATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_BUTTON = RankineLists.WOODEN_BUTTONS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_DOOR = RankineLists.WOODEN_DOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_TRAPDOOR = RankineLists.WOODEN_TRAPDOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCE = RankineLists.WOODEN_FENCES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCE_GATE = RankineLists.WOODEN_FENCE_GATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Item WOODEN_BOAT = RankineLists.WOODEN_BOATS.get(RankineLists.PLANKS.indexOf(PLANK));
            slab(consumer, WOODEN_SLAB.asItem(), PLANK.asItem(), "wooden_slab");
            stairs(consumer, WOODEN_STAIRS.asItem(), PLANK.asItem(), "wooden_stairs");
            pressurePlate(consumer, WOODEN_PRESSURE_PLATE.asItem(), PLANK.asItem(), "wooden_pressure_plate");
            door(consumer, WOODEN_DOOR.asItem(), PLANK.asItem(), "wooden_door",  "has_plank", PLANK.asItem());
            trapdoor(consumer, WOODEN_TRAPDOOR.asItem(), PLANK.asItem(), "wooden_trapdoor",  "has_plank", PLANK.asItem());
            fence(consumer, WOODEN_FENCE.asItem(), PLANK.asItem(), "wooden_fence",  "has_plank", PLANK.asItem());
            fenceGate(consumer, WOODEN_FENCE_GATE.asItem(), PLANK.asItem(), "wooden_fence_gate",  "has_plank", PLANK.asItem());
            boat(consumer, WOODEN_BOAT, PLANK.asItem(), "boat",  "has_plank", PLANK.asItem());
            ShapelessRecipeBuilder.shapelessRecipe(WOODEN_BUTTON).addIngredient(PLANK).setGroup("wooden_button").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer);
            
            ShapelessRecipeBuilder.shapelessRecipe(WOODEN_SLAB).addIngredient(WOODEN_VERTICAL_SLAB).setGroup("planks_slab_from_vslab").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer,"rankine:"+baseName+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(WOODEN_VERTICAL_SLAB).addIngredient(WOODEN_SLAB).setGroup("planks_vslab_from_slab").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer,"rankine:"+baseName+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(PLANK).addIngredient(WOODEN_VERTICAL_SLAB).addIngredient(WOODEN_VERTICAL_SLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer,"rankine:"+baseName+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(PLANK).addIngredient(WOODEN_SLAB).addIngredient(WOODEN_SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(PLANK,6).addIngredient(WOODEN_STAIRS).addIngredient(WOODEN_STAIRS).addIngredient(WOODEN_STAIRS).addIngredient(WOODEN_STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(PLANK)).build(consumer,"rankine:"+baseName+"_from_stairs");
        }
        
        for (Block STONE : RankineLists.STONES) {
            String baseStone = STONE.getRegistryName().getPath();
            Block COBBLE = RankineLists.STONE_COBBLES.get(RankineLists.STONES.indexOf(STONE));
            Block COLUMN = RankineLists.STONE_COLUMNS.get(RankineLists.STONES.indexOf(STONE));
            Block POLISHED_STONE = RankineLists.POLISHED_STONES.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS = RankineLists.STONE_BRICKS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_SLAB = RankineLists.STONE_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block POLISHED_STONE_SLAB = RankineLists.POLISHED_STONE_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS_SLAB = RankineLists.STONE_BRICKS_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_VERTICAL_SLAB = RankineLists.STONE_VERTICAL_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block POLISHED_STONE_VERTICAL_SLAB = RankineLists.POLISHED_STONE_VERTICAL_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS_VERTICAL_SLAB = RankineLists.STONE_BRICKS_VERTICAL_SLABS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_STAIRS = RankineLists.STONE_STAIRS.get(RankineLists.STONES.indexOf(STONE));
            Block POLISHED_STONE_STAIRS = RankineLists.POLISHED_STONE_STAIRS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS_STAIRS = RankineLists.STONE_BRICKS_STAIRS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_WALL = RankineLists.STONE_WALLS.get(RankineLists.STONES.indexOf(STONE));
            Block POLISHED_STONE_WALL = RankineLists.POLISHED_STONE_WALLS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS_WALL = RankineLists.STONE_BRICKS_WALLS.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_PRESSURE_PLATE = RankineLists.STONE_PRESSURE_PLATES.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BRICKS_PRESSURE_PLATE = RankineLists.STONE_BRICKS_PRESSURE_PLATES.get(RankineLists.STONES.indexOf(STONE));
            Block STONE_BUTTON = RankineLists.STONE_BUTTONS.get(RankineLists.STONES.indexOf(STONE));

            ShapedRecipeBuilder.shapedRecipe(COLUMN, 8)
                    .patternLine("#")
                    .patternLine("#")
                    .key('#', STONE)
                    .setGroup("stone_column")
                    .addCriterion("has_ingredient", InventoryChangeTrigger.Instance.forItems(STONE))
                    .build(consumer);
            ShapelessRecipeBuilder.shapelessRecipe(STONE).addIngredient(COLUMN).addIngredient(COLUMN).addIngredient(COLUMN).addIngredient(COLUMN).setGroup("stone_from_stone_column").addCriterion("has_ingredient", hasItem(COLUMN)).build(consumer,"rankine:"+STONE.getRegistryName().getPath()+"_from_column");
            ShapelessRecipeBuilder.shapelessRecipe(STONE).addIngredient(COBBLE).addIngredient(COBBLE).addIngredient(COBBLE).addIngredient(COBBLE).setGroup("stone_from_cobble").addCriterion("has_ingredient", hasItem(COBBLE)).build(consumer,"rankine:"+STONE.getRegistryName().getPath()+"_from_cobble");


            ShapedRecipeBuilder.shapedRecipe(POLISHED_STONE, 4)
                    .patternLine("##")
                    .patternLine("##")
                    .key('#', STONE)
                    .setGroup("polished_stone")
                    .addCriterion("has_ingredient", InventoryChangeTrigger.Instance.forItems(STONE))
                    .build(consumer);

            ShapedRecipeBuilder.shapedRecipe(STONE_BRICKS, 2)
                    .patternLine("#M")
                    .patternLine("M#")
                    .key('#', STONE)
                    .key('M', RankineItems.MORTAR.get())
                    .setGroup("stone_bricks")
                    .addCriterion("has_mortar", InventoryChangeTrigger.Instance.forItems(RankineItems.MORTAR.get()))
                    .build(consumer);

            slab(consumer, STONE_SLAB.asItem(), STONE.asItem(), "stone_slab");
            slab(consumer, POLISHED_STONE_SLAB.asItem(), POLISHED_STONE.asItem(), "stone_slab");
            slab(consumer, STONE_BRICKS_SLAB.asItem(), STONE_BRICKS.asItem(), "stone_slab");
            stairs(consumer, STONE_STAIRS.asItem(), STONE.asItem(), "stone_stairs");
            stairs(consumer, POLISHED_STONE_STAIRS.asItem(), POLISHED_STONE.asItem(), "stone_stairs");
            stairs(consumer, STONE_BRICKS_STAIRS.asItem(), STONE_BRICKS.asItem(), "stone_stairs");
            wall(consumer, STONE_WALL.asItem(), STONE.asItem(), "stone_wall");
            wall(consumer, POLISHED_STONE_WALL.asItem(), POLISHED_STONE.asItem());
            wall(consumer, STONE_BRICKS_WALL.asItem(), STONE_BRICKS.asItem());
            pressurePlate(consumer, STONE_PRESSURE_PLATE.asItem(), STONE.asItem(), "stone_pressure_plate");
            pressurePlate(consumer, STONE_BRICKS_PRESSURE_PLATE.asItem(), STONE_BRICKS.asItem(), "stone_pressure_plate");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_BUTTON).addIngredient(STONE).setGroup("stone_button").addCriterion("has_ingredient", hasItem(STONE)).build(consumer);

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), POLISHED_STONE, 1).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:polished_"+baseStone+"_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_BRICKS, 1).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_bricks_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_SLAB, 2).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_slab_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_VERTICAL_SLAB, 2).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_vertical_slab_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_STAIRS).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_stairs_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_WALL).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_wall_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_PRESSURE_PLATE).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_pressure_plate_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_BUTTON).addCriterion("has_ingredient", hasItem(STONE)).build(consumer, "rankine:"+baseStone+"_button_from_"+baseStone+"_stonecutting");

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_SLAB, 2).addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer, "rankine:"+baseStone+"_brick_slab_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_VERTICAL_SLAB, 2).addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer, "rankine:"+baseStone+"_brick_vertical_slab_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_STAIRS).addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer, "rankine:"+baseStone+"_brick_stairs_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_WALL).addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer, "rankine:"+baseStone+"_brick_wall_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_PRESSURE_PLATE).addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer, "rankine:"+baseStone+"_brick_button_from_"+baseStone+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_SLAB, 2).addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer, "rankine:polished_"+baseStone+"_slab_from_polished_"+baseStone+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_VERTICAL_SLAB, 2).addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer, "rankine:polished_"+baseStone+"_vertical_slab_from_polished_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_STAIRS).addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer, "rankine:polished_"+baseStone+"_stairs_from_polished_"+baseStone+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_WALL).addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer, "rankine:polished_"+baseStone+"_wall_from_polished_"+baseStone+"__stonecutting");

            ShapelessRecipeBuilder.shapelessRecipe(STONE_SLAB).addIngredient(STONE_VERTICAL_SLAB).setGroup("stone_slab_from_vslab").addCriterion("has_ingredient", hasItem(STONE)).build(consumer,"rankine:"+baseStone+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_VERTICAL_SLAB).addIngredient(STONE_SLAB).setGroup("stone_vslab_from_slab").addCriterion("has_ingredient", hasItem(STONE)).build(consumer,"rankine:"+baseStone+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE).addIngredient(STONE_VERTICAL_SLAB).addIngredient(STONE_VERTICAL_SLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(STONE)).build(consumer,"rankine:"+baseStone+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE).addIngredient(STONE_SLAB).addIngredient(STONE_SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(STONE)).build(consumer,"rankine:"+baseStone+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE,6).addIngredient(STONE_STAIRS).addIngredient(STONE_STAIRS).addIngredient(STONE_STAIRS).addIngredient(STONE_STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(STONE)).build(consumer,"rankine:"+baseStone+"_from_stairs");

            ShapelessRecipeBuilder.shapelessRecipe(STONE_BRICKS_SLAB).addIngredient(STONE_BRICKS_VERTICAL_SLAB).setGroup("stone_slab_from_vslab").addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_BRICKS_VERTICAL_SLAB).addIngredient(STONE_BRICKS_SLAB).setGroup("stone_vslab_from_slab").addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_BRICKS).addIngredient(STONE_BRICKS_VERTICAL_SLAB).addIngredient(STONE_BRICKS_VERTICAL_SLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_BRICKS).addIngredient(STONE_BRICKS_SLAB).addIngredient(STONE_BRICKS_SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(STONE_BRICKS,6).addIngredient(STONE_BRICKS_STAIRS).addIngredient(STONE_BRICKS_STAIRS).addIngredient(STONE_BRICKS_STAIRS).addIngredient(STONE_BRICKS_STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(STONE_BRICKS)).build(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_from_stairs");

            ShapelessRecipeBuilder.shapelessRecipe(POLISHED_STONE_SLAB).addIngredient(POLISHED_STONE_VERTICAL_SLAB).setGroup("stone_slab_from_vslab").addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_slab_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(POLISHED_STONE_VERTICAL_SLAB).addIngredient(POLISHED_STONE_SLAB).setGroup("stone_vslab_from_slab").addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_vslab_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(POLISHED_STONE).addIngredient(POLISHED_STONE_VERTICAL_SLAB).addIngredient(POLISHED_STONE_VERTICAL_SLAB).setGroup("block_from_slab").addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_from_vslab");
            ShapelessRecipeBuilder.shapelessRecipe(POLISHED_STONE).addIngredient(POLISHED_STONE_SLAB).addIngredient(POLISHED_STONE_SLAB).setGroup("block_from_vslab").addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_from_slab");
            ShapelessRecipeBuilder.shapelessRecipe(POLISHED_STONE,6).addIngredient(POLISHED_STONE_STAIRS).addIngredient(POLISHED_STONE_STAIRS).addIngredient(POLISHED_STONE_STAIRS).addIngredient(POLISHED_STONE_STAIRS).setGroup("block_from_stairs").addCriterion("has_ingredient", hasItem(POLISHED_STONE)).build(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_from_stairs");

        }

        //NATIVE ORES
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).build(consumer, "rankine:sulfur_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).build(consumer, "rankine:sulfur_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).build(consumer, "rankine:arsenic_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).build(consumer, "rankine:arsenic_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).build(consumer, "rankine:bismuth_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).build(consumer, "rankine:bismuth_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).build(consumer, "rankine:lead_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).build(consumer, "rankine:lead_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).build(consumer, "rankine:gold_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).build(consumer, "rankine:gold_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).build(consumer, "rankine:silver_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).build(consumer, "rankine:silver_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).build(consumer, "rankine:tin_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).build(consumer, "rankine:tin_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).build(consumer, "rankine:indium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).build(consumer, "rankine:indium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).build(consumer, "rankine:gallium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).build(consumer, "rankine:gallium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).build(consumer, "rankine:selenium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).build(consumer, "rankine:selenium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).build(consumer, "rankine:tellurium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).build(consumer, "rankine:tellurium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.PORPHYRY_COPPER.get()), RankineItems.COPPER_INGOT.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.PORPHYRY_COPPER.get().asItem())).build(consumer, "rankine:copper_ingot_from_porphyry_copper_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.PORPHYRY_COPPER.get()), RankineItems.COPPER_INGOT.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.PORPHYRY_COPPER.get().asItem())).build(consumer, "rankine:copper_ingot_from_porphyry_copper_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.STIBNITE_ORE.get()), RankineItems.ANTIMONY.get(), 0.5F, 200).addCriterion("has_ingredient", hasItem(RankineBlocks.STIBNITE_ORE.get().asItem())).build(consumer, "rankine:antimony_ingot_from_stibnite_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.STIBNITE_ORE.get()), RankineItems.ANTIMONY.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineBlocks.STIBNITE_ORE.get().asItem())).build(consumer, "rankine:antimony_ingot_from_stibnite_ore_blasting");

        //ALLOY STUFFS
        for (Item NUGGET : RankineLists.ALLOY_NUGGETS) {
            Block BLOCK = RankineLists.ALLOY_BLOCKS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));

            if (!INGOT.equals(RankineItems.SOLDER.get())) {
                threeXthreeAlloy(consumer, BLOCK.asItem(), INGOT, 1, "has_ingredient", INGOT);
                threeXthreeAlloy(consumer, INGOT, NUGGET, 1, "has_ingredient", NUGGET, INGOT.getRegistryName().getPath()+"_from_"+NUGGET.getRegistryName().getPath());
                OneToXAlloy(consumer, INGOT, BLOCK.asItem(), 9, "has_ingredient", BLOCK.asItem(), INGOT.getRegistryName().getPath()+"_from_"+BLOCK.getRegistryName().getPath());
                OneToXAlloy(consumer, NUGGET, INGOT, 9, "has_ingredient", INGOT);
            }
        }
        for (Block ALLOY_PEDESTAL : RankineLists.ALLOY_PEDESTALS) {
            Item ALLOY = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_PEDESTALS.indexOf(ALLOY_PEDESTAL));
            if (!ALLOY.equals(RankineItems.SOLDER.get())) {
                pedestal(consumer, ALLOY_PEDESTAL.asItem(), "pedestal", ALLOY, "has_ingredient", ALLOY);
            }
        }

        door(consumer, RankineItems.BRASS_DOOR.get(), RankineItems.BRASS_INGOT.get(), "metal_door", "has_ingredient", RankineItems.BRASS_INGOT.get());
        door(consumer, RankineItems.BRONZE_DOOR.get(), RankineItems.BRONZE_INGOT.get(), "metal_door", "has_ingredient", RankineItems.BRONZE_INGOT.get());
        door(consumer, RankineItems.CUPRONICKEL_DOOR.get(), RankineItems.CUPRONICKEL_INGOT.get(), "metal_door", "has_ingredient", RankineItems.CUPRONICKEL_INGOT.get());
        door(consumer, RankineItems.STEEL_DOOR.get(), RankineItems.STEEL_INGOT.get(), "metal_door", "has_ingredient", RankineItems.STEEL_INGOT.get());
        trapdoor(consumer, RankineItems.BRASS_TRAPDOOR.get(), RankineItems.BRASS_INGOT.get(), "metal_trapdoor", "has_ingredient", RankineItems.BRASS_INGOT.get());
        trapdoor(consumer, RankineItems.BRONZE_TRAPDOOR.get(), RankineItems.BRONZE_INGOT.get(), "metal_trapdoor", "has_ingredient", RankineItems.BRONZE_INGOT.get());
        trapdoor(consumer, RankineItems.CUPRONICKEL_TRAPDOOR.get(), RankineItems.CUPRONICKEL_INGOT.get(), "metal_trapdoor", "has_ingredient", RankineItems.CUPRONICKEL_INGOT.get());
        trapdoor(consumer, RankineItems.STEEL_TRAPDOOR.get(), RankineItems.STEEL_INGOT.get(), "metal_trapdoor", "has_ingredient", RankineItems.STEEL_INGOT.get());

        //SHEETMETALS
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.COPPER_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_COPPER).key('#', RankineTags.Items.NUGGETS_COPPER).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_COPPER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TIN_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_TIN).key('#', RankineTags.Items.NUGGETS_TIN).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_TIN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ALUMINUM_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_ALUMINUM).key('#', RankineTags.Items.NUGGETS_ALUMINUM).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_ALUMINUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.LEAD_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_LEAD).key('#', RankineTags.Items.NUGGETS_LEAD).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_LEAD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BISMUTH_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_BISMUTH).key('#', RankineTags.Items.NUGGETS_BISMUTH).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BISMUTH)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.SILVER_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_SILVER).key('#', RankineTags.Items.NUGGETS_SILVER).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_SILVER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.GOLD_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', Tags.Items.INGOTS_GOLD).key('#', Tags.Items.NUGGETS_GOLD).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(Tags.Items.INGOTS_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.IRON_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', Tags.Items.INGOTS_IRON).key('#', Tags.Items.NUGGETS_IRON).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(Tags.Items.INGOTS_IRON)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TITANIUM_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_TITANIUM).key('#', RankineTags.Items.NUGGETS_TITANIUM).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_TITANIUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TUNGSTEN_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_TUNGSTEN).key('#', RankineTags.Items.NUGGETS_TUNGSTEN).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_TUNGSTEN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.PLATINUM_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_PLATINUM).key('#', RankineTags.Items.NUGGETS_PLATINUM).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_PLATINUM)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.NICKEL_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_NICKEL).key('#', RankineTags.Items.NUGGETS_NICKEL).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_NICKEL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BRASS_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_BRASS).key('#', RankineTags.Items.NUGGETS_BRASS).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRASS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BRONZE_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_BRONZE).key('#', RankineTags.Items.NUGGETS_BRONZE).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_BRONZE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.INVAR_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_INVAR).key('#', RankineTags.Items.NUGGETS_INVAR).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_INVAR)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CUPRONICKEL_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_CUPRONICKEL).key('#', RankineTags.Items.NUGGETS_CUPRONICKEL).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_CUPRONICKEL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.STEEL_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_STEEL).key('#', RankineTags.Items.NUGGETS_STEEL).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STEEL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get(), 8).patternLine("#I#").patternLine("#I#").patternLine("#I#").key('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).key('#', RankineTags.Items.NUGGETS_STAINLESS_STEEL).setGroup("sheetmetal").addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_STAINLESS_STEEL)).build(consumer);
        
        
        hLine(consumer,RankineItems.TAP_LINE.get(),3,RankineItems.VULCANIZED_RUBBER.get(),"has_ingredient",RankineItems.VULCANIZED_RUBBER.get());
        hLine(consumer,RankineItems.METAL_PIPE.get(),8,RankineItems.CUPRONICKEL_INGOT.get(),"has_ingredient",RankineItems.CUPRONICKEL_INGOT.get());
        
        
        ladder(consumer,RankineItems.BRASS_LADDER.get(),8,RankineTags.Items.INGOTS_BRASS);
        ladder(consumer,RankineItems.CAST_IRON_LADDER.get(),8,RankineTags.Items.INGOTS_CAST_IRON);
        ladder(consumer,RankineItems.CUPRONICKEL_LADDER.get(),8,RankineTags.Items.INGOTS_CUPRONICKEL);
        ladder(consumer,RankineItems.DURALUMIN_LADDER.get(),8,RankineTags.Items.INGOTS_DURALUMIN);
        ladder(consumer,RankineItems.INVAR_LADDER.get(),8,RankineTags.Items.INGOTS_INVAR);

        
        //Campfire
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.DRIED_BAMBOO.get()), Items.BAMBOO, 0.35F, 40, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(Items.BAMBOO)).build(consumer, "rankine:dried_bamboo_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.BONE_ASH.get()), Items.BONE, 0.35F, 200, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(Items.BONE)).build(consumer, "rankine:bone_ash_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.FIRE_CLAY_BALL.get()), RankineItems.REFRACTORY_BRICK.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.FIRE_CLAY_BALL.get())).build(consumer, "rankine:refractory_brick_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.PANCAKE_BATTER.get()), RankineItems.PANCAKE.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.PANCAKE_BATTER.get())).build(consumer, "rankine:pancake_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.CORN_EAR.get()), RankineItems.POPCORN.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.CORN_EAR.get())).build(consumer, "rankine:popcorn_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.ASPARAGUS.get()), RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.ROASTED_ASPARAGUS.get())).build(consumer, "rankine:roasted_asparagus_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.BLACK_WALNUT.get()), RankineItems.ROASTED_WALNUT.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.BLACK_WALNUT.get())).build(consumer, "rankine:roasted_walnut_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.COCONUT.get()), RankineItems.TOASTED_COCONUT.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.COCONUT.get())).build(consumer, "rankine:toasted_coconut_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.BREAD), RankineItems.TOAST.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.BREAD)).build(consumer, "rankine:toast_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.GF_BREAD.get()), RankineItems.TOAST.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.BREAD)).build(consumer, "rankine:toast_gf_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.DOUGH.get()), Items.BREAD, 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.FLOUR)).build(consumer, "rankine:bread_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.DOUGH_GF.get()), RankineItems.GF_BREAD.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.FLOUR_GF)).build(consumer, "rankine:gf_bread_campfire_cooking");
        //Smoking
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.PANCAKE_BATTER.get()), RankineItems.PANCAKE.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineItems.PANCAKE_BATTER.get())).build(consumer, "rankine:pancake_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.CORN_EAR.get()), RankineItems.POPCORN.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineItems.CORN_EAR.get())).build(consumer, "rankine:popcorn_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.ASPARAGUS.get()), RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineItems.ROASTED_ASPARAGUS.get())).build(consumer, "rankine:roasted_asparagus_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.BLACK_WALNUT.get()), RankineItems.ROASTED_WALNUT.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineItems.BLACK_WALNUT.get())).build(consumer, "rankine:roasted_walnut_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.COCONUT.get()), RankineItems.TOASTED_COCONUT.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineItems.COCONUT.get())).build(consumer, "rankine:toasted_coconut_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.BREAD), RankineItems.TOAST.get(), 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.BREAD)).build(consumer, "rankine:toast_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.GF_BREAD.get()), RankineItems.TOAST.get(), 0.35F, 600, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.BREAD)).build(consumer, "rankine:toast_gf_csmoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.DOUGH.get()), Items.BREAD, 0.35F, 600, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.FLOUR)).build(consumer, "rankine:bread_smoking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.DOUGH_GF.get()), RankineItems.GF_BREAD.get(), 0.35F, 600, IRecipeSerializer.SMOKING).addCriterion("has_ingredient", hasItem(RankineTags.Items.FLOUR_GF)).build(consumer, "rankine:gf_bread_smoking");




    }

    private void slab(Consumer<IFinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', input)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void slab(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', tagIn)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void slab(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void slab(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', tagIn)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', input)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', tagIn)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', tagIn)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', tagIn)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', tagIn)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', input)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', tagIn)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', input)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> tagIn, String group) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', tagIn)
                .setGroup(group)
                .addCriterion("has_ingredient", hasItem(tagIn))
                .build(consumer);
    }
    
    
    
    
    
    
    
    
    
    
    
    private void door(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 3)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void trapdoor(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 2)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void boat(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("# #")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void fence(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 3)
                .patternLine("#S#")
                .patternLine("#S#")
                .key('#', input)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void fenceGate(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("S#S")
                .patternLine("S#S")
                .key('#', input)
                .key('S', Tags.Items.RODS_WOODEN)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void led(Consumer<IFinishedRecipe> consumer, Item output, ITag<Item> dye) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("DRD")
                .patternLine("RSR")
                .patternLine("DRD")
                .key('D', dye)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('S', RankineItems.SILICON_CARBIDE.get())
                .setGroup("led")
                .addCriterion("has_silicon_carbide", InventoryChangeTrigger.Instance.forItems(RankineItems.SILICON_CARBIDE.get()))
                .build(consumer);
    }
    
    private void pedestal(Consumer<IFinishedRecipe> consumer, Item output, String group, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output)
                .patternLine(" P ")
                .patternLine(" # ")
                .patternLine(" # ")
                .key('#', input)
                .key('P', RankineTags.Items.STONE_PRESSURE_PLATES)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void threeXthree(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthree(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void threeXthreeAlloy(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthreeAlloy(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void twoXtwo(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("##")
                .patternLine("##")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void twoXtwo(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("##")
                .patternLine("##")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void OneToX(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
            ShapelessRecipeBuilder.shapelessRecipe(output, count)
            .addIngredient(input)
            //.setGroup("rankine")
            .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
            .build(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToX(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapelessRecipe(output, count)
                .addIngredient(input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void OneToXAlloy(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                //.setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToXAlloy(Consumer<IFinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                .setGroup("rankine")
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void OneToXTag(Consumer<IFinishedRecipe> consumer, Item output, String group, ITag<Item> input, int count, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapelessRecipe(output, count)
                .addIngredient(input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    
    private void hLine(Consumer<IFinishedRecipe> consumer, Item output, int count, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }    
    private void hLine(Consumer<IFinishedRecipe> consumer, Item output, int count, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void rod(Consumer<IFinishedRecipe> consumer, Item output, int count, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("  #")
                .patternLine(" # ")
                .patternLine("#  ")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

    private void ladder(Consumer<IFinishedRecipe> consumer, Item output, int count, ITag<Item> input) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("# #")
                .key('#', input)
                .addCriterion("has_ingredient", hasItem(input))
                .build(consumer);
    }

}