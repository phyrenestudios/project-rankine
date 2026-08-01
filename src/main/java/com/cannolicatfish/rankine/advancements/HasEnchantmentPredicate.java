package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.init.RankineCriteriaTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

/**
 * 1.21.1: was an ItemPredicate subclass ("rankine:has_enchantment_check"); now an ItemSubPredicate.
 * Matches when the stack has EVERY listed enchantment at level >= 1 (1.18.2 semantics).
 * Enchantments are datapack entries in 1.21, hence Holder references (codec resolves by id).
 */
public record HasEnchantmentPredicate(List<Holder<Enchantment>> enchantments) implements ItemSubPredicate {

    public static final Codec<HasEnchantmentPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Enchantment.CODEC.listOf().fieldOf("enchantments").forGetter(HasEnchantmentPredicate::enchantments)
    ).apply(instance, HasEnchantmentPredicate::new));

    @Override
    public boolean matches(ItemStack stack) {
        for (Holder<Enchantment> enchant : this.enchantments) {
            if (EnchantmentHelper.getItemEnchantmentLevel(enchant, stack) <= 0) {
                return false;
            }
        }
        return true;
    }

    public static class Builder {
        private final ItemPredicate.Builder delegate = ItemPredicate.Builder.item();
        private List<Holder<Enchantment>> enchantments = List.of();

        private Builder() {}

        public static Builder item() {
            return new Builder();
        }

        public Builder of(ItemLike... items) {
            this.delegate.of(items);
            return this;
        }

        public Builder of(TagKey<Item> tag) {
            this.delegate.of(tag);
            return this;
        }

        /**
         * 1.21.1: enchantments are datapack entries — datagen must pass holders obtained from
         * a HolderLookup.Provider (e.g. {@code registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key)}).
         */
        @SafeVarargs
        public final Builder withEnchantments(Holder<Enchantment>... enchantments) {
            this.enchantments = Arrays.asList(enchantments);
            return this;
        }

        public ItemPredicate build() {
            return this.delegate
                    .withSubPredicate(RankineCriteriaTriggers.HAS_ENCHANTMENT_CHECK.get(), new HasEnchantmentPredicate(this.enchantments))
                    .build();
        }
    }
}
