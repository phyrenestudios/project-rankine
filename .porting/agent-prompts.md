# Saved agent prompts — relaunch with Agent tool (subagent_type: general-purpose), prompt = section body verbatim.
# Model per RELAUNCH_PLAN.md. Sections delimited by `=== AGENT: <id> ===`.

=== AGENT: A1-init-giant (model: sonnet) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md — it defines all conventions.
API reference: complete decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources — grep/read these for any signature you're unsure of. Never guess an API.

A mechanical rename pass already ran (forge→neoforged packages, Component.literal, ResourceLocation.parse/fromNamespaceAndPath, ModConfigSpec, @EventBusSubscriber). Leftover broken imports like `net.neoforged.neoforge.registries.ForgeRegistries`, `RegistryObject`, `ObjectHolder` are yours to fix per the guide.

YOU OWN ONLY THESE FILES (do not modify anything else):
- src/main/java/com/cannolicatfish/rankine/init/RankineBlocks.java (1580 lines)
- src/main/java/com/cannolicatfish/rankine/init/RankineItems.java (2112 lines)
- src/main/java/com/cannolicatfish/rankine/init/RankineTags.java
- src/main/java/com/cannolicatfish/rankine/init/RankineLists.java
- src/main/java/com/cannolicatfish/rankine/init/Config.java
- src/main/java/com/cannolicatfish/rankine/init/ClientConfig.java
- src/main/java/com/cannolicatfish/rankine/init/RankineFoods.java

Key work:
1. RankineBlocks: `DeferredRegister.create(ForgeRegistries.BLOCKS, ...)` → `DeferredRegister.createBlocks(MODID)`; `RegistryObject<X>` → `DeferredBlock<X>`; ITEMS register in this file (if present) → `DeferredRegister.Items`. The big one: `Material` is GONE. Every `Properties.of(Material.X)` / `Properties.of(Material.X, MaterialColor.Y)` must become `BlockBehaviour.Properties.of()` with `.mapColor(MapColor.Y)` plus behavior flags the old Material implied: STONE→`.mapColor(...).instrument(NoteBlockInstrument.BASEDRUM)`, METAL→`.mapColor(MapColor.METAL)`, WOOD→`.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()`, PLANT/REPLACEABLE_PLANT→`.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY)` (+`.replaceable()` for replaceable plants), LEAVES→`.mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY)`, SAND/DIRT/CLAY→mapColor only, GLASS/ICE→`.mapColor(...)`+(instrument HAT for glass), WATER/LAVA (LiquidBlocks)→`.mapColor(...).replaceable().noCollission().liquid().pushReaction(PushReaction.DESTROY)`. `MaterialColor` → `MapColor` (constant names mostly identical — check MapColor.java in reference sources). `Properties.copy(...)` → `Properties.ofFullCopy(...)`. `.harvestLevel/.harvestTool` if present → delete (tags handle it). `requiresCorrectToolForDrops`, `strength`, `sound`, `noOcclusion`, `randomTicks`, `lightLevel` unchanged. SoundType constants mostly unchanged.
2. RankineItems: `DeferredRegister.createItems(MODID)`; `RegistryObject<Item>` → `DeferredItem<Item>` (net.neoforged.neoforge.registries.DeferredItem). `.tab(...)` calls were already stripped. Tool/armor constructors changed (see guide Items section) — but item CLASS definitions live in items/ package owned by another agent; here just fix constructor ARGUMENTS per the new signatures: `new SwordItem(tier, dmg, speed, props)` → `new SwordItem(tier, props.attributes(SwordItem.createAttributes(tier, dmg, speed)))`; same for Pickaxe/Axe/Shovel/Hoe (AxeItem/HoeItem createAttributes take (Tier, float, float)). Custom Rankine tool classes (AlloySwordItem etc.): KEEP their existing constructor arg lists as-is (that agent will match). For vanilla-class registrations with removed ctors, adapt. `Rarity`, `stacksTo`, `durability`, `food` unchanged. `CreativeModeTab` references should be gone; if any remain, delete them.
3. RankineTags: `BlockTags.create(rl)` / `ItemTags.create(rl)` → `TagKey.create(Registries.BLOCK, rl)` etc, or keep BlockTags.create if it exists in NeoForge (check reference). Forge tags namespace changed `forge:` → `c:` — for tags the MOD creates in its own namespace keep `rankine:`; for references to forge namespace tags in this file, use `c:` equivalents (check net/neoforged/neoforge/common/Tags.java in reference for the new canonical names).
4. Config/ClientConfig: ModConfigSpec rename was mechanical — verify builder API compiles (push/pop/comment/define unchanged). Fix any `.worldRestart()` etc. if removed (check ModConfigSpec source).
5. RankineFoods: FoodProperties.Builder — `.effect(Supplier<MobEffectInstance>, float)` in 1.21.1; MobEffectInstance now takes `Holder<MobEffect>` — RankineMobEffects entries are DeferredHolder which IS a Holder; vanilla MobEffects constants are already Holders in 1.21.1. `.alwaysEat()` → `.alwaysEdible()` if renamed (check FoodProperties.Builder source).
6. RankineLists: mostly List<Block>/List<Item> of references — should need few changes; fix any typing issues from DeferredBlock/DeferredItem (`.get()` unchanged).

