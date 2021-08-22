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
            Config.ALLOYS.ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"80Hg-20Au", null),

    AMALGAM(RankineToolMaterials.AMALGAM, Config.ALLOYS.AMALGAM_ALLOY_BONUS_DURABILITY,Config.ALLOYS.AMALGAM_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.AMALGAM_ALLOY_BONUS_HL,Config.ALLOYS.AMALGAM_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.AMALGAM_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.AMALGAM_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.AMALGAM_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.AMALGAM_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.AMALGAM_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"60Hg-40Au", null),

    BRONZE(RankineToolMaterials.BRONZE, Config.ALLOYS.BRONZE_ALLOY_BONUS_DURABILITY,Config.ALLOYS.BRONZE_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.BRONZE_ALLOY_BONUS_HL,Config.ALLOYS.BRONZE_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.BRONZE_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.BRONZE_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.BRONZE_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.BRONZE_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.BRONZE_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"80Cu-20Sn", TextFormatting.GOLD),

    INVAR(RankineToolMaterials.INVAR, Config.ALLOYS.INVAR_ALLOY_BONUS_DURABILITY,Config.ALLOYS.INVAR_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.INVAR_ALLOY_BONUS_HL,Config.ALLOYS.INVAR_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.INVAR_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.INVAR_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.INVAR_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.INVAR_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.INVAR_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"90Fe-10Ni", TextFormatting.DARK_AQUA),

    ROSE_GOLD(RankineToolMaterials.ROSE_GOLD, Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.ROSE_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","swing")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //shovel
                    Collections.singletonList(new ResourceLocation("rankine","puncture")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","sharpness"))), //sword
            "75Au-22Cu-3Ni", TextFormatting.YELLOW),

    WHITE_GOLD(RankineToolMaterials.WHITE_GOLD,  Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.WHITE_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","fortune")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","atomize")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","impaling")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","looting"))), //sword
            "90Au-10Zn", TextFormatting.YELLOW),

    GREEN_GOLD(RankineToolMaterials.GREEN_GOLD,  Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.GREEN_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","mending")), //axe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","mending"))), //sword
            "50Au-50Ag", TextFormatting.YELLOW),

    BLUE_GOLD(RankineToolMaterials.BLUE_GOLD, Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.BLUE_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //axe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking"))), //sword
            "75Au-25Fe", TextFormatting.YELLOW),

    PURPLE_GOLD(RankineToolMaterials.PURPLE_GOLD, Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.PURPLE_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","daze")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //shovel
                    Collections.singletonList(new ResourceLocation("rankine","impact")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","knockback"))), //sword
            "80Au-20Al", TextFormatting.YELLOW),

    BLACK_GOLD(RankineToolMaterials.BLACK_GOLD, Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_DURABILITY,Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_HL,Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.BLACK_GOLD_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("minecraft","bane_of_arthropods")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","excavate")), //hammer
                    Collections.singletonList(new ResourceLocation("rankine","foraging")), //hoe
                    Collections.singletonList(new ResourceLocation("rankine","quake")), //pickaxe
                    Collections.singletonList(new ResourceLocation("rankine","quake")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","loyalty")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","bane_of_arthropods"))), //sword
            "75Au-25Co", TextFormatting.YELLOW),

    PEWTER(RankineToolMaterials.PEWTER, Config.ALLOYS.PEWTER_ALLOY_BONUS_DURABILITY,Config.ALLOYS.PEWTER_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.PEWTER_ALLOY_BONUS_HL,Config.ALLOYS.PEWTER_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.PEWTER_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.PEWTER_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.PEWTER_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.PEWTER_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.PEWTER_ALLOY_BONUS_TOUGHNESS,
            new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),14,2,3),
            "90Sn-10Sb", TextFormatting.DARK_GREEN),

    STEEL(RankineToolMaterials.STEEL, Config.ALLOYS.STEEL_ALLOY_BONUS_DURABILITY,Config.ALLOYS.STEEL_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.STEEL_ALLOY_BONUS_HL,Config.ALLOYS.STEEL_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.STEEL_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.STEEL_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.STEEL_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.STEEL_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.STEEL_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"99Fe-1C", TextFormatting.DARK_GRAY),

    TITANIUM(RankineToolMaterials.TITANIUM, Config.ALLOYS.TITANIUM_ALLOY_BONUS_DURABILITY,Config.ALLOYS.TITANIUM_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.TITANIUM_ALLOY_BONUS_HL,Config.ALLOYS.TITANIUM_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.TITANIUM_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.TITANIUM_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.TITANIUM_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.TITANIUM_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.TITANIUM_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"90Ti-6Al-4V", TextFormatting.DARK_GRAY),

    STAINLESS(RankineToolMaterials.STAINLESS, Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_DURABILITY,Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_HL,Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.STAINLESS_STEEL_ALLOY_BONUS_TOUGHNESS, new AlloyEnchantmentHandlerOld(Collections.singletonList(new ResourceLocation("rankine","antiquated")),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.singletonList(new ResourceLocation("rankine","cleanse")),14,2,3),"75Fe-18Cr-5Ni-2C", TextFormatting.WHITE),

    TUNGSTEN(RankineToolMaterials.TUNGSTEN, Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_DURABILITY,Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_HL,Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_DAMAGE,
            Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_CORR_RESIST,Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.TUNGSTEN_HEAVY_ALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"90W-7Ni-3Fe", TextFormatting.DARK_PURPLE),

    NICKEL_SA(RankineToolMaterials.NICKEL_SA, Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_DURABILITY,Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_HL,Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_DAMAGE,
            Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_CORR_RESIST,Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.NICKEL_SUPERALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"70Ni-20Cr-10Co", TextFormatting.DARK_BLUE),

    COBALT_SA(RankineToolMaterials.COBALT_SA, Config.ALLOYS.COBALT_SUPERALLOY_BONUS_DURABILITY,Config.ALLOYS.COBALT_SUPERALLOY_BONUS_MINING_SPEED,
            Config.ALLOYS.COBALT_SUPERALLOY_BONUS_HL,Config.ALLOYS.COBALT_SUPERALLOY_BONUS_ENCHANTABILITY,Config.ALLOYS.COBALT_SUPERALLOY_BONUS_DAMAGE,
            Config.ALLOYS.COBALT_SUPERALLOY_BONUS_ATTACK_SPEED,Config.ALLOYS.COBALT_SUPERALLOY_BONUS_CORR_RESIST,Config.ALLOYS.COBALT_SUPERALLOY_BONUS_HEAT_RESIST,
            Config.ALLOYS.COBALT_SUPERALLOY_BONUS_TOUGHNESS, AlloyEnchantmentHandlerOld.EMPTY,"70Co-20Cr-10Ni", TextFormatting.DARK_BLUE);

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
