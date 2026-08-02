# Datagen — DONE

## New files
- data/RankineDatapackProvider.java — DatapackBuiltinEntriesProvider wiring 5 bootstraps (modid "rankine"):
  ENCHANTMENT→RankineEnchantmentBootstrap, CONFIGURED_FEATURE→RankineConfiguredFeatures,
  PLACED_FEATURE→RankinePlacedFeatures, DAMAGE_TYPE→RankineDamageSources,
  NeoForgeRegistries.Keys.BIOME_MODIFIERS→RankineBiomeModifiers. Its getRegistryProvider() feeds all downstream providers.
- data/RankineEnchantmentTagsProvider.java, data/RankineDamageTypeTagsProvider.java
- data/RankineDataUtils.java — rl(Block/Item/Fluid/ItemLike) replacing ~250 removed getRegistryName() call sites
- data/RankineDataTags.java — c:storage_blocks/quartz (dropped from NeoForge Tags)

## Ported
DataGenerators (real net.neoforged.neoforge.data.event.GatherDataEvent, 14 providers, datapack provider first);
all 5 tag providers on the new ctor shapes; RankineLootTableProvider → thin LootTableProvider + BlockLootSubProvider
(generate(BiConsumer) overridden so only explicitly-built tables emit — vanilla's "every block needs a table"
validation would otherwise fail; table ids still come from Block#getLootTable(), unchanged);
RankineRecipesProvider (210 shaped / 122 shapeless / 60 cooking / 29 stonecutting given RecipeCategory;
SimpleCookingRecipeBuilder split into campfireCooking/smoking/smelting; 8 UpgradeRecipeBuilder →
SmithingTransformRecipeBuilder; SpecialRecipeBuilder.special(JamRecipe::new); forge:→c: ingot/nugget tags);
both recipe builders; BlockState/ItemModel providers (+ applyRenderTypes() implementing the full render-type inventory);
RankineAdvancementProvider (AdvancementHolder, AdvancementType, AdvancementRequirements.Strategy, enchantment
ResourceKey lookups); Lang + LootModifier providers.

## Tags generated
- Item rankine:enchantable/*: 21 tags (members derived by reflecting over RankineItems.ITEMS filtered by item class).
- Enchantment rankine:exclusive_set/*: 4 (atomize→fortune, quake→efficiency, endpoint→loyalty, stance→retaliate+retreat).
- Vanilla enchantment tags: 23 into in_enchanting_table/non_treasure/tradeable, 21 into treasure, 1 into curse —
  matching the E report exactly (antiquated excluded from non-treasure, present in treasure).
- Damage type: bypasses_armor ← uranium_shot + suffocating; bypasses_effects ← suffocating.
- forge:→c: conversion + renamed NeoForge common tags: COBBLESTONE→COBBLESTONES, GLASS→GLASS_BLOCKS, GRAVEL→GRAVELS,
  OBSIDIAN→OBSIDIANS, SAND→SANDS, SANDSTONE→SANDSTONE_BLOCKS, STONE→STONES, STRING→STRINGS, LEATHER→LEATHERS,
  GUNPOWDER→GUNPOWDERS; BlockTags.CARPETS→WOOL_CARPETS; POLAR_BEARS_SPAWNABLE_ON_IN_FROZEN_OCEAN→..._ALTERNATE.

## Accepted deltas
- Recipe-unlock advancements now land in advancements/.../recipes/misc/ (1.18 derived the folder from the creative tab;
  everything is RecipeCategory.MISC now). RECIPE IDS THEMSELVES ARE BYTE-IDENTICAL — no world-compat impact.
- The 8 smithing recipes pass Ingredient.EMPTY as the template (1.21 requires one; matches 1.18 behavior of no template).
- Render types applied as a post-pass matching generated models by block/<name> prefix, longest-name-first,
  first-claim-wins (needed for doors/trapdoors with suffixed models).
- Tags.Items.STORAGE_BLOCKS_QUARTZ gone → re-declared locally.
- Dropped 6 unreferenced private loot helpers that relied on removed APIs.
- RankineLootModifierProvider.start() still empty (1.18 generated no GLM JSONs).
