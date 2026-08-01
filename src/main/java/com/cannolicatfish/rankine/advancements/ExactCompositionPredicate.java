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
 * 1.21.1: was an ItemPredicate subclass ("rankine:exact_composition_check"); now an ItemSubPredicate.
 * The "composition" string is a regex matched against the stack's alloy composition (1.18.2 semantics).
 */
public record ExactCompositionPredicate(String composition) implements ItemSubPredicate {

    public static final Codec<ExactCompositionPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("composition").forGetter(ExactCompositionPredicate::composition)
    ).apply(instance, ExactCompositionPredicate::new));

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getItem() instanceof IAlloyItem && this.composition.matches(IAlloyItem.getAlloyComposition(stack));
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
                    .withSubPredicate(RankineCriteriaTriggers.EXACT_COMPOSITION_CHECK.get(), new ExactCompositionPredicate(this.composition))
                    .build();
        }
    }
}
