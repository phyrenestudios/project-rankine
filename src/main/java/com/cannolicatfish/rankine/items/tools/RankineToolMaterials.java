package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum RankineToolMaterials implements IItemTier {
    FLINT(1, 81, 3.0F, 1.0F, 8, () -> {
        return Ingredient.fromItems(Items.FLINT);
    }),
    ALLOY(0, 63, 2.0F, 0.0F, 5, () -> Ingredient.EMPTY),
    BRONZE(1, 150, 4.0F, 1.0F, 10, () -> {
        return Ingredient.fromItems(RankineItems.BRONZE_INGOT.get());
    }),
    INVAR(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.fromItems(RankineItems.INVAR_INGOT.get());
    }),
    ROSE_GOLD(1, 60, 11.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(RankineItems.ROSE_GOLD_INGOT.get());
    }),
    WHITE_GOLD(0, 48, 12.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(RankineItems.WHITE_GOLD_INGOT.get());
    }),
    GREEN_GOLD(0, 52, 12.0F, 1.0F, 24, () -> {
        return Ingredient.fromItems(RankineItems.GREEN_GOLD_INGOT.get());
    }),
    BLUE_GOLD(2, 78, 9.0F, 0.0F, 20, () -> {
        return Ingredient.fromItems(RankineItems.BLUE_GOLD_INGOT.get());
    }),
    PURPLE_GOLD(1, 40, 14.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(RankineItems.PURPLE_GOLD_INGOT.get());
    }),
    BLACK_GOLD(2, 78, 9.0F, 0.0F, 20, () -> {
        return Ingredient.fromItems(RankineItems.BLUE_GOLD_INGOT.get());
    }),
    PEWTER(1, 63, 4.0F, 0.0F, 14, () -> {
        return Ingredient.fromItems(RankineItems.PEWTER_INGOT.get());
    }),
    AMALGAM(0, 63, 2.0F, 0.0F, 0, () -> {
        return Ingredient.fromItems(RankineItems.AMALGAM_INGOT.get());
    }),
    ENDER_AMALGAM(0, 63, 2.0F, 0.0F, 0, () -> {
        return Ingredient.fromItems(RankineItems.ENDER_AMALGAM_INGOT.get());
    }),
    STEEL(3, 750, 8.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(RankineItems.STEEL_INGOT.get());
    }),
    STAINLESS(3, 750, 8.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(RankineItems.STEEL_INGOT.get());
    }),
    TITANIUM(3, 336, 10.0F, 2.0F, 5, () -> {
        return Ingredient.fromItems(RankineItems.STEEL_INGOT.get());
    }),
    TUNGSTEN(4, 2031, 8.0F, 5.0F, 10, () -> {
        return Ingredient.fromItems(RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get());
    }),
    NICKEL_SA(4, 2031, 9.0F, 4.0F, 15, () -> {
        return Ingredient.fromItems(RankineItems.NICKEL_SUPERALLOY_INGOT.get());
    }),
    COBALT_SA(4, 2031, 9.0F, 4.0F, 12, () -> {
        return Ingredient.fromItems(RankineItems.COBALT_SUPERALLOY_INGOT.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private RankineToolMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
    {

        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
