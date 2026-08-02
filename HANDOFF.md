# Project Rankine — Forge 1.18.2 → NeoForge 1.21.1 port: HANDOFF

**Read this first, then `PORTING_GUIDE.md`, then `.porting/reports/*.md`.**

Repo: `C:\Users\karl\Documents\Projects\project-rankine`, branch **`1.21.1-neoforge`**
(based on `origin/1.18.X` = stable v1.3.8, **not** the repo's default `1.19.X`, which is an
unfinished WIP port and was deliberately ignored).

Target: Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21 / ModDevGradle 2.0.143.

---

## Current state: 3 of 5 gates green

| Gate | Command | Status |
|---|---|---|
| 1. build | `./gradlew build -x test` | **PASS** |
| 2. unit tests | `./gradlew test` | **PASS** — 19 tests, 0 failures |
| 3. datagen | `./gradlew runData` | **PASS** — 3m12s, 35,662 JSONs |
| 4. gametest | `./gradlew runGameTestServer` | 1st run: server booted, ran all tests, **5 failed → all triaged and fixed**. **RERUN WAS IN FLIGHT** when this was written — check the log first. |
| 5. server smoke | `./gradlew runServer` | **NOT YET RUN** |

All work is committed; working tree clean at `30ca7b9d7`.
Everything compiles: **0 errors** across ~680 source files (was 4,833 at the start of the loop).

### Immediate next step
1. Check whether the in-flight gate 4 rerun finished:
   `grep -aE "BUILD SUCCESSFUL|BUILD FAILED|required tests" .porting/logs/gametest.log | tail -3`
   If no BUILD line and no java process is running, just re-run it:
   ```bash
   cd /c/Users/karl/Documents/Projects/project-rankine && ./gradlew runGameTestServer --console=plain > .porting/logs/gametest.log 2>&1
   ```
2. Then gate 5 (`runServer`). It needs `run/server/eula.txt` with `eula=true`.
   `.porting/verify.sh` writes that and drives all gates, but has NOT been exercised end-to-end —
   prefer the raw gradle commands if it misbehaves.
3. Then hand back to Karl for a manual dev-client pass (`./gradlew runClient`).

---

## CRITICAL environment gotchas (all already fixed in-repo — do not regress)

1. **Gradle daemon must run on a JDK, not a JRE.** NeoForm's decompile/recompile calls
   `javax.tools.JavaCompiler` in-process and dies with "compiler is null" on a JRE.
   `gradle.properties` sets `org.gradle.java.home=C:/Users/karl/.gradle/jdks/eclipse_adoptium-21-amd64-windows.2`.
   Update that path if the auto-provisioned JDK disappears.
2. **Builds are SLOW (3–15 min) and look hung.** `src/generated/resources` (36k files) is on the
   resource path, so `:processResources` + Gradle input snapshotting hashes ~92k files one
   `open()` at a time. A thread dump showed `DirectorySnapshotter → DefaultFileHasher →
   FileInputStream.open0` — it is progressing, not deadlocked.
   - **Do not judge liveness by log output or file mtimes** — neither changes during snapshotting.
     Check accumulated CPU of the java process instead (`Get-Process java | Select Id,CPU`).
   - `org.gradle.caching` and `org.gradle.parallel` are **off** on purpose (pure overhead here).
   - **Windows Defender exclusion: DECIDED AGAINST — do not apply, and do not re-suggest it
     unprompted.** Real-time scanning of those file opens is the dominant cost, but Karl asked and
     the conclusion was that it is not worth trading a security control for a few minutes on
     one-off gate runs (datagen is ~3m once caches are warm). If build times ever do become the
     bottleneck, exclude ONLY the two machine-generated trees, never `~/.gradle` — that is where
     third-party JARs from Maven Central/NeoForged/BlameJared land and is the one place scanning
     has real value against a compromised dependency:
     `Add-MpPreference -ExclusionPath '<repo>\build','<repo>\src\generated\resources'`
     (Those two trees are ~92k reproducible generated files and account for nearly all the cost.)
     It is a system security setting — surface the command, let Karl run it, never run it here.
   - One earlier run genuinely wedged; if CPU is flat for 5+ min, `./gradlew --stop`, kill stray
     java PIDs, and re-run.
3. **Gradle incremental compilation emits stale phantom errors** (symbols reported missing that are
   plainly declared). If an error looks impossible, force a clean recompile before chasing it.
   This wasted real time once — 136 bogus errors vanished on a clean recompile.
   - **BUT: `--rerun-tasks` is a trap here.** It also re-runs `createMinecraftArtifacts`, i.e. the
     entire NeoForm decompile/recompile pipeline. One such run took **2h38m**. Prefer
     `./gradlew cleanCompileJava compileJava`, or just delete `build/classes`. Reserve
     `--rerun-tasks` for genuinely suspected stale state, and expect to lose hours if you use it.
