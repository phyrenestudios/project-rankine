package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.commands.BlockWallCommand;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import com.cannolicatfish.rankine.commands.PeriodicTableCommand;
import com.cannolicatfish.rankine.events.handlers.common.*;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.level.block.CreateFluidSourceEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

/**
 * Game-bus event dispatcher. See each handler for the individual 1.18.2 -> 1.21.1 event mapping.
 * The old AttachCapabilitiesEvent&lt;LevelChunk&gt; subscription is gone — the retrogen flag is a
 * data attachment now (see init/RankineAttachments and CapabilityRegistrationHandler).
 */
@EventBusSubscriber(modid = ProjectRankine.MODID)
public class RankineEventHandler {

    @SubscribeEvent
    public static void playSoundAtEntityEvent(PlayLevelSoundEvent.AtEntity event) {
        PlaySoundAtEntityHandler.onPlaySound(event);
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        // 1.21.1: item arguments need the CommandBuildContext
        CreateAlloyCommand.register(event.getDispatcher(), event.getBuildContext());
        GiveTagCommand.register(event.getDispatcher(), event.getBuildContext());
        BlockWallCommand.register(event.getDispatcher());
        PeriodicTableCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Pre event) {
        PlayerTickHandler.onPlayerTick(event);
        PlayerTickHandler.movementModifier(event);
    }
    @SubscribeEvent
    public static void worldLoadEvent(LevelEvent.Load event) {
        WorldLoadHandler.updateFuelValues(event);
    }
    @SubscribeEvent
    public static void serverAboutToStart(ServerAboutToStartEvent event) {
        // Worldgen biome lists can only be resolved once the datapack registries exist.
        WorldgenUtils.ensureBiomesResolved(event.getServer().registryAccess());
    }
    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        FurnaceFuelBurnTimeHandler.updateFuelValues(event);
    }
    @SubscribeEvent
    public static void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        EntityInteractHandler.breedables(event);
    }
    @SubscribeEvent
    public static void itemExpireEvent(ItemExpireEvent event) {
        ItemExpireHandler.onItemPickup(event);
    }
    @SubscribeEvent
    public static void noWater(CreateFluidSourceEvent event) {
        CreateFluidSourceHandler.noWater(event);
    }
    @SubscribeEvent
    public static void entityJoinWorldEvent(EntityJoinLevelEvent event) {
        EntityJoinWorldHandler.onLightningEvent(event);
        EntityJoinWorldHandler.onSheepJoinWorld(event);
    }
    @SubscribeEvent
    public static void onPistonExtend(PistonEvent.Pre event) {
        PistonHandler.onPistonExtendPre(event);
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chunkLoadEvent(ChunkEvent.Load event) {
        ChunkLoadHandler.retrogenChunk(event);
    }
    @SubscribeEvent
    public static void itemPickupEvent(ItemEntityPickupEvent.Post event) {
        ItemPickupHandler.onItemPickup(event);
    }
    @SubscribeEvent
    public static void wandererTradesEvent(WandererTradesEvent event) {
        WandererTradesHandler.addWandererTrades(event);
    }
    @SubscribeEvent
    public static void villagerTradesEvent(VillagerTradesEvent event) {
        VillagerTradesHandler.addVillagerTrades(event);
    }
    @SubscribeEvent
    public static void saplingGrowTreeEvent(BlockGrowFeatureEvent event) {
        SaplingGrowTreeHandler.onSaplingGrow(event);
    }
    @SubscribeEvent
    public static void onCropTrample(BlockEvent.FarmlandTrampleEvent event) {
        FarmlandTrampleHandler.onCropTrample(event);
    }
    @SubscribeEvent
    public static void anvilUpdateEvent(AnvilUpdateEvent event) {
        AnvilUpdateHandler.specialEnchants(event);
    }
    @SubscribeEvent
    public static void livingSetAttackTargetEvent(LivingChangeTargetEvent event) {
        LivingSetAttackTargetHandler.onLivingSetAttackTarget(event);
    }
    @SubscribeEvent
    public static void livingUpdateEvent(EntityTickEvent.Pre event) {
        LivingUpdateHandler.onLivingUpdate(event);
        LivingUpdateHandler.onEnvironmentEffect(event);
    }
    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolModificationEvent event) {
        BlockToolModificationHandler.onToolUse(event);
    }

    @SubscribeEvent
    public static void livingHurt(LivingIncomingDamageEvent event) {
        LivingHurtHandler.onParryEvent(event);
        LivingHurtHandler.onLivingHurt(event);
    }
    @SubscribeEvent
    public static void livingDamageEntity(LivingDamageEvent.Pre event) {
        LivingDamageHandler.onDamageEntity(event);
        LivingDamageHandler.onLivingDamaged(event);
    }
    @SubscribeEvent
    public static void livingDamagePost(LivingDamageEvent.Post event) {
        // 1.21.1: datapack enchantments have no doPostAttack hook — Prying/Puncture run here.
        LivingDamageHandler.onPostDamage(event);
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        LeftClickBlockHandler.leftClickTools(event);
    }
    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event) {
        FluidPlaceBlockHandler.onFluidInteraction(event);
    }
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerLoggedInHandler.onPlayerJoin(event);
    }
    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemAttributeModifierHandler.onItemAttributeModification(event);
    }

    @SubscribeEvent
    public static void breakSpeedEvent(PlayerEvent.BreakSpeed event) {
        BreakSpeedHandler.treeChop(event);
        BreakSpeedHandler.onBreakSpeed(event);
    }
    @SubscribeEvent
    public static void blockBreakEvent(BlockEvent.BreakEvent event) {
        BlockBreakHandler.blockBreakingEvents(event);
    }

    @SubscribeEvent
    public static void rightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        RightClickBlockHandler.onRightClickBlock(event);
        RightClickBlockHandler.rightClickBlockEvent(event);
    }



}
