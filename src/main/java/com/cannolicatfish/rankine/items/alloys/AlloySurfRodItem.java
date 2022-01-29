package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AlloySurfRodItem extends FishingRodItem implements IAlloyTieredItem {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloySurfRodItem(IItemTier tier, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Properties properties) {
        super(properties);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
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

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCreated(stack, worldIn, playerIn);
    }


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
}
