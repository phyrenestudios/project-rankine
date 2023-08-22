package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.blocks.block_enums.*;
import com.cannolicatfish.rankine.data.builders.AlloyCraftingRecipeBuilder;
import com.cannolicatfish.rankine.data.builders.AlloyIngredient;
import com.cannolicatfish.rankine.data.builders.BeehiveOvenRecipeBuilder;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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
    
    
    public RankineRecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //MINECRAFT OVERRIDE
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.LOGS_THAT_BURN), RecipeCategory.MISC, RankineItems.ASH.get(), 0.1F, 200).unlockedBy("has_logs", has(ItemTags.LOGS_THAT_BURN)).save(consumer, "minecraft:charcoal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BRICKS).define('#', Items.BRICK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_brick", has(Items.BRICK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.END_STONE_BRICKS, 2).define('#', Blocks.END_STONE).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_end_stone", has(Blocks.END_STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.QUARTZ_BRICKS, 2).define('#', Blocks.QUARTZ_BLOCK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.STONE_BRICKS, 2).define('#', Blocks.STONE).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_stone", has(Blocks.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.NETHER_BRICKS).define('#', Items.NETHER_BRICK).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_netherbrick", has(Items.NETHER_BRICK)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.RED_NETHER_BRICKS).requires(Blocks.NETHER_BRICKS).requires(Items.NETHER_WART).unlockedBy("has_nether_wart", has(Items.NETHER_WART)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.PRISMARINE_BRICKS).define('#', Items.PRISMARINE_SHARD).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Blocks.DARK_PRISMARINE).requires(Blocks.PRISMARINE_BRICKS).requires(Tags.Items.DYES_BLACK).unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(consumer);


        //ALTERNATIVE RECIPES
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.SILT.get()), RecipeCategory.MISC, Items.GLASS, 0.1F, 200).unlockedBy("has_ingredient", has(RankineBlocks.SILT.get().asItem())).save(consumer, "rankine:glass_from_silt_smelting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TORCH, 3).pattern("C").pattern("S").define('C', RankineItems.LIGNITE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.LIGNITE.get())).group("torch").save(consumer, "rankine:torch_from_lignite");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TORCH, 6).pattern("C").pattern("S").define('C', RankineItems.BITUMINOUS_COAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.BITUMINOUS_COAL.get())).group("torch").save(consumer, "rankine:torch_from_bituminous_coal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TORCH, 8).pattern("C").pattern("S").define('C', RankineItems.ANTHRACITE_COAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.ANTHRACITE_COAL.get())).group("torch").save(consumer, "rankine:torch_from_anthracite_coal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.TORCH, 8).pattern("C").pattern("S").define('C', RankineTags.Items.COKE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.COKE)).group("torch").save(consumer, "rankine:torch_from_coke");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.SOUL_TORCH, 2).pattern("C").pattern("S").define('C', RankineTags.Items.SULFUR).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).group("torch").save(consumer, "rankine:torch_from_sulfur");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUCKET, 2).pattern("I I").pattern(" I ").define('I', RankineItems.STEEL_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.STEEL_INGOT.get())).save(consumer, "rankine:bucket_from_steel");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUCKET, 1).pattern("I I").pattern(" I ").define('I', RankineItems.BRASS_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.BRASS_INGOT.get())).save(consumer, "rankine:bucket_from_brass");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BUCKET, 1).pattern("I I").pattern(" I ").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:bucket_from_crafting_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.GEMS_DIAMOND).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:diamond_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GOLDEN_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.INGOTS_GOLD).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:gold_horse_armor_from_saddle_tree");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.IRON_HORSE_ARMOR, 1).pattern("I I").pattern("III").pattern("ITI").define('I', Tags.Items.INGOTS_IRON).define('T', RankineItems.SADDLE_TREE.get()).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:iron_horse_armor_from_saddle_tree");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER,2).requires(Items.CHARCOAL).requires(RankineTags.Items.SULFUR).requires(Items.BONE_MEAL).unlockedBy("has_ingredient", has(RankineItems.SULFUR.get())).save(consumer, "rankine:gunpowder_from_bonemeal");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER,2).requires(Items.CHARCOAL).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.SALTPETER).unlockedBy("has_ingredient", has(RankineItems.SULFUR.get())).save(consumer, "rankine:gunpowder_from_saltpeter");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COAL,2).requires(RankineItems.ANTHRACITE_COAL.get()).unlockedBy("has_ingredient", has(RankineItems.ANTHRACITE_COAL.get())).group("coal").save(consumer, "rankine:coal_from_anthracite");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COAL,3).requires(RankineItems.BITUMINOUS_COAL.get()).requires(RankineItems.BITUMINOUS_COAL.get()).unlockedBy("has_ingredient", has(RankineItems.BITUMINOUS_COAL.get())).group("coal").save(consumer, "rankine:coal_from_bituminous");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COAL,2).requires(RankineItems.LIGNITE.get()).requires(RankineItems.LIGNITE.get()).requires(RankineItems.LIGNITE.get()).unlockedBy("has_ingredient", has(RankineItems.LIGNITE.get())).group("coal").save(consumer, "rankine:coal_from_lignite");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CAKE, 1).pattern("MMM").pattern("SES").pattern("GGG").define('M',Items.MILK_BUCKET).define('S', Items.SUGAR).define('E', Tags.Items.EGGS).define('G', RankineTags.Items.GRAIN).unlockedBy("has_ingredient", has(RankineTags.Items.GRAIN)).save(consumer, "rankine:cake_from_grains");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 16).pattern(" C ").pattern("IMI").pattern(" I ").define('C',RankineItems.CORK.get()).define('I', RankineTags.Items.HARDENED_GLASS).define('M', RankineTags.Items.INGOTS_MISCHMETAL).unlockedBy("has_ingredient", has(RankineItems.CORK.get())).save(consumer, "rankine:xpbottle_from_mischmetal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLASS_BOTTLE, 8).pattern(" C ").pattern("I I").pattern(" I ").define('C',RankineItems.CORK.get()).define('I', Tags.Items.GLASS).unlockedBy("has_ingredient", has(RankineItems.CORK.get())).save(consumer, "rankine:glass_bottle_from_cork");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BELL, 1).pattern("SRS").pattern("SBS").pattern("S S").define('S', Tags.Items.STONE).define('R', Tags.Items.RODS_WOODEN).define('B', RankineItems.BRASS_INGOT.get()).unlockedBy("has_ingredient", has(RankineItems.BRASS_INGOT.get())).save(consumer, "rankine:bell_from_brass");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LODESTONE, 1).pattern("SSS").pattern("SLS").pattern("SSS").define('S', Items.CHISELED_STONE_BRICKS).define('L', RankineItems.LODESTONE.get()).unlockedBy("has_ingredient", has(RankineItems.LODESTONE.get())).save(consumer, "rankine:lodestone_from_lodestone");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BLAST_FURNACE, 1).pattern("SBS").pattern("SFS").pattern("BBB").define('S',RankineTags.Items.SHEETMETAL).define('F', Items.FURNACE).define('B', ItemTags.STONE_BRICKS).unlockedBy("has_ingredient", has(RankineTags.Items.SHEETMETAL)).save(consumer, "rankine:blast_furnace_from_sheetmetal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SADDLE, 1).pattern("LLL").pattern("LSL").define('S', RankineItems.SADDLE_TREE.get()).define('L', Tags.Items.LEATHER).unlockedBy("has_ingredient", has(RankineItems.SADDLE_TREE.get())).save(consumer, "rankine:saddle_from_saddle_tree");
        //ShapedRecipeBuilder.shapedRecipe(Items.SCAFFOLDING, 1).patternLine("RRR").patternLine("SLS").key('S', RankineItems.SADDLE_TREE.get()).key('L', Tags.Items.LEATHER).addCriterion("has_ingredient", hasItem(RankineItems.SADDLE_TREE.get())).build(consumer, "rankine:saddle_from_saddle_tree");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING, 1).pattern("SSS").define('S', RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineTags.Items.CROPS_COTTON)).save(consumer, "rankine:string_from_cotton");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAINTING, 1).pattern("SSS").pattern("SMS").pattern("SSS").define('M', RankineItems.ARTIST_CONK_MUSHROOM.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineItems.ARTIST_CONK_MUSHROOM.get())).save(consumer, "rankine:painting_from_mushroom");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER,1).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).requires(RankineItems.SYNTHETIC_LEATHER.get()).unlockedBy("has_ingredient", has(RankineItems.SYNTHETIC_LEATHER.get())).save(consumer, "rankine:leather_from_synthetic_leather");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER,1).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).requires(RankineItems.BEAVER_PELT.get()).unlockedBy("has_ingredient", has(RankineItems.BEAVER_PELT.get())).save(consumer, "rankine:leather_from_beaver_pelt");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MUSHROOM_STEW,1).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(RankineTags.Items.EDIBLE_MUSHROOMS).requires(Items.BOWL).unlockedBy("has_ingredient", has(RankineTags.Items.EDIBLE_MUSHROOMS)).save(consumer, "rankine:mushroom_stew_from_edible_mushrooms");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RABBIT_STEW,1).requires(Items.BAKED_POTATO).requires(Items.COOKED_RABBIT).requires(Items.BOWL).requires(Items.CARROT).requires(RankineTags.Items.EDIBLE_MUSHROOMS).unlockedBy("has_ingredient", has(Items.COOKED_RABBIT)).save(consumer, "rankine:rabbit_stew_from_edible_mushrooms");


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR,2).requires(RankineItems.BONE_CHAR.get()).requires(Items.SUGAR_CANE).unlockedBy("has_ingredient", has(RankineItems.BONE_CHAR.get())).save(consumer, "rankine:sugar_from_bone_char");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR,2).requires(RankineItems.MAPLE_SYRUP.get()).unlockedBy("has_ingredient", has(RankineItems.MAPLE_SYRUP.get())).save(consumer, "rankine:sugar_from_maple_syrup");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL,4).requires(Items.WATER_BUCKET).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineTags.Items.SULFUR).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).save(consumer, "rankine:bonemeal_from_ash");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL,12).requires(Items.WATER_BUCKET).requires(RankineItems.BONE_ASH.get()).requires(RankineItems.BONE_ASH.get()).requires(RankineTags.Items.SULFUR).unlockedBy("has_ingredient", has(RankineTags.Items.SULFUR)).save(consumer, "rankine:bonemeal_from_bone_ash");

        //ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 1).patternLine("SS ").patternLine("SB ").patternLine("  S").key('S', Tags.Items.STRING).key('L', RankineItems.LODESTONE.get()).addCriterion("has_ingredient", hasItem(RankineItems.LODESTONE.get())).build(consumer, "rankine:lodestone_from_lodestone");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHAIN, 1).pattern("N").pattern("I").pattern("N").define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:chain_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HOPPER, 1).pattern("I I").pattern("ICI").pattern(" I ").define('C', Tags.Items.CHESTS).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:hopper_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.RAIL, 16).pattern("I I").pattern("ISI").pattern("I I").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:rail_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.POWERED_RAIL, 6).pattern("I I").pattern("ISI").pattern("IRI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.COLORED_GOLD_INGOTS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.COLORED_GOLD_INGOTS)).save(consumer, "rankine:powered_rail_from_golds");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.POWERED_RAIL, 8).pattern("I I").pattern("ISI").pattern("IRI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.RODS_GRAPHITE).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.COLORED_GOLD_INGOTS)).save(consumer, "rankine:powered_rail_from_graphite");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DETECTOR_RAIL, 6).pattern("I I").pattern("IRI").pattern("IPI").define('P', RankineTags.Items.STONE_PRESSURE_PLATES).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:detector_rail_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.ACTIVATOR_RAIL, 6).pattern("ISI").pattern("ITI").pattern("ISI").define('S', Tags.Items.RODS_WOODEN).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('T', Items.REDSTONE_TORCH).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:activator_rail_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.MINECART, 1).pattern("I I").pattern("III").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:minecart_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SHEARS, 1).pattern("I ").pattern(" I").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer, "rankine:shears_from_metals");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NAME_TAG, 1).pattern(" I ").pattern("I I").pattern("CI ").define('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).define('C',RankineItems.CORK.get()).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STAINLESS_STEEL)).save(consumer, "rankine:nametag_from_stainless_steel");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DAYLIGHT_DETECTOR, 6).pattern("C").pattern("S").define('S', ItemTags.WOODEN_SLABS).define('C',RankineItems.CADMIUM_TELLURIDE_CELL.get()).unlockedBy("has_ingredient", has(RankineItems.CADMIUM_TELLURIDE_CELL.get())).save(consumer, "rankine:daylight_detector_from_cell");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PISTON, 1).pattern("PPP").pattern("CIC").pattern("CRC").define('P', ItemTags.PLANKS).define('C', Tags.Items.COBBLESTONE_NORMAL).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(Items.REDSTONE)).save(consumer, "rankine:piston_from_metals");



        //RANKINE
        for (Block BLOCK : RankineLists.MISC_BLOCKS) {
            String baseName = getItemName(BLOCK);
            Block SLAB = RankineLists.MISC_SLABS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block STAIRS = RankineLists.MISC_STAIRS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            Block WALL = RankineLists.MISC_WALLS.get(RankineLists.MISC_BLOCKS.indexOf(BLOCK));
            RecipeProvider.slab(consumer,RecipeCategory.MISC, SLAB.asItem(),BLOCK.asItem());
            stairs(consumer,STAIRS.asItem(),BLOCK.asItem());
            wall(consumer,RecipeCategory.MISC, WALL.asItem(),BLOCK.asItem());
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BLOCK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BLOCK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(BLOCK)).save(consumer,"rankine:"+baseName+"_from_stairs");

            if (Arrays.asList(RankineBlocks.SKARN.get(),RankineBlocks.BRECCIA.get()).contains(BLOCK)) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), RecipeCategory.MISC, SLAB, 2).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_slab_from_"+baseName+"_stonecutting");
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), RecipeCategory.MISC, STAIRS).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_stairs_from_"+baseName+"_stonecutting");
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(BLOCK), RecipeCategory.MISC, WALL).unlockedBy("has_ingredient", has(BLOCK)).save(consumer, "rankine:"+baseName+"_wall_from_"+baseName+"_stonecutting");

            }
        }

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FLINT_AND_STEEL, 1).pattern("P ").pattern(" F").define('P', RankineItems.PYRITE.get()).define('F', RankineTags.Items.FLINT).unlockedBy("has_ingredient", has(RankineItems.PYRITE.get())).save(consumer, "rankine:flint_and_steel_from_pyrite");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIVING_BOOTS.get(), 1).pattern("R R").pattern("I I").pattern("I I").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIVING_CHESTPLATE.get(), 1).pattern("IRI").pattern("III").pattern("III").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIVING_LEGGINGS.get(), 1).pattern("IRI").pattern("I I").pattern("I I").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIVING_HELMET.get(), 1).pattern("III").pattern("IGI").pattern("RRR").define('I', RankineTags.Items.INGOTS_BRASS).define('R', RankineTags.Items.RUBBER).define('G', Tags.Items.GLASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),4).requires(RankineItems.JUTE.get()).unlockedBy("has_ingredient", has(RankineItems.JUTE.get())).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_jute");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),2).requires(Items.VINE).unlockedBy("has_ingredient", has(Items.VINE)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_vine");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),2).requires(Items.WEEPING_VINES).unlockedBy("has_ingredient", has(Items.WEEPING_VINES)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_weeping_vines");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),2).requires(Items.TWISTING_VINES).unlockedBy("has_ingredient", has(Items.TWISTING_VINES)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_twisting_vines");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),2).requires(Items.TALL_GRASS).unlockedBy("has_ingredient", has(Items.TALL_GRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_tall_grass");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),2).requires(Items.LARGE_FERN).unlockedBy("has_ingredient", has(Items.LARGE_FERN)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_large_fern");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),1).requires(RankineItems.WILLOW_BRANCHLET.get()).unlockedBy("has_ingredient", has(RankineItems.WILLOW_BRANCHLET.get())).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_willow");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),1).requires(Items.GRASS).unlockedBy("has_ingredient", has(Items.GRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_grass");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),1).requires(Items.SEAGRASS).unlockedBy("has_ingredient", has(Items.SEAGRASS)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_seagrass");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),1).requires(Items.FERN).unlockedBy("has_ingredient", has(Items.FERN)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_fern");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PLANT_FIBER.get(),1).requires(RankineItems.SHORT_GRASS.get()).requires(RankineItems.SHORT_GRASS.get()).unlockedBy("has_ingredient", has(Items.STICK)).group("plant_fiber").save(consumer, "rankine:plant_fiber_from_short_grass");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.STUMP.get()), RecipeCategory.MISC, RankineItems.ASH.get(), 0.2F, 200).unlockedBy("has_ingredient", has(RankineItems.STUMP.get())).save(consumer, "rankine:ash_from_stump_smelting");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.STUMP.get()), RecipeCategory.MISC, RankineItems.ASH.get(), 0.2F, 600).unlockedBy("has_ingredient", has(RankineItems.STUMP.get())).save(consumer, "rankine:ash_from_stump_campfire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD,  1).pattern("###").define('#', RankineItems.MILLET.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.MILLET.get())).save(consumer,"rankine:bread_from_millet");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD,  1).pattern("###").define('#', RankineItems.OATS.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.OATS.get())).save(consumer,"rankine:bread_from_oats");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD,  1).pattern("###").define('#', RankineItems.SORGHUM.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.SORGHUM.get())).save(consumer,"rankine:bread_from_sorghum");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD,  1).pattern("###").define('#', RankineItems.RICE.get()).group("gf_bread").unlockedBy("has_ingredient", has(RankineItems.RICE.get())).save(consumer,"rankine:bread_from_rice");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD, 1).pattern("###").define('#', RankineItems.RYE.get()).group("bread").unlockedBy("has_ingredient", has(RankineItems.RYE.get())).save(consumer,"rankine:bread_from_rye");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREAD, 1).pattern("###").define('#', RankineItems.BARLEY.get()).group("bread").unlockedBy("has_ingredient", has(RankineItems.BARLEY.get())).save(consumer,"rankine:bread_from_barley");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.RYE_BALE.get(), 1).pattern("###").pattern("###").pattern("###").define('#', RankineItems.RYE.get()).group("bales").unlockedBy("has_ingredient", has(RankineItems.RYE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.RYE.get(),9).requires(RankineBlocks.RYE_BALE.get()).unlockedBy("has_ingredient", has(RankineItems.RYE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BARLEY_BALE.get(), 1).pattern("###").pattern("###").pattern("###").define('#', RankineItems.BARLEY.get()).group("bales").unlockedBy("has_ingredient", has(RankineItems.BARLEY.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.BARLEY.get(),9).requires(RankineBlocks.BARLEY_BALE.get()).unlockedBy("has_ingredient", has(RankineItems.BARLEY.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.SORGHUM_BALE.get(), 1).pattern("###").pattern("###").pattern("###").define('#', RankineItems.SORGHUM.get()).group("bales").unlockedBy("has_ingredient", has(RankineItems.SORGHUM.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.SORGHUM.get(),9).requires(RankineBlocks.SORGHUM_BALE.get()).unlockedBy("has_ingredient", has(RankineItems.SORGHUM.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.OAT_BALE.get(), 1).pattern("###").pattern("###").pattern("###").define('#', RankineItems.OATS.get()).group("bales").unlockedBy("has_ingredient", has(RankineItems.OATS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.OATS.get(),9).requires(RankineBlocks.OAT_BALE.get()).unlockedBy("has_ingredient", has(RankineItems.OATS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.MILLET_BALE.get(), 1).pattern("###").pattern("###").pattern("###").define('#', RankineItems.MILLET.get()).group("bales").unlockedBy("has_ingredient", has(RankineItems.MILLET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.MILLET.get(),9).requires(RankineBlocks.MILLET_BALE.get()).unlockedBy("has_ingredient", has(RankineItems.MILLET.get())).save(consumer);

        //ore conversion recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COAL_ORE,1).requires(RankineItems.COAL_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.COAL_ORE.get())).group("ore_conversion").save(consumer, "rankine:coal_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.IRON_ORE,1).requires(RankineItems.IRON_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.IRON_ORE.get())).group("ore_conversion").save(consumer, "rankine:iron_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COPPER_ORE,1).requires(RankineItems.COPPER_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.COPPER_ORE.get())).group("ore_conversion").save(consumer, "rankine:copper_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GOLD_ORE,1).requires(RankineItems.GOLD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.GOLD_ORE.get())).group("ore_conversion").save(consumer, "rankine:gold_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.REDSTONE_ORE,1).requires(RankineItems.REDSTONE_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.REDSTONE_ORE.get())).group("ore_conversion").save(consumer, "rankine:redstone_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LAPIS_ORE,1).requires(RankineItems.LAPIS_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.LAPIS_ORE.get())).group("ore_conversion").save(consumer, "rankine:lapis_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.DIAMOND_ORE,1).requires(RankineItems.DIAMOND_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.DIAMOND_ORE.get())).group("ore_conversion").save(consumer, "rankine:diamond_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EMERALD_ORE,1).requires(RankineItems.EMERALD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.EMERALD_ORE.get())).group("ore_conversion").save(consumer, "rankine:emerald_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NETHER_QUARTZ_ORE,1).requires(RankineItems.NETHER_QUARTZ_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.NETHER_QUARTZ_ORE.get())).group("ore_conversion").save(consumer, "rankine:nether_quartz_ore_conversion");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NETHER_GOLD_ORE,1).requires(RankineItems.NETHER_GOLD_ORE.get()).unlockedBy("has_ingredient", has(RankineItems.NETHER_GOLD_ORE.get())).group("ore_conversion").save(consumer, "rankine:nether_gold_ore_conversion");

        //pumice soap recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COBBLESTONE,1).requires(Items.MOSSY_COBBLESTONE).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:cobblestone_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STONE_BRICKS,1).requires(Items.MOSSY_STONE_BRICKS).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:stone_bricks_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLAZE_POWDER,1).requires(Items.MAGMA_CREAM).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:blaze_powder_from_puumice_soap");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PISTON,1).requires(Items.STICKY_PISTON).requires(RankineItems.PUMICE_SOAP.get()).unlockedBy("has_ingredient", has(RankineItems.PUMICE_SOAP.get())).save(consumer, "rankine:piston_from_pumice_soap");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.MINERAL_WOOL.get(),1).requires(RankineTags.Items.MINERAL_WOOL).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(RankineTags.Items.MINERAL_WOOL)).save(consumer, "rankine:mineral_wool_from_colors");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.TERRACOTTA,1).requires(ItemTags.TERRACOTTA).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(ItemTags.TERRACOTTA)).save(consumer, "rankine:terracotta_from_colors");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GLASS,1).requires(Tags.Items.GLASS).requires(RankineItems.BLEACH.get()).unlockedBy("has_ingredient", has(Tags.Items.GLASS)).save(consumer, "rankine:glass_from_colors");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.SLATE_STEPPING_STONES.get(), 4).pattern("###").define('#', StoneBlocks.SLATE.getStone()).unlockedBy("has_ingredient", has(StoneBlocks.SLATE.getStone())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.EMERGENCY_FLOTATION_DEVICE.get(), 1).pattern(" # ").pattern("#C#").pattern(" # ").define('C', RankineItems.CARBON_DIOXIDE_GAS_BOTTLE.get()).define('#', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.SOD_BLOCK.get(), 4).pattern("##").pattern("##").define('#', RankineTags.Items.GRASS_BLOCKS).unlockedBy("has_ingredient", has(RankineTags.Items.GRASS_BLOCKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BLASTING_POWDER.get(), 1).pattern("#R").pattern("R#").define('#', Tags.Items.GUNPOWDER).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(Tags.Items.GUNPOWDER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.CARBON_NUGGET.get(),1).requires(RankineTags.Items.COKE).unlockedBy("has_ingredient", has(RankineTags.Items.COKE)).group("carbon_nugget").save(consumer, "rankine:carbon_from_coke");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.CARBON_NUGGET.get(),3).requires(RankineTags.Items.GRAPHITE).unlockedBy("has_ingredient", has(RankineTags.Items.GRAPHITE)).group("carbon_nugget").save(consumer, "rankine:carbon_from_graphite");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.CARBON_NUGGET.get(), 1).pattern("##").pattern("##").define('#', Items.CHARCOAL).unlockedBy("has_ingredient", has(Items.CHARCOAL)).save(consumer,"rankine:carbon_nugget_from_charcoal");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.FIRE_CLAY.get(), 1).pattern("##").pattern("##").define('#', RankineItems.FIRE_CLAY_BALL.get()).unlockedBy("has_ingredient", has(RankineItems.KAOLINITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.PORCELAIN_CLAY.get(), 1).pattern("##").pattern("##").define('#', RankineItems.PORCELAIN_CLAY_BALL.get()).unlockedBy("has_ingredient", has(RankineItems.PORCELAIN_CLAY_BALL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.KAOLINITE_BLOCK.get(), 1).pattern("##").pattern("##").define('#', RankineItems.KAOLINITE.get()).unlockedBy("has_ingredient", has(RankineItems.KAOLINITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.HALLOYSITE_BLOCK.get(), 1).pattern("##").pattern("##").define('#', RankineItems.HALLOYSITE.get()).unlockedBy("has_ingredient", has(RankineItems.HALLOYSITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.PACKED_SNOW.get(), 2).pattern("B#").pattern("#B").define('#', Items.SNOW_BLOCK).define('B', Items.SNOWBALL).unlockedBy("has_ingredient", has(Items.SNOWBALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.ICE_BRICKS.get(), 2).pattern("B#").pattern("#B").define('#', RankineTags.Items.ICE).define('B', Items.SNOWBALL).unlockedBy("has_ingredient", has(Items.SNOWBALL)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.BANDAGE.get(),3).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(Items.PAPER).requires(Items.PAPER).requires(Items.PAPER).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_paper");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.BANDAGE.get(),6).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_cotton");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.BANDAGE.get(),12).requires(RankineItems.RESIN_BUCKET.get()).requires(RankineItems.ALOE.get()).requires(RankineItems.STINGING_NETTLE.get()).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineItems.ALOE.get())).group("bandage").save(consumer, "rankine:bandage_from_nettle");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.GUN_COTTON.get(),8).requires(RankineTags.Items.SALTPETER).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).requires(RankineTags.Items.CROPS_COTTON).unlockedBy("has_ingredient", has(RankineTags.Items.SALTPETER)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.CINNAMON_TOAST.get(),1).requires(RankineItems.TOAST.get()).requires(RankineItems.CINNAMON.get()).requires(Items.SUGAR).unlockedBy("has_ingredient", has(RankineItems.TOAST.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PINA_COLADA.get(),1).requires(RankineTags.Items.ICE).requires(RankineItems.COCONUT.get()).requires(RankineTags.Items.PINEAPPLE).unlockedBy("has_ingredient", has(RankineItems.PINEAPPLE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PULP.get(),1).requires(Items.WATER_BUCKET).requires(RankineTags.Items.CLAY_BALLS).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).unlockedBy("has_ingredient", has(RankineTags.Items.SAWDUST)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.PULP.get(),4).requires(Items.WATER_BUCKET).requires(RankineItems.SODIUM_HYDROXIDE.get()).requires(RankineItems.SODIUM_SULFIDE.get()).requires(RankineTags.Items.CLAY_BALLS).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).requires(RankineTags.Items.SAWDUST).unlockedBy("has_ingredient", has(RankineTags.Items.SAWDUST)).save(consumer,"rankine:pulp_kraft");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.TRAIL_MIX.get(),1).requires(RankineTags.Items.BERRIES).requires(RankineItems.ROASTED_WALNUT.get()).requires(RankineItems.TOASTED_COCONUT.get()).unlockedBy("has_ingredient", has(RankineTags.Items.BERRIES)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PAPER,1).requires(RankineItems.ALLOY_TEMPLATE.get()).unlockedBy("has_ingredient", has(RankineItems.ALLOY_TEMPLATE.get())).save(consumer, "rankine:alloy_template_clear");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.GROUND_TAP.get(),1).requires(RankineItems.METAL_PIPE.get()).unlockedBy("has_ingredient", has(RankineItems.METAL_PIPE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.VULCANIZED_RUBBER.get(),2).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_sulfur");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.VULCANIZED_RUBBER.get(),2).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineItems.SODIUM_SULFIDE.get()).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_sodium_sulfide");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.VULCANIZED_RUBBER.get(),4).requires(RankineItems.DRY_RUBBER.get()).requires(Items.BONE_MEAL).requires(RankineItems.TELLURIUM.get()).requires(RankineTags.Items.NUGGETS_CARBON).unlockedBy("has_ingredient", has(RankineItems.DRY_RUBBER.get())).save(consumer, "rankine:vulcanized_rubber_from_tellurium");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.COMPRESSED_BIOMASS.get(),1).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).requires(RankineItems.BIOMASS.get()).unlockedBy("has_ingredient", has(RankineItems.BIOMASS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineTags.Items.CARBON).requires(RankineTags.Items.CARBON).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_carbon");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineTags.Items.SILICON_CARBIDE).requires(RankineTags.Items.SILICON_CARBIDE).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_silicon_carbide");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineItems.MAGNESIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_magnesia");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.REFRACTORY_BRICK.get()).requires(RankineItems.MAGNESITE.get()).requires(RankineTags.Items.NUGGETS_CHROMIUM).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:high_refractory_brick_from_magnesite");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.ALUMINA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_alumina");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.HAFNIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_hafnia");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),1).requires(RankineItems.HIGH_REFRACTORY_BRICK.get()).requires(RankineItems.ZIRCONIA.get()).unlockedBy("has_ingredient", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer, "rankine:ultra-high_refractory_brick_from_zirconia");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SYNTHETIC_LEATHER.get(), 1).pattern("PPP").pattern("CSC").pattern("PPP").define('S', RankineTags.Items.SALTPETER).define('C', RankineItems.CAMPHOR_BASIL_LEAF.get()).define('P', RankineItems.PULP.get()).unlockedBy("has_ingredient", has(RankineItems.PULP.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.TOFU_CURRY.get(),1).requires(Items.BOWL).requires(RankineItems.TOFU.get()).requires(Items.PUMPKIN).requires(RankineItems.MAPLE_SYRUP.get()).requires(RankineTags.Items.NUTS_COCONUT).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.MORTAR.get(),4).requires(RankineItems.CEMENT_MIX.get()).requires(ItemTags.SAND).unlockedBy("has_ingredient", has(RankineItems.CEMENT_MIX.get())).group("mortar").save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_KNIFE.get(), 1).pattern(" F").pattern("F ").define('F', Items.FLINT).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_AXE.get(), 1).pattern("RF").pattern("S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_PICKAXE.get(), 1).pattern("FRF").pattern(" S ").pattern(" S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_SPEAR.get(), 1).pattern(" FF").pattern(" RF").pattern("S  ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_HOE.get(), 1).pattern("RF").pattern("S ").pattern("S ").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FLINT_SHOVEL.get(), 1).pattern("F").pattern("R").pattern("S").define('F', Items.FLINT).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.FLINT)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.WOODEN_HAMMER.get(), 1).pattern("PPP").pattern("PSP").pattern(" S ").define('P', ItemTags.PLANKS).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.STONE_HAMMER.get(), 1).pattern("PPP").pattern("PSP").pattern(" S ").define('P', ItemTags.STONE_TOOL_MATERIALS).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.COBBLESTONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.BUILDING_TOOL.get(), 1).pattern("PRP").pattern("PRP").pattern(" S ").define('P', Tags.Items.STONE).define('S', Tags.Items.RODS_WOODEN).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(Tags.Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ROPE_COIL_ARROW.get(), 1).pattern("T").pattern("S").pattern("F").define('F', Tags.Items.FEATHERS).define('T', RankineTags.Items.ROPE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(RankineTags.Items.ROPE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FIRE_EXTINGUISHER.get(), 1).pattern("INI").pattern("IWI").pattern("III").define('W', Items.WATER_BUCKET).define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.PROSPECTING_STICK.get(), 1).pattern(" SN").pattern(" RS").pattern("S  ").define('N', Tags.Items.NUGGETS).define('R', RankineItems.ROPE.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.NUGGETS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.HARDNESS_TESTER.get(), 1).pattern("  R").pattern(" S ").pattern("G  ").define('G', Tags.Items.GLASS).define('R', Tags.Items.STONE).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Tags.Items.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ELEMENT_INDEXER.get(), 1).pattern("III").pattern("ICI").pattern("III").define('I', RankineTags.Items.CRAFTING_METAL_INGOTS).define('C', RankineItems.CADMIUM_TELLURIDE_CELL.get()).unlockedBy("has_ingredient", has(RankineItems.CADMIUM_TELLURIDE_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_COBBLING.get(), 1).pattern("MHM").pattern("WML").pattern(" M ").define('H', RankineTags.Items.HAMMERS).define('M', RankineTags.Items.INGOTS_ROSE_METAL).define('W', Items.WATER_BUCKET).define('L', Items.LAVA_BUCKET).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ALTIMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_BRASS).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.BIOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_ROSE_METAL).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.PHOTOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_NICKEL_SILVER).define('R', Items.DAYLIGHT_DETECTOR).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_NICKEL_SILVER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SPEEDOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', RankineTags.Items.INGOTS_DURALUMIN).define('R', Tags.Items.DUSTS_REDSTONE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_DURALUMIN)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.THERMOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', Tags.Items.GLASS).define('R', RankineTags.Items.MERCURY).unlockedBy("has_ingredient", has(RankineTags.Items.MERCURY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.MAGNETOMETER.get(), 1).pattern(" I ").pattern("IRI").pattern(" I ").define('I', Tags.Items.DUSTS_REDSTONE).define('R', RankineTags.Items.INGOTS_POTASSIUM).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_POTASSIUM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_HASTENING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_ROSE_GOLD).define('R', Tags.Items.STORAGE_BLOCKS_EMERALD).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_ENDURING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_BLUE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_GARNET).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BLUE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_REPULSING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_BLACK_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_RUBY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BLACK_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_TIMESAVING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_STERLING_SILVER).define('R', RankineTags.Items.STORAGE_BLOCKS_SAPPHIRE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STERLING_SILVER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_POWERING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_NEPTUNIUM_ALLOY).define('R', Items.CONDUIT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_NEPTUNIUM_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_PROMISING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_WHITE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_TOPAZ).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_WHITE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_SOFTENING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_PURPLE_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_PEARL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_PURPLE_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_LEVITATING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_OSMIRIDIUM).define('R', RankineTags.Items.STORAGE_BLOCKS_OPAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_OSMIRIDIUM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_INVIGORATING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_GREEN_GOLD).define('R', RankineTags.Items.STORAGE_BLOCKS_TOURMALINE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_GREEN_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_BLAZING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_FERROCERIUM).define('R', RankineTags.Items.STORAGE_BLOCKS_PERIDOT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_FERROCERIUM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.TOTEM_OF_INFUSING.get(), 1).pattern("III").pattern("IRI").pattern("III").define('I', RankineTags.Items.INGOTS_MISCHMETAL).define('R', RankineTags.Items.STORAGE_BLOCKS_PERIDOT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_MISCHMETAL)).save(consumer);



        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ICE_SKATES.get(), 1).pattern("L  ").pattern("RRL").pattern("NNN").define('L', Tags.Items.LEATHER).define('R', RankineItems.ROPE.get()).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SANDALS.get(), 1).pattern("L  ").pattern("LRR").define('L', Tags.Items.LEATHER).define('R', RankineItems.ROPE.get()).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SNOWSHOES.get(), 1).pattern("SRS").pattern("SRS").pattern(" S ").define('S', Tags.Items.RODS_WOODEN).define('R', RankineItems.ROPE.get()).unlockedBy("has_ingredient", has(RankineItems.ROPE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.FINS.get(), 1).pattern("L  ").pattern("LRR").define('L', Tags.Items.LEATHER).define('R', RankineTags.Items.RUBBER).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.GAS_MASK.get(), 1).pattern("RRR").pattern("MRM").pattern("CMC").define('R', RankineTags.Items.RUBBER).define('C', RankineTags.Items.CARBON).define('M', RankineItems.BIOTITE.get()).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.GOGGLES.get(), 1).pattern("GRG").pattern("RSR").define('R', RankineTags.Items.RUBBER).define('G', Tags.Items.GLASS_PANES).define('S', Tags.Items.STRING).unlockedBy("has_ingredient", has(RankineTags.Items.RUBBER)).save(consumer);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.WOOD_TIER_MINING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.STONECUTTER).define('B', RankineTags.Items.STORAGE_BLOCKS_ROSE_METAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.STONE_TIER_MINING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.STONECUTTER).define('B', RankineTags.Items.STORAGE_BLOCKS_BRASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.IRON_TIER_MINING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.STONECUTTER).define('B', RankineTags.Items.STORAGE_BLOCKS_STEEL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIAMOND_TIER_MINING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.STONECUTTER).define('B', RankineTags.Items.STORAGE_BLOCKS_TITANIUM_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TITANIUM_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.NETHERITE_TIER_MINING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.STONECUTTER).define('B', RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.WOOD_TIER_CRUSHING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.GRINDSTONE).define('B', RankineTags.Items.STORAGE_BLOCKS_ROSE_METAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ROSE_METAL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.STONE_TIER_CRUSHING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.GRINDSTONE).define('B', RankineTags.Items.STORAGE_BLOCKS_BRASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.IRON_TIER_CRUSHING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.GRINDSTONE).define('B', RankineTags.Items.STORAGE_BLOCKS_STEEL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DIAMOND_TIER_CRUSHING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.GRINDSTONE).define('B', RankineTags.Items.STORAGE_BLOCKS_TITANIUM_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TITANIUM_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.NETHERITE_TIER_CRUSHING_HEAD.get(), 1).pattern(" G ").pattern("GBG").pattern("SSS").define('S', ItemTags.STONE_BRICKS).define('G', Items.GRINDSTONE).define('B', RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN_HEAVY_ALLOY).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_TUNGSTEN_HEAVY_ALLOY)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.DOWSING_ROD.get(), 1).pattern("#R#").pattern(" # ").define('#', ItemTags.PLANKS).define('R', RankineTags.Items.ROPE).unlockedBy("has_ingredient", has(RankineTags.Items.ROPE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.WOODEN_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', ItemTags.PLANKS).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.PEWTER_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', RankineTags.Items.INGOTS_PEWTER).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_PEWTER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.STEEL_GOLD_PAN.get(), 1).pattern("###").pattern(" # ").define('#', RankineTags.Items.INGOTS_STEEL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.RARE_EARTH_MAGNET.get(), 1).pattern("I I").pattern("###").define('#', RankineTags.Items.INGOTS_SAMARIUM).define('I', RankineTags.Items.INGOTS_COBALT).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer,"rankine:rare_earth_magnet_sm");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.RARE_EARTH_MAGNET.get(), 1).pattern("I I").pattern("#B#").define('I', Tags.Items.INGOTS_IRON).define('#', RankineTags.Items.INGOTS_NEODYMIUM).define('B', RankineTags.Items.BORON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer, "rankine:rare_earth_magnet_nd");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ALNICO_MAGNET.get(), 1).pattern("# #").pattern("###").define('#', RankineTags.Items.INGOTS_ALNICO).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_ALNICO)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ORNAMENT.get(), 8).pattern(" C ").pattern("NGN").pattern(" N ").define('C', Items.CHAIN).define('G', Tags.Items.GLASS).define('N', RankineTags.Items.CRAFTING_METAL_NUGGETS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_NUGGETS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SADDLE_TREE.get(), 1).pattern("III").pattern("PPP").pattern("SSS").define('S', RankineTags.Items.INGOTS_STEEL).define('P', ItemTags.PLANKS).define('I', RankineTags.Items.INGOTS_ALUMINUM).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:saddle_tree_from_aluminum");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SADDLE_TREE.get(), 1).pattern("III").pattern("PPP").pattern("SSS").define('S', RankineTags.Items.INGOTS_STEEL).define('P', ItemTags.PLANKS).define('I', Tags.Items.INGOTS_IRON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:saddle_tree_from_iron");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_LEAD).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_LEAD)).save(consumer,"rankine:lead_cannonball");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_BISMUTH).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BISMUTH)).save(consumer,"rankine:bismuth_cannonball");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.CANNONBALL.get(), 3).pattern(" I ").pattern("III").pattern(" I ").define('I', RankineTags.Items.INGOTS_CAST_IRON).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_CAST_IRON)).save(consumer,"rankine:cast_iron_cannonball");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RankineItems.CARCASS.get()).requires(RankineItems.CANNONBALL.get()).requires(RankineTags.Items.SULFUR).requires(RankineTags.Items.SALTPETER).requires(Items.GUNPOWDER).requires(RankineTags.Items.NUGGETS_ANTIMONY).unlockedBy("has_ingredient", has(RankineTags.Items.CANNONBALLS)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.GRAPHITE_ELECTRODE.get(), 1).pattern("  I").pattern(" I ").pattern("I  ").define('I', RankineTags.Items.GRAPHITE).unlockedBy("has_ingredient", has(RankineTags.Items.GRAPHITE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.HARD_CARBON_ELECTRODE.get(), 1).pattern("  I").pattern(" I ").pattern("I  ").define('I', RankineTags.Items.CARBON).unlockedBy("has_ingredient", has(RankineTags.Items.CARBON)).save(consumer);

        //Machines
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BEEHIVE_OVEN_PIT.get(), 1).pattern(" S ").pattern("SLS").pattern(" S ").define('S', BricksBlocks.REFRACTORY_BRICKS.getBricksBlock()).define('L', Tags.Items.STORAGE_BLOCKS_COAL).unlockedBy("has_ingredient", has(BricksBlocks.REFRACTORY_BRICKS.getBricksBlock())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.ALLOY_FURNACE.get(), 1).pattern("BSB").pattern("BSB").pattern("BCB").define('B', BricksBlocks.REFRACTORY_BRICKS.getBricksBlock()).define('S', RankineTags.Items.SHEETMETAL).define('C', RankineTags.Items.CAMPFIRES).unlockedBy("has_ingredient", has(BricksBlocks.REFRACTORY_BRICKS.getBricksBlock())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.MIXING_BARREL.get(), 1).pattern("R").pattern("B").pattern("S").define('B', Blocks.BARREL).define('S', ItemTags.WOODEN_SLABS).define('R', Tags.Items.RODS_WOODEN).unlockedBy("has_ingredient", has(Items.BARREL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.CHARCOAL_PIT.get(), 1).pattern(" S ").pattern("SLS").pattern(" S ").define('S', Tags.Items.RODS_WOODEN).define('L', ItemTags.LOGS_THAT_BURN).unlockedBy("has_ingredient", has(ItemTags.LOGS_THAT_BURN)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.GAS_BOTTLER.get(), 1).pattern("XSX").pattern("S S").pattern("XSX").define('X', RankineTags.Items.SHEETMETAL).define('S', RankineTags.Items.INGOTS_BRASS).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BATTERY_CHARGER.get(), 1).pattern("XSX").pattern("R R").pattern("XSX").define('X', RankineTags.Items.SHEETMETAL).define('R', Items.REPEATER).define('S', RankineTags.Items.SHEETMETAL).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_MAGNESIUM_ALLOY)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.GAS_VENT.get(), 1).pattern("XSX").pattern("SBS").pattern("XSX").define('X', RankineTags.Items.SHEETMETAL).define('S', RankineItems.BOROSILICATE_GLASS.get()).define('B', Items.GLASS_BOTTLE).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_BRASS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.FLOOD_GATE.get(), 1).pattern(" S ").pattern("SSS").pattern(" S ").define('S', RankineTags.Items.CRAFTING_METAL_INGOTS).unlockedBy("has_ingredient", has(RankineTags.Items.CRAFTING_METAL_INGOTS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.CRUCIBLE_BLOCK.get(), 1).pattern("R R").pattern("R R").pattern("RCR").define('R', BricksBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock()).define('C', Items.CAULDRON).unlockedBy("has_ingredient", has(BricksBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.MATERIAL_TESTING_TABLE.get(), 1).pattern("TT").pattern("BB").pattern("BB").define('B', ItemTags.PLANKS).define('T', ItemTags.STONE_BRICKS).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.TEMPLATE_TABLE.get(), 1).pattern("TT").pattern("BB").pattern("BB").define('B', ItemTags.PLANKS).define('T', Items.PAPER).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.RARE_EARTH_ELECTROMAGNET.get(), 1).pattern("WPW").pattern("WPW").pattern("WBW").define('W', RankineItems.ALLOY_WIRE.get()).define('B', RankineItems.SODIUM_SULFUR_BATTERY.get()).define('P', RankineTags.Items.METAL_POLES).unlockedBy("has_ingredient", has(RankineItems.SODIUM_SULFUR_BATTERY.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.ALNICO_ELECTROMAGNET.get(), 1).pattern("WPW").pattern("WPW").pattern("WBW").define('W', RankineItems.ALLOY_WIRE.get()).define('B', RankineItems.LEAD_ACID_BATTERY.get()).define('P', RankineTags.Items.METAL_POLES).unlockedBy("has_ingredient", has(RankineItems.LEAD_ACID_BATTERY.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineItems.SIMPLE_ELECTROMAGNET.get(), 1).pattern("WPW").pattern("WPW").pattern("WBW").define('W', RankineItems.ALLOY_WIRE.get()).define('B', RankineItems.SILVER_ZINC_BATTERY.get()).define('P', RankineTags.Items.METAL_POLES).unlockedBy("has_ingredient", has(RankineItems.SILVER_ZINC_BATTERY.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.AIR_DISTILLATION_PACKING.get(), 1).pattern("##").pattern("##").define('#', RankineItems.STAINLESS_STEEL_SHEETMETAL.get()).unlockedBy("has_ingredient", has(RankineItems.STAINLESS_STEEL_SHEETMETAL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.DISTILLATION_TOWER.get(), 1).pattern("IBI").pattern("I#I").pattern("IMI").define('I', RankineTags.Items.INGOTS_STAINLESS_STEEL).define('B', RankineTags.Items.ICE).define('M', Items.MAGMA_BLOCK).define('#', RankineItems.MUSCOVITE_BLOCK.get()).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_STAINLESS_STEEL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.HEATING_ELEMENT_1.get(), 1).pattern("III").pattern("WMW").pattern("WMW").define('W', RankineItems.ALLOY_WIRE.get()).define('I', RankineTags.Items.INGOTS_CAST_IRON).define('M', Items.MAGMA_BLOCK).unlockedBy("has_ingredient", has(RankineTags.Items.INGOTS_CAST_IRON)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BOTANIST_STATION.get(), 1).pattern("FS").pattern("PP").pattern("PP").define('S', ItemTags.SAPLINGS).define('F',  ItemTags.FLOWERS).define('P', ItemTags.PLANKS).unlockedBy("has_ingredient", has(ItemTags.PLANKS)).save(consumer);

        //Smithing Recipes
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_BOOTS),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RecipeCategory.MISC, RankineItems.BRIGADINE_BOOTS.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_boots_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_LEGGINGS),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RecipeCategory.MISC, RankineItems.BRIGADINE_LEGGINGS.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_leggings_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_CHESTPLATE),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RecipeCategory.MISC, RankineItems.BRIGADINE_CHESTPLATE.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_chestplate_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(Items.LEATHER_HELMET),Ingredient.of(RankineTags.Items.INGOTS_STEEL),RecipeCategory.MISC, RankineItems.BRIGADINE_HELMET.get()).unlocks("has_ingredient", has(RankineTags.Items.INGOTS_STEEL)).save(consumer,"rankine:brigadine_helmet_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_BOOTS.get()),Ingredient.of(new ItemStack(Items.PRISMARINE_SHARD,8)),RecipeCategory.MISC, RankineItems.CONDUIT_DIVING_BOOTS.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_boots_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_LEGGINGS.get()),Ingredient.of(new ItemStack(Items.PRISMARINE_CRYSTALS,8)),RecipeCategory.MISC, RankineItems.CONDUIT_DIVING_LEGGINGS.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_leggings_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_CHESTPLATE.get()),Ingredient.of(Items.CONDUIT),RecipeCategory.MISC, RankineItems.CONDUIT_DIVING_CHESTPLATE.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_chestplate_from_smithing");
        LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(RankineItems.DIVING_HELMET.get()),Ingredient.of(Items.SPONGE),RecipeCategory.MISC, RankineItems.CONDUIT_DIVING_HELMET.get()).unlocks("has_ingredient", has(Items.CONDUIT)).save(consumer,"rankine:conduit_diving_helmet_from_smithing");
        

        
        //furnace recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.FIRE_CLAY_BALL.get()), RecipeCategory.MISC, RankineItems.REFRACTORY_BRICK.get(), 0.2F, 200).unlockedBy("has_ingredient", has(RankineTags.Items.CLAY_BALLS)).save(consumer, "rankine:refractory_brick_from_fire_clay_ball_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BAMBOO), RecipeCategory.MISC, RankineItems.DRIED_BAMBOO.get(), 0.1F, 25).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer, "rankine:dried_bamboo_from_bamboo_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BONE), RecipeCategory.MISC, RankineItems.BONE_ASH.get(), 0.1F, 200).unlockedBy("has_ingredient", has(Items.BONE)).save(consumer, "rankine:bone_ash_from_bone_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.PULP.get()), RecipeCategory.MISC, Items.PAPER, 0.1F, 100).unlockedBy("has_ingredient", has(RankineItems.PULP.get())).save(consumer, "rankine:paper_from_pulp_smelting");

        //blasting recipes
        for (Item MINERAL : blastingMap.keySet()) {
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(MINERAL), RecipeCategory.MISC, blastingMap.get(MINERAL), 0.5F, 100).unlockedBy("has_ingredient", has(MINERAL)).save(consumer, "rankine:"+getItemName(blastingMap.get(MINERAL))+"_from_"+getItemName(MINERAL)+"_blasting");
        }
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineTags.Items.SLAG), RecipeCategory.MISC, Items.IRON_NUGGET, 0.5F, 100).unlockedBy("has_ingredient", has(RankineTags.Items.SLAG)).save(consumer, "rankine:iron_nugget_from_slag_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.APATITE.get()), RecipeCategory.MISC, RankineItems.PHOSPHORUS_NUGGET.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.APATITE.get())).save(consumer, "rankine:phosphorus_nugget_from_apatite_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.BADDELEYITE.get()), RecipeCategory.MISC, RankineItems.ZIRCONIA.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.BADDELEYITE.get())).save(consumer, "rankine:zirconia_from_baddeleyite_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.METEORIC_IRON.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.METEORIC_IRON.get())).save(consumer, "rankine:iron_from_meteoric_iron_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.TRONA.get()), RecipeCategory.MISC, RankineItems.SODIUM_CARBONATE.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineItems.TRONA.get())).save(consumer, "rankine:sodium_carbonate_from_trona_blasting");



        for (FiberBlocks fiber : FiberBlocks.values()) {
            if (fiber.getDyeColor() != null) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, fiber.getBlock()).requires(FiberBlocks.FIBER.getBlock()).requires(fiber.getDyeColor().getTag()).unlockedBy("has_ingredient", has(RankineItems.PLANT_FIBER.get())).save(consumer, "rankine:"+getItemName(fiber.getBlock())+"_from_dye");
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, fiber.getMat()).requires(FiberBlocks.FIBER.getMat()).requires(fiber.getDyeColor().getTag()).unlockedBy("has_ingredient", has(RankineItems.PLANT_FIBER.get())).save(consumer, "rankine:"+getItemName(fiber.getMat())+"_from_dye");
            } else {
                threeXthree(consumer, fiber.getBlock(), RankineTags.Items.FIBER_PLANT, 1, "has_plant_fiber", RankineItems.PLANT_FIBER.get());
            }
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, fiber.getMat(), 3).pattern("##").define('#', fiber.getBlock()).group("fiber_mat").unlockedBy("has_block", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.PLANT_FIBER.get())).save(consumer);
        }


        for (Block BLOCK : RankineLists.LEDS) {
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.LEDS.indexOf(BLOCK));
            led(consumer, BLOCK.asItem(), DYE);
        }


        twoXtwo(consumer, RankineItems.ROPE.get(), RankineTags.Items.FIBER_PLANT, 4, "has_plant_fiber", RankineItems.PLANT_FIBER.get());

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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLACK_DYE).requires(RankineItems.BONE_CHAR.get()).unlockedBy("has_ingredient", has(RankineItems.BONE_CHAR.get())).save(consumer, "rankine:black_dye_from_bone_char");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PINK_DYE).requires(RankineItems.PINK_BELLFLOWER.get(),2).unlockedBy("has_ingredient", has(RankineItems.PINK_BELLFLOWER.get())).save(consumer, "rankine:pink_dye_from_pink_bellflower");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGENTA_DYE).requires(RankineItems.VIOLET_BELLFLOWER.get(),2).unlockedBy("has_ingredient", has(RankineItems.VIOLET_BELLFLOWER.get())).save(consumer, "rankine:magenta_dye_from_violet_bellflower");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE).requires(RankineItems.GOLDENROD.get(),2).unlockedBy("has_ingredient", has(RankineItems.GOLDENROD.get())).save(consumer, "rankine:yellow_dye_from_goldenrod");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RED_DYE).requires(RankineItems.RED_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.RED_LILY.get())).save(consumer, "rankine:red_dye_from_red_lily");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ORANGE_DYE).requires(RankineItems.ORANGE_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.ORANGE_LILY.get())).save(consumer, "rankine:orange_dye_from_orange_lily");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.WHITE_DYE).requires(RankineItems.WHITE_LILY.get(),2).unlockedBy("has_ingredient", has(RankineItems.WHITE_LILY.get())).save(consumer, "rankine:white_dye_from_white_lily");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLACK_DYE).requires(RankineItems.BLACK_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.BLACK_MORNING_GLORY.get())).save(consumer, "rankine:black_dye_from_black_morning_glory");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLUE_DYE).requires(RankineItems.BLUE_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.BLUE_MORNING_GLORY.get())).save(consumer, "rankine:blue_dye_from_blue_morning_glory");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PURPLE_DYE).requires(RankineItems.PURPLE_MORNING_GLORY.get(),2).unlockedBy("has_ingredient", has(RankineItems.PURPLE_MORNING_GLORY.get())).save(consumer, "rankine:purple_dye_from_purple_morning_glory");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GRAY_DYE).requires(Items.WATER_BUCKET).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).requires(RankineItems.ASH.get()).unlockedBy("has_ingredient", has(RankineItems.ASH.get())).save(consumer, "rankine:gray_dye_from_ash");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLUE_DYE,4).requires(RankineTags.Items.NUGGETS_YTTRIUM).requires(RankineTags.Items.NUGGETS_YTTRIUM).requires(RankineTags.Items.NUGGETS_INDIUM).requires(RankineTags.Items.NUGGETS_INDIUM).requires(RankineTags.Items.NUGGETS_MANGANESE).requires(RankineTags.Items.NUGGETS_MANGANESE).unlockedBy("has_ingredient", has(RankineTags.Items.NUGGETS_MANGANESE)).save(consumer, "rankine:blue_dye_from_metals");


        SpecialRecipeBuilder.special(RankineRecipeSerializers.JAM_RECIPE_SERIALIZER.get()).save(consumer, "fruit_jam");


        for (Block HOLLOW : RankineLists.HOLLOW_LOGS) {
            String PATH = getItemName(HOLLOW);
            Block LOG = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("minecraft:"+PATH.replace("hollow_","")));
            if (LOG != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HOLLOW.asItem(), 16).pattern("###").pattern("# #").pattern("###").define('#', LOG).group("rankine:hollow_logs").unlockedBy("has_ingredient", has(LOG)).save(consumer);
            }
        }
        for (Block BLK : RankineLists.LEAF_LITTERS) {
            String PATH = getItemName(BLK);
            Block LEAF = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse( "minecraft:"+PATH.replace("leaf_litter","leaves")));
            if (LEAF != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BLK.asItem(), 4).pattern("##").define('#', LEAF).group("rankine:leaf_litters").unlockedBy("has_ingredient", has(LEAF)).save(consumer);
            }
        }
        for (Block BLK : RankineLists.GLAZED_PORCELAIN_BLOCKS) {
            TagKey<Item> DYE = RankineLists.DYES.get(RankineLists.GLAZED_PORCELAIN_BLOCKS.indexOf(BLK));
            centerRing(consumer, BLK.asItem(), 8, Ingredient.of(RankineItems.PORCELAIN.get()), Ingredient.of(DYE), "rankine:glazed_porcelain", RankineItems.PORCELAIN.get());
        }

        for (Item MINERAL_ITEM : RankineLists.MINERAL_ITEMS) {
            Item MINERAL_BLOCK = RankineLists.MINERAL_BLOCKS.get(RankineLists.MINERAL_ITEMS.indexOf(MINERAL_ITEM)).asItem();
            threeXthree(consumer, MINERAL_BLOCK, MINERAL_ITEM, 1, "has_ingredient", MINERAL_ITEM);
            OneToX(consumer, MINERAL_ITEM, MINERAL_BLOCK, 9, "has_ingredient", MINERAL_ITEM, getItemName(MINERAL_ITEM)+"_from_block");
        }

        //ELEMENTS
        for (Item NUGGET : RankineLists.ELEMENT_NUGGETS) {
            Block BLOCK = RankineLists.ELEMENT_BLOCKS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ELEMENT_INGOTS.get(RankineLists.ELEMENT_NUGGETS.indexOf(NUGGET));
            threeXthree(consumer, BLOCK.asItem(), INGOT, 1, "has_ingredient", INGOT);
            threeXthree(consumer, INGOT, NUGGET, 1, "has_ingredient", NUGGET, getItemName(INGOT)+"_from_"+getItemName(NUGGET));
            OneToX(consumer, INGOT, BLOCK.asItem(), 9, "has_ingredient", BLOCK.asItem(), getItemName(INGOT)+"_from_"+getItemName(BLOCK));
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
        // uncrafting bricks
        stonecutterResultFromBase(consumer, Items.BRICK, Items.BRICKS, 2);
        stonecutterResultFromBase(consumer, Items.NETHER_BRICK, Items.NETHER_BRICKS, 2);
        stonecutterResultFromBase(consumer, Items.NETHER_BRICK, Items.RED_NETHER_BRICKS, 2);
        stonecutterResultFromBase(consumer, Items.PRISMARINE_SHARD, Items.PRISMARINE_BRICKS, 2);
        stonecutterResultFromBase(consumer, Items.PRISMARINE_SHARD, Items.DARK_PRISMARINE, 2);
        stonecutterResultFromBase(consumer, RankineItems.REFRACTORY_BRICK.get(), BricksBlocks.REFRACTORY_BRICKS.getBricksBlock(), 2);
        stonecutterResultFromBase(consumer, RankineItems.HIGH_REFRACTORY_BRICK.get(), BricksBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock(), 2);
        stonecutterResultFromBase(consumer, RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(), BricksBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.getBricksBlock(), 2);


        //WOODS
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            if (Wood.hasLogs()) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getWood(), 3).pattern("##").pattern("##").define('#', Wood.getLog()).unlockedBy("has_ingredient", has(Wood.getLog())).group("rankine:wood").save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getStrippedWood(), 3).pattern("##").pattern("##").define('#', Wood.getStrippedLog()).unlockedBy("has_ingredient", has(Wood.getStrippedLog())).group("stripped_wood").save(consumer);
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Wood.getPlanks(), 4).requires(ItemTags.create(new ResourceLocation("rankine", Wood.getBaseName() + "_logs"))).group("planks").unlockedBy("has_ingredient", has(ItemTags.create(new ResourceLocation("rankine", Wood.getBaseName() + "_logs")))).save(consumer);
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getHollowLog(), 16).pattern("###").pattern("# #").pattern("###").define('#', Wood.getLog()).group("rankine:hollow_logs").unlockedBy("has_ingredient", has(Wood.getLog())).save(consumer);
            }
            if (Wood.isTree()) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getLeafLitter(), 4).pattern("##").define('#', Wood.getLeaves()).group("rankine:leaf_litters").unlockedBy("has_ingredient", has(Wood.getLeaves())).save(consumer);
            }

            Block PLANK = Wood.getPlanks();
            slab(consumer, Wood.getSlab(), PLANK, "wooden_slab");
            stairs(consumer, Wood.getStairs(), PLANK, "wooden_stairs");
            pressurePlate(consumer, Wood.getPressurePlate(), PLANK, "wooden_pressure_plate");
            door(consumer, Wood.getDoor(), PLANK, "wooden_door",  "has_plank", PLANK);
            trapdoor(consumer, Wood.getTrapdoor(), PLANK, "wooden_trapdoor",  "has_plank", PLANK);
            fence(consumer, Wood.getFence(), PLANK, "wooden_fence",  "has_plank", PLANK);
            fenceGate(consumer, Wood.getFenceGate(), PLANK, "wooden_fence_gate",  "has_plank", PLANK);
            bookshelf(consumer, Wood.getBookshelf(), PLANK, "wooden_bookshelves",  "has_plank", PLANK);
            boat(consumer, Wood.getBoat(), PLANK, "boat",  "has_plank", PLANK);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getSignItem(), 3).pattern("###").pattern("###").pattern(" S ").define('#', Wood.getPlanks()).define('S', Tags.Items.RODS_WOODEN).group("sign").unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(Wood.getPlanks())).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Wood.getButton()).requires(PLANK).group("wooden_button").unlockedBy("has_ingredient", has(PLANK)).save(consumer);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PLANK).requires(Wood.getSlab()).requires(Wood.getSlab()).group("block_from_vslab").unlockedBy("has_ingredient", has(PLANK)).save(consumer,"rankine:"+Wood.getBaseName()+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PLANK,3).requires(Wood.getStairs()).requires(Wood.getStairs()).requires(Wood.getStairs()).requires(Wood.getStairs()).group("block_from_stairs").unlockedBy("has_ingredient", has(PLANK)).save(consumer,"rankine:"+Wood.getBaseName()+"_from_stairs");

            if (Wood.getBaseName().equals("bamboo")) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getPlanks(), 1).pattern("##").pattern("##").define('#', RankineItems.DRIED_BAMBOO.get()).unlockedBy("has_ingredient", has(RankineItems.DRIED_BAMBOO.get())).save(consumer);
            } else if (Wood.getBaseName().equals("bamboo_culms")) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Wood.getPlanks(), 1).pattern("##").pattern("##").define('#', Items.BAMBOO).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer);
            }

        }

        for (SoilBlocks base : SoilBlocks.values()) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, base.getCoarseSoilBlock().asItem(), 4).pattern("#G").pattern("G#").define('#', base.getSoilBlock()).define('G', Tags.Items.GRAVEL).group("rankine:coarse_soil").unlockedBy("has_ingredient", has(base.getSoilBlock())).save(consumer);

        }
        for (StoneBlocks baseStone : StoneBlocks.values()) {
            String baseStoneName = baseStone.getBaseName();
            Block COBBLE = baseStone.getCobble();
            Block COLUMN = baseStone.getColumn();
            Block STONE = baseStone.getStone();
            Block POLISHED_STONE = baseStone.getPolished();
            Block STONE_BRICKS = baseStone.getBricks();
            Block MOSSY_STONE_BRICKS = baseStone.getMossyBricks();
            Block STONE_SLAB = baseStone.getSlab();
            Block POLISHED_STONE_SLAB = baseStone.getPolishedSlab();
            Block STONE_BRICKS_SLAB = baseStone.getBricksSlab();
            Block MOSSY_STONE_BRICKS_SLAB = baseStone.getMossyBricksSlab();
            Block STONE_STAIRS = baseStone.getStairs();
            Block POLISHED_STONE_STAIRS = baseStone.getPolishedStairs();
            Block STONE_BRICKS_STAIRS = baseStone.getBricksStairs();
            Block MOSSY_STONE_BRICKS_STAIRS = baseStone.getMossyBricksStairs();
            Block STONE_WALL = baseStone.getWall();
            Block POLISHED_STONE_WALL = baseStone.getPolishedWall();
            Block STONE_BRICKS_WALL = baseStone.getBricksWall();
            Block MOSSY_STONE_BRICKS_WALL = baseStone.getMossyBricksWall();
            Block STONE_PRESSURE_PLATE = baseStone.getPressurePlate();
            Block STONE_BRICKS_PRESSURE_PLATE = baseStone.getBricksPressurePlate();
            Block STONE_BUTTON = baseStone.getButton();

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, COLUMN, 8)
                    .pattern("#")
                    .pattern("#")
                    .define('#', STONE)
                    .group("stone_column")
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(STONE))
                    .save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE).requires(COLUMN).requires(COLUMN).requires(COLUMN).requires(COLUMN).group("stone_from_stone_column").unlockedBy("has_ingredient", has(COLUMN)).save(consumer,"rankine:"+getItemName(STONE)+"_from_column");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE).requires(COBBLE).requires(COBBLE).requires(COBBLE).requires(COBBLE).group("stone_from_cobble").unlockedBy("has_ingredient", has(COBBLE)).save(consumer,"rankine:"+getItemName(STONE)+"_from_cobble");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, COBBLE,8).requires(STONE).requires(Tags.Items.COBBLESTONE).group("cobble_from_stone").unlockedBy("has_ingredient", has(COBBLE)).save(consumer,"rankine:"+getItemName(COBBLE)+"_from_stone");

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, POLISHED_STONE, 4)
                    .pattern("##")
                    .pattern("##")
                    .define('#', STONE)
                    .group("polished_stone")
                    .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(STONE))
                    .save(consumer);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STONE_BRICKS, 2)
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
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE_BUTTON).requires(STONE).group("stone_button").unlockedBy("has_ingredient", has(STONE)).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MOSSY_STONE_BRICKS).requires(STONE).requires(Items.VINE).group("mossy_bricks_vines").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:mossy_"+baseStoneName+"_bricks_from_vine");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MOSSY_STONE_BRICKS).requires(STONE).requires(Items.MOSS_BLOCK).group("mossy_bricks_moss").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:mossy_"+baseStoneName+"_bricks_from_moss");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, POLISHED_STONE, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:polished_"+baseStoneName+"_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_BRICKS, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_bricks_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_SLAB, 2).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_slab_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_STAIRS).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_stairs_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_WALL).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_wall_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_pressure_plate_from_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_BUTTON).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+baseStoneName+"_button_from_"+baseStoneName+"_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), RecipeCategory.MISC, STONE_BRICKS_SLAB, 2).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStoneName+"_brick_slab_from_"+baseStoneName+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), RecipeCategory.MISC, STONE_BRICKS_STAIRS).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStoneName+"_brick_stairs_from_"+baseStoneName+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), RecipeCategory.MISC, STONE_BRICKS_WALL).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStoneName+"_brick_wall_from_"+baseStoneName+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICKS), RecipeCategory.MISC, STONE_BRICKS_PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer, "rankine:"+baseStoneName+"_brick_button_from_"+baseStoneName+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), RecipeCategory.MISC, MOSSY_STONE_BRICKS_SLAB, 2).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStoneName+"_brick_slab_from_mossy_"+baseStoneName+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), RecipeCategory.MISC, MOSSY_STONE_BRICKS_STAIRS).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStoneName+"_brick_stairs_from_mossy_"+baseStoneName+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(MOSSY_STONE_BRICKS), RecipeCategory.MISC, MOSSY_STONE_BRICKS_WALL).unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer, "rankine:mossy_"+baseStoneName+"_brick_wall_from_mossy_"+baseStoneName+"_bricks_stonecutting");

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), RecipeCategory.MISC, POLISHED_STONE_SLAB, 2).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStoneName+"_slab_from_polished_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), RecipeCategory.MISC, POLISHED_STONE_STAIRS).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStoneName+"_stairs_from_polished_"+baseStoneName+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(POLISHED_STONE), RecipeCategory.MISC, POLISHED_STONE_WALL).unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer, "rankine:polished_"+baseStoneName+"_wall_from_polished_"+baseStoneName+"_stonecutting");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE).requires(STONE_SLAB).requires(STONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:"+baseStoneName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE,3).requires(STONE_STAIRS).requires(STONE_STAIRS).requires(STONE_STAIRS).requires(STONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STONE)).save(consumer,"rankine:"+baseStoneName+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE_BRICKS).requires(STONE_BRICKS_SLAB).requires(STONE_BRICKS_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer,"rankine:"+getItemName(STONE_BRICKS)+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE_BRICKS,3).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).requires(STONE_BRICKS_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STONE_BRICKS)).save(consumer,"rankine:"+getItemName(STONE_BRICKS)+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MOSSY_STONE_BRICKS).requires(MOSSY_STONE_BRICKS_SLAB).requires(MOSSY_STONE_BRICKS_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer,"rankine:mossy_"+getItemName(MOSSY_STONE_BRICKS)+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MOSSY_STONE_BRICKS,3).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).requires(MOSSY_STONE_BRICKS_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(MOSSY_STONE_BRICKS)).save(consumer,"rankine:mossy_"+getItemName(MOSSY_STONE_BRICKS)+"_from_stairs");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POLISHED_STONE).requires(POLISHED_STONE_SLAB).requires(POLISHED_STONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer,"rankine:"+getItemName(POLISHED_STONE)+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, POLISHED_STONE,3).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).requires(POLISHED_STONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(POLISHED_STONE)).save(consumer,"rankine:"+getItemName(POLISHED_STONE)+"_from_stairs");

        }

        //SANDSTONES
        for (RankineSandstone Sandstone : RankineLists.RANKINE_SANDSTONES) {
            Block SANDSTONE = Sandstone.getSandstone();
            String baseName = Sandstone.getBaseName();
            Block CHISELED = Sandstone.getChiseledSandstone();
            Block SANDSTONE_SLAB = Sandstone.getSandstoneSlab();
            Block SANDSTONE_STAIRS = Sandstone.getSandstoneStairs();
            Block SANDSTONE_WALL = Sandstone.getSandstoneWall();
            Block SMOOTH_SANDSTONE = Sandstone.getSmoothSandstone();
            Block SMOOTH_SANDSTONE_SLAB = Sandstone.getSmoothSandstoneSlab();
            Block SMOOTH_SANDSTONE_STAIRS = Sandstone.getSmoothSandstoneStairs();
            Block SMOOTH_SANDSTONE_WALL = Sandstone.getSmoothSandstoneWall();
            Block CUT_SANDSTONE = Sandstone.getCutSandstone();
            Block CUT_SANDSTONE_SLAB = Sandstone.getCutSandstoneSlab();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CHISELED, 1).pattern("S").pattern("S").define('S', SANDSTONE_SLAB).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer);
            slab(consumer,SANDSTONE_SLAB,SANDSTONE, "sandstone_slab");
            stairs(consumer,SANDSTONE_STAIRS,SANDSTONE, "sandstone_stairs");
            wall(consumer,SANDSTONE_WALL,SANDSTONE, "sandstone_wall");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SANDSTONE).requires(SANDSTONE_SLAB).requires(SANDSTONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SANDSTONE,3).requires(SANDSTONE_STAIRS).requires(SANDSTONE_STAIRS).requires(SANDSTONE_STAIRS).requires(SANDSTONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer,"rankine:"+baseName+"_from_stairs");
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(SANDSTONE), RecipeCategory.MISC, SMOOTH_SANDSTONE, 0.1F, 200).unlockedBy("has_sandstone", has(SANDSTONE)).save(consumer);
            slab(consumer,SMOOTH_SANDSTONE_SLAB,SMOOTH_SANDSTONE, "smooth_sandstone_slab");
            stairs(consumer,SMOOTH_SANDSTONE_STAIRS,SMOOTH_SANDSTONE, "smooth_sandstone_stairs");
            wall(consumer,SMOOTH_SANDSTONE_WALL,SMOOTH_SANDSTONE, "smooth_sandstone_wall");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SMOOTH_SANDSTONE).requires(SMOOTH_SANDSTONE_SLAB).requires(SMOOTH_SANDSTONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer,"rankine:smooth_"+baseName+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SMOOTH_SANDSTONE,3).requires(SMOOTH_SANDSTONE_STAIRS).requires(SMOOTH_SANDSTONE_STAIRS).requires(SMOOTH_SANDSTONE_STAIRS).requires(SMOOTH_SANDSTONE_STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(SMOOTH_SANDSTONE)).save(consumer,"rankine:smooth_"+baseName+"_from_stairs");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CUT_SANDSTONE, 4).pattern("SS").pattern("SS").define('S', SANDSTONE).unlockedBy("has_ingredient", has(SANDSTONE)).save(consumer);
            slab(consumer,CUT_SANDSTONE_SLAB,CUT_SANDSTONE, "cut_sandstone_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, CUT_SANDSTONE).requires(CUT_SANDSTONE_SLAB).requires(CUT_SANDSTONE_SLAB).group("block_from_slab").unlockedBy("has_ingredient", has(CUT_SANDSTONE)).save(consumer,"rankine:cut_"+baseName+"_from_slab");
            stonecutterResultFromBase(consumer, CHISELED, SANDSTONE);
            stonecutterResultFromBase(consumer, SANDSTONE_SLAB, SANDSTONE, 2);
            stonecutterResultFromBase(consumer, SANDSTONE_STAIRS, SANDSTONE);
            stonecutterResultFromBase(consumer, SANDSTONE_WALL, SANDSTONE);
            stonecutterResultFromBase(consumer, CUT_SANDSTONE, SANDSTONE);
            stonecutterResultFromBase(consumer, CUT_SANDSTONE_SLAB, SANDSTONE, 2);
            stonecutterResultFromBase(consumer, CUT_SANDSTONE_SLAB, CUT_SANDSTONE, 2);
            stonecutterResultFromBase(consumer, SMOOTH_SANDSTONE_SLAB, SMOOTH_SANDSTONE, 2);
            stonecutterResultFromBase(consumer, SMOOTH_SANDSTONE_STAIRS, SMOOTH_SANDSTONE);
            stonecutterResultFromBase(consumer, SMOOTH_SANDSTONE_WALL, SMOOTH_SANDSTONE);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.WHITE_SANDSTONE.getSandstone(), 1).pattern("SS").pattern("SS").define('S', RankineBlocks.WHITE_SAND.get()).unlockedBy("has_ingredient", has(RankineBlocks.WHITE_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.BLACK_SANDSTONE.getSandstone(), 1).pattern("SS").pattern("SS").define('S', RankineBlocks.BLACK_SAND.get()).unlockedBy("has_ingredient", has(RankineBlocks.BLACK_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.DESERT_SANDSTONE.getSandstone(), 1).pattern("SS").pattern("SS").define('S', RankineBlocks.DESERT_SAND.get()).unlockedBy("has_ingredient", has(RankineBlocks.DESERT_SAND.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RankineBlocks.SOUL_SANDSTONE.getSandstone(), 1).pattern("SS").pattern("SS").define('S', Blocks.SOUL_SAND).unlockedBy("has_ingredient", has(Blocks.SOUL_SAND)).save(consumer);

        for (CementBlocks baseCementBlock : CementBlocks.values()) {
            Block CEMENT = baseCementBlock.getCementBlock();
            Block CEMENT_SLAB = baseCementBlock.getCementSlab();
            Block CEMENT_STAIRS = baseCementBlock.getCementStairs();
            Block CEMENT_WALL = baseCementBlock.getCementWall();
            slab(consumer,CEMENT_SLAB,CEMENT, "cement_slab");
            stairs(consumer,CEMENT_STAIRS,CEMENT, "cement_stairs");
            wall(consumer,CEMENT_WALL,CEMENT, "cement_wall");
            stonecutterResultFromBase(consumer, CEMENT_SLAB, CEMENT, 2);
            stonecutterResultFromBase(consumer, CEMENT_STAIRS, CEMENT);
            stonecutterResultFromBase(consumer, CEMENT_WALL, CEMENT);
            slabReconstruct(consumer, CEMENT_SLAB, CEMENT);
            stairsReconstruct(consumer, CEMENT_STAIRS, CEMENT);
        }
        stonecutterResultFromBase(consumer, CementBlocks.POLISHED_ROMAN_CONCRETE.getCementBlock(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.POLISHED_ROMAN_CONCRETE.getCementSlab(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.POLISHED_ROMAN_CONCRETE.getCementStairs(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.POLISHED_ROMAN_CONCRETE.getCementWall(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.ROMAN_CONCRETE_BRICKS.getCementBlock(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.ROMAN_CONCRETE_BRICKS.getCementSlab(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.ROMAN_CONCRETE_BRICKS.getCementStairs(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        stonecutterResultFromBase(consumer, CementBlocks.ROMAN_CONCRETE_BRICKS.getCementWall(), CementBlocks.ROMAN_CONCRETE.getCementBlock());
        //other stonecutter recipes

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CementBlocks.POLISHED_ROMAN_CONCRETE.getCementBlock(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', CementBlocks.ROMAN_CONCRETE.getCementBlock())
                .group("polished_stone")
                .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(CementBlocks.ROMAN_CONCRETE.getCementBlock()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CementBlocks.ROMAN_CONCRETE_BRICKS.getCementBlock(), 2)
                .pattern("#M")
                .pattern("M#")
                .define('#', CementBlocks.ROMAN_CONCRETE.getCementBlock())
                .define('M', RankineItems.MORTAR.get())
                .group("stone_bricks")
                .unlockedBy("has_mortar", InventoryChangeTrigger.TriggerInstance.hasItems(RankineItems.MORTAR.get()))
                .save(consumer);


        for (RankineDripstone Dripstone : RankineLists.RANKINE_DRIPSTONES) {
            twoXtwo(consumer, Dripstone.getDripstone(), Dripstone.getPointedDripstone(),1,"has_ingredient", Dripstone.getPointedDripstone().asItem());
        }










        for (BricksBlocks baseBricksBlock : BricksBlocks.values()) {
            Block BRICKS = baseBricksBlock.getBricksBlock();
            slab(consumer, baseBricksBlock.getBricksSlab(), BRICKS, "bricks_slab");
            stairs(consumer, baseBricksBlock.getBricksStairs(), BRICKS, "bricks_stairs");
            wall(consumer, baseBricksBlock.getBricksWall(), BRICKS, "bricks_wall");
            stonecutterResultFromBase(consumer, baseBricksBlock.getBricksSlab(), BRICKS, 2);
            stonecutterResultFromBase(consumer, baseBricksBlock.getBricksStairs(), BRICKS);
            stonecutterResultFromBase(consumer, baseBricksBlock.getBricksWall(), BRICKS);
            slabReconstruct(consumer, baseBricksBlock.getBricksSlab(), BRICKS);
            stairsReconstruct(consumer, baseBricksBlock.getBricksStairs(), BRICKS);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.CLAY_BRICKS.getBricksBlock()).define('#', Items.CLAY_BALL).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(Items.CLAY_BALL)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.KAOLINITE_BRICKS.getBricksBlock()).define('#', RankineItems.KAOLINITE.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.KAOLINITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.HALLOYSITE_BRICKS.getBricksBlock()).define('#', RankineItems.HALLOYSITE.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.HALLOYSITE.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.FIRE_CLAY_BRICKS.getBricksBlock()).define('#', RankineItems.FIRE_CLAY_BALL.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.FIRE_CLAY_BALL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.PORCELAIN_CLAY_BRICKS.getBricksBlock()).define('#', RankineItems.PORCELAIN_CLAY_BALL.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_clay", has(RankineItems.PORCELAIN_CLAY_BALL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.REFRACTORY_BRICKS.getBricksBlock()).define('#', RankineItems.REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_refractory_brick", has(RankineItems.REFRACTORY_BRICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock()).define('#', RankineItems.HIGH_REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_high_refractory_brick", has(RankineItems.HIGH_REFRACTORY_BRICK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BricksBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.getBricksBlock()).define('#', RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get()).define('M', RankineItems.MORTAR.get()).pattern("#M").pattern("M#").unlockedBy("has_refractory_brick", has(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get())).save(consumer);


        for (Block STONE_BRICK : RankineLists.VANILLA_BRICKS) {
            String PATH = getItemName(STONE_BRICK);
            Block STONE = RankineLists.VANILLA_STONES.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block SLAB = RankineLists.VANILLA_BRICKS_SLABS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block STAIRS = RankineLists.VANILLA_BRICKS_STAIRS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block WALL = RankineLists.VANILLA_BRICKS_WALLS.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));
            Block PRESSURE_PLATE = RankineLists.VANILLA_BRICKS_PRESSURE_PLATES.get(RankineLists.VANILLA_BRICKS.indexOf(STONE_BRICK));

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STONE_BRICK, 2)
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

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE), RecipeCategory.MISC, STONE_BRICK, 1).unlockedBy("has_ingredient", has(STONE)).save(consumer, "rankine:"+PATH+"_bricks_from_"+PATH+"_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), RecipeCategory.MISC, SLAB, 2).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_slab_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), RecipeCategory.MISC, STAIRS).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_stairs_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), RecipeCategory.MISC, WALL).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_wall_from_"+PATH+"_bricks_stonecutting");
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(STONE_BRICK), RecipeCategory.MISC, PRESSURE_PLATE).unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer, "rankine:"+PATH+"_brick_pressure_plate_from_"+PATH+"_bricks_stonecutting");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE_BRICK).requires(SLAB).requires(SLAB).group("block_from_vslab").unlockedBy("has_ingredient", has(STONE_BRICK)).save(consumer,"rankine:"+PATH+"_from_slab");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STONE_BRICK,3).requires(STAIRS).requires(STAIRS).requires(STAIRS).requires(STAIRS).group("block_from_stairs").unlockedBy("has_ingredient", has(STAIRS)).save(consumer,"rankine:"+PATH+"_from_stairs");

        }

        //NATIVE ORES
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SULFUR_ORE.get()), RecipeCategory.MISC, RankineItems.SULFUR.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).save(consumer, "rankine:sulfur_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SULFUR_ORE.get()), RecipeCategory.MISC, RankineItems.SULFUR.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SULFUR_ORE.get().asItem())).save(consumer, "rankine:sulfur_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_ARSENIC_ORE.get()), RecipeCategory.MISC, RankineItems.ARSENIC.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).save(consumer, "rankine:arsenic_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_ARSENIC_ORE.get()), RecipeCategory.MISC, RankineItems.ARSENIC.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_ARSENIC_ORE.get().asItem())).save(consumer, "rankine:arsenic_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_BISMUTH_ORE.get()), RecipeCategory.MISC, RankineItems.BISMUTH_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).save(consumer, "rankine:bismuth_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_BISMUTH_ORE.get()), RecipeCategory.MISC, RankineItems.BISMUTH_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_BISMUTH_ORE.get().asItem())).save(consumer, "rankine:bismuth_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_LEAD_ORE.get()), RecipeCategory.MISC, RankineItems.LEAD_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).save(consumer, "rankine:lead_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_LEAD_ORE.get()), RecipeCategory.MISC, RankineItems.LEAD_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_LEAD_ORE.get().asItem())).save(consumer, "rankine:lead_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SILVER_ORE.get()), RecipeCategory.MISC, RankineItems.SILVER_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).save(consumer, "rankine:silver_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SILVER_ORE.get()), RecipeCategory.MISC, RankineItems.SILVER_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SILVER_ORE.get().asItem())).save(consumer, "rankine:silver_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_TIN_ORE.get()), RecipeCategory.MISC, RankineItems.TIN_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).save(consumer, "rankine:tin_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_TIN_ORE.get()), RecipeCategory.MISC, RankineItems.TIN_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TIN_ORE.get().asItem())).save(consumer, "rankine:tin_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_INDIUM_ORE.get()), RecipeCategory.MISC, RankineItems.INDIUM_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).save(consumer, "rankine:indium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_INDIUM_ORE.get()), RecipeCategory.MISC, RankineItems.INDIUM_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_INDIUM_ORE.get().asItem())).save(consumer, "rankine:indium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_GALLIUM_ORE.get()), RecipeCategory.MISC, RankineItems.GALLIUM_INGOT.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).save(consumer, "rankine:gallium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_GALLIUM_ORE.get()), RecipeCategory.MISC, RankineItems.GALLIUM_INGOT.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_GALLIUM_ORE.get().asItem())).save(consumer, "rankine:gallium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_SELENIUM_ORE.get()), RecipeCategory.MISC, RankineItems.SELENIUM.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).save(consumer, "rankine:selenium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_SELENIUM_ORE.get()), RecipeCategory.MISC, RankineItems.SELENIUM.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_SELENIUM_ORE.get().asItem())).save(consumer, "rankine:selenium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NATIVE_TELLURIUM_ORE.get()), RecipeCategory.MISC, RankineItems.TELLURIUM.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).save(consumer, "rankine:tellurium_ingot_from_native_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NATIVE_TELLURIUM_ORE.get()), RecipeCategory.MISC, RankineItems.TELLURIUM.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NATIVE_TELLURIUM_ORE.get().asItem())).save(consumer, "rankine:tellurium_ingot_from_native_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.PORPHYRY_COPPER.get()), RecipeCategory.MISC, Items.COPPER_INGOT, 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.PORPHYRY_COPPER.get().asItem())).save(consumer, "rankine:copper_ingot_from_porphyry_copper_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.PORPHYRY_COPPER.get()), RecipeCategory.MISC, Items.COPPER_INGOT, 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.PORPHYRY_COPPER.get().asItem())).save(consumer, "rankine:copper_ingot_from_porphyry_copper_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.STIBNITE_ORE.get()), RecipeCategory.MISC, RankineItems.ANTIMONY.get(), 0.5F, 200).unlockedBy("has_ingredient", has(RankineBlocks.STIBNITE_ORE.get().asItem())).save(consumer, "rankine:antimony_ingot_from_stibnite_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.STIBNITE_ORE.get()), RecipeCategory.MISC, RankineItems.ANTIMONY.get(), 0.5F, 100).unlockedBy("has_ingredient", has(RankineBlocks.STIBNITE_ORE.get().asItem())).save(consumer, "rankine:antimony_ingot_from_stibnite_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.IRON_ORE.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.IRON_ORE.get().asItem())).save(consumer, "rankine:iron_ingot_from_rankine_iron_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.IRON_ORE.get()), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.IRON_ORE.get().asItem())).save(consumer, "rankine:iron_ingot_from_rankine_iron_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.COPPER_ORE.get()), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.COPPER_ORE.get().asItem())).save(consumer, "rankine:copper_ingot_from_rankine_copper_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.COPPER_ORE.get()), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.COPPER_ORE.get().asItem())).save(consumer, "rankine:copper_ingot_from_rankine_copper_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_gold_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_nether_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NETHER_GOLD_ORE.get()), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_GOLD_ORE.get().asItem())).save(consumer, "rankine:gold_ingot_from_rankine_nether_gold_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 200).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_QUARTZ_ORE.get().asItem())).save(consumer, "rankine:quartz_ingot_from_rankine_nether_quartz_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.NETHER_QUARTZ_ORE.get()), RecipeCategory.MISC, Items.QUARTZ, 0.2F, 100).unlockedBy("has_ingredient", has(RankineBlocks.NETHER_QUARTZ_ORE.get().asItem())).save(consumer, "rankine:quartz_ingot_from_rankine_nether_quartz_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.COAL_ORE.get()), RecipeCategory.MISC, Items.COAL, 0.1F, 200).unlockedBy("has_ingredient", has(RankineBlocks.COAL_ORE.get().asItem())).save(consumer, "rankine:coal_from_rankine_coal_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.COAL_ORE.get()), RecipeCategory.MISC, Items.COAL, 0.1F, 100).unlockedBy("has_ingredient", has(RankineBlocks.COAL_ORE.get().asItem())).save(consumer, "rankine:coal_from_rankine_coal_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.LAPIS_ORE.get()), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 200).unlockedBy("has_ingredient", has(RankineBlocks.LAPIS_ORE.get().asItem())).save(consumer, "rankine:lapis_from_rankine_lapis_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.LAPIS_ORE.get()), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F,   100).unlockedBy("has_ingredient", has(RankineBlocks.LAPIS_ORE.get().asItem())).save(consumer, "rankine:lapis_from_rankine_lapis_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.REDSTONE_ORE.get()), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 200).unlockedBy("has_ingredient", has(RankineBlocks.REDSTONE_ORE.get().asItem())).save(consumer, "rankine:redstone_from_rankine_redstone_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.REDSTONE_ORE.get()), RecipeCategory.MISC, Items.REDSTONE, 0.7F, 100).unlockedBy("has_ingredient", has(RankineBlocks.REDSTONE_ORE.get().asItem())).save(consumer, "rankine:redstone_from_rankine_redstone_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.DIAMOND_ORE.get()), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.DIAMOND_ORE.get().asItem())).save(consumer, "rankine:diamond_from_rankine_diamond_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.DIAMOND_ORE.get()), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.DIAMOND_ORE.get().asItem())).save(consumer, "rankine:diamond_from_rankine_diamond_ore_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.EMERALD_ORE.get()), RecipeCategory.MISC, Items.EMERALD, 1.0F, 200).unlockedBy("has_ingredient", has(RankineBlocks.EMERALD_ORE.get().asItem())).save(consumer, "rankine:emerlad_from_rankine_emerlad_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(RankineItems.EMERALD_ORE.get()), RecipeCategory.MISC, Items.EMERALD, 1.0F, 100).unlockedBy("has_ingredient", has(RankineBlocks.EMERALD_ORE.get().asItem())).save(consumer, "rankine:emerlad_from_rankine_emerlad_ore_blasting");

        //ALLOY STUFFS
        for (Item NUGGET : RankineLists.ALLOY_NUGGETS) {
            Block BLOCK = RankineLists.ALLOY_BLOCKS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));
            Item INGOT = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_NUGGETS.indexOf(NUGGET));

            if (!INGOT.equals(RankineItems.SOLDER.get())) {
                threeXthreeAlloy(consumer, BLOCK.asItem(), INGOT, 1, "has_ingredient", INGOT, 16777215,null);
                threeXthreeAlloy(consumer, INGOT, NUGGET, 1, "has_ingredient", NUGGET, getItemName(INGOT)+"_from_"+getItemName(NUGGET),16777215,null);
                OneToXAlloy(consumer, INGOT, BLOCK.asItem(), 9, "has_ingredient", BLOCK.asItem(), getItemName(INGOT)+"_from_"+getItemName(BLOCK));
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
        for (Block BLK : RankineLists.ALLOY_LADDERS) {
            Item ALLOY = RankineLists.ALLOY_INGOTS.get(RankineLists.ALLOY_LADDERS.indexOf(BLK));
            metalLadder(consumer, BLK.asItem(), "rankine:ladders", ALLOY, "has_ingredient", ALLOY);
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
            TagKey<Item> ingotTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.tryParse("forge:ingots/"+ getItemName(BLK).replace("_sheetmetal","")));
            TagKey<Item> nugTag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.tryParse("forge:nuggets/"+ getItemName(BLK).replace("_sheetmetal","")));

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BLK, 8).pattern("#I#").pattern("#I#").pattern("#I#").define('I', ingotTag).define('#', nugTag).group("sheetmetal").unlockedBy("has_ingredient", has(ingotTag)).save(consumer);
        }
        
        hLine(consumer,RankineItems.TAP_LINE.get(),3,RankineItems.VULCANIZED_RUBBER.get(),"has_ingredient",RankineItems.VULCANIZED_RUBBER.get());
        hLine(consumer,RankineItems.METAL_PIPE.get(),8,RankineItems.CUPRONICKEL_INGOT.get(),"has_ingredient",RankineItems.CUPRONICKEL_INGOT.get());

        //Beehive Oven
        for (Map.Entry<Ingredient,Block> entry : BEEHIVE_OVEN_MINERAL_MAP.entrySet()) {
            BeehiveOvenRecipeBuilder.beehiveOvenRecipe(entry.getKey(),entry.getValue(), 2400, 4800).save(consumer,new ResourceLocation("rankine:beehive_oven/"+getItemName(entry.getKey().getItems()[0].getItem())+"_beehive_oven_cooking"));
        }
        for (Map.Entry<Ingredient,Block> entry : BEEHIVE_OVEN_OTHER_MAP.entrySet()) {
            BeehiveOvenRecipeBuilder.beehiveOvenRecipe(entry.getKey(),entry.getValue()).save(consumer,new ResourceLocation("rankine:beehive_oven/"+getItemName(entry.getKey().getItems()[0].getItem())+"_beehive_oven_cooking"));
        }
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(StoneBlocks.LIMESTONE.getStone()), RankineBlocks.QUICKLIME_BLOCK.get()).save(consumer, new ResourceLocation("rankine:beehive_oven/quicklime_from_limestone_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(StoneBlocks.DOLOSTONE.getStone()), RankineBlocks.QUICKLIME_BLOCK.get()).save(consumer, new ResourceLocation("rankine:beehive_oven/quicklime_from_dolomite_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(ItemTags.SAND), Blocks.GLASS).save(consumer, new ResourceLocation("rankine:beehive_oven/glass_from_sand_beehive_oven_cooking"));
        BeehiveOvenRecipeBuilder.beehiveOvenRecipe(Ingredient.of(RankineTags.Items.SILT), Blocks.GLASS).save(consumer, new ResourceLocation("rankine:beehive_oven/glass_from_silt_beehive_oven_cooking"));


        //Campfire
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.BAMBOO), RecipeCategory.MISC, RankineItems.DRIED_BAMBOO.get(), 0.35F, 40).unlockedBy("has_ingredient", has(Items.BAMBOO)).save(consumer, "rankine:dried_bamboo_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.BONE), RecipeCategory.MISC, RankineItems.BONE_ASH.get(), 0.35F, 200).unlockedBy("has_ingredient", has(Items.BONE)).save(consumer, "rankine:bone_ash_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.FIRE_CLAY_BALL.get()), RecipeCategory.MISC, RankineItems.REFRACTORY_BRICK.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.FIRE_CLAY_BALL.get())).save(consumer, "rankine:refractory_brick_campfire_cooking");

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.PANCAKE_BATTER.get()), RecipeCategory.MISC, RankineItems.PANCAKE.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.PANCAKE_BATTER.get())).save(consumer, "rankine:pancake_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.CORN_EAR.get()), RecipeCategory.MISC, RankineItems.POPCORN.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.CORN_EAR.get())).save(consumer, "rankine:popcorn_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.ASPARAGUS.get()), RecipeCategory.MISC, RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.ROASTED_ASPARAGUS.get())).save(consumer, "rankine:roasted_asparagus_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.BLACK_WALNUT.get()), RecipeCategory.MISC, RankineItems.ROASTED_WALNUT.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.BLACK_WALNUT.get())).save(consumer, "rankine:roasted_walnut_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.COCONUT.get()), RecipeCategory.MISC, RankineItems.TOASTED_COCONUT.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.COCONUT.get())).save(consumer, "rankine:toasted_coconut_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.BREAD), RecipeCategory.MISC, RankineItems.TOAST.get(), 0.35F, 600).unlockedBy("has_ingredient", has(Items.BREAD)).save(consumer, "rankine:toast_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.DOUGH.get()), RecipeCategory.MISC, Items.BREAD, 0.35F, 600).unlockedBy("has_ingredient", has(RankineTags.Items.FLOUR)).save(consumer, "rankine:bread_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.TOFU.get()), RecipeCategory.MISC, RankineItems.COOKED_TOFU.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer, "rankine:cooked_tofu_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.TUNA.get()), RecipeCategory.MISC, RankineItems.COOKED_TUNA.get(), 0.35F, 600).unlockedBy("has_ingredient", has(RankineItems.TUNA.get())).save(consumer, "rankine:cooked_tuna_campfire_cooking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RankineItems.TINDER_CONK_MUSHROOM.get()), RecipeCategory.MISC, RankineItems.SMOULDERING_TINDER_CONK.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.TINDER_CONK_MUSHROOM.get())).save(consumer, "rankine:smouldering_tinder_conk_campfire_cooking");
        //Smoking
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.PANCAKE_BATTER.get()), RecipeCategory.MISC, RankineItems.PANCAKE.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.PANCAKE_BATTER.get())).save(consumer, "rankine:pancake_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.CORN_EAR.get()), RecipeCategory.MISC, RankineItems.POPCORN.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.CORN_EAR.get())).save(consumer, "rankine:popcorn_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.ASPARAGUS.get()), RecipeCategory.MISC, RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.ROASTED_ASPARAGUS.get())).save(consumer, "rankine:roasted_asparagus_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.BLACK_WALNUT.get()),RecipeCategory.MISC, RankineItems.ROASTED_WALNUT.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.BLACK_WALNUT.get())).save(consumer, "rankine:roasted_walnut_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.COCONUT.get()), RecipeCategory.MISC, RankineItems.TOASTED_COCONUT.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.COCONUT.get())).save(consumer, "rankine:toasted_coconut_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.BREAD), RecipeCategory.MISC, RankineItems.TOAST.get(), 0.35F, 100).unlockedBy("has_ingredient", has(Items.BREAD)).save(consumer, "rankine:toast_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.TOFU.get()), RecipeCategory.MISC, RankineItems.COOKED_TOFU.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer, "rankine:cooked_tofu_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.TUNA.get()), RecipeCategory.MISC, RankineItems.COOKED_TUNA.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.TUNA.get())).save(consumer, "rankine:cooked_tuna_smoking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RankineItems.TINDER_CONK_MUSHROOM.get()), RecipeCategory.MISC, RankineItems.SMOULDERING_TINDER_CONK.get(), 0.35F, 100).unlockedBy("has_ingredient", has(RankineItems.TINDER_CONK_MUSHROOM.get())).save(consumer, "rankine:smouldering_tinder_conk_smoking");
        //furnace
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.PANCAKE_BATTER.get()), RecipeCategory.MISC, RankineItems.PANCAKE.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.PANCAKE_BATTER.get())).save(consumer, "rankine:pancake_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.CORN_EAR.get()), RecipeCategory.MISC, RankineItems.POPCORN.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.CORN_EAR.get())).save(consumer, "rankine:popcorn_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.ASPARAGUS.get()), RecipeCategory.MISC, RankineItems.ROASTED_ASPARAGUS.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.ROASTED_ASPARAGUS.get())).save(consumer, "rankine:roasted_asparagus_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.BLACK_WALNUT.get()), RecipeCategory.MISC, RankineItems.ROASTED_WALNUT.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.BLACK_WALNUT.get())).save(consumer, "rankine:roasted_walnut_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.COCONUT.get()), RecipeCategory.MISC, RankineItems.TOASTED_COCONUT.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.COCONUT.get())).save(consumer, "rankine:toasted_coconut_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BREAD), RecipeCategory.MISC, RankineItems.TOAST.get(), 0.35F, 100).unlockedBy("has_ingredient", has(Items.BREAD)).save(consumer, "rankine:toast_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.TOFU.get()), RecipeCategory.MISC, RankineItems.COOKED_TOFU.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.TOFU.get())).save(consumer, "rankine:cooked_tofu_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.TUNA.get()), RecipeCategory.MISC, RankineItems.COOKED_TUNA.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.TUNA.get())).save(consumer, "rankine:cooked_tuna_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RankineItems.TINDER_CONK_MUSHROOM.get()), RecipeCategory.MISC, RankineItems.SMOULDERING_TINDER_CONK.get(), 0.35F, 200).unlockedBy("has_ingredient", has(RankineItems.TINDER_CONK_MUSHROOM.get())).save(consumer, "rankine:smouldering_tinder_conk_smelting");


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

    private void slabReconstruct(Consumer<FinishedRecipe> consumer, ItemLike slabBlock, ItemLike mainBLock) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, mainBLock).requires(slabBlock).requires(slabBlock).group("block_from_slab").unlockedBy("has_ingredient", has(mainBLock)).save(consumer,"rankine:"+getItemName(mainBLock.asItem())+"_from_slab");
    }
    private void stairsReconstruct(Consumer<FinishedRecipe> consumer, ItemLike stairsBlock, ItemLike mainBLock) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, mainBLock,3).requires(stairsBlock).requires(stairsBlock).requires(stairsBlock).requires(stairsBlock).group("block_from_stairs").unlockedBy("has_ingredient", has(mainBLock)).save(consumer,"rankine:"+getItemName(mainBLock.asItem())+"_from_stairs");
    }

    private void slab(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 6)
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void stairs(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void wall(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    private void pressurePlate(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                .pattern("##")
                .define('#', input)
                .group(group)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }
    
    
    
    
    
    
    
    
    
    
    
    private void door(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void trapdoor(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void boat(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                .pattern("# #")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void fence(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', input)
                .define('S', Tags.Items.RODS_WOODEN)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void fenceGate(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', input)
                .define('S', Tags.Items.RODS_WOODEN)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void bookshelf(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 1)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, 4)
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
    
    private void pedestal(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .pattern(" P ")
                .pattern(" # ")
                .pattern(" # ")
                .define('#', input)
                .define('P', RankineTags.Items.STONE_PRESSURE_PLATES)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void metalLadder(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output,8)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void bars(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output,16)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void pole(Consumer<FinishedRecipe> consumer, Item output, String group, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output,8)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                //.setGroup("rankine")
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                //.setGroup("rankine")
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike output, TagKey<Item> input, int count, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                //.setGroup("rankine")
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void centerRing(Consumer<FinishedRecipe> consumer, Item output, int count, Ingredient ring, Ingredient center, String group, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .pattern("#A#")
                .pattern("###")
                .define('A', center)
                .define('#', ring)
                .group(group)
                .unlockedBy("has_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void threeXthreeAlloy(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger, String name,int color,@Nullable String langName) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true,"",langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', input)
                .setGroup("rankine:alloy_blocks")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void threeXthreeAlloy(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger,int color,@Nullable String langName) {
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
        String rs = ForgeRegistries.ITEMS.getKey(ingot).toString();
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
    }

    private void mimicToolRecipes(Consumer<FinishedRecipe> consumer, AlloyItem ingot, Item mimic, int alloyColor, String alloyLang, boolean steelRod, String recipeName) {
        String rs = ForgeRegistries.ITEMS.getKey(mimic).toString();
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
    }

    private void alloyRod(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 4,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("  #")
                .patternLine(" # ")
                .patternLine("#  ")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_rods")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName+"_rod");
    }

    private void alloyGear(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyHelmet(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color,@Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("   ")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_helmets")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyChestplate(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger,int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("# #")
                .patternLine("###")
                .patternLine("###")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_chestplate")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyLeggings(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color,@Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("###")
                .patternLine("# #")
                .patternLine("# #")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_leggings")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyBoots(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, 1,true,inheritRecipe == null ? "" : inheritRecipe,langName == null ? "" : langName, color)
                .patternLine("   ")
                .patternLine("# #")
                .patternLine("# #")
                .directAlloyKey('#', input)
                .setGroup("rankine:alloy_boots")
                .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer,recipeName);
    }

    private void alloyCrowbar(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloySurfRod(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyBlunderbuss(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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
                    .key('s', ItemTags.LOGS)
                    .key('f', Items.FLINT_AND_STEEL)
                    .setGroup("rankine:alloy_blunderbuss")
                    .addCriterion("has_alloy", InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                    .build(consumer,recipeName+"blunderbuss");
        }

    }

    private void alloyPickaxe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyHoe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyAxe(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger, int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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



    private void alloyShovel(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloySword(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyHammer(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloyKnife(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void alloySpear(Consumer<FinishedRecipe> consumer, Item output, AlloyIngredient input, AlloyIngredient rod, ItemLike trigger,  int color, @Nullable String langName, String recipeName, String inheritRecipe) {
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

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer, new ResourceLocation("rankine",name));
    }
    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike output, TagKey<Item> input, int count, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void OneToX(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger, String name) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)
            .requires(input)
            //.setGroup("rankine")
            .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
            .save(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToX(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)
                .requires(input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void OneToXAlloy(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger, String name) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                .setGroup("rankine:alloy_nuggets")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer, new ResourceLocation("rankine",name));
    }
    private void OneToXAlloy(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count, String triggerName, ItemLike trigger) {
        AlloyCraftingRecipeBuilder.shapedRecipe(output, count,true)
                .patternLine("#")
                .key('#', input)
                .setGroup("rankine:alloy_nuggets")
                .addCriterion(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .build(consumer);
    }

    private void OneToXTag(Consumer<FinishedRecipe> consumer, Item output, String group, TagKey<Item> input, int count, String triggerName, ItemLike trigger) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)
                .requires(input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    
    private void hLine(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String group, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .define('#', input)
                .group(group)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }    
    private void hLine(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("###")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }
    private void rod(Consumer<FinishedRecipe> consumer, Item output, int count, Item input, String triggerName, ItemLike trigger) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("  #")
                .pattern(" # ")
                .pattern("#  ")
                .define('#', input)
                .unlockedBy(triggerName, InventoryChangeTrigger.TriggerInstance.hasItems(trigger))
                .save(consumer);
    }

    private void metalLadder(Consumer<FinishedRecipe> consumer, Item output, int count, TagKey<Item> input) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count)
                .pattern("# #")
                .pattern("###")
                .pattern("# #")
                .define('#', input)
                .unlockedBy("has_ingredient", has(input))
                .save(consumer);
    }


    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> p_176736_, ItemLike p_176737_, ItemLike p_176738_) {
        stonecutterResultFromBase(p_176736_, p_176737_, p_176738_, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> p_176547_, ItemLike p_176548_, ItemLike p_176549_, int p_176550_) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(p_176549_), RecipeCategory.MISC, p_176548_, p_176550_).unlockedBy(getHasName(p_176549_), has(p_176549_)).save(p_176547_, "rankine:"+getConversionRecipeName(p_176548_, p_176549_) + "_stonecutting");
    }

}