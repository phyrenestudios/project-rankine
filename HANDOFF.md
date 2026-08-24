# Project Rankine — Forge 1.18.2 → NeoForge 1.21.1 port: HANDOFF

**Read this first, then `PORTING_GUIDE.md`, then `.porting/WORKSTREAM.md` and `.porting/reports/*.md`.**

Repo: `C:\Users\karl\Documents\Projects\project-rankine`, branch **`1.21.1-neoforge`**
(based on `origin/1.18.X` = stable v1.3.8, **not** the repo's default `1.19.X`, which is an
unfinished WIP port and was deliberately ignored).

Target: Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21 / ModDevGradle 2.0.143.

HEAD as of this writing: **`4c5d10e9e`**, about to be tagged **`1.21.1-1.4.0`** once QA and the
release gate run below close out. This file describes the release, not a mid-port snapshot — a
70-agent review (2026-08-09) and a four-batch fix workstream (2026-08-09 → 2026-08-23, 14 commits)
happened since the port first compiled. `.porting/WORKSTREAM.md` is the full day-by-day log; treat
this file as a distillation of it, not a replacement.

---

## Current state: 5 gates, release candidate at `4c5d10e9e`

| Gate | Command | Status as of `4c5d10e9e` |
|---|---|---|
| 1. build | `./gradlew build -x test` | **PASS** — every one of the 14 fix-workstream commits built green (HEAD itself: 11m27s, an environment-slow day) |
| 2. unit tests | `./gradlew test` | **PASS** — **46/46**, 0 failures |
| 3. datagen | `./gradlew runData` | **PASS** — idempotent; HEAD's own datagen run touched exactly the 8 files its feature predicted (4 modifier JSONs + 4 wall models), no collateral churn |
| 4. gametest | `./gradlew runGameTestServer` | **PASS** — "All 63 required tests passed" |
| 5. server smoke | `./gradlew runServer` | **PASS** — re-verified fresh on `4c5d10e9e` via a full `verify.sh all` run (`.porting/logs/verify-release-4c5d10e9e.log`): boots a normal-terrain world to `Done`, all recipes loaded. Previous confirmation was 2026-08-02, before this entire fix workstream — this run closes that gap. |

Gates 1–4 are re-proven fresh at every one of the 14 commits (each engineer report in
`.porting/WORKSTREAM.md` includes its own gate run). Gate 5 had not been re-run since before the
fix workstream started; a fresh `verify.sh all` was launched concurrently with this rewrite and
**completed PASS on all five gates while this file was being written** — see the WORKSTREAM log
entry timestamped 2026-08-23 "H3 `verify.sh all` PASS on `4c5d10e9e`". Re-run any single gate with
`bash .porting/verify.sh <gate>`.

### What the test suite actually covers

**46 unit tests** (`src/test/java/com/cannolicatfish/rankine/`, 11 files) are pure-JVM:
`ElementEquationTest` (14, alloy stat curves), `WeightedCollectionTest` (5, seeded drop weighting),
`AlloyEnchantmentGateTest` (5, the alloy/enchant gate truth table), `RenderTypeModelTest` (5,
render-type assignment over the generated model tree), `AlloyKnockbackResistanceTest` (5),
`AABBInflationRegressionTest` (4), `AlloyArmorTintSourceTest` (2), `AlloyBlockModelTest` (2),
`MaterialTestingTableTitleTest` (2), `FluidDescriptionIdTest` (1), `PatchouliJournalContentTest` (1).

**63 GameTests** (`src/main/java/com/cannolicatfish/rankine/gametest/`, 4 files) boot a real
headless server and exercise the live registries/datapacks: `RankineRegistryTests` (47, the bulk of
the suite — baseline registry/datapack integrity plus nearly every feature-specific regression net
added since the review: enchantability and table-pool membership, compostables, POI job sites,
farmland hydration, every worldgen category selector, End vein placement and runtime replacement,
fluid physics, tree-stump tagging, reactive/explosive drop mode, biome-resolution reset),
`RankineAlloyTests` (7, the alloy system: rod gating, packaged-item recipe resolution, AlloyBlock
place/break/sync/tint), `RankineCombatTests` (3, damage-event ordering, parry i-frames, alloy-arrow
enchantments), `RankineMachineTests` (4, machine capability exposure and block-entity binding).

### The falsification discipline (house rule for this workstream)

Every fix in batches 1–4 follows the same proof shape, not just "green gate": (1) predict, before
touching code, exactly which assertions a regression test would fail against the OLD code and why;
(2) confirm the test actually fails that way pre-fix, proving it isn't vacuous; (3) fix; (4) confirm
the predicted assertions now pass; (5) **invert** every new/changed assertion in one run and confirm
it fails with the *exact predicted mismatch count* — this proves the test would catch a real
regression, not merely that it currently happens to pass; (6) restore the file and diff/sha256 it
against the pre-inversion version to confirm the revert was clean. Every batch report in
`.porting/WORKSTREAM.md` states predicted-vs-observed counts; they match exactly, every time, across
all 14 commits. A batch report that skips this shape is incomplete, not evidence the fix works.

### Where the evidence lives

