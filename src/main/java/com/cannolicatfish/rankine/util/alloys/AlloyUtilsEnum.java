package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public enum AlloyUtilsEnum implements AlloyUtils {
    ALLOY(RankineToolMaterials.ALLOY, 0,0,0,0,0,0,0,0,0f, AlloyEnchantmentHandler.EMPTY,"80Hg-20Au", null),
    AMALGAM(RankineToolMaterials.AMALGAM, 0,0,0,0,0,0,0,0,-0.2f, AlloyEnchantmentHandler.EMPTY,"60Hg-40Au", null),
    BRONZE(RankineToolMaterials.BRONZE, 51,0,0,0,0,0,0,0.25f,0.05f, AlloyEnchantmentHandler.EMPTY,"80Cu-20Sn", TextFormatting.GOLD),
    INVAR(RankineToolMaterials.INVAR, 0,0,0,0,0,0,0,0.25f,0.05f, AlloyEnchantmentHandler.EMPTY,"90Fe-10Ni", TextFormatting.DARK_AQUA),
    ROSE_GOLD(RankineToolMaterials.ROSE_GOLD, 48,0,0,0,0,0,0.05f,0.35f,-0.1f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","swing")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","efficiency")), //shovel
                    Collections.singletonList(new ResourceLocation("rankine","puncture")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","sharpness"))), //sword
            "75Au-22Cu-3Ni", TextFormatting.YELLOW),
    WHITE_GOLD(RankineToolMaterials.WHITE_GOLD, 32,0,0,0,0,0,0.1f,0.3f,-0.2f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","fortune")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","atomize")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","fortune")), //shovel
                    Collections.singletonList(new ResourceLocation("rankine","impaling")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","looting"))), //sword
            "90Au-10Zn", TextFormatting.YELLOW),
    GREEN_GOLD(RankineToolMaterials.GREEN_GOLD, 32,0,0,0,0,0,0.25f,0.45f,-0.15f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","mending")), //axe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","mending")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","mending"))), //sword
            "50Au-50Ag", TextFormatting.YELLOW),
    BLUE_GOLD(RankineToolMaterials.BLUE_GOLD, 32,0,0,0,0,0,0,0.2f,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //axe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","unbreaking"))), //sword
            "75Au-25Fe", TextFormatting.YELLOW),
    PURPLE_GOLD(RankineToolMaterials.PURPLE_GOLD, 32,0,0,0,0,0,0.25f,0.25f,-0.1f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","daze")), //hammer
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //hoe
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //pickaxe
                    Collections.singletonList(new ResourceLocation("minecraft","silk_touch")), //shovel
                    Collections.singletonList(new ResourceLocation("rankine","impact")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","knockback"))), //sword
            "80Au-20Al", TextFormatting.YELLOW),
    BLACK_GOLD(RankineToolMaterials.BLACK_GOLD, 32,0,0,0,0,0,0f,0.2f,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("minecraft","bane_of_arthropods")), //axe
                    Collections.singletonList(new ResourceLocation("rankine","excavate")), //hammer
                    Collections.singletonList(new ResourceLocation("rankine","foraging")), //hoe
                    Collections.singletonList(new ResourceLocation("rankine","quake")), //pickaxe
                    Collections.singletonList(new ResourceLocation("rankine","quake")), //shovel
                    Collections.singletonList(new ResourceLocation("minecraft","loyalty")), //spear
                    Collections.singletonList(new ResourceLocation("minecraft","bane_of_arthropods"))), //sword
            "75Au-25Co", TextFormatting.YELLOW),
    PEWTER(RankineToolMaterials.PEWTER, 20,4,1,5,0,0,0.25F,0,-0.05f,
            new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),
                    Collections.singletonList(new ResourceLocation("rankine","antiquated")),14,2,3),
            "90Sn-10Sb", TextFormatting.DARK_GREEN),
    STEEL(RankineToolMaterials.STEEL, 460,4,1,0,0,0,0F,0,0.25f, AlloyEnchantmentHandler.EMPTY,"99Fe-1C", TextFormatting.DARK_GRAY),
    TITANIUM(RankineToolMaterials.TITANIUM, 50,0,1,0,0,0,0F,0,0f, AlloyEnchantmentHandler.EMPTY,"90Ti-6Al-4V", TextFormatting.DARK_GRAY),
    STAINLESS(RankineToolMaterials.STAINLESS, 760,4,1,0,0,0,0F,0,0.25f, new AlloyEnchantmentHandler(Collections.singletonList(new ResourceLocation("rankine","antiquated")),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.singletonList(new ResourceLocation("rankine","cleanse")),14,2,3),"75Fe-13Cr-10Ni-2C", TextFormatting.WHITE),
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
