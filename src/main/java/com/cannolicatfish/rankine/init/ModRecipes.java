package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.IPistonCrusherRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProjectRankine.MODID);

    private static void registerType(ResourceLocation name, IRecipeType<?> recipeType) {
        Registry.register(Registry.RECIPE_TYPE, name, recipeType);
    }

    public static void init() {
        registerType(new ResourceLocation(ProjectRankine.MODID,"crushing"), IPistonCrusherRecipe.RECIPE_TYPE);
    }
    public static final RegistryObject<IPistonCrusherRecipe.Serializer> PISTON_CRUSHER_SERIALIZER = REGISTRY.register("crushing", IPistonCrusherRecipe.Serializer::new);

    public static List<IPistonCrusherRecipe> getCrushingRecipes()
    {
        List<IPistonCrusherRecipe> recipes = new ArrayList<>();
        recipes.add(crushingRecipe("ironstone_crushing",ModBlocks.IRONSTONE.asItem(),new ItemStack(ModItems.IRON_OXIDE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(),new ItemStack(ModItems.CHROMIUM_OXIDE),new ItemStack(ModItems.IRON_OXIDE), 0.1f));
        recipes.add(crushingRecipe("andesite_crushing",ModBlocks.ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("gray_andesite_crushing",Blocks.ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("diorite_crushing",ModBlocks.DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("quartz_diorite_crushing",Blocks.DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("red_granite_crushing",Blocks.GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("granite_crushing",ModBlocks.GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gneiss_crushing",ModBlocks.GNEISS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.2f));
        recipes.add(crushingRecipe("limestone_crushing",ModBlocks.LIMESTONE.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("cobblestone_crushing",Blocks.COBBLESTONE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.01f));
        recipes.add(crushingRecipe("stone_crushing",Blocks.STONE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("sandstone_crushing",Blocks.SANDSTONE.asItem(), new ItemStack(Items.SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("red_sandstone_crushing",Blocks.RED_SANDSTONE.asItem(), new ItemStack(Items.RED_SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("basalt_crushing",ModBlocks.BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("marble_crushing",ModBlocks.MARBLE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("shale_crushing",ModBlocks.SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("rhyolite_crushing",ModBlocks.RHYOLITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("peridotite_crushing",ModBlocks.PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("komatiite_crushing",ModBlocks.KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("ringwoodite_crushing",ModBlocks.RINGWOODITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("wadsleyite_crushing",ModBlocks.WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("bridgmanite_crushing",ModBlocks.BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("kimberlite_crushing",ModBlocks.KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("ferropericlase_crushing",ModBlocks.FERROPERICLASE.asItem(), new ItemStack(ModItems.IRON_OXIDE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("perovskite_crushing",ModBlocks.PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("smooth_stone_crushing",Blocks.SMOOTH_STONE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_granite_crushing",ModBlocks.SMOOTH_GRANITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_andesite_crushing",ModBlocks.SMOOTH_ANDESITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("smooth_diorite_crushing",ModBlocks.SMOOTH_DIORITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_limestone_crushing",ModBlocks.SMOOTH_LIMESTONE.asItem(), new ItemStack(ModItems.CALCITE,2), new ItemStack(ModItems.DOLOMITE,1),0.1f));
        recipes.add(crushingRecipe("smooth_basalt_crushing",ModBlocks.SMOOTH_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("smooth_gneiss_crushing",ModBlocks.SMOOTH_GNEISS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.CALCITE,1),0.1f));
        recipes.add(crushingRecipe("smooth_marble_crushing",ModBlocks.SMOOTH_MARBLE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_shale_crushing",ModBlocks.SMOOTH_SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("smooth_rhyolite_crushing",ModBlocks.SMOOTH_RHYOLITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_peridotite_crushing",ModBlocks.SMOOTH_PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("smooth_komatiite_crushing",ModBlocks.SMOOTH_KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("smooth_ringwoodite_crushing",ModBlocks.SMOOTH_RINGWOODITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("smooth_wadsleyite_crushing",ModBlocks.SMOOTH_WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("smooth_bridgmanite_crushing",ModBlocks.SMOOTH_BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("smooth_kimberlite_crushing",ModBlocks.SMOOTH_KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("smooth_ferropericlase_crushing",ModBlocks.SMOOTH_FERROPERICLASE.asItem(), new ItemStack(ModItems.IRON_OXIDE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("smooth_perovskite_crushing",ModBlocks.SMOOTH_PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("meteorite_crushing",ModBlocks.METEORITE.asItem(), new ItemStack(ModItems.IRON_OXIDE,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("magnetite_ore_crushing",ModBlocks.MAGNETITE_ORE.asItem(), new ItemStack(ModItems.IRON_OXIDE,1), new ItemStack(ModItems.CHROMIUM_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("malachite_ore_crushing",ModBlocks.MALACHITE_ORE.asItem(), new ItemStack(ModItems.COPPER_HYDROXIDE,1), new ItemStack(ModItems.AZURITE,1),0.1f));
        recipes.add(crushingRecipe("bauxite_ore_crushing",ModBlocks.BAUXITE_ORE.asItem(), new ItemStack(ModItems.ALUMINA,1), new ItemStack(ModItems.TITANIA,1),0.05f));
        recipes.add(crushingRecipe("cassiterite_ore_crushing",ModBlocks.CASSITERITE_ORE.asItem(), new ItemStack(ModItems.TIN_OXIDE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("sphalerite_ore_crushing",ModBlocks.SPHALERITE_ORE.asItem(), new ItemStack(ModItems.ZINC_SULFIDE,1), new ItemStack(ModItems.OPAL,1),0.05f));
        recipes.add(crushingRecipe("cinnabarite_ore_crushing",ModBlocks.CINNABAR_ORE.asItem(), new ItemStack(Items.REDSTONE,4), new ItemStack(ModItems.SULFUR,1),0.25f));
        recipes.add(crushingRecipe("pentlandite_ore_crushing",ModBlocks.PENTLANDITE_ORE.asItem(), new ItemStack(ModItems.NICKEL_SULFIDE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("magnesite_ore_crushing",ModBlocks.MAGNESITE_ORE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("galena_ore_crushing",ModBlocks.GALENA_ORE.asItem(), new ItemStack(ModItems.LEAD_SULFIDE,1), new ItemStack(ModItems.SILVER_SULFIDE,1),0.1f));
        recipes.add(crushingRecipe("acanthite_ore_crushing",ModBlocks.ACANTHITE_ORE.asItem(), new ItemStack(ModItems.SILVER_SULFIDE,1), new ItemStack(ModItems.LEAD_SULFIDE,1),0.1f));
        recipes.add(crushingRecipe("pyrolusite_ore_crushing",ModBlocks.PYROLUSITE_ORE.asItem(), new ItemStack(ModItems.MANGANESE_OXIDE,1), new ItemStack(ModItems.TANTALUM_OXIDE,1),0.05f));
        recipes.add(crushingRecipe("bismite_ore_crushing",ModBlocks.BISMITE_ORE.asItem(), new ItemStack(ModItems.BISMUTH_OXIDE,1), new ItemStack(ModItems.LEAD_SULFIDE,1),0.1f));
        recipes.add(crushingRecipe("vanadinite_ore_crushing",ModBlocks.VANADINITE_ORE.asItem(), new ItemStack(ModItems.VANADINITE,1), new ItemStack(ModItems.LEAD_SULFIDE,1),0.1f));
        recipes.add(crushingRecipe("ilmenite_ore_crushing",ModBlocks.ILMENITE_ORE.asItem(), new ItemStack(ModItems.TITANIA,1), new ItemStack(ModItems.COPPER_HYDROXIDE,1),0.1f));
        recipes.add(crushingRecipe("molybdenite_ore_crushing",ModBlocks.MOLYBDENITE_ORE.asItem(), new ItemStack(ModItems.MOLYBDENUM_OXIDE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(), new ItemStack(ModItems.CHROMIUM_OXIDE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("columbite_ore_crushing",ModBlocks.COLUMBITE_ORE.asItem(), new ItemStack(ModItems.NIOBIUM_OXIDE,1), new ItemStack(ModItems.IRON_OXIDE,1),0.1f));
        recipes.add(crushingRecipe("tantalite_ore_crushing",ModBlocks.TANTALITE_ORE.asItem(), new ItemStack(ModItems.TANTALUM_OXIDE,1), new ItemStack(ModItems.LEAD_SULFIDE,1),0.1f));
        recipes.add(crushingRecipe("wolframite_ore_crushing",ModBlocks.WOLFRAMITE_ORE.asItem(), new ItemStack(ModItems.TUNGSTEN_OXIDE,1), new ItemStack(ModItems.TIN_OXIDE,1),0.2f));
        recipes.add(crushingRecipe("lignite_ore_crushing",ModBlocks.LIGNITE_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,1), new ItemStack(ModItems.GRAPHITE,1),0.01f));
        recipes.add(crushingRecipe("sub_bituminous_coal_ore_crushing",ModBlocks.SUBBITUMINOUS_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,2), new ItemStack(ModItems.GRAPHITE,1),0.02f));
        recipes.add(crushingRecipe("bituminous_coal_ore_crushing",ModBlocks.BITUMINOUS_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,2), new ItemStack(ModItems.GRAPHITE,1),0.05f));
        recipes.add(crushingRecipe("anthracite_ore_crushing",ModBlocks.ANTHRACITE_ORE.asItem(), new ItemStack(ModItems.CRUSHED_COAL,3), new ItemStack(ModItems.GRAPHITE,1),0.1f));
        recipes.add(crushingRecipe("sperrylite_ore_crushing",ModBlocks.SPERRYLITE_ORE.asItem(), new ItemStack(ModItems.PLATINUM_ARSENIDE,1), new ItemStack(ModItems.OSMIRIDIUM_ALLOY,1),1f));
        // Example of using tags for recipe (don""t use unless necessary, i.e. large list of blocks)
        // recipes.addAll(groupCrushingRecipe("andesite_crushing","rankine:andesite",new ItemStack(ModItems.FELDSPAR),new ItemStack(ModItems.PYROXENE), 0.2f));
        return recipes;
    }

    public static IPistonCrusherRecipe crushingRecipe(String registry, Item input, ItemStack output, ItemStack secondary, float chance)
    {
        return new IPistonCrusherRecipe(new ResourceLocation(ProjectRankine.MODID,registry),new ItemStack[]{output,secondary},
                Ingredient.fromStacks(new ItemStack(input)),chance);
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
}
