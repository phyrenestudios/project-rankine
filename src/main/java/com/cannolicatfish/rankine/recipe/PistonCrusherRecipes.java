package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import javafx.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class PistonCrusherRecipes {
    private static final PistonCrusherRecipes INSTANCE = new PistonCrusherRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static PistonCrusherRecipes getInstance() {
        return INSTANCE;
    }

    private PistonCrusherRecipes() {


    }


    public Pair<ItemStack, Float[]> getPrimaryResult(ItemStack input1) {

        if ((input1.getItem() == Items.STONE || input1.getItem() == Items.COBBLESTONE || input1.getItem() == new ItemStack(ModBlocks.GRANITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.ANDESITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.DIORITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.BASALT).getItem()
                || input1.getItem() == new ItemStack(ModBlocks.GNEISS).getItem() || input1.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem() || input1.getItem() == Items.SMOOTH_STONE
                || input1.getItem() == new ItemStack(ModBlocks.MARBLE).getItem())) {
            return new Pair<>(new ItemStack(ModItems.FELDSPAR), new Float[]{3f,0f});
        }

        if ((input1.getItem() == Items.SANDSTONE))
        {
            return new Pair<>(new ItemStack(Items.SAND), new Float[]{3f,0f});
        }

        if ((input1.getItem() == Items.RED_SANDSTONE))
        {
            return new Pair<>(new ItemStack(Items.RED_SAND), new Float[]{3f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.LIMESTONE).getItem())) {
            return new Pair<>(new ItemStack(ModItems.CALCITE), new Float[]{3f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem())) {
            return new Pair<>(new ItemStack(Items.FLINT), new Float[]{2f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.SHALE).getItem())) {
            return new Pair<>(new ItemStack(Items.CLAY_BALL), new Float[]{2f,0f});
        }

        if ((input1.getItem() == new ItemStack(ModBlocks.PERIDOTITE).getItem()) || input1.getItem() == new ItemStack(ModBlocks.KOMATIITE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.PYROXENE), new Float[]{2f,0f});
        }

        if ((input1.getItem() == new ItemStack(ModBlocks.RINGWOODITE).getItem()) || input1.getItem() == new ItemStack(ModBlocks.WADSLEYITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.BRIDGMANITE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.MAGNESIA), new Float[]{0f,0.5f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.KIMBERLITE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.OLIVINE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.FERROPERICLASE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.MAGNESIA), new Float[]{0f,0.5f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PEROVSKITE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.CALCIUM_SILICATE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MAGNETITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.METEORIC_IRON_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MALACHITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.COPPER_HYDROXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.VANADINITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.VANADINITE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BAUXITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.ALUMINA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CASSITERITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.TIN_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.SPHALERITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.ZINC_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CINNABAR_ORE).getItem()) {
            return new Pair<>(new ItemStack(Items.REDSTONE), new Float[]{4f, 0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MAGNESITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.MAGNESIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ILMENITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.TITANIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PENTLANDITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.NICKEL_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.GALENA_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.LEAD_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ACANTHITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.SILVER_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MOLYBDENITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.MOLYBDENUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PYROLUSITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.MAGNESIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CHROMITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.CHROMIUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.COLUMBITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.NIOBIUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.TANTALITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.TANTALUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.WOLFRAMITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.TUNGSTEN_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BISMITE_ORE).getItem()) {
            return new Pair<>(new ItemStack(ModItems.BISMUTH_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.LIGNITE_ORE).getItem() || input1.getItem() == ModItems.LIGNITE) {
            return new Pair<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.SUBBITUMINOUS_ORE).getItem() || input1.getItem() == Items.COAL) {
            return new Pair<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{2f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BITUMINOUS_ORE).getItem() || input1.getItem() == ModItems.BITUMINOUS_COAL) {
            return new Pair<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{2f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ANTHRACITE_ORE).getItem() || input1.getItem() == ModItems.ANTHRACITE_COAL) {
            return new Pair<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{3f,0f});
        }
        return new Pair<>(ItemStack.EMPTY, new Float[]{1f,0f});
    }

    public Pair<ItemStack, Float> getSecondaryResult(ItemStack input) {
        float chance = 1.0f;
        if (input.getItem() == Items.STONE)
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GRANITE).getItem()|| input.getItem() == new ItemStack(ModBlocks.DIORITE).getItem() || input.getItem() == new ItemStack(ModBlocks.MARBLE).getItem() && input.getItem() == Items.SMOOTH_STONE)
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ANDESITE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.LIMESTONE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.DOLOMITE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BASALT).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.SHALE).getItem())
        {
            chance = 0.5f;
            return new Pair<>(new ItemStack(Items.SAND), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GNEISS).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.CALCITE), chance);
        }
        if (input.getItem() == Items.SANDSTONE || input.getItem() == Items.RED_SANDSTONE)
        {
            chance = 0.5f;
            return new Pair<>(new ItemStack(ModItems.FELDSPAR), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PERIDOTITE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.OLIVINE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.KOMATIITE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.MAGNESIA), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BRIDGMANITE).getItem())
        {
            chance = 0.2f;
            return new Pair<>(new ItemStack(ModItems.CALCIUM_SILICATE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.KIMBERLITE).getItem())
        {
            chance = 0.05f;
            return new Pair<>(new ItemStack(Items.DIAMOND), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.FERROPERICLASE).getItem())
        {
            chance = 0.5f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CASSITERITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.SPHALERITE_ORE).getItem())
        {
            chance = 0.05f;
            return new Pair<>(new ItemStack(ModItems.OPAL), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PENTLANDITE_ORE).getItem())
        {
            chance = 0.05f;
            return new Pair<>(new ItemStack(ModItems.COBALTITE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ILMENITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.COPPER_HYDROXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GALENA_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.SILVER_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ACANTHITE_ORE).getItem() || input.getItem() == new ItemStack(ModBlocks.VANADINITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.MOLYBDENITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PYROLUSITE_ORE).getItem())
        {
            chance = 0.05f;
            return new Pair<>(new ItemStack(ModItems.TANTALUM_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CHROMITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.COLUMBITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.TANTALITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BISMITE_ORE).getItem())
        {
            chance = 0.1f;
            return new Pair<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CINNABAR_ORE).getItem())
        {
            chance = 0.25f;
            return new Pair<>(new ItemStack(ModItems.SULFUR), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.METEORIC_IRON_ORE).getItem())
        {
            chance = 0.75f;
            return new Pair<>(new ItemStack(ModItems.SULFUR), chance);
        }
        return new Pair<>(ItemStack.EMPTY, chance);
    }
}
