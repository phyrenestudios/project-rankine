package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.recipe.AlloyModifierRecipe;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyModifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlloyArmorItem extends DyeableArmorItem implements IAlloyTieredItem, IDyeableArmorItem, IAlloyNeedsRegenerate {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Properties builderIn) {
        super(materialIn, slot, builderIn);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent(IAlloyItem.getNameOverride(stack)));
        }
        TranslationTextComponent translation = new TranslationTextComponent(this.getTranslationKey(stack));
        if (translation.getString().contains("%1$s")) {
            return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent("item.rankine.custom_alloy_default"));
        } else {
            return super.getDisplayName(stack);
        }
    }
    @Override
    public void initStats(ItemStack stack, Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloyRecipe, @Nullable AlloyModifierRecipe alloyModifier) {
        CompoundNBT listnbt = new CompoundNBT();
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
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getDamage(stack) * 1f / this.getAlloyDurability(stack);
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
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    private int createValueForArmorDurability(Map<ElementRecipe, Integer> elementMap, @Nullable AlloyingRecipe alloy, @Nullable AlloyModifier modifier) {
        int durability = IAlloyTieredItem.super.createValueForDurability(elementMap, alloy, modifier);
        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        if (durability <= 100) {
            return Math.round(MAX_DAMAGE_ARRAY[this.getEquipmentSlot().getIndex()] * durability/10f);
        } else {
            return Math.round(MAX_DAMAGE_ARRAY[this.getEquipmentSlot().getIndex()] * (10 + (durability-100)/50f));
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
        EquipmentSlotType slotType = this.getEquipmentSlot();
        int hl = createValueForHarvestLevel(elementMap,alloy,modifier);
        int base = slotType == EquipmentSlotType.CHEST ? 3 : slotType == EquipmentSlotType.LEGS ? 2 : 1;
        if (slotType == EquipmentSlotType.CHEST || slotType == EquipmentSlotType.LEGS) {
            return Math.min(base + hl,10);
        } else if (slotType == EquipmentSlotType.FEET) {
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

    /*@Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            World worldIn = ProjectRankine.proxy.getClientWorld();
            if (worldIn != null) {
                List<ICraftingRecipe> s = worldIn.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING).stream().filter(iCraftingRecipe -> iCraftingRecipe.getRecipeOutput().getItem() == this.getItem()).collect(Collectors.toList());
                for (ICraftingRecipe recipe : s) {
                    if (recipe instanceof AlloyCraftingRecipe && !items.contains(recipe.getRecipeOutput())) {
                        items.add(recipe.getRecipeOutput());
                    }
                }
            } else {
                items.add(getAlloyItemStack(new AlloyData(this.alloy.getDefComposition()),this.getItem()));
            }

            items.add(getAlloyItemStack(new AlloyData(this.alloy.getDefComposition()),this.getItem()));
        }
    }

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
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        }
        if (!hasColor(stack)) {
            setColor(stack,AlloyColorHelper.getColor(stack,0));
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 10511680;
    }

    @Override
    public void removeColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("color")) {
            compoundnbt.remove("color");
        }

    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color", color);
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
