package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.items.alloys.IAlloySpecialItem;
import com.google.gson.JsonObject;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

public class AlloySmithingRecipe extends UpgradeRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    public static final AlloySmithingRecipe.Serializer SERIALIZER = new AlloySmithingRecipe.Serializer();

    public AlloySmithingRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ItemStack result) {
        super(recipeId, base, addition, result);
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public ItemStack assemble(Container inv) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundnbt = inv.getItem(0).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }
        if (itemstack.getItem() instanceof IAlloySpecialItem) {
            IAlloySpecialItem item = ((IAlloySpecialItem) itemstack.getItem());
            // add alloyModifier NBT to alloy
            item.setRefresh(itemstack);
        }
        return itemstack;
    }

    public AlloySmithingRecipe read(ResourceLocation recipeId, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
        Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
        ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        return new AlloySmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
    }

    public AlloySmithingRecipe read(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
        ItemStack itemstack = buffer.readItem();
        return new AlloySmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
    }

    public void write(FriendlyByteBuf buffer, AlloySmithingRecipe recipe) {
        recipe.base.toNetwork(buffer);
        recipe.addition.toNetwork(buffer);
        buffer.writeItem(recipe.result);
    }
}

