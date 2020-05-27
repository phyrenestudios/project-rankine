package com.cannolicatfish.rankine.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

public class AlloyIngredientSerializer implements IIngredientSerializer<NBTIngredient> {
    @Override
    public NBTIngredient parse(final JsonObject json) {
        final ItemStack stack = CraftingHelper.getItemStack(json, true);
        final String composition = JSONUtils.getString(json, "data");
        final CompoundNBT tileEntityData = stack.getOrCreateChildTag("StoredComposition");

        final CompoundNBT alloyData = tileEntityData.getCompound("comp");
        alloyData.putString("id", composition);
        tileEntityData.put("AlloyData", alloyData);

        return new IngredientRankineNBT(stack);
    }

    @Override
    public NBTIngredient parse(final PacketBuffer buffer) {
        throw new UnsupportedOperationException("Can't parse from PacketBuffer, use the Ingredient's own IIngredientSerializer instead");
    }

    @Override
    public void write(final PacketBuffer buffer, final NBTIngredient ingredient) {
        throw new UnsupportedOperationException("Can't write to PacketBuffer, use the Ingredient's own IIngredientSerializer instead");
    }
}