- `.porting/WORKSTREAM.md` — the primary source: every feature, its finding, engineer, commit, gate
  numbers, falsification counts, and triage notes. Everything in this file is a distillation of it.
- `.porting/CLIENT-TEST-PROGRESS.md` — the running visual-check log each QA pass updates.
- `.porting/logs/visual/` — QA screenshots, prefixed `qa_` (batch 1), `qa2_` (batch 2), `qa3_`
  (batch 3), tied to specific checks in the progress file above.
- `.porting/logs/` — raw gate logs (gitignored; do not hand-edit, do not commit).

---

## What shipped since the review (14 commits, batches 1–4)

One line each, player-visible framing. Full detail, root causes and gate numbers for every item are
in `.porting/WORKSTREAM.md`.

**Batch 1 (2026-08-09) — the P0 fix and the first datapack-family gaps:**
- `8cf5681fc` — Rankine gear becomes enchantable again (14 vanilla `enchantable/*` tags populated;
  before this, nothing modded could be enchanted at a table or an anvil, at all).
- `4f591c41f` — Composting, villager professions, 21 enchantments' loot/trade availability, and
  crop-soil hydration all restored — four separate instances of "registration moved into a datapack
  file datagen was never taught to emit."

**Batch 2 (2026-08-17) — worldgen:**
- `3f423d0e6` — Antiquated rejoins the enchanting table pool (only on properly alloyed pewter gear).
- `9f27c95a9` — End ore veins, Snowy Plains terrain, and modded-dimension stone/intrusion coverage
  all restored — three separate worldgen key/tag bugs (`"theend"` vs `"the_end"`, a too-narrow icy
  tag, dimension-tag gating that excluded every non-vanilla dimension).
- `ed3765213` — End vein runtime path proven end-to-end, headlessly (no gameplay change; closes the
  "couldn't find one in-world" QA finding as a sampling artifact, not a bug).

**Batch 3 (2026-08-19/20) — alloy correctness and the first decided items:**
- `f7587f689` — Tier-3 alloy tools demand the correct rod again, loot-chest packaged gear stops
  minting blank/crashing, alloy blocks keep their composition through place-then-break.
- `3f25463f6` — Rankine fluids stop drowning/pushing/swimming you like water, an antimatter blast
  stops dropping ~12× too many items, the gas-vacuum and building-mode tool icons track what you
  actually did, stony shores get their ore veins back.
- `b5a824fa7` — Six armor enchantments (Dune Walker, Snow Drifter, Speed Skater, Swift Swimmer, Gas
  Protection, Aqua Lense) declared the 1.18.2 way; End veins retuned to actually be findable.

**Batch 4 (2026-08-22/23) — the release batch:**
- `d57e198e5` — Worn alloy armor is tinted again; placed alloy blocks (and their item icon) show
  their alloy colour and sync correctly to the client.
- `3dde0a6be` — 104 element/storage blocks render solid again (not translucent), 1,180 stone-brick
  variants stop shimmering (mipmapped again), fluid buckets/tanks/JEI show real names instead of raw
  translation keys, the parry/gas HUD overlays are visible again, the Material Testing Table has a
  title.
- `a01d97e0f` — Damage buffs respect absorption again, parried/immune hits keep their i-frames, alloy
  arrows keep their bow enchantments, knockback resistance is real but capped (never full immunity),
  the Spear enchants at both the table and the anvil; the Totem of Infusing's by-hand infusion is
  GameTest-pinned (see `61956edc4` — its anvil-book refusal turned out to be a faithful 1.18.2 override).
- `9ebb92dd6` — The mixing barrel and the balloon stop yanking entities from a 6×-too-large box,
  chopping a tree on grass drops the log instead of turning it into a stump, reactive-item/explosive
  drops stop silently decaying, switching worlds mid-session stops using the previous world's biome
  data.
- `6bfde63dd` — Every fluid now has its own realistic movement feel, driven by its own viscosity and
  density, instead of either "walk through it like air" or "swim through it like water."
- `4c5d10e9e` — Meteorites, fumaroles, wall mushrooms and cobble patches (and `the_void`) generate in
  modded dimensions again; End sphere ores are twice as common; the `"all"` biome selector works
  (kimberlitic tuff generates).

Also written, doc-only, no code commit: `.porting/reports/TICKET-ores-without-loot-tables.md` (F37 —
see Known Gaps).

---

## Decisions settled (do not re-litigate)

**Process/environment:**
- **Security review (`/security-review`) is not warranted for this port.** Grounded in the actual
  diff: the only server-bound network payload is `SelectAlloyPacket` (a single VAR_INT) whose
  handler is an empty lambda — preserved 1.18 behaviour, where the body was commented out.
  `FluidStackPacket` is client-bound only. No auth, path handling, command execution, external I/O or
  credential surface anywhere in the change. **Would become worth doing** if `SelectAlloyPacket`'s
  handler is ever wired up to act on client input, or a new server-bound packet writes NBT or moves
  items.
- **Windows Defender exclusion: declined.** See the environment gotchas below for the reasoning; the
  command is documented there for Karl to run himself if build times ever become the bottleneck.

