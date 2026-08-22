# Project Rankine 1.18.2 → NeoForge 1.21.1 — Port Review

**Date:** 2026-08-09 · **Branch:** `1.21.1-neoforge` @ `43e6895f9` · **Baseline:** `859f87773` (origin/1.18.X, v1.3.8)

**Method:** 70 review agents across 15 domains, every source area diffed against the 1.18.2
baseline and checked against the decompiled 1.21.1+NeoForge sources; the top 55 findings each
independently re-verified by an adversarial agent instructed to refute them (48 confirmed,
7 refuted); the most serious findings then spot-checked by hand; plus a live `runClient` visual
pass driven through `.porting/tools/mc-client-driver.ps1` (results in `CLIENT-TEST-PROGRESS.md`,
evidence in `.porting/logs/visual/`).

---

## Verdict

The port is **structurally excellent and behaviorally leaky**. All five headless gates pass
honestly; ~680 files compile; registries, datagen, GameTests, server boot, machine GUIs, the
Patchouli journal and the creative tabs all genuinely work — the visual pass confirmed the
biggest predicted client risks (invisible items, broken journal, scrambled GUIs) did NOT
materialize. But the audit confirmed **48 behavior regressions** the gates cannot see, one of
which is game-breaking. They cluster into two families the port already knew about but
under-applied:

- **Family A — "Forge/1.18 did it for you; NeoForge/1.21 makes you ask"** (milk fluid, armor
  durability… and now: fluid entity-interaction defaults, explosion decay modes).
- **Family B — "registration moved from code into a datapack file datagen never emits"**
  (enchantable tags, compostables data map, POI job-site tags). Nothing errors; content is
  simply absent. Detection trick: grep for the *presence of the directory* in
  `src/generated/resources`, not entries in it.

---

## P0 — game-breaking, fix before any release

### 1. Nothing can be enchanted ⚠ visually confirmed
`RankineItemTagsProvider` populates no `minecraft:enchantable/*` tags, so **every** Rankine tool,
weapon and armor piece is rejected by the enchanting table AND the anvil (NeoForge routes books
through `supportsEnchantment` too). 1.18's `EnchantmentCategory` used `instanceof` checks that
included mod items automatically; 1.21 requires opting into the tags. Also kills the mod's own
recipe-granted enchantments on alloy gear (Guard, Gas Protection, auto-Fortune on white gold…).
*Visual proof:* `v_ench_place_zoom.png` — steel pickaxe + lapis, three empty offer rows.
**Fix:** in `addEnchantableTags`, add every damageable item to `DURABILITY_ENCHANTABLE`, tools to
`MINING_ENCHANTABLE`(+`_LOOT`), weapons to `SWORD/WEAPON_ENCHANTABLE`, armor to the four
`*_ARMOR_ENCHANTABLE` tags, bows/crossbows/tridents to theirs. Mirror the old category predicates.

## P1 — content silently missing or wrongly gated (13 findings)

**Worldgen (4):**
- `WorldgenUtils.java:74`: key is `"theend"`, config ships 9 rows keyed `"the_end"` (0 match).
  All 8 End ore veins dead ⇒ gallium/indium/selenium/tellurium **unobtainable in survival**.
  Also add the missing `"none"` category mapping while there.
- `"icy"` mapped to `c:is_icy`, which lacks `snowy_plains` ⇒ that whole biome loses all Rankine
  terrain (soils, stone layers, cryolite ore). Use a mod-owned tag reproducing 1.18 membership.
- `world_replacer`/`flat_bedrock`/`intrusion` gated on the three vanilla dimension tags; 1.18
  applied them to every biome ⇒ modded dimensions (e.g. Twilight Forest, which the config
  explicitly supports with 20 rows) get vanilla stone. Use a broader mod-owned tag.
- (See CLIENT-TEST-PROGRESS backlog: none of this is visually verified yet — fix, regen, then
  eyeball a fresh world.)

**Datapack-file family B (2, plus P0 above):**
- Compostables: `VanillaIntegration.registerCompostable` writes `ComposterBlock.COMPOSTABLES`,
  which 1.21 never reads ⇒ ~100 items non-compostable. Emit a
  `neoforge:data_maps/item/compostables` DataMapProvider driven off the same list.
- Villager professions: no `point_of_interest_type/acquirable_job_site` tag ⇒ Metallurgist,
  Mineralogist, Botanist, Gem Cutter, Rock Collector can never be acquired; every custom trade is
  dead code. Add a `PoiTypeTagsProvider`.

