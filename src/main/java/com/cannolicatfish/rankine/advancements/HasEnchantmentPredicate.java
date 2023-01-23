package com.cannolicatfish.rankine.advancements;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Stream;

public class HasEnchantmentPredicate extends ItemPredicate {
    @Nullable
    private final TagKey<Item> tag;
    @Nullable
    private final Set<Item> items;
    private final Set<Enchantment> enchantments;

    public HasEnchantmentPredicate(@Nullable TagKey<Item> tagIn, @Nullable Set<Item> itemsIn, Set<Enchantment> enchantmentsIn) {
        this.tag = tagIn;
        this.items = itemsIn;
        this.enchantments = enchantmentsIn;
    }

    public HasEnchantmentPredicate(JsonObject jsonobject) {
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
        Set<Enchantment> enchants = null;
        JsonArray jsonarray2 = GsonHelper.getAsJsonArray(jsonobject, "enchantments", (JsonArray)null);
        if (jsonarray2 != null) {
            ImmutableSet.Builder<Enchantment> builder = ImmutableSet.builder();
            for(JsonElement jsonelement : jsonarray2) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "enchantment"));
                builder.add(Registry.ENCHANTMENT.getOptional(resourcelocation).orElseThrow(() -> {
                    return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                }));
            }
            enchants = builder.build();
        }

        this.tag = tagkey;
        this.items = set;
        this.enchantments = enchants;
    }

    @Override
    public boolean matches(ItemStack stack) {
        if (this == ANY) return true;
        if (this.tag != null && stack.is(this.tag)) {
            for (Enchantment enchant : this.enchantments) {
                if (EnchantmentHelper.getItemEnchantmentLevel(enchant, stack) <= 0) return false;
            }
        } else if (this.items != null && this.items.contains(stack.getItem())) {
            for (Enchantment enchant : this.enchantments) {
                if (EnchantmentHelper.getItemEnchantmentLevel(enchant, stack) <= 0) return false;
            }
        }
        return true;
    }

    public static ItemPredicate fromJson(@Nullable JsonElement p_45052_) {
        if (p_45052_ != null && !p_45052_.isJsonNull()) {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(p_45052_, "item");
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

                Set<Enchantment> enchants = null;
                JsonArray jsonarray2 = GsonHelper.getAsJsonArray(jsonobject, "enchantments", (JsonArray)null);
                if (jsonarray2 != null) {
                    ImmutableSet.Builder<Enchantment> builder = ImmutableSet.builder();
                    for(JsonElement jsonelement : jsonarray2) {
                        ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "enchantment"));
                        builder.add(Registry.ENCHANTMENT.getOptional(resourcelocation).orElseThrow(() -> {
                            return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                        }));
                    }
                    enchants = builder.build();
                }

                return new HasEnchantmentPredicate(tagkey, set, enchants);
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
            jsonobject.addProperty("type", "rankine:has_enchantment_check");
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
            if (this.enchantments != null) {
                JsonArray jsonarray = new JsonArray();
                for(Enchantment item : this.enchantments) {
                    jsonarray.add(Registry.ENCHANTMENT.getKey(item).toString());
                }
                jsonobject.add("enchantments", jsonarray);
            }
            return jsonobject;
        }
    }

    public static class Builder {
        @Nullable
        private Set<Item> items;
        @Nullable
        private TagKey<Item> tag;
        private Set<Enchantment> enchantments;

        private Builder() {
        }

        public static HasEnchantmentPredicate.Builder item() {
            return new HasEnchantmentPredicate.Builder();
        }

        public HasEnchantmentPredicate.Builder of(ItemLike... p_151446_) {
            this.items = Stream.of(p_151446_).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
            return this;
        }

        public HasEnchantmentPredicate.Builder of(TagKey<Item> p_204146_) {
            this.tag = p_204146_;
            return this;
        }

        public HasEnchantmentPredicate.Builder withEnchantments(Enchantment... p_151444_) {
            this.enchantments = Stream.of(p_151444_).collect(ImmutableSet.toImmutableSet());
            return this;
        }

        public HasEnchantmentPredicate build() {
            return new HasEnchantmentPredicate(this.tag, this.items, this.enchantments);
        }
    }
}
