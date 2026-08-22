package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.mtt.MaterialTestingTableTile;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.level.block.state.BlockState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Port fix F24: the Material Testing Table's GUI opened with a blank title bar
 * ({@code .porting/CLIENT-TEST-PROGRESS.md} row 1, {@code v_r2g_71_title.png}). Root cause was
 * two-fold, and both halves existed identically in the 1.18.2 baseline too (not a port
 * regression, but real enough that the release batch fixes it):
 * <ol>
 *   <li>{@code MaterialTestingTableTile#getDisplayName()} returned
 *       {@code Component.literal(<raw block-entity-type registry path>)} -- an untranslated
 *       literal, not the block's real name.</li>
 *   <li>{@code MaterialTestingTableScreen#renderLabels} fully overrides
 *       {@code AbstractContainerScreen#renderLabels} without calling {@code super.renderLabels(...)},
 *       so the title (whatever it was) was never actually drawn.</li>
 * </ol>
 * This test pins the menu-provider half: {@code getDisplayName()} must return a real
 * translatable component pointing at the block's own (pre-existing, no new key)
 * "block.rankine.material_testing_table" lang entry, and that entry must resolve to non-empty
 * text in the generated lang. The screen-wiring half (the actual {@code super.renderLabels} call)
 * is a one-line, code-inspection-verifiable fix with no independent runtime hook to assert
 * against short of a full GUI screenshot, which is QA's job per {@code .porting/CLIENT-TESTING.md}.
 *
 * <p>Constructs the block entity directly (no live Level needed for this call) rather than a
 * GameTest: {@code Component#getString()} depends on {@code Language.getInstance()}, which a
 * dedicated {@code runGameTestServer} process never loads (client lang assets are a resource-pack
 * concern) -- asserting resolved English text there would be environment-dependent, not a real
 * regression guard. Checking the translation KEY plus the generated lang file directly (this
 * class runs through NeoForge's unitTest bootstrap, so {@code DeferredHolder.get()} is safe here,
 * per {@link AlloyArmorTintSourceTest}) is deterministic either way.
 */
class MaterialTestingTableTitleTest {

    private static JsonObject enUs() throws IOException {
        String projectDir = System.getProperty("rankine.projectDir");
        assertNotNull(projectDir, "system property rankine.projectDir is not set");
        Path path = Paths.get(projectDir).resolve("src/generated/resources/assets/rankine/lang/en_us.json");
        assertTrue(Files.isRegularFile(path), path + " is not a file");
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    @Test
    @DisplayName("MaterialTestingTableTile#getDisplayName() returns the block's real translatable name, not the raw registry path")
    void getDisplayNameIsTheBlocksRealName() {
        BlockState state = RankineBlocks.MATERIAL_TESTING_TABLE.get().defaultBlockState();
        MaterialTestingTableTile tile = new MaterialTestingTableTile(BlockPos.ZERO, state);

        Component name = tile.getDisplayName();
        assertNotNull(name, "getDisplayName() returned null");

        TranslatableContents contents = assertInstanceOf(TranslatableContents.class, name.getContents(),
                "getDisplayName() must be a real Component.translatable(...), not a literal -- "
                        + "the pre-fix bug was Component.literal(<raw registry path>)");
        assertEquals("block.rankine.material_testing_table", contents.getKey(),
                "expected the block's own standard description id (no new lang key needed)");

        // Belt-and-braces: even without lang resolution, getString() must not be empty and must
        // not equal the old buggy literal value.
        String text = name.getString();
        assertFalse(text.isEmpty(), "MTT title resolves to empty text");
        assertNotEquals("material_testing_table", text,
                "MTT title fell back to the raw un-translated registry path (the pre-fix bug)");
    }

    @Test
    @DisplayName("block.rankine.material_testing_table has non-empty text in the generated lang")
    void titleKeyHasLangText() throws IOException {
        String descId = RankineBlocks.MATERIAL_TESTING_TABLE.get().getDescriptionId();
        assertEquals("block.rankine.material_testing_table", descId);

        JsonObject lang = enUs();
        assertTrue(lang.has(descId), "no \"" + descId + "\" entry in the generated en_us.json");
        assertFalse(lang.get(descId).getAsString().isEmpty(), "\"" + descId + "\" is present but empty");
    }
}
