package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;

import java.util.*;

public class WorldgenUtils {

    public static List<ResourceLocation> GEN_BIOMES = new ArrayList<>();
    public static List<Block> O1 = new ArrayList<>();
    public static List<Block> A1 = new ArrayList<>();
    public static List<Block> B1 = new ArrayList<>();
    public static List<Block> O2 = new ArrayList<>();
    public static List<Block> A2 = new ArrayList<>();
    public static List<Block> B2 = new ArrayList<>();
    public static List<List<String>> INTRUSION_LISTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_BLOCKS = new ArrayList<>();
    public static List<List<Float>> INTRUSION_WEIGHTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_ORES = new ArrayList<>();
    public static List<List<Float>> INTRUSION_ORE_CHANCES = new ArrayList<>();
    public static List<WeightedCollection<BlockState>> INTRUSION_COLLECTIONS = new ArrayList<>();
    public static List<List<String>> LAYER_LISTS = new ArrayList<>();
    public static List<List<String>> VEGETATION_LISTS = new ArrayList<>();
    public static List<WeightedCollection<BlockState>> VEGETATION_COLLECTIONS = new ArrayList<>();
    public static List<Block> DRIPSTONES = new ArrayList<>();
    public static List<Block> GRAVELS = new ArrayList<>();
    public static List<Block> SANDS = new ArrayList<>();
    public static List<Block> SANDSTONES = new ArrayList<>();

    public static List<Block> ORE_STONES = new ArrayList<>();
    public static List<String> ORE_TEXTURES = new ArrayList<>();

    /**
     * PORT-NOTE: Biome.BiomeCategory was removed in 1.19. Category names used in the config
     * (biomeSettings / oreSettings entries) are now mapped onto biome tags.
     * <p>
     * The keys must be exactly the strings 1.18.2's {@code Biome.BiomeCategory#getName()} produced:
     * the baseline wrote those names into the default config ({@code Config.java}, e.g.
     * {@code Biome.BiomeCategory.THEEND.getName()}) and read them back with
     * {@code Biome.BiomeCategory.byName(...)} from both {@code WorldgenUtils#initConfigs} and
     * {@code FeatureGeneration#addFeaturesToBiomes}, so the round-trip defines the spelling.
     * THEEND's name is {@code "the_end"}, not {@code "theend"}.
     * <p>
     * {@code "icy"} deliberately does NOT use {@code c:is_icy}: NeoForge ships that tag as
     * {@code [ice_spikes, frozen_peaks]}, while 1.18.2's {@code BiomeCategory.ICY} was
     * {@code [snowy_plains, ice_spikes]} (frozen_peaks was MOUNTAIN, and the "mountain" config row
     * already claims it). {@link RankineTags.Biomes#IS_ICY} reproduces the 1.18.2 membership.
     * <p>
     * {@code "beach"} is the same story: {@code minecraft:is_beach} is {@code [beach, snowy_beach]}
     * and {@code c:is_beach} is an alias of it, but 1.18.2's {@code BiomeCategory.BEACH} also
     * contained {@code stony_shore}. {@link RankineTags.Biomes#IS_BEACH} reproduces the 1.18.2
     * membership.
     * <p>
     * {@code "none"} has no tag at all — see {@link #matchesCategory}.
     */
    public static final Map<String, TagKey<Biome>> CATEGORY_TAGS = new HashMap<>();
    static {
        CATEGORY_TAGS.put("taiga", BiomeTags.IS_TAIGA);
        CATEGORY_TAGS.put("extreme_hills", BiomeTags.IS_HILL);
        CATEGORY_TAGS.put("jungle", BiomeTags.IS_JUNGLE);
        CATEGORY_TAGS.put("mesa", BiomeTags.IS_BADLANDS);
        CATEGORY_TAGS.put("plains", Tags.Biomes.IS_PLAINS);
        CATEGORY_TAGS.put("savanna", BiomeTags.IS_SAVANNA);
        CATEGORY_TAGS.put("icy", RankineTags.Biomes.IS_ICY);
        CATEGORY_TAGS.put("the_end", BiomeTags.IS_END);
        CATEGORY_TAGS.put("beach", RankineTags.Biomes.IS_BEACH);
        CATEGORY_TAGS.put("forest", BiomeTags.IS_FOREST);
        CATEGORY_TAGS.put("ocean", BiomeTags.IS_OCEAN);
        CATEGORY_TAGS.put("desert", Tags.Biomes.IS_DESERT);
        CATEGORY_TAGS.put("river", BiomeTags.IS_RIVER);
        CATEGORY_TAGS.put("swamp", Tags.Biomes.IS_SWAMP);
        CATEGORY_TAGS.put("mushroom", Tags.Biomes.IS_MUSHROOM);
        CATEGORY_TAGS.put("nether", BiomeTags.IS_NETHER);
        CATEGORY_TAGS.put("underground", Tags.Biomes.IS_UNDERGROUND);
        CATEGORY_TAGS.put("mountain", BiomeTags.IS_MOUNTAIN);
    }

