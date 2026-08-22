package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.client.AlloyArmorClientExtensions;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.colors.AlloyItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The worn alloy armor layer and the inventory icon must return the identical tint.
 *
 * <p>This is the pin the GameTest cannot carry: both consumers are client-only classes
 * ({@code net.minecraft.client.color.item.ItemColor} and NeoForge's {@code IClientItemExtensions}),
 * so a dedicated-server GameTest must not load them, and it can only assert the shared accessor
 * they both call. Here — a JUnit run on the joined classpath — the two real objects the renderer
 * uses can be compared directly.
 *
 * <p>The regression being pinned: the port tinted the icon from
 * {@code RankineDataComponents.COLOR} while the worn layer fell through to NeoForge's default
 * {@code getDefaultDyeColor}, which reads {@code DataComponents.DYED_COLOR} behind an
 * {@code ItemTags.DYEABLE} check. Neither exists on alloy armour, so the worn piece rendered
 * 0xFFFFFFFF and mismatched its own icon.
 */
class AlloyArmorTintSourceTest {

    private static List<Item> alloyArmor() {
        return List.of(RankineItems.ALLOY_HELMET.get(), RankineItems.ALLOY_CHESTPLATE.get(),
                RankineItems.ALLOY_LEGGINGS.get(), RankineItems.ALLOY_BOOTS.get());
    }

    @Test
    @DisplayName("HumanoidArmorLayer's tint equals the inventory icon's tint, for every alloy piece")
    void wornTintEqualsIconTint() {
        AlloyItemColor icon = new AlloyItemColor();
        for (Item item : alloyArmor()) {
            ItemStack stack = new ItemStack(item);
            stack.set(RankineDataComponents.COLOR.get(), 0x2E86AB);

            int worn = AlloyArmorClientExtensions.INSTANCE.getDefaultDyeColor(stack);
            assertEquals(icon.getColor(stack, 0), worn, item + " worn tint differs from its icon tint");
            // ARGB with full alpha: 1.21 multiplies the armor quad by this value, and a stored
            // 0x00RRGGBB would render the piece invisible rather than untinted.
            assertEquals(0xFF2E86AB, worn, item + " worn tint is not the opaque alloy colour");
        }
    }

    @Test
    @DisplayName("a colourless alloy armor stack renders untinted white, not transparent")
    void colourlessStackIsWhite() {
        for (Item item : alloyArmor()) {
            ItemStack stack = new ItemStack(item);
            assertEquals(0xFFFFFFFF, AlloyArmorClientExtensions.INSTANCE.getDefaultDyeColor(stack), item.toString());
        }
    }
}
