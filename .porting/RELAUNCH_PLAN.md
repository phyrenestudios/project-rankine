# Port orchestration state — read this after any context loss

## Situation
- Porting project-rankine 1.18.2 Forge → 1.21.1 NeoForge on branch `1.21.1-neoforge` (based on origin/1.18.X stable, NOT the WIP 1.19.X branch).
- Toolchain DONE: ModDevGradle 2.0.143, NeoForge 21.1.248, Gradle 8.12, Java 21 toolchain. `gradlew tasks` works; NeoForm pipeline produced MC sources.
- Decompiled MC+NeoForge 1.21.1 reference sources: `C:\Users\karl\Documents\Projects\mc-1.21.1-sources` (49MB, net/minecraft + net/neoforged).
- Conventions: `PORTING_GUIDE.md` in repo root. All agents must read it first.
- Done so far: build files, neoforge.mods.toml, pack.mcmeta(34), mixins removed, bulk package rename (364 files), .tab()→RankineCreativeTabs (generated, 6 tabs, 1915 items), new ProjectRankine.java, new RankineDataComponents.java, RankineSetup/IProxy/ServerProxy deleted. Committed.
- 8 port agents were launched 2026-08-01 ~1:15pm but ALL died instantly: session usage limit, resets 5:20pm ET. Agent prompts saved as `.porting/agent-*.md` — relaunch them (general-purpose agents, prompt = file content verbatim).

## Relaunch order (stagger to survive limits; 4 first, rest when those finish)
Wave 1a: agent-A1-init-giant.md (sonnet), agent-B2-blocks-machines.md (DEFAULT model), agent-C-items.md (DEFAULT), agent-D-recipes.md (DEFAULT)
Wave 1b: agent-E-enchantments.md (DEFAULT), agent-F-worldgen.md (DEFAULT), agent-G-events.md (DEFAULT), agent-A2-init-misc.md (DEFAULT), agent-B1-blocks-toplevel.md (sonnet), agent-H-client.md (sonnet)
Wave 2 (after wave 1 + compile loop): JEI integration agent, datagen agent (prompts not yet written — see TODO below).

## After all agents finish
1. Compile loop: `./gradlew compileJava` → group errors by package → fix directly or dispatch small fix agents. Expect cross-package seams (ctor signature mismatches, PORT-TODO wiring).
2. Wire main-class registrations agents may have added (grep `PORT-TODO(main)`): RankineAttachments, RankineCriteriaTriggers, RankineBiomeModifierTypes, RankineWorldgenTypes, RankineArmorMaterials.
3. Wave 2 agents:
   - Datagen: port data/** providers; add DatapackBuiltinEntriesProvider wiring RegistrySetBuilder with: Registries.ENCHANTMENT → RankineEnchantmentBootstrap::bootstrap, Registries.CONFIGURED_FEATURE/PLACED_FEATURE → RankineConfiguredFeatures/RankinePlacedFeatures::bootstrap, Registries.DAMAGE_TYPE → RankineDamageSources::bootstrap, NeoForgeRegistries.Keys.BIOME_MODIFIERS → RankineBiomeModifiers::bootstrap. Plus enchantable/* + exclusive_set/* tags from agent E report.
   - JEI: port client/integration/jei/** against JEI 19.21.0.247 API (dep already in build.gradle as compileOnly).
4. `./gradlew runData` → regenerate src/generated/resources (35k JSONs). Delete stale generated files first (git rm -r src/generated/resources, keep .cache? no — full regen; datagen writes everything). Check handwritten src/main/resources/data JSONs: worldgen JSONs and recipes may need migration (script if needed: result.item→result.id etc. — only for VANILLA recipe types; custom serializers keep 1.18 format).
5. Tests (task 10): src/test/java unit tests (MDG unitTest enabled in build.gradle, testedMod = rankine): codec round-trips for all 18 recipe serializers, alloy math, enchantment key sanity. GameTests: new package gametest/ with @GameTestHolder("rankine") — machine smoke tests (place alloy furnace, insert, tick, assert), registry presence asserts. `./gradlew runGameTestServer`.
6. Gates (task 11): `./gradlew build` → `runData` (idempotent, no crash) → `runGameTestServer` (exit 0) → `runServer` boot to "Done" + `/stop` via stdin (use --nogui; pipe "stop" after Done appears in log; eula.txt: create run/server/eula.txt eula=true first).
7. Update CHANGELOG.md + commit. Final report to user.

## Verification gate commands (working dir = repo root)
- Build: ./gradlew build -x test / full: ./gradlew build
- Datagen: ./gradlew runData
- GameTest: ./gradlew runGameTestServer
- Server smoke: ./gradlew runServer (needs run/eula.txt eula=true; sends in --nogui; auto-stop via GameTest-style or timeout+Done grep)

## Known risk areas to double-check in review
- WorldgenUtils biome iteration at setup time (must move to server-start; biomes are datapack).
- Alloy tool dynamic stats → Tool/ATTRIBUTE_MODIFIERS components set at composition-apply time.
- RecipeWrapper slot-index parity with old Container indices per machine.
- Boat/sign/villager APIs.
- MissingMappingsEvent for old-world remaps (also consider neoforge alias for `forge:` tags? No — data tags in JSONs need c: namespace updates in datagen).
