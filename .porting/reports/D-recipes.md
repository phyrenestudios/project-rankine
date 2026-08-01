# D recipes — DONE (24 files)

## MUST-FIX integration seams (orchestrator)
1. **AlloyingRecipe furnace discrimination**: old code branched on `inv instanceof AlloyFurnaceTile / InductionFurnaceTile`; RecipeWrapper erases that. New API: `matches(RecipeInput, Level, int type)` where 1=alloy furnace, 2=induction, 3=both. 2-arg `matches` falls back to type 3 and returns false when getTier()==0.
   → **AlloyFurnaceTile must call matches(input, level, 1); InductionFurnaceTile must call matches(input, level, 2).** Otherwise induction-only/alloy-only recipes cross-match.
2. **MixingRecipe.matches no longer checks the barrel fluid tank** (was `inv instanceof MixingBarrelTile`).
   → **MixingBarrelTile must itself check `getOutputFluidReq(input)` against its input tank.**
3. **CrushingRecipe tier table**: TierSortingRegistry is gone. `CrushingRecipe.registerTier(ResourceLocation, Tier, int level)` must be called during common setup BEFORE recipes load, for Rankine/other-mod tiers. Vanilla tiers pre-registered (existing JSONs only use minecraft:stone/iron/diamond/netherite, so they work as-is).
4. **Field-name check**: BeehiveOvenRecipe calls `RankineRecipeTypes.BEEHIVE.get()`; A2 registered id `beehive_oven`. Verify the FIELD name matches (BEEHIVE vs BEEHIVE_OVEN) and align.

## Contracts satisfied
- RecipeWrapper(InvWrapper(inv)) slot layout preserved: Crushing/Beehive/Stripping/Treetapping/AlloyModifier=slot0; Sluicing=0 input,1 tool; Crucible=0-3; Alloying=0-5; FusionFurnace=0,1 items + 3 gas; Mixing/RockGenerator iterate inv.size().
- ElementRecipe is `Recipe<SingleRecipeInput>` (matches uses inv.item()); AlloyingRecipe.generateResult declares SingleRecipeInput.
- JamRecipe(CraftingBookCategory) extends CustomRecipe — works with SimpleCraftingRecipeSerializer.
- All serializers: public no-arg ctor + public static final CODEC/STREAM_CODEC. `X.Serializer::new` registration works.
- RankineRecipeTypes generics: `RecipeType<ElementRecipe>` (SingleRecipeInput input), others `RecipeType<XRecipe>` (RecipeInput).
- AlloySmithingRecipe.Serializer was never registered in 1.18.2 and still isn't (vanilla RecipeType.SMITHING if wanted; INSTANCE exists).

## JSON compat
Preserved via `recipe/helper/RankineMapCodec` (decode → JsonObject → original fromJson verbatim). Exceptions:
- `"nbt"` on result/output items: only rankine keys mapped to components (comp, recipe, nameOverride, color); other NBT keys dropped. Helper also accepts vanilla {"id","count","components"}.
- AlloyCraftingRecipe encode emits one key letter per grid slot (decode unaffected; generated JSON more verbose).
- AlloyModifierRecipe modifier names now `rankine:alloy_modifier_<type>` (recipes no longer know their id at deserialization).

## Other API notes
- Ingredient.merge removed → local mergeIngredients in AlloyingRecipe.
- IShapedRecipe removed → AlloyCraftingRecipe keeps getRecipeWidth/Height as plain methods for JEI.
- Recipe self-ids removed → AlloyingRecipe/AlloyCraftingRecipe expose `bindId(ResourceLocation)`; lazily resolve from RecipeManager otherwise.
- AirDistillationRecipe.getBiomeList/getBiomeString/matchesDistillationRecipe/getDistillationResult now take RegistryAccess first arg (biomes are datapack).
- Random→RandomSource in getResults/getAtomizeResults/getPossibleResults/getSluicingResult/getForagingResult/generateRandomResult.
- Helpers: AlloyColorHelper reads RankineDataComponents.COLOR; FluidHelper on Attributes.GRAVITY + ResourceLocation AttributeModifier; BlockRecipeHelper on BuiltInRegistries.
