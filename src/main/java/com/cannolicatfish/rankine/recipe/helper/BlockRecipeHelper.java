package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
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

    public static FluidStack getBlockFluidStack(JsonObject json)
    {
        String itemName = JSONUtils.getString(json, "block");

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(itemName));

        if (fluid == null)
            throw new JsonSyntaxException("Unknown fluid '" + itemName + "'");

        return new FluidStack(fluid,1);
    }
}