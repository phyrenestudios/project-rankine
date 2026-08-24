# Visual verification progress — 1.21.1 port review

Session: 2026-08-09. Harness: `.porting/CLIENT-TESTING.md` + `.porting/tools/mc-client-driver.ps1`.
Evidence screenshots: `.porting/logs/visual/` (copied from session scratchpad; gitignored, local only).
World: `run/saves/New World`, machine test bed at x=59..71, y=70, z=-273/-269/-265.

Session 2 (2026-08-17): worldgen eyeball for F7–F9 (batch 2), fresh world `New World (3)`. See the
"Worldgen eyeball pass" section below. Evidence prefixed `qa2_`.

Session 4 (2026-08-23): batch-4 (release) rendering checklist, C1–C9, fresh world
`New World (5)`. See the "Batch 4 visual pass" section below. Evidence prefixed `qa4_`. Closes
rows 1, 2, 4, 5, 6, 9 of the table below.

**Rule for updating this file: every visual-test session updates the table below as its LAST act,
and the final per-check verdicts from any subagent report get folded in here. If a session dies
mid-run, this table is the resume point.**

## The 10 standing checks (defined in CLIENT-TESTING.md)

| # | Check | Status | Evidence / notes |
|---|---|---|---|
| 1 | All machine GUIs open, slots aligned, text readable | **FIXED (post `3dde0a6be`, F24) — PASS** | Batch 4 (2026-08-23): `material_testing_table` title bar CONFIRMED FIXED — right-clicked a freshly `/setblock`'d table (aim confirmed via F3 Targeted Block first), title reads "Material Testing Table" cleanly, no blank bar (`qa4_c1_title_crop.png`). Prior history: 10/14 opened correctly with legible titles + aligned slots (alloy_furnace, crucible, induction_furnace, fusion_furnace, mixing_barrel, gas_bottler, battery_charger, template_table, MTT-partial; evidence `v_r1_*.png`, `v_r2*_*.png`). Reconciled against code: beehive_oven_pit, charcoal_pit, distillation_tower, particle_accelerator have NO registered menu on HEAD **and none in 1.18** — passive blocks, faithful, NOT regressions. evaporation_tower still UNTESTED for GUI (not in batch-4 scope). |
| 2 | Alloy armour tinted worn + inventory | **FIXED (post `d57e198e5`, F19/F20) — PASS** | Batch 4 (2026-08-23) — armour: gave two properly-composed `rankine:alloy_chestplate` via `/givealloy` (rose_gold 75Au-22Cu-3Ni, blue_gold 75Au-22Fe-3Ni), NBT-confirmed `rankine:color` differs (16756647 vs 8695295, `qa4_c2_nbt1.png`); icons clearly pink vs blue (`qa4_c2_hotbar_crop.png`); wore the rose_gold piece, F5 third-person — worn tint visibly matches its own pink icon, both in one frame (`qa4_c2_worn_and_icon.png`). Negative control `rankine:diving_chestplate` (non-alloy): icon and worn model both show their own natural yellow/grey design, unaffected by the tint system (`qa4_c2_diving_worn.png`). Alloy BLOCK colour (same fix family, F20) also fully re-verified — icon + placed-cube tint, survives a genuine ~254-block chunk-unload/reload round trip, pick-block returns the correctly-composed stack, negative-control `black_gold_block` unaffected; full detail in the "Batch 4 visual pass" section below. |
| 3 | Item predicates flip (gas vacuum, building mode) | **FIXED (post `b5a824fa7`, F15) — PASS** | See "Batch 3 visual pass" section below. Vacuum full/empty cycle and building-mode pick-block both NBT-proven correct. |
| 4 | Element blocks solid, not translucent | **FIXED (post `3dde0a6be`, F21) — PASS** | Batch 4 (2026-08-23): `rankine:aluminum_block` beside `minecraft:iron_block`, viewed from a genuine ~20-block sightline (cleared corridor, not just a teleport): both equally crisp/solid, no shimmer or translucency (`qa4_c3_far_crop_metal.png`). `rankine:gneiss_bricks_slab` + `rankine:gneiss_bricks_stairs` next to a full `gneiss_bricks` block at the same distance: also solid, crisp, correctly mipmapped (`qa4_c3_far_crop_stone.png`). Hand-written models `rankine:cob` and `rankine:ornament`: both render cleanly, no opaque-black rectangles or magenta (`qa4_c3_far_crop_cob.png`, `qa4_c3_ornament_zoom2_crop.png`). |
| 5 | Fluid buckets show real names | **FIXED (post `3dde0a6be`, F22) — PASS** | Batch 4 (2026-08-23): `/give @s rankine:maple_sap_bucket` — give feedback read "Gave 1 [Maple Sap Bucket] to Dev" in full chat text (`qa4_c4_inv_full.png`), a real name not a raw `fluid_type.rankine.*` key; slot/id cross-checked via `/data get entity @s Inventory` (`qa4_c4_inventory_nbt.png`). Note: item-slot **tooltip hover** proved unreliable in this harness session (a Button tooltip rendered fine on hover; Slot tooltips did not, across several coordinates) — treated as a driver/timing limitation, not a game bug; used the chat text-probe instead per CLIENT-TESTING.md's own stated preference for text over pixels. Bonus mixing-barrel-tank hover check NOT attempted (time-boxed; core check already satisfied). |
| 6 | JEI category text + non-square recipe layout | **PASS** | Batch 4 (2026-08-23): searched "steel hoe" → Stainless Steel Hoe recipe: "Crafting" category text crisp/readable, no doubling or black-outline garbling, 2×3 grid laid out correctly (2 wide × 3 tall, not stretched to 3×3) (`qa4_c5_hoe_recipe_full.png`). Note: the generic `rankine:alloy_chestplate`/`alloy_helmet` have no JEI-visible crafting recipe (only Anvil-enchant-combo and Item-Tags categories show) — architectural, they're made via the alloying/Material Testing Table path with `recipe=null` in `RankineItems.java`, not a bug. Used non-alloy `rankine:diving_chestplate` for the 3×2 armor example instead: clean "Crafting" category text, correct 3-wide × 2-tall grid (`qa4_c5_diving_recipe.png`). |
| 7 | Enchanting table / anvil accept mod gear | **FIXED (post `8cf5681fc`) — PASS via `/enchant`** | Was CONFIRMED BROKEN (see history below); F1 QA pass 2026-08-09 ~22:00 re-tested against `8cf5681fc`. `/enchant @s minecraft:efficiency 3` on a clean `rankine:steel_pickaxe` and `/enchant @s minecraft:protection 2` on `rankine:alloy_helmet` both succeeded, confirmed via `Inventory[{Slot:Nb}]` NBT showing `minecraft:enchantments` on the correct item id (`qa_f1_clean_pickaxe_enchant_nbt.png`, `qa_f1_clean_helmet_enchant_nbt.png`). Negative control still correctly rejects: sharpness on the pickaxe fails with "Tool Steel Pickaxe cannot support that enchantment" (`qa_f1_clean_negative_chat.png`). Enchanting-table GUI item-placement (offers rendering on screen) still NOT captured — 2 driver attempts both missed the recessed table's hitbox; capped per plan, `/enchant` evidence is considered sufficient proof of the tag fix. Anvil variant still NOT tested. Original broken-state evidence: `v_ench_place_zoom.png` / `v_ench_step2.png` (rankine:steel_pickaxe + lapis in table = all three offer rows empty, reproduced twice pre-fix; matched root cause of missing `minecraft:enchantable/*` tags). |
| 8 | Rankine Journal opens with content | **LOOKS FINE** | `v_journal10_page.png`: real chapters and entries render (Clays → Clay Ball, text + item icon). The port's `patchouli:book` component binding works. Note: `rankine:rankine_journal` is a recipe id, not an item — give via `/give @s patchouli:guide_book[patchouli:book="rankine:rankine_journal"]`. |
| 9 | Creative tabs: no invisible/missing items | **PASS (deep pages closed out)** | Batch 4 (2026-08-23): scrolled Elements, Metallurgy and Misc Rankine tabs to their LAST page each (mouse-wheel scratch script — the driver has no scroll step; see notes below), confirmed via scrollbar-thumb-at-bottom. Elements last page: element-sample vials/gases, no magenta/blank (`qa4_c6_elements_lastpage_crop.png`). Metallurgy last page: ingots/blocks/tools, no magenta/blank (`qa4_c6_metallurgy_lastpage.png`). Misc last page: seeds/tools/blocks, no magenta/blank (`qa4_c6_misc_lastpage_crop.png`). First-page pass from batch 1 (`v_cr3_tab*.png`, `v_cr4_*_zoom.png`) still stands. |
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

