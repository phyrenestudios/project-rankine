# Visual verification progress — 1.21.1 port review

Session: 2026-08-09. Harness: `.porting/CLIENT-TESTING.md` + `.porting/tools/mc-client-driver.ps1`.
Evidence screenshots: `.porting/logs/visual/` (copied from session scratchpad; gitignored, local only).
World: `run/saves/New World`, machine test bed at x=59..71, y=70, z=-273/-269/-265.

Session 2 (2026-08-17): worldgen eyeball for F7–F9 (batch 2), fresh world `New World (3)`. See the
"Worldgen eyeball pass" section below. Evidence prefixed `qa2_`.

**Rule for updating this file: every visual-test session updates the table below as its LAST act,
and the final per-check verdicts from any subagent report get folded in here. If a session dies
mid-run, this table is the resume point.**

## The 10 standing checks (defined in CLIENT-TESTING.md)

| # | Check | Status | Evidence / notes |
|---|---|---|---|
| 1 | All machine GUIs open, slots aligned, text readable | **MOSTLY FINE** | 10/14 opened correctly with legible titles + aligned slots (alloy_furnace, crucible, induction_furnace, fusion_furnace, mixing_barrel, gas_bottler, battery_charger, template_table, MTT-partial; evidence `v_r1_*.png`, `v_r2*_*.png`). Reconciled against code: beehive_oven_pit, charcoal_pit, distillation_tower, particle_accelerator have NO registered menu on HEAD **and none in 1.18** (DistillationTowerBlock had no `use()`) — passive blocks, faithful, NOT regressions. Open item: **material_testing_table title bar renders blank** (`v_r2g_71_title.png`) — GUI + recipe list otherwise fine; check its menu `getDisplayName`/lang key vs 1.18. evaporation_tower was missing from the test bed (likely harness placement failure — its self-degradation randomTick is byte-identical to 1.18 and can't fire on a lone block); its GUI is UNTESTED. |
| 2 | Alloy armour tinted worn + inventory | **PARTIAL — suspicious** | Inventory icons look grey/untinted (`v_armor2_zoom.png`) — but items given via bare `/give` have no alloy composition, so grey may be correct for dataless items. Worn-model check not done. Static audit still predicts worn armour untinted + overlay missing (RankineArmorMaterials.java:47). Redo with a properly-composed alloy piece. |
| 3 | Item predicates flip (gas vacuum, building mode) | **FIXED (post `b5a824fa7`, F15) — PASS** | See "Batch 3 visual pass" section below. Vacuum full/empty cycle and building-mode pick-block both NBT-proven correct. |
| 4 | Element blocks solid, not translucent | **NOT TESTED** | Static audit: 104-107 element blocks emitted with translucent render type. |
| 5 | Fluid buckets show real names | **NOT TESTED** | Static audit predicts raw `fluid_type.rankine.*` keys. |
| 6 | JEI category text + non-square recipe layout | **NOT TESTED** | (v_zoom1 shots were machine GUIs, not JEI.) |
| 7 | Enchanting table / anvil accept mod gear | **FIXED (post `8cf5681fc`) — PASS via `/enchant`** | Was CONFIRMED BROKEN (see history below); F1 QA pass 2026-08-09 ~22:00 re-tested against `8cf5681fc`. `/enchant @s minecraft:efficiency 3` on a clean `rankine:steel_pickaxe` and `/enchant @s minecraft:protection 2` on `rankine:alloy_helmet` both succeeded, confirmed via `Inventory[{Slot:Nb}]` NBT showing `minecraft:enchantments` on the correct item id (`qa_f1_clean_pickaxe_enchant_nbt.png`, `qa_f1_clean_helmet_enchant_nbt.png`). Negative control still correctly rejects: sharpness on the pickaxe fails with "Tool Steel Pickaxe cannot support that enchantment" (`qa_f1_clean_negative_chat.png`). Enchanting-table GUI item-placement (offers rendering on screen) still NOT captured — 2 driver attempts both missed the recessed table's hitbox; capped per plan, `/enchant` evidence is considered sufficient proof of the tag fix. Anvil variant still NOT tested. Original broken-state evidence: `v_ench_place_zoom.png` / `v_ench_step2.png` (rankine:steel_pickaxe + lapis in table = all three offer rows empty, reproduced twice pre-fix; matched root cause of missing `minecraft:enchantable/*` tags). |
| 8 | Rankine Journal opens with content | **LOOKS FINE** | `v_journal10_page.png`: real chapters and entries render (Clays → Clay Ball, text + item icon). The port's `patchouli:book` component binding works. Note: `rankine:rankine_journal` is a recipe id, not an item — give via `/give @s patchouli:guide_book[patchouli:book="rankine:rankine_journal"]`. |
| 9 | Creative tabs: no invisible/missing items | **PARTIAL, LOOKS FINE** | All 5 Rankine tabs visited (Biota, Components, Elements, Metallurgy, Misc); first page of each fully rendered, distinct icons, no magenta/invisible slots (`v_cr3_tab*.png`, `v_cr4_*_zoom.png`). Deep pages NOT scrolled — Elements/Metallurgy/Misc have scrollbars. |
| 10 | Anything else visibly wrong | **DONE (light)** | Only new items: MTT blank title (row 1); battery_charger titles itself "Battery Holder" (verify vs 1.18 lang — likely faithful). |

## Ground already verified good (don't re-test)

- All 21 machines place with correct models/textures, no magenta (`inworld2.png`, zoom pass).
- Block identities on the test bed verified via F3 Targeted Block (`v_scan*.png`, `v_topdown*.png`).
- Creative tabs page in, tab titles correct, items visible on first pages of Components/Elements/Building.
- Client boots clean: only pre-existing warnings (one bad `strontium_Lantern.png.mcmeta` path, goat-horn sounds).

## Worldgen eyeball pass (F7–F9, batch 2 — session 2026-08-17)

Fresh world `New World (3)` (`run/saves/New World (3)`), spectator mode, commands enabled.
Evidence: `.porting/logs/visual/qa2_*.png`.

- **F8 snowy_plains terrain — PASS.** `/locate biome minecraft:snowy_plains` → `[3687, 74, 3563]`.
  Biome confirmed with `/execute if biome 3687 91 3563 minecraft:snowy_plains` (`qa2_f8_biome_probe.png`).
  Surface pinned: `/execute if block 3687 93 3563 rankine:silty_loam_grass_block` succeeded
  (`qa2_f8_surface_probe.png`). Stone column pinned: `rankine:chalk` at (3687,71,3563) via F3 +
  `/execute if block` (`qa2_f8_stone_pin_chalk.png`); `rankine:andesite` also visually confirmed
  deeper (`qa2_f8_column_y30.png`) — both are Rankine sedimentary/igneous stones, not vanilla stone.
- **F8 negative control (frozen_peaks) — PASS.** `/locate biome minecraft:frozen_peaks` →
  `[4167, 94, 3851]`. Biome confirmed (`qa2_f8neg_biome_probe.png`). Stone pinned:
  `rankine:hornblende_andesite` (mountain geology) at (4167,141,3851); confirmed absence of
  `rankine:whiteschist` (icy geology) at the same coordinate — no icy-stone leak into the mountain
  biome (`qa2_f8neg_stone_pin.png`).
- **F7 End ore veins (native gallium/indium/selenium/tellurium, +sphere ores) — COULD NOT CONFIRM.**
  Config verified correct (`Config.java:1262-1269`: all 8 ore types registered for `the_end`,
  y −50..70, count 15/chunk for the 4 common ones — genuinely common by design). Extensive in-world
  search across two separate End islands (~5000 blocks apart): ~25 visual vantage points (several
  full-resolution crops, `qa2_f7_fullres_*.png`, `qa2_f7_scan_*.png`) plus two programmatic grid
  scans using `/execute if block ... #c:ores` (75 + 144 points, `qa2_f7_grid_scan*_result.png`) plus
  a 30-point dense single-column scan checking the 4 native ore IDs directly (`qa2_f7_final_scan.png`).
  One `#c:ores` hit was found near a meteorite at (1482,30,30), but it matched none of the 8 target
  ore IDs, none of the 4 meteorite ore IDs (kamacite/antitaenite/taenite/tetrataenite), nor
  lonsdaleite — its exact identity is unresolved (`qa2_f7_identify_*.png`, `qa2_f7_confirm_lonsdaleite.png`).
  Net result: did not positively find a native End ore in-world despite the config being correct and
  the density being nominally high. This is a search-coverage gap, not a confirmed regression — do
  not read it as a failure of F7. A future session should either search longer/smarter (e.g. mine a
  tunnel through a full chunk column instead of point-sampling) or add a temporary debug tool.
  Bonus finding: End meteorites (`EndMeteoriteFeature`, checkered sphere structures) generate
  reliably and were seen at every explored location — unrelated to F7 but confirms other End
  worldgen features are alive.
- **F6 Antiquated table-pool — NOT ATTEMPTED this session** (optional/confirmatory per WORKSTREAM.md;
  time budget was consumed by the F7 search). Headless GameTest already covers this path per the
  engineer's report.

## Batch 3 visual pass (F13/F15/F18 — session 2026-08-22)

Resumed a paused 2026-08-19/20 QA pass on the same save, `run/saves/New World (4)`. Evidence:
`.porting/logs/visual/qa3_00`–`qa3_72` (pre-pause) and `qa3_73`–`qa3_95` (this session).

- **Check 1 — gas vacuum full/empty predicate (F15) — PASS.** Verdict **carried from the
  pre-pause screenshots** (conclusive, not re-run): `qa3_39_state_nbt.png` and
  `qa3_43_reabsorb_chat.png` show the vacuum's `rankine:gas` component present
  (`rankine:hydrogen_gas_block`) after absorbing/reabsorbing; `qa3_41_afterrelease_chat.png`
  shows the component absent (bare `id` only) after releasing. Full empty→filled→empty→filled
  cycle proven via NBT text probes (`/data get entity @s Inventory[{Slot:0b}]`), the
  CLIENT-TESTING.md-preferred method over reading icons. Icon crops `qa3_35_tight_filled.png` /
  `qa3_42_tight_empty_v2.png` show visibly different silhouettes as corroborating (not
  load-bearing) evidence.
- **Check 2 — building-mode pick-block asymmetric control (F15) — PASS, re-run this session.**
  The pre-pause evidence (`qa3_65`/`qa3_68`/`qa3_71_pickblock*_result.png`) was ambiguous by the
  previous agent's own admission: pick-block kept reselecting slot 0, which could mean either
  "correctly matched an existing mode-2 stack" or "always defaults to slot 0" — indistinguishable
  without a control. Driver has no native middle-click (`.porting/tools/mc-client-driver.ps1`
  only implements left/right `ClickAt`); posted a scratch middle-click via the same
  non-activating `PostMessage` technique (WM_MBUTTONDOWN/UP) rather than editing the tracked
  harness. Control: cleared inventory, gave a **mode-1** `rankine:chalk_bricks` stack into slot 0
  (`qa3_78_control_setup_mode1_nbt.png`), confirmed aim on the placed **mode-2** block via F3
  (`qa3_79_aim_check_f3_style2.png`: `rankine:chalk_bricks`, `style: 2`), then pick-blocked it.
  Result (`qa3_80_pickblock_asymmetric_control_PASS.png`): slot 0 **unchanged** at mode 1; a
  **new** stack appeared in slot 1 with `rankine:building_mode: 2`; `SelectedItemSlot` moved to
  1; `SelectedItem` confirms the mode-2 stack. Correct behavior — pick-block did not
  false-positive-match the mismatched slot 0 stack, and correctly minted a fresh mode-2 item,
  consistent with Engineer E's fix to `BuildingModeBlock#getCloneItemStack`.
- **Check 3 — F13 fluid feel (maple sap) — PASS.** Built a small enclosed pool
  (`qa3_81_fluid_pool_built.png`), stood submerged in survival ~15s. `/data get entity @s Air`
  stayed at `300s` (max, no decay) across three samples ~5s apart; `Health` stayed `20.0f`; HUD
  screenshots (`qa3_82`/`qa3_83`) show no bubble bar despite being fully submerged with the
  brown fluid-vision overlay active — confirms `canDrown=false`. Float/drag while inside was
  observed (expected residual, not a bug). Bonus pig check: a pig submerged in an open room
  wandered ~39 blocks away over ~15s with `Air` still `300s` (`qa3_85`) — read as normal AI
  roaming through an under-walled test room, not a fluid-physics anomaly. A tighter fully-glassed
  re-test (`qa3_86_pig_sank_to_floor_nbt.png`) showed the pig settle to the pool floor (y=64,
  the fill's bottom) rather than floating, consistent with `canSwim=false`: no buoyancy, no
  drowning.
- **Check 4 — F18 End vein findability — PASS, the headline result.** Spectator to
  `minecraft:the_end` at (1500,75,0); `/locate biome minecraft:end_highlands` → 32 blocks away;
  landed in an end_highlands island in `New World (4)`'s End dimension, never previously loaded
  in this save. Got inside around y 44–55 (`qa3_88`), stripped a 31×31×31 interior with
  `/fill ~-15 ~ ~-15 ~15 ~-30 ~15 air replace #rankine:world_strip` — **"Successfully filled
  29329 block(s)"**, under the 32768 cap. Result: a dense field of floating ore clusters exposed
  immediately on every flythrough (`qa3_90`, `qa3_95` — dramatically more findable than the
  batch-2 "could not confirm" result, matching Engineer F's ~48-vein-starts/chunk claim). Pinned
  **two different native ores** via F3 aim + `/execute if block` text-probe (both required):
  `rankine:native_indium_ore` at (1500, 28, -22) (`qa3_91` F3, `qa3_92` probe echo) and
  `rankine:native_tellurium_ore` at (1487, 39, -26) (`qa3_93` F3, `qa3_94` probe echo). A third
  and fourth visually distinct ore texture (green-speckled, likely a sphere ore) were seen in the
  field but not pinned by ID — time-boxed, not required since two were already confirmed.
- **Check 5 — piggyback sanity — nothing newly broken observed.** Client booted clean, all
  screens/menus responsive, no crashes, no magenta/missing textures, no console errors surfaced
  in-band across main menu → world load → overworld building-mode work → maple sap fluid test →
  End dimension → strip-fill → save-and-quit. Not an exhaustive sweep — scoped to what was
  actually passed through this session.
- **World state**: `run/saves/New World (4)` — overworld test room now also holds a
  glass-enclosed maple sap pool (Check 3); a fresh End island near (1500, y30-55, -20) has a
  31×31×31 stripped cavity exposing native/sphere ore clusters (Check 4). Client was save-and-quit
  cleanly (not force-killed) after `/kill`ing test pigs; `Get-Process java,javaw` confirmed empty
  afterward.
- **Leftover from the pre-pause agent, not touched**: 24 untracked `.porting/tools/_qa3_step*.json`
  scratch step files (git-untracked, harmless) — left in place, not part of this session's remit.

## Not yet attempted at all (future session backlog)

- Machine GUIs beyond those noted in row 1 (esp. towers, gas bottler, particle accelerator, PCF, template table interactions).
- Anvil enchanting path (row 7 tail).
- In-world machine PROCESSING (put real inputs in, watch a recipe run) — no automated coverage exists anywhere for this.
- F6 Antiquated table-pool in-game check (optional, see above).
- Alloy tool crafting + tooltip stat verification.
- JEI non-square shapes explicitly (2x3 hoe/1x3 rod shapes), JEI search for rankine items.

## Static-audit context

48 confirmed code findings (1 critical) in the session scratchpad `confirmed-findings.md`; the
critical enchantability finding is row 7 above, visually confirmed. Fix list belongs to the main
review report, not this file.
