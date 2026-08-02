package com.cannolicatfish.rankine.items.tools.arrows;

import com.cannolicatfish.rankine.entities.RopeCoilArrowEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RopeCoilArrowItem extends ArrowItem {
    public RopeCoilArrowItem(Item.Properties builder) {
        super(builder);
    }

    // PORT-TODO(entities): RopeCoilArrowEntity ctor kept at (Level, LivingEntity); 1.21.1 arrows also
    // carry a pickup ItemStack and an optional firedFromWeapon stack.
    @Override
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new RopeCoilArrowEntity(worldIn, shooter);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.literal("Firing this arrow with rope in the offhand deploys additional rope.").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.literal("Hold shift for more information...").withStyle(ChatFormatting.GRAY));
        }

    }
}
