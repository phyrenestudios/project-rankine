# Rankine 1.18.2 Forge → 1.21.1 NeoForge Porting Guide

Target: **Minecraft 1.21.1, NeoForge 21.1.248, Java 21, ModDevGradle**. Mappings are official Mojang (unchanged).
This file is the single source of truth for porting conventions. Follow it exactly so the codebase stays consistent.

## Golden rules
1. Do NOT invent new features or refactor logic. Port behavior 1:1 unless the API forces a redesign (enchantments, damage sources, worldgen registration).
2. If an API is gone and behavior cannot be preserved, preserve as much as possible and leave a `// PORT-TODO: <what & why>` comment. Grep-able.
3. Keep registry names (`rankine:xxx`) IDENTICAL — world compat depends on it.
4. No mixins, no reflection hacks, no AT unless unavoidable.

## Package renames (mechanical)
| 1.18.2 Forge | 1.21.1 NeoForge |
|---|---|
| `net.minecraftforge.api.distmarker.*` | `net.neoforged.api.distmarker.*` |
| `net.minecraftforge.eventbus.api.*` | `net.neoforged.bus.api.*` |
| `net.minecraftforge.fml.*` | `net.neoforged.fml.*` |
| `net.minecraftforge.common.MinecraftForge.EVENT_BUS` | `net.neoforged.neoforge.common.NeoForge.EVENT_BUS` |
| `net.minecraftforge.common.ForgeConfigSpec` | `net.neoforged.neoforge.common.ModConfigSpec` |
| `net.minecraftforge.items.*` | `net.neoforged.neoforge.items.*` (same class names) |
| `net.minecraftforge.event.*` | `net.neoforged.neoforge.event.*` (some renamed, see Events) |
| `net.minecraftforge.registries.*` | `net.neoforged.neoforge.registries.*` |
| `net.minecraftforge.common.Tags` | `net.neoforged.neoforge.common.Tags` (namespace now `c:`, many names changed e.g. `Tags.Items.INGOTS_IRON` still exists) |
| `@Mod.EventBusSubscriber` | `@EventBusSubscriber` (`net.neoforged.fml.common.EventBusSubscriber`) |

## Registries
- `DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)` → `DeferredRegister.createBlocks(MODID)` (type `DeferredRegister.Blocks`).
- `DeferredRegister.create(ForgeRegistries.ITEMS, MODID)` → `DeferredRegister.createItems(MODID)` (type `DeferredRegister.Items`).
- Other registries: `DeferredRegister.create(Registries.MOB_EFFECT, MODID)` etc. (`net.minecraft.core.registries.Registries`). NeoForge-only registries: `NeoForgeRegistries.Keys.FLUID_TYPES`, `NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS`, etc.
- `RegistryObject<T>` → `DeferredHolder<RegistryType, T>`; for blocks/items prefer `DeferredBlock<T>` / `DeferredItem<T>`.
- `ForgeRegistries.ITEMS.getValue(rl)` → `BuiltInRegistries.ITEM.get(rl)`; `.getKey(obj)` → `BuiltInRegistries.ITEM.getKey(obj)`.
- Registration order requirement: registries registered in mod constructor via `modBus`.
- **REMOVED registries in 1.21**: ConfiguredFeature/PlacedFeature/Enchantment/DamageType/Biome are DATAPACK registries — never `DeferredRegister` them. Use `ResourceKey` constants + datagen bootstrap (see Worldgen/Enchantments).