**Alloy system (3):**
- `AlloyCraftingRecipe.Serializer#toJson` drops per-key `alloyRecipe` constraints ⇒ 40 tier-3
  tool recipes (superalloys, ender amalgam, stainless, tungsten heavy) accept ANY alloy rod
  instead of steel. Fix the builder to write its own JSON (HANDOFF knew of this, with wrong
  numbers — it's 40 gameplay files, not 136).
- `PackagedToolItem`/`PackagedArmorItem` mint gear with a null recipe id unless that alloy was
  smelted first this session ⇒ loot-chest rewards are gutted (no recipe, blank name, wrong
  stats). Resolve ids eagerly.
- `AlloyBlock` place/break round-trips alloy data through `CUSTOM_DATA`, which is never written
  ⇒ placing then breaking any alloy block wipes its composition.
- Related balance item: alloy armor knockback resistance changed from truncated-int (always 0)
  to real float — a 25%-zinc set now gives total knockback immunity. HANDOFF calls it a bugfix;
  it is a large balance change. Decide explicitly (see "Decisions for Karl").

**Fluids (1):** all 16 fluids use default `FluidType.Properties` ⇒ they drown/buoy/push entities
like water; 1.18 fluids did none of that (you could stand in maple sap safely). Set
`canSwim/canDrown/canPushEntity(false)`, `pathType(null)` etc. in the shared helper.

**Enchantments (1):** Endplay, Withering Curse, Ghastly Regeneration missing from
`minecraft:tradeable` ⇒ unobtainable in survival (Endplay is a content dead-end for the
ender-amalgam blunderbuss line).

**Client (1):** `gas_held`/`building_mode` model predicates read `CUSTOM_DATA` but the values
live in dedicated components ⇒ Shulker Gas Vacuum never shows filled, building-mode tools never
change icon. One-line fixes in `ClientSetup.java:93`.

**Misc (1):** `AntimatterBlock.java:49` uses `DESTROY` instead of `DESTROY_WITH_DECAY` ⇒ a
radius-12 blast drops ~7,000 block items (≈12× intended; server-lagging).

## P2 — visible or mechanical wrongness (medium, 18 findings — top items)

- **Worn alloy armor untinted + overlay layer never drawn** (`RankineArmorMaterials`: single
  un-dyeable layer). Inventory icons ARE tinted — the asymmetry is the tell.
- 104–107 element storage blocks emitted `translucent` instead of solid; 1,224 stone-brick
  variant models forced to unmipped `cutout`; hand-written models (Cob, Ornament, Tree Tap, Tap
  Line) lost their render_type entirely.
- Damage-event mapping: buffs applied on `LivingDamageEvent.Pre` now punch through absorption;
  knife-parry/immunity cancels now abort `hurt()` entirely (no i-frames). Tree-chop turns
  bottom logs of grass-grown trees into Stumps (log drop lost).
- Mixing barrel flings entities in a 3×2×3 neighborhood (AABB helper inflates 6×), HUD overlays
  (parry, gas) drawn once instead of per-layer (near-invisible), fluid names render as raw
  `fluid_type.rankine.*` keys, alloy arrows lose Power/Punch/Flame/Piercing, Rankine
  enchantments can't appear on loot/mob gear (`on_random_loot` tag missing) and 21 villager
  trades lost, crops don't keep tilled soil hydrated (`MAINTAINS_FARMLAND` missing
  `#minecraft:crops`), Antiquated absent from the enchanting table pool.
- **Material Testing Table title bar blank** (new, from visual pass — `v_r2g_71_title.png`);
  GUI otherwise functional. Check its `getDisplayName`/lang key against 1.18.

## P3 — low (17 findings)

Niche-condition items: brewing base narrowed to exact potions, Element Indexer double-dimmed
background, template-table status icon always "craftable", gas blocks random-tick with configs
off and lost `replaceable`, sapling ground-rule mismatch, enchantment pool discoverability
filter dropped, tofu/tuna not wolf food, recipe-book categories collapsed to "misc", etc.
Full list with fixes: `.porting/logs/visual/confirmed-findings.md`.

## Refuted during adversarial verification (do not chase)

7 findings died under verification — among them: machine tick rates/slot indices/ContainerData
maps are **byte-identical** to 1.18 across all 10 machines; `use→useWithoutItem` placement is
correct everywhere; GUI text colors are safe (Font auto-fills alpha); `getAllRecipesFor` order
matches 1.18. The machines themselves are the *best-ported* part of the codebase.

