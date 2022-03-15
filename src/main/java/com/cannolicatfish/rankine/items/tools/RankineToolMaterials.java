package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum RankineToolMaterials implements IItemTier {
    FLINT(1, 81, 3.0F, 1.0F, 8, () -> {
        return Ingredient.of(Items.FLINT);
    }),
    ALLOY(0, 63, 2.0F, 0.0F, 5, () -> Ingredient.EMPTY),
    BRONZE(1, 150, 4.0F, 1.0F, 10, () -> {
        return Ingredient.of(RankineItems.BRONZE_INGOT.get());
    }),
    INVAR(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.of(RankineItems.INVAR_INGOT.get());
    }),
    ROSE_GOLD(1, 60, 11.0F, 0.0F, 22, () -> {
        return Ingredient.of(RankineItems.ROSE_GOLD_INGOT.get());
    }),
    WHITE_GOLD(0, 48, 12.0F, 0.0F, 22, () -> {
        return Ingredient.of(RankineItems.WHITE_GOLD_INGOT.get());
    }),
    GREEN_GOLD(0, 52, 12.0F, 1.0F, 24, () -> {
        return Ingredient.of(RankineItems.GREEN_GOLD_INGOT.get());
    }),
    BLUE_GOLD(2, 78, 9.0F, 0.0F, 20, () -> {
        return Ingredient.of(RankineItems.BLUE_GOLD_INGOT.get());
    }),
    PURPLE_GOLD(1, 40, 14.0F, 0.0F, 22, () -> {
        return Ingredient.of(RankineItems.PURPLE_GOLD_INGOT.get());
    }),
    BLACK_GOLD(2, 78, 9.0F, 0.0F, 20, () -> {
        return Ingredient.of(RankineItems.BLUE_GOLD_INGOT.get());
    }),
    PEWTER(1, 63, 4.0F, 0.0F, 14, () -> {
        return Ingredient.of(RankineItems.PEWTER_INGOT.get());
    }),
    AMALGAM(0, 63, 2.0F, 0.0F, 0, () -> {
        return Ingredient.of(RankineItems.AMALGAM_INGOT.get());
    }),
    ENDER_AMALGAM(0, 63, 2.0F, 0.0F, 0, () -> {
        return Ingredient.of(RankineItems.ENDER_AMALGAM_INGOT.get());
    }),
    STEEL(3, 750, 8.0F, 3.0F, 10, () -> {
        return Ingredient.of(RankineItems.STEEL_INGOT.get());
    }),
    STAINLESS(3, 750, 8.0F, 3.0F, 10, () -> {
        return Ingredient.of(RankineItems.STEEL_INGOT.get());
    }),
    TITANIUM(3, 336, 10.0F, 2.0F, 5, () -> {
        return Ingredient.of(RankineItems.STEEL_INGOT.get());
    }),
    TUNGSTEN(4, 2031, 8.0F, 5.0F, 10, () -> {
        return Ingredient.of(RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get());
    }),
    NICKEL_SA(4, 2031, 9.0F, 4.0F, 15, () -> {
        return Ingredient.of(RankineItems.NICKEL_SUPERALLOY_INGOT.get());
    }),
    COBALT_SA(4, 2031, 9.0F, 4.0F, 12, () -> {
        return Ingredient.of(RankineItems.COBALT_SUPERALLOY_INGOT.get());
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

    public int getUses() {
        return this.maxUses;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
