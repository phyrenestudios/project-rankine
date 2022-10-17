package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.blocks.RankineStone;
import com.cannolicatfish.rankine.data.builders.AlloyCraftingRecipeBuilder;
import com.cannolicatfish.rankine.data.builders.AlloyIngredient;
import com.cannolicatfish.rankine.data.builders.BeehiveOvenRecipeBuilder;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.JamRecipe;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineRecipesProvider extends RecipeProvider {

    private static final Map<Item,Item> blastingMap = new HashMap<>();
    static {
        blastingMap.put(RankineItems.CASSITERITE.get(),RankineItems.TIN_INGOT.get());
        blastingMap.put(RankineItems.SPHALERITE.get(),RankineItems.ZINC_INGOT.get());
        blastingMap.put(RankineItems.PENTLANDITE.get(),RankineItems.NICKEL_INGOT.get());
        blastingMap.put(RankineItems.MAGNETITE.get(),Items.IRON_INGOT);
        blastingMap.put(RankineItems.HEMATITE.get(),Items.IRON_INGOT);
        blastingMap.put(RankineItems.GALENA.get(),RankineItems.LEAD_INGOT.get());
        blastingMap.put(RankineItems.BISMUTHINITE.get(),RankineItems.BISMUTH_INGOT.get());
        blastingMap.put(RankineItems.ACANTHITE.get(),RankineItems.SILVER_INGOT.get());
        blastingMap.put(RankineItems.PYROLUSITE.get(),RankineItems.MANGANESE_INGOT.get());
        blastingMap.put(RankineItems.CHROMITE.get(),RankineItems.CHROMIUM_INGOT.get());
        blastingMap.put(RankineItems.MOLYBDENITE.get(),RankineItems.MOLYBDENUM_INGOT.get());
        blastingMap.put(RankineItems.GREENOCKITE.get(),RankineItems.CADMIUM_INGOT.get());
        blastingMap.put(RankineItems.URANINITE.get(),RankineItems.URANIUM_INGOT.get());
        blastingMap.put(RankineItems.STIBNITE.get(),RankineItems.ANTIMONY.get());
        blastingMap.put(RankineItems.XENOTIME.get(),RankineItems.YTTRIUM_INGOT.get());
        blastingMap.put(RankineItems.CELESTINE.get(),RankineItems.STRONTIUM_INGOT.get());
        blastingMap.put(RankineItems.CHALCOPYRITE.get(),Items.COPPER_INGOT);
        blastingMap.put(RankineItems.PLATINUM_ARSENIDE.get(),RankineItems.PLATINUM_INGOT.get());
        blastingMap.put(RankineItems.RHENIITE.get(),RankineItems.RHENIUM_INGOT.get());
        blastingMap.put(RankineItems.THORITE.get(),RankineItems.THORIUM_INGOT.get());
        blastingMap.put(RankineItems.GOETHITE.get(),Items.IRON_INGOT);
        blastingMap.put(RankineItems.REALGAR.get(),RankineItems.ARSENIC.get());
        blastingMap.put(RankineItems.COOPERITE.get(),RankineItems.PALLADIUM_INGOT.get());
        blastingMap.put(RankineItems.HEDENBERGITE.get(),Items.IRON_INGOT);
        blastingMap.put(RankineItems.LAURITE.get(),RankineItems.RUTHENIUM_INGOT.get());
        blastingMap.put(RankineItems.RUTILE.get(),RankineItems.TITANIUM_INGOT.get());
        blastingMap.put(RankineItems.SPODUMENE.get(),RankineItems.LITHIUM_INGOT.get());
        blastingMap.put(RankineItems.CHALCOCITE.get(),Items.COPPER_INGOT);
        blastingMap.put(RankineItems.LORANDITE.get(),RankineItems.THALLIUM_INGOT.get());
        blastingMap.put(RankineItems.RHODOCHROSITE.get(),RankineItems.MANGANESE_INGOT.get());
        blastingMap.put(RankineItems.MAGNESITE.get(),RankineItems.MAGNESIA.get());

        blastingMap.put(RankineItems.SILVER_ZINC_BATTERY.get(),RankineItems.ZINC_NUGGET.get());
        blastingMap.put(RankineItems.MAGNESIUM_BATTERY.get(),RankineItems.ZINC_NUGGET.get());
        blastingMap.put(RankineItems.LEAD_ACID_BATTERY.get(),RankineItems.LEAD_NUGGET.get());
        blastingMap.put(RankineItems.VANADIUM_REDOX_BATTERY.get(),RankineItems.VANADIUM_NUGGET.get());
        blastingMap.put(RankineItems.ZINC_BROMINE_BATTERY.get(),RankineItems.ZINC_NUGGET.get());
        blastingMap.put(RankineItems.SODIUM_SULFUR_BATTERY.get(),RankineItems.SULFUR_NUGGET.get());
        blastingMap.put(RankineItems.LITHIUM_ION_BATTERY.get(),RankineItems.COBALT_NUGGET.get());

        blastingMap.put(RankineItems.STRONTIUM_RTG.get(),RankineItems.YTTRIUM_NUGGET.get());
        blastingMap.put(RankineItems.POLONIUM_RTG.get(),RankineItems.LEAD_NUGGET.get());
        blastingMap.put(RankineItems.PLUTONIUM_RTG.get(),RankineItems.URANIUM_NUGGET.get());
        blastingMap.put(RankineItems.AMERICIUM_RTG.get(),RankineItems.NEPTUNIUM_NUGGET.get());
        blastingMap.put(RankineItems.CURIUM_RTG.get(),RankineItems.PLUTONIUM_NUGGET.get());

    }

    public static final Map<Ingredient, Block> BEEHIVE_OVEN_MINERAL_MAP = new HashMap<>();
    static {
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.ACANTHITE_BLOCK.get()), RankineBlocks.SILVER_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.AZURMALACHITE_BLOCK.get()), Blocks.COPPER_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.BADDELEYITE_BLOCK.get()), RankineBlocks.ZIRCONIA_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.BISMUTHINITE_BLOCK.get()), RankineBlocks.BISMUTH_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.BITUMINOUS_COAL_BLOCK.get()), RankineBlocks.COKE_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.CASSITERITE_BLOCK.get()), RankineBlocks.TIN_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.CELESTINE_BLOCK.get()), RankineBlocks.STRONTIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.CHALCOPYRITE_BLOCK.get()), Blocks.COPPER_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.CHROMITE_BLOCK.get()), RankineBlocks.CHROMIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(Blocks.COAL_BLOCK), RankineBlocks.BITUMINOUS_COAL_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.COBALTITE_BLOCK.get()), RankineBlocks.COBALT_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.COOPERITE_BLOCK.get()), RankineBlocks.PALLADIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.GALENA_BLOCK.get()), RankineBlocks.LEAD_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.GREENOCKITE_BLOCK.get()), RankineBlocks.CADMIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.GOETHITE_BLOCK.get()), Blocks.IRON_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.HEDENBERGITE_BLOCK.get()), Blocks.IRON_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.HEMATITE_BLOCK.get()), Blocks.IRON_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.LAURITE_BLOCK.get()), RankineBlocks.RUTHENIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.LORANDITE_BLOCK.get()), RankineBlocks.THALLIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.MAGNETITE_BLOCK.get()), Blocks.IRON_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.MOLYBDENITE_BLOCK.get()), RankineBlocks.MOLYBDENUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.PENTLANDITE_BLOCK.get()), RankineBlocks.NICKEL_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.PLATINUM_ARSENIDE_BLOCK.get()), RankineBlocks.PLATINUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.PYROLUSITE_BLOCK.get()), RankineBlocks.MANGANESE_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.REALGAR_BLOCK.get()), RankineBlocks.ARSENIC_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.RHODOCHROSITE_BLOCK.get()), RankineBlocks.MANGANESE_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.RUTILE_BLOCK.get()), RankineBlocks.TITANIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SCHEELITE_BLOCK.get()), RankineBlocks.TUNGSTEN_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SILICON_BLOCK.get()), RankineBlocks.SILICON_CARBIDE_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SILICON_CARBIDE_BLOCK.get()), RankineBlocks.GRAPHITE_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SPHALERITE_BLOCK.get()), RankineBlocks.ZINC_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SPODUMENE_BLOCK.get()), RankineBlocks.LITHIUM_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.STIBNITE_BLOCK.get()), RankineBlocks.ANTIMONY_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.SUBBITUMINOUS_COAL_BLOCK.get()), RankineBlocks.BITUMINOUS_COAL_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(Blocks.RAW_COPPER_BLOCK), Blocks.COPPER_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(Blocks.RAW_GOLD_BLOCK), Blocks.GOLD_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(Blocks.RAW_IRON_BLOCK), Blocks.IRON_BLOCK);
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.ZIRCON_BLOCK.get()), RankineBlocks.ZIRCONIA_BLOCK.get());
        BEEHIVE_OVEN_MINERAL_MAP.put(Ingredient.of(RankineBlocks.MAGNESIA_BLOCK.get()), RankineBlocks.MAGNESIA_BLOCK.get());
    }

    public static final Map<Ingredient, Block> BEEHIVE_OVEN_OTHER_MAP = new HashMap<>();
    static {
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.BONE_BLOCK), RankineBlocks.BONE_CHAR_BLOCK.get());

        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.CLAY), Blocks.TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(RankineBlocks.FIRE_CLAY.get()), Blocks.TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(RankineBlocks.PORCELAIN_CLAY.get()), RankineBlocks.PORCELAIN.get());
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
        BEEHIVE_OVEN_OTHER_MAP.put(Ingredient.of(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA);
    }
    
    public RankineRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Project Rankine - Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //MINECRAFT OVERRIDE
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.LOGS_THAT_BURN), RankineItems.ASH.get(), 0.1F, 200).unlockedBy("has_logs", has(ItemTags.LOGS_THAT_BURN)).save(consumer, "minecraft:charcoal");
        ShapedRecipeBuilder.shaped(Blocks.BRICKS).define('#', Items.BRICK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_brick", has(Items.BRICK)).save(consumer);
        ShapedRecipeBuilder.shaped(Blocks.END_STONE_BRICKS, 2).define('#', Blocks.END_STONE).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_end_stone", has(Blocks.END_STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(Blocks.QUARTZ_BRICKS, 2).define('#', Blocks.QUARTZ_BLOCK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(Blocks.STONE_BRICKS, 2).define('#', Blocks.STONE).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_stone", has(Blocks.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(Blocks.NETHER_BRICKS).define('#', Items.NETHER_BRICK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_netherbrick", has(Items.NETHER_BRICK)).save(consumer);
        ShapelessRecipeBuilder.shapeless(Blocks.RED_NETHER_BRICKS).requires(Blocks.NETHER_BRICKS).requires(Items.NETHER_WART).unlockedBy("has_nether_wart", has(Items.NETHER_WART)).save(consumer);
        ShapedRecipeBuilder.shaped(Blocks.PRISMARINE_BRICKS).define('#', Items.PRISMARINE_SHARD).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(consumer);
        ShapelessRecipeBuilder.shapeless(Blocks.DARK_PRISMARINE).requires(Blocks.PRISMARINE_BRICKS).requires(Tags.Items.DYES_BLACK).unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(consumer);


        //ALTERNATIVE RECIPES
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.SILT.get()), Items.GLASS, 0.1F, 200).unlockedBy("has_ingredient", has(RankineBlocks.SILT.get().asItem())).save(consumer, "rankine:glass_from_silt_smelting");

        ShapedRecipeBuilder.shaped(Blocks.TORCH, 3).pattern("C").pattern("S").define('C', RankineItems.LIGNITE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.LIGNITE.get())).group("torch").save(consumer, "rankine:torch_from_lignite");
        ShapedRecipeBuilder.shaped(Blocks.TORCH, 4).pattern("C").pattern("S").define('C', RankineItems.SUBBITUMINOUS_COAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.SUBBITUMINOUS_COAL.get())).group("torch").save(consumer, "rankine:torch_from_subbituminous_coal");
        ShapedRecipeBuilder.shaped(Blocks.TORCH, 6).pattern("C").pattern("S").define('C', RankineItems.BITUMINOUS_COAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.BITUMINOUS_COAL.get())).group("torch").save(consumer, "rankine:torch_from_bituminous_coal");
        ShapedRecipeBuilder.shaped(Blocks.TORCH, 8).pattern("C").pattern("S").define('C', RankineItems.ANTHRACITE_COAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.ANTHRACITE_COAL.get())).group("torch").save(consumer, "rankine:torch_from_anthracite_coal");
        ShapedRecipeBuilder.shaped(Blocks.TORCH, 8).pattern("C").pattern("S").define('C', RankineTags.Items.COKE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.COKE)).group("torch").save(consumer, "rankine:torch_from_coke");
        ShapedRecipeBuilder.shaped(Blocks.SOUL_TORCH, 2).pattern("C").pattern("S").define('C', RankineTags.Items.SULFUR).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).group("torch").save(consumer, "rankine:torch_from_sulfur");

        ShapedRecipeBuilder.shaped(Items.BUCKET, 2).pattern("I I").pattern(" I ").define('I', RankineItems.STEEL_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.STEEL_INGOT.get())).save(consumer, "rankine:bucket_from_steel");
        ShapedRecipeBuilder.shaped(Items.BUCKET, 1).pattern("I I").pattern(" I ").define('I', RankineItems.BRASS_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.BRASS_INGOT.get())).save(consumer, "rankine:bucket_from_brass");
        ShapedRecipeBuilder.shaped(Items.BUCKET, 1).pattern("I I").pattern(" I ").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:bucket_from_crafting_metals");
        ShapedRecipeBuilder.shaped(Items.DIAMOND_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.GEMS_DIAMOND).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:diamond_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shaped(Items.GOLDEN_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.INGOTS_GOLD).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:gold_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shaped(Items.IRON_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.INGOTS_IRON).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:iron_horse_armor_from_saddle_tree");

        ShapelessRecipeBuilder.shapeless(Items.GUNPOWDER,2).requires(Items.CHARCOAL).requires(RankineTags.Items.SULFUR).requires(Items.BONE_MEAL).unlockedBy("has_ingredient", has(RankineItems.SULFUR.get())).save(consumer, "rankine:gunpowder_from_bonemeal");
        ShapelessRecipeBuilder.shapeless(Items.GUNPOWDER,2).requires(Items.CHARCOAL).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.SALTPETER).requires(RankineTags.Items.SALTPETER).unlockedBy("has_ingredient", has(RankineItems.SULFUR.get())).save(consumer, "rankine:gunpowder_from_saltpeter");

        ShapelessRecipeBuilder.shapeless(Items.COAL,2).requires(RankineItems.ANTHRACITE_COAL.get()).unlockedBy("has_ingredient", has(RankineItems.ANTHRACITE_COAL.get())).group("coal").save(consumer, "rankine:coal_from_anthracite");
        ShapelessRecipeBuilder.shapeless(Items.COAL,3).requires(RankineItems.BITUMINOUS_COAL.get()).requires(RankineItems.BITUMINOUS_COAL.get()).unlockedBy("has_ingredient", has(RankineItems.BITUMINOUS_COAL.get())).group("coal").save(consumer, "rankine:coal_from_bituminous");
        ShapelessRecipeBuilder.shapeless(Items.COAL,1).requires(RankineItems.SUBBITUMINOUS_COAL.get()).unlockedBy("has_ingredient", has(RankineItems.SUBBITUMINOUS_COAL.get())).group("coal").save(consumer, "rankine:coal_from_subbituminous");
        ShapelessRecipeBuilder.shapeless(Items.COAL,2).requires(RankineItems.LIGNITE.get()).requires(RankineItems.LIGNITE.get()).requires(RankineItems.LIGNITE.get()).unlockedBy("has_ingredient", has(RankineItems.LIGNITE.get())).group("coal").save(consumer, "rankine:coal_from_lignite");

        ShapedRecipeBuilder.shaped(Items.CAKE, 1).pattern("MMM").pattern("SES").pattern("GGG").define('M',Items.MILK_BUCKET).define('S', Items.SUGAR).define('E', Tags.Items.EGGS).define('G', RankineTags.Items.GRAIN).unlockedBy("has_ingredient", has(RankineTags.Items.GRAIN)).save(consumer, "rankine:cake_from_grains");
        ShapedRecipeBuilder.shaped(Items.EXPERIENCE_BOTTLE, 1).pattern(" C ").pattern("IMI").pattern(" I ").define('C',RankineItems.CORK.get()).define('I', RankineTags.Items.HARDENED_GLASS).define('M', RankineTags.Items.INGOTS_MISCHMETAL).unlockedBy("has_ingredient", has(RankineItems.CORK.get())).save(consumer, "rankine:xpbottle_from_mischmetal");
        ShapedRecipeBuilder.shaped(Items.GLASS_BOTTLE, 8).pattern(" C ").pattern("I I").pattern(" I ").define('C',RankineItems.CORK.get()).define('I', Tags.Items.GLASS).unlockedBy("has_ingredient", has(RankineItems.CORK.get())).save(consumer, "rankine:glass_bottle_from_cork");
        ShapedRecipeBuilder.shaped(Items.BELL, 1).pattern("SRS").pattern("SBS").pattern("S S").define('S', Tags.Items.STONE).define('R', Tags.Items.RODS_WOODEN).define('B', RankineItems.BRASS_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.BRASS_INGOT.get())).save(consumer, "rankine:bell_from_brass");
        ShapedRecipeBuilder.shaped(Items.LODESTONE, 1).pattern("SSS").pattern("SLS").pattern("SSS").define('S', Items.CHISELED_STONE_BRICKS).define('L', RankineItems.LODESTONE.get()).unlockedBy("has_ingredient", has(RankineItems.LODESTONE.get())).save(consumer, "rankine:lodestone_from_lodestone");
        ShapedRecipeBuilder.shaped(Items.BLAST_FURNACE, 1).pattern("SBS").pattern("SFS").pattern("BBB").define('S',RankineTags.Items.SHEETMETAL).define('F', Items.FURNACE).define('B', ItemTags.STONE_BRICKS).unlockedBy("has_ingredient", has(RankineTags.Items.SHEETMETAL)).save(consumer, "rankine:blast_furnace_from_sheetmetal");
        ShapedRecipeBuilder.shaped(Items.SADDLE, 1).pattern("LLL").pattern("LSL").define('S', RankineItems.SADDLE_TREE.get()).define('L', Tags.Items.LEATHER).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:saddle_from_saddle_tree");
        //ShapedRecipeBuilder.shapedRecipe(Items.SCAFFOLDING, 1).patternLine("RRR").patternLine("SLS").key('S', RankineItems.SADDLE_TREE.get()).key('L', Tags.Items.LEATHER).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:saddle_from_saddle_tree");
        ShapedRecipeBuilder.shaped(Items.STRING, 1).pattern("SSS").define('S', RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineTags.Items.CROPS_COTTON)).save(consumer, "rankine:string_from_cotton");

        ShapelessRecipeBuilder.shapeless(Items.LEATHER,1).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).unlockedBy("has_ingredient", has(RankineItems.SYNTHETIC_LEATHER.get())).save(consumer, "rankine:leather_from_synthetic_leather");
        ShapelessRecipeBuilder.shapeless(Items.LEATHER,1).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).unlockedBy("has_ingredient", has(RankineItems.BEAVER_PELT.get())).save(consumer, "rankine:leather_from_beaver_pelt");

        ShapelessRecipeBuilder.shapeless(Items.MUSHROOM_STEW,1).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(Items.BOWL).unlockedBy("has_ingredient", has(RankineTags.Items.EDIBLE_MUSHROOMS)).save(consumer, "rankine:mushroom_stew_from_edible_mushrooms");
        ShapelessRecipeBuilder.shapeless(Items.RABBIT_STEW,1).requires(Items.BAKED_POTATO).requires(Items.COOKED_RABBIT).requires(Items.BOWL).requires(Items.CARROT).requires(RankineTags.Items.EDIBLE_MUSHROOMS).unlockedBy("has_ingredient", has(Items.COOKED_RABBIT)).save(consumer, "rankine:rabbit_stew_from_edible_mushrooms");


        ShapelessRecipeBuilder.shapeless(Items.SUGAR,2).requires(RankineItems.BONE_CHAR.get()).requires(Items.SUGAR_CANE).unlockedBy("has_ingredient", has(RankineItems.BONE_CHAR.get())).save(consumer, "rankine:sugar_from_bone_char");
        ShapelessRecipeBuilder.shapeless(Items.SUGAR,2).requires(RankineItems.MAPLE_SYRUP.get()).unlockedBy("has_ingredient", has(RankineItems.MAPLE_SYRUP.get())).save(consumer, "rankine:sugar_from_maple_syrup");
        ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL,4).requires(Items.WATER_BUCKET).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineTags.Items.SULFUR).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).save(consumer, "rankine:bonemeal_from_ash");
        ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL,12).requires(Items.WATER_BUCKET).requires(RankineItems.BONE_ASH.get()).requires(RankineItems.BONE_ASH.get()).requires(RankineTags.Items.SULFUR).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).save(consumer, "rankine:bonemeal_from_bone_ash");

        //ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 1).patternLine("SS ").patternLine("SB ").patternLine("  S").key('S', Tags.Items.STRING).key('L', RankineItems.LODESTONE.get()).addCriterion("has_ingredient", hasItem(RankineItems.LODESTONE.get())).build(consumer, "rankine:lodestone_from_lodestone");

        ShapedRecipeBuilder.shaped(Items.CHAIN, 1).pattern("N").pattern("I").pattern("N").define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:chain_from_metals");
        ShapedRecipeBuilder.shaped(Items.HOPPER, 1).pattern("I I").pattern("ICI").pattern(" I ").define('C', Tags.Items.CHESTS).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:hopper_from_metals");
        ShapedRecipeBuilder.shaped(Items.RAIL, 16).pattern("I I").pattern("ISI").pattern("I I").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:rail_from_metals");
        ShapedRecipeBuilder.shaped(Items.POWERED_RAIL, 6).pattern("I I").pattern("ISI").pattern("IRI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.COLORED_GOLD_INGOTS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.COLORED_GOLD_INGOTS)).save(consumer, "rankine:powered_rail_from_golds");
        ShapedRecipeBuilder.shaped(Items.POWERED_RAIL, 8).pattern("I I").pattern("ISI").pattern("IRI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.RODS_GRAPHITE).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.COLORED_GOLD_INGOTS)).save(consumer, "rankine:powered_rail_from_graphite");
        ShapedRecipeBuilder.shaped(Items.DETECTOR_RAIL, 6).pattern("I I").pattern("IRI").pattern("IPI").define('P', RankineTags.Items.STONE_PRESSURE_PLATES).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:detector_rail_from_metals");
        ShapedRecipeBuilder.shaped(Items.ACTIVATOR_RAIL, 6).pattern("ISI").pattern("ITI").pattern("ISI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('T', Items.REDSTONE_TORCH).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:activator_rail_from_metals");
        ShapedRecipeBuilder.shaped(Items.MINECART, 1).pattern("I I").pattern("III").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:minecart_from_metals");
        ShapedRecipeBuilder.shaped(Items.SHEARS, 1).pattern("I ").pattern(" I").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:shears_from_metals");
        ShapedRecipeBuilder.shaped(Items.NAME_TAG, 1).pattern(" I ").pattern("I I").pattern("CI ").define('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).define('C',RankineItems.CORK.get()).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STAINLESS_STEEL)).save(consumer, "rankine:nametag_from_stainless_steel");
        ShapedRecipeBuilder.shaped(Items.DAYLIGHT_DETECTOR, 6).pattern("C").pattern("S").define('S', ItemTags.WOODEN_SLABS).define('C',RankineItems.CADMIUM_TELLURIDE_CELL.get()).unlockedBy("has_ingredient", has(RankineItems.CADMIUM_TELLURIDE_CELL.get())).save(consumer, "rankine:daylight_detector_from_cell");



        //RANKINE
        for (Block DRIP : RankineLists.DRIPSTONES) {
            Block POINT = RankineLists.POINTED_DRIPSTONES.get(RankineLists.DRIPSTONES.indexOf(DRIP));
            twoXtwo(consumer,DRIP.asItem(),POINT.asItem(),1,"has_ingredient",POINT.asItem());
        }
        for (Block SANDSTONE : RankineLists.SANDSTONES) {
            String baseName = SANDSTONE.getRegistryName().getPath();
            Block CHISELED = RankineLists.CHISELED_SANDSTONES.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block SAND = RankineLists.SANDS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block SLAB = RankineLists.SANDSTONE_SLABS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block STAIRS = RankineLists.SANDSTONE_STAIRS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            Block WALL = RankineLists.SANDSTONE_WALLS.get(RankineLists.SANDSTONES.indexOf(SANDSTONE));
            ShapedRecipeBuilder.shaped(SANDSTONE, 1).pattern("SS").pattern("SS").define('S', SAND).unlockedBy("has_ingredient", has(SAND)).save(consumer);
            ShapedRecipeBuilder.shaped(CHISELED, 1).pattern("S").pattern("S").define('S', SLAB).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer);
            slab(consumer,SLAB.asItem(),SANDSTONE.asItem(), "sandstone_slab");
            stairs(consumer,STAIRS.asItem(),SANDSTONE.asItem(), "sandstone_stairs");
            wall(consumer,WALL.asItem(),SANDSTONE.asItem(), "sandstone_wall");
            ShapelessRecipeBuilder.shapeless(SANDSTONE).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(SANDSTONE,6).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SANDSTONE), SLAB, 2).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SANDSTONE), STAIRS).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SANDSTONE), WALL).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

        }
        for (Block SMOOTH_SANDSTONE : RankineLists.SMOOTH_SANDSTONES) {
            String baseName = SMOOTH_SANDSTONE.getRegistryName().getPath();
            Block SANDSTONE = RankineLists.SANDSTONES.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block SLAB = RankineLists.SMOOTH_SANDSTONE_SLABS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block STAIRS = RankineLists.SMOOTH_SANDSTONE_STAIRS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            Block WALL = RankineLists.SMOOTH_SANDSTONE_WALLS.get(RankineLists.SMOOTH_SANDSTONES.indexOf(SMOOTH_SANDSTONE));
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(SANDSTONE), SMOOTH_SANDSTONE, 0.1F, 200).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer);
            slab(consumer,SLAB.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_slab");
            stairs(consumer,STAIRS.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_stairs");
            wall(consumer,WALL.asItem(),SMOOTH_SANDSTONE.asItem(), "smooth_sandstone_wall");
            ShapelessRecipeBuilder.shapeless(SMOOTH_SANDSTONE).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(SMOOTH_SANDSTONE,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SMOOTH_SANDSTONE), SLAB, 2).unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SMOOTH_SANDSTONE), STAIRS).unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(SMOOTH_SANDSTONE), WALL).unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

        }
        for (Block CUT_SANDSTONE : RankineLists.CUT_SANDSTONES) {
            String baseName = CUT_SANDSTONE.getRegistryName().getPath();
            Block SANDSTONE = RankineLists.SANDSTONES.get(RankineLists.CUT_SANDSTONES.indexOf(CUT_SANDSTONE));
            Block SLAB = RankineLists.CUT_SANDSTONE_SLABS.get(RankineLists.CUT_SANDSTONES.indexOf(CUT_SANDSTONE));
            ShapedRecipeBuilder.shaped(CUT_SANDSTONE, 1).pattern("SS").pattern("SS").define('S', SANDSTONE).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer);
            slab(consumer,SLAB.asItem(),CUT_SANDSTONE.asItem(), "cut_sandstone_slab");
            ShapelessRecipeBuilder.shapeless(CUT_SANDSTONE).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(CUT_SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_slab");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(CUT_SANDSTONE), SLAB, 2).unlockedBy("has_ingredient", has(CUT_SANDSTONE)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
        }
        for (Block BRICK : RankineLists.BRICKS) {
            String baseName = BRICK.getRegistryName().getPath();
            Block SLAB = RankineLists.BRICKS_SLAB.get(RankineLists.BRICKS.indexOf(BRICK));
            Block STAIRS = RankineLists.BRICKS_STAIRS.get(RankineLists.BRICKS.indexOf(BRICK));
            Block WALL = RankineLists.BRICKS_WALL.get(RankineLists.BRICKS.indexOf(BRICK));
            slab(consumer,SLAB.asItem(),BRICK.asItem(), "bricks_slab");
            stairs(consumer,STAIRS.asItem(),BRICK.asItem(), "bricks_stairs");
            wall(consumer,WALL.asItem(),BRICK.asItem(), "bricks_wall");
            ShapelessRecipeBuilder.shapeless(BRICK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(BRICK)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(BRICK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(BRICK)).save(consumer,"rankine:"+baseName+"_from_stairs");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BRICK), SLAB, 2).unlockedBy("has_ingredient", has(BRICK)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BRICK), STAIRS).unlockedBy("has_ingredient", has(BRICK)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BRICK), WALL).unlockedBy("has_ingredient", has(BRICK)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

        }
        for (Block BLOCK : RankineLists.MISC_BLOCKS) {
            String baseName = BLOCK.getRegistryName().getPath();
            Block SLAB = RankineLists.MISC_SLABS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block STAIRS = RankineLists.MISC_STAIRS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block WALL = RankineLists.MISC_WALLS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            slab(consumer,SLAB.asItem(),BLOCK.asItem());
            stairs(consumer,STAIRS.asItem(),BLOCK.asItem());
            wall(consumer,WALL.asItem(),BLOCK.asItem());
            ShapelessRecipeBuilder.shapeless(BLOCK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(BLOCK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_stairs");

            if (Arrays.asList(RankineBlocks.SKARN.get(),RankineBlocks.BRECCIA.get()).contains(BLOCK)) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), SLAB, 2).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), STAIRS).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), WALL).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

            }
        }


        ShapedRecipeBuilder.shaped(RankineItems.POLISHED_ROMAN_CONCRETE.get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', RankineItems.ROMAN_CONCRETE.get())
                .group("polished_stone")
                .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.ROMAN_CONCRETE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RankineItems.ROMAN_CONCRETE_BRICKS.get(), 2)
                .pattern("#M")
                .pattern("M#")
                .define('#', RankineItems.ROMAN_CONCRETE.get())
                .define('M', RankineItems.MORTAR.get())
                .group("stone_bricks")
                .unlockedBy("has_mortar", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.MORTAR.get()))
                .save(consumer);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RankineItems.ROMAN_CONCRETE.get()), RankineItems.POLISHED_ROMAN_CONCRETE.get()).unlockedBy("has_ingredient", has(RankineItems.ROMAN_CONCRETE.get())).save(consumer, "rankine:polished_roman_concrete_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RankineItems.ROMAN_CONCRETE.get()), RankineItems.ROMAN_CONCRETE_BRICKS.get()).unlockedBy("has_ingredient", has(RankineItems.ROMAN_CONCRETE.get())).save(consumer, "rankine:roman_concrete_bricks_stonecutting");


        for (Block BLOCK : RankineLists.CONCRETE_BLOCKS) {
            String baseName = BLOCK.getRegistryName().getPath();
            Block SLAB = RankineLists.QUARTER_SLABS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            Block STAIRS = RankineLists.CONCRETE_STAIRS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            Block WALL = RankineLists.CONCRETE_WALLS.get(RankineLists.CONCRETE_BLOCKS.indexOf(BLOCK));
            slab(consumer,SLAB.asItem(),BLOCK.asItem());
            stairs(consumer,STAIRS.asItem(),BLOCK.asItem());
            wall(consumer,WALL.asItem(),BLOCK.asItem());
            ShapelessRecipeBuilder.shapeless(BLOCK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(BLOCK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_stairs");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), SLAB, 2).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), STAIRS).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), WALL).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

        }








        ShapedRecipeBuilder.shaped(RankineItems.DIVING_BOOTS.get(), 1).pattern("R R").pattern("I I").pattern("I I").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.DIVING_CHESTPLATE.get(), 1).pattern("IRI").pattern("III").pattern("III").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.DIVING_LEGGINGS.get(), 1).pattern("IRI").pattern("I I").pattern("I I").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.DIVING_HELMET.get(), 1).pattern("III").pattern("IGI").pattern("RRR").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).define('G', Tags.Items.GLASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);


        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),4).requires(RankineItems.JUTE.get()).unlockedBy("has_ingredient", has(RankineItems.JUTE.get())).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_jute");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),2).requires(Items.VINE).unlockedBy("has_ingredient", has(Items.VINE)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_vine");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),2).requires(Items.WEEPING_VINES).unlockedBy("has_ingredient", has(Items.WEEPING_VINES)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_weeping_vines");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),2).requires(Items.TWISTING_VINES).unlockedBy("has_ingredient", has(Items.TWISTING_VINES)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_twisting_vines");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),2).requires(Items.TALL_GRASS).unlockedBy("has_ingredient", has(Items.TALL_GRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_tall_grass");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),2).requires(Items.LARGE_FERN).unlockedBy("has_ingredient", has(Items.LARGE_FERN)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_large_fern");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),1).requires(RankineItems.WILLOW_BRANCHLET.get()).unlockedBy("has_ingredient", has(RankineItems.WILLOW_BRANCHLET.get())).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_willow");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),1).requires(Items.GRASS).unlockedBy("has_ingredient", has(Items.GRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_grass");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),1).requires(Items.SEAGRASS).unlockedBy("has_ingredient", has(Items.SEAGRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_seagrass");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),1).requires(Items.FERN).unlockedBy("has_ingredient", has(Items.FERN)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_fern");
        ShapelessRecipeBuilder.shapeless(RankineItems.PLANT_FIBER.get(),1).requires(RankineItems.SHORT_GRASS.get()).requires(RankineItems.SHORT_GRASS.get()).unlockedBy("has_ingredient", has(Items.STICK)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_short_grass");

        ShapedRecipeBuilder.shaped(Items.BREAD,  1).pattern("###").define('#', RankineItems.MILLET.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.MILLET.get())).save(consumer,"rankine:gf_bread_from_millet");
        ShapedRecipeBuilder.shaped(Items.BREAD,  1).pattern("###").define('#', RankineItems.OATS.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.OATS.get())).save(consumer,"rankine:gf_bread_from_oats");
        ShapedRecipeBuilder.shaped(Items.BREAD,  1).pattern("###").define('#', RankineItems.SORGHUM.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.SORGHUM.get())).save(consumer,"rankine:gf_bread_from_sorghum");
        ShapedRecipeBuilder.shaped(Items.BREAD,  1).pattern("###").define('#', RankineItems.RICE.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.RICE.get())).save(consumer,"rankine:gf_bread_from_rice");
        ShapedRecipeBuilder.shaped(Items.BREAD, 1).pattern("###").define('#', RankineItems.RYE.get()).group("bread").unlockedBy("has_ingredient", has(RankineItems.RYE.get())).save(consumer,"rankine:bread_from_rye");
        ShapedRecipeBuilder.shaped(Items.BREAD, 1).pattern("###").define('#', RankineItems.BARLEY.get()).group("bread").unlockedBy("has_ingredient", has(RankineItems.BARLEY.get())).save(consumer,"rankine:bread_from_barley");

        //ore conversion recipes
        ShapelessRecipeBuilder.shapeless(Items.COAL_ORE,1).requires(RankineItems.COAL_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.COAL_ORE.get())).group("ore_conversion").save(consumer, "rankine:coal_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.IRON_ORE,1).requires(RankineItems.IRON_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.IRON_ORE.get())).group("ore_conversion").save(consumer, "rankine:iron_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.COPPER_ORE,1).requires(RankineItems.COPPER_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.COPPER_ORE.get())).group("ore_conversion").save(consumer, "rankine:copper_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.GOLD_ORE,1).requires(RankineItems.GOLD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.GOLD_ORE.get())).group("ore_conversion").save(consumer, "rankine:gold_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.REDSTONE_ORE,1).requires(RankineItems.REDSTONE_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.REDSTONE_ORE.get())).group("ore_conversion").save(consumer, "rankine:redstone_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.LAPIS_ORE,1).requires(RankineItems.LAPIS_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.LAPIS_ORE.get())).group("ore_conversion").save(consumer, "rankine:lapis_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.DIAMOND_ORE,1).requires(RankineItems.DIAMOND_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.DIAMOND_ORE.get())).group("ore_conversion").save(consumer, "rankine:diamond_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.EMERALD_ORE,1).requires(RankineItems.EMERALD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.EMERALD_ORE.get())).group("ore_conversion").save(consumer, "rankine:emerald_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.NETHER_QUARTZ_ORE,1).requires(RankineItems.NETHER_QUARTZ_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.NETHER_QUARTZ_ORE.get())).group("ore_conversion").save(consumer, "rankine:nether_quartz_ore_conversion");
        ShapelessRecipeBuilder.shapeless(Items.NETHER_GOLD_ORE,1).requires(RankineItems.NETHER_GOLD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.NETHER_GOLD_ORE.get())).group("ore_conversion").save(consumer, "rankine:nether_gold_ore_conversion");


        //pumice soap recipes
        
        ShapelessRecipeBuilder.shapeless(Items.COBBLESTONE,1).requires(Items.MOSSY_COBBLESTONE).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:cobblestone_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(Items.STONE_BRICKS,1).requires(Items.MOSSY_STONE_BRICKS).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:stone_bricks_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(Items.BLAZE_POWDER,1).requires(Items.MAGMA_CREAM).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:blaze_powder_from_puumice_soap");
        ShapelessRecipeBuilder.shapeless(Items.PISTON,1).requires(Items.STICKY_PISTON).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:piston_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(RankineItems.MINERAL_WOOL.get(),1).requires(RankineTags.Items.MINERAL_WOOL).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(RankineTags.Items.MINERAL_WOOL)).save(consumer, "rankine:mineral_wool_from_colors");
        ShapelessRecipeBuilder.shapeless(Items.TERRACOTTA,1).requires(ItemTags.TERRACOTTA).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(ItemTags.TERRACOTTA)).save(consumer, "rankine:terracotta_from_colors");
        ShapelessRecipeBuilder.shapeless(Items.GLASS,1).requires(Tags.Items.GLASS).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(Tags.Items.GLASS)).save(consumer, "rankine:glass_from_colors");


        ShapedRecipeBuilder.shaped(RankineBlocks.SLATE_STEPPING_STONES.get(), 4).pattern("###").define('#', RankineTags.Items.STONES_SLATE).unlockedBy("has_ingredient", has(RankineTags.Items.STONES_SLATE)).save(consumer);

        ShapedRecipeBuilder.shaped(RankineItems.EMERGENCY_FLOTATION_DEVICE.get(), 1).pattern(" # ").pattern("#C#").pattern(" # ").define('C', RankineItems.CARBON_DIOXIDE_GAS_BOTTLE.get()).define('#', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.SOD_BLOCK.get(), 4).pattern("##").pattern("##").define('#', RankineTags.Items.GRASS_BLOCKS).unlockedBy("has_ingredient", has(RankineTags.Items.GRASS_BLOCKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.BLASTING_POWDER.get(), 1).pattern("#R").pattern("R#").define('#', Tags.Items.GUNPOWDER).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(Tags.Items.GUNPOWDER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.CARBON_NUGGET.get(),1).requires(RankineTags.Items.COKE).unlockedBy("has_ingredient", has(RankineTags.Items.COKE)).group("carbon_nugget").save(consumer, "rankine:carbon_from_coke");
        ShapelessRecipeBuilder.shapeless(RankineItems.CARBON_NUGGET.get(),3).requires(RankineTags.Items.GRAPHITE).unlockedBy("has_ingredient", has(RankineTags.Items.GRAPHITE)).group("carbon_nugget").save(consumer, "rankine:carbon_from_graphite");
        ShapedRecipeBuilder.shaped(RankineItems.CARBON_NUGGET.get(), 1).pattern("##").pattern("##").define('#', Items.CHARCOAL).unlockedBy("has_ingredient", has(Items.CHARCOAL)).save(consumer,"rankine:carbon_nugget_from_charcoal");
        ShapedRecipeBuilder.shaped(RankineBlocks.FIRE_CLAY.get(), 1).pattern("##").pattern("##").define('#', RankineItems.FIRE_CLAY_BALL.get()).unlockedBy("has_ingredient", has(RankineItems.KAOLINITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.PORCELAIN_CLAY.get(), 1).pattern("##").pattern("##").define('#', RankineItems.PORCELAIN_CLAY_BALL.get()).unlockedBy("has_ingredient", has(RankineItems.PORCELAIN_CLAY_BALL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.PACKED_SNOW.get(), 2).pattern("B#").pattern("#B").define('#', Items.SNOW_BLOCK).define('B', Items.SNOWBALL).unlockedBy("has_ingredient", has(Items.SNOWBALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.ICE_BRICKS.get(), 2).pattern("B#").pattern("#B").define('#', RankineTags.Items.ICE).define('B', Items.SNOWBALL).unlockedBy("has_ingredient", has(Items.SNOWBALL)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RankineItems.BANDAGE.get(),3).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(Items.PAPER).requires(Items.PAPER).requires(Items.PAPER).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_paper");
        ShapelessRecipeBuilder.shapeless(RankineItems.BANDAGE.get(),6).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_cotton");
        ShapelessRecipeBuilder.shapeless(RankineItems.BANDAGE.get(),12).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(RankineItems.STINGING_NETTLE.get()).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_nettle");
        ShapelessRecipeBuilder.shapeless(RankineItems.GUN_COTTON.get(),8).requires(RankineTags.Items.SALTPETER).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineTags.Items.SALTPETER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.CINNAMON_TOAST.get(),1).requires(RankineItems.TOAST.get()).requires(RankineItems.CINNAMON.get()).requires(Items.SUGAR).unlockedBy("has_ingredient", has(RankineItems.TOAST.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.PINA_COLADA.get(),1).requires(RankineTags.Items.ICE).requires(RankineItems.COCONUT.get()).requires(RankineTags.Items.PINEAPPLE).unlockedBy("has_ingredient", has(RankineItems.PINEAPPLE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.PULP.get(),1).requires(Items.WATER_BUCKET).requires(RankineTags.Items.CLAY_BALLS).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).unlockedBy("has_ingredient", has(RankineTags.Items.SAWDUST)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.PULP.get(),4).requires(Items.WATER_BUCKET).requires(RankineItems.SODIUM_HYDROXIDE.get()).requires(RankineItems.SODIUM_SULFIDE.get()).requires(RankineTags.Items.CLAY_BALLS).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).unlockedBy("has_ingredient", has(RankineTags.Items.SAWDUST)).save(consumer,"rankine:pulp_kraft");
        ShapelessRecipeBuilder.shapeless(RankineItems.TRAIL_MIX.get(),1).requires(RankineTags.Items.BERRIES).requires(RankineItems.ROASTED_WALNUT.get()).requires(RankineItems.TOASTED_COCONUT.get()).unlockedBy("has_ingredient", has(RankineTags.Items.BERRIES)).save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.PAPER,1).requires(RankineItems.ALLOY_TEMPLATE.get()).unlockedBy("has_ingredient", has(RankineItems.ALLOY_TEMPLATE.get())).save(consumer, "rankine:alloy_template_clear");
        ShapelessRecipeBuilder.shapeless(RankineItems.GROUND_TAP.get(),1).requires(RankineItems.METAL_PIPE.get()).unlockedBy("has_ingredient", has(RankineItems.METAL_PIPE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.VULCANIZED_RUBBER.get(),2).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_sulfur");
        ShapelessRecipeBuilder.shapeless(RankineItems.VULCANIZED_RUBBER.get(),2).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineItems.SODIUM_SULFIDE.get()).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_sodium_sulfide");
        ShapelessRecipeBuilder.shapeless(RankineItems.VULCANIZED_RUBBER.get(),4).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineItems.TELLURIUM.get()).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_tellurium");
        ShapelessRecipeBuilder.shapeless(RankineItems.COMPRESSED_BIOMASS.get(),1).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).unlockedBy("has_ingredient", has(RankineItems.BIOMASS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineTags.Items.CARBON).requires(RankineTags.Items.CARBON).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_carbon");
        ShapelessRecipeBuilder.shapeless(RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineTags.Items.SILICON_CARBIDE).requires(RankineTags.Items.SILICON_CARBIDE).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_silicon_carbide");
        ShapelessRecipeBuilder.shapeless(RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineItems.MAGNESIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_magnesia");
        ShapelessRecipeBuilder.shapeless(RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineItems.MAGNESITE.get()).requires(RankineTags.Items.NUGGETS_CHROMIUM).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_magnesite");
        ShapelessRecipeBuilder.shapeless(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.ALUMINA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_alumina");
        ShapelessRecipeBuilder.shapeless(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.HAFNIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_hafnia");
        ShapelessRecipeBuilder.shapeless(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.ZIRCONIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_zirconia");
        ShapedRecipeBuilder.shaped(RankineItems.SYNTHETIC_LEATHER.get(), 1).pattern("PPP").pattern("CSC").pattern("PPP").define('S', RankineTags.Items.SALTPETER).define('C', RankineItems.CAMPHOR_BASIL_LEAF.get()).define('P', RankineItems.PULP.get()).unlockedBy("has_ingredient", has(RankineItems.PULP.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RankineItems.TOFU_CURRY.get(),1).requires(Items.BOWL).requires(RankineItems.TOFU.get()).requires(Items.PUMPKIN).requires(RankineItems.MAPLE_SYRUP.get()).requires(RankineTags.Items.NUTS_COCONUT).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer);


        ShapelessRecipeBuilder.shapeless(RankineItems.MORTAR.get(),4).requires(RankineItems.CEMENT_MIX.get()).requires(ItemTags.SAND).unlockedBy("has_ingredient", has(RankineItems.CEMENT_MIX.get())).group("mortar").save(consumer);

        ShapedRecipeBuilder.shaped(RankineItems.FLINT_KNIFE.get(), 1).pattern(" F").pattern("F ").define('F', Items.FLINT).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FLINT_AXE.get(), 1).pattern("RF").pattern("S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FLINT_PICKAXE.get(), 1).pattern("FRF").pattern(" S ").pattern(" S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FLINT_SPEAR.get(), 1).pattern(" FF").pattern(" RF").pattern("S  ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FLINT_HOE.get(), 1).pattern("RF").pattern("S ").pattern("S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FLINT_SHOVEL.get(), 1).pattern("F").pattern("R").pattern("S").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.WOODEN_HAMMER.get(), 1).pattern("PPP").pattern("PSP").pattern(" S ").define('P', ItemTags.PLANKS).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.STONE_HAMMER.get(), 1).pattern("PPP").pattern("PSP").pattern(" S ").define('P', ItemTags.STONE_TOOL_MATERIALS).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.COBBLESTONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.BUILDING_TOOL.get(), 1).pattern("PRP").pattern("PRP").pattern(" S ").define('P', Tags.Items.STONE).define('S', Tags.Items.RODS_WOODEN).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(Tags.Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.ROPE_COIL_ARROW.get(), 1).pattern("T").pattern("S").pattern("F").define('F', Tags.Items.FEATHERS).define('T', RankineTags.Items.ROPE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.ROPE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FIRE_EXTINGUISHER.get(), 1).pattern("INI").pattern("IWI").pattern("III").define('W', Items.WATER_BUCKET).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.PROSPECTING_STICK.get(), 1).pattern(" SN").pattern(" RS").pattern("S  ").define('N', Tags.Items.NUGGETS).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.NUGGETS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.HARDNESS_TESTER.get(), 1).pattern("  R").pattern(" S ").pattern("G  ").define('G', Tags.Items.GLASS).define('R', Tags.Items.STONE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.ELEMENT_INDEXER.get(), 1).pattern("III").pattern("ICI").pattern("III").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('C', RankineItems.CADMIUM_TELLURIDE_CELL.get()).unlockedBy("has_ingredient", has(RankineItems.CADMIUM_TELLURIDE_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_COBBLING.get(), 1).pattern("MHM").pattern("WML").pattern(" M ").define('H', RankineTags.Items.HAMMERS).define('M', RankineTags.Items.INGOTS_ROSE_METAL).define('W', Items.WATER_BUCKET).define('L', Items.LAVA_BUCKET).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.ALTIMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_BRASS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.BIOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_ROSE_METAL).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.PHOTOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_NICKEL_SILVER).define('R', Items.DAYLIGHT_DETECTOR).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_NICKEL_SILVER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.SPEEDOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_DURALUMIN).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_DURALUMIN)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.THERMOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', Tags.Items.GLASS).define('R', RankineTags.Items.MERCURY).unlockedBy("has_ingredient", has(RankineTags.Items.MERCURY)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.MAGNETOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', Tags.Items.DUSTS_REDSTONE).define('R', RankineTags.Items.INGOTS_POTASSIUM).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_POTASSIUM)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_HASTENING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_ROSE_GOLD).define('R', Tags.Items.STORAGE_BLOCKS_EMERALD).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_ENDURING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_BLUE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_GARNET).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BLUE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_REPULSING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_BLACK_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_RUBY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BLACK_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_TIMESAVING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_STERLING_SILVER).define('R', RankineTags.Items.STORAGE_BLOCKS_SAPPHIRE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STERLING_SILVER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_PROMISING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_WHITE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_TOPAZ).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_WHITE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_SOFTENING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_PURPLE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_PEARL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_PURPLE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_LEVITATING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_OSMIRIDIUM).define('R', RankineTags.Items.STORAGE_BLOCKS_OPAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_OSMIRIDIUM)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_INVIGORATING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_GREEN_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_TOURMALINE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_GREEN_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.TOTEM_OF_BLAZING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_FERROCERIUM).define('R', RankineTags.Items.STORAGE_BLOCKS_PERIDOT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_FERROCERIUM)).save(consumer);

        //ShapedRecipeBuilder.shapedRecipe(RankineItems.TOTEM_OF_INFUSING.get(), 1).patternLine("III").patternLine("IRI").patternLine("III").key('I', RankineTags.Items.INGOTS_MISCHMETAL).key('R', RankineTags.Items.STORAGE_BLOCKS_PERIDOT).addCriterion("has_ingredient", hasItem(RankineTags.Items.INGOTS_MISCHMETAL)).build(consumer);



        ShapedRecipeBuilder.shaped(RankineItems.ICE_SKATES.get(), 1).pattern("L  ").pattern("RRL").pattern("NNN").define('L', Tags.Items.LEATHER).define('R', RankineItems.ROPE.get()).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.SANDALS.get(), 1).pattern("L  ").pattern("LRR").define('L', Tags.Items.LEATHER).define('R', RankineItems.ROPE.get()).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.SNOWSHOES.get(), 1).pattern("SRS").pattern("SRS").pattern(" S ").define('S', Tags.Items.RODS_WOODEN).define('R', RankineItems.ROPE.get()).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.FINS.get(), 1).pattern("L  ").pattern("LRR").define('L', Tags.Items.LEATHER).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.GAS_MASK.get(), 1).pattern("RRR").pattern("MRM").pattern("CMC").define('R', RankineTags.Items.RUBBER).define('C', RankineTags.Items.CARBON).define('M', RankineItems.BIOTITE.get()).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.GOGGLES.get(), 1).pattern("GRG").pattern("RSR").define('R', RankineTags.Items.RUBBER).define('G', Tags.Items.GLASS_PANES).define('S', Tags.Items.STRING).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        
        ShapedRecipeBuilder.shaped(RankineItems.WOOD_TIER_CRUSHING_HEAD.get(), 1).pattern(" I ").pattern("IGI").pattern("#B#").define('#', Items.STONECUTTER).define('G', Items.GRINDSTONE).define('I', RankineTags.Items.INGOTS_ROSE_METAL).define('B', RankineTags.Items.STORAGE_BLOCKS_ROSE_METAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.STONE_TIER_CRUSHING_HEAD.get(), 1).pattern(" I ").pattern("IGI").pattern("#B#").define('#', Items.STONECUTTER).define('G', Items.GRINDSTONE).define('I', RankineTags.Items.INGOTS_BRASS).define('B', RankineTags.Items.STORAGE_BLOCKS_BRASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.IRON_TIER_CRUSHING_HEAD.get(), 1).pattern(" I ").pattern("IGI").pattern("#B#").define('#', Items.STONECUTTER).define('G', Items.GRINDSTONE).define('I', RankineTags.Items.INGOTS_STEEL).define('B', RankineTags.Items.STORAGE_BLOCKS_STEEL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.DIAMOND_TIER_CRUSHING_HEAD.get(), 1).pattern(" I ").pattern("IGI").pattern("#B#").define('#', Items.STONECUTTER).define('G', Items.GRINDSTONE).define('I', RankineTags.Items.INGOTS_TITANIUM_ALLOY).define('B', RankineTags.Items.STORAGE_BLOCKS_TITANIUM_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TITANIUM_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.NETHERITE_TIER_CRUSHING_HEAD.get(), 1).pattern(" I ").pattern("IGI").pattern("#B#").define('#', Items.STONECUTTER).define('G', Items.GRINDSTONE).define('I', RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY).define('B', RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY)).save(consumer);

        ShapedRecipeBuilder.shaped(RankineItems.DOWSING_ROD.get(), 1).pattern("#R#").pattern(" # ").define('#', ItemTags.PLANKS).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(RankineTags.Items.ROPE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.WOODEN_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', ItemTags.PLANKS).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.PEWTER_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', RankineTags.Items.INGOTS_PEWTER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_PEWTER)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.STEEL_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', RankineTags.Items.INGOTS_STEEL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.RARE_EARTH_MAGNET.get(), 1).pattern("I I").pattern("###").define('#', RankineTags.Items.INGOTS_SAMARIUM).define('I', RankineTags.Items.INGOTS_COBALT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer,"rankine:rare_earth_magnet_sm");
        ShapedRecipeBuilder.shaped(RankineItems.RARE_EARTH_MAGNET.get(), 1).pattern("I I").pattern("#B#").define('I', Tags.Items.INGOTS_IRON).define('#', RankineTags.Items.INGOTS_NEODYMIUM).define('B', RankineTags.Items.BORON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer, "rankine:rare_earth_magnet_nd");
        ShapedRecipeBuilder.shaped(RankineItems.ALNICO_MAGNET.get(), 1).pattern("# #").pattern("###").define('#', RankineTags.Items.INGOTS_ALNICO).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.GARLAND.get(), 3).pattern("###").define('#', ItemTags.LEAVES).unlockedBy("has_ingredient", has(ItemTags.LEAVES)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.ORNAMENT.get(), 8).pattern(" C ").pattern("NGN").pattern(" N ").define('C', Items.CHAIN).define('G', Tags.Items.GLASS).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_NUGGETS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.SADDLE_TREE.get(), 1).pattern("III").pattern("PPP").pattern("SSS").define('S', RankineTags.Items.INGOTS_STEEL).define('P', ItemTags.PLANKS).define('I', RankineTags.Items.INGOTS_ALUMINUM).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:saddle_tree_from_aluminum");
        ShapedRecipeBuilder.shaped(RankineItems.SADDLE_TREE.get(), 1).pattern("III").pattern("PPP").pattern("SSS").define('S', RankineTags.Items.INGOTS_STEEL).define('P', ItemTags.PLANKS).define('I', Tags.Items.INGOTS_IRON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:saddle_tree_from_iron");
        ShapedRecipeBuilder.shaped(RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_LEAD).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_LEAD)).save(consumer,"rankine:lead_cannonball");
        ShapedRecipeBuilder.shaped(RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_BISMUTH).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BISMUTH)).save(consumer,"rankine:bismuth_cannonball");
        ShapedRecipeBuilder.shaped(RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_CAST_IRON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_CAST_IRON)).save(consumer,"rankine:cast_iron_cannonball");
        ShapelessRecipeBuilder.shapeless(RankineItems.CARCASS.get()).requires(RankineItems.CANNONBALL.get()).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.SALTPETER).requires(Items.GUNPOWDER).requires(RankineTags.Items.NUGGETS_ANTIMONY).unlockedBy("has_ingredient", has(RankineTags.Items.CANNONBALLS)).save(consumer);

        ShapedRecipeBuilder.shaped(RankineItems.GRAPHITE_ELECTRODE.get(), 1).pattern("  I").pattern(" I ").pattern("I  ").define('I', RankineTags.Items.GRAPHITE).unlockedBy("has_ingredient", has(RankineTags.Items.GRAPHITE)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.HARD_CARBON_ELECTRODE.get(), 1).pattern("  I").pattern(" I ").pattern("I  ").define('I', RankineTags.Items.CARBON).unlockedBy("has_ingredient", has(RankineTags.Items.CARBON)).save(consumer);

        //Machines
        ShapedRecipeBuilder.shaped(RankineBlocks.BEEHIVE_OVEN_PIT.get(), 1).pattern(" S ").pattern("SLS").pattern(" S ").define('S', RankineBlocks.REFRACTORY_BRICKS.get()).define('L', Tags.Items.STORAGE_BLOCKS_COAL).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.ALLOY_FURNACE.get(), 1).pattern("BSB").pattern("BSB").pattern("BCB").define('B', RankineBlocks.REFRACTORY_BRICKS.get()).define('S', RankineTags.Items.SHEETMETAL).define('C', RankineTags.Items.CAMPFIRES).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.MIXING_BARREL.get(), 1).pattern("R").pattern("B").pattern("S").define('B', Blocks.BARREL).define('S', ItemTags.WOODEN_SLABS).define('R', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.BARREL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.CHARCOAL_PIT.get(), 1).pattern(" S ").pattern("SLS").pattern(" S ").define('S', Tags.Items.RODS_WOODEN).define('L', ItemTags.LOGS_THAT_BURN).unlockedBy("has_ingredient", has(ItemTags.LOGS_THAT_BURN)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.GAS_BOTTLER.get(), 1).pattern("XSX").pattern("S S").pattern("XSX").define('X', RankineTags.Items.SHEETMETAL).define('S', RankineTags.Items.INGOTS_BRASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.GAS_VENT.get(), 1).pattern("XSX").pattern("SBS").pattern("XSX").define('X', RankineTags.Items.SHEETMETAL).define('S', RankineItems.BOROSILICATE_GLASS.get()).define('B', Items.GLASS_BOTTLE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.FLOOD_GATE.get(), 1).pattern(" S ").pattern("SSS").pattern(" S ").define('S', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.CRUCIBLE_BLOCK.get(), 1).pattern("R R").pattern("R R").pattern("RCR").define('R', RankineItems.HIGH_REFRACTORY_BRICKS.get()).define('C', Items.CAULDRON).unlockedBy("has_ingredient", has(RankineItems.HIGH_REFRACTORY_BRICKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.MATERIAL_TESTING_TABLE.get(), 1).pattern("TT").pattern("BB").pattern("BB").define('B', ItemTags.PLANKS).define('T', ItemTags.STONE_BRICKS).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.TEMPLATE_TABLE.get(), 1).pattern("TT").pattern("BB").pattern("BB").define('B', ItemTags.PLANKS).define('T', Items.PAPER).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.RARE_EARTH_ELECTROMAGNET.get(), 1).pattern("WBW").pattern("WMW").define('W', RankineItems.ALLOY_WIRE.get()).define('B', RankineItems.LITHIUM_ION_BATTERY.get()).define('M', RankineItems.RARE_EARTH_MAGNET.get()).unlockedBy("has_ingredient", has(RankineItems.RARE_EARTH_MAGNET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineItems.ALNICO_ELECTROMAGNET.get(), 1).pattern("WBW").pattern("WMW").define('W', RankineItems.ALLOY_WIRE.get()).define('B', RankineItems.LEAD_ACID_BATTERY.get()).define('M', RankineItems.ALNICO_MAGNET.get()).unlockedBy("has_ingredient", has(RankineItems.RARE_EARTH_MAGNET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.AIR_DISTILLATION_PACKING.get(), 1).pattern("##").pattern("##").define('#', RankineItems.STAINLESS_STEEL_SHEETMETAL.get()).unlockedBy("has_ingredient", has(RankineItems.STAINLESS_STEEL_SHEETMETAL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.DISTILLATION_TOWER.get(), 1).pattern("IBI").pattern("I#I").pattern("IMI").define('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).define('B', RankineTags.Items.ICE).define('M', Items.MAGMA_BLOCK).define('#', RankineItems.MUSCOVITE_BLOCK.get()).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STAINLESS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.HEATING_ELEMENT_1.get(), 1).pattern("III").pattern("MMM").pattern("III").define('I', Tags.Items.INGOTS_IRON).define('M', Items.MAGMA_BLOCK).unlockedBy("has_ingredient", has(Tags.Items.INGOTS_IRON)).save(consumer);

        //Smithing Recipes
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_BOOTS),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_BOOTS.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_boots_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_LEGGINGS),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_LEGGINGS.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_leggings_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_CHESTPLATE),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_CHESTPLATE.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_chestplate_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_HELMET),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RankineItems.BRIGADINE_HELMET.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_helmet_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_BOOTS.get()),Ingredient.of(new ItemStack(Items.PRISMARINE_SHARD,8)),RankineItems.CONDUIT_DIVING_BOOTS.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_boots_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_LEGGINGS.get()),Ingredient.of(new ItemStack(Items.PRISMARINE_CRYSTALS,8)),RankineItems.CONDUIT_DIVING_LEGGINGS.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_leggings_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_CHESTPLATE.get()),Ingredient.of(Items.CONDUIT),RankineItems.CONDUIT_DIVING_CHESTPLATE.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_chestplate_from_smithing");
        UpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_HELMET.get()),Ingredient.of(Items.SPONGE),RankineItems.CONDUIT_DIVING_HELMET.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_helmet_from_smithing");
        

        
        //furnace recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.FIRE_CLAY_BALL.get()), RankineItems.REFRACTORY_BRICK.get(), 0.2F, 200).unlockedBy("has_ingredient", has(RankineTags.Items.CLAY_BALLS)).save(consumer, "rankine:refractory_brick_from_fire_clay_ball_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BAMBOO), RankineItems.DRIED_BAMBOO.get(), 0.1F, 25).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer, "rankine:dried_bamboo_from_bamboo_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BONE), RankineItems.BONE_ASH.get(), 0.1F, 200).unlockedBy("has_ingredient", has(Items.BONE)).save(consumer, "rankine:bone_ash_from_bone_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.PULP.get()), Items.PAPER, 0.1F, 100).unlockedBy("has_ingredient", has(RankineItems.PULP.get())).save(consumer, "rankine:paper_from_pulp_smelting");

        //blasting recipes
        for (Item MINERAL : blastingMap.keySet()) {
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(MINERAL), blastingMap.get(MINERAL), 0.5F, 100).unlockedBy("has_ingredient", has(MINERAL)).save(consumer, "rankine:"+blastingMap.get(MINERAL).getRegistryName().getPath()+"_from_"+MINERAL.getRegistryName().getPath()+"_blasting");
        }
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineTags.Items.SLAG), Items.IRON_NUGGET, 0.5F, 100).unlockedBy("has_ingredient", has(RankineTags.Items.SLAG)).save(consumer, "rankine:iron_nugget_from_slag_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.APATITE.get()), RankineItems.PHOSPHORUS_NUGGET.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.APATITE.get())).save(consumer, "rankine:phosphorus_nugget_from_apatite_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.BADDELEYITE.get()), RankineItems.ZIRCONIA.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.BADDELEYITE.get())).save(consumer, "rankine:zirconia_from_baddeleyite_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.METEORIC_IRON.get()), Items.IRON_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.METEORIC_IRON.get())).save(consumer, "rankine:iron_from_meteoric_iron_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.TRONA.get()), RankineItems.SODIUM_CARBONATE.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.TRONA.get())).save(consumer, "rankine:sodium_carbonate_from_trona_blasting");




        for (Block MAT : RankineLists.FIBER_MAT) {
            Block BLOCK = RankineLists.FIBER_BLOCK.get(RankineLists.FIBER_MAT.indexOf(MAT));
            ShapedRecipeBuilder.shaped(MAT, 3).pattern("##").define('#', BLOCK).group("fiber_mat").unlockedBy("has_block", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.FIBER_BLOCK.get())).save(consumer);
        }
        for (Block MAT : RankineLists.FIBER_MAT) {
            if (RankineLists.FIBER_MAT.indexOf(MAT)==0) continue;
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.FIBER_MAT.indexOf(MAT)-1);
            ShapelessRecipeBuilder.shapeless(MAT).requires(RankineItems.FIBER_MAT.get()).requires(DYE).unlockedBy("has_ingredient", has(RankineItems.FIBER_MAT.get())).save(consumer, "rankine:"+MAT.getRegistryName().getPath()+"_from_dye");
        }
        for (Block BLOCK : RankineLists.FIBER_BLOCK) {
            if (RankineLists.FIBER_BLOCK.indexOf(BLOCK)==0) continue;
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.FIBER_BLOCK.indexOf(BLOCK)-1);
            ShapelessRecipeBuilder.shapeless(BLOCK).requires(RankineItems.FIBER_BLOCK.get()).requires(DYE).unlockedBy("has_ingredient", has(RankineItems.FIBER_BLOCK.get())).save(consumer, "rankine:"+BLOCK.getRegistryName().getPath()+"_from_dye");
        }
        for (Block BLOCK : RankineLists.LEDS) {
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.LEDS.indexOf(BLOCK));
            led(consumer, BLOCK.asItem(), DYE);
        }


        twoXtwo(consumer, RankineItems.ROPE.get(), RankineItems.PLANT_FIBER.get(), 1, "has_plant_fiber", RankineItems.PLANT_FIBER.get());
        threeXthree(consumer, RankineItems.FIBER_BLOCK.get(), RankineItems.PLANT_FIBER.get(), 1, "has_plant_fiber", RankineItems.PLANT_FIBER.get());

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
        ShapelessRecipeBuilder.shapeless(Items.BLACK_DYE).requires(RankineItems.BONE_CHAR.get()).unlockedBy("has_ingredient", has(RankineItems.BONE_CHAR.get())).save(consumer, "rankine:black_dye_from_bone_char");
        ShapelessRecipeBuilder.shapeless(Items.PINK_DYE).requires(RankineItems.PINK_BELLFLOWER.get(),2).unlockedBy("has_ingredient", has(RankineItems.PINK_BELLFLOWER.get())).save(consumer, "rankine:pink_dye_from_pink_bellflower");
        ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE).requires(RankineItems.VIOLET_BELLFLOWER.get(),2).unlockedBy("has_ingredient", has(RankineItems.VIOLET_BELLFLOWER.get())).save(consumer, "rankine:magenta_dye_from_violet_bellflower");
        ShapelessRecipeBuilder.shapeless(Items.YELLOW_DYE).requires(RankineItems.GOLDENROD.get(),2).unlockedBy("has_ingredient", has(RankineItems.GOLDENROD.get())).save(consumer, "rankine:yellow_dye_from_goldenrod");
        ShapelessRecipeBuilder.shapeless(Items.RED_DYE).requires(RankineItems.RED_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.RED_LILY.get())).save(consumer, "rankine:red_dye_from_red_lily");
        ShapelessRecipeBuilder.shapeless(Items.ORANGE_DYE).requires(RankineItems.ORANGE_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.ORANGE_LILY.get())).save(consumer, "rankine:orange_dye_from_orange_lily");
        ShapelessRecipeBuilder.shapeless(Items.WHITE_DYE).requires(RankineItems.WHITE_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.WHITE_LILY.get())).save(consumer, "rankine:white_dye_from_white_lily");
        ShapelessRecipeBuilder.shapeless(Items.BLACK_DYE).requires(RankineItems.BLACK_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.BLACK_MORNING_GLORY.get())).save(consumer, "rankine:black_dye_from_black_morning_glory");
        ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE).requires(RankineItems.BLUE_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.BLUE_MORNING_GLORY.get())).save(consumer, "rankine:blue_dye_from_blue_morning_glory");
        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE).requires(RankineItems.PURPLE_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.PURPLE_MORNING_GLORY.get())).save(consumer, "rankine:purple_dye_from_purple_morning_glory");
        ShapelessRecipeBuilder.shapeless(Items.GRAY_DYE).requires(Items.WATER_BUCKET).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).unlockedBy("has_ingredient", has(RankineItems.ASH.get())).save(consumer, "rankine:gray_dye_from_ash");
        ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE,4).requires(RankineTags.Items.NUGGETS_YTTRIUM).requires(RankineTags.Items.NUGGETS_YTTRIUM).requires(RankineTags.Items.NUGGETS_INDIUM).requires(RankineTags.Items.NUGGETS_INDIUM).requires(RankineTags.Items.NUGGETS_MANGANESE).requires(RankineTags.Items.NUGGETS_MANGANESE).unlockedBy("has_ingredient", has(RankineTags.Items.NUGGETS_MANGANESE)).save(consumer, "rankine:blue_dye_from_metals");


        SpecialRecipeBuilder.special(JamRecipe.SERIALIZER).save(consumer, "fruit_jam");

        ShapedRecipeBuilder.shaped(RankineBlocks.CLAY_BRICKS.get()).define('#', Items.CLAY_BALL).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(Items.CLAY_BALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.FIRE_CLAY_BRICKS.get()).define('#', RankineItems.FIRE_CLAY_BALL.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.FIRE_CLAY_BALL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.PORCELAIN_CLAY_BRICKS.get()).define('#', RankineItems.PORCELAIN_CLAY_BALL.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.KAOLINITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.REFRACTORY_BRICKS.get()).define('#', RankineItems.REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_refractory_brick", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.HIGH_REFRACTORY_BRICKS.get()).define('#', RankineItems.HIGH_REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_high_refractory_brick", has(RankineItems.HIGH_REFRACTORY_BRICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get()).define('#', RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_refractory_brick", has(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.METEORITE_BRICKS.get()).define('#', RankineBlocks.METEORITE.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_meteorite", has(RankineBlocks.METEORITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get()).define('#', RankineBlocks.ENSTATITE_CHONDRITE.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_enstatite_chondrite", has(RankineBlocks.ENSTATITE_CHONDRITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.FROZEN_METEORITE_BRICKS.get()).define('#', RankineBlocks.FROZEN_METEORITE.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_meteorite", has(RankineBlocks.FROZEN_METEORITE.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RankineBlocks.METEORITE.get()), RankineBlocks.METEORITE_BRICKS.get(), 1).unlockedBy("has_meteorite", has(RankineBlocks.METEORITE.get())).save(consumer, "rankine:meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RankineBlocks.FROZEN_METEORITE.get()), RankineBlocks.FROZEN_METEORITE_BRICKS.get(), 1).unlockedBy("has_frozen_meteorite", has(RankineBlocks.FROZEN_METEORITE.get())).save(consumer, "rankine:frozen_meteorite_bricks_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RankineBlocks.ENSTATITE_CHONDRITE.get()), RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get(), 1).unlockedBy("has_meteorite", has(RankineBlocks.ENSTATITE_CHONDRITE.get())).save(consumer, "rankine:enstatite_bricks_from_stonecutting");




        for (Block HOLLOW : RankineLists.HOLLOW_LOGS) {
            String PATH = HOLLOW.getRegistryName().getPath();
            String nameSpace;
            if (Arrays.asList("hollow_oak_log","hollow_birch_log","hollow_spruce_log","hollow_acacia_log","hollow_jungle_log","hollow_dark_oak_log","hollow_crimson_stem","hollow_warped_stem").contains(PATH)) {
                nameSpace = "minecraft";
            } else {
                nameSpace = "rankine";
            }
            Block LOG = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(nameSpace+":"+PATH.replace("hollow_","")));
            if (LOG != null) {
                ShapedRecipeBuilder.shaped(HOLLOW.asItem(), 16).pattern("###").pattern("# #").pattern("###").define('#', LOG).group("rankine:hollow_logs").unlockedBy("has_ingredient", has(LOG)).save(consumer);
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
            Block LEAF = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(nameSpace+":"+PATH.replace("leaf_litter","leaves")));
            if (LEAF != null) {
                ShapedRecipeBuilder.shaped(BLK.asItem(), 4).pattern("##").define('#', LEAF).group("rankine:leaf_litters").unlockedBy("has_ingredient", has(LEAF)).save(consumer);
            }
        }
        for (Block COARSE_SOIL : RankineLists.COARSE_SOIL_BLOCKS) {
            Block SOIL = RankineLists.SOIL_BLOCKS.get(RankineLists.COARSE_SOIL_BLOCKS.indexOf(COARSE_SOIL));
            ShapedRecipeBuilder.shaped(COARSE_SOIL.asItem(), 4).pattern("#G").pattern("G#").define('#', SOIL).define('G', Tags.Items.GRAVEL).group("rankine:coarse_soil").unlockedBy("has_ingredient", has(SOIL)).save(consumer);

        }
        for (Block BLK : RankineLists.GLAZED_PORCELAIN_BLOCKS) {
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.GLAZED_PORCELAIN_BLOCKS.indexOf(BLK));
            centerRing(consumer, BLK.asItem(), 8, Ingredient.of(RankineItems.PORCELAIN.get()), Ingredient.of(DYE), "rankine:glazed_porcelain", RankineItems.PORCELAIN.get());
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
        OneToX(consumer, RankineItems.COPPER_NUGGET.get(), Items.COPPER_INGOT, 9, "has_ingredient", Items.COPPER_INGOT);
        threeXthree(consumer, Items.COPPER_INGOT, RankineItems.COPPER_NUGGET.get(), 1, "has_ingredient", Items.COPPER_INGOT, "copper_ingot_from_copper_nugget");
        //Misc Blocks
        OneToX(consumer, RankineItems.CALCITE.get(), Items.CALCITE, 9, "has_ingredient", Items.CALCITE);
        threeXthree(consumer, Items.CALCITE, RankineItems.CALCITE.get(), 1, "has_ingredient", Items.CALCITE, "calcite_from_calcite_block");
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
        ShapedRecipeBuilder.shaped(RankineBlocks.BAMBOO_CULMS.get(), 1).pattern("##").pattern("##").define('#', Items.BAMBOO).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer);
        ShapedRecipeBuilder.shaped(RankineBlocks.BAMBOO_PLANKS.get(), 1).pattern("##").pattern("##").define('#', RankineItems.DRIED_BAMBOO.get()).unlockedBy("has_ingredient", has(RankineItems.DRIED_BAMBOO.get())).save(consumer);

        for (Block PLANK : RankineLists.PLANKS) {
            String baseName = PLANK.getRegistryName().getPath();
            Block WOODEN_SLAB = RankineLists.WOODEN_SLABS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_STAIRS = RankineLists.WOODEN_STAIRS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_PRESSURE_PLATE = RankineLists.WOODEN_PRESSURE_PLATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_BUTTON = RankineLists.WOODEN_BUTTONS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_DOOR = RankineLists.WOODEN_DOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_TRAPDOOR = RankineLists.WOODEN_TRAPDOORS.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCE = RankineLists.WOODEN_FENCES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_FENCE_GATE = RankineLists.WOODEN_FENCE_GATES.get(RankineLists.PLANKS.indexOf(PLANK));
            Block WOODEN_BOOKSHELVES = RankineLists.WOODEN_BOOKSHELVES.get(RankineLists.PLANKS.indexOf(PLANK));
            Item WOODEN_BOAT = RankineLists.WOODEN_BOATS.get(RankineLists.PLANKS.indexOf(PLANK));
            slab(consumer, WOODEN_SLAB.asItem(), PLANK.asItem(), "wooden_slab");
            stairs(consumer, WOODEN_STAIRS.asItem(), PLANK.asItem(), "wooden_stairs");
            pressurePlate(consumer, WOODEN_PRESSURE_PLATE.asItem(), PLANK.asItem(), "wooden_pressure_plate");
            door(consumer, WOODEN_DOOR.asItem(), PLANK.asItem(), "wooden_door",  "has_plank", PLANK.asItem());
            trapdoor(consumer, WOODEN_TRAPDOOR.asItem(), PLANK.asItem(), "wooden_trapdoor",  "has_plank", PLANK.asItem());
            fence(consumer, WOODEN_FENCE.asItem(), PLANK.asItem(), "wooden_fence",  "has_plank", PLANK.asItem());
            fenceGate(consumer, WOODEN_FENCE_GATE.asItem(), PLANK.asItem(), "wooden_fence_gate",  "has_plank", PLANK.asItem());
            bookshelf(consumer, WOODEN_BOOKSHELVES.asItem(), PLANK.asItem(), "wooden_bookshelves",  "has_plank", PLANK.asItem());
            boat(consumer, WOODEN_BOAT, PLANK.asItem(), "boat",  "has_plank", PLANK.asItem());
            ShapelessRecipeBuilder.shapeless(WOODEN_BUTTON).requires(PLANK).group("wooden_button").unlockedBy("has_ingredient", has(PLANK)).save(consumer);
            
            ShapelessRecipeBuilder.shapeless(PLANK).requires(WOODEN_SLAB).requires(WOODEN_SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(PLANK)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(PLANK,3).requires(WOODEN_STAIRS).requires(WOODEN_STAIRS).requires(WOODEN_STAIRS).requires(WOODEN_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(PLANK)).save(consumer,"rankine:"+baseName+"_from_stairs");
        }


        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            String baseStone = Stone.getBaseName();
            Block COBBLE = Stone.getCobble();
            Block COLUMN = Stone.getColumn();
            Block STONE = Stone.getStone();
            Block POLISHED_STONE = Stone.getPolished();
            Block STONE_BRICKS = Stone.getBricks();
            Block MOSSY_STONE_BRICKS = Stone.getMossyBricks();
            Block STONE_SLAB = Stone.getSlab();
            Block POLISHED_STONE_SLAB = Stone.getPolishedSlab();
            Block STONE_BRICKS_SLAB = Stone.getBricksSlab();
            Block MOSSY_STONE_BRICKS_SLAB = Stone.getMossyBricksSlab();
            Block STONE_STAIRS = Stone.getStairs();
            Block POLISHED_STONE_STAIRS = Stone.getPolishedStairs();
            Block STONE_BRICKS_STAIRS = Stone.getBricksStairs();
            Block MOSSY_STONE_BRICKS_STAIRS = Stone.getMossyBricksStairs();
            Block STONE_WALL = Stone.getWall();
            Block POLISHED_STONE_WALL = Stone.getPolishedWall();
            Block STONE_BRICKS_WALL = Stone.getBricksWall();
            Block MOSSY_STONE_BRICKS_WALL = Stone.getMossyBricksWall();
            Block STONE_PRESSURE_PLATE = Stone.getPressurePlate();
            Block STONE_BRICKS_PRESSURE_PLATE = Stone.getBricksPressurePlate();
            Block STONE_BUTTON = Stone.getButton();

            ShapedRecipeBuilder.shaped(COLUMN, 8)
                    .pattern("#")
                    .pattern("#")
                    .define('#', STONE)
                    .group("stone_column")
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(STONE))
                    .save(consumer);
            ShapelessRecipeBuilder.shapeless(STONE).requires(COLUMN).requires(COLUMN).requires(COLUMN).requires(COLUMN).group("stone_from_stone_column").unlockedBy("has_ingredient", has(COLUMN)).save(consumer,"rankine:"+STONE.getRegistryName().getPath()+"_from_column");
            ShapelessRecipeBuilder.shapeless(STONE).requires(COBBLE).requires(COBBLE).requires(COBBLE).requires(COBBLE).group("stone_from_cobble").unlockedBy("has_ingredient", has(COBBLE)).save(consumer,"rankine:"+STONE.getRegistryName().getPath()+"_from_cobble");
            ShapelessRecipeBuilder.shapeless(COBBLE,8).requires(STONE).requires(Tags.Items.COBBLESTONE).group("cobble_from_stone").unlockedBy("has_ingredient", has(COBBLE)).save(consumer,"rankine:"+COBBLE.getRegistryName().getPath()+"_from_stone");

            ShapedRecipeBuilder.shaped(POLISHED_STONE, 4)
                    .pattern("##")
                    .pattern("##")
                    .define('#', STONE)
                    .group("polished_stone")
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(STONE))
                    .save(consumer);

            ShapedRecipeBuilder.shaped(STONE_BRICKS, 2)
                    .pattern("#M")
                    .pattern("M#")
                    .define('#', STONE)
                    .define('M', RankineItems.MORTAR.get())
                    .group("stone_bricks")
                    .unlockedBy("has_mortar", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.MORTAR.get()))
                    .save(consumer);

            slab(consumer, STONE_SLAB.asItem(), STONE.asItem(), "stone_slab");
            slab(consumer, POLISHED_STONE_SLAB.asItem(), POLISHED_STONE.asItem(), "stone_slab");
            slab(consumer, STONE_BRICKS_SLAB.asItem(), STONE_BRICKS.asItem(), "stone_slab");
            slab(consumer, MOSSY_STONE_BRICKS_SLAB.asItem(), MOSSY_STONE_BRICKS.asItem(), "stone_slab");
            stairs(consumer, STONE_STAIRS.asItem(), STONE.asItem(), "stone_stairs");
            stairs(consumer, POLISHED_STONE_STAIRS.asItem(), POLISHED_STONE.asItem(), "stone_stairs");
            stairs(consumer, STONE_BRICKS_STAIRS.asItem(), STONE_BRICKS.asItem(), "stone_stairs");
            stairs(consumer, MOSSY_STONE_BRICKS_STAIRS.asItem(), MOSSY_STONE_BRICKS.asItem(), "stone_stairs");
            wall(consumer, STONE_WALL.asItem(), STONE.asItem(), "stone_wall");
            wall(consumer, POLISHED_STONE_WALL.asItem(), POLISHED_STONE.asItem(), "stone_wall");
            wall(consumer, STONE_BRICKS_WALL.asItem(), STONE_BRICKS.asItem(), "stone_wall");
            wall(consumer, MOSSY_STONE_BRICKS_WALL.asItem(), MOSSY_STONE_BRICKS.asItem(), "stone_wall");
            pressurePlate(consumer, STONE_PRESSURE_PLATE.asItem(), STONE.asItem(), "stone_pressure_plate");
            pressurePlate(consumer, STONE_BRICKS_PRESSURE_PLATE.asItem(), STONE_BRICKS.asItem(), "stone_pressure_plate");
            ShapelessRecipeBuilder.shapeless(STONE_BUTTON).requires(STONE).group("stone_button").unlockedBy("has_ingredient", has(STONE)).save(consumer);
            ShapelessRecipeBuilder.shapeless(MOSSY_STONE_BRICKS).requires(STONE).requires(Items.VINE).group("mossy_bricks_vines").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:mossy_"+baseStone+"_bricks_from_vine");
            ShapelessRecipeBuilder.shapeless(MOSSY_STONE_BRICKS).requires(STONE).requires(Items.MOSS_BLOCK).group("mossy_bricks_moss").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:mossy_"+baseStone+"_bricks_from_moss");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), POLISHED_STONE, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:polished_"+baseStone+"_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_BRICKS, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_bricks_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_SLAB, 2).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_slab_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_STAIRS).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_stairs_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_WALL).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_wall_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_pressure_plate_from_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_BUTTON).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStone+"_button_from_"+baseStone+"_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), STONE_BRICKS_SLAB, 2).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStone+"_brick_slab_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), STONE_BRICKS_STAIRS).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStone+"_brick_stairs_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), STONE_BRICKS_WALL).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStone+"_brick_wall_from_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), STONE_BRICKS_PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStone+"_brick_button_from_"+baseStone+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), MOSSY_STONE_BRICKS_SLAB, 2).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStone+"_brick_slab_from_mossy_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), MOSSY_STONE_BRICKS_STAIRS).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStone+"_brick_stairs_from_mossy_"+baseStone+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), MOSSY_STONE_BRICKS_WALL).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStone+"_brick_wall_from_mossy_"+baseStone+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), POLISHED_STONE_SLAB, 2).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStone+"_slab_from_polished_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), POLISHED_STONE_STAIRS).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStone+"_stairs_from_polished_"+baseStone+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), POLISHED_STONE_WALL).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStone+"_wall_from_polished_"+baseStone+"_stonecutting");

            ShapelessRecipeBuilder.shapeless(STONE).requires(STONE_SLAB).requires(STONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:"+baseStone+"_from_slab");
            ShapelessRecipeBuilder.shapeless(STONE,3).requires(STONE_STAIRS).requires(STONE_STAIRS).requires(STONE_STAIRS).requires(STONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:"+baseStone+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(STONE_BRICKS).requires(STONE_BRICKS_SLAB).requires(STONE_BRICKS_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_from_slab");
            ShapelessRecipeBuilder.shapeless(STONE_BRICKS,3).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer,"rankine:"+STONE_BRICKS.getRegistryName().getPath()+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(MOSSY_STONE_BRICKS).requires(MOSSY_STONE_BRICKS_SLAB).requires(MOSSY_STONE_BRICKS_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer,"rankine:mossy_"+MOSSY_STONE_BRICKS.getRegistryName().getPath()+"_from_slab");
            ShapelessRecipeBuilder.shapeless(MOSSY_STONE_BRICKS,3).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer,"rankine:mossy_"+MOSSY_STONE_BRICKS.getRegistryName().getPath()+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(POLISHED_STONE).requires(POLISHED_STONE_SLAB).requires(POLISHED_STONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_from_slab");
            ShapelessRecipeBuilder.shapeless(POLISHED_STONE,3).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer,"rankine:"+POLISHED_STONE.getRegistryName().getPath()+"_from_stairs");

        }


        for (Block STONE_BRICK : RankineLists.VANILLA_BRICKS) {
            String PATH = STONE_BRICK.getRegistryName().getPath();
            Block STONE = RankineLists.VANILLA_STONES.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block SLAB = RankineLists.VANILLA_BRICKS_SLABS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block STAIRS = RankineLists.VANILLA_BRICKS_STAIRS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block WALL = RankineLists.VANILLA_BRICKS_WALLS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block PRESSURE_PLATE = RankineLists.VANILLA_BRICKS_PRESSURE_PLATES.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));

            ShapedRecipeBuilder.shaped(STONE_BRICK, 2)
                    .pattern("#M")
                    .pattern("M#")
                    .define('#', STONE)
                    .define('M', RankineItems.MORTAR.get())
                    .group("stone_bricks")
                    .unlockedBy("has_mortar", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.MORTAR.get()))
                    .save(consumer);

            slab(consumer, SLAB.asItem(), STONE_BRICK.asItem(), "stone_slab");
            stairs(consumer, STAIRS.asItem(), STONE_BRICK.asItem(), "stone_stairs");
            wall(consumer, WALL.asItem(), STONE_BRICK.asItem(), "stone_wall");
            pressurePlate(consumer, PRESSURE_PLATE.asItem(), STONE_BRICK.asItem(), "stone_pressure_plate");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), STONE_BRICK, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+PATH+"_bricks_from_"+PATH+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), SLAB, 2).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_slab_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), STAIRS).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_stairs_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), WALL).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_wall_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_pressure_plate_from_"+PATH+"_bricks_stonecutting");

            ShapelessRecipeBuilder.shapeless(STONE_BRICK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer,"rankine:"+PATH+"_from_slab");
            ShapelessRecipeBuilder.shapeless(STONE_BRICK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STAIRS)).save(consumer,"rankine:"+PATH+"_from_stairs");

        }

        //NATIVE ORES
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).save(consumer, "rankine:sulfur_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SULFUR_ORE.get()), RankineItems.SULFUR.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).save(consumer, "rankine:sulfur_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).save(consumer, "rankine:arsenic_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_ARSENIC_ORE.get()), RankineItems.ARSENIC.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).save(consumer, "rankine:arsenic_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).save(consumer, "rankine:bismuth_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_BISMUTH_ORE.get()), RankineItems.BISMUTH_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).save(consumer, "rankine:bismuth_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).save(consumer, "rankine:lead_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_LEAD_ORE.get()), RankineItems.LEAD_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).save(consumer, "rankine:lead_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).save(consumer, "rankine:silver_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SILVER_ORE.get()), RankineItems.SILVER_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).save(consumer, "rankine:silver_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).save(consumer, "rankine:tin_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_TIN_ORE.get()), RankineItems.TIN_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).save(consumer, "rankine:tin_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).save(consumer, "rankine:indium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_INDIUM_ORE.get()), RankineItems.INDIUM_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).save(consumer, "rankine:indium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).save(consumer, "rankine:gallium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_GALLIUM_ORE.get()), RankineItems.GALLIUM_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).save(consumer, "rankine:gallium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).save(consumer, "rankine:selenium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SELENIUM_ORE.get()), RankineItems.SELENIUM.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).save(consumer, "rankine:selenium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).save(consumer, "rankine:tellurium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_TELLURIUM_ORE.get()), RankineItems.TELLURIUM.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).save(consumer, "rankine:tellurium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.PORPHYRY_COPPER.get()), Items.COPPER_INGOT, 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.PORPHYRY_COPPER.get().asItem())).save(consumer, "rankine:copper_ingot_from_porphyry_copper_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.PORPHYRY_COPPER.get()), Items.COPPER_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.PORPHYRY_COPPER.get().asItem())).save(consumer, "rankine:copper_ingot_from_porphyry_copper_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.STIBNITE_ORE.get()), RankineItems.ANTIMONY.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.STIBNITE_ORE.get().asItem())).save(consumer, "rankine:antimony_ingot_from_stibnite_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.STIBNITE_ORE.get()), RankineItems.ANTIMONY.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.STIBNITE_ORE.get().asItem())).save(consumer, "rankine:antimony_ingot_from_stibnite_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.IRON_ORE.get()), Items.IRON_INGOT, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.IRON_ORE.get().asItem())).save(consumer, "rankine:iron_ingot_from_rankine_iron_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.IRON_ORE.get()), Items.IRON_INGOT, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.IRON_ORE.get().asItem())).save(consumer, "rankine:iron_ingot_from_rankine_iron_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.COPPER_ORE.get()), Items.COPPER_INGOT, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.COPPER_ORE.get().asItem())).save(consumer, "rankine:copper_ingot_from_rankine_copper_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.COPPER_ORE.get()), Items.COPPER_INGOT, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.COPPER_ORE.get().asItem())).save(consumer, "rankine:copper_ingot_from_rankine_copper_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.GOLD_ORE.get()), Items.GOLD_INGOT, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.GOLD_ORE.get()), Items.GOLD_INGOT, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_gold_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NETHER_GOLD_ORE.get()), Items.GOLD_INGOT, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_nether_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NETHER_GOLD_ORE.get()), Items.GOLD_INGOT, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_nether_gold_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NETHER_QUARTZ_ORE.get()), Items.QUARTZ, 0.2F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_QUARTZ_ORE.get().asItem())).save(consumer, "rankine:quartz_ingot_from_rankine_nether_quartz_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NETHER_QUARTZ_ORE.get()), Items.QUARTZ, 0.2F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_QUARTZ_ORE.get().asItem())).save(consumer, "rankine:quartz_ingot_from_rankine_nether_quartz_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.COAL_ORE.get()), Items.COAL, 0.1F, 200).unlockedBy("has_ingredient", has(RankineBlocks.COAL_ORE.get().asItem())).save(consumer, "rankine:coal_from_rankine_coal_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.COAL_ORE.get()), Items.COAL, 0.1F, 100).unlockedBy("has_ingredient", has(RankineBlocks.COAL_ORE.get().asItem())).save(consumer, "rankine:coal_from_rankine_coal_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.2F, 200).unlockedBy("has_ingredient", has(RankineBlocks.LAPIS_ORE.get().asItem())).save(consumer, "rankine:lapis_from_rankine_lapis_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.2F,   100).unlockedBy("has_ingredient", has(RankineBlocks.LAPIS_ORE.get().asItem())).save(consumer, "rankine:lapis_from_rankine_lapis_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.REDSTONE_ORE.get()), Items.REDSTONE, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.REDSTONE_ORE.get().asItem())).save(consumer, "rankine:redstone_from_rankine_redstone_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.REDSTONE_ORE.get()), Items.REDSTONE, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.REDSTONE_ORE.get().asItem())).save(consumer, "rankine:redstone_from_rankine_redstone_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.DIAMOND_ORE.get()), Items.DIAMOND, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.DIAMOND_ORE.get().asItem())).save(consumer, "rankine:diamond_from_rankine_diamond_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.DIAMOND_ORE.get()), Items.DIAMOND, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.DIAMOND_ORE.get().asItem())).save(consumer, "rankine:diamond_from_rankine_diamond_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.EMERALD_ORE.get()), Items.EMERALD, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.EMERALD_ORE.get().asItem())).save(consumer, "rankine:emerlad_from_rankine_emerlad_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.EMERALD_ORE.get()), Items.EMERALD, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.EMERALD_ORE.get().asItem())).save(consumer, "rankine:emerlad_from_rankine_emerlad_ore_blasting");

        //ALLOY STUFFS
        for (Item NUGGET : RankineLists.ALLOY_NUGGETS) {
            Block BLOCK = RankineLists.ALLOY_BLOCKS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));

            if (!INGOT.equals(RankineItems.SOLDER.get())) {
                threeXthreeAlloy(consumer, BLOCK.asItem(), INGOT, 1, "has_ingredient", INGOT, 16777215,null);
                threeXthreeAlloy(consumer, INGOT, NUGGET, 1, "has_ingredient", NUGGET, INGOT.getRegistryName().getPath()+"_from_"+NUGGET.getRegistryName().getPath(),16777215,null);
                OneToXAlloy(consumer, INGOT, BLOCK.asItem(), 9, "has_ingredient", BLOCK.asItem(), INGOT.getRegistryName().getPath()+"_from_"+BLOCK.getRegistryName().getPath());
                OneToXAlloy(consumer, NUGGET, INGOT, 9, "has_ingredient", INGOT);
            }
        }
        for (Block BLK : RankineLists.ALLOY_PEDESTALS) {
            Item ALLOY = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_PEDESTALS.indexOf(BLK));
            pedestal(consumer, BLK.asItem(), "rankine:pedestals", ALLOY, "has_ingredient", ALLOY);
        }
        for (Block BLK : RankineLists.ALLOY_POLES) {
            Item ALLOY = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_POLES.indexOf(BLK));
            pole(consumer, BLK.asItem(), "rankine:poles", ALLOY, "has_ingredient", ALLOY);
        }
        for (Block BLK : RankineLists.ALLOY_BARS) {
            Item ALLOY = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_BARS.indexOf(BLK));
            bars(consumer, BLK.asItem(), "rankine:bars", ALLOY, "has_ingredient", ALLOY);
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
        for (Block BLK : Stream.of(RankineLists.SHEETMETALS,RankineLists.ALLOY_SHEETMETALS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String PATH = BLK.getRegistryName().getPath();
            TagKey<Item> ingotTag = TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation.tryParse("forge:ingots/"+PATH.replace("_sheetmetal","")));
            TagKey<Item> nugTag = TagKey.create(Registry.ITEM_REGISTRY, ResourceLocation.tryParse("forge:nuggets/"+PATH.replace("_sheetmetal","")));

            ShapedRecipeBuilder.shaped(BLK, 8).pattern("#I#").pattern("#I#").pattern("#I#").define('I', ingotTag).define('#', nugTag).group("sheetmetal").unlockedBy("has_ingredient", has(ingotTag)).save(consumer);
        }

        
        hLine(consumer,RankineItems.TAP_LINE.get(),3,RankineItems.VULCANIZED_RUBBER.get(),"has_ingredient",RankineItems.VULCANIZED_RUBBER.get());
        hLine(consumer,RankineItems.METAL_PIPE.get(),8,RankineItems.CUPRONICKEL_INGOT.get(),"has_ingredient",RankineItems.CUPRONICKEL_INGOT.get());
        
        
        ladder(consumer,RankineItems.BRASS_LADDER.get(),8,RankineTags.Items.INGOTS_BRASS);
        ladder(consumer,RankineItems.CAST_IRON_LADDER.get(),8,RankineTags.Items.INGOTS_CAST_IRON);
        ladder(consumer,RankineItems.CUPRONICKEL_LADDER.get(),8,RankineTags.Items.INGOTS_CUPRONICKEL);
        ladder(consumer,RankineItems.DURALUMIN_LADDER.get(),8,RankineTags.Items.INGOTS_DURALUMIN);
        ladder(consumer,RankineItems.INVAR_LADDER.get(),8,RankineTags.Items.INGOTS_INVAR);

        //Beehive Oven
        for (Map.Entry<Ingredient,Block> entry : BEEHIVE_OVEN_MINERAL_MAP.entrySet()) {
            BeehiveOvenRecipeBuilder.beehiveOvenRecipe(entry.getKey(),entry.getValue(), 2400, 4800).save(consumer,new ResourceLocation("rankine:"+entry.getKey().getItems()[0].getItem().getRegistryName().getPath()+"_beehive_oven_cooking"));
        }
        for (Map.Entry<Ingredient,Block> entry : BEEHIVE_OVEN_OTHER_MAP.entrySet()) {
            BeehiveOvenRecipeBuilder.beehiveOvenRecipe(entry.getKey(),entry.getValue()).save(consumer,new ResourceLocation("rankine:"+entry.getKey().getItems()[0].getItem().getRegistryName().getPath()+"_beehive_oven_cooking"));
        }
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(RankineTags.Items.STONES_LIMESTONE), RankineBlocks.QUICKLIME_BLOCK.get()).save(consumer, new ResourceLocation("rankine:quicklime_from_limestone_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(RankineTags.Items.STONES_DOLOMITE), RankineBlocks.QUICKLIME_BLOCK.get()).save(consumer, new ResourceLocation("rankine:quicklime_from_dolomite_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(ItemTags.SAND), Blocks.GLASS).save(consumer, new ResourceLocation("rankine:glass_from_sand_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(RankineTags.Items.SILT), Blocks.GLASS).save(consumer, new ResourceLocation("rankine:glass_from_silt_beehive_oven_cooking"));


        //Campfire
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.BAMBOO), RankineItems.DRIED_BAMBOO.get(), 0.35F, 40, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer, "rankine:dried_bamboo_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.BONE), RankineItems.BONE_ASH.get(), 0.35F, 200, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(Items.BONE)).save(consumer, "rankine:bone_ash_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.FIRE_CLAY_BALL.get()), RankineItems.REFRACTORY_BRICK.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.FIRE_CLAY_BALL.get())).save(consumer, "rankine:refractory_brick_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.PANCAKE_BATTER.get()), RankineItems.PANCAKE.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.PANCAKE_BATTER.get())).save(consumer, "rankine:pancake_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.CORN_EAR.get()), RankineItems.POPCORN.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.CORN_EAR.get())).save(consumer, "rankine:popcorn_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.ASPARAGUS.get()), RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.ROASTED_ASPARAGUS.get())).save(consumer, "rankine:roasted_asparagus_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.BLACK_WALNUT.get()), RankineItems.ROASTED_WALNUT.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.BLACK_WALNUT.get())).save(consumer, "rankine:roasted_walnut_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.COCONUT.get()), RankineItems.TOASTED_COCONUT.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.COCONUT.get())).save(consumer, "rankine:toasted_coconut_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.BREAD), RankineItems.TOAST.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(Items.BREAD)).save(consumer, "rankine:toast_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.DOUGH.get()), Items.BREAD, 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineTags.Items.FLOUR)).save(consumer, "rankine:bread_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.TOFU.get()), RankineItems.COOKED_TOFU.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer, "rankine:cooked_tofu_campfire_cooking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.TUNA.get()), RankineItems.COOKED_TUNA.get(), 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.TUNA.get())).save(consumer, "rankine:cooked_tuna_campfire_cooking");
        //Smoking
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.PANCAKE_BATTER.get()), RankineItems.PANCAKE.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.PANCAKE_BATTER.get())).save(consumer, "rankine:pancake_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.CORN_EAR.get()), RankineItems.POPCORN.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.CORN_EAR.get())).save(consumer, "rankine:popcorn_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.ASPARAGUS.get()), RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.ROASTED_ASPARAGUS.get())).save(consumer, "rankine:roasted_asparagus_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.BLACK_WALNUT.get()), RankineItems.ROASTED_WALNUT.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.BLACK_WALNUT.get())).save(consumer, "rankine:roasted_walnut_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.COCONUT.get()), RankineItems.TOASTED_COCONUT.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.COCONUT.get())).save(consumer, "rankine:toasted_coconut_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.BREAD), RankineItems.TOAST.get(), 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(Items.BREAD)).save(consumer, "rankine:toast_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.TOFU.get()), RankineItems.COOKED_TOFU.get(), 0.35F, 600, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer, "rankine:cooked_tofu_smoking");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(RankineItems.TUNA.get()), RankineItems.COOKED_TUNA.get(), 0.35F, 600, RecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ingredient", has(RankineItems.TUNA.get())).save(consumer, "rankine:cooked_tuna_smoking");


        defaultToolRecipes(consumer, (AlloyItem) RankineItems.PEWTER_INGOT.get(), 11711154, "item.rankine.pewter_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.BRONZE_INGOT.get(), 15510384, "item.rankine.bronze_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.INVAR_INGOT.get(), 13028546, "item.rankine.invar_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.STEEL_INGOT.get(), 7634311, "item.rankine.steel_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.STAINLESS_STEEL_INGOT.get(), 13292499, "item.rankine.stainless_steel_alloying", true);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.NICKEL_SUPERALLOY_INGOT.get(), 12769246, "item.rankine.nickel_superalloy_alloying", true);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.COBALT_SUPERALLOY_INGOT.get(), 6534878, "item.rankine.cobalt_superalloy_alloying", true);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get(), 11113071, "item.rankine.tungsten_heavy_alloy_alloying", true);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.ROSE_GOLD_INGOT.get(), 16756647, "item.rankine.rose_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.WHITE_GOLD_INGOT.get(), 16777207, "item.rankine.white_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.GREEN_GOLD_INGOT.get(), 15728547, "item.rankine.green_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.BLUE_GOLD_INGOT.get(), 8695295, "item.rankine.blue_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.PURPLE_GOLD_INGOT.get(), 11836415, "item.rankine.purple_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.BLACK_GOLD_INGOT.get(), 3684408, "item.rankine.black_gold_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.AMALGAM_INGOT.get(), 13881539, "item.rankine.amalgam_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.ENDER_AMALGAM_INGOT.get(), 492385, "item.rankine.ender_amalgam_alloying", true);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.OSMIRIDIUM_INGOT.get(), 13212593, "item.rankine.osmiridium_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.TITANIUM_ALLOY_INGOT.get(), 13750746, "item.rankine.titanium_alloy_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.NIOBIUM_ALLOY_INGOT.get(), 13750746, "item.rankine.niobium_alloy_alloying", false);
        defaultToolRecipes(consumer, (AlloyItem) RankineItems.ZIRCONIUM_ALLOY_INGOT.get(), 13750746, "item.rankine.zirconium_alloy_alloying", false);

        mimicToolRecipes(consumer, (AlloyItem) RankineItems.BRASS_INGOT.get(), RankineItems.BRONZE_INGOT.get(), 15510384, "item.rankine.brass_alloying", false, "rankine:brass_");
        mimicToolRecipes(consumer, (AlloyItem) RankineItems.METEORIC_IRON.get(), RankineItems.INVAR_INGOT.get(), 13028546, "item.rankine.invar_alloying", false, "rankine:meteoric_iron_");
        mimicToolRecipes(consumer, (AlloyItem) RankineItems.NITINOL_INGOT.get(), RankineItems.TITANIUM_ALLOY_INGOT.get(), 12882104, "item.rankine.nitinol_alloying", false, "rankine:nitinol_");
        mimicToolRecipes(consumer, (AlloyItem) RankineItems.OSMIRIDIUM.get(), RankineItems.OSMIRIDIUM_INGOT.get(), 13212593, "item.rankine.osmiridium_alloying", false, "rankine:native_osmiridium_");


        alloyGear(consumer, RankineItems.ALLOY_GEAR.get(), new AlloyIngredient(Ingredient.of(RankineItems.BRONZE_INGOT.get()), "", null, null, 16777215),RankineItems.BRONZE_INGOT.get(),15510384, "item.rankine.bronze_alloying", "rankine:bronze",null);
        alloyGear(consumer, RankineItems.ALLOY_GEAR.get(), new AlloyIngredient(Ingredient.of(RankineItems.CUPRONICKEL_INGOT.get()), "", null, null, 16777215),RankineItems.CUPRONICKEL_INGOT.get(),11946807, "item.rankine.cupronickel_alloying", "rankine:cupronickel",null);

        alloyRod(consumer, RankineItems.ALLOY_ROD.get(), new AlloyIngredient(Ingredient.of(RankineItems.CAST_IRON_INGOT.get()), "", null, null, 16777215),RankineItems.CAST_IRON_INGOT.get(),2236962, "item.rankine.cast_iron_alloying", "rankine:cast_iron",null);
        alloyRod(consumer, RankineItems.ALLOY_ROD.get(), new AlloyIngredient(Ingredient.of(RankineItems.CUPRONICKEL_INGOT.get()), "", null, null, 16777215),RankineItems.CUPRONICKEL_INGOT.get(),11946807, "item.rankine.cupronickel_alloying", "rankine:cupronickel",null);
        alloyRod(consumer, RankineItems.ALLOY_ROD.get(), new AlloyIngredient(Ingredient.of(RankineItems.STEEL_INGOT.get()), "", null, null, 16777215),RankineItems.STEEL_INGOT.get(),7634311, "item.rankine.steel_alloying", "rankine:steel",null);
        alloyRod(consumer, RankineItems.ALLOY_ROD.get(), new AlloyIngredient(Ingredient.of(RankineItems.FERROCERIUM_INGOT.get()), "", null, null, 16777215),RankineItems.FERROCERIUM_INGOT.get(),7433071, "item.rankine.ferrocerium_alloying", "rankine:ferrocerium",null);
    }

    private void slab(Consumer<FinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void slab(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .define('#', tagIn)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void slab(Consumer<FinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void slab(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn, String group) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .define('#', tagIn)
                .group(group)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shaped(output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn) {
        ShapedRecipeBuilder.shaped(output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', tagIn)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shaped(output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn, String group) {
        ShapedRecipeBuilder.shaped(output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', tagIn)
                .group(group)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void wall(Consumer<FinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void wall(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', tagIn)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void wall(Consumer<FinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void wall(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn, String group) {
        ShapedRecipeBuilder.shaped(output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', tagIn)
                .group(group)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void pressurePlate(Consumer<FinishedRecipe> consumer, Item output, Item input) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void pressurePlate(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("##")
                .define('#', tagIn)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    private void pressurePlate(Consumer<FinishedRecipe> consumer, Item output, Item input, String group) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("##")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void pressurePlate(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> tagIn, String group) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("##")
                .define('#', tagIn)
                .group(group)
                .unlockedBy("has_ingredient", has(tagIn))
                .save(consumer);
    }
    
    
    
    
    
    
    
    
    
    
    
    private void door(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void trapdoor(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void boat(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("# #")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void fence(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input)
                .define('S', Tags.Items.RODS_WOODEN)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void fenceGate(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input)
                .define('S', Tags.Items.RODS_WOODEN)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void bookshelf(Consumer<FinishedRecipe> consumer, Item output, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, 1)
                .pattern("###")
                .pattern("BBB")
                .pattern("###")
                .define('#', input)
                .define('B', Items.BOOK)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void led(Consumer<FinishedRecipe> consumer, Item output, TagKey<Item> dye) {
        ShapedRecipeBuilder.shaped(output, 4)
                .pattern("DRD")
                .pattern("RSR")
                .pattern("DRD")
                .define('D', dye)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('S', RankineItems.SILICON_CARBIDE.get())
                .group("led")
                .unlockedBy("has_silicon_carbide", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.SILICON_CARBIDE.get()))
                .save(consumer);
    }
    
    private void pedestal(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output)
                .pattern(" P ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', input)
                .define('P', RankineTags.Items.STONE_PRESSURE_PLATES)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void bars(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output,16)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void pole(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output,8)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void threeXthree(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                //.setGroup("rankine")
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthree(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                //.setGroup("rankine")
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void centerRing(Consumer<FinishedRecipe> consumer, Item output, int count, Ingredient ring, Ingredient center, String group, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("###")
                .pattern("#A#")
                .pattern("###")
                .define('A', center)
                .define('#', ring)
                .group(group)
                .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void threeXthreeAlloy(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name,int color,@Nullable String langName) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true,"",langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine:alloy_blocks")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthreeAlloy(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger,int color,@Nullable String langName) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true,"",langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine:alloy_blocks")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer);
    }

    private void alloyArmorRecipes(Consumer<FinishedRecipe> consumer, Item ingot, int alloyColor, ResourceLocation alloyRecipe, String alloyLang, String recipeName) {
        alloyHelmet(consumer, RankineItems.ALLOY_HELMET.get(),
                new AlloyIngredient(Ingredient.of(ingot), "", alloyRecipe, alloyLang, alloyColor),
                ingot, alloyColor, alloyLang, recipeName+"helmet",null);
        alloyChestplate(consumer, RankineItems.ALLOY_CHESTPLATE.get(),
                new AlloyIngredient(Ingredient.of(ingot), "", alloyRecipe, alloyLang, alloyColor),
                ingot, alloyColor, alloyLang, recipeName+"chestplate",null);
        alloyLeggings(consumer, RankineItems.ALLOY_LEGGINGS.get(),
                new AlloyIngredient(Ingredient.of(ingot), "", alloyRecipe, alloyLang, alloyColor),
                ingot, alloyColor, alloyLang, recipeName+"leggings",null);
        alloyBoots(consumer, RankineItems.ALLOY_BOOTS.get(),
                new AlloyIngredient(Ingredient.of(ingot), "", alloyRecipe, alloyLang, alloyColor),
                ingot, alloyColor, alloyLang, recipeName+"boots",null);
    }

    //private void defaultToolRecipes(Consumer<IFinishedRecipe> consumer, Item ingot, int alloyColor, ResourceLocation alloyRecipe, String alloyLang) {
    private void defaultToolRecipes(Consumer<FinishedRecipe> consumer, AlloyItem ingot, int alloyColor, String alloyLang, boolean steelRod) {
        String rs = ingot.getRegistryName().toString();
        AlloyIngredient alloyIngot = new AlloyIngredient(Ingredient.of(ingot), "", null, null, 16777215);
        AlloyIngredient rod = new AlloyIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), null, null, null, 16777215);
        if (steelRod) {
            rod = new AlloyIngredient(Ingredient.of(RankineItems.ALLOY_ROD.get()), null, ResourceLocation.tryParse("rankine:alloying/steel_alloying"), "item.rankine.steel_alloying", 3291714);
        }
        String recipeNamePart = "rankine:"+alloyLang.replace("item.rankine.","").replace("alloying","");
        alloyArmorRecipes(consumer, ingot, alloyColor, null, alloyLang, recipeNamePart);
        alloyCrowbar(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","crowbar"))), alloyIngot, ingot, 16777215, null, null,null);
        alloyHammer(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","hammer"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyBlunderbuss(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","blunderbuss"))), alloyIngot, ingot, 16777215, null, null,null);
        alloyPickaxe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","pickaxe"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyAxe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","axe"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyHoe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","hoe"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyShovel(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","shovel"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySpear(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","spear"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySword(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","sword"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyKnife(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","knife"))), alloyIngot, rod, ingot, 16777215, null, null,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySurfRod(consumer, RankineItems.ALLOY_SURF_ROD.get(), alloyIngot, ingot, alloyColor, alloyLang, recipeNamePart,null);
    }

    private void mimicToolRecipes(Consumer<FinishedRecipe> consumer, AlloyItem ingot, Item mimic, int alloyColor, String alloyLang, boolean steelRod, String recipeName) {
        String rs = mimic.getRegistryName().toString();
        AlloyIngredient alloyIngot = new AlloyIngredient(Ingredient.of(ingot), "", null, null, 16777215);
        AlloyIngredient rod = new AlloyIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), null, null, null, 16777215);
        if (steelRod) {
            rod = new AlloyIngredient(Ingredient.of(RankineItems.ALLOY_ROD.get()), null, ResourceLocation.tryParse("rankine:alloying/steel_alloying"), "item.rankine.steel_alloying", 3291714);
        }
        alloyArmorRecipes(consumer, ingot, alloyColor, null, alloyLang, recipeName);
        alloyCrowbar(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","crowbar"))), alloyIngot, ingot, 16777215, null, recipeName,null);
        alloyHammer(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","hammer"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyBlunderbuss(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","blunderbuss"))), alloyIngot, ingot, 16777215, null, recipeName,null);
        alloyPickaxe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","pickaxe"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyAxe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","axe"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyHoe(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","hoe"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyShovel(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","shovel"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySpear(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","spear"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySword(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","sword"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloyKnife(consumer, ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(rs.replace("ingot","knife"))), alloyIngot, rod, ingot, 16777215, null, recipeName,steelRod ? ingot.getDefaultRecipe().toString() : null);
        alloySurfRod(consumer, RankineItems.ALLOY_SURF_ROD.get(), alloyIngot, ingot, alloyColor, alloyLang, recipeName,null);
    }

    private void alloyRod(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 4,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("  #")
                .patternLine(" # ")
                .patternLine("#  ")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_rods")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName+"_rod");
    }

    private void alloyGear(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine(" # ")
                .patternLine("#R#")
                .patternLine(" # ")
                .directAlloyKey('#', input)
                .key('R', Tags.Items.RODS_WOODEN)
                .setGroup("rankine:alloy_gears")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName+"_gear");
    }

    private void alloyHelmet(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color,@Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("   ")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_helmets")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyChestplate(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger,int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("###")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_chestplate")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyLeggings(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color,@Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("# #")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_leggings")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyBoots(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("   ")
                .patternLine("# #")
                .patternLine("# #")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_boots")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyCrowbar(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" ##")
                    .patternLine(" # ")
                    .patternLine("## ")
                    .directAlloyKey('#', input)
                    .setGroup("rankine:alloy_crowbars")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" ##")
                    .patternLine(" # ")
                    .patternLine("## ")
                    .directAlloyKey('#', input)
                    .setGroup("rankine:alloy_crowbars")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"crowbar");
        }

    }

    private void alloySurfRod(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("  #")
                    .patternLine(" #s")
                    .patternLine("# s")
                    .directAlloyKey('#', input)
                    .key('s', Tags.Items.STRING)
                    .setGroup("rankine:alloy_surf_rods")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("  #")
                    .patternLine(" #s")
                    .patternLine("# s")
                    .directAlloyKey('#', input)
                    .key('s', Tags.Items.STRING)
                    .setGroup("rankine:alloy_surf_rods")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"surf_rod");
        }

    }

    private void alloyBlunderbuss(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##f")
                    .patternLine("ss#")
                    .patternLine("  s")
                    .directAlloyKey('#', input)
                    .key('s', ItemTags.LOGS)
                    .key('f', Items.FLINT_AND_STEEL)
                    .setGroup("rankine:alloy_blunderbuss")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##f")
                    .patternLine("ss#")
                    .patternLine("  s")
                    .directAlloyKey('#', input)
                    .key('s', RankineTags.Items.FIREPROOF_LOGS)
                    .key('f', Items.FLINT_AND_STEEL)
                    .setGroup("rankine:alloy_blunderbuss")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"blunderbuss");
        }

    }

    private void alloyPickaxe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("###")
                    .patternLine(" s ")
                    .patternLine(" s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_pickaxes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("###")
                    .patternLine(" s ")
                    .patternLine(" s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_pickaxes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"pickaxe");
        }

    }

    private void alloyHoe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##")
                    .patternLine(" s")
                    .patternLine(" s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_hoes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##")
                    .patternLine(" s")
                    .patternLine(" s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_hoes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"hoe");
        }

    }

    private void alloyAxe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##")
                    .patternLine("#s")
                    .patternLine(" s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_axes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("##")
                    .patternLine("#s")
                    .patternLine(" s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_axes")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"axe");
        }

    }



    private void alloyShovel(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("#")
                    .patternLine("s")
                    .patternLine("s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_shovels")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("#")
                    .patternLine("s")
                    .patternLine("s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_shovels")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"shovel");
        }

    }

    private void alloySword(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("#")
                    .patternLine("#")
                    .patternLine("s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_swords")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine("#")
                    .patternLine("#")
                    .patternLine("s")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_swords")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"sword");
        }

    }

    private void alloyHammer(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true, inheritRecipe == null ? "" : inheritRecipe, langName == null ? "" : langName, color)
                    .patternLine("###")
                    .patternLine("#s#")
                    .patternLine(" s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_hammers")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true, inheritRecipe == null ? "" : inheritRecipe, langName == null ? "" : langName, color)
                    .patternLine("###")
                    .patternLine("#s#")
                    .patternLine(" s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_hammers")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"hammer");
        }

    }

    private void alloyKnife(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" #")
                    .patternLine("s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_knives")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" #")
                    .patternLine("s ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_knives")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"knife");
        }

    }

    private void alloySpear(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, Item trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        if (recipeName == null) {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" ##")
                    .patternLine(" s#")
                    .patternLine("s  ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_spears")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer);
        } else {
            AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                    .patternLine(" ##")
                    .patternLine(" s#")
                    .patternLine("s  ")
                    .directAlloyKey('#', input)
                    .directAlloyKey('s', rod)
                    .setGroup("rankine:alloy_spears")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"spear");
        }
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer, new ResourceLocation("rankine",name));
    }
    private void twoXtwo(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void OneToX(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
            ShapelessRecipeBuilder.shapeless(output, count)
            .requires(input)
            //.setGroup("rankine")
            .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
            .save(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToX(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapeless(output, count)
                .requires(input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void OneToXAlloy(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger, String name) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                .setGroup("rankine:alloy_nuggets")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToXAlloy(Consumer<FinishedRecipe> consumer, Item output, Item input, int count, String triggerName, Item trigger) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                .setGroup("rankine:alloy_nuggets")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer);
    }

    private void OneToXTag(Consumer<FinishedRecipe> consumer, Item output, String group, TagKey<Item> input, int count, String triggerName, Item trigger) {
        ShapelessRecipeBuilder.shapeless(output, count)
                .requires(input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    
    private void hLine(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String group, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }    
    private void hLine(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("###")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void rod(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String triggerName, Item trigger) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("  #")
                .pattern(" # ")
                .pattern("#  ")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void ladder(Consumer<FinishedRecipe> consumer, Item output, int count, TagKey<Item> input) {
        ShapedRecipeBuilder.shaped(output, count)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }

}