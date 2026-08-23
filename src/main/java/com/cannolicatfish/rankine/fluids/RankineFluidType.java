package com.cannolicatfish.rankine.fluids;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

/**
 * The {@link FluidType} every Rankine fluid uses (port item F33, Karl's decision 6 of
 * 2026-08-22: <i>"make fluid movement realistic per each fluid — e.g. maple syrup slows"</i>).
 *
 * <p><b>This is a deliberate feature, not a 1.18.2 parity fix.</b> 1.18.2 had no per-fluid entity
 * physics at all — Forge's {@code FluidAttributes} carried colour/sound/density/viscosity as inert
 * metadata and vanilla gated every movement effect on {@code FluidTags.WATER}/{@code LAVA}, so you
 * walked through maple sap as if it were air. Batch 3 (F13, {@code 3f25463f6}) restored that
 * outcome for drowning, swimming, current pushing and mob path types through
 * {@code FluidType.Properties}, and flagged the one residual it could not reach: vanilla
 * {@code LivingEntity#travel} (LivingEntity.java:2170-2202) applies <em>water</em> buoyancy and
 * drag inside <em>any</em> non-lava fluid type, and no property switches that off. This class
 * replaces that residual with physics derived from what each fluid actually is.
 *
 * <h2>The model</h2>
 * {@link #move} is vanilla's own in-water travel block, line for line, with three scalars threaded
 * through it. Everything else in that block — the {@code WATER_MOVEMENT_EFFICIENCY} (depth strider)
 * ramp, Dolphin's Grace, {@code SWIM_SPEED}, the climbable clamp, the
 * {@code getFluidFallingAdjustedMovement} slow-falling stabiliser and the
 * {@code horizontalCollision -> dy = 0.3} hop-out — is kept verbatim, so a Rankine fluid feels like
 * water that has been made thicker or thinner rather than like a different game.
 *
 * <p>The three scalars are derived <b>once, at registration</b> from the fluid's own declared
 * {@code viscosity} and {@code density} (see {@code RankineFluids#fluidType}); {@link #move} does
 * arithmetic only — no registry lookups, no map lookups, no extra allocation beyond the {@link Vec3}
 * instances vanilla itself creates on the same path.
 *
 * <ul>
 *   <li><b>{@link #getSpeedFactor() speedFactor}</b> — horizontal speed as a fraction of what the
 *       same entity would reach in water, from a power law in viscosity:
 *       {@code S = (viscosity / 1000) ^ -0.18}, clamped to
 *       {@code [}{@value #MIN_SPEED_FACTOR}{@code , }{@value #MAX_SPEED_FACTOR}{@code ]}.
 *       Water's 1000 gives exactly {@code 1.0}, i.e. vanilla. It is applied by rewriting vanilla's
 *       drag coefficient {@code f4} so that the <em>terminal speed</em> scales by exactly {@code S}:
 *       vanilla's per-tick recurrence {@code v <- (v + a) * f4} settles at {@code v = a * k} with
 *       {@code k = f4 / (1 - f4)}, so scaling {@code k} by {@code S} and inverting
 *       ({@code f4' = kS / (1 + kS)}) scales the terminal speed by {@code S} while leaving
 *       acceleration, and therefore responsiveness, alone. The floor exists so that no fluid can
 *       ever become a trap: at {@value #MIN_SPEED_FACTOR} an entity still walks a fifth as fast as
 *       it swims in water.</li>
 *   <li><b>{@link #getSinkScale() sinkScale}</b> — Archimedes, normalised so that water reproduces
 *       vanilla: {@code (1 - density / 1600) / (1 - 1000 / 1600)}. It multiplies the {@code gravity}
 *       vanilla hands to {@code getFluidFallingAdjustedMovement}, which sinks an entity at
 *       {@code gravity / 16} per tick. {@code 1.0} at density 1000 is therefore vanilla water
 *       exactly; a fluid denser than {@value #NEUTRAL_BUOYANCY_DENSITY} gives a negative value and
 *       the entity is pushed <em>up</em> instead, at the same {@code gravity / 16} scale. Clamped to
 *       {@code [-}{@value #MAX_LIFT_SCALE}{@code , }{@value #MAX_SINK_SCALE}{@code ]}: the lift cap
 *       means the most buoyant fluid possible lifts exactly as fast as water sinks, which is gentle
 *       enough that a surfacing entity bobs rather than being launched.</li>
 *   <li><b>{@link #getVerticalDrag() verticalDrag}</b> — vanilla's hard-coded {@code 0.8F} vertical
 *       damping, scaled by thickness: {@code 0.8 * S ^ 0.35}, clamped to
 *       {@code [}{@value #MIN_VERTICAL_DRAG}{@code , }{@value #MAX_VERTICAL_DRAG}{@code ]}. Thick
 *       fluids swallow vertical momentum, which is what makes falling into resin feel like landing
 *       in tar.</li>
 * </ul>
 *
 * <h2>The exit guarantee</h2>
 * No fluid may pin an entity. Three independent escapes are preserved or proven:
 * <ol>
 *   <li>{@code speedFactor >= }{@value #MIN_SPEED_FACTOR} keeps horizontal travel strictly positive,
 *       so an entity can always cross a pool and reach its rim — or walk straight out of one whose
 *       rim is level with its floor.</li>
 *   <li><b>Holding jump always wins.</b> {@code LivingEntity#aiStep} (LivingEntity.java:2712-2738)
 *       adds {@value #JUMP_IN_FLUID_IMPULSE} to {@code deltaMovement.y} through
 *       {@code ILivingEntityExtension#jumpInFluid} <em>before</em> {@code travel} runs, so with
 *       {@code d} the vertical speed at the top of a tick the loop is
 *       {@code d' = d * verticalDrag - gravity / 16 * sinkScale + 0.04}, settling at
 *       {@code (0.04 - gravity / 16 * sinkScale) / (1 - verticalDrag)}. The entity therefore rises
 *       whenever {@code 0.04 > gravity / 16 * sinkScale}, which the sink clamp alone makes
 *       unconditional at default gravity ({@code 0.005 * }{@value #MAX_SINK_SCALE}
 *       {@code = 0.010 < 0.04}). Note what this does <em>not</em> depend on: {@code verticalDrag}.
 *       Heavier damping slows the ascent but cannot reverse it, because the impulse is re-applied
 *       every tick while only the carried-over velocity is damped — a falsification run that
 *       collapsed resin's {@code verticalDrag} to 0.05 still let the entity climb out, which is why
 *       the condition is stated this way rather than as
 *       {@code 0.04 * verticalDrag > gravity / 16 * sinkScale}.
 *       {@code RankineRegistryTests#rankineFluidPhysicalPropertiesArePinned} re-checks both the sign
 *       and the resulting rise rate per fluid with the real numbers.</li>
 *   <li>Vanilla's {@code horizontalCollision && isFree(...)} hop is kept verbatim, so an entity at
 *       the surface of a pool is lifted {@code 0.3} per tick onto the rim. That route only opens
 *       once the box the entity would move into is clear of fluid, because {@code Entity#isFree}
 *       also requires {@code !level.containsAnyLiquid(box)} (Entity.java:559-565) — exactly as in
 *       vanilla water, where climbing out from the bottom of a pool is likewise the jump's job.</li>
 * </ol>
 */
