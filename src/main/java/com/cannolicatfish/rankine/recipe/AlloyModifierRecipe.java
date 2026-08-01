package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlloyModifierRecipe implements Recipe<RecipeInput> {

    protected Ingredient ingredient;
    private final List<AlloyModifier> modifiers;
    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    public AlloyModifierRecipe(Ingredient input, List<AlloyModifier> alloyModifiers, List<String> enchantmentsIn, List<String> enchantmentTypesIn) {
        this.ingredient = input;
        this.modifiers = alloyModifiers;
        this.enchantments = enchantmentsIn;
        this.enchantmentTypes = enchantmentTypesIn;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,ingredient);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public List<AlloyModifier> getModifiers() {
        return modifiers;
    }

    public List<String> getEnchantments() {
        return enchantments;
    }

    public List<String> getEnchantmentTypes() {
        return enchantmentTypes;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ALLOY_MODIFIER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.ALLOY_MODIFIER.get();
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

    public static class Serializer implements RecipeSerializer<AlloyModifierRecipe> {

        public static final MapCodec<AlloyModifierRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("input","modifiers","modifierTypes","values","enchantments","enchantmentTypes"));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyModifierRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<AlloyModifierRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyModifierRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        // PORT-TODO: 1.21.1 recipes no longer know their own id during deserialization. The old code
        // derived each AlloyModifier's name from "<namespace>:<last path segment>_<type>"; the recipe-id
        // part is replaced by a constant prefix. Modifier names remain unique per type within a recipe.
        private static String modifierName(String type) {
            return "rankine:alloy_modifier_" + type.toLowerCase(Locale.ROOT);
        }

        public static AlloyModifierRecipe fromJson(JsonObject json) {
            Ingredient ingredient = AlloyIngredientHelper.ingredientFromJson(json.get("input"));

            List<AlloyModifier> alloyModifiers = new ArrayList<>();
            if (json.has("modifiers")) {
                JsonArray modTypes = GsonHelper.getAsJsonArray(json,"modifiers");
                JsonArray modConds = GsonHelper.getAsJsonArray(json,"modifierTypes");
                JsonArray modVals = GsonHelper.getAsJsonArray(json,"values");
                for (int i = 0; i < modTypes.size(); i++) {
                    String inName = modifierName(modTypes.get(i).getAsString());
                    alloyModifiers.add(new AlloyModifier(inName,modTypes.get(i).getAsString().toUpperCase(Locale.ROOT),modConds.get(i).getAsString().toUpperCase(Locale.ROOT),modVals.get(i).getAsFloat()));
                }
            }
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();

            if (json.has("enchantments")) {
                JsonArray e = GsonHelper.getAsJsonArray(json,"enchantments");
                JsonArray eTypes = GsonHelper.getAsJsonArray(json,"enchantmentTypes");
                for (int i = 0; i < e.size(); i++) {
                    enchantments.add(e.get(i).getAsString().toLowerCase(Locale.ROOT));
                    enchantmentTypes.add(eTypes.get(i).getAsString().toUpperCase(Locale.ROOT));
                }
            }
            return new AlloyModifierRecipe(ingredient,alloyModifiers,enchantments,enchantmentTypes);
        }

        public static void toJson(AlloyModifierRecipe recipe, JsonObject json) {
            json.add("input", AlloyIngredientHelper.ingredientToJson(recipe.getIngredient()));
            if (!recipe.getModifiers().isEmpty()) {
                JsonArray modTypes = new JsonArray();
                JsonArray modConds = new JsonArray();
                JsonArray modVals = new JsonArray();
                for (AlloyModifier mod : recipe.getModifiers()) {
                    modTypes.add(mod.getType().toString().toUpperCase(Locale.ROOT));
                    modConds.add(mod.getCondition().toString().toUpperCase(Locale.ROOT));
                    modVals.add(mod.getValue());
                }
                json.add("modifiers", modTypes);
                json.add("modifierTypes", modConds);
                json.add("values", modVals);
            }
            if (!recipe.getEnchantments().isEmpty()) {
                JsonArray e = new JsonArray();
                JsonArray eTypes = new JsonArray();
                for (int i = 0; i < recipe.getEnchantments().size(); i++) {
                    e.add(recipe.getEnchantments().get(i));
                    eTypes.add(recipe.getEnchantmentTypes().get(i));
                }
                json.add("enchantments", e);
                json.add("enchantmentTypes", eTypes);
            }
        }

        public static AlloyModifierRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            int size = buffer.readInt();
            List<AlloyModifier> modifiers = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                String type = buffer.readUtf().toUpperCase(Locale.ROOT);
                String cond = buffer.readUtf().toUpperCase(Locale.ROOT);
                float val = buffer.readFloat();
                modifiers.add(new AlloyModifier(modifierName(type),type,cond,val));
            }

            size = buffer.readInt();
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                enchantments.add(buffer.readUtf().toLowerCase(Locale.ROOT));
                enchantmentTypes.add(buffer.readUtf().toUpperCase(Locale.ROOT));
            }

            return new AlloyModifierRecipe(input,modifiers,enchantments,enchantmentTypes);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, AlloyModifierRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getIngredient());

            int size = recipe.getModifiers().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                AlloyModifier mod = recipe.getModifiers().get(i);
                buffer.writeUtf(mod.getType().toString().toUpperCase(Locale.ROOT));
                buffer.writeUtf(mod.getCondition().toString().toUpperCase(Locale.ROOT));
                buffer.writeFloat(mod.getValue());
            }

            size = recipe.getEnchantments().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                buffer.writeUtf(recipe.getEnchantments().get(i));
                buffer.writeUtf(recipe.getEnchantmentTypes().get(i));
            }
        }
    }

}
