package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.util.WeightedCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WeightedCollection backs crushing/sluicing/foraging drop rolls. Seeded so the
 * distribution assertions are deterministic rather than flaky.
 */
class WeightedCollectionTest {

    @Test
    @DisplayName("single entry always returned")
    void singleEntry() {
        WeightedCollection<String> c = new WeightedCollection<String>(new Random(1)).add(1f, "only");
        for (int i = 0; i < 20; i++) {
            assertEquals("only", c.getRandomElement());
        }
    }

    @Test
    @DisplayName("non-positive weights are ignored")
    void nonPositiveWeightsIgnored() {
        WeightedCollection<String> c = new WeightedCollection<String>(new Random(1))
                .add(0f, "zero")
                .add(-5f, "negative")
                .add(1f, "kept");
        assertEquals(1, c.getMap().size());
        assertEquals("kept", c.getRandomElement());
    }

    @Test
    @DisplayName("draw frequency tracks the assigned weights")
    void weightsDriveDistribution() {
        WeightedCollection<String> c = new WeightedCollection<String>(new Random(12345))
                .add(90f, "common")
                .add(10f, "rare");

        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < 10_000; i++) {
            counts.merge(c.getRandomElement(), 1, Integer::sum);
        }

        int common = counts.getOrDefault("common", 0);
        int rare = counts.getOrDefault("rare", 0);
        assertEquals(10_000, common + rare);
        // 90/10 split: allow generous slack so the test cannot flake, while still
        // catching an inverted or ignored weight.
        assertTrue(common > 8_500 && common < 9_500, "common drawn " + common + " times, expected ~9000");
        assertTrue(rare > 500 && rare < 1_500, "rare drawn " + rare + " times, expected ~1000");
    }

    @Test
    @DisplayName("removal draw shrinks the pool and never repeats an entry")
    void removalDrawsEachEntryOnce() {
        WeightedCollection<String> c = new WeightedCollection<String>(new Random(7))
                .add(1f, "a").add(1f, "b").add(1f, "c");
        assertEquals(3, c.getMap().size());

        String first = c.getRandomElementWithRemoval();
        assertEquals(2, c.getMap().size());
        String second = c.getRandomElementWithRemoval();
        assertEquals(1, c.getMap().size());
        String third = c.getRandomElementWithRemoval();
        assertEquals(0, c.getMap().size());

        assertNotEquals(first, second);
        assertNotEquals(second, third);
        assertNotEquals(first, third);
    }

    @Test
    @DisplayName("entries view exposes every added element")
    void entriesView() {
        WeightedCollection<String> c = new WeightedCollection<String>(new Random(3))
                .add(1f, "x").add(2f, "y");
        assertTrue(c.getEntries().containsAll(java.util.List.of("x", "y")));
        assertEquals(2, c.getWeights().size());
    }
}
