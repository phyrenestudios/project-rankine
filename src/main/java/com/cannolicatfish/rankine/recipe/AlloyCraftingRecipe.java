package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloySpecialItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Set;

public class AlloyCraftingRecipe implements CraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingContainer> {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    /**
     * Expand the max width and height allowed in the deserializer.
     * This should be called by modders who add custom crafting tables that are larger than the vanilla 3x3.
     * @param width your max recipe width
     * @param height your max recipe height
     */
    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }

    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;
    private final boolean inherit;
    private final String inheritRecipe;
    private final int color;
    private final String localName;

    public AlloyCraftingRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, boolean inherit,
                               String inheritRecipeIn, String nameIn, int colorIn) {
        this.id = idIn;
        this.group = groupIn;
        this.recipeWidth = recipeWidthIn;
        this.recipeHeight = recipeHeightIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
        this.inherit = inherit;
        this.inheritRecipe = inheritRecipeIn;
        this.color = colorIn;
        this.localName = nameIn;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ALLOY_CRAFTING_RECIPE_SERIALIZER.get();
    }

    /**
     * Recipes with equal group are combined into one button in the recipe book
     */
    public String getGroup() {
        return this.group;
    }

    public int getColor() {
        return this.color;
    }

    public String getInheritRecipe() {
        return inheritRecipe;
    }

    public String getLocalName() {
        if (this.localName.isEmpty()) {
            return "";
        } else if (!this.inheritRecipe.isEmpty()){
            ResourceLocation rs = new ResourceLocation(this.inheritRecipe);
            String[] s = rs.getPath().split("/");
            return "item." + rs.getNamespace() + "." + s[s.length-1];
        } else if (this.localName.equals("def")){
            String[] s = this.id.getPath().split("/");
            return "item." + this.id.getNamespace() + "." + s[s.length-1];
        } else {
            return this.localName;
        }

    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    public ItemStack getResultItem() {
        ItemStack out = this.recipeOutput.copy();
        if (this.getColor() != 16777215) {
            out.getOrCreateTag().putInt("color",this.getColor());
        }

        if (!this.getLocalName().isEmpty()) {
            out.getOrCreateTag().putString("nameOverride",this.getLocalName());
        }
        return out;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingContainer inv, Level worldIn) {
        for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
            for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(CraftingContainer craftingInventory, int width, int height, boolean p_77573_4_) {
        //String workingComposition = "";
        for(int i = 0; i < craftingInventory.getWidth(); ++i) {
            for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (p_77573_4_) {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                ItemStack stackInSlot = craftingInventory.getItem(i + j * craftingInventory.getWidth());
                if (!ingredient.test(stackInSlot)) {
                    return false;
                }
                if (ingredient.getItems().length > 0)
                {
                    if (!IAlloyItem.getAlloyComposition(ingredient.getItems()[0]).isEmpty() &&
                            !IAlloyItem.getAlloyComposition(ingredient.getItems()[0]).equals(IAlloyItem.getAlloyComposition(stackInSlot))) {
                        //System.out.println("Item " + stackInSlot + " does not match composition of " + ingredient.getMatchingStacks()[0]);
                        //System.out.println("ingredient comp: " + (IAlloyItem.getAlloyComposition(ingredient.getMatchingStacks()[0])));
                        //System.out.println("stackInSlot comp: " + (IAlloyItem.getAlloyComposition(stackInSlot)));
                        return false;
                    }
                    ResourceLocation rs = IAlloyItem.getAlloyRecipe(ingredient.getItems()[0]);
                    ResourceLocation inSlot = IAlloyItem.getAlloyRecipe(stackInSlot);
                    if (rs != null &&
                            (inSlot == null || !rs.toString().equals(inSlot.toString()))) {
                        //System.out.println("Item " + stackInSlot + " does not match alloy recipe of " + ingredient.getMatchingStacks()[0]);
                        //System.out.println("ingredient recipe: " + (IAlloyItem.getAlloyRecipe(ingredient.getMatchingStacks()[0])));
                        //System.out.println("stackInSlot recipe: " + (IAlloyItem.getAlloyRecipe(stackInSlot)));
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack res = this.getResultItem().copy();
        if (this.inherit)
        {
            String workingRecipe = "";
            if (!this.inheritRecipe.isEmpty()) {
                workingRecipe = this.inheritRecipe;
            }
            String workingComposition = "";
            for(int i = 0; i < inv.getWidth(); ++i) {
                for (int j = 0; j < inv.getHeight(); ++j) {

                    ItemStack stackInSlot = inv.getItem(i + j * inv.getWidth());
                    ResourceLocation rs = IAlloyItem.getAlloyRecipe(stackInSlot);
                    //System.out.println(stackInSlot + " has alloy recipe of " + rs);
                    if (!this.inheritRecipe.isEmpty()) {
                        if (rs != null && rs.toString().equals(this.inheritRecipe))
                        {
                            String comp = IAlloyItem.getAlloyComposition(stackInSlot);
                            if (workingComposition.isEmpty())
                            {
                                //System.out.println("Composition updated to " + comp);
                                workingComposition = comp;
                            }
                            else if (!comp.equals(workingComposition))
                            {
                                //System.out.println("Not all compositions match!");
                                return ItemStack.EMPTY;
                            }
                        }
                    } else if (rs != null) {

                        String comp = IAlloyItem.getAlloyComposition(stackInSlot);
                        String recipe = rs.toString();
                        if (workingComposition.isEmpty())
                        {
                            //System.out.println("Composition updated to " + comp);
                            workingComposition = comp;
                        }
                        else if (!comp.equals(workingComposition))
                        {
                            //System.out.println("Not all compositions match!");
                            return ItemStack.EMPTY;
                        }

                        if (workingRecipe.isEmpty()) {
                            //System.out.println("Recipe updated to " + recipe);
                            workingRecipe = recipe;
                        } else if (!recipe.equals(workingRecipe)) {
                            //System.out.println("Not all recipes match!");
                            return ItemStack.EMPTY;
                        }
                    }

                }
            }
            if (workingComposition.isEmpty()) {
                //System.out.println("No matching compositions for inheritRecipe " + workingRecipe);
                return ItemStack.EMPTY;
            } else {
                IAlloyItem.createDirectAlloyNBT(res,workingComposition,workingRecipe,null,res.getItem() instanceof IAlloySpecialItem);
            }
        }
        if (this.getColor() != 16777215) {
            res.getOrCreateTag().putInt("color",this.getColor());
        }

        if (!this.getLocalName().isEmpty()) {
            res.getOrCreateTag().putString("nameOverride",this.getLocalName());
        }
        //System.out.println("RECIPE OUTPUT: " + res);
        return res;
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    @Override
    public int getRecipeWidth() {
        return getWidth();
    }

    public int getHeight() {
        return this.recipeHeight;
    }

    @Override
    public int getRecipeHeight() {
        return getHeight();
    }

    private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + patternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... toShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < toShrink.length; ++i1) {
            String s = toShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (toShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[toShrink.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = toShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String str) {
        int i;
        for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String str) {
        int i;
        for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
        }

        return i;
    }

    private static String[] patternFromJson(JsonArray jsonArr) {
        String[] astring = new String[jsonArr.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(jsonArr.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    /**
     * Returns a key json object as a Java HashMap.
     */
    private static Map<String, Ingredient> deserializeKey(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            String alloyComp = null;
            String alloyRecipe = null;
            String name = null;
            int color = 16777215;
            if (entry.getValue().isJsonObject())
            {
                if (entry.getValue().getAsJsonObject().has("alloyComp"))
                {
                    alloyComp = GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "alloyComp");
                }
                if (entry.getValue().getAsJsonObject().has("alloyRecipe"))
                {
                    alloyRecipe = GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "alloyRecipe");
                }
                if (entry.getValue().getAsJsonObject().has("langName")) {
                    name = GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "langName");
                }
                if (entry.getValue().getAsJsonObject().has("color")) {
                    color = Math.max(0,GsonHelper.getAsInt(entry.getValue().getAsJsonObject(), "color"));
                }
            }

            map.put(entry.getKey(), AlloyIngredientHelper.deserialize(entry.getValue(),alloyComp,alloyRecipe,name,color));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
        if (item == null) {
            throw new JsonSyntaxException("Unknown item '" + s + "'");
        }

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer implements RecipeSerializer<AlloyCraftingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "alloy_crafting");
        public AlloyCraftingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = GsonHelper.getAsString(json, "group", "");
            int c;
            if (json.has("color")) {
                c = Math.max(0,json.get("color").getAsInt());
            } else {
                c = 16777215;
            }
            String n = json.has("langName") ? json.get("langName").getAsString() : "";
            Map<String, Ingredient> map = AlloyCraftingRecipe.deserializeKey(GsonHelper.getAsJsonObject(json, "key"));
            String[] astring = AlloyCraftingRecipe.shrink(AlloyCraftingRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = AlloyCraftingRecipe.deserializeIngredients(astring, map, i, j);
            ItemStack itemstack = AlloyCraftingRecipe.deserializeItem(GsonHelper.getAsJsonObject(json, "result"));
            boolean in = json.has("inherit") && GsonHelper.getAsBoolean(json, "inherit");
            String inR = json.has("inheritRecipe") ? GsonHelper.getAsString(json, "inheritRecipe") : "";
            return new AlloyCraftingRecipe(recipeId, s, i, j, nonnulllist, itemstack, in, inR,n,c);
        }

        public AlloyCraftingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buffer));
            }

            ItemStack itemstack = buffer.readItem();
            boolean in = buffer.readBoolean();
            String inR = buffer.readUtf();
            String n = buffer.readUtf();
            int c = buffer.readInt();
            return new AlloyCraftingRecipe(recipeId, s, i, j, nonnulllist, itemstack, in, inR,n,c);
        }

        public void toNetwork(FriendlyByteBuf buffer, AlloyCraftingRecipe recipe) {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);
            buffer.writeUtf(recipe.group);


            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.recipeOutput);
            buffer.writeBoolean(recipe.inherit);
            buffer.writeUtf(recipe.inheritRecipe);
            buffer.writeUtf(recipe.localName);
            buffer.writeInt(recipe.color);
        }
    }
}