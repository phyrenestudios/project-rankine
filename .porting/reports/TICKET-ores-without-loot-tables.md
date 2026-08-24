# Ore blocks with no loot table — mining them drops nothing

**Suggested GitHub issue title:** 4 ore blocks (coltan, interspinifex, monazite, sperrylite) have no loot table — mining drops nothing
**Suggested labels:** bug, content-gap, deferred-to-1.4.1

## Summary

Four Rankine ore blocks — `coltan_ore`, `interspinifex_ore`, `monazite_ore`, and `sperrylite_ore` —
have no loot table registered. In game this means: a player finds the ore, mines it with the correct
tool, and the block simply vanishes. No item, no experience orbs, nothing. Pick-block/creative-inventory
access to the block still works (it has an item form), but survival mining of it is a dead end.

The impact goes beyond the block itself: all four ores already have a **crusher machine recipe**
(`src/main/resources/data/rankine/recipe/crushing/`) whose input is literally the ore's own item
(e.g. `rankine:coltan_ore` → `rankine:columbite`/`rankine:tantalite`/etc.). Since mining never produces
that item, those four crushing recipes — and the rarer materials only reachable through them
(columbite, tantalite, pentlandite, the six monazite rare-earth variants, platinum arsenide,
osmiridium, cooperite, garnet, and more) — are currently unreachable in survival.

