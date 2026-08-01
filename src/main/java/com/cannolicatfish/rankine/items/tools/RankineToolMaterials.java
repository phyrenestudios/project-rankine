package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * 1.21: {@link Tier} no longer exposes an int harvest level — it uses an "incorrect blocks for
 * drops" tag instead. Mapping: 0→INCORRECT_FOR_WOODEN_TOOL, 1→INCORRECT_FOR_STONE_TOOL,
 * 2→INCORRECT_FOR_IRON_TOOL, 3→INCORRECT_FOR_DIAMOND_TOOL, 4+→INCORRECT_FOR_NETHERITE_TOOL.
 * The old int level is kept as {@link #getHarvestLevel()} for Rankine logic that still needs it.
 */
public enum RankineToolMaterials implements Tier {
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
    private final Supplier<Ingredient> repairMaterial;

    private RankineToolMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn)
    {

        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = com.google.common.base.Suppliers.memoize(repairMaterialIn::get);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    /** Old harvest-level int (was Tier#getLevel in 1.18.2). */
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocksForLevel(this.harvestLevel);
    }

    public static TagKey<Block> incorrectBlocksForLevel(int harvestLevel) {
        return switch (harvestLevel) {
            case 0 -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
            case 1 -> BlockTags.INCORRECT_FOR_STONE_TOOL;
            case 2 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case 3 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            default -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
