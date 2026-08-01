package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.BatteryItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

// PORT-TODO(init): creative-tab entries — old fillItemCategory added a Curse of Vanishing enchanted
// variant (search tab + rankineTools tab) when Config.GENERAL.PENDANT_CURSE is set; move to RankineCreativeTabs.
public class PoweringTotemItem extends Item {
    public PoweringTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_powering.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
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

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player living) {
            if (living.hasEffect(MobEffects.CONDUIT_POWER) && (living.getMainHandItem().equals(stack) || living.getOffhandItem().equals(stack))) {
                if (!living.hasEffect(MobEffects.DOLPHINS_GRACE)) {
                    living.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,900));
                }
                if (worldIn.getDayTime() % 400 == 0) {
                    for (ItemStack s : living.getInventory().items) {
                        if (s.getItem() instanceof BatteryItem && s.getDamageValue() > 0) {
                            s.setDamageValue(s.getDamageValue() - 1);
                        }
                    }
                }

            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
