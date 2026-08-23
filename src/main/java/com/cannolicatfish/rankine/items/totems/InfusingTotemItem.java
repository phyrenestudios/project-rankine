package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class InfusingTotemItem extends Item {
    public InfusingTotemItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.rankine.totem_of_infusing.tooltip").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            worldIn.registryAccess().registry(Registries.ENCHANTMENT)
                    .flatMap(reg -> reg.getHolder(Enchantments.VANISHING_CURSE))
                    .ifPresent(holder -> stack.enchant(holder, 1));
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    // PORT-TODO(init): fillItemCategory is gone — when Config.GENERAL.PENDANT_CURSE is enabled the
    // creative-tab entry for this item must be a stack pre-enchanted with minecraft:vanishing_curse
    // (add it in RankineCreativeTabs' displayItems).

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (worldIn.isClientSide) return super.use(worldIn, playerIn, handIn);

        InteractionHand oppHand = InteractionHand.values()[(handIn.ordinal() + 1) % InteractionHand.values().length];
        ItemStack oppHandStack = playerIn.getItemInHand(oppHand);
        ItemEnchantments own = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        List<Holder<Enchantment>> ownNonCurses = new ArrayList<>();
        for (Holder<Enchantment> holder : own.keySet()) {
            if (!holder.is(EnchantmentTags.CURSE)) ownNonCurses.add(holder);
        }

        if (oppHandStack.isEnchanted() && !ownNonCurses.isEmpty()) return InteractionResultHolder.pass(stack);

        ItemEnchantments oppEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(oppHandStack);
        List<Holder<Enchantment>> en = new ArrayList<>();
        for (Holder<Enchantment> holder : oppEnchantments.keySet()) {
            if (!holder.is(EnchantmentTags.CURSE)) en.add(holder);
        }
        if (en.isEmpty()) return InteractionResultHolder.pass(stack);
        int index = en.size() == 1 ? 0 : worldIn.getRandom().nextInt(en.size() - 1);
        Holder<Enchantment> key = en.get(index);
        stack.enchant(key, oppEnchantments.getLevel(key));

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

    /**
     * The ANVIL / enchanted-book path. Kept per decision 3B ("allow BOTH"), even though 1.18.2's
     * {@code canEnchant} allowed nothing here — {@code isBookEnchantable} below still refuses the
     * book half, so this only opens what an anvil can otherwise apply.
     */
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }

    /**
     * The ENCHANTING TABLE path — the one 1.18.2 had.
     * {@code InfusingTotemItem#canApplyAtEnchantingTable} (InfusingTotemItem.java:93 at
     * {@code 859f87773}) returned true for EVERY enchantment, which is the whole point of the item:
     * the totem soaks up an arbitrary enchantment at a table and hands it on. 1.21 routes the table
     * through {@code isPrimaryItemFor}, which the port never overrode, so the default's
     * {@code primary_items} check silently narrowed the totem to whatever each enchantment happens
     * to declare. Unconditional true restores it, matching {@code supportsEnchantment} above.
     */
    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    /** Mirrors GrindstoneMenu#removeNonCursesFrom — keeps curses, clears everything else. */
    private ItemStack removeEnchantments(ItemStack stack, int damage, int count) {
        ItemStack itemstack = stack.copy();
        ItemEnchantments remaining = EnchantmentHelper.updateEnchantments(itemstack,
                mutable -> mutable.removeIf(holder -> !holder.is(EnchantmentTags.CURSE)));
        itemstack.remove(DataComponents.STORED_ENCHANTMENTS);
        if (damage > 0) {
            itemstack.setDamageValue(damage);
        } else {
            itemstack.remove(DataComponents.DAMAGE);
        }

        itemstack.setCount(count);
        if (itemstack.is(Items.ENCHANTED_BOOK) && remaining.isEmpty()) {
            ItemStack book = itemstack.transmuteCopy(Items.BOOK);
            if (stack.has(DataComponents.CUSTOM_NAME)) {
                book.set(DataComponents.CUSTOM_NAME, stack.getHoverName());
            }
            itemstack = book;
        }

        int repairCost = 0;
        for(int i = 0; i < remaining.size(); ++i) {
            repairCost = AnvilMenu.calculateIncreasedRepairCost(repairCost);
        }
        itemstack.set(DataComponents.REPAIR_COST, repairCost);

        return itemstack;
    }
}
