package com.cannolicatfish.rankine.compatibility;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.fml.ModList;

/**
 * Patchouli compatibility WITHOUT compile-time Patchouli imports (dependency removed in the port).
 * The guide book stack is built by hand: patchouli:guide_book + custom data {"patchouli:book": <book id>}.
 */
public class Patchouli {

    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().isLoaded("patchouli");
    }

    /**
     * Replacement for PatchouliAPI.get().getBookStack(ResourceLocation.parse("rankine:rankine_journal")).
     * Returns ItemStack.EMPTY when Patchouli is not installed.
     */
    public static ItemStack getJournalBookStack() {
        if (!isInstalled()) {
            return ItemStack.EMPTY;
        }
        Item guideBook = BuiltInRegistries.ITEM.get(ResourceLocation.parse("patchouli:guide_book"));
        if (guideBook == Items.AIR) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(guideBook);
        CompoundTag tag = new CompoundTag();
        tag.putString("patchouli:book", "rankine:rankine_journal");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return stack;
    }
}