## Core class changes (vanilla 1.18.2 → 1.21.1)
- `new ResourceLocation(ns, path)` → `ResourceLocation.fromNamespaceAndPath(ns, path)`; `new ResourceLocation(str)` → `ResourceLocation.parse(str)`.
- `new TextComponent(s)` → `Component.literal(s)`; `new TranslatableComponent(k, ...)` → `Component.translatable(k, ...)`.
- `java.util.Random` in vanilla API positions → `net.minecraft.util.RandomSource` (`level.random`, `animateTick`, features, etc.).
- `Level.isClientSide` unchanged. `level.getRecipeManager()` unchanged.
- Tool/armor items: see Items.
- `Material` (block) is GONE. `Block.Properties.of(Material.X, color)` → `BlockBehaviour.Properties.of().mapColor(MapColor.X)` plus `.instrument()`, `.pushReaction(PushReaction.X)`, `.ignitedByLava()`, `.replaceable()`, `.forceSolidOn()` as behavior requires. `Properties.copy(b)` → `Properties.ofFullCopy(b)`.
- `Block#use(...)` → `protected InteractionResult useWithoutItem(BlockState, Level, BlockPos, Player, BlockHitResult)` and/or `protected ItemInteractionResult useItemOn(ItemStack, BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult)`.
- `Item#use / useOn / interactLivingEntity` mostly same signatures. `ItemStack#getTag()/getOrCreateTag()` are GONE → Data Components (see below).
- `CompoundTag` save/load on BlockEntity: `saveAdditional(CompoundTag, HolderLookup.Provider)` / `loadAdditional(CompoundTag, HolderLookup.Provider)` (loadAdditional replaces `load`).
- `ItemStack.of(tag)` → `ItemStack.parseOptional(provider, tag)`; `stack.save(tag)` → `stack.save(provider)` or `stack.saveOptional(provider)`.
- `getUpdateTag()`/`handleUpdateTag` take `HolderLookup.Provider`.
- `entity.level` field → `entity.level()` method.
- `player.getItemInHand` unchanged; `abilities` → `player.getAbilities()`.
- `LivingEntity#hurt(DamageSource, float)` still exists; DamageSource creation changed (see Damage).
- `SoundEvent` registration: `SoundEvent.createVariableRangeEvent(rl)`.
- `MobEffect`/`Potion` registration: `Holder<MobEffect>` used in `MobEffectInstance(holder, dur, amp)`.
- `CreativeModeTab`: build via `CreativeModeTab.builder().title(...).icon(...).displayItems((params, output) -> ...)`, registered in `DeferredRegister<CreativeModeTab>` on `Registries.CREATIVE_MODE_TAB`.
- `RandomSource` for `getSeed` worldgen positions.
- `Direction.getNormal()` → `getUnitVec3i()`? NO — in 1.21.1 it is still `getNormal()`. Leave as-is.
- Vanilla tags moved: `BlockTags.MINEABLE_WITH_PICKAXE` etc. unchanged.

## Data Components (replaces ItemStack NBT) — 1.20.5+
- Register: `DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID)`.
- All Rankine per-stack data (alloy composition `nbt "comp"`, `"nbtRecipe"`, tool NBT, etc.) lives in `RankineDataComponents` (new class in `init`). Use records with `Codec` + `StreamCodec`.
- Read: `stack.get(RankineDataComponents.ALLOY_COMP.get())`, write: `stack.set(type, value)`, remove: `stack.remove(type)`.
- Vanilla equivalents: damage `stack.getDamageValue()` same; custom names `DataComponents.CUSTOM_NAME`; unbreakable `DataComponents.UNBREAKABLE`; `stack.enchant(holder, lvl)`.
- Dynamic tool stats (alloy tools): when composition is applied to a stack, ALSO set `DataComponents.TOOL` (mining behavior), `DataComponents.ATTRIBUTE_MODIFIERS` (damage/speed), and `DataComponents.MAX_DAMAGE`/`DataComponents.MAX_STACK_SIZE` as needed. Central helper in `AlloyItemUtils`.