public class RankineFluidType extends FluidType {

    /** Viscosity of water in {@code FluidType.Properties} viscosity units; the reference point. */
    public static final int WATER_VISCOSITY = 1000;
    /** Density of water in {@code FluidType.Properties} density units (kg/m^3); the reference point. */
    public static final int WATER_DENSITY = 1000;

    /**
     * Exponent of the viscosity -> speed power law. Chosen so that the thickest fluid the mod
     * declares (resin, 5 Pa*s, 5000x water) lands just above the {@value #MIN_SPEED_FACTOR} floor at
     * ~0.216 — visibly a crawl without hitting the clamp, which keeps the floor a genuine guard
     * against future declarations rather than a value anything currently relies on.
     */
    private static final double SPEED_EXPONENT = 0.18D;
    /** Slowest any fluid may make an entity, as a fraction of its speed in water. */
    public static final double MIN_SPEED_FACTOR = 0.20D;
    /** Fastest any fluid may make an entity, as a fraction of its speed in water. */
    public static final double MAX_SPEED_FACTOR = 1.50D;

    /**
     * Density at which a living entity is neutrally buoyant. Vanilla already sinks players in water
     * despite real bodies floating in it, and this model keeps that; the neutral point is set well
     * above water so that only clearly heavy fluids (>=1.6x water: concentrated sulfuric acid,
     * mercury) hold an entity up, and everything in between merely sinks more slowly.
     */
    private static final double NEUTRAL_BUOYANCY_DENSITY = 1600.0D;
    /** Fastest sink, as a multiple of water's {@code gravity / 16}. */
    public static final double MAX_SINK_SCALE = 2.0D;
    /** Strongest lift, as a multiple of water's {@code gravity / 16}. */
    public static final double MAX_LIFT_SCALE = 1.0D;

