# Visual verification progress — 1.21.1 port review

Session: 2026-08-09. Harness: `.porting/CLIENT-TESTING.md` + `.porting/tools/mc-client-driver.ps1`.
Evidence screenshots: `.porting/logs/visual/` (copied from session scratchpad; gitignored, local only).
World: `run/saves/New World`, machine test bed at x=59..71, y=70, z=-273/-269/-265.

**Rule for updating this file: every visual-test session updates the table below as its LAST act,
and the final per-check verdicts from any subagent report get folded in here. If a session dies
mid-run, this table is the resume point.**

## The 10 standing checks (defined in CLIENT-TESTING.md)

| # | Check | Status | Evidence / notes |
|---|---|---|---|
| 1 | All machine GUIs open, slots aligned, text readable | **MOSTLY FINE** | 10/14 opened correctly with legible titles + aligned slots (alloy_furnace, crucible, induction_furnace, fusion_furnace, mixing_barrel, gas_bottler, battery_charger, template_table, MTT-partial; evidence `v_r1_*.png`, `v_r2*_*.png`). Reconciled against code: beehive_oven_pit, charcoal_pit, distillation_tower, particle_accelerator have NO registered menu on HEAD **and none in 1.18** (DistillationTowerBlock had no `use()`) — passive blocks, faithful, NOT regressions. Open item: **material_testing_table title bar renders blank** (`v_r2g_71_title.png`) — GUI + recipe list otherwise fine; check its menu `getDisplayName`/lang key vs 1.18. evaporation_tower was missing from the test bed (likely harness placement failure — its self-degradation randomTick is byte-identical to 1.18 and can't fire on a lone block); its GUI is UNTESTED. |
| 2 | Alloy armour tinted worn + inventory | **PARTIAL — suspicious** | Inventory icons look grey/untinted (`v_armor2_zoom.png`) — but items given via bare `/give` have no alloy composition, so grey may be correct for dataless items. Worn-model check not done. Static audit still predicts worn armour untinted + overlay missing (RankineArmorMaterials.java:47). Redo with a properly-composed alloy piece. |
| 3 | Item predicates flip (gas vacuum, building mode) | **NOT TESTED** | Static audit predicts both stuck (ClientSetup.java:93 reads CUSTOM_DATA; data moved to components). |
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

## Not yet attempted at all (future session backlog)

- Machine GUIs beyond those noted in row 1 (esp. towers, gas bottler, particle accelerator, PCF, template table interactions).
- Anvil enchanting path (row 7 tail).
- In-world machine PROCESSING (put real inputs in, watch a recipe run) — no automated coverage exists anywhere for this.
- Worldgen eyeball pass (new world, look at stone layers/intrusions/ores; End + snowy-plains regressions from the static audit).
- Alloy tool crafting + tooltip stat verification.
- JEI non-square shapes explicitly (2x3 hoe/1x3 rod shapes), JEI search for rankine items.

## Static-audit context

48 confirmed code findings (1 critical) in the session scratchpad `confirmed-findings.md`; the
critical enchantability finding is row 7 above, visually confirmed. Fix list belongs to the main
review report, not this file.
