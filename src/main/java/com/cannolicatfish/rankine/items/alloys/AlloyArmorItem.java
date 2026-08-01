// PORT-TODO(init): new ctor signature —
//   AlloyArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, String defaultComposition,
//                  @Nullable ResourceLocation defaultAlloyRecipe, Item.Properties props)
// (was (ArmorMaterial, EquipmentSlot, String, ResourceLocation, Properties)). Pass
// RankineArmorMaterials.ALLOY and the matching ArmorItem.Type; durability comes from the
// dynamic alloy stats so no Properties.durability(...) is needed for the alloy set.
package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyStats;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

// PORT-TODO(client): alloy armor was DyeableArmorItem — the per-stack tint now lives in
// RankineDataComponents.COLOR and needs an ItemColor handler registered in
// RegisterColorHandlersEvent.Item (client agent) using AlloyArmorItem#getColor.
public class AlloyArmorItem extends ArmorItem implements IAlloyTieredItem {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyArmorItem(Holder<ArmorMaterial> materialIn, ArmorItem.Type type, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Properties builderIn) {
        super(materialIn, type, builderIn);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return Component.translatable(this.getDescriptionId(stack),Component.translatable(IAlloyItem.getNameOverride(stack)));
        }
        return Component.translatable(this.getDescriptionId(stack),Component.translatable(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    @Override
    public void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        // was NBT "StoredAlloyStats" {durability, armorToughness, damageResistance, enchantability,
        // corrResist, heatResist, knockbackResist}
        AlloyStats stats = new AlloyStats(
                createValueForArmorDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)),
                0f,
                0,
                createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)),
                0f,
                0f,
                createValueForCorrosionResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.CORROSION_RESISTANCE)),
                createValueForHeatResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HEAT_RESISTANCE)),
                0f,
                createValueForArmorToughness(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.TOUGHNESS)),
                createValueForDamageResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HARVEST_LEVEL)),
                createValueForKnockbackResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.KNOCKBACK_RESISTANCE)),
                0f);
        applyAlloyStats(stack, stats);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.is(Tags.Items.INGOTS) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = String.valueOf(IAlloyItem.getAlloyRecipe(repair));
            String r2 = String.valueOf(IAlloyItem.getAlloyRecipe(toRepair));
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }


    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    public void addAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((Component.literal("Hold shift for details...")).withStyle(ChatFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((Component.literal("Any Composition").withStyle(ChatFormatting.GOLD)));
                } else {
                    tooltip.add((Component.literal("Composition: " + IAlloyItem.getAlloyComposition(stack)).withStyle(ChatFormatting.GOLD)));
                }

                if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                    tooltip.add((Component.literal("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).get(0).name())).withStyle(ChatFormatting.AQUA)));
                } else {
                    tooltip.add((Component.literal("No Modifiers Present").withStyle(ChatFormatting.AQUA)));
                }

                if (!this.needsRefresh(stack)) {

                    tooltip.add((Component.literal("Durability: " + (getAlloyDurability(stack) - stack.getDamageValue()) + "/" + getAlloyDurability(stack))).withStyle(ChatFormatting.DARK_GREEN));
                    tooltip.add((Component.literal("Enchantability: " + getAlloyEnchantability(stack))).withStyle(ChatFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get()) {
                        tooltip.add((Component.literal("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get()) {
                        tooltip.add((Component.literal("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,context.level(),tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,context.level(),tooltip,flagIn);
        }
    }

    private int createValueForArmorDurability(Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int durability = IAlloyTieredItem.super.createValueForDurability(elementMap, alloy, modifier);
        // vanilla ArmorItem.Type#getDurability(1) equals the old MAX_DAMAGE_ARRAY {13,15,16,11}
        // indexed by EquipmentSlot#getIndex (boots, leggings, chestplate, helmet).
        int slotFactor = this.getType().getDurability(1);
        if (durability <= 100) {
            return Math.round(slotFactor * durability/10f);
        } else {
            return Math.round(slotFactor * (10 + (durability-100)/50f));
        }
    }

    private int createValueForArmorToughness(Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier)
    {
        float tough = createValueForToughness(elementMap,alloy,modifier);
        if (tough >= 0.4) {
            return 4;
        } else if (tough >= 0.3) {
            return 3;
        } else if (tough >= 0.2){
            return 2;
        } else if (tough >= 0.1){
            return 1;
        } else {
            return 0;
        }
    }

    private int createValueForDamageResistance(Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier)
    {
        EquipmentSlot slotType = this.getType().getSlot();
        int hl = createValueForHarvestLevel(elementMap,alloy,modifier);
        int base = slotType == EquipmentSlot.CHEST ? 3 : slotType == EquipmentSlot.LEGS ? 2 : 1;
        if (slotType == EquipmentSlot.CHEST || slotType == EquipmentSlot.LEGS) {
            return Math.min(base + hl,10);
        } else if (slotType == EquipmentSlot.FEET) {
            if (hl >= 5) {
                return base + 2;
            } else if (hl >= 3) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            if (hl >= 5) {
                return base + 3;
            } else if (hl >= 3) {
                return base + 2;
            } else if (hl >= 1) {
                return base + 1;
            } else {
                return base;
            }
        }
    }

    public int getAlloyArmorToughness(ItemStack stack)
    {
        AlloyStats stats = getAlloyStats(stack);
        return stats != null ? stats.armorToughness() : 0;
    }

    public int getAlloyDamageResistance(ItemStack stack)
    {
        AlloyStats stats = getAlloyStats(stack);
        return stats != null ? stats.damageResistance() : 1;
    }

    // PORT-TODO(init): fillItemCategory is gone — dynamic creative-tab variants
    // (AlloyCustomHelper.getItemsFromAlloying/getItemsFromAlloyCrafting when defaultAlloyRecipe == null)
    // must be added in RankineCreativeTabs' displayItems.

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        }
        if (!hasCustomColor(stack)) {
            setColor(stack,AlloyColorHelper.getColor(stack,0));
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    /** was NBT "color" (int tag id 99) on a DyeableArmorItem. */
    public boolean hasCustomColor(ItemStack stack) {
        return stack.has(RankineDataComponents.COLOR.get());
    }

    public int getColor(ItemStack stack) {
        return stack.getOrDefault(RankineDataComponents.COLOR.get(), 10511680);
    }

    public void clearColor(ItemStack stack) {
        stack.remove(RankineDataComponents.COLOR.get());
    }

    public void setColor(ItemStack stack, int color) {
        stack.set(RankineDataComponents.COLOR.get(), color);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        Level world = wearer.getCommandSenderWorld();
        Optional<RecipeHolder<?>> gold = world.getRecipeManager().byKey(ResourceLocation.parse("rankine:elements/gold"));
        if (gold.isPresent() && gold.get().value() instanceof ElementRecipe g) {
            return this.checkCompositionRequirement(stack,world,g,">=",60);
        }
        return super.makesPiglinsNeutral(stack,wearer);
    }

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
    }
}