    /** Vanilla's vertical damping inside water ({@code LivingEntity#travel}, LivingEntity.java:2196). */
    public static final float WATER_VERTICAL_DRAG = 0.8F;
    /**
     * {@code LivingEntity#getWaterSlowDown()} (LivingEntity.java:2148-2150) — vanilla's horizontal
     * damping inside water. Numerically equal to {@link #WATER_VERTICAL_DRAG} but a different knob.
     */
    public static final float WATER_SLOW_DOWN = 0.8F;
    /** How hard thickness bites into vertical damping. */
    private static final double VERTICAL_DRAG_EXPONENT = 0.35D;
    /** Heaviest vertical damping allowed; bounds how slowly an entity can rise, never whether it can. */
    public static final double MIN_VERTICAL_DRAG = 0.45D;
    /** Lightest vertical damping allowed. */
    public static final double MAX_VERTICAL_DRAG = 0.85D;

    /** Vanilla's per-tick upward impulse while jump is held in a fluid ({@code jumpInFluid}). */
    public static final double JUMP_IN_FLUID_IMPULSE = 0.04D;
    /** Vanilla divides gravity by this before applying it inside a fluid. */
    public static final double FLUID_GRAVITY_DIVISOR = 16.0D;

    private final double speedFactor;
    private final double sinkScale;
    private final double verticalDrag;

    public RankineFluidType(Properties properties) {
        super(properties);
        this.speedFactor = speedFactor(this.getViscosity());
        this.sinkScale = sinkScale(this.getDensity());
        this.verticalDrag = verticalDrag(this.speedFactor);
    }

    /**
     * Horizontal speed as a fraction of the same entity's speed in water. Static so that
     * {@code RankineFluids} can derive {@code fallDistanceModifier} from it while still building the
     * {@link FluidType.Properties}, before any instance exists.
     */
    public static double speedFactor(int viscosity) {
        double relative = Math.max((double) viscosity, 1.0D) / (double) WATER_VISCOSITY;
        return Mth.clamp(Math.pow(relative, -SPEED_EXPONENT), MIN_SPEED_FACTOR, MAX_SPEED_FACTOR);
    }

    /** Multiplier on vanilla's {@code gravity / 16} sink term; negative means the fluid lifts. */
    public static double sinkScale(int density) {
        double neutral = 1.0D - (double) density / NEUTRAL_BUOYANCY_DENSITY;
        double waterNeutral = 1.0D - (double) WATER_DENSITY / NEUTRAL_BUOYANCY_DENSITY;
        return Mth.clamp(neutral / waterNeutral, -MAX_LIFT_SCALE, MAX_SINK_SCALE);
    }

    /** Vanilla's {@value #WATER_VERTICAL_DRAG} vertical damping, thickened with the fluid. */
    public static double verticalDrag(double speedFactor) {
        return Mth.clamp((double) WATER_VERTICAL_DRAG * Math.pow(speedFactor, VERTICAL_DRAG_EXPONENT),
                MIN_VERTICAL_DRAG, MAX_VERTICAL_DRAG);
    }

    /**
     * The fall-distance multiplier a fluid of this viscosity earns, applied once per tick the entity
     * touches it ({@code Entity#updateInWaterStateAndDoFluidPushing}, Entity.java:1261).
     *
     * <p><b>Deliberate override of F13's {@code fallDistanceModifier(1.0F)}</b>, which was the
     * 1.18.2-parity value ("fluids do not cushion anything"). Karl's decision explicitly allows this
     * where a viscous fluid should realistically break a fall, and a fluid's ability to absorb
     * momentum is the same physical quantity that slows a walk — so this is exactly
     * {@link #speedFactor(int)}, capped at 1 so no fluid can ever <em>increase</em> fall damage.
     * A fluid at water's viscosity still gets exactly {@code 1.0}, i.e. F13's value is preserved at
     * the reference point and only genuinely thick fluids deviate from it. Because it is per tick,
     * a shallow puddle cushions far less than a deep pool, which is the right shape.
     */
    public static float fallDistanceModifier(int viscosity) {
        return (float) Math.min(1.0D, speedFactor(viscosity));
    }

