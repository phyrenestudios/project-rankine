# Enchantments agent report (condensed contract for downstream agents)

Status: DONE. 44 old Enchantment subclasses deleted; package now = RankineEnchantmentHelper, RankineEnchantmentBehaviors, RankineEnchantmentTags. init/RankineEnchantments.java = ResourceKey constants (old field names/paths kept). data/RankineEnchantmentBootstrap.java has `bootstrap(BootstrapContext<Enchantment>)` with all 44 definitions.

## For DATAGEN agent
1. Wire `RegistrySetBuilder.add(Registries.ENCHANTMENT, RankineEnchantmentBootstrap::bootstrap)` into DatapackBuiltinEntriesProvider.
2. Generate these tags:

**Item tags rankine:enchantable/**:
- hammer — every HammerItem + AlloyHammerItem instance
- crowbar — every CrowbarItem + AlloyCrowbarItem
- knife — every KnifeItem + AlloyKnifeItem
- swing — #rankine:enchantable/hammer + #rankine:enchantable/crowbar
- spear — every SpearItem + AlloySpearItem
- mining — all shovels + pickaxes (vanilla + rankine)
- alloy_hoe — every AlloyHoeItem
- alloy_tool — every IAlloyTool item with durability
- alloy_sword — every AlloySwordItem
- alloy_armor — every AlloyArmorItem
- blunderbuss — every BlunderbussItem (incl. Alloy)
- ender_amalgam_hammer|sword|shovel|knife|crowbar|spear|axe|hoe|pickaxe|blunderbuss — exactly the matching RankineItems.ENDER_AMALGAM_* item each

**Enchantment tags rankine:exclusive_set/**:
- atomize = [minecraft:fortune]; quake = [minecraft:efficiency]; endpoint = [minecraft:loyalty]; stance = [rankine:retaliate, rankine:retreat]

**Vanilla enchantment tags**:
- minecraft:enchantment/in_enchanting_table + non_treasure + tradeable: ONLY puncture, swing, daze, atomize, excavate, impact, quake, foraging, lightning_aspect, cleanse, guard, accuracy, preparation, poison_aspect, backstab, grafting, retaliate, retreat, leverage, torque, prying, fulcrum, retrieval. (antiquated deliberately excluded.)
- minecraft:enchantment/treasure: dune_walker, snow_drifter, speed_skater, swift_swimmer, gas_protection, aqua_lense, all 11 end* enchantments, ghastly_regeneration, withering_curse, shape_memory, antiquated.
- minecraft:enchantment/curse: withering_curse.
3. RankineAdvancementProvider used RankineEnchantments.X.get() in HasEnchantmentPredicate — switch to keys/holders.

## For EVENTS agent
- NEW wiring: old doPostAttack fired from vanilla doPostDamageEffects; now nothing calls it. Must call RankineEnchantmentBehaviors.applyPrying(attacker, target, lvl) and applyPuncture(attacker, target, lvl) from a post-attack hook (LivingDamageEvent.Post) when attacker's weapon has PRYING / PUNCTURE. PORT-TODO(events) comments in RankineEnchantmentBehaviors.
- Replace old helper calls (getTorqueEnchantment etc.) with RankineEnchantmentHelper.getLevel(levelOrEntity, RankineEnchantments.X, stack) / hasEnchantment(entity, key). Affected: AnvilUpdateHandler (also output.enchant needs RankineEnchantmentHelper.holder), BlockBreakHandler, BreakSpeedHandler, ItemAttributeModifierHandler, LeftClickBlockHandler, LivingDamageHandler, LivingHurtHandler, PlayerTickHandler, RightClickBlockHandler, client RenderGameOverlayHandler; entities SpearEntity (ENDPOINT, IMPACT).

## For ITEMS agent
- Affected: BlunderbussItem, CrowbarItem, HammerItem, KnifeItem, AlloyHammerItem, AlloyHoeItem, AlloyPickaxeItem, AlloyShovelItem, AlloySwordItem, IAlloyShield, IAlloyTieredItem.
- Stack-only context (no Level, e.g. getDestroySpeed): iterate stack.getTagEnchantments() holders and match holder.is(RankineEnchantments.KEY).

## For BLOCKS agents
- gases/AbstractGasBlock uses GAS_PROTECTION → RankineEnchantmentHelper.

## For A2/init
- init/RankineEnchantmentTypes.java is dead and must be deleted (already in A2's instructions).

## Accepted behavior deltas
- Pewter/nitinol recipe-conditional enchantability (antiquated, shape_memory) widened to tag-based.
- Old ender_shovel category bug (matched spear item) corrected — endolithic tag contains the shovel.