**Content/gameplay** (from `.porting/reports/PORT-REVIEW-2026-08-09.md`, "Decisions for Karl" +
"Decisions recorded 2026-08-22"; numbering and option letters are the review's own):

1. Alloy armor knockback resistance → **(B) keep, scaled down.** Never reaches 1.0; the strongest
   realistic set caps near vanilla full netherite. `a01d97e0f` (F27).
2. The 44 loot-table-less blocks (and 2 BlockItem-less crushers) → **(A) leave for 1.4.0**; write an
   issue-ready ticket for the ore subset. `.porting/reports/TICKET-ores-without-loot-tables.md`
   (F37, doc-only).
3. Vanilla recipe-book grid mis-fill for 4 tool shapes → **moot**, already fixed pre-review
   (`43e6895f9`).
4. 1.18.2 network-serialization bugs silently fixed by the codec rewrite → **(A) keep**, recorded as
   an accepted delta (see below). No commit — the fix predates the review; this was a decision not to
   revert it.
5. Spear / Totem of Infusing enchanting-table access → **(B) allow BOTH** table and anvil.
   `a01d97e0f` (F28) + `61956edc4` (F39) — the spear half turned out to be a non-bug (REFUTED: the
   table already offered Impaling/Loyalty; the anvil allowance is the deliberate widening). The totem
   needed no change at all: it structurally cannot enter an enchanting table (no durability — 1.18.2
   too) and it deliberately refuses anvil books (its own 1.18.2 `isBookEnchantable` override, carried
   faithfully). Its real feature is by-hand infusion via `use()` (enchanted item or book in the main
   hand), now GameTest-pinned along with the anvil refusal. Deleting that override to open anvil
   books would be a gameplay change beyond 1.18.2 — offered to Karl, not taken.
6. The six never-table-offerable enchantments → **(b) `primary_items: []`** — the literal 1.18.2
   translation, joins the pool tags, zero gear-behavior change (enchanted books of them are
   restored). `b5a824fa7` (F17).
7. End ore vein height bands → **retune for findability**, deliberate non-parity. 1.18.2's own bands
   wasted over half of all vein attempts below or above the islands. `b5a824fa7` (F18).
8. Fluid buoyancy/movement → **make it realistic, per fluid** (deliberate feature, not parity).
   `6bfde63dd` (F33).
9. Modded-dimension coverage for meteorites/fumaroles/wall mushrooms/cobble patches → **yes**.
   `4c5d10e9e` (F34). **Scope note:** this decision did NOT include flora or trees — those remain
   gated on `c:is_overworld`; see Known Gaps.
10. Alloy block colour → **yes, add it** (it never worked in 1.18.2 either — this is a new feature,
    not a restoration). `d57e198e5` + `a01d97e0f` (F20 + F20b).
11. Unverified-findings pool → **(C) pull in three, defer the rest to 1.4.1**: the Spear/Totem table
    hook (`a01d97e0f`, F28), the `ensureBiomesResolved` per-world-start reset (`9ebb92dd6`, F32), and
    the reactive-item explosion drop mode (`9ebb92dd6`, F31). Everything else in the pool is still
    open — see Known Gaps.
12. End sphere ore spawn chance → **double it**, 0.3 → 0.6 (4 config rows). `4c5d10e9e` (F35).
13. Crucible / Damascus steel zero-input alloying → **intentional, keep as 1.18.2** (Damascus is
    template-gated by design). Closed, no code change.
14. `"all"` biome selector on `kimberlitic_tuff` → **a bug; implement it.** `4c5d10e9e` (F36).

---

## Accepted deltas vs 1.18.2 (deliberate, documented — not regressions)

**From the fix workstream (batches 1–4):**

- **Alloy armor knockback resistance scale = 0.1**, applied at the point attributes are added (the
  stored stat itself is untouched, for tooltips/Indexer). Brass 25%-zinc set: 1.18.2 gave 0.00
  (silent bug), mid-port accidentally gave 1.00 (full immunity), release gives **0.10**. Ender
  amalgam 0.30. A 90%-netherite white-gold set caps at **0.40** — vanilla full netherite, the
  deliberate ceiling; 1.0 is unreachable by design.
- **Four 1.18.2 client-sync bugs stay fixed, not restored** (decision #4): air-distillation JEI pages
  no longer show their dimension and biome lists swapped; evaporation and foraging pages now
  correctly send their biome tags to the client; a stripping recipe's chance is read as the float it
  actually is. No crafting outcome changes anywhere — display-only. (The original review estimated
  "three" bugs here; CHANGELOG.md's later, more precise accounting says four — trust the changelog.)
- **Per-fluid realistic movement** (feature, not parity): resin and the muds are a crawl (~2.7×
  slower than water), liquid mercury and sulfuric acid make you float, carbon disulfide is slightly
  *faster* than water, thick fluids cushion falls. Drowning/pushing/swimming stay off (unchanged from
  F13). Two declarations were corrected on physical grounds while implementing this: carbon
  disulfide's viscosity 6000 → 363 (the 1.18.2 number was backwards and had been physically inert the
  whole time); raw maple sap stays ≈ water (~1300, thin) rather than being thickened, because the
  mod's *syrup* is the thick item and raw sap genuinely isn't — flagged for Karl in case "maple syrup
  slows you" was meant to describe the raw-sap fluid rather than the crafted item.
- **End vein height bands retuned:** natives (gallium/indium/selenium/tellurium) now
  `triangle(15, 60)`, the four sphere ores `uniform(26, 58)`; counts and sizes untouched. Measured
  in-island placement 30% → 81% for the natives against the live End generator. Sphere per-chunk
  spawn chance separately doubled, 0.3 → 0.6.
- **`"all"` biome selector is now live:** `kimberlitic_tuff` (its only user) generates on every
  biome — it was dead code in 1.18.2 and in every 1.21 build before this one.
- **Six armor enchantments** (Dune Walker, Snow Drifter, Speed Skater, Swift Swimmer, Gas Protection,
  Aqua Lense) carry an explicit empty `primary_items` list. Gear behavior is unchanged — still never
  table-offerable on an item — but enchanted **books** of them are offerable again, which 1.18.2
  allowed and the port had silently dropped.
- **The Alloy Block (and its item icon) is tinted for the first time ever** — three stacked causes,
  none of them a 1.21 regression: the model had no tint slot at all (true since 1.18.2), the colour
  handler read data nothing wrote, and the block entity never synced to the client. Colour now also
  survives the four generic ingot↔nugget↔block recipes (a block crafted from furnace ingots keeps its
  colour); the 380 other alloy-crafting recipes that mint named items with their own textures are
  untouched; `steel_block`-style non-alloy blocks are unaffected (asserted in both directions).
- **Crucible / Damascus steel alloying declaring zero element inputs is intentional** — Damascus
  steel is template-gated by design, not a data bug. Flagged here so nobody "fixes" it later.

**From the original port (pre-review, still true):**

- Chunk retrogen state moved from a Forge capability to a NeoForge attachment and is **not
  migrated** → pre-existing chunks run the retrogen replacement pass one more time.
- The Invigorating Totem repairs at half its old rate (the old rate came from a 1.18 double-tick
  bug, also listed below).
- Rankine leaves are now waterloggable, matching vanilla `LeavesBlock`, which forces the property.
- Electromagnet's "is metal" gate is approximated via `SoundType.METAL` (misses netherite/anvils; a
  `rankine:magnet_attractable` tag is the clean fix — see Known Gaps for the fuller writeup).
- Off-hand right-clicks no longer reach a few block-interaction fallbacks, following vanilla's
  `use()` split.
- Advancement custom item predicates are now `ItemSubPredicate`s; their JSON shape changed —
  external datapacks referencing Rankine's predicates need updating.
- Recipe-unlock advancements are grouped under `recipes/misc` (recipe ids themselves unchanged). This
  is the advancement-folder half of a wider issue whose recipe-book-UI half is still open — see Known
  Gaps.
- Cannonball/Enderball post-attack enchantment effects fire only on the victim's side (the projectile
  is ownerless in 1.21).

