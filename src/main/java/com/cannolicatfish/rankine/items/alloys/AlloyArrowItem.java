package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.AlloyArrowEntity;
import com.cannolicatfish.rankine.entities.MagnesiumArrowEntity;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyArrowItem extends ArrowItem implements IAlloyArrow {
    private final AlloyUtils alloy;
    public AlloyArrowItem(AlloyUtils alloy, Item.Properties builder) {
        super(builder);
        this.alloy = alloy;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au") && nbt != null && !nbt.getString("nameAdd").isEmpty() && !nbt.getString("nameAdd").equals("false")) {
            String name = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
            String[] sp = name.split(" ");
            if (sp.length > 0) {
                name = sp[sp.length - 1];
            }
            return new StringTextComponent(stack.getTag().getString("nameAdd") + " " + name);
        }
        return super.getDisplayName(stack);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new AlloyArrowEntity(worldIn, stack, shooter, getAlloyArrowDamage(returnCompositionString(stack,this.alloy),this.alloy));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent("Composition: " + returnCompositionString(stack,this.alloy)).mergeStyle(alloy.getAlloyGroupColor()));
            tooltip.add(new StringTextComponent("Damage: " + (getAlloyArrowDamage(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.GRAY));
        } else {
            tooltip.add(new StringTextComponent("Hold shift for details...").mergeStyle(TextFormatting.GRAY));
        }

    }

    @Override
    public AlloyUtils returnAlloyUtils() {
        return alloy;
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            items.add(getAlloyItemStack(new AlloyData(alloy.getDefComposition()),this.getItem()));
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
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au")) {
            CompoundNBT nbt = stack.getTag();
            if (nbt != null && nbt.getString("nameAdd").isEmpty()) {
                nbt.putString("nameAdd", AlloyRecipeHelper.getAlloyFromComposition(getComposition(stack).getCompound(0).get("comp").getString(),worldIn));
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
