package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.items.alloys.AlloyData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyTemplate extends Item {
    public AlloyTemplate(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (getTemplate(stack).size() != 0) {
            String comp = getTemplate(stack).get("Name").getString();
            return new StringTextComponent(comp + new TranslationTextComponent(this.getTranslationKey(stack)));
        } else {
            return new TranslationTextComponent(this.getTranslationKey(stack));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getTemplate(stack).size() != 0) {
            String comp = getTemplate(stack).get("StoredTemplate").getString();
            tooltip.add(new StringTextComponent(comp).mergeStyle(TextFormatting.GRAY));
        }
    }

    public static CompoundNBT getTemplate(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundNBT();
    }

    public static void addTemplate(ItemStack stack, String data, String name) {
        stack.getOrCreateTag().putString("StoredTemplate",data);
        stack.getOrCreateTag().putString("Name",name);
    }
}
