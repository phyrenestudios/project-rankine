package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.BlunderbussItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.Tags;

public class AlloyBlunderbussItem extends BlunderbussItem implements IAlloyTieredItem {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyBlunderbussItem(Tier tier, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
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
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
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
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
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
    public int calcHeatResistanceProc(ItemStack stack, LivingEntity entity, Random random) {
        float heatResist = getHeatResist(stack);
        int hr = IAlloyTieredItem.super.calcHeatResistanceProc(stack, entity, random);
        if ((random.nextFloat() > heatResist)) {
            return hr + Config.ALLOYS.ALLOY_HEAT_AMT.get();
        }
        return hr;
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
}
