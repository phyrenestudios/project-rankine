package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.entities.AlloyArrowEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return Component.translatable(this.getDescriptionId(stack),Component.translatable(IAlloyItem.getNameOverride(stack)));
        }
        return Component.translatable(this.getDescriptionId(stack),Component.translatable(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    /**
     * 1.21.1 hands the firing weapon in as {@code weapon} and every bow/crossbow enchantment is
     * gated on the arrow entity storing it (see {@link AlloyArrowEntity}); vanilla
     * {@code ArrowItem#createArrow} passes it straight through, so this does too. Dropping it — as
     * the first port pass did — cost alloy arrows Power, Punch and Flame from bows and Piercing
     * from crossbows, leaving them strictly worse than vanilla arrows in enchanted gear.
     */
    @Override
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new AlloyArrowEntity(worldIn, stack, shooter, getAlloyArrowDamage(stack), weapon);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,context.level(),tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,context.level(),tooltip,flagIn);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    // PORT-TODO(init): fillItemCategory is gone — dynamic creative-tab variants
    // (AlloyCustomHelper.getItemsFromAlloying/getItemsFromAlloyCrafting when defaultAlloyRecipe == null)
    // must be added in RankineCreativeTabs' displayItems.

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
    }
}