    /** 1.18.2 {@code Biome.BiomeCategory.NONE} — biomes that declared no category at all. */
    public static final String CATEGORY_NONE = "none";

    /**
     * F36 (decision 12, Karl, batch 4): {@code "all"} — every biome, unconditionally. NOT a
     * 1.18.2 {@code BiomeCategory} name (there was no such enum constant), so
     * {@code BiomeCategory.byName("all")} returned null and any row that selected it, like the
     * shipped {@code rankine:kimberlitic_tuff} ore row (Config.java:1246,
     * {@code List.of("all")}), matched no biome in 1.18.2 — dead on arrival, never a deliberate
     * "everywhere" selector. Karl called this a bug rather than parity worth preserving and asked
     * for it to actually spawn. Deliberately handled the same way as {@link #CATEGORY_NONE}: a
     * predicate special-cased in {@link #isCategory} / {@link #matchesCategory} rather than a
     * {@link #CATEGORY_TAGS} entry, so it is NOT one of the "real category tags" the {@code "none"}
     * computation loops over — a biome can match both {@code "all"} and {@code "none"} at once,
     * which is correct: {@code "none"} still means "declared no real category".
     */
    public static final String CATEGORY_ALL = "all";

    /**
     * True if the string names a biome category the config can select on, i.e. one that 1.18.2's
     * {@code BiomeCategory.byName(...)} resolved to a non-null enum constant, OR {@link #CATEGORY_ALL}
     * (batch 4 F36 — a deliberate non-parity addition, see its javadoc). Anything else (a typo)
     * selected no biomes in 1.18.2 either, and still selects none here.
     */
    public static boolean isCategory(String category) {
        return CATEGORY_NONE.equals(category) || CATEGORY_ALL.equals(category) || CATEGORY_TAGS.containsKey(category);
    }

    /**
     * The one biome-category test. Shared by {@link #ensureBiomesResolved} (biomeSettings rows) and
     * {@code RankineOreBiomeModifier#matches} (oreSettings rows) so the two cannot drift.
     * <p>
     * PORT-NOTE: no tag can express "declared no category", so {@code "none"} is a predicate rather
     * than a tag: it matches a biome that is in none of the {@link #CATEGORY_TAGS} tags. In vanilla
     * 1.21.1 that is {@code minecraft:the_void} and nothing else — 1.18.2's only NONE biome.
     * ({@code minecraft:stony_shore} used to fall in here too, because the vanilla beach tag omits
     * it; {@link RankineTags.Biomes#IS_BEACH} put it back where 1.18.2 had it.)
     * <p>
     * Semantic delta: a modded biome that declared no BiomeCategory in 1.18.2 but that modern tag
     * convention places in, say, {@code c:is_forest} now matches "forest" instead of "none".
     * Arguably more correct, but it is a delta from the baseline.
     * <p>
     * {@link #CATEGORY_ALL} (batch 4 F36) matches unconditionally, checked before either tag test.
     */
    public static boolean matchesCategory(Holder<Biome> biome, String category) {
        if (CATEGORY_ALL.equals(category)) {
            return true;
        }
        if (CATEGORY_NONE.equals(category)) {
            for (TagKey<Biome> tag : CATEGORY_TAGS.values()) {
                if (biome.is(tag)) return false;
            }
            return true;
        }
        TagKey<Biome> tag = CATEGORY_TAGS.get(category);
        return tag != null && biome.is(tag);
    }

