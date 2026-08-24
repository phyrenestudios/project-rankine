# Project Rankine 1.21.1-1.4.0 — release notes

*Paste-ready summary for CurseForge/Modrinth. The full, per-fix changelog is in `CHANGELOG.md`.*

**Project Rankine comes to NeoForge 1.21.1.** This is a full port from Forge 1.18.2 (v1.3.8) —
~680 source files, all 35k+ generated data files re-emitted — followed by a 70-agent behavioral
review against the 1.18.2 baseline and four fix batches closing every confirmed regression that
mattered. It ships with 46 unit tests and 63 GameTests; five verification gates (build, unit,
datagen, GameTest server, dedicated-server smoke boot) pass on the release commit.

## Highlights

- **Everything is enchantable, compostable, and employable again** — the port's original
  game-breaker (no Rankine gear accepted any enchantment) plus composting, all five villager
  professions, and enchantment loot/trade availability are restored.
- **Worldgen is back everywhere**: End ore veins (gallium, indium, selenium, tellurium — retuned
  to actually be findable, and sphere-ore chances doubled), Snowy Plains geology, stony-shore
  beach ores, and full stone-layer/intrusion/feature coverage in modded dimensions
  (Twilight Forest etc.). Kimberlitic tuff generates for the first time ever.
- **The alloy system is correct end to end**: tier-3 tools demand the right rod, loot-chest
  packaged gear works on a fresh world (and no longer crashes the server), placed alloy blocks
  keep their composition — and both worn alloy armor and placed alloy blocks now *show their
  alloy colour*, which alloy blocks never did in any version.
- **Combat matches 1.18.2**: damage buffs respect absorption, knife parries grant proper
  invulnerability frames, alloy arrows carry Power/Punch/Flame/Piercing, explosions drop the
  right amounts.
- **Fluids got real physics** (new feature): each of the 16 fluids moves you according to its
  actual viscosity and density — resin and mud are a crawl, liquid mercury floats you, carbon
  disulfide is thinner than water, thick fluids cushion falls. Nothing drowns or shoves you.
- **Hundreds of rendering fixes**: element blocks solid again, 1,180 stone-brick models
  re-mipmapped, fluid names everywhere, visible parry/gas overlays, the Material Testing Table
  finally has its title.

## Balance changes (deliberate)

- Alloy armor knockback resistance is now real but scaled: a brass set gives 0.10 total; the
  strongest realistic set caps at vanilla full-netherite 0.40. (1.18.2 gave zero via a rounding
  bug; the raw port accidentally gave full immunity.)
- End vein height bands target the islands' actual stone; sphere ores spawn at double the old
  chance.

## Upgrading

- **Config note:** worldgen changes apply to config *defaults*. An existing `rankine-common.toml`
  keeps its old values — delete it (or hand-edit the End ore rows) to pick up the new bands and
  chances. Only newly generated chunks are affected.
- Existing 1.18.2 worlds: previously generated chunks run the retrogen replacement pass once more
  (chunk retrogen state is not migrated).
- Requires NeoForge 21.1.x for Minecraft 1.21.1, Java 21. JEI 19.21.0.247+ and Patchouli
  1.21.1-93 supported.

## Known gaps

The pre-existing 1.18.2 content gaps remain by choice (44 blocks with no loot table — tracked for
a follow-up — and two crushers with no item form). A small pool of low-priority audit findings is
queued for 1.4.1. Full accounting: `HANDOFF.md` and `.porting/` in the repo.