**Pre-existing 1.18.2 bugs fixed in passing** (not balance changes — plain bugfixes): alloy template
colour (written under one NBT key, read under a differently-cased one, so dyeing a template never
changed its rendered colour), double-firing player tick handlers (a missing phase check), a block
overlay render handler that was registered but never actually invoked, the Ender Amalgam Shovel's
enchantment category matching the Spear's instead of its own. Knockback resistance is **not** in this
list — it reads like a plain bugfix (float written, int read, so it was always zero) but Karl's
decision #1 turned it into a deliberate balance change; see above.

---

## Config caveat — read before regenerating or reporting a "missing" change

Every worldgen fix in batches 2–4 that changed a numeric config row (End vein bands, End sphere spawn
chance, the `"all"` selector) changed only the **default** baked into the mod. An existing
`rankine-common.toml` on disk keeps its old values for every one of those rows — new bands/chances
apply only to a freshly generated config. Delete the file (it regenerates with the new defaults) or
hand-edit the specific rows. This has already cost real time once: in batch 3, a stale dev TOML made
an already-fixed feature look broken during QA. Always regenerate the TOML, and always test on
freshly generated chunks — never a save that predates the change.

---

## Known gaps carried to 1.4.1

### Content gaps (pre-existing 1.18.2 gaps, not port regressions)

- **44 blocks have no loot table** (4 ores + 40 others) — mining them drops nothing. Full breakdown,
  1.18.2-parity proof, and a ready-to-file fix for the ore subset:
  `.porting/reports/TICKET-ores-without-loot-tables.md`. Decision #2: leave for 1.4.0.
- **`piston_crusher` and `gyratory_crusher` have no `BlockItem`** (block-only registration, identical
  to 1.18.2) — uncraftable/unobtainable other than by `/setblock`.

### Unverified-findings pool (`.porting/reports/UNVERIFIED-FINDINGS.md`)

