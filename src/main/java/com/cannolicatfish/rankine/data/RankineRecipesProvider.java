package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

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
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.LOGS_THAT_BURN), RankineItems.ASH.get(), 0.1F, 200).addCriterion("has_logs", hasItem(ItemTags.LOGS_THAT_BURN)).build(consumer, "ash_from_smelting");
        ShapedRecipeBuilder.shapedRecipe(Blocks.BRICKS).key('#', Items.BRICK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_brick", hasItem(Items.BRICK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.END_STONE_BRICKS, 2).key('#', Blocks.END_STONE).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_end_stone", hasItem(Blocks.END_STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.QUARTZ_BRICKS, 2).key('#', Blocks.QUARTZ_BLOCK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_quartz_block", hasItem(Blocks.QUARTZ_BLOCK)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STONE_BRICKS, 2).key('#', Blocks.STONE).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_stone", hasItem(Blocks.STONE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.NETHER_BRICKS).key('#', Items.NETHER_BRICK).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_netherbrick", hasItem(Items.NETHER_BRICK)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.RED_NETHER_BRICKS).addIngredient(Blocks.NETHER_BRICKS).addIngredient(Items.NETHER_WART).addCriterion("has_nether_wart", hasItem(Items.NETHER_WART)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.PRISMARINE_BRICKS).key('#', Items.PRISMARINE_SHARD).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.DARK_PRISMARINE).addIngredient(Blocks.PRISMARINE_BRICKS).addIngredient(Tags.Items.DYES_BLACK).addCriterion("has_prismarine_shard", hasItem(Items.PRISMARINE_SHARD)).build(consumer);


        //ALTERNATIVE RECIPES
        //most use alloys so they use alloy_crafting
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 3).patternLine("C").patternLine("S").key('C', RankineItems.LIGNITE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_coal", hasItem(RankineItems.LIGNITE.get())).build(consumer, "torch_from_lignite");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 4).patternLine("C").patternLine("S").key('C', RankineItems.SUBBITUMINOUS_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_coal", hasItem(RankineItems.SUBBITUMINOUS_COAL.get())).build(consumer, "torch_from_subbituminous_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 6).patternLine("C").patternLine("S").key('C', RankineItems.BITUMINOUS_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_coal", hasItem(RankineItems.BITUMINOUS_COAL.get())).build(consumer, "torch_from_bituminous_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 8).patternLine("C").patternLine("S").key('C', RankineItems.ANTHRACITE_COAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_coal", hasItem(RankineItems.ANTHRACITE_COAL.get())).build(consumer, "torch_from_anthracite_coal");
        ShapedRecipeBuilder.shapedRecipe(Blocks.TORCH, 8).patternLine("C").patternLine("S").key('C', RankineItems.COKE.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_coal", hasItem(RankineItems.COKE.get())).build(consumer, "torch_from_coke");







        //RANKINE
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),4).addIngredient(RankineItems.JUTE.get()).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_jute");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.VINE).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_vine");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.WEEPING_VINES).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_weeping_vines");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.TWISTING_VINES).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_twisting_vines");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.TALL_GRASS).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_tall_grass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),2).addIngredient(Items.LARGE_FERN).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_large_fern");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.GRASS).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_grass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.SEAGRASS).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_seagrass");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PLANT_FIBER.get(),1).addIngredient(Items.FERN).addCriterion("has_flint", hasItem(Items.FLINT)).build(consumer, "plant_fiber_from_fern");



        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.SOD_BLOCK.get(), 4).patternLine("##").patternLine("##").key('#', RankineTags.Items.GRASS).addCriterion("has_block", hasItem(RankineTags.Items.GRASS)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ASPHALT_0.get(),6).addIngredient(RankineItems.BITUMEN.get()).addIngredient(Items.GRAVEL).addIngredient(Items.GRAVEL).addIngredient(Items.SAND).addCriterion("has_ingredient", hasItem(RankineItems.BITUMEN.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ROMAN_CONCRETE.get(),8).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineTags.Items.TUFF).addIngredient(RankineItems.ALUMINA.get()).addIngredient(Tags.Items.GEMS_QUARTZ).addIngredient(RankineItems.CEMENT_MIX.get()).addIngredient(RankineItems.CEMENT_MIX.get()).addCriterion("has_ingredient", hasItem(RankineTags.Items.TUFF)).build(consumer);


        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MORTAR.get(),4).addIngredient(RankineItems.CEMENT_MIX.get()).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineItems.CEMENT_MIX.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MORTAR.get(),2).addIngredient(RankineTags.Items.CLAY_BALLS).addIngredient(RankineTags.Items.DIRT).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineTags.Items.CLAY_BALLS)).build(consumer, "mortar_simple");

        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CEMENT_MIX.get(),4).addIngredient(RankineTags.Items.CLAY_BALLS).addIngredient(RankineTags.Items.STONES_LIMESTONE).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineTags.Items.CLAY_BALLS)).build(consumer, "cement_mix_limestone");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CEMENT_MIX.get(),6).addIngredient(RankineTags.Items.CLAY_BALLS).addIngredient(RankineItems.QUICKLIME.get()).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineTags.Items.CLAY_BALLS)).build(consumer, "cement_mix_quicklime");
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CEMENT_MIX.get(),6).addIngredient(RankineTags.Items.SLAG).addIngredient(RankineTags.Items.ASH).addIngredient(ItemTags.SAND).addCriterion("has_ingredient", hasItem(RankineTags.Items.SLAG)).build(consumer, "cement_mix_slag");




        for (Item MINERAL : RankineLists.BLASTING_MINERALS) {
            String baseName = MINERAL.getRegistryName().getPath();
            Item INGOT = RankineLists.BLASTING_INGOTS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            Item MINERAL_BLOCK = RankineLists.BLASTING_MINERAL_BLOCKS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            Item INGOT_BLOCK = RankineLists.BLASTING_BLOCKS.get(RankineLists.BLASTING_MINERALS.indexOf(MINERAL));
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(MINERAL), INGOT, 0.5F, 100).addCriterion("has_"+baseName, hasItem(MINERAL)).build(consumer, INGOT.getRegistryName().getPath()+"_from_"+baseName+"_blasting");
            CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(MINERAL_BLOCK), INGOT_BLOCK, 5.0F, 800).addCriterion("has_"+baseName, hasItem(MINERAL)).build(consumer, INGOT_BLOCK.getRegistryName().getPath()+"_from_"+baseName+"_block_blasting");
            //beehive oven
        }
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.APATITE.get()), RankineItems.PHOSPHORUS_NUGGET.get(), 0.5F, 100).addCriterion("has_ingredient", hasItem(RankineItems.APATITE.get())).build(consumer, RankineItems.APATITE.get().getRegistryName().getPath()+"_from_apatite_blasting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.APATITE_BLOCK.get()), RankineItems.PHOSPHORUS.get(), 5.0F, 800).addCriterion("has_ingredient", hasItem(RankineItems.APATITE.get())).build(consumer, RankineItems.APATITE_BLOCK.get().getRegistryName().getPath()+"_from_apatite_block_blasting");


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
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.MINERAL_WOOL.get()).addIngredient(RankineTags.Items.MINERAL_WOOL).addIngredient(RankineItems.PUMICE_SOAP.get()).addCriterion("has_slag", hasItem(RankineTags.Items.SLAG)).build(consumer);
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
        twoXtwo(consumer, Items.WHITE_DYE, RankineItems.ILMENITE.get(), 16, "has_ingredient", RankineItems.ILMENITE.get(), "white_dye_from_ilmenite");
        twoXtwo(consumer, Items.YELLOW_DYE, RankineItems.CHROMITE.get(), 16, "has_ingredient", RankineItems.CHROMITE.get(), "yellow_dye_from_chromite");
        twoXtwo(consumer, Items.LIGHT_BLUE_DYE, RankineItems.AZURITE.get(), 16, "has_ingredient", RankineItems.AZURITE.get(), "light_blue_dye_from_azurite");
        //twoXtwo(consumer, Items.GREEN_DYE, RankineItems.MALACHITE.get(), 16, "has_ingredient", RankineItems.MALACHITE.get(), "_dye_from_");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(RankineItems.BONE_CHAR.get()).addCriterion("has_ingredient", hasItem(RankineItems.BONE_CHAR.get())).build(consumer, "black_dye_from_bone_char");
        ShapelessRecipeBuilder.shapelessRecipe(Items.YELLOW_DYE).addIngredient(RankineItems.GOLDENROD.get()).addCriterion("has_ingredient", hasItem(RankineItems.GOLDENROD.get())).build(consumer, "yellow_dye_from_goldenrod");
        ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(RankineItems.RED_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.RED_LILY.get())).build(consumer, "red_dye_from_red_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.ORANGE_DYE).addIngredient(RankineItems.ORANGE_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.ORANGE_LILY.get())).build(consumer, "orange_dye_from_orange_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE).addIngredient(RankineItems.WHITE_LILY.get()).addCriterion("has_ingredient", hasItem(RankineItems.WHITE_LILY.get())).build(consumer, "white_dye_from_white_lily");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLACK_DYE).addIngredient(RankineItems.BLACK_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.BLACK_MORNING_GLORY.get())).build(consumer, "black_dye_from_black_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLUE_DYE).addIngredient(RankineItems.BLUE_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.BLUE_MORNING_GLORY.get())).build(consumer, "blue_dye_from_blue_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(RankineItems.PURPLE_MORNING_GLORY.get()).addCriterion("has_ingredient", hasItem(RankineItems.PURPLE_MORNING_GLORY.get())).build(consumer, "purple_dye_from_purple_morning_glory");
        ShapelessRecipeBuilder.shapelessRecipe(Items.GRAY_DYE).addIngredient(Items.WATER_BUCKET).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addIngredient(RankineItems.ASH.get()).addCriterion("has_ingredient", hasItem(RankineItems.ASH.get())).build(consumer, "gray_dye_from_ash");


        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLACKBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(RankineTags.Items.BERRIES_BLACKBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_BLACKBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.BLUEBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(RankineTags.Items.BERRIES_BLUEBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_BLUEBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.RASPBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(RankineTags.Items.BERRIES_RASPBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_RASPBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.ELDERBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(RankineTags.Items.BERRIES_ELDERBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_ELDERBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.CRANBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(RankineTags.Items.BERRIES_CRANBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_CRANBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.SNOWBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(RankineTags.Items.BERRIES_SNOWBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_SNOWBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.STRAWBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(RankineTags.Items.BERRIES_STRAWBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_STRAWBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.POKEBERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(RankineTags.Items.BERRIES_POKEBERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_POKEBERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.SWEET_BERRY_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(RankineTags.Items.BERRIES_SWEET_BERRY).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.BERRIES_SWEET_BERRY)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(RankineItems.PINEAPPLE_JAM.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(RankineTags.Items.PINEAPPLE).addIngredient(Items.SUGAR).addIngredient(Items.SUGAR).addCriterion("has_ingredient", hasItem(RankineTags.Items.PINEAPPLE)).build(consumer);


        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CLAY_BRICKS.get()).key('#', Items.CLAY_BALL).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_clay", hasItem(Items.CLAY_BALL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FIRE_CLAY_BRICKS.get()).key('#', RankineItems.FIRE_CLAY_BALL.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_clay", hasItem(RankineItems.FIRE_CLAY_BALL.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.REFRACTORY_BRICKS.get()).key('#', RankineItems.REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_refractory_brick", hasItem(RankineItems.REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.HIGH_REFRACTORY_BRICKS.get()).key('#', RankineItems.HIGH_REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_high_refractory_brick", hasItem(RankineItems.HIGH_REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get()).key('#', RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_refractory_brick", hasItem(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.METEORITE_BRICKS.get()).key('#', RankineBlocks.METEORITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_meteorite", hasItem(RankineBlocks.METEORITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ENSTATITE_BRICKS.get()).key('#', RankineBlocks.ENSTATITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_frozen_meteorite", hasItem(RankineBlocks.ENSTATITE.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.FROZEN_METEORITE_BRICKS.get()).key('#', RankineBlocks.FROZEN_METEORITE.get()).key('M', RankineItems.MORTAR.get()).patternLine("#M").patternLine("M#").addCriterion("has_meteorite", hasItem(RankineBlocks.FROZEN_METEORITE.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.METEORITE.get()), RankineBlocks.METEORITE_BRICKS.get(), 1).addCriterion("has_meteorite", hasItem(RankineBlocks.METEORITE.get())).build(consumer, "meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.FROZEN_METEORITE.get()), RankineBlocks.FROZEN_METEORITE_BRICKS.get(), 1).addCriterion("has_frozen_meteorite", hasItem(RankineBlocks.FROZEN_METEORITE.get())).build(consumer, "frozen_meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(RankineBlocks.ENSTATITE.get()), RankineBlocks.ENSTATITE_BRICKS.get(), 1).addCriterion("has_meteorite", hasItem(RankineBlocks.ENSTATITE.get())).build(consumer, "enstatite_bricks_from_stonecutting");

        slab(consumer, RankineItems.FIBER_BLOCK_SLAB.get(), RankineItems.FIBER_BLOCK.get(), "rankine", "has_block", RankineItems.FIBER_BLOCK.get());
        verticalSlab(consumer, RankineItems.FIBER_BLOCK_VERTICAL_SLAB.get(), RankineItems.FIBER_BLOCK.get(), "rankine",  "has_block", RankineItems.FIBER_BLOCK.get());
        stairs(consumer, RankineItems.FIBER_BLOCK_STAIRS.get(), RankineItems.FIBER_BLOCK.get(), "rankine",  "has_block", RankineItems.FIBER_BLOCK.get());
        wall(consumer, RankineItems.FIBER_BLOCK_WALL.get(), RankineItems.FIBER_BLOCK.get(), "rankine",  "has_block", RankineItems.FIBER_BLOCK.get());
        slab(consumer, RankineItems.CEMENT_SLAB.get(), RankineItems.CEMENT.get(), "rankine", "has_block", RankineItems.CEMENT.get());
        verticalSlab(consumer, RankineItems.CEMENT_VERTICAL_SLAB.get(), RankineItems.CEMENT.get(), "rankine",  "has_block", RankineItems.CEMENT.get());
        stairs(consumer, RankineItems.CEMENT_STAIRS.get(), RankineItems.CEMENT.get(), "rankine",  "has_block", RankineItems.CEMENT.get());
        wall(consumer, RankineItems.CEMENT_WALL.get(), RankineItems.CEMENT.get(), "rankine",  "has_block", RankineItems.CEMENT.get());
        slab(consumer, RankineItems.CONCRETE_SLAB.get(), RankineItems.CONCRETE.get(), "rankine", "has_block", RankineItems.CONCRETE.get());
        verticalSlab(consumer, RankineItems.CONCRETE_VERTICAL_SLAB.get(), RankineItems.CONCRETE.get(), "rankine",  "has_block", RankineItems.CONCRETE.get());
        stairs(consumer, RankineItems.CONCRETE_STAIRS.get(), RankineItems.CONCRETE.get(), "rankine",  "has_block", RankineItems.CONCRETE.get());
        wall(consumer, RankineItems.CONCRETE_WALL.get(), RankineItems.CONCRETE.get(), "rankine",  "has_block", RankineItems.CONCRETE.get());
        slab(consumer, RankineItems.CLAY_BRICKS_SLAB.get(), RankineItems.CLAY_BRICKS.get(), "rankine", "has_block", RankineItems.CLAY_BRICKS.get());
        verticalSlab(consumer, RankineItems.CLAY_BRICKS_VERTICAL_SLAB.get(), RankineItems.CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.CLAY_BRICKS.get());
        stairs(consumer, RankineItems.CLAY_BRICKS_STAIRS.get(), RankineItems.CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.CLAY_BRICKS.get());
        wall(consumer, RankineItems.CLAY_BRICKS_WALL.get(), RankineItems.CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.CLAY_BRICKS.get());
        slab(consumer, RankineItems.FIRE_CLAY_BRICKS_SLAB.get(), RankineItems.FIRE_CLAY_BRICKS.get(), "rankine", "has_block", RankineItems.FIRE_CLAY_BRICKS.get());
        verticalSlab(consumer, RankineItems.FIRE_CLAY_BRICKS_VERTICAL_SLAB.get(), RankineItems.FIRE_CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.FIRE_CLAY_BRICKS.get());
        stairs(consumer, RankineItems.FIRE_CLAY_BRICKS_STAIRS.get(), RankineItems.FIRE_CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.FIRE_CLAY_BRICKS.get());
        wall(consumer, RankineItems.FIRE_CLAY_BRICKS_WALL.get(), RankineItems.FIRE_CLAY_BRICKS.get(), "rankine",  "has_block", RankineItems.FIRE_CLAY_BRICKS.get());
        slab(consumer, RankineItems.REFRACTORY_BRICKS_SLAB.get(), RankineItems.REFRACTORY_BRICKS.get(), "rankine", "has_block", RankineItems.REFRACTORY_BRICKS.get());
        verticalSlab(consumer, RankineItems.REFRACTORY_BRICKS_VERTICAL_SLAB.get(), RankineItems.REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.REFRACTORY_BRICKS.get());
        stairs(consumer, RankineItems.REFRACTORY_BRICKS_STAIRS.get(), RankineItems.REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.REFRACTORY_BRICKS.get());
        wall(consumer, RankineItems.REFRACTORY_BRICKS_WALL.get(), RankineItems.REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.REFRACTORY_BRICKS.get());
        slab(consumer, RankineItems.HIGH_REFRACTORY_BRICKS_SLAB.get(), RankineItems.HIGH_REFRACTORY_BRICKS.get(), "rankine", "has_block", RankineItems.HIGH_REFRACTORY_BRICKS.get());
        verticalSlab(consumer, RankineItems.HIGH_REFRACTORY_BRICKS_VERTICAL_SLAB.get(), RankineItems.HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.HIGH_REFRACTORY_BRICKS.get());
        stairs(consumer, RankineItems.HIGH_REFRACTORY_BRICKS_STAIRS.get(), RankineItems.HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.HIGH_REFRACTORY_BRICKS.get());
        wall(consumer, RankineItems.HIGH_REFRACTORY_BRICKS_WALL.get(), RankineItems.HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.HIGH_REFRACTORY_BRICKS.get());
        slab(consumer, RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS_SLAB.get(), RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get(), "rankine", "has_block", RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get());
        verticalSlab(consumer, RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS_VERTICAL_SLAB.get(), RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get());
        stairs(consumer, RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS_STAIRS.get(), RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get());
        wall(consumer, RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS_WALL.get(), RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get(), "rankine",  "has_block", RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get());
        slab(consumer, RankineItems.ROMAN_CONCRETE_SLAB.get(), RankineItems.ROMAN_CONCRETE.get(), "rankine", "has_block", RankineItems.ROMAN_CONCRETE.get());
        verticalSlab(consumer, RankineItems.ROMAN_CONCRETE_VERTICAL_SLAB.get(), RankineItems.ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE.get());
        stairs(consumer, RankineItems.ROMAN_CONCRETE_STAIRS.get(), RankineItems.ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE.get());
        wall(consumer, RankineItems.ROMAN_CONCRETE_WALL.get(), RankineItems.ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE.get());
        slab(consumer, RankineItems.POLISHED_ROMAN_CONCRETE_SLAB.get(), RankineItems.POLISHED_ROMAN_CONCRETE.get(), "rankine", "has_block", RankineItems.POLISHED_ROMAN_CONCRETE.get());
        verticalSlab(consumer, RankineItems.POLISHED_ROMAN_CONCRETE_VERTICAL_SLAB.get(), RankineItems.POLISHED_ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.POLISHED_ROMAN_CONCRETE.get());
        stairs(consumer, RankineItems.POLISHED_ROMAN_CONCRETE_STAIRS.get(), RankineItems.POLISHED_ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.POLISHED_ROMAN_CONCRETE.get());
        wall(consumer, RankineItems.POLISHED_ROMAN_CONCRETE_WALL.get(), RankineItems.POLISHED_ROMAN_CONCRETE.get(), "rankine",  "has_block", RankineItems.POLISHED_ROMAN_CONCRETE.get());
        slab(consumer, RankineItems.ROMAN_CONCRETE_BRICKS_SLAB.get(), RankineItems.ROMAN_CONCRETE_BRICKS.get(), "rankine", "has_block", RankineItems.ROMAN_CONCRETE_BRICKS.get());
        verticalSlab(consumer, RankineItems.ROMAN_CONCRETE_BRICKS_VERTICAL_SLAB.get(), RankineItems.ROMAN_CONCRETE_BRICKS.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE_BRICKS.get());
        stairs(consumer, RankineItems.ROMAN_CONCRETE_BRICKS_STAIRS.get(), RankineItems.ROMAN_CONCRETE_BRICKS.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE_BRICKS.get());
        wall(consumer, RankineItems.ROMAN_CONCRETE_BRICKS_WALL.get(), RankineItems.ROMAN_CONCRETE_BRICKS.get(), "rankine",  "has_block", RankineItems.ROMAN_CONCRETE_BRICKS.get());


        for (Item MINERAL_ITEM : RankineLists.MINERAL_ITEMS) {
            Item MINERAL_BLOCK = RankineLists.MINERAL_BLOCKS.get(RankineLists.MINERAL_ITEMS.indexOf(MINERAL_ITEM)).asItem();
            threeXthree(consumer, MINERAL_BLOCK, MINERAL_ITEM, 1, "has_ingredient", MINERAL_ITEM);
            OneToX(consumer, MINERAL_ITEM, MINERAL_BLOCK, 9, "has_ingredient", MINERAL_ITEM, MINERAL_ITEM.getRegistryName().getPath()+"_from_block");
        }

        //ELEMENTS
        for (Item NUGGET : RankineLists.ELEMENT_NUGGETS) {
            Block BLOCK = RankineLists.ELEMENT_BLOCKS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ELEMENT_INGOTS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            threeXthree(consumer, BLOCK.asItem(), INGOT, 1, "has_element", INGOT);
            threeXthree(consumer, INGOT, NUGGET, 1, "has_element", NUGGET, INGOT.getRegistryName().getPath()+"_from_"+NUGGET.getRegistryName().getPath());
            OneToX(consumer, INGOT, BLOCK.asItem(), 9, "has_element", BLOCK.asItem(), INGOT.getRegistryName().getPath()+"_from_"+BLOCK.getRegistryName().getPath());
            OneToX(consumer, NUGGET, INGOT, 9, "has_element", INGOT);
        }

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
        OneToXTag(consumer,RankineItems.YELLOW_BIRCH_PLANKS.get(),"planks",RankineTags.Items.YELLOW_BIRCH_LOGS,4,"has_log",RankineItems.YELLOW_BIRCH_LOG.get());
        OneToXTag(consumer,RankineItems.BLACK_BIRCH_PLANKS.get(),"planks",RankineTags.Items.BLACK_BIRCH_LOGS,4,"has_log",RankineItems.BLACK_BIRCH_LOG.get());
        OneToXTag(consumer,RankineItems.SHARINGA_PLANKS.get(),"planks",RankineTags.Items.SHARINGA_LOGS,4,"has_log",RankineItems.SHARINGA_LOG.get());
        OneToXTag(consumer,RankineItems.BLACK_WALNUT_PLANKS.get(),"planks",RankineTags.Items.BLACK_WALNUT_LOGS,4,"has_log",RankineItems.BLACK_WALNUT_LOG.get());
        OneToXTag(consumer,RankineItems.CINNAMON_PLANKS.get(),"planks",RankineTags.Items.CINNAMON_LOGS,4,"has_log",RankineItems.CINNAMON_LOG.get());
        OneToXTag(consumer,RankineItems.CORK_OAK_PLANKS.get(),"planks",RankineTags.Items.CORK_OAK_LOGS,4,"has_log",RankineItems.CORK_OAK_LOG.get());
        OneToXTag(consumer,RankineItems.ERYTHRINA_PLANKS.get(),"planks",RankineTags.Items.ERYTHRINA_LOGS,4,"has_log",RankineItems.ERYTHRINA_LOG.get());
        OneToXTag(consumer,RankineItems.CHARRED_PLANKS.get(),"planks",RankineTags.Items.CHARRED_LOGS,4,"has_log",RankineItems.CHARRED_LOG.get());
        OneToXTag(consumer,RankineItems.MAPLE_PLANKS.get(),"planks",RankineTags.Items.MAPLE_LOGS,4,"has_log",RankineItems.MAPLE_LOG.get());
        OneToXTag(consumer,RankineItems.PETRIFIED_CHORUS_PLANKS.get(),"planks",RankineTags.Items.PETRIFIED_CHORUS_LOGS,4,"has_log",RankineItems.PETRIFIED_CHORUS_LOG.get());

        for (Block PLANK : RankineLists.PLANKS) {
            Block WOODEN_SLAB = RankineLists.WOODEN_SLABS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_VERTICAL_SLABS = RankineLists.WOODEN_VERTICAL_SLABS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_STAIRSS = RankineLists.WOODEN_STAIRS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_PRESSURE_PLATES = RankineLists.WOODEN_PRESSURE_PLATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_BUTTONS = RankineLists.WOODEN_BUTTONS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_DOORS = RankineLists.WOODEN_DOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_TRAPDOORS = RankineLists.WOODEN_TRAPDOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCES = RankineLists.WOODEN_FENCES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCE_GATES = RankineLists.WOODEN_FENCE_GATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Item WOODEN_BOATS = RankineLists.WOODEN_BOATS.get(RankineLists.PLANKS.indexOf(PLANK));
            slab(consumer, WOODEN_SLAB.asItem(), PLANK.asItem(), "wooden_slab", "has_plank", PLANK.asItem());
            verticalSlab(consumer, WOODEN_VERTICAL_SLABS.asItem(), PLANK.asItem(), "wooden_vertical_slab",  "has_plank", PLANK.asItem());
            stairs(consumer, WOODEN_STAIRSS.asItem(), PLANK.asItem(), "wooden_stairs",  "has_plank", PLANK.asItem());
            button(consumer, WOODEN_BUTTONS.asItem(), PLANK.asItem(), "wooden_button",  "has_plank", PLANK.asItem());
            pressurePlate(consumer, WOODEN_PRESSURE_PLATES.asItem(), PLANK.asItem(), "wooden_pressure_plate",  "has_plank", PLANK.asItem());
            door(consumer, WOODEN_DOORS.asItem(), PLANK.asItem(), "wooden_door",  "has_plank", PLANK.asItem());
            trapdoor(consumer, WOODEN_TRAPDOORS.asItem(), PLANK.asItem(), "wooden_trapdoor",  "has_plank", PLANK.asItem());
            fence(consumer, WOODEN_FENCES.asItem(), PLANK.asItem(), "wooden_fence",  "has_plank", PLANK.asItem());
            fenceGate(consumer, WOODEN_FENCE_GATES.asItem(), PLANK.asItem(), "wooden_fence_gate",  "has_plank", PLANK.asItem());
            boat(consumer, WOODEN_BOATS, PLANK.asItem(), "boat",  "has_plank", PLANK.asItem());
        }


        for (Block STONE : RankineLists.STONE) {
            String baseStone = STONE.getRegistryName().getPath();
            Block POLISHED_STONE = RankineLists.POLISHED_STONE.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS = RankineLists.STONE_BRICKS.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_SLAB = RankineLists.STONE_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block POLISHED_STONE_SLAB = RankineLists.POLISHED_STONE_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS_SLAB = RankineLists.STONE_BRICKS_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_VERTICAL_SLAB = RankineLists.STONE_VERTICAL_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block POLISHED_STONE_VERTICAL_SLAB = RankineLists.POLISHED_STONE_VERTICAL_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS_VERTICAL_SLAB = RankineLists.STONE_BRICKS_VERTICAL_SLAB.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_STAIRS = RankineLists.STONE_STAIRS.get(RankineLists.STONE.indexOf(STONE));
            Block POLISHED_STONE_STAIRS = RankineLists.POLISHED_STONE_STAIRS.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS_STAIRS = RankineLists.STONE_BRICKS_STAIRS.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_WALL = RankineLists.STONE_WALL.get(RankineLists.STONE.indexOf(STONE));
            Block POLISHED_STONE_WALL = RankineLists.POLISHED_STONE_WALL.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS_WALL = RankineLists.STONE_BRICKS_WALL.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_PRESSURE_PLATE = RankineLists.STONE_PRESSURE_PLATE.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BRICKS_PRESSURE_PLATE = RankineLists.STONE_BRICKS_PRESSURE_PLATE.get(RankineLists.STONE.indexOf(STONE));
            Block STONE_BUTTON = RankineLists.STONE_BUTTON.get(RankineLists.STONE.indexOf(STONE));

            ShapedRecipeBuilder.shapedRecipe(POLISHED_STONE, 4)
                    .patternLine("##")
                    .patternLine("##")
                    .key('#', STONE)
                    .setGroup("rankine")
                    .addCriterion("has_"+baseStone, InventoryChangeTrigger.Instance.forItems(STONE))
                    .build(consumer);

            ShapedRecipeBuilder.shapedRecipe(STONE_BRICKS, 2)
                    .patternLine("#M")
                    .patternLine("M#")
                    .key('#', STONE)
                    .key('M', RankineItems.MORTAR.get())
                    .setGroup("rankine")
                    .addCriterion("has_mortar", InventoryChangeTrigger.Instance.forItems(RankineItems.MORTAR.get()))
                    .build(consumer);

            slab(consumer, STONE_SLAB.asItem(), STONE.asItem(), "stone_slab", "has_"+baseStone, STONE.asItem());
            slab(consumer, POLISHED_STONE_SLAB.asItem(), POLISHED_STONE.asItem(), "stone_slab",  "has_polished_"+baseStone, POLISHED_STONE.asItem());
            slab(consumer, STONE_BRICKS_SLAB.asItem(), STONE_BRICKS.asItem(), "stone_slab",  "has_"+baseStone+"_bricks", STONE_BRICKS.asItem());
            verticalSlab(consumer, STONE_VERTICAL_SLAB.asItem(), STONE.asItem(), "stone_vertical_slab", "has_"+baseStone, STONE.asItem());
            verticalSlab(consumer, POLISHED_STONE_VERTICAL_SLAB.asItem(), POLISHED_STONE.asItem(), "stone_vertical_slab", "has_polished_"+baseStone, POLISHED_STONE.asItem());
            verticalSlab(consumer, STONE_BRICKS_VERTICAL_SLAB.asItem(), STONE_BRICKS.asItem(), "stone_vertical_slab", "has_"+baseStone+"_bricks", STONE_BRICKS.asItem());
            stairs(consumer, STONE_STAIRS.asItem(), STONE.asItem(), "stone_stairs", "has_"+baseStone, STONE.asItem());
            stairs(consumer, POLISHED_STONE_STAIRS.asItem(), POLISHED_STONE.asItem(), "stone_stairs", "has_polished_"+baseStone, POLISHED_STONE.asItem());
            stairs(consumer, STONE_BRICKS_STAIRS.asItem(), STONE_BRICKS.asItem(), "stone_stairs", "has_"+baseStone+"_bricks", STONE_BRICKS.asItem());
            wall(consumer, STONE_WALL.asItem(), STONE.asItem(), "stone_wall", "has_"+baseStone, STONE.asItem());
            wall(consumer, POLISHED_STONE_WALL.asItem(), POLISHED_STONE.asItem(), "stone_wall", "has_polished_"+baseStone, POLISHED_STONE.asItem());
            wall(consumer, STONE_BRICKS_WALL.asItem(), STONE_BRICKS.asItem(), "stone_wall", "has_"+baseStone+"_bricks", STONE_BRICKS.asItem());
            button(consumer, STONE_BUTTON.asItem(), STONE.asItem(), "stone_button", "has_"+baseStone, STONE.asItem());
            pressurePlate(consumer, STONE_PRESSURE_PLATE.asItem(), STONE.asItem(), "stone_pressure_plate", "has_"+baseStone, STONE.asItem());
            pressurePlate(consumer, STONE_BRICKS_PRESSURE_PLATE.asItem(), STONE_BRICKS.asItem(), "stone_pressure_plate", "has_"+baseStone+"_bricks", STONE_BRICKS.asItem());
            
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_SLAB, 2).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_slab_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_VERTICAL_SLAB, 2).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_vertical_slab_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_STAIRS).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_stairs_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_WALL).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_wall_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_PRESSURE_PLATE).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_pressure_plate_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE), STONE_BUTTON).addCriterion("has_"+baseStone, hasItem(STONE)).build(consumer, baseStone+"_button_from_"+baseStone+"_stonecutting");

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_SLAB, 2).addCriterion("has_"+baseStone+"_bricks", hasItem(STONE_BRICKS)).build(consumer, baseStone+"_brick_slab_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_VERTICAL_SLAB, 2).addCriterion("has_"+baseStone+"_bricks", hasItem(STONE_BRICKS)).build(consumer, baseStone+"_brick_vertical_slab_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_STAIRS).addCriterion("has_"+baseStone+"_bricks", hasItem(STONE_BRICKS)).build(consumer, baseStone+"_brick_stairs_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_WALL).addCriterion("has_"+baseStone+"_bricks", hasItem(STONE_BRICKS)).build(consumer, baseStone+"_brick_wall_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(STONE_BRICKS), STONE_BRICKS_PRESSURE_PLATE).addCriterion("has_"+baseStone+"_bricks", hasItem(STONE_BRICKS)).build(consumer, baseStone+"_brick_button_from_"+baseStone+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_SLAB, 2).addCriterion("has_polished_"+baseStone, hasItem(POLISHED_STONE)).build(consumer, "polished_"+baseStone+"_slab_from_polished_"+baseStone+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_VERTICAL_SLAB, 2).addCriterion("has_polished_"+baseStone, hasItem(POLISHED_STONE)).build(consumer, "polished_"+baseStone+"__vertical_slab_from_polished_"+baseStone+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_STAIRS).addCriterion("has_polished_"+baseStone, hasItem(POLISHED_STONE)).build(consumer, "polished_"+baseStone+"_stairs_from_polished_"+baseStone+"__stonecutting");
            SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(POLISHED_STONE), POLISHED_STONE_WALL).addCriterion("has_polished_"+baseStone, hasItem(POLISHED_STONE)).build(consumer, "polished_"+baseStone+"_wall_from_polished_"+baseStone+"__stonecutting");

        }

        //NATIVE ORES
        //CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ALUMINUM_ORE.get()), RankineItems.ALUMINUM_INGOT.get(), 0.5F, 200).addCriterion("has_native_aluminum_ore", hasItem(RankineBlocks.NATIVE_ALUMINUM_ORE.get().asItem())).build(consumer, "aluminum_ingot_from_native_ore_smelting");
        //CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ALUMINUM_ORE.get()), RankineItems.ALUMINUM_INGOT.get(), 0.5F, 100).addCriterion("has_native_aluminum_ore", hasItem(RankineBlocks.NATIVE_ALUMINUM_ORE.get().asItem())).build(consumer, "aluminum_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 200).addCriterion("has_native_sulfur_ore", hasItem(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).build(consumer, "sulfur_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 100).addCriterion("has_native_sulfur_ore", hasItem(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).build(consumer, "sulfur_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC_INGOT.get(), 0.5F, 200).addCriterion("has_native_arsenic_ore", hasItem(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).build(consumer, "arsenic_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC_INGOT.get(), 0.5F, 100).addCriterion("has_native_arsenic_ore", hasItem(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).build(consumer, "arsenic_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 200).addCriterion("has_native_bismuth_ore", hasItem(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).build(consumer, "bismuth_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 100).addCriterion("has_native_bismuth_ore", hasItem(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).build(consumer, "bismuth_ingot_from_native_ore_blasting");
        //CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_COPPER_ORE.get()), RankineItems.COPPER_INGOT.get(), 0.5F, 200).addCriterion("has_native_copper_ore", hasItem(RankineBlocks.NATIVE_COPPER_ORE.get().asItem())).build(consumer, "copper_ingot_from_native_ore_smelting");
        //CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_COPPER_ORE.get()), RankineItems.COPPER_INGOT.get(), 0.5F, 100).addCriterion("has_native_copper_ore", hasItem(RankineBlocks.NATIVE_COPPER_ORE.get().asItem())).build(consumer, "copper_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 200).addCriterion("has_native_lead_ore", hasItem(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).build(consumer, "lead_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 100).addCriterion("has_native_lead_ore", hasItem(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).build(consumer, "lead_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 200).addCriterion("has_native_gold_ore", hasItem(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).build(consumer, "gold_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 100).addCriterion("has_native_gold_ore", hasItem(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).build(consumer, "gold_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 200).addCriterion("has_native_silver_ore", hasItem(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).build(consumer, "silver_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 100).addCriterion("has_native_silver_ore", hasItem(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).build(consumer, "silver_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 200).addCriterion("has_native_tin_ore", hasItem(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).build(consumer, "tin_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 100).addCriterion("has_native_tin_ore", hasItem(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).build(consumer, "tin_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 200).addCriterion("has_native_indium_ore", hasItem(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).build(consumer, "indium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 100).addCriterion("has_native_indium_ore", hasItem(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).build(consumer, "indium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 200).addCriterion("has_native_gallium_ore", hasItem(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).build(consumer, "gallium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 100).addCriterion("has_native_gallium_ore", hasItem(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).build(consumer, "gallium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM_INGOT.get(), 0.5F, 200).addCriterion("has_native_selenium_ore", hasItem(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).build(consumer, "selenium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM_INGOT.get(), 0.5F, 100).addCriterion("has_native_selenium_ore", hasItem(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).build(consumer, "selenium_ingot_from_native_ore_blasting");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM_INGOT.get(), 0.5F, 200).addCriterion("has_native_tellurium_ore", hasItem(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).build(consumer, "tellurium_ingot_from_native_ore_smelting");
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM_INGOT.get(), 0.5F, 100).addCriterion("has_native_tellurium_ore", hasItem(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).build(consumer, "tellurium_ingot_from_native_ore_blasting");

        //ALLOY STUFFS
        for (Block ALLOY_BLOCK : RankineLists.ALLOY_BLOCKS) {
            Item ALLOY = RankineLists.ALLOYS.get(RankineLists.ALLOY_BLOCKS.indexOf(ALLOY_BLOCK));
            if (!ALLOY.equals("solder_alloy")) {
                threeXthree(consumer, ALLOY_BLOCK.asItem(), ALLOY, 1, "has_alloy", ALLOY);
            }
        }
        for (Block ALLOY_PEDESTAL : RankineLists.ALLOY_PEDESTALS) {
            Item ALLOY = RankineLists.ALLOYS.get(RankineLists.ALLOY_PEDESTALS.indexOf(ALLOY_PEDESTAL));
            if (!ALLOY.equals("solder_alloy")) {
                pedestal(consumer, ALLOY_PEDESTAL.asItem(), "pedestal", ALLOY, "has_alloy", ALLOY);
            }
        }

        door(consumer, RankineItems.BRASS_DOOR.get(), RankineItems.BRASS_ALLOY.get(), "metal_door", "has_alloy", RankineItems.BRASS_ALLOY.get());
        door(consumer, RankineItems.BRONZE_DOOR.get(), RankineItems.BRONZE_ALLOY.get(), "metal_door", "has_alloy", RankineItems.BRONZE_ALLOY.get());
        door(consumer, RankineItems.CUPRONICKEL_DOOR.get(), RankineItems.CUPRONICKEL_ALLOY.get(), "metal_door", "has_alloy", RankineItems.CUPRONICKEL_ALLOY.get());
        door(consumer, RankineItems.STEEL_DOOR.get(), RankineItems.STEEL_ALLOY.get(), "metal_door", "has_alloy", RankineItems.STEEL_ALLOY.get());
        trapdoor(consumer, RankineItems.BRASS_TRAPDOOR.get(), RankineItems.BRASS_ALLOY.get(), "metal_trapdoor", "has_alloy", RankineItems.BRASS_ALLOY.get());
        trapdoor(consumer, RankineItems.BRONZE_TRAPDOOR.get(), RankineItems.BRONZE_ALLOY.get(), "metal_trapdoor", "has_alloy", RankineItems.BRONZE_ALLOY.get());
        trapdoor(consumer, RankineItems.CUPRONICKEL_TRAPDOOR.get(), RankineItems.CUPRONICKEL_ALLOY.get(), "metal_trapdoor", "has_alloy", RankineItems.CUPRONICKEL_ALLOY.get());
        trapdoor(consumer, RankineItems.STEEL_TRAPDOOR.get(), RankineItems.STEEL_ALLOY.get(), "metal_trapdoor", "has_alloy", RankineItems.STEEL_ALLOY.get());

        //SHEETMETALS
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.COPPER_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.COPPER_INGOT.get()).setGroup("rankine").addCriterion("has_copper_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.COPPER_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TIN_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.TIN_INGOT.get()).setGroup("rankine").addCriterion("has_tin_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.TIN_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.LEAD_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.LEAD_INGOT.get()).setGroup("rankine").addCriterion("has_lead_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.LEAD_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.SILVER_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.SILVER_INGOT.get()).setGroup("rankine").addCriterion("has_silver_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.SILVER_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.GOLD_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', Items.GOLD_INGOT).setGroup("rankine").addCriterion("has_gold_ingot", InventoryChangeTrigger.Instance.forItems(Items.GOLD_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BISMUTH_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.BISMUTH_INGOT.get()).setGroup("rankine").addCriterion("has_bismuth_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.BISMUTH_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TITANIUM_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.TITANIUM_INGOT.get()).setGroup("rankine").addCriterion("has_titanium_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.TITANIUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.TUNGSTEN_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.TUNGSTEN_INGOT.get()).setGroup("rankine").addCriterion("has_tungsten_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.TUNGSTEN_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.NICKEL_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.NICKEL_INGOT.get()).setGroup("rankine").addCriterion("has_nickel_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.NICKEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.PLATINUM_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.PLATINUM_INGOT.get()).setGroup("rankine").addCriterion("has_platinum_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.PLATINUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.ALUMINUM_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.ALUMINUM_INGOT.get()).setGroup("rankine").addCriterion("has_aluminum_ingot", InventoryChangeTrigger.Instance.forItems(RankineItems.ALUMINUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BRASS_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.BRASS_ALLOY.get()).setGroup("rankine").addCriterion("has_brass_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.BRASS_ALLOY.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.BRONZE_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.BRONZE_ALLOY.get()).setGroup("rankine").addCriterion("has_bronze_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.BRONZE_ALLOY.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.INVAR_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.INVAR_ALLOY.get()).setGroup("rankine").addCriterion("has_invar_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.INVAR_ALLOY.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.CUPRONICKEL_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.CUPRONICKEL_ALLOY.get()).setGroup("rankine").addCriterion("has_cupronickel_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.CUPRONICKEL_ALLOY.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.STEEL_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.STEEL_ALLOY.get()).setGroup("rankine").addCriterion("has_steel_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.STEEL_ALLOY.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get(), 8).patternLine("# #").patternLine("# #").patternLine("# #").key('#', RankineItems.STAINLESS_STEEL_ALLOY.get()).setGroup("rankine").addCriterion("has_stainless_steel_alloy", InventoryChangeTrigger.Instance.forItems(RankineItems.STAINLESS_STEEL_ALLOY.get())).build(consumer);

        verticalSlab(consumer,RankineItems.COPPER_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.COPPER_SHEETMETAL.get(), "sheetmetal_vertical_slab", "has_copper_ingot",RankineItems.COPPER_INGOT.get());
        verticalSlab(consumer,RankineItems.TIN_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.TIN_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_tin_ingot",RankineItems.TIN_INGOT.get());
        verticalSlab(consumer,RankineItems.ALUMINUM_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.ALUMINUM_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_aluminum_ingot",RankineItems.ALUMINUM_INGOT.get());
        verticalSlab(consumer,RankineItems.LEAD_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.LEAD_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_lead_ingot",RankineItems.LEAD_INGOT.get());
        verticalSlab(consumer,RankineItems.SILVER_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.SILVER_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_silver_ingot",RankineItems.SILVER_INGOT.get());
        verticalSlab(consumer,RankineItems.BISMUTH_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.BISMUTH_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_bismuth_ingot",RankineItems.BISMUTH_INGOT.get());
        verticalSlab(consumer,RankineItems.PLATINUM_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.PLATINUM_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_platinum_ingot",RankineItems.PLATINUM_INGOT.get());
        verticalSlab(consumer,RankineItems.TITANIUM_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.TITANIUM_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_titanium_ingot",RankineItems.TITANIUM_INGOT.get());
        verticalSlab(consumer,RankineItems.TUNGSTEN_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.TUNGSTEN_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_tungsten_ingot",RankineItems.TUNGSTEN_INGOT.get());
        verticalSlab(consumer,RankineItems.NICKEL_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.NICKEL_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_nickel_ingot",RankineItems.NICKEL_INGOT.get());
        verticalSlab(consumer,RankineItems.GOLD_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.GOLD_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_gold_ingot",Items.GOLD_INGOT);
        verticalSlab(consumer,RankineItems.BRASS_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.BRASS_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_brass_alloy",RankineItems.BRASS_ALLOY.get());
        verticalSlab(consumer,RankineItems.BRONZE_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.BRONZE_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_bronze_alloy",RankineItems.BRONZE_ALLOY.get());
        verticalSlab(consumer,RankineItems.CUPRONICKEL_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.CUPRONICKEL_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_cupronickel_alloy",RankineItems.CUPRONICKEL_ALLOY.get());
        verticalSlab(consumer,RankineItems.INVAR_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.INVAR_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_invar_alloy",RankineItems.INVAR_ALLOY.get());
        verticalSlab(consumer,RankineItems.STEEL_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.STEEL_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_steel_alloy",RankineItems.STEEL_ALLOY.get());
        verticalSlab(consumer,RankineItems.STAINLESS_STEEL_SHEETMETAL_VERTICAL_SLAB.get(),RankineItems.STAINLESS_STEEL_SHEETMETAL.get(), "sheetmetal_vertical_slab","has_stainless_steel_alloy",RankineItems.STAINLESS_STEEL_ALLOY.get());


        
        hLine(consumer,RankineItems.TAP_LINE.get(),3,RankineItems.VULCANIZED_RUBBER.get(),"has_ingredient",RankineItems.VULCANIZED_RUBBER.get());
        hLine(consumer,RankineItems.METAL_PIPE.get(),3,RankineItems.CUPRONICKEL_ALLOY.get(),"has_ingredient",RankineItems.CUPRONICKEL_ALLOY.get());
        
        
        ladder(consumer,RankineItems.BRASS_LADDER.get(),8,RankineItems.BRASS_ALLOY.get(),"has_ingredient",RankineItems.BRASS_ALLOY.get());
        ladder(consumer,RankineItems.CAST_IRON_LADDER.get(),8,RankineItems.CAST_IRON_ALLOY.get(),"has_ingredient",RankineItems.CAST_IRON_ALLOY.get());
        ladder(consumer,RankineItems.CUPRONICKEL_LADDER.get(),8,RankineItems.CUPRONICKEL_ALLOY.get(),"has_ingredient",RankineItems.CUPRONICKEL_ALLOY.get());
        ladder(consumer,RankineItems.DURALUMIN_LADDER.get(),8,RankineItems.DURALUMIN_ALLOY.get(),"has_ingredient",RankineItems.DURALUMIN_ALLOY.get());

        
        //Campfire
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(RankineItems.FIRE_CLAY_BALL.get()), RankineItems.REFRACTORY_BRICK.get(), 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ingredient", hasItem(RankineItems.FIRE_CLAY_BALL.get())).build(consumer, "cooked_beef_from_" + "campfire_cooking");


    }


    private static void shapelessPlanksNew(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider planks, ITag<Item> input) {
        ShapelessRecipeBuilder.shapelessRecipe(planks, 4).addIngredient(input).setGroup("planks").addCriterion("has_log", hasItem(input)).build(recipeConsumer);
    }



    private void halfSuite(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        slab(consumer, output, input, group, triggerName, trigger);
        verticalSlab(consumer, output, input, group, triggerName, trigger);
        stairs(consumer, output, input, group, triggerName, trigger);
        wall(consumer, output, input, group, triggerName, trigger);
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

    //with group
    private void slab(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void verticalSlab(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("#")
                .patternLine("#")
                .patternLine("#")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void button(Consumer<IFinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapelessRecipe(output, 1)
                .addIngredient(input)
                .setGroup(group)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
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

    //no group
    private void slab(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void verticalSlab(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("#")
                .patternLine("#")
                .patternLine("#")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void stairs(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 4)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void wall(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 6)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void pressurePlate(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("##")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void button(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapelessRecipe(output, 1)
                .addIngredient(input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void door(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 3)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void trapdoor(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 2)
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void boat(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("# #")
                .patternLine("###")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void fence(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 3)
                .patternLine("#S#")
                .patternLine("#S#")
                .key('#', input)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }
    private void fenceGate(Consumer<IFinishedRecipe> consumer, Item output, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, 1)
                .patternLine("S#S")
                .patternLine("S#S")
                .key('#', input)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
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

    private void ladder(Consumer<IFinishedRecipe> consumer, Item output, int count, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shapedRecipe(output, count)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("# #")
                .key('#', input)
                .addCriterion(triggerName, InventoryChangeTrigger.Instance.forItems(trigger))
                .build(consumer);
    }

}