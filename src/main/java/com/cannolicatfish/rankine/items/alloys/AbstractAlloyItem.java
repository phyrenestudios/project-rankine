package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class AbstractAlloyItem extends Item {

    private static Random rand = new Random();

    public AbstractAlloyItem() {
        super(new Item.Properties().group(ProjectRankine.setup.itemGroup).maxStackSize(64));
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add((new TranslationTextComponent("item.rankine." + "bronze_alloy").applyTextStyle(TextFormatting.GRAY)));
    }

}