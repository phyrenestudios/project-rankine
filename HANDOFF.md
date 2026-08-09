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
| 4. gametest | `./gradlew runGameTestServer` | **PASS** — "All 16 required tests passed". Re-run after the gate 5 fixes below, since those change registry contents: still green, 26s. |
| 5. server smoke | `./gradlew runServer` | **PASS** — booted a fresh normal-terrain world to `Done (6.461s)`, idled 6 min, no crash reports, no ERROR after boot. **Found 2 load-time content bugs — see below.** Re-run after the fixes: both recipes now load, `Done (4.718s)`. |

Gates 4 and 5 were both re-run *after* the final commit, so the table describes `408ded349`,
not an earlier state.

All work is committed; working tree clean at `30ca7b9d7`.
Everything compiles: **0 errors** across ~680 source files (was 4,833 at the start of the loop).

### Immediate next step

**Playtest round 2 is fixed and verified headlessly; it needs Karl's eyes in `./gradlew runClient`.**
Four issues, all root-caused against the real Patchouli/JEI jars and the 1.18 baseline before any edit:

1. **Blank Rankine Journal** — Patchouli 1.20.5+ binds a book through the dedicated `patchouli:book`
   data component and reads ONLY that (no legacy-NBT fallback, never reads `minecraft:custom_data`).
   The previous round translated 1.18's `nbt` field to `custom_data`, the literal equivalent, which
   yields a real guide book bound to nothing. Fixed in `compatibility/Patchouli.java` and
   `data/rankine/recipe/rankine_journal.json`. **The book CONTENT was fine** — the `use_resource_pack`
   + `/assets/` layout from `8d11649c4` is correct; do not revert it (Book's ctor throws without it).
   Also fixed `book.json`'s `creative_tab`, which is a ResourceLocation on 1.21 so a bare
   `rankine_misc` resolved to `minecraft:rankine_misc`.
2. **All 22 armor pieces stacked to 64** — 1.21's `ArmorItem` ctor no longer applies the material's
   durability, and `Properties.durability(n)` is the only thing that sets MAX_STACK_SIZE=1. Same root
   cause left 12 pieces indestructible and all 22 un-enchantable. `RankineArmorMaterials`'s own class
   doc had spelled out the required call; it was never applied. **This is a new instance of the
   "1.18 did it for you in a base class, 1.21 makes you ask" family.**
3. **Alloy hoe/axe/shovel/sword scrambled in JEI** — NOT a datagen bug. All 486 generated alloy
   recipes are shape-identical to 1.18. 1.18 exposed grid size via Forge's `IShapedRecipe`; NeoForge
   1.21 deleted that interface with no replacement, so JEI saw 0x0, treated the recipe as shapeless
   and re-laid it over a guessed square — right by luck for 3x3/2x2/1x1, wrong for 2x3/1x3/3x2. Fixed
   with an `ICraftingCategoryExtension` in `JEIRankinePlugin`. Note `setRecipe` must be overridden,
   not just getWidth/getHeight: the default delegates to a no-op and would render an empty grid.
4. **JEI category text black-on-black** — 1.18's `Font#draw` does not shadow; 1.21's 5-arg
   `GuiGraphics#drawString` defaults `dropShadow` to TRUE. All 20 sites now pass it explicitly.
   **No colour was ever changed by the port** — the 1.18 and 1.19 baselines both draw these black.
   Karl remembered white text; he chose 1.18 parity (black, no shadow) when asked.

Gates re-run after these fixes: **1 build PASS, 2 unit tests PASS (25, was 24), 4 gametest PASS
("All 22 required tests passed", was 20)**. Datagen NOT re-run and not needed — `git status` over
`src/generated/resources` is clean. Gate 5 not re-run: the only datapack change is the journal recipe,
and gate 4 already loads every datapack (verify.sh's gate-5 journal whitelist matches on the recipe
id, not the message text, so it still covers the new failure mode when Patchouli is absent).

New regression nets: `RankineRegistryTests#armorIsSingleStackAndDamageable` (every rankine ArmorItem
is stack-1 and has MAX_DAMAGE) and `#patchouliJournalIsBoundToItsBook` (verified to actually assert —
Patchouli loads in dev runs, confirmed in the log), plus unit test `PatchouliJournalContentTest`,
which walks all 74 journal JSONs and asserts every `rankine:` item id resolves. That last one guards a
nastier class: Patchouli resolves item ids eagerly, so ONE bad id makes `Book.reloadContents` install
empty contents and blank the WHOLE book, with a single client-side log line as the only trace.
NB `build.gradle` now passes `rankine.projectDir` to the test task — MDG runs tests from
`build/minecraft-junit`, not the project root.

**Still open, deliberately not done (see the spawned task):** `AlloyCraftingRecipe.Serializer#toJson`
drops per-key alloy metadata (`alloyComp`/`alloyRecipe`/`langName`/`color`) on 136 generated recipes,
so steel-rod-gated superalloy tools currently accept ANY alloy rod. Real gameplay regression, but
fixing it needs `runData` and re-tightens matching, so it is Karl's call.
Also unfixed by choice: vanilla's recipe book still mis-fills the grid for those four tool shapes
(only `extends ShapedRecipe` fixes that; Karl declined the wider blast radius).

**The port is otherwise ready for Karl's manual dev-client pass (`./gradlew runClient`).**

Gate 5 found two bugs that no earlier gate could see, because a Minecraft server *logs* bad
datapack content and boots anyway — it does not fail. Both are fixed:

1. **`minecraft:milk` was not registered** → `rankine:mixing/unaged_cheese_mixing` and
   `rankine:mixing/pancake_batter_mixing` failed to parse ("Unknown fluid 'minecraft:milk'"),
   i.e. cheese and pancake batter were **uncraftable**. Forge 1.18 registered the milk fluid
   unconditionally; NeoForge only registers it if a mod calls `NeoForgeMod.enableMilkFluid()`
   from its constructor. Added to `ProjectRankine`. **This is a new instance of the "Forge did it
   for you, NeoForge makes you ask" class — worth checking any other Forge-provided extra.**
2. **`rankine_journal`'s recipe result used the `nbt` field**, which 1.21's ItemStack codec
   (`id` / `count` / `components`) does not have. It is not an error — the codec silently ignores
   the unknown field — so the crafted Patchouli book would have opened blank. Now written as
   `components: {minecraft:custom_data: {...}}`, matching what `compatibility/Patchouli.java`
   builds in code. **Unverified against a real Patchouli install** (not in the dev runtime);
   worth a look if Karl ever runs with Patchouli.

Note `rankine:rankine_journal` still logs a parse error in dev — its result item is
`patchouli:guide_book` and Patchouli is not in the dev runtime. **1.18.2 logged the same error for
the same reason**; neither version guards the recipe with a mod-loaded condition. Adding a
`neoforge:conditions` guard would silence it, but changes nothing functionally (the recipe is
absent either way), so it was left alone.

`.porting/verify.sh` gate 5 has been corrected against the real run and now also fails on any
recipe parse error except the journal — that check is what would have caught the milk bug.

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
6. **`runServer` runs in `run/`, not `run/server/`.** ModDevGradle points every run config
   (client / server / data / gameTestServer) at the same game directory. The eula therefore goes in
   `run/eula.txt`; an earlier version of verify.sh wrote `run/server/eula.txt`, which the server
   never reads, so it would have refused to boot. Crash reports land in `run/crash-reports/`.
7. **You cannot stop a `runServer` cleanly, and both obvious ways fail.** MDG does not wire stdin
   into the run task, so piping `stop` into `./gradlew` never reaches the server console (verified —
   the server ignored it and kept idling). And the server JVM is forked by the *Gradle daemon*, so
   it is not a child of your `gradlew` process and survives killing that. Kill it by command line —
   but **not** by matching `--nogui` or any other program argument: MDG passes those in an
   `@argfile`, so they never appear on the command line at all. Match the argfile path, which is
   unique per run config. `-clike` (case-**sensitive**) is deliberate — plain `-like` would also
   match `gameTestServerRunProgramArgs.txt` and kill a concurrent gate 4:
   ```bash
   powershell.exe -NoProfile -Command 'Get-CimInstance Win32_Process | Where-Object { $_.Name -eq "java.exe" -and $_.CommandLine -clike "*\serverRunProgramArgs.txt*" } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force }'
   ```
   Gradle then reports **BUILD FAILED** — that is just the killed JVM's exit code, not a gate
   failure. Judge gate 5 by the log, never by Gradle's verdict.

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
6. **Optional runtime mods (JEI, Patchouli) must go on the source set's `runtimeClasspath`, NOT
   `additionalRuntimeClasspath`.** FML's dev-run mod discovery scans the JVM `-cp` for
   `META-INF/neoforge.mods.toml`; MDG builds each run's `-cp` from the source set's
   runtimeClasspath. `additionalRuntimeClasspath` feeds `build/moddev/*LegacyClasspath.txt`
   (boot-layer LIBRARIES) which FML never scans for mods — a jar there loads silently as a plain
   library and simply never appears in the mod list. build.gradle now defines a custom
   `localRuntime` configuration extending runtimeClasspath (keeps dev mods out of the published
   POM). An earlier version of this note claimed the opposite — `additionalRuntimeClasspath` was
   verified to put the jar on the classpath txt, but "on a classpath" ≠ "loaded as a mod".
   Verify by grepping the run log's mod list, never the classpath files.
7. **`grep` finds commented-out registrations — check for an enclosing `/* */` before believing a
   declaration exists.** RankineItems.java has three block-commented registration groups inherited
   from 1.18.X (62 metal lanterns at ~515-580, the 6 `CRUSHING_HEAD_HL*` items, and the dead
   `itemInit()` at ~2090). Both a triage agent and a straight grep concluded the lantern items were
   registered; javac disagreed. They are dead code UPSTREAM (1.18.X has the same comment blocks),
   so leaving them dead is faithful — they are pre-existing content gaps like `piston_crusher`,
   not port regressions.
7. **Item tints are ARGB in 1.21; they were RGB in 1.18 — a zero alpha byte renders NOTHING.**
   `ItemRenderer#renderQuadList` now does `FastColor.ARGB32.alpha(i)` and passes it as the quad's
   alpha. Every plain `0xRRGGBB` tint therefore has alpha 0 and the item is drawn fully transparent:
   present in the inventory, correct data components, no sprite. Found only by looking at a creative
   tab — **no gate catches this**, since gates 1–5 are all headless. Fixed centrally by
   `util/colors/ItemTint#opaque`. **Block tints are still RGB**, so the same colour class looks right
   on a placed block and invisible as an item — that asymmetry is what makes it confusing to spot.
   Vanilla's convention is the tell: its "no tint" constant is `-1` (`0xFFFFFFFF`), not `0xFFFFFF`.

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

- `src/test/java/.../AlloyEnchantmentGateTest.java` (5 tests) — the alloy-gate truth table, via the
  pure `IAlloyTool#alloyGateDecision`. Writing this table out is what caught a real bug: a matching
  alloy was bypassing the item-type check, so a pewter-alloyed ALLOY HELMET accepted Antiquated (a
  tool-only enchantment). 1.18.2 required `rs.equals(required) && instanceof IAlloyTool`; the second
  half is the supported_items tag now, and it had been dropped.
- `src/test/java/.../ElementEquationTest.java` (14 tests) — alloy stat curves, pure math.
- `src/test/java/.../WeightedCollectionTest.java` (5 tests) — seeded drop weighting.
- `RankineRegistryTests#alloyGatedEnchantmentsRespectAlloy` — asserts the alloy gate on Antiquated,
  Shape Memory and Endobiotic in both directions, for both the anvil (`supportsEnchantment`) and the
  enchanting table (`isPrimaryItemFor`). Worth knowing why it asserts the hooks directly rather than
  driving a real anvil: **the anvil skips the check entirely for creative players**
  (`AnvilMenu`: `if (player.getAbilities().instabuild) flag1 = true;`), so a creative-mode test of
  this gate always passes and proves nothing.
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