    public double getSpeedFactor() {
        return this.speedFactor;
    }

    public double getSinkScale() {
        return this.sinkScale;
    }

    public double getVerticalDrag() {
        return this.verticalDrag;
    }

    /**
     * Vanilla's in-water travel block ({@code LivingEntity#travel}, LivingEntity.java:2172-2202)
     * with {@link #getSpeedFactor()}, {@link #getVerticalDrag()} and {@link #getSinkScale()} threaded
     * through it. Returning {@code true} tells {@code ILivingEntityExtension#moveInFluid}
     * (LivingEntity.java:2171) that the fluid handled the whole step, so vanilla's water branch is
     * skipped rather than run twice.
     *
     * <p>The one thing that cannot be carried over verbatim is {@code getWaterSlowDown()}: it is
     * {@code protected} on {@code LivingEntity}, so this uses the base value {@code 0.8F} it returns
     * for every entity in the game except {@code SkeletonHorse} (0.96) and {@code PolarBear} (0.98).
     * Those two lose their swimming bonus in Rankine fluids, which is the answer this feature wants
     * anyway — being adapted to water says nothing about being adapted to resin.
     */
    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        double startY = entity.getY();
        boolean falling = entity.getDeltaMovement().y <= 0.0D;

        // --- vanilla, verbatim: the drag/acceleration pair and everything that modifies it ---
        float drag = entity.isSprinting() ? 0.9F : WATER_SLOW_DOWN;
        float acceleration = 0.02F;
        float efficiency = (float) entity.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (!entity.onGround()) {
            efficiency *= 0.5F;
        }

        if (efficiency > 0.0F) {
            drag += (0.54600006F - drag) * efficiency;
            acceleration += (entity.getSpeed() - acceleration) * efficiency;
        }

        if (entity.hasEffect(MobEffects.DOLPHINS_GRACE)) {
            drag = 0.96F;
        }

        acceleration *= (float) entity.getAttributeValue(NeoForgeMod.SWIM_SPEED);
        entity.moveRelative(acceleration, movementVector);
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        Vec3 moved = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.onClimbable()) {
            moved = new Vec3(moved.x, 0.2D, moved.z);
        }

        // --- parametrised: scale the terminal speed by speedFactor, then invert back to a drag ---
        double coefficient = (double) drag / (1.0D - (double) drag) * this.speedFactor;
        double scaledDrag = coefficient / (1.0D + coefficient);
        entity.setDeltaMovement(moved.multiply(scaledDrag, this.verticalDrag, scaledDrag));

        // --- parametrised: vanilla's gravity/16 sink, scaled by density; negative means lift ---
        if (this.sinkScale > 0.0D) {
            entity.setDeltaMovement(entity.getFluidFallingAdjustedMovement(
                    gravity * this.sinkScale, falling, entity.getDeltaMovement()));
        } else if (this.sinkScale < 0.0D && !entity.isSprinting()) {
            // Mirrors the !isSprinting() guard inside getFluidFallingAdjustedMovement, so sprinting
            // still lets a player dive through a buoyant fluid exactly as it lets them dive in water.
            Vec3 buoyant = entity.getDeltaMovement();
            entity.setDeltaMovement(buoyant.x,
                    buoyant.y - gravity * this.sinkScale / FLUID_GRAVITY_DIVISOR, buoyant.z);
        }

        // --- vanilla, verbatim: the hop-out that lets an entity climb the rim of a pool ---
        Vec3 result = entity.getDeltaMovement();
        if (entity.horizontalCollision
                && entity.isFree(result.x, result.y + 0.6F - entity.getY() + startY, result.z)) {
            entity.setDeltaMovement(result.x, 0.3F, result.z);
        }

        return true;
    }
}
