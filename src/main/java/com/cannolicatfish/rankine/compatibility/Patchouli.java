package com.cannolicatfish.rankine.compatibility;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

/**
 * Patchouli compatibility WITHOUT compile-time Patchouli imports (dependency removed in the port).
 * The guide book stack is built by hand: patchouli:guide_book carrying the patchouli:book data
 * component, whose value is the book id.
 *
 * <p>1.18's Patchouli read a raw NBT tag literally named "patchouli:book". On 1.20.5+ that became a
 * real DataComponentType&lt;ResourceLocation&gt; registered as patchouli:book, and
 * {@code ItemModBook#getBookId} reads ONLY that component — it has no legacy-NBT fallback and never
 * looks at minecraft:custom_data. Writing the id into custom_data therefore produces a real guide
 * book bound to no book at all, i.e. a blank one. Keep this in sync with
 * data/rankine/recipe/rankine_journal.json, which must use the same component.
 */
public class Patchouli {

    private static final ResourceLocation BOOK_COMPONENT_ID =
            ResourceLocation.fromNamespaceAndPath("patchouli", "book");
    private static final ResourceLocation JOURNAL_BOOK_ID =
            ResourceLocation.fromNamespaceAndPath("rankine", "rankine_journal");

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
        DataComponentType<ResourceLocation> bookComponent = journalBookComponent();
        if (bookComponent == null) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(guideBook);
        stack.set(bookComponent, JOURNAL_BOOK_ID);
        return stack;
    }

    /** Patchouli's book-binding component, or null if Patchouli is absent or ever renames it. */
    @SuppressWarnings("unchecked")
    private static DataComponentType<ResourceLocation> journalBookComponent() {
        DataComponentType<?> raw = BuiltInRegistries.DATA_COMPONENT_TYPE.get(BOOK_COMPONENT_ID);
        return (DataComponentType<ResourceLocation>) raw;
    }
}
