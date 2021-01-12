package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.pendants.*;
import com.cannolicatfish.rankine.items.tools.ItemGoldPan;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.util.stream.Collectors;

public class ModRecipes {

    public static List<IAlloyRecipe> getAlloyRecipes()
    {
        List<IAlloyRecipe> recipes = new ArrayList<>();
        recipes.add(alloyRecipe("bronze_alloy",new ItemStack(ModItems.BRONZE_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("tin"),
                returnTagFamily("aluminum"),returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),
                returnTagFamily("iron"),returnTagFamily("bismuth"),returnTagFamily("lead"),returnTagFamily("antimony"),returnTagFamily("silicon"),
                returnTagFamily("phosphorus")),new AbstractMap.SimpleEntry<>(.8f,.9f),new AbstractMap.SimpleEntry<>(.1f,.2f),
                new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("pewter_alloy",new ItemStack(ModItems.PEWTER_ALLOY),Arrays.asList(returnTagFamily("tin"),returnTagFamily("antimony","lead"),
                returnTagFamily("copper"),returnTagFamily("bismuth"),returnTagFamily("silver")),
                new AbstractMap.SimpleEntry<>(.85f,.98f), new AbstractMap.SimpleEntry<>(.02f,.1f),new AbstractMap.SimpleEntry<>(0f,.13f),.87f));

        recipes.add(alloyRecipe("aluminum_bronze_alloy",new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("aluminum"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),returnTagFamily("iron"),returnTagFamily("lead")),
                new AbstractMap.SimpleEntry<>(.74f,.93f), new AbstractMap.SimpleEntry<>(.04f,.12f),new AbstractMap.SimpleEntry<>(0f,.15f),.85f));

