package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloySpecialItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/**
 * Extends vanilla ShapedRecipe (rather than implementing CraftingRecipe directly) so the vanilla
 * recipe book fills the grid correctly: PlaceRecipe#placeRecipe special-cases
 * {@code instanceof ShapedRecipe} to learn the grid dimensions and treats everything else as
 * shapeless, which scrambled the 2x3 (hoes/axes), 1x3 (swords/shovels) and 3x2 (helmets/boots)
 * shapes. 1.18 got this via Forge's IShapedRecipe, which NeoForge 1.21.1 removed.
 *
 * <p>Only the TYPE and the pattern dimensions are inherited; matches() and assemble() stay fully
 * custom (alloy composition checks, data inheritance), so crafting behavior is unchanged.
 */
public class AlloyCraftingRecipe extends ShapedRecipe {
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
    private final String group;
    private final boolean inherit;
    private final String inheritRecipe;
    private final int color;
    private final String localName;

    /**
     * PORT NOTE: recipes no longer know their own id in 1.21.1. It is resolved lazily from the
     * RecipeManager during matches() (or bound via {@link #bindId}) because the "def" langName
     * option derives the generated lang key from the recipe id.
     */
    @Nullable
    private ResourceLocation cachedId;

    public AlloyCraftingRecipe(String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, boolean inherit,
                               String inheritRecipeIn, String nameIn, int colorIn) {
        super(groupIn, CraftingBookCategory.MISC,
                new ShapedRecipePattern(recipeWidthIn, recipeHeightIn, recipeItemsIn, java.util.Optional.empty()),
                recipeOutputIn, true);
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

    /** Binds the recipe's own id (e.g. from a RecipeHolder). Safe to call multiple times. */
    public void bindId(ResourceLocation id) {
        if (this.cachedId == null) {
            this.cachedId = id;
        }
    }

    @Nullable
    public ResourceLocation getId() {
        return this.cachedId;
    }

    private void resolveId(Level level) {
        if (this.cachedId == null && this.localName.equals("def")) {
            for (RecipeHolder<CraftingRecipe> holder : level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
                if (holder.value() == this) {
                    this.cachedId = holder.id();
                    break;
                }
            }
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ALLOY_CRAFTING_RECIPE_SERIALIZER.get();
    }

    // category() and getGroup() are inherited from ShapedRecipe (MISC and groupIn via the super call).

    /** The "no colour" sentinel every alloy path uses; {@code IAlloyItem#getColorNBT} returns it too. */
    private static final int WHITE = 16777215;

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
            ResourceLocation rs = ResourceLocation.parse(this.inheritRecipe);
            String[] s = rs.getPath().split("/");
            return "item." + rs.getNamespace() + "." + s[s.length-1];
        } else if (this.localName.equals("def")){
            if (this.cachedId == null) {
                // PORT-TODO: id unresolved until matches() has run once (see resolveId).
                return "";
            }
            String[] s = this.cachedId.getPath().split("/");
            return "item." + this.cachedId.getNamespace() + "." + s[s.length-1];
        } else {
            return this.localName;
        }

    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return getResultItem();
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        ItemStack out = this.recipeOutput.copy();
        if (this.getColor() != WHITE) {
            IAlloyItem.addColorNBT(out, this.getColor());
        }

        if (!this.getLocalName().isEmpty()) {
            out.set(RankineDataComponents.NAME_OVERRIDE.get(), this.getLocalName());
        }
        return out;
    }

