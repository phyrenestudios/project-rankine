package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.BasicTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RankineVillagerTrades {

    public static class EnchantedAlloyItemForEmeraldsTrade implements VillagerTrades.ItemListing {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;
        private final String alloyData;
        private final String alloyRecipe;
        private final String alloyNameOverride;

        public EnchantedAlloyItemForEmeraldsTrade(Item p_i50535_1_, String alloyData, String alloyRecipe, String alloyNameOverride,int emeraldCount, int maxUses, int xpValue) {
            this(p_i50535_1_, alloyData,alloyRecipe,alloyNameOverride, emeraldCount, maxUses, xpValue, 0.05F);
        }

        public EnchantedAlloyItemForEmeraldsTrade(Item sellItem, String alloyData, String alloyRecipe, String alloyNameOverride, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingStack = new ItemStack(sellItem);
            this.emeraldCount = emeraldCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.alloyData = alloyData;
            this.alloyRecipe = alloyRecipe;
            this.alloyNameOverride = alloyNameOverride;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            int i = 5 + rand.nextInt(15);
            ItemStack itemstack = EnchantmentHelper.enchantItem(rand, new ItemStack(this.sellingStack.getItem()), i, false);
            IAlloyItem.createDirectAlloyNBT(itemstack,alloyData,alloyRecipe,alloyNameOverride);
            int j = Math.min(this.emeraldCount + i, 64);
            ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
            return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class RandomItemFromTagForEmeraldsTrade implements VillagerTrades.ItemListing {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public RandomItemFromTagForEmeraldsTrade(ResourceLocation tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue) {
            this(tag, fallback, emeraldCount, amount, maxUses, xpValue, 0.05F);
        }

        public RandomItemFromTagForEmeraldsTrade(ResourceLocation tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
            Tag<Item> e = ItemTags.getAllTags().getTag(tag);
            if (e != null)
            {
                this.sellingStack = new ItemStack(e.getRandomElement(new Random()),amount);
            } else {
                this.sellingStack = new ItemStack(fallback,amount);
            }

            this.emeraldCount = emeraldCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD), sellingStack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static List<VillagerTrades.ItemListing> returnTagTrades(ResourceLocation tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue, float mult) {
        Tag<Item> e = ItemTags.getAllTags().getTag(tag);
        List<VillagerTrades.ItemListing> trades = new ArrayList<>();
        if (e != null) {
            for (Item item : e.getValues()) {
                trades.add(new BasicTrade(emeraldCount, new ItemStack(item,amount), maxUses, xpValue, mult));
            }
        } else {
            trades.add(new BasicTrade(emeraldCount, new ItemStack(fallback,amount), maxUses, xpValue, mult));
        }
        return trades;

    }
}
