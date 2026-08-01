package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Locale;
import java.util.Map;

public class RockGeneratorRecipe implements Recipe<RecipeInput> {

    protected RockGeneratorUtils.RockGenType genType;
    protected Ingredient ingredient1;
    protected Ingredient ingredient2;
    protected ItemStack result;

    public RockGeneratorRecipe(RockGeneratorUtils.RockGenType genType, Ingredient input, Ingredient input2, ItemStack output) {
        this.genType = genType;
        this.ingredient1 = input;
        this.ingredient2 = input2;
        this.result = output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    public RockGeneratorUtils.RockGenType getGenType() {
        return genType;
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        if (this.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS) || this.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC) || this.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC) ||
        this.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY)) {
            boolean test;
            for (int i = 0; i < inv.size(); i++) {
                test = this.ingredient1.test(inv.getItem(i));
                if (test) {
                    return true;
                }
            }
            return false;
        } else {
            boolean test1 = false;
            boolean test2 = false;
            for (int i = 0; i < inv.size(); i++) {
                if (!test1) {
                    test1 = this.ingredient1.test(inv.getItem(i));
                }
                if (!test2) {
                    test2 = this.ingredient2.test(inv.getItem(i));
                }
                if (test1 && test2) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public BlockPos getRandomInput(Map<BlockPos,Block> map) {
        for (Map.Entry<BlockPos,Block> b: map.entrySet()) {
            if (this.ingredient1.test(new ItemStack(b.getValue())) || this.ingredient2.test(new ItemStack(b.getValue()))) {
                return b.getKey();
            }
        }
        return null;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        if (this.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS))
        {
            return NonNullList.of(Ingredient.EMPTY, ingredient1, Ingredient.of(new ItemStack(Items.SOUL_SOIL)),Ingredient.of(new ItemStack(Items.BLUE_ICE)));
        } else if (this.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC) || this.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC) || this.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY)) {
            return NonNullList.of(Ingredient.EMPTY, ingredient1);
        } else {
            return NonNullList.of(Ingredient.EMPTY, ingredient1, ingredient2);
        }

    }

    public Ingredient getFirstIngredient() {
        return ingredient1;
    }

    public Ingredient getSecondIngredient() {
        return ingredient2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ROCK_GENERATOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.ROCK_GENERATOR.get();
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = GsonHelper.getAsString(object, "block");

        BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer implements RecipeSerializer<RockGeneratorRecipe> {

        public static final MapCodec<RockGeneratorRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("genType","input1","input2","result"));

        public static final StreamCodec<RegistryFriendlyByteBuf, RockGeneratorRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<RockGeneratorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RockGeneratorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static RockGeneratorRecipe fromJson(JsonObject json) {
            RockGeneratorUtils.RockGenType t = json.has("genType") ? RockGeneratorUtils.RockGenType.valueOf(json.get("genType").getAsString().toUpperCase(Locale.ROOT)) : RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS;
            Ingredient ingredient1 = AlloyIngredientHelper.ingredientFromJson(json.get("input1"));
            Ingredient ingredient2;
            if (t.equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS) || t.equals(RockGeneratorUtils.RockGenType.METAMORPHIC) || t.equals(RockGeneratorUtils.RockGenType.VOLCANIC)) {
                ingredient2 = Ingredient.EMPTY;
            } else {
                ingredient2 = json.has("input2") ? AlloyIngredientHelper.ingredientFromJson(json.get("input2")) : Ingredient.EMPTY;
            }

            ItemStack result = deserializeBlock(GsonHelper.getAsJsonObject(json, "result"));
            return new RockGeneratorRecipe(t,ingredient1,ingredient2,result);
        }

        public static void toJson(RockGeneratorRecipe recipe, JsonObject json) {
            json.addProperty("genType", recipe.getGenType().toString().toLowerCase(Locale.ROOT));
            json.add("input1", AlloyIngredientHelper.ingredientToJson(recipe.getFirstIngredient()));
            if (!recipe.getSecondIngredient().isEmpty()) {
                json.add("input2", AlloyIngredientHelper.ingredientToJson(recipe.getSecondIngredient()));
            }
            JsonObject result = new JsonObject();
            result.addProperty("block", BuiltInRegistries.ITEM.getKey(recipe.result.getItem()).toString());
            json.add("result", result);
        }

        public static RockGeneratorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            RockGeneratorUtils.RockGenType t = RockGeneratorUtils.RockGenType.valueOf(buffer.readUtf().toUpperCase(Locale.ROOT));
            Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            return new RockGeneratorRecipe(t,input1,input2,output);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, RockGeneratorRecipe recipe) {
            buffer.writeUtf(recipe.getGenType().toString());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getFirstIngredient());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getSecondIngredient());
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.result);
        }
    }

}
