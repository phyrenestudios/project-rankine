package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

// PORT-TODO(init): creative-tab entries — old fillItemCategory added a Curse of Vanishing enchanted
// variant (search tab + rankineTools tab) when Config.GENERAL.PENDANT_CURSE is set; move to RankineCreativeTabs.
public class SofteningTotemItem extends Item {
    public SofteningTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_softening.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            stack.enchant(worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.VANISHING_CURSE),1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
