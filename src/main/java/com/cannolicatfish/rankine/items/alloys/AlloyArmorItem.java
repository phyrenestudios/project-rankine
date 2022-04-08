package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

public class AlloyArmorItem extends DyeableArmorItem implements IAlloyTieredItem, DyeableLeatherItem {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Properties builderIn) {
        super(materialIn, slot, builderIn);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }
    @Override
    public void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        CompoundTag listnbt = new CompoundTag();
        listnbt.putInt("durability",createValueForArmorDurability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.DURABILITY)));
        listnbt.putInt("armorToughness",createValueForArmorToughness(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.TOUGHNESS)));
        listnbt.putInt("damageResistance",createValueForDamageResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HARVEST_LEVEL)));
        listnbt.putInt("enchantability",createValueForEnchantability(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.ENCHANTABILITY)));
        listnbt.putFloat("corrResist",createValueForCorrosionResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.CORROSION_RESISTANCE)));
        listnbt.putFloat("heatResist",createValueForHeatResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.HEAT_RESISTANCE)));
        listnbt.putFloat("knockbackResist",createValueForKnockbackResistance(elementMap,alloyRecipe,getModifierForStat(alloyModifier, AlloyModifier.ModifierType.KNOCKBACK_RESISTANCE)));
        stack.getOrCreateTag().put("StoredAlloyStats", listnbt);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.is(Tags.Items.INGOTS) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }


    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    public void addAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack)) {
            if (!Screen.hasShiftDown()) {
                tooltip.add((new TextComponent("Hold shift for details...")).withStyle(ChatFormatting.GRAY));
            }
            if (Screen.hasShiftDown()) {
                if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                    tooltip.add((new TextComponent("Any Composition").withStyle(ChatFormatting.GOLD)));
                } else {
                    tooltip.add((new TextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).withStyle(ChatFormatting.GOLD)));
                }

                if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                    tooltip.add((new TextComponent("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).getCompound(0).getString("modifierName"))).withStyle(ChatFormatting.AQUA)));
                } else {
                    tooltip.add((new TextComponent("No Modifiers Present").withStyle(ChatFormatting.AQUA)));
                }

                if (!this.needsRefresh(stack)) {

                    tooltip.add((new TextComponent("Durability: " + (getAlloyDurability(stack) - stack.getDamageValue()) + "/" + getAlloyDurability(stack))).withStyle(ChatFormatting.DARK_GREEN));
                    tooltip.add((new TextComponent("Enchantability: " + getAlloyEnchantability(stack))).withStyle(ChatFormatting.GRAY));
                    if (Config.ALLOYS.ALLOY_CORROSION.get()) {
                        tooltip.add((new TextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                    if (Config.ALLOYS.ALLOY_HEAT.get()) {
                        tooltip.add((new TextComponent("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    private int createValueForArmorDurability(Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int durability = IAlloyTieredItem.super.createValueForDurability(elementMap, alloy, modifier);
        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        if (durability <= 100) {
            return Math.round(MAX_DAMAGE_ARRAY[this.getSlot().getIndex()] * durability/10f);
        } else {
            return Math.round(MAX_DAMAGE_ARRAY[this.getSlot().getIndex()] * (10 + (durability-100)/50f));
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
        EquipmentSlot slotType = this.getSlot();
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
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("armorToughness");
        } else {
            return 0;
        }

    }

    public int getAlloyDamageResistance(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloyStats").getInt("damageResistance");
        } else {
            return 1;
        }

    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group) && this.defaultAlloyRecipe == null) {
            items.addAll(AlloyCustomHelper.getItemsFromAlloying(this));
            items.addAll(AlloyCustomHelper.getItemsFromAlloyCrafting(this));
        } else if (this.allowdedIn(group)) {
            super.fillItemCategory(group,items);
        }
    }

    /*

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }
        if (!hasColor(stack)) {
            setColor(stack,new AlloyColorHelper().getColor(stack,0));
        }
        super.onCreated(stack, worldIn, playerIn);
    }*/

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

    @Override
    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    @Override
    public void clearColor(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("color")) {
            compoundnbt.remove("color");
        }

    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color", color);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        Level world = wearer.getCommandSenderWorld();
        Optional<? extends Recipe<?>> gold = world.getRecipeManager().byKey(new ResourceLocation("rankine:elements/gold"));
        if (gold.isPresent() && gold.get() instanceof ElementRecipe) {
            ElementRecipe g = (ElementRecipe) gold.get();
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
