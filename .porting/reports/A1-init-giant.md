# A1 init-giant — DONE (7 files)

RankineBlocks.java (1117 DeferredBlock, 414 Material sites converted), RankineItems.java (1938 DeferredItem),
RankineTags, RankineLists (0 changes), Config, ClientConfig (0 changes), RankineFoods.

## MapColor / Material mapping used (verified against real vanilla 1.21.1 Blocks.java)
- STONE(150) → .mapColor(explicit or STONE).instrument(BASEDRUM)
- METAL(19) → .mapColor(explicit or METAL)
- WOOD(16) → .mapColor(explicit or WOOD).instrument(BASS).ignitedByLava()
- PLANT(34)/REPLACEABLE_PLANT(15) → .mapColor(PLANT).pushReaction(DESTROY) (+.replaceable())
- SAND(7)/DIRT(17)/CLAY(6) → mapColor only
- GLASS(16)/BUILDABLE_GLASS(17) → .instrument(HAT)
- WATER(16) → .mapColor(WATER).replaceable().noCollission().liquid().pushReaction(DESTROY)
- WOOL(41) → .instrument(GUITAR).ignitedByLava()
- CLOTH_DECORATION(18) → .ignitedByLava()
- AIR(19) → mapColor stripped (chains already had .noCollission().air())
- ICE_SOLID(3) → .mapColor(ICE).instrument(CHIME)
- CAKE(2) → .forceSolidOn().pushReaction(DESTROY)
- BARRIER(2) → .mapColor(NONE); TOP_SNOW(5)/SNOW(1) → .mapColor(SNOW)
- GRASS(7) → .instrument(BANJO) (hay/sod/FULGURITE — preserved 1:1)
- 3 Material.ROCK/EARTH/IRON left in pre-existing commented-out dead code.

## CROSS-PACKAGE FIXES OWED (orchestrator must verify/fix)
1. **RankineArmorMaterials** (items agent): BRIGANDINE, DIVING, CONDUIT_DIVING, RUBBER must be `Holder<ArmorMaterial>`
   (DeferredHolder on Registries.ARMOR_MATERIAL) — 18 vanilla ArmorItem ctors now use (Holder<ArmorMaterial>, ArmorItem.Type, Properties).
2. **RankineToolMaterials** FLINT/ALLOY assumed unchanged `Tier` (used in createAttributes(Tier,float,float)).
3. **blocks/block_groups** owner: factory params `newBricksReg(name, MapColor.X)` / `newDripstoneReg(name, MapColor.X)`
   (15 sites) must now take MapColor, not MaterialColor. Also any field capturing `RankineBlocks.ITEMS.register(...)`
   (e.g. RankineWood.signItem, RankineWood.boat) must be retyped `DeferredItem<Item>`.
4. **ELEMENT_INDEXER_CONTAINER**: @ObjectHolder alias removed from RankineItems; consumers must use
   `RankineContainers.ELEMENT_INDEXER_CONTAINER.get()` — sites: items/indexer/ElementIndexerContainer.java,
   and client/ClientSetup.java (formerly ClientProxy).
5. ~30 dead @ObjectHolder MenuType/BlockEntityType aliases removed from RankineBlocks (zero references repo-wide;
   real registrations live in RankineContainers / RankineBlockEntityTypes).

## Behavior deltas
- RankineFoods: FoodProperties.Builder#meat() removed in 1.21.1 — flag dropped for TOFU, COOKED_TOFU, TUNA, COOKED_TUNA (nutrition/saturation preserved).
- Config: Biome.BiomeCategory removed — the 17 category strings inlined as literals, preserving the config's string-matching format.

## Known cosmetic
- Unused imports remain in RankineBlocks.java (MenuType, BlockEntityType, *Container) after @ObjectHolder removal — warnings only.
