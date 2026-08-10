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