    public static List<String> ORES = Arrays.asList("minecraft:stone", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:deepslate|minecraft:deepslate", "minecraft:sandstone|minecraft:sandstone_bottom", "minecraft:red_sandstone|minecraft:red_sandstone_bottom", "minecraft:netherrack", "minecraft:blackstone", "minecraft:basalt|minecraft:basalt_top", "minecraft:end_stone", "minecraft:obsidian", "rankine:pegmatite", "rankine:gray_granite", "rankine:rhyolite", "rankine:comendite", "rankine:granodiorite", "rankine:red_porphyry", "rankine:purple_porphyry", "rankine:hornblende_andesite", "rankine:black_dacite", "rankine:red_dacite", "rankine:tholeiitic_basalt", "rankine:diabase","rankine:gabbro", "rankine:anorthosite", "rankine:dunite", "rankine:harzburgite", "rankine:lherzolite", "rankine:wehrlite", "rankine:troctolite", "rankine:kimberlite", "rankine:komatiite","rankine:shonkinite", "rankine:norite", "rankine:pyroxenite", "rankine:rose_marble", "rankine:white_marble", "rankine:gray_marble", "rankine:black_marble", "rankine:gneiss", "rankine:mica_schist", "rankine:phyllite", "rankine:slate", "rankine:quartzite","rankine:mariposite","rankine:eclogite","rankine:limestone", "rankine:dolostone", "rankine:chalk", "rankine:soapstone", "rankine:shale", "rankine:siltstone", "rankine:itacolumite", "rankine:arkose", "rankine:mudstone","rankine:serpentinite","rankine:marlstone", "rankine:graywacke", "rankine:blueschist", "rankine:greenschist", "rankine:whiteschist","rankine:sommanite","rankine:post_perovskite","rankine:bridgmanham","rankine:ringwoodine","rankine:wadsleyone","rankine:honeystone", "rankine:meteorite", "rankine:frozen_meteorite", "rankine:enstatite_chondrite", "rankine:soul_sandstone|rankine:soul_sandstone_bottom", "rankine:white_sandstone|rankine:white_sandstone_bottom", "rankine:black_sandstone|rankine:black_sandstone_bottom", "rankine:desert_sandstone|rankine:desert_sandstone_bottom", "rankine:episyenite", "rankine:nepheline_syenite", "rankine:phonolite");

    public static void initOreTextures() {

        for (String ORE : ORES) {
            String[] ores = ORE.split("\\|");
            if (ores.length > 1) {
                ORE_TEXTURES.add(ores[1]);
            } else {
                ORE_TEXTURES.add(ores[0]);
            }

        }

    }

    /**
     * Raw config rows whose biome selectors have not been resolved against the biome registry yet.
     * PORT-NOTE: biomes are a datapack registry in 1.21 and cannot be enumerated during common setup,
     * so resolution is deferred to {@link #ensureBiomesResolved(RegistryAccess)} (first worldgen access
     * or server start).
     */
    private static final List<List<Object>> PENDING_BIOME_SETTINGS = new ArrayList<>();
    private static volatile boolean biomesResolved = false;

    public static void initConfigs() {

        // F32: ORE_STONES is static and was never cleared before this loop, so a second call (e.g.
        // a future config-reload hook; there is exactly one call site today, FMLCommonSetupEvent,
        // which fires once per game launch) would append ORES.size() duplicate entries on top of
        // the first call's. Harmless to indices that only ever read 0..ORES.size()-1, but it defeats
        // the point of re-init and leaks memory every reload. Same bug family as
        // ensureBiomesResolved's latch below; fixed the same way.
        ORE_STONES.clear();
        for (String ORE : ORES) {
            ORE_STONES.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(ORE.split("\\|")[0])));
        }

