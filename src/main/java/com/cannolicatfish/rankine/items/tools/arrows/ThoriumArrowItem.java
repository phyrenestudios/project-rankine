package com.cannolicatfish.rankine.items.tools.arrows;

import com.cannolicatfish.rankine.entities.ThoriumArrowEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class ThoriumArrowItem extends ArrowItem {
    public ThoriumArrowItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        ThoriumArrowEntity arrowentity = new ThoriumArrowEntity(worldIn, shooter);
        return arrowentity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent("Summons lightning upon landing. Does not require rain.").withStyle(TextFormatting.GRAY));
        } else {
            tooltip.add(new StringTextComponent("Hold shift for more information...").withStyle(TextFormatting.GRAY));
        }

    }
}
