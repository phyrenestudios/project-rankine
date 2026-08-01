# A2 init-misc + fluids report (condensed contracts)

Status: DONE. All init/ misc files + fluids ported. RankineEnchantmentTypes.java and ClientProxy.java deleted; client/ClientSetup.java created (item properties, 11 menu screens, render-layer PORT-TODO inventory).

## Contracts other agents / compile loop must honor
1. **RecipeType ids registered** (RankineRecipeTypes.RECIPE_TYPES, RecipeType.simple): alloying, crushing, beehive_oven, sluicing, element, evaporation, crucible, rock_generator, fusion_furnace, treetapping, stripping, foraging, air_distillation, mixing, alloy_modifier. Constant names unchanged; callers use `.get()`.
2. **JamRecipe**: registered via `SimpleCraftingRecipeSerializer<>(JamRecipe::new)` → JamRecipe ctor must accept `CraftingBookCategory` (verify vs recipes agent output in compile loop).
3. **ElementRecipe** assumed `Recipe<SingleRecipeInput>` (used in RankineRecipes.generateAlloyString).
4. **RankineAttributes**: SPEAR_RANGE_MODIFIER / KNIFE_RANGE_MODIFIER / REACH_MODIFIER changed type UUID→ResourceLocation (items/events agents must use as modifier ids). ForgeMod attributes → vanilla: REACH_DISTANCE→BLOCK_INTERACTION_RANGE, ATTACK_RANGE→ENTITY_INTERACTION_RANGE, STEP_HEIGHT_ADDITION→STEP_HEIGHT, ENTITY_GRAVITY→GRAVITY. Old rankine:mercury_mod split → rankine:mercury_speed + rankine:mercury_health.
5. **RankineFluids** fields now `DeferredHolder<Fluid, FlowingFluid>` — all callers need `.get()`. 16 FluidTypes + 32 fluids, names byte-identical.
6. **SurfRodModifier** must expose `public static final MapCodec<SurfRodModifier> CODEC` (events agent).
7. **RankineRemappings**: MissingMappingsEvent does NOT exist in NeoForge 21.1 → uses BuiltInRegistries.ITEM/BLOCK.addAlias in FMLCommonSetupEvent handler within that file.

## For DATAGEN agent
- Damage type tags: rankine:uranium_shot → minecraft:bypasses_armor; rankine:suffocating → bypasses_armor + bypasses_effects. Damage types bootstrap: RankineDamageSources.bootstrap (keys: columns, cannonball, uranium_shot, suffocating).
- Render-type inventory (cutout/cutout_mipped/translucent lists) in ClientSetup PORT-TODO(datagen-rendertype) — apply via BlockStateProvider .renderType().
- Enchantable tags possibly missing vs old categories: HOE (plain hoes — vanilla tags may suffice), ALLOY_SHOVEL, PEWTER (fold into alloy_tool) — cross-check with E report.

## For CLIENT agent
- BER registrations needed: RANKINE_SIGN→SignRenderer, PEDESTAL→PedestalRenderer (PORT-TODO(client) in RankineBlockEntityTypes).
- IClientFluidTypeExtensions map: each fluid type → rankine:block/<name>_still/_flow/_overlay, tint 0xFFFFFFFF; names: liquid_mercury, sap, maple_sap, latex, juglone, resin, aqua_regia, carbon_disulfide, hexafluorosilicic_acid, hydrobromic_acid, gray_mud, red_mud, sulfuric_acid, black_liquor, green_liquor, white_liquor.
- Menu→screen pairs implemented in ClientSetup (11): alloy_furnace, crucible, mixing_barrel, induction_furnace, fusion_furnace, evaporation_tower, gas_condenser→GasBottlerScreen, battery_charger, material_testing_table, template_table, element_indexer.

## Open PORT-TODOs from A2
- RankineRecipes.registerPredicates: 5 custom ItemPredicates need reimplementation as ItemSubPredicates (advancements/events agent or compile loop).
- ClientSetup PORT-TODO(items): building_mode default storage + shulker-vacuum "gas" component location — align with items agent components.
