package com.cannolicatfish.rankine.util.colors;

/**
 * Alpha guard for {@link net.minecraft.client.color.item.ItemColor} results.
 *
 * <p>1.18.2's ItemRenderer read only the RGB channels of a tint and always drew items opaque.
 * 1.21 treats the returned int as ARGB and passes its alpha straight through to the quad:
 * <pre>
 *   float f = (float)FastColor.ARGB32.alpha(i) / 255.0F;      // ItemRenderer#renderQuadList
 *   consumer.putBulkData(pose, quad, f1, f2, f3, f, ...);     // f is the quad's alpha
 * </pre>
 * Every Rankine tint is a plain 0xRRGGBB value -- alloy colours stored by AlloyingRecipe, the
 * biome colours borrowed from the block handlers, and the 16777215 "no tint" fallback all have
 * an alpha byte of zero. Under 1.21 that renders the item completely invisible: the stack is
 * present and keeps its data components, but nothing is drawn.
 *
 * <p>Block tints are NOT affected -- BlockColors results are still treated as RGB -- which is why
 * the same colour classes look correct on placed blocks and wrong in the inventory.
 */
public final class ItemTint {

    private ItemTint() {
    }

    /** Forces full alpha, restoring 1.18.2's "the alpha channel is ignored" behaviour. */
    public static int opaque(int rgb) {
        return 0xFF000000 | rgb;
    }
}