Rules:
- Do NOT run gradle. Ensure syntactically valid Java 21; only use APIs present in the reference sources.
- Registry names (strings) must not change.
- Port 1:1; no feature removal. Where impossible, `// PORT-TODO(init): why`.
- If another package must change to match (e.g. a custom item class ctor), leave PORT-TODO and list it in your report.

Final report: files done, MapColor mapping decisions, list of custom classes whose constructors you assumed (with assumed signatures), all PORT-TODOs.

=== AGENT: A2-init-misc (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources — grep these for real signatures; never guess.

A mechanical rename pass already ran (packages, Component.literal, ResourceLocation.parse/fromNamespaceAndPath, ModConfigSpec, @EventBusSubscriber). Fix leftovers per guide.

YOU OWN ONLY THESE FILES:
- init/RankineBlockEntityTypes.java (move its client `registerBlockEntityRenders` body into a `// PORT-TODO(client)` comment block for the client agent; the file keeps only the DeferredRegister + registration)
- init/RankineContainers.java (MenuType registration → IMenuTypeExtension.create for data-carrying menus; plain MenuType otherwise. Also note: screens registration moves to client agent — leave PORT-TODO listing menu→screen pairs)
- init/RankineEntityTypes.java (EntityType.Builder API; attribute registration via EntityAttributeCreationEvent if used)
- init/RankineMobEffects.java (MobEffect registration; MobEffectCategory; attribute modifiers on effects take ResourceLocation ids in 1.21.1)
- init/RankinePotions.java (Potion registration: `new Potion(new MobEffectInstance(holder, dur))`; brewing recipes NOT here)
- init/RankinePOIs.java (PoiType: `new PoiType(states, ticketCount, searchDistance)`; register via DeferredRegister on Registries.POINT_OF_INTEREST_TYPE)
- init/RankineVillagerProfessions.java (VillagerProfession ctor takes name, Predicate<Holder<PoiType>> heldJobSite/acquirableJobSite, ...; check reference source)
- init/RankineSoundEvents.java (SoundEvent.createVariableRangeEvent)
- init/RankineAttributes.java (DeferredRegister<Attribute> on Registries.ATTRIBUTE; adding to players via EntityAttributeModificationEvent — put that handler in this file as @EventBusSubscriber(modid="rankine", bus=MOD))
- init/RankineRecipeTypes.java (create if needed: DeferredRegister<RecipeType<?>> named RECIPE_TYPES with RecipeType.simple(rl) for every recipe type currently created via `RecipeType.register` or deprecated patterns; keep exact type ids. ProjectRankine.java already calls RankineRecipeTypes.RECIPE_TYPES.register(modBus))
- init/RankineRecipeSerializers.java (DeferredRegister<RecipeSerializer<?>>; serializer classes themselves are ported by another agent — reference them as-is)
- init/RankineRecipes.java (dispenser behaviors: DispenserBlock.registerBehavior unchanged-ish; potion recipes: BrewingRecipeRegistry is GONE in 1.21.1 → use RegisterBrewingRecipesEvent (net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent) — restructure as event handler; predicates: ItemPredicate/registry changes — check what registerPredicates does and port or PORT-TODO)
- init/RankineRemappings.java (RegistryEvent.MissingMappings → net.neoforged.neoforge.registries.MissingMappingsEvent; handler class @EventBusSubscriber(modid="rankine", bus=...check which bus in reference); event.getAllMappings(Registries.ITEM) etc.)
- init/VanillaIntegration.java (ComposterBlock.COMPOSTABLES; FireBlock flammability — check current access patterns in 1.21.1; AxeItem.STRIPPABLES etc. maps)
- init/RankineDamageSources.java (convert to: ResourceKey<DamageType> constants + a static helper `DamageSource source(Level, ResourceKey<DamageType>)` using level.registryAccess(); also keep a bootstrap method `static void bootstrap(BootstrapContext<DamageType> ctx)` registering each DamageType(name, exhaustion) for datagen)
- init/RankineEnchantmentTypes.java (EnchantmentCategory is GONE — this file defined custom categories; DELETE it and note replacement item tags needed, e.g. rankine:enchantable/alloy_tools — list them in report + PORT-TODO)
- init/RankineLootModifiers.java (DeferredRegister of MapCodec<? extends IGlobalLootModifier> on NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS; loot modifier classes ported by another agent — they expose `public static final MapCodec<X> CODEC`)
- init/ClientProxy.java: DELETE this file. Move its entire useful content into a NEW file src/main/java/com/cannolicatfish/rankine/client/ClientSetup.java annotated @EventBusSubscriber(modid="rankine", bus=EventBusSubscriber.Bus.MOD, value=Dist.CLIENT) — item properties registration (ItemProperties.register) in FMLClientSetupEvent enqueueWork, menu screens in RegisterMenuScreensEvent (screens exist in client/ package or blocks subpackages; reference them, do not edit them), render layers: prefer leaving ItemBlockRenderTypes.setRenderLayer calls with PORT-TODO(datagen-rendertype) comments if that API is gone (check reference).
- fluids/** (19 files) and init/RankineFluids.java: full fluid rework per guide. RankineFluids gets: `FLUIDS` (DeferredRegister<Fluid>) registering all source+flowing fluids as BaseFlowingFluid.Source/Flowing with BaseFlowingFluid.Properties, and `FLUID_TYPES` (DeferredRegister<FluidType> on NeoForgeRegistries.Keys.FLUID_TYPES) — one FluidType per fluid with density/viscosity/sound properties. The old per-fluid classes in fluids/ hold FLUID_STILL/FLUID_FLOWING/OVERLAY ResourceLocations — keep those constants accessible for the client agent (IClientFluidTypeExtensions is registered by the client agent, NOT you; leave PORT-TODO(client) listing fluid→texture mappings). Fluid registry names MUST stay identical (from `git show origin/1.18.X:src/main/java/com/cannolicatfish/rankine/ProjectRankine.java` registerFluids): liquid_mercury, liquid_mercury_flowing, sap, flowing_sap, maple_sap, flowing_maple_sap, latex, flowing_latex, juglone, flowing_juglone, resin, flowing_resin, aqua_regia, flowing_aqua_regia, carbon_disulfide (viscosity 6000), flowing_carbon_disulfide, hexafluorosilicic_acid, flowing_hexafluorosilicic_acid, hydrobromic_acid, flowing_hydrobromic_acid, gray_mud, flowing_gray_mud, red_mud, flowing_red_mud, sulfuric_acid, flowing_sulfuric_acid, black_liquor, flowing_black_liquor, green_liquor, flowing_green_liquor, white_liquor, flowing_white_liquor. Buckets/blocks per old Properties (bucket + block suppliers from RankineItems/RankineBlocks).

Rules:
- Do NOT modify files outside your list except creating the named new files. Do NOT touch ProjectRankine.java, RankineBlocks/Items/Tags/Lists/Config (another agent owns them), recipe/, blocks/, items/, world/ packages.
- Do NOT run gradle. Syntactically valid Java 21; APIs must exist in reference sources.
- Registry name strings must not change.
- `// PORT-TODO(area): ...` for anything cross-package.

Final report: per-file status, the menu→screen pair list, fluid texture mapping list, item tags needed to replace enchantment categories, all PORT-TODOs.

=== AGENT: B1-blocks-toplevel (model: sonnet) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources — grep for real signatures; never guess. E.g. before porting a Block subclass method, open net/minecraft/world/level/block/state/BlockBehaviour.java and the closest vanilla analog block.

A mechanical rename pass already ran (packages, Component.literal, ResourceLocation.*, @EventBusSubscriber).

YOU OWN: every *.java DIRECTLY in src/main/java/com/cannolicatfish/rankine/blocks/ (the ~90 top-level files, NOT subdirectories).

Common changes you will hit (details in guide):
- `Block#use(...)` → `useWithoutItem` / `useItemOn` split (new signatures + InteractionResult/ItemInteractionResult).
- `java.util.Random` → `RandomSource` in overrides (randomTick, animateTick, tick, getSeed...).
- getDrops/loot: LootParams.Builder; `LootContextParams`.
- `Material`-based Properties in constructors → `BlockBehaviour.Properties.of()` + mapColor/instrument/pushReaction etc. (see guide table).
- Overrides that gained/lost params: verify EVERY @Override against reference source. Be rigorous.
- BonemealableBlock: verify `isValidBonemealTarget` signature in reference.
- `PlantType` (forge) is GONE in NeoForge → remove getPlantType overrides; `canSustainPlant` API changed (TriState) — adapt or PORT-TODO.
- `SimpleWaterloggedBlock` unchanged. `ToolAction`→`ItemAbility` already renamed by sed; verify imports.
- CropBlock: `getBaseSeedId` returns ItemLike — check.

Rules:
- ONLY modify your ~90 files. Cross-package needs → `// PORT-TODO(x)` + report.
- No gradle. Valid Java 21. Registry names/behavior unchanged.
- Port 1:1, no refactors beyond what APIs force.

Final report: files ported, tricky rewrites (list file + what changed), PORT-TODOs.

=== AGENT: B2-blocks-machines (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources — grep for real signatures; never guess. Great analogs: net/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity.java, net/minecraft/world/inventory/AbstractFurnaceMenu.java.

A mechanical rename pass already ran (packages, Component.literal, ResourceLocation.*, @EventBusSubscriber).

YOU OWN: every *.java under ALL SUBDIRECTORIES of src/main/java/com/cannolicatfish/rankine/blocks/ (alloyfurnace, alloys, asphalt, batterycharger, beehiveoven, block_groups, buildingmodes, cauldrons, charcoalpit, crucible, dac, distillationtower, evaporationtower, fusionfurnace, gasbottler, gases, gasvent, groundtap, inductionfurnace, laserquarry, mixingbarrel, mtt, particleaccelerator, pcf, pedestal, plants, sedimentfan, signs, states, tap, templatetable, tilledsoil — ~144 files). NOT the top-level blocks/*.java files (another agent owns those).

This is the machine/BlockEntity/Menu-heavy area. Key changes (details in guide):
- BlockEntity: `load(CompoundTag)` → `loadAdditional(CompoundTag, HolderLookup.Provider)`; `saveAdditional(CompoundTag, HolderLookup.Provider)`; `getUpdateTag(HolderLookup.Provider)`; ContainerHelper.loadAllItems/saveAllItems take provider.
- CAPABILITIES REWORK: remove `getCapability`/`LazyOptional`/`invalidateCaps` overrides entirely. Instead each BE exposes plain accessors: `public IItemHandler getItemHandler(@Nullable Direction side)` returning the appropriate wrapped/sided handler (preserve old sided logic!). Central registration will be done by the events agent via RegisterCapabilitiesEvent — add `// PORT-CAP: <BEType> -> getItemHandler` comment on each accessor so they can wire it. Keep ItemStackHandler/IItemHandler (net.neoforged.neoforge.items) usage otherwise.
- Menus: constructor pattern `(int windowId, Inventory inv, FriendlyByteBuf extraData)` for client factory; `ContainerLevelAccess`/`DataSlot`/`ContainerData`/`stillValid`/SlotItemHandler unchanged.
- Screens in these packages: `render(GuiGraphics, int, int, float)`; blit via GuiGraphics; `renderBg(GuiGraphics, float, int, int)`; `renderLabels(GuiGraphics, int, int)`; Button.builder(...).bounds(...).build().
- Recipes referenced by machines: `level.getRecipeManager().getRecipeFor(RankineRecipeTypes.X.get(), input, level)` returns Optional<RecipeHolder<R>>; construct RecipeInput — use net/neoforged/neoforge/items/wrapper/RecipeWrapper(itemHandler) where old code passed the BE/inventory itself. Assume recipe classes keep their old public getters plus new `matches(RecipeInput, Level)`.
- `NetworkHooks.openScreen(player, ...)` → `serverPlayer.openMenu(menuProvider, buf -> ...)`.
- Random→RandomSource; use()→useWithoutItem/useItemOn; Material→Properties.of() per guide.
- signs/: vanilla sign API changed a lot (front/back text). Port carefully against reference; PORT-TODO if blocked.
- block_groups/ (RankineWood etc.): WoodType.register signature: `new WoodType(name, BlockSetType)`; BlockSetType.register too. Check reference WoodType.java.

Rules:
- ONLY your files. Cross-package needs → `// PORT-TODO(x)` + report.
- No gradle. Valid Java 21. Every @Override verified against reference sources.
- Port behavior 1:1 (cook times, slot logic, sided access rules identical).

Final report: files ported, list of PORT-CAP accessors (BE class → accessor), tricky areas, PORT-TODOs.

=== AGENT: C-items (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources — grep for real signatures; never guess. Key files: net/minecraft/world/item/SwordItem.java, DiggerItem.java, ArmorItem.java, ArmorMaterials.java, component/DataComponents.java, component/Tool.java, ItemAttributeModifiers.java, net/neoforged/neoforge/common/extensions/IItemExtension.java.

A mechanical rename pass already ran (packages, Component.literal, ResourceLocation.*).

YOU OWN:
- ALL of src/main/java/com/cannolicatfish/rankine/items/** (102 files, including alloys/, elements/, indexer/, tools/, totems/)
- src/main/java/com/cannolicatfish/rankine/init/RankineDataComponents.java (already created with core components — EXTEND as needed; keep style)
- NEW file init/RankineArmorMaterials.java if armor materials need registration (DeferredRegister<ArmorMaterial> on Registries.ARMOR_MATERIAL)
- src/main/java/com/cannolicatfish/rankine/util/alloys/** and util files that are pure alloy logic.

THE BIG JOB — ItemStack NBT → Data Components:
1. Inventory every NBT usage in your files (grep getTag/getOrCreateTag/CompoundTag in items/).
2. Design components in RankineDataComponents (records with Codec + StreamCodec where compound; e.g. ALLOY_STATS record with all numeric stats).
3. Replace reads/writes with stack.get/set/remove. Keep semantics EXACTLY (defaults when absent must match old getInt-etc. defaults).
4. Dynamic alloy tool behavior: when composition/stats are applied to a stack, ALSO set vanilla components: DataComponents.MAX_DAMAGE, DataComponents.ATTRIBUTE_MODIFIERS (ItemAttributeModifiers.builder() with EquipmentSlotGroup.MAINHAND, ResourceLocation ids like rankine:alloy_attack_damage), and DataComponents.TOOL (new Tool(rules, speed, damagePerBlock)) built from mineable tag + incorrect-for tag (Tool.Rule.deniesDrops + Tool.Rule.minesAndDrops — study Tool.java + DiggerItem/Tiers).
- Tiers: custom AlloyTier/ForgeTier → SimpleTier with incorrect-blocks TagKey from old harvest level: 0→INCORRECT_FOR_WOODEN_TOOL, 1→STONE, 2→IRON, 3→DIAMOND, 4+→NETHERITE.
- Armor: ArmorItem takes Holder<ArmorMaterial> + ArmorItem.Type + Properties (durability via Properties.durability(Type.getDurability(factor))). Alloy armor dynamic stats: set ATTRIBUTE_MODIFIERS component when stats applied.
- appendHoverText new signature (Item.TooltipContext).
- Arrows/spears: AbstractArrow constructor changes (ItemStack pickupStack, @Nullable ItemStack firedFromWeapon — check reference).
- Enchantment interactions: use com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper.getLevel(level, RankineEnchantments.KEY, stack) (keys keep old field names); vanilla via net.minecraft.world.item.enchantment.Enchantments ResourceKeys.
- setISTER/IItemRenderProperties → leave `// PORT-TODO(client): <item> needs IClientItemExtensions for <what>`.
- getShareTag/readShareTag → delete.

Rules:
- ONLY your files (+ named init files). RankineItems.java registration is another agent's — if your ctors change arity, note in report AND add `// PORT-TODO(init): new ctor signature ...` atop the class.
- No gradle. Valid Java 21. Verify every @Override against reference.
- Behavior 1:1 — stat formulas, tooltips, tick logic identical.

Final report: component design (name → type → replaces which NBT), ctor signature changes for init agent, PORT-TODOs, files done.

=== AGENT: D-recipes (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources. Study: net/minecraft/world/item/crafting/Recipe.java, RecipeInput.java, RecipeSerializer.java, ShapedRecipe.java (codec patterns!), SingleRecipeInput.java, Ingredient.java, ItemStack.java (CODEC/STREAM_CODEC), net/neoforged/neoforge/items/wrapper/RecipeWrapper.java. Never guess a codec API.

YOU OWN: ALL of src/main/java/com/cannolicatfish/rankine/recipe/** (24 files: AirDistillationRecipe, AlloyCraftingRecipe, AlloyModifierRecipe, AlloySmithingRecipe, AlloyingRecipe, BeehiveOvenRecipe, CrucibleRecipe, CrushingRecipe, ElementRecipe, EvaporationRecipe, ForagingRecipe, FusionFurnaceRecipe, JamRecipe, MixingRecipe, RockGeneratorRecipe, SluicingRecipe, StrippingRecipe, TreetappingRecipe + helper/).

The job: rewrite every recipe class for 1.21.1:
1. `class XRecipe implements Recipe<RecipeInput>` (machines pass RecipeWrapper(itemHandler) — index layout MUST match old Container slot indexing). AlloyCraftingRecipe extends CraftingRecipe (CraftingInput). AlloySmithingRecipe: SmithingRecipe interface changed (SmithingRecipeInput) — check reference SmithingTransformRecipe.java.
2. Serializer: `MapCodec<XRecipe> CODEC` (RecordCodecBuilder.mapCodec) + `StreamCodec<RegistryFriendlyByteBuf, XRecipe> STREAM_CODEC`. PRESERVE exact JSON field names/semantics of old fromJson (optional fields, defaults, weighted outputs, chance floats etc.) so handwritten recipe JSONs keep working. StreamCodec.of(encoder, decoder) fine.
3. Methods: matches(input, level), assemble(input, HolderLookup.Provider), getResultItem(HolderLookup.Provider), canCraftInDimensions, getSerializer, getType, isSpecial() where old dynamic recipes returned true. Keep ALL custom public helpers (getOutputs, getChances, rollOutputs etc.) identical — machine BEs call them.
4. Random → RandomSource in roll helpers.
5. helper/ classes: port; keep public API stable.
6. Registration lives in init/RankineRecipeSerializers/Types.java (another agent) — expose public static final Serializer instances or trivial ctors; note exact registration expressions in report.

Rules:
- ONLY recipe/** files. No gradle. Valid Java 21; verify all interfaces against reference sources.
- JSON format backward compatible with 1.18.2 handwritten files (log exceptions in report).
- Keep class/package names identical.

Final report: per-recipe status, JSON compat notes, registration expressions, RecipeInput slot-layout decisions per machine, PORT-TODOs.

=== AGENT: E-enchantments (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md (Enchantments section).
API reference: decompiled MC 1.21.1 + NeoForge 21.1.248 sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources. Study: net/minecraft/world/item/enchantment/Enchantment.java (enchantment()/definition() builders), Enchantments.java (vanilla bootstrap), EnchantmentHelper.java.

CONTEXT: In 1.21 enchantments are data-driven — Enchantment can't be subclassed. Rankine has ~40 subclasses in enchantment/ whose behavior lives partly there, partly in event handlers/items.

YOU OWN:
- ALL of src/main/java/com/cannolicatfish/rankine/enchantment/** (45 files)
- init/RankineEnchantments.java (rewrite completely)
- NEW: src/main/java/com/cannolicatfish/rankine/data/RankineEnchantmentBootstrap.java

THE JOB:
1. For EVERY old enchantment class record: name, rarity, category, min/max level, costs, isTreasure/isCurse, incompatibilities, AND behavior methods with logic.
2. init/RankineEnchantments.java: `public static final ResourceKey<Enchantment> EXCAVATE = key("excavate");` — KEEP old field names and registry path names!
3. data/RankineEnchantmentBootstrap.java: `public static void bootstrap(BootstrapContext<Enchantment> context)` registering definitions via Enchantment.enchantment(Enchantment.definition(...)); rarity→weight/costs mapped like vanilla Enchantments.java; supported-item tags via context.lookup(Registries.ITEM) with custom TagKeys in new class enchantment/RankineEnchantmentTags (rankine:enchantable/*) mapped from old EnchantmentCategory (read old init/RankineEnchantmentTypes.java via `git show origin/1.18.X:src/main/java/com/cannolicatfish/rankine/init/RankineEnchantmentTypes.java` if deleted). Exclusivity via TagKey<Enchantment> rankine:exclusive_set/* (note contents for datagen in report).
4. enchantment/RankineEnchantmentHelper.java: `int getLevel(Level, ResourceKey<Enchantment>, ItemStack)`, `int getLevel(LivingEntity, ResourceKey<Enchantment>, ItemStack)`, `Holder<Enchantment> holder(Level, ResourceKey<Enchantment>)` (try/catch → 0/empty), `int getEquipmentLevel(...)` if needed.
5. BEHAVIOR MIGRATION: per old class with behavior, move logic to static methods in new enchantment/RankineEnchantmentBehaviors.java + `// PORT-TODO(events): call X from <old hook>` for the events agent.
6. DELETE old Enchantment subclasses once captured (package ends with Helper, Behaviors, Tags only).

Rules:
- ONLY your files + named ones. Do NOT edit events/items packages.
- No gradle. Valid Java 21; verify APIs in reference.
- Registry path names identical to 1.18.2.

Final report: table old class → key + definition + behavior disposition, tag contents needed (enchantable/* + exclusive_set/* with member lists), PORT-TODOs, files deleted/created.

=== AGENT: F-worldgen (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1. This is the WORLDGEN area — hardest part. Verify everything against real sources.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md (Worldgen section).
API reference: decompiled sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources. Study: levelgen/feature/Feature.java + OreFeature.java, placement modifiers, net/minecraft/data/worldgen bootstraps, net/neoforged/neoforge/common/world/BiomeModifier.java + BiomeModifiers.java, block/grower/TreeGrower.java.

YOU OWN:
- ALL of src/main/java/com/cannolicatfish/rankine/world/** (65 files)
- init/RankineFeatures.java (DeferredRegister<Feature<?>>)
- init/RankineConfiguredFeatures.java + init/RankinePlacedFeatures.java — REWRITE as ResourceKey constants + `public static void bootstrap(BootstrapContext<...>)`. Registry path names identical.
- util/WorldgenUtils.java (+ purely-worldgen util files)
- NEW: src/main/java/com/cannolicatfish/rankine/data/RankineBiomeModifiers.java — biome modifier bootstrap replacing BiomeLoadingEvent behavior.

KEY WORK:
1. Custom Features: port place(FeaturePlaceContext) — Random→RandomSource etc. Config codecs port directly.
2. Ore/intrusion/stone-replacement gen: find 1.18.2 biome wiring (grep BiomeLoadingEvent in events/ — READ other packages, don't edit). Reproduce via biome modifiers; if config-dependent, custom BiomeModifier w/ MapCodec + NEW init/RankineBiomeModifierTypes.java (DeferredRegister<MapCodec<? extends BiomeModifier>>) + `// PORT-TODO(main): register`.
3. Trees/mushrooms: convert growers to TreeGrower instances (grower/RankineTreeGrowers.java); tree CFs into bootstrap. Custom Trunk/FoliagePlacers: port codecs + type registration in NEW init/RankineWorldgenTypes.java + PORT-TODO(main).
4. WorldgenUtils: initConfigs/initOreTextures port intact; CANNOT enumerate biomes at construct time (datapack) — restructure to lazy/server-start (ServerAboutToStartEvent has registryAccess; PORT-TODO(events) if hook needed elsewhere).
5. Sapling/mushroom growth hooks: keep public API names where feasible, else PORT-TODO with new call pattern.

Rules:
- ONLY your files + named new ones. READ anything.
- No gradle. Valid Java 21; verify every API incl. placement modifiers.
- Feature registry path names identical.
- Bootstraps signatures exactly `public static void bootstrap(BootstrapContext<T> context)` (datagen agent wires them).

Final report: feature inventory, biome-wiring design (old event behavior → modifier entries, full list), new registries, PORT-TODOs, config-dynamic behavior changes.

=== AGENT: G-events (model: default) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources. Study: net/neoforged/neoforge/event/**, network/** (payloads), attachment/AttachmentType.java, capabilities/RegisterCapabilitiesEvent.java, net/minecraft/advancements/critereon/**.

YOU OWN:
- events/** (35), init/packets/** (4+handler), capabilities/** (3), commands/** (5), advancements/** (5), potion/** (3), loot/** (1), compatibility/** (5), entities/** (12)
- NEW: events/handlers/common/CapabilityRegistrationHandler.java — @EventBusSubscriber(modid="rankine", bus=MOD) RegisterCapabilitiesEvent wiring every machine BE item handler: `event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TYPE.get(), (be, side) -> be.getItemHandler(side))` — grep `PORT-CAP` under blocks/ late in your work; wire from init/RankineBlockEntityTypes.java entries otherwise; list unwired BEs.

KEY WORK:
1. Events: LivingHurtEvent→LivingIncomingDamageEvent (pre-armor semantics), LivingAttackEvent→LivingIncomingDamageEvent (cancel), TickEvent.PlayerTickEvent→PlayerTickEvent.Pre/Post (match old phase), PlayerEvent.HarvestCheck/BreakSpeed, BlockEvent.BreakEvent, ProjectileImpactEvent, LivingDropsEvent, ItemFishedEvent, BonemealEvent, PlayerLoggedInEvent, FurnaceFuelBurnTimeEvent, CriticalHitEvent, AnvilUpdateEvent — VERIFY each in reference. Enchantment lookups via RankineEnchantmentHelper.getLevel(level, RankineEnchantments.KEY, stack); behaviors via RankineEnchantmentBehaviors.* (write expected calls + PORT-TODO(enchant-sync) if not yet existing).
2. Client handlers: RenderGameOverlayEvent→RegisterGuiLayersEvent/RenderGuiLayerEvent, FOVModifierEvent→ComputeFovModifierEvent, InputEvent, GuiGraphics.
3. Network: packets → record CustomPacketPayload + TYPE + STREAM_CODEC; NEW init/packets/RankinePayloads.java with RegisterPayloadHandlersEvent (version "1"); update send sites in YOUR packages (PacketDistributor.*); list send sites in other packages in report + PORT-TODO.
4. capabilities/ → NeoForge attachments: NEW init/RankineAttachments.java (DeferredRegister<AttachmentType<?>> on NeoForgeRegistries.Keys.ATTACHMENT_TYPES) + PORT-TODO(main); player.getData/setData; persistence via serializer.
5. Commands: verify Commands API/CommandBuildContext.
6. Advancements: custom triggers → SimpleCriterionTrigger<Instance record w/ Codec>; NEW init/RankineCriteriaTriggers.java (Registries.TRIGGER_TYPE) + PORT-TODO(main).
7. Loot: GLM MapCodec pattern; expose `public static final MapCodec<X> CODEC` per class.
8. compatibility/Patchouli.java: NO Patchouli imports — if ModList.get().isLoaded("patchouli"), ItemStack of patchouli:guide_book with CustomData component {"patchouli:book":"rankine:journal"}; else EMPTY. Other compat: ModList checks only; stub with PORT-TODO if they imported other mods' classes.
9. Entities: AbstractArrow ctor changes, custom boat (verify Boat ctor/getDropItem), spear, goals, getAddEntityPacket→delete or IEntityWithComplexSpawn, RegisterSpawnPlacementsEvent.

Rules:
- ONLY your files + named new ones. READ anything.
- No gradle. Valid Java 21; verify events in reference.
- Semantics 1:1 (pre/post armor, cancel, phase).

Final report: event mapping table, payload design, attachment design, PORT-TODOs (main-class registrations: attachments, criteria), unwired caps, cross-package send-site changes.

=== AGENT: H-client (model: sonnet) ===
You are porting part of the Minecraft mod "Project Rankine" from Forge 1.18.2 to NeoForge 1.21.1.

Repo: C:\Users\karl\Documents\Projects\project-rankine (branch 1.21.1-neoforge, Windows).
MANDATORY FIRST STEP: Read C:\Users\karl\Documents\Projects\project-rankine\PORTING_GUIDE.md.
API reference: decompiled sources at C:\Users\karl\Documents\Projects\mc-1.21.1-sources. Study: client/gui/GuiGraphics.java, net/neoforged/neoforge/client/event/**, extensions/common/IClientFluidTypeExtensions.java, vanilla boat/arrow renderers.

YOU OWN:
- ALL of src/main/java/com/cannolicatfish/rankine/client/** EXCEPT client/integration/jei/** — do NOT create/edit ClientSetup.java (another agent creates it from old ClientProxy).
- NEW: client/RankineClientEvents.java — @EventBusSubscriber(modid="rankine", bus=MOD, value=Dist.CLIENT) with everything from the OLD main class client events: get original via `git show origin/1.18.X:src/main/java/com/cannolicatfish/rankine/ProjectRankine.java` (RegistryEvents inner class): item/block colors → RegisterColorHandlersEvent.Item/.Block; entity renderers → EntityRenderersEvent.RegisterRenderers. ALSO RegisterClientExtensionsEvent registering IClientFluidTypeExtensions for all 13 fluids (textures from fluid classes' FLUID_STILL/FLUID_FLOWING/OVERLAY constants; fluid types in init/RankineFluids.java FLUID_TYPES by concurrent agent — check late, adapt, or exact PORT-TODO).
- util/colors/** (BlockColor/ItemColor impls — alloy colors read NBT → use RankineDataComponents via stack.get(); check init/RankineDataComponents.java).

KEY WORK:
1. renders/: entity renderers (spear, arrows, cannonball, enderball, carcass, balloon, reactive item, boat) — verify every override; getTextureLocation required. Custom boat: own ModelLayers via EntityRenderersEvent.RegisterLayerDefinitions (BoatModel.createBodyModel()).
2. BERs if any.
3. models/: LayerDefinition APIs (verify), layer registration event.
4. Screens here: GuiGraphics rework.
5. setRenderLayer calls: delete with PORT-TODO(datagen-rendertype) listing block→layer pairs.
6. IClientItemExtensions for ISTER items: wire PORT-TODO(client) notes from items agent late in work.

Rules:
- ONLY your files + named new one. No gradle. Valid Java 21; verify overrides.
Final report: files ported, renderer registrations, fluid client extension status, PORT-TODOs.
