package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum RankineToolMaterials implements IItemTier {
    FLINT(0, 63, 2.0F, 0.0F, 5, () -> {
        return Ingredient.fromItems(Items.FLINT);
    }),
    BRONZE(1, 150, 4.0F, 1.0F, 10, () -> {
        return Ingredient.fromItems(ModItems.BRONZE_ALLOY);
    }),
    METEORIC_IRON(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.fromItems(ModItems.METEORIC_IRON);
    }),
    ROSE_GOLD(1, 60, 11.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(ModItems.ROSE_GOLD_ALLOY);
    }),
    WHITE_GOLD(1, 48, 12.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(ModItems.WHITE_GOLD_ALLOY);
    }),
    GREEN_GOLD(1, 52, 12.0F, 1.0F, 24, () -> {
        return Ingredient.fromItems(ModItems.GREEN_GOLD_ALLOY);
    }),
    BLUE_GOLD(2, 78, 9.0F, 0.0F, 20, () -> {
        return Ingredient.fromItems(ModItems.BLUE_GOLD_ALLOY);
    }),
    PURPLE_GOLD(1, 40, 14.0F, 0.0F, 22, () -> {
        return Ingredient.fromItems(ModItems.PURPLE_GOLD_ALLOY);
    }),
    AMALGAM(0, 63, 2.0F, 0.0F, 0, () -> {
        return Ingredient.fromItems(ModItems.AMALGAM_ALLOY);
    }),
    STEEL(3, 1561, 8.0F, 3.0F, 10, () -> {
        return Ingredient.fromItems(ModItems.STEEL_ALLOY);
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
