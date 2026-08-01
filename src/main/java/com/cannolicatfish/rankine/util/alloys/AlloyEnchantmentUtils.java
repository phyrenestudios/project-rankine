package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.BlunderbussItem;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

public class AlloyEnchantmentUtils {

    public static List<Holder<Enchantment>> getAlloyEnchantments(AlloyingRecipe recipe, ItemStack stack, Level worldIn) {
        Registry<Enchantment> registry = worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> enchantments = new ArrayList<>();
        List<String> alloyEnchants = recipe.getEnchantments();
        List<String> alloyEnchantTypes = recipe.getEnchantmentTypes();
        for (int i = 0; i < alloyEnchants.size(); i++) {
            Holder<Enchantment> en;
            if (alloyEnchants.get(i).equals("rankine:random")) {
                List<EnchantmentInstance> dat = EnchantmentHelper.selectEnchantment(worldIn.getRandom(),stack,worldIn.getRandom().nextInt(40),registry.holders().map(h -> (Holder<Enchantment>) h));
                if (!dat.isEmpty()) {
                    en = dat.get(0).enchantment;
                } else {
                    en = registry.getHolderOrThrow(Enchantments.UNBREAKING);
                }
            } else {
                en = getHolder(registry, alloyEnchants.get(i));
            }
            if (en != null && matchesEnchantmentType(en,stack,EnchantmentTypes.valueOf(alloyEnchantTypes.get(i)))) {
                if (!enchantments.contains(en)) {
                    enchantments.add(en);
                }
            }
        }

        return enchantments;
    }

    public static List<Holder<Enchantment>> getElementEnchantments(List<ElementRecipe> elements, List<Integer> percents, ItemStack stack, Level worldIn) {
        Registry<Enchantment> registry = worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        List<Holder<Enchantment>> enchantments = new ArrayList<>();
        List<Float> enchantmentTotals = new ArrayList<>();
        for (int e = 0; e < elements.size(); e++) {
            ElementRecipe element = elements.get(e);
            int percent = percents.get(e);
            List<String> elementEnchants = element.getEnchantments();
            List<String> elementEnchantTypes = element.getEnchantmentTypes();
            List<Float> elementEnchantFactors = element.getEnchantmentFactors();

            for (int i = 0; i < elementEnchants.size(); i++) {
                Holder<Enchantment> en;
                if (elementEnchants.get(i).equals("rankine:random")) {
                    List<EnchantmentInstance> dat = EnchantmentHelper.selectEnchantment(worldIn.getRandom(),stack,1,registry.holders().map(h -> (Holder<Enchantment>) h));
                    if (!dat.isEmpty()) {
                        en = dat.get(0).enchantment;
                    } else {
                        en = registry.getHolderOrThrow(Enchantments.UNBREAKING);
                    }
                } else {
                    en = getHolder(registry, elementEnchants.get(i));
                }
                if (en != null && matchesEnchantmentType(en,stack,EnchantmentTypes.valueOf(elementEnchantTypes.get(i)))) {
                    if (!enchantments.contains(en)) {
                        enchantments.add(en);
                        enchantmentTotals.add(0f);
                    }
                    int index = enchantments.indexOf(en);
                    enchantmentTotals.set(index,enchantmentTotals.get(index) + elementEnchantFactors.get(i)*percent); // bound -1 to 1 in recipe class
                }
            }
        }

        List<Holder<Enchantment>> finalEnchantments = new ArrayList<>();
        for (int i = 0; i < enchantments.size(); i++) {
            if (enchantmentTotals.get(i) >= 1) {
                finalEnchantments.add(enchantments.get(i));
            }
        }

        return finalEnchantments;
    }

    @Nullable
    private static Holder<Enchantment> getHolder(Registry<Enchantment> registry, String id) {
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) {
            return null;
        }
        return registry.getHolder(rl).map(h -> (Holder<Enchantment>) h).orElse(null);
    }

    public static boolean matchesEnchantmentType(Holder<Enchantment> enchantmentHolder, ItemStack stack, EnchantmentTypes type) {
        Enchantment enchantment = enchantmentHolder.value();
        switch (type) {
            case ALL:
                return enchantment.canEnchant(stack);
            case TOOLS:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof DiggerItem || stack.getItem() instanceof SwordItem);
            case ARMOR:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ArmorItem);
            case PICKAXE:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof PickaxeItem);
            case AXE:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof AxeItem);
            case SPEAR:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof SpearItem);
            case SHOVEL:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ShovelItem);
            case HOE:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof HoeItem);
            case SWORD:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof SwordItem && !(stack.getItem() instanceof KnifeItem));
            case HAMMER:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof HammerItem);
            case KNIFE:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof KnifeItem);
            case CROWBAR:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof CrowbarItem);
            case HELMET:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ArmorItem) && ((ArmorItem) stack.getItem()).getType().getSlot() == EquipmentSlot.HEAD;
            case CHESTPLATE:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ArmorItem) && ((ArmorItem) stack.getItem()).getType().getSlot() == EquipmentSlot.CHEST;
            case LEGGINGS:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ArmorItem) && ((ArmorItem) stack.getItem()).getType().getSlot() == EquipmentSlot.LEGS;
            case BOOTS:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ArmorItem) && ((ArmorItem) stack.getItem()).getType().getSlot() == EquipmentSlot.FEET;
            case SHIELD:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof ShieldItem);
            case FISHING_ROD:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof FishingRodItem);
            case BOW:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof BowItem);
            case BLUNDERBUSS:
                return enchantment.canEnchant(stack) && (stack.getItem() instanceof BlunderbussItem);
            default:
                return enchantment.canEnchant(stack);
        }
    }

    public enum EnchantmentTypes {
        ALL,
        TOOLS,
        ARMOR,
        PICKAXE,
        AXE,
        SPEAR,
        SHOVEL,
        HOE,
        SWORD,
        HAMMER,
        KNIFE,
        CROWBAR,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        SHIELD,
        FISHING_ROD,
        BOW,
        BLUNDERBUSS

    }
}