## Items
- `SwordItem(tier, dmg, speed, props)` → `new SwordItem(tier, props.attributes(SwordItem.createAttributes(tier, dmg, speed)))`. Same pattern for Digger tools (`PickaxeItem.createAttributes(...)` etc.).
- `ForgeTier` → `SimpleTier(TagKey<Block> incorrectBlocks, uses, speed, dmg, ench, repair)` — NOTE: tiers now use an *incorrect blocks* tag instead of harvest level int. Custom alloy tiers: use `Tiers.IRON.getIncorrectBlocksForDrops()`-style tags or `BlockTags.INCORRECT_FOR_IRON_TOOL`.
- Armor: `ArmorMaterial` is a registry object now → register custom materials in `DeferredRegister<ArmorMaterial>` (`Registries.ARMOR_MATERIAL`); `ArmorItem(Holder<ArmorMaterial>, ArmorItem.Type, props)`.
- `Item#appendHoverText(ItemStack, @Nullable Level, List<Component>, TooltipFlag)` → `appendHoverText(ItemStack, Item.TooltipContext, List<Component>, TooltipFlag)`.
- Food: `Item.Properties().food(FoodProperties)` — builder mostly unchanged; effects take `Holder<MobEffect>` suppliers.
- `ItemStack#getMaxDamage()` etc. fine. `isDamageable` → `isDamageableItem`.
- Arrows: `AbstractArrow` ctor takes `@Nullable ItemStack firedFromWeapon` in 1.21.1: `super(type, level)` variants exist; pickup stack handled via `getPickupItemStackOrigin`/ctor `ItemStack pickupItemStack`.
- `IItemExtension` (NeoForge) replaces IForgeItem: e.g. `canPerformAction(stack, ItemAbility)` (was ToolAction → `ItemAbility`, `ItemAbilities.AXE_STRIP` etc.).

## Recipes (full rewrite of serializers)
- `Recipe<Container>` → `Recipe<I extends RecipeInput>`. For machine recipes define/use `RecipeInput` wrappers (`new SingleRecipeInput(stack)` or custom `RecipeInput` records exposing `getItem(int)`/`size()`).
- Serializer interface:
```java
public class XSerializer implements RecipeSerializer<XRecipe> {
    public static final MapCodec<XRecipe> CODEC = RecordCodecBuilder.mapCodec(...);
    public static final StreamCodec<RegistryFriendlyByteBuf, XRecipe> STREAM_CODEC = StreamCodec.composite(...) /* or of(encode, decode) */;
    @Override public MapCodec<XRecipe> codec() { return CODEC; }
    @Override public StreamCodec<RegistryFriendlyByteBuf, XRecipe> streamCodec() { return STREAM_CODEC; }
}
```
- Useful codecs: `Ingredient.CODEC`, `Ingredient.CODEC_NONEMPTY`, `Ingredient.CONTENTS_STREAM_CODEC`, `ItemStack.CODEC`, `ItemStack.OPTIONAL_CODEC`, `ItemStack.STREAM_CODEC`, `ItemStack.OPTIONAL_STREAM_CODEC`, `Codec.floatRange`, `ExtraCodecs`.
- Keep JSON field names the same as the 1.18 format wherever practical (datagen regenerates files anyway, but handwritten compat recipes exist).
- `Recipe` methods now: `matches(I input, Level)`, `assemble(I input, HolderLookup.Provider)`, `getResultItem(HolderLookup.Provider)`, `canCraftInDimensions`, `getType()`, `getSerializer()`.
- Lookup: `level.getRecipeManager().getRecipeFor(TYPE.get(), input, level)` → `Optional<RecipeHolder<X>>`; use `holder.value()` and `holder.id()`.
- `RecipeType.register` → `DeferredRegister<RecipeType<?>>` with `RecipeType.simple(rl)`.

## Menus / Screens
- Registration: `IMenuTypeExtension.create((windowId, inv, buf) -> new XMenu(...))` for data-carrying menus; register in `DeferredRegister<MenuType<?>>` (`Registries.MENU`).
- Opening: `NetworkHooks.openScreen(player, provider, buf -> ...)` → `serverPlayer.openMenu(provider, buf -> ...)` (NeoForge `IPlayerExtension#openMenu`).
- Client screen registration: `RegisterMenuScreensEvent` (mod bus) → `event.register(MENU_TYPE.get(), XScreen::new)`.
- `Screen#render(PoseStack,...)` → `render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)`; blitting via `guiGraphics.blit(TEXTURE, ...)`.

