package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.data.RankineDataUtils;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.world.level.block.Block;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the 1.18.2-parity render-type assignment made by
 * {@code RankineBlockStateProvider#applyRenderTypes()} (port fix F21 -- review findings #28/#47,
 * finding #29, and the UNVERIFIED-FINDINGS.md "1,224 stone-brick ... models newly forced to the
 * unmipped cutout layer" pool item).
 *
 * <p>Reads the committed datagen output under {@code src/generated/resources} -- what the game
 * actually loads -- for the two datagen-rule fixes, plus the hand-written source model JSONs
 * under {@code src/main/resources} for the four blocks that bypass datagen entirely. Iterates the
 * real {@code RankineLists.ELEMENT_BLOCKS} / {@code RANKINE_STONES} (this test class runs through
 * NeoForge's unitTest bootstrap, same as {@link AlloyArmorTintSourceTest}, so {@code DeferredHolder.get()}
 * is safe here) so a future addition to either list is exercised automatically instead of silently
 * skipped by a stale hardcoded list.
 */
class RenderTypeModelTest {

    private static Path projectDir() {
        String projectDir = System.getProperty("rankine.projectDir");
        assertNotNull(projectDir, "system property rankine.projectDir is not set");
        return Paths.get(projectDir);
    }

    private static Path resource(String projectRelativePath) {
        Path path = projectDir().resolve(projectRelativePath);
        assertTrue(Files.isRegularFile(path), path + " is not a file");
        return path;
    }

    private static String renderTypeOfJson(Path path) throws IOException {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            JsonObject model = JsonParser.parseReader(reader).getAsJsonObject();
            JsonElement rt = model.get("render_type");
            return rt == null ? null : rt.getAsString();
        }
    }

    /** {@code base} is a model path with no extension, relative to models/, e.g. "block/cob". */
    private static String generatedRenderType(String base) throws IOException {
        return renderTypeOfJson(resource("src/generated/resources/assets/rankine/models/" + base + ".json"));
    }

    private static String handWrittenRenderType(String base) throws IOException {
        return renderTypeOfJson(resource("src/main/resources/assets/rankine/models/" + base + ".json"));
    }

    // =========================================================================================
    // (a) Element/storage blocks: only the twelve gaseous ones are translucent (finding #28/#47)
    // =========================================================================================

    private static final Set<Block> GASEOUS_ELEMENT_BLOCKS = Set.of(
            RankineBlocks.HYDROGEN_BLOCK.get(), RankineBlocks.HELIUM_BLOCK.get(),
            RankineBlocks.NITROGEN_BLOCK.get(), RankineBlocks.OXYGEN_BLOCK.get(),
            RankineBlocks.FLUORINE_BLOCK.get(), RankineBlocks.NEON_BLOCK.get(),
            RankineBlocks.CHLORINE_BLOCK.get(), RankineBlocks.ARGON_BLOCK.get(),
            RankineBlocks.KRYPTON_BLOCK.get(), RankineBlocks.XENON_BLOCK.get(),
            RankineBlocks.RADON_BLOCK.get(), RankineBlocks.OGANESSON_BLOCK.get());

    @Test
    @DisplayName("only the twelve gaseous element blocks are translucent; no element/storage block carries cutout")
    void elementBlocksAreSolidExceptTheTwelveGases() throws IOException {
        assertEquals(116, RankineLists.ELEMENT_BLOCKS.size(),
                "RankineLists.ELEMENT_BLOCKS count drifted from 116 -- recheck the 104/12 split "
                        + "against baseline ClientProxy.java (859f87773) before trusting this test");
        assertTrue(RankineLists.ELEMENT_BLOCKS.containsAll(GASEOUS_ELEMENT_BLOCKS),
                "the twelve gaseous element blocks are not all present in ELEMENT_BLOCKS");

        List<String> gasesNotTranslucent = new ArrayList<>();
        List<String> solidsWithARenderType = new ArrayList<>();
        int solidCount = 0;
        for (Block b : RankineLists.ELEMENT_BLOCKS) {
            String base = "block/" + RankineDataUtils.rl(b).getPath();
            String renderType = generatedRenderType(base);
            if (GASEOUS_ELEMENT_BLOCKS.contains(b)) {
                if (!"minecraft:translucent".equals(renderType)) {
                    gasesNotTranslucent.add(base + "=" + renderType);
                }
            } else {
                solidCount++;
                // Solid means NO render_type key at all (defaults to RenderType.solid()) --
                // "cutout" would be just as wrong as "translucent" here, so reject either.
                if (renderType != null) {
                    solidsWithARenderType.add(base + "=" + renderType);
                }
            }
        }
        assertEquals(104, solidCount, "expected 104 non-gaseous element blocks (116 - 12)");
        assertTrue(gasesNotTranslucent.isEmpty(),
                "gaseous element blocks that lost their translucent render type: " + gasesNotTranslucent);
        assertTrue(solidsWithARenderType.isEmpty(),
                solidsWithARenderType.size() + " solid element/storage block models still carry a "
                        + "non-default render_type (finding #28/#47): " + solidsWithARenderType);
    }

    // =========================================================================================
    // (b) Stone-brick slab/stairs/wall/pressure-plate stay solid (UNVERIFIED-FINDINGS.md item)
    // =========================================================================================

    @Test
    @DisplayName("stone-brick slabs, stairs, walls and pressure plates carry no render_type (1.18.2 left them solid)")
    void stoneBrickSubVariantsAreSolid() throws IOException {
        assertEquals(59, RankineLists.RANKINE_STONES.size(),
                "RankineLists.RANKINE_STONES count drifted from 59 -- recompute the expected file "
                        + "count (59 x 20 = 1180) before trusting this test");

        // Confirms the suffix convention assumed below actually matches how RankineStone
        // registers these blocks, before relying on it for every stone.
        RankineStone probe = RankineLists.RANKINE_STONES.get(0);
        String probeBase = RankineDataUtils.rl(probe.getBricks()).getPath();
        assertEquals(probeBase + "_slab", RankineDataUtils.rl(probe.getBricksSlab()).getPath());
        assertEquals("mossy_" + probeBase, RankineDataUtils.rl(probe.getMossyBricks()).getPath());

        String[] plainSuffixes = {
                "_slab", "_slab_top", "_stairs", "_stairs_inner", "_stairs_outer",
                "_wall_post", "_wall_side", "_wall_side_tall", "_wall_inventory",
                "_pressure_plate_up", "_pressure_plate_down"
        };
        String[] mossySuffixes = {
                "_slab", "_slab_top", "_stairs", "_stairs_inner", "_stairs_outer",
                "_wall_post", "_wall_side", "_wall_side_tall", "_wall_inventory"
                // RankineStone has no mossyBricksPressurePlate field -- it was never generated,
                // in either version.
        };

        List<String> stillCutout = new ArrayList<>();
        int checked = 0;
        for (RankineStone stone : RankineLists.RANKINE_STONES) {
            String plainBase = "block/" + RankineDataUtils.rl(stone.getBricks()).getPath();
            String mossyBase = "block/" + RankineDataUtils.rl(stone.getMossyBricks()).getPath();
            for (String suffix : plainSuffixes) {
                checked++;
                String rt = generatedRenderType(plainBase + suffix);
                if (rt != null) stillCutout.add(plainBase + suffix + "=" + rt);
            }
            for (String suffix : mossySuffixes) {
                checked++;
                String rt = generatedRenderType(mossyBase + suffix);
                if (rt != null) stillCutout.add(mossyBase + suffix + "=" + rt);
            }
        }
        assertEquals(1180, checked,
                "expected 59 stones x 20 sub-variant files (11 plain + 9 mossy, mossy has no "
                        + "pressure plate) = 1180 -- recompute this constant if the stone/suffix set changes");
        assertTrue(stillCutout.isEmpty(),
                stillCutout.size() + " stone-brick slab/stairs/wall/pressure-plate models are "
                        + "still forced to a non-solid render type: " + stillCutout);
    }

    @Test
    @DisplayName("the stone-bricks block itself (both fancy variants, plain and mossy) keeps its cutout render type")
    void stoneBricksBlockItselfStaysCutout() throws IOException {
        List<String> notCutout = new ArrayList<>();
        for (RankineStone stone : RankineLists.RANKINE_STONES) {
            for (Block bricksBlock : List.of(stone.getBricks(), stone.getMossyBricks())) {
                String base = "block/" + RankineDataUtils.rl(bricksBlock).getPath();
                for (int variant = 1; variant <= 2; variant++) {
                    String rt = generatedRenderType(base + variant);
                    if (!"minecraft:cutout".equals(rt)) {
                        notCutout.add(base + variant + "=" + rt);
                    }
                }
            }
        }
        assertTrue(notCutout.isEmpty(),
                "the stone bricks block's own fancy-variant models must stay cutout, unchanged by "
                        + "this fix: " + notCutout);
    }

    // =========================================================================================
    // (c) Hand-written models that bypass datagen entirely (finding #29)
    // =========================================================================================

    @Test
    @DisplayName("hand-written models that bypass datagen (Cob, Ornament, Tree Tap, Tap Line) carry their 1.18.2 render_type")
    void handWrittenModelsCarryExpectedRenderType() throws IOException {
        String cobBase = "block/" + RankineDataUtils.rl(RankineBlocks.COB.get()).getPath();
        String ornamentBase = "block/" + RankineDataUtils.rl(RankineBlocks.ORNAMENT.get()).getPath();
        String treeTapBase = "block/" + RankineDataUtils.rl(RankineBlocks.TREE_TAP.get()).getPath();
        String tapLineBase = "block/" + RankineDataUtils.rl(RankineBlocks.TAP_LINE.get()).getPath();

        Map<String, String> expected = new LinkedHashMap<>();
        expected.put(cobBase, "minecraft:cutout_mipped");
        expected.put(ornamentBase + "0", "minecraft:cutout");
        expected.put(ornamentBase + "1", "minecraft:cutout");
        expected.put(ornamentBase + "2", "minecraft:cutout");
        expected.put(treeTapBase, "minecraft:cutout");
        expected.put(treeTapBase + "_connected", "minecraft:cutout");
        expected.put(tapLineBase, "minecraft:cutout");
        expected.put(tapLineBase + "_side", "minecraft:cutout");

        List<String> wrong = new ArrayList<>();
        for (Map.Entry<String, String> e : expected.entrySet()) {
            String actual = handWrittenRenderType(e.getKey());
            if (!e.getValue().equals(actual)) {
                wrong.add(e.getKey() + ": expected " + e.getValue() + ", got " + actual);
            }
        }
        assertTrue(wrong.isEmpty(), "hand-written models with the wrong (or missing) render_type: " + wrong);
    }
}
