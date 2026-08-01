package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class BlockRecipeHelper {


    public static ItemStack getBlockItemStack(JsonObject json)
    {
        String itemName = GsonHelper.getAsString(json, "block");

        Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(itemName));

        if (block == null)
            throw new JsonSyntaxException("Unknown block '" + itemName + "'");

        return new ItemStack(block);
    }

    public static FluidStack getBlockFluidStack(JsonObject json)
    {
        String itemName = GsonHelper.getAsString(json, "block");

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.parse(itemName));

        if (fluid == null)
            throw new JsonSyntaxException("Unknown fluid '" + itemName + "'");

        return new FluidStack(fluid,1);
    }
}