Reported by the 2026-08-09 domain review but never adversarially verified — treat each as a CLAIM,
reproduce against baseline `859f87773` before fixing; some may be wrong or already stale. Three items
were pulled forward and fixed in batch 4 (decision #11 above). **While writing this rewrite, two more
turned out to already be fixed as a side effect of unrelated batch-4 work** and should be struck from
the pool rather than re-verified: the stone-brick-cutout regression (fixed by F21, `3dde0a6be` — the
real count is 1,180 models, not the pool's estimated 1,224; the gap is real, not a rounding error —
F21's own report explains it: no mossy stone-brick pressure-plate variant exists to fill out the
higher number) and its `cast_iron_support_wall_*` cousin (fixed by F38, `4c5d10e9e`). Everything else
in the pool is still open:

- **Recipes/codecs** (low impact — no shipped recipe is currently affected by either): `CrushingRecipe`'s
  tier-ordering replacement gives modded "gold" tier the same rank as wood and silently downgrades an
  unresolvable tier name to wood with no warning. `AlloyingRecipe`'s unfilled-element placeholder now
  resolves to a real element (mercury) instead of nothing, so an add-on datapack with a gap in its
  `total` could get a phantom mercury requirement.
- **Alloy system**: an empty `alloyRecipe` string now parses to the bogus ResourceLocation
  `minecraft:` instead of meaning "no recipe" (medium — latent today, breaks any datapack recipe that
  supplies `alloyComp` without `alloyRecipe`). `AlloyingRecipe#getResultItem()` shows the generic
  fallback name in JEI/GUI until that exact recipe has actually been run once this session (medium,
  affects the 5 `genName: true` recipes). `AlloyIngredientHelper#getItemStack` forces count 1 on some
  paths where 1.18.2 kept a declared count (low). The Alloy Template's inventory tint changed from
  always-white to the real dye colour because two differently-cased NBT keys merged into one
  component (low — arguably a wanted fix, but nobody has explicitly decided that yet, so it isn't
  promoted to Accepted Deltas).
- **Enchantments/combat**: Prying and Puncture now fire on any damage with a living source, not just
  melee, and read only the main hand instead of off-hand/armour too (medium,
  `LivingDamageHandler.java:173`). Endolithic's stone-teleport test narrowed from all of 1.18.2's
  `Material.STONE` to three base-stone tags — cobblestone, stone bricks, ores and mined walls no
  longer trigger it (medium). Antiquated's Luck modifier now leaks onto enchanted books, which
  1.18.2's NBT-only lookup excluded (low). `rankine:enchantable/*` tags are hand-enumerated
  Rankine+vanilla lists, so a third-party mod's pickaxe/shovel/hoe no longer picks up Quake etc. the
  way the old `instanceof` checks did (low). Repulsion/Mercury retarget now cancels the target
  *change* (mob keeps its old target) instead of clearing the target entirely — a consequence of
  `LivingChangeTargetEvent`'s cancel semantics (low).
- **Entities**: `SpearEntity` can load an empty pickup stack via `ItemStack.parseOptional` and then
  throw on the next chunk save, where 1.18.2 degraded silently (low — only on corrupt/legacy NBT).
  Boat bubble-column wobble no longer scales the model — cosmetic-only, and arguably correct, since
  1.18.2's unnormalised-quaternion "bug" is one vanilla 1.21 deliberately preserves too (low).
- **Generated data**: recipe-book category is hardcoded `RecipeCategory.MISC` for every custom
  recipe (461 call sites), moving 109 vanilla-result recipes (stone bricks, ore-conversions, etc.)
  into the Misc tab of the in-game recipe book instead of Building Blocks. This is the same root
  cause as the already-accepted "recipe-unlock advancements grouped under `recipes/misc`" delta above
  — but this half is a real, fixable UI wrongness, not just an advancement-folder cosmetic (low, but
  probably worth doing). All four Rankine damage types (`columns`, `cannonball`, `uranium_shot`,
  `suffocating`) now scale with difficulty where 1.18.2's plain `DamageSource`s did not (low, narrow
  — these are usually player-caused anyway).
- **Test/gate infrastructure itself** (meta — these limit what future gates can prove; not gameplay
  bugs): gate 3 (datagen) has no fail path on non-idempotency despite `verify.sh`'s own comment
  saying it must — a dirty tree after a second `runData` just prints a note today. Gate 4 (gametest)
  never asserts a *minimum* test count, so a GameTest class that silently failed to register would
  still print a passing banner for a smaller N. Gate 5 (server) greps only for recipe parse errors —
  loot-table, advancement and generic-tag load failures use different log strings and are invisible
  to it. `PatchouliJournalContentTest` validates only `rankine:`-namespaced item ids in the journal,
  skipping all `minecraft:` references — the exact failure class ("one bad id blanks the whole book")
  the test exists to catch.

### Batch triage — noted along the way, not fixed, still open

- `assertEmpty`'s GameTest failure message can exceed the 1024-char limit and gets silently
  truncated — a pre-existing helper bug that resurfaced independently in nearly every batch (8+
  sightings). Fix the helper, not each call site.
- Alloy armor lost 1.18.2's crafting-table dyeing (`ArmorDyeRecipe` matched `DyeableLeatherItem`) —
  an undecided gameplay question, not a bug (batch 4, F19/F20 triage).
