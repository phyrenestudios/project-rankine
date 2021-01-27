package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.advancements.ExactCompositionPredicate;
import com.cannolicatfish.rankine.advancements.HarvestLevelPredicate;
import com.cannolicatfish.rankine.advancements.IncludesCompositionPredicate;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.pendants.*;
import com.cannolicatfish.rankine.items.tools.GoldPanItem;
import com.cannolicatfish.rankine.potion.ModPotions;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.util.stream.Stream;

public class RankineRecipes {

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromItems(() -> RankineItems.MERCURY_INGOT.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.MERCURY_POISON));
    }

    public static void registerPredicates() {
        ItemPredicate.register(new ResourceLocation("rankine","harvest_level_check"), HarvestLevelPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","exact_composition"), ExactCompositionPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","includes_composition"), IncludesCompositionPredicate::new);
    }

    public static List<IAlloyRecipe> getAlloyRecipes()
    {
        List<IAlloyRecipe> recipes = new ArrayList<>();
        recipes.add(alloyRecipe("bronze_alloy",new ItemStack(RankineItems.BRONZE_ALLOY.get()),Arrays.asList(returnTagFamily("copper"),returnTagFamily("tin"),
                returnTagFamily("aluminum"),returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),
                returnTagFamily("iron"),returnTagFamily("bismuth"),returnTagFamily("lead"),returnTagFamily("antimony"),returnTagFamily("silicon"),
                returnTagFamily("phosphorus")),new AbstractMap.SimpleEntry<>(.8f,.9f),new AbstractMap.SimpleEntry<>(.1f,.2f),
                new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("pewter_alloy",new ItemStack(RankineItems.PEWTER_ALLOY.get()),Arrays.asList(returnTagFamily("tin"),returnTagFamily("antimony","lead"),
                returnTagFamily("copper"),returnTagFamily("bismuth"),returnTagFamily("silver")),
                new AbstractMap.SimpleEntry<>(.85f,.98f), new AbstractMap.SimpleEntry<>(.02f,.1f),new AbstractMap.SimpleEntry<>(0f,.13f),.87f));

        recipes.add(alloyRecipe("aluminum_bronze_alloy",new ItemStack(RankineItems.ALUMINUM_BRONZE_ALLOY.get()),Arrays.asList(returnTagFamily("copper"),returnTagFamily("aluminum"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("zinc"),returnTagFamily("arsenic"),returnTagFamily("iron"),returnTagFamily("lead")),
                new AbstractMap.SimpleEntry<>(.74f,.93f), new AbstractMap.SimpleEntry<>(.04f,.12f),new AbstractMap.SimpleEntry<>(0f,.15f),.85f));

        recipes.add(alloyRecipe("brass_alloy",new ItemStack(RankineItems.BRASS_ALLOY.get()),Arrays.asList(returnTagFamily("copper"),returnTagFamily("zinc"),
                returnTagFamily("tin"),returnTagFamily("lead"),returnTagFamily("aluminum"),returnTagFamily("nickel"),returnTagFamily("iron"),
                returnTagFamily("selenium")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.15f,.6f),new AbstractMap.SimpleEntry<>(0f,.1f),.9f));

        recipes.add(alloyRecipe("cupronickel_alloy",new ItemStack(RankineItems.CUPRONICKEL_ALLOY.get()),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("iron"),returnTagFamily("manganese"),returnTagFamily("tin"),returnTagFamily("niobium"),returnTagFamily("tantalum"),returnTagFamily("lead"), returnTagFamily("titanium"),
                returnTagFamily("chromium"),returnTagFamily("aluminum"), returnTagFamily("beryllium"),returnTagFamily("silicon"),returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.7f,.9f), new AbstractMap.SimpleEntry<>(.1f,.3f),new AbstractMap.SimpleEntry<>(0f,.05f),.95f));

        recipes.add(alloyRecipe("sterling_silver_alloy",new ItemStack(RankineItems.STERLING_SILVER_ALLOY.get()),Arrays.asList(returnTagFamily("silver"),returnTagFamily("copper"),
                returnTagFamily("zinc"),returnTagFamily("platinum"),returnTagFamily("boron"),returnTagFamily("germanium"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.92f,.96f), new AbstractMap.SimpleEntry<>(.01f,.08f),new AbstractMap.SimpleEntry<>(0f,.07f),0.93f));

        recipes.add(alloyRecipe("nickel_silver_alloy",new ItemStack(RankineItems.NICKEL_SILVER_ALLOY.get()),Arrays.asList(returnTagFamily("copper"),returnTagFamily("nickel"),
                returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.5f,.7f), new AbstractMap.SimpleEntry<>(.15f,.25f),new AbstractMap.SimpleEntry<>(.15f,.25f),1f));

        recipes.add(alloyRecipe("invar_alloy",new ItemStack(RankineItems.INVAR_ALLOY.get()),Arrays.asList(returnTagFamily("iron"),returnTagFamily("nickel"),
                returnTagFamily("cobalt"),returnTagFamily("chromium"),returnTagFamily("manganese"),returnTagFamily("palladium")), new AbstractMap.SimpleEntry<>(.5f,.9f), new AbstractMap.SimpleEntry<>(.1f,.5f),new AbstractMap.SimpleEntry<>(0f,.05f),0.95f));

        recipes.add(alloyRecipe("cast_iron_alloy",new ItemStack(RankineItems.CAST_IRON_ALLOY.get()),Arrays.asList(returnTagFamily("iron"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("manganese"),returnTagFamily("nickel"),returnTagFamily("chromium"),returnTagFamily("molybdenum"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("silicon"),returnTagFamily("phosphorus")),
                new AbstractMap.SimpleEntry<>(.86f,.98f), new AbstractMap.SimpleEntry<>(.02f,.04f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("rose_gold_alloy",new ItemStack(RankineItems.ROSE_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("copper"),
                returnTagFamily("silver"), returnTagFamily("zinc")), new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.06f),.94f));

        recipes.add(alloyRecipe("white_gold_alloy",new ItemStack(RankineItems.WHITE_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("zinc"),
                returnTagFamily("nickel"),returnTagFamily("palladium"),returnTagFamily("silver"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.74f,.9f), new AbstractMap.SimpleEntry<>(.05f,.1f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("green_gold_alloy",new ItemStack(RankineItems.GREEN_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("silver"),
                returnTagFamily("copper"),returnTagFamily("cadmium"),returnTagFamily("platinum")),
                new AbstractMap.SimpleEntry<>(.3f,.7f), new AbstractMap.SimpleEntry<>(.3f,.7f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy",new ItemStack(RankineItems.BLUE_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("iron"),
                returnTagFamily("nickel"),returnTagFamily("rhodium"),returnTagFamily("ruthenium")),
                new AbstractMap.SimpleEntry<>(.74f,.76f), new AbstractMap.SimpleEntry<>(.2f,.25f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        recipes.add(alloyRecipe("blue_gold_alloy_alt",new ItemStack(RankineItems.BLUE_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("gallium","indium")),
                new AbstractMap.SimpleEntry<>(.46f,.60f), new AbstractMap.SimpleEntry<>(.40f,.54f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("purple_gold_alloy",new ItemStack(RankineItems.PURPLE_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("aluminum")),
                new AbstractMap.SimpleEntry<>(.79f,.81f), new AbstractMap.SimpleEntry<>(.19f,.21f),new AbstractMap.SimpleEntry<>(0f,0f),1f));

        recipes.add(alloyRecipe("black_gold_alloy",new ItemStack(RankineItems.BLACK_GOLD_ALLOY.get()),Arrays.asList(returnTagFamily("gold","netherite"),returnTagFamily("cobalt"),
                returnTagFamily("chromium")), new AbstractMap.SimpleEntry<>(.75f,.80f), new AbstractMap.SimpleEntry<>(.15f,.20f),new AbstractMap.SimpleEntry<>(0f,0.1f),.9f));

        return recipes;
    }

    public static List<ITripleAlloyRecipe> getTripleAlloyRecipes() {
        List<ITripleAlloyRecipe> recipes = new ArrayList<>();

        recipes.add(tripleAlloyRecipe("steel_alloy", new ItemStack(RankineItems.STEEL_ALLOY.get()), Arrays.asList(returnTagFamily("iron"),
                returnTagFamily("manganese"),returnTagFamily("carbon","coke","graphite"),
                returnTagFamily("chromium"),returnTagFamily("cobalt"),
                returnTagFamily("molybdenum"),returnTagFamily("nickel"),
                returnTagFamily("niobium"),returnTagFamily("titanium"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"),
                returnTagFamily("copper"),returnTagFamily("calcium"),
                returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.97f,.98f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(.01f, .02f), new AbstractMap.SimpleEntry<>(0f, .01f),.99f));

        recipes.add(tripleAlloyRecipe("stainless_steel_alloy", new ItemStack(RankineItems.STAINLESS_STEEL_ALLOY.get()), Arrays.asList(returnTagFamily("iron"),
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

        recipes.add(tripleAlloyRecipe("tungsten_heavy_alloy", new ItemStack(RankineItems.TUNGSTEN_HEAVY_ALLOY.get()), Arrays.asList(returnTagFamily("tungsten"),
                returnTagFamily("nickel"),returnTagFamily("iron"),returnTagFamily("copper"),
                returnTagFamily("cobalt"),returnTagFamily("chromium"),
                returnTagFamily("molybdenum"),returnTagFamily("aluminum"),
                returnTagFamily("boron"),returnTagFamily("titanium"),
                returnTagFamily("tantalum"),returnTagFamily("yttrium"),
                returnTagFamily("cerium"),returnTagFamily("lanthanum"),
                returnTagFamily("rhenium"), returnTagFamily("carbon","coke","graphite"), returnTagFamily("netherite"),
                returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.88f, .95f), new AbstractMap.SimpleEntry<>(.02f, .1f), new AbstractMap.SimpleEntry<>(.01f, .04f), new AbstractMap.SimpleEntry<>(0f, .09f),.91f));

        recipes.add(tripleAlloyRecipe("mischmetal_alloy", new ItemStack(RankineItems.MISCHMETAL_ALLOY.get()), Arrays.asList(returnTagFamily("cerium"),
                returnTagFamily("lanthanum"),returnTagFamily("neodymium"),
                returnTagFamily("praseodymium"),returnTagFamily("dysprosium"),
                returnTagFamily("europium"),returnTagFamily("gadolinium"),
                returnTagFamily("samarium"),returnTagFamily("terbium"),
                returnTagFamily("iron"), returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.5f, .55f), new AbstractMap.SimpleEntry<>(.2f, .25f), new AbstractMap.SimpleEntry<>(.15f, .2f), new AbstractMap.SimpleEntry<>(0f, .15f),.85f));

        recipes.add(tripleAlloyRecipe("ferrocerium_alloy", new ItemStack(RankineItems.FERROCERIUM_ALLOY.get()), Arrays.asList(returnTagFamily("cerium"),
                returnTagFamily("lanthanum"),returnTagFamily("iron"),
                returnTagFamily("praseodymium"),returnTagFamily("neodymium"),
                returnTagFamily("magnesium")),
                new AbstractMap.SimpleEntry<>(.4f, .5f), new AbstractMap.SimpleEntry<>(.23f, .25f), new AbstractMap.SimpleEntry<>(.19f, .21f), new AbstractMap.SimpleEntry<>(0f, .04f),.92f));

        recipes.add(tripleAlloyRecipe("duralumin_alloy", new ItemStack(RankineItems.DURALUMIN_ALLOY.get()), Arrays.asList(returnTagFamily("aluminum"),
                returnTagFamily("copper"),returnTagFamily("magnesium"),
                returnTagFamily("manganese"),returnTagFamily("iron"),
                returnTagFamily("zinc"),returnTagFamily("titanium"),
                returnTagFamily("calcium"),returnTagFamily("chromium"),
                 returnTagFamily( "silicon")),
                new AbstractMap.SimpleEntry<>(.91f, .95f), new AbstractMap.SimpleEntry<>(.03f, .05f), new AbstractMap.SimpleEntry<>(.01f, .03f), new AbstractMap.SimpleEntry<>(0f, .04f),.96f));

        recipes.add(tripleAlloyRecipe("magnesium_alloy", new ItemStack(RankineItems.MAGNESIUM_ALLOY.get()), Arrays.asList(returnTagFamily("magnesium"),
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

        recipes.add(tripleAlloyRecipe("rose_metal_alloy", new ItemStack(RankineItems.ROSE_METAL_ALLOY.get()), Arrays.asList(returnTagFamily("bismuth"),
                returnTagFamily("lead"),returnTagFamily("tin")),
                new AbstractMap.SimpleEntry<>(.3f, .5f), new AbstractMap.SimpleEntry<>(.18f, .4f), new AbstractMap.SimpleEntry<>(.1f, .25f), new AbstractMap.SimpleEntry<>(0f, 0f),1f));

        recipes.add(tripleAlloyRecipe("alnico_alloy", new ItemStack(RankineItems.ALNICO_ALLOY.get()), Arrays.asList(returnTagFamily("iron"),
                returnTagFamily("nickel"),returnTagFamily("cobalt"),returnTagFamily("aluminum"),returnTagFamily("copper","titanium","hafnium","niobium")),
                new AbstractMap.SimpleEntry<>(.3f, .5f), new AbstractMap.SimpleEntry<>(.13f, .26f), new AbstractMap.SimpleEntry<>(.05f, .35f), new AbstractMap.SimpleEntry<>(.08f, .12f),1f));

        recipes.add(tripleAlloyRecipe("galinstan_alloy", new ItemStack(RankineItems.GALINSTAN_ALLOY.get()), Arrays.asList(returnTagFamily("gallium"),
                returnTagFamily("indium"),returnTagFamily("tin"), returnTagFamily("antimony"), returnTagFamily("bismuth")),
                new AbstractMap.SimpleEntry<>(.68f, .8f), new AbstractMap.SimpleEntry<>(.12f, .22f), new AbstractMap.SimpleEntry<>(.08f, .1f), new AbstractMap.SimpleEntry<>(0f, .1f),.9f));

            recipes.add(tripleAlloyRecipe("nickel_superalloy_tial", new ItemStack(RankineItems.NICKEL_SUPERALLOY.get()), Arrays.asList(returnTagFamily("nickel"),
                returnTagFamily("titanium"),returnTagFamily("aluminum"),
                returnTagFamily("chromium"),returnTagFamily("molybdenum"),
                returnTagFamily("niobium"),returnTagFamily("tantalum"),
                returnTagFamily("cobalt"),returnTagFamily("manganese"),
                returnTagFamily("boron"),returnTagFamily("iron"), returnTagFamily("netherite"),
                returnTagFamily("vanadium"),returnTagFamily("zirconium"), returnTagFamily("carbon","coke","graphite"),returnTagFamily("tungsten"),
                returnTagFamily("ruthenium"),returnTagFamily("rhenium"), returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.6f, .8f), new AbstractMap.SimpleEntry<>(.01f, .1f), new AbstractMap.SimpleEntry<>(.01f, .1f), new AbstractMap.SimpleEntry<>(0f, .15f),.62f));

        recipes.add(tripleAlloyRecipe("nickel_superalloy_crfe", new ItemStack(RankineItems.NICKEL_SUPERALLOY.get()), Arrays.asList(returnTagFamily("nickel"),
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

        recipes.add(tripleAlloyRecipe("cobalt_superalloy", new ItemStack(RankineItems.COBALT_SUPERALLOY.get()), Arrays.asList(returnTagFamily("cobalt"),
                returnTagFamily("chromium"),returnTagFamily("nickel"),
                returnTagFamily("tantalum"),returnTagFamily("molybdenum"),
                returnTagFamily("tungsten"),returnTagFamily("titanium"),
                returnTagFamily("aluminum"),returnTagFamily("iridium"),
                returnTagFamily("iron"),returnTagFamily("aluminum"),
                returnTagFamily("carbon","coke","graphite"), returnTagFamily("netherite"),
                returnTagFamily("phosphorus"),returnTagFamily("silicon")),
                new AbstractMap.SimpleEntry<>(.6f, .8f), new AbstractMap.SimpleEntry<>(.18f, .3f), new AbstractMap.SimpleEntry<>(.02f, .12f), new AbstractMap.SimpleEntry<>(0f, .2f),.8f));

        recipes.add(tripleAlloyRecipe("amalgam_alloy",new ItemStack(RankineItems.AMALGAM_ALLOY.get()),Arrays.asList(returnTagFamily("mercury"),returnTagFamily("gold"),
                returnAmalgamStacks()), new AbstractMap.SimpleEntry<>(.25f,.8f), new AbstractMap.SimpleEntry<>(.25f,.5f),new AbstractMap.SimpleEntry<>(0f,0.5f),  new AbstractMap.SimpleEntry<>(0f, 0f),1f));

        return recipes;
    }

    public static ItemStack returnCrucibleOutput(List<Item> items) {
        if (items.stream().distinct().count() != items.size())
        {
            return ItemStack.EMPTY;
        }
        ITag<Item> ironTag = ItemTags.getCollection().get(new ResourceLocation("forge:ingots/iron"));
        List<Item> iron = ironTag != null ? ironTag.getAllElements() : Collections.singletonList(Items.IRON_INGOT);
        Stream<Item> irons = items.stream().filter(iron::contains);
        if (!Collections.disjoint(items,iron) && irons.count() == 1) {
            int ingCount = 1;
            ITag<Item> coalTag = ItemTags.getCollection().get(new ResourceLocation("minecraft:coals"));
            List<Item> coal = coalTag != null  ? coalTag.getAllElements() : Arrays.asList(RankineItems.ANTHRACITE_COAL.get(), RankineItems.SUBBITUMINOUS_COAL.get(), RankineItems.BITUMINOUS_COAL.get(), RankineItems.LIGNITE.get(), RankineItems.COKE.get(),Items.COAL,Items.CHARCOAL);
            ITag<Item> siliconTag = ItemTags.getCollection().get(new ResourceLocation("rankine:glass_inputs"));
            List<Item> silicon = siliconTag != null  ? siliconTag.getAllElements() : Arrays.asList(RankineBlocks.BLACK_SAND.get().asItem(), Items.SAND, Items.RED_SAND, Items.QUARTZ);
            ITag<Item> limestoneTag = ItemTags.getCollection().get(new ResourceLocation("rankine:limestone_fluxes"));
            List<Item> limestone = limestoneTag != null ? limestoneTag.getAllElements() : Arrays.asList(RankineBlocks.LIMESTONE.get().asItem(), RankineItems.QUICKLIME.get(), RankineItems.DOLOMITE.get());
            ITag<Item> saltpeterTag = ItemTags.getCollection().get(new ResourceLocation("forge:saltpeter"));
            List<Item> saltpeter = saltpeterTag != null ? saltpeterTag.getAllElements() : Collections.singletonList(RankineItems.SALTPETER.get());
            ITag<Item> phosphorusTag = ItemTags.getCollection().get(new ResourceLocation("forge:phosphorus"));
            List<Item> phosphorus = phosphorusTag != null ? phosphorusTag.getAllElements() : Collections.singletonList(RankineItems.PHOSPHORUS.get());
            ITag<Item> fluoriteTag = ItemTags.getCollection().get(new ResourceLocation("forge:fluorite"));
            List<Item> fluorite = fluoriteTag != null ? fluoriteTag.getAllElements() : Collections.singletonList(RankineItems.FLUORITE.get());
            ITag<Item> boraxTag = ItemTags.getCollection().get(new ResourceLocation("forge:borax"));
            List<Item> borax = boraxTag != null ? boraxTag.getAllElements() : Collections.singletonList(RankineItems.BORAX.get());
            int ironlvl = 99;
            int carbonlvl = 1;
            int siliconlvl = 0;
            int sulfurlvl = 0;
            int potassiumlvl = 0;
            int phosphoruslvl = 0;

            Stream<Item> coals = items.stream().filter(coal::contains);
            int count = (int) coals.count();
            coals = items.stream().filter(coal::contains);
            if (count == 1 && coals.findFirst().isPresent())
            {
                coals = items.stream().filter(coal::contains);
                Item recipeCoal = coals.findFirst().get();
                if (recipeCoal == RankineItems.COKE.get() || recipeCoal == Items.CHARCOAL) // 98Fe-2C
                {
                    ironlvl -= 1;
                    carbonlvl += 1;
                } else if (recipeCoal == RankineItems.ANTHRACITE_COAL.get()) // 97Fe-2C-1S
                {
                    sulfurlvl += 1;
                    carbonlvl += 1;
                    ironlvl -= 2;
                } else if (recipeCoal == RankineItems.BITUMINOUS_COAL.get()) // 96Fe-2C-2S
                {
                    sulfurlvl += 2;
                    carbonlvl += 1;
                    ironlvl -= 3;
                }  else if (recipeCoal == RankineItems.SUBBITUMINOUS_COAL.get() || recipeCoal == Items.COAL) // 96Fe-3S-1C
                {
                    sulfurlvl += 3;
                    ironlvl -= 3;
                }  else if (recipeCoal == RankineItems.LIGNITE.get()) // 95Fe-4S-1C
                {
                    sulfurlvl += 4;
                    ironlvl -= 4;
                } else {
                    sulfurlvl += 3;
                    ironlvl -= 3;
                }
                ingCount += 1;
            } else if (count > 1 || count == 0) {
                return ItemStack.EMPTY;
            }

            Stream<Item> sands = items.stream().filter(silicon::contains);
            count = (int) sands.count();
            sands = items.stream().filter(silicon::contains);
            if (count == 1 && sands.findFirst().isPresent()) {
                sands = items.stream().filter(silicon::contains);
                Item recipeSand = sands.findFirst().get();
                if (carbonlvl > 1 && recipeSand == RankineBlocks.BLACK_SAND.get().asItem())
                {
                    carbonlvl -= 1;
                    siliconlvl += 1;
                } else {
                    ironlvl -= 1;
                    siliconlvl += 1;
                }
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            Stream<Item> limestones = items.stream().filter(limestone::contains);
            count = (int) limestones.count();
            limestones = items.stream().filter(limestone::contains);
            if (count == 1 && limestones.findFirst().isPresent()) {
                if (sulfurlvl >= 1)
                {
                    ironlvl += sulfurlvl;
                    sulfurlvl = 0;
                }
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            Stream<Item> saltpeters = items.stream().filter(saltpeter::contains);
            count = (int) saltpeters.count();
            saltpeters = items.stream().filter(saltpeter::contains);
            if (count == 1 && saltpeters.findFirst().isPresent()) {
                ironlvl -= 1;
                potassiumlvl += 1;
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            Stream<Item> phosphor = items.stream().filter(phosphorus::contains);
            count = (int) phosphor.count();
            phosphor = items.stream().filter(phosphorus::contains);
            if (count == 1 && phosphor.findFirst().isPresent()) {
                ironlvl -= 2;
                phosphoruslvl += 2;
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            Stream<Item> fluorites = items.stream().filter(fluorite::contains);
            count = (int) fluorites.count();
            fluorites = items.stream().filter(fluorite::contains);
            if (count == 1 && fluorites.findFirst().isPresent()) {
                if (phosphoruslvl >= 1) {
                    ironlvl += phosphoruslvl;
                    phosphoruslvl = 0;
                }
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            Stream<Item> boraxes = items.stream().filter(borax::contains);
            count = (int) boraxes.count();
            boraxes = items.stream().filter(borax::contains);
            if (count == 1 && boraxes.findFirst().isPresent()) {
                ingCount += 1;
            } else if (count > 1) {
                return ItemStack.EMPTY;
            }

            if (ironlvl < 94 && carbonlvl > 1)
            {
                carbonlvl -= 1;
                ironlvl += 1;
            }

            if (ingCount == 4)
            {
                String alloyOutput = "";
                String ironOutput = ironlvl + "Fe";
                String carbonOutput = carbonlvl + "C";


                if (siliconlvl == 0 && sulfurlvl == 0 && phosphoruslvl == 0 && potassiumlvl == 0)
                {
                    alloyOutput = ironOutput + "-" + carbonOutput;
                } else {
                    List<Integer> percents = new ArrayList<>();
                    List<String> elements = new ArrayList<>();

                    percents.add(ironlvl);
                    elements.add("Fe");
                    percents.add(carbonlvl);
                    elements.add("C");
                    if (siliconlvl > 0)
                    {
                        percents.add(siliconlvl);
                        elements.add("Si");
                    }
                    if (sulfurlvl > 0)
                    {
                        percents.add(sulfurlvl);
                        elements.add("S");
                    }
                    if (potassiumlvl > 0)
                    {
                        percents.add(potassiumlvl);
                        elements.add("K");
                    }
                    if (phosphoruslvl > 0)
                    {
                        percents.add(phosphoruslvl);
                        elements.add("P");
                    }
                    alloyOutput = AlloyRecipeHelper.getInstance().getDirectComposition(percents,elements);
                }

                ItemStack steel = new ItemStack(RankineItems.STEEL_ALLOY.get());
                AlloyItem.addAlloy(steel, new AlloyData(alloyOutput));
                return steel;
            }
            return ItemStack.EMPTY;
        }

        if (items.contains(RankineItems.CINNABAR))
        {
            int ingCount = 1;
            int outputamt = 3;
            Item output = Items.REDSTONE;

            if (Collections.frequency(items, RankineItems.ARSENIC_INGOT) > 1 || Collections.frequency(items, RankineItems.COBALTITE) > 1 || Collections.frequency(items,Items.QUARTZ) > 1 || Collections.frequency(items, RankineItems.PHOSPHORUS) > 1)
            {
                return ItemStack.EMPTY;
            }
            if (items.contains(RankineItems.PHOSPHORUS)) {
                ingCount += 1;
                output = Items.GLOWSTONE;
            }

            if (items.contains(RankineItems.ARSENIC_INGOT)) {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(Items.QUARTZ)) {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.COBALTITE)) {
                ingCount += 1;
                outputamt += 2;
            }

            if (ingCount == 4) {
                return new ItemStack(output,outputamt);
            }
        }

        ITag<Item> siliconTag = ItemTags.getCollection().get(new ResourceLocation("rankine:glass_inputs"));
        List<Item> silicon = siliconTag != null  ? siliconTag.getAllElements() : Arrays.asList(RankineBlocks.BLACK_SAND.get().asItem(), Items.SAND, Items.RED_SAND, Items.QUARTZ);
        Stream<Item> silicons = items.stream().filter(silicon::contains);
        if (!Collections.disjoint(items,silicon) && silicons.count() == 1) {
            int ingCount = 1;
            int outputamt = 1;
            Item glassOut = Items.GLASS;

            silicons = items.stream().filter(silicon::contains);
            if (silicons.findFirst().isPresent())
            {
                silicons = items.stream().filter(silicon::contains);
                Item recipeSand = silicons.findFirst().get();
                if (recipeSand.getItem() != Items.QUARTZ)
                {
                    outputamt += 1;
                }
            }

            ITag<Item> dyeTag = ItemTags.getCollection().get(new ResourceLocation("forge:dyes"));
            List<Item> dye = dyeTag != null  ? dyeTag.getAllElements() : Arrays.asList(Items.WHITE_DYE,Items.ORANGE_DYE,Items.MAGENTA_DYE,Items.LIGHT_BLUE_DYE,Items.YELLOW_DYE,Items.LIME_DYE,
                    Items.PINK_DYE,Items.GRAY_DYE,Items.LIGHT_GRAY_DYE,Items.CYAN_DYE,Items.PURPLE_DYE,Items.BLUE_DYE,Items.BROWN_DYE,Items.GREEN_DYE,Items.RED_DYE, Items.BLACK_DYE);
            Stream<Item> dyes = items.stream().filter(dye::contains);
            if (items.contains(RankineItems.GALENA) && dyes.count() >= 1)
            {
                return ItemStack.EMPTY;
            }

            if (Collections.frequency(items, RankineItems.ALUMINA) > 1 || Collections.frequency(items, RankineItems.GALENA) > 1 || Collections.frequency(items, RankineItems.MAGNESIA) > 1 || Collections.frequency(items, RankineItems.QUICKLIME) > 1)
            {
                return ItemStack.EMPTY;
            }

            if (items.contains(RankineItems.GALENA))
            {
                ingCount += 1;
                glassOut = RankineBlocks.LEAD_GLASS.get().asItem();
                outputamt += 1;
            }

            dyes = items.stream().filter(dye::contains);
            if (dyes.findFirst().isPresent())
            {
                ingCount += 1;
                dyes = items.stream().filter(dye::contains);
                ResourceLocation color = dyes.findFirst().get().getRegistryName();
                if (color != null) {
                    String c = color.getPath().split("_")[0];
                    String rest = "_stained_glass";
                    String path = c + rest;
                    Item regout = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft",path));
                    if (regout != null && regout != Items.GLASS)
                    {
                        glassOut = regout;
                    }
                }
            }

            if (items.contains(RankineItems.ALUMINA))
            {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.MAGNESIA))
            {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.QUICKLIME))
            {
                ingCount += 1;
                outputamt += 2;
            }

            if (ingCount == 4) {
                return new ItemStack(glassOut,outputamt);
            }
        }

        return ItemStack.EMPTY;
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
            result = new ItemStack[]{new ItemStack(RankineItems.ELEMENT.get())};
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
        recipes.add(beehiveOvenRecipe("limestone_oven_cooking", RankineBlocks.LIMESTONE.get().asItem(),new ItemStack(RankineBlocks.QUICKLIME_BLOCK.get())));
        recipes.add(beehiveOvenRecipe("magnesite_oven_cooking", RankineBlocks.MAGNESITE_BLOCK.get().asItem(),new ItemStack(RankineBlocks.MAGNESIA_BLOCK.get())));
        recipes.add(beehiveOvenRecipe("subbituminous_oven_cooking", RankineBlocks.SUBBITUMINOUS_COAL_BLOCK.get().asItem(),new ItemStack(RankineBlocks.BITUMINOUS_COAL_BLOCK.get())));
        recipes.add(beehiveOvenRecipe("bituminous_oven_cooking", RankineBlocks.BITUMINOUS_COAL_BLOCK.get().asItem(),new ItemStack(RankineBlocks.COKE_BLOCK.get())));
        recipes.add(beehiveOvenRecipe("coal_oven_cooking", Blocks.COAL_BLOCK.asItem(),new ItemStack(RankineBlocks.BITUMINOUS_COAL_BLOCK.get())));
        recipes.add(beehiveOvenRecipe("bone_char_cooking", Blocks.BONE_BLOCK.asItem(),new ItemStack(RankineBlocks.BONE_CHAR_BLOCK.get())));

        return recipes;
    }

    public static List<ISluicingRecipe> getSluicingRecipes()
    {
        List<ISluicingRecipe> recipes = new ArrayList<>();
        recipes.add(sluicingRecipe("alluvium_sluicing", RankineBlocks.ALLUVIUM.get().asItem(), GoldPanItem.returnAlluviumCollection()));
        recipes.add(sluicingRecipe("black_sand_sluicing", RankineBlocks.BLACK_SAND.get().asItem(), GoldPanItem.returnBlackSandCollection()));
        return recipes;
    }

    public static List<IEvaporationRecipe> getEvaporationRecipes()
    {
        List<IEvaporationRecipe> recipes = new ArrayList<>();
        recipes.add(evaporationRecipe("groundwater_evaporation", RankineItems.BIOME_INDICATOR_GENERIC.get(), EvaporationTowerTile.returnGroundwaterCollection()));
        recipes.add(evaporationRecipe("ocean_evaporation", RankineItems.BIOME_INDICATOR_OCEAN.get(), EvaporationTowerTile.returnOceanCollection()));
        recipes.add(evaporationRecipe("river_evaporation", RankineItems.BIOME_INDICATOR_RIVER.get(), EvaporationTowerTile.returnRiverCollection()));
        return recipes;
    }

    public static List<IPistonCrusherRecipe> getCrushingRecipes()
    {
        List<IPistonCrusherRecipe> recipes = new ArrayList<>();

        //Stones
        recipes.add(crushingRecipe("cobblestone_crushing",Blocks.COBBLESTONE.asItem(), new ItemStack(Items.GRAVEL,1), new ItemStack(Items.QUARTZ,1),0.05f));
        recipes.add(crushingRecipe("stone_crushing",Blocks.STONE.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.FELDSPAR.get(),1),0.1f));
        recipes.add(crushingRecipe("granite_crushing",Blocks.GRANITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("diorite_crushing",Blocks.DIORITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("andesite_crushing",Blocks.ANDESITE.asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.ZIRCON.get()), 0.1f));
        recipes.add(crushingRecipe("basalt_crushing",Blocks.BASALT.asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.PYROXENE.get(),1),0.1f));
        recipes.add(crushingRecipe("sandstone_crushing",Blocks.SANDSTONE.asItem(), new ItemStack(Items.SAND,3), new ItemStack(RankineItems.FELDSPAR.get(),1),0.5f));
        recipes.add(crushingRecipe("red_sandstone_crushing",Blocks.RED_SANDSTONE.asItem(), new ItemStack(Items.RED_SAND,3), new ItemStack(RankineItems.FELDSPAR.get(),1),0.5f));
        recipes.add(crushingRecipe("blackstone_crushing",Blocks.BLACKSTONE.asItem(), new ItemStack(RankineItems.PYROXENE.get(),1), new ItemStack(Items.GOLD_NUGGET,1),0.08f));
        recipes.add(crushingRecipe("netherrack_crushing", Blocks.NETHERRACK.asItem(),new ItemStack(RankineItems.PYROXENE.get(), 1), new ItemStack(Items.GOLD_NUGGET), 0.04f));
        recipes.add(crushingRecipe("end_stone_crushing", Blocks.END_STONE.asItem(),new ItemStack(Items.COBBLESTONE, 3), new ItemStack(Items.ENDER_PEARL), 0.05f));
        recipes.add(crushingRecipe("obsidian_crushing",Blocks.OBSIDIAN.asItem(),new ItemStack(RankineItems.PERLITE.get(), 1), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("red_granite_crushing", RankineBlocks.RED_GRANITE.get().asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("granodiorite_crushing", RankineBlocks.GRANODIORITE.get().asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("hornblende_andesite_crushing", RankineBlocks.HORNBLENDE_ANDESITE.get().asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.ZIRCON.get()), 0.1f));
        recipes.add(crushingRecipe("limestone_crushing", RankineBlocks.LIMESTONE.get().asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.CALCITE.get()), 0.1f));
        recipes.add(crushingRecipe("shale_crushing", RankineBlocks.SHALE.get().asItem(), new ItemStack(Items.CLAY_BALL,2), new ItemStack(Items.SAND,1),0.5f));
        recipes.add(crushingRecipe("anorthosite_crushing", RankineBlocks.ANORTHOSITE.get().asItem(),new ItemStack(Items.COBBLESTONE, 1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("ironstone_crushing", RankineBlocks.IRONSTONE.get().asItem(),new ItemStack(Items.IRON_NUGGET, 2),new ItemStack(RankineItems.TIGER_IRON.get()), 0.05f));
        recipes.add(crushingRecipe("tholeiitic_basalt_crushing", RankineBlocks.THOLEIITIC_BASALT.get().asItem(), new ItemStack(RankineItems.FELDSPAR.get(),3), new ItemStack(RankineItems.OLIVINE.get(),1),0.05f));
        recipes.add(crushingRecipe("rhyolite_crushing", RankineBlocks.RHYOLITE.get().asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.FELDSPAR.get()),0.1f));
        recipes.add(crushingRecipe("marble_crushing", RankineBlocks.MARBLE.get().asItem(), new ItemStack(Items.COBBLESTONE,1), new ItemStack(Items.QUARTZ),0.1f));
        recipes.add(crushingRecipe("gneiss_crushing", RankineBlocks.GNEISS.get().asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("pumice_crushing", RankineBlocks.PUMICE.get().asItem(),new ItemStack(RankineItems.POZZOLAN.get(), 2), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("scoria_crushing", RankineBlocks.SCORIA.get().asItem(),new ItemStack(RankineItems.POZZOLAN.get(), 2), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("slate_crushing", RankineBlocks.SLATE.get().asItem(),new ItemStack(Items.CLAY_BALL, 2), new ItemStack(Items.QUARTZ), 0.1f));
        recipes.add(crushingRecipe("gabbro_crushing", RankineBlocks.GABBRO.get().asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.PYROXENE.get()), 0.1f));
        recipes.add(crushingRecipe("schist_crushing", RankineBlocks.SCHIST.get().asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.FELDSPAR.get()), 0.1f));
        recipes.add(crushingRecipe("breccia_crushing", RankineBlocks.BRECCIA.get().asItem(),new ItemStack(Items.COBBLESTONE,1), new ItemStack(RankineItems.DOLOMITE.get()), 0.1f));
        recipes.add(crushingRecipe("peridotite_crushing", RankineBlocks.PERIDOTITE.get().asItem(), new ItemStack(RankineItems.PYROXENE.get(),2), new ItemStack(RankineItems.OLIVINE.get(),1),0.1f));
        recipes.add(crushingRecipe("komatiite_crushing", RankineBlocks.KOMATIITE.get().asItem(), new ItemStack(RankineItems.PYROXENE.get(),2), new ItemStack(RankineItems.MAGNESIA.get(),1),0.1f));
        recipes.add(crushingRecipe("ringwoodite_crushing", RankineBlocks.RINGWOODITE.get().asItem(), new ItemStack(RankineItems.OLIVINE.get(),1), new ItemStack(RankineItems.MAGNESIA.get(),1),0.1f));
        recipes.add(crushingRecipe("wadsleyite_crushing", RankineBlocks.WADSLEYITE.get().asItem(), new ItemStack(RankineItems.MAGNESIA.get(),1), new ItemStack(Items.QUARTZ,1),0.5f));
        recipes.add(crushingRecipe("bridgmanite_crushing", RankineBlocks.BRIDGMANITE.get().asItem(), new ItemStack(RankineItems.MAGNESIA.get(),1), new ItemStack(RankineItems.CALCIUM_SILICATE.get(),1),0.2f));
        recipes.add(crushingRecipe("kimberlite_crushing", RankineBlocks.KIMBERLITE.get().asItem(), new ItemStack(RankineItems.OLIVINE.get(),1), new ItemStack(Items.DIAMOND,1),0.05f));
        recipes.add(crushingRecipe("ferropericlase_crushing", RankineBlocks.FERROPERICLASE.get().asItem(), new ItemStack(RankineItems.MAGNESIA.get(),1), new ItemStack(RankineItems.MAGNETITE.get(),1),0.1f));
        recipes.add(crushingRecipe("perovskite_crushing", RankineBlocks.PEROVSKITE.get().asItem(), new ItemStack(RankineItems.CALCIUM_SILICATE.get(),1), new ItemStack(RankineItems.MAGNETITE.get(),1),0.1f));
        recipes.add(crushingRecipe("andesitic_tuff_crushing", RankineBlocks.ANDESITIC_TUFF.get().asItem(),new ItemStack(RankineBlocks.HORNBLENDE_ANDESITE.get(), 1), new ItemStack(RankineItems.CALCITE.get()), 0.15f));
        recipes.add(crushingRecipe("tholeiitic_basaltic_tuff_crushing", RankineBlocks.THOLEIITIC_BASALTIC_TUFF.get().asItem(),new ItemStack(RankineBlocks.THOLEIITIC_BASALT.get(), 1), new ItemStack(Items.NAUTILUS_SHELL), 0.01f));
        recipes.add(crushingRecipe("rhyolitic_tuff_crushing", RankineBlocks.RHYOLITIC_TUFF.get().asItem(),new ItemStack(RankineBlocks.RHYOLITE.get(), 1), new ItemStack(RankineItems.OPAL.get()), 0.01f));
        recipes.add(crushingRecipe("phosphorite_crushing", RankineBlocks.PHOSPHORITE.get().asItem(),new ItemStack(RankineItems.PHOSPHORUS.get(), 1), new ItemStack(RankineItems.PHOSPHORUS.get()), 0.1f));

        //Mod Ores
        recipes.add(crushingRecipe("magnetite_ore_crushing", RankineBlocks.MAGNETITE_ORE.get().asItem(), new ItemStack(RankineItems.MAGNETITE.get(),1), new ItemStack(RankineItems.CHROMITE.get(),1),0.1f));
        recipes.add(crushingRecipe("malachite_ore_crushing", RankineBlocks.MALACHITE_ORE.get().asItem(), new ItemStack(RankineItems.MALACHITE.get(),1), new ItemStack(RankineItems.AZURITE.get(),1),0.1f));
        recipes.add(crushingRecipe("bauxite_ore_crushing", RankineBlocks.BAUXITE_ORE.get().asItem(), new ItemStack(RankineItems.ALUMINA.get(),1), new ItemStack(RankineItems.GALLIUM_NUGGET.get(),1),0.05f));
        recipes.add(crushingRecipe("cassiterite_ore_crushing", RankineBlocks.CASSITERITE_ORE.get().asItem(), new ItemStack(RankineItems.CASSITERITE.get(),1), new ItemStack(RankineItems.MAGNETITE.get(),1),0.1f));
        recipes.add(crushingRecipe("sphalerite_ore_crushing", RankineBlocks.SPHALERITE_ORE.get().asItem(), new ItemStack(RankineItems.SPHALERITE.get(),1), new ItemStack(RankineItems.GERMANIUM_NUGGET.get(),1),0.1f));
        recipes.add(crushingRecipe("pentlandite_ore_crushing", RankineBlocks.PENTLANDITE_ORE.get().asItem(), new ItemStack(RankineItems.PENTLANDITE.get(),1), new ItemStack(RankineItems.COBALTITE.get(),1),0.05f));
        recipes.add(crushingRecipe("interpinifex_ore_crushing", RankineBlocks.INTERSPINIFEX_ORE.get().asItem(), new ItemStack(RankineItems.PENTLANDITE.get(),1), new ItemStack(RankineItems.CHALCOPYRITE.get(),1),0.1f));
        recipes.add(crushingRecipe("magnesite_ore_crushing", RankineBlocks.MAGNESITE_ORE.get().asItem(), new ItemStack(RankineItems.MAGNESITE.get(),1), new ItemStack(RankineItems.COBALTITE.get(),1),0.05f));
        recipes.add(crushingRecipe("galena_ore_crushing", RankineBlocks.GALENA_ORE.get().asItem(), new ItemStack(RankineItems.GALENA.get(),1), new ItemStack(RankineItems.SPHALERITE.get(),1),0.1f));
        recipes.add(crushingRecipe("acanthite_ore_crushing", RankineBlocks.ACANTHITE_ORE.get().asItem(), new ItemStack(RankineItems.ACANTHITE.get(),1), new ItemStack(RankineItems.GALENA.get(),1),0.1f));
        recipes.add(crushingRecipe("pyrolusite_ore_crushing", RankineBlocks.PYROLUSITE_ORE.get().asItem(), new ItemStack(RankineItems.PYROLUSITE.get(),1), new ItemStack(RankineItems.TANTALITE.get(),1),0.05f));
        recipes.add(crushingRecipe("bismite_ore_crushing", RankineBlocks.BISMUTHINITE_ORE.get().asItem(), new ItemStack(RankineItems.BISMUTHINITE.get(),1), new ItemStack(RankineItems.WOLFRAMITE.get(),1),0.05f));
        recipes.add(crushingRecipe("vanadinite_ore_crushing", RankineBlocks.VANADINITE_ORE.get().asItem(), new ItemStack(RankineItems.VANADINITE.get(),1), new ItemStack(RankineItems.GALENA.get(),1),0.1f));
        recipes.add(crushingRecipe("ilmenite_ore_crushing", RankineBlocks.ILMENITE_ORE.get().asItem(), new ItemStack(RankineItems.TITANIA.get(),1), new ItemStack(RankineItems.MALACHITE.get(),1),0.1f));
        recipes.add(crushingRecipe("molybdenite_ore_crushing", RankineBlocks.MOLYBDENITE_ORE.get().asItem(), new ItemStack(RankineItems.MOLYBDENITE.get(),1), new ItemStack(RankineItems.RHENIUM_NUGGET.get(),1),0.1f));
        recipes.add(crushingRecipe("chromite_ore_crushing", RankineBlocks.CHROMITE_ORE.get().asItem(), new ItemStack(RankineItems.CHROMITE.get(),1), new ItemStack(RankineItems.MAGNETITE.get(),1),0.1f));
        recipes.add(crushingRecipe("celestine_ore_crushing", RankineBlocks.CELESTINE_ORE.get().asItem(), new ItemStack(RankineItems.CELESTINE.get(),1), new ItemStack(RankineItems.SALT.get(),1),0.1f));
        recipes.add(crushingRecipe("columbite_ore_crushing", RankineBlocks.COLUMBITE_ORE.get().asItem(), new ItemStack(RankineItems.COLUMBITE.get(),1), new ItemStack(RankineItems.TANTALITE.get(),1),0.1f));
        recipes.add(crushingRecipe("tantalite_ore_crushing", RankineBlocks.TANTALITE_ORE.get().asItem(), new ItemStack(RankineItems.TANTALITE.get(),1), new ItemStack(RankineItems.COLUMBITE.get(),1),0.1f));
        recipes.add(crushingRecipe("wolframite_ore_crushing", RankineBlocks.WOLFRAMITE_ORE.get().asItem(), new ItemStack(RankineItems.WOLFRAMITE.get(),1), new ItemStack(RankineItems.CASSITERITE.get(),1),0.1f));
        recipes.add(crushingRecipe("greenokite_ore_crushing", RankineBlocks.GREENOCKITE_ORE.get().asItem(), new ItemStack(RankineItems.GREENOCKITE.get(),1), new ItemStack(RankineItems.GALENA.get(),1),0.1f));
        recipes.add(crushingRecipe("uraninite_ore_crushing", RankineBlocks.URANINITE_ORE.get().asItem(), new ItemStack(RankineItems.URANINITE.get(),1), new ItemStack(RankineItems.GALENA.get(),1),0.1f));
        recipes.add(crushingRecipe("stibnite_ore_crushing", RankineBlocks.STIBNITE_ORE.get().asItem(), new ItemStack(RankineItems.STIBNITE.get(),1), new ItemStack(RankineItems.BARITE.get(),1),0.1f));
        recipes.add(crushingRecipe("xenotime_ore_crushing", RankineBlocks.XENOTIME_ORE.get().asItem(), new ItemStack(RankineItems.XENOTIME.get(),1), new ItemStack(RankineItems.CALCITE.get(),1),0.1f));
        recipes.add(crushingRecipe("lignite_crushing", RankineItems.LIGNITE.get(), new ItemStack(Items.COAL), new ItemStack(RankineItems.SULFUR.get(),1),0.5f));
        recipes.add(crushingRecipe("subbituminous_coal_crushing", RankineItems.SUBBITUMINOUS_COAL.get(), new ItemStack(Items.COAL), new ItemStack(RankineItems.SULFUR.get(),1),0.4f));
        recipes.add(crushingRecipe("bituminous_coal_crushing", RankineItems.BITUMINOUS_COAL.get(), new ItemStack(Items.COAL), new ItemStack(RankineItems.SULFUR.get(),1),0.25f));
        recipes.add(crushingRecipe("anthracite_crushing", RankineItems.ANTHRACITE_COAL.get(), new ItemStack(Items.COAL,2), new ItemStack(RankineItems.SULFUR.get(),1),0.1f));
        recipes.add(crushingRecipe("sperrylite_ore_crushing", RankineBlocks.SPERRYLITE_ORE.get().asItem(), new ItemStack(RankineItems.PLATINUM_ARSENIDE.get(),1), new ItemStack(RankineItems.OSMIRIDIUM_ALLOY.get(),1),1f));
        recipes.add(crushingRecipe("meteorite_crushing", RankineBlocks.METEORITE.get().asItem(), new ItemStack(RankineItems.SILICON.get(),2), new ItemStack(RankineItems.SULFUR.get(),1),0.75f));
        recipes.add(crushingRecipe("cobalite_ore_crushing", RankineBlocks.COBALTITE_ORE.get().asItem(), new ItemStack(RankineItems.COBALTITE.get(),1), new ItemStack(RankineItems.COBALTITE.get(),1),0.1f));
        recipes.add(crushingRecipe("petalite_ore_crushing", RankineBlocks.PETALITE_ORE.get().asItem(), new ItemStack(RankineItems.PETALITE.get(),1), new ItemStack(RankineItems.TOURMALINE.get(),1),0.1f));
        recipes.add(crushingRecipe("chalcopyrite_crushing", RankineItems.CHALCOPYRITE.get(), new ItemStack(RankineItems.COPPER_NUGGET.get(),5), new ItemStack(Items.IRON_NUGGET,5),1.0f));
        recipes.addAll(groupCrushingRecipe("leaves_crushing","minecraft:leaves",new ItemStack(RankineItems.BIOMASS.get(), 1),new ItemStack(RankineItems.COMPOST.get(),1), 1.0f));
        recipes.addAll(groupCrushingRecipe("crops_crushing","forge:crops",new ItemStack(RankineItems.BIOMASS.get(),3),new ItemStack(RankineItems.COMPOST.get(),1), 0.5f));
        recipes.addAll(groupCrushingRecipe("saplings_crushing","minecraft:saplings",new ItemStack(RankineItems.BIOMASS.get(),2),new ItemStack(RankineItems.COMPOST.get(),1), 0.3f));
        recipes.add(crushingRecipe("cinnabar_crushing", RankineItems.CINNABAR.get(), new ItemStack(Items.REDSTONE,3), new ItemStack(RankineItems.SULFUR.get(),1),0.1f));
        recipes.add(crushingRecipe("cinnabar_crushing", RankineBlocks.CINNABAR_BLOCK.get().asItem(), new ItemStack(Items.REDSTONE,27), new ItemStack(RankineItems.SULFUR.get(),1),0.9f));
        recipes.add(crushingRecipe("cryolite_ore_crushing", RankineBlocks.CRYOLITE_ORE.get().asItem(), new ItemStack(RankineItems.CRYOLITE.get(),1), new ItemStack(RankineItems.ALUMINA.get(),1),0.1f));
        recipes.add(crushingRecipe("pyrite_ore_crushing", RankineBlocks.PYRITE_ORE.get().asItem(), new ItemStack(RankineItems.PYRITE.get(),1), new ItemStack(Items.GOLD_NUGGET,1),1.0f));
        recipes.add(crushingRecipe("pyrite_crushing", RankineItems.PYRITE.get(), new ItemStack(Items.IRON_NUGGET,6), new ItemStack(Items.GOLD_NUGGET,1),1.0f));




        // Example of using tags for recipe (don""t use unless necessary, i.e. large list of blocks)
        // recipes.addAll(groupCrushingRecipe("andesite_crushing","rankine:andesite",new ItemStack(ModItems.FELDSPAR),new ItemStack(ModItems.PYROXENE), 0.2f));
        return recipes;
    }

    public static AbstractMap.SimpleEntry<ItemStack,ItemStack> getCrushingOutputs(ItemStack input)
    {
        List<IPistonCrusherRecipe> recipes = RankineRecipes.getCrushingRecipes();
        for (IPistonCrusherRecipe recipe: recipes)
        {
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getItem() == input.getItem())
            {
                return new AbstractMap.SimpleEntry<>(recipe.getRecipeOutput(), recipe.getSecondaryOutput());
            }
        }
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY,ItemStack.EMPTY);
    }


    public static ItemStack getBeehiveOutput(ItemStack input1)
    {
        List<IBeehiveOvenRecipe> recipes = RankineRecipes.getBeehiveOvenRecipes();
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

        List<IAlloyRecipe> recipes = RankineRecipes.getAlloyRecipes();
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

        List<ITripleAlloyRecipe> recipes = RankineRecipes.getTripleAlloyRecipes();
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
        List<IPistonCrusherRecipe> recipes = RankineRecipes.getCrushingRecipes();
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