## Capabilities (NeoForge rework — no more LazyOptional)
- BlockEntity item handlers: do NOT override `getCapability`. Instead register in mod-bus event:
```java
@SubscribeEvent // mod bus
static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, RankineBlockEntityTypes.X.get(), (be, side) -> be.getItemHandler(side));
}
```
- Query: `level.getCapability(Capabilities.ItemHandler.BLOCK, pos, side)` or `stack.getCapability(Capabilities.ItemHandler.ITEM)`.
- Player persistent data: Rankine's custom player capabilities → NeoForge **attachments**: `AttachmentType` + `DeferredRegister<AttachmentType<?>>` on `NeoForgeRegistries.Keys.ATTACHMENT_TYPES`; `player.getData(TYPE)` / `setData`.

## Networking
- Payloads: `record XPayload(...) implements CustomPacketPayload` with `TYPE` (`CustomPacketPayload.Type<XPayload>`) and `STREAM_CODEC`.
- Register (mod bus): `RegisterPayloadHandlersEvent` → `registrar.playToServer(TYPE, STREAM_CODEC, handler)` / `playToClient`.
- Handler: `(payload, context) -> context.enqueueWork(() -> ...)`; player via `context.player()`.
- Send: `PacketDistributor.sendToServer(payload)`, `PacketDistributor.sendToPlayer(serverPlayer, payload)`, `sendToAllPlayers`.

## Events (game bus unless noted)
- `LivingHurtEvent` → `LivingIncomingDamageEvent` (pre-armor; use `event.setAmount`) or `LivingDamageEvent.Pre`.
- `LivingAttackEvent` → `LivingIncomingDamageEvent` (cancelable).
- `TickEvent.PlayerTickEvent` → `PlayerTickEvent.Pre`/`Post`; `TickEvent.LevelTickEvent` → `LevelTickEvent.Pre/Post`; `ServerTickEvent.Pre/Post`; NO `event.phase` checks anymore — pick Pre or Post to match old `Phase.START`/`END`.
- `PlayerEvent.BreakSpeed`, `BlockEvent.BreakEvent`, `PlayerInteractEvent.*` — same names in `net.neoforged.neoforge.event.*`.
- `FMLServerAboutToStartEvent` etc → `ServerAboutToStartEvent` (`net.neoforged.neoforge.event.server`).
- `BiomeLoadingEvent` GONE → biome modifier JSON (datagen; see Worldgen).
- `RegistryEvent.Register<T>` GONE → DeferredRegister.
- `RegistryEvent.MissingMappings` → `MissingMappingsEvent` (game bus... actually MOD bus in NeoForge; check usage) with `event.getAllMappings(Registries.ITEM)`, `mapping.remap(...)`.
- `ColorHandlerEvent.Item/Block` → `RegisterColorHandlersEvent.Item/Block` (mod bus).
- `FMLClientSetupEvent` same name. `InterModComms` rarely needed.
- Mod bus events implement `IModBusEvent`; register with `@EventBusSubscriber(modid = "rankine", bus = EventBusSubscriber.Bus.MOD)`.

## Enchantments (1.21 data-driven — REDESIGN)
- All 40+ `Enchantment` subclasses are DELETED as classes. Replace with:
  1. `RankineEnchantments` holding `ResourceKey<Enchantment>` constants: `ResourceKey.create(Registries.ENCHANTMENT, rl("excavate"))`.
  2. Datagen bootstrap (`RegistrySetBuilder.add(Registries.ENCHANTMENT, ctx -> {...})`) defining each: `Enchantment.enchantment(Enchantment.definition(itemTag, weight, maxLvl, cost1, cost2, anvilCost, EquipmentSlotGroup.X))`.
  3. Behavior stays in event handlers / item code, querying levels:
