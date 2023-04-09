package com.cannolicatfish.rankine.advancements;

import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Stream;

public class HarvestLevelPredicate extends ItemPredicate {
    @Nullable
    private final TagKey<Item> tag;
    @Nullable
    private final Set<Item> items;
    private final MinMaxBounds.Ints level;

    public HarvestLevelPredicate(@Nullable TagKey<Item> tagIn, @Nullable Set<Item> itemsIn, MinMaxBounds.Ints level) {
        this.tag = tagIn;
        this.items = itemsIn;
        this.level = level;
    }

    public HarvestLevelPredicate(JsonObject jsonobject) {
        Set<Item> set = null;
        JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "items", (JsonArray)null);
        if (jsonarray != null) {
            ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

            for(JsonElement jsonelement : jsonarray) {
                ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "item"));
                builder.add(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                }));
            }

            set = builder.build();
        }
        TagKey<Item> tagkey = null;
        if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(jsonobject, "tag"));
            tagkey = TagKey.create(Registries.ITEM, resourcelocation1);
        }

        this.tag = tagkey;
        this.items = set;
        this.level = MinMaxBounds.Ints.fromJson(jsonobject.get("level"));

    }

    @Override
    public boolean matches(ItemStack stack) {
        if (stack.getItem() instanceof IAlloyTool) {
            if (this == ANY) {
                return true;
            } else if (this.tag != null && stack.is(this.tag)) {
                return this.level.matches(((IAlloyTool) stack.getItem()).getAlloyHarvestLevel(stack));
            } else if (this.items != null && this.items.contains(stack.getItem())) {
                return this.level.matches(((IAlloyTool) stack.getItem()).getAlloyHarvestLevel(stack));
            }
        }

        return false;
    }

    public static ItemPredicate fromJson(@Nullable JsonElement p_45052_) {
        if (p_45052_ != null && !p_45052_.isJsonNull()) {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(p_45052_, "item");

            MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.fromJson(jsonobject.get("level"));

            if (jsonobject.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            } else {
                Set<Item> set = null;
                JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "items", (JsonArray)null);
                if (jsonarray != null) {
                    ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

                    for(JsonElement jsonelement : jsonarray) {
                        ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "item"));
                        builder.add(BuiltInRegistries.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                            return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                        }));
                    }

                    set = builder.build();
                }

                TagKey<Item> tagkey = null;
                if (jsonobject.has("tag")) {
                    ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.getAsString(jsonobject, "tag"));
                    tagkey = TagKey.create(Registries.ITEM, resourcelocation1);
                }

                return new HarvestLevelPredicate(tagkey, set, minmaxbounds$ints);
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
            jsonobject.addProperty("type", "rankine:harvest_level_check");

            if (this.items != null) {
                JsonArray jsonarray = new JsonArray();
                for(Item item : this.items) {
                    jsonarray.add(BuiltInRegistries.ITEM.getKey(item).toString());
                }
                jsonobject.add("items", jsonarray);
            }

            if (this.tag != null) {
                jsonobject.addProperty("tag", this.tag.location().toString());
            }

            jsonobject.add("level", this.level.serializeToJson());

            return jsonobject;
        }
    }

    public static class Builder {
        @Nullable
        private Set<Item> items;
        @Nullable
        private TagKey<Item> tag;
        private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;

        private Builder() {
        }

        public static HarvestLevelPredicate.Builder item() {
            return new HarvestLevelPredicate.Builder();
        }

        public HarvestLevelPredicate.Builder of(ItemLike... p_151446_) {
            this.items = Stream.of(p_151446_).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
            return this;
        }

        public HarvestLevelPredicate.Builder of(TagKey<Item> p_204146_) {
            this.tag = p_204146_;
            return this;
        }

        public HarvestLevelPredicate.Builder withLevel(MinMaxBounds.Ints p_151444_) {
            this.level = p_151444_;
            return this;
        }

        public HarvestLevelPredicate build() {
            return new HarvestLevelPredicate(this.tag, this.items, this.level);
        }
    }
}
