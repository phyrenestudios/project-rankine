package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class PistonCrusherRecipes {
    private static final PistonCrusherRecipes INSTANCE = new PistonCrusherRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static PistonCrusherRecipes getInstance() {
        return INSTANCE;
    }

    private PistonCrusherRecipes() {


    }


    public AbstractMap.SimpleEntry<ItemStack, Float[]> getPrimaryResult(ItemStack input1) {

        AbstractMap.SimpleEntry<ItemStack,ItemStack> stacks = ModRecipes.getCrushingOutputs(input1);
        if (stacks != null)
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(stacks.getKey().getItem()),new Float[]{stacks.getKey().getCount()*1f,0f});
        }
/*
        if ((input1.getItem() == Items.STONE || input1.getItem() == Items.COBBLESTONE || input1.getItem() == new ItemStack(ModBlocks.GRANITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.ANDESITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.DIORITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.BASALT).getItem()
                || input1.getItem() == new ItemStack(ModBlocks.GNEISS).getItem() || input1.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem() || input1.getItem() == Items.SMOOTH_STONE
                || input1.getItem() == new ItemStack(ModBlocks.MARBLE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.FELDSPAR), new Float[]{3f,0f});
        }

        if ((input1.getItem() == new ItemStack(ModBlocks.STICK_BLOCK).getItem()))
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.STICK), new Float[]{2f,0f});
        }

        if ((input1.getItem() == Items.SANDSTONE))
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.SAND), new Float[]{3f,0f});
        }

        if ((input1.getItem() == Items.RED_SANDSTONE))
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.RED_SAND), new Float[]{3f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.LIMESTONE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CALCITE), new Float[]{3f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.FELDSPAR), new Float[]{2f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.SHALE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.CLAY_BALL), new Float[]{2f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.IRONSTONE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), new Float[]{1f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.ANORTHOSITE).getItem())) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.FELDSPAR), new Float[]{2f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.PERIDOTITE).getItem()) || input1.getItem() == new ItemStack(ModBlocks.KOMATIITE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.PYROXENE), new Float[]{2f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.RINGWOODITE).getItem()) || input1.getItem() == new ItemStack(ModBlocks.WADSLEYITE).getItem() || input1.getItem() == new ItemStack(ModBlocks.BRIDGMANITE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNESIA), new Float[]{0f,0.5f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.KIMBERLITE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.OLIVINE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.FERROPERICLASE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNESIA), new Float[]{0f,0.5f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PEROVSKITE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CALCIUM_SILICATE), new Float[]{1f,0f});
        }
        if ((input1.getItem() == new ItemStack(ModBlocks.PUMICE).getItem()))
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.POZZOLAN), new Float[]{2f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MAGNETITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.METEORITE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), new Float[]{0.5f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MALACHITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.COPPER_HYDROXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.VANADINITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.VANADINITE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BAUXITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.ALUMINA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CASSITERITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.TIN_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.SPHALERITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.ZINC_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CINNABAR_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.REDSTONE), new Float[]{4f, 0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MAGNESITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNESIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ILMENITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.TITANIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PENTLANDITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.NICKEL_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.GALENA_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.LEAD_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ACANTHITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SILVER_SULFIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.MOLYBDENITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MOLYBDENUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.PYROLUSITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNESIA), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.CHROMITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CHROMIUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.COLUMBITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.NIOBIUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.TANTALITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.TANTALUM_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.WOLFRAMITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.TUNGSTEN_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BISMITE_ORE).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.BISMUTH_OXIDE), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.LIGNITE_ORE).getItem() || input1.getItem() == ModItems.LIGNITE) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{1f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.SUBBITUMINOUS_ORE).getItem() || input1.getItem() == Items.COAL) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{2f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.BITUMINOUS_ORE).getItem() || input1.getItem() == ModItems.BITUMINOUS_COAL) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{2f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.ANTHRACITE_ORE).getItem() || input1.getItem() == ModItems.ANTHRACITE_COAL) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CRUSHED_COAL), new Float[]{3f,0f});
        }
        if (input1.getItem() == new ItemStack(ModBlocks.NITER).getItem()) {
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SALTPETER), new Float[]{1f,0f});
        }*/
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, new Float[]{0f,0f});
    }

    public AbstractMap.SimpleEntry<ItemStack, Float> getSecondaryResult(ItemStack input) {
        AbstractMap.SimpleEntry<ItemStack,Float> stacks = ModRecipes.getCrushingSecondaryOutputs(input);
        if (stacks != null)
        {
            return new AbstractMap.SimpleEntry<>(new ItemStack(stacks.getKey().getItem()),stacks.getValue());
        }
        /*
        float chance = 1.0f;
        if (input.getItem() == Items.STONE)
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GRANITE).getItem()|| input.getItem() == new ItemStack(ModBlocks.DIORITE).getItem() || input.getItem() == new ItemStack(ModBlocks.MARBLE).getItem() && input.getItem() == Items.SMOOTH_STONE)
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ANDESITE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.PYROXENE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.LIMESTONE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.DOLOMITE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BASALT).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.RHYOLITE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.QUARTZ), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.SHALE).getItem())
        {
            chance = 0.5f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.SAND), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GNEISS).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CALCITE), chance);
        }
        if (input.getItem() == Items.SANDSTONE || input.getItem() == Items.RED_SANDSTONE)
        {
            chance = 0.5f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.FELDSPAR), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PERIDOTITE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.OLIVINE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.KOMATIITE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNESIA), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BRIDGMANITE).getItem())
        {
            chance = 0.2f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CALCIUM_SILICATE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.KIMBERLITE).getItem())
        {
            chance = 0.05f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(Items.DIAMOND), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.FERROPERICLASE).getItem())
        {
            chance = 0.5f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CASSITERITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.SPHALERITE_ORE).getItem())
        {
            chance = 0.05f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.OPAL), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PENTLANDITE_ORE).getItem())
        {
            chance = 0.05f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.COBALTITE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ILMENITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MANGANESE_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.GALENA_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SILVER_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.ACANTHITE_ORE).getItem() || input.getItem() == new ItemStack(ModBlocks.VANADINITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.MOLYBDENITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.PYROLUSITE_ORE).getItem())
        {
            chance = 0.05f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.TANTALUM_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CHROMITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.COLUMBITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.IRON_OXIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.TANTALITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.BISMITE_ORE).getItem())
        {
            chance = 0.1f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.LEAD_SULFIDE), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.CINNABAR_ORE).getItem())
        {
            chance = 0.25f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SULFUR), chance);
        }
        if (input.getItem() == new ItemStack(ModBlocks.METEORITE).getItem())
        {
            chance = 0.75f;
            return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.SULFUR), chance);
        }*/
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, 0f);
    }
}
