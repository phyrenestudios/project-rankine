package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Stream;

public class ExactCompositionPredicate extends ItemPredicate {
    @Nullable
    private final TagKey<Item> tag;
    @Nullable
    private final Set<Item> items;
    private final String composition;

    public ExactCompositionPredicate(@Nullable TagKey<Item> tagIn, @Nullable Set<Item> itemsIn, String composition) {
        this.tag = tagIn;
        this.items = itemsIn;
        this.composition = composition;
    }

    public ExactCompositionPredicate(JsonObject jsonobject) {
        Set<Item> set = null;
        JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "items", (JsonArray)null);
        if (jsonarray != null) {
            ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

            for(JsonElement jsonelement : jsonarray) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "item"));
                builder.add(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                }));
            }

            set = builder.build();
        }
        TagKey<Item> tagkey = null;
        if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(jsonobject, "tag"));
            tagkey = TagKey.create(Registry.ITEM_REGISTRY, resourcelocation1);
        }

        this.tag = tagkey;
        this.items = set;
        this.composition = GsonHelper.getAsString(jsonobject, "composition");

    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyItem) {
            if (this == ANY) {
                return true;
            } else if (this.tag != null && stack.is(this.tag)) {
                return this.composition.matches(IAlloyItem.getAlloyComposition(stack));
            } else if (this.items != null && this.items.contains(stack.getItem())) {
                return this.composition.matches(IAlloyItem.getAlloyComposition(stack));
            }
        }

        return false;
    }

    public static ItemPredicate fromJson(@Nullable JsonElement p_45052_) {
        if (p_45052_ != null && !p_45052_.isJsonNull()) {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(p_45052_, "item");

            String composition = GsonHelper.getAsString(jsonobject, "composition");

            if (jsonobject.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            } else {
                Set<Item> set = null;
                JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "items", (JsonArray)null);
                if (jsonarray != null) {
                    ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

                    for(JsonElement jsonelement : jsonarray) {
                        ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "item"));
                        builder.add(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                            return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                        }));
                    }

                    set = builder.build();
                }

                TagKey<Item> tagkey = null;
                if (jsonobject.has("tag")) {
                    ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(jsonobject, "tag"));
                    tagkey = TagKey.create(Registry.ITEM_REGISTRY, resourcelocation1);
                }

                return new ExactCompositionPredicate(tagkey, set, composition);
            }
        } else {
            return ANY;
        }
    }

    public JsonElement serializeToJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("type", "rankine:exact_composition_check");

            if (this.items != null) {
                JsonArray jsonarray = new JsonArray();
                for(Item item : this.items) {
                    jsonarray.add(Registry.ITEM.getKey(item).toString());
                }
                jsonobject.add("items", jsonarray);
            }

            if (this.tag != null) {
                jsonobject.addProperty("tag", this.tag.location().toString());
            }

            jsonobject.addProperty("composition", this.composition);

            return jsonobject;
        }
    }

    public static class Builder {
        @Nullable
        private Set<Item> items;
        @Nullable
        private TagKey<Item> tag;
        private String composition;

        private Builder() {
        }

        public static ExactCompositionPredicate.Builder item() {
            return new ExactCompositionPredicate.Builder();
        }

        public ExactCompositionPredicate.Builder of(ItemLike... p_151446_) {
            this.items = Stream.of(p_151446_).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
            return this;
        }

        public ExactCompositionPredicate.Builder of(TagKey<Item> p_204146_) {
            this.tag = p_204146_;
            return this;
        }

        public ExactCompositionPredicate.Builder withComposition(String p_151444_) {
            this.composition = p_151444_;
            return this;
        }

        public ExactCompositionPredicate build() {
            return new ExactCompositionPredicate(this.tag, this.items, this.composition);
        }
    }
}
