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

public enum AlloyUtilsEnum implements AlloyUtils {
    ALLOY(RankineToolMaterials.ALLOY, 0,0,0,0,0,0,0,0,0f, AlloyEnchantmentHandler.EMPTY,"80Hg-20Au", null),
    AMALGAM(RankineToolMaterials.AMALGAM, 0,0,0,0,0,0,0,0,-0.2f, AlloyEnchantmentHandler.EMPTY,"80Hg-20Au", null),
    BRONZE(RankineToolMaterials.BRONZE, 51,0,0,0,1,0,0,0.25f,0.05f, AlloyEnchantmentHandler.EMPTY,"80Cu-20Sn", TextFormatting.GOLD),
    INVAR(RankineToolMaterials.INVAR, 0,0,0,0,2,0,0,0.25f,0.05f, AlloyEnchantmentHandler.EMPTY,"90Fe-10Ni", TextFormatting.DARK_AQUA),
    ROSE_GOLD(RankineToolMaterials.ROSE_GOLD, 48,0,0,0,0,0,0.05f,0.35f,-0.1f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.EFFICIENCY), //axe
                    Collections.singletonList(RankineEnchantments.SWING), //hammer
                    Collections.singletonList(Enchantments.EFFICIENCY), //hoe
                    Collections.singletonList(Enchantments.EFFICIENCY), //pickaxe
                    Collections.singletonList(Enchantments.EFFICIENCY), //shovel
                    Collections.singletonList(RankineEnchantments.PUNCTURE), //spear
                    Collections.singletonList(Enchantments.SHARPNESS)), //sword
            "75Au-22Cu-3Ni", TextFormatting.YELLOW),
    WHITE_GOLD(RankineToolMaterials.WHITE_GOLD, 32,0,0,0,0,0,0.1f,0.3f,-0.2f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.FORTUNE), //axe
                    Collections.singletonList(RankineEnchantments.SWING), //hammer
                    Collections.singletonList(Enchantments.FORTUNE), //hoe
                    Collections.singletonList(Enchantments.FORTUNE), //pickaxe
                    Collections.singletonList(Enchantments.FORTUNE), //shovel
                    Collections.singletonList(RankineEnchantments.PUNCTURE), //spear
                    Collections.singletonList(Enchantments.LOOTING)), //sword
            "90Au-10Zn", TextFormatting.YELLOW),
    GREEN_GOLD(RankineToolMaterials.GREEN_GOLD, 32,0,0,0,0,0,0.25f,0.45f,-0.15f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.MENDING), //axe
                    Collections.singletonList(Enchantments.MENDING), //hammer
                    Collections.singletonList(Enchantments.MENDING), //hoe
                    Collections.singletonList(Enchantments.MENDING), //pickaxe
                    Collections.singletonList(Enchantments.MENDING), //shovel
                    Collections.singletonList(Enchantments.MENDING), //spear
                    Collections.singletonList(Enchantments.MENDING)), //sword
            "50Au-50Ag", TextFormatting.YELLOW),
    BLUE_GOLD(RankineToolMaterials.BLUE_GOLD, 32,0,0,0,1,0,0,0.2f,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.UNBREAKING), //axe
                    Collections.singletonList(Enchantments.UNBREAKING), //hammer
                    Collections.singletonList(Enchantments.UNBREAKING), //hoe
                    Collections.singletonList(Enchantments.UNBREAKING), //pickaxe
                    Collections.singletonList(Enchantments.UNBREAKING), //shovel
                    Collections.singletonList(Enchantments.UNBREAKING), //spear
                    Collections.singletonList(Enchantments.UNBREAKING)), //sword
            "75Au-25Fe", TextFormatting.YELLOW),
    PURPLE_GOLD(RankineToolMaterials.PURPLE_GOLD, 32,0,0,0,0,0,0.25f,0.25f,-0.1f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.SILK_TOUCH), //axe
                    Collections.singletonList(RankineEnchantments.DAZE), //hammer
                    Collections.singletonList(Enchantments.SILK_TOUCH), //hoe
                    Collections.singletonList(Enchantments.SILK_TOUCH), //pickaxe
                    Collections.singletonList(Enchantments.SILK_TOUCH), //shovel
                    Collections.singletonList(RankineEnchantments.IMPACT), //spear
                    Collections.singletonList(Enchantments.KNOCKBACK)), //sword
            "80Au-20Al", TextFormatting.YELLOW),
    BLACK_GOLD(RankineToolMaterials.BLACK_GOLD, 32,0,0,0,1,0,0f,0.2f,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(Enchantments.BANE_OF_ARTHROPODS), //axe
                    Collections.singletonList(RankineEnchantments.EXCAVATE), //hammer
                    Collections.singletonList(RankineEnchantments.FORAGING), //hoe
                    Collections.singletonList(RankineEnchantments.QUAKE), //pickaxe
                    Collections.singletonList(RankineEnchantments.QUAKE), //shovel
                    Collections.singletonList(Enchantments.LOYALTY), //spear
                    Collections.singletonList(Enchantments.BANE_OF_ARTHROPODS)), //sword
            "75Au-25Co", TextFormatting.YELLOW),
    PEWTER(RankineToolMaterials.PEWTER, 20,4,1,5,0,0,0.25F,0,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED),
                    Collections.singletonList(RankineEnchantments.ANTIQUATED)),
            "90Sn-10Sb", TextFormatting.DARK_GREEN),
    STEEL(RankineToolMaterials.STEEL, 460,4,1,0,0,0,0F,0,0.25f, AlloyEnchantmentHandler.EMPTY,"99Fe-1C", TextFormatting.DARK_GRAY),
    STAINLESS(RankineToolMaterials.STAINLESS, 760,4,1,0,0,0,0F,0,0.25f, AlloyEnchantmentHandler.EMPTY,"75Fe-13Cr-10Ni-2C", TextFormatting.WHITE),
    TUNGSTEN(RankineToolMaterials.TUNGSTEN, 370,3.5f,1,3,0,0,0F,0,0.15f, AlloyEnchantmentHandler.EMPTY,"90W-7Ni-3Fe", TextFormatting.DARK_PURPLE),
    NICKEL_SA(RankineToolMaterials.NICKEL_SA, 970,3.5f,1,3,0,0,0F,0,0.15f, AlloyEnchantmentHandler.EMPTY,"75Ni-15Cr-10Fe", TextFormatting.DARK_BLUE),
    COBALT_SA(RankineToolMaterials.COBALT_SA, 370,3.5f,1,3,0,0,0F,0,0.3f, AlloyEnchantmentHandler.EMPTY,"70Co-20Cr-10Ni", TextFormatting.DARK_BLUE);

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
    AlloyEnchantmentHandler toolEnchants;
    String comp;

    AlloyUtilsEnum(IItemTier tierIn, int durabilityIn, float miningSpeedIn, int miningLevelIn, int enchantabilityIn, float attackDamageIn,
                   float attackSpeedIn, float corrResistIn, float heatResistIn, float toughnessIn, AlloyEnchantmentHandler toolEnchantsIn,
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
    public List<Enchantment> getEnchantmentBonus(Item item) {
        return this.toolEnchants.getEnchantmentsForItem(item);
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