    // getIngredients() and canCraftInDimensions() are inherited from ShapedRecipe; the pattern was
    // built from this.recipeItems, so they return the same list and the same bounds check.

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(CraftingInput inv, Level worldIn) {
        resolveId(worldIn);
        for(int i = 0; i <= inv.width() - this.recipeWidth; ++i) {
            for(int j = 0; j <= inv.height() - this.recipeHeight; ++j) {
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
    private boolean checkMatch(CraftingInput craftingInventory, int width, int height, boolean mirrored) {
        for(int i = 0; i < craftingInventory.width(); ++i) {
            for(int j = 0; j < craftingInventory.height(); ++j) {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (mirrored) {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                ItemStack stackInSlot = craftingInventory.getItem(i + j * craftingInventory.width());
                if (!ingredient.test(stackInSlot)) {
                    return false;
                }
                if (ingredient.getItems().length > 0)
                {
                    if (!IAlloyItem.getAlloyComposition(ingredient.getItems()[0]).isEmpty() &&
                            !IAlloyItem.getAlloyComposition(ingredient.getItems()[0]).equals(IAlloyItem.getAlloyComposition(stackInSlot))) {
                        return false;
                    }
                    ResourceLocation rs = IAlloyItem.getAlloyRecipe(ingredient.getItems()[0]);
                    ResourceLocation inSlot = IAlloyItem.getAlloyRecipe(stackInSlot);
                    if (rs != null &&
                            (inSlot == null || !rs.toString().equals(inSlot.toString()))) {
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
    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider provider) {
        ItemStack res = this.getResultItem().copy();
        if (this.inherit)
        {
            String workingRecipe = "";
            if (!this.inheritRecipe.isEmpty()) {
                workingRecipe = this.inheritRecipe;
            }
            String workingComposition = "";
            int workingColor = WHITE;
            for(int i = 0; i < inv.width(); ++i) {
                for (int j = 0; j < inv.height(); ++j) {

                    ItemStack stackInSlot = inv.getItem(i + j * inv.width());
                    ResourceLocation rs = IAlloyItem.getAlloyRecipe(stackInSlot);
                    if (!this.inheritRecipe.isEmpty()) {
                        if (rs != null && rs.toString().equals(this.inheritRecipe))
                        {
                            String comp = IAlloyItem.getAlloyComposition(stackInSlot);
                            if (workingComposition.isEmpty())
                            {
                                workingComposition = comp;
                            }
                            else if (!comp.equals(workingComposition))
                            {
                                return ItemStack.EMPTY;
                            }
                            if (workingColor == WHITE) {
                                workingColor = IAlloyItem.getColorNBT(stackInSlot);
                            }
                        }
                    } else if (rs != null) {

                        String comp = IAlloyItem.getAlloyComposition(stackInSlot);
                        String recipe = rs.toString();
                        if (workingComposition.isEmpty())
                        {
                            workingComposition = comp;
                        }
                        else if (!comp.equals(workingComposition))
                        {
                            return ItemStack.EMPTY;
                        }

                        if (workingRecipe.isEmpty()) {
                            workingRecipe = recipe;
                        } else if (!recipe.equals(workingRecipe)) {
                            return ItemStack.EMPTY;
                        }

                        if (workingColor == WHITE) {
                            workingColor = IAlloyItem.getColorNBT(stackInSlot);
                        }
                    }

                }
            }
            if (workingComposition.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                IAlloyItem.createDirectAlloyNBT(res,workingComposition,workingRecipe,null,res.getItem() instanceof IAlloySpecialItem);
                // The alloy colour rides along with the composition and the recipe, for the results
                // that have no colour of their own. See inheritsColor.
                if (workingColor != WHITE && inheritsColor(res)) {
                    IAlloyItem.addColorNBT(res, workingColor);
                }
            }
        }
        if (this.getColor() != WHITE) {
            IAlloyItem.addColorNBT(res, this.getColor());
        }

        if (!this.getLocalName().isEmpty()) {
            res.set(RankineDataComponents.NAME_OVERRIDE.get(), this.getLocalName());
        }
        return res;
    }


    /**
     * Whether an {@code inherit} result should take its colour from the ingredients as well as its
     * composition and recipe.
     *
     * <p>Only results with NO default alloying recipe qualify: the "generic" alloy items, whose
     * whole identity is carried by the stack rather than by the registration --
     * {@code rankine:alloy_block}, {@code rankine:alloy_ingot}, {@code rankine:alloy_nugget}, and
     * the generic armour/rod/gear. Every other alloy item ({@code steel_*}, {@code brass_*},
     * {@code white_gold_*} ...) names a fixed alloy when it is registered and ships a texture
     * already drawn in that alloy's colour, so writing a COLOR component onto one would change
     * stack identity with nothing to show for it.
     *
     * <p>In practice this selects exactly four of the 486 shipped alloy_crafting recipes: those
     * producing a generic item AND leaving {@code color} at the white sentinel, which is the
     * ingot -> block -> ingot -> nugget chain. The 102 other recipes producing generic items
     * (24 of each armour piece, 4 rods, 2 gears) each name a concrete colour, because each is fed
     * by one specific named ingot; that explicit colour still wins, since this path is only reached
     * when the recipe's own colour is the sentinel.
     *
     * <p>Why the block needed it at all: an alloy ingot leaves the alloy furnace with a COLOR
     * component ({@code AlloyingRecipe#assemble} writes the alloying recipe's colour), but nine of
     * them crafted into {@code rankine:alloy_block} produced an untinted block, because the single
     * recipe that can accept ANY alloy cannot name a colour in its JSON.
     */
    private static boolean inheritsColor(ItemStack result) {
        return result.getItem() instanceof IAlloyItem alloyItem && alloyItem.getDefaultRecipe() == null;
    }

    // getWidth()/getHeight() are inherited from ShapedRecipe (pattern dimensions). Both JEI's
    // category extension and the vanilla recipe book's PlaceRecipe read them from there.

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
        BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    public static class Serializer implements RecipeSerializer<AlloyCraftingRecipe> {

        public static final MapCodec<AlloyCraftingRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("group","color","langName","key","pattern","result","inherit","inheritRecipe"));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyCraftingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<AlloyCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static AlloyCraftingRecipe fromJson(JsonObject json) {
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
            return new AlloyCraftingRecipe(s, i, j, nonnulllist, itemstack, in, inR,n,c);
        }

        /**
         * PORT-TODO: encode emits one unique key letter per grid slot rather than reconstructing the
         * original shared pattern keys. Decode(encode(x)) is functionally equivalent (same ingredient
         * grid, same result); only the generated JSON is more verbose than a hand-written file.
         *
         * <p>Key entries go through {@link AlloyIngredientHelper#alloyIngredientToJson}, not the bare
         * ingredient codec: the per-key alloy constraint lives in the data components of the
         * ingredient's example stack, and {@code Ingredient.CODEC} drops components on encode.
         */
        public static void toJson(AlloyCraftingRecipe recipe, JsonObject json) {
            if (!recipe.group.isEmpty()) {
                json.addProperty("group", recipe.group);
            }
            json.addProperty("color", recipe.color);
            if (!recipe.localName.isEmpty()) {
                json.addProperty("langName", recipe.localName);
            }
            String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXY";
            JsonObject key = new JsonObject();
            JsonArray pattern = new JsonArray();
            for (int row = 0; row < recipe.recipeHeight; row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < recipe.recipeWidth; col++) {
                    int index = col + row * recipe.recipeWidth;
                    Ingredient ingredient = recipe.recipeItems.get(index);
                    if (ingredient.isEmpty()) {
                        line.append(' ');
                    } else {
                        char symbol = symbols.charAt(index % symbols.length());
                        line.append(symbol);
                        key.add(String.valueOf(symbol), AlloyIngredientHelper.alloyIngredientToJson(ingredient));
                    }
                }
                pattern.add(line.toString());
            }
            json.add("key", key);
            json.add("pattern", pattern);
            json.add("result", AlloyIngredientHelper.itemStackToJson(recipe.recipeOutput));
            json.addProperty("inherit", recipe.inherit);
            if (!recipe.inheritRecipe.isEmpty()) {
                json.addProperty("inheritRecipe", recipe.inheritRecipe);
            }
        }

        public static AlloyCraftingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            ItemStack itemstack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            boolean in = buffer.readBoolean();
            String inR = buffer.readUtf();
            String n = buffer.readUtf();
            int c = buffer.readInt();
            return new AlloyCraftingRecipe(s, i, j, nonnulllist, itemstack, in, inR,n,c);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, AlloyCraftingRecipe recipe) {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);
            buffer.writeUtf(recipe.group);


            for(Ingredient ingredient : recipe.recipeItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.recipeOutput);
            buffer.writeBoolean(recipe.inherit);
            buffer.writeUtf(recipe.inheritRecipe);
            buffer.writeUtf(recipe.localName);
            buffer.writeInt(recipe.color);
        }
    }
}
