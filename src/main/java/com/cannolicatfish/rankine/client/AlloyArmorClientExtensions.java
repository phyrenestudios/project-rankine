package com.cannolicatfish.rankine.client;

import com.cannolicatfish.rankine.recipe.helper.AlloyColorHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

/**
 * Puts the alloy colour back on the worn alloy helmet/chestplate/leggings/boots.
 *
 * <p>1.18.2: {@code AlloyArmorItem extends DyeableArmorItem}, and vanilla's
 * {@code HumanoidArmorLayer#renderArmorPiece} took the dyeable branch — base texture tinted with
 * {@code getColor(stack)}, then an untinted {@code _overlay} pass.
 *
 * <p>1.21.1: the tint is {@code IClientItemExtensions#getArmorLayerTintColor(...)}, whose default is
 * {@code layer.dyeable() ? fallbackColor : 0xFFFFFFFF} — which is already the 1.18.2 two-pass rule
 * once the material declares both layers (see {@code RankineArmorMaterials#layers}). Only the
 * fallback needs replacing: NeoForge's default {@code getDefaultDyeColor} reads
 * {@code DataComponents.DYED_COLOR} behind an {@code ItemTags.DYEABLE} check, and the alloy colour
 * lives in {@code RankineDataComponents.COLOR} instead, so it resolved to 0xFFFFFFFF (untinted) for
 * every alloy while the inventory icon stayed correctly tinted.
 *
 * <p>The alternative — write {@code DataComponents.DYED_COLOR} as well and add the four pieces to
 * {@code minecraft:dyeable} — would reach the same vanilla path, and would additionally restore
 * 1.18.2's crafting-table dyeing (1.18.2's {@code ArmorDyeRecipe} matched any
 * {@code DyeableLeatherItem}, so alloy armour really could be dyed then, and cannot now). It is not
 * taken here because it stores the alloy colour in two components that nothing keeps in step, and
 * because re-enabling player dyeing is a gameplay decision, not part of restoring the render. It is
 * flagged for triage. Overriding the fallback instead keeps {@code RankineDataComponents.COLOR} the
 * single source — literally the same call the inventory icon makes,
 * {@code AlloyColorHelper#getTintARGB}.
 */
public class AlloyArmorClientExtensions implements IClientItemExtensions {

    public static final AlloyArmorClientExtensions INSTANCE = new AlloyArmorClientExtensions();

    @Override
    public int getDefaultDyeColor(ItemStack stack) {
        return AlloyColorHelper.getTintARGB(stack);
    }
}
