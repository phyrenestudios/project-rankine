package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AirDistillationRecipe implements Recipe<RecipeInput> {

    private final List<String> dims;
    private final List<String> biomes;
    private final NonNullList<ItemStack> recipeOutputs;
    private final NonNullList<Float> chances;

    public AirDistillationRecipe(NonNullList<ItemStack> recipeOutputsIn, NonNullList<Float> chancesIn,
                                 List<String> dimsIn, List<String> biomesIn) {
        this.recipeOutputs = recipeOutputsIn;
        this.chances = chancesIn;
        this.dims = dimsIn;
        this.biomes = biomesIn;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,Ingredient.EMPTY);
    }

    public NonNullList<Float> getChances() {
        return chances;
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return recipeOutputs;
    }

    public NonNullList<ItemStack> getRecipeOutputsJEI() {
        if (recipeOutputs.contains(ItemStack.EMPTY))
        {
            NonNullList<ItemStack> newJeiCopy = recipeOutputs;
            newJeiCopy.replaceAll(stack -> {
                if (stack == ItemStack.EMPTY)
                {
                    return new ItemStack(RankineItems.ELEMENT.get());
                } else {
                    return stack;
                }
            });
            return newJeiCopy;
        }
        return recipeOutputs;
    }

    @Override
    public boolean matches(RecipeInput inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public List<String> getBiomes() {
        return this.biomes;
    }

    public List<String> getDims() {
        return this.dims;
    }

    /**
     * PORT NOTE: biomes are a datapack registry in 1.21.1, so a {@link RegistryAccess}
     * (e.g. {@code level.registryAccess()}) is now required to resolve them.
     */
    public List<Biome> getBiomeList(RegistryAccess registryAccess) {
        Registry<Biome> reg = registryAccess.registryOrThrow(Registries.BIOME);
        List<Biome> biomeList = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("T#")) {
                TagKey<Biome> tag = TagKey.create(Registries.BIOME, ResourceLocation.parse(s.split("T#")[1]));
                for (Holder<Biome> holder : reg.getTagOrEmpty(tag)) {
                    biomeList.add(holder.value());
                }
            } else if (s.contains("B#")) {
                reg.getOptional(ResourceLocation.parse(s.split("B#")[1])).ifPresent(biomeList::add);
            }
        }
        return biomeList;
    }

    public List<String> getBiomeString(RegistryAccess registryAccess) {
        Registry<Biome> reg = registryAccess.registryOrThrow(Registries.BIOME);
        List<String> list = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("T#")) {
                TagKey<Biome> tag = TagKey.create(Registries.BIOME, ResourceLocation.parse(s.split("T#")[1]));
                for (Holder<Biome> holder : reg.getTagOrEmpty(tag)) {
                    holder.unwrapKey().ifPresent(key -> {
                        String name = key.location().getPath().toUpperCase(Locale.ROOT).replace("_", " ");
                        if (!list.contains(name)) {
                            list.add(name);
                        }
                    });
                }
            } else if (s.contains("B#")) {
                ResourceLocation rl = ResourceLocation.parse(s.split("B#")[1]);
                if (reg.containsKey(rl)) {
                    String name = rl.getPath().toUpperCase(Locale.ROOT).replace("_", " ");
                    if (!list.contains(name)) {
                        list.add(name);
                    }
                }
            }
        }
        return list;
    }

    /** Resolves the set of biome ids this recipe applies to. */
    private List<ResourceLocation> getBiomeIds(RegistryAccess registryAccess) {
        Registry<Biome> reg = registryAccess.registryOrThrow(Registries.BIOME);
        List<ResourceLocation> ids = new ArrayList<>();
        for (String s : this.getBiomes()) {
            if (s.contains("T#")) {
                TagKey<Biome> tag = TagKey.create(Registries.BIOME, ResourceLocation.parse(s.split("T#")[1]));
                for (Holder<Biome> holder : reg.getTagOrEmpty(tag)) {
                    holder.unwrapKey().ifPresent(key -> ids.add(key.location()));
                }
            } else if (s.contains("B#")) {
                ResourceLocation rl = ResourceLocation.parse(s.split("B#")[1]);
                if (reg.containsKey(rl)) {
                    ids.add(rl);
                }
            }
        }
        return ids;
    }

    public boolean matchesDistillationRecipe(RegistryAccess registryAccess, ResourceLocation biome, ResourceLocation dim) {
        List<ResourceLocation> biomeIds = getBiomeIds(registryAccess);
        if (biomeIds.isEmpty() && (getDims().isEmpty() || getDims().contains(dim.toString()))) {
            return true;
        } else {
            return biomeIds.contains(biome) && (getDims().isEmpty() || getDims().contains(dim.toString()));
        }
    }

    public ItemStack getDistillationResult(RegistryAccess registryAccess, int level, ResourceLocation biome, ResourceLocation dim) {
        if (matchesDistillationRecipe(registryAccess, biome, dim)) {
            return recipeOutputs.get(level-1);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getDistillationWithChances(Level worldIn, int level, ResourceLocation biome, ResourceLocation dim) {
        return worldIn.getRandom().nextFloat() < chances.get(level-1) ? getDistillationResult(worldIn.registryAccess(), level, biome, dim) : ItemStack.EMPTY;
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
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.AIR_DISTILLATION_RECIPE_SERIALIZER.get();
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

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.AIR_DISTILLATION.get();
    }

    public static class Serializer implements RecipeSerializer<AirDistillationRecipe> {

        public static final MapCodec<AirDistillationRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.withNumbered(RankineMapCodec.fixedKeys("biomes","dimensions"), "output", 10));

        public static final StreamCodec<RegistryFriendlyByteBuf, AirDistillationRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<AirDistillationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AirDistillationRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static AirDistillationRecipe fromJson(JsonObject json) {

            JsonArray b = json.has("biomes") ? GsonHelper.getAsJsonArray(json, "biomes") : new JsonArray();
            List<String> biomes = new ArrayList<>();
            for (int i = 0; i < b.size(); i++) {
                biomes.add(b.get(i).getAsString());
            }

            JsonArray d = json.has("dimensions") ? GsonHelper.getAsJsonArray(json, "dimensions") : new JsonArray();
            List<String> dims = new ArrayList<>();
            for (int i = 0; i < d.size(); i++) {
                dims.add(d.get(i).getAsString());
            }

            NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
            NonNullList<Float> chances = NonNullList.withSize(10, 0f);
            for (int i = 0; i < 10; i++) {
                String output = "output" + (i+1);
                if (json.has(output)) {
                    JsonObject object = GsonHelper.getAsJsonObject(json, output);
                    stacks.set(i, AirDistillationRecipe.deserializeItem(object));
                    if (object.has("chance")){
                        chances.set(i,object.get("chance").getAsFloat());
                    } else {
                        chances.set(i,0f);
                    }

                }
            }

            return new AirDistillationRecipe(stacks, chances, dims, biomes);
        }

        public static void toJson(AirDistillationRecipe recipe, JsonObject json) {
            if (!recipe.getBiomes().isEmpty()) {
                JsonArray biomes = new JsonArray();
                recipe.getBiomes().forEach(biomes::add);
                json.add("biomes", biomes);
            }
            if (!recipe.getDims().isEmpty()) {
                JsonArray dims = new JsonArray();
                recipe.getDims().forEach(dims::add);
                json.add("dimensions", dims);
            }
            for (int i = 0; i < recipe.getRecipeOutputs().size(); i++) {
                ItemStack stack = recipe.getRecipeOutputs().get(i);
                if (!stack.isEmpty()) {
                    JsonObject object = AlloyIngredientHelper.itemStackToJson(stack);
                    object.addProperty("chance", recipe.getChances().get(i));
                    json.add("output" + (i+1), object);
                }
            }
        }

        public static AirDistillationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {

            NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
            for(int k = 0; k < stacks.size(); ++k) {
                stacks.set(k, ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer));
            }

            NonNullList<Float> chances = NonNullList.withSize(10, 0f);
            for(int k = 0; k < chances.size(); ++k) {
                chances.set(k, buffer.readFloat());
            }

            int dimsSize = buffer.readInt();
            String[] dimArray = new String[dimsSize];
            for (int i = 0; i < dimsSize; i++) {
                dimArray[i] = buffer.readUtf();
            }

            List<String> dims = Arrays.asList(dimArray);

            int biomesSize = buffer.readInt();
            String[] biomeArray = new String[biomesSize];
            for (int i = 0; i < biomesSize; i++) {
                biomeArray[i] = buffer.readUtf();
            }

            List<String> biomes = Arrays.asList(biomeArray);


            return new AirDistillationRecipe(stacks, chances,dims,biomes);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, AirDistillationRecipe recipe) {

            int count = 0;
            for(ItemStack stack : recipe.recipeOutputs) {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, stack);
                count++;
            }
            while (count < 10) {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, ItemStack.EMPTY);
                count++;
            }

            count = 0;
            for (float chance : recipe.chances) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < 10) {
                buffer.writeFloat(0f);
                count++;
            }

            buffer.writeInt(recipe.getDims().size());
            for (int i = 0; i < recipe.getDims().size(); i++) {
                buffer.writeUtf(recipe.getDims().get(i));
            }

            buffer.writeInt(recipe.getBiomes().size());
            for (int i = 0; i < recipe.getBiomes().size(); i++) {
                buffer.writeUtf(recipe.getBiomes().get(i));
            }
        }
    }

}
