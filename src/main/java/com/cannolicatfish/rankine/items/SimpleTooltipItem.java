package com.cannolicatfish.rankine.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class SimpleTooltipItem extends Item {
    private final int tooltipCount;
    public SimpleTooltipItem(int tooltipCount, Properties properties) {
        super(properties);
        this.tooltipCount = tooltipCount;
    }

    @Deprecated
    public SimpleTooltipItem(List<String> stringList, Properties properties) {
        super(properties);
        this.tooltipCount = stringList.size();
    }

    @Deprecated
    public SimpleTooltipItem(String stringList, Properties properties) {
        super(properties);
        this.tooltipCount = 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     /*   if (Screen.hasShiftDown()) {
            for (int i = 0; i < tooltipCount; i++) {
                tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".tooltip" + i).mergeStyle(TextFormatting.GRAY));
            }
    //    } else {
   //         tooltip.add(new StringTextComponent("Hold shift for information...").mergeStyle(TextFormatting.GRAY));
 //       }*/
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
