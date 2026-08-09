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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integrity of the Rankine Journal's Patchouli content.
 *
 * <p>Why this exists: Patchouli resolves item strings eagerly, at book-build time. An unresolvable
 * id makes {@code ItemStackUtil.deserializeStack} throw, {@code BookContentsBuilder} wraps it, and
 * {@code Book.reloadContents} catches it at the top level and installs {@code BookContents.empty()}.
 * So <em>one</em> bad item id anywhere in the book blanks the <em>whole</em> book — and the only
 * trace is a single client-side log line. No headless gate can see that, which makes it exactly the
 * kind of silent-and-total failure worth a cheap permanent guard. This is the Patchouli analogue of
 * the elementRecipeIngredientsResolve GameTest.
 *
 * <p>Reads the authored source tree rather than the classpath: the mod is packaged as a jar for unit
 * tests, and walking a jar FileSystem buys nothing here. Gradle runs tests with the project directory
 * as the working directory, so these relative paths resolve. rankine: ids are validated against the
 * generated item models, which is what the game will look for too; minecraft: ids are left alone,
 * since vanilla is not ours to validate.
 */
class PatchouliJournalContentTest {

    private static final String BOOK_ROOT =
            "src/main/resources/assets/rankine/patchouli_books/rankine_journal/en_us";
    // Most item models are generated, but a handful are hand-written; an id is valid if either has it.
    private static final String GENERATED_ITEM_MODEL_ROOT =
            "src/generated/resources/assets/rankine/models/item";
    private static final String HANDWRITTEN_ITEM_MODEL_ROOT =
            "src/main/resources/assets/rankine/models/item";

    private static Path resourceDir(String projectRelativePath) {
        String projectDir = System.getProperty("rankine.projectDir");
        assertNotNull(projectDir,
                "system property rankine.projectDir is not set — build.gradle's test task should pass it, "
                        + "since MDG runs tests from build/minecraft-junit rather than the project root");
        Path dir = Paths.get(projectDir).resolve(projectRelativePath);
        assertTrue(Files.isDirectory(dir), dir + " is not a directory");
        return dir;
    }

    private static List<Path> jsonFilesUnder(Path root) throws IOException {
        try (Stream<Path> walk = Files.walk(root)) {
            return walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".json"))
                    .sorted()
                    .toList();
        }
    }

    /** Collects the values of every key Patchouli parses as an item stack, at any depth. */
    private static void collectItemStrings(JsonElement element, String key, List<String> out) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            for (String k : obj.keySet()) {
                collectItemStrings(obj.get(k), k, out);
            }
        } else if (element.isJsonArray()) {
            JsonArray arr = element.getAsJsonArray();
            for (JsonElement e : arr) {
                collectItemStrings(e, key, out);
            }
        } else if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()
                && key != null && isItemKey(key)) {
            out.add(element.getAsString());
        }
    }

    private static boolean isItemKey(String key) {
        // "item"/"icon" are Patchouli's own; item0..item8 are this book's own templates.
        return key.equals("item") || key.equals("icon") || key.matches("item[0-9]+");
    }

    @Test
    @DisplayName("every rankine item id referenced by the journal resolves to a real item")
    void journalItemIdsResolve() throws IOException {
        Set<String> knownItems = new HashSet<>();
        for (String root : List.of(GENERATED_ITEM_MODEL_ROOT, HANDWRITTEN_ITEM_MODEL_ROOT)) {
            for (Path model : jsonFilesUnder(resourceDir(root))) {
                String name = model.getFileName().toString();
                knownItems.add(name.substring(0, name.length() - ".json".length()));
            }
        }
        assertTrue(knownItems.size() > 1000,
                "expected >1000 rankine item models, found " + knownItems.size()
                        + " — datagen output is probably missing");

        List<Path> contentFiles = jsonFilesUnder(resourceDir(BOOK_ROOT));
        assertTrue(contentFiles.size() > 50,
                "expected the journal's ~74 content files, found " + contentFiles.size());

        List<String> problems = new ArrayList<>();
        for (Path file : contentFiles) {
            List<String> refs = new ArrayList<>();
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                collectItemStrings(JsonParser.parseReader(reader), null, refs);
            } catch (Exception e) {
                problems.add(file.getFileName() + " does not parse: " + e.getMessage());
                continue;
            }
            for (String raw : refs) {
                String id = normalise(raw);
                if (id == null || !id.startsWith("rankine:")) continue;
                String path = id.substring("rankine:".length());
                // Texture paths and other non-item resources share the "icon" key; skip them.
                if (path.contains("/")) continue;
                if (!knownItems.contains(path)) {
                    problems.add(file.getFileName() + " -> " + id);
                }
            }
        }

        assertTrue(problems.isEmpty(),
                problems.size() + " unresolvable item reference(s) in the Rankine Journal — Patchouli "
                        + "would blank the ENTIRE book, not just these entries:\n  "
                        + String.join("\n  ", problems));
    }

    /** Strips Patchouli's optional count/components suffix and tag marker; null for a tag or blank. */
    private static String normalise(String raw) {
        String s = raw.trim().toLowerCase(Locale.ROOT);
        int cut = s.indexOf(' ');
        if (cut >= 0) s = s.substring(0, cut);
        cut = s.indexOf('{');
        if (cut >= 0) s = s.substring(0, cut);
        if (s.isEmpty() || s.startsWith("#")) return null;
        return s;
    }
}
