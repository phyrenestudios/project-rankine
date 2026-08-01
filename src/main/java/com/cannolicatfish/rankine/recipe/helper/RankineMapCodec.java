package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Bridge {@link MapCodec} that adapts the 1.18.2-era Gson based recipe (de)serialization logic
 * to the 1.21.1 codec-driven {@code RecipeSerializer} API.
 *
 * <p>The decode path converts the incoming map (usually Json, but works for any {@link DynamicOps})
 * into a {@link JsonObject} and hands it to the legacy {@code fromJson} logic, which keeps the exact
 * field names, optional-field defaults and error behavior of the old handwritten parsers. This keeps
 * every handwritten 1.18.2 recipe JSON loading unchanged.</p>
 *
 * <p>The encode path (used by datagen and debug dumps) writes the same field layout back out.</p>
 */
public final class RankineMapCodec<R> extends MapCodec<R> {

    private final Set<String> knownKeys;
    private final Function<JsonObject, R> fromJson;
    private final BiConsumer<R, JsonObject> toJson;

    private RankineMapCodec(Set<String> knownKeys, Function<JsonObject, R> fromJson, BiConsumer<R, JsonObject> toJson) {
        this.knownKeys = knownKeys;
        this.fromJson = fromJson;
        this.toJson = toJson;
    }

    public static <R> RankineMapCodec<R> of(Function<JsonObject, R> fromJson, BiConsumer<R, JsonObject> toJson, Set<String> knownKeys) {
        return new RankineMapCodec<>(knownKeys, fromJson, toJson);
    }

    /** Convenience builder for the known-keys set (only used by compressed ops; harmless for Json/Nbt ops). */
    public static Set<String> fixedKeys(String... keys) {
        Set<String> set = new LinkedHashSet<>();
        for (String key : keys) {
            set.add(key);
        }
        return set;
    }

    /** Adds numbered keys such as {@code input1..inputN} to an existing key set. */
    public static Set<String> withNumbered(Set<String> base, String prefix, int max) {
        Set<String> set = new LinkedHashSet<>(base);
        for (int i = 1; i <= max; i++) {
            set.add(prefix + i);
        }
        return set;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return knownKeys.stream().map(ops::createString);
    }

    @Override
    public <T> DataResult<R> decode(DynamicOps<T> ops, MapLike<T> input) {
        JsonObject json = new JsonObject();
        input.entries().forEach(pair -> {
            JsonElement key = ops.convertTo(JsonOps.INSTANCE, pair.getFirst());
            if (key != null && key.isJsonPrimitive()) {
                json.add(key.getAsString(), ops.convertTo(JsonOps.INSTANCE, pair.getSecond()));
            }
        });
        try {
            return DataResult.success(fromJson.apply(json));
        } catch (Exception e) {
            String message = e.getMessage() == null ? e.toString() : e.getMessage();
            return DataResult.error(() -> message);
        }
    }

    @Override
    public <T> RecordBuilder<T> encode(R value, DynamicOps<T> ops, RecordBuilder<T> builder) {
        JsonObject json = new JsonObject();
        toJson.accept(value, json);
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            builder.add(entry.getKey(), JsonOps.INSTANCE.convertTo(ops, entry.getValue()));
        }
        return builder;
    }
}
