package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.advancements.ExactCompositionPredicate;
import com.cannolicatfish.rankine.advancements.HarvestLevelPredicate;
import com.cannolicatfish.rankine.advancements.IncludesCompositionPredicate;
import com.cannolicatfish.rankine.advancements.PewterEnchantabilityPredicate;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.potion.RankinePotions;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Stream;

public class RankineRecipes {

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromItems(RankineItems.MERCURY_INGOT::get), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), RankinePotions.MERCURY_POISON));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromItems(RankineItems.SALT::get,RankineItems.PINK_SALT::get), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), RankinePotions.CONDUCTIVE_POTION));

    }

    public static void registerPredicates() {
        ItemPredicate.register(new ResourceLocation("rankine","harvest_level_check"), HarvestLevelPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","enchant_check"), PewterEnchantabilityPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","exact_composition"), ExactCompositionPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","includes_composition"), IncludesCompositionPredicate::new);
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
            List<Item> limestone = limestoneTag != null ? limestoneTag.getAllElements() : Arrays.asList(RankineBlocks.TUFA_LIMESTONE.get().asItem(), RankineItems.QUICKLIME.get(), RankineItems.DOLOMITE.get());
            ITag<Item> tronaTag = ItemTags.getCollection().get(new ResourceLocation("forge:trona"));
            List<Item> trona = tronaTag != null ? tronaTag.getAllElements() : Collections.singletonList(RankineItems.TRONA.get());
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

            Stream<Item> tronas = items.stream().filter(tronaTag::contains);
            count = (int) tronas.count();
            tronas = items.stream().filter(trona::contains);
            if (count == 1 && tronas.findFirst().isPresent()) {
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
                    alloyOutput = AlloyRecipeHelper.getDirectComposition(percents,elements);
                }

                ItemStack steel = new ItemStack(RankineItems.STEEL_ALLOY.get());
                AlloyItem.addAlloy(steel, new AlloyData(alloyOutput));
                return steel;
            }
            return ItemStack.EMPTY;
        }

        if (items.contains(RankineItems.CINNABAR.get()))
        {
            int ingCount = 1;
            int outputamt = 3;
            Item output = Items.REDSTONE;

            if (Collections.frequency(items, RankineItems.ARSENIC_INGOT) > 1 || Collections.frequency(items, RankineItems.COBALTITE) > 1 || Collections.frequency(items,Items.QUARTZ) > 1 || Collections.frequency(items, RankineItems.PHOSPHORUS) > 1)
            {
                return ItemStack.EMPTY;
            }
            if (items.contains(RankineItems.PHOSPHORUS.get())) {
                ingCount += 1;
                output = Items.GLOWSTONE_DUST;
            }

            if (items.contains(RankineItems.ARSENIC_INGOT.get())) {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(Items.QUARTZ)) {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.COBALTITE.get())) {
                ingCount += 1;
                outputamt += 2;
            }

            if (items.contains(RankineItems.ALUMINA.get())) {
                ingCount += 1;
                outputamt += 2;
            }

            if (items.contains(RankineItems.CRYOLITE.get())) {
                ingCount += 1;
                outputamt += 3;
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
            if (items.contains(RankineItems.GALENA.get()) && dyes.count() >= 1)
            {
                return ItemStack.EMPTY;
            }

            if (Collections.frequency(items, RankineItems.ALUMINA) > 1 || Collections.frequency(items, RankineItems.GALENA) > 1 || Collections.frequency(items, RankineItems.MAGNESIA) > 1 || Collections.frequency(items, RankineItems.QUICKLIME) > 1)
            {
                return ItemStack.EMPTY;
            }

            if (items.contains(RankineItems.GALENA.get()))
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

            if (items.contains(RankineItems.ALUMINA.get()))
            {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.MAGNESIA.get()))
            {
                ingCount += 1;
                outputamt += 1;
            }

            if (items.contains(RankineItems.QUICKLIME.get()))
            {
                ingCount += 1;
                outputamt += 2;
            }

            if (items.contains(RankineItems.PETALITE.get()))
            {
                ingCount += 1;
                outputamt += 3;
            }


            if (items.contains(RankineItems.SODIUM_CARBONATE.get()))
            {
                ingCount += 1;
                outputamt += 2;
            }


            if (items.contains(RankineItems.PLAGIOCLASE_FELDSPAR.get()) || items.contains(RankineItems.FELDSPAR.get()))
            {
                ingCount += 1;
                outputamt += 1;
            }


            if (ingCount == 4) {
                return new ItemStack(glassOut,outputamt);
            }
        }

        return ItemStack.EMPTY;
    }

    public static List<IEvaporationRecipe> getEvaporationRecipes()
    {
        List<IEvaporationRecipe> recipes = new ArrayList<>();
        recipes.add(evaporationRecipe("groundwater_evaporation", RankineItems.BIOME_INDICATOR_GENERIC.get(), EvaporationTowerTile.returnGroundwaterCollection()));
        recipes.add(evaporationRecipe("ocean_evaporation", RankineItems.BIOME_INDICATOR_OCEAN.get(), EvaporationTowerTile.returnOceanCollection()));
        recipes.add(evaporationRecipe("river_evaporation", RankineItems.BIOME_INDICATOR_RIVER.get(), EvaporationTowerTile.returnRiverCollection()));
        return recipes;
    }


    public static String generateAlloyString(IInventory inv) {
        List<PeriodicTableUtils.Element> currentElements = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                PeriodicTableUtils.Element element = utils.getElementFromItem(stack.getItem());
                if (!currentElements.contains(element)) {
                    currentElements.add(element);
                    currentMaterial.add(0);
                }
                int workingIndex = currentElements.indexOf(element);
                Item item = stack.getItem();
                ResourceLocation reg = item.getRegistryName();
                String registry = "";
                if (reg != null) {
                    registry = reg.getPath();
                }

                if (stack.getItem().getTags().contains(new ResourceLocation("forge:storage_blocks")) || stack.getItem() instanceof BlockItem || registry.contains("block")) {
                    currentMaterial.set(workingIndex, currentMaterial.get(workingIndex) + 81 * stack.getCount());
                } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || registry.contains("ingot")) {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")) || registry.contains("nugget")) {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + stack.getCount());
                } else if (stack.getItem() == Items.NETHERITE_SCRAP || registry.contains("scrap")){
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 2 * stack.getCount());
                } else {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                }
            }
        }

        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < currentElements.size(); j++) {
            PeriodicTableUtils.Element curEl = currentElements.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        return AlloyRecipeHelper.getDirectComposition(percents,symbols);
    }


    public static IEvaporationRecipe evaporationRecipe(String registry, Item input, WeightedCollection<ItemStack> col)
    {
        List<ItemStack> output = new ArrayList<>(col.getEntries());
        List<Float> weights = new ArrayList<>(col.getWeights());
        return new IEvaporationRecipe(new ResourceLocation(ProjectRankine.MODID,registry),output,
                Ingredient.fromStacks(new ItemStack(input)),weights);
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
            if (e != null && !enchantments.contains(e))
            {
                enchantments.add(e);
            }
        }
        List<Enchantment> en = alloy.getEnchantmentBonus(item);
        for (Enchantment e: en)
        {
            if (e != null && !enchantments.contains(e))
            {
                enchantments.add(e);
            }
        }
        return enchantments;
    }
}
