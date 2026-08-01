# Agent resume roster (session limit hit 2026-08-01 ~6:05pm ET, resets 6:20pm ET)

418 files already changed and committed at checkpoint `3c6f3dc6a`. These agents died mid-work but retain full
context in their transcripts — resume with SendMessage using the agentId, NOT a fresh Agent call.

## COMPLETED (do not resume)
- E-enchantments — report saved to .porting/reports/E-enchantments.md
- A2-init-misc + fluids — report saved to .porting/reports/A2-init-misc.md
- machines sub-teams: batterycharger/crucible/ovens; distillation/evap/fusion/gasbottler; asphalt/building/cauldron/gas/states; plants (22); induction/laser/mixing/mtt; small BEs + pedestal/tap

## RESUME THESE (agentId → what to tell them)
| agentId | Scope | Resume message |
|---|---|---|
| a188aeb65211abd75 | A1 init-giant (RankineBlocks.java last) | Continue: finish RankineBlocks.java port (Material→Properties, RegistryObject→DeferredBlock, drop PlantType args from RankinePlantBlock/RankineDoublePlantBlock ctors), verify all 7 owned files, produce final report |
| a5b5d23bb50b23f79 | RankineBlocks.java sub-agent of A1 | (child of A1 — resume A1 first; only resume this if A1 asks) |
| a9ea5cfad88163dba | B1 blocks top-level (90) | Continue porting remaining top-level blocks/*.java; then final report |
| a86d6bf4cfaf11ee9 | B1 sub-agent: block group A (13) | child of B1 |
| afbe01157b3fda14d | B1 sub-agent: block group B (13) | child of B1 |
| ab1b59828ba889422 | B1 sub-agent: block group C (13) | child of B1 |
| aa70c51593eabb617 | B2 machines parent (consolidation) | Verify all blocks/ subdirs ported, consolidate PORT-CAP list, final report |
| a77875f22efccd382 | C items + data components | Continue items/ port (was mid alloy tools); then final report w/ component design |
| a3e9b178c2b3a7845 | C sub-agent: misc root items | child of C |
| a625afbb753256957 | D recipes (18 types) | Files written; was verifying API assumptions against reference sources. Finish verification + final report |
| acaa03cf38ec732ed | F worldgen | Continue: was writing custom placement filter + type registry; then bootstraps + biome modifiers + final report |
| a529b2e3f3f390515 | G events/network/caps/entities | Continue: was on SpearEntity, SphericalExplosion, goals; then payloads/attachments/caps wiring + final report |
| a109b47c1ef3f4f78 | H client renders | Continue renderers + RankineClientEvents + final report |

## Resume procedure
Send each parent agent (A1, B1, B2, C, D, F, G, H) a SendMessage: "Session limit is lifted. Continue exactly where
you left off. If you delegated to sub-agents, resume/verify their scope too. Finish with the final report specified
in your original instructions." Children resume themselves only if the parent asks.

## After all resume + complete
Follow .porting/RELAUNCH_PLAN.md "After all agents finish": wire PORT-TODO(main) registrations, wave-2 (JEI + datagen),
compile loop, tests, gates.