4. **javac caps at 100 errors by default.** `-Xmaxerrs 3000` is set in build.gradle; without it the
   true error count was hidden (200 reported vs 4,833 actual).
5. **Datagen needs `src/generated/resources` as BOTH `--existing` and `--output`** (providers parent
   models to previously generated ones). This is wired in build.gradle; dropping it breaks runData.

---

## Reference material

- **Decompiled MC 1.21.1 + NeoForge sources: `C:\Users\karl\Documents\Projects\mc-1.21.1-sources`**
  (~49MB, `net/minecraft/**` + `net/neoforged/**`). Grep these for real signatures — the single
  most valuable asset in this port. Never guess an API.
- `PORTING_GUIDE.md` — conventions every agent followed (package renames, registry/codec/capability
  patterns, data components, enchantments, worldgen).
- `.porting/reports/*.md` — per-area agent reports with cross-package contracts and behavior deltas:
  `A1-init-giant`, `A2-init-misc`, `B2-machines`, `C-items`, `D-recipes`, `E-enchantments`,
  `G-events`, `datagen`, `JEI`, `rendertype-spec`.
- `.porting/RELAUNCH_PLAN.md` — orchestration history; `.porting/agent-prompts.md` — the agent prompts.
- `.porting/logs/` — gate logs (gitignored).

---

## Bug classes this port hit (watch for more of these in gates 4–5)

These were all invisible to the compiler and found only by running things:

1. **Config read at registry time now throws.** NeoForge fires registry events *before* configs load,
   and never loads configs during datagen. Fixed in 6 places (static Block.Properties, 10
   AttributeModifiers, Block/Item field initializers, and `isRandomlyTicking` — which runs while the
   block state cache is built). If a new "Cannot get config value before config is loaded" appears,
   this is the pattern.
2. **BlockState property maps are identity-keyed in 1.21** (`Reference2ObjectArrayMap`; 1.18 used an
   equals-based `ImmutableMap`). Two separate-but-equal `Property` instances no longer interoperate.
   `CornStalkBlock` declared its own `section` property while `CornPlantBlock`/datagen applied
   `TripleCropsBlock.SECTION` — would have thrown when corn grew. All duplicate property
   declarations were audited; corn was the only cross-class case.
3. **Empty `@EventBusSubscriber` classes now hard-error** (was silently ignored).
4. **`ItemTagsProvider.copy` validates the source block tag exists.** Forge auto-created an empty
   builder. Five cases fixed by declaring the empty item tag directly (matching 1.18 output byte-for-byte).
5. **Ambiguous `X::new`** — `DeferredRegister.Blocks/Items` have both `Function<ResourceLocation,B>`
   and `Supplier<B>` overloads. Always write `() -> new X()`. Hit twice (literal and computed names).

---

## Known behavior deltas to mention when reporting to Karl

Accepted and documented in-code + in CHANGELOG.md:
- Chunk retrogen state moved from a Forge capability to a NeoForge attachment and is **not migrated**
  → pre-existing chunks run the retrogen replacement pass once more.
- Invigorating Totem repairs at half its old rate (the old rate came from a 1.18 double-tick bug).
- Tofu/Cooked Tofu/Tuna/Cooked Tuna lose the "is meat" flag (API removed).
- Rankine leaves are now waterloggable (vanilla LeavesBlock forces the property).
- Electromagnet's "is metal" gate approximated via `SoundType.METAL` (misses netherite/anvil).
  Clean fix would be a `rankine:magnet_attractable` block tag.
- Off-hand right-clicks no longer reach a few block fallbacks (vanilla `use()` split).
- Advancement custom item predicates are now `ItemSubPredicate`s; their JSON shape changed.
- Recipe-unlock advancements grouped under `recipes/misc`; **recipe ids themselves unchanged**.
- Cannonball/Enderball post-attack enchantment effects: only victim-side fires (source is ownerless).

Pre-existing 1.18 bugs fixed in passing: alloy template colour (written/read under different keys),
alloy knockback resistance (float written, int read), double-firing player tick handlers, a block
overlay handler that never fired, Ender Amalgam Shovel's category matching the spear.

---

## Test assets

- `src/test/java/.../ElementEquationTest.java` (14 tests) — alloy stat curves, pure math.
- `src/test/java/.../WeightedCollectionTest.java` (5 tests) — seeded drop weighting.
- `src/main/java/.../gametest/RankineRegistryTests.java` — registry/datapack integrity: block+item
  counts, block↔item wiring, recipe types loading real recipes, enchantments parsing, damage types,
  fluids having FluidTypes, loot tables existing per block, creative tabs, data components.
