# Should Rankine update to Minecraft 26.2? — Platform & ecosystem assessment

**Date:** 2026-08-10 · **Current baseline:** 1.21.1 / NeoForge 21.1.248 · All claims below verified
against live sources this date (knowledge-cutoff-safe).

## Verdict

**Not 26.2, not now.** NeoForge for 26.2 is beta-only — the API is still explicitly allowed to
break build-to-build. If the goal is "get onto the 2026 versioning family," the defensible target
is **26.1.2 (stable)**, and the right time is **after the remaining review-fix batches land on
1.21.1**. If the goal is specifically 26.2, wait for its stable promotion and re-assess; based on
the 26.1 cadence that's plausibly 1–3 months out.

## Platform maturity: where NeoForge actually is

- Mojang moved to calendar versioning in 2026 (`year.drop.patch`); 26.1 was the first drop, 26.2
  the second. NeoForge's scheme follows it (`26.2.0.42-beta` = MC 26.2.0, build 42).
- **Every published NeoForge 26.2 build is `-beta`** (26.2.0.0-beta … 26.2.0.42-beta on their
  Maven as of today). NeoForge's own convention: the beta channel means breaking changes are
  permitted while the line stabilizes. There is not even a 26.2 release announcement on their
  blog yet.
- **26.1.2.9x is the current stable line** and the community reference guidance for beta lines is
  blunt: pin an exact build, re-test after every bump.
- Minecraft 26.x runs on **Java 25** and requires **Gradle 9.1+** (toolchain, ModDevGradle and CI
  all bump with it). One genuine improvement: Mojang **removed obfuscation**, so official names
  ship with the game — our "grep the decompiled tree" workflow gets easier, and Parchment becomes
  optional.

## Community maturity: where the ecosystem actually is

- **The stable center of mass for 2026 packs is 26.1.2** — e.g. All The Mods 11 shipped on
  NeoForge/26.1.2 in July 2026. **1.21.1 remains the largest installed base** (ATM10 on 1.21.1,
  ~20M downloads). 26.2 is bleeding-edge; early adopters only.
- **JEI**: already publishes for 26.x (they track new versions fast). Irrelevant per Karl's
  scoping (custom compat), but a healthy ecosystem signal.
- **Patchouli: hard ceiling at 1.21.1.** No release for anything newer on any loader, and no
  sign of active porting. On any 26.x target the Rankine Journal cannot ship as a Patchouli book.
  `compatibility/Patchouli.java` already no-ops when Patchouli is absent, so the mod degrades
  gracefully — but the Journal is lost until/unless Patchouli revives or is replaced. (Karl has
  pre-accepted this.)

## Migration cost: this is a second port, not an update

1.21.1 → 26.2 crosses ~8 breaking NeoForge lines (21.2, 21.4, 21.5, 21.6, 21.9, 21.11, 26.1,
26.2). NeoForge's own 26.1 notes tell porters to read six prior release-note sets. The changes
that hit Rankine hardest, mapped to its actual surfaces:

| Change (version) | Rankine exposure |
|---|---|
| **Recipe system overhaul** (21.2+): recipes no longer synced to clients; `RecipeDisplay`/`SlotDisplay` required per recipe type; ingredient JSON format changed | 15 custom recipe types with hand-built StreamCodecs, plus the JEI plugin built on exactly the old sync assumption. Largest single work item. |
| **Item model definitions** (21.4): `ItemProperties.register` predicates replaced by data-driven item models | The `gas_held`/`building_mode` predicates (just fixed in review) get rewritten again, as datagen. |
| **GUI render-state extraction** (26.1): `Screen#render` → `extractRenderState`, `GuiGraphics` → `GuiGraphicsExtractor` | All 13 screens, the HUD overlays, JEI category renderers — an architectural rewrite, not a rename. |
| `ItemStackTemplate` (26.1): stacks unrepresentable before registries load | Touches recipe codecs, datagen, anything holding static ItemStacks. |
| Registry API renames, BlockEntityType ctor change, `blit` signature, ServerLevel-only methods (21.2+) | Mechanical but wide; the same "~700 files" surface as last time. |
| Worldgen/datapack schema drift across drops | All 35k generated JSONs must regenerate through re-ported providers; every provider signature change must be absorbed. |
| Java 21 → 25, Gradle 9.1, MDG bump | Build/CI churn; the JDK-daemon gotcha in HANDOFF gets re-verified. |

Realistic estimate: **60–80% of the original port's effort** (that one went 4,833 compile errors
→ 0 across ~680 files). Discounts: no obfuscation, our decompiled-reference workflow, the
verify.sh gates, 29 GameTests, the client-testing harness and PORTING_GUIDE all carry over — and
the review just eliminated 48 latent behavior bugs that would otherwise be re-found mid-port.
Premiums: the recipe-display and GUI-extraction rewrites are architectural, and on 26.2-beta the
target itself moves under you.

## Recommendation, sequenced

1. **Finish the 1.21.1 fix backlog first** (worldgen batch, alloy batch, P2 client polish).
   A behaviorally-correct, gate-covered 1.21.1 is the best possible launchpad; every fix ported
   forward is one less mystery in the next diff.
2. **Skip 26.2-beta.** Do not port onto a moving API. Re-check the channel in ~4–6 weeks;
   when `26.2.x.y` ships without `-beta` (and one or two bellwether mods land on it), re-assess.
3. **If updating in the near term, target 26.1.2 stable** — same modern family, where 2026 packs
   actually live, and every lesson transfers forward to 26.2 nearly for free.
4. **Plan the port as a project**: same orchestrated engineer/QA workstream, starting with a
   compile-error census on a branch, recipe system first, GUI extraction second.
5. **Journal**: accept Patchouli-less 26.x (existing graceful no-op), and decide separately
   whether to eventually replace it (e.g. static in-game book or web docs).

## Sources

- NeoForge 26.1 release notes (Java 25, GuiGraphicsExtractor, ItemStackTemplate, porting guidance): https://neoforged.net/news/26.1release/
- NeoForge releases index (line history 20.2 → 21.11): https://neoforged.net/categories/releases/
- NeoForge Maven (26.2 = beta-only, 26.1.2.9x stable): https://maven.neoforged.net/releases/net/neoforged/neoforge
- Version-numbering + channel guidance (reviewed 2026-07-27): https://mcreference.com/guides/neoforge-version-numbers-explained
- NeoForge 21.2 release notes (recipe overhaul, registry renames): https://neoforged.net/news/21.2release/
- Patchouli versions (ceiling 1.21.1): https://modrinth.com/mod/patchouli/versions
- JEI versions (26.x supported): https://modrinth.com/mod/jei/versions
- Ecosystem targets: ATM11 on 26.1.2 (Jul 2026), ATM10 on 1.21.1 (~20M DL): https://minecraft.how/blog/post/best-minecraft-modpacks-2026 · https://respawnhost.com/en/wiki/games/minecraft/minecraft-best-modpacks-2026/