- Non-living entities (items, boats, minecarts) still use vanilla water physics inside Rankine
  fluids — `FluidType#move` (F33) only intercepts `LivingEntity`.
- `FluidHelper.travel` is dead code both at HEAD and at the 1.18.2 baseline — wire it up or delete
  it.
- Stale `PORT-TODO` comment in `RankineFluids.java:21-42` claims client extensions aren't
  registered — they have been since `3dde0a6be`. Delete the comment.
- `REDSTONE_ORE`'s registration copies `Blocks.LAPIS_ORE`'s properties — a copy-paste bug present
  since 1.18.2 (the loot table itself is fine; this is only the block-properties call).
- Biotite/muscovite/sodium-chloride/pink-salt blocks are `addTranslucent` at HEAD but were
  `addCutoutMipped` in the 1.18.2 baseline — pre-existing, unrelated call sites to the ones F21
  fixed for the similar-looking element-block translucency bug.
- The damage-event cooldown-gate residual needs `lastHurt` (an access-transformed field) to fully
  match 1.18.2 — one documented edge case from the F25/H1 report.
- `PedestalRenderer` render bounds were noted alongside the F29 AABB-inflation family as cosmetic;
  not confirmed actually fixed by that commit — check before assuming.
- `AlloyEnchantmentUtils` passes `registry.holders()` where it should filter through
  `#in_enchanting_table` (review finding 41) — harmless for Antiquated today (the alloy gate still
  restricts it independently), but its wider effect is unaudited.
- The wider modded-dimension gating family is only *partly* closed: F34 (`4c5d10e9e`) covers
  meteorites, fumaroles, wall mushrooms and cobble patches, but **flora and trees are still gated on
  `c:is_overworld`** and were never in scope for decision #9 — don't assume they're fixed.
- Two arrow recipes use JSON key `"name"` where the deserializer reads `"langName"` — a data typo,
  already inert in 1.18.2 (batch 3 triage).
- `AlloyCraftingRecipe.Serializer#toJson` still emits one key letter per grid slot (~4× more verbose
  than necessary) — a pre-existing `PORT-TODO`, functional but verbose.
- The parry/gas HUD overlay redraw (F23) is exact-but-brute-force: 50 full-screen blits per frame to
  reproduce 1.18.2's alpha accumulation, where one blit at the equivalent accumulated alpha would be
  mathematically identical for a uniform-alpha texture. Flagged as a cleanup candidate, not a bug —
  watch for frame hitching if it ever becomes one.

### PORT-TODO markers

`grep -rn "PORT-TODO(" src/main/java | wc -l` → **85** as of `4c5d10e9e` (read-only, safe to re-run
any time). Not every hit is a live gap — several of the ones above are already known-stale (e.g. the
`RankineFluids.java` one). Read the specific comment before assuming it still describes reality.

---

## Release mechanics

- Version lives in `gradle.properties`: `mod_version=1.21.1-1.4.0`. `build.gradle` reads it via
  `version = mod_version`.
- `build.gradle`'s plugin block is `java-library` + `maven-publish` + `net.neoforged.moddev` —
  **no** CurseForge or Modrinth publish plugin. Uploading the built jar to either platform is a
  manual step outside `gradlew build`.
- **NeoForge 26.2 platform assessment: not yet.** 26.2 is beta-only (the API can still break
  build-to-build) and the community's 2026-pack center of mass is 26.1.2 stable, with 1.21.1 still
  the largest installed base. If/when Rankine does move, target 26.1.2 stable and budget it as a
  second port (~60–80% of this one's effort — recipe system and GUI-render-state are both
  architectural rewrites, not renames), not a routine update. Full analysis and sources:
  `.porting/reports/NEOFORGE-26.2-ASSESSMENT.md`.

---

## How to keep verifying

- `bash .porting/verify.sh [gate...]` — `build | test | datagen | gametest | server | all`. Exits
  non-zero on the first failing gate; logs land in `.porting/logs/` (gitignored). Each gate has an
  inline comment explaining exactly what it can and cannot catch — not decorative, see the
  test/gate-infrastructure gaps under Known Gaps above.
- **`.porting/CLIENT-TESTING.md`** — the harness for everything the five headless gates structurally
  cannot see: tints, render layers, GUI layout, item model predicates, JEI layout. Read it before
  driving `runClient` from an agent. Several of its documented traps produce *silent* wrong behavior
  — `pauseOnLostFocus` above all: leave `run/options.txt` at its default and the game pauses the
  instant the automated window loses focus, which looks exactly like a permanently broken Escape key.