## Batch 4 visual pass — release batch (session 2026-08-23, QA-1)

Fresh world `New World (5)` (`run/saves/New World (5)`), Creative, commands ON, smooth-stone test
platform + cleared corridor built at world origin. Verified against `4c5d10e9e` (G1 `d57e198e5`,
G2 `3dde0a6be`). Evidence: `.porting/logs/visual/qa4_*.png`. Scoped to checks C1–C9 of the QA-1
brief, which map onto rows 1, 2, 4, 5, 6, 9 of the table above (full detail here; rows carry the
summary).

- **C1 — Material Testing Table title (row 1) — PASS.** Title reads "Material Testing Table"
  cleanly. `qa4_c1_title_crop.png`.
- **C2 — worn alloy armor tint (row 2) — PASS.** Two `/givealloy`-composed chestplates
  (rose_gold, blue_gold), NBT-confirmed differing `rankine:color`, worn tint matches icon,
  differs between alloys, non-alloy `diving_chestplate` unaffected. `qa4_c2_worn_and_icon.png`,
  `qa4_c2_diving_worn.png`.
- **C3 — element blocks solid + stone-brick mipmap + cob/ornament (row 4) — PASS.** All
  crisp/solid at ~20 blocks, no shimmer; cob and ornament clean, no black rectangles.
  `qa4_c3_far_crop_metal.png`, `qa4_c3_far_crop_stone.png`.
