package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.util.colors.ItemTint;
import net.minecraft.world.item.ItemStack;

public class AlloyColorHelper {

    public static int getColor(ItemStack stack, int tintIndex) {
        Integer color = stack.get(RankineDataComponents.COLOR.get());
        if (color != null) {
            return color;
        }
        return 16777215;
    }

    /**
     * The one alloy tint every renderer reads, in ARGB with full alpha.
     *
     * <p>1.18.2 had a single source too — the stack's {@code "color"} NBT int — but two consumers
     * that could not disagree, because {@code ItemRenderer} ignored the alpha byte and
     * {@code HumanoidArmorLayer} split the same int into r/g/b floats. 1.21 pushes the two through
     * different APIs ({@code ItemColor} for the inventory icon,
     * {@code IClientItemExtensions#getDefaultDyeColor} for the worn armor layer) and honours the
     * alpha byte in both, so both call this and neither carries its own default: the worn piece and
     * its own inventory icon cannot drift apart, and neither can render invisible on a stack whose
     * stored colour has a zero alpha byte (every Rankine colour is a plain 0xRRGGBB).
     */
    public static int getTintARGB(ItemStack stack) {
        return ItemTint.opaque(getColor(stack, 0));
    }

}
