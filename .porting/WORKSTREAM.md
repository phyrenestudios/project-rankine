# Fix workstream — post-review remediation

Started 2026-08-09. Source of truth for who is doing what on the review fix backlog
(`.porting/reports/PORT-REVIEW-2026-08-09.md`). **Update the table on every state change.**

## Roles

| Role | Model | Responsibilities |
|---|---|---|
| **Orchestrator** | Fable 5 (main session) | Splits work, launches/monitors agents (~3-min heartbeat check-ins), unblocks them, reconciles results, owns this file + final report/changelog updates |
| **Engineer** | Opus 5, high effort | Implements the feature; runs headless verification (build / unit / datagen / gametest gates); commits. Does NOT do in-game visual confirmation |
| **QA** | Sonnet 5, medium effort | In-game visual verification via `.porting/CLIENT-TESTING.md` harness — only for features where headless gates can't prove the player-visible outcome |

Working agreement: engineers run **sequentially** (they share the gradle daemon, the datagen
output tree, and overlapping provider files); QA runs once at the end against a freshly built
client. The dev client must be **closed** while engineers run gates, and **relaunched by QA**
afterwards (a running client tests stale code).

## Features in this batch

| # | Feature | Findings covered | Engineer status | QA status |
|---|---|---|---|---|
| F1 | Vanilla `minecraft:enchantable/*` item tags for all Rankine gear + regression GameTest | P0 #1 (nothing enchantable); also LOW #40 (table-only bans hit the anvil) | **DONE** (`8cf5681fc`) | **PASS** — `/enchant` accepts efficiency 3 on `rankine:steel_pickaxe` and protection 2 on `rankine:alloy_helmet` (NBT-confirmed both times), sharpness 1 on the pickaxe still correctly rejected. Enchanting-table GUI item-placement not visually captured (driver aim/click capped at 2 attempts, both missed); `/enchant` evidence stands in for it. |
| F2 | Compostables `neoforge:data_maps/item/compostables` DataMapProvider | P1 (composting dead) | **DONE** (`4f591c41f`) | **PASS** — composter blockstate went `level=0`→`level=3` after 10 right-clicks with `rankine:balsam_fir_leaves` (proven via `/execute if block ... composter[level=N]`, not just pixels); steel pickaxe negative control left it at `level=3` unchanged. |
| F3 | `PoiTypeTagsProvider` → `acquirable_job_site` (5 professions) | P1 (villagers can't take jobs) | **DONE** (`4f591c41f`) | **PASS** — villager penned next to a `rankine:template_table` claimed it inside ~45s; `/data get entity ... VillagerData` returned `profession: "rankine:metallurgist", level: 1`. Visual apron/clothing change confirmed close-up. Trade-GUI screenshot itself not captured (villager kept moving off the click target during 2 tries); NBT + clothing evidence stands in for it. |
| F4 | Enchantment tags: `tradeable` += endplay/withering_curse/ghastly_regeneration; `on_random_loot` for rankine enchants | P1 (unobtainable enchants) | **DONE** (`4f591c41f`) | not needed (tag JSON content is proof) |
| F5 | `MAINTAINS_FARMLAND` += `#minecraft:crops` (crop hydration) | P2 (crops don't hydrate soil) | **DONE** (`4f591c41f`) | not needed (tag JSON content is proof) |

## Batch 1 status: **COMPLETE** (2026-08-09 ~22:00)

All five features implemented, gate-verified ("All 29 required tests passed") and QA-passed.
Commits `8cf5681fc` (F1) + `4f591c41f` (F2–F5). Queued for a next batch, in priority order:
1. **Karl's call:** add the 7 missing `in_enchanting_table` enchantments (Dune Walker, Snow
   Drifter, Speed Skater, Swift Swimmer, Gas Protection, Aqua Lense, Antiquated) — review P2/P3,
   one-line fix, documented in `RankineEnchantmentTagsProvider`'s javadoc.
2. Review P1 batch 2 (worldgen: `"the_end"` key, icy tag, dimension gating) + fresh-world eyeball.
3. Review P1 batch 3 (alloy correctness: recipe toJson, packaged items, AlloyBlock round-trip,
   fluid entity-interaction props, antimatter decay, model predicates).
Also open, from engineer triage: the SpearItem anvil-allowance decision (review decisions #5).

## Batch 2 (opened 2026-08-17) — worldgen P1 + the approved table-pool decision

Same roles and working agreement as batch 1. Scope = review "P1 batch 2 (worldgen)" plus queue
item 1 (Karl approved the `in_enchanting_table` one-liner on 2026-08-17).

| # | Feature | Findings covered | Engineer status | QA status |
|---|---|---|---|---|
| F6 | Table pool restoration — **scoped down to Antiquated only** during implementation: baseline check refuted the other 6 (1.18.2 `canApplyAtEnchantingTable() == false`, never table-offerable; see log + review decision #6) | P2 "Antiquated absent from the enchanting table pool" + P3 "discoverability filter dropped"; decision approved by Karl, scope corrected by verification | **DONE** (`3f423d0e6`) | not attempted (was optional) — headless GameTest drives the real table path and carries it |
| F7 | `WorldgenUtils.CATEGORY_TAGS`: `"theend"` key → `"the_end"` (config's spelling), `"none"` implemented as a shared not-any-other-category predicate used by both consumers | P1 worldgen: all 8 End ore veins dead ⇒ gallium/indium/selenium/tellurium unobtainable; `"none"` biome row silently skipped | **DONE** (`9f27c95a9`) | in-world **inconclusive** (veins sparse by design — 1.18.2-parity height bands, decision #7); every link proven headlessly by `ed3765213` |
| F8 | `icy` → new `rankine:is_icy` = `[snowy_plains, ice_spikes]`, exact 1.18.2 `BiomeCategory.ICY` (membership read from 1.18.2 vanilla biome JSONs; no `#c:is_icy` union — it would drag in `frozen_peaks` behind a config-ordering invariant) | P1 worldgen: snowy_plains loses all Rankine terrain (soils, stone layers, cryolite ore) | **DONE** (`9f27c95a9`) | **PASS** — surface + stone column pinned by text probes; frozen_peaks negative control held (no icy leak) |
| F9 | `ConfigGatedFeaturesBiomeModifier.biomes` now `Optional` (absent = every biome, exact 1.18.2 semantics); nine modifiers collapsed to three, stale JSONs hand-deleted | P1 worldgen: modded dimensions (e.g. Twilight Forest, 20 config rows) get vanilla stone | **DONE** (`9f27c95a9`) | headless proof primary (all-64-biome GameTest); TF not installed |

Engineer split: **Engineer A** = F6 (isolated provider, quick win, first per queue priority).
**Engineer B** = F7–F9 (one worldgen session — shared files, one datagen run). Sequential, then
one QA pass: fresh-world worldgen eyeball per `.porting/CLIENT-TESTING.md` + the F6 table check.

## Batch 3 (opened 2026-08-19) — alloy correctness P1 + decided items

Same roles and working agreement. Scope = review "P1 batch 3 (alloy correctness)", the beach-tag
triage item from batch 2, and Karl's two decisions (2026-08-19): **#6 = option (b)** (faithful:
`primary_items: []` for the six, join the pool tags) and **#7 = retune** ("veins need to be
findable" — deliberate, approved non-parity). NOT in scope: decision #1 (knockback resistance,
still open), the wider F9 gating family (meteorites/fumaroles/flora/trees — not approved).

| # | Feature | Findings covered | Engineer | Status | QA |
|---|---|---|---|---|---|
| F10 | `AlloyCraftingRecipe.Serializer#toJson`: emit per-key `alloyRecipe` constraints (40 tier-3 tool recipes currently accept any alloy rod instead of steel) | P1 alloy #1 | D | **DONE** (`f7587f689`) | not needed — recipe JSON diff + crafting GameTest (accept steel / reject other rod) |
| F11 | `PackagedToolItem`/`PackagedArmorItem`: resolve recipe ids eagerly (loot-chest gear currently mints with null recipe id — no recipe, blank name, wrong stats) | P1 alloy #2 | D | **DONE** (`f7587f689`) | not attempted (optional) — cold-cache GameTest carries it |
| F12 | `AlloyBlock` place/break round-trip: alloy data through a component that is actually written (currently `CUSTOM_DATA`, never written ⇒ composition wiped) | P1 alloy #3 | D | **DONE** (`f7587f689`) | not attempted (optional) — place/break GameTest carries it |
| F13 | All 16 fluids: entity-interaction props to 1.18.2 behavior (`canSwim/canDrown/canPushEntity(false)`, `pathType(null)`, etc. in the shared helper) | P1 fluids | E | **DONE** (`3f25463f6`) | **PASS** — Air stays max submerged, no bubble bar; pig neither drowns nor floats |
| F14 | `AntimatterBlock.java:49`: `DESTROY` → `DESTROY_WITH_DECAY` (radius-12 blast drops ~7,000 items, ≈12× intended) | P1 misc | E | **DONE** (`3f25463f6`) | not needed — decay measured headlessly (120/120 → 11/120) |
| F15 | `ClientSetup.java:93`: `gas_held`/`building_mode` predicates read the dedicated components, not `CUSTOM_DATA` | P1 client | E | **DONE** (`3f25463f6`) | **PASS** — vacuum full/empty NBT-proven both ways; building-mode icon flips; pick-block asymmetric control returns a fresh mode-2 stack |
| F16 | `rankine:is_beach` tag restoring 1.18.2 BEACH membership incl. `stony_shore` (beach-keyed ore rows currently skip stony shores) | batch-2 triage (a), same family as F8 | E | **DONE** (`3f25463f6`) | not attempted (optional) — tag JSON + sylvinite GameTest carry it |
| F17 | Decision #6(b): `primary_items: []` on the six in `RankineEnchantmentBootstrap`; they join `in_enchanting_table`/`non_treasure` (24→30 each); verify the 1.21 `isPrimaryItem` consumer chain; closes batch-2 triage (a) | P2/P3 remnant + decision #6 | F | **DONE** (`b5a824fa7`) | not needed — gear-unchanged + books-restored both asserted headlessly |
| F18 | Decision #7: retune the 8 End vein config rows so veins land in the island band (y≈40–75); report before/after in-band placement fractions | decision #7 | F | **DONE** (`b5a824fa7`) | **PASS** — fresh end_highlands island, one strip-fill exposed a dense ore field; indium + tellurium pinned by text probe |

Engineer split: **D** = F10–F12 (alloy family, one commit). **E** = F13–F16 (runtime/misc, one
commit). **F** = F17–F18 (the decided items, one commit). Sequential D → E → F, then one QA pass
(predicate flips, fluid feel, End vein findability).

## Batch 4 (opened 2026-08-22) — THE RELEASE BATCH for 1.21.1-1.4.0

Karl's directive: subagents for everything, Sonnet for quick/mechanical items, Opus where
judgment is needed; orchestrator owns definition-of-done. **When this batch completes we tag.**
Scope = review step 5 (P2 client pass) + hygiene + Karl's 2026-08-22 decisions (review report,
"Decisions recorded 2026-08-22") + three unverified-pool items. Engineers sequential (one gradle
daemon); two QA sessions at the end; then `verify.sh all`, changelog, HANDOFF, jar, push.

**Definition of done (every engineer item):** root cause verified against `859f87773` (or the
decision text) · fix · regression test that fails before / passes after, falsification-proven ·
gates build/test/runData/gametest green with churn justified · one commit · report. Orchestrator
checks each report against this before the next launch.

| # | Feature | Source | Eng (model) | Status | QA |
|---|---|---|---|---|---|
| F19 | Worn alloy armor: alloy tint + overlay layer drawn (1.18.2 `DyeableLeatherItem` two-pass render) | P2 #20/21/27 | G1 (Opus) | **DONE** (`d57e198e5`) | required — tint only; overlay PNGs are fully transparent (drew nothing in 1.18.2 either) |
| F20 | Alloy block colour renders + tile client sync (`getUpdateTag`/`getUpdatePacket`) + pick-block returns composed stack | decision 8A; D triage (a)(b) | G1 (Opus) + H1 F20b | **DONE** (`d57e198e5` + `a01d97e0f`) — crafted path: colour inherited for the 4 generic ingot↔nugget↔block recipes only | required — craft from furnace ingots; steel_block negative control |
| F21 | Render types: 107 element blocks solid (#28/47); 1,224 stone-brick models back off unmipped cutout; hand-written models (Cob, Ornament, Tree Tap, Tap Line) get their render_type (#29) | P2 + pool [low] | G2 (Sonnet) | **DONE** (`3dde0a6be`) | required (check 4) |
| F22 | Fluid display names (raw `fluid_type.rankine.*` keys in buckets, tanks, JEI) | P2 #30 | G2 (Sonnet) | **DONE** (`3dde0a6be`) | required (check 5) |
| F23 | Parry / gas HUD overlays drawn per HUD layer again (near-invisible now) | P2 #26 | G2 (Sonnet) | **DONE** (`3dde0a6be`) | required — also watch for frame hitching while the overlay is active (50 blits/frame) |
| F24 | Material Testing Table blank title bar | visual pass | G2 (Sonnet) | **DONE** (`3dde0a6be`) | required |
| F25 | Damage-event mapping: buffs no longer bypass absorption (#25); parry/immunity cancels keep i-frames, no side effects on discarded hits (#24) | P2 | H1 (Opus) | **DONE** (`a01d97e0f`) | parry feel optional |
| F26 | Alloy arrows keep Power/Punch/Flame/Piercing | P2 #31 | H1 (Opus) | **DONE** (`a01d97e0f`) | not needed — measured parity with vanilla arrows |
| F27 | Knockback resistance scaled (decision 1B) | decision | H1 (Opus) | **DONE** (`a01d97e0f`) — scale 0.1: brass set 0.10, max (90% netherite) 0.40 | `/attribute` check optional |
| F28 | Spear + Totem of Infusing: enchanting table offers restored AND anvil kept (decision 3B) | decision + pool [medium] | H1 (Opus) | **DONE** (`a01d97e0f`) — spear table half REFUTED (already offered); totem can't enter a table at all (no durability, 1.18.2 too); anvil/book path live for both | not needed |
| F29 | AABB inflation family: mixing barrel (#19), BalloonEntity launch/lift (pool "8× too large"), PedestalRenderer bounds | P2 + pool | H2 (Sonnet) | **DONE** (`9ebb92dd6`) — 6× not 8× (pool headline refuted, same code shape as #19) | barrel/balloon beside-vs-on optional |
| F30 | Tree-chop no longer stumps the bottom log of grass-grown trees | P2 #23 | H2 (Sonnet) | **DONE** (`9ebb92dd6`) — new `rankine:stump_bases` tag = 1.18.2 `Material.DIRT/SAND` set | stump case optional |
| F31 | Reactive-item (alkali metal in water) explosion drop mode — verify vs baseline, fix if inverted | pool [medium] | H2 (Sonnet) | **DONE** (`9ebb92dd6`) — CONFIRMED; reactive items + 3 explosive blocks back to full drops (1.18.2 `BREAK`) | not needed |
| F32 | `ensureBiomesResolved` latch: re-resolve per world/server start | pool [medium] | H2 (Sonnet) | **DONE** (`9ebb92dd6`) — CONFIRMED; `resetBiomeResolution()` clears latch + all 14 lists atomically per server start | not needed |
| F33 | Per-fluid realistic movement (viscosity/density-driven `FluidType#move`; maple sap slows) | decision 6 | I (Opus) | **DONE** (`6bfde63dd`) — `RankineFluidType#move`, vanilla water-travel math parametrised by viscosity/density; 2 author decisions flagged (maple_sap thin, CS2 viscosity corrected) | required — resin / liquid_mercury / carbon_disulfide + exit check |
| F34 | Modded-dimension coverage for meteorites / fumaroles / wall mushrooms / cobble patches (`!nether && !end` semantics) | decision 7A | J (Sonnet) | **DONE** (`4c5d10e9e`) — `exclude` field on the modifier; `the_void` was also missing them | not needed (GameTest) |
| F35 | End sphere ore spawn chance 0.3 → 0.6 (4 config rows) | decision 10 | J (Sonnet) | **DONE** (`4c5d10e9e`) — 0.3 → 0.6, pinned in the band test | not needed |
| F36 | `"all"` biome selector implemented (kimberlitic tuff row generates) | decision 12 | J (Sonnet) | **DONE** (`4c5d10e9e`) — `CATEGORY_ALL`; End biomes now carry 9 veins (derivation updated) | not needed (GameTest) |
| F37 | `.porting/reports/TICKET-ores-without-loot-tables.md` — issue-ready list of ore blocks that drop nothing | decision 4A | doc agent (Sonnet), ran in parallel with G1 | **DONE** (file written, uncommitted until the artifacts commit) | n/a |
| F38 | `cast_iron_support_wall_*` models back off `cutout_mipped` (baseline never listed the wall) | G2 triage | J (Sonnet) | **DONE** (`4c5d10e9e`) | not needed (JSON pinned) |
| H1 | HANDOFF.md rewritten to release state | hygiene | Sonnet | **DONE** (uncommitted until the artifacts commit) — 353→638 lines; 6 source contradictions reconciled (trusted the derivable numbers: 104 element blocks, 1,180 stone-brick models, four network fixes); 2 more unverified-pool items marked already-fixed (F21, F38) | n/a |
| H2 | CHANGELOG: fix the false "identical JSON" line (#34), batch-4 entries, balance/known-change notes | hygiene | orchestrator | **DONE** (uncommitted until the artifacts commit) — 13 review-fix entries, 4 pre-existing-bug fixes, deliberate-change notes, counts 46/61 | n/a |
| H3 | `verify.sh all` — all FIVE gates on the release HEAD (server smoke last ran 2026-08-02) | hygiene | orchestrator | **DONE** — `verify.sh all` on `4c5d10e9e`: build / test / datagen / gametest / server smoke all PASS (`.porting/logs/verify-release-4c5d10e9e.log`) | n/a |
| H4 | Release jar (`gradlew build`) + release-notes excerpt; push | hygiene | orchestrator | pending | n/a |
| QA-1 | Rendering checklist: checks 1 (MTT), 2 (armor worn+inventory), 4, 5, 6 (JEI), 9 deep pages, alloy block colour, HUD overlays | visual | Sonnet | pending | — |
| QA-2 | Mechanics: fluid feel per fluid, spear/totem at table, anvil (check 7), arrows | visual | Sonnet | pending | — |

Order: G1 → G2 → H1 → H2 → I → J → H1(doc) → H3 → QA-1 → QA-2 → H2 → H4.

## Log

- 2026-08-09 ~19:55 — workstream opened; dev client killed; Engineer A launched on F1.
- 2026-08-09 ~20:30 — **F1 DONE**, commit `8cf5681fc`. `RankineItemTagsProvider.addVanillaEnchantableTags()`
  now populates 14 `minecraft:enchantable/*` tags by re-stating each 1.18.2 `EnchantmentCategory`
  predicate as a rule over the item registry (durability/vanishing 267 items, mining/mining_loot 133,
  sword/fire_aspect 43, sharp_weapon/weapon 65, armor/equippable + 4 slot tags 22). Axes get
  Sharpness/Smite/Bane but no sword-primary enchantments; bow/crossbow/trident/fishing/mace stay
  empty because 1.18.2 matched nothing there (spears keep durability only, not trident). Also moved
  the Hammer/Crowbar/Knife bans from `supportsEnchantment` to `isPrimaryItemFor` — review LOW #40,
  latent while the tags were empty and made live by this fix. Two new GameTests
  (`rankineGearIsEnchantable`, `enchantingTableBansDoNotBlockBooks`), both proven non-vacuous by
  falsification runs. Gates: `build -x test` SUCCESSFUL (14m32s), `test` SUCCESSFUL (25/25),
  `runGameTestServer` "All 25 required tests passed" (was 23 before). `runData` rewrote exactly 14
  files, all the new tags — no collateral churn. **Not verified headlessly, for QA:** that the table
  actually *offers* the enchantments (asserted `isPrimaryItemFor`, not the `EnchantmentMenu` roll),
  and per-enchantment level/cost. **Left alone, flag for triage:** `SpearItem.supportsEnchantment`
  force-allows Impaling/Loyalty on the *anvil* too, where 1.18.2 allowed them at the table only —
  pre-existing, unchanged by this commit, and the mirror image of #40.
- 2026-08-09 ~21:10 — **F2–F5 DONE**, one commit `4f591c41f`. All four are the same bug class
  (registration that was code in 1.18 is a datapack file in 1.21). Two new providers
  (`RankineDataMapProvider`, `RankinePoiTypeTagsProvider`) wired into `DataGenerators`.
  - **F2 compostables — 108 entries**, chances byte-identical to the 1.18.2 list (verified against
    the baseline call sites line by line: 19 tree woods × leaves/sapling/leaf-litter, 6 vanilla-wood
    leaf litters, 9 tall flowers, 13 seeds, 16 berries/foods, 6 clovers/nettle/compost, grass,
    pineapple sleeves). Driven from `VanillaIntegration.compostables()`, a **static builder both
    the game and datagen call** — `init()` runs on `FMLLoadCompleteEvent` so a field would have
    been ordering-dependent; a method has no such problem. Dead `ComposterBlock.COMPOSTABLES`
    writes **deleted** (NeoForge only reads it in its own datagen). `canVillagerCompost=false`
    everywhere — 1.18.2's `WorkAtComposter.COMPOSTABLE_ITEMS` was a hard-coded 2-item list no mod
    could extend, so parity is false. Also now filters `Items.AIR` (a BlockItem-less block would
    otherwise have made **air** compostable — 1.18 tolerated that junk map entry, a data map
    would not).
  - **F3 job sites — 5 POIs** in `acquirable_job_site`. `minecraft:village` deliberately NOT
    touched: vanilla's `village.json` is `["#minecraft:acquirable_job_site", home, meeting]`, so
    they join it transitively (asserted in the GameTest). `super.addTags` deliberately not called
    — it would copy the 13 vanilla job sites into Rankine's file.
  - **F4 enchantments — the review's framing was half right, and the real bug was bigger.**
    Read all 44 enchantment classes at `859f87773`: **not one overrides `isTreasureOnly()`,
    `isTradeable()` or `isAllowedOnBooks()`.** The only overridden flags are `isDiscoverable()`
    (false on 14: the 11 ender-amalgam ones + Ghastly Regeneration + Shape Memory + Withering
    Curse) and `isCurse()` (Withering Curse). HEAD used `minecraft:treasure` as a proxy for "keep
    it out of the enchanting table", which is a different flag — and since vanilla's
    `tradeable.json` is `["#minecraft:non_treasure", …]`, that silently barred **21** enchantments
    from villager book trades and doubled the price of the rest.
    Also **partly refutes the review's "`on_random_loot` is never generated" finding**: the tag
    file was absent, but vanilla's `on_random_loot.json` is `["#minecraft:non_treasure", …]`, so
    the 23 non-treasure Rankine enchantments were already in it transitively. The 21 in `treasure`
    were the ones actually excluded.
    Result: `tradeable` 23→**44**, `on_random_loot` / `on_mob_spawn_equipment` /
    `on_traded_equipment` **30** each (= the `isDiscoverable()` set), `treasure` **removed**
    (stale generated file deleted by hand — providers don't clean up), `non_treasure` and
    `in_enchanting_table` **unchanged at 23**. The 14 non-discoverable ones are tradeable but in
    no random-selection tag, so the curse is exactly as available as in 1.18 and no more.
  - **F5 farmland** — `maintains_farmland` = `["#minecraft:crops"]`. 1.18.2's `hasCrops` was an
    `IPlantable` check and Forge made every `BushBlock` IPlantable; 1.21 reads the tag from both
    `TilledSoilBlock#hasCrops` and vanilla `FarmBlock`, and vanilla's copy lists vanilla crops
    only. Adding the tag contributes exactly the 23 Rankine crops (vanilla's 8 are already there).
  - Four new GameTests, **all four proven non-vacuous** by inverting each assertion in one run and
    checking the counts (108 / 5 / 26 / 12). `compostablesDataMapMatchesTheModsList` reads the data
    map off the live registry holder and compares to `VanillaIntegration.compostables()`, so it
    fails both if the provider is dropped and if the generated JSON goes stale.
  - Gates: `build -x test` SUCCESSFUL (7m45s), `test` SUCCESSFUL, `runData` (8m18s) wrote exactly
    7 files and no collateral churn, `runGameTestServer` "All 29 required tests passed" (was 25).
  - **For QA (visual):** composter fills from Rankine leaves/saplings/seeds; an unemployed villager
    actually claims a Template Table / Material Testing Table / Botanist Station / Fusion Furnace /
    Sediment Fan and shows the profession + trades; tilled soil under a grown Rankine crop stays
    dark past a few random ticks with no water in range. None of that is provable headlessly.
  - **Left alone, flag for triage (all out of F2–F5 scope, noted not fixed):**
    1. **7 enchantments are still missing from `in_enchanting_table`** — Dune Walker, Snow Drifter,
       Speed Skater, Swift Swimmer, Gas Protection, Aqua Lense and **Antiquated** all had
       `isDiscoverable() == true` in 1.18.2, so the table could offer them. They sit in this pack's
       `OFF_TABLE_ENCHANTMENTS` list. This is exactly review P2 "Antiquated absent from the
       enchanting table pool" + P3 "discoverability filter dropped"; moving them to
       `NON_TREASURE_ENCHANTMENTS` is a one-line fix but changes the table pool that F1's QA pass
       is about to verify, so it was left for its own owner. They ARE in the loot/trade tags now,
       which is what 1.18.2 did.
    2. `assertEmpty`'s failure message can exceed the GameTest 1024-char limit and gets truncated
       with a warning (seen only during the falsification run) — cosmetic, pre-existing helper.
- 2026-08-09 ~22:00 — **QA visual pass on F1/F2/F3, all PASS.** Built + launched a fresh client
  against `8cf5681fc`+`4f591c41f`, world "New World" (8/9/26 save), clean platform at
  `30,72,-300`ish. Trap hit early: `/give`'d items landed in main inventory instead of the
  expected hotbar slot because the hotbar already held leftover enchanted items from a prior
  session (a `minecraft:wooden_hoe` w/ efficiency 3 sitting in slot 0) — silently tested the wrong
  item until caught via `/data get entity @s Inventory[{Slot:Nb}]`. Fix: `/clear @s` before every
  give, then confirm the item **id** via NBT before acting on it, not just the icon. Recommend
  future QA sessions do the same rather than trusting `/give` + hotbar-select blindly on a
  reused world.
  - **F1**: `/enchant @s minecraft:efficiency 3` on a freshly-clean `rankine:steel_pickaxe` and
    `/enchant @s minecraft:protection 2` on `rankine:alloy_helmet` both succeeded — confirmed via
    `/data get entity @s Inventory[{Slot:Nb}]` showing `minecraft:enchantments` on the correct
    `id`. Negative control held: `/enchant @s minecraft:sharpness 1` on the pickaxe failed with
    "Tool Steel Pickaxe cannot support that enchantment" (display name confirms it's the right
    item, not a fallback). Enchanting-table GUI secondary check not captured — driver's click
    kept landing on the recessed floor tile beside the table instead of the table itself two
    attempts running (aim math for a sub-height block sunk into a flat platform is fiddly); capped
    at 2 attempts per plan, `/enchant` evidence carries F1.
  - **F2**: composter placed at `30,72,-302`. `/data get entity`-style NBT doesn't cover
    blockstate, so verified fill level with `/execute if block 30 72 -302
    minecraft:composter[level=N] run say LEVEL_IS_N` — went from level 0 to **level 3** after 10
    right-clicks with `rankine:balsam_fir_leaves` (creative mode doesn't decrement the held stack,
    so item count was not usable as a signal — the blockstate check was necessary, not optional).
    Negative control: 3 right-clicks with the steel pickaxe left it at level 3, unchanged.
  - **F3**: glass pen at `x:24-28,y:72-75,z:-297..-293` around a `rankine:template_table`,
    villager summoned inside. Claimed the job site within ~45s:
    `/data get entity @e[type=minecraft:villager,limit=1] VillagerData` returned
    `{profession: "rankine:metallurgist", level: 1, type: "minecraft:plains"}`, and a close-up
    shot shows the profession apron replacing the plain robe. Trade-GUI screenshot itself missed
    twice (villager kept stepping off the aimed point between screenshot and click — the pen is
    too roomy to pin them still); NBT profession + clothing change carries F3.
  - Evidence copied to `.porting/logs/visual/` with `qa_` prefix (30+ screenshots); key ones:
    `qa_f1_clean_pickaxe_enchant_nbt.png`, `qa_f1_clean_helmet_enchant_nbt.png`,
    `qa_f1_clean_negative_chat.png`, `qa_f2_level_result.png`, `qa_f2_negative_control.png`,
    `qa_f3_profession_nbt_45s.png`, `qa_f3_villager_pos_nbt.png`.
  - `.porting/CLIENT-TEST-PROGRESS.md` check 7 updated to reflect the fix.
- 2026-08-17 — **Batch 2 opened.** Karl approved the queued `in_enchanting_table` item.
  Scope = F6 (table pool) + F7–F9 (review P1 worldgen batch 2). Confirmed no java/client
  process running; working tree clean at `4f45aac0b`. Engineer A (Opus) launched on F6.
- 2026-08-17 — **F6 DONE, scoped down to Antiquated only**, commit `3f423d0e6`. Engineer A
  re-verified the premise against all 44 baseline enchantment classes at `859f87773` and
  **refuted 6 of the 7**: Dune Walker, Snow Drifter, Speed Skater, Swift Swimmer, Gas Protection
  and Aqua Lense all override `canApplyAtEnchantingTable()` to an unconditional `false` — the
  1.18.2 table filter (`!treasure && discoverable && canApplyAtEnchantingTable(stack)`) could
  never offer them on ANY stack, so adding them to `in_enchanting_table` would exceed baseline
  availability, not restore it. (The batch-1 triage note had checked `isDiscoverable` only;
  review finding 22 names only Antiquated, corroborating.) Antiquated's per-stack gate is already
  ported (`IAlloyTool#alloyGateAllows`), so it needed exactly the tag membership it now has.
  - Tags: `in_enchanting_table` 23→24, `non_treasure` 23→24 (both `+rankine:antiquated`);
    everything else byte-identical sets (4 files churned on provider iteration order only).
  - Gates: `build -x test` 7m11s, `test` 25/25, `runData` (6 files, all accounted), 
    `runGameTestServer` "All 29 required tests passed". The extended GameTest now drives the
    real table path (`EnchantmentHelper.getAvailableEnchantmentResults` over the live
    `#in_enchanting_table` holder set): Antiquated offered at level 5 on pewter-alloyed gear,
    refused on rose-gold-alloyed and non-alloyed; Dune Walker/Aqua Lense negatives pinned at a
    cost inside their own level windows. Falsification: inverting every new assertion produced
    exactly the 32 predicted mismatches; restored file sha256-matched the passing version.
  - **Re-queued as review decision #6** (added to PORT-REVIEW "Decisions for Karl"): if the
    "all 7" shape is still wanted, the faithful mechanism is `primary_items: []` on the six in
    `RankineEnchantmentBootstrap` — zero behavior change, and it also fixes triage item (a) below.
  - **Engineer A triage (not fixed):** (a) batch 1's `on_mob_spawn_equipment`/`on_traded_equipment`
    membership for the six may exceed baseline — 1.18.2's mob-equip/villager-gear paths went
    through `getAvailableEnchantmentResults` and so required `canApplyAtEnchantingTable` too;
    1.21 consumer chain unverified. (b) open finding 41 (`AlloyEnchantmentUtils` passes
    `registry.holders()` not `#in_enchanting_table`) now interacts with this commit — harmless
    for Antiquated (alloy gate still restricts), but its owner should know. (c) stale comments in
    `RankineEnchantmentBootstrap.java` (:61-64, :72, :136-137) claim the pewter/nitinol gating
    "is not expressible" and mislabel the six as "treasure-only". (d) `assertEmpty` >1024-char
    truncation, again.
  - **QA note:** the F6 check is Antiquated ONLY, and needs a properly *alloyed* pewter tool —
    a bare `/give`n pewter sword has no alloy component and is *correctly* refused.
- 2026-08-17 — Engineer B (Opus) launched on F7–F9 (worldgen), client confirmed closed.
- 2026-08-17 — **F7–F9 DONE**, one commit `9f27c95a9`.
  - **F7**: key fixed to `"the_end"` — baseline round-trip (`BiomeCategory.THEEND.getName()` write,
    `byName()` read) defines the spelling; no alias kept for `"theend"` (`byName("theend")` was null
    in 1.18.2 too). `"none"` = "matches no other category tag", factored into a shared
    `WorldgenUtils.matchesCategory` used by BOTH consumers so they can't drift; `the_void`'s
    1.18.2 category verified as literally `"none"`.
  - **F8**: `rankine:is_icy` = `[snowy_plains, ice_spikes]`, verified per-biome against 1.18.2
    vanilla biome JSONs (frozen_peaks/snowy_slopes/stony_peaks were "mountain", snowy_taiga
    "taiga", snowy_beach + stony_shore "beach", frozen_river "river", grove "forest"). Union with
    `#c:is_icy` rejected: it adds frozen_peaks, harmless only while the mountain row precedes the
    icy row in the config — a silent ordering trap.
  - **F9**: option (a) — `ConfigGatedFeaturesBiomeModifier.biomes` is `Optional`, absent = all
    biomes (1.18.2 attached these three to every biome, verified at `FeatureGeneration.java:47-62`;
    no dimension test existed). 9 modifiers → 3; 9 stale generated JSONs hand-deleted. Union tag
    rejected: can't name mods that aren't installed. Safety: WorldReplacer/Intrusion no-op on
    GEN_BIOMES index −1 anywhere; FlatBedrock has no such guard but is parity (universal in
    1.18.2, `flatBedrockLayers` defaults 0).
  - Gates: `build -x test` 6m15s, `test` 25/25, `runData` 29s (churn fully justified: 3 collapsed
    modifier JSONs with no `biomes` field, 1 new icy tag, 9 deletions, zero collateral),
    `runGameTestServer` **"All 33 required tests passed"** (was 29). Falsification: all 16 new
    assertion sites inverted in one run; the 4 new tests failed with exactly the pre-predicted
    mismatch counts (13 / 4 / 7 / 133), other 29 stayed green; restored file sha256-identical.
  - Stale `PORT-TODO(events)` javadoc removed — `ensureBiomesResolved` was already wired at
    `RankineEventHandler.java:70`.
  - QA (Sonnet) launched afterwards: fresh-world eyeball (snowy_plains + frozen_peaks negative
    control + End veins + general sanity; F6 Antiquated optional, capped).
- 2026-08-17 — **QA fresh-world pass done** (save `New World (3)`, 86 screenshots `qa2_*` in
  `.porting/logs/visual/`, CLIENT-TEST-PROGRESS.md updated, client killed).
  - **F8 PASS**: snowy_plains at (3687,~,3563) — surface pinned `rankine:silty_loam_grass_block`,
    stone column pinned `rankine:chalk` + observed `rankine:andesite`, all via `/execute if block`
    text probes. **Negative control PASS**: frozen_peaks at (4167,~,3851) pinned
    `rankine:hornblende_andesite` (mountain geology) and confirmed *absence* of whiteschist —
    no icy leak.
  - **F7 INCONCLUSIVE in-world** (honest "could not confirm", not a FAIL): ~25 vantage points +
    two grid scans (75+144 probes) + a 30-point direct-ID column scan found no target veins.
    Orchestrator initially blamed scan blindness (claimed Rankine ores lack parent `#c:ores`
    membership) — **that root-cause was WRONG, refuted by Engineer C**: `c:ores.json` exists at
    HEAD (block `RankineBlockTagsProvider.java:612`, item copy at `RankineItemTagsProvider.java:245`)
    and is byte-identical to the baseline's `forge:ores` modulo namespace; the orchestrator's
    file-existence check had silently failed on a MSYS→Windows path translation. QA's `#c:ores`
    scans were valid probes that genuinely found nothing — see the sparsity explanation in the
    Engineer C entry. Data links verified good: `c:base_stone_end` (the `rankine_ore_replaceables`
    End component) DOES contain `minecraft:end_stone`, and the selection logic is GameTest-proven.
    Remaining unproven link = the vein feature actually executing/replacing at runtime →
    Engineer C launched to close it headlessly.
  - **F6 not attempted** (optional; headless GameTest drives the real table path and carries it).
  - QA loose end: unidentified `#c:ores` block near a meteorite at (1482,30,30) in
    `New World (3)` — 16 candidate IDs checked, none matched.
- 2026-08-17 — Engineer C (Opus) launched: F7 runtime-proof GameTests + check whether 1.18.2
  had ores in the parent `forge:ores` tag (possible dropped-membership regression, same family
  as the review's Family B).
- 2026-08-17 — **Engineer C DONE**, commit `ed3765213`. **F7 verdict: End veins demonstrably
  work at runtime** — every link now independently proven headlessly.
  - `endBiomesCarryExactlyTheEightEndOreVeins`: all 5 End biomes carry exactly the 8 config
    End-ore features at UNDERGROUND_ORES, as an ordered-list equality over the live modified
    generation settings (derivation over all 75 active config rows documented in the test);
    vacuity guards both ways (end_highlands lacks hematite, plains HAS hematite and lacks the
    6 End-exclusive ores).
  - `endOreVeinFeatureReplacesEndStone`: runs the real `DEFAULT_ORE_VEIN` feature with the real
    `TagMatchTest(RANKINE_ORE_REPLACEABLES)` over a 5×5×5 end_stone region, seed 1004 (chosen by
    offline simulation of the vein arithmetic; predicted 5 placements, observed 5, 120 end_stone
    intact). Negative control over end_stone_bricks (identical everything, differs only in tag
    membership): 0 placed, 125/125 intact.
  - Falsification: 12 sites inverted in one run, predicted 21+7 mismatches, observed exactly
    21+7, restored file sha256-identical, re-ran green. Gates: build 12s, test 25/25, runData
    **zero churn**, `runGameTestServer` **"All 35 required tests passed"** (was 33).
  - **Task-2 premise refuted (no fix needed):** parent `c:ores` block+item tags exist at HEAD
    and diff byte-identical to baseline `859f87773`'s `forge:ores` (normalizing namespace and
    the 1.18-only `"replace": false`). Exact parity; nothing to fix or improve.
  - **Why QA saw nothing (triage → review decision #7):** the 4 sphere End rows (y 10–40
    uniform) target a band that is mostly void in the End; the 4 triangle rows waste ~35% of
    attempts below the dimension floor and land only ~12% in the island band. Baseline config
    numbers are **identical** — 1.18.2 parity, a content/tuning decision, not a port bug.
  - Other triage: `assertEmpty` >1024-char truncation, 4th sighting (2679-char message);
    pre-existing deprecation notes in `RankineRegistryTests.java` (`registryOrThrow` etc.).
- 2026-08-19 — **Batch 3 opened.** Karl: "Complete batch 3", decision #6 → faithful (option b),
  decision #7 → retune for findability. Client confirmed closed; HEAD `102336f6e`.
  Engineer D (Opus) launched on F10–F12 (alloy). (One transient API-error restart mid-run;
  resumed with context intact, reconciled the tree via git status before continuing.)
- 2026-08-19 — **F10–F12 DONE**, one commit `f7587f689`. Gates: build/test/runData green,
  `runGameTestServer` **"All 38 required tests passed"** (was 35). Falsification: pre-fix run
  predicted 10/crash/4, observed exactly that; inversion run predicted 15/1025/5 mismatches,
  observed exactly that; restored file sha256-matched.
  - **F10** — root cause one layer off the review's framing: the datagen *builder* emitted the
    constraint; the *encode* path (`Serializer#toJson` → `Ingredient.CODEC`) dropped every data
    component. New `AlloyIngredientHelper.alloyIngredientToJson` (reads `getValues()`, not
    `getItems()` — tag expansion is unavailable during datagen); throws rather than silently
    writing an unconstrained key. Churn = 136 files, exactly reconciling HANDOFF's 136 (all
    per-key data) vs the review's 40 (the `alloyRecipe` gameplay subset; the other 96 armor
    recipes lost only `langName`+`color` display data).
  - **F11** — `.map(RecipeHolder::value)` discarded the recipe id (lazily filled only after a
    smelt). Fixed by keeping the holder; safe on dedicated servers (RecipeManager holds ids from
    datapack load, no caches/ordering). **Scope taken, flagged:** the same three lines had a live
    crash — random draw bounded by the unfiltered list while 2 of 49 alloys (crucible/damascus
    steel, zero element inputs) filter to a shorter one ⇒ ~4% of packaged-item uses threw
    IndexOutOfBounds on the server thread. Baseline-identical (inherited), but the id fix is
    worthless while 4% of uses crash; bounded by the filtered list + empty guard.
  - **F12** — narrower than reviewed: only the *break* half was broken (1.21 `BlockItem#place`
    already applies stack components to the tile). `playerWillDestroy`/`getCloneItemStack` now
    use `saveToItem`; dead `AlloyBlockTile.alloyData` deleted (nothing ever wrote it).
  - **Engineer D triage (not fixed):** (a) `AlloyBlockColor` reads never-written persistent data
    ⇒ alloy blocks always render untinted — baseline parity, but post-F12 a `collectComponents()`
    read would make tint work for the first time (needs client sync, see b); (b) `AlloyBlockTile`
    lacks `getUpdateTag`/`getUpdatePacket` ⇒ client never has alloy data, pick-block returns a
    bare stack — parity; (c) two arrow recipes use `"name"` where the deserializer reads
    `"langName"` — data typo, already ignored in 1.18.2; (d) `assertEmpty` 1024-char truncation,
    5th sighting; (e) `toJson` still emits one key letter per slot (~4× verbosity, existing
    PORT-TODO); (f) crucible/damascus steel alloying declare zero element inputs — content
    question for the mod author.
- 2026-08-19 — Engineer E (Opus) launched on F13–F16 (fluids, antimatter, predicates, beach tag).
- 2026-08-19 — **F13–F16 DONE**, one commit `3f25463f6`. Gates green; `runGameTestServer`
  **"All 43 required tests passed"** (was 38); runData churn = exactly the new
  `rankine:is_beach` tag. Falsification: 47 sites inverted, predicted 402/3/4/241/18 across the
  five new tests, observed exactly that, sha256-verified restore.
  - **F13** — review framing corrected: 1.18.2 `FluidAttributes` carried NO entity behavior;
    vanilla gated drown/swim/push on water/lava tags Rankine fluids were never in, so baseline
    outcome was "nothing happens". Six properties set, each pinned to a named 1.21 consumer
    (canDrown/canPushEntity/canSwim false, fallDistanceModifier 1.0 — the 0.5 default silently
    halved fall damage — pathType/adjacentPathType null); seven others verified already-parity
    and asserted anyway. All 16 fluids uniform (none lava-like). Behavioral GameTest: pig sealed
    in maple sap 40 ticks, air stays exactly 100, with an eye-in-fluid vacuity guard.
    **Known residual, flagged for QA and triage:** water-style buoyancy/drag inside the fluid is
    hardcoded in `LivingEntity#travel` for every non-lava fluid — not reachable from Properties;
    needs a FluidType#move override (triage).
  - **F14** — antimatter `DESTROY` → `DESTROY_WITH_DECAY`; decisive evidence in-repo (baseline
    `SphericalExplosion:149` gated `EXPLOSION_RADIUS` decay on exactly the mode antimatter
    passed). Empirically measured through the real `onExplosionHit`: 120/120 stone drops before,
    11/120 after — the review's ≈12× confirmed.
  - **F15** — both predicates now read the components gameplay writes (write/read file:line
    pairs in the engineer report). **Scope taken, flagged:** `BuildingModeBlock#getCloneItemStack`
    was still WRITING `CUSTOM_DATA` — pick-block returned mode 1 and re-placed the wrong
    variant; fixed as the write side of the same pair.
  - **F16** — `rankine:is_beach` = `[beach, snowy_beach, stony_shore]`, re-verified against
    1.18.2 biome JSONs; corroborated by the mod author's own 1.19 branch doing the same union.
    Negative-control lesson: pentlandite/phosphorite have second rows elsewhere — sylvinite is
    the only shore-exclusive control (the cold gate run caught the bad first pick).
    The brief's predicted 4→3 "none"-test update was wrong — that test asserts rows, not
    counts; no change needed (deviation reported honestly).
  - **Engineer E triage:** (a) the buoyancy residual above; (b) BlastingPowder/GunCotton/
    UnamedExplosive used 1.18.2 `BREAK` → HEAD `ExplosionInteraction.BLOCK` = decay — whether
    that drifts depends on which mode 1.18.2 vanilla gated decay on; unresolved in-repo, needs
    one 1.18.2 `Explosion.java` lookup; (c) `FluidHelper.travel` is dead code at HEAD AND
    baseline — wire or delete; (d) `getBuildingMode` absent-component fallback differs from
    baseline (1 vs 0) — deliberate, saner, matches the model overrides; (e) `assertEmpty`
    truncation, 6th sighting; (f) `BuildingToolItem`'s `"buildingMode"` NBT not audited.
- 2026-08-19 — Engineer F (Opus) launched on F17–F18 (decisions #6b and #7).
- 2026-08-19 — **F17–F18 DONE**, one commit `b5a824fa7`. Gates green; `runGameTestServer`
  **"All 45 required tests passed"** (was 43); runData churn = exactly 8 files (6 enchantment
  defs + 2 tags), idempotent. Falsification: pre-fix 12/30 predicted=observed; inversion run
  26/90/22 predicted=observed; sha256-verified restore.
  - **F17** — `primary_items: []` mechanism verified end-to-end before use (codec round-trip
    at `HolderSetCodec:97/:68`, both runtime readers spell `primaryItems.isEmpty() ||
    stack.is(...)` — absent-Optional means unrestricted, present-empty means never-primary).
    Tags `in_enchanting_table`/`non_treasure` 24→30; other four availability tags byte-identical.
    Mob-equip consumer chain cited to file:line (`Mob:1158` → … → `getAvailableEnchantmentResults`
    `.filter(isPrimaryItemFor)`), **closing batch-2 triage (a)** — the loot-tag membership is not
    over-generous once primary_items gates it. Stale `RankineEnchantmentBootstrap` comments
    de-staled (batch-2 triage (c) closed). **Deviation, correct on the evidence:** "zero behavior
    change" is not literal — 1.21 tests books BEFORE primary_items, so the six become table-
    offerable on BOOKS again, which is exactly 1.18.2's `isAllowedOnBooks()` behavior that HEAD
    had lost; gear behavior unchanged, both now asserted.
  - **F18** — natives `triangle(-50,70)` → `triangle(15,60)`, spheres `uniform(10,40)` →
    `uniform(26,58)`; counts/sizes/chances untouched. Decision driven by MEASURED End terrain
    (live generator noise over 1152 outer-ring columns: end stone spans y 11–64, nothing above):
    natives in-island hit 0.300 → **0.806** (2.7×), spheres 0.603 → **0.825**; old-band waste
    was 51.4%, not the earlier ~35% estimate. **Two batch-2/C claims refuted:** the sphere band
    was never "mostly void" (its scarcity is `count=1 × chance=0.3`, a content knob, triaged);
    and QA's batch-2 "no veins found" was a sampling artifact — even old bands yielded ~18 vein
    starts per island chunk, so the veins were never missing, just under-sampled. The retune
    stands on its own merits (waste removal + 2.7×). Engineer C's two End tests needed no
    changes (they compare ore identity/order and direct execution, not placement).
  - **Config caveat for changelog:** only DEFAULTS changed — existing `rankine-common.toml`
    files keep old values for EVERY row (worth a one-time migration note); stale dev TOML
    deleted and regenerated so QA's client is correct, but QA must explore FRESH End chunks.
  - **Engineer F triage:** (a) sphere ore scarcity (`count=1 × 0.3`) — content question, not
    touched; (b) `SphericalOreVeinFeature` offset(8,0,8) defeats InSquarePlacement spread —
    baseline parity; (c) `assertEmpty` truncation, 7th sighting.
- 2026-08-19 — QA (Sonnet) launched on the batch-3 visual pass: vacuum + building-mode
  predicate flips (incl. the pick-block leg), fluid feel (buoyancy expected, not a bug), and
  the End vein strip-fill hunt on fresh chunks.
- 2026-08-19 — QA **paused** mid-pass at Karl's request (mid Check 2: the building-mode pick-block
  control test). Agent context retained for resume.
- 2026-08-20 — QA resume attempted on the same agent; its transcript had not survived the
  session reset, so a **fresh QA agent** was launched instead, briefed to recover from disk:
  72 pre-pause screenshots (`qa3_00`–`qa3_72`, save `New World (4)`) cover Check 1 (vacuum)
  and most of Check 2 (building mode, three pick-block shots); it re-derives verdicts from
  conclusive images, re-runs what isn't, then finishes the pick-block control, fluid feel,
  the End vein strip-fill hunt and sanity.
- 2026-08-20 — **QA batch-3 pass done, all five checks PASS** (save `New World (4)`, evidence
  `qa3_00`–`qa3_95` in `.porting/logs/visual/`, CLIENT-TEST-PROGRESS.md updated, client
  save-and-quit then confirmed no java remaining).
  - **Check 1 vacuum predicate (F15): PASS** — carried from conclusive pre-pause evidence:
    `rankine:gas` component present after absorb (`qa3_39_state_nbt`, `qa3_43_reabsorb_chat`),
    absent after release (`qa3_41_afterrelease_chat`); icon/tooltip states captured.
  - **Check 2 building-mode pick-block (F15): PASS, re-run** — pre-pause evidence was genuinely
    ambiguous (pick-block kept reselecting slot 0). The driver lacks middle-click, so QA wrote a
    scratch PostMessage helper (same non-activating technique, harness untouched). Asymmetric
    control: mode-1 stack in slot 0, pick-block the placed `chalk_bricks style:2` (F3-confirmed)
    → slot 0 stayed mode-1, a NEW mode-2 stack appeared in slot 1, `SelectedItemSlot` moved to
    it (`qa3_80_pickblock_asymmetric_control_PASS`).
  - **Check 3 fluid feel (F13): PASS** — `/data get entity @s Air` held at max over ~15 s
    submerged in survival, health 20.0, no bubble bar. Pig bonus: no drowning AND no buoyancy
    (sinks, `canSwim=false`) — stronger than expected given the flagged `LivingEntity#travel`
    residual; QA's observation is noted, not treated as refuting the engineer's code reading.
  - **Check 4 End vein findability (F18): PASS — the headline.** Fresh, never-loaded
    end_highlands island; one strip-fill exposed a dense floating ore field immediately.
    Pinned `rankine:native_indium_ore` (1500,28,-22) and `rankine:native_tellurium_ore`
    (1487,39,-26) by F3 + `/execute if block` text probes.
  - **Check 5 sanity: PASS** — nothing newly broken observed.
  - Orchestrator removed the 24 untracked `.porting/tools/_qa3_step*.json` scratch files QA
    left behind (untracked, pattern-named, harness file untouched).

## Batch 3 status: **COMPLETE** (2026-08-20)

F10–F18 implemented and gate-verified across `f7587f689` (alloy), `3f25463f6` (fluids /
antimatter / predicates / beach), `b5a824fa7` (decisions #6b + #7); GameTests 35 → **45**, all
passing; QA pass all PASS. Decisions #6 and #7 closed. Still open for Karl: **#1** (alloy armor
knockback resistance), **#2–#5** as before. Triage carried forward (candidates for a batch 4):
the wider F9 gating family (meteorites/fumaroles/flora/trees still overworld-gated);
`LivingEntity#travel` fluid buoyancy residual (needs `FluidType#move`); alloy block tint never
rendering + missing tile client sync (now fixable post-F12); the explosive-block
`BREAK`→`BLOCK` decay question (one 1.18.2 `Explosion.java` lookup); sphere-ore scarcity
(`count=1 × 0.3`, content); `FluidHelper.travel` dead code; `assertEmpty` >1024-char
truncation (7 sightings — fix the helper); `BuildingToolItem` NBT audit; crucible/damascus
steel zero-input alloying (content).

## Batch 2 status: **COMPLETE** (2026-08-17)

F6 (scoped to Antiquated) + F7–F9 implemented and gate-verified across commits `3f423d0e6`,
`9f27c95a9`, `ed3765213`; GameTests 29 → **35**, all passing. QA fresh-world pass: F8 PASS with
negative control, F7 in-world inconclusive but headlessly proven at every link (sparsity is
baseline parity, see decision #7), F9 headless-only (TF not installed), F6 optional check not
attempted (GameTest drives the real table path). New decisions queued for Karl: **#6** (the six
never-table-offerable enchantments: leave / `primary_items: []` / widen) and **#7** (End vein
height-band tuning vs parity). Batch-3 candidates from triage: `c:is_beach` missing
`stony_shore`; the wider F9 family (meteorites/fumaroles/flora/trees still overworld-gated);
review P1 batch 3 (alloy correctness) as previously queued.
  - **Engineer B triage (found, not fixed):** (a) `c:is_beach` lacks `stony_shore` (1.18.2 BEACH
    had it) ⇒ ore rows keyed `"beach"` miss stony shores — same family as F8, fix with a
    `rankine:is_beach` tag; (b) `"all"` selector on the `rankine:kimberlitic_tuff` row
    (Config:1246) is dead but was dead in 1.18.2 too — parity; fixing it is a content decision,
    not a port fix; (c) the F9 bug family is wider than the review's scope: meteorites, fumaroles,
    wall mushrooms, flora, trees, cobble patches are still gated on `c:is_overworld` though
    1.18.2's `isOverworld` (`!NETHER && !THEEND`) reached modded dimensions — candidate for a
    later batch; (d) `assertEmpty` >1024-char truncation, third sighting.
- 2026-08-22 — **Batch 4 (release) opened.** Karl's answers recorded in the review report. Client
  confirmed closed at the end of the batch-3 QA pass. Engineer G1 (Opus) launched on F19–F20.
- 2026-08-22 — **F37 DONE** (parallel Sonnet doc agent, no gradle): `.porting/reports/
  TICKET-ores-without-loot-tables.md`. Method: registered blocks (Java + 3,011 blockstates) vs
  2,931 loot tables → 80 raw gaps − 36 fluid/gas blocks that call `.noLootTable()` (a real
  vanilla "intentionally empty" sentinel, verified in decompiled `BlockBehaviour`) = **44 genuine
  gaps, matching HANDOFF**. Six cauldron variants initially mis-excluded then correctly kept
  (vanilla's cauldron copies no loot override). **4 ores drop nothing:** `coltan_ore`,
  `interspinifex_ore`, `monazite_ore`, `sperrylite_ore` — all `RankineOreBlock`s in
  `SPECIAL_ORES` next to the meteorite ores that DO drop themselves (`kamacite_ore`'s table is
  the 4-line template); identical gap in 1.18.2; each ore's crusher recipe is unreachable today.
  **Side finding (not fixed):** `REDSTONE_ORE` registration copies `Blocks.LAPIS_ORE` properties
  — copy-paste bug since 1.18.2, loot table itself is fine.
- 2026-08-22 — **F19–F20 DONE**, commit `d57e198e5`. Gates: build 7m04s, `test` 29/29 (was 25),
  runData 1 file (alloy_block model), `runGameTestServer` **"All 48 required tests passed"**
  (was 45). Falsification: pre-fix 2 unit + 2 GameTest failures naming the real bugs; inversion
  of all 28 sites predicted 18/12/13, observed exactly; sha256-verified restores.
  - **F19** — one `ArmorMaterial.Layer` per material was the root cause (1.21's per-layer
    `dyeable` flag + `getDefaultDyeColor` defaulting to white without a `minecraft:dyeable` tag).
    ALLOY now = dyeable base layer + non-dyeable `_overlay` layer; tint via
    `AlloyArmorClientExtensions#getDefaultDyeColor`, and worn/inventory both delegate to one
    `AlloyColorHelper#getTintARGB` so they cannot diverge. **Deviation:** both overlay PNGs are
    fully transparent (0 opaque pixels) — restored for parity/resource packs, but nothing visible.
  - **F20** — THREE causes: (1) colour read never-written persistent data (known); (2) **new:**
    `alloy_block.json` parented `cube_all` with no `tintindex`, so no colour handler could ever
    reach it — in 1.18.2 too; now explicit cube with `tintindex 0` (icon becomes tinted too,
    intended); (3) no `getUpdateTag`/`getUpdatePacket` — added; `onDataPacket` →
    `sendBlockUpdated` is the working re-render trigger (both suggested triggers verified to be
    no-ops for same-state changes). Pick-block fixed as a consequence.
  - **G1 triage:** (1) **HIGH, blocks the survival path:** `AlloyCraftingRecipe#assemble` never
    inherits colour, and the 9-ingot alloy_block recipe carries the white sentinel ⇒ a *crafted*
    Alloy Block stays untinted; 384 alloy_crafting recipes carry the sentinel, so colour
    inheritance has a blast radius → assigned to H1 as **F20b** (verify, fix if it's what crafted
    tools already do, else escalate to Karl); (2) alloy armour lost 1.18.2 crafting-table dyeing
    (`ArmorDyeRecipe` matched `DyeableLeatherItem`) — gameplay decision, not taken; (3)
    `inventoryTick` colour fallback is self-referential dead code (baseline-identical); (4) recipe
    generator inconsistent about display colour (helmets real, most pickaxes sentinel).
- 2026-08-22 — Engineer G2 (Sonnet) launched on F21–F24.
- 2026-08-22 — **F21–F24 DONE**, commit `3dde0a6be` (Sonnet; one "ended turn to wait" resume).
  Gates: build 13s, `test` **36/36** (was 29), runData idempotent after a 10m36s implementation
  run, `runGameTestServer` 48/48 (no new GameTests — all three testable fixes are unit tests over
  the live registry / committed JSON). Falsification: scoped stash of every touched file → 5
  predicted method failures with exact 1180/104/8/16 mismatch counts, observed verbatim; second
  pass synthetically stripped 2 untouched models → 2 predicted/observed.
  - **F21** — three causes: (1) `translucent.addAll(ELEMENT_BLOCKS)` (all 116) vs baseline's
    explicit 12 gaseous blocks → **104** models back to solid; (2) stone-bricks family: baseline
    filtered `instanceof RankineStoneBricksBlock` (bricks + mossy bricks only), and the permissive
    prefix matcher re-claimed `_slab/_stairs/_wall/_pressure_plate` models anyway → new exact-name
    matcher mode → **1,180** models back to solid (59 stones × 20; no mossy pressure plate exists,
    which is why "1,224" was an overestimate); (3) 8 hand-written models bypass datagen →
    `render_type` added in the JSON (cob `cutout_mipped`, the rest `cutout`, per baseline
    `ClientProxy`). Churn 1,284 = HashCache `written: 1284`.
  - **F22** — `RankineFluids.fluidType()` never set `.descriptionId`, so FluidType defaulted to
    `fluid_type.rankine.<name>` which nothing generates; now points at the pre-existing
    `fluid.rankine.*` keys (zero lang churn).
  - **F23** — 1.18.2 drew the overlay on Pre+Post of ~25 HUD layers (~50 passes at 0.05 alpha);
    now blits the same texture 50× per frame to reproduce the accumulation exactly, registered
    above `SAVING_INDICATOR` (last layer) instead of first. No headless test possible.
    **Orchestrator note:** 50 fullscreen blits/frame is exact but brute-force — a single blit at
    the equivalent accumulated alpha (1−0.95^50 ≈ 0.92) is mathematically identical for a
    uniform-alpha texture; QA to watch for hitching; cleanup candidate.
  - **F24** — two baseline-identical bugs: `getDisplayName()` returned a raw registry path and
    `renderLabels` overrode without `super` → nothing drawn. Fixed; resolves via
    `block.rankine.material_testing_table`.
  - **G2 triage:** `cast_iron_support_wall_*` models swept into `cutout_mipped` by the same
    permissive matcher (baseline never listed the wall) → queued as **F38** for J.
- 2026-08-22 — Engineer H1 (Opus) launched on F25–F28 + F20b.
- 2026-08-22 — **F25–F28 + F20b DONE**, commit `a01d97e0f`. Gates: build 20s, `test` **41/41**
  (was 36), runData churn 0, `runGameTestServer` **"All 52 required tests passed"** (was 48).
  Falsification: 9 targeted reverts, every predicted count observed exactly (5/4/2/8/0/1/4/1/5).
  - **F25a** — NeoForge's `LivingDamageEvent.Pre` fires one line BEFORE the absorption
    reduction; 1.18.2's event fired after. New `LegacyDamage` reproduces 1.18.2 arithmetic and
    pins the ABSORPTION reduction via `DamageContainer#addModifier` — needed because the flat
    adders (Endotoxin/Cleanse/Torque/…) bypassed absorption in 1.18.2; the finding's simpler
    formula was only right for multiplicative buffs (its own verify block predicted this).
  - **F25b** — `setCanceled` on `LivingIncomingDamageEvent` is not 1.18.2's cancel (it precedes
    shield/cooldown/hurt-sound); parry, Endobiotic and the five armour immunities now zero the
    amount instead, `Pre` listeners bail on nullified hits, `Post` still fires (1.18.2 returned
    true). Accepted deltas: RETALIATE reflects the pre-shield amount; cooldown gate reads only
    `invulnerableTime` (`lastHurt` is protected, no AT).
  - **F26** — `AlloyArrowItem` discarded the `weapon` argument; threaded through. Measured
    byte-identical to vanilla arrows (Power V/Punch II/Flame: 2000 fire ticks, 4.5 dmg, 2.0 kb;
    Piercing III: level 3).
  - **F27** — `KNOCKBACK_RESISTANCE_SCALE = 0.1` at the attribute-application point (stored
    stat untouched for tooltips/Indexer). Brass 25% Zn set: 1.18.2 0.00 / port 1.00 / now 0.10;
    ender amalgam 0.30; white gold 90% netherite 0.40 (= vanilla full netherite, the ceiling).
  - **F28** — **spear half REFUTED**: Impaling/Loyalty have no `primary_items` (7-arg
    `definition` overload), so `isPrimaryItemFor` collapsed to `supportsEnchantment`, which
    SpearItem already overrode — the table offered them all along (deleting the new override
    leaves all 52 tests green; kept as a documented guard). Totem half confirmed and fixed —
    but **the Totem of Infusing cannot enter an enchanting table at all** (`isEnchantable`
    needs durability; 1.18.2 identical), so the override is live only for anvil/book +
    `isPrimaryItemFor` consumers. Making it table-enchantable would be a new feature — not done.
  - **F20b** — facts: 486 alloy_crafting recipes = 384 sentinel + 102 coloured; the coloured
    ones are generic gear fed by a named ingot (colour in JSON); 380 sentinel ones make named
    items with their own textures; **exactly one** recipe produces `alloy_block`, fed by the
    generic ingot, so no JSON colour is possible; no colour-from-recipe mechanism exists. Fix:
    `assemble` inherits colour only for results with no default alloying recipe — exactly the
    4 generic ingot↔nugget↔block recipes (deviation from "block only", so block→ingots keeps
    it). Asserted both directions; `steel_block` control unaffected.
  - **H1 triage:** (1) cooldown-gate residual needs `lastHurt` (AT) — one edge case; (2)
    RETALIATE amount, same fix; (3) `!(WITHER && MAGIC)` always-true condition in
    `LivingDamageHandler:59` — baseline bug, preserved.
- 2026-08-22 — Engineer H2 (Sonnet) launched on F29–F32 (+ the explosive-block decay check).
- 2026-08-22/23 — **F29–F32 DONE**, commit `9ebb92dd6` (Sonnet; one "stopped to wait" resume,
  then a Claude Code process restart mid-gate-4 — resumed with transcript intact, re-ran gates).
  Gates: build 9m02s, `test` **45/45** (was 41), runData 1 new file (`stump_bases.json`),
  `runGameTestServer` **"All 56 required tests passed"** (was 52). Falsification: per-item
  reverts predicted 4 / 1 / 1 / 1, observed exactly; combined F30+F31+F32 revert failed exactly
  the 3 predicted tests with the dirt-stump control staying green.
  - **F29** — `boxBetween` lifted into `RankineMathHelper`, five call sites unified; mixing
    barrel and balloon were both 6× (pool's "8×" refuted — same code shape as #19); pedestal
    bounds cosmetic.
  - **F30** — `#minecraft:dirt`/`#minecraft:sand` ≠ 1.18.2 `Material.DIRT`/`SAND` (grass and
    mycelium never stumped); all 29 baseline Material sites grepped; new `rankine:stump_bases`
    tag; post-1.18.2 vanilla dirt-family blocks (moss, rooted dirt, mud, muddy mangrove roots)
    and soul_soil deliberately excluded (no 1.18.2 Material to match) — triage.
  - **F31** — pool claim CONFIRMED, and batch-3 E's triage (b) with it: 1.18.2 `BREAK` = full
    drops; HEAD's `ExplosionInteraction.BLOCK` decays by default gamerule. Reactive items +
    BlastingPowder/GunCotton/UnamedExplosive (8 call sites) now use a literal `DESTROY` via new
    `SphericalExplosion.vanillaExplode` (plain vanilla raycast shape preserved); 120-trial
    measurement vs a decay control.
  - **F32** — pool claim CONFIRMED; reset clears latch + 14 parallel lists under one lock
    (partial clear would misalign indices 3×-vs-2×); `ORE_STONES` hardened defensively.
- 2026-08-23 — Engineer I (Opus) launched on F33 (per-fluid realistic movement).
- 2026-08-23 — **F33 DONE**, commit `6bfde63dd` (Opus). Gates: build 4m45s, `test` 45/45,
  runData 0 churn, `runGameTestServer` **"All 60 required tests passed"** (was 56).
  - Model: `RankineFluidType extends FluidType` overriding `move()` with the in-water branch of
    `LivingEntity#travel` verbatim (depth strider, dolphin's grace, swim speed, climbable clamp,
    hop-out all preserved) plus three scalars: horizontal `S = clamp((visc/1000)^-0.18, 0.2, 1.5)`
    applied to the *terminal* speed via the drag coefficient (responsiveness untouched);
    `sink` = Archimedes vs density 1000–1600 normalised, clamped [−1, 2]; vertical drag
    `0.8·S^0.35` in [0.45, 0.85]; `fallDistanceModifier = min(1, S)` (F13's 1.0 survives at the
    water reference point; only thick fluids cushion). Precomputed at registration; no per-tick
    lookups. F13's no-drown/no-push/no-swim and path types untouched, both its tests still pass.
  - Measured (one pig, one pool, fluid swapped): coast distance water 0.586 / resin 0.219 /
    CS2 0.680 vs predictions 0.600 / 0.224 / 0.696; Δy over 40 ticks water −0.99, maple_sap
    −0.91, mercury **+0.90**; exit from 1-deep resin in 30 ticks with jump. Falsification: 5
    runs, 3 as predicted; **2 refuted the engineer's own exit invariant** (vertical drag can't
    stop the rise; horizontal speed isn't what exits a pool — the jump is) → code + test
    corrected, then re-attacked on the real mechanism (pig pinned exactly as predicted).
  - **Declaration changes, flagged for Karl:** (e) `maple_sap` kept at 1300 (≈ water) — the mod
    has no maple *syrup* fluid; raw sap is ~1.3 cP; Karl's example said "maple syrup slows" —
    one number (→ ~180000) flips it if he wants sap to feel thick; (f) `carbon_disulfide`
    viscosity 6000 → 363 (physically 0.363 cP; the 1.18.2 value was backwards and inert then).
  - **I triage:** (a) stale PORT-TODO in `RankineFluids.java:21-42` (client extensions ARE
    registered since `3dde0a6be`); (b) `RankineFlowingFluidBlock#entityInside` discards
    `Vec3.add` result — baseline-identical dead line; (c) non-living entities (items, boats,
    minecarts) still use vanilla water physics — `FluidType#move` is LivingEntity-only; (d)
    `sap` 6 cP is a judgement call (pitch would be far thicker); (g) `getWaterSlowDown()` is
    protected so skeleton horse / polar bear lose their swim bonus in Rankine fluids.
- 2026-08-23 — Engineer J (Sonnet) launched on F34–F36 + F38.
- 2026-08-23 — **F34–F36 + F38 DONE**, commit `4c5d10e9e` (Sonnet; one "stopped to wait"
  resume; an orphaned `find` from its own exploration starved the gradle daemon for ~45 min —
  found via jstack, killed; no correctness impact). Gates: build (env-slow 11m27s), `test`
  **46/46**, runData idempotent (producing run: exactly 8 files — 4 modifier JSONs, 4 wall
  models), `runGameTestServer` **"All 61 required tests passed"** (was 60). Falsification: F34
  predicted 8, observed 12 — the 4 extra were `minecraft:the_void` also lacking the four
  features (a stock vanilla biome `#c:is_overworld` omits: stronger confirmation than the
  modded-dimension framing); F35+F36 combined predicted 4 + 5 + 5, observed exactly; F38 1
  method / 4 entries exactly.
  - **F34** — `ConfigGatedFeaturesBiomeModifier.exclude` (default empty, back-compatible);
    meteorite/fumarole/wall mushrooms/cobbles = all biomes except `#is_nether`/`#is_end`
    (1.18.2 `isOverworld` semantics, `FeatureGeneration:54-156` baseline). Live count: 54
    non-nether/non-end biomes carry all four; nether keeps only its own fumarole; End none.
  - **F35** — four sphere rows 0.3 → 0.6 (defaults only; existing TOMLs keep 0.3).
  - **F36** — `"all"` = every biome via `CATEGORY_ALL` in the shared predicate, excluded from
    the "none" computation; kimberlitic_tuff is the only `"all"` row and precedes the End rows,
    so End biomes carry 9 veins with it first. Side fact: kimberlitic_tuff is a plain rock
    block, not a `RankineOreBlock`, so it is correctly outside `#c:ores`.
  - **F38** — `CAST_IRON_SUPPORT` routed through the restrictive matcher; slab/stairs stay
    permissive (their sweeps only reach their own variants).
  - **J triage:** biotite/muscovite/sodium chloride/pink salt blocks were `addCutoutMipped` in
    the baseline but go through `addTranslucent` at HEAD — pre-existing, unrelated call sites;
    `assertEmpty` truncation, again.
- 2026-08-23 — **All engineering done** (G1, G2, H1, H2, I, J). Launched in parallel: H3
  `verify.sh all` (five gates, background) and H1 HANDOFF rewrite (Sonnet doc agent).
- 2026-08-23 — **H3 `verify.sh all` PASS on `4c5d10e9e`** — all five gates incl. the server smoke
  gate (boots a normal-terrain world to Done, all recipes loaded). H2 changelog entries written.
  QA-1 (Sonnet) launched on the rendering checklist.
- 2026-08-23 — **H1 HANDOFF rewrite DONE** (Sonnet; one connection-error resume, no work lost —
  verified the file was still pristine before writing). Stale pre-review narrative and numbers
  removed; environment gotchas / bug classes / test assets preserved and re-verified; decisions
  #1–#14 merged into "settled"; accepted-deltas and known-gaps sections added; per-file test
  breakdowns grep-verified (11 unit files / 46 methods, 4 GameTest files / 61 methods).
- 2026-08-23 — **QA-1 rendering checklist DONE** (save `New World (5)`, 156 `qa4_*` screenshots,
  CLIENT-TEST-PROGRESS rows 1/2/4/5/6/9 closed, client killed; one connection-error resume).
  **PASS:** MTT title; worn armor tint (two alloys, NBT-differed colors, worn matches icon,
  non-alloy control unchanged); element blocks + stone-bricks crisp at distance, cob/ornament
  transparent; fluid names (text-probe — item-slot tooltips never render under the driver,
  isolated as harness limitation); JEI 2×3 + 3×2 layouts clean (generic alloy armor has no JEI
  crafting recipe by architecture, used diving chestplate for 3×2); all three deep creative tabs
  to last page clean; alloy block colour all 5 sub-checks incl. ~254-block chunk reload and
  pick-block dedupe; gas overlay visible with zero FPS cost (50fps both ways).
  **COULD NOT TEST:** parry overlay (5 attempts; multi-zombie pile-on root-caused to natural
  spawns in the carved test platform + /kill-during-death-screen trap; gas overlay shares the
  render path, so risk is low but the parry trigger itself is unverified visually) → QA-2 gets
  one capped retry with doMobSpawning off.
  QA-1 harness notes for the doc: item-slot tooltip hover unreliable (Button tooltips fine);
  Create-World / JEI search clicks drop ~every other click — verify-after-click, not blind
  batches; carved-terrain platforms need `/gamerule doMobSpawning false`.
- 2026-08-23 — QA-2 (Sonnet) launched on the mechanics pass.
- 2026-08-23 — **QA-2 mechanics pass DONE** (save `New World (5)`, 173 `qa5_*` screenshots,
  CLIENT-TEST-PROGRESS updated, client killed; mid-run corrective injection after Karl watched
  the burning-zombie failure live — husk rig used for the final capped attempt).
  **PASS:** M1 fluids (resin: no fall damage, Air pinned 300, ~1/4 water speed, sub-2s exit;
  mercury floats; CS2 ~102% water — two mid-session "drowned" deaths root-caused to a dropped
  teleport leaving the player in plain water, retried clean); M2 knockback exactly 0.1 brass /
  0.4 white gold, hit still knocks back; M3 crafted Alloy Block colour both directions with
  steel_block control; M4 spear+Impaling anvil accepted; M5 balloon launch-vs-beside exact.
  **FAIL-shaped finding:** M4 totem — **anvil REJECTS enchanted books on the Totem of Infusing**
  (isBookEnchantable=false still gates despite supportsEnchantment=true) ⇒ with the table
  impossible (no durability), the item's defining feature is dead on every path, contradicting
  decision 3B → Engineer K launched (F39).
  **COULD NOT TEST:** M6 stumps (two layers: `#rankine:tree_choppers` holds only Rankine axes —
  the brief's iron_axe silently skips the handler; and the driver cannot sustain a mining
  left-click at all — even hardness-0.5 dirt survived 20+ clicks) — GameTests carry both
  directions; M7 parry overlay (cap reached; the properly-rigged husk attempt landed its hit
  between screenshot windows) — gas overlay covers the renderer.
  QA-2 gotchas for the doc: `/item replace ... inventory.N` silently no-ops for hotbar slots
  (use `hotbar.N`); coordinate-template reuse caused two into-wall teleports.
- 2026-08-23 — Engineer K (Opus) launched on F39: totem anvil-book rejection.
- 2026-08-23 — **F39 REFUTED (the right outcome), commit `61956edc4`** (Opus; comments + tests
  only, zero behavior change). The anvil's rejection of totem+book is 1.18.2-faithful: baseline
  `InfusingTotemItem:96-99` itself overrides `isBookEnchantable` to false, and 1.18.2's anvil
  refused the combination twice over. QA's observation was correct; the "feature is dead"
  conclusion was not — the totem's defining feature is the BY-HAND infusion (`use()`: enchanted
  item or book in main hand, totem in offhand), proven working headlessly incl. a book donor.
  The hook-level green vs menu-level red gap is now closed by a real `AnvilMenu#createResult`
  GameTest on a survival mock player (totem+book EMPTY; pickaxe+book level 5; crowbar control
  proves F1's ban stays table-only) plus a by-hand infusion test with an already-loaded guard.
  Falsification: 2 + 3 predicted = observed. Gates green; `runGameTestServer` **"All 63
  required tests passed"** (was 61); runData zero churn.
  **Open option for Karl (gameplay change, not taken):** deleting the totem's
  `isBookEnchantable` override would open anvil books beyond 1.18.2. **K triage:** cursed-book
  donor returns blank where 1.18.2 kept the curses (`removeEnchantments` strips
  STORED_ENCHANTMENTS after curse write-back) — narrow, pre-existing, by-hand-only.

## Batch 4 status: **COMPLETE** (2026-08-23) — RELEASE BATCH

F19–F39 done across `d57e198e5`, `3dde0a6be`, `a01d97e0f`, `9ebb92dd6`, `6bfde63dd`,
`4c5d10e9e`, `61956edc4`. Tests 46 unit / **63 GameTests**, `verify.sh all` five-gate PASS,
QA-1 rendering all-PASS (parry overlay could-not-test, renderer covered via gas), QA-2
mechanics M1–M5 PASS (M6/M7 could-not-test, headlessly covered), F39 investigated and refuted.
Remaining for the release: changelog/HANDOFF corrections (done, uncommitted), artifacts commit,
release jar, release notes, push. Carried to 1.4.1: the QA-datapack gameplay-gate speed-up
(Karl's mandate, see CLIENT-TESTING.md rig checklist + [[rankine-qa-harness]] memory), the
unverified-findings pool (minus the five now resolved), the 44 loot-less blocks ticket, batch
triage items (alloy armour dyeing, non-living entities in fluids, `assertEmpty` truncation fix,
`lastHurt` AT, cursed-book donor, maple_sap/sap viscosity feel, anvil-books-for-totem option).