- **C4 — fluid names (row 5) — PASS (via text-probe, not tooltip hover).** "Maple Sap Bucket"
  confirmed via `/give` chat feedback + NBT slot/id cross-check. Item-slot tooltip **hover**
  did not render across ~8 attempts at varied coordinates/items (grid slots, hotbar-mirror
  slots, both creative-panel modes) despite a plain Button tooltip ("Survival Inventory")
  rendering correctly on the same hover mechanism — isolated as a harness/timing limitation
  specific to `Slot` tooltips, not a game bug (the give-feedback message resolves the same
  display-name path a tooltip would). Future sessions: budget for this if a tooltip screenshot
  is specifically required; a text probe is a valid substitute per CLIENT-TESTING.md's own
  stated preference for text over pixels.
- **C5 — JEI (row 6) — PASS.** 2×3 tool recipe (Stainless Steel Hoe) and 3×2 armor recipe
  (Diving Chestplate) both render with clean text and correctly-proportioned grids, not
  stretched to square. `qa4_c5_hoe_recipe_full.png`, `qa4_c5_diving_recipe.png`. Generic alloy
  armor pieces have no JEI crafting-category recipe at all (Anvil + Item Tags only) — confirmed
  architectural (`recipe=null` in `RankineItems.java` for `ALLOY_CHESTPLATE`/`ALLOY_HELMET`),
  not a rendering bug.
