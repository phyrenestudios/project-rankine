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
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RankineAttributes {

    // No custom Attribute objects are registered (1.18.2 only referenced Forge-added attributes,
    // which are vanilla attributes in 1.21.1), but the DeferredRegister exists so future custom
    // attributes have a home; ProjectRankine registers it on the mod bus.
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ProjectRankine.MODID);

    public static final AttributeModifier GRASS_PATH_MS = new AttributeModifier(rl("grass_path_ms"), Config.GENERAL.MOVEMENT_GRASS_PATH.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SAND_MS = new AttributeModifier(rl("sand_ms"), Config.GENERAL.MOVEMENT_SAND.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier BRICKS_MS = new AttributeModifier(rl("bricks_ms"), Config.GENERAL.MOVEMENT_BRICKS.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier ROMAN_CONCRETE_MS = new AttributeModifier(rl("roman_concrete_ms"), Config.GENERAL.MOVEMENT_ROMAN_CONCRETE.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier DIRT_MS = new AttributeModifier(rl("dirt_ms"), Config.GENERAL.MOVEMENT_DIRT.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier POLISHED_STONE_MS = new AttributeModifier(rl("polished_stone_ms"), Config.GENERAL.MOVEMENT_POLISHED_STONE.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier WOODEN_MS = new AttributeModifier(rl("wooden_ms"), Config.GENERAL.MOVEMENT_WOODEN.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier CONCRETE_MS = new AttributeModifier(rl("concrete_ms"), Config.GENERAL.MOVEMENT_CONCRETE.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SNOW_MS = new AttributeModifier(rl("snow_ms"), Config.GENERAL.MOVEMENT_SNOW.get(), AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier DUNE_WALKER = new AttributeModifier(rl("dune_walker"), 0.05, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SNOW_DRIFTER = new AttributeModifier(rl("snowshoes"), 0.05, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SPEED_SKATER = new AttributeModifier(rl("speed_skater"), 0.1, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier SWIFTNESS_TOTEM = new AttributeModifier(rl("speed_pendant_ms"), 0.075, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier ENDURING_TOTEM = new AttributeModifier(rl("health_pendant_ms"), 10.0, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier MUD_MS = new AttributeModifier(rl("mud_ms"), Config.GENERAL.MOVEMENT_MUD.get(), AttributeModifier.Operation.ADD_VALUE);
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

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeModificationEvent event) {
        // All attributes Rankine modifies (interaction ranges, step height, gravity, movement speed,
        // max health) are vanilla attributes already present on players in 1.21.1 — nothing to add.
        // Custom attributes registered in ATTRIBUTES above should be attached to players here.
    }

}
