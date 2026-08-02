# JEI integration — DONE (24 files, JEI 19.21.0.247)

Verified against the real API jars (jei-1.21.1-common-api + jei-1.21.1-neoforge-api) with javap, not guesswork.

## API mapping (old → new)
- mezz.jei.api.forge.ForgeTypes.FLUID_STACK → mezz.jei.api.neoforge.NeoForgeTypes.FLUID_STACK
  (the neoforge-api jar contains ONLY that class; everything else lives in common-api).
- IRecipeCategory.getUid()/getRecipeClass() REMOVED → `RecipeType<T> getRecipeType()` returning the constant from JEIRankinePlugin.
- IRecipeCategory.setRecipe(...) is now ABSTRACT (was a default no-op) → all `IRecipeCategory.super.setRecipe(...)` calls
  removed from 8 categories (they were no-ops; no behavior change).
- draw(T, IRecipeSlotsView, PoseStack, double, double) → draw(..., GuiGraphics, ...); Font.draw(PoseStack,...) →
  GuiGraphics.drawString(Font, ...); IDrawable(Animated).draw(PoseStack,x,y) → .draw(GuiGraphics,x,y).
- ForgeRegistries does not exist in NeoForge 21.1 → BuiltInRegistries.BLOCK/ITEM.
- TierSortingRegistry removed → CrushingRecipeCategory uses CrushingRecipe.nameOfTier(Tier).
- Enchantment is a datapack registry record → AlloyingRecipeCategory resolves via
  level.registryAccess().registryOrThrow(Registries.ENCHANTMENT) and uses en.value().description() (a Component)
  instead of I18n.get(getDescriptionId()).
- RecipeManager#getAllRecipesFor returns List<RecipeHolder<T>>; Recipe no longer has getId(). Getters return
  List<RecipeHolder<T>>; sorting is by RecipeHolder::id (preserves the old display order) then unwrapped via ::value.

## Notes
- Nothing dropped; every recipe category still renders.
- No mezz.jei imports leak outside client/integration/jei/** (verified repo-wide) — JEI stays compileOnly/optional.
- Two inline PORT-TODO(jei) comments document the enchantment-registry and tier-name substitutions.
