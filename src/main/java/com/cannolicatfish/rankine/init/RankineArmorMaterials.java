package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * 1.21: {@link ArmorMaterial} is a registry object. These replace the old
 * items/RankineArmorMaterials enum (same material names and stats).
 * Old per-slot durability factors (durability = factor * ArmorItem.Type#getDurability(1)):
 * BRIGANDINE 20, ALLOY 10, RUBBER 15, DIVING 10, CONDUIT_DIVING 24 — apply them at item
 * registration via Properties.durability(type.getDurability(factor)).
 */
public class RankineArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, ProjectRankine.MODID);

    /**
     * Old durability factor per material. Applied at item registration in RankineItems as
     * Properties.durability(Type.getDurability(factor)) -- 1.21's ArmorItem constructor does not do
     * it for you, and that call is also what sets MAX_STACK_SIZE=1.
     */
    public static final int BRIGANDINE_DURABILITY_FACTOR = 20;
    public static final int ALLOY_DURABILITY_FACTOR = 10;
    public static final int RUBBER_DURABILITY_FACTOR = 15;
    public static final int DIVING_DURABILITY_FACTOR = 10;
    public static final int CONDUIT_DIVING_DURABILITY_FACTOR = 24;
    /** Vanilla ArmorMaterials.LEATHER's factor, for the pieces built on it (sandals, snowshoes, ice skates). */
    public static final int LEATHER_DURABILITY_FACTOR = 5;

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRIGANDINE = register("brigandine",
            new int[]{3, 6, 8, 3}, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, false,
            () -> Ingredient.of(Items.LEATHER, RankineItems.STEEL_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ALLOY = register("alloy",
            new int[]{0, 0, 0, 0}, 0, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, true,
            () -> Ingredient.of(RankineItems.ALLOY_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RUBBER = register("rubber",
            new int[]{1, 3, 5, 2}, 7, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, false,
            () -> Ingredient.of(RankineItems.VULCANIZED_RUBBER.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DIVING = register("diving",
            new int[]{2, 4, 5, 2}, 14, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, false,
            () -> Ingredient.of(RankineItems.BRASS_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CONDUIT_DIVING = register("conduit_diving",
            new int[]{3, 5, 6, 3}, 16, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.1F, false,
            () -> Ingredient.of(Items.PRISMARINE, Items.PRISMARINE_CRYSTALS, Items.CONDUIT));

    /**
     * The worn-armor render passes for one material.
     *
     * <p>1.18.2's {@code HumanoidArmorLayer#renderArmorPiece} branched on
     * {@code armoritem instanceof DyeableArmorItem} and, on that branch, rendered the piece TWICE:
     * {@code <name>_layer_N.png} tinted with {@code getColor(stack)}, then
     * {@code <name>_layer_N_overlay.png} untinted at (1,1,1). 1.21 expresses the same thing as a
     * list of {@link ArmorMaterial.Layer}s — {@code dyeable} selects the tint, the suffix selects
     * the texture — so the dyeable material (ALLOY) needs both entries or the second pass is lost.
     * Non-dyeable materials keep the single untinted pass they had in 1.18.2; they ship no
     * {@code _overlay} texture and a second layer would resolve to a missing one.
     *
     * <p>Known and deliberate: {@code alloy_layer_1_overlay.png} and {@code alloy_layer_2_overlay.png}
     * are 64x32 images with zero non-transparent pixels, so with the shipped assets this second pass
     * draws nothing — exactly as in 1.18.2, where it also drew nothing. It is restored for render
     * parity and because a resource pack may supply real overlay textures, which 1.18.2 honoured.
     */
    private static List<ArmorMaterial.Layer> layers(String name, boolean dyeable) {
        ResourceLocation asset = ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name);
        return dyeable
                ? List.of(new ArmorMaterial.Layer(asset, "", true),
                          new ArmorMaterial.Layer(asset, "_overlay", false))
                : List.of(new ArmorMaterial.Layer(asset, "", false));
    }

    /**
     * @param defense per-slot defense in old array order [boots, leggings, chestplate, helmet]
     *                (indexed by EquipmentSlot#getIndex, like the 1.18.2 enum).
     */
    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, int[] defense, int enchantability,
                                                                         net.minecraft.core.Holder<net.minecraft.sounds.SoundEvent> equipSound,
                                                                         float toughness, float knockbackResistance, boolean dyeable,
                                                                         Supplier<Ingredient> repairIngredient) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.BOOTS, defense[0]);
                    map.put(ArmorItem.Type.LEGGINGS, defense[1]);
                    map.put(ArmorItem.Type.CHESTPLATE, defense[2]);
                    map.put(ArmorItem.Type.HELMET, defense[3]);
                    map.put(ArmorItem.Type.BODY, defense[2]);
                }),
                enchantability,
                equipSound,
                repairIngredient,
                layers(name, dyeable),
                toughness,
                knockbackResistance));
    }
}