```java
Registry<Enchantment> reg = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
Holder<Enchantment> h = reg.getHolderOrThrow(RankineEnchantments.EXCAVATE);
int lvl = EnchantmentHelper.getItemEnchantmentLevel(h, stack);
```
  Helper `RankineEnchantmentHelper.getLevel(LivingEntity/Level, ResourceKey<Enchantment>, ItemStack)` — write once in `enchantment` package, use everywhere.
- Item applicability via item tags referenced in the definition (`/tags/item/enchantable/...` style custom tags, e.g. `rankine:enchantable/crowbar`).
- `EnchantmentCategory` GONE.

## Damage sources (1.19.4+ data-driven)
- Custom damage types → `ResourceKey<DamageType>` constants + datagen bootstrap (`Registries.DAMAGE_TYPE`) + `data/rankine/damage_type/*.json`.
- Create source at use-site: `new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(KEY))` — put a helper in `RankineDamageSources`.
- Tags for behaviors (bypass armor etc.): `data/rankine/tags/damage_type/…` via datagen if needed.

## Fluids
- Each fluid needs a registered `FluidType` (`DeferredRegister<FluidType>` on `NeoForgeRegistries.Keys.FLUID_TYPES`); textures/color via `IClientFluidTypeExtensions` registered in `RegisterClientExtensionsEvent` (mod bus, client).
- `ForgeFlowingFluid.Source/Flowing` → `BaseFlowingFluid.Source/Flowing` with `BaseFlowingFluid.Properties(fluidType, still, flowing).bucket(...).block(...)`.
- Register fluids in `DeferredRegister<Fluid>`; no more `setRegistryName`.
- `FluidAttributes` GONE — viscosity/density/sound live on `FluidType`, render info on client extensions.

## Worldgen
- `Feature<C>` subclasses: still code — port `place(FeaturePlaceContext)` (RandomSource etc.). Register via `DeferredRegister<Feature<?>>`.
- Configured/Placed features: `ResourceKey` constants + datagen bootstrap via `RegistrySetBuilder` (`Registries.CONFIGURED_FEATURE`, `Registries.PLACED_FEATURE`) emitted by `DatapackBuiltinEntriesProvider`.
- Adding to biomes: NeoForge biome modifier JSONs (datagen `neoforge:add_features` with biome tag + placed feature + `GenerationStep`), or a custom `BiomeModifier` (codec-registered) when logic is conditional on config.
- `BiomeLoadingEvent` handlers → biome modifiers.
- Tree/mushroom growers: `TreeGrower` is now a record-ish class instantiated inline (no subclassing `AbstractTreeGrower`; use `new TreeGrower(name, Optional.empty(), Optional.of(CONFIGURED_KEY), Optional.empty())`).

## Loot modifiers
- `GlobalLootModifierSerializer` → `MapCodec<X>`; register codec in `DeferredRegister<MapCodec<? extends IGlobalLootModifier>>` on `NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS`. `LootModifier` base class provides `codecStart`.
- Loot tables referenced by `ResourceKey<LootTable>`: `level.getServer().reloadableRegistries().getLootTable(key)`.

## Client misc
- BER registration: `BlockEntityRenderers.register` or `EntityRenderersEvent.RegisterRenderers`.
- `ItemBlockRenderTypes.setRenderLayer` → put `"render_type": "minecraft:cutout"` etc. in block MODEL JSON via datagen (`.renderType("cutout")` in BlockStateProvider model builders).
- Particles: `RegisterParticleProvidersEvent` with `registerSpriteSet`.
- `PoseStack` param in screens → `GuiGraphics`.
- Item model predicates: `ItemProperties.register` unchanged (client setup enqueueWork).
- Keybindings: `RegisterKeyMappingsEvent`.
- Boat entities: vanilla `Boat` types are enum-locked; Rankine custom boat entity stays a custom entity — port its renderer with `BoatModel` layer defs as needed.

