package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum AlloyUtilsEnum implements AlloyUtils{
    ALLOY(RankineToolMaterials.ALLOY, 0,0,0,0,0,0,0,0,0f, Collections.emptyList(),"80Hg-20Au", null),
    AMALGAM(RankineToolMaterials.AMALGAM, 0,0,0,0,0,0,0,0,-0.2f, Collections.emptyList(),"80Hg-20Au", null),
    BRONZE(RankineToolMaterials.BRONZE, 51,0,0,0,1,0,0,0.25f,0.05f, Collections.emptyList(),"80Cu-20Sn", TextFormatting.GOLD),
    INVAR(RankineToolMaterials.INVAR, 0,0,0,0,2,0,0,0.25f,0.05f, Collections.emptyList(),"90Fe-10Ni", TextFormatting.DARK_AQUA),
    ROSE_GOLD(RankineToolMaterials.ROSE_GOLD, 48,0,0,0,0,0,0.05f,0.35f,-0.1f,
            Collections.emptyList(),"75Au-22Cu-3Ni", TextFormatting.YELLOW),
    WHITE_GOLD(RankineToolMaterials.WHITE_GOLD, 32,0,0,0,0,0,0.1f,0.3f,-0.2f,
            Collections.emptyList(),"90Au-10Zn", TextFormatting.YELLOW),
    GREEN_GOLD(RankineToolMaterials.GREEN_GOLD, 32,0,0,0,0,0,0.25f,0.45f,-0.15f,
            Collections.emptyList(),"50Au-50Ag", TextFormatting.YELLOW),
    BLUE_GOLD(RankineToolMaterials.BLUE_GOLD, 32,0,0,0,1,0,0,0.2f,-0.05f,
            Collections.emptyList(),"75Au-25Fe", TextFormatting.YELLOW),
    PURPLE_GOLD(RankineToolMaterials.PURPLE_GOLD, 32,0,0,0,0,0,0.25f,0.25f,-0.1f,
            Collections.emptyList(),"80Au-20Al", TextFormatting.YELLOW),
    BLACK_GOLD(RankineToolMaterials.BLACK_GOLD, 32,0,0,0,1,0,0f,0.2f,-0.05f,
            Collections.emptyList(),"75Au-25Co", TextFormatting.YELLOW),
    PEWTER(RankineToolMaterials.PEWTER, 20,4,1,5,0,0,0.25F,0,-0.05f, Collections.emptyList(),"90Sn-10Sb", TextFormatting.DARK_GREEN),
    STEEL(RankineToolMaterials.STEEL, 460,4,1,0,3,0,0F,0,0.25f, Collections.emptyList(),"99Fe-1C", TextFormatting.DARK_GRAY),
    STAINLESS(RankineToolMaterials.STAINLESS, 760,4,1,0,3,0,0F,0,0.25f, Collections.emptyList(),"75Fe-13Cr-10Ni-2C", TextFormatting.WHITE),
    TUNGSTEN(RankineToolMaterials.TUNGSTEN, 370,3.5f,1,3,4,0,0F,0,0.15f, Collections.emptyList(),"90W-7Ni-3Fe", TextFormatting.DARK_PURPLE),
    NICKEL_SA(RankineToolMaterials.NICKEL_SA, 970,3.5f,1,3,4,0,0.15F,0,0.15f, Collections.emptyList(),"75Ni-15Cr-10Fe", TextFormatting.DARK_BLUE),
    COBALT_SA(RankineToolMaterials.COBALT_SA, 370,3.5f,1,3,4,0,0F,0,0.3f, Collections.emptyList(),"70Co-20Cr-10Ni", TextFormatting.DARK_BLUE);

    IItemTier tier;
    int durabilityBonus;
    float miningSpeedBonus;
    int miningLevelBonus;
    int enchantabilityBonus;
    float attackDamageBonus;
    float attackSpeedBonus;
    float corrResistBonus;
    float heatResistBonus;
    float toughnessBonus;
    TextFormatting groupColor;
    List<AbstractMap.SimpleEntry<Item,Enchantment>> toolEnchants;
    String comp;

    AlloyUtilsEnum(IItemTier tierIn, int durabilityIn, float miningSpeedIn, int miningLevelIn, int enchantabilityIn, float attackDamageIn,
                   float attackSpeedIn, float corrResistIn, float heatResistIn, float toughnessIn, List<AbstractMap.SimpleEntry<Item, Enchantment>> toolEnchantsIn,
                   String defaultCompIn, @Nullable TextFormatting groupColorIn)
    {

        this.tier = tierIn;
        this.durabilityBonus = durabilityIn;
        this.miningSpeedBonus = miningSpeedIn;
        this.miningLevelBonus = miningLevelIn;
        this.attackDamageBonus = attackDamageIn;
        this.attackSpeedBonus = attackSpeedIn;
        this.enchantabilityBonus = enchantabilityIn;
        this.corrResistBonus = corrResistIn;
        this.heatResistBonus = heatResistIn;
        this.toughnessBonus = toughnessIn;
        this.toolEnchants = toolEnchantsIn;
        this.comp = defaultCompIn;
        this.groupColor = groupColorIn != null ? groupColorIn : TextFormatting.WHITE;
    }

    @Override
    public IItemTier getMaterial() {
        return this.tier;
    }

    @Override
    public int getDurabilityBonus() {
        return this.durabilityBonus;
    }

    @Override
    public float getMiningSpeedBonus() {
        return this.miningSpeedBonus;
    }

    @Override
    public int getMiningLevelBonus() {
        return this.miningLevelBonus;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public float getAttackSpeedBonus() {
        return this.attackSpeedBonus;
    }

    @Override
    public int getEnchantabilityBonus() {
        return this.enchantabilityBonus;
    }

    @Override
    public float getCorrResistBonus() {
        return this.corrResistBonus;
    }

    @Override
    public float getHeatResistBonus() {
        return this.heatResistBonus;
    }

    @Override
    public float getToughnessBonus() {
        return this.toughnessBonus;
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return this.groupColor;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        for (AbstractMap.SimpleEntry<Item, Enchantment> entry : this.toolEnchants){
            if (entry.getKey() == item) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public int getEnchantmentLevel(Enchantment en, int enchantability) {
        if (enchantability >= 35 && en.getMaxLevel() >= 5)
        {
            return 5;
        }
        else if (enchantability >= 30 && en.getMaxLevel() >= 4)
        {
            return 4;
        }
        else if (enchantability >= 25 && en.getMaxLevel() >= 3)
        {
            return 3;
        }
        else if (enchantability >= 20 && en.getMaxLevel() >= 2)
        {
            return 2;
        }
        return 1;
    }

    @Override
    public String getDefComposition() {
        return this.comp;
    }
}
