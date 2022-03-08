package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.BlockRecipeHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlloyModifierRecipe implements IRecipe<IInventory> {

    public static final AlloyModifierRecipe.Serializer SERIALIZER = new AlloyModifierRecipe.Serializer();
    protected Ingredient ingredient;
    protected ItemStack result;
    protected final ResourceLocation id;
    private final List<AlloyModifier> modifiers;
    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    public AlloyModifierRecipe(ResourceLocation id, Ingredient input, List<AlloyModifier> alloyModifiers, List<String> enchantmentsIn, List<String> enchantmentTypesIn) {
        this.id = id;
        this.ingredient = input;
        this.modifiers = alloyModifiers;
        this.enchantments = enchantmentsIn;
        this.enchantmentTypes = enchantmentTypesIn;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
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
    public ItemStack getRecipeOutput() {
        return result;
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
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.ALLOY_MODIFIER;
    }

    public static ItemStack deserializeBlock(JsonObject object) {
        String s = JSONUtils.getString(object, "block");

        Block block = Registry.BLOCK.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonParseException("Unknown block '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return BlockRecipeHelper.getBlockItemStack(object);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloyModifierRecipe> {

        @Override
        public AlloyModifierRecipe read(ResourceLocation recipeId, JsonObject json) {
            String[] s = recipeId.getPath().split("/");
            String nm = recipeId.getNamespace() + ":" + s[s.length-1];
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));

            List<AlloyModifier> alloyModifiers = new ArrayList<>();
            if (json.has("modifiers")) {
                JsonArray modTypes = JSONUtils.getJsonArray(json,"modifiers");
                JsonArray modConds = JSONUtils.getJsonArray(json,"modifierTypes");
                JsonArray modVals = JSONUtils.getJsonArray(json,"values");
                for (int i = 0; i < modTypes.size(); i++) {
                    String inName = nm + "_" + modTypes.get(i).getAsString().toLowerCase(Locale.ROOT);
                    alloyModifiers.add(new AlloyModifier(inName,modTypes.get(i).getAsString().toUpperCase(Locale.ROOT),modConds.get(i).getAsString().toUpperCase(Locale.ROOT),modVals.get(i).getAsFloat()));
                }
            }
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();

            if (json.has("enchantments")) {
                JsonArray e = JSONUtils.getJsonArray(json,"enchantments");
                JsonArray eTypes = JSONUtils.getJsonArray(json,"enchantmentTypes");
                for (int i = 0; i < e.size(); i++) {
                    enchantments.add(e.get(i).getAsString().toLowerCase(Locale.ROOT));
                    enchantmentTypes.add(eTypes.get(i).getAsString().toUpperCase(Locale.ROOT));
                }
            }
            return new AlloyModifierRecipe(recipeId,ingredient,alloyModifiers,enchantments,enchantmentTypes);
        }

        @Nullable
        @Override
        public AlloyModifierRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String[] s = recipeId.getPath().split("/");
            String nm = recipeId.getNamespace() + ":" + s[s.length-1];
            Ingredient input = Ingredient.read(buffer);

            int size = buffer.readInt();
            List<AlloyModifier> modifiers = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                String type = buffer.readString().toUpperCase(Locale.ROOT);
                String cond = buffer.readString().toUpperCase(Locale.ROOT);
                float val = buffer.readFloat();
                String inName = nm + "_" + type.toLowerCase(Locale.ROOT);
                modifiers.add(new AlloyModifier(inName,type,cond,val));
            }

            size = buffer.readInt();
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                enchantments.add(buffer.readString().toLowerCase(Locale.ROOT));
                enchantmentTypes.add(buffer.readString().toUpperCase(Locale.ROOT));
            }

            return new AlloyModifierRecipe(recipeId,input,modifiers,enchantments,enchantmentTypes);
        }

        @Override
        public void write(PacketBuffer buffer, AlloyModifierRecipe recipe) {
            recipe.getIngredient().write(buffer);

            int size = recipe.getModifiers().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                AlloyModifier mod = recipe.getModifiers().get(i);
                buffer.writeString(mod.getType().toString().toUpperCase(Locale.ROOT));
                buffer.writeString(mod.getCondition().toString().toUpperCase(Locale.ROOT));
                buffer.writeFloat(mod.getValue());
            }

            size = recipe.getEnchantments().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                buffer.writeString(recipe.getEnchantments().get(i));
                buffer.writeString(recipe.getEnchantmentTypes().get(i));
            }
        }
    }

}
