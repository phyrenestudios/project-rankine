package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

/**
 * Data components replacing per-ItemStack NBT from the 1.18.2 version.
 * Registry names intentionally mirror the old NBT keys where possible.
 */
public class RankineDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ProjectRankine.MODID);

    /** Alloy composition string, e.g. "80Fe-20C" (was NBT "StoredAlloy"."comp"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ALLOY_COMPOSITION =
            DATA_COMPONENTS.register("alloy_composition", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Alloy recipe id used to derive stats (was NBT "StoredAlloy"."recipe"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> ALLOY_RECIPE =
            DATA_COMPONENTS.register("alloy_recipe", () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build());

    /** Marks an alloy item whose stats need regeneration (was NBT "RegenerateAlloy"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> REGENERATE_ALLOY =
            DATA_COMPONENTS.register("regenerate_alloy", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    /** Generic display-name override (was NBT "nameOverride"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> NAME_OVERRIDE =
            DATA_COMPONENTS.register("name_override", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Stored gas id for the gas bottler / shulker gas vacuum (was NBT "gas"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> GAS =
            DATA_COMPONENTS.register("gas", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Stored alloy string on alloy templates (was NBT "StoredAlloy"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STORED_ALLOY =
            DATA_COMPONENTS.register("stored_alloy", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Generic color (was NBT "color"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COLOR =
            DATA_COMPONENTS.register("color", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    // ------------------------------------------------------------------
    // Alloy stats (was NBT compound "StoredAlloyStats"; shields stored the
    // same values inside "StoredAlloy"). All fields default to 0 to match
    // CompoundTag#getInt/#getFloat on absent keys.
    // ------------------------------------------------------------------

    /**
     * All numeric per-stack alloy stats. Tools write durability/miningSpeed/harvestLevel/
     * enchantability/attackDamage/attackSpeed/corrResist/heatResist/toughness; armor writes
     * durability/armorToughness/damageResistance/enchantability/corrResist/heatResist/knockbackResist;
     * projectiles write projectileDamage. Unwritten fields stay 0, exactly like the old NBT reads.
     */
    public record AlloyStats(int durability, float miningSpeed, int harvestLevel, int enchantability,
                             float attackDamage, float attackSpeed, float corrResist, float heatResist,
                             float toughness, int armorToughness, int damageResistance, float knockbackResist,
                             float projectileDamage) {
        public static final Codec<AlloyStats> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.INT.optionalFieldOf("durability", 0).forGetter(AlloyStats::durability),
                Codec.FLOAT.optionalFieldOf("miningSpeed", 0f).forGetter(AlloyStats::miningSpeed),
                Codec.INT.optionalFieldOf("harvestLevel", 0).forGetter(AlloyStats::harvestLevel),
                Codec.INT.optionalFieldOf("enchantability", 0).forGetter(AlloyStats::enchantability),
                Codec.FLOAT.optionalFieldOf("attackDamage", 0f).forGetter(AlloyStats::attackDamage),
                Codec.FLOAT.optionalFieldOf("attackSpeed", 0f).forGetter(AlloyStats::attackSpeed),
                Codec.FLOAT.optionalFieldOf("corrResist", 0f).forGetter(AlloyStats::corrResist),
                Codec.FLOAT.optionalFieldOf("heatResist", 0f).forGetter(AlloyStats::heatResist),
                Codec.FLOAT.optionalFieldOf("toughness", 0f).forGetter(AlloyStats::toughness),
                Codec.INT.optionalFieldOf("armorToughness", 0).forGetter(AlloyStats::armorToughness),
                Codec.INT.optionalFieldOf("damageResistance", 0).forGetter(AlloyStats::damageResistance),
                Codec.FLOAT.optionalFieldOf("knockbackResist", 0f).forGetter(AlloyStats::knockbackResist),
                Codec.FLOAT.optionalFieldOf("projectileDamage", 0f).forGetter(AlloyStats::projectileDamage)
        ).apply(inst, AlloyStats::new));

        public static final StreamCodec<ByteBuf, AlloyStats> STREAM_CODEC = StreamCodec.of(
                (buf, v) -> {
                    buf.writeInt(v.durability);
                    buf.writeFloat(v.miningSpeed);
                    buf.writeInt(v.harvestLevel);
                    buf.writeInt(v.enchantability);
                    buf.writeFloat(v.attackDamage);
                    buf.writeFloat(v.attackSpeed);
                    buf.writeFloat(v.corrResist);
                    buf.writeFloat(v.heatResist);
                    buf.writeFloat(v.toughness);
                    buf.writeInt(v.armorToughness);
                    buf.writeInt(v.damageResistance);
                    buf.writeFloat(v.knockbackResist);
                    buf.writeFloat(v.projectileDamage);
                },
                buf -> new AlloyStats(buf.readInt(), buf.readFloat(), buf.readInt(), buf.readInt(),
                        buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(),
                        buf.readFloat(), buf.readInt(), buf.readInt(), buf.readFloat(), buf.readFloat())
        );

        public static final AlloyStats EMPTY = new AlloyStats(0, 0f, 0, 0, 0f, 0f, 0f, 0f, 0f, 0, 0, 0f, 0f);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AlloyStats>> ALLOY_STATS =
            DATA_COMPONENTS.register("alloy_stats", () -> DataComponentType.<AlloyStats>builder()
                    .persistent(AlloyStats.CODEC).networkSynchronized(AlloyStats.STREAM_CODEC).build());

    // ------------------------------------------------------------------
    // Stored element list (was NBT ListTag "Elements": {id, percent}).
    // ------------------------------------------------------------------

    /** One element entry of an alloy composition (was one CompoundTag in the "Elements" list). */
    public record AlloyElement(String id, int percent) {
        public static final Codec<AlloyElement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.STRING.fieldOf("id").forGetter(AlloyElement::id),
                Codec.INT.fieldOf("percent").forGetter(AlloyElement::percent)
        ).apply(inst, AlloyElement::new));

        public static final StreamCodec<ByteBuf, AlloyElement> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, AlloyElement::id,
                ByteBufCodecs.VAR_INT, AlloyElement::percent,
                AlloyElement::new);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<AlloyElement>>> ALLOY_ELEMENTS =
            DATA_COMPONENTS.register("alloy_elements", () -> DataComponentType.<List<AlloyElement>>builder()
                    .persistent(AlloyElement.CODEC.listOf())
                    .networkSynchronized(AlloyElement.STREAM_CODEC.apply(ByteBufCodecs.list())).build());

    // ------------------------------------------------------------------
    // Applied alloy modifiers (was NBT ListTag "AlloyModifiers": {modifierName, value}).
    // ------------------------------------------------------------------

    /** One applied alloy modifier (was one CompoundTag in the "AlloyModifiers" list). */
    public record AlloyModifierEntry(String name, float value) {
        public static final Codec<AlloyModifierEntry> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.STRING.fieldOf("modifierName").forGetter(AlloyModifierEntry::name),
                Codec.FLOAT.optionalFieldOf("value", 0f).forGetter(AlloyModifierEntry::value)
        ).apply(inst, AlloyModifierEntry::new));

        public static final StreamCodec<ByteBuf, AlloyModifierEntry> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, AlloyModifierEntry::name,
                ByteBufCodecs.FLOAT, AlloyModifierEntry::value,
                AlloyModifierEntry::new);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<AlloyModifierEntry>>> ALLOY_MODIFIERS =
            DATA_COMPONENTS.register("alloy_modifiers", () -> DataComponentType.<List<AlloyModifierEntry>>builder()
                    .persistent(AlloyModifierEntry.CODEC.listOf())
                    .networkSynchronized(AlloyModifierEntry.STREAM_CODEC.apply(ByteBufCodecs.list())).build());

    // ------------------------------------------------------------------
    // Alloy template components (AlloyTemplateItem; was NBT "StoredInfo" + "StoredTemplate").
    // ------------------------------------------------------------------

    /** Summary info of an alloy template (was NBT compound "StoredInfo"). */
    public record TemplateInfo(int stackSize, String output, String alloyComp, String alloyName,
                               String alloyRecipe, int alloyTier) {
        public static final Codec<TemplateInfo> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.INT.optionalFieldOf("stackSize", 0).forGetter(TemplateInfo::stackSize),
                Codec.STRING.optionalFieldOf("output", "").forGetter(TemplateInfo::output),
                Codec.STRING.optionalFieldOf("alloyComp", "").forGetter(TemplateInfo::alloyComp),
                Codec.STRING.optionalFieldOf("alloyName", "").forGetter(TemplateInfo::alloyName),
                Codec.STRING.optionalFieldOf("alloyRecipe", "").forGetter(TemplateInfo::alloyRecipe),
                Codec.INT.optionalFieldOf("alloyTier", 0).forGetter(TemplateInfo::alloyTier)
        ).apply(inst, TemplateInfo::new));

        public static final StreamCodec<ByteBuf, TemplateInfo> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT, TemplateInfo::stackSize,
                ByteBufCodecs.STRING_UTF8, TemplateInfo::output,
                ByteBufCodecs.STRING_UTF8, TemplateInfo::alloyComp,
                ByteBufCodecs.STRING_UTF8, TemplateInfo::alloyName,
                ByteBufCodecs.STRING_UTF8, TemplateInfo::alloyRecipe,
                ByteBufCodecs.VAR_INT, TemplateInfo::alloyTier,
                TemplateInfo::new);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TemplateInfo>> TEMPLATE_INFO =
            DATA_COMPONENTS.register("template_info", () -> DataComponentType.<TemplateInfo>builder()
                    .persistent(TemplateInfo.CODEC).networkSynchronized(TemplateInfo.STREAM_CODEC).build());

    /** One ingredient line of an alloy template (was one CompoundTag in the "StoredTemplate" list). */
    public record TemplateEntry(String ingredient, int ingredientAmount, String element, int elementAmount) {
        public static final Codec<TemplateEntry> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.STRING.fieldOf("ingredient").forGetter(TemplateEntry::ingredient),
                Codec.INT.optionalFieldOf("ingredientAmount", 0).forGetter(TemplateEntry::ingredientAmount),
                Codec.STRING.optionalFieldOf("element", "").forGetter(TemplateEntry::element),
                Codec.INT.optionalFieldOf("elementAmount", 0).forGetter(TemplateEntry::elementAmount)
        ).apply(inst, TemplateEntry::new));

        public static final StreamCodec<ByteBuf, TemplateEntry> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, TemplateEntry::ingredient,
                ByteBufCodecs.VAR_INT, TemplateEntry::ingredientAmount,
                ByteBufCodecs.STRING_UTF8, TemplateEntry::element,
                ByteBufCodecs.VAR_INT, TemplateEntry::elementAmount,
                TemplateEntry::new);
    }

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<TemplateEntry>>> TEMPLATE_ENTRIES =
            DATA_COMPONENTS.register("template_entries", () -> DataComponentType.<List<TemplateEntry>>builder()
                    .persistent(TemplateEntry.CODEC.listOf())
                    .networkSynchronized(TemplateEntry.STREAM_CODEC.apply(ByteBufCodecs.list())).build());

    // ------------------------------------------------------------------
    // Misc simple components.
    // ------------------------------------------------------------------

    /** Building style mode (was NBT "building_mode" on BuildingModeBlockItem / "buildingMode" on BuildingToolItem). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BUILDING_MODE =
            DATA_COMPONENTS.register("building_mode", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    /** Forced tool type on a packaged tool (was NBT "forceTool"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FORCE_TOOL =
            DATA_COMPONENTS.register("force_tool", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Forced armor piece on packaged armor (was NBT "forceArmor"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FORCE_ARMOR =
            DATA_COMPONENTS.register("force_armor", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    // NOTE for porters: add further components here as NBT usages are migrated.
    // BlockEntity NBT (CookTime etc.) stays as CompoundTag — do NOT convert BE persistence.
}
