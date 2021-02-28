package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRecipeHelper {


    public static ItemStack getBlockItemStack(JsonObject json)
    {
        String itemName = JSONUtils.getString(json, "block");

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(itemName));

        if (block == null)
            throw new JsonSyntaxException("Unknown block '" + itemName + "'");

        return new ItemStack(block);
    }
}
