package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Port fix F22 / finding #30: every Rankine {@link FluidType}'s description id must resolve to
 * real text, not the unmapped {@code fluid_type.rankine.*} key {@code FluidType.getDescriptionId()}
 * falls back to by default (mc-1.21.1-sources FluidType.java:146-152). That raw key is what
 * players saw in bucket/tank tooltips, this mod's own FusionFurnaceScreen/MixingBarrelScreen, and
 * JEI.
 *
 * <p>Verifies both ends directly against the live registry (this test class runs through
 * NeoForge's unitTest bootstrap, so {@code DeferredHolder.get()} is safe -- see
 * {@link AlloyArmorTintSourceTest}): the id {@code RankineFluids#fluidType(...)} actually sets on
 * each of the 16 fluid types, and that id's entry in the committed generated lang file (what the
 * client loads).
 */
class FluidDescriptionIdTest {

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
    @DisplayName("every registered Rankine fluid type's description id resolves to a non-empty generated lang entry")
    void everyFluidTypeDescriptionIdHasLangText() throws IOException {
        List<DeferredHolder<FluidType, ? extends FluidType>> entries =
                new ArrayList<>(RankineFluids.FLUID_TYPES.getEntries());
        assertEquals(16, entries.size(),
                "RankineFluids.FLUID_TYPES count drifted from 16 -- update this test's expectations");

        JsonObject lang = enUs();
        List<String> wrongFormat = new ArrayList<>();
        List<String> missingOrEmpty = new ArrayList<>();
        for (DeferredHolder<FluidType, ? extends FluidType> holder : entries) {
            FluidType type = holder.get();
            String descId = type.getDescriptionId();
            String name = holder.getId().getPath();

            // Finding #30's whole point: this must NOT be the unmapped "fluid_type.rankine.*"
            // default -- it must be the pre-existing 1.18.2-era "fluid.rankine.*" key.
            String expected = "fluid." + ProjectRankine.MODID + "." + name;
            if (!expected.equals(descId)) {
                wrongFormat.add(name + " -> " + descId + " (expected " + expected + ")");
                continue;
            }
            if (!lang.has(descId) || lang.get(descId).getAsString().isEmpty()) {
                missingOrEmpty.add(descId);
            }
        }
        assertTrue(wrongFormat.isEmpty(),
                "fluid types whose description id is not \"fluid.<modid>.<name>\": " + wrongFormat);
        assertTrue(missingOrEmpty.isEmpty(),
                "fluid types whose description id has no non-empty generated en_us.json entry "
                        + "(would render the raw key in buckets/tanks/JEI): " + missingOrEmpty);
    }
}
