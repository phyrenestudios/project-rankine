package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class BlockRecipeHelper {


    public static ItemStack getBlockItemStack(JsonObject json)
    {
        String itemName = GsonHelper.getAsString(json, "block");

        Block block = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(itemName)).orElse(null);

        if (block == null || (block == Blocks.AIR && !itemName.equals("minecraft:air")))
            throw new JsonSyntaxException("Unknown block '" + itemName + "'");

        return new ItemStack(block);
    }

    public static FluidStack getBlockFluidStack(JsonObject json)
    {
        String itemName = GsonHelper.getAsString(json, "block");

        Fluid fluid = BuiltInRegistries.FLUID.getOptional(ResourceLocation.parse(itemName)).orElse(null);

        if (fluid == null || (fluid == Fluids.EMPTY && !itemName.equals("minecraft:empty")))
            throw new JsonSyntaxException("Unknown fluid '" + itemName + "'");

        return new FluidStack(fluid,1);
    }
}
