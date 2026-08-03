package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Truth table for the alloy-gated enchantments (Antiquated, Shape Memory, Endobiotic).
 *
 * <p>1.18.2 enforced these inside the Enchantment classes, which read
 * {@code rs.equals(required) && <item type check>} in canEnchant, and the same with a hard
 * {@code false} fallback in canApplyAtEnchantingTable. 1.21 applicability is an item tag, which
 * keys on the item TYPE and cannot see the per-stack alloy, so the alloy half moved into
 * {@link IAlloyTool#alloyGateDecision}. The tag check arrives as {@code tagFallback}.
 *
 * <p>The GameTest covers the wiring — that the anvil and enchanting table really reach these hooks.
 * This covers the decision itself, which is where the 1.18.2 semantics are easy to get subtly wrong
 * and where no server boot is needed to check them.
 */
class AlloyEnchantmentGateTest {

    private static final ResourceLocation PEWTER = ResourceLocation.parse("rankine:alloying/pewter_alloying");
    private static final ResourceLocation ROSE_GOLD = ResourceLocation.parse("rankine:alloying/rose_gold_alloying");

    private static boolean anvil(ResourceLocation stackAlloy, ResourceLocation required, boolean tagFallback) {
        return IAlloyTool.alloyGateDecision(stackAlloy, required, false, tagFallback);
    }

    private static boolean table(ResourceLocation stackAlloy, ResourceLocation required, boolean tagFallback) {
        return IAlloyTool.alloyGateDecision(stackAlloy, required, true, tagFallback);
    }

    @Test
    @DisplayName("an enchantment that is not alloy-gated defers entirely to the tag")
    void notGatedDefersToTag() {
        assertTrue(anvil(PEWTER, null, true));
        assertFalse(anvil(PEWTER, null, false));
        assertTrue(table(ROSE_GOLD, null, true));
        assertFalse(table(ROSE_GOLD, null, false));
    }

    @Test
    @DisplayName("the matching alloy is accepted on both paths")
    void matchingAlloyAccepted() {
        assertTrue(anvil(PEWTER, PEWTER, true));
        assertTrue(table(PEWTER, PEWTER, true));
    }

    @Test
    @DisplayName("the wrong alloy is refused on both paths, whatever the tag says")
    void wrongAlloyRefused() {
        assertFalse(anvil(ROSE_GOLD, PEWTER, true));
        assertFalse(table(ROSE_GOLD, PEWTER, true));
        assertFalse(anvil(ROSE_GOLD, PEWTER, false));
        assertFalse(table(ROSE_GOLD, PEWTER, false));
    }

    /**
     * The alloy match must not be able to override the item type. 1.18.2 required
     * {@code stack.getItem() instanceof IAlloyTool} alongside the recipe comparison, so a
     * pewter-alloyed ALLOY HELMET was refused Antiquated. That type check is the tag now, so
     * dropping it here would let any alloyed item accept a tool-only enchantment.
     */
    @Test
    @DisplayName("the matching alloy still cannot bypass the item-type tag")
    void matchingAlloyStillNeedsTheTag() {
        assertFalse(anvil(PEWTER, PEWTER, false));
        assertFalse(table(PEWTER, PEWTER, false));
    }

    /**
     * 1.18.2's two methods differed only here: canApplyAtEnchantingTable returned a hard false for
     * a stack with no alloy recipe, while canEnchant fell through to the type check.
     */
    @Test
    @DisplayName("a stack with no alloy: the table refuses, the anvil defers to the tag")
    void alloylessStackSplitsTheTwoPaths() {
        assertFalse(table(null, PEWTER, true));
        assertFalse(table(null, PEWTER, false));
        assertTrue(anvil(null, PEWTER, true));
        assertFalse(anvil(null, PEWTER, false));
    }
}
