package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class PowerCellItem extends Item {
    public PowerCellItem(Properties properties) {
        super(properties);
    }

    public static CompoundNBT getPowerCellNBT(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundNBT();
    }

    public static void addPowerCellNBT(ItemStack stack, Integer value) {
        stack.getOrCreateTag().putInt("EnergyVal",value);
    }

    public static int calcElementsToPower(PeriodicTableUtils.Element e1, PeriodicTableUtils.Element e2) {
        float p1 = e1.element.getElectrodePotentialFromPercent(0);
        float p2 = e2.element.getElectrodePotentialFromPercent(0);

        if ((p1 > 0 && p2 > 0) || (p1 < 0 && p2 < 0))
        {
            return 0;
        } else {
            return Math.round(Math.abs(p1 + p2)/7.371f * 5000);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getPowerCellNBT(stack).size() != 0) {
            tooltip.add(new StringTextComponent(getPowerCellNBT(stack).get("EnergyVal").getString() + " ticks").mergeStyle(TextFormatting.GRAY));
        }
    }
}

