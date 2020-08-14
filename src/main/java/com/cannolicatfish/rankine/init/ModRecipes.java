package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.cannolicatfish.rankine.util.alloys.GreenGoldAlloyUtils;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.lang.ref.Reference;
import java.util.*;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProjectRankine.MODID);

    private static void registerType(ResourceLocation name, IRecipeType<?> recipeType) {
        Registry.register(Registry.RECIPE_TYPE, name, recipeType);
    }

    public static void init() {
        registerType(new ResourceLocation(ProjectRankine.MODID,"crushing"), IPistonCrusherRecipe.RECIPE_TYPE);
    }
    public static final RegistryObject<IPistonCrusherRecipe.Serializer> PISTON_CRUSHER_SERIALIZER = REGISTRY.register("crushing", IPistonCrusherRecipe.Serializer::new);

    public static List<IAlloyRecipe> getAlloyRecipes()
    {
        List<IAlloyRecipe> recipes = new ArrayList<>();
        recipes.add(alloyRecipe("bronze_alloy",new ItemStack(ModItems.BRONZE_ALLOY),Arrays.asList(ModItems.COPPER_INGOT,ModItems.TIN_INGOT,ModItems.ALUMINUM_INGOT,
                ModItems.MANGANESE_INGOT,ModItems.NICKEL_INGOT,ModItems.ZINC_INGOT),new AbstractMap.SimpleEntry<>(.8f,.9f),new AbstractMap.SimpleEntry<>(.1f,.2f),
                new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("aluminum_bronze_alloy",new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY),Arrays.asList(ModItems.COPPER_INGOT,ModItems.ALUMINUM_INGOT,
                ModItems.MANGANESE_INGOT,ModItems.NICKEL_INGOT,ModItems.ZINC_INGOT,ModItems.ARSENIC_INGOT,Items.IRON_INGOT,ModItems.LEAD_INGOT),
                new AbstractMap.SimpleEntry<>(.74f,.93f), new AbstractMap.SimpleEntry<>(.04f,.12f),new AbstractMap.SimpleEntry<>(0f,.15f),.85f));

        recipes.add(alloyRecipe("brass_alloy",new ItemStack(ModItems.BRASS_ALLOY),Arrays.asList(ModItems.COPPER_INGOT,ModItems.ZINC_INGOT,
                ModItems.TIN_INGOT,ModItems.LEAD_INGOT,ModItems.ALUMINUM_INGOT,ModItems.NICKEL_INGOT,Items.IRON_INGOT),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.15f,.6f),new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("cupronickel_alloy",new ItemStack(ModItems.CUPRONICKEL_ALLOY),Arrays.asList(ModItems.COPPER_INGOT,ModItems.NICKEL_INGOT,
                Items.IRON_INGOT,ModItems.MANGANESE_INGOT),
                new AbstractMap.SimpleEntry<>(.7f,.9f), new AbstractMap.SimpleEntry<>(.1f,.3f),new AbstractMap.SimpleEntry<>(0f,.05f),.95f));

        recipes.add(alloyRecipe("nickel_silver_alloy",new ItemStack(ModItems.NICKEL_SILVER_ALLOY),Arrays.asList(ModItems.COPPER_INGOT,ModItems.NICKEL_INGOT,
                ModItems.ZINC_INGOT),
                new AbstractMap.SimpleEntry<>(.5f,.7f), new AbstractMap.SimpleEntry<>(.15f,.25f),new AbstractMap.SimpleEntry<>(.15f,.25f),1f));

        recipes.add(alloyRecipe("invar_alloy",new ItemStack(ModItems.INVAR_ALLOY),Arrays.asList(Items.IRON_INGOT,ModItems.NICKEL_INGOT),
                new AbstractMap.SimpleEntry<>(.5f,.9f), new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("rose_gold_alloy",new ItemStack(ModItems.ROSE_GOLD_ALLOY),Arrays.asList(Items.GOLD_INGOT,ModItems.COPPER_INGOT,
                ModItems.SILVER_INGOT,ModItems.ZINC_INGOT),
                new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.6f),.94f));

        recipes.add(alloyRecipe("white_gold_alloy",new ItemStack(ModItems.WHITE_GOLD_ALLOY),Arrays.asList(Items.GOLD_INGOT,ModItems.ZINC_INGOT,
                ModItems.NICKEL_INGOT,ModItems.PALLADIUM_INGOT,ModItems.SILVER_INGOT,ModItems.PLATINUM_INGOT),
                new AbstractMap.SimpleEntry<>(.74f,.9f), new AbstractMap.SimpleEntry<>(.05f,.1f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("green_gold_alloy",new ItemStack(ModItems.GREEN_GOLD_ALLOY),Arrays.asList(Items.GOLD_INGOT,ModItems.SILVER_INGOT,
                ModItems.COPPER_INGOT,ModItems.CADMIUM_INGOT,ModItems.PLATINUM_INGOT),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.3f,.7f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy",new ItemStack(ModItems.BLUE_GOLD_ALLOY),Arrays.asList(Items.GOLD_INGOT,Items.IRON_INGOT,
                ModItems.NICKEL_INGOT,ModItems.RHODIUM_INGOT,ModItems.RUTHENIUM_INGOT),
                new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("purple_gold_alloy",new ItemStack(ModItems.PURPLE_GOLD_ALLOY),Arrays.asList(Items.GOLD_INGOT,ModItems.ALUMINUM_INGOT),
                new AbstractMap.SimpleEntry<>(.79f,.81f), new AbstractMap.SimpleEntry<>(.19f,.21f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("nickel_superalloy",new ItemStack(ModItems.NICKEL_SUPERALLOY),Arrays.asList(ModItems.NICKEL_INGOT,ModItems.CHROMIUM_INGOT,
                ModItems.SILICON, ModItems.MANGANESE_INGOT,ModItems.ALUMINUM_INGOT,ModItems.TITANIUM_INGOT,ModItems.CARBON_INGOT,Items.IRON_INGOT,
                ModItems.VANADIUM_INGOT),new AbstractMap.SimpleEntry<>(.5f,.75f),new AbstractMap.SimpleEntry<>(.14f,.2f),
                new AbstractMap.SimpleEntry<>(0.05f,.36f),.64f));

                /*recipes.add(alloyRecipe("nickel_superalloy",new ItemStack(ModItems.NICKEL_SUPERALLOY),Arrays.asList(ModItems.NICKEL_INGOT,ModItems.CHROMIUM_INGOT,
                ModItems.MOLYBDENUM_INGOT,ModItems.NIOBIUM_INGOT,ModItems.TANTALUM_INGOT,ModItems.COBALT_INGOT,ModItems.MANGANESE_INGOT,
                ModItems.ALUMINUM_INGOT,ModItems.TITANIUM_INGOT,ModItems.SILICON,ModItems.CARBON_INGOT,ModItems.PHOSPHORUS,
                ModItems.BORON_INGOT, Items.IRON_INGOT,ModItems.VANADIUM_INGOT,ModItems.ZIRCONIUM_INGOT,ModItems.HAFNIUM_INGOT,
                ModItems.RHENIUM_INGOT,ModItems.TUNGSTEN_INGOT),new AbstractMap.SimpleEntry<>(.5f,.75f),new AbstractMap.SimpleEntry<>(.14f,.2f),
                new AbstractMap.SimpleEntry<>(0.05f,.36f),.64f));*/
        return recipes;
    }

    public static List<IBeehiveOvenRecipe> getBeehiveOvenRecipes()
    {
        List<IBeehiveOvenRecipe> recipes = new ArrayList<>();
        recipes.add(beehiveOvenRecipe("limestone_oven_cooking",ModBlocks.LIMESTONE.asItem(),new ItemStack(ModBlocks.QUICKLIME_BLOCK)));
        recipes.add(beehiveOvenRecipe("magnesite_oven_cooking",ModBlocks.MAGNESITE_BLOCK.asItem(),new ItemStack(ModBlocks.MAGNESIA_BLOCK)));
        recipes.add(beehiveOvenRecipe("coal_oven_cooking",Blocks.COAL_BLOCK.asItem(),new ItemStack(ModBlocks.COKE_BLOCK)));
        recipes.add(beehiveOvenRecipe("bituminous_oven_cooking",ModBlocks.BITUMINOUS_COAL_BLOCK.asItem(),new ItemStack(ModBlocks.COKE_BLOCK)));

        return recipes;
    }
    public static List<IPistonCrusherRecipe> getCrushingRecipes()
    {
        List<IPistonCrusherRecipe> recipes = new ArrayList<>();

        //Vanilla Blocks
        recipes.add(crushingRecipe("cobblestone_crushing",Blocks.COBBLESTONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("stone_crushing",Blocks.STONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("smooth_stone_crushing",Blocks.SMOOTH_STONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("stone_bricks_crushing",Blocks.STONE_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("sandstone_crushing",Blocks.SANDSTONE.asItem(), new ItemStack(Items.SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("red_sandstone_crushing",Blocks.RED_SANDSTONE.asItem(), new ItemStack(Items.RED_SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));

        //Mod Blocks
        recipes.add(crushingRecipe("red_granite_crushing",Blocks.GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("quartz_diorite_crushing",Blocks.DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gray_andesite_crushing",Blocks.ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("granite_crushing",ModBlocks.GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("diorite_crushing",ModBlocks.DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("andesite_crushing",ModBlocks.ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("limestone_crushing",ModBlocks.LIMESTONE.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("shale_crushing",ModBlocks.SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_crushing",ModBlocks.ANORTHOSITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("ironstone_crushing",ModBlocks.IRONSTONE.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("basalt_crushing",ModBlocks.BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("rhyolite_crushing",ModBlocks.RHYOLITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("marble_crushing",ModBlocks.MARBLE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("gneiss_crushing",ModBlocks.GNEISS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.2f));
        recipes.add(crushingRecipe("peridotite_crushing",ModBlocks.PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("komatiite_crushing",ModBlocks.KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("ringwoodite_crushing",ModBlocks.RINGWOODITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("wadsleyite_crushing",ModBlocks.WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("bridgmanite_crushing",ModBlocks.BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("kimberlite_crushing",ModBlocks.KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("ferropericlase_crushing",ModBlocks.FERROPERICLASE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("perovskite_crushing",ModBlocks.PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("pumice_crushing",ModBlocks.PUMICE.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("scoria_crushing",ModBlocks.SCORIA.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));

        recipes.add(crushingRecipe("smooth_red_granite_crushing",Blocks.POLISHED_GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("smooth_quartz_diorite_crushing",Blocks.POLISHED_DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("smooth_gray_andesite_crushing",Blocks.POLISHED_ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("smooth_granite_crushing",ModBlocks.SMOOTH_GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("smooth_diorite_crushing",ModBlocks.SMOOTH_DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("smooth_andesite_crushing",ModBlocks.SMOOTH_ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("smooth_limestone_crushing",ModBlocks.SMOOTH_LIMESTONE.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("smooth_shale_crushing",ModBlocks.SMOOTH_SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("smooth_anorthosite_crushing",ModBlocks.SMOOTH_ANORTHOSITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("smooth_ironstone_crushing",ModBlocks.SMOOTH_IRONSTONE.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("smooth_basalt_crushing",ModBlocks.SMOOTH_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("smooth_rhyolite_crushing",ModBlocks.SMOOTH_RHYOLITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_marble_crushing",ModBlocks.SMOOTH_MARBLE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_gneiss_crushing",ModBlocks.SMOOTH_GNEISS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.2f));
        recipes.add(crushingRecipe("smooth_peridotite_crushing",ModBlocks.SMOOTH_PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("smooth_komatiite_crushing",ModBlocks.SMOOTH_KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("smooth_ringwoodite_crushing",ModBlocks.SMOOTH_RINGWOODITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("smooth_wadsleyite_crushing",ModBlocks.SMOOTH_WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("smooth_bridgmanite_crushing",ModBlocks.SMOOTH_BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("smooth_kimberlite_crushing",ModBlocks.SMOOTH_KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("smooth_ferropericlase_crushing",ModBlocks.SMOOTH_FERROPERICLASE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("smooth_perovskite_crushing",ModBlocks.SMOOTH_PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("smooth_pumice_crushing",ModBlocks.SMOOTH_PUMICE.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("smooth_scoria_crushing",ModBlocks.SMOOTH_SCORIA.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));

        recipes.add(crushingRecipe("red_granite_bricks_crushing",ModBlocks.RED_GRANITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("quartz_diorite_bricks_crushing",ModBlocks.QUARTZ_DIORITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gray_andesite_bricks_crushing",ModBlocks.GRAY_ANDESITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("granite_bricks_crushing",ModBlocks.GRANITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("diorite_bricks_crushing",ModBlocks.DIORITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("andesite_bricks_crushing",ModBlocks.ANDESITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("limestone_bricks_crushing",ModBlocks.LIMESTONE_BRICKS.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("shale_bricks_crushing",ModBlocks.SHALE_BRICKS.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_bricks_crushing",ModBlocks.ANORTHOSITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("ironstone_bricks_crushing",ModBlocks.IRONSTONE_BRICKS.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("basalt_bricks_crushing",ModBlocks.BASALT_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("rhyolite_bricks_crushing",ModBlocks.RHYOLITE_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("marble_bricks_crushing",ModBlocks.MARBLE_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("gneiss_bricks_crushing",ModBlocks.GNEISS_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.2f));
        recipes.add(crushingRecipe("peridotite_bricks_crushing",ModBlocks.PERIDOTITE_BRICKS.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("komatiite_bricks_crushing",ModBlocks.KOMATIITE_BRICKS.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("ringwoodite_bricks_crushing",ModBlocks.RINGWOODITE_BRICKS.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("wadsleyite_bricks_crushing",ModBlocks.WADSLEYITE_BRICKS.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("bridgmanite_bricks_crushing",ModBlocks.BRIDGMANITE_BRICKS.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("kimberlite_bricks_crushing",ModBlocks.KIMBERLITE_BRICKS.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("ferropericlase_bricks_crushing",ModBlocks.FERROPERICLASE_BRICKS.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("perovskite_bricks_crushing",ModBlocks.PEROVSKITE_BRICKS.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("pumice_bricks_crushing",ModBlocks.PUMICE_BRICKS.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("scoria_bricks_crushing",ModBlocks.SCORIA_BRICKS.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));

        //Mod Ores
        recipes.add(crushingRecipe("magnetite_ore_crushing",ModBlocks.MAGNETITE_ORE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.CHROMIUM_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("malachite_ore_crushing",ModBlocks.MALACHITE_ORE.asItem(), new ItemStack(ModItems.MALACHITE,1), new ItemStack(ModItems.AZURITE,1),0.1f));
        recipes.add(crushingRecipe("bauxite_ore_crushing",ModBlocks.BAUXITE_ORE.asItem(), new ItemStack(ModItems.ALUMINA,1), new ItemStack(ModItems.TITANIA,1),0.05f));
        recipes.add(crushingRecipe("cassiterite_ore_crushing",ModBlocks.CASSITERITE_ORE.asItem(), new ItemStack(ModItems.CASSITERITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("sphalerite_ore_crushing",ModBlocks.SPHALERITE_ORE.asItem(), new ItemStack(ModItems.SPHALERITE,1), new ItemStack(ModItems.OPAL,1),0.05f));
        recipes.add(crushingRecipe("cinnabarite_ore_crushing",ModBlocks.CINNABAR_ORE.asItem(), new ItemStack(Items.REDSTONE,4), new ItemStack(ModItems.SULFUR,1),0.25f));
        recipes.add(crushingRecipe("pentlandite_ore_crushing",ModBlocks.PENTLANDITE_ORE.asItem(), new ItemStack(ModItems.PENTLANDITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("magnesite_ore_crushing",ModBlocks.MAGNESITE_ORE.asItem(), new ItemStack(ModItems.MAGNESITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("galena_ore_crushing",ModBlocks.GALENA_ORE.asItem(), new ItemStack(ModItems.GALENA,1), new ItemStack(ModItems.SPHALERITE,1),0.1f));
        recipes.add(crushingRecipe("acanthite_ore_crushing",ModBlocks.ACANTHITE_ORE.asItem(), new ItemStack(ModItems.SILVER_SULFIDE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("pyrolusite_ore_crushing",ModBlocks.PYROLUSITE_ORE.asItem(), new ItemStack(ModItems.PYROLUSITE,1), new ItemStack(ModItems.TANTALUM_OXIDE,1),0.05f));
        recipes.add(crushingRecipe("bismite_ore_crushing",ModBlocks.BISMITE_ORE.asItem(), new ItemStack(ModItems.BISMUTH_OXIDE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("vanadinite_ore_crushing",ModBlocks.VANADINITE_ORE.asItem(), new ItemStack(ModItems.VANADINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("ilmenite_ore_crushing",ModBlocks.ILMENITE_ORE.asItem(), new ItemStack(ModItems.TITANIA,1), new ItemStack(ModItems.MALACHITE,1),0.1f));
        recipes.add(crushingRecipe("molybdenite_ore_crushing",ModBlocks.MOLYBDENITE_ORE.asItem(), new ItemStack(ModItems.MOLYBDENUM_OXIDE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(), new ItemStack(ModItems.CHROMIUM_OXIDE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("columbite_ore_crushing",ModBlocks.COLUMBITE_ORE.asItem(), new ItemStack(ModItems.NIOBIUM_OXIDE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("tantalite_ore_crushing",ModBlocks.TANTALITE_ORE.asItem(), new ItemStack(ModItems.TANTALUM_OXIDE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("wolframite_ore_crushing",ModBlocks.WOLFRAMITE_ORE.asItem(), new ItemStack(ModItems.TUNGSTEN_OXIDE,1), new ItemStack(ModItems.CASSITERITE,1),0.2f));
        recipes.add(crushingRecipe("greenokite_ore_crushing",ModBlocks.GREENOCKITE_ORE.asItem(), new ItemStack(ModItems.GREENOCKITE,1), new ItemStack(ModItems.GALENA,1),0.2f));
        recipes.add(crushingRecipe("uraninite_ore_crushing",ModBlocks.URANINITE_ORE.asItem(), new ItemStack(ModItems.URANINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("lignite_ore_crushing",ModBlocks.LIGNITE_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,1), new ItemStack(ModItems.GRAPHITE,1),0.01f));
        recipes.add(crushingRecipe("sub_bituminous_coal_ore_crushing",ModBlocks.SUBBITUMINOUS_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,2), new ItemStack(ModItems.GRAPHITE,1),0.02f));
        recipes.add(crushingRecipe("bituminous_coal_ore_crushing",ModBlocks.BITUMINOUS_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,2), new ItemStack(ModItems.GRAPHITE,1),0.05f));
        recipes.add(crushingRecipe("anthracite_ore_crushing",ModBlocks.ANTHRACITE_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,3), new ItemStack(ModItems.GRAPHITE,1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(),new ItemStack(ModItems.CHROMIUM_OXIDE),new ItemStack(ModItems.MAGNETITE), 0.1f));
        recipes.add(crushingRecipe("sperrylite_ore_crushing",ModBlocks.SPERRYLITE_ORE.asItem(), new ItemStack(ModItems.PLATINUM_ARSENIDE,1), new ItemStack(ModItems.OSMIRIDIUM_ALLOY,1),1f));
        recipes.add(crushingRecipe("meteorite_crushing",ModBlocks.METEORITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("kamacite_crushing",ModBlocks.KAMACITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("antitaenite_crushing",ModBlocks.ANTITAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("taenite_crushing",ModBlocks.TAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("tetrataenite_crushing",ModBlocks.TETRATAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        // Example of using tags for recipe (don""t use unless necessary, i.e. large list of blocks)
        // recipes.addAll(groupCrushingRecipe("andesite_crushing","rankine:andesite",new ItemStack(ModItems.FELDSPAR),new ItemStack(ModItems.PYROXENE), 0.2f));
        return recipes;
    }

    public static List<ICoalForgeRecipe> getForgingRecipes()
    {
        List<ICoalForgeRecipe> recipes = new ArrayList<>();

        recipes.add(forgingRecipe("bronze_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_PICKAXE)));
        recipes.add(forgingRecipe("bronze_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_AXE)));
        recipes.add(forgingRecipe("bronze_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,1),new ItemStack(ModItems.BRONZE_SHOVEL)));
        recipes.add(forgingRecipe("bronze_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,2),new ItemStack(ModItems.BRONZE_HOE)));
        recipes.add(forgingRecipe("bronze_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.BRONZE_ALLOY,2),new ItemStack(ModItems.BRONZE_SWORD)));
        recipes.add(forgingRecipe("bronze_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_SPEAR)));
        recipes.add(forgingRecipe("bronze_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BRONZE_ALLOY,5),new ItemStack(ModItems.BRONZE_HAMMER)));

        recipes.add(forgingRecipe("aluminum_bronze_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_PICKAXE)));
        recipes.add(forgingRecipe("aluminum_bronze_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_AXE)));
        recipes.add(forgingRecipe("aluminum_bronze_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,1),new ItemStack(ModItems.BRONZE_SHOVEL)));
        recipes.add(forgingRecipe("aluminum_bronze_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,2),new ItemStack(ModItems.BRONZE_HOE)));
        recipes.add(forgingRecipe("aluminum_bronze_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,2),new ItemStack(ModItems.BRONZE_SWORD)));
        recipes.add(forgingRecipe("aluminum_bronze_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,3),new ItemStack(ModItems.BRONZE_SPEAR)));
        recipes.add(forgingRecipe("aluminum_bronze_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY,5),new ItemStack(ModItems.BRONZE_HAMMER)));

        recipes.add(forgingRecipe("amalgam_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_PICKAXE)));
        recipes.add(forgingRecipe("amalgam_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_AXE)));
        recipes.add(forgingRecipe("amalgam_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,1),new ItemStack(ModItems.AMALGAM_SHOVEL)));
        recipes.add(forgingRecipe("amalgam_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,2),new ItemStack(ModItems.AMALGAM_HOE)));
        recipes.add(forgingRecipe("amalgam_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.AMALGAM_ALLOY,2),new ItemStack(ModItems.AMALGAM_SWORD)));
        recipes.add(forgingRecipe("amalgam_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_SPEAR)));
        recipes.add(forgingRecipe("amalgam_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,5),new ItemStack(ModItems.AMALGAM_HAMMER)));

        recipes.add(forgingRecipe("meteoric_iron_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,3),new ItemStack(ModItems.METEORIC_IRON_PICKAXE)));
        recipes.add(forgingRecipe("meteoric_iron_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,3),new ItemStack(ModItems.METEORIC_IRON_AXE)));
        recipes.add(forgingRecipe("meteoric_iron_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,1),new ItemStack(ModItems.METEORIC_IRON_SHOVEL)));
        recipes.add(forgingRecipe("meteoric_iron_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,2),new ItemStack(ModItems.METEORIC_IRON_HOE)));
        recipes.add(forgingRecipe("meteoric_iron_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.METEORIC_IRON,2),new ItemStack(ModItems.METEORIC_IRON_SWORD)));
        recipes.add(forgingRecipe("meteoric_iron_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,3),new ItemStack(ModItems.METEORIC_IRON_SPEAR)));
        recipes.add(forgingRecipe("meteoric_iron_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.METEORIC_IRON,5),new ItemStack(ModItems.METEORIC_IRON_HAMMER)));

        recipes.add(forgingRecipe("invar_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,3),new ItemStack(ModItems.METEORIC_IRON_PICKAXE)));
        recipes.add(forgingRecipe("invar_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,3),new ItemStack(ModItems.METEORIC_IRON_AXE)));
        recipes.add(forgingRecipe("invar_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,1),new ItemStack(ModItems.METEORIC_IRON_SHOVEL)));
        recipes.add(forgingRecipe("invar_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,2),new ItemStack(ModItems.METEORIC_IRON_HOE)));
        recipes.add(forgingRecipe("invar_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.INVAR_ALLOY,2),new ItemStack(ModItems.METEORIC_IRON_SWORD)));
        recipes.add(forgingRecipe("invar_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,3),new ItemStack(ModItems.METEORIC_IRON_SPEAR)));
        recipes.add(forgingRecipe("invar_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.INVAR_ALLOY,5),new ItemStack(ModItems.METEORIC_IRON_HAMMER)));

        recipes.add(forgingRecipe("rose_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,3),new ItemStack(ModItems.ROSE_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("rose_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,3),new ItemStack(ModItems.ROSE_GOLD_AXE)));
        recipes.add(forgingRecipe("rose_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,1),new ItemStack(ModItems.ROSE_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("rose_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,2),new ItemStack(ModItems.ROSE_GOLD_HOE)));
        recipes.add(forgingRecipe("rose_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.ROSE_GOLD_ALLOY,2),new ItemStack(ModItems.ROSE_GOLD_SWORD)));
        recipes.add(forgingRecipe("rose_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,3),new ItemStack(ModItems.ROSE_GOLD_SPEAR)));
        recipes.add(forgingRecipe("rose_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.ROSE_GOLD_ALLOY,5),new ItemStack(ModItems.ROSE_GOLD_HAMMER)));

        recipes.add(forgingRecipe("white_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,3),new ItemStack(ModItems.WHITE_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("white_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,3),new ItemStack(ModItems.WHITE_GOLD_AXE)));
        recipes.add(forgingRecipe("white_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,1),new ItemStack(ModItems.WHITE_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("white_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,2),new ItemStack(ModItems.WHITE_GOLD_HOE)));
        recipes.add(forgingRecipe("white_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.WHITE_GOLD_ALLOY,2),new ItemStack(ModItems.WHITE_GOLD_SWORD)));
        recipes.add(forgingRecipe("white_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,3),new ItemStack(ModItems.WHITE_GOLD_SPEAR)));
        recipes.add(forgingRecipe("white_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.WHITE_GOLD_ALLOY,5),new ItemStack(ModItems.WHITE_GOLD_HAMMER)));

        recipes.add(forgingRecipe("green_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,3),new ItemStack(ModItems.GREEN_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("green_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,3),new ItemStack(ModItems.GREEN_GOLD_AXE)));
        recipes.add(forgingRecipe("green_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,1),new ItemStack(ModItems.GREEN_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("green_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,2),new ItemStack(ModItems.GREEN_GOLD_HOE)));
        recipes.add(forgingRecipe("green_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.GREEN_GOLD_ALLOY,2),new ItemStack(ModItems.GREEN_GOLD_SWORD)));
        recipes.add(forgingRecipe("green_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,3),new ItemStack(ModItems.GREEN_GOLD_SPEAR)));
        recipes.add(forgingRecipe("green_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.GREEN_GOLD_ALLOY,5),new ItemStack(ModItems.GREEN_GOLD_HAMMER)));

        recipes.add(forgingRecipe("blue_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,3),new ItemStack(ModItems.BLUE_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("blue_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,3),new ItemStack(ModItems.BLUE_GOLD_AXE)));
        recipes.add(forgingRecipe("blue_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,1),new ItemStack(ModItems.BLUE_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("blue_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,2),new ItemStack(ModItems.BLUE_GOLD_HOE)));
        recipes.add(forgingRecipe("blue_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.BLUE_GOLD_ALLOY,2),new ItemStack(ModItems.BLUE_GOLD_SWORD)));
        recipes.add(forgingRecipe("blue_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,3),new ItemStack(ModItems.BLUE_GOLD_SPEAR)));
        recipes.add(forgingRecipe("blue_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLUE_GOLD_ALLOY,5),new ItemStack(ModItems.BLUE_GOLD_HAMMER)));

        recipes.add(forgingRecipe("purple_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,3),new ItemStack(ModItems.PURPLE_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("purple_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,3),new ItemStack(ModItems.PURPLE_GOLD_AXE)));
        recipes.add(forgingRecipe("purple_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,1),new ItemStack(ModItems.PURPLE_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("purple_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,2),new ItemStack(ModItems.PURPLE_GOLD_HOE)));
        recipes.add(forgingRecipe("purple_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,2),new ItemStack(ModItems.PURPLE_GOLD_SWORD)));
        recipes.add(forgingRecipe("purple_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,3),new ItemStack(ModItems.PURPLE_GOLD_SPEAR)));
        recipes.add(forgingRecipe("purple_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,5),new ItemStack(ModItems.PURPLE_GOLD_HAMMER)));

        recipes.add(forgingRecipe("steel_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_PICKAXE)));
        recipes.add(forgingRecipe("steel_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_AXE)));
        recipes.add(forgingRecipe("steel_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,1),new ItemStack(ModItems.STEEL_SHOVEL)));
        recipes.add(forgingRecipe("steel_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,2),new ItemStack(ModItems.STEEL_HOE)));
        recipes.add(forgingRecipe("steel_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.STEEL_ALLOY,2),new ItemStack(ModItems.STEEL_SWORD)));
        recipes.add(forgingRecipe("steel_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_SPEAR)));
        recipes.add(forgingRecipe("steel_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,5),new ItemStack(ModItems.STEEL_HAMMER)));

        recipes.add(forgingRecipe("nickel_superalloy_pickaxe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_PICKAXE)));
        recipes.add(forgingRecipe("nickel_superalloy_axe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_AXE)));
        recipes.add(forgingRecipe("nickel_superalloy_shovel",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,1),new ItemStack(ModItems.NICKEL_SUPERALLOY_SHOVEL)));
        recipes.add(forgingRecipe("nickel_superalloy_hoe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,2),new ItemStack(ModItems.NICKEL_SUPERALLOY_HOE)));
        recipes.add(forgingRecipe("nickel_superalloy_sword",new ItemStack(ModItems.STEEL_ROD,1),new ItemStack(ModItems.NICKEL_SUPERALLOY,2),new ItemStack(ModItems.NICKEL_SUPERALLOY_SWORD)));
        recipes.add(forgingRecipe("nickel_superalloy_spear",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_SPEAR)));
        recipes.add(forgingRecipe("nickel_superalloy_hammer",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,5),new ItemStack(ModItems.NICKEL_SUPERALLOY_HAMMER)));

        recipes.add(forgingRecipe("diamond_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,3),new ItemStack(Items.DIAMOND_PICKAXE)));
        recipes.add(forgingRecipe("diamond_axe",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,3),new ItemStack(Items.DIAMOND_AXE)));
        recipes.add(forgingRecipe("diamond_shovel",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,1),new ItemStack(Items.DIAMOND_SHOVEL)));
        recipes.add(forgingRecipe("diamond_hoe",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,2),new ItemStack(Items.DIAMOND_HOE)));
        recipes.add(forgingRecipe("diamond_sword",new ItemStack(Items.STICK,1),new ItemStack(Items.DIAMOND,2),new ItemStack(Items.DIAMOND_SWORD)));
        recipes.add(forgingRecipe("diamond_spear",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,3),new ItemStack(ModItems.DIAMOND_SPEAR)));
        recipes.add(forgingRecipe("diamond_hammer",new ItemStack(Items.STICK,2),new ItemStack(Items.DIAMOND,5),new ItemStack(ModItems.DIAMOND_HAMMER)));

        recipes.add(forgingRecipe("iron_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,3),new ItemStack(Items.IRON_PICKAXE)));
        recipes.add(forgingRecipe("iron_axe",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,3),new ItemStack(Items.IRON_AXE)));
        recipes.add(forgingRecipe("iron_shovel",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,1),new ItemStack(Items.IRON_SHOVEL)));
        recipes.add(forgingRecipe("iron_hoe",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,2),new ItemStack(Items.IRON_HOE)));
        recipes.add(forgingRecipe("iron_sword",new ItemStack(Items.STICK,1),new ItemStack(Items.IRON_INGOT,2),new ItemStack(Items.IRON_SWORD)));
        recipes.add(forgingRecipe("iron_spear",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,3),new ItemStack(ModItems.IRON_SPEAR)));
        recipes.add(forgingRecipe("iron_hammer",new ItemStack(Items.STICK,2),new ItemStack(Items.IRON_INGOT,5),new ItemStack(ModItems.IRON_HAMMER)));
        
        return recipes;
    }

    public static List<IFineryForgeRecipe> getFineryRecipes()
    {
        List<IFineryForgeRecipe> recipes = new ArrayList<>();
        recipes.add(fineryRecipe("finery_pig_to_wrought",ModItems.PIG_IRON_INGOT, new ItemStack(ModItems.BLOOM_IRON,1), new ItemStack(ModItems.WROUGHT_IRON_INGOT,1),new ItemStack(ModItems.SLAG,1),0.5f));
        return recipes;
    }

    public static AbstractMap.SimpleEntry<ItemStack,ItemStack> getCrushingOutputs(ItemStack input)
    {
        List<IPistonCrusherRecipe> recipes = ModRecipes.getCrushingRecipes();
        for (IPistonCrusherRecipe recipe: recipes)
        {
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() == input.getItem())
            {
                return new AbstractMap.SimpleEntry<>(recipe.getRecipeOutput(), recipe.getSecondaryOutput());
            }
        }
        return null;
    }

    public static ItemStack getForgingOutput(ItemStack input1, ItemStack input2, ItemStack template)
    {
        List<ICoalForgeRecipe> recipes = ModRecipes.getForgingRecipes();
        for (ICoalForgeRecipe recipe: recipes)
        {
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() == input1.getItem()  && recipe.getIngredients().get(1).getMatchingStacks()[0].getItem() == input2.getItem()
                    && recipe.getIngredients().get(0).getMatchingStacks()[0].getCount() <= input1.getCount()  && recipe.getIngredients().get(1).getMatchingStacks()[0].getCount() <= input2.getCount()
                    && recipe.getIngredients().get(2).getMatchingStacks()[0].getItem() == template.getItem())
            {
                return getForgingItemStack(input2,recipe.getRecipeOutput(),true).get(0);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getBeehiveOutput(ItemStack input1)
    {
        List<IBeehiveOvenRecipe> recipes = ModRecipes.getBeehiveOvenRecipes();
        for (IBeehiveOvenRecipe recipe: recipes)
        {
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() == input1.getItem())
            {
                return recipe.getOutputs().get(0);
            }
        }
        return ItemStack.EMPTY;
    }

    public static AbstractMap.SimpleEntry<ItemStack,Float> getCrushingSecondaryOutputs(ItemStack input)
    {
        List<IPistonCrusherRecipe> recipes = ModRecipes.getCrushingRecipes();
        for (IPistonCrusherRecipe recipe: recipes)
        {
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() == input.getItem())
            {
                return new AbstractMap.SimpleEntry<>(recipe.getSecondaryOutput(), recipe.getSecondaryChance());
            }
        }
        return null;
    }
    public static IAlloyRecipe alloyRecipe(String registry, ItemStack output, List<Item> input, AbstractMap.SimpleEntry<Float, Float> primary,
                                           AbstractMap.SimpleEntry<Float, Float> secondary, AbstractMap.SimpleEntry<Float, Float> other, float req)
    {
        NonNullList<Ingredient> list = NonNullList.create();
        for (int i = 0; i < input.size(); i++)
        {
            list.add(i,Ingredient.fromStacks(new ItemStack(input.get(i))));
        }
        return new IAlloyRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output, list
                ,primary,secondary,other,req);
    }

    public static IPistonCrusherRecipe crushingRecipe(String registry, Item input, ItemStack output, ItemStack secondary, float chance)
    {
        return new IPistonCrusherRecipe(new ResourceLocation(ProjectRankine.MODID,registry),new ItemStack[]{output,secondary},
                Ingredient.fromStacks(new ItemStack(input)),chance);
    }

    public static IBeehiveOvenRecipe beehiveOvenRecipe(String registry, Item input, ItemStack output)
    {
        return new IBeehiveOvenRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                Ingredient.fromStacks(new ItemStack(input)));
    }

    public static ICoalForgeRecipe forgingRecipe(String registry, ItemStack input, ItemStack alloy, ItemStack output)
    {
        ItemStack result = ItemStack.EMPTY;
        ItemStack template = new ItemStack(ModItems.PICKAXE_TEMPLATE);
        result = getForgingItemStack(alloy,output,false).get(0);
        template = getForgingItemStack(alloy,output,false).get(1);
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(Arrays.asList(Ingredient.fromStacks(input),Ingredient.fromStacks(alloy),Ingredient.fromStacks(template)));
        if (result != ItemStack.EMPTY)
        {
            return new ICoalForgeRecipe(new ResourceLocation(ProjectRankine.MODID,registry),result,
                    list);
        } else {
            return new ICoalForgeRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                    list);
        }

    }

    public static IFineryForgeRecipe fineryRecipe(String registry, Item input, ItemStack intermediate, ItemStack output, ItemStack secondary, float chance)
    {
        return new IFineryForgeRecipe(new ResourceLocation(ProjectRankine.MODID,registry),new ItemStack[]{intermediate,output,secondary},
                Ingredient.fromStacks(new ItemStack(input)),chance);
    }

    public static List<ItemStack> getForgingItemStack(ItemStack alloy, ItemStack output, boolean hasComposition) {
        ItemStack result = ItemStack.EMPTY;
        ItemStack template = new ItemStack(ModItems.PICKAXE_TEMPLATE);
        String comp;
        if (output.getItem() instanceof AlloyPickaxe)
        {
            AlloyPickaxe e = (AlloyPickaxe) output.getItem();
            template = new ItemStack(ModItems.PICKAXE_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloyPickaxe.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloyAxe)
        {
            AlloyAxe e = (AlloyAxe) output.getItem();
            template = new ItemStack(ModItems.AXE_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloyAxe.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloyShovel)
        {
            AlloyShovel e = (AlloyShovel) output.getItem();
            template = new ItemStack(ModItems.SHOVEL_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloyShovel.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloyHoe)
        {
            AlloyHoe e = (AlloyHoe) output.getItem();
            template = new ItemStack(ModItems.HOE_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloyHoe.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloySword)
        {
            AlloySword e = (AlloySword) output.getItem();
            template = new ItemStack(ModItems.SWORD_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloySword.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloySpear)
        {
            AlloySpear e = (AlloySpear) output.getItem();
            template = new ItemStack(ModItems.SPEAR_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloySpear.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        if (output.getItem() instanceof AlloyHammer)
        {
            AlloyHammer e = (AlloyHammer) output.getItem();
            template = new ItemStack(ModItems.HAMMER_TEMPLATE);
            PeriodicTableUtils utils = new PeriodicTableUtils();
            AlloyUtils alloyUtils = e.getAlloy();
            result = new ItemStack(e);
            if (hasComposition)
            {
                comp = alloy.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString();

            } else
            {
                comp = alloyUtils.getDefComposition();
            }
            AlloyHammer.addAlloy(result,new AlloyData(comp));
            for (Enchantment en: getEnchantments(comp, alloyUtils,result.getItem()))
            {
                result.addEnchantment(en,alloyUtils.getEnchantmentLevel(en,utils.calcEnchantability(getElements(comp), getPercents(comp)) + alloyUtils.getEnchantabilityBonus()));
            }
        }
        return Arrays.asList(result,template);
    }

    public static List<IPistonCrusherRecipe> groupCrushingRecipe(String registry, List<Item> input, ItemStack output, ItemStack secondary, float chance)
    {
        List<IPistonCrusherRecipe> crusherRecipes = new ArrayList<>();
        String registryName;
        int count = 0;
        for (Item i: input)
        {
            registryName = registry.concat(Integer.toString(count));
            crusherRecipes.add(new IPistonCrusherRecipe(new ResourceLocation(ProjectRankine.MODID,registryName),new ItemStack[]{output,secondary},
                    Ingredient.fromStacks(new ItemStack(i)),chance));
            count++;
        }
        return crusherRecipes;
    }

    public static List<IPistonCrusherRecipe> groupCrushingRecipe(String registry, String tag, ItemStack output, ItemStack secondary, float chance)
    {
        List<IPistonCrusherRecipe> crusherRecipes = new ArrayList<>();
        ResourceLocation tagGroup = new ResourceLocation(tag);
        String registryName;
        int count = 0;
        if (ItemTags.getCollection().get(tagGroup) != null)
        {
            List<Item> input = Objects.requireNonNull(ItemTags.getCollection().get(tagGroup)).getAllElements();
            if (!input.isEmpty())
            {
                for (Item i: input)
                {
                    registryName = registry.concat(Integer.toString(count));
                    crusherRecipes.add(new IPistonCrusherRecipe(new ResourceLocation(ProjectRankine.MODID,registryName),new ItemStack[]{output,secondary},
                            Ingredient.fromStacks(new ItemStack(i)),chance));
                    count++;
                }
            } else {
                LogManager.getLogger().warn("No items found from tag: {}", () -> {
                    return tag;
                });
            }
        } else {
            LogManager.getLogger().warn("Tag not found: {}", () -> {
                return tag;
            });
        }
        return crusherRecipes;
    }

    public static List<PeriodicTableUtils.Element> getElements(String c)
    {
        //String c = getComposition(stack).getCompound(0).get("comp").getString();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        String[] comp = c.split("-");
        List<PeriodicTableUtils.Element> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("[^A-Za-z]+", "");
            list.add(utils.getElementBySymbol(str));
        }
        return list;
    }

    public static List<Integer> getPercents(String c)
    {
        String[] comp = c.split("-");
        List<Integer> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("\\D+", "");
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public static List<Enchantment> getEnchantments(String c, AlloyUtils alloy, Item item)
    {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        List<Enchantment> enchantments = new ArrayList<>();
        List<Enchantment> elementEn = utils.getEnchantments(getElements(c),getPercents(c));
        for (Enchantment e: elementEn)
        {
            if (e != null)
            {
                enchantments.add(e);
            }
        }
        Enchantment en = alloy.getEnchantmentBonus(item);
        if (en != null)
        {
            enchantments.add(en);
        }
        return enchantments;
    }
}
