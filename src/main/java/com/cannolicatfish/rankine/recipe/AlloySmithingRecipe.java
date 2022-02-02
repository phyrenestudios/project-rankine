package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.items.alloys.IAlloySpecialItem;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class AlloySmithingRecipe extends SmithingRecipe {
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
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack itemstack = this.result.copy();
        CompoundNBT compoundnbt = inv.getStackInSlot(0).getTag();
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
        Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
        Ingredient ingredient1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "addition"));
        ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        return new AlloySmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
    }

    public AlloySmithingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        Ingredient ingredient = Ingredient.read(buffer);
        Ingredient ingredient1 = Ingredient.read(buffer);
        ItemStack itemstack = buffer.readItemStack();
        return new AlloySmithingRecipe(recipeId, ingredient, ingredient1, itemstack);
    }

    public void write(PacketBuffer buffer, AlloySmithingRecipe recipe) {
        recipe.base.write(buffer);
        recipe.addition.write(buffer);
        buffer.writeItemStack(recipe.result);
    }
}

