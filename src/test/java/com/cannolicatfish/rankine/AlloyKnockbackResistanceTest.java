package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.items.alloys.IAlloyTieredItem;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.cannolicatfish.rankine.util.ElementEquation.FormulaModifier;
import com.cannolicatfish.rankine.util.ElementEquation.FormulaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The knockback-resistance balance decision (release decision 1B, 2026-08-22), pinned end to end.
 *
 * <p>This is a deliberate BALANCE change, not a parity fix, which is exactly why it needs a test
 * that states the numbers out loud. 1.18.2 gave alloy armour zero knockback resistance by accident
 * — {@code AlloyArmorItem#initStats} wrote the stat with {@code putFloat} and
 * {@code IAlloyTieredItem#getKnockbackResistance} read it back with {@code CompoundTag#getInt},
 * which floors a FloatTag, so everything under 1.0 came back 0. The port read the real float and
 * fed it straight to {@code Attributes.KNOCKBACK_RESISTANCE}, where 0.25 per piece is a full
 * four-piece 1.0 — total immunity to melee, mob and projectile knockback, reachable with an
 * early-game 25%-zinc brass set. Karl's decision was to keep the stat, scaled, so the strongest set
 * in the game lands on vanilla full netherite (0.4) rather than past it.
 *
 * <p>Two halves are asserted, and both matter:
 * <ol>
 *   <li>the shipped element CURVES, read out of the JSON in the repo rather than restated here, so
 *       the table below cannot silently drift when someone retunes zinc or krypton;</li>
 *   <li>{@link IAlloyTieredItem#scaleKnockbackResistance}, which is the single point the live
 *       attribute is fed from.</li>
 * </ol>
 * A GameTest cannot cover this usefully — there is no server behaviour to observe, only arithmetic
 * — and the arithmetic is the whole decision.
 */
class AlloyKnockbackResistanceTest {

    /** Vanilla's own reference point: one netherite armour piece grants this much. */
    private static final float VANILLA_NETHERITE_PER_PIECE = 0.1f;

    private static final String ELEMENTS = "src/main/resources/data/rankine/recipe/elements";

    /**
     * The knockbackresist curve out of an element's shipped recipe JSON, decoded the same way
     * {@code ElementRecipe}'s codec decodes it (breaks/formulas/a/b/modifiers/limit).
     */
    private static ElementEquation curve(String element) throws IOException {
        String projectDir = System.getProperty("rankine.projectDir");
        assertNotNull(projectDir, "system property rankine.projectDir is not set — build.gradle's test task passes it");
        Path file = Paths.get(projectDir).resolve(ELEMENTS).resolve(element + ".json");
        assertTrue(Files.isRegularFile(file), "no element recipe at " + file);

        JsonObject root;
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            root = JsonParser.parseReader(reader).getAsJsonObject();
        }
        JsonObject kr = root.getAsJsonObject("knockbackresist");
        assertNotNull(kr, element + ".json has no knockbackresist block");

        JsonArray breaks = kr.getAsJsonArray("breaks");
        JsonArray formulas = kr.getAsJsonArray("formulas");
        JsonArray a = kr.getAsJsonArray("a");
        JsonArray b = kr.getAsJsonArray("b");
        JsonArray modifiers = kr.getAsJsonArray("modifiers");
        JsonArray limit = kr.getAsJsonArray("limit");

        int n = breaks.size();
        int[] breaksOut = new int[n];
        FormulaType[] typesOut = new FormulaType[n];
        float[] aOut = new float[n];
        float[] bOut = new float[n];
        FormulaModifier[] modsOut = new FormulaModifier[n];
        float[] limitOut = new float[n];
        for (int i = 0; i < n; i++) {
            breaksOut[i] = breaks.get(i).getAsInt();
            typesOut[i] = FormulaType.valueOf(formulas.get(i).getAsString());
            aOut[i] = a.get(i).getAsFloat();
            bOut[i] = b.get(i).getAsFloat();
            modsOut[i] = FormulaModifier.valueOf(modifiers.get(i).getAsString());
            limitOut[i] = limit.get(i).getAsFloat();
        }
        return new ElementEquation(breaksOut, typesOut, aOut, bOut, modsOut, limitOut);
    }

    /**
     * {@code IAlloyTieredItem#createValueForKnockbackResistance} for a composition, minus the
     * alloy/modifier bonuses (no shipped alloying recipe declares a knockback bonus): sum the
     * per-element curves, clamp to [0,1], round to two decimals.
     */
    private static float rawPerPiece(Map<String, Integer> composition) throws IOException {
        float kr = 0f;
        for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            kr += curve(entry.getKey()).calculateFloat(entry.getValue());
        }
        return Math.round(Math.min(Math.max(0, kr), 1) * 100) / 100f;
    }

    private static Map<String, Integer> composition(Object... pairs) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put((String) pairs[i], (Integer) pairs[i + 1]);
        }
        return map;
    }

    /** What a wearer of four matching pieces actually gets: four ADD_VALUE modifiers on one attribute. */
    private static float fullSet(float rawPerPiece) {
        return 4f * IAlloyTieredItem.scaleKnockbackResistance(rawPerPiece);
    }

    @Test
    @DisplayName("brass at the zinc cap: 1.00 before, 0.10 for a full set after")
    void brassSet() throws IOException {
        // brass_alloying permits 15-60% zinc. Zinc is LINEAR a=0.01 with a MIN cap of 0.25, so the
        // cap already binds at 25% and a 60% set is worth no more than a 25% one -- which is why
        // both rows of the decision table land on the same number.
        float quarterZinc = rawPerPiece(composition("zinc", 25));
        float maxZinc = rawPerPiece(composition("zinc", 60));
        assertEquals(0.25f, quarterZinc, 1e-5f, "25% zinc per-piece curve value");
        assertEquals(0.25f, maxZinc, 1e-5f, "60% zinc per-piece curve value (the 0.25 cap binds)");

        // Before: the raw value went straight onto the attribute -> 4 x 0.25 = full immunity.
        assertEquals(1.0f, 4f * quarterZinc, 1e-5f, "the pre-fix four-piece total this decision removes");

        assertEquals(0.025f, IAlloyTieredItem.scaleKnockbackResistance(quarterZinc), 1e-6f);
        assertEquals(0.10f, fullSet(quarterZinc), 1e-5f, "25% zinc brass set");
        assertEquals(0.10f, fullSet(maxZinc), 1e-5f, "60% zinc brass set");
    }

    @Test
    @DisplayName("krypton-bearing ender amalgam: 0.75 per piece, 0.30 for a full set")
    void kryptonSet() throws IOException {
        // ender_amalgam_alloying is the only recipe that admits krypton, at up to 5% -- which is
        // already where krypton's own 0.5 cap binds (LINEAR a=0.1) -- alongside up to 50% zinc,
        // capped at 0.25. Those two carry the set; the trace elements are pinned separately below.
        float raw = rawPerPiece(composition("krypton", 5, "zinc", 50));
        assertEquals(0.75f, raw, 1e-5f, "krypton + zinc per-piece curve value");
        assertEquals(0.30f, fullSet(raw), 1e-5f, "krypton-bearing set");
        assertTrue(fullSet(raw) < 1.0f);
    }

    @Test
    @DisplayName("the small contributors stay small")
    void traceElements() throws IOException {
        // Calcium and strontium are CONSTANT, so their contribution does not scale with how much
        // of them is in the alloy; phosphorus is linear but caps at 0.1. None of them can move a
        // set far on their own, which is what keeps ordinary alloys well under the netherite mark.
        assertEquals(0.05f, curve("calcium").calculateFloat(50), 1e-6f);
        assertEquals(0.05f, curve("calcium").calculateFloat(5), 1e-6f);
        assertEquals(0.025f, curve("strontium").calculateFloat(50), 1e-6f);
        assertEquals(0.1f, curve("phosphorus").calculateFloat(5), 1e-6f, "the 0.1 cap binds at 5%");
        assertEquals(0.04f, curve("phosphorus").calculateFloat(2), 1e-6f);

        // A netherite-BEARING (rather than netherite-based) alloy: stainless_steel_alloying admits
        // up to 12% netherite and 12% calcium. Nowhere near white gold, and it shows.
        float raw = rawPerPiece(composition("netherite", 12, "calcium", 12));
        assertEquals(0.17f, raw, 1e-5f, "stainless-steel-class per-piece curve value");
        assertEquals(0.068f, fullSet(raw), 1e-5f, "a netherite-bearing set stays far below netherite's 0.4");
    }

    @Test
    @DisplayName("netherite white gold: the strongest set in the game ties vanilla netherite at 0.40")
    void netheriteSet() throws IOException {
        // white_gold_nr_alloying permits 74-90% netherite plus up to 10% zinc. Netherite is LINEAR
        // a=0.01 and UNCAPPED, so 90% alone is 0.9 and the per-piece clamp does the rest.
        float raw = rawPerPiece(composition("netherite", 90, "zinc", 10));
        assertEquals(1.0f, raw, 1e-5f, "max netherite per-piece curve value (clamped)");

        assertEquals(VANILLA_NETHERITE_PER_PIECE, IAlloyTieredItem.scaleKnockbackResistance(raw), 1e-6f,
                "the strongest alloy piece must match one netherite piece");
        assertEquals(0.40f, fullSet(raw), 1e-5f, "the strongest realistic set == vanilla full netherite");
        assertTrue(fullSet(raw) < 1.0f, "no four-piece set may reach knockback immunity");
    }

    @Test
    @DisplayName("the scale is bounded, so nothing can reach immunity even with hand-written stats")
    void boundedForAnyInput() {
        assertEquals(0f, IAlloyTieredItem.scaleKnockbackResistance(0f), 1e-6f);
        assertEquals(0f, IAlloyTieredItem.scaleKnockbackResistance(-5f), 1e-6f, "negatives clamp to zero");
        assertEquals(VANILLA_NETHERITE_PER_PIECE, IAlloyTieredItem.scaleKnockbackResistance(1f), 1e-6f);
        // createValueForKnockbackResistance already clamps to [0,1]; this covers a stack whose
        // ALLOY_STATS component was written by hand or by a future datapack.
        assertEquals(VANILLA_NETHERITE_PER_PIECE, IAlloyTieredItem.scaleKnockbackResistance(87f), 1e-6f);
        assertTrue(fullSet(87f) < 1.0f, "even an absurd stored stat must not reach immunity");
    }
}
