package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

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
        recipes.add(alloyRecipe("bronze_alloy",new ItemStack(ModItems.BRONZE_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("tin"),
                returnTagFamily("aluminum"),returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),
                returnTagFamily("iron"),returnTagFamily("bismuth"),returnTagFamily("lead"),returnTagFamily("antimony"),returnTagFamily("silicon"),
                returnTagFamily("phosphorus")),new AbstractMap.SimpleEntry<>(.8f,.9f),new AbstractMap.SimpleEntry<>(.1f,.2f),
                new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("pewter_alloy",new ItemStack(ModItems.PEWTER_ALLOY),Arrays.asList(returnTagFamily("tin"),returnTagFamily("antimony"),
                returnTagFamily("copper"),returnTagFamily("bismuth"),returnTagFamily("silver"),returnTagFamily("lead")),
                new AbstractMap.SimpleEntry<>(.85f,.98f), new AbstractMap.SimpleEntry<>(.02f,.1f),new AbstractMap.SimpleEntry<>(0f,.13f),.87f));

        recipes.add(alloyRecipe("aluminum_bronze_alloy",new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("aluminum"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),returnTagFamily("iron"),returnTagFamily("lead")),
                new AbstractMap.SimpleEntry<>(.74f,.93f), new AbstractMap.SimpleEntry<>(.04f,.12f),new AbstractMap.SimpleEntry<>(0f,.15f),.85f));

        recipes.add(alloyRecipe("brass_alloy",new ItemStack(ModItems.BRASS_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("zinc"),
                returnTagFamily("tin"),returnTagFamily("lead"),returnTagFamily("aluminum"),returnTagFamily("nickel"),returnTagFamily("iron")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.15f,.6f),new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("cupronickel_alloy",new ItemStack(ModItems.CUPRONICKEL_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("iron"),returnTagFamily("manganese"),returnTagFamily("tin"),returnTagFamily("niobium"),returnTagFamily("lead"), returnTagFamily("titanium"),
                returnTagFamily("chromium"),returnTagFamily("aluminum"), returnTagFamily("beryllium"),returnTagFamily("silicon"),returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.7f,.9f), new AbstractMap.SimpleEntry<>(.1f,.3f),new AbstractMap.SimpleEntry<>(0f,.05f),.95f));

        recipes.add(alloyRecipe("nickel_silver_alloy",new ItemStack(ModItems.NICKEL_SILVER_ALLOY),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.5f,.7f), new AbstractMap.SimpleEntry<>(.15f,.25f),new AbstractMap.SimpleEntry<>(.15f,.25f),1f));

        recipes.add(alloyRecipe("invar_alloy",new ItemStack(ModItems.INVAR_ALLOY),Arrays.asList(returnTagFamily("iron"),returnTagFamily("nickel"),
                returnTagFamily("cobalt")), new AbstractMap.SimpleEntry<>(.5f,.9f), new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(0f,.05f),0.95f));

        recipes.add(alloyRecipe("cast_iron_alloy",new ItemStack(ModItems.CAST_IRON_ALLOY),Arrays.asList(returnTagFamily("pig_iron"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("chromium"),returnTagFamily("molybdenum"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("silicon")), new AbstractMap.SimpleEntry<>(.86f,.98f), new AbstractMap.SimpleEntry<>(.02f,.04f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("rose_gold_alloy",new ItemStack(ModItems.ROSE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("copper"),
                returnTagFamily("silver"), returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.6f),.94f));

        recipes.add(alloyRecipe("white_gold_alloy",new ItemStack(ModItems.WHITE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("zinc"),
                returnTagFamily("nickel"),returnTagFamily("palladium"),returnTagFamily("silver"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.74f,.9f), new AbstractMap.SimpleEntry<>(.05f,.1f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("green_gold_alloy",new ItemStack(ModItems.GREEN_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("silver"),
                returnTagFamily("copper"),returnTagFamily("cadmium"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.3f,.7f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy",new ItemStack(ModItems.BLUE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("iron"),
                returnTagFamily("nickel"),returnTagFamily("rhodium"),returnTagFamily("ruthenium")),
                new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("purple_gold_alloy",new ItemStack(ModItems.PURPLE_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("aluminum")),
                new AbstractMap.SimpleEntry<>(.79f,.81f), new AbstractMap.SimpleEntry<>(.19f,.21f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("black_gold_alloy",new ItemStack(ModItems.BLACK_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("cobalt"),
                returnTagFamily("chromium")), new AbstractMap.SimpleEntry<>(.75f,.80f), new AbstractMap.SimpleEntry<>(.15f,.20f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("black_gold_alloy",new ItemStack(ModItems.BLACK_GOLD_ALLOY),Arrays.asList(returnTagFamily("gold"),returnTagFamily("cobalt"),
                returnTagFamily("chromium")), new AbstractMap.SimpleEntry<>(.75f,.80f), new AbstractMap.SimpleEntry<>(.15f,.20f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));


        return recipes;
    }

    public static List<ITripleAlloyRecipe> getTripleAlloyRecipes() {
        List<ITripleAlloyRecipe> recipes = new ArrayList<>();

        recipes.add(tripleAlloyRecipe("steel_alloy", new ItemStack(ModItems.STEEL_ALLOY), Arrays.asList(returnTagFamily("pig_iron"),
                returnTagFamily("manganese"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("chromium"),returnTagFamily("cobalt"),
                returnTagFamily("molybdenum"),returnTagFamily("nickel"),
                returnTagFamily("niobium"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"),
                returnTagFamily("copper"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.97f,.98f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(0f, .01f),.99f));

        recipes.add(tripleAlloyRecipe("stainless_steel_alloy", new ItemStack(ModItems.STAINLESS_STEEL_ALLOY), Arrays.asList(returnTagFamily("wrought_iron"),
                returnTagFamily("chromium"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("molybdenum"),returnTagFamily("aluminum"),
                returnTagFamily("manganese"),returnTagFamily("copper"),
                returnTagFamily("nickel"),returnTagFamily("boron"),
                returnTagFamily("cobalt"),returnTagFamily("strontium"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("titanium"),returnTagFamily("tungsten"),
                returnTagFamily("yttrium"),returnTagFamily("selenium"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"),
                returnTagFamily("silicon"), returnTagFamily("phosphorus"),
                returnTagFamily("sulfur"),returnTagFamily("nitrogen")),
                new AbstractMap.SimpleEntry<>(.65f, .75f), new AbstractMap.SimpleEntry<>(.10f, .2f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(0f, .12f),.85f));

        recipes.add(tripleAlloyRecipe("tungsten_heavy_alloy", new ItemStack(ModItems.TUNGSTEN_HEAVY_ALLOY), Arrays.asList(returnTagFamily("tungsten"),
                returnTagFamily("nickel"),returnTagFamily("iron"),returnTagFamily("copper"),
                returnTagFamily("cobalt"),returnTagFamily("chromium"),
                returnTagFamily("molybdenum"),returnTagFamily("aluminum"),
                returnTagFamily("boron"),returnTagFamily("titanium"),
                returnTagFamily("tantalum"),returnTagFamily("yttrium"),
                returnTagFamily("cerium"),returnTagFamily("lanthanum"),
                returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.88f, .95f), new AbstractMap.SimpleEntry<>(.02f, .1f), new AbstractMap.SimpleEntry<>(.01f, .04f), new AbstractMap.SimpleEntry<>(0f, .09f),.91f));

        recipes.add(tripleAlloyRecipe("mischmetal_alloy", new ItemStack(ModItems.MISCHMETAL_ALLOY), Arrays.asList(returnTagFamily("cerium"),
                returnTagFamily("lanthanum"),returnTagFamily("neodymium"),
                returnTagFamily("praseodymium"),returnTagFamily("dysprosium"),
                returnTagFamily("europium"),returnTagFamily("gadolinium"),
                returnTagFamily("samarium"),returnTagFamily("tebrium"),
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
                returnTagFamily("chromium"), returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.91f, .95f), new AbstractMap.SimpleEntry<>(.03f, .05f), new AbstractMap.SimpleEntry<>(.01f, .03f), new AbstractMap.SimpleEntry<>(0f, .04f),.96f));

        recipes.add(tripleAlloyRecipe("rose_metal_alloy", new ItemStack(ModItems.ROSE_METAL_ALLOY), Arrays.asList(returnTagFamily("bismuth"),
                returnTagFamily("lead"),returnTagFamily("tin")),
                new AbstractMap.SimpleEntry<>(.3f, .5f), new AbstractMap.SimpleEntry<>(.18f, .4f), new AbstractMap.SimpleEntry<>(.1f, .25f), new AbstractMap.SimpleEntry<>(0f, 0f),1f));

            recipes.add(tripleAlloyRecipe("nickel_superalloy_tial", new ItemStack(ModItems.NICKEL_SUPERALLOY), Arrays.asList(returnTagFamily("nickel"),
                returnTagFamily("titanium"),returnTagFamily("aluminum"),
                returnTagFamily("chromium"),returnTagFamily("molybdenum"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("cobalt"),returnTagFamily("manganese"),
                returnTagFamily("boron"),returnTagFamily("iron"),
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
                returnTagFamily("ruthenium"),returnTagFamily("rhenium"),
                returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.5f, .75f), new AbstractMap.SimpleEntry<>(.14f, .27f), new AbstractMap.SimpleEntry<>(.01f, .2f), new AbstractMap.SimpleEntry<>(0f, .15f),.65f));

        recipes.add(tripleAlloyRecipe("cobalt_superalloy", new ItemStack(ModItems.COBALT_SUPERALLOY), Arrays.asList(returnTagFamily("cobalt"),
                returnTagFamily("chromium"),returnTagFamily("nickel"),
                returnTagFamily("tantalum"),returnTagFamily("molybdenum"),
                returnTagFamily("tungsten"),returnTagFamily("titanium"),
                returnTagFamily("aluminum"),returnTagFamily("iridium"),
                returnTagFamily("iron"),returnTagFamily("aluminum"),
                returnTagFamily("carbon","coke","graphite"),
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
            ITag<Item> d = ItemTags.getCollection().get(i);
            if (d != null)
            {
                list.addAll(d.getAllElements());
            } /* else
            {
                System.out.println("TAG at ResourceLocation " + i + " does not exist.");
            }*/

        }
        ItemStack[] result = new ItemStack[list.size()];
        if (!list.isEmpty())
        {
            for (int i = 0; i < list.size(); i++)
            {
                result[i] = new ItemStack(list.get(i));
            }

        } else
        {
            System.out.println("Tag not found for JEI Recipe with ResourceLocation(s): " + Arrays.toString(rs));
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
        recipes.add(beehiveOvenRecipe("subbituminous_oven_cooking", ModBlocks.LIGNITE_BLOCK.asItem(),new ItemStack(ModBlocks.SUBBITUMINOUS_COAL_BLOCK)));
        recipes.add(beehiveOvenRecipe("subbituminous_oven_cooking", ModBlocks.SUBBITUMINOUS_COAL_BLOCK.asItem(),new ItemStack(ModBlocks.BITUMINOUS_COAL_BLOCK)));
        recipes.add(beehiveOvenRecipe("bituminous_oven_cooking", ModBlocks.BITUMINOUS_COAL_BLOCK.asItem(),new ItemStack(ModBlocks.COKE_BLOCK)));
        recipes.add(beehiveOvenRecipe("bloom_pig_oven_cooking", ModBlocks.PIG_IRON_BLOCK.asItem(),new ItemStack(ModBlocks.BLOOM_IRON_BLOCK)));

        return recipes;
    }
    public static List<IPistonCrusherRecipe> getCrushingRecipes()
    {
        List<IPistonCrusherRecipe> recipes = new ArrayList<>();

        //Stones
        recipes.add(crushingRecipe("cobblestone_crushing",Blocks.COBBLESTONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("stone_crushing",Blocks.STONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("sandstone_crushing",Blocks.SANDSTONE.asItem(), new ItemStack(Items.SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("red_sandstone_crushing",Blocks.RED_SANDSTONE.asItem(), new ItemStack(Items.RED_SAND,3), new ItemStack(ModItems.FELDSPAR,1),0.5f));
        recipes.add(crushingRecipe("red_granite_crushing",Blocks.GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("quartz_diorite_crushing",Blocks.DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gray_andesite_crushing",Blocks.ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("basalt_crushing",Blocks.BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("blackstone_crushing",Blocks.BLACKSTONE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("granite_crushing",ModBlocks.RED_GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("diorite_crushing",ModBlocks.GRANODIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("andesite_crushing",ModBlocks.HORNBLENDE_ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("limestone_crushing",ModBlocks.LIMESTONE.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("shale_crushing",ModBlocks.SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_crushing",ModBlocks.ANORTHOSITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("ironstone_crushing",ModBlocks.IRONSTONE.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("tholeiitic_basalt_crushing",ModBlocks.THOLEIITIC_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
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

        //smooth stones
        recipes.add(crushingRecipe("smooth_stone_crushing",Blocks.SMOOTH_STONE.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("polished_red_granite_crushing",Blocks.POLISHED_GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("polished_quartz_diorite_crushing",Blocks.POLISHED_DIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("polished_gray_andesite_crushing",Blocks.POLISHED_ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("polished_basalt_crushing",Blocks.POLISHED_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("polished_blackstone_crushing",Blocks.POLISHED_BLACKSTONE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("smooth_granite_crushing",ModBlocks.POLISHED_RED_GRANITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("smooth_diorite_crushing",ModBlocks.POLISHED_GRANODIORITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("smooth_andesite_crushing",ModBlocks.POLISHED_HORNBLENDE_ANDESITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("smooth_limestone_crushing",ModBlocks.POLISHED_LIMESTONE.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("smooth_shale_crushing",ModBlocks.POLISHED_SHALE.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("smooth_anorthosite_crushing",ModBlocks.POLISHED_ANORTHOSITE.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("smooth_ironstone_crushing",ModBlocks.POLISHED_IRONSTONE.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("smooth_tholeiitic_basalt_crushing",ModBlocks.POLISHED_THOLEIITIC_BASALT.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("smooth_rhyolite_crushing",ModBlocks.POLISHED_RHYOLITE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_marble_crushing",ModBlocks.POLISHED_MARBLE.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(Items.QUARTZ,1),0.1f));
        recipes.add(crushingRecipe("smooth_gneiss_crushing",ModBlocks.POLISHED_GNEISS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.2f));
        recipes.add(crushingRecipe("smooth_peridotite_crushing",ModBlocks.POLISHED_PERIDOTITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.OLIVINE,1),0.1f));
        recipes.add(crushingRecipe("smooth_komatiite_crushing",ModBlocks.POLISHED_KOMATIITE.asItem(), new ItemStack(ModItems.PYROXENE,2), new ItemStack(ModItems.MAGNESIA,1),0.1f));
        recipes.add(crushingRecipe("smooth_ringwoodite_crushing",ModBlocks.POLISHED_RINGWOODITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("smooth_wadsleyite_crushing",ModBlocks.POLISHED_WADSLEYITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("smooth_bridgmanite_crushing",ModBlocks.POLISHED_BRIDGMANITE.asItem(), new ItemStack(ModItems.MAGNESIA,1), new ItemStack(ModItems.CALCIUM_SILICATE,1),0.2f));
        recipes.add(crushingRecipe("smooth_kimberlite_crushing",ModBlocks.POLISHED_KIMBERLITE.asItem(), new ItemStack(ModItems.OLIVINE,1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("smooth_ferropericlase_crushing",ModBlocks.POLISHED_FERROPERICLASE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.MAGNESIA,1),0.5f));
        recipes.add(crushingRecipe("smooth_perovskite_crushing",ModBlocks.POLISHED_PEROVSKITE.asItem(), new ItemStack(ModItems.CALCIUM_SILICATE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("smooth_pumice_crushing",ModBlocks.POLISHED_PUMICE.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));
        recipes.add(crushingRecipe("smooth_scoria_crushing",ModBlocks.POLISHED_SCORIA.asItem(),new ItemStack(ModItems.POZZOLAN, 2), new ItemStack(ModItems.FELDSPAR), 0.1f));

        //bricks
        recipes.add(crushingRecipe("stone_bricks_crushing",Blocks.STONE_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("red_granite_bricks_crushing",ModBlocks.GRANITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("quartz_diorite_bricks_crushing",ModBlocks.DIORITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gray_andesite_bricks_crushing",ModBlocks.ANDESITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("polished_blackstone_bricks_crushing",Blocks.POLISHED_BLACKSTONE_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
        recipes.add(crushingRecipe("granite_bricks_crushing",ModBlocks.RED_GRANITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("diorite_bricks_crushing",ModBlocks.GRANODIORITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.PYROXENE), 0.1f));
        recipes.add(crushingRecipe("andesite_bricks_crushing",ModBlocks.HORNBLENDE_ANDESITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.ZIRCON), 0.05f));
        recipes.add(crushingRecipe("limestone_bricks_crushing",ModBlocks.LIMESTONE_BRICKS.asItem(),new ItemStack(ModItems.CALCITE, 1), new ItemStack(ModItems.DOLOMITE), 0.1f));
        recipes.add(crushingRecipe("shale_bricks_crushing",ModBlocks.SHALE_BRICKS.asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_bricks_crushing",ModBlocks.ANORTHOSITE_BRICKS.asItem(),new ItemStack(ModItems.FELDSPAR, 3), new ItemStack(ModItems.OLIVINE), 0.1f));
        recipes.add(crushingRecipe("ironstone_bricks_crushing",ModBlocks.IRONSTONE_BRICKS.asItem(),new ItemStack(ModItems.MAGNETITE),new ItemStack(ModItems.TIGER_IRON), 0.1f));
        recipes.add(crushingRecipe("tholeiitic_basalt_bricks_crushing",ModBlocks.THOLEIITIC_BASALT_BRICKS.asItem(), new ItemStack(ModItems.FELDSPAR,3), new ItemStack(ModItems.MAGNETITE,1),0.05f));
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
        recipes.add(crushingRecipe("phosphorite_crushing",ModBlocks.PHOSPHORITE.asItem(),new ItemStack(ModItems.PHOSPHORUS, 1), new ItemStack(ModItems.PHOSPHORUS), 0.1f));

        //Mod Ores
        recipes.add(crushingRecipe("magnetite_ore_crushing",ModBlocks.MAGNETITE_ORE.asItem(), new ItemStack(ModItems.MAGNETITE,1), new ItemStack(ModItems.CHROMITE,1),0.1f));
        recipes.add(crushingRecipe("malachite_ore_crushing",ModBlocks.MALACHITE_ORE.asItem(), new ItemStack(ModItems.MALACHITE,1), new ItemStack(ModItems.AZURITE,1),0.1f));
        recipes.add(crushingRecipe("bauxite_ore_crushing",ModBlocks.BAUXITE_ORE.asItem(), new ItemStack(ModItems.ALUMINA,1), new ItemStack(ModItems.GALLIUM_NUGGET,1),0.05f));
        recipes.add(crushingRecipe("cassiterite_ore_crushing",ModBlocks.CASSITERITE_ORE.asItem(), new ItemStack(ModItems.CASSITERITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("sphalerite_ore_crushing",ModBlocks.SPHALERITE_ORE.asItem(), new ItemStack(ModItems.SPHALERITE,1), new ItemStack(ModItems.OPAL,1),0.05f));
        recipes.add(crushingRecipe("cinnabarite_ore_crushing",ModBlocks.CINNABAR_ORE.asItem(), new ItemStack(Items.REDSTONE,4), new ItemStack(ModItems.SULFUR,1),0.25f));
        recipes.add(crushingRecipe("pentlandite_ore_crushing",ModBlocks.PENTLANDITE_ORE.asItem(), new ItemStack(ModItems.PENTLANDITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("interpinifex_ore_crushing",ModBlocks.INTERSPINIFEX_ORE.asItem(), new ItemStack(ModItems.PENTLANDITE,1), new ItemStack(ModItems.CHALCOPYRITE,1),0.1f));
        recipes.add(crushingRecipe("magnesite_ore_crushing",ModBlocks.MAGNESITE_ORE.asItem(), new ItemStack(ModItems.MAGNESITE,1), new ItemStack(ModItems.COBALTITE,1),0.05f));
        recipes.add(crushingRecipe("galena_ore_crushing",ModBlocks.GALENA_ORE.asItem(), new ItemStack(ModItems.GALENA,1), new ItemStack(ModItems.SPHALERITE,1),0.1f));
        recipes.add(crushingRecipe("acanthite_ore_crushing",ModBlocks.ACANTHITE_ORE.asItem(), new ItemStack(ModItems.ACANTHITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("pyrolusite_ore_crushing",ModBlocks.PYROLUSITE_ORE.asItem(), new ItemStack(ModItems.PYROLUSITE,1), new ItemStack(ModItems.TANTALITE,1),0.05f));
        recipes.add(crushingRecipe("bismite_ore_crushing",ModBlocks.BISMUTHINITE_ORE.asItem(), new ItemStack(ModItems.BISMUTHINITE,1), new ItemStack(ModItems.WOLFRAMITE,1),0.05f));
        recipes.add(crushingRecipe("vanadinite_ore_crushing",ModBlocks.VANADINITE_ORE.asItem(), new ItemStack(ModItems.VANADINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("ilmenite_ore_crushing",ModBlocks.ILMENITE_ORE.asItem(), new ItemStack(ModItems.TITANIA,1), new ItemStack(ModItems.MALACHITE,1),0.1f));
        recipes.add(crushingRecipe("molybdenite_ore_crushing",ModBlocks.MOLYBDENITE_ORE.asItem(), new ItemStack(ModItems.MOLYBDENITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing",ModBlocks.CHROMITE_ORE.asItem(), new ItemStack(ModItems.CHROMITE,1), new ItemStack(ModItems.MAGNETITE,1),0.1f));
        recipes.add(crushingRecipe("celestine_ore_crushing",ModBlocks.CELESTINE_ORE.asItem(), new ItemStack(ModItems.CELESTINE,1), new ItemStack(ModItems.SALT,1),0.1f));
        recipes.add(crushingRecipe("columbite_ore_crushing",ModBlocks.COLUMBITE_ORE.asItem(), new ItemStack(ModItems.COLUMBITE,1), new ItemStack(ModItems.TANTALITE,1),0.1f));
        recipes.add(crushingRecipe("tantalite_ore_crushing",ModBlocks.TANTALITE_ORE.asItem(), new ItemStack(ModItems.TANTALITE,1), new ItemStack(ModItems.COLUMBITE,1),0.1f));
        recipes.add(crushingRecipe("wolframite_ore_crushing",ModBlocks.WOLFRAMITE_ORE.asItem(), new ItemStack(ModItems.WOLFRAMITE,1), new ItemStack(ModItems.CASSITERITE,1),0.2f));
        recipes.add(crushingRecipe("greenokite_ore_crushing",ModBlocks.GREENOCKITE_ORE.asItem(), new ItemStack(ModItems.GREENOCKITE,1), new ItemStack(ModItems.GALENA,1),0.2f));
        recipes.add(crushingRecipe("uraninite_ore_crushing",ModBlocks.URANINITE_ORE.asItem(), new ItemStack(ModItems.URANINITE,1), new ItemStack(ModItems.GALENA,1),0.1f));
        recipes.add(crushingRecipe("stibnite_ore_crushing",ModBlocks.STIBNITE_ORE.asItem(), new ItemStack(ModItems.STIBNITE,1), new ItemStack(ModItems.BARITE,1),0.1f));
        recipes.add(crushingRecipe("xenotime_ore_crushing",ModBlocks.XENOTIME_ORE.asItem(), new ItemStack(ModItems.XENOTIME,1), new ItemStack(ModItems.CALCITE,1),0.1f));
        recipes.add(crushingRecipe("lignite_ore_crushing",ModBlocks.LIGNITE_ORE.asItem(), new ItemStack(Items.COAL,1), new ItemStack(ModItems.GRAPHITE,1),0.01f));
        recipes.add(crushingRecipe("sub_bituminous_coal_ore_crushing",ModBlocks.SUBBITUMINOUS_ORE.asItem(), new ItemStack(Items.COAL,1), new ItemStack(ModItems.GRAPHITE,1),0.02f));
        recipes.add(crushingRecipe("bituminous_coal_ore_crushing",ModBlocks.BITUMINOUS_ORE.asItem(), new ItemStack(Items.COAL,2), new ItemStack(ModItems.GRAPHITE,1),0.05f));
        recipes.add(crushingRecipe("anthracite_ore_crushing",ModBlocks.ANTHRACITE_ORE.asItem(), new ItemStack(Items.COAL,3), new ItemStack(ModItems.GRAPHITE,1),0.1f));
        recipes.add(crushingRecipe("lignite_crushing",ModItems.LIGNITE, new ItemStack(Items.COAL,1), new ItemStack(ModItems.SULFUR,1),0.5f));
        recipes.add(crushingRecipe("sub_bituminous_coal_crushing",ModItems.SUBBITUMINOUS_COAL, new ItemStack(Items.COAL,1), new ItemStack(ModItems.SULFUR,1),0.4f));
        recipes.add(crushingRecipe("bituminous_coal_crushing",ModItems.BITUMINOUS_COAL, new ItemStack(Items.COAL,1), new ItemStack(ModItems.SULFUR,1),0.25f));
        recipes.add(crushingRecipe("anthracite_crushing",ModItems.ANTHRACITE_COAL, new ItemStack(Items.COAL,2), new ItemStack(ModItems.SULFUR,1),0.1f));
        recipes.add(crushingRecipe("sperrylite_ore_crushing",ModBlocks.SPERRYLITE_ORE.asItem(), new ItemStack(ModItems.PLATINUM_ARSENIDE,1), new ItemStack(ModItems.OSMIRIDIUM_ALLOY,1),1f));
        recipes.add(crushingRecipe("meteorite_crushing",ModBlocks.METEORITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("kamacite_crushing",ModBlocks.KAMACITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("antitaenite_crushing",ModBlocks.ANTITAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("taenite_crushing",ModBlocks.TAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("tetrataenite_crushing",ModBlocks.TETRATAENITE.asItem(), new ItemStack(ModItems.SILICON,1), new ItemStack(ModItems.SULFUR,1),0.75f));
        recipes.add(crushingRecipe("bloom_iron_crushing",ModItems.BLOOM_IRON.asItem(), new ItemStack(ModItems.WROUGHT_IRON_INGOT,1), new ItemStack(ModItems.SLAG,1),0.5f));
        recipes.add(crushingRecipe("cobalite_ore_crushing",ModBlocks.COBALTITE_ORE.asItem(), new ItemStack(ModItems.COBALTITE,1), new ItemStack(ModItems.COBALTITE,1),0.1f));
        recipes.add(crushingRecipe("petalite_ore_crushing",ModBlocks.PETALITE_ORE.asItem(), new ItemStack(ModItems.PETALITE,1), new ItemStack(ModItems.TOURMALINE,1),0.1f));
        recipes.add(crushingRecipe("chalcopyrite_crushing",ModItems.CHALCOPYRITE, new ItemStack(ModItems.COPPER_NUGGET,5), new ItemStack(Items.IRON_NUGGET,5),1.0f));


        recipes.addAll(groupCrushingRecipe("leaves_crushing","minecraft:leaves",new ItemStack(ModItems.BIOMASS, 1),new ItemStack(ModItems.COMPOST,1), 1.0f));
        recipes.addAll(groupCrushingRecipe("crops_crushing","forge:crops",new ItemStack(ModItems.BIOMASS,3),new ItemStack(ModItems.COMPOST,1), 0.5f));
        recipes.addAll(groupCrushingRecipe("saplings_crushing","minecraft:saplings",new ItemStack(ModItems.BIOMASS,2),new ItemStack(ModItems.COMPOST,1), 0.3f));


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
