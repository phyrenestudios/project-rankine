package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.init.RankineCriteriaTriggers;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * 1.21.1: was an ItemPredicate subclass ("rankine:enchantability_check"); now an ItemSubPredicate.
 * Item/tag matching moved to the wrapping vanilla ItemPredicate (see Builder).
 */
public record AlloyEnchantabilityPredicate(MinMaxBounds.Ints level) implements ItemSubPredicate {

    public static final Codec<AlloyEnchantabilityPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MinMaxBounds.Ints.CODEC.optionalFieldOf("level", MinMaxBounds.Ints.ANY).forGetter(AlloyEnchantabilityPredicate::level)
    ).apply(instance, AlloyEnchantabilityPredicate::new));

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getItem() instanceof IAlloyTool alloyTool && this.level.matches(alloyTool.getAlloyEnchantability(stack));
    }

    public static class Builder {
        private final ItemPredicate.Builder delegate = ItemPredicate.Builder.item();
        private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;

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

        public Builder withLevel(MinMaxBounds.Ints level) {
            this.level = level;
            return this;
        }

        public ItemPredicate build() {
            return this.delegate
                    .withSubPredicate(RankineCriteriaTriggers.ENCHANTABILITY_CHECK.get(), new AlloyEnchantabilityPredicate(this.level))
                    .build();
        }
    }
}