        recipes.add(alloyRecipe("brass_alloy",new ItemStack(ModItems.BRASS_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("zinc"),
                returnTagFamily("tin"),returnTagFamily("lead"),returnTagFamily("aluminum"),returnTagFamily("nickel"),returnTagFamily("iron"),
                returnTagFamily("selenium")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.15f,.6f),new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("cupronickel_alloy",new ItemStack(ModItems.CUPRONICKEL_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("iron"),returnTagFamily("manganese"),returnTagFamily("tin"),returnTagFamily("niobium"),returnTagFamily("tantalum"),returnTagFamily("lead"), returnTagFamily("titanium"),
                returnTagFamily("chromium"),returnTagFamily("aluminum"), returnTagFamily("beryllium"),returnTagFamily("silicon"),returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.7f,.9f), new AbstractMap.SimpleEntry<>(.1f,.3f),new AbstractMap.SimpleEntry<>(0f,.05f),.95f));

        recipes.add(alloyRecipe("sterling_silver_alloy",new ItemStack(ModItems.STERLING_SILVER_ALLOY),Arrays.asList(returnTagFamily("silver"),returnTagFamily("copper"),
                returnTagFamily("zinc"),returnTagFamily("platinum"),returnTagFamily("boron"),returnTagFamily("germanium"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.92f,.96f), new AbstractMap.SimpleEntry<>(.01f,.08f),new AbstractMap.SimpleEntry<>(0f,.07f),0.93f));

        recipes.add(alloyRecipe("nickel_silver_alloy",new ItemStack(ModItems.NICKEL_SILVER_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.5f,.7f), new AbstractMap.SimpleEntry<>(.15f,.25f),new AbstractMap.SimpleEntry<>(.15f,.25f),1f));

        recipes.add(alloyRecipe("invar_alloy",new ItemStack(ModItems.INVAR_ALLOY),Arrays.asList(returnTagFamily("iron"),returnTagFamily("nickel"),
                returnTagFamily("cobalt")), new AbstractMap.SimpleEntry<>(.5f,.9f), new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(0f,.05f),0.95f));

        recipes.add(alloyRecipe("cast_iron_alloy",new ItemStack(ModItems.CAST_IRON_ALLOY),Arrays.asList(returnTagFamily("iron"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("chromium"),returnTagFamily("molybdenum"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("silicon"),returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.86f,.98f), new AbstractMap.SimpleEntry<>(.02f,.04f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("rose_gold_alloy",new ItemStack(ModItems.ROSE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("copper"),
                returnTagFamily("silver"), returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.06f),.94f));

        recipes.add(alloyRecipe("white_gold_alloy",new ItemStack(ModItems.WHITE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("zinc"),
                returnTagFamily("nickel"),returnTagFamily("palladium"),returnTagFamily("silver"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.74f,.9f), new AbstractMap.SimpleEntry<>(.05f,.1f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("green_gold_alloy",new ItemStack(ModItems.GREEN_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("silver"),
                returnTagFamily("copper"),returnTagFamily("cadmium"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.3f,.7f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy",new ItemStack(ModItems.BLUE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("iron"),
                returnTagFamily("nickel"),returnTagFamily("rhodium"),returnTagFamily("ruthenium")),
                new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy_alt",new ItemStack(ModItems.BLUE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("gallium","indium")),
                new AbstractMap.SimpleEntry<>(.46f,.60f), new AbstractMap.SimpleEntry<>(.40f,.54f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("purple_gold_alloy",new ItemStack(ModItems.PURPLE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("aluminum")),
                new AbstractMap.SimpleEntry<>(.79f,.81f), new AbstractMap.SimpleEntry<>(.19f,.21f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("black_gold_alloy",new ItemStack(ModItems.BLACK_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("cobalt"),
                returnTagFamily("chromium")), new AbstractMap.SimpleEntry<>(.75f,.80f), new AbstractMap.SimpleEntry<>(.15f,.20f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        return recipes;
    }

    public static List<ITripleAlloyRecipe> getTripleAlloyRecipes() {
        List<ITripleAlloyRecipe> recipes = new ArrayList<>();

        recipes.add(tripleAlloyRecipe("steel_alloy", new ItemStack(ModItems.STEEL_ALLOY), Arrays.asList(returnTagFamily("iron"),
                returnTagFamily("manganese"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("chromium"),returnTagFamily("cobalt"),
                returnTagFamily("molybdenum"),returnTagFamily("nickel"),
                returnTagFamily("niobium"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"),
                returnTagFamily("copper"),returnTagFamily("calcium"),
                returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.97f,.98f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(0f, .01f),.99f));

        recipes.add(tripleAlloyRecipe("stainless_steel_alloy", new ItemStack(ModItems.STAINLESS_STEEL_ALLOY), Arrays.asList(returnTagFamily("iron"),
                returnTagFamily("chromium"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("molybdenum"),returnTagFamily("aluminum"),
                returnTagFamily("manganese"),returnTagFamily("copper"),
                returnTagFamily("nickel"),returnTagFamily("boron"),
                returnTagFamily("cobalt"),returnTagFamily("strontium"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("titanium"),returnTagFamily("tungsten"),
                returnTagFamily("yttrium"),returnTagFamily("selenium"),
                returnTagFamily("vanadium"),returnTagFamily("calcium"),
                returnTagFamily("zirconium"), returnTagFamily("silicon"), returnTagFamily("netherite"),
                returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.65f, .75f), new AbstractMap.SimpleEntry<>(.10f, .21f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(0.01f, .12f),.85f));

        recipes.add(tripleAlloyRecipe("tungsten_heavy_alloy", new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY), Arrays.asList(returnTagFamily("tungsten"),
                returnTagFamily("nickel"),returnTagFamily("iron"),returnTagFamily("copper"),
                returnTagFamily("cobalt"),returnTagFamily("chromium"),
                returnTagFamily("molybdenum"),returnTagFamily("aluminum"),
                returnTagFamily("boron"),returnTagFamily("titanium"),
                returnTagFamily("tantalum"),returnTagFamily("yttrium"),
                returnTagFamily("cerium"),returnTagFamily("lanthanum"),
                returnTagFamily("rhenium"), returnTagFamily("carbon","coke","graphite"), returnTagFamily("netherite"),
                returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.88f, .95f), new AbstractMap.SimpleEntry<>(.02f, .1f), new AbstractMap.SimpleEntry<>(.01f, .04f), new AbstractMap.SimpleEntry<>(0f, .09f),.91f));

        recipes.add(tripleAlloyRecipe("mischmetal_alloy", new ItemStack(ModItems.MISCHMETAL_ALLOY), Arrays.asList(returnTagFamily("cerium"),
                returnTagFamily("lanthanum"),returnTagFamily("neodymium"),
                returnTagFamily("praseodymium"),returnTagFamily("dysprosium"),
                returnTagFamily("europium"),returnTagFamily("gadolinium"),
                returnTagFamily("samarium"),returnTagFamily("terbium"),
                returnTagFamily("iron"), returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.5f, .55f), new AbstractMap.SimpleEntry<>(.2f, .25f), new AbstractMap.SimpleEntry<>(.15f, .2f), new AbstractMap.SimpleEntry<>(0f, .15f),.85f));

        recipes.add(tripleAlloyRecipe("ferrocerium_alloy", new ItemStack(ModItems.FERROCERIUM_ALLOY), Arrays.asList(returnTagFamily("cerium"),
                returnTagFamily("lanthanum"),returnTagFamily("iron"),
                returnTagFamily("praseodymium"),returnTagFamily("neodymium"),
                returnTagFamily("magnesium")),
                new AbstractMap.SimpleEntry<>(.4f, .5f), new AbstractMap.SimpleEntry<>(.23f, .25f), new AbstractMap.SimpleEntry<>(.19f, .21f), new AbstractMap.SimpleEntry<>(0f, .04f),.92f));

        recipes.add(tripleAlloyRecipe("duralumin_alloy", new ItemStack(ModItems.DURALUMIN_ALLOY), Arrays.asList(returnTagFamily("aluminum"),
                returnTagFamily("copper"),returnTagFamily("magnesium"),
                returnTagFamily("manganese"),returnTagFamily("iron"),
                returnTagFamily("zinc"),returnTagFamily("titanium"),
                returnTagFamily("calcium"),returnTagFamily("chromium"),
                 returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.91f, .95f), new AbstractMap.SimpleEntry<>(.03f, .05f), new AbstractMap.SimpleEntry<>(.01f, .03f), new AbstractMap.SimpleEntry<>(0f, .04f),.96f));

        recipes.add(tripleAlloyRecipe("magnesium_alloy", new ItemStack(ModItems.MAGNESIUM_ALLOY), Arrays.asList(returnTagFamily("magnesium"),
                returnTagFamily("aluminum"),returnTagFamily("zinc"),
                returnTagFamily("beryllium"),returnTagFamily("copper"),
                returnTagFamily("iron"),returnTagFamily("nickel"),
                returnTagFamily("chromium"),returnTagFamily("lead"),
                returnTagFamily("tin"),returnTagFamily("yttrium"),
                returnTagFamily("zirconium"),returnTagFamily("calcium"),
                returnTagFamily("silver"),returnTagFamily("cadmium"),
                returnTagFamily("thorium"),returnTagFamily("lithium"),
                returnTagFamily("antimony"),returnTagFamily("bismuth"),
                returnTagFamily("strontium"),returnTagFamily("neodymium"),
                returnTagFamily("gadolinium"),returnTagFamily("manganese"),
                returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.85f, .96f), new AbstractMap.SimpleEntry<>(.03f, .13f), new AbstractMap.SimpleEntry<>(.01f, .03f), new AbstractMap.SimpleEntry<>(0f, .06f),.94f));

        recipes.add(tripleAlloyRecipe("rose_metal_alloy", new ItemStack(ModItems.ROSE_METAL_ALLOY), Arrays.asList(returnTagFamily("bismuth"),
                returnTagFamily("lead"),returnTagFamily("tin")),
                new AbstractMap.SimpleEntry<>(.3f, .5f), new AbstractMap.SimpleEntry<>(.18f, .4f), new AbstractMap.SimpleEntry<>(.1f, .25f), new AbstractMap.SimpleEntry<>(0f, 0f),1f));

        recipes.add(tripleAlloyRecipe("alnico_alloy", new ItemStack(ModItems.ALNICO_ALLOY), Arrays.asList(returnTagFamily("iron"),
                returnTagFamily("nickel"),returnTagFamily("cobalt"),returnTagFamily("aluminum"),returnTagFamily("copper","titanium","hafnium","niobium")),
                new AbstractMap.SimpleEntry<>(.3f, .5f), new AbstractMap.SimpleEntry<>(.13f, .26f), new AbstractMap.SimpleEntry<>(.05f, .35f), new AbstractMap.SimpleEntry<>(.08f, .12f),1f));

        recipes.add(tripleAlloyRecipe("galinstan_alloy", new ItemStack(ModItems.GALINSTAN_ALLOY), Arrays.asList(returnTagFamily("gallium"),
                returnTagFamily("indium"),returnTagFamily("tin"), returnTagFamily("antimony"), returnTagFamily("bismuth")),
                new AbstractMap.SimpleEntry<>(.68f, .8f), new AbstractMap.SimpleEntry<>(.12f, .22f), new AbstractMap.SimpleEntry<>(.08f, .1f), new AbstractMap.SimpleEntry<>(0f, .1f),.9f));

            recipes.add(tripleAlloyRecipe("nickel_superalloy_tial", new ItemStack(ModItems.NICKEL_SUPERALLOY), Arrays.asList(returnTagFamily("nickel"),
                returnTagFamily("titanium"),returnTagFamily("aluminum"),
                returnTagFamily("chromium"),returnTagFamily("molybdenum"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("cobalt"),returnTagFamily("manganese"),
                returnTagFamily("boron"),returnTagFamily("iron"), returnTagFamily("netherite"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"), returnTagFamily("carbon","coke","graphite"),returnTagFamily("tungsten"),
                returnTagFamily("ruthenium"),returnTagFamily("rhenium"), returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.6f, .8f), new AbstractMap.SimpleEntry<>(.01f, .1f), new AbstractMap.SimpleEntry<>(.01f, .1f), new AbstractMap.SimpleEntry<>(0f, .15f),.62f));

        recipes.add(tripleAlloyRecipe("nickel_superalloy_crfe", new ItemStack(ModItems.NICKEL_SUPERALLOY), Arrays.asList(returnTagFamily("nickel"),
                returnTagFamily("chromium"),returnTagFamily("cobalt"),
                returnTagFamily("titanium"),returnTagFamily("molybdenum"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("iron"),returnTagFamily("manganese"),
                returnTagFamily("boron"),returnTagFamily("aluminum"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"),
                returnTagFamily("carbon","coke","graphite"),returnTagFamily("tungsten"),
                returnTagFamily("ruthenium"),returnTagFamily("rhenium"), returnTagFamily("netherite"),
                returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.5f, .75f), new AbstractMap.SimpleEntry<>(.14f, .27f), new AbstractMap.SimpleEntry<>(.01f, .2f), new AbstractMap.SimpleEntry<>(0f, .15f),.65f));

        recipes.add(tripleAlloyRecipe("cobalt_superalloy", new ItemStack(ModItems.COBALT_SUPERALLOY), Arrays.asList(returnTagFamily("cobalt"),
                returnTagFamily("chromium"),returnTagFamily("nickel"),
                returnTagFamily("tantalum"),returnTagFamily("molybdenum"),
                returnTagFamily("tungsten"),returnTagFamily("titanium"),
                returnTagFamily("aluminum"),returnTagFamily("iridium"),
                returnTagFamily("iron"),returnTagFamily("aluminum"),
                returnTagFamily("carbon","coke","graphite"), returnTagFamily("netherite"),
                returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.6f, .8f), new AbstractMap.SimpleEntry<>(.18f, .3f), new AbstractMap.SimpleEntry<>(.02f, .12f), new AbstractMap.SimpleEntry<>(0f, .2f),.8f));

        recipes.add(tripleAlloyRecipe("amalgam_alloy",new ItemStack(ModItems.AMALGAM_ALLOY),Arrays.asList(returnTagFamily("mercury"),returnTagFamily("gold"),
                returnAmalgamStacks()), new AbstractMap.SimpleEntry<>(.25f,.8f), new AbstractMap.SimpleEntry<>(.25f,.5f),new AbstractMap.SimpleEntry<>(0f,0.5f),  new AbstractMap.SimpleEntry<>(0f, 0f),1f));

        return recipes;
    }

    public static ItemStack[] returnTag(ResourceLocation... rs) {
        List<Item> list = new ArrayList<>();
        for (ResourceLocation i : rs)
        {
            //System.out.println("Attempting to access " + i);
            ITag<Item> d = ItemTags.getCollection().get(i);
            if (d != null)
            {
                list.addAll(d.getAllElements());
            } /* else
            {
                System.out.println("TAG at ResourceLocation " + i + " does not exist.");
            }*/

        }

        if (list.isEmpty())
        {
            //System.out.println("Tag not found for JEI Recipe with ResourceLocation(s): " + Arrays.toString(rs));
            //System.out.println("Attempting fix...");
            for (ResourceLocation i : rs)
            {
                if (i != null)
                {
                    String namespace;
                    String path = i.getPath();
                    Item end;
                    if (path.contains("iron") && (!path.contains("pig") && !path.contains("wrought")) || path.contains("gold") || path.contains("netherite")){
                        namespace = "minecraft";
                    } else {
                        namespace = "rankine";
                    }
                    String s;
                    if (path.contains("ingot"))
                    {
                        s = path.split("/")[1] + "_ingot";
                    } else if (path.contains("nugget"))
                    {
                        s = path.split("/")[1] + "_nugget";
                    } else if (path.contains("block")) {
                        s = path.split("/")[1] + "_block";
                    } else {
                        s = path;
                    }
                    end = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,s));
                    if (end != null)
                    {
                        list.add(end);
                    }
                }

            }
        }

        ItemStack[] result = new ItemStack[list.size()];

        if (!list.isEmpty())
        {
            for (int i = 0; i < list.size(); i++)
            {
                result[i] = new ItemStack(list.get(i));
            }

        } else {
            System.out.println("JEI Alloy Ingredient error handler did not work! Inserting element error item...");
            result = new ItemStack[]{new ItemStack(ModItems.ELEMENT)};
        }
        return result;
    }

    public static ItemStack[] returnAmalgamStacks() {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        String[] list = utils.getAmalgamNames().toArray(new String[0]);
        return returnTagFamily(list);
    }

    public static ItemStack[] returnTagFamily(String... s) {
        List<Item> list = new ArrayList<>();
        ResourceLocation[] r = new ResourceLocation[s.length * 3];
        int count = 0;
        for (String str: s)
        {
            if (str.equals("phosphorus") || str.equals("sulfur") || str.equals("silicon") || str.equals("astatine"))
            {
                r[count] = new ResourceLocation("forge",str);
                r[count + 1] = new ResourceLocation("forge","nuggets/"+str);
                r[count + 2] = new ResourceLocation("forge", "storage_blocks/"+str);
                count += 3;
            } else if (str.equals("graphite") || str.equals("coke"))
            {
                r[count] = new ResourceLocation("forge",str);
                r[count + 1] = new ResourceLocation("forge", "storage_blocks/"+str);
                count += 2;
            } else {
                r[count] = new ResourceLocation("forge","ingots/"+str);
                r[count + 1] = new ResourceLocation("forge","nuggets/"+str);
                r[count + 2] = new ResourceLocation("forge", "storage_blocks/"+str);
                count += 3;
            }
        }
        return returnTag(r);
    }
    public static List<IBeehiveOvenRecipe> getBeehiveOvenRecipes()
    {
        List<IBeehiveOvenRecipe> recipes = new ArrayList<>();
        recipes.add(beehiveOvenRecipe("limestone_oven_cooking", ModBlocks.LIMESTONE.asItem(),new ItemStack(ModBlocks.QUICKLIME_BLOCK)));
        recipes.add(beehiveOvenRecipe("magnesite_oven_cooking", ModBlocks.MAGNESITE_BLOCK.asItem(),new ItemStack(ModBlocks.MAGNESIA_BLOCK)));
        recipes.add(beehiveOvenRecipe("subbituminous_oven_cooking", ModBlocks.SUBBITUMINOUS_COAL_BLOCK.asItem(),new ItemStack(ModBlocks.BITUMINOUS_COAL_BLOCK)));
        recipes.add(beehiveOvenRecipe("bituminous_oven_cooking", ModBlocks.BITUMINOUS_COAL_BLOCK.asItem(),new ItemStack(ModBlocks.COKE_BLOCK)));
        recipes.add(beehiveOvenRecipe("bloom_pig_oven_cooking", ModBlocks.PIG_IRON_BLOCK.asItem(),new ItemStack(ModBlocks.BLOOM_IRON_BLOCK)));
        recipes.add(beehiveOvenRecipe("coal_oven_cooking", Blocks.COAL_BLOCK.asItem(),new ItemStack(ModBlocks.BITUMINOUS_COAL_BLOCK)));
        recipes.add(beehiveOvenRecipe("bone_char_cooking", Blocks.BONE_BLOCK.asItem(),new ItemStack(ModBlocks.BONE_CHAR_BLOCK)));

        return recipes;
    }

    public static List<ISluicingRecipe> getSluicingRecipes()
    {
        List<ISluicingRecipe> recipes = new ArrayList<>();
        recipes.add(sluicingRecipe("alluvium_sluicing", ModBlocks.ALLUVIUM.asItem(), ItemGoldPan.returnAlluviumCollection()));
        recipes.add(sluicingRecipe("black_sand_sluicing", ModBlocks.BLACK_SAND.asItem(), ItemGoldPan.returnBlackSandCollection()));
        return recipes;
    }

    public static List<IEvaporationRecipe> getEvaporationRecipes()
    {
        List<IEvaporationRecipe> recipes = new ArrayList<>();
        recipes.add(evaporationRecipe("groundwater_evaporation", ModItems.BIOME_INDICATOR_GENERIC, EvaporationTowerTile.returnGroundwaterCollection()));
        recipes.add(evaporationRecipe("ocean_evaporation", ModItems.BIOME_INDICATOR_OCEAN, EvaporationTowerTile.returnOceanCollection()));
        recipes.add(evaporationRecipe("river_evaporation", ModItems.BIOME_INDICATOR_RIVER, EvaporationTowerTile.returnRiverCollection()));
        return recipes;
    }

    public static List<IPistonCrusherRecipe> getCrushingRecipes()
    {
        List<IPistonCrusherRecipe> recipes = new ArrayList<>();

        //Stones
        recipes.add(crushingRecipe("cobblestone_crushing",Blocks.COBBLESTONE.asItem(), new ItemStack(Items.GRAVEL,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("stone_crushing",Blocks.STONE.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.FELDSPAR,1),0.1f));
        recipes.add(crushingRecipe("granite_crushing",Blocks.GRANITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("diorite_crushing",Blocks.DIORITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("andesite_crushing",Blocks.ANDESITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.ZIRCON), 0.1f));
        recipes.add(crushingRecipe("basalt_crushing",Blocks.BASALT.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.PYROXENE,1),0.1f));
        recipes.add(crushingRecipe("sandstone_crushing",Blocks.SANDSTONE.asItem(), new ItemStack(Items.SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("red_sandstone_crushing",Blocks.RED_SANDSTONE.asItem(), new ItemStack(Items.RED_SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("blackstone_crushing",Blocks.BLACKSTONE.asItem(), new ItemStack(ModItems.PYROXENE,1), new ItemStack(Items.GOLD_NUGGET,1),0.08f));
        recipes.add(crushingRecipe("netherrack_crushing", Blocks.NETHERRACK.asItem(),new ItemStack(ModItems.PYROXENE, 1), new ItemStack(Items.GOLD_NUGGET), 0.04f));
        recipes.add(crushingRecipe("end_stone_crushing", Blocks.END_STONE.asItem(),new ItemStack(Items.COBBLESTONE, 3), new ItemStack(Items.ENDER_PEARL), 0.05f));
        recipes.add(crushingRecipe("obsidian_crushing",Blocks.OBSIDIAN.asItem(),new ItemStack(ModItems.PERLITE, 1), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("red_granite_crushing",ModBlocks.RED_GRANITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("granodiorite_crushing",ModBlocks.GRANODIORITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("hornblende_andesite_crushing",ModBlocks.HORNBLENDE_ANDESITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.ZIRCON), 0.1f));
        recipes.add(crushingRecipe("limestone_crushing",ModBlocks.LIMESTONE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.CALCITE), 0.1f));
        recipes.add(crushingRecipe("shale_crushing",ModBlocks.SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_crushing",ModBlocks.ANORTHOSITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("ironstone_crushing",ModBlocks.IRONSTONE.asItem(),new ItemStack(Items.IRON_NUGGET, 2),new ItemStack(ModItems.TIGER_IRON), 0.05f));
        recipes.add(crushingRecipe("tholeiitic_basalt_crushing",ModBlocks.THOLEIITIC_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.OLIVINE,1),0.05f));
        recipes.add(crushingRecipe("rhyolite_crushing",ModBlocks.RHYOLITE.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.FELDSPAR),0.1f));
        recipes.add(crushingRecipe("marble_crushing",ModBlocks.MARBLE.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(Items.QUARTZ),0.1f));
        recipes.add(crushingRecipe("gneiss_crushing",ModBlocks.GNEISS.asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("pumice_crushing",ModBlocks.PUMICE.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("scoria_crushing",ModBlocks.SCORIA.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("slate_crushing",ModBlocks.SLATE.asItem(),new ItemStack(Items.CLAY_BALL, 2), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gabbro_crushing",ModBlocks.GABBRO.asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("schist_crushing",ModBlocks.SCHIST.asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("breccia_crushing",ModBlocks.BRECCIA.asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("peridotite_crushing",ModBlocks.PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("komatiite_crushing",ModBlocks.KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("ringwoodite_crushing",ModBlocks.RINGWOODITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("wadsleyite_crushing",ModBlocks.WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("bridgmanite_crushing",ModBlocks.BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("kimberlite_crushing",ModBlocks.KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("ferropericlase_crushing",ModBlocks.FERROPERICLASE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("perovskite_crushing",ModBlocks.PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("andesitic_tuff_crushing",ModBlocks.ANDESITIC_TUFF.asItem(),new ItemStack(ModBlocks.HORNBLENDE_ANDESITE, 1), new ItemStack(ModItems.CALCITE), 0.15f));
        recipes.add(crushingRecipe("tholeiitic_basaltic_tuff_crushing",ModBlocks.THOLEIITIC_BASALTIC_TUFF.asItem(),new ItemStack(ModBlocks.THOLEIITIC_BASALT, 1), new ItemStack(Items.NAUTILUS_SHELL), 0.01f));
        recipes.add(crushingRecipe("rhyolitic_tuff_crushing",ModBlocks.RHYOLITIC_TUFF.asItem(),new ItemStack(ModBlocks.RHYOLITE, 1), new ItemStack(ModItems.OPAL), 0.01f));
        recipes.add(crushingRecipe("phosphorite_crushing",ModBlocks.PHOSPHORITE.asItem(),new ItemStack(ModItems.PHOSPHORUS, 1), new ItemStack(ModItems.PHOSPHORUS), 0.1f));

        //Mod Ores
        recipes.add(crushingRecipe("magnetite_ore_crushing",ModBlocks.MAGNETITE_ORE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.CHROMITE,1),0.1f));
        recipes.add(crushingRecipe("malachite_ore_crushing",ModBlocks.MALACHITE_ORE.asItem(), new ItemStack(ModItems.MALACHITE,1), new ItemStack(ModItems.AZURITE,1),0.1f));
        recipes.add(crushingRecipe("bauxite_ore_crushing",ModBlocks.BAUXITE_ORE.asItem(), new ItemStack(ModItems.ALUMINA,1), new ItemStack(ModItems.GALLIUM_NUGGET,1),0.05f));
        recipes.add(crushingRecipe("cassiterite_ore_crushing",ModBlocks.CASSITERITE_ORE.asItem(), new ItemStack(ModItems.CASSITERITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("sphalerite_ore_crushing",ModBlocks.SPHALERITE_ORE.asItem(), new ItemStack(ModItems.SPHALERITE,1), new ItemStack(ModItems.GERMANIUM_NUGGET,1),0.1f));
        recipes.add(crushingRecipe("pentlandite_ore_crushing",ModBlocks.PENTLANDITE_ORE.asItem(), new ItemStack(ModItems.PENTLANDITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("interpinifex_ore_crushing",ModBlocks.INTERSPINIFEX_ORE.asItem(), new ItemStack(ModItems.PENTLANDITE,1), new ItemStack(ModItems.CHALCOPYRITE,1),0.1f));
        recipes.add(crushingRecipe("magnesite_ore_crushing",ModBlocks.MAGNESITE_ORE.asItem(), new ItemStack(ModItems.MAGNESITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("galena_ore_crushing",ModBlocks.GALENA_ORE.asItem(), new ItemStack(ModItems.GALENA,1), new ItemStack(ModItems.SPHALERITE,1),0.1f));
        recipes.add(crushingRecipe("acanthite_ore_crushing",ModBlocks.ACANTHITE_ORE.asItem(), new ItemStack(ModItems.ACANTHITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("pyrolusite_ore_crushing",ModBlocks.PYROLUSITE_ORE.asItem(), new ItemStack(ModItems.PYROLUSITE,1), new ItemStack(ModItems.TANTALITE,1),0.05f));
        recipes.add(crushingRecipe("bismite_ore_crushing",ModBlocks.BISMUTHINITE_ORE.asItem(), new ItemStack(ModItems.BISMUTHINITE,1), new ItemStack(ModItems.WOLFRAMITE,1),0.05f));
        recipes.add(crushingRecipe("vanadinite_ore_crushing",ModBlocks.VANADINITE_ORE.asItem(), new ItemStack(ModItems.VANADINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("ilmenite_ore_crushing",ModBlocks.ILMENITE_ORE.asItem(), new ItemStack(ModItems.TITANIA,1), new ItemStack(ModItems.MALACHITE,1),0.1f));
        recipes.add(crushingRecipe("molybdenite_ore_crushing",ModBlocks.MOLYBDENITE_ORE.asItem(), new ItemStack(ModItems.MOLYBDENITE,1), new ItemStack(ModItems.RHENIUM_NUGGET,1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(), new ItemStack(ModItems.CHROMITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("celestine_ore_crushing",ModBlocks.CELESTINE_ORE.asItem(), new ItemStack(ModItems.CELESTINE,1), new ItemStack(ModItems.SALT,1),0.1f));
        recipes.add(crushingRecipe("columbite_ore_crushing",ModBlocks.COLUMBITE_ORE.asItem(), new ItemStack(ModItems.COLUMBITE,1), new ItemStack(ModItems.TANTALITE,1),0.1f));
        recipes.add(crushingRecipe("tantalite_ore_crushing",ModBlocks.TANTALITE_ORE.asItem(), new ItemStack(ModItems.TANTALITE,1), new ItemStack(ModItems.COLUMBITE,1),0.1f));
        recipes.add(crushingRecipe("wolframite_ore_crushing",ModBlocks.WOLFRAMITE_ORE.asItem(), new ItemStack(ModItems.WOLFRAMITE,1), new ItemStack(ModItems.CASSITERITE,1),0.1f));
        recipes.add(crushingRecipe("greenokite_ore_crushing",ModBlocks.GREENOCKITE_ORE.asItem(), new ItemStack(ModItems.GREENOCKITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("uraninite_ore_crushing",ModBlocks.URANINITE_ORE.asItem(), new ItemStack(ModItems.URANINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("stibnite_ore_crushing",ModBlocks.STIBNITE_ORE.asItem(), new ItemStack(ModItems.STIBNITE,1), new ItemStack(ModItems.BARITE,1),0.1f));
        recipes.add(crushingRecipe("xenotime_ore_crushing",ModBlocks.XENOTIME_ORE.asItem(), new ItemStack(ModItems.XENOTIME,1), new ItemStack(ModItems.CALCITE,1),0.1f));
        recipes.add(crushingRecipe("lignite_crushing",ModItems.LIGNITE, new ItemStack(Items.COAL), new ItemStack(ModItems.SULFUR,1),0.5f));
        recipes.add(crushingRecipe("subbituminous_coal_crushing",ModItems.SUBBITUMINOUS_COAL, new ItemStack(Items.COAL), new ItemStack(ModItems.SULFUR,1),0.4f));
        recipes.add(crushingRecipe("bituminous_coal_crushing",ModItems.BITUMINOUS_COAL, new ItemStack(Items.COAL), new ItemStack(ModItems.SULFUR,1),0.25f));
        recipes.add(crushingRecipe("anthracite_crushing",ModItems.ANTHRACITE_COAL, new ItemStack(Items.COAL,2), new ItemStack(ModItems.SULFUR,1),0.1f));
        recipes.add(crushingRecipe("sperrylite_ore_crushing",ModBlocks.SPERRYLITE_ORE.asItem(), new ItemStack(ModItems.PLATINUM_ARSENIDE,1), new ItemStack(ModItems.OSMIRIDIUM_ALLOY,1),1f));
        recipes.add(crushingRecipe("meteorite_crushing",ModBlocks.METEORITE.asItem(), new ItemStack(ModItems.SILICON,2), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("bloom_iron_crushing",ModItems.BLOOM_IRON.asItem(), new ItemStack(ModItems.WROUGHT_IRON_INGOT,1), new ItemStack(ModItems.SLAG,1),0.5f));
        recipes.add(crushingRecipe("cobalite_ore_crushing",ModBlocks.COBALTITE_ORE.asItem(), new ItemStack(ModItems.COBALTITE,1), new ItemStack(ModItems.COBALTITE,1),0.1f));
        recipes.add(crushingRecipe("petalite_ore_crushing",ModBlocks.PETALITE_ORE.asItem(), new ItemStack(ModItems.PETALITE,1), new ItemStack(ModItems.TOURMALINE,1),0.1f));
        recipes.add(crushingRecipe("chalcopyrite_crushing",ModItems.CHALCOPYRITE, new ItemStack(ModItems.COPPER_NUGGET,5), new ItemStack(Items.IRON_NUGGET,5),1.0f));
        recipes.addAll(groupCrushingRecipe("leaves_crushing","minecraft:leaves",new ItemStack(ModItems.BIOMASS, 1),new ItemStack(ModItems.COMPOST,1), 1.0f));
        recipes.addAll(groupCrushingRecipe("crops_crushing","forge:crops",new ItemStack(ModItems.BIOMASS,3),new ItemStack(ModItems.COMPOST,1), 0.5f));
        recipes.addAll(groupCrushingRecipe("saplings_crushing","minecraft:saplings",new ItemStack(ModItems.BIOMASS,2),new ItemStack(ModItems.COMPOST,1), 0.3f));
        recipes.add(crushingRecipe("cinnabar_crushing",ModItems.CINNABAR, new ItemStack(Items.REDSTONE,3), new ItemStack(ModItems.SULFUR,1),0.1f));
        recipes.add(crushingRecipe("cinnabar_crushing",ModBlocks.CINNABAR_BLOCK.asItem(), new ItemStack(Items.REDSTONE,27), new ItemStack(ModItems.SULFUR,1),0.9f));
        recipes.add(crushingRecipe("cryolite_ore_crushing",ModBlocks.CRYOLITE_ORE.asItem(), new ItemStack(ModItems.CRYOLITE,1), new ItemStack(ModItems.ALUMINA,1),0.1f));
        recipes.add(crushingRecipe("pyrite_ore_crushing",ModBlocks.PYRITE_ORE.asItem(), new ItemStack(ModItems.PYRITE,1), new ItemStack(Items.GOLD_NUGGET,1),1.0f));
        recipes.add(crushingRecipe("pyrite_crushing",ModItems.PYRITE, new ItemStack(Items.IRON_NUGGET,6), new ItemStack(Items.GOLD_NUGGET,1),1.0f));




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

        recipes.add(forgingRecipe("pewter_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,3),new ItemStack(ModItems.PEWTER_PICKAXE)));
        recipes.add(forgingRecipe("pewter_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,3),new ItemStack(ModItems.PEWTER_AXE)));
        recipes.add(forgingRecipe("pewter_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,1),new ItemStack(ModItems.PEWTER_SHOVEL)));
        recipes.add(forgingRecipe("pewter_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,2),new ItemStack(ModItems.PEWTER_HOE)));
        recipes.add(forgingRecipe("pewter_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.PEWTER_ALLOY,2),new ItemStack(ModItems.PEWTER_SWORD)));
        recipes.add(forgingRecipe("pewter_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,3),new ItemStack(ModItems.PEWTER_SPEAR)));
        recipes.add(forgingRecipe("pewter_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.PEWTER_ALLOY,5),new ItemStack(ModItems.PEWTER_HAMMER)));

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

        recipes.add(forgingRecipe("black_gold_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,3),new ItemStack(ModItems.BLACK_GOLD_PICKAXE)));
        recipes.add(forgingRecipe("black_gold_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,3),new ItemStack(ModItems.BLACK_GOLD_AXE)));
        recipes.add(forgingRecipe("black_gold_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,1),new ItemStack(ModItems.BLACK_GOLD_SHOVEL)));
        recipes.add(forgingRecipe("black_gold_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,2),new ItemStack(ModItems.BLACK_GOLD_HOE)));
        recipes.add(forgingRecipe("black_gold_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.BLACK_GOLD_ALLOY,2),new ItemStack(ModItems.BLACK_GOLD_SWORD)));
        recipes.add(forgingRecipe("black_gold_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,3),new ItemStack(ModItems.BLACK_GOLD_SPEAR)));
        recipes.add(forgingRecipe("black_gold_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.BLACK_GOLD_ALLOY,5),new ItemStack(ModItems.BLACK_GOLD_HAMMER)));

        recipes.add(forgingRecipe("steel_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_PICKAXE)));
        recipes.add(forgingRecipe("steel_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_AXE)));
        recipes.add(forgingRecipe("steel_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,1),new ItemStack(ModItems.STEEL_SHOVEL)));
        recipes.add(forgingRecipe("steel_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,2),new ItemStack(ModItems.STEEL_HOE)));
        recipes.add(forgingRecipe("steel_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.STEEL_ALLOY,2),new ItemStack(ModItems.STEEL_SWORD)));
        recipes.add(forgingRecipe("steel_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,3),new ItemStack(ModItems.STEEL_SPEAR)));
        recipes.add(forgingRecipe("steel_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.STEEL_ALLOY,5),new ItemStack(ModItems.STEEL_HAMMER)));

        recipes.add(forgingRecipe("stainless_steel_pickaxe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,3),new ItemStack(ModItems.STAINLESS_STEEL_PICKAXE)));
        recipes.add(forgingRecipe("stainless_steel_axe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,3),new ItemStack(ModItems.STAINLESS_STEEL_AXE)));
        recipes.add(forgingRecipe("stainless_steel_shovel",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,1),new ItemStack(ModItems.STAINLESS_STEEL_SHOVEL)));
        recipes.add(forgingRecipe("stainless_steel_hoe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,2),new ItemStack(ModItems.STAINLESS_STEEL_HOE)));
        recipes.add(forgingRecipe("stainless_steel_sword",new ItemStack(ModItems.STEEL_ROD,1),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,2),new ItemStack(ModItems.STAINLESS_STEEL_SWORD)));
        recipes.add(forgingRecipe("stainless_steel_spear",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,3),new ItemStack(ModItems.STAINLESS_STEEL_SPEAR)));
        recipes.add(forgingRecipe("stainless_steel_hammer",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.STAINLESS_STEEL_ALLOY,5),new ItemStack(ModItems.STAINLESS_STEEL_HAMMER)));

        recipes.add(forgingRecipe("nickel_superalloy_pickaxe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_PICKAXE)));
        recipes.add(forgingRecipe("nickel_superalloy_axe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_AXE)));
        recipes.add(forgingRecipe("nickel_superalloy_shovel",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,1),new ItemStack(ModItems.NICKEL_SUPERALLOY_SHOVEL)));
        recipes.add(forgingRecipe("nickel_superalloy_hoe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,2),new ItemStack(ModItems.NICKEL_SUPERALLOY_HOE)));
        recipes.add(forgingRecipe("nickel_superalloy_sword",new ItemStack(ModItems.STEEL_ROD,1),new ItemStack(ModItems.NICKEL_SUPERALLOY,2),new ItemStack(ModItems.NICKEL_SUPERALLOY_SWORD)));
        recipes.add(forgingRecipe("nickel_superalloy_spear",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,3),new ItemStack(ModItems.NICKEL_SUPERALLOY_SPEAR)));
        recipes.add(forgingRecipe("nickel_superalloy_hammer",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.NICKEL_SUPERALLOY,5),new ItemStack(ModItems.NICKEL_SUPERALLOY_HAMMER)));

        recipes.add(forgingRecipe("cobalt_superalloy_pickaxe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,3),new ItemStack(ModItems.COBALT_SUPERALLOY_PICKAXE)));
        recipes.add(forgingRecipe("cobalt_superalloy_axe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,3),new ItemStack(ModItems.COBALT_SUPERALLOY_AXE)));
        recipes.add(forgingRecipe("cobalt_superalloy_shovel",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,1),new ItemStack(ModItems.COBALT_SUPERALLOY_SHOVEL)));
        recipes.add(forgingRecipe("cobalt_superalloy_hoe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,2),new ItemStack(ModItems.COBALT_SUPERALLOY_HOE)));
        recipes.add(forgingRecipe("cobalt_superalloy_sword",new ItemStack(ModItems.STEEL_ROD,1),new ItemStack(ModItems.COBALT_SUPERALLOY,2),new ItemStack(ModItems.COBALT_SUPERALLOY_SWORD)));
        recipes.add(forgingRecipe("cobalt_superalloy_spear",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,3),new ItemStack(ModItems.COBALT_SUPERALLOY_SPEAR)));
        recipes.add(forgingRecipe("cobalt_superalloy_hammer",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.COBALT_SUPERALLOY,5),new ItemStack(ModItems.COBALT_SUPERALLOY_HAMMER)));

        recipes.add(forgingRecipe("tungsten_heavy_alloy_pickaxe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,3),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_PICKAXE)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_axe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,3),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_AXE)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_shovel",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,1),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_SHOVEL)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_hoe",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_HOE)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_sword",new ItemStack(ModItems.STEEL_ROD,1),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_SWORD)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_spear",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,3),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_SPEAR)));
        recipes.add(forgingRecipe("tungsten_heavy_alloy_hammer",new ItemStack(ModItems.STEEL_ROD,2),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY,5),new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY_HAMMER)));

        if (Config.HASTE_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("haste_pendant",new ItemStack(ModItems.RUBY,9),new ItemStack(ModItems.ROSE_GOLD_ALLOY,64),new ItemStack(ModItems.HASTE_PENDANT)));}
        if (Config.LUCK_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("luck_pendant",new ItemStack(ModItems.PERIDOT,9),new ItemStack(ModItems.WHITE_GOLD_ALLOY,64),new ItemStack(ModItems.LUCK_PENDANT)));}
        if (Config.HEALTH_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("health_pendant",new ItemStack(Items.EMERALD,9),new ItemStack(ModItems.GREEN_GOLD_ALLOY,64),new ItemStack(ModItems.HEALTH_PENDANT)));}
        if (Config.SPEED_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("speed_pendant",new ItemStack(ModItems.SAPPHIRE,9),new ItemStack(ModItems.BLUE_GOLD_ALLOY,64),new ItemStack(ModItems.SPEED_PENDANT)));}
        if (Config.LEVITATION_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("levitation_pendant",new ItemStack(ModItems.AQUAMARINE,9),new ItemStack(ModItems.PURPLE_GOLD_ALLOY,64),new ItemStack(ModItems.LEVITATION_PENDANT)));}
        if (Config.REPULSION_PENDANT_RECIPE.get()) {recipes.add(forgingRecipe("repulsion_pendant",new ItemStack(ModItems.GARNET,9),new ItemStack(ModItems.BLACK_GOLD_ALLOY,64),new ItemStack(ModItems.REPULSION_PENDANT)));}

        if (Config.ENABLE_AMALGAM_TOOLS.get())
        {
            recipes.add(forgingRecipe("amalgam_pickaxe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_PICKAXE)));
            recipes.add(forgingRecipe("amalgam_axe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_AXE)));
            recipes.add(forgingRecipe("amalgam_shovel",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,1),new ItemStack(ModItems.AMALGAM_SHOVEL)));
            recipes.add(forgingRecipe("amalgam_hoe",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,2),new ItemStack(ModItems.AMALGAM_HOE)));
            recipes.add(forgingRecipe("amalgam_sword",new ItemStack(Items.STICK,1),new ItemStack(ModItems.AMALGAM_ALLOY,2),new ItemStack(ModItems.AMALGAM_SWORD)));
            recipes.add(forgingRecipe("amalgam_spear",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,3),new ItemStack(ModItems.AMALGAM_SPEAR)));
            recipes.add(forgingRecipe("amalgam_hammer",new ItemStack(Items.STICK,2),new ItemStack(ModItems.AMALGAM_ALLOY,5),new ItemStack(ModItems.AMALGAM_HAMMER)));
        }


        // (EXPERIMENTAL) CONFIG ALLOYS
        if (Config.ENABLE_CUPRONICKEL_TOOLS.get())
        {
            recipes.add(forgingRecipe("cupronickel_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("cupronickel_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("cupronickel_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("cupronickel_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("cupronickel_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.CUPRONICKEL_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("cupronickel_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("cupronickel_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CUPRONICKEL_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_STERLING_SILVER_TOOLS.get())
        {
            recipes.add(forgingRecipe("sterling_silver_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("sterling_silver_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("sterling_silver_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("sterling_silver_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("sterling_silver_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.STERLING_SILVER_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("sterling_silver_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("sterling_silver_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.STERLING_SILVER_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_BRASS_TOOLS.get())
        {
            recipes.add(forgingRecipe("brass_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("brass_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("brass_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("brass_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("brass_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.BRASS_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("brass_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("brass_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.BRASS_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_NICKEL_SILVER_TOOLS.get())
        {
            recipes.add(forgingRecipe("nickel_silver_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("nickel_silver_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("nickel_silver_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("nickel_silver_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("nickel_silver_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("nickel_silver_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("nickel_silver_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.NICKEL_SILVER_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_CAST_IRON_TOOLS.get())
        {
            recipes.add(forgingRecipe("cast_iron_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("cast_iron_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("cast_iron_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("cast_iron_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("cast_iron_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.CAST_IRON_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("cast_iron_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("cast_iron_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.CAST_IRON_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_DURALUMIN_TOOLS.get())
        {
            recipes.add(forgingRecipe("duralumin_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("duralumin_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("duralumin_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("duralumin_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("duralumin_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.DURALUMIN_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("duralumin_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("duralumin_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.DURALUMIN_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_MAGNESIUM_ALLOY_TOOLS.get())
        {
            recipes.add(forgingRecipe("magnesium_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("magnesium_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("magnesium_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("magnesium_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("magnesium_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.MAGNESIUM_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("magnesium_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("magnesium_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.MAGNESIUM_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_ROSE_METAL_TOOLS.get())
        {
            recipes.add(forgingRecipe("rose_metal_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("rose_metal_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("rose_metal_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("rose_metal_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("rose_metal_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.ROSE_METAL_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("rose_metal_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("rose_metal_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ROSE_METAL_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_GALINSTAN_TOOLS.get())
        {
            recipes.add(forgingRecipe("galinstan_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("galinstan_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("galinstan_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("galinstan_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("galinstan_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.GALINSTAN_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("galinstan_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("galinstan_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.GALINSTAN_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }

        if (Config.ENABLE_ALNICO_TOOLS.get())
        {
            recipes.add(forgingRecipe("alnico_alloy_pickaxe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,3),new ItemStack(ModItems.ALLOY_PICKAXE)));
            recipes.add(forgingRecipe("alnico_alloy_axe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,3),new ItemStack(ModItems.ALLOY_AXE)));
            recipes.add(forgingRecipe("alnico_alloy_shovel",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,1),new ItemStack(ModItems.ALLOY_SHOVEL)));
            recipes.add(forgingRecipe("alnico_alloy_hoe",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,2),new ItemStack(ModItems.ALLOY_HOE)));
            recipes.add(forgingRecipe("alnico_alloy_sword",new ItemStack(ModItems.CAST_IRON_ROD,1),new ItemStack(ModItems.ALNICO_ALLOY,2),new ItemStack(ModItems.ALLOY_SWORD)));
            recipes.add(forgingRecipe("alnico_alloy_spear",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,3),new ItemStack(ModItems.ALLOY_SPEAR)));
            recipes.add(forgingRecipe("alnico_alloy_hammer",new ItemStack(ModItems.CAST_IRON_ROD,2),new ItemStack(ModItems.ALNICO_ALLOY,5),new ItemStack(ModItems.ALLOY_HAMMER)));
        }


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
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY,ItemStack.EMPTY);
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


    public static ItemStack getAlloyOutput(ItemStack input1, ItemStack input2, ItemStack input3)
    {

        List<IAlloyRecipe> recipes = ModRecipes.getAlloyRecipes();
        AlloyRecipeHelper r = AlloyRecipeHelper.getInstance();

        AbstractMap.SimpleEntry<String,Integer> material1 = r.returnItemMaterial(input1);
        AbstractMap.SimpleEntry<String,Integer> material2 = r.returnItemMaterial(input2);
        AbstractMap.SimpleEntry<String,Integer> material3 = r.returnItemMaterial(input3);

        List<String> materials = Arrays.asList(material1.getKey(),material2.getKey(),material3.getKey());
        List<Integer> amounts = Arrays.asList(material1.getValue(),material2.getValue(),material3.getValue());
        float total = material1.getValue() + material2.getValue() + material3.getValue();

        if (materials.contains("nope") || material1.getKey().equals("none") || material2.getKey().contains("none"))
        {
            return ItemStack.EMPTY;
        }
        List<Float> props = Arrays.asList(amounts.get(0)/total,
                amounts.get(1)/total,
                amounts.get(2)/total);

        IAlloyRecipe foundRecipe = null;
        boolean flag = false;
        boolean flag2 = true;
        for (IAlloyRecipe recipe: recipes)
        {/*
            if (input1.getItem().getTags().contains(recipe.getPrimaryTag())
                    && input2.getItem().getTags().contains(recipe.getSecondaryTag()))
            {
                if (input3.isEmpty() && recipe.getOther().getKey() == 0)
                {
                    flag = true;
                } else if (!input3.isEmpty()){
                    for (int i = 2; i < recipe.getIngredients().size(); i++)
                    {
                        if (input3.getItem().getTags().contains(recipe.getRemainderTag(i)))
                        {
                            flag = true;
                        }
                    }
                }
            }*/
            if (recipe.getItemsPrimary().contains(input1.getItem()) && recipe.getItemsSecondary().contains(input2.getItem()))
            {
                if (input3.isEmpty() && recipe.getOther().getKey() == 0)
                {
                    flag = true;
                } else if (!input3.isEmpty()){
                    if (recipe.getItemsRemainder().contains(input3.getItem()))
                    {
                        flag = true;
                    }
                }
            }
            if (flag)
            {
                foundRecipe = recipe;
                break;
            }
        }
        if (foundRecipe != null)
        {
            for (int i = 0; i < 3; i++)
            {
                if (i == 2 && foundRecipe.getOther().getKey() == 0)
                {
                    break;
                }
                AbstractMap.SimpleEntry<Float,Float> bounds = foundRecipe.getBounds(i);
                if (props.get(i) < bounds.getKey() || props.get(i) > bounds.getValue())
                {
                    flag2 = false;
                    break;
                }
            }
            if (flag2 && Math.round(total/10) <= 64)
            {
                return new ItemStack(foundRecipe.getRecipeOutput().getItem(),Math.round(total/10));
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getTripleAlloyOutput(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5)
    {

        List<ITripleAlloyRecipe> recipes = ModRecipes.getTripleAlloyRecipes();
        AlloyRecipeHelper r = AlloyRecipeHelper.getInstance();

        AbstractMap.SimpleEntry<String,Integer> material1 = r.returnItemMaterial(input1);
        AbstractMap.SimpleEntry<String,Integer> material2 = r.returnItemMaterial(input2);
        AbstractMap.SimpleEntry<String,Integer> material3 = r.returnItemMaterial(input3);
        AbstractMap.SimpleEntry<String,Integer> material4 = r.returnItemMaterial(input4);
        AbstractMap.SimpleEntry<String,Integer> material5 = r.returnItemMaterial(input5);

        List<String> materials = Arrays.asList(material1.getKey(),material2.getKey(),material3.getKey(),material4.getKey(),material5.getKey());
        List<Integer> amounts = Arrays.asList(material1.getValue(),material2.getValue(),material3.getValue(),material4.getValue(),material5.getValue());
        float total = material1.getValue() + material2.getValue() + material3.getValue() + material4.getValue() + material5.getValue();

        if (materials.contains("nope") || material1.getKey().equals("none") || material2.getKey().contains("none") || material3.getKey().contains("none"))
        {
            return ItemStack.EMPTY;
        }
        List<Float> props = Arrays.asList(amounts.get(0)/total,
                amounts.get(1)/total,
                amounts.get(2)/total,
                amounts.get(3)/total,
                amounts.get(4)/total);

        ITripleAlloyRecipe foundRecipe = null;
        boolean flag = false;
        boolean flag2 = true;
        for (ITripleAlloyRecipe recipe: recipes)
        {/*
            if (input1.getItem().getTags().contains(recipe.getPrimaryTag())
                    && input2.getItem().getTags().contains(recipe.getSecondaryTag()))
            {
                if (input3.isEmpty() && recipe.getOther().getKey() == 0)
                {
                    flag = true;
                } else if (!input3.isEmpty()){
                    for (int i = 2; i < recipe.getIngredients().size(); i++)
                    {
                        if (input3.getItem().getTags().contains(recipe.getRemainderTag(i)))
                        {
                            flag = true;
                        }
                    }
                }
            }*/
            if (recipe.getItemsPrimary().contains(input1.getItem()) && recipe.getItemsSecondary().contains(input2.getItem()) && recipe.getItemsTertiary().contains(input3.getItem()))
            {
                if (input4.isEmpty() && input5.isEmpty() && recipe.getOther().getKey() == 0)
                {
                    flag = true;
                } else if (!input4.isEmpty()){
                    if (recipe.getItemsRemainder().contains(input4.getItem()))
                    {
                        if (input5.isEmpty() || (recipe.getItemsRemainder().contains(input5.getItem()) && !input5.isItemEqual(input4)))
                        {
                            flag = true;
                        }

                    }
                }
            }
            if (flag)
            {
                foundRecipe = recipe;
                break;
            }
        }
        if (foundRecipe != null)
        {
            for (int i = 0; i < 5; i++)
            {
                AbstractMap.SimpleEntry<Float,Float> bounds = foundRecipe.getBounds(i);
                if (props.get(i) < bounds.getKey() || props.get(i) > bounds.getValue())
                {
                    flag2 = false;
                    break;
                }
            }
            if (flag2 && Math.round(total/10) <= 64)
            {
                return new ItemStack(foundRecipe.getRecipeOutput().getItem(),Math.round(total/10));
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
    public static IAlloyRecipe alloyRecipe(String registry, ItemStack output, List<ItemStack[]> input, AbstractMap.SimpleEntry<Float, Float> primary,
                                           AbstractMap.SimpleEntry<Float, Float> secondary, AbstractMap.SimpleEntry<Float, Float> other, float req)
    {
        NonNullList<Ingredient> list = NonNullList.create();
        for (int i = 0; i < input.size(); i++)
        {
            list.add(i,Ingredient.fromStacks(input.get(i)));
        }
        return new IAlloyRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output, list
                ,primary,secondary,other,req);
    }

    public static ITripleAlloyRecipe tripleAlloyRecipe(String registry, ItemStack output, List<ItemStack[]> input, AbstractMap.SimpleEntry<Float, Float> primary,
                                           AbstractMap.SimpleEntry<Float, Float> secondary, AbstractMap.SimpleEntry<Float, Float> tertiary, AbstractMap.SimpleEntry<Float, Float> other, float req)
    {
        NonNullList<Ingredient> list = NonNullList.create();
        for (int i = 0; i < input.size(); i++)
        {
            list.add(i,Ingredient.fromStacks(input.get(i)));
        }
        return new ITripleAlloyRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output, list
                ,primary,secondary,tertiary,other,req);
    }

    public static IPistonCrusherRecipe crushingRecipe(String registry, Item input, ItemStack output, ItemStack secondary, float secondaryChance)
    {
        return new IPistonCrusherRecipe(new ResourceLocation(ProjectRankine.MODID,registry),new ItemStack[]{output,secondary},
                Ingredient.fromStacks(new ItemStack(input)),secondaryChance);
    }

    public static ISluicingRecipe sluicingRecipe(String registry, Item input, WeightedCollection<ItemStack> col)
    {
        List<ItemStack> output = new ArrayList<>(col.getEntries());
        List<Float> weights = new ArrayList<>(col.getWeights());
        return new ISluicingRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                Ingredient.fromStacks(new ItemStack(input)),weights);
    }

    public static IEvaporationRecipe evaporationRecipe(String registry, Item input, WeightedCollection<ItemStack> col)
    {
        List<ItemStack> output = new ArrayList<>(col.getEntries());
        List<Float> weights = new ArrayList<>(col.getWeights());
        return new IEvaporationRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                Ingredient.fromStacks(new ItemStack(input)),weights);
    }


    public static IBeehiveOvenRecipe beehiveOvenRecipe(String registry, Item input, ItemStack output)
    {
        return new IBeehiveOvenRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                Ingredient.fromStacks(new ItemStack(input)));
    }

    public static ICoalForgeRecipe forgingRecipe(String registry, ItemStack input, ItemStack alloy, ItemStack output)
    {
        ItemStack result = getForgingItemStack(alloy,output,false).get(0);
        ItemStack template = getForgingItemStack(alloy,output,false).get(1);
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

    public static List<ItemStack> getForgingItemStack(ItemStack alloy, ItemStack output, boolean hasComposition) {

        ItemStack result = ItemStack.EMPTY;
        ItemStack template = new ItemStack(ModItems.PICKAXE_TEMPLATE);
        String comp;
        if (output.getItem() instanceof HastePendantItem || output.getItem() instanceof HealthPendantItem ||
                output.getItem() instanceof RepulsionPendantItem || output.getItem() instanceof LuckPendantItem ||
                output.getItem() instanceof SpeedPendantItem || output.getItem() instanceof LevitationPendantItem)
        {
            result = output;
            template = new ItemStack(ModItems.PENDANT_TEMPLATE);
        } else if (output.getItem() instanceof AlloyPickaxe)
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
        else if (output.getItem() instanceof AlloyAxe)
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
        else if (output.getItem() instanceof AlloyShovel)
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
        else if (output.getItem() instanceof AlloyHoe)
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
        else if (output.getItem() instanceof AlloySword)
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
        else if (output.getItem() instanceof AlloySpear)
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
        else if (output.getItem() instanceof AlloyHammer)
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
