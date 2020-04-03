package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum RankineToolMaterials implements IItemTier {
    FLINT(0, 63, 2.0F, 0.0F, 5, () -> {
        return Ingredient.fromItems(Items.FLINT);
    }),
    BRONZE(1, 131, 4.0F, 1.0F, 10, () -> {
        return Ingredient.fromItems(ModItems.BRONZE_ALLOY);
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
