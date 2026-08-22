package com.cannolicatfish.rankine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The generic Alloy Block's model must declare a tint index.
 *
 * <p>Why this exists: {@code BlockColors}/{@code ItemColors} are only consulted for quads whose
 * model declares a {@code tintindex} ({@code ModelBlockRenderer#putQuadData} and
 * {@code ItemRenderer#renderQuadList} both gate on {@code BakedQuad#isTinted()}). The alloy block's
 * model was {@code minecraft:block/cube_all}, which declares none, so the registered
 * {@code AlloyBlockColor} and {@code AlloyItemColor} handlers could not run whatever colour they
 * were given — the failure is total, silent, and invisible to every other gate, and it survived the
 * whole 1.18.2 line unnoticed. One reparent of the model in the block state provider would kill the
 * tint again, so pin the generated output.
 *
 * <p>Reads the committed datagen output, which is what the game loads.
 */
class AlloyBlockModelTest {

    private static final String BLOCK_MODEL =
            "src/generated/resources/assets/rankine/models/block/alloy_block.json";
    private static final String ITEM_MODEL =
            "src/generated/resources/assets/rankine/models/item/alloy_block.json";

    private static Path resource(String projectRelativePath) {
        String projectDir = System.getProperty("rankine.projectDir");
        assertNotNull(projectDir, "system property rankine.projectDir is not set");
        Path path = Paths.get(projectDir).resolve(projectRelativePath);
        assertTrue(Files.isRegularFile(path), path + " is not a file");
        return path;
    }

    private static JsonObject read(String projectRelativePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(resource(projectRelativePath), StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    @Test
    @DisplayName("every face of the alloy block model carries tintindex 0")
    void blockModelIsTinted() throws IOException {
        JsonObject model = read(BLOCK_MODEL);
        assertTrue(model.has("elements"),
                "alloy_block.json has no elements of its own, so it inherits an untinted parent ("
                        + model.get("parent") + ") and the alloy colour cannot render");

        JsonArray elements = model.getAsJsonArray("elements");
        assertEquals(1, elements.size(), "expected the single full cube");

        JsonObject faces = elements.get(0).getAsJsonObject().getAsJsonObject("faces");
        List<String> untinted = new ArrayList<>();
        for (String direction : List.of("north", "east", "south", "west", "up", "down")) {
            assertTrue(faces.has(direction), "alloy block model has no " + direction + " face");
            JsonObject face = faces.getAsJsonObject(direction);
            JsonElement tint = face.get("tintindex");
            if (tint == null || tint.getAsInt() != 0) {
                untinted.add(direction + "=" + tint);
            }
        }
        assertTrue(untinted.isEmpty(),
                "faces without tintindex 0 render the alloy block untinted: " + untinted);
    }

    @Test
    @DisplayName("the alloy block item model inherits the tinted block model")
    void itemModelInheritsTheTintedModel() throws IOException {
        JsonObject item = read(ITEM_MODEL);
        assertEquals("rankine:block/alloy_block", item.get("parent").getAsString(),
                "the inventory icon's tint comes from the block model's tintindex via this parent");
    }
}
