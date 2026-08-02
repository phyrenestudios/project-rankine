# G events/network/caps/entities — DONE

## Event mapping (old → new)
PlaySoundAtEntityEvent→PlayLevelSoundEvent.AtEntity (getDefaultPitch/setPitch→getOriginalPitch/setNewPitch);
TickEvent.PlayerTickEvent→PlayerTickEvent.Pre; WorldEvent.Load→LevelEvent.Load; NEW ServerAboutToStartEvent →
WorldgenUtils.ensureBiomesResolved; BlockEvent.CreateFluidSourceEvent→level.block.CreateFluidSourceEvent
(Result.DENY→setCanConvert(false)); EntityJoinWorldEvent→EntityJoinLevelEvent; ChunkEvent.Load getStatus→getPersistedStatus;
PlayerEvent.ItemPickupEvent→ItemEntityPickupEvent.Post (getStack→getOriginalStack); SaplingGrowTreeEvent→BlockGrowFeatureEvent
(Result.DENY→setCanceled); LivingSetAttackTargetEvent→LivingChangeTargetEvent (now pre-set + cancelable);
LivingEvent.LivingUpdateEvent→EntityTickEvent.Pre; LivingHurtEvent→LivingIncomingDamageEvent (pre-armor parity);
LivingDamageEvent→LivingDamageEvent.Pre (getAmount/setAmount→getNewDamage/setNewDamage); NEW LivingDamageEvent.Post →
RankineEnchantmentBehaviors.applyPrying/applyPuncture (vanilla no longer fires doPostAttack);
PistonEvent.Pre→level.PistonEvent.Pre (TierSortingRegistry gone → !state.is(tier.getIncorrectBlocksForDrops()));
BlockEvent.*→event.level.BlockEvent.*; BreakSpeed getPos→getPosition (Optional); FOVModifierEvent→ComputeFovModifierEvent
(setNewfov→setNewFovModifier); RenderGameOverlayEvent→RegisterGuiLayersEvent + LayeredDraw.Layer;
RenderBlockOverlayEvent→RenderBlockScreenEffectEvent.
NOT USED anywhere in this mod (verified by grep): ProjectileImpactEvent, LivingDropsEvent, ItemFishedEvent, BonemealEvent,
CriticalHitEvent, HarvestCheck.

## Payloads (RankinePayloads, MOD bus, registrar version "1")
- FluidStackPacket(FluidStack, BlockPos, boolean) → playToClient, id rankine:fluid_stack.
- SelectAlloyPacket(int) → playToServer, id rankine:select_alloy. Handler intentionally empty (1.18.2 body was fully commented out).
- RankinePacketHandler (SimpleChannel) deleted. All 4 cross-package send sites already match.

## Attachments
RankineAttachments.RETROGEN_DONE (rankine:retrogen_chunk), AttachmentType.builder(() -> FALSE).serialize(Codec.BOOL).
Replaces the deleted capabilities/ package. WORLD-COMPAT CAVEAT: 1.18.2 stored this under ForgeCaps and it is NOT migrated,
so pre-existing chunks will run retrogen replacement one extra time.

## Advancement predicates
ItemPredicate is a final record in 1.21.1 → all five became ItemSubPredicate records with Codecs, registered in
RankineCriteriaTriggers on Registries.ITEM_SUB_PREDICATE_TYPE (paths unchanged). Nothing on Registries.TRIGGER_TYPE.
JSON SHAPE CHANGED (vanilla-driven): now nested under an item predicate's `predicates` map, e.g.
`"predicates": {"rankine:harvest_level_check": {"level": {"min": 2}}}`.

## Capabilities wired (10 BEs)
ALLOY_FURNACE, BATTERY_CHARGER, CRUCIBLE, EVAPORATION_TOWER, FUSION_FURNACE, GAS_BOTTLER, INDUCTION_FURNACE,
MIXING_BARREL, MATERIAL_TESTING_TABLE, PEDESTAL. LaserQuarryTile unwired (dead/unregistered in 1.18.2 too).
All other BEs never exposed a capability in 1.18.2 (verified) — nothing missing. No fluid/energy caps existed.

## ACTIONS OWED BY OTHERS
1. init: RankineRecipes.registerPredicates() is now a dead no-op → delete the method and its call in ProjectRankine.
2. datagen: HasEnchantmentPredicate.Builder.withEnchantments signature changed Enchantment... → Holder<Enchantment>...;
   resolve via registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key).
3. datagen: RankineDamageSources needs bypasses_armor / bypasses_effects damage-type tags.

## Deliberate deviations (commented in-code)
- PlayerTickHandler ran TWICE per tick in 1.18.2 (no phase check); now once (Pre). Only visible effect: Invigorating Totem
  repairs at half the old rate; everything else idempotent.
- event.setResult(ALLOW/DENY) in EntityInteractHandler/RightClickBlockHandler was inert in 1.18.2 (not @HasResult); dropped.
- RankineClientEventHandler.renderBlockOverlayEvent was non-static and never fired in 1.18.2; it is static now, so the
  block overlay actually works.
- ItemAttributeModifierHandler: event no longer carries slot/Level → EquipmentSlotGroups + registry-free enchantment reads
  off the stack's ItemEnchantments component.
- CarcassEntity: Fireball#getDefaultItem is private → cannonball fallback via getItem() override.
- PlayerTickHandler thermometer: Biome#shouldSnowGolemBurn gone, getTemperature(BlockPos) private → getBaseTemperature() > 1.0F
  (drops altitude adjustment on that branch).