- QA lessons from four batches of visual passes (already folded into `CLIENT-TESTING.md`'s own
  checklist; restated here because they're easy to relearn the hard way otherwise):
  - **Confirm identity via NBT or blockstate, never the icon.** A reused world's leftover items, or
    `/give` landing in an unexpected slot, can silently make you test the wrong stack. Check
    `/data get entity @s Inventory[{Slot:Nb}]` before any per-item assertion, and
    `/execute if block <pos> <state> run say ...` for blockstate-dependent checks (fill levels, crop
    ages) — both are unambiguous where a screenshot is not.
  - **Prefer a text probe over a pixel judgement whenever one exists.** Chat output and NBT are
    exact; a screenshot needs correct interpretation, especially at night, from an awkward angle, or
    of a subtle tint difference.
  - **A driving agent must not end its turn to "wait."** Ending a turn mid-check loses the state
    around it. Keep issuing steps within one turn until the check is actually done or genuinely
    blocked on something external.
  - **A transcript may not survive a session reset.** If an agent's context is gone, don't try to
    resume the same conversation from memory — relaunch fresh and brief it to recover from on-disk
    evidence (screenshots already taken, `.porting/CLIENT-TEST-PROGRESS.md`, the WORKSTREAM log).
    This happened for real during batch 3's QA pass and cost nothing, because the on-disk evidence
    was sufficient to resume from cold.
- If any gate fails: check the exception class first
  (`grep -aE "Caused by: java\.lang" .porting/logs/<gate>.log | head`); isolate Rankine's own frames
  with `grep -aoE "com\.cannolicatfish[A-Za-z0-9_.$]*\([A-Za-z]*\.java:[0-9]*\)" <log> | sort -u`.
  Crash reports land in `run/gametestserver/crash-reports/` or `run/crash-reports/` (not
  `run/server/` — see the environment gotchas below for why).

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
- **`.porting/WORKSTREAM.md` — the fix-workstream log and source of truth** for what shipped, which
  commit, which test counts, every decision and every triage note. Read it before trusting any
  summary of the release, including this file's.
- **`.porting/CLIENT-TESTING.md`** — how to drive `runClient` automatically for VISUAL checks, plus
  `.porting/tools/mc-client-driver.ps1`. See "How to keep verifying" above.
- `.porting/reports/PORT-REVIEW-2026-08-09.md` — the 70-agent review verdict, the P0–P3 findings,
  and "Decisions for Karl" (now settled — see Decisions above).
- `.porting/reports/UNVERIFIED-FINDINGS.md` — the findings pool deferred to 1.4.1+ (see Known Gaps).
- `.porting/reports/TICKET-ores-without-loot-tables.md` — issue-ready writeup of the 4 loot-table-less
  ores.
- `.porting/reports/NEOFORGE-26.2-ASSESSMENT.md` — should Rankine chase 26.2? (see Release Mechanics).
- `.porting/reports/*.md` — the original per-area porting reports with cross-package contracts:
  `A1-init-giant`, `A2-init-misc`, `B2-machines`, `C-items`, `D-recipes`, `E-enchantments`,
  `G-events`, `datagen`, `JEI`, `rendertype-spec`.
- `.porting/CLIENT-TEST-PROGRESS.md` — the running visual-check log every QA pass updates.
- `.porting/RELAUNCH_PLAN.md`, `.porting/RESUME_ROSTER.md` — orchestration history and session-recovery
  notes. `.porting/agent-prompts.md`, `.porting/items-agent-brief.md` — the agent prompts used.
- `.porting/logs/` — gate logs and QA screenshots (gitignored).

---

## Bug classes this port hit (watch for more of these if you touch this code again)

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
   Verify by grepping the run log's mod list, never the classpath files. Confirmed still correct:
   JEI (19.21.0.247) and Patchouli (1.21.1-93) both genuinely load as mods in dev runs today.
7. **`grep` finds commented-out registrations — check for an enclosing `/* */` before believing a
   declaration exists.** RankineItems.java has three block-commented registration groups inherited
   from 1.18.X (62 metal lanterns at ~515-580, the 6 `CRUSHING_HEAD_HL*` items, and the dead
   `itemInit()` at ~2090). Both a triage agent and a straight grep concluded the lantern items were
   registered; javac disagreed. They are dead code UPSTREAM (1.18.X has the same comment blocks),
   so leaving them dead is faithful — they are pre-existing content gaps like `piston_crusher`,
   not port regressions.
8. **Item tints are ARGB in 1.21; they were RGB in 1.18 — a zero alpha byte renders NOTHING.**
   `ItemRenderer#renderQuadList` now does `FastColor.ARGB32.alpha(i)` and passes it as the quad's
   alpha. Every plain `0xRRGGBB` tint therefore has alpha 0 and the item is drawn fully transparent:
   present in the inventory, correct data components, no sprite. Found only by looking at a creative
   tab — **no gate catches this**, since gates 1–5 are all headless. Fixed centrally by
   `util/colors/ItemTint#opaque`. **Block tints are still RGB**, so the same colour class looks right
   on a placed block and invisible as an item — that asymmetry is what makes it confusing to spot.
   Vanilla's convention is the tell: its "no tint" constant is `-1` (`0xFFFFFFFF`), not `0xFFFFFF`.
   **There is now a harness for exactly this blind spot — see `.porting/CLIENT-TESTING.md`.** Any
   change touching rendering, colours, models, GUIs or JEI should go through it; a green gate run
   says nothing about those.
9. **"Registration moved from code into a datapack file, and datagen was never taught to emit it."**
   The 1.21 sibling of bug class 1, and the nastiest, because nothing anywhere errors — the feature
   is simply absent. Confirmed instances so far: compostables (1.21 reads
   `neoforge:data_maps/item/compostables`, not `ComposterBlock.COMPOSTABLES`), villager professions
   (`point_of_interest_type/acquirable_job_site`), vanilla enchantability (`minecraft:enchantable/*`
   replaced `EnchantmentCategory`'s `instanceof` checks). When auditing, grep `src/generated/resources`
   for the *presence of the directory*, not for entries within it — an absent directory is the tell,
   e.g. `data/minecraft/tags/item/enchantable/`, `data/*/data_maps/`,
   `data/minecraft/tags/point_of_interest_type/`.

---

## Test assets

**11 unit test files, 46 `@Test` methods** (`src/test/java/com/cannolicatfish/rankine/`):
- `ElementEquationTest` (14) — alloy stat curves, pure math.
- `WeightedCollectionTest` (5) — seeded drop weighting.
- `AlloyEnchantmentGateTest` (5) — the alloy-gate truth table, via the pure
  `IAlloyTool#alloyGateDecision`. Writing this table out is what caught a real bug: a matching alloy
  was bypassing the item-type check, so a pewter-alloyed ALLOY HELMET accepted Antiquated (a
  tool-only enchantment). 1.18.2 required `rs.equals(required) && instanceof IAlloyTool`; the second
  half is the supported-items tag now, and it had been dropped.
- `RenderTypeModelTest` (5) — render-type assignment over the generated model tree; this is the test
  family F21 relies on to pin the 104-element-block and 1,180-stone-brick fixes.
- `AlloyKnockbackResistanceTest` (5) — the 0.1-scale arithmetic (F27).
- `AABBInflationRegressionTest` (4) — mixing barrel / balloon / pedestal box geometry (F29).
- `AlloyArmorTintSourceTest` (2), `AlloyBlockModelTest` (2) — worn-armor and alloy-block tint sources
  (F19/F20).
- `MaterialTestingTableTitleTest` (2), `FluidDescriptionIdTest` (1) — F24 and F22 respectively.
- `PatchouliJournalContentTest` (1) — walks all 74 journal JSONs and asserts every `rankine:` item id
  resolves (see Known Gaps: it does NOT validate `minecraft:` ids, which is a real coverage gap).

**4 GameTest files, 61 `@GameTest` methods** (`src/main/java/com/cannolicatfish/rankine/gametest/`):
- `RankineRegistryTests` (47) — the bulk of the suite. Baseline registry/datapack integrity
  (block↔item wiring, recipe types loading real recipes, enchantments/damage types/fluids parsing,
  loot tables existing, creative tabs, data components) plus nearly every feature-specific regression
  net added since the review: enchantability and table-pool membership, compostables, POI job sites,
  farmland hydration, every worldgen category selector (End/none/icy/beach/dimension-wide), End vein
  placement and runtime replacement, fluid physical properties and movement, tree-stump tagging,
  reactive/explosive drop mode, biome-resolution reset.
- `RankineAlloyTests` (7) — the alloy system specifically: tier-3 rod gating, packaged-item recipe
  resolution, AlloyBlock place/break/sync/tint (both the placed-block path and the
  crafted-from-ingots path).
- `RankineCombatTests` (3) — damage-event/absorption ordering, parry i-frames, alloy-arrow
  enchantments.
- `RankineMachineTests` (4) — **highest-value runtime coverage carried over from the original
  port**: every machine exposes an `IItemHandler` capability on some usable side, every block entity
  places/ticks/saves without throwing, block-entity types accept their blocks.
- `src/main/resources/data/rankine/structure/empty.nbt` — hand-built 5×5×5 empty GameTest template
  (`@GameTest(template = "empty")`).

The GameTests are deliberately registry-driven rather than pinned to specific machine recipes, so
they stay valid as content details change.

**Two known vacuities, not yet fixed** (from `.porting/reports/UNVERIFIED-FINDINGS.md`; also listed
under Known Gaps above): `creativeTabsPopulated`'s `assertEmpty` call can never fire — the list it
checks is declared but never populated, so the assertion is dead weight and the test's only real
check is a tab count. And the block-entity-binding tests (`blockEntityTypesValid`,
`blockEntityTypesAcceptTheirBlocks`) use an at-least-one-block `anyMatch`, which would stay green
even reverted to the exact bug they were written to catch (all 35 specific alloy blocks unplaceable);
the real backstop for that class of bug is `blockEntitiesTickWithoutCrashing`'s crash catch, not
these two.

---

## For the next hard-to-explain bug

If a change touches rendering/colours/models/GUIs/JEI, headless gates passing means nothing — see
`.porting/CLIENT-TESTING.md`. If a feature silently doesn't work with no error anywhere, check bug
class 9 above (registration moved into an unemitted datapack file) before anything else — it has
been the single most common root cause across all four batches. If in doubt about what 1.18.2
actually did, grep the baseline commit (`859f87773` — the reviews and this file cite it constantly)
rather than trusting memory or a comment; several "fixes" in the unverified pool above turned out to
be reproducing a 1.18.2 bug that nobody had actually confirmed was a bug.