## Config
- `ForgeConfigSpec` → `ModConfigSpec` (same builder API).
- Register: mod constructor takes `(IEventBus modBus, ModContainer container)`; `container.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG, "rankine-common.toml")`.
- Config load events: `ModConfigEvent.Loading/Reloading` on mod bus.

## Datagen (1.21.1)
- `GatherDataEvent` (mod bus): providers added to `event.getGenerator()`; most providers take `(PackOutput, CompletableFuture<HolderLookup.Provider>)`.
- `RecipeProvider#buildRecipes(RecipeOutput)`; builders: `ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item)`, `.unlockedBy(...)`, `.save(output)`; custom recipe builders write `Recipe` instances via `output.accept(id, recipe, advancement)`.
- Tags: `BlockTagsProvider(output, lookupProvider, MODID, existingFileHelper)`; `tag(...).add(...)` — keys via `TagKey.create`.
- Loot: `LootTableProvider(output, Set.of(), List.of(new SubProviderEntry(RankineBlockLootTables::new, LootContextParamSets.BLOCK)), registries)`.
- Models: `BlockStateProvider(output, MODID, existingFileHelper)` — API mostly unchanged; `.renderType("cutout")` for foliage etc.
- Datapack entries: `DatapackBuiltinEntriesProvider(output, registries, RegistrySetBuilder, Set.of(MODID))` — this generates worldgen + enchantments + damage types JSON.
- Advancements: `AdvancementProvider` with `AdvancementSubProvider`s; criteria via `Criteria` triggers; `Advancement.Builder` largely same but `save(Consumer<AdvancementHolder>, ...)`.

## Custom advancement triggers
- `SimpleCriterionTrigger<X.TriggerInstance>` where `TriggerInstance` is a record with a `Codec`; register codecs via `DeferredRegister<CriterionTrigger<?>>` on `Registries.TRIGGER_TYPE`.

## GameTests
- Class annotated `@GameTestHolder(ProjectRankine.MODID)` (net.neoforged.neoforge.gametest) + `@PrefixGameTestTemplate(false)`; methods `@GameTest(template = "rankine:empty" or use structure)`. Empty platform template: use `@GameTest(template = "empty")` with a generated structure nbt, or `helper.getLevel()` assertions.
- Run with `gradlew runGameTestServer`.

## Things that are REMOVED / must be stubbed
- `DistExecutor`, `IProxy/ClientProxy/ServerProxy` pattern → delete; client-only init goes in `@EventBusSubscriber(value = Dist.CLIENT, bus = MOD)` classes under `client/`.
- Patchouli dependency: REMOVED. `compatibility/Patchouli.java` → return `ItemStack` built from `BuiltInRegistries.ITEM.get(ResourceLocation.parse("patchouli:guide_book"))` with a component-based book tag only when `ModList.get().isLoaded("patchouli")`; otherwise empty. No compile-time Patchouli imports.
- `VanillaIntegration` compost/fire registrations: `ComposterBlock.COMPOSTABLES` still a map in 1.21.1 — keep; `FireBlock.setFlammable` via accessor method `fireBlock.setFlammable(block, encouragement, flammability)` — it's public in NeoForge patches... if not accessible, PORT-TODO.
- JEI: port against JEI 19.21.x API (`mezz.jei.api.*`): `IRecipeCategory` uses `RecipeType<T>`, `setRecipe(IRecipeLayoutBuilder, T, IFocusGroup)`; registration via `@JeiPlugin IModPlugin`.

## Testing conventions
- Unit tests: `src/test/java` — runs with NeoForge unit test support (registries bootstrapped). Test pure logic + codec round-trips.
- GameTests in `src/main/java/com/cannolicatfish/rankine/gametest/` (main source set so they ship in dev runs; excluded from release behavior by GameTest being server-opt-in).