## Visual pass summary (details: `CLIENT-TEST-PROGRESS.md`)

Confirmed working: 10/14 machine GUIs (4 have no GUI **by 1.18 design** — verified against
baseline), journal fully functional, all 5 creative tabs clean on first pages, all 21 machines
place with correct models. Confirmed broken: enchanting (P0). Untested: predicates, element
translucency, fluid tooltips, JEI layout, worn armor model, deep tab scroll — the backlog is in
the progress file. Harness docs: `.porting/CLIENT-TESTING.md`.

## Decisions for Karl (not bugs — judgment calls)

1. Alloy armor knockback resistance: keep 1.18's always-0, or embrace the (large) buff?
2. The 44 loot-table-less blocks and 2 BlockItem-less crushers — pre-existing 1.18 gaps;
   fill or leave?
3. Vanilla recipe-book grid mis-fill for 4 tool shapes — declined `extends ShapedRecipe` fix
   earlier; JEI-side is fixed. Revisit?
4. Three 1.18 *network* serialization bugs were silently fixed in the codec rewrite — keep the
   fixes (recommended) or restore bug-parity?
5. (Raised by F1 engineer, 2026-08-09:) `SpearItem.supportsEnchantment` force-allows
   Impaling/Loyalty on the **anvil** as well; 1.18.2 allowed them at the **table only**. Mirror
   image of finding #40, pre-existing on HEAD. Restrict to `isPrimaryItemFor`, or keep the wider
   allowance?
6. (Raised by F6 engineer, 2026-08-17:) The batch-1 triage note proposed moving 7 enchantments
   into `in_enchanting_table`; verification refuted 6 of them (Dune Walker, Snow Drifter, Speed
   Skater, Swift Swimmer, Gas Protection, Aqua Lense override `canApplyAtEnchantingTable()` to an
   unconditional `false` in 1.18.2 — the table never offered them on any stack), so only
   Antiquated was moved (`3f423d0e6`). Three options for the six: **(a)** leave as committed —
   out of the pool tags, exact table parity, but their batch-1 `on_mob_spawn_equipment`/
   `on_traded_equipment` membership may exceed baseline (1.18.2's mob-equip/villager-gear paths
   also required `canApplyAtEnchantingTable`); **(b)** give them `primary_items: []` in
   `RankineEnchantmentBootstrap` — the literal translation of the 1.18.2 override, zero behavior
   change, lets them join the pool tags AND closes the (a) generosity gap (recommended);
   **(c)** deliberately widen availability beyond 1.18.2 — a gameplay buff, not a port fix.
   **DECIDED by Karl 2026-08-19: option (b)** — faithful to 1.18.2. Implemented in batch 3.
7. (Raised by F7 follow-up engineer, 2026-08-17:) End ore veins work (proven at every link by
   GameTest, `ed3765213`) but are **extremely sparse in practice, and were equally sparse in
   1.18.2** — the config numbers are identical at the baseline. The 4 sphere rows (molybdenite/
   uraninite/xenotime/greenockite, y 10–40 uniform) target a band that is mostly void in the End;
   the 4 triangle rows (y −50..70) waste ~35% of attempts below the dimension floor and land
   only ~12% in the island band (y≈40–70). QA could not find a single vein in a two-island
   search. Keep parity, or retune the height bands to the End's actual terrain (a content
   change)?
   **DECIDED by Karl 2026-08-19: retune — "veins need to be findable."** Implemented in batch 3.

## Suggested execution order

1. **P0 enchantable tags** + regen (`runData`) + the GameTest that asserts a Rankine pickaxe
   accepts Efficiency (add it — nothing guards this today).
2. **P1 batch 1 (pure datagen):** compostables data map, POI tags, tradeable tags,
   `on_random_loot`, `MAINTAINS_FARMLAND` — one datagen provider session, low risk.
3. **P1 batch 2 (worldgen):** `"the_end"` key + icy tag + dimension gating, then a fresh-world
   eyeball pass via the client harness.
4. **P1 batch 3 (alloy correctness):** recipe builder toJson, packaged items, AlloyBlock
   round-trip, fluid properties, antimatter, predicates.
5. **P2 client pass:** armor layers/tint, render types, MTT title, overlays, damage-event
   mapping — then re-run the visual checklist end-to-end (checks 2–6 still open).
6. Gates + `verify.sh all` + updated CHANGELOG (fix the "identical JSON" claim — it's false
   for 40 recipes) before calling it 1.21.1-1.4.0.
