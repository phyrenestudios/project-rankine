# Shared brief for items sub-agents (1.18.2 Forge → 1.21.1 NeoForge port)

Read `C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md` first.
API reference (decompiled, grep for real signatures — NEVER guess):
`C:\Users\karl\Documents\Projects\mc-1.21.1-sources`

Golden rules: behavior 1:1; keep registry names; valid Java 21; verify every @Override against the
reference sources; ONLY edit the files assigned to you. If a constructor's arg list must change,
add `// PORT-TODO(init): ...` atop the class and list it in your final report.

## Data components (ALL per-stack NBT is GONE — stack.getTag()/getOrCreateTag() do not exist)
Use `com.cannolicatfish.rankine.init.RankineDataComponents` (already written). Mapping:

| Old NBT | Component (RankineDataComponents.X.get()) | Type |
|---|---|---|
| StoredAlloy.comp ("comp") | ALLOY_COMPOSITION | String |
| StoredAlloy.recipe | ALLOY_RECIPE | ResourceLocation |
| RegenerateAlloy | REGENERATE_ALLOY | Boolean |
| nameOverride | NAME_OVERRIDE | String |
| gas | GAS | String |
| color | COLOR | Integer (absent = 16777215 at most read sites — keep each site's old default!) |
| StoredAlloyStats {…} | ALLOY_STATS | RankineDataComponents.AlloyStats record (13 numeric fields, unwritten = 0) |
| Elements list | ALLOY_ELEMENTS | List<AlloyElement(String id, int percent)> |
| AlloyModifiers list | ALLOY_MODIFIERS | List<AlloyModifierEntry(String name, float value)> |
| StoredInfo (templates) | TEMPLATE_INFO | TemplateInfo record |
| StoredTemplate (templates) | TEMPLATE_ENTRIES | List<TemplateEntry> |
| building_mode / buildingMode | BUILDING_MODE | Integer |
| forceTool / forceArmor | FORCE_TOOL / FORCE_ARMOR | String |

Read: `stack.get(TYPE.get())` / `stack.getOrDefault(TYPE.get(), def)` / `stack.has(...)`,
write `stack.set(...)`, delete `stack.remove(...)`. Match the OLD defaults for absent data exactly
(e.g. old `getTag()==null ? 1 : ...getInt(...)`).

Core alloy interfaces (ALREADY PORTED — call them, do not re-implement):
- `IAlloyItem`: statics getAlloyComposition/getAlloyRecipe/getNameOverride/getAlloyModifiers/
  getColorNBT/addColorNBT/createDirectAlloyNBT/getSubtype; defaults createAlloyNBT/isAlloyInit/
  needsRefresh/setRefresh/getElementMap/getAlloyingRecipe(ResourceLocation, Level)/generateLangFromRecipe.
- `IAlloySpecialItem`: initStats(...), applyAlloyStats(stack, AlloyStats) (also mirrors MAX_DAMAGE +
  ensures DAMAGE), getAlloyStats(stack).
- `IAlloyTieredItem`: all createValueForX + getAlloyDurability/getAlloyMiningSpeed/getAlloyHarvestLevel/
  getAlloyEnchantability/getAlloyAttackDamage/getAlloyAttackSpeed/getCorrResist/getHeatResist/
  getToughness/getKnockbackResistance/calcDurabilityLoss(stack, level, living, boolean)/
  applyAlloyEnchantments(stack, level)/getAlloyTier(stack).
- `IAlloyTool`: initStats also sets vanilla TOOL component via `getAlloyMineableTag()` — override
  that in tool classes to return the tool's mineable tag.
- `IAlloyProjectile.getAlloyArrowDamage(stack)`, `IAlloyShield` (stats via ALLOY_STATS).

## API migration patterns (verified against the reference tree)
1. `appendHoverText(ItemStack, @Nullable Level, List<Component>, TooltipFlag)` →
   `appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)`.
   Where a Level was used, `context.level()` (@Nullable, NeoForge extension). Keep old @OnlyIn if present.
2. `stack.hurtAndBreak(n, living, e -> e.broadcastBreakEvent(SLOT/hand))` →
   `stack.hurtAndBreak(n, living, EquipmentSlot.MAINHAND)` (or `LivingEntity.getSlotForHand(hand)`).
3. Enchantment levels: `RankineEnchantments.X` are now `ResourceKey<Enchantment>` (same constant names).
   - stack-only check (no Level available): copy this private helper into the class:
     ```java
     private static int getEnchantmentLevel(ItemStack stack, net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> key) {
         for (var entry : stack.getTagEnchantments().entrySet()) {
             if (entry.getKey().is(key)) return entry.getIntValue();
         }
         return 0;
     }
     ```
   - with Level available you may also use `com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper.getLevel(level, KEY, stack)` (being created by the enchantments agent).
   - Vanilla keys renamed: `Enchantments.BLOCK_FORTUNE`→`FORTUNE`, `BLOCK_EFFICIENCY`→`EFFICIENCY`; all are ResourceKeys now.
   - `stack.enchant(Holder<Enchantment>, int)`; `EnchantmentHelper.getItemEnchantmentLevel(Holder, stack)`.
   - `EnchantmentHelper.getRiptide/getLoyalty/...` GONE (data-driven) — for spear/trident logic copy what
     1.21.1 `TridentItem.java` does (`EnchantmentHelper.getTridentSpinAttackStrength(stack, player)` etc).
4. `canApplyAtEnchantingTable(ItemStack, Enchantment)` →
   `public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)` (IItemExtension);
   compare with `enchantment.is(Enchantments.SWEEPING_EDGE)` etc.
5. `getItemEnchantability(ItemStack)` → `getEnchantmentValue(ItemStack)` (IItemExtension).
   `getMaxDamage(ItemStack)` override still exists (IItemExtension) — keep it.
6. `ForgeRegistries.ITEMS/BLOCKS/...` → `BuiltInRegistries.ITEM/BLOCK/...`
   (`net.minecraft.core.registries.BuiltInRegistries`); `.getValue(rl)` → `.get(rl)`;
   `obj.getRegistryName()` → `BuiltInRegistries.X.getKey(obj)`.
7. `TierSortingRegistry.isCorrectTierForDrops(tier, state)` → `!state.is(tier.getIncorrectBlocksForDrops())`.
   Old harvest-level ints: use `RankineToolMaterials.incorrectBlocksForLevel(level)` mapping
   0→INCORRECT_FOR_WOODEN_TOOL … 4+→INCORRECT_FOR_NETHERITE_TOOL. `RankineToolMaterials.getLevel()` is
   now `getHarvestLevel()`.
8. `Material.*` is GONE. Approximate with tags and leave `// PORT-TODO: Material.X approximation`:
   stone→`BlockTags.BASE_STONE_OVERWORLD || BASE_STONE_NETHER`, vegetable→`BlockTags.SWORD_EFFICIENT`,
   wood→`BlockTags.LOGS`/`MINEABLE_WITH_AXE` as fits the check.
9. `MobType.UNDEAD` → `target.getType().is(EntityTypeTags.UNDEAD)`; `DamageSource.GENERIC` →
   `level.damageSources().generic()`; `entity.level` → `entity.level()`; `player.abilities` →
   `player.getAbilities()`; `isOnGround()` → `onGround()`; `new BlockPos(double,double,double)` →
   `BlockPos.containing(...)`; `blockpos.offset(double...)` → use explicit `getX()+0.5` etc.
10. `getUseDuration(ItemStack)` → `getUseDuration(ItemStack, LivingEntity)`.
11. `fillItemCategory`/`allowdedIn` are GONE — delete the override and add
    `// PORT-TODO(init): creative-tab entries (AlloyCustomHelper variants) must move to RankineCreativeTabs`.
12. Recipes: `RankineRecipeTypes.X` will be `DeferredHolder` — call `.get()`.
    `getAllRecipesFor(TYPE.get())` returns `List<RecipeHolder<R>>` (use `.value()`);
    `byKey(rl)` returns `Optional<RecipeHolder<?>>`; `getRecipeFor(TYPE.get(), input, level)` returns
    `Optional<RecipeHolder<R>>`. Where old code passed a `SimpleContainer` of ONE stack, use
    `new SingleRecipeInput(stack)`; multi-slot uses stay as the recipe agent defines — if unclear, keep
    the old shape and add `// PORT-TODO(recipes): input wrapper`.
13. `IForgeShearable` → `net.neoforged.neoforge.common.IShearable` (check exact method signatures in
    the reference; onSheared has no fortune parameter anymore).
14. `Vanishable` interface GONE — remove.
15. Old Forge `getShareTag`/`readShareTag` → delete entirely (components auto-sync).
16. Tool item constructors: vanilla tool ctors are now `(Tier, Properties)` with attributes baked via
    `Properties.attributes(SwordItem/DiggerItem.createAttributes(tier, dmgFloat, speed))` and TOOL
    component auto-added. KEEP the Rankine classes' external ctor arg lists unchanged (init agent
    expects them) and adapt the super() call inside.
