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
