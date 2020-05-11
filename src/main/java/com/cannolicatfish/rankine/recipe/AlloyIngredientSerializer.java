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

        final ResourceLocation entityRegistryName = new ResourceLocation(JSONUtils.getString(json, "entity"));
        if (!ForgeRegistries.ENTITIES.containsKey(entityRegistryName)) {
            throw new JsonSyntaxException("Unknown entity type '" + entityRegistryName.toString() + "'");
        }

        final CompoundNBT tileEntityData = stack.getOrCreateChildTag("BlockEntityTag");

        final CompoundNBT spawnData = tileEntityData.getCompound("SpawnData");
        spawnData.putString("id", entityRegistryName.toString());
        tileEntityData.put("SpawnData", spawnData);

        tileEntityData.put("SpawnPotentials", tileEntityData.getList("SpawnPotentials", Constants.NBT.TAG_COMPOUND));

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