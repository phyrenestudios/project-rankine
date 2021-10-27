package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public enum AlloyUtilsEnum implements AlloyUtils {
    ALLOY(RankineToolMaterials.ALLOY, Config.ALLOYS.ALLOY_BONUS_DURABILITY,Config.ALLOYS.ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.ALLOY_BONUS_HL,Config.ALLOYS.ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"80Hg-20Au", null);

    IItemTier tier;
    ForgeConfigSpec.IntValue durabilityBonus;
    ForgeConfigSpec.DoubleValue miningSpeedBonus;
    ForgeConfigSpec.IntValue miningLevelBonus;
    ForgeConfigSpec.IntValue enchantabilityBonus;
    ForgeConfigSpec.DoubleValue attackDamageBonus;
    ForgeConfigSpec.DoubleValue attackSpeedBonus;
    ForgeConfigSpec.DoubleValue corrResistBonus;
    ForgeConfigSpec.DoubleValue heatResistBonus;
    ForgeConfigSpec.DoubleValue toughnessBonus;
    TextFormatting groupColor;
    AlloyEnchantmentHandlerOld toolEnchants;
    String comp;

    AlloyUtilsEnum(IItemTier tierIn, ForgeConfigSpec.IntValue durabilityIn, ForgeConfigSpec.DoubleValue miningSpeedIn, ForgeConfigSpec.IntValue miningLevelIn, ForgeConfigSpec.IntValue enchantabilityIn, ForgeConfigSpec.DoubleValue attackDamageIn,
                   ForgeConfigSpec.DoubleValue attackSpeedIn, ForgeConfigSpec.DoubleValue corrResistIn, ForgeConfigSpec.DoubleValue heatResistIn, ForgeConfigSpec.DoubleValue toughnessIn, AlloyEnchantmentHandlerOld toolEnchantsIn,
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
        return this.durabilityBonus.get();
    }

    @Override
    public float getMiningSpeedBonus() {
        return this.miningSpeedBonus.get().floatValue();
    }

    @Override
    public int getMiningLevelBonus() {
        return this.miningLevelBonus.get();
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus.get().floatValue();
    }

    @Override
    public float getAttackSpeedBonus() {
        return this.attackSpeedBonus.get().floatValue();
    }

    @Override
    public int getEnchantabilityBonus() {
        return this.enchantabilityBonus.get();
    }

    @Override
    public float getCorrResistBonus() {
        return this.corrResistBonus.get().floatValue();
    }

    @Override
    public float getHeatResistBonus() {
        return this.heatResistBonus.get().floatValue();
    }

    @Override
    public float getToughnessBonus() {
        return this.toughnessBonus.get().floatValue();
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return this.groupColor;
    }

    @Override
    public List<Enchantment> getEnchantmentBonus(Item item) {
        List<ResourceLocation> rslist = this.toolEnchants.getEnchantmentsForItem(item);
        List<Enchantment> enchants = new ArrayList<>();
        for (ResourceLocation rs : rslist) {
            if (ForgeRegistries.ENCHANTMENTS.getValue(rs) != null) {
                enchants.add(ForgeRegistries.ENCHANTMENTS.getValue(rs));
            }
        }
        return enchants;
    }

    @Override
    public int getEnchantmentLevel(Enchantment en, int enchantability) {
        return this.toolEnchants.returnEnchantmentLevel(en,enchantability);
    }

    @Override
    public String getDefComposition() {
        return this.comp;
    }
}
