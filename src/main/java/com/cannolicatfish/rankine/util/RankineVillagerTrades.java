package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.common.BasicItemListing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PORT-NOTE (1.21.1): {@code VillagerTrades.ItemListing#getOffer} now takes a {@link RandomSource}
 * instead of {@code java.util.Random}, {@code MerchantOffer} takes an {@link ItemCost} for the price,
 * and tag lookups go through {@link BuiltInRegistries} instead of {@code ForgeRegistries#tags()}.
 * Prices, max uses, xp and price multipliers are unchanged.
 */
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

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            int i = 5 + rand.nextInt(15);
            // PORT-NOTE: the old `enchantItem(rand, stack, level, false)` (treasure = false) is replaced by the
            // 1.21.1 form that selects from the `minecraft:on_traded_equipment` enchantment tag, which is what
            // vanilla's own EnchantedItemForEmeralds listing uses.
            RegistryAccess registryAccess = trader.level().registryAccess();
            Optional<HolderSet.Named<Enchantment>> optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT)
                    .getTag(EnchantmentTags.ON_TRADED_EQUIPMENT);
            ItemStack itemstack = EnchantmentHelper.enchantItem(rand, new ItemStack(this.sellingStack.getItem()), i, registryAccess, optional);
            IAlloyItem.createDirectAlloyNBT(itemstack,alloyData,alloyRecipe,alloyNameOverride);
            int j = Math.min(this.emeraldCount + i, 64);
            return new MerchantOffer(new ItemCost(Items.EMERALD, j), itemstack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class RandomItemFromTagForEmeraldsTrade implements VillagerTrades.ItemListing {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public RandomItemFromTagForEmeraldsTrade(TagKey<Item> tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue) {
            this(tag, fallback, emeraldCount, amount, maxUses, xpValue, 0.05F);
        }

        public RandomItemFromTagForEmeraldsTrade(TagKey<Item> tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingStack = new ItemStack(BuiltInRegistries.ITEM.getTag(tag)
                    .flatMap(holderSet -> holderSet.getRandomElement(RandomSource.create()))
                    .map(Holder::value)
                    .orElse(fallback), amount);

            this.emeraldCount = emeraldCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            return new MerchantOffer(new ItemCost(Items.EMERALD), sellingStack, this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static List<VillagerTrades.ItemListing> returnTagTrades(TagKey<Item> tag, Item fallback, int amount, int emeraldCount, int maxUses, int xpValue, float mult) {
        List<VillagerTrades.ItemListing> trades = new ArrayList<>();
        if (tag != null) {

            List<Item> tagItems = BuiltInRegistries.ITEM.getTag(tag)
                    .map(holderSet -> holderSet.stream().map(Holder::value).toList())
                    .orElse(List.of());
            for (Item item : tagItems) {
                trades.add(new BasicItemListing(emeraldCount, new ItemStack(item,amount), maxUses, xpValue, mult));
            }
        } else {
            trades.add(new BasicItemListing(emeraldCount, new ItemStack(fallback,amount), maxUses, xpValue, mult));
        }
        return trades;

    }
}
