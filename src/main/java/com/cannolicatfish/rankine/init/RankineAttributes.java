package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RankineAttributes {

    // No custom Attribute objects are registered (1.18.2 only referenced Forge-added attributes,
    // which are vanilla attributes in 1.21.1), but the DeferredRegister exists so future custom
    // attributes have a home; ProjectRankine registers it on the mod bus.
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ProjectRankine.MODID);

    // ------------------------------------------------------------------
    // Config-driven movement modifiers.
    //
    // These cannot be built at class-init: NeoForge constructs mods and fires the registry
    // events BEFORE configs load (and never loads configs during datagen), so reading a
    // ModConfigSpec value here throws "Cannot get config value before config is loaded".
    // They start at the config defaults and are rebuilt once the config is available, so the
    // fields are never null and always sane. Call sites keep using them as plain fields.
    // ------------------------------------------------------------------

    public static AttributeModifier GRASS_PATH_MS = movement("grass_path_ms", 0.00D);
    public static AttributeModifier SAND_MS = movement("sand_ms", -0.02D);
    public static AttributeModifier BRICKS_MS = movement("bricks_ms", 0.02D);
    public static AttributeModifier ROMAN_CONCRETE_MS = movement("roman_concrete_ms", 0.07D);
    public static AttributeModifier DIRT_MS = movement("dirt_ms", -0.01D);
    public static AttributeModifier POLISHED_STONE_MS = movement("polished_stone_ms", 0.00D);
    public static AttributeModifier WOODEN_MS = movement("wooden_ms", 0.00D);
    public static AttributeModifier CONCRETE_MS = movement("concrete_ms", 0.02D);
    public static AttributeModifier SNOW_MS = movement("snow_ms", -0.02D);
    public static final AttributeModifier DUNE_WALKER = new AttributeModifier(rl("dune_walker"), 0.05, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SNOW_DRIFTER = new AttributeModifier(rl("snowshoes"), 0.05, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SPEED_SKATER = new AttributeModifier(rl("speed_skater"), 0.1, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SWIFTNESS_TOTEM = new AttributeModifier(rl("speed_pendant_ms"), 0.075, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier ENDURING_TOTEM = new AttributeModifier(rl("health_pendant_ms"), 10.0, AttributeModifier.Operation.ADD_VALUE);
    public static AttributeModifier MUD_MS = movement("mud_ms", -0.03D);
    public static final AttributeModifier SWIFT_SWIMMER = new AttributeModifier(rl("swift_swimmer"), 2.5, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier AQUA_LENSE = new AttributeModifier(rl("aqua_lense"), 1.0, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier STEP = new AttributeModifier(rl("step_boost"), 1.0D, AttributeModifier.Operation.ADD_VALUE);

    // Old 1.18.2 code used the name "rankine:mercury_mod" for both of these (distinguished by UUID);
    // AttributeModifier ids must now be unique per attribute map entry, so they get distinct paths.
    public static final AttributeModifier MERCURY_MS = new AttributeModifier(rl("mercury_speed"), -0.04D, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier MERCURY_HEALTH = new AttributeModifier(rl("mercury_health"), -2D, AttributeModifier.Operation.ADD_VALUE);

    public static final AttributeModifier MINOR_RADIATION_POISONING = new AttributeModifier(rl("minor_rad"), -2D, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier RADIATION_POISONING = new AttributeModifier(rl("rad"), -2D, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier EXTREME_RADIATION_POISONING = new AttributeModifier(rl("extreme_rad"), -4D, AttributeModifier.Operation.ADD_VALUE);

    // Former UUID constants — AttributeModifiers are identified by ResourceLocation in 1.21.1.
    public static final ResourceLocation SPEAR_RANGE_MODIFIER = rl("spear_range_modifier");
    public static final ResourceLocation KNIFE_RANGE_MODIFIER = rl("knife_range_modifier");
    public static final ResourceLocation REACH_MODIFIER = rl("reach_modifier");

    // Forge attributes that became vanilla attributes in 1.21.1:
    public static final Holder<Attribute> REACH_DISTANCE = Attributes.BLOCK_INTERACTION_RANGE;
    public static final Holder<Attribute> ATTACK_RANGE = Attributes.ENTITY_INTERACTION_RANGE;
    public static final Holder<Attribute> STEP_HEIGHT = Attributes.STEP_HEIGHT;
    public static final Holder<Attribute> GRAVITY = Attributes.GRAVITY;

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path);
    }

    private static AttributeModifier movement(String path, double value) {
        return new AttributeModifier(rl(path), value, AttributeModifier.Operation.ADD_VALUE);
    }

    /**
     * Rebuilds the config-driven movement modifiers. Runs once configs are available and again
     * whenever they are reloaded, so edits to the config take effect without a restart.
     */
    private static void refreshFromConfig() {
        GRASS_PATH_MS = movement("grass_path_ms", Config.GENERAL.MOVEMENT_GRASS_PATH.get());
        SAND_MS = movement("sand_ms", Config.GENERAL.MOVEMENT_SAND.get());
        BRICKS_MS = movement("bricks_ms", Config.GENERAL.MOVEMENT_BRICKS.get());
        ROMAN_CONCRETE_MS = movement("roman_concrete_ms", Config.GENERAL.MOVEMENT_ROMAN_CONCRETE.get());
        DIRT_MS = movement("dirt_ms", Config.GENERAL.MOVEMENT_DIRT.get());
        POLISHED_STONE_MS = movement("polished_stone_ms", Config.GENERAL.MOVEMENT_POLISHED_STONE.get());
        WOODEN_MS = movement("wooden_ms", Config.GENERAL.MOVEMENT_WOODEN.get());
        CONCRETE_MS = movement("concrete_ms", Config.GENERAL.MOVEMENT_CONCRETE.get());
        SNOW_MS = movement("snow_ms", Config.GENERAL.MOVEMENT_SNOW.get());
        MUD_MS = movement("mud_ms", Config.GENERAL.MOVEMENT_MUD.get());
    }

    @SubscribeEvent
    public static void onConfigLoaded(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == Config.COMMON_CONFIG) {
            refreshFromConfig();
        }
    }

    @SubscribeEvent
    public static void onConfigReloaded(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == Config.COMMON_CONFIG) {
            refreshFromConfig();
        }
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeModificationEvent event) {
        // All attributes Rankine modifies (interaction ranges, step height, gravity, movement speed,
        // max health) are vanilla attributes already present on players in 1.21.1 — nothing to add.
        // Custom attributes registered in ATTRIBUTES above should be attached to players here.
    }

}
