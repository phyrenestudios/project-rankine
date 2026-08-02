# Port orchestration state — read this after any context loss

Branch `1.21.1-neoforge` (based on origin/1.18.X stable v1.3.8, NOT the unfinished 1.19.X default branch).
Target: MC 1.21.1 / NeoForge 21.1.248 / Java 21 / ModDevGradle 2.0.143.

## Environment gotchas (already fixed in-repo — do not regress)
1. The Gradle daemon MUST run on a JDK. NeoForm's decompile/recompile calls `javax.tools.JavaCompiler`
   in-process and dies with "compiler is null" on a JRE. `org.gradle.java.home` in gradle.properties points at
   `~/.gradle/jdks/eclipse_adoptium-21-amd64-windows.2`. Change it if that path disappears.
2. `-Xmaxerrs 3000` is set in build.gradle — javac's default cap of 100 hid 98% of the port's errors.
3. Gradle incremental compilation produces STALE PHANTOM ERRORS in this project (symbols reported missing that are
   plainly declared). When an error looks impossible, re-run `./gradlew compileJava --rerun-tasks` before chasing it.
4. `DeferredRegister.Blocks/Items` have both Function and Supplier register overloads → a bare `X::new` is ambiguous;
   always write `() -> new X()`.

## Reference material
- Conventions: `PORTING_GUIDE.md` (repo root).
- Decompiled MC 1.21.1 + NeoForge sources: `C:\Users\karl\Documents\Projects\mc-1.21.1-sources`. Agents MUST grep
  these for signatures instead of guessing.
- Per-area agent reports with cross-package contracts: `.porting/reports/*.md`. Read these before touching a package.

## Status
DONE (committed): build toolchain; mechanical rename; handwritten datapack JSON migration; init registries (ALL, clean);
blocks (top-level + all subpackages); items + data components; 18 recipe serializers; 44 enchantments (data-driven);
worldgen (features + 59 biome-modifier entries replacing BiomeLoadingEvent); fluids; client rendering; events/network/
capabilities/entities; util package; JEI (19.21 API); creative tabs (incl. restored dynamic entries).

IN FLIGHT: datagen package (data/**), and a finisher agent on ~32 straggler files across blocks/items/entities/events.

NOT STARTED: the verification gates.

## Verification gates — `bash .porting/verify.sh [build|test|datagen|gametest|server|all]`
Logs land in `.porting/logs/`. Gate order and meaning:
1. build   — `gradlew build -x test`
2. test    — JUnit: ElementEquationTest (alloy stat math), WeightedCollectionTest (drop weighting)
3. datagen — `runData`; exercises every registry/codec/bootstrap headlessly
4. gametest— `runGameTestServer`; boots a real headless server and runs
             `gametest/RankineRegistryTests` (registry counts, recipes actually loading per type, enchantments
             parsing, loot tables existing per block, fluids having FluidTypes, data components registered)
5. server  — dedicated server boots to "Done", then checks for crash reports

## Remaining work after the gates go green
- Regenerate `src/generated/resources` via runData and sanity-check the diff (35k JSONs).
- Update CHANGELOG.md; final report to Karl. He then does a quick manual dev-client pass.

## Known behavior deltas to mention to Karl (accepted, documented in-code)
- Retrogen chunk flag moved from a Forge capability to a NeoForge attachment and is NOT migrated → pre-existing
  chunks run retrogen replacement once more.
- PlayerTickHandler ran twice per tick in 1.18.2 (missing phase check); now once → Invigorating Totem repairs at
  half the old rate.
- Advancement custom item predicates are now ItemSubPredicates; their JSON shape changed (vanilla-driven).
- Enchantment applicability widened from recipe-conditional to tag-based for antiquated / shape_memory.
- FoodProperties#meat() removed → flag dropped for TOFU, COOKED_TOFU, TUNA, COOKED_TUNA.
- RankineLeavesBlock is now waterloggable (vanilla LeavesBlock forces the property).
- ElectromagnetBlock's "is metal" gate approximated via SoundType.METAL (misses netherite/anvil); clean fix is a
  `rankine:magnet_attractable` block tag.
- Off-hand right-clicks no longer reach CobbleBlock/MetalPoleBlock fallback paths (vanilla use() split).
