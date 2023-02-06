package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfusingTotemItem extends Item {
    public InfusingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("item.rankine.totem_of_infusing.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            stack.enchant(Enchantments.VANISHING_CURSE,1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if ((group == CreativeModeTab.TAB_SEARCH || group == ProjectRankine.setup.rankineTools) && Config.GENERAL.PENDANT_CURSE.get()) {
            ItemStack stack = new ItemStack(this);
            stack.enchant(Enchantments.VANISHING_CURSE,1);
            items.add(stack);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (worldIn.isClientSide) return super.use(worldIn, playerIn, handIn);

        InteractionHand oppHand = InteractionHand.values()[(handIn.ordinal() + 1) % InteractionHand.values().length];
        ItemStack oppHandStack = playerIn.getItemInHand(oppHand);
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter(enchantmentIntegerEntry -> !enchantmentIntegerEntry.getKey().isCurse())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (oppHandStack.isEnchanted() && map.size() != 0) return InteractionResultHolder.pass(stack);

        Map<Enchantment, Integer> output = EnchantmentHelper.getEnchantments(oppHandStack).entrySet().stream().filter(enchantmentIntegerEntry -> !enchantmentIntegerEntry.getKey().isCurse())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Enchantment[] en = output.keySet().toArray(new Enchantment[0]);
        if (en.length <= 0) return InteractionResultHolder.pass(stack);
        int index = en.length == 1 ? 0 : worldIn.getRandom().nextInt(en.length - 1);
        Enchantment key = en[index];
        stack.enchant(key, output.get(key));

        ItemStack oppNew = removeEnchantments(oppHandStack, oppHandStack.getDamageValue(), oppHandStack.getCount());
        int slot = playerIn.getInventory().findSlotMatchingItem(oppHandStack);
        playerIn.getInventory().removeItemNoUpdate(slot);
        playerIn.getInventory().setItem(slot, oppNew);
        worldIn.playSound(playerIn, playerIn.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResultHolder.success(stack);


    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    private ItemStack removeEnchantments(ItemStack stack, int damage, int count) {
        ItemStack itemstack = stack.copy();
        itemstack.removeTagKey("Enchantments");
        itemstack.removeTagKey("StoredEnchantments");
        if (damage > 0) {
            itemstack.setDamageValue(damage);
        } else {
            itemstack.removeTagKey("Damage");
        }

        itemstack.setCount(count);
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((p_217012_0_) -> {
            return p_217012_0_.getKey().isCurse();
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, itemstack);
        itemstack.setRepairCost(0);
        if (itemstack.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            itemstack = new ItemStack(Items.BOOK);
            if (stack.hasCustomHoverName()) {
                itemstack.setHoverName(stack.getHoverName());
            }
        }

        for(int i = 0; i < map.size(); ++i) {
            itemstack.setRepairCost(AnvilMenu.calculateIncreasedRepairCost(itemstack.getBaseRepairCost()));
        }

        return itemstack;
    }
}
