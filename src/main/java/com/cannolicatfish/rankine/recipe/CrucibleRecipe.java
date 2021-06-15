package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrucibleRecipe implements IRecipe<IInventory> {

    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final int total;
    private final String alloyComp;
    private final NonNullList<Integer> ingredientGroups;
    private final NonNullList<Boolean> required;
    private final NonNullList<Integer> countMod;
    private final NonNullList<Integer> cookMod;
    private final NonNullList<List<String>> shiftMod;
    private final ItemStack recipeOutput;
    private final ItemStack secondaryOutput;
    private final int color;
    private final ResourceLocation id;

    public static final CrucibleRecipe.Serializer SERIALIZER = new CrucibleRecipe.Serializer();

    public CrucibleRecipe(ResourceLocation idIn, int cookTimeIn, int totalIn, int colorIn, NonNullList<Ingredient> recipeItemsIn, NonNullList<Integer> ingredientGroupsIn, NonNullList<Boolean> requiredIn, NonNullList<Integer> countModIn,
                          NonNullList<Integer> cookModIn, NonNullList<List<String>> shiftsIn, ItemStack outputIn, String alloyCompIn, ItemStack secondaryOutputIn) {
        this.id = idIn;
        this.ingredientGroups = ingredientGroupsIn;
        this.cookTime = cookTimeIn;
        this.total = totalIn;
        this.color = colorIn;
        this.required = requiredIn;
        this.countMod = countModIn;
        this.cookMod = cookModIn;
        this.shiftMod = shiftsIn;
        this.recipeItems = recipeItemsIn;
        this.alloyComp = alloyCompIn;
        this.recipeOutput = outputIn;
        this.secondaryOutput = secondaryOutputIn;
    }

    public String getGroup() {
        return "";
    }

    public int getColor() {
        return this.color;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public List<Ingredient> getCondensedIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<ItemStack> stacks = new ArrayList<>();
            for (int s : this.getIndexList(i)) {
                stacks.addAll(Arrays.asList(this.getIngredients().get(s).getMatchingStacks()));
            }
            if (stacks.isEmpty()) {
                stacks.add(new ItemStack(RankineItems.ELEMENT.get()));
            }
            ingredients.add(Ingredient.fromStacks(stacks.toArray(new ItemStack[0])));
        }
        for (int s : this.getIndexList(-1)) {
            ingredients.add(this.getIngredients().get(s));
        }
        return ingredients;
    }

    public List<Integer> getIndexList(int val) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < this.getIngredients().size(); i++) {
            if (this.getIngredientGroups().get(i) == val || ((val < 0 || val > 3) && (this.getIngredientGroups().get(i) < 0 || this.getIngredientGroups().get(i) > 3))) {
                ret.add(i);
            }
        }
        return ret;
    }


    public ItemStack generateResult(IInventory inv) {
        ItemStack output = this.getPrimaryOutput().copy();
        List<Integer> groupsUsed = new ArrayList<>();
        List<List<String>> alloyCommands = new ArrayList<>();
        for (int s = 0; s < 4; s++) {
            ItemStack stack = inv.getStackInSlot(s);
            int workingIndex = -1;
            for (int i = 0; i < this.getIngredients().size(); i++) {
                if (this.getIngredients().get(i).test(stack)) {
                    workingIndex = i;
                    break;
                }
            }
            if (workingIndex == -1) {
                return ItemStack.EMPTY;
            }
            output.grow(this.getCountMod().get(workingIndex));
            groupsUsed.add(this.getIngredientGroups().get(workingIndex));
            if (this.getIngredientGroups().get(workingIndex) == -1 || Collections.frequency(groupsUsed,this.getIngredientGroups().get(workingIndex)) <= 1) {
                alloyCommands.add(this.getShiftMod().get(workingIndex));
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (alloyComp.isEmpty()) {
            return output;
        } else {
            AlloyItem.addAlloy(output,new AlloyData(returnAlloyDataMod(alloyCommands)));
            return output;
        }
    }

    public String returnAlloyDataMod(List<List<String>> alloyCommands) {
        List<String> elements = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (String s : this.getAlloyComp().split("-")) {
            elements.add(s.replaceAll("[\\d.-]", ""));
            nums.add(Integer.parseInt(s.replaceAll("[^\\d.-]", "")));
        }
        //System.out.println("PRE-TASK COMP:");
        //System.out.println(elements);
        //System.out.println(nums);
        for (List<String> strL : alloyCommands) {
            //System.out.println("TASK: " + strL);
            int cons = 0;
            for (String str : strL) {
                int negative = str.contains("-") ? -1 : 1;
                String numCheck = str.replaceAll("[^\\d.]", "");
                int num = 0;
                if (!numCheck.isEmpty()) {
                    num = Integer.parseInt(numCheck);
                }
                String strCheck = str.replaceAll("[\\d.-]", "");
                int workingIndex = elements.indexOf(strCheck);
                if (workingIndex != -1) {
                    if (num == 0) {
                        if (cons == 0) {
                            cons = nums.get(workingIndex);
                            nums.set(workingIndex, nums.get(workingIndex) + cons * negative);
                        } else {
                            nums.set(workingIndex, nums.get(workingIndex) + cons * negative);
                        }
                    } else {
                        nums.set(workingIndex,nums.get(workingIndex) + num * negative);
                    }

                } else {
                    if (num > 0) {
                        nums.add(num);
                        elements.add(strCheck);
                    } else {
                        break;
                    }

                }
            }
            //System.out.println("POST-TASK OUTCOME:");
            //System.out.println(elements);
            //System.out.println(nums);
        }
        //System.out.println("FINAL OUTCOME:");
        //System.out.println(elements);
        //System.out.println(nums);
        return AlloyRecipeHelper.getDirectComposition(nums,elements);
    }


    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return !generateResult(inv).isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        if (alloyComp.isEmpty()) {
            return this.recipeOutput.copy();
        } else {
            ItemStack stack = this.recipeOutput.copy();
            AlloyItem.addAlloy(stack,new AlloyData(alloyComp));
            return stack;
        }
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getRecipeCookTime(IInventory inv) {
        int cook = this.getCookTime();
        for (int s = 0; s < 4; s++) {
            ItemStack stack = inv.getStackInSlot(s);
            int workingIndex = -1;
            for (int i = 0; i < this.getIngredients().size(); i++) {
                if (this.getIngredients().get(i).test(stack)) {
                    workingIndex = i;
                    break;
                }
            }
            cook += this.getCookMod().get(workingIndex);
        }
        return cook;
    }

    public int getTotal() {
        return total;
    }

    public ItemStack getPrimaryOutput() {
        return this.recipeOutput.copy();
    }

    public ItemStack getSecondaryOutput() {
        return this.secondaryOutput.copy();
    }

    public NonNullList<Boolean> getRequired() {
        return required;
    }

    public NonNullList<Ingredient> getRecipeItems() {
        return recipeItems;
    }

    public NonNullList<Integer> getCountMod() {
        return countMod;
    }

    public NonNullList<Integer> getCookMod() {
        return cookMod;
    }


    public NonNullList<Integer> getIngredientGroups() {
        return ingredientGroups;
    }

    public NonNullList<List<String>> getShiftMod() {
        return shiftMod;
    }

    public String getAlloyComp() {
        return alloyComp;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true, false);
        }
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.CRUCIBLE;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<CrucibleRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "crucible");
        public CrucibleRecipe read(ResourceLocation recipeId, JsonObject json) {
            int c = json.get("cookTime").getAsInt();
            int t = json.get("total").getAsInt();
            int col = json.has("color") ? json.get("color").getAsInt() : 16777215;
            NonNullList<Boolean> requiredbool = NonNullList.withSize(4,false);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(t,Ingredient.EMPTY);
            NonNullList<Integer> groups = NonNullList.withSize(t,-1);
            NonNullList<Integer> countMods = NonNullList.withSize(t,0);
            NonNullList<Integer> cookMods = NonNullList.withSize(t,0);
            NonNullList<List<String>> shiftMods = NonNullList.withSize(t, Collections.emptyList());

            ItemStack itemstack = AlloyCraftingRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            String alloy = json.has("alloyData") ? json.get("alloyData").getAsString() : "";
            ItemStack secondary = AlloyCraftingRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondary"));

            JsonArray req = JSONUtils.getJsonArray(json,"required");
            for (int i = 0; i < req.size(); i++) {
                requiredbool.set(i,req.get(i).getAsBoolean());
            }
            for (int i = 0; i < t; i++) {
                String input = "input" + (i+1);
                if (json.has(input)) {
                    JsonObject object = JSONUtils.getJsonObject(json, input);
                    ingredients.set(i, AlloyIngredientHelper.deserialize(object,null));
                    if (object.has("group")) {
                        groups.set(i,object.get("group").getAsInt());
                    }
                    if (object.has("countMod")) {
                        countMods.set(i,object.get("countMod").getAsInt());
                    }
                    if (object.has("cookMod")) {
                        cookMods.set(i,object.get("cookMod").getAsInt());
                    }
                    if (object.has("shiftMod")) {
                        JsonArray smod = JSONUtils.getJsonArray(object,"shiftMod");
                        List<String> strL = new ArrayList<>();
                        for (int j = 0; j < smod.size(); j++) {
                            strL.add(j,smod.get(j).getAsString());
                        }
                        shiftMods.set(i,strL);
                    }
                }
            }
            return new CrucibleRecipe(recipeId,c,t,col,ingredients, groups, requiredbool, countMods, cookMods, shiftMods, itemstack, alloy, secondary);
        }

        public CrucibleRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int c = buffer.readInt();
            int t = buffer.readInt();
            int col = buffer.readInt();
            NonNullList<Boolean> req = NonNullList.withSize(4, false);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Integer> groups = NonNullList.withSize(t,-1);
            NonNullList<Integer> countMods = NonNullList.withSize(t,0);
            NonNullList<Integer> cookMods = NonNullList.withSize(t,0);
            NonNullList<List<String>> shiftMods = NonNullList.withSize(t, Collections.emptyList());

            for (int i = 0; i < 4; i++) {
                req.set(i,buffer.readBoolean());
            }

            for (int i = 0; i < t; i++) {
                ingredients.set(i,Ingredient.read(buffer));
                groups.set(i,buffer.readInt());
                countMods.set(i,buffer.readInt());
                cookMods.set(i,buffer.readInt());


                int s = buffer.readInt();
                String[] str = new String[s];
                if (s > 0) {
                    for (int j = 0; j < s; j++) {
                        str[j] = buffer.readString();
                    }
                    shiftMods.set(i,Arrays.asList(str));
                }


            }

            ItemStack stack = buffer.readItemStack();
            String all = buffer.readString();
            ItemStack secondary = buffer.readItemStack();

            return new CrucibleRecipe(recipeId,c,t,col,ingredients,groups,req,countMods,cookMods,shiftMods, stack, all, secondary);
        }

        public void write(PacketBuffer buffer, CrucibleRecipe recipe) {
            buffer.writeInt(recipe.getCookTime());
            buffer.writeInt(recipe.getTotal());
            buffer.writeInt(recipe.getColor());
            for (int i = 0; i < 4; i++) {
                if (i < recipe.getRequired().size()) {
                    buffer.writeBoolean(recipe.getRequired().get(i));
                } else {
                    buffer.writeBoolean(false);
                }
            }

            for (int i = 0; i < recipe.getTotal(); i++) {
                recipe.getIngredients().get(i).write(buffer);
                buffer.writeInt(recipe.getIngredientGroups().get(i));
                buffer.writeInt(recipe.getCountMod().get(i));
                buffer.writeInt(recipe.getCookMod().get(i));


                buffer.writeInt(recipe.getShiftMod().get(i).size());
                if (recipe.getShiftMod().get(i).size() > 0) {
                    for (int j=0;j<recipe.getShiftMod().get(i).size();j++) {
                        buffer.writeString(recipe.getShiftMod().get(i).get(j));
                    }
                }


            }

            buffer.writeItemStack(recipe.getPrimaryOutput());
            buffer.writeString(recipe.getAlloyComp());
            buffer.writeItemStack(recipe.getSecondaryOutput());

        }
    }

}
