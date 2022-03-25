package com.cannolicatfish.rankine.items;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
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
