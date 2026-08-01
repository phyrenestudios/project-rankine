package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.init.RankineCriteriaTriggers;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * 1.21.1: was an ItemPredicate subclass ("rankine:includes_composition_check"); now an ItemSubPredicate.
 * Matches when the stack's alloy composition CONTAINS the given string (1.18.2 semantics).
 */
public record IncludesCompositionPredicate(String composition) implements ItemSubPredicate {

    public static final Codec<IncludesCompositionPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("composition").forGetter(IncludesCompositionPredicate::composition)
    ).apply(instance, IncludesCompositionPredicate::new));

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getItem() instanceof IAlloyItem && IAlloyItem.getAlloyComposition(stack).contains(this.composition);
    }

    public static class Builder {
        private final ItemPredicate.Builder delegate = ItemPredicate.Builder.item();
        private String composition = "";

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

        public Builder withComposition(String composition) {
            this.composition = composition;
            return this;
        }

        public ItemPredicate build() {
            return this.delegate
                    .withSubPredicate(RankineCriteriaTriggers.INCLUDES_COMPOSITION_CHECK.get(), new IncludesCompositionPredicate(this.composition))
                    .build();
        }
    }
}
