# B2 machines (blocks/ subdirectories) — DONE, 144 files verified

Clean sweep confirmed for: net.minecraftforge, LazyOptional, getCapability(, invalidateCaps, CapabilityItemHandler,
NetworkHooks, ForgeHooks, TranslatableComponent, Properties.of(Material, MaterialColor, ForgeRegistries,
IForgeMenuType, sameItem/tagMatches, single-arg load/saveAdditional/getUpdateTag, PoseStack, RenderSystem.setShader*,
java.util.Random, old use(BlockState...), IPlantable/PlantType/ToolAction/AbstractTreeGrower/WoodType.create/getRegistryName().
Every BaseEntityBlock has MapCodec+codec(); every live menu has (int, Inventory, FriendlyByteBuf) ctor; Screens on GuiGraphics.

## PORT-CAP table (for events agent's RegisterCapabilitiesEvent)
Pattern: `event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, <TYPE>.get(), (be, side) -> be.getItemHandler(side))`
All accessors: `@Nullable public IItemHandler getItemHandler(@Nullable Direction side)`. 11 pre-port getCapability decls → 11 accessors, no gaps.

| BlockEntity | BE type constant | Sided mapping (null side → null unless noted) |
|---|---|---|
| AlloyFurnaceTile | ALLOY_FURNACE | UP/DOWN/EAST/WEST, else→SOUTH |
| InductionFurnaceTile | INDUCTION_FURNACE | UP/DOWN/EAST/WEST, else→SOUTH |
| BatteryChargerTile | BATTERY_CHARGER | UP, DOWN, else→NORTH |
| CrucibleTile | CRUCIBLE | UP, DOWN, else→NORTH |
| EvaporationTowerTile | EVAPORATION_TOWER | UP, DOWN, else→NORTH |
| FusionFurnaceTile | FUSION_FURNACE | UP, DOWN, else→NORTH |
| GasBottlerTile | GAS_BOTTLER | UP, DOWN, else→NORTH |
| MixingBarrelTile | MIXING_BARREL | UP, DOWN, else→NORTH |
| MaterialTestingTableTile | MATERIAL_TESTING_TABLE | UP, DOWN, else→NORTH |
| LaserQuarryTile | (unregistered, dormant) | UP, DOWN, else→NORTH |
| PedestalTile | PEDESTAL | ALL sides incl. null → single InvWrapper |

## Cross-package PORT-TODOs owed by others
- init: RankineBlocks must drop trailing PlantType arg for RankinePlantBlock/RankineDoublePlantBlock (all were PlantType.PLAINS).
- init: laserquarry dormant (no BE/Menu/block/config registrations) — leave dormant.
- datacomponents: alloys/AlloyBlock (3 sites) + buildingmodes/BuildingModeBlock currently use CUSTOM_DATA; items agent may supply proper components.
- networking: FluidStackPacket → CustomPacketPayload (FusionFurnaceContainer, MixingBarrelContainer call sites).
- util: GasUtilsEnum Explosion.BlockInteraction.NONE→KEEP; RankineDamageSources new DamageSource(String) → level-based helper; GrassySoilBlock.growGrassBlock must take RandomSource.
- plants: TilledSoilBlock IPlantable sustain check approximated (MAINTAINS_FARMLAND tag + canSustainPlant TriState).
- client: PedestalRenderer must override BlockEntityRenderer#getRenderBoundingBox → new AABB(pos, pos.above(2)).

## CRITICAL contract for recipes agent
14 lookup sites use `getRecipeFor(TYPE.get(), input, level).map(RecipeHolder::value)`. These custom recipe methods are
passed `new RecipeWrapper(new InvWrapper(...))` and MUST accept RecipeInput:
generateResult, getRecipeCookTime, getMixingResult, getOutputFluidReq, getOutputMixTime, matchesRecipe, cannotMake.

## Notable rewrites
- RankineWood: WoodType.register(new WoodType("rankine:"+name, BlockSetType.register(new BlockSetType("rankine:"+name)))); sign ctor arg order flipped (WoodType first); TreeGrower replaces AbstractTreeGrower in factory signature.
- RankineSignBlockEntity: removed illegal getType() override; uses super(BlockEntityType, pos, state).
- PCF package: empty stubs in 1.18 too — intentionally untouched.