This is a **pre-existing gap, not a porting regression**: all four blocks were registered identically
in the 1.18.2 codebase (commit `859f87773`) and had no loot table there either. Karl reviewed this
alongside 40 other loot-table-less blocks during the 1.4.0 release triage and decided to leave all of
them as-is for 1.4.0 (see `.porting/reports/PORT-REVIEW-2026-08-09.md`, "Decisions recorded
2026-08-22," item 2), with this file filed as the follow-up ticket specifically for the ore subset.

## Affected ore blocks

| Ore block (id) | Drops today | Expected drop | Sibling template | Present in 1.18.2? |
|---|---|---|---|---|
| `rankine:coltan_ore` | Nothing | 1× `rankine:coltan_ore` (itself) | `kamacite_ore` | Yes — registered, loot table absent there too |
| `rankine:interspinifex_ore` | Nothing | 1× `rankine:interspinifex_ore` (itself) | `kamacite_ore` | Yes — registered, loot table absent there too |
| `rankine:monazite_ore` | Nothing | 1× `rankine:monazite_ore` (itself) | `kamacite_ore` | Yes — registered, loot table absent there too |
| `rankine:sperrylite_ore` | Nothing | 1× `rankine:sperrylite_ore` (itself) | `kamacite_ore` | Yes — registered, loot table absent there too |

**Why "drop itself" is the expected behavior:** all four blocks are plain
`new RankineOreBlock(DEF_ORE)` instances (no distinct raw-material item exists for any of them — checked
`RankineItems.java`), and all four sit in the exact same `RankineLists.SPECIAL_ORES` grouping
(`src/main/java/com/cannolicatfish/rankine/init/RankineLists.java:139`) as `kamacite_ore`,
`taenite_ore`, `tetrataenite_ore`, and `antitaenite_ore` — which *do* have loot tables, all four built
with the same `createBlockLootTable(Block)` "drop self" helper
(`src/main/java/com/cannolicatfish/rankine/data/RankineBlockLootTables.java:337-340`). `kamacite_ore`
is the closest sibling: identical class, identical `DEF_ORE` properties, identical (default/zero) XP
range, same list membership — its generated table is:

```json
// src/generated/resources/data/rankine/loot_table/blocks/kamacite_ore.json
{
  "type": "minecraft:block",
  "pools": [
    {
      "bonus_rolls": 0.0,
      "conditions": [{ "condition": "minecraft:survives_explosion" }],
      "entries": [{ "type": "minecraft:item", "name": "rankine:kamacite_ore" }],
      "rolls": 1.0
    }
  ],
  "random_sequence": "rankine:blocks/kamacite_ore"
}
```

### Registration references (file:line)

- `coltan_ore` — `src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java:1136`
- `interspinifex_ore` — `src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java:1127`
- `monazite_ore` — `src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java:1164`
- `sperrylite_ore` — `src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java:1155`
- `kamacite_ore` (template sibling) — `src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java:1159`

All five are declared the same way, e.g. `BLOCKS.register("coltan_ore", () -> new RankineOreBlock(DEF_ORE))`.

### 1.18.2 parity check

Checked against the last 1.18.2 commit (`859f87773`, path uses the old `loot_tables` plural directory):

- All four blocks existed there too, registered identically (`RegistryObject` instead of `DeferredBlock`,
  same `RankineOreBlock(DEF_ORE)` construction) — e.g. `coltan_ore` at the old
  `RankineBlocks.java:1128`, `interspinifex_ore` at `:1119`, `sperrylite_ore` at `:1147`,
  `monazite_ore` at `:1156`.
- `git show 859f87773:src/generated/resources/data/rankine/loot_tables/blocks/<name>.json` — for all
  four names, git reports the path does not exist in that commit. `kamacite_ore.json` **does** exist
  at that same commit, confirming the sibling had a table back then too and these four simply never
  did.

## Suggested fix

Add four lines next to the existing `SPECIAL_ORES` self-drop entries in
`src/main/java/com/cannolicatfish/rankine/data/RankineBlockLootTables.java` (right after line 340,
which currently ends this group with `KAMACITE_ORE`):

```java
// RankineBlockLootTables.java, addTables(), immediately after line 340:
lootTables.put(RankineBlocks.COLTAN_ORE.get(), createBlockLootTable(RankineBlocks.COLTAN_ORE.get()));
lootTables.put(RankineBlocks.INTERSPINIFEX_ORE.get(), createBlockLootTable(RankineBlocks.INTERSPINIFEX_ORE.get()));
lootTables.put(RankineBlocks.MONAZITE_ORE.get(), createBlockLootTable(RankineBlocks.MONAZITE_ORE.get()));
lootTables.put(RankineBlocks.SPERRYLITE_ORE.get(), createBlockLootTable(RankineBlocks.SPERRYLITE_ORE.get()));
```

`createBlockLootTable(Block)` is defined locally at
`src/main/java/com/cannolicatfish/rankine/data/RankineBlockLootTables.java:740-746` — it builds a
single pool, 1 roll, `LootItem.lootTableItem(block)`, gated on `ExplosionCondition.survivesExplosion()`,
which is exactly what produced `kamacite_ore.json` above. After adding the four lines, regenerate
datapack resources (`runData`) to emit the four new
`src/generated/resources/data/rankine/loot_table/blocks/<name>.json` files.

This is the recommended fix *if* these ores are meant to behave like their `SPECIAL_ORES` siblings.
Note the four already have a distinct downstream identity via their crushing recipes (see Summary) —
if a future design wants mining to award something other than the raw ore item, that's a judgment
call for whoever picks up this ticket, not something this analysis assumes.

## Methodology / count note

Rankine registers ~3,000 blocks. To find "every block with no loot table" without running gradle,
this analysis cross-referenced:

- **Registered blocks:** every block that has a blockstate JSON, generated
  (`src/generated/resources/assets/rankine/blockstates/`, 3,006 files) or hand-written
  (`src/main/resources/assets/rankine/blockstates/`, 5 files: `aged_cheese`, `crucible`, `metal_pipe`,
  `tap_line`, `unaged_cheese`) — 3,011 names total. Cross-checked against direct
  `grep "BLOCKS.register("` hits in `RankineBlocks.java` and the `block_groups` helper classes
  (`RankineStone`, `RankineWood`, `RankineBricks`, `RankineCement`, `RankineDripstone`,
  `RankineSandstone`), which build block IDs by string concatenation (e.g. `RankineStone.newStoneReg`
  registers 22 blocks per call) rather than literal `register(` calls.
- **Loot tables:** every file under `src/generated/resources/data/rankine/loot_table/blocks/`
  (2,930) plus the one hand-written table under `src/main/resources/data/rankine/loot_table/blocks/`
  (`tilled_soil.json`).

Diffing those gives **80** blocks with no loot table file — higher than the ~44 cited in
`HANDOFF.md` and `PORT-REVIEW-2026-08-09.md`. Investigating the gap, one mechanism (confirmed against
Mojang's own decompiled `BlockBehaviour.java`/`Blocks.java`, pulled from the local NeoForm runtime
cache — `~/.gradle/caches/neoformruntime/intermediate_results/...output.jar` — not from anything under
this repo) explains 36 of the 80 as intentional, not a bug:

- **36 blocks — fluids and gases** (`ammonia_gas_block`, `aqua_regia`, all the acid/liquor/sap
  fluids, `antimatter`, etc.) are declared with `.noLootTable()` in their `Block.Properties`, which
  sets `drops = BuiltInLootTables.EMPTY` (`BlockBehaviour.java:1133-1136`) — a real, always-registered
  vanilla sentinel (`BuiltInLootTables.EMPTY = minecraft:empty`, `BuiltInLootTables.java:13`), the same
  explicit "intentionally empty" mechanism vanilla itself uses for fire/water/lava. Their loot table
  key resolves and is found; it's just empty by design. Not a gap.

**A false lead, ruled out:** the 6 `AbstractRankineCauldronBlock` variants (`juglone_cauldron`,
`latex_cauldron`, `maple_sap_cauldron`, `maple_syrup_cauldron`, `resin_cauldron`, `sap_cauldron`) build
their properties via `BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)`
(`AbstractRankineCauldronBlock.java:45`), and `getCloneItemStack` returns a vanilla cauldron — which
looks at first glance like they're meant to share vanilla's `minecraft:blocks/cauldron` loot table.
They don't: `ofFullCopy` only copies the `drops`/`lootTableSupplier` fields *if the source block set
them*, and vanilla's own `Blocks.CAULDRON` is plain `Properties.of()...` with no `.noLootTable()`/
`.dropsLike()`/`.lootFrom()` (`Blocks.java:2779-2781`) — confirmed by vanilla using `ofLegacyCopy`
(which never touches `drops`) for its *own* water/lava/powder-snow cauldron variants
(`Blocks.java:2782-2791`) and still giving each one its own loot table. So the copy carries nothing,
and `BlockBehaviour`'s constructor (`BlockBehaviour.java:114-123`) falls through to its default case:
derive the key from **the new block's own registry name**. These 6 blocks each need — and lack — their
own `rankine:blocks/<name>.json` exactly like everything else in this list. `getCloneItemStack` only
affects creative pick-block, not survival mining drops.

Excluding just the 36 verified-intentional fluid/gas blocks leaves **44 confirmed genuine gaps: 4 ores
(this ticket) + 40 other blocks** (appendix below) — matching the ~44 figure in `HANDOFF.md` and
`PORT-REVIEW-2026-08-09.md` exactly. Every specific example HANDOFF.md names (`skarn_stairs`,
`skarn_wall`, `cast_iron_support_stairs`/`wall`, `fiber_block_wall`, `sodium_vapor_lamp`, `aged_cheese`,
`stick_block`, `distillation_tower`, `pcf`, `tap_line`, `locust_spine`, the fumaroles, "several ores")
appears in this analysis's 44.

## Appendix: the other 40 non-ore blocks with no loot table

Recorded here so the full accounting (40 + 4 ores = 44) is in one place, per Karl's 2026-08-22 decision
to leave all of them for 1.4.0. No further breakdown attempted — this ticket's scope is the ore table
above.

`aged_cheese`, `aloe_plant`, `breccia_stairs`, `breccia_wall`, `carbon_dioxide_fumarole`,
`cast_iron_support_stairs`, `cast_iron_support_wall`, `checkered_marble_stairs`,
`checkered_marble_wall`, `corn_stalk`, `distillation_tower`, `fiber_block_stairs`, `fiber_block_wall`,
`hydrogen_chloride_fumarole`, `hydrogen_sulfide_fumarole`, `ice_bricks_stairs`, `ice_bricks_wall`,
`juglone_cauldron`, `latex_cauldron`, `locust_spine`, `maple_sap_cauldron`, `maple_syrup_cauldron`,
`mycelium_path`, `packed_snow_stairs`, `packed_snow_wall`, `pcf`, `pineapple_bush`, `resin_cauldron`,
`rope`, `sap_cauldron`, `skarn_stairs`, `skarn_wall`, `sodium_vapor_lamp`, `stick_block`,
`sulfur_dioxide_fumarole`, `tap_line`, `unaged_cheese`, `unamed_explosive`, `willow_branchlet`,
`willow_branchlet_plant`