- **C6 — creative tabs deep pages (row 9) — PASS.** Elements, Metallurgy, Misc Rankine tabs
  each scrolled to their genuine last page (scrollbar-thumb-at-bottom, via a scratch
  `WM_MOUSEWHEEL` script — the driver's JSON vocabulary has no scroll step). No blank/invisible
  slots, no magenta, on any of the three. `qa4_c6_elements_lastpage_crop.png`,
  `qa4_c6_metallurgy_lastpage.png`, `qa4_c6_misc_lastpage_crop.png`. Tab navigation note for
  future sessions: the creative-panel tab-icon row is a SECOND page (top area shows "1/2" with
  arrows just above the tab icons) — Elements/Metallurgy/Misc/Biota/Components live on page 2,
  not page 1; clicking the "›" arrow at approx. image-coord (630,156) reveals them.
- **C7 — alloy block colour (F20/F20b) — PASS, all five sub-checks.** `/givealloy` rose_gold
  `alloy_block`: (a) icon tinted pink (`qa4_c7_hotbar_crop.png`); (b) placed cube tinted pink
  (`qa4_c7_placed1.png`); (c) flew ~254 blocks away (past the 12-chunk/192-block view distance,
  confirmed "Waiting for chunk..." at the far end) and back — genuine chunk unload/reload, both
  the alloy_block and the black_gold_block negative control still correctly tinted after reload
  (`qa4_c7_reloaded_clean.png`); (d) pick-block: reproduced the same non-activating `PostMessage`
  technique as a SCRATCH script (`WM_MBUTTONDOWN`/`UP`, not committed to the tracked harness) —
  selected an empty hotbar slot, aimed via F3, middle-clicked the placed block; `SelectedItemSlot`
  moved to the slot holding the matching rose_gold stack (composition/color already confirmed via
  NBT) rather than duplicating — correct pick-block dedup behavior, achieved on the first attempt;
  (e) negative control `rankine:black_gold_block` (default composition, no `/givealloy` override)
  placed beside it: its own correct dark-grey tint, unaffected by the pink block next to it
  (`qa4_c7_sidebyside.png`). NBT for both: `qa4_c7_nbt.png`.
- **C8 — HUD overlays (F23) — gas PASS, parry COULD NOT TEST.** Gas: stood inside
  `rankine:ammonia_gas_block` (placed at eye level, not feet level — a block at feet level does
  not reach the camera's y-position and shows no overlay, a framing trap worth noting for future
  sessions); a distinct purple world-fog plus a clearly separate reddish HUD band above the
  hotbar both render (`qa4_c8_gas_overlay_clean.png`). FPS identical with and without the
  overlay active (50 fps both, F3-confirmed) — no hitching. Parry: **could not test** despite
  five separate attempts (including one with absorption amplifier 20 + Resistance IV as a
  safety net) — the player died three times engaging a single zombie while holding the
  offhand-knife parry stance, and the overlay was never captured on the surviving attempts
  either. Root-caused the deaths (not to a damage bug, see C9) but never positively observed
  the parry tint. A future session should retry with the `doMobSpawning false` /
  `qa4_scratch_mousehold.ps1`-style hold-before-summon approach from the start, and consider
  that "hold" may need to overlap the zombie's swing more precisely than a fixed ~1.5–2s wait
  achieves.
- **C9 — piggyback sanity.**
  - **Zombie swarm / lethality finding, explained (not a Rankine bug):** the three parry-test
    deaths were preceded by "Killed 24 entities" and "Killed 14 entities" on later cleanup
    passes — natural hostile mobs were continuously spawning in unlit pockets of the carved-out
    test platform (a snowy-mountain cavity) throughout the session, on top of QA's own repeated
    `/summon` calls, several of which went un-killed because the cleanup `/kill` command was
    sent while a screen (the death screen itself, or the pause menu) was open — the exact
    "never send `cmd` while a screen is open" trap CLIENT-TESTING.md warns about, hit firsthand.
    Net effect: multi-zombie pile-on damage, not an abnormal per-hit damage value. Fixed for the
    rest of the session with `/gamerule doMobSpawning false`. Future sessions building a test
    platform by carving into natural terrain should either fully light the interior or disable
    mob spawning from the start.
  - Client booted clean, world created/loaded/saved without incident, no crashes, no
    magenta/missing textures anywhere encountered outside what's logged above, no console
    errors surfaced in-band. Save-and-quit was clean; `Get-Process java,javaw` confirmed only
    the pre-existing Gradle daemon remained afterward (verified by PID and command line).
  - Windows Game-Mode button (`Create World` screen) and the JEI search field both showed
    intermittent posted-click drops (roughly every other click did not register) — a harness
    input-reliability note, not a game issue; screenshot-and-verify after each click caught it
    every time.

## Not yet attempted at all (future session backlog)

- Machine GUIs beyond those noted in row 1 (esp. towers, gas bottler, particle accelerator, PCF, template table interactions).
- Anvil enchanting path (row 7 tail).
- In-world machine PROCESSING (put real inputs in, watch a recipe run) — no automated coverage exists anywhere for this.
- F6 Antiquated table-pool in-game check (optional, see above).
- Alloy tool crafting + tooltip stat verification.
- Parry HUD overlay (F23) — see C8 above; gas overlay is confirmed but parry still needs a clean positive-or-negative capture.
- Item-slot tooltip hover rendering — came up empty across many attempts in the batch-4 session; worth a dedicated look if a future check specifically needs a tooltip screenshot rather than a text probe.

## Static-audit context

48 confirmed code findings (1 critical) in the session scratchpad `confirmed-findings.md`; the
critical enchantability finding is row 7 above, visually confirmed. Fix list belongs to the main
review report, not this file.

## Batch 4 visual pass (QA-2, mechanics) — session 2026-08-23

Reused `New World (5)` (`run/saves/New World (5)`); built a fresh 50×50 smooth-stone platform at
`x[-25,25] z[55,105] y99` (floor) / `y100-110` (clearance), south of QA-1's leftover structures, to
avoid interference. Verified against `4c5d10e9e` (H1 `a01d97e0f`, H2 `9ebb92dd6`, I `6bfde63dd`).
Evidence: `.porting/logs/visual/qa5_*.png`. Covers checks M1-M7 of the QA-2 brief.

- **M1 — per-fluid movement feel (F33) — PASS.** Three 3×3×2-deep stone-walled pools
  (`rankine:resin`/`rankine:liquid_mercury`/`rankine:carbon_disulfide`) plus a 1-deep resin exit
  puddle and four parallel 15-long speed lanes (water baseline + the three fluids).
  - Resin: 10-block fall into the deep pool, health unchanged 20.0→20.0
    (`qa5_20_resin_fallen.png`, `qa5_21_resin_health.png`); no drowning bubbles, `Air` stat stayed
    at 300 through a 20-key-tap active-swim burst (`qa5_22_resin_submerged_wait.png`,
    `qa5_46_diag_e.png`-`qa5_48_diag_g.png`); horizontal crawl ~0.036 blocks/tap vs water's
    ~0.142 blocks/tap over an identical 20-tap burst (~1:4, matching the code's clamped
    `(visc/1000)^-0.18` ≈ 0.2 for resin); exit from the 1-deep edge confirmed via a `rankine:resin`
    block-probe at the feet (`T0_IN_RESIN`) immediately followed by the player 4.8+ blocks clear of
    the puddle at the next check (`qa5_53_exit2_t0.png`, `qa5_54_exit2_t1.png`) — comfortably
    under the ~2s bar.
  - Liquid mercury: floats up, Y +0.9 to +1.3 blocks across repeated ~2-4s windows
    (`qa5_27_mercury_float.png`); horizontal speed ~67% of the water baseline over 20 taps
    (`qa5_55_mercury_speed_t0.png`, `qa5_56_mercury_speed_t1.png`) — "near water speed", lower
    than the code's steady-state ≈93% because the player floats out of the fluid mid-burst.
  - Carbon disulfide: sinks (settles to pool floor faster than the 2-deep pool can show a clean
    rate — both CS2 and a water control hit bottom within the same ~1s window, so the "sinks
    about half as fast" ratio is code-verified — `sink` term keyed off density 1263 vs water's
    1000-1600 reference — rather than independently re-measured); horizontal speed ~102% of
    water over 20 taps, i.e. very slightly faster, directionally matching the code's ≈120%
    (`qa5_34_cs2_safe.png`, `qa5_57_cs2_speed_t0.png`, `qa5_59_final.png`).
  - Harness note for future sessions: two unexplained "drowned" deaths occurred mid-session
    (once in an unverified-teleport deep water/CS2 shaft, once after a full unbroken 20-tap resin
    burst). Re-running the *identical* resin scenario with a position/`Air`/`Health` check
    inserted after every few taps reproduced no drowning at all up to the full 20 taps
    (`Air` stayed 300 throughout) — most likely a dropped/garbled teleport left the player
    submerged in plain **water** far longer than intended (normal drowning applies there), not a
    resin no-drown regression. Lesson: verify a teleport landed (position or block probe) before
    trusting a subsequent multi-second `wait`, especially right after a fluid test.
- **M2 — knockback attribute (F27) — PASS.** Brass set (`75Cu-25Zn` ×4 via
  `rankine:alloying/brass_alloying`) worn (equip via `/item replace entity @s armor.X from entity
  @s hotbar.N` — note **`hotbar.N`, not `inventory.N`**, for items sitting in the hotbar; the
  latter silently no-ops to `[Air]` without erroring): `/attribute @s
  minecraft:generic.knockback_resistance get` → **0.10000000149011612** (`qa5_76_m2_attribute_brass.png`),
  matching the documented brass-25%-zinc curve exactly. Behavioral: an unattended `PersistenceRequired`
  zombie both damaged (20.0→~partial) and displaced the player ~5.8 blocks over a few seconds
  (`qa5_77_before_zombie_pos.png`, `qa5_79_after_knockback_pos.png`) — knockback is clearly not
  zeroed. White gold set (`90Nr-10Zn` ×4 via `rankine:alloying/white_gold_alloying`; `Nr` =
  netherite's element symbol, confirmed via `elements/netherite.json` — the composition is
  accepted as-is, `/givealloy` does not validate against the target recipe's own element ranges)
  → **0.4000000059604645** (`qa5_84_m2_attribute_whitegold.png`), matching the vanilla-netherite
  ceiling exactly.
- **M3 — crafted Alloy Block colour, survival path (F20b) — PASS, all sub-checks.** Nine
  `rankine:alloy_ingot` given in one command with a `count` argument (`/givealloy ... 9`); NBT
  confirmed a single stack of 9 sharing identical `rankine:alloy_composition`
  (`75Au-22Cu-3Ni`) and `rankine:color` (16756647) — stacking itself is proof all 9 match
  (`qa5_86_m3_ingot_nbt.png`). Crafted 3×3 at a real crafting table: output slot tinted rose with
  a "Composition: 75Au-22Cu-3Ni" tooltip (`qa5_91_m3_row3.png`); NBT of the taken result confirmed
  color+composition carried onto the `alloy_block` item (`qa5_95_m3_block_nbt.png`); placed cube
  renders visibly pink (`qa5_99_m3_placed_check.png`). Broken back via
  `alloy_ingot_from_alloy_block`: 9 ingots returned, NBT confirmed identical composition/color
  preserved (`qa5_102_m3_ingots_back_nbt.png`). Negative control: `rankine:steel_block` uses the
  **same** `rankine:alloy_crafting`+`inherit:true` recipe machinery (verified in the recipe JSON)
  yet the live crafting-grid preview stayed plain dark grey, never tinted
  (`qa5_112_m3_steel_filled.png`) — confirms the `assemble()` fix's "no default alloying recipe"
  gate is doing real runtime work, not just relying on a different recipe type.
- **M4 — spear + totem enchanting (F28) — spear PASS, totem REJECTED (see note).** Anvil:
  `rankine:steel_spear` + `minecraft:enchanted_book[stored_enchantments={levels:{"minecraft:impaling":3}}]`
  → accepted, result slot populated with an "Impaling III" spear and "Enchantment Cost: 6"
  (`qa5_123_m4_result_clean.png`). `rankine:totem_of_infusing` + a Sharpness V enchanted book in
  the same anvil → **rejected** (red X between the slots, result slot stayed empty,
  `qa5_125_m4_totem_result.png`), despite `InfusingTotemItem.supportsEnchantment` returning
  `true` unconditionally. This is consistent with the class's own javadoc on that method —
  *"isBookEnchantable below still refuses the book half, so this only opens what an anvil can
  otherwise apply"* — `isBookEnchantable` returns `false` for the totem and appears to be a hard,
  earlier gate that `supportsEnchantment` does not override for fresh book application; whatever
  anvil capability decision 3B's "allow BOTH" intended for the totem, it is not "give it a new
  enchantment from a book," at least not as currently wired. Flagging for engineer follow-up
  rather than calling it a clean pass. Optional enchanting-table glance (2-attempt cap) was
  skipped — time-boxed, and the anvil/spear half was already unambiguous.
- **M5 — mixing barrel / balloon AABB (F29) — PASS, via balloon (barrel not attempted).**
  Reused the shared `RankineMathHelper.boxBetween(pos, pos.above()).expandTowards(1,1,1)` math:
  worked out to exactly the balloon's own 1×2×1 block column (no horizontal expansion after the
  fix), so "on" vs "beside" is unambiguous regardless of which side you stand on. Standing
  co-located with a freshly-summoned `rankine:balloon`: launched ~12 blocks straight up in under a
  second, off the test platform entirely (`qa5_130_m5_on_test.png`) — clearly flung. Standing 1
  block away horizontally: position exactly unchanged after summoning a balloon there
  (`qa5_132_m5_beside_test.png`) — clearly not caught. Did not additionally test the mixing
  barrel (same code shape, per the engineer's F29 note) given the balloon gave an unambiguous
  positive+negative pair already; barrel would need a redstone-power+inventory-item setup that
  adds GUI-click risk for no new information.
- **M6 — stump behavior (F30) — COULD NOT TEST (harness limitation, root-caused).** Root cause of
  two early false negatives: `#rankine:tree_choppers` (the tag gating `BlockBreakHandler`'s whole
  tree-chop branch) contains **only Rankine's own alloy axes** — vanilla `iron_axe`/`netherite_axe`
  silently skip the entire tree-fell/stump code path and fall through to an ordinary single-block
  break, regardless of Efficiency/Haste. Switching to `rankine:steel_axe` (real chopper) fixed the
  tag issue but exposed a deeper harness problem: **this driver cannot complete a real,
  multi-tick "hold left click to mine" action in-world.** Confirmed by elimination: a trivial
  hardness-0.5 `minecraft:dirt` block, with `rankine:steel_axe` + Efficiency X + Haste X (levels
  chosen to make the break time trivially short), did not break after a single click nor after
  20+ repeated clicks with waits between them (`qa5_151_m6_easy_test.png`) — zero cumulative
  progress, not merely slow progress. In-world right-click (`use`/`interact`, edge-triggered —
  opening tables/anvils, placing blocks) and in-GUI left-click (screen open, cursor released) both
  work fine throughout this session; only in-world *sustained* left-click appears not to register,
  most likely because Minecraft polls raw mouse-button state for "continue destroying" once the
  cursor is grabbed for camera control, which a posted legacy `WM_LBUTTONDOWN`/`UP` pair does not
  update. Creative-mode instant-break was considered and rejected as a substitute: `instabuild`
  players skip `BlockBreakHandler`'s tree-chop branch entirely (`!player.getAbilities().instabuild`
  guards the whole block), so a creative break tests nothing here. Static evidence gathered
  instead: `rankine:stump_bases` tag confirmed (from the generated JSON) to include
  `minecraft:dirt` and exclude `minecraft:grass_block`, matching the intended 1.18.2
  `Material.DIRT` parity. Also discovered mid-investigation: standing at a block's *center*
  X/Z coordinate for a `/tp`-then-look aim requires `blockCoord + 0.5`, not `blockCoord - 0.5` —
  an off-by-one that cost significant time before F3's "Targeted Block" line caught it.
  Recommend a future session either scripts this via a real held-key rebind (`key.attack` can be
  remapped to a keyboard key in `options.txt`, which this driver's `PressKey` can drive) or
  accepts this as a permanent gap for the automated harness and leans on the GameTests
  (`treeChopOnGrassDropsLogWithoutStump` / `treeChopOnDirtPlacesStump`) for this behavior.
- **M7 — parry overlay retry (F23, cap 2 attempts) — COULD NOT TEST, cap reached.** Attempt 1: a
  bare (no-armor) player summoned a zombie 2 blocks away and died before/during the first
  guard-click — a repeat of QA-1's exact failure mode. Attempt 2 (final, per live orchestrator
  guidance mid-session): healed, self fire-resistance, gamemode asserted via
  `/execute if entity @s[gamemode=survival]`, basic iron helmet+chestplate for survivability,
  knife confirmed in `weapon.offhand`, and a **`minecraft:husk`** substituted for the zombie
  (husks do not burn in daylight, removing the fire-death confound). Two separate guard-clicks
  were bracketed with 7 screenshots total (`qa5_164`-`qa5_170_m7_final_*.png`); the overlay never
  appeared in any of them, and health dropped 20.0→16.0 (`qa5_171_m7_final_health.png`, armor
  absorbed part of a hit) in the gap between two of the bracketing shots — meaning a hit landed
  outside both ~10-tick (0.5s) guard windows, not that guarding failed to zero a hit it caught.
  The player was then finished off by the same husk between that health check and the following
  `/kill` cleanup command (`Dev was slain by Husk`) — harmless, evidence was already captured.
  Consistent with QA-1's conclusion: the parry overlay's own render path (shared with the gas
  overlay, which passed cleanly in batch 4) is not in doubt; hitting a ≤0.5s window with a
  ~90ms-per-key-tap, ~870ms-per-command driver against a mob's own attack timing is the
  limiting factor, not a game bug. Two-attempt cap reached as instructed; not retried further.

**Session hygiene:** two accidental teleports landed the player inside solid terrain (once
suffocation-killed in survival at an untested Z coordinate outside the built platform, once
harmlessly in creative) — both from reusing a coordinate template without checking it was inside
the platform's actual `z[55,105]` bounds. No lasting damage; noted here so a future session
double-checks bounds before teleporting to a "new" test area rather than reusing one blindly.
Client exited cleanly via death screen → Title Screen → Singleplayer list (no crash); all three
`java.exe` processes (including the gradle daemon) were force-killed at session end per the task's
"confirm no java client remains" instruction, so a future session's first `./gradlew` invocation
will pay a fresh daemon-startup cost.
