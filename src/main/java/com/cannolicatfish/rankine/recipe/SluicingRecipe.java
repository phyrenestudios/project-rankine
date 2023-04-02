package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.util.WeightedCollection;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Random;

public class SluicingRecipe implements Recipe<Container> {

    private final int cooldownTicks;
    private final Ingredient ingredient;
    private final Ingredient tool;
    private final NonNullList<Ingredient> recipeOutputs;
    private final ResourceLocation id;
    private final NonNullList<Float> weights;
    private final NonNullList<Integer> mins;
    private final NonNullList<Integer> maxes;

    public SluicingRecipe(ResourceLocation idIn,Ingredient ingredientIn, Ingredient itemIn, NonNullList<Ingredient> recipeOutputsIn,
                          NonNullList<Float> weightsIn, NonNullList<Integer> minsIn, NonNullList<Integer> maxesIn, int cooldownTicksIn) {
        this.id = idIn;
        this.ingredient = ingredientIn;
        this.tool = itemIn;
        this.recipeOutputs = recipeOutputsIn;
        this.weights = weightsIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
        this.cooldownTicks = cooldownTicksIn;
    }


    public String getGroup() {
        return "";
    }

    public Ingredient getTool() {
        return tool;
    }

    public NonNullList<Integer> getMins() {
        return mins;
    }

    public NonNullList<Integer> getMaxes() {
        return maxes;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.ingredient, this.tool);
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public Float getChance(int index) {
        float in = getWeights().get(index);
        return (in/getWeights().stream().reduce(0f, Float::sum));
    }

    public NonNullList<Float> getWeights() {
        return this.weights;
    }

    public NonNullList<Ingredient> getOutputs() {
        return this.recipeOutputs;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        return this.getIngredient().test(inv.getItem(0)) && this.getTool().test(inv.getItem(1));
    }

    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getSluicingResult(Level worldIn) {
        WeightedCollection<ItemStack> col = new WeightedCollection<>();
        Random rand = worldIn.getRandom();
        for (int i = 0; i < this.recipeOutputs.size(); i++) {
            ItemStack[] curOut = this.recipeOutputs.get(i).getItems();
            col.add(this.weights.get(i),new ItemStack(curOut[rand.nextInt(curOut.length)].getItem(), this.maxes.get(i).equals(this.mins.get(i)) ? this.maxes.get(i) : worldIn.getRandom().nextInt(this.maxes.get(i) - this.mins.get(i)) + this.mins.get(i)));
        }
        return col.getRandomElement().copy();
    }

    public int getCooldownTicks() {
        return this.cooldownTicks;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.SLUICING_RECIPE_SERIALIZER.get();
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = GsonHelper.getAsString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true);
        }
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.SLUICING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<SluicingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "sluicing");
        public SluicingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            Ingredient it = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "tool"));

            JsonArray outputsArray = GsonHelper.getAsJsonArray(json, "outputs");
            int outputsArraySize = outputsArray.size();
            NonNullList<Ingredient> stacks = NonNullList.withSize(outputsArraySize, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(outputsArraySize, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(outputsArraySize, 1);
            NonNullList<Integer> maxes = NonNullList.withSize(outputsArraySize, 1);

            int i = 0;
            for (JsonElement element : outputsArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    stacks.set(i,Ingredient.fromJson(object));
                    if (object.has("weight")){
                        weights.set(i,object.get("weight").getAsFloat());
                    }

                    if (object.has("min")){
                        mins.set(i,object.get("min").getAsInt());
                    }

                    if (object.has("max")){
                        maxes.set(i,object.get("max").getAsInt());
                    }
                }
                i++;
            }
            int ticks = json.has("cooldown") ? json.get("cooldown").getAsInt() : 10;
            return new SluicingRecipe(recipeId,ingredient, it, stacks, weights, mins,maxes,ticks);
        }

        public SluicingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int ticks = buffer.readInt();
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient it = Ingredient.fromNetwork(buffer);

            int t = buffer.readVarInt();
            NonNullList<Ingredient> stacks = NonNullList.withSize(t, Ingredient.EMPTY);
            NonNullList<Float> weights = NonNullList.withSize(t, 0f);
            NonNullList<Integer> mins = NonNullList.withSize(t,1);
            NonNullList<Integer> maxes = NonNullList.withSize(t,1);

            for (int i = 0; i < stacks.size(); i++) {
                stacks.set(i,Ingredient.fromNetwork(buffer));
                weights.set(i,buffer.readFloat());
                mins.set(i,buffer.readInt());
                maxes.set(i,buffer.readInt());
            }

            return new SluicingRecipe(recipeId, input, it, stacks, weights, mins, maxes,ticks);
        }

        public void toNetwork(FriendlyByteBuf buffer, SluicingRecipe recipe) {
            buffer.writeInt(recipe.cooldownTicks);
            recipe.getIngredient().toNetwork(buffer);
            recipe.getTool().toNetwork(buffer);

            buffer.writeVarInt(recipe.getOutputs().size());
            for (int i = 0; i < recipe.getOutputs().size(); i++) {
                recipe.getOutputs().get(i).toNetwork(buffer);
                buffer.writeFloat(recipe.getWeights().get(i));
                buffer.writeInt(recipe.getMins().get(i));
                buffer.writeInt(recipe.getMaxes().get(i));
            }
        }
    }

}
