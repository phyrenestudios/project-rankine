package com.cannolicatfish.rankine.util;

public enum Element {
    CARBON("C", 2,400, 1.0F, 3.0F, 2, 0.0F, 0.0F, 1F, 1F),
    ALUMINUM("Al", 1,100, 10.0F, 1.2F, 10, 0.0F, 0.0F, 1F, 1F),
    SULFUR("S", 0,1, 1.0F, 0.1F, 0, -0.5F, -0.5F, -0.5F, -0.5F),
    COPPER("Cu", 1,150, 3.0F, 1.7F, 10, 0.3F, 0.9F, 0.8F, 0.0F),
    MANGANESE("Mn", 2,250, 1.0F, 1.4F, 6, 0.5F, 0.95F, 0.1F,-0.2F),
    IRON("Fe", 2, 250, 6.0F, 2.0F, 14, 0.0F, 0.0F, 1F, 1F),
    NICKEL("Ni", 2,150, 4.0F,0.8F, 12, 0.0F, 0.0F, 1F, 1F),
    ZINC("Zn", 1,110, 3.0F, 1.0F, 16, 0.0F, 0.0F, 1F, 1F),
    TIN("Sn", 1,120, 6.0F, 0.5F, 10, 0.1F, 0.2F, 0.7F, 0.0F),
    GOLD("Au", 1, 32, 12.0F, 0.0F, 22, 0.0F, 0.0F, 1F, 1F);

    public final String symbol;
    private final int harvestLevel;
    private final int durability;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final float wear;
    private final float toughness;
    private final float heatResist;
    private final float corrResist;



    private Element(String symbol, int harvestLevelIn, int durabilityIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, float wearIn, float toughnessIn, float heatResistIn, float corrResistIn)
    {
        this.symbol = symbol;
        this.harvestLevel = harvestLevelIn;
        this.durability = durabilityIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.wear = wearIn;
        this.toughness = toughnessIn;
        this.heatResist = heatResistIn;
        this.corrResist = corrResistIn;
    }

    public int getDurability() {
        return this.durability;
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

    public float getWear() {
        return this.wear;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getHeatResist() {
        return this.heatResist;
    }

    public float getCorrResist() {
        return this.corrResist;
    }
}
