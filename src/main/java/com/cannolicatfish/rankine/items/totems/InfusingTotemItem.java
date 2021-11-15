package com.cannolicatfish.rankine.items.totems;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.GrindstoneContainer;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InfusingTotemItem extends Item {
    public InfusingTotemItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.rankine.totem_of_infusing.tooltip").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (Config.GENERAL.PENDANT_CURSE.get()) {
            stack.addEnchantment(Enchantments.VANISHING_CURSE,1);
        }
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if ((group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) && Config.GENERAL.PENDANT_CURSE.get()) {
            ItemStack stack = new ItemStack(this.getItem());
            stack.addEnchantment(Enchantments.VANISHING_CURSE,1);
            items.add(stack);
        } else {
            super.fillItemGroup(group, items);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            Hand oppHand = Hand.values()[(handIn.ordinal() + 1) % Hand.values().length];
            ItemStack oppHandStack = playerIn.getHeldItem(oppHand);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter(enchantmentIntegerEntry -> !enchantmentIntegerEntry.getKey().isCurse())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (oppHandStack.isEnchanted() && map.size() == 0) {
                Map<Enchantment, Integer> output = EnchantmentHelper.getEnchantments(oppHandStack).entrySet().stream().filter(enchantmentIntegerEntry -> !enchantmentIntegerEntry.getKey().isCurse())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                Enchantment[] en = output.keySet().toArray(new Enchantment[0]);
                if (en.length > 0) {
                    int index = en.length == 1 ? 0 : worldIn.getRandom().nextInt(en.length - 1);
                    Enchantment key = en[index];
                    stack.addEnchantment(key,output.get(key));

                    ItemStack oppNew = removeEnchantments(oppHandStack, oppHandStack.getDamage(),oppHandStack.getCount());
                    int slot = playerIn.inventory.getSlotFor(oppHandStack);
                    playerIn.inventory.removeStackFromSlot(slot);
                    playerIn.inventory.setInventorySlotContents(slot,oppNew);
                    worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    return ActionResult.resultSuccess(stack);
                }
                /*
                if (!oppHandStack.isEnchanted() && oppHandStack.isEnchantable() && map.size() > 0) {
                Enchantment[] en = map.keySet().toArray(new Enchantment[0]);
                int index = en.length == 1 ? 0 : worldIn.getRandom().nextInt(en.length - 1);
                Enchantment key = en[index];
                if (key.canApply(oppHandStack)) {
                    oppHandStack.addEnchantment(key,map.get(key));
                    oppHandStack.getOrCreateTag().putString("infusingTotemEnchant",key.getRegistryName().toString());
                }

                 */
            }
            return ActionResult.resultPass(stack);
        }
        return super.onItemRightClick(worldIn,playerIn,handIn);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
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
        itemstack.removeChildTag("Enchantments");
        itemstack.removeChildTag("StoredEnchantments");
        if (damage > 0) {
            itemstack.setDamage(damage);
        } else {
            itemstack.removeChildTag("Damage");
        }

        itemstack.setCount(count);
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((p_217012_0_) -> {
            return p_217012_0_.getKey().isCurse();
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, itemstack);
        itemstack.setRepairCost(0);
        if (itemstack.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            itemstack = new ItemStack(Items.BOOK);
            if (stack.hasDisplayName()) {
                itemstack.setDisplayName(stack.getDisplayName());
            }
        }

        for(int i = 0; i < map.size(); ++i) {
            itemstack.setRepairCost(RepairContainer.getNewRepairCost(itemstack.getRepairCost()));
        }

        return itemstack;
    }
}
