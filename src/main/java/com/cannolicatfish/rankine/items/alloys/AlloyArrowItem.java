package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.AlloyArrowEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyArrowItem extends ArrowItem implements IAlloyProjectile {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyArrowItem(String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Item.Properties builder) {
        super(builder);
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
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new AlloyArrowEntity(worldIn, stack, shooter, getAlloyArrowDamage(stack));
    }



    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD));
            tooltip.add(new StringTextComponent("Damage: " + (getAlloyArrowDamage(stack))).mergeStyle(TextFormatting.GRAY));
        } else {
            tooltip.add(new StringTextComponent("Hold shift for details...").mergeStyle(TextFormatting.GRAY));
        }

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