17. Entities (SpearEntity, RopeCoilArrowEntity, etc.) belong to another agent. Update call sites to
    plausible 1.21.1 shapes based on reference `AbstractArrow`/`Arrow`/`ThrownTrident`; if the Rankine
    entity ctor can't be known, keep args as-is + `// PORT-TODO(entities): verify ctor`.
18. `RandomSource` replaces `java.util.Random` in all vanilla positions (`level.getRandom()`).
19. `SoundEvents.X` some are now `Holder<SoundEvent>` — `level.playSound` overloads accept both; check.
20. Attribute modifier events stay in events package (NOT yours). Alloy attack/armor attributes are
    intentionally NOT baked into ATTRIBUTE_MODIFIERS on stacks.
21. `Item.Properties().setISTER`/`IItemRenderProperties`/`initializeClient` custom renderers → leave
    `// PORT-TODO(client): <item> needs IClientItemExtensions for <what>` and keep renderer class
    references in a comment.
22. Colors: `DyeableLeatherItem` GONE. Rankine "color" NBT → `RankineDataComponents.COLOR`
    (or `DataComponents.DYED_COLOR` where vanilla dyeing behavior is intended — AlloyArmorItem is
    handled elsewhere; for your files use COLOR).
23. `player.displayClientMessage`, `playSound` mostly unchanged; `Component.literal/translatable` unchanged.
24. `player.playSound(soundEvent, vol, pitch)` — playSound(SoundEvent,...) still exists on Entity but
    check reference; RankineSoundEvents entries are `DeferredHolder<SoundEvent, SoundEvent>` → `.get()`.

Final report requirements: list per file — status, ctor signature changes (for the init agent),
every PORT-TODO you left, and any new components you believe are missing (do NOT edit
RankineDataComponents yourself; report instead).
