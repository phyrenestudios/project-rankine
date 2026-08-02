# C items + data components — DONE (102 files + init/RankineDataComponents + init/RankineArmorMaterials)

## Components registered (RankineDataComponents)
ALLOY_COMPOSITION(String), ALLOY_RECIPE(ResourceLocation), REGENERATE_ALLOY(Boolean), NAME_OVERRIDE(String),
GAS(String), STORED_ALLOY(String), COLOR(Integer), ALLOY_STATS(record AlloyStats, 13 numeric fields),
ALLOY_ELEMENTS(List<AlloyElement(id,percent)>), ALLOY_MODIFIERS(List<AlloyModifierEntry(name,value)>),
TEMPLATE_INFO(record), TEMPLATE_ENTRIES(List<TemplateEntry>), BUILDING_MODE(Integer), FORCE_TOOL/FORCE_ARMOR(String).

- IAlloySpecialItem#applyAlloyStats writes ALLOY_STATS + mirrors durability into vanilla DataComponents.MAX_DAMAGE.
- IAlloyTool#applyAlloyToolComponent also writes DataComponents.TOOL (deniesDrops(incorrect-tag) + minesAndDrops(mineableTag, miningSpeed), damagePerBlock=0 since alloy tools do their own calcDurabilityLoss).
- Attack damage/speed deliberately NOT baked into ATTRIBUTE_MODIFIERS — ItemAttributeModifierHandler supplies them per-query (1:1 with 1.18.2).

## SEAMS OWED (orchestrator)
1. **init/RankineItems**: `AlloyArmorItem(Holder<ArmorMaterial>, ArmorItem.Type, String comp, @Nullable ResourceLocation recipe, Properties)` — was (ArmorMaterial, EquipmentSlot, ...). Pass RankineArmorMaterials.ALLOY + matching ArmorItem.Type; no .durability() (dynamic).
2. **items/RankineArmorMaterials.java DELETED** → init/RankineArmorMaterials.java (already registered on mod bus). Names: BRIGANDINE, ALLOY, RUBBER, DIVING, CONDUIT_DIVING + *_DURABILITY_FACTOR. Vanilla armor: `new ArmorItem(RankineArmorMaterials.X, type, props.durability(type.getDurability(FACTOR)))`.
3. **RankineCreativeTabs** must add what the deleted fillItemCategory overrides did:
   - dynamic alloy variants via AlloyCustomHelper.getItemsFromAlloying / getItemsFromAlloyCrafting for alloy items whose defaultAlloyRecipe == null
   - the Curse-of-Vanishing totem stacks (11 totems)
   - the Silk-Touch glass cutter
   - the pre-cursed infusing totem
4. **entities agent**: SpearEntity, AlloyArrowEntity, RopeCoilArrowEntity ctors (1.21.1 arrows carry pickup/firedFromWeapon stacks).
5. **client agent**: alloy armor tint needs an ItemColor in RegisterColorHandlersEvent.Item (was DyeableArmorItem).
6. **blocks**: AlloyBlock + BuildingModeBlock should drop DataComponents.CUSTOM_DATA and use ALLOY_COMPOSITION/ALLOY_RECIPE/ALLOY_STATS and BUILDING_MODE.

## Behavior fixes (old bugs corrected, documented in-code)
- AlloyTemplateItem.getColor: 1.18.2 wrote "color" but read "Color" → templates were always white; now returns the stored color.
- AlloyTemplateItem.getName: the `new ResourceLocation("")` branch always threw; now degrades to the default lang key.
- IAlloyTieredItem.getKnockbackResistance: wrote float, read with getInt (always 0); now reads the real value.
- AlloyPickaxeItem ore-nugget bonus uses `c:` tags.
- Element ids moved to RecipeHolder in 1.21 → added IAlloyItem.getElementRecipeId(Level, ElementRecipe).

## Approximations flagged
Material.* → AlloyShovel STONE→BASE_STONE_*/Tags.Blocks.STONES; Knife VEGETABLE→SWORD_EFFICIENT; GlassCutter GLASS.
HardnessTesterItem: TierSortingRegistry gone → vanilla tiers only.
