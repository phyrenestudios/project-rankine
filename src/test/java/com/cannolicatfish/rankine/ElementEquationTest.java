package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.util.ElementEquation;
import com.cannolicatfish.rankine.util.ElementEquation.FormulaModifier;
import com.cannolicatfish.rankine.util.ElementEquation.FormulaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ElementEquation drives every alloy stat curve (durability, mining speed, damage...).
 * It is pure math with no Minecraft dependencies, so it is worth pinning precisely:
 * a silent change here would rebalance the entire mod without failing anything else.
 */
class ElementEquationTest {

    private static ElementEquation single(FormulaType type, float a, float b, FormulaModifier mod, float limit) {
        return new ElementEquation(
                new int[]{100},
                new FormulaType[]{type},
                new float[]{a},
                new float[]{b},
                new FormulaModifier[]{mod},
                new float[]{limit});
    }

    @Test
    @DisplayName("empty equation reports empty and yields zero")
    void emptyEquation() {
        ElementEquation eq = new ElementEquation();
        assertTrue(eq.isEmpty());
        assertEquals(0f, eq.calculateFloat(50));
        assertEquals(0, eq.calculateRounded(50));
    }

    @Test
    @DisplayName("linear formula: a*x + b")
    void linear() {
        ElementEquation eq = single(FormulaType.LINEAR, 2f, 5f, FormulaModifier.NONE, 0f);
        assertEquals(25f, eq.calculateFloat(10), 1e-4);
        assertEquals(5f, eq.calculateFloat(0), 1e-4);
    }

    @Test
    @DisplayName("input beyond the last break yields zero")
    void beyondLastBreak() {
        ElementEquation eq = single(FormulaType.LINEAR, 2f, 5f, FormulaModifier.NONE, 0f);
        assertEquals(0f, eq.calculateFloat(101), 1e-4);
    }

    @Test
    @DisplayName("breaks select the matching piece of a piecewise curve")
    void piecewiseSelectsByBreak() {
        ElementEquation eq = new ElementEquation(
                new int[]{10, 100},
                new FormulaType[]{FormulaType.CONSTANT, FormulaType.CONSTANT},
                new float[]{1f, 2f},
                new float[]{0f, 0f},
                new FormulaModifier[]{FormulaModifier.NONE, FormulaModifier.NONE},
                new float[]{0f, 0f});
        assertEquals(1f, eq.calculateFloat(5), 1e-4, "x<=10 uses the first piece");
        assertEquals(1f, eq.calculateFloat(10), 1e-4, "break is inclusive");
        assertEquals(2f, eq.calculateFloat(11), 1e-4, "x>10 falls through to the second piece");
    }

    @Test
    @DisplayName("quadratic formula: a*x^2 + b*x")
    void quadratic() {
        ElementEquation eq = single(FormulaType.QUADRATIC, 2f, 3f, FormulaModifier.NONE, 0f);
        assertEquals(2f * 16f + 3f * 4f, eq.calculateFloat(4), 1e-3);
    }

    @Test
    @DisplayName("power formula: x^a + b")
    void power() {
        ElementEquation eq = single(FormulaType.POWER, 2f, 1f, FormulaModifier.NONE, 0f);
        assertEquals(26f, eq.calculateFloat(5), 1e-3);
    }

    @Test
    @DisplayName("exponential formula: a^x + b")
    void exponential() {
        ElementEquation eq = single(FormulaType.EXPONENTIAL, 2f, 1f, FormulaModifier.NONE, 0f);
        assertEquals(9f, eq.calculateFloat(3), 1e-3);
    }

    @Test
    @DisplayName("MIN and MAX modifiers clamp against limit")
    void clampModifiers() {
        ElementEquation min = single(FormulaType.LINEAR, 1f, 0f, FormulaModifier.MIN, 10f);
        assertEquals(10f, min.calculateFloat(50), 1e-4, "MIN takes the smaller of value and limit");
        assertEquals(5f, min.calculateFloat(5), 1e-4);

        ElementEquation max = single(FormulaType.LINEAR, 1f, 0f, FormulaModifier.MAX, 10f);
        assertEquals(10f, max.calculateFloat(5), 1e-4, "MAX takes the larger of value and limit");
        assertEquals(50f, max.calculateFloat(50), 1e-4);
    }

    @Test
    @DisplayName("FLOOR, CEILING and ABSOLUTE_VALUE modifiers")
    void roundingModifiers() {
        assertEquals(2f, single(FormulaType.LINEAR, 0.5f, 0f, FormulaModifier.FLOOR, 0f).calculateFloat(5), 1e-4);
        assertEquals(3f, single(FormulaType.LINEAR, 0.5f, 0f, FormulaModifier.CEILING, 0f).calculateFloat(5), 1e-4);
        assertEquals(5f, single(FormulaType.LINEAR, -1f, 0f, FormulaModifier.ABSOLUTE_VALUE, 0f).calculateFloat(5), 1e-4);
    }

    @Test
    @DisplayName("MULTIPLIER and ADDITION modifiers transform x before the formula")
    void inputTransformModifiers() {
        // percent = x * limit, then LINEAR a*percent + b
        ElementEquation mult = single(FormulaType.LINEAR, 1f, 0f, FormulaModifier.MULTIPLIER, 3f);
        assertEquals(30f, mult.calculateFloat(10), 1e-4);

        // percent = x + limit
        ElementEquation add = single(FormulaType.LINEAR, 1f, 0f, FormulaModifier.ADDITION, 7f);
        assertEquals(17f, add.calculateFloat(10), 1e-4);
    }

    @Test
    @DisplayName("ALTERNATING picks a or b by x modulo 2 by default")
    void alternating() {
        ElementEquation eq = single(FormulaType.ALTERNATING, 2f, 3f, FormulaModifier.NONE, 0f);
        assertEquals(2f * 4f, eq.calculateFloat(4), 1e-4, "even x uses a");
        assertEquals(3f * 5f, eq.calculateFloat(5), 1e-4, "odd x uses b");
    }

    @Test
    @DisplayName("ALTERNATING_MODULO overrides the modulo with limit")
    void alternatingModulo() {
        ElementEquation eq = single(FormulaType.CONSTANT_ALTERNATING, 2f, 3f, FormulaModifier.ALTERNATING_MODULO, 3f);
        assertEquals(2f, eq.calculateFloat(9), 1e-4, "x divisible by 3 uses a");
        assertEquals(3f, eq.calculateFloat(10), 1e-4, "otherwise b");
    }

    @Test
    @DisplayName("CONSTANT ignores x")
    void constant() {
        ElementEquation eq = single(FormulaType.CONSTANT, 42f, 999f, FormulaModifier.NONE, 0f);
        assertEquals(42f, eq.calculateFloat(1), 1e-4);
        assertEquals(42f, eq.calculateFloat(99), 1e-4);
    }

    @Test
    @DisplayName("calculateRounded rounds the float result")
    void rounded() {
        ElementEquation eq = single(FormulaType.LINEAR, 0.5f, 0f, FormulaModifier.NONE, 0f);
        assertEquals(3, eq.calculateRounded(5), "2.5 rounds to 3");
        assertEquals(2, eq.calculateRounded(4));
    }
}
