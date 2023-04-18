package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum RankineArmorMaterials implements ArmorMaterial {
    BRIGANDINE("rankine:brigandine", 20, new int[]{3, 6, 8, 3}, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () -> {
        return Ingredient.of(Items.LEATHER, RankineItems.STEEL_INGOT.get());
    }),
    ALLOY("rankine:alloy", 10, new int[]{0, 0, 0, 0}, 0, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
        return Ingredient.of(RankineItems.ALLOY_INGOT.get());
    }),
    RUBBER("rankine:rubber", 15, new int[]{1, 3, 5, 2}, 7, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
        return Ingredient.of(RankineItems.VULCANIZED_RUBBER.get());
    }),
    DIVING("rankine:diving", 10, new int[]{2, 4, 5, 2}, 14, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, () -> {
        return Ingredient.of(RankineItems.BRASS_INGOT.get());
    }),
    CONDUIT_DIVING("rankine:conduit_diving", 24, new int[]{3, 5, 6, 3}, 16, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.1F, () -> {
        return Ingredient.of(Items.PRISMARINE,Items.PRISMARINE_CRYSTALS,Items.CONDUIT);
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    private RankineArmorMaterials(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.maxDamageFactor = maxDamageFactorIn;
        this.damageReductionAmountArray = damageReductionAmountsIn;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialSupplier);
    }

    public int getDurabilityForType(ArmorItem.Type slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getSlot().getIndex()] * this.maxDamageFactor;
    }

    public int getDefenseForType(ArmorItem.Type slotIn) {
        return this.damageReductionAmountArray[slotIn.getSlot().getIndex()];
    }
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}