        PENDING_BIOME_SETTINGS.clear();
        for (List<Object> L : Config.BIOME_GEN.BIOME_SETTINGS.get()) {
            PENDING_BIOME_SETTINGS.add(L);
        }
    }

    /**
     * Resolves the config biome settings against the actual (datapack-aware) biome registry.
     * Idempotent and cheap after first call — it no-ops until {@link #resetBiomeResolution()} is
     * called again. Call with {@code level.registryAccess()} from worldgen, or from server start.
     * {@code RankineEventHandler#serverAboutToStart} calls {@link #resetBiomeResolution()} then
     * this, every server start, so blocks (SoilBlock, GrassySoilBlock, retrogen) see freshly
     * resolved lists before any feature runs.
     * <p>
     * F32 (batch 4): {@code biomesResolved} used to latch permanently — correct for 1.18.2, where
     * the biome registry was static and world-independent, but 1.21's biome registry is a per-world
     * datapack registry, so a second world loaded in the same client session (possibly with
     * different datapack biomes) used to resolve against the FIRST world's stale lists.
     */
    public static void ensureBiomesResolved(RegistryAccess registryAccess) {
        if (biomesResolved) return;
        synchronized (WorldgenUtils.class) {
            if (biomesResolved) return;
            Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);

            for (List<Object> L : PENDING_BIOME_SETTINGS) {
                String biomeToAdd = (String) L.get(0);
                List<String> biomeName = Arrays.asList(biomeToAdd.split(":"));
                Block gravel = ResourceLocation.tryParse((String) L.get(5)) == null ? Blocks.AIR : BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String) L.get(5)));
                Block sand = ResourceLocation.tryParse((String) L.get(6)) == null ? Blocks.AIR : BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String) L.get(6)));
                Block sandstone = ResourceLocation.tryParse((String) L.get(7)) == null ? Blocks.AIR : BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String) L.get(7)));
                Block dripstone = ResourceLocation.tryParse((String) L.get(8)) == null ? Blocks.AIR : BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String) L.get(8)));
                if (biomeName.size() > 1) {
                    populateLists(ResourceLocation.tryParse(biomeToAdd),(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3),(List<String>) L.get(4), gravel, sand, sandstone, dripstone);
                } else {
                    if (!isCategory(biomeToAdd)) continue;
                    for (Holder.Reference<Biome> holder : biomeRegistry.holders().toList()) {
                        if (matchesCategory(holder, biomeToAdd)) {
                            populateLists(holder.key().location(),(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3),(List<String>) L.get(4), gravel, sand, sandstone, dripstone);
                        }
                    }
                }
            }

            for (List<String> I : INTRUSION_LISTS) {
                int ind = 0;
                WeightedCollection<BlockState> col = new WeightedCollection<>();
                List<Block> tempIB = new ArrayList<>();
                List<Float> tempIW = new ArrayList<>();
                List<Block> tempIO = new ArrayList<>();
                List<Float> tempIC = new ArrayList<>();
                for (String entry : I) {
                    tempIB.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(entry.split("\\|")[0])));
                    tempIW.add(Float.parseFloat(entry.split("\\|")[1]));
                    tempIO.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(entry.split("\\|")[2])));
                    tempIC.add(Float.parseFloat(entry.split("\\|")[3]));
                    col.add(tempIW.get(ind),tempIB.get(ind).defaultBlockState());
                    ind += 1;
                }
                INTRUSION_BLOCKS.add(tempIB);
                INTRUSION_WEIGHTS.add(tempIW);
                INTRUSION_ORES.add(tempIO);
                INTRUSION_ORE_CHANCES.add(tempIC);
                INTRUSION_COLLECTIONS.add(col);
            }

            for (List<String> V : VEGETATION_LISTS) {
                int ind = 0;
                WeightedCollection<BlockState> col = new WeightedCollection<>();
                List<Block> tempVB = new ArrayList<>();
                List<Float> tempVW = new ArrayList<>();
                for (String entry : V) {
                    tempVB.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(entry.split("\\|")[0])));
                    tempVW.add(Float.parseFloat(entry.split("\\|")[1]));
                    col.add(tempVW.get(ind),tempVB.get(ind).defaultBlockState());
                    ind += 1;
                }
                VEGETATION_COLLECTIONS.add(col);
            }

            biomesResolved = true;
        }
    }

    /**
     * F32 (batch 4): clears every list {@link #ensureBiomesResolved} populates, and the latch
     * itself, so the next call re-resolves from scratch instead of appending to stale data.
     * <p>
     * ALL of them must be cleared together, in the same synchronized section as the latch, or the
     * lists desync: {@link #GEN_BIOMES}/{@link #O1}.../{@link #B2}/{@link #INTRUSION_LISTS}/
     * {@link #LAYER_LISTS}/{@link #VEGETATION_LISTS}/{@link #GRAVELS}/{@link #SANDS}/
     * {@link #SANDSTONES}/{@link #DRIPSTONES} share one "biome index" (one {@link #populateLists}
     * call appends exactly one entry to each), and {@link #INTRUSION_BLOCKS}/
     * {@link #INTRUSION_WEIGHTS}/{@link #INTRUSION_ORES}/{@link #INTRUSION_ORE_CHANCES}/
     * {@link #INTRUSION_COLLECTIONS}/{@link #VEGETATION_COLLECTIONS} are themselves derived by
     * re-iterating {@link #INTRUSION_LISTS}/{@link #VEGETATION_LISTS} inside
     * {@link #ensureBiomesResolved}. Clearing only the latch (or only {@code INTRUSION_LISTS}) and
     * re-resolving would not just duplicate entries — the second run's derive-loop iterates the
     * ALREADY-doubled {@code INTRUSION_LISTS} while appending onto the NOT-yet-cleared derived
     * lists, so the derived lists end up 3x the correct size while {@code INTRUSION_LISTS} is only
     * 2x, an alignment break strictly worse than a simple duplicate.
     */
    public static void resetBiomeResolution() {
        synchronized (WorldgenUtils.class) {
            biomesResolved = false;
            GEN_BIOMES.clear();
            O1.clear();
            A1.clear();
            B1.clear();
            O2.clear();
            A2.clear();
            B2.clear();
            INTRUSION_LISTS.clear();
            INTRUSION_BLOCKS.clear();
            INTRUSION_WEIGHTS.clear();
            INTRUSION_ORES.clear();
            INTRUSION_ORE_CHANCES.clear();
            INTRUSION_COLLECTIONS.clear();
            LAYER_LISTS.clear();
            VEGETATION_LISTS.clear();
            VEGETATION_COLLECTIONS.clear();
            GRAVELS.clear();
            SANDS.clear();
            SANDSTONES.clear();
            DRIPSTONES.clear();
        }
    }

    private static void populateLists(ResourceLocation BIOME, List<String> SOILS, List<String> INTRUSIONS, List<String> STONES, List<String> VEGETATION, Block GRAVEL, Block SAND, Block SANDSTONE, Block DRIPSTONE) {
        GEN_BIOMES.add(BIOME);
        if (SOILS.isEmpty()) {
            O1.add(Blocks.AIR);
            A1.add(Blocks.AIR);
            B1.add(Blocks.AIR);
            O2.add(Blocks.AIR);
            A2.add(Blocks.AIR);
            B2.add(Blocks.AIR);
        } else {
            O1.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(0))));
            A1.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(1))));
            B1.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(2))));
            O2.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(3))));
            A2.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(4))));
            B2.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(SOILS.get(5))));
        }
        INTRUSION_LISTS.add(INTRUSIONS);
        LAYER_LISTS.add(STONES);
        VEGETATION_LISTS.add(VEGETATION);
        GRAVELS.add(GRAVEL);
        SANDS.add(SAND);
        SANDSTONES.add(SANDSTONE);
        DRIPSTONES.add(DRIPSTONE);

    }

    /** Location of a biome holder, or null for a direct (unregistered) holder. */
    public static ResourceLocation biomeLocation(Holder<Biome> biome) {
        return biome.unwrapKey().map(key -> key.location()).orElse(null);
    }

    /** Replacement for the old BiomeCategory-based overworld check: not nether and not end. */
    public static boolean isOverworld(Holder<Biome> biome) {
        return !biome.is(BiomeTags.IS_NETHER) && !biome.is(BiomeTags.IS_END);
    }

    /**
     * Replacement for the removed Forge {@code canSustainPlant(..., IPlantable)} overload:
     * asks the soil first (NeoForge TriState), falling back to the vanilla sapling rule (dirt or farmland).
     */
    public static boolean canSustainSapling(LevelAccessor level, BlockPos soilPos, Block sapling) {
        BlockState soil = level.getBlockState(soilPos);
        TriState result = soil.canSustainPlant(level, soilPos, Direction.UP, sapling.defaultBlockState());
        if (result.isTrue()) return true;
        if (result.isFalse()) return false;
        return soil.is(BlockTags.DIRT) || soil.is(Blocks.FARMLAND);
    }

    public static boolean isWet(ChunkAccess chunk, BlockPos pos) {
        for(BlockPos POS : BlockPos.betweenClosed(pos.offset(-2,0,-2),pos.offset(2,1,2))) {
            if (Math.abs(POS.getX() - pos.getX()) <= 2 && Math.abs(POS.getZ() - pos.getZ()) <= 2 && chunk.getFluidState(POS).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    public static Direction randomHorizontalDirection(RandomSource rand) {
        List<Direction> dirs = Arrays.asList(Direction.NORTH,Direction.EAST,Direction.WEST,Direction.SOUTH);
        return dirs.get(rand.nextInt(dirs.size()));
    }

    public static BlockPos eightBlockDirection(BlockPos pos, int type, int mult) {
        switch (type % 8) {
            case 0:
                return pos.offset(-1*mult,0,-1*mult);
            case 1:
                return pos.offset(-1*mult,0,0);
            case 2:
                return pos.offset(-1*mult,0, mult);
            case 7:
                return pos.offset(0,0,-1*mult);
            case 3:
                return pos.offset(0,0, mult);
            case 6:
                return pos.offset(mult,0,-1*mult);
            case 5:
                return pos.offset(mult,0,0);
            case 4:
                return pos.offset(mult,0, mult);
            default:
                return pos;
        }
    }

    public static boolean inInfiniteSource(Level levelIn, BlockPos posIn) {
        int i = 0;
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().isVertical()) continue;
            FluidState fluid = levelIn.getFluidState(posIn.relative(dir));
            if (levelIn.getFluidState(posIn.relative(dir)).is(Fluids.WATER)) {
                i++;
                if (i>1) return true;
            }
        }
        return false;
    }

    public static int waterTableHeight(Level levelIn, BlockPos posIn) {
        if (!Config.GENERAL.DISABLE_WATER.get()) return levelIn.getMaxBuildHeight();
        int surface = levelIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG,posIn.getX(),posIn.getZ());
        return levelIn.getSeaLevel() + Math.max(0, Math.round((surface-levelIn.getSeaLevel()) * (1 - levelIn.getBiome(posIn).value().getModifiedClimateSettings().downfall())));
    }

    /**
     * PORT-NOTE: the {@code center} branch used {@code b.offset(0.5,0.5,0.5)};
     * {@code BlockPos.offset(double,double,double)} no longer exists in 1.21, and the old call
     * truncated back to the same integer position, so both branches are now identical.
     * The {@code center} parameter is kept for call-site compatibility.
     */
    public static boolean inArea(BlockPos b, double radius, boolean center, BlockPos... targets) {
        for (BlockPos target : targets) {
            if (center) {
                if (b.distSqr(target) < Math.pow(radius, 2) + 0.5) {
                    return true;
                }
            } else {
                if (b.distSqr(target) < Math.pow(radius, 2) + 0.5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static BlockPos averagePos(BlockPos... targets) {
        int x = 0;
        int y = 0;
        int z = 0;
        for (BlockPos target : targets) {
            x += target.getX();
            y += target.getY();
            z += target.getZ();
        }
        return new BlockPos(x/targets.length,y/targets.length,z/targets.length);
    }


    public static Block getCeillingBlock(Level worldIn, BlockPos pos, Direction dir, int height) {
        int i = 1;
        while (i<=height) {
            if (!worldIn.isEmptyBlock(pos.relative(dir,i)) && !(worldIn.getBlockState(pos.above(i)).canBeReplaced(Fluids.WATER))) {
                return worldIn.getBlockState(pos.above(i)).getBlock();
            }
            ++i;
        }
        return Blocks.AIR;
    }



    public static boolean inRadiusCenter(BlockPos center, BlockPos checkPos, double radius) {
        return center.distSqr(checkPos) < radius*radius;
    }

    public static void checkLog(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, Direction.Axis axis) {
        if (isAirOrLeaves(reader, pos)) {
            placeLogAt(reader, pos, rand, config, axis);
        }
    }

    public static void placeLogAt(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, Direction.Axis axis) {
        setLogState(reader, pos, config.trunkProvider.getState(rand, pos).setValue(BlockStateProperties.AXIS, axis));
    }

    public static void setLogState(WorldGenLevel reader, BlockPos pos, BlockState state) {
        reader.setBlock(pos, state, 18);
    }

    public static void placeLeafAt(WorldGenLevel world, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        if (isAirOrLeaves(world, pos)) {
            setLogState(world, pos, config.foliageProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
        }
    }

    public static boolean isAirOrLeaves(WorldGenLevel levelIn, BlockPos pos) {
        return levelIn.isStateAtPosition(pos, (state) -> state.isAir() || state.is(BlockTags.LEAVES));
    }

    public static boolean isAir(WorldGenLevel reader, BlockPos pos) {
        return reader.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
    }

    public static boolean isGasOrAir(Level reader, BlockPos pos) {
        return RankineLists.GAS_BLOCKS.contains(reader.getBlockState(pos).getBlock()) || reader.getBlockState(pos).isAir();
    }

    public static boolean biomeTagCheck(Level level, Biome biome, TagKey<Biome> tag)
    {
        var reg = level.registryAccess().registryOrThrow(Registries.BIOME);
        return reg.getResourceKey(biome)
                .flatMap(reg::getHolder)
                .map(holder -> holder.is(tag)).orElse(false);
    }
}