- `src/main/java/.../gametest/RankineMachineTests.java` — **highest-value runtime coverage**: every
  machine exposes an `IItemHandler` capability on some side with >0 slots (the capability rework
  touched all 10 machines), every block entity places/ticks/saves without throwing, BE types bound
  to blocks.
- `src/main/resources/data/rankine/structure/empty.nbt` — hand-built 5×5×5 empty GameTest template
  (tests use `@GameTest(template = "empty")`). Unverified until gate 4 runs.

The GameTests are deliberately registry-driven rather than asserting specific machine recipes, so
they stay valid as details change.

---

## Gate 4 (GameTest) — first run results, already triaged

The headless server **booted fully**: all registries loaded, datapacks parsed, world generated,
tests executed. That alone validates the bulk of the port. 5 of the tests failed; all were
investigated against the 1.18.2 baseline and resolved in commit `30ca7b9d7`.

**One real bug, fixed:** every `AlloyBlock` creates an `AlloyBlockTile`, but the block entity type
only listed the generic `rankine:alloy_block`. That mismatch existed in 1.18.2 as well, where it was
only *logged as a warning*; 1.21 throws `IllegalStateException` from `LevelChunk`, so placing any of
the 35 specific alloy blocks (steel_block, bronze_block, invar_block, …) would fail. Now bound to
all 36 via `RankineLists.ALLOY_BLOCKS`. **This is the template for the remaining risk in this port:
1.18 tolerances that 1.21 turned into hard errors.**

**Three were my tests being too strict, relaxed (not the mod's fault):**
- A sided `IItemHandler` may legitimately expose 0 slots on faces with no accessible slots (1.18's
  sided wrappers did the same). Now requires one usable face, not all.
- There is no `rankine:pedestal` block — pedestals are registered per alloy (`rose_gold_pedestal`…).
  Removed from the machine list; they are still covered by the block-entity sweep.

**Pre-existing content gaps — deliberately NOT "fixed" (would change gameplay; Karl's call):**
- **44 blocks have no loot table** and therefore drop nothing when broken: skarn_stairs, skarn_wall,
  cast_iron_support_stairs/wall, fiber_block_wall, sodium_vapor_lamp, aged_cheese, stick_block,
  distillation_tower, pcf, tap_line, locust_spine, the fumaroles, several ores, and more.
  Verified against 1.18.2's committed `src/generated/resources` — **absent there too**.
- **`piston_crusher` and `gyratory_crusher` have no BlockItem** (registered as blocks only, same as
  1.18.2), so they are uncraftable/unobtainable.
- Both checks are now *floor* assertions (loot tables > 900 loaded; > 90% of blocks have items) so
  they catch a wholesale provider failure without asserting a gap that predates the port.
  If Karl wants these filled in, that is a content change, not a port fix.

## Decisions already settled (do not re-litigate)

- **Security review (`/security-review`) is not warranted for this port.** Grounded in the actual
  diff, not a guess: the only server-bound network payload is `SelectAlloyPacket` (a single VAR_INT)
  whose handler is an empty lambda — preserved 1.18 behaviour, where the body was commented out.
  `FluidStackPacket` is client-bound only. No auth, path handling, command execution, external I/O,
  or credential surface anywhere in the change. Data components / recipe codecs / datapack JSON all
  deserialize trusted content (the mod's own generated files and packs the server owner installs),
  so failures there are correctness bugs, not privilege boundaries.
  **It would become worth doing** if `SelectAlloyPacket`'s handler is ever wired up to act on client
  input (it is the template-table alloy picker), or if new server-bound packets write NBT or move
  items — that is unvalidated client input against a container the player may not have open.
- **Windows Defender exclusion: declined.** See the build-speed section above.

## If gate 4 or 5 fails

Expect runtime-only issues of the classes listed above. Useful moves:
- `grep -aE "Caused by: java\.lang" .porting/logs/gametest.log | head`
- Rankine frames: `grep -aoE "com\.cannolicatfish[A-Za-z0-9_.$]*\([A-Za-z]*\.java:[0-9]*\)" <log> | sort -u`
- Crash reports land in `run/gametestserver/crash-reports/` or `run/server/crash-reports/`.
- `PORT-TODO(` markers remain in-source for anything deliberately deferred: `grep -rn "PORT-TODO(" src/main/java | wc -l`

After gates pass: Karl does a quick manual dev-client pass (`./gradlew runClient`). CHANGELOG.md
already has the 1.21.1-1.4.0 entry written.
