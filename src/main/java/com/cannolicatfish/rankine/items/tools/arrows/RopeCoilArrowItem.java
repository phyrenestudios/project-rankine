package com.cannolicatfish.rankine.items.tools.arrows;

import com.cannolicatfish.rankine.entities.RopeCoilArrowEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RopeCoilArrowItem extends ArrowItem {
    public RopeCoilArrowItem(Item.Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new RopeCoilArrowEntity(worldIn, shooter);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new StringTextComponent("Firing this arrow with rope in the offhand deploys additional rope.").withStyle(TextFormatting.GRAY));
        } else {
            tooltip.add(new StringTextComponent("Hold shift for more information...").withStyle(TextFormatting.GRAY));
        }

    }
